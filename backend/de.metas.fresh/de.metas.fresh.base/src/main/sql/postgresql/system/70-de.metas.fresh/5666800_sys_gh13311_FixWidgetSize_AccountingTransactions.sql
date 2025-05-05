UPDATE ad_ui_element SET   updated = now(), updatedby = 99, widgetsize = null WHERE ad_ui_element_id = 543370;
UPDATE ad_ui_element SET   updated = now(), updatedby = 99, widgetsize = null WHERE ad_ui_element_id = 548015;
UPDATE ad_ui_element SET   updated = now(), updatedby = 99, widgetsize = null WHERE ad_ui_element_id = 575353;
UPDATE ad_ui_element SET   updated = now(), updatedby = 99, widgetsize = null WHERE ad_ui_element_id = 612272;
UPDATE ad_ui_element SET   updated = now(), updatedby = 99, widgetsize = null WHERE ad_ui_element_id = 613238;

update ad_ui_element set widgetsize = null, updated=now(), updatedby=99 where trim(widgetsize)='';

