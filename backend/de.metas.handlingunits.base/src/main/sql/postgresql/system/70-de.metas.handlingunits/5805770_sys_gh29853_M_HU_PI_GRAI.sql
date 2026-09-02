-- IDs allocated from idserver.metas.de on 2026-06-02:
--   AD_Table   542611  (M_HU_PI_GRAI)
--   AD_Element 584931  (M_HU_PI_GRAI_ID     — PK element)
--   AD_Element 584929  (GRAI_CompanyPrefix   — new)
--   AD_Element 584930  (GRAI_AssetType       — new)
--   AD_Column  592689  (M_HU_PI_GRAI.M_HU_PI_GRAI_ID  — PK)
--   AD_Column  592690  (M_HU_PI_GRAI.M_HU_PI_ID        — FK, reuses element 542135)
--   AD_Column  592691  (M_HU_PI_GRAI.GRAI_CompanyPrefix)
--   AD_Column  592692  (M_HU_PI_GRAI.GRAI_AssetType)
--   AD_Column  592693  (M_HU_PI_GRAI.AD_Client_ID      — reuses element 102)
--   AD_Column  592694  (M_HU_PI_GRAI.AD_Org_ID         — reuses element 113)
--   AD_Column  592695  (M_HU_PI_GRAI.IsActive          — reuses element 348)
--   AD_Column  592696  (M_HU_PI_GRAI.Created           — reuses element 245)
--   AD_Column  592697  (M_HU_PI_GRAI.CreatedBy         — reuses element 246)
--   AD_Column  592698  (M_HU_PI_GRAI.Updated           — reuses element 607)
--   AD_Column  592699  (M_HU_PI_GRAI.UpdatedBy         — reuses element 608)

-- ===========================================================================
-- 1. Physical table + unique index
-- ===========================================================================
CREATE TABLE IF NOT EXISTS M_HU_PI_GRAI
(
    M_HU_PI_GRAI_ID    NUMERIC(10)    NOT NULL,
    M_HU_PI_ID         NUMERIC(10)    NOT NULL,
    GRAI_CompanyPrefix VARCHAR(255)    NOT NULL,
    GRAI_AssetType     VARCHAR(255)    NOT NULL,
    AD_Client_ID       NUMERIC(10)    NOT NULL,
    AD_Org_ID          NUMERIC(10)    NOT NULL,
    IsActive           CHAR(1)        NOT NULL DEFAULT 'Y',
    Created            TIMESTAMP      NOT NULL DEFAULT now(),
    CreatedBy          NUMERIC(10)    NOT NULL,
    Updated            TIMESTAMP      NOT NULL DEFAULT now(),
    UpdatedBy          NUMERIC(10)    NOT NULL,
    CONSTRAINT M_HU_PI_GRAI_Key   PRIMARY KEY (M_HU_PI_GRAI_ID),
    CONSTRAINT M_HU_PI_GRAI_M_HU_PI FOREIGN KEY (M_HU_PI_ID) REFERENCES M_HU_PI (M_HU_PI_ID)
);

-- A scanned GRAI must resolve to exactly one M_HU_PI (global, non-partial)
CREATE UNIQUE INDEX IF NOT EXISTS M_HU_PI_GRAI_CompanyPrefix_AssetType_UIdx
    ON M_HU_PI_GRAI (GRAI_CompanyPrefix, GRAI_AssetType);

-- ===========================================================================
-- 2. AD_Table
-- ===========================================================================
INSERT INTO AD_Table
    (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, TableName, IsView, AccessLevel, EntityType,
     ImportTable, IsDeleteable, IsHighVolume, IsChangeLog,
     ReplicationType, IsSecurityEnabled, LoadSeq)
VALUES
    (542611 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_HU_PI_GRAI', 'M_HU_PI_GRAI', 'N', '3', 'de.metas.handlingunits',
     'N', 'Y', 'N', 'N', 'L', 'N', 0);

-- ===========================================================================
-- 3. AD_Element — M_HU_PI_GRAI_ID (PK, new)
-- ===========================================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES
    (584931 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_HU_PI_GRAI_ID',
     'GRAI-Packvorschrift',
     'GRAI-Packvorschrift',
     NULL, NULL,
     'de.metas.handlingunits');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated,
     Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 584931 /*From ID Server*/, 'N',
       e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:00:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND e.AD_Element_ID = 584931
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 584931);

UPDATE AD_Element_Trl
SET    Name = 'GRAI-Packvorschrift', PrintName = 'GRAI-Packvorschrift',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584931 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    Name = 'GRAI-Packvorschrift', PrintName = 'GRAI-Packvorschrift',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584931 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    Name = 'GRAI Packing Instruction', PrintName = 'GRAI Packing Instruction',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584931 AND AD_Language = 'en_US';

-- ===========================================================================
-- 4. AD_Element — GRAI_CompanyPrefix (new, verified absent)
-- ===========================================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES
    (584929 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:00:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GRAI_CompanyPrefix',
     'GS1 Firmenpräfix',
     'GS1 Firmenpräfix',
     'GS1 Firmenpräfix des Eigentümers der Gebindeart',
     NULL,
     'de.metas.handlingunits');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated,
     Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 584929 /*From ID Server*/, 'N',
       e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:00:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND e.AD_Element_ID = 584929
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 584929);

UPDATE AD_Element_Trl
SET    Name = 'GS1 Firmenpräfix', PrintName = 'GS1 Firmenpräfix',
       Description = 'GS1 Firmenpräfix des Eigentümers der Gebindeart',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:22', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584929 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    Name = 'GS1 Firmenpräfix', PrintName = 'GS1 Firmenpräfix',
       Description = 'GS1 Firmenpräfix des Eigentümers der Gebindeart',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:23', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584929 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    Name = 'GS1 Company Prefix', PrintName = 'GS1 Company Prefix',
       Description = 'GS1 company prefix of the crate owner',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:24', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584929 AND AD_Language = 'en_US';

-- ===========================================================================
-- 5. AD_Element — GRAI_AssetType (new, verified absent)
-- ===========================================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES
    (584930 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:00:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GRAI_AssetType',
     'GRAI Gebindetyp',
     'GRAI Gebindetyp',
     'Internes Gebindemodell-Kürzel des Eigentümers',
     NULL,
     'de.metas.handlingunits');

INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, IsTranslated,
     Name, PrintName, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 584930 /*From ID Server*/, 'N',
       e.Name, e.PrintName, e.Description, e.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:00:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND e.AD_Element_ID = 584930
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 584930);

UPDATE AD_Element_Trl
SET    Name = 'GRAI Gebindetyp', PrintName = 'GRAI Gebindetyp',
       Description = 'Internes Gebindemodell-Kürzel des Eigentümers',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:32', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584930 AND AD_Language = 'de_DE';

UPDATE AD_Element_Trl
SET    Name = 'GRAI Gebindetyp', PrintName = 'GRAI Gebindetyp',
       Description = 'Internes Gebindemodell-Kürzel des Eigentümers',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:33', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584930 AND AD_Language = 'de_CH';

UPDATE AD_Element_Trl
SET    Name = 'GRAI Asset Type', PrintName = 'GRAI Asset Type',
       Description = 'Owner''s internal crate-model code',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-02 10:00:34', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 584930 AND AD_Language = 'en_US';

-- ===========================================================================
-- 6. AD_Column — M_HU_PI_GRAI_ID (PK, AD_Reference_ID=13 = ID)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592689 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'M_HU_PI_GRAI_ID',
     542611 /*From ID Server*/, 'M_HU_PI_GRAI_ID', 10,
     13, 'Y', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'de.metas.handlingunits', 584931 /*From ID Server*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592689 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592689
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592689);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584931 /*M_HU_PI_GRAI_ID*/);

-- IsParent='N' — standalone window, not a child tab
-- ===========================================================================
-- 7. AD_Column — M_HU_PI_ID (FK → M_HU_PI, AD_Reference_ID=19 = TableDir)
--    Reuses existing AD_Element 542135 (M_HU_PI_ID / Packvorschrift)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592690 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Packvorschrift',
     542611 /*From ID Server*/, 'M_HU_PI_ID', 10,
     19, 'N', 'N', 'Y',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'de.metas.handlingunits', 542135 /*existing: M_HU_PI_ID element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592690 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:01:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:01:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592690
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592690);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542135 /*M_HU_PI_ID element*/);

-- ===========================================================================
-- 8. AD_Column — GRAI_CompanyPrefix (varchar, AD_Reference_ID=10 = String)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592691 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:01:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:01:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GS1 Firmenpräfix',
     542611 /*From ID Server*/, 'GRAI_CompanyPrefix', 255,
     10, 'N', 'N', 'Y',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'de.metas.handlingunits', 584929 /*From ID Server*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592691 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:01:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:01:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592691
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592691);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584929 /*GRAI_CompanyPrefix element*/);

-- ===========================================================================
-- 9. AD_Column — GRAI_AssetType (varchar, AD_Reference_ID=10 = String)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592692 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:01:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:01:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'GRAI Gebindetyp',
     542611 /*From ID Server*/, 'GRAI_AssetType', 255,
     10, 'N', 'N', 'Y',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'de.metas.handlingunits', 584930 /*From ID Server*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592692 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:01:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:01:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592692
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592692);

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584930 /*GRAI_AssetType element*/);

-- ===========================================================================
-- 10. AD_Column — AD_Client_ID (reuses element 102)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592693 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:01:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:01:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Mandant',
     542611 /*From ID Server*/, 'AD_Client_ID', 10,
     19, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'de.metas.handlingunits', 102 /*existing: AD_Client_ID element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592693 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:01:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:01:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592693
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592693);

-- ===========================================================================
-- 11. AD_Column — AD_Org_ID (reuses element 113)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592694 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:01:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:01:50', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Organisation',
     542611 /*From ID Server*/, 'AD_Org_ID', 10,
     30, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'de.metas.handlingunits', 113 /*existing: AD_Org_ID element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592694 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:01:51', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:01:51', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592694
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592694);

-- ===========================================================================
-- 12. AD_Column — IsActive (reuses element 348)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     DefaultValue, EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592695 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktiv',
     542611 /*From ID Server*/, 'IsActive', 1,
     20, 'N', 'N', 'Y',
     'N', 'N', 'N', 'Y', 'N',
     'N', 'N',
     'Y', 'de.metas.handlingunits', 348 /*existing: IsActive element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592695 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:02:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592695
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592695);

-- ===========================================================================
-- 13. AD_Column — Created (reuses element 245, AD_Reference_ID=16=Date+Time)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592696 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:02:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Erstellt',
     542611 /*From ID Server*/, 'Created', 7,
     16, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'de.metas.handlingunits', 245 /*existing: Created element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592696 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:02:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:02:11', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592696
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592696);

-- ===========================================================================
-- 14. AD_Column — CreatedBy (reuses element 246, AD_Reference_ID=18=Table, ref_value=110)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, AD_Reference_Value_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592697 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:02:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:02:20', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Erstellt von',
     542611 /*From ID Server*/, 'CreatedBy', 10,
     18, 110, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'de.metas.handlingunits', 246 /*existing: CreatedBy element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592697 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:02:21', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:02:21', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592697
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592697);

-- ===========================================================================
-- 15. AD_Column — Updated (reuses element 607, AD_Reference_ID=16, fieldlength=29)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592698 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:02:30', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktualisiert',
     542611 /*From ID Server*/, 'Updated', 29,
     16, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'de.metas.handlingunits', 607 /*existing: Updated element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592698 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:02:31', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:02:31', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592698
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592698);

-- ===========================================================================
-- 16. AD_Column — UpdatedBy (reuses element 608, AD_Reference_ID=18, ref_value=110)
-- ===========================================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
     Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Table_ID, ColumnName, FieldLength,
     AD_Reference_ID, AD_Reference_Value_ID, IsKey, IsParent, IsMandatory,
     IsTranslated, IsIdentifier, IsEncrypted, IsUpdateable, IsAlwaysUpdateable,
     IsSelectionColumn, IsSyncDatabase,
     EntityType, AD_Element_ID, Version, PersonalDataCategory)
VALUES
    (592699 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-02 10:02:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     TO_TIMESTAMP('2026-06-02 10:02:40', 'YYYY-MM-DD HH24:MI:SS'), 100,
     'Aktualisiert von',
     542611 /*From ID Server*/, 'UpdatedBy', 10,
     18, 110, 'N', 'N', 'Y',
     'N', 'N', 'N', 'N', 'N',
     'N', 'N',
     'de.metas.handlingunits', 608 /*existing: UpdatedBy element*/, 0, 'NP');

INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, IsTranslated, Name,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 592699 /*From ID Server*/, 'N', t.Name,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-06-02 10:02:41', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-06-02 10:02:41', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 592699
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592699);

-- ===========================================================================
-- 17. Backfill any missing translations for the new table rows
-- ===========================================================================
SELECT add_missing_translations();
