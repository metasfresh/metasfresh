-- AD_Table_Access.IsCanCreateNewRecords: reshape from the three-state List/_YesNo column
-- (migration 5825490, NOT edited or reverted here) into a plain NOT NULL Yes-No with a
-- non-restricting default. This is a NEW script.
--
-- WHY
-- Task R1 (migration 5825940, committed) already restored IsReadOnly / IsCanReport / IsCanExport
-- to NOT NULL Yes-No with non-restricting defaults and dropped IsExclude outright: no column on
-- AD_Table_Access has a third state any more. This script brings IsCanCreateNewRecords into the
-- same shape: IsReadOnly='Y' removes WRITE; IsCanReport/IsCanExport/IsCanCreateNewRecords='N'
-- remove REPORT/EXPORT/CREATE from the role's default access set. A flag left at its
-- non-restricting default ('Y' here) removes nothing, so a row at its defaults is
-- indistinguishable from no row at all.
--
-- Table verified empty (0 rows) on this stack before writing this script (same as 5825940), so
-- the backfill UPDATE below is defensive only, not load-bearing here — still backed up
-- defensively (business-adjacent AD_* operator data), matching 5825940's precedent.
--
-- ELEMENT 585478 (IsCanCreateNewRecords) — OWN element, not shared. Verified live before writing
-- this script: AD_Column query for AD_Element_ID=585478 returns exactly one row
-- (AD_Table_Access.IsCanCreateNewRecords, AD_Column_ID=593636), and the field that renders it
-- (AD_Field_ID=785051, tab 549493) has AD_Field.AD_Name_ID NULL — i.e. it inherits straight from
-- AD_Column.AD_Element_ID=585478, no per-field override in play. So the element is mutated
-- directly here; no AD_Field.AD_Name_ID override is needed (contrast with 5825940's IsReadOnly,
-- which forked a NEW element because 405 is shared by 12 other columns).
--
-- DESCRIPTION REWRITE — per REQUIREMENTS.md AC16 as amended (live, LIVE § 3.2c wording):
--   - The retired text "Nicht gesetzt = keine Einschränkung durch die Rolle" is removed — there is
--     no longer an unset/third state on this column (or on any column of this table, since R1).
--   - States what the setting actually GUARANTEES (adversarial review § 1.1): the desktop WebUI
--     will not offer or accept creating a new record for this role, in this table — not "this role
--     cannot create records" (a phrasing that overclaims past the WebUI-only enforcement scope).
--   - Still carries the AC16 warning: a row added to an included role for an unrelated reason
--     defaults to Yes here and can — in a role-inclusion chain — lift another role's restriction.
--   - Says nothing about IsExclude or any third state.
-- CUSTOMER-VISIBLE WORDING — first draft, flag for human review before UAT.
--
-- IDs allocated from idserver.metas.de on 2026-09-22:
--   AD_MigrationScript 5825950 (this script)

-- =================================================================================================
-- 1. Backup (AD_Table_Access holds operator-configured permission data).
-- =================================================================================================
SELECT backup_table('ad_table_access', '_R2_create_permission_plain_boolean');

-- =================================================================================================
-- 2. Backfill any NULL to 'Y' (defensive; table verified empty, so this is a no-op today) —
--    the only data step, per the plan: the default is what makes every existing row and every
--    unconfigured role unrestricted.
-- =================================================================================================
UPDATE AD_Table_Access SET IsCanCreateNewRecords = 'Y', Updated = TO_TIMESTAMP('2026-09-22 16:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100 WHERE IsCanCreateNewRecords IS NULL;

-- =================================================================================================
-- 3. Convert the physical column in place: NOT NULL, DEFAULT 'Y', CHECK ('Y','N').
-- =================================================================================================
INSERT INTO t_alter_column values('AD_Table_Access','IsCanCreateNewRecords','CHAR(1)','NOT NULL','Y');

SELECT db_alter_table('AD_Table_Access', 'ALTER TABLE public.AD_Table_Access ADD CONSTRAINT ad_table_access_iscancreatenewrecords_check CHECK (IsCanCreateNewRecords IN (''Y'',''N''))');

-- =================================================================================================
-- 4. AD_Column: Yes-No (20) over no validation list, mandatory, default 'Y'.
-- =================================================================================================
UPDATE AD_Column
   SET AD_Reference_ID       = 20,
       AD_Reference_Value_ID = NULL,
       IsMandatory            = 'Y',
       DefaultValue           = 'Y',
       Updated                = TO_TIMESTAMP('2026-09-22 16:01:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy              = 100
 WHERE AD_Column_ID = 593636; -- IsCanCreateNewRecords

-- =================================================================================================
-- 5. AD_Element 585478 (own element, not shared — see header) — rewrite Description per language
--    through the two propagation functions. Name/PrintName are unchanged.
-- =================================================================================================
UPDATE AD_Element_Trl
   SET Description = 'Bei ''Nein'' bietet die Desktop-WebUI für diese Rolle in dieser Tabelle das Anlegen neuer Datensätze nicht an und akzeptiert es nicht. Achtung: Ein Datensatz, der einer eingeschlossenen Rolle aus einem anderen Grund hinzugefügt wird, hat hier standardmäßig den Wert ''Ja'' und kann so in einer Rollen-Einschlusskette die Einschränkung einer anderen Rolle aufheben.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-22 16:02:00','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Element_ID=585478 AND AD_Language IN ('de_DE','de_CH');

UPDATE AD_Element_Trl
   SET Description = 'When set to No, the desktop WebUI will not offer or accept creating a new record for this role in this table. Warning: a row added to an included role for an unrelated reason defaults to Yes here and can, in a role-inclusion chain, lift another role''s restriction.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-09-22 16:02:10','YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
 WHERE AD_Element_ID=585478 AND AD_Language='en_US';

-- fr_CH keeps the seeded row (German base text, IsTranslated='N') — authoring French is outside
-- this task, matching the precedent set by migration 5825940 (and 5825760 before it).

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585478, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585478, 'de_DE');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585478, 'de_CH');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585478, 'de_CH');
/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(585478, 'en_US');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585478, 'en_US');
