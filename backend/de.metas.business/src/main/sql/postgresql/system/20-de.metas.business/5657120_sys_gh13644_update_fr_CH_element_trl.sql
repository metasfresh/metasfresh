
select backup_table('AD_Element_Trl');

UPDATE ad_element_trl
SET name                    = data.name_trl
  , printname               = data.name_trl
  , description             = data.description_trl
  , po_printname            = data.po_printname_trl
  , po_description          = data.po_description_trl
  , po_help                 = data.po_help_trl
  , webui_namenew           = data.webui_namenew_trl
  , webui_namebrowse        = data.webui_namebrowse_trl
  , webui_namenewbreadcrumb = data.webui_namenewbreadcrumb_trl
FROM migration_data.ad_element_trl_220907_2 data
WHERE ad_element_trl.ad_element_id = data.ad_element_id
  AND ad_language ilike 'fr_%'
  AND length(trim(data.name_trl)) > 0
;
