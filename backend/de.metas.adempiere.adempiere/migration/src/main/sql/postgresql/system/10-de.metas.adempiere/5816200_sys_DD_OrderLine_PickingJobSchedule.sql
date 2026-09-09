-- DD_OrderLine_PickingJobSchedule: association table carrying each contributing M_Picking_Job_Schedule
-- (workstation assignment)'s planned quantity share of one DD_OrderLine.
--
-- IDs allocated from idserver.metas.de on 2026-07-25:
--   AD_MigrationScript  5816200 (this script)
--   AD_Table            542630  (DD_OrderLine_PickingJobSchedule)
--   AD_Element          585135  (DD_OrderLine_PickingJobSchedule_ID, new)
--   AD_Column           593025  (DD_OrderLine_PickingJobSchedule_ID, PK)
--   AD_Column           593026  (AD_Client_ID)
--   AD_Column           593027  (AD_Org_ID)
--   AD_Column           593028  (IsActive)
--   AD_Column           593029  (Created)
--   AD_Column           593030  (CreatedBy)
--   AD_Column           593031  (Updated)
--   AD_Column           593032  (UpdatedBy)
--   AD_Column           593033  (DD_OrderLine_ID) -- reuses existing AD_Element 53313
--   AD_Column           593034  (M_Picking_Job_Schedule_ID) -- reuses existing AD_Element 583882
--   AD_Column           593035  (Qty) -- reuses existing AD_Element 526 ("Menge")
--   AD_Column           593036  (C_UOM_ID) -- reuses existing AD_Element 215

-- =============================================================================
-- 1. AD_Element for the new PK column
-- =============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, EntityType)
VALUES (585135 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:00:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'DD_OrderLine_PickingJobSchedule_ID', 'DD_OrderLine Picking Job Schedule', 'DD_OrderLine Picking Job Schedule', 'de.metas.handlingunits');

-- Skeleton Trl rows (untranslated technical text; not user-facing)
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                             Name, PrintName, Description, Help,
                             IsTranslated, AD_Client_ID, AD_Org_ID,
                             Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
       t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585135
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

-- =============================================================================
-- 2. AD_Table
-- =============================================================================
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive,
                      Created, CreatedBy, Updated, UpdatedBy,
                      Name, TableName, IsView, AccessLevel, EntityType,
                      ImportTable, IsChangeLog, ReplicationType, IsHighVolume)
VALUES (542630 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:01:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:01:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'DD_OrderLine Picking Job Schedule', 'DD_OrderLine_PickingJobSchedule', 'N', '3', 'de.metas.handlingunits',
        'N', 'Y', 'L', 'N');

INSERT INTO AD_Table_Trl (AD_Language, AD_Table_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID,
                           Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Table t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Table_ID = 542630
  AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Table_ID = t.AD_Table_ID);

-- =============================================================================
-- 3. AD_Column — one per physical column
-- =============================================================================

-- DD_OrderLine_PickingJobSchedule_ID (PK)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593025 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:00.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'DD_OrderLine Picking Job Schedule', 542630, 585135, 'DD_OrderLine_PickingJobSchedule_ID',
        13, 10,
        'Y', 'Y', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'Y', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593026 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:01.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Mandant', 542630, 102, 'AD_Client_ID',
        19, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593027 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:02.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Sektion', 542630, 113, 'AD_Org_ID',
        30, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'Y', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength, DefaultValue,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593028 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:03.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Aktiv', 542630, 348, 'IsActive',
        20, 1, 'Y',
        'Y', 'N', 'N', 'Y', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593029 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:04.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Erstellt', 542630, 245, 'Created',
        16, 29,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593030 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:05.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:05.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Erstellt durch', 542630, 246, 'CreatedBy',
        18, 110, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593031 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:06.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:06.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Aktualisiert', 542630, 607, 'Updated',
        16, 29,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593032 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:07.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:07.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Aktualisiert durch', 542630, 608, 'UpdatedBy',
        18, 110, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'N',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

-- DD_OrderLine_ID (FK; no cascade delete — see CREATE TABLE below)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593033 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:08.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:08.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Distribution Order Line', 542630, 53313, 'DD_OrderLine_ID',
        30, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

-- M_Picking_Job_Schedule_ID (FK; no cascade delete — see CREATE TABLE below)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593034 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:09.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:09.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Kommissionierplan', 542630, 583882, 'M_Picking_Job_Schedule_ID',
        30, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593035 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:10.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:10.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Menge', 542630, 526, 'Qty',
        29, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive,
                       Created, CreatedBy, Updated, UpdatedBy,
                       Name, AD_Table_ID, AD_Element_ID, ColumnName,
                       AD_Reference_ID, AD_Reference_Value_ID, FieldLength,
                       IsMandatory, IsKey, IsParent, IsUpdateable, IsAlwaysUpdateable,
                       IsLazyLoading, IsExcludeFromZoomTargets, IsAllowLogging,
                       IsEncrypted, IsIdentifier, IsSelectionColumn, IsSyncDatabase, IsTranslated, Version, EntityType,
                       PersonalDataCategory)
VALUES (593036 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-07-25 14:02:11.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        TO_TIMESTAMP('2026-07-25 14:02:11.000000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100,
        'Maßeinheit', 542630, 215, 'C_UOM_ID',
        30, 541960, 10,
        'Y', 'N', 'N', 'N', 'N',
        'N', 'Y', 'Y',
        'N', 'N', 'N', 'Y', 'N', 0, 'de.metas.handlingunits',
        'NP');

-- AD_Column_Trl: skeleton rows for all 12 new columns (one per active system/base language)
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID IN (593025, 593026, 593027, 593028, 593029, 593030, 593031, 593032, 593033, 593034, 593035, 593036)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- Propagate element translations into the skeleton AD_Column_Trl rows
SELECT update_Column_Translation_From_AD_Element(585135); -- DD_OrderLine_PickingJobSchedule_ID
SELECT update_Column_Translation_From_AD_Element(102);    -- AD_Client_ID
SELECT update_Column_Translation_From_AD_Element(113);    -- AD_Org_ID
SELECT update_Column_Translation_From_AD_Element(348);    -- IsActive
SELECT update_Column_Translation_From_AD_Element(245);    -- Created
SELECT update_Column_Translation_From_AD_Element(246);    -- CreatedBy
SELECT update_Column_Translation_From_AD_Element(607);    -- Updated
SELECT update_Column_Translation_From_AD_Element(608);    -- UpdatedBy
SELECT update_Column_Translation_From_AD_Element(53313);  -- DD_OrderLine_ID
SELECT update_Column_Translation_From_AD_Element(583882); -- M_Picking_Job_Schedule_ID
SELECT update_Column_Translation_From_AD_Element(526);    -- Qty
SELECT update_Column_Translation_From_AD_Element(215);    -- C_UOM_ID

-- =============================================================================
-- 4. Physical DDL
-- =============================================================================
CREATE TABLE DD_OrderLine_PickingJobSchedule
(
    DD_OrderLine_PickingJobSchedule_ID numeric(10)                        NOT NULL,
    AD_Client_ID                       numeric(10)                        NOT NULL,
    AD_Org_ID                          numeric(10)                        NOT NULL,
    IsActive                           character(1) DEFAULT 'Y'::bpchar   NOT NULL,
    Created                            timestamp with time zone DEFAULT now() NOT NULL,
    CreatedBy                          numeric(10)                        NOT NULL,
    Updated                            timestamp with time zone DEFAULT now() NOT NULL,
    UpdatedBy                          numeric(10)                        NOT NULL,
    DD_OrderLine_ID                    numeric(10)                        NOT NULL,
    M_Picking_Job_Schedule_ID          numeric(10)                        NOT NULL,
    Qty                                numeric                            NOT NULL,
    C_UOM_ID                           numeric(10)                        NOT NULL,
    CONSTRAINT ddorderline_pickingjobschedule_key PRIMARY KEY (DD_OrderLine_PickingJobSchedule_ID),
    -- No cascade delete: the FKs are deferrable, so a surviving row fails the delete at commit instead of silently disappearing.
    CONSTRAINT ddorderline_pjs_ddorderline FOREIGN KEY (DD_OrderLine_ID)
        REFERENCES DD_OrderLine (DD_OrderLine_ID) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT ddorderline_pjs_pickingjobsched FOREIGN KEY (M_Picking_Job_Schedule_ID)
        REFERENCES M_Picking_Job_Schedule (M_Picking_Job_Schedule_ID) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT ddorderline_pjs_uom FOREIGN KEY (C_UOM_ID)
        REFERENCES C_UOM (C_UOM_ID) DEFERRABLE INITIALLY DEFERRED
);

-- line -> contributors: settlement, guards, per-line invariant check
CREATE INDEX ddorderline_pjs_ddorderline_idx ON DD_OrderLine_PickingJobSchedule (DD_OrderLine_ID);
-- assignment -> lines: related-documents navigation, "is this assignment served?" watchdog predicate
CREATE INDEX ddorderline_pjs_pickingjobsched_idx ON DD_OrderLine_PickingJobSchedule (M_Picking_Job_Schedule_ID);
