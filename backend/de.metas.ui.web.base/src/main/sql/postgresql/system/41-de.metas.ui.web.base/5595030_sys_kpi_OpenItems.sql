-- 2021-06-25T13:10:56.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI (AD_Client_ID,AD_Org_ID,ChartType,Created,CreatedBy,Description,IsActive,IsApplySecuritySettings,IsGenerateComparation,KPI_DataSource_Type,Name,Source_Table_ID,SQL_WhereClause,Updated,UpdatedBy,WEBUI_KPI_ID) VALUES (0,0,'M',TO_TIMESTAMP('2021-06-25 16:10:56','YYYY-MM-DD HH24:MI:SS'),100,'Target indicator showing current invoice open amount','Y','N','N','S','Invoice Open Amount',413,'AD_Org_ID=@AD_Org_ID@',TO_TIMESTAMP('2021-06-25 16:10:56','YYYY-MM-DD HH24:MI:SS'),100,540004)
;

-- 2021-06-25T13:12:18.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,1526,0,12,TO_TIMESTAMP('2021-06-25 16:12:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Open Amount','COALESCE(SUM(currencyconvert(oi.openamt, oi.c_currency_id, cas.c_currency_id, oi.dateacct, oi.c_conversiontype_id, oi.ad_client_id, oi.ad_org_id)), 0)',TO_TIMESTAMP('2021-06-25 16:12:18','YYYY-MM-DD HH24:MI:SS'),100,540003,540004)
;

-- 2021-06-25T13:12:18.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540003 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2021-06-25T13:12:28.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET SQL_From='FROM rv_openitem oi
INNER JOIN ad_clientinfo ci ON ci.ad_client_id = oi.ad_client_id
INNER JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id;
',Updated=TO_TIMESTAMP('2021-06-25 16:12:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

-- 2021-06-25T13:12:39.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET SQL_From='FROM rv_openitem oi
INNER JOIN ad_clientinfo ci ON ci.ad_client_id = oi.ad_client_id
INNER JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id
',Updated=TO_TIMESTAMP('2021-06-25 16:12:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

-- 2021-06-25T13:13:24.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI_Field (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,IsActive,IsGroupBy,Name,SQL_Select,Updated,UpdatedBy,WEBUI_KPI_Field_ID,WEBUI_KPI_ID) VALUES (0,193,0,30,TO_TIMESTAMP('2021-06-25 16:13:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Currency','cas.C_Currency_ID',TO_TIMESTAMP('2021-06-25 16:13:24','YYYY-MM-DD HH24:MI:SS'),100,540004,540004)
;

-- 2021-06-25T13:13:24.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO WEBUI_KPI_Field_Trl (AD_Language,WEBUI_KPI_Field_ID, Name,OffsetName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.WEBUI_KPI_Field_ID, t.Name,t.OffsetName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, WEBUI_KPI_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.WEBUI_KPI_Field_ID=540004 AND NOT EXISTS (SELECT 1 FROM WEBUI_KPI_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.WEBUI_KPI_Field_ID=t.WEBUI_KPI_Field_ID)
;

-- 2021-06-25T13:47:58.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET SQL_From='FROM rv_openitem oi
INNER JOIN ad_clientinfo ci ON ci.ad_client_id = oi.ad_client_id
INNER JOIN c_acctschema cas ON cas.c_acctschema_id = ci.c_acctschema1_id
INNER JOIN c_currency cy ON cy.c_currency_id = cas.c_currency_id
',Updated=TO_TIMESTAMP('2021-06-25 16:47:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

-- 2021-06-25T13:49:30.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI_Field SET AD_Element_ID=577559, AD_Reference_ID=10, SQL_Select='cy.iso_code',Updated=TO_TIMESTAMP('2021-06-25 16:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_Field_ID=540004
;

-- 2021-06-25T14:23:32.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET SQL_WhereClause='oi.AD_Org_ID=@AD_Org_ID@',Updated=TO_TIMESTAMP('2021-06-25 17:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

-- 2021-06-25T14:24:34.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE WEBUI_KPI SET SQL_GroupAndOrderBy='GROUP BY cy.iso_code',Updated=TO_TIMESTAMP('2021-06-25 17:24:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540004
;

