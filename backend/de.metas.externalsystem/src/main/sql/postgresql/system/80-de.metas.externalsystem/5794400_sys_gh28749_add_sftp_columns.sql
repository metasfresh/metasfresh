-- Migration: Add TransportType and SFTP columns to ExternalSystem_Endpoint
-- Issue: me03#28749 (EDI-Austausch via sFTP)
-- Adds transport protocol selection (HTTP/SFTP) and all SFTP-specific configuration columns.
-- Makes existing HTTP-only columns conditionally mandatory/visible based on TransportType.

-- =============================================================================
-- 1. AD_Reference lists
-- =============================================================================

-- 1a. TransportType reference (List type)
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          Name, Description, ValidationType, EntityType, IsOrderByValue)
VALUES (542077 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'ExternalSystem_Endpoint_TransportType',
        'Transportprotokoll für den Endpunkt: HTTP oder SFTP',
        'L', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Name, Description, IsTranslated,
                               AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 542077 /*From ID Server*/, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Reference_ID = 542077
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID);

-- 1b. SftpAuthType reference (List type)
INSERT INTO AD_Reference (AD_Reference_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          Name, Description, ValidationType, EntityType, IsOrderByValue)
VALUES (542078 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'ExternalSystem_Endpoint_SftpAuthType',
        'SFTP-Authentifizierungsmethode: Passwort oder SSH-Schlüssel',
        'L', 'de.metas.externalsystem', 'N');

INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Name, Description, IsTranslated,
                               AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 542078 /*From ID Server*/, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Reference t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Reference_ID = 542078
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID);

-- =============================================================================
-- 2. AD_Ref_List entries
-- =============================================================================

-- TransportType -> HTTP
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544168 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542077, 'HTTP', 'HTTP', 'HTTP', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description, IsTranslated,
                              AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 544168 /*From ID Server*/, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 544168
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID);

-- TransportType -> SFTP
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544169 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542077, 'SFTP', 'SFTP', 'SFTP', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description, IsTranslated,
                              AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 544169 /*From ID Server*/, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 544169
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID);

-- SftpAuthType -> PASSWORD
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544170 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542078, 'PASSWORD', 'PASSWORD', 'Passwort', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description, IsTranslated,
                              AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 544170 /*From ID Server*/, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 544170
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID);

-- SftpAuthType -> SSH_KEY
INSERT INTO AD_Ref_List (AD_Ref_List_ID, AD_Client_ID, AD_Org_ID, IsActive,
                         Created, CreatedBy, Updated, UpdatedBy,
                         AD_Reference_ID, Value, ValueName, Name, EntityType)
VALUES (544171 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        542078, 'SSH_KEY', 'SSH_KEY', 'SSH-Schlüssel', 'de.metas.externalsystem');

INSERT INTO AD_Ref_List_Trl (AD_Language, AD_Ref_List_ID, Name, Description, IsTranslated,
                              AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 544171 /*From ID Server*/, t.Name, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Ref_List t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Ref_List_ID = 544171
  AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Ref_List_ID = t.AD_Ref_List_ID);

-- English translations for ref list entries
UPDATE AD_Ref_List_Trl SET Name = 'Password', IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544170 AND AD_Language = 'en_US';

UPDATE AD_Ref_List_Trl SET Name = 'SSH Key', IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Ref_List_ID = 544171 AND AD_Language = 'en_US';

-- =============================================================================
-- 3. AD_Element entries (new columns)
-- =============================================================================

-- TransportType
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (584670 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'TransportType', 'Transportart', 'Transportart',
        'Transportprotokoll: HTTP für Web-APIs, SFTP für dateibasierten Austausch',
        'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584670 /*From ID Server*/, t.Name, t.PrintName, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584670
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'Transport Type', PrintName = 'Transport Type',
  Description = 'Transport protocol: HTTP for web APIs, SFTP for file-based exchange',
  IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584670 AND AD_Language = 'en_US';

-- SftpHost
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (584671 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'SftpHost', 'SFTP-Host', 'SFTP-Host',
        'Hostname oder IP-Adresse des SFTP-Servers',
        'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584671 /*From ID Server*/, t.Name, t.PrintName, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584671
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'SFTP Host', PrintName = 'SFTP Host',
  Description = 'Hostname or IP address of the SFTP server',
  IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584671 AND AD_Language = 'en_US';

-- SftpPort
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (584672 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'SftpPort', 'SFTP-Port', 'SFTP-Port',
        'Portnummer für die SFTP-Verbindung (Standard: 22)',
        'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584672 /*From ID Server*/, t.Name, t.PrintName, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584672
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'SFTP Port', PrintName = 'SFTP Port',
  Description = 'Port number for SFTP connection (default: 22)',
  IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584672 AND AD_Language = 'en_US';

-- SftpUsername
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (584673 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'SftpUsername', 'SFTP-Benutzername', 'SFTP-Benutzername',
        'Benutzername für die SFTP-Authentifizierung',
        'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584673 /*From ID Server*/, t.Name, t.PrintName, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584673
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'SFTP Username', PrintName = 'SFTP Username',
  Description = 'Username for SFTP authentication',
  IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584673 AND AD_Language = 'en_US';

-- SftpAuthType
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (584674 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'SftpAuthType', 'SFTP-Authentifizierung', 'SFTP-Authentifizierung',
        'SFTP-Authentifizierungsmethode: Passwort oder SSH-Schlüssel',
        'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584674 /*From ID Server*/, t.Name, t.PrintName, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584674
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'SFTP Auth Type', PrintName = 'SFTP Auth Type',
  Description = 'SFTP authentication method: password or SSH private key',
  IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584674 AND AD_Language = 'en_US';

-- SshPrivateKey
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (584675 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'SshPrivateKey', 'SSH-Private-Key', 'SSH-Private-Key',
        'SSH-Private-Key-Inhalt für schlüsselbasierte SFTP-Authentifizierung',
        'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584675 /*From ID Server*/, t.Name, t.PrintName, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584675
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'SSH Private Key', PrintName = 'SSH Private Key',
  Description = 'SSH private key content for key-based SFTP authentication',
  IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584675 AND AD_Language = 'en_US';

-- SftpRemotePath
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (584676 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'SftpRemotePath', 'SFTP-Verzeichnispfad', 'SFTP-Verzeichnispfad',
        'Verzeichnispfad auf dem SFTP-Server für den Dateiaustausch',
        'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584676 /*From ID Server*/, t.Name, t.PrintName, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584676
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'SFTP Remote Path', PrintName = 'SFTP Remote Path',
  Description = 'Directory path on the SFTP server for file exchange',
  IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584676 AND AD_Language = 'en_US';

-- SftpFilenamePattern
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (584677 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'SftpFilenamePattern', 'SFTP-Dateinamensmuster', 'SFTP-Dateinamensmuster',
        'Muster für ausgehende Dateinamen, z.B. DESADV_{documentno}_{timestamp}.json',
        'de.metas.externalsystem');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 584677 /*From ID Server*/, t.Name, t.PrintName, t.Description, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584677
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name = 'SFTP Filename Pattern', PrintName = 'SFTP Filename Pattern',
  Description = 'Pattern for outbound filenames, e.g., DESADV_{documentno}_{timestamp}.json',
  IsTranslated = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584677 AND AD_Language = 'en_US';

-- =============================================================================
-- 4. AD_Column entries (new columns on ExternalSystem_Endpoint, AD_Table_ID=542551)
-- =============================================================================

-- TransportType: VARCHAR(10), NOT NULL, default 'HTTP', List reference
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID, AD_Reference_Value_ID,
                       IsMandatory, DefaultValue, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       MandatoryLogic, PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592242 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584670, 542551, 'TransportType', 'Transportart',
        'Transportprotokoll: HTTP für Web-APIs, SFTP für dateibasierten Austausch',
        0, 'de.metas.externalsystem', 17, 542077,
        'Y', 'HTTP', 'Y', 'N', 'N', 'N',
        10, 'N', 'Y', 'N',
        NULL, 'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592242
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584670);

-- SftpHost: VARCHAR(255), nullable
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       MandatoryLogic, PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592243 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584671, 542551, 'SftpHost', 'SFTP-Host',
        'Hostname oder IP-Adresse des SFTP-Servers',
        0, 'de.metas.externalsystem', 10,
        'N', 'Y', 'N', 'N', 'N',
        255, 'N', 'N', 'N',
        '@TransportType/X@=''SFTP''', 'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592243
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584671);

-- SftpPort: INTEGER, nullable, default 22
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, DefaultValue, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       MandatoryLogic, PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592244 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584672, 542551, 'SftpPort', 'SFTP-Port',
        'Portnummer für die SFTP-Verbindung (Standard: 22)',
        0, 'de.metas.externalsystem', 11,
        'N', '22', 'Y', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        '@TransportType/X@=''SFTP''', 'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592244
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584672);

-- SftpUsername: VARCHAR(255), nullable
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       MandatoryLogic, PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592245 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584673, 542551, 'SftpUsername', 'SFTP-Benutzername',
        'Benutzername für die SFTP-Authentifizierung',
        0, 'de.metas.externalsystem', 10,
        'N', 'Y', 'N', 'N', 'N',
        255, 'N', 'N', 'N',
        '@TransportType/X@=''SFTP''', 'P',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592245
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584673);

-- SftpAuthType: VARCHAR(10), nullable, List reference
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID, AD_Reference_Value_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       MandatoryLogic, PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592246 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584674, 542551, 'SftpAuthType', 'SFTP-Authentifizierung',
        'SFTP-Authentifizierungsmethode: Passwort oder SSH-Schlüssel',
        0, 'de.metas.externalsystem', 17, 542078,
        'N', 'Y', 'N', 'N', 'N',
        10, 'N', 'N', 'N',
        '@TransportType/X@=''SFTP''', 'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592246
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584674);

-- SshPrivateKey: TEXT, nullable
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       MandatoryLogic, PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592247 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584675, 542551, 'SshPrivateKey', 'SSH-Private-Key',
        'SSH-Private-Key-Inhalt für schlüsselbasierte SFTP-Authentifizierung',
        0, 'de.metas.externalsystem', 14,
        'N', 'Y', 'N', 'N', 'N',
        0, 'N', 'N', 'N',
        '@TransportType/X@=''SFTP'' & @SftpAuthType/X@=''SSH_KEY''', 'SP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'Y', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592247
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584675);

-- SftpRemotePath: VARCHAR(1024), nullable
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       MandatoryLogic, PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592248 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584676, 542551, 'SftpRemotePath', 'SFTP-Verzeichnispfad',
        'Verzeichnispfad auf dem SFTP-Server für den Dateiaustausch',
        0, 'de.metas.externalsystem', 10,
        'N', 'Y', 'N', 'N', 'N',
        1024, 'N', 'N', 'N',
        '@TransportType/X@=''SFTP''', 'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592248
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584676);

-- SftpFilenamePattern: VARCHAR(255), nullable
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, IsUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn, IsAlwaysUpdateable,
                       MandatoryLogic, PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (592249 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        584677, 542551, 'SftpFilenamePattern', 'SFTP-Dateinamensmuster',
        'Muster für ausgehende Dateinamen, z.B. DESADV_{documentno}_{timestamp}.json',
        0, 'de.metas.externalsystem', 10,
        'N', 'Y', 'N', 'N', 'N',
        255, 'N', 'N', 'N',
        NULL, 'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0);

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592249
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

SELECT update_Column_Translation_From_AD_Element(584677);

-- =============================================================================
-- 5. Physical table columns (DDL)
-- =============================================================================

-- TransportType: add column, populate existing rows, then NOT NULL
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS TransportType VARCHAR(10);
UPDATE ExternalSystem_Endpoint SET TransportType = 'HTTP' WHERE TransportType IS NULL;
ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN TransportType SET NOT NULL;
ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN TransportType SET DEFAULT 'HTTP';

-- Nullable SFTP columns
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpHost VARCHAR(255);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpPort INTEGER DEFAULT 22;
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpUsername VARCHAR(255);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpAuthType VARCHAR(10);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SshPrivateKey TEXT;
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpRemotePath VARCHAR(1024);
ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IF NOT EXISTS SftpFilenamePattern VARCHAR(255);

-- CHECK constraints to enforce valid reference list values at DB level
ALTER TABLE ExternalSystem_Endpoint ADD CONSTRAINT ck_endpoint_transporttype
	CHECK (TransportType IN ('HTTP', 'SFTP'));

ALTER TABLE ExternalSystem_Endpoint ADD CONSTRAINT ck_endpoint_sftpauthtype
	CHECK (SftpAuthType IS NULL OR SftpAuthType IN ('PASSWORD', 'SSH_KEY'));

-- =============================================================================
-- 6. Make existing HTTP columns nullable (AD_Column + physical DDL)
-- =============================================================================

-- OutboundHttpEP: make nullable
UPDATE AD_Column SET IsMandatory = 'N',
  MandatoryLogic = '@TransportType/X@=''HTTP''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Column_ID = 591478;

ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN OutboundHttpEP DROP NOT NULL;

-- OutboundHttpMethod: make nullable
UPDATE AD_Column SET IsMandatory = 'N',
  MandatoryLogic = '@TransportType/X@=''HTTP''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Column_ID = 591479;

ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN OutboundHttpMethod DROP NOT NULL;

-- ContentType: make nullable
UPDATE AD_Column SET IsMandatory = 'N',
  MandatoryLogic = '@TransportType/X@=''HTTP''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Column_ID = 592116;

ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN ContentType DROP NOT NULL;

-- Password: add MandatoryLogic (stays nullable in AD, just logic for UI enforcement)
UPDATE AD_Column SET
  MandatoryLogic = '@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'' | @TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Column_ID = 591488;

-- =============================================================================
-- 7. AD_Field entries for new columns (AD_Tab_ID=548506)
-- =============================================================================

-- TransportType
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774924 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592242, 548506, 'Transportart',
        'Transportprotokoll: HTTP für Web-APIs, SFTP für dateibasierten Austausch',
        'de.metas.externalsystem',
        'Y', 'Y', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 774924
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- SftpHost
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774925 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592243, 548506, 'SFTP-Host',
        'Hostname oder IP-Adresse des SFTP-Servers',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 774925
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- SftpPort
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774926 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592244, 548506, 'SFTP-Port',
        'Portnummer für die SFTP-Verbindung (Standard: 22)',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 774926
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- SftpUsername
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774927 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592245, 548506, 'SFTP-Benutzername',
        'Benutzername für die SFTP-Authentifizierung',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 774927
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- SftpAuthType
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774928 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592246, 548506, 'SFTP-Authentifizierung',
        'SFTP-Authentifizierungsmethode: Passwort oder SSH-Schlüssel',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 774928
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- SshPrivateKey
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774929 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592247, 548506, 'SSH-Private-Key',
        'SSH-Private-Key-Inhalt für schlüsselbasierte SFTP-Authentifizierung',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP'' & @SftpAuthType/X@=''SSH_KEY''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 774929
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- SftpRemotePath
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774930 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592248, 548506, 'SFTP-Verzeichnispfad',
        'Verzeichnispfad auf dem SFTP-Server für den Dateiaustausch',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 774930
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- SftpFilenamePattern
INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, EntityType,
                      DisplayLogic, IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly)
VALUES (774931 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        592249, 548506, 'SFTP-Dateinamensmuster',
        'Muster für ausgehende Dateinamen, z.B. DESADV_{documentno}_{timestamp}.json',
        'de.metas.externalsystem',
        '@TransportType/X@=''SFTP''',
        'Y', 'N', 'N', 'N', 'N');

INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 774931
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID);

-- =============================================================================
-- 8. Update DisplayLogic on existing HTTP fields
-- =============================================================================

-- OutboundHttpEP: show only for HTTP
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 755940;

-- OutboundHttpMethod: show only for HTTP
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 755941;

-- AuthType: show only for HTTP
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 755942;

-- ContentType: show only for HTTP
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 774765;

-- Password: show for HTTP+Basic or SFTP+PASSWORD
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP'' & @AuthType/X@=''Basic'' | @TransportType/X@=''SFTP'' & @SftpAuthType/X@=''PASSWORD''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 755950;

-- AuthToken: show only for HTTP+Token
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP'' & @AuthType/X@=''Token''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 755946;

-- ClientId: show only for HTTP+OAuth
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 755948;

-- ClientSecret: show only for HTTP+OAuth
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP'' & @AuthType/X@=''OAuth''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 755947;

-- LoginUsername: show only for HTTP+OAuth or HTTP+Basic
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP'' & (@AuthType/X@=''OAuth'' | @AuthType/X@=''Basic'')',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 755949;

-- SasSignature: show only for HTTP+SAS
UPDATE AD_Field SET DisplayLogic = '@TransportType/X@=''HTTP'' & @AuthType/X@=''SAS''',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID = 756181;

-- =============================================================================
-- 9. Window layout: AD_UI_Section + AD_UI_Column + AD_UI_ElementGroup
-- =============================================================================

-- Add TransportType to existing main group (553738, section 547038, column 548586)
-- Insert after Art (Type) at SeqNo 25
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774924, 0, 548506, 553738,
        648566 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'Y',
        'N', 'N', 0,
        'Transportart', 25, 10, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- New section for advanced edit: "Transport" section
INSERT INTO AD_UI_Section (AD_UI_Section_ID, AD_Client_ID, AD_Org_ID, IsActive,
                           Created, CreatedBy, Updated, UpdatedBy,
                           AD_Tab_ID, SeqNo, Name)
VALUES (547602 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        548506, 20, 'Transport');

-- Left column in transport section
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          AD_UI_Section_ID, SeqNo)
VALUES (549281 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        547602, 10);

-- Right column in transport section
INSERT INTO AD_UI_Column (AD_UI_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                          Created, CreatedBy, Updated, UpdatedBy,
                          AD_UI_Section_ID, SeqNo)
VALUES (549282 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        547602, 20);

-- HTTP element group (left column)
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
                                 Created, CreatedBy, Updated, UpdatedBy,
                                 AD_UI_Column_ID, SeqNo, Name)
VALUES (554995 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        549281, 10, 'HTTP');

-- SFTP element group (right column)
INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID, AD_Client_ID, AD_Org_ID, IsActive,
                                 Created, CreatedBy, Updated, UpdatedBy,
                                 AD_UI_Column_ID, SeqNo, Name)
VALUES (554996 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        549282, 10, 'SFTP');

-- =============================================================================
-- 10. AD_UI_Element entries in the HTTP group
-- =============================================================================

-- HTTP group: OutboundHttpEP
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 755940, 0, 548506, 554995,
        648567 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'Ausgehender HTTP-Endpunkt', 10, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- HTTP group: OutboundHttpMethod
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 755941, 0, 548506, 554995,
        648568 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'Ausgehende HTTP-Methode', 20, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- HTTP group: AuthType
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 755942, 0, 548506, 554995,
        648569 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'Authentifizierungstyp', 30, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- HTTP group: ContentType
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774765, 0, 548506, 554995,
        648570 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'Content type', 40, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- HTTP group: Password
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 755950, 0, 548506, 554995,
        648571 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'Kennwort', 50, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- =============================================================================
-- 11. AD_UI_Element entries in the SFTP group
-- =============================================================================

-- SFTP group: SftpHost
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774925, 0, 548506, 554996,
        648572 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'SFTP-Host', 10, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- SFTP group: SftpPort
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774926, 0, 548506, 554996,
        648573 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'SFTP-Port', 20, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- SFTP group: SftpUsername
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774927, 0, 548506, 554996,
        648574 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'SFTP-Benutzername', 30, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- SFTP group: SftpAuthType
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774928, 0, 548506, 554996,
        648575 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'SFTP-Authentifizierung', 40, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- SFTP group: SshPrivateKey
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774929, 0, 548506, 554996,
        648576 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'Y', 5,
        'SSH-Private-Key', 50, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- SFTP group: SftpRemotePath
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774930, 0, 548506, 554996,
        648577 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'SFTP-Verzeichnispfad', 60, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- SFTP group: SftpFilenamePattern
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID,
                           AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, IsActive,
                           IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid,
                           IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount,
                           Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774931, 0, 548506, 554996,
        648578 /*From ID Server*/, 'F',
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100, 'Y',
        'N', 'N', 'Y', 'N',
        'N', 'N', 0,
        'SFTP-Dateinamensmuster', 70, 0, 0,
        TO_TIMESTAMP('2026-03-13 10:00', 'YYYY-MM-DD HH24:MI'), 100);

-- =============================================================================
-- 12. Grid view: set IsDisplayedGrid + SeqNoGrid on existing UI elements for grid columns
-- =============================================================================

-- Value (already in main group, SeqNo=10): show in grid
UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 10,
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638543;

-- TransportType: already set via insert (SeqNoGrid=10 in main) — update to grid seq 20
UPDATE AD_UI_Element SET SeqNoGrid = 20,
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 648566;

-- OutboundHttpEP in main group: set grid seq 30
UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 30,
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638545;

-- IsActive in flags group: show in grid
UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 40,
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638548;

-- AD_Org_ID in org group: show in grid
UPDATE AD_UI_Element SET IsDisplayedGrid = 'Y', SeqNoGrid = 50,
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 638549;

-- =============================================================================
-- 13. Filter columns: TransportType, Value
-- =============================================================================

-- TransportType is already IsSelectionColumn='Y' in AD_Column (set above)
-- Value: make it a selection/filter column
UPDATE AD_Column SET IsSelectionColumn = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Column_ID = 591482;

-- TransportType filter: enable in UI Element
UPDATE AD_UI_Element SET IsAllowFiltering = 'Y',
  Updated = TO_TIMESTAMP('2026-03-13 10:01', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_UI_Element_ID = 648566;
