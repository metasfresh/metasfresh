-- AD_Element 2068 (ColumnName 'P_Msg', EntityType 'D') has never had a German translation: the
-- de_DE and de_CH AD_Element_Trl rows still carry the English base text 'Process Message' /
-- 'P Msg' with IsTranslated='N', so a German session falls back to the English caption. This is
-- not a mistranslation to correct - it is a missing translation to supply.
--
-- It became visible in two places instead of one after 5825180_AD_PInstance_Log_P_Msg_always_visible.sql
-- made AD_PInstance_Log.P_Msg render in BOTH the grid and the single-row form of window 332
-- ("Prozess-Revision") tab 665 ("Protokoll").
--
-- Impact analysis - every usage of AD_Element_ID=2068 (all mean the same thing, "the message a
-- process wrote into its log"), so the shared element is mutated rather than forked:
--   AD_Column 8781   AD_PInstance_Log.P_Msg
--   AD_Column 9389   AD_Replication_Log.P_Msg
--   AD_Column 572971 ExternalSystem_Config_PInstance_Log_v.P_Msg
--   AD_Field  10521  window 332 "Prozess-Revision" / tab 665 "Protokoll"
--   AD_Field  554984 window 540231 "Stapel" / tab 540634 "Log"
--   AD_Field  7512   window 284 "Replizierung" / tab 523 "Protokoll"
--   AD_Field  632157 window 541040 "Externes System Log"
-- No AD_Window, AD_Tab, AD_Menu, AD_Process_Para, AD_UI_Element (AD_Name_ID) or WEBUI_KPI_Field
-- references this element.
--
-- Wording: "Prozessmeldung" - "Prozess" is the established German term for Process
-- (AD_Element 117 AD_Process_ID -> "Prozess", 114 AD_PInstance_ID -> "Prozess-Instanz") and
-- "-meldung" the established head noun for Message (AD_Element 1021 ErrorMsg -> "Fehlermeldung",
-- 1752 AD_Message_ID / 2749 Message -> "Meldung"); every "-meldung" compound in AD_Element_Trl
-- de_DE is written solid, e.g. "Fehlermeldung" and "Gruppierungsfehlermeldung".
--
-- Only de_DE (base language) and de_CH are touched. en_US, en_GB, fr_CH and it_CH are left as they
-- are - fr_CH already carries a proper French translation.

-- 1. de_DE (base language)
UPDATE AD_Element_Trl SET Name='Prozessmeldung', PrintName='Prozessmeldung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-18 14:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=2068 AND AD_Language='de_DE'
;

-- 2. de_CH - same text as de_DE (no 'ss'/'ß' divergence in this term)
UPDATE AD_Element_Trl SET Name='Prozessmeldung', PrintName='Prozessmeldung', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-18 14:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=2068 AND AD_Language='de_CH'
;

-- 3. Propagate to AD_Element (base row, from the de_DE trl row), AD_Column/AD_Column_Trl,
--    AD_Field/AD_Field_Trl and AD_PrintFormatItem/_Trl - the established mechanism; never patch
--    those tables by hand.
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(2068)
;
