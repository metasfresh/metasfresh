-- Direction descriptions for the three endpoint fields whose AD_Column uses a SHARED AD_Element
-- (ContentType, AuthToken, LoginUsername). A plain AD_Field.Description override does not survive
-- after_migration_sync_translations (it re-propagates the field description from the column's shared,
-- empty element). So fork a dedicated element per field (same label, new description) and point the
-- endpoint field's AD_Name_ID at it. These are outbound HTTP-transport fields -> export only.
--
-- IDs allocated from idserver.metas.de on 2026-07-20:
--   AD_Element 585112 (ContentType field), 585113 (AuthToken field), 585114 (LoginUsername field)

-- =============================== ContentType (field 774765) ===============================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, Description, EntityType)
VALUES (585112 /*From ID Server*/, 0, 0, 'Y', now(), 100, now(), 100, 'ExtSysEndpoint_ContentTypeOut', 'Content type', 'Content type', 'Content-Type der ausgehenden HTTP-Anfrage (nur Export).', 'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585112
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Content type',    Description='Content-Type der ausgehenden HTTP-Anfrage (nur Export).',       IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585112 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Content type',    Description='Content type of the outbound HTTP request (export only).',       IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585112 AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Name='Type de contenu', Description='Type de contenu de la requête HTTP sortante (export uniquement).',IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585112 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Name='Content type',    Description='Tipo di contenuto della richiesta HTTP in uscita (solo export).', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585112 AND AD_Language='it_CH';

UPDATE AD_Field SET AD_Name_ID=585112, Name='Content type', Description='Content-Type der ausgehenden HTTP-Anfrage (nur Export).', Updated=now(), UpdatedBy=100 WHERE AD_Field_ID=774765;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585112, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585112, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585112, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585112, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585112, 'it_CH');

-- =============================== AuthToken (field 755946) ===============================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, Description, EntityType)
VALUES (585113 /*From ID Server*/, 0, 0, 'Y', now(), 100, now(), 100, 'ExtSysEndpoint_AuthTokenOut', 'Authentifizierungs-Token', 'Authentifizierungs-Token', 'Authentifizierungs-Token für ausgehende HTTP-Aufrufe (nur Export).', 'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585113
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Authentifizierungs-Token', Description='Authentifizierungs-Token für ausgehende HTTP-Aufrufe (nur Export).',              IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585113 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Authentication Token',     Description='Authentication token for outbound HTTP calls (export only).',                    IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585113 AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Name='Jeton d''authentification',Description='Jeton d''authentification pour les appels HTTP sortants (export uniquement).',    IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585113 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Name='Authentication Token',     Description='Token di autenticazione per le chiamate HTTP in uscita (solo export).',          IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585113 AND AD_Language='it_CH';

UPDATE AD_Field SET AD_Name_ID=585113, Name='Authentifizierungs-Token', Description='Authentifizierungs-Token für ausgehende HTTP-Aufrufe (nur Export).', Updated=now(), UpdatedBy=100 WHERE AD_Field_ID=755946;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585113, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585113, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585113, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585113, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585113, 'it_CH');

-- =============================== LoginUsername (field 755949) ===============================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, Name, PrintName, Description, EntityType)
VALUES (585114 /*From ID Server*/, 0, 0, 'Y', now(), 100, now(), 100, 'ExtSysEndpoint_LoginUsernameOut', 'Login Nutzer Name', 'Login Nutzer Name', 'Benutzername für die Authentifizierung ausgehender HTTP-Aufrufe (nur Export).', 'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.IsActive, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585114
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Login Nutzer Name',           Description='Benutzername für die Authentifizierung ausgehender HTTP-Aufrufe (nur Export).',                 IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585114 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Name='Login User Name',             Description='Username for authentication of outbound HTTP calls (export only).',                             IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585114 AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Name='Nom d''utilisateur connexion',Description='Nom d''utilisateur pour l''authentification des appels HTTP sortants (export uniquement).',      IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585114 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Name='Login User Name',             Description='Nome utente per l''autenticazione delle chiamate HTTP in uscita (solo export).',                IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585114 AND AD_Language='it_CH';

UPDATE AD_Field SET AD_Name_ID=585114, Name='Login Nutzer Name', Description='Benutzername für die Authentifizierung ausgehender HTTP-Aufrufe (nur Export).', Updated=now(), UpdatedBy=100 WHERE AD_Field_ID=755949;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585114, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585114, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585114, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585114, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585114, 'it_CH');
