CREATE UNIQUE INDEX IF NOT EXISTS idx_ad_window_trl_name_ad_language
    ON ad_window_trl (ad_org_id, name, ad_language)
;


CREATE UNIQUE INDEX IF NOT EXISTS idx_c_uom_trl_name_ad_language
    ON c_uom_trl (ad_client_id, name, ad_language)
;
