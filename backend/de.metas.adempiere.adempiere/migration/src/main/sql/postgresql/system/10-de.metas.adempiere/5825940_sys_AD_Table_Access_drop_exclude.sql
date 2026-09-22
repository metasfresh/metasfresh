-- AD_Table_Access: drop IsExclude and restore IsReadOnly / IsCanReport / IsCanExport to NOT NULL
-- Yes-No with non-restricting defaults. This is a NEW script; the committed migrations 5825480 /
-- 5825490 / 5825500 / 5825620 are pushed and are not edited or reverted here.
--
-- WHY
-- AD_Table_Access is redefined as four NOT NULL flags that each SUBTRACT one access from the
-- role's default access set (IsReadOnly='Y' removes WRITE; IsCanReport/IsCanExport/
-- IsCanCreateNewRecords='N' remove REPORT/EXPORT/CREATE). A flag left at its non-restricting default
-- removes nothing, so a row at its defaults is indistinguishable from no row at all. IsExclude has
-- no role in that shape (there is no longer an "exclude the whole table" concept — a deliberate
-- capability removal, not an oversight) and is dropped outright. AccessTypeRule is already gone
-- (5825500, stands).
--
-- IsExclude'S ELEMENT (2079) — KEEP, do not delete the element row.
-- AD_Column rows bound to AD_Element_ID=2079, queried live before this script: 8846
-- (AD_Column_Access.IsExclude), 15633 (CM_AccessProfile.IsExclude), 8844 (AD_Table_Access.IsExclude
-- — the one this script removes). That is 3 usages total, i.e. 2 OTHER active columns besides this
-- table's own — so the element stays; only THIS table's usage (AD_Column 8844 and its two AD_Field
-- rows) is removed.
--
-- FIELDS REMOVED
--   AD_Field 6712   — tab 482 (window 268, renamed to LEGACY, 0 AD_UI_Section rows, not reachable
--                     via the menu; no AD_UI_Element exists for this field).
--   AD_Field 785049 — tab 549493 (window 111, the live tab), with its AD_UI_Element 654791.
-- Both fields' full FK chain (AD_Element_Link, AD_Field_Trl, AD_UI_Element) is cleared first, then
-- the AD_Field rows, then the grid-sequence gaps on both tabs are closed (Swing-client behaviour;
-- raw SQL does not do this automatically — same pattern as migration 5825500 step 2b).
--
-- IsReadOnly's NEW table-access-specific meaning ("subtracts WRITE from the role default") is NOT
-- written onto shared AD_Element 405: live query shows 405 bound by 12 active AD_Columns (AD_Field,
-- AD_Tab, AD_Menu, AD_Attribute, M_HU_PI_Attribute, AD_UserDef_Field/Tab/Win, AD_User_OrgAccess,
-- AD_Role_OrgAccess, AD_Column_Access, AD_Table_Access) — "subtracts WRITE" is false for a read-only
-- UI field/tab/menu/attribute. The table-access wording is carried via a NEW element (585485) bound
-- through AD_Field.AD_Name_ID on AD_Field 785050 (tab 549493's live IsReadOnly field) ONLY — the
-- same per-field-override mechanism migration 5825760 used for the record-access-type field. Field
-- 6712 (tab 482, the LEGACY window, 0 AD_UI_Section — nothing on that tab renders) keeps its
-- existing override (AD_Element 1001358) untouched; it is out of the rendered scope and out of this
-- task.
-- CUSTOMER-VISIBLE WORDING — the new element's text (585485) is a first draft; flag for human review
-- before UAT.
--
-- SEPARATELY, the GENERIC defects on shared AD_Element 405 (apply to all 12 uses, so fixed on the
-- shared element itself, not via an override): en_US Name renders as lowercase "readonly" (deferred
-- at 5825710 precisely so it could be handled together with this task), and 405's en_US Description
-- still carries the German base text. Both fixed here for en_US only, through the two propagation
-- functions. de_DE/de_CH/fr_CH/en_GB/it_CH rows are untouched (en_GB/it_CH/fr_CH pre-existing content
-- gaps are out of scope, per 5825710/5825760's standing exclusions).
--
-- Table verified empty (0 rows) on this stack before writing this script, so no data backfill is
-- needed for the NOT NULL restoration — still backed up defensively (business-adjacent AD_*
-- operator data).

-- =================================================================================================
-- 1. Backup (AD_Table_Access holds operator-configured permission data).
-- =================================================================================================
SELECT backup_table('ad_table_access', '_R1_drop_isexclude');

-- =================================================================================================
-- 2. Drop AD_Table_Access's usage of IsExclude — AD_Field / AD_UI_Element / AD_Element_Link first,
--    then AD_Column, then the physical column.
-- =================================================================================================

-- 2a. Element-link rows binding element 2079 to these two fields.
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (6712, 785049);

-- 2b. The live tab's UI element (tab 482 has no AD_UI_Section, so no AD_UI_Element exists for 6712).
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID = 654791;

-- 2c. Field translations, then the fields themselves.
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN (6712, 785049);
DELETE FROM AD_Field WHERE AD_Field_ID IN (6712, 785049);

-- 2d. Close the grid-sequence gap on tab 482 (window 268 legacy) — SeqNoGrid only, SeqNo (form) is
--     left alone, matching migration 5825500 step 2b's precedent for the same tab.
UPDATE AD_Field SET SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-09-22 15:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=6714;
UPDATE AD_Field SET SeqNoGrid=70, Updated=TO_TIMESTAMP('2026-09-22 15:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=8320;
UPDATE AD_Field SET SeqNoGrid=80, Updated=TO_TIMESTAMP('2026-09-22 15:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=8321;

-- 2e. Close the sequence gap on tab 549493 (window 111, the live section-backed tab) — both
--     AD_UI_Element and AD_Field are kept in sync here (they were mirrored before this change), so
--     both are shifted down by one slot (-10) for every field that sat after IsExclude.
UPDATE AD_UI_Element SET SeqNo=30, SeqNoGrid=30, Updated=TO_TIMESTAMP('2026-09-22 15:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=654792; -- IsReadOnly
UPDATE AD_UI_Element SET SeqNo=40, SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-09-22 15:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=654793; -- IsCanCreateNewRecords
UPDATE AD_UI_Element SET SeqNo=50, SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-09-22 15:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=654794; -- IsCanReport
UPDATE AD_UI_Element SET SeqNo=60, SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-09-22 15:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=654795; -- IsCanExport
UPDATE AD_UI_Element SET SeqNo=70, SeqNoGrid=70, Updated=TO_TIMESTAMP('2026-09-22 15:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=654796; -- AD_Org_ID
UPDATE AD_UI_Element SET SeqNo=80, Updated=TO_TIMESTAMP('2026-09-22 15:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=654797; -- AD_Client_ID (SeqNoGrid stays 0 — hidden from the grid, unaffected)

UPDATE AD_Field SET SeqNo=30, SeqNoGrid=30, Updated=TO_TIMESTAMP('2026-09-22 15:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785050; -- IsReadOnly
UPDATE AD_Field SET SeqNo=40, SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-09-22 15:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785051; -- IsCanCreateNewRecords
UPDATE AD_Field SET SeqNo=50, SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-09-22 15:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785052; -- IsCanReport
UPDATE AD_Field SET SeqNo=60, SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-09-22 15:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785053; -- IsCanExport
UPDATE AD_Field SET SeqNo=70, SeqNoGrid=70, Updated=TO_TIMESTAMP('2026-09-22 15:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785054; -- AD_Org_ID
UPDATE AD_Field SET SeqNo=80, Updated=TO_TIMESTAMP('2026-09-22 15:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785055; -- AD_Client_ID (SeqNoGrid stays 0)

-- 2f. AD_Column_Access rows keyed directly on the column (0 today; cleared unconditionally, same
--     reasoning as migration 5825500 step 3 — empty here, not necessarily on a long-lived instance).
DELETE FROM AD_Column_Access WHERE AD_Column_ID=8844;
DELETE FROM AD_Column_Trl WHERE AD_Column_ID=8844;
DELETE FROM AD_Column WHERE AD_Column_ID=8844;

-- 2g. Physical column. AD_Element 2079 is deliberately NOT touched (see header — 2 other active
--     columns still bind it).
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access DROP COLUMN IsExclude');

-- =================================================================================================
-- 3. Restore IsReadOnly / IsCanReport / IsCanExport to NOT NULL Yes-No with non-restricting
--    defaults. Table verified empty (0 rows), so no backfill statement is needed.
-- =================================================================================================
INSERT INTO t_alter_column values('AD_Table_Access','IsReadOnly','CHAR(1)','NOT NULL','N');
INSERT INTO t_alter_column values('AD_Table_Access','IsCanReport','CHAR(1)','NOT NULL','Y');
INSERT INTO t_alter_column values('AD_Table_Access','IsCanExport','CHAR(1)','NOT NULL','Y');

SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access ADD CONSTRAINT ad_table_access_isreadonly_check CHECK (IsReadOnly IN (''Y'',''N''))');
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access ADD CONSTRAINT ad_table_access_iscanreport_check CHECK (IsCanReport IN (''Y'',''N''))');
SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access ADD CONSTRAINT ad_table_access_iscanexport_check CHECK (IsCanExport IN (''Y'',''N''))');

UPDATE AD_Column
   SET AD_Reference_ID       = 20,
       AD_Reference_Value_ID = NULL,
       IsMandatory            = 'Y',
       DefaultValue           = 'N',
       Updated                = TO_TIMESTAMP('2026-09-22 15:01:01','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy              = 100
 WHERE AD_Column_ID = 8568; -- IsReadOnly

UPDATE AD_Column
   SET AD_Reference_ID       = 20,
       AD_Reference_Value_ID = NULL,
       IsMandatory            = 'Y',
       DefaultValue           = 'Y',
       Updated                = TO_TIMESTAMP('2026-09-22 15:01:02','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy              = 100
 WHERE AD_Column_ID = 9970; -- IsCanReport

UPDATE AD_Column
   SET AD_Reference_ID       = 20,
       AD_Reference_Value_ID = NULL,
       IsMandatory            = 'Y',
       DefaultValue           = 'Y',
       Updated                = TO_TIMESTAMP('2026-09-22 15:01:03','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy              = 100
 WHERE AD_Column_ID = 9971; -- IsCanExport

-- =================================================================================================
-- 4. New element for the table-access-specific IsReadOnly wording, bound to field 785050 ONLY
--    (tab 549493, window 111's live tab). Base row is German, per the base-language convention.
--    CUSTOMER-VISIBLE WORDING — first draft, flag for human review before UAT.
-- =================================================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, ColumnName, EntityType, Name, PrintName, Description)
VALUES (585485 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-09-22 15:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-09-22 15:02:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'AD_Table_Access_IsReadOnly', 'D', 'Schreibgeschützt', 'Schreibgeschützt', 'Entzieht der Rolle für diese Tabelle die Schreibberechtigung (WRITE); Lesen, Berichte und Export bleiben unverändert.');

-- Skeleton translation rows for every active system language (de_CH, de_DE, en_US, fr_CH).
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585485
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

-- de_DE / de_CH: the German text is final (no 'ß' in it, so no separate Swiss variant is needed).
UPDATE AD_Element_Trl SET Name='Schreibgeschützt', PrintName='Schreibgeschützt', Description='Entzieht der Rolle für diese Tabelle die Schreibberechtigung (WRITE); Lesen, Berichte und Export bleiben unverändert.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 15:02:10', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585485 AND AD_Language IN ('de_DE','de_CH');

-- en_US.
UPDATE AD_Element_Trl SET Name='Read-only', PrintName='Read-only', Description='Removes WRITE access to this table from the role''s default permissions; read, report and export are unaffected.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-22 15:02:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585485 AND AD_Language='en_US';

-- fr_CH keeps the seeded row (German base text, IsTranslated='N') — authoring French is outside this
-- task, matching the precedent set by migration 5825760 for the same situation.

-- Point field 785050 at the new element and propagate; then rebuild its AD_Element_Link row (it
-- still points at 405). Order matters: repoint BEFORE the 405 fix below, so element 405's
-- propagation can no longer reach this field.
UPDATE AD_Field SET AD_Name_ID=585485, Updated=TO_TIMESTAMP('2026-09-22 15:02:20', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=785050;

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585485, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585485, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585485, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585485, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585485, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585485, 'en_US');

DELETE FROM AD_Element_Link WHERE AD_Field_ID=785050;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(785050);

-- =================================================================================================
-- 5. SEPARATELY — generic AD_Element 405 (IsReadOnly, shared by 12 active columns) defects, en_US
--    only: Name 'readonly' -> 'Read-only'; Description (still the German base text) -> an English
--    translation. de_DE/de_CH/en_GB/fr_CH/it_CH are untouched.
-- =================================================================================================
UPDATE AD_Element_Trl SET Name='Read-only', Description='Field / entry / area is read-only.', Updated=TO_TIMESTAMP('2026-09-22 15:03:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=405 AND AD_Language='en_US';

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(405, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(405, 'en_US');
