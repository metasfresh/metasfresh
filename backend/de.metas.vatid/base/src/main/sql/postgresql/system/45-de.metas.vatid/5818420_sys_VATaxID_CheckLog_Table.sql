-- VAT-ID online check: VATaxID_CheckLog table.
-- One row per check attempt (legal evidence), append-only except the single transition from
-- 'RequestSent' to its final status when the answer arrives. AD_PInstance_ID and AD_Session_ID
-- are indexed but deliberately carry NO foreign key: both parent tables (AD_PInstance,
-- AD_Session) are purge targets, and an FK would either block the purge or cascade the evidence
-- away. IsDeleteable='N' on the AD_Table reflects the "never deleted" retention rule.

-- IDs allocated from idserver.metas.de:
--   AD_Table            542639
--   AD_Element          585185..585194 (10: the table's own ID column + 9 business columns)
--   AD_Column           593164..593185 (22: 8 standard + 14 business, in column order)

-- 1. Physical table
CREATE TABLE VATaxID_CheckLog
(
    VATaxID_CheckLog_ID     NUMERIC(10)  NOT NULL,
    AD_Client_ID            NUMERIC(10)  NOT NULL,
    AD_Org_ID               NUMERIC(10)  NOT NULL,
    IsActive                CHAR(1)      NOT NULL DEFAULT 'Y',
    Created                 TIMESTAMP    NOT NULL,
    CreatedBy               NUMERIC(10)  NOT NULL,
    Updated                 TIMESTAMP    NOT NULL,
    UpdatedBy               NUMERIC(10)  NOT NULL,
    C_BPartner_ID           NUMERIC(10)  NOT NULL,
    C_BPartner_Location_ID  NUMERIC(10),
    VATaxID                 VARCHAR(60)  NOT NULL,
    VATaxIDStatus           VARCHAR(20)  NOT NULL,
    RequestDate             TIMESTAMP    NOT NULL,
    ResponseDate            TIMESTAMP,
    RequestIdentifier       VARCHAR(50),
    AD_PInstance_ID         NUMERIC(10),
    AD_Session_ID           NUMERIC(10),
    ReturnedName            VARCHAR(400),
    ReturnedAddress         VARCHAR(400),
    TraderNameMatch         VARCHAR(20),
    TraderAddressMatch      VARCHAR(20),
    RawResponse             TEXT,
    CONSTRAINT VATaxID_CheckLog_key PRIMARY KEY (VATaxID_CheckLog_ID),
    CONSTRAINT VATaxID_CheckLog_C_BPartner_ID_FK FOREIGN KEY (C_BPartner_ID)
        REFERENCES C_BPartner (C_BPartner_ID),
    CONSTRAINT VATaxID_CheckLog_C_BPartner_Location_ID_FK FOREIGN KEY (C_BPartner_Location_ID)
        REFERENCES C_BPartner_Location (C_BPartner_Location_ID),
    CONSTRAINT VATaxID_CheckLog_IsActive_check CHECK (IsActive IN ('Y', 'N')),
    CONSTRAINT VATaxID_CheckLog_VATaxIDStatus_check CHECK (VATaxIDStatus IN
        ('NotChecked', 'RequestSent', 'Valid', 'Invalid', 'NotSupported', 'ServiceUnavailable'))
);

COMMENT ON TABLE VATaxID_CheckLog IS 'One row per VAT-ID online check attempt against VIES; legal evidence, never deleted.';

-- Deliberately NO foreign key on AD_PInstance_ID / AD_Session_ID (see header comment).
CREATE INDEX VATaxID_CheckLog_C_BPartner_ID_idx ON VATaxID_CheckLog (C_BPartner_ID);
CREATE INDEX VATaxID_CheckLog_C_BPartner_Location_ID_idx ON VATaxID_CheckLog (C_BPartner_Location_ID);
CREATE INDEX VATaxID_CheckLog_AD_PInstance_ID_idx ON VATaxID_CheckLog (AD_PInstance_ID);
CREATE INDEX VATaxID_CheckLog_AD_Session_ID_idx ON VATaxID_CheckLog (AD_Session_ID);
CREATE INDEX VATaxID_CheckLog_VATaxID_RequestDate_idx ON VATaxID_CheckLog (VATaxID, RequestDate);

-- 2. AD_Table (cloned from X_TableTemplate 540290); IsDeleteable='N' — evidence rows are never deleted.
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Name, TableName, AccessLevel, IsView, IsSecurityEnabled, IsChangeLog, IsDeleteable,
                       IsHighVolume, LoadSeq, EntityType, ImportTable)
VALUES (542639 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-12 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-12 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'VATaxID_CheckLog', 'VATaxID_CheckLog', '3', 'N', 'Y', 'Y', 'N',
        'Y', 0, 'D', 'N');

-- 3. AD_Element for the table's own ID column
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         ColumnName, Name, PrintName, Description, EntityType)
VALUES (585185 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-12 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-12 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'VATaxID_CheckLog_ID', 'USt-IdNr.-Prüfprotokoll', 'USt-IdNr.-Prüfprotokoll',
        'Protokoll der einzelnen USt-IdNr.-Prüfversuche gegen VIES.', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585185
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Name = 'VAT-ID Check Log', PrintName = 'VAT-ID Check Log',
    Description = 'Log of individual VAT-ID online check attempts against VIES.',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585185;

UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-12 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585185;

-- 4. AD_Elements for the nine new business columns (VATaxID reuses shared element 502388;
--    C_BPartner_ID/_Location_ID reuse standard elements 187/189 — no new element needed for those three).
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                         ColumnName, Name, PrintName, Description, EntityType)
VALUES
    (585186 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RequestDate', 'Anfrage gesendet am', 'Anfrage gesendet am',
     'Zeitpunkt, zu dem die USt-IdNr.-Prüfanfrage an VIES gesendet wurde.', 'D'),
    (585187 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ResponseDate', 'Antwort erhalten am', 'Antwort erhalten am',
     'Zeitpunkt, zu dem die Antwort von VIES eingetroffen ist; leer solange die Prüfung ansteht.', 'D'),
    (585188 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RequestIdentifier', 'VIES-Vorgangsnummer', 'VIES-Vorgangsnummer',
     'Von VIES zurückgegebene Vorgangsnummer der Konsultation; nur vorhanden, wenn die anfragende USt-IdNr. konfiguriert ist.', 'D'),
    (585189 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ReturnedName', 'Von VIES zurückgegebener Name', 'Von VIES zurückgegebener Name',
     'Firmenname, den VIES zur geprüften USt-IdNr. zurückgegeben hat (qualifizierte Prüfung, noch nicht genutzt).', 'D'),
    (585190 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'ReturnedAddress', 'Von VIES zurückgegebene Adresse', 'Von VIES zurückgegebene Adresse',
     'Anschrift, die VIES zur geprüften USt-IdNr. zurückgegeben hat (qualifizierte Prüfung, noch nicht genutzt).', 'D'),
    (585191 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'TraderNameMatch', 'Namensabgleich', 'Namensabgleich',
     'Ergebnis des Namensabgleichs der qualifizierten VIES-Prüfung (noch nicht genutzt).', 'D'),
    (585192 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'TraderAddressMatch', 'Adressabgleich', 'Adressabgleich',
     'Ergebnis des Adressabgleichs der qualifizierten VIES-Prüfung (noch nicht genutzt).', 'D'),
    (585193 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:17', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'RawResponse', 'VIES-Rohantwort', 'VIES-Rohantwort',
     'Unverarbeitete Antwort des VIES-Dienstes, zur Nachvollziehbarkeit im Streitfall.', 'D'),
    -- VATaxIDStatus: same column name/element as will be used on C_BPartner / C_BPartner_Location (follow-up task) —
    -- created here first and meant to be reused there, per the design's "same column name, deliberately".
    (585194 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-08-12 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'VATaxIDStatus', 'USt-IdNr.-Prüfstatus', 'USt-IdNr.-Prüfstatus',
     'Ergebnis der USt-IdNr.-Prüfung.', 'D');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID IN (585186, 585187, 585188, 585189, 585190, 585191, 585192, 585193, 585194)
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Request Sent On', PrintName = 'Request Sent On',
    Description = 'Point in time the VAT-ID check request was sent to VIES.',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585186;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Response Received On', PrintName = 'Response Received On',
    Description = 'Point in time the VIES response arrived; empty while the check is still pending.',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585187;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'VIES Consultation Number', PrintName = 'VIES Consultation Number',
    Description = 'Consultation number returned by VIES; only present when the requester VAT-ID is configured.',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:22', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585188;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Name Returned by VIES', PrintName = 'Name Returned by VIES',
    Description = 'Company name VIES returned for the checked VAT-ID (qualified check, not yet used).',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585189;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Address Returned by VIES', PrintName = 'Address Returned by VIES',
    Description = 'Address VIES returned for the checked VAT-ID (qualified check, not yet used).',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:24', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585190;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Name Match', PrintName = 'Name Match',
    Description = 'Result of the qualified VIES check''s name match (not yet used).',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:25', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585191;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Address Match', PrintName = 'Address Match',
    Description = 'Result of the qualified VIES check''s address match (not yet used).',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:26', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585192;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Raw VIES Response', PrintName = 'Raw VIES Response',
    Description = 'Unprocessed VIES service response, kept so a dispute can be reconstructed.',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:27', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585193;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'VAT-ID Check Status', PrintName = 'VAT-ID Check Status',
    Description = 'Result of the VAT-ID check.',
    Updated = TO_TIMESTAMP('2026-08-12 10:00:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585194;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-08-12 10:00:29', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Element_ID IN (585186, 585187, 585188, 585189, 585190, 585191, 585192, 585193, 585194);

-- 5. AD_Column — standard columns (cloned from X_TableTemplate 540290, reusing the shared standard elements)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        EntityType, PersonalDataCategory, Version)
VALUES
    (593164 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585185, 'VATaxID_CheckLog_ID', 13, 10,
     'Y', 'N', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593165 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 102, 'AD_Client_ID', 19, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593166 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:32', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 113, 'AD_Org_ID', 30, 10,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593167 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 348, 'IsActive', 20, 1,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593168 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 245, 'Created', 16, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593169 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:35', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 246, 'CreatedBy', 18, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593170 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:36', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 607, 'Updated', 16, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0),
    (593171 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:37', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 608, 'UpdatedBy', 18, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'D', 'NP', 0);

-- 6. AD_Column — business columns
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                        IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier,
                        IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                        DDL_NoForeignKey, EntityType, PersonalDataCategory, Version)
VALUES
    (593172 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 187, 'C_BPartner_ID', 19, NULL, 10,
     'N', 'N', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593173 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 189, 'C_BPartner_Location_ID', 19, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593174 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:42', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 502388, 'VATaxID', 10, NULL, 60,
     'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'N', 'D', 'P', 0),
    (593175 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:43', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585194, 'VATaxIDStatus', 17, 542125, 20,
     'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593176 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:44', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585186, 'RequestDate', 16, NULL, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593177 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:45', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585187, 'ResponseDate', 16, NULL, 29,
     'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593178 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:46', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585188, 'RequestIdentifier', 10, NULL, 50,
     'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593179 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:47', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 114, 'AD_PInstance_ID', 19, NULL, 10,
     'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', 'D', 'NP', 0),
    (593180 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:48', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:48', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 2029, 'AD_Session_ID', 19, NULL, 10,
     'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'Y', 'D', 'NP', 0),
    (593181 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:49', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:49', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585189, 'ReturnedName', 10, NULL, 400,
     'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'D', 'P', 0),
    (593182 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585190, 'ReturnedAddress', 10, NULL, 400,
     'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'D', 'P', 0),
    (593183 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585191, 'TraderNameMatch', 10, NULL, 20,
     'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593184 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:52', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585192, 'TraderAddressMatch', 10, NULL, 20,
     'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'D', 'NP', 0),
    (593185 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-08-12 10:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-08-12 10:00:53', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542639, 585193, 'RawResponse', 14, NULL, 999999,
     'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'D', 'P', 0);

-- 7. AD_Column_Trl skeleton rows for all 22 columns
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.ColumnName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Table_ID = 542639
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 8. Propagate element translations to the newly-created columns
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(x.AD_Element_ID)
FROM (VALUES (585185), (585186), (585187), (585188), (585189), (585190), (585191), (585192), (585193),
             (585194), (502388), (187), (189)) AS x(AD_Element_ID);
