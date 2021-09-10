-- 2021-06-25T16:39:13.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET SQL_Details_WhereClause='oi.AD_Client_ID = @AD_Client_ID@ AND oi.AD_Org_ID=@AD_Org_ID@', SQL_From='FROM ad_clientinfo ci
LEFT OUTER JOIN rv_openitem oi ON oi.ad_client_id = ci.ad_client_id AND oi.ad_org_id = @AD_Org_ID@
LEFT OUTER JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id
LEFT OUTER JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id
', SQL_WhereClause='ci.AD_Client_ID = @AD_Client_ID@',Updated=TO_TIMESTAMP('2021-06-25 19:39:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

-- 2021-06-25T21:54:44.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET SQL_Details_WhereClause='RV_OpenItem.AD_Client_ID = @AD_Client_ID@ AND RV_OpenItem.AD_Org_ID=@AD_Org_ID@',Updated=TO_TIMESTAMP('2021-06-26 00:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

