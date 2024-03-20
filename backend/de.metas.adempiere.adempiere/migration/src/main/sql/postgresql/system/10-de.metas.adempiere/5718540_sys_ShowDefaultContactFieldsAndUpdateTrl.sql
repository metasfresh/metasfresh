INSERT INTO ad_ui_element (ad_client_id, ad_field_id, ad_org_id, ad_ui_element_id, ad_ui_elementgroup_id, created, createdby, description, help, isactive, isadvancedfield, name, seqno, updated, updatedby, uistyle, isdisplayed, isdisplayedgrid, seqnogrid, isdisplayed_sidelist, seqno_sidelist, ad_tab_id, widgetsize, ad_ui_elementtype, labels_tab_id, labels_selector_field_id, isallowfiltering,
                                  mediatypes, ismultiline, multiline_linescount, inline_tab_id, vieweditmode, ad_name_id)
VALUES (0, 583304, 0, nextval('ad_ui_element_seq'), 542737, '2024-03-06 21:51:10.000000 +02:00', 100, NULL, NULL, 'Y', 'N', 'IsPurchaseContact_Default', 130, '2024-03-06 22:23:12.000000 +02:00', 100, NULL, 'Y', 'Y', 80, 'N', 0, 541855, NULL, 'F', NULL, NULL, 'N', NULL, 'N', NULL, NULL, NULL, NULL)
;

INSERT INTO ad_ui_element (ad_client_id, ad_field_id, ad_org_id, ad_ui_element_id, ad_ui_elementgroup_id, created, createdby, description, help, isactive, isadvancedfield, name, seqno, updated, updatedby, uistyle, isdisplayed, isdisplayedgrid, seqnogrid, isdisplayed_sidelist, seqno_sidelist, ad_tab_id, widgetsize, ad_ui_elementtype, labels_tab_id, labels_selector_field_id, isallowfiltering,
                           mediatypes, ismultiline, multiline_linescount, inline_tab_id, vieweditmode, ad_name_id)
VALUES (0, 583306, 0, nextval('ad_ui_element_seq'), 542737, '2024-03-06 21:51:10.000000 +02:00', 100, NULL, NULL, 'Y', 'N', 'IsSalesContact_Default', 140, '2024-03-06 22:23:12.000000 +02:00', 100, NULL, 'Y', 'Y', 80, 'N', 0, 541855, NULL, 'F', NULL, NULL, 'N', NULL, 'N', NULL, NULL, NULL, NULL)
;

UPDATE ad_element_trl SET  name = 'Standard-Vertriebskontakt', printname = 'Standard-Vertriebskontakt' WHERE ad_element_id = 543275 AND ad_language IN ('de_CH', 'de_DE');

UPDATE ad_element_trl SET  name = 'Standardkontakt für den Kauf', printname = 'Standardkontakt für den Kauf' WHERE ad_element_id = 543274 AND ad_language IN ('de_CH', 'de_DE');

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543275,'de_CH');
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543275,'de_DE');

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543274,'de_CH');
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543274,'de_DE');


/* DDL */  select update_ad_element_on_ad_element_trl_update(543275,'de_DE');
/* DDL */  select update_ad_element_on_ad_element_trl_update(543275,'de_CH');

/* DDL */  select update_ad_element_on_ad_element_trl_update(543274,'de_DE');
/* DDL */  select update_ad_element_on_ad_element_trl_update(543274,'de_CH');