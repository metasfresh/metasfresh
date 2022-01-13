update webui_kpi_field_trl set ad_client_id=0, ad_org_id=0 where ad_client_id!=0 or ad_org_id!=0;
update webui_kpi_field set ad_client_id=0, ad_org_id=0 where ad_client_id!=0 or ad_org_id!=0;
update webui_kpi set ad_client_id=0, ad_org_id=0 where ad_client_id!=0 or ad_org_id!=0;



-- 2021-06-26T05:53:33.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AccessLevel='4', IsChangeLog='Y',Updated=TO_TIMESTAMP('2021-06-26 08:53:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540801
;

-- 2021-06-26T05:53:39.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2021-06-26 08:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540801
;

