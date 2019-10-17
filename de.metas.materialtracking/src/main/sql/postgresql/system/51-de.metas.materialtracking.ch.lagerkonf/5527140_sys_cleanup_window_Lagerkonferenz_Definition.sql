--
-- This cleanup is about an obsolete and half-backed webui-window that just causes confusion.
-- The original lagerkonferenz-window was meanwhile adapted to the webUI and works just fine.
--

-- 2019-07-11T22:26:52.899
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET IsActive='N',Updated=TO_TIMESTAMP('2019-07-11 22:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540363
;

-- 2019-07-11T22:26:52.915
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description=NULL, IsActive='N', Name='Lagerkonferenz Definition',Updated=TO_TIMESTAMP('2019-07-11 22:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540954
;

delete from ad_menu where ad_window_id=540363;

delete from ad_ui_element where ad_tab_id=540865;
delete from ad_ui_elementgroup where ad_ui_column_id IN (select ad_ui_column_ID from ad_ui_column where ad_ui_section_id IN (select ad_ui_section_id from ad_ui_section where ad_tab_id=540865));
delete from ad_ui_column where ad_ui_section_id IN (select ad_ui_section_id from ad_ui_section where ad_tab_id=540865);
delete from ad_ui_section_trl where ad_ui_section_id IN (select ad_ui_section_id from ad_ui_section where ad_tab_id=540865);
delete from ad_ui_section where ad_tab_id=540865;
-- 2019-07-11T22:51:32.685
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540363
;

-- 2019-07-11T22:51:32.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=540363
;

-- 2019-07-11T22:51:32.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=540363
;

