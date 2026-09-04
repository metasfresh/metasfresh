-- DDL-retire companion to 5821590_sys_mobileMfg_migrateEditableAttributeFlags.sql (must run AFTER it,
-- which converts every existing flag value into a MobileUI_MFG_Config_Attribute child row first).
--
-- Retires the F8030 per-attribute boolean flags now superseded by the editable-attributes child list:
--   MobileUI_MFG_Config.IsLotNumberEditable / IsBestBeforeDateEditable   (AD_Column 592879 / 592878)
--   MobileUI_UserProfile_MFG.IsLotNumberEditable / IsBestBeforeDateEditable (AD_Column 592881 / 592880)
-- Per-user overrides are dropped with no data-migration step: verified empty (0 rows with either
-- flag non-null in MobileUI_UserProfile_MFG) before this change was authored - v1 of the
-- editable-attributes list is global-only by design (DESIGN.md), so there is no per-user
-- equivalent to migrate the flags into.
--
-- Dependency sweep (views / functions / val-rules / virtual ColumnSQL / EXP_FormatLine) run against
-- the live DB found zero references to either column outside the AD_Field/AD_UI_Element rows
-- retired below - safe to drop without repointing anything else.

-- ============================================================================
-- AD_Field / AD_UI_Element / AD_Element_Link / AD_Field_Trl cleanup, anchored by AD_Column_ID
-- (metasfresh-application-dictionary skill "Deleting an AD_Field" recipe)
-- ============================================================================

DELETE FROM AD_UI_Element WHERE AD_Field_ID IN
	(SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881))
;
DELETE FROM AD_UI_Element WHERE Labels_Selector_Field_ID IN
	(SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881))
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN
	(SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881))
;
DELETE FROM AD_Field_Trl WHERE AD_Field_ID IN
	(SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881))
;
DELETE FROM AD_Field_ContextMenu WHERE AD_Field_ID IN
	(SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881))
;
DELETE FROM AD_UI_ElementField WHERE AD_Field_ID IN
	(SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881))
;
DELETE FROM AD_UserDef_Field WHERE AD_Field_ID IN
	(SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881))
;
DELETE FROM AD_User_SortPref_Line WHERE AD_Field_ID IN
	(SELECT AD_Field_ID FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881))
;
DELETE FROM AD_Field WHERE AD_Column_ID IN (592878,592879,592880,592881)
;

-- ============================================================================
-- SeqNo/SeqNoGrid gap-close on the two AD_UI_ElementGroup's siblings that shift up
-- (tab 546679 group 550042; tab 547483 group 551690 - both formerly had SeqNo 60..100
-- after the two retired fields at SeqNo 40/50; shift down by 20 to close the gap)
-- ============================================================================

-- 2026-09-01 11:05:00
UPDATE AD_UI_Element SET SeqNo=40, SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-09-01 11:05:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653173;
UPDATE AD_UI_Element SET SeqNo=50, SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-09-01 11:05:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653174;
UPDATE AD_UI_Element SET SeqNo=60, SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-09-01 11:05:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653175;
UPDATE AD_UI_Element SET SeqNo=70, SeqNoGrid=70, Updated=TO_TIMESTAMP('2026-09-01 11:05:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653176;
UPDATE AD_UI_Element SET SeqNo=80, SeqNoGrid=80, Updated=TO_TIMESTAMP('2026-09-01 11:05:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653686;

UPDATE AD_UI_Element SET SeqNo=40, SeqNoGrid=40, Updated=TO_TIMESTAMP('2026-09-01 11:05:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653169;
UPDATE AD_UI_Element SET SeqNo=50, SeqNoGrid=50, Updated=TO_TIMESTAMP('2026-09-01 11:05:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653170;
UPDATE AD_UI_Element SET SeqNo=60, SeqNoGrid=60, Updated=TO_TIMESTAMP('2026-09-01 11:05:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653171;
UPDATE AD_UI_Element SET SeqNo=70, SeqNoGrid=70, Updated=TO_TIMESTAMP('2026-09-01 11:05:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653172;
UPDATE AD_UI_Element SET SeqNo=80, SeqNoGrid=80, Updated=TO_TIMESTAMP('2026-09-01 11:05:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_UI_Element_ID=653685;

-- ============================================================================
-- AD_Column_Trl + AD_Column cleanup
-- ============================================================================

DELETE FROM AD_Column_Trl WHERE AD_Column_ID IN (592878,592879,592880,592881)
;
DELETE FROM AD_Column WHERE AD_Column_ID IN (592878,592879,592880,592881)
;

-- ============================================================================
-- Physical column drop - backup first (business tables)
-- ============================================================================

SELECT backup_table('mobileui_mfg_config', '_31771_editableAttrFlags');
SELECT backup_table('mobileui_userprofile_mfg', '_31771_editableAttrFlags');

SELECT db_alter_table('MobileUI_MFG_Config', 'ALTER TABLE public.MobileUI_MFG_Config DROP COLUMN IsLotNumberEditable, DROP COLUMN IsBestBeforeDateEditable')
;

-- MobileUI_UserProfile_MFG's flags are NULLABLE (NULL = no per-user override). Unlike the global
-- MobileUI_MFG_Config table above (NOT NULL DEFAULT 'Y', already migrated to child rows by
-- 5821590), a per-user row can genuinely hold a real override that was never migrated anywhere -
-- abort rather than silently discard it.
DO $$
DECLARE
	v_count INTEGER;
BEGIN
	SELECT COUNT(*) INTO v_count
	FROM MobileUI_UserProfile_MFG
	WHERE IsLotNumberEditable IS NOT NULL OR IsBestBeforeDateEditable IS NOT NULL;

	IF v_count > 0 THEN
		RAISE EXCEPTION 'Aborting drop of MobileUI_UserProfile_MFG.IsLotNumberEditable/IsBestBeforeDateEditable: % row(s) in MobileUI_UserProfile_MFG have a non-NULL per-user override flag set - migration refused to avoid data loss', v_count;
	END IF;
END $$;

SELECT db_alter_table('MobileUI_UserProfile_MFG', 'ALTER TABLE public.MobileUI_UserProfile_MFG DROP COLUMN IsLotNumberEditable, DROP COLUMN IsBestBeforeDateEditable')
;
