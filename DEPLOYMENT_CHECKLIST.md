# Cabinet-API — Checklist MVP & Déploiement

> Audit réalisé le 2026-05-20 sur la branche `dev`.
> Stack : JHipster (Spring Boot) + Angular + PostgreSQL.

---

## État actuel — ce qui est déjà fait

**Backend (Java)**

- Entités : `Patient`, `Appointement`, `Prescription`, `PrescriptionItem`, `Invoice`, `InvoiceItem`
- REST resources correspondantes
- Authentification JWT JHipster fonctionnelle

**Frontend (Angular)**

- Modules : patient, appointment (commités)
- Modules : prescription, invoice (présents mais non commités)

---

## Fonctionnalités MVP essentielles

### 1. Authentification & rôles

- [ ] Rôles distincts : médecin, secrétaire, admin (avec permissions)
- [x] Connexion sécurisée (JHipster JWT)
- [x] Réinitialisation mot de passe (JHipster)

### 2. Gestion patients

- [x] Fiche complète : état civil, contact, antécédents, allergies
- [ ] Historique des consultations consultable rapidement

### 3. Agenda / rendez-vous

- [x] CRUD rendez-vous
- [ ] Vue calendrier (jour/semaine) — actuellement liste simple
- [x] Statuts : confirmé, annulé, terminé, no-show
- [ ] Détection des conflits d'horaires côté backend (`AppointementService`)

### 4. Consultation / dossier médical

- [ ] Note de consultation liée au RDV
- [ ] Champs : motif, diagnostic, observations
- [ ] **C'est le cœur du métier — souvent oublié au profit du CRUD**

### 5. Prescriptions

- [x] Backend entité + resource
- [ ] Module frontend à commiter
- [ ] Génération PDF imprimable avec en-tête cabinet
- [ ] Modèles d'ordonnance réutilisables

### 6. Facturation

- [x] Backend entité + resource
- [ ] Module frontend à commiter
- [ ] Statut payé/impayé, mode de paiement
- [ ] Export PDF

### 7. Dashboard minimal

- [ ] RDV du jour, nombre de patients, factures impayées

---

## Problèmes BLOQUANTS pour le déploiement

### 1. Trou de sécurité critique — `SecurityConfiguration.java:71`

```java
.requestMatchers(mvc.pattern("/api/**")).permitAll()
```

**Tous les endpoints API sont publics, sans authentification.**
N'importe qui sur internet peut lire/modifier patients, ordonnances, factures.
**Inacceptable pour des données de santé.**

À remplacer par `.authenticated()` et ajouter des `@PreAuthorize` par rôle sur les resources.

### 2. Secret JWT en clair dans le repo — `application-dev.yml:64`

Le `base64-secret` JWT est commité.
→ Pour la prod : générer un nouveau secret et l'injecter via variable d'environnement
`JHIPSTER_SECURITY_AUTHENTICATION_JWT_BASE64_SECRET`.

### 3. Mot de passe BDD en clair — `application-dev.yml:94` (`password: admin`)

Idem : variables d'environnement pour la prod, profile `prod` séparé.

### 4. Modèle de données incohérent

- `Patient.id` est `Integer` mais `Appointement.id` est `Long` → uniformiser sur `Long` partout.
- Champs avec fautes :
  - `emeregencyContactPhone` → `emergencyContactPhone`
  - `chronicdiseases` → `chronicDiseases`
- Doublon `nom` + `lastName` dans `Patient` → garder un seul champ.
- **À corriger maintenant avant qu'il y ait des données réelles.**

### 5. Pas de validation côté backend

Les champs `email`, `phone`, `dateBirth` n'ont pas de `@NotNull`/`@Email`/`@Pattern`.
→ Ajouter `jakarta.validation` sur les domaines.

### 6. Vérifier les relations

S'assurer que `Prescription` et `Invoice` ont bien un `@ManyToOne Patient`
(sinon impossible de retrouver les ordonnances/factures d'un patient).

---

## Checklist avant déploiement

### Sécurité (OBLIGATOIRE)

- [ ] Fermer `/api/**` derrière `.authenticated()`
- [ ] `@PreAuthorize` par rôle sur Patient/Prescription/Invoice Resources
- [ ] Profile `prod` avec secrets via env vars (JWT, BDD, mail)
- [ ] Activer HTTPS (reverse proxy Nginx + Let's Encrypt)
- [ ] Vérifier que les logs ne contiennent pas de données patient (DEBUG actuellement actif)

### Fonctionnel (à finir)

- [ ] Commiter le module Invoice frontend
- [ ] Commiter le module Prescription frontend
- [ ] Génération PDF ordonnance + facture (avec en-tête cabinet)
- [ ] Vue agenda calendrier (actuellement c'est juste une liste)
- [ ] Détection des conflits d'horaires côté backend
- [ ] Page consultation rattachée au RDV (notes médecin)

### Données / qualité

- [ ] Uniformiser les IDs en `Long`
- [ ] Corriger les fautes de frappe sur les champs (et migration Liquibase)
- [ ] Validations Bean (`@NotNull`, `@Email`, `@Pattern` téléphone)
- [ ] Migration Liquibase propre (pas de `update` automatique en prod)

### Compliance santé

- [ ] Audit log : qui consulte quel dossier (Envers ou table dédiée)
- [ ] Page CGU / mentions légales / politique de confidentialité
- [ ] Procédure de sauvegarde BDD (cron quotidien chiffré)
- [ ] Export données patient (RGPD article 20)
- [ ] Suppression données patient (RGPD article 17 — droit à l'oubli)

### Déploiement

- [ ] Dockerfile + docker-compose pour prod (Postgres + app + nginx)
- [ ] Domaine + certificat SSL
- [ ] Configurer SMTP pour les emails (reset password, etc.)
- [ ] Monitoring minimal (Spring Actuator + alerting sur `/management/health`)

---

## À reporter post-MVP

Ne pas inclure dans le premier déploiement :

- SMS de rappel automatiques
- Prise de RDV en ligne pour patients (espace patient public)
- Téléconsultation
- Gestion stock médicaments
- Intégration assurance / tiers payant
- Statistiques avancées / reporting
- Multi-cabinets / multi-praticiens

**Pourquoi reporter le portail patient ?** Ça double la surface de sécurité et complique le déploiement. Sors d'abord la version "interne cabinet", valide avec 1-2 médecins, puis ajoute le portail patient.

---

## Priorisation suggérée

| Semaine | Focus                                                             |
| ------- | ----------------------------------------------------------------- |
| 1-2     | Fixer la sécurité (#1, #2, #3) + finir modules frontend manquants |
| 3       | Modèle de données propre + validations + génération PDF           |
| 4       | Compliance + déploiement Docker + test avec un médecin pilote     |
