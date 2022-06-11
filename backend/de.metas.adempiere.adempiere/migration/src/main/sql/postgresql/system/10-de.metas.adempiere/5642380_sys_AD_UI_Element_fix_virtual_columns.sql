-- Column: AD_UI_Element.AD_Window_ID
-- Column SQL (old): (            select w.ad_window_id            from ad_field f                     JOIN ad_tab t on f.ad_tab_id = t.ad_tab_id                     JOIN ad_window w on t.ad_window_id = w.ad_window_id            where f.ad_field_id = ad_ui_element.ad_field_id )
-- 2022-06-06T14:02:24.113Z
UPDATE AD_Column SET ColumnSQL='( select w.ad_window_id from ad_field f JOIN ad_tab t on f.ad_tab_id = t.ad_tab_id JOIN ad_window w on t.ad_window_id = w.ad_window_id where f.ad_field_id = AD_UI_Element.AD_Field_ID )', IsLazyLoading='Y',Updated=TO_TIMESTAMP('2022-06-06 17:02:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568590
;

