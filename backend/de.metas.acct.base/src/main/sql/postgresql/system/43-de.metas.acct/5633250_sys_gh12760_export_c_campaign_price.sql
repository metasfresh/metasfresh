-- 2022-04-04T14:18:29.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,SQLStatement,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585034,'Y','de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess','N',TO_TIMESTAMP('2022-04-04 17:18:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y',0,'Aktionspreise Export','json','N','N','xls','SELECT p.name as "Produkt",
       bp.name as "Geschäftspartner",
       bpg.Value as "Geschäftspartnergruppe",
       ps.value as "Preissystem",
       c.countryCode as "Land",
       cur.iso_code as "Währung",
       cp.pricestd as "Standardpreis",
       cp.validfrom as "Gültig ab",
       cp.validTo as "Gültig bis",
       uom.x12de355 as "Maßeinheit",
       tc.Name as "Steuerkategorie",
       cp.invoicableqtybasedon as "Abr. Menge basiert auf",
       cp.ad_org_id as "Sektion",
       cp.ad_client_id as "Mandant"
FROM c_campaign_price cp
         LEFT JOIN m_product p ON cp.m_product_id = p.m_product_id
         LEFT JOIN c_bpartner bp ON cp.c_bpartner_id = bp.c_bpartner_id
         LEFT JOIN c_bp_group bpg ON cp.c_bp_group_id = bpg.c_bp_group_id
         LEFT JOIN m_pricingsystem ps ON cp.m_pricingsystem_id = ps.m_pricingsystem_id
         LEFT JOIN c_country c ON cp.c_country_id = c.c_country_id
         LEFT JOIN c_currency cur ON cp.c_currency_id = cur.c_currency_id
         LEFT JOIN c_uom uom ON cp.c_uom_id = uom.c_uom_id
         LEFT JOIN c_taxCategory tc ON cp.c_taxcategory_id = tc.c_taxcategory_id
WHERE cp.validFrom <= ''2022-04-04''::DATE
  AND cp.validTo >= ''2022-04-04''::DATE
  AND cp.isactive = ''Y''
;','Excel',TO_TIMESTAMP('2022-04-04 17:18:29','YYYY-MM-DD HH24:MI:SS'),100,'Export_C_Campaign_Price')
;

-- 2022-04-04T14:18:29.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585034 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-04-04T14:20:27.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577762,0,585034,542244,15,'Date',TO_TIMESTAMP('2022-04-04 17:20:27','YYYY-MM-DD HH24:MI:SS'),100,'@#Date@','D',0,'Y','N','Y','N','N','N','Datum',10,TO_TIMESTAMP('2022-04-04 17:20:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-04T14:20:27.594Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542244 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-04-04T14:20:47.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT p.value as "Produkt", bp.value as "Geschäftspartner", bpg.Value as "Geschäftspartnergruppe", ps.value as "Preissystem", c.countryCode as "Land", cur.iso_code as "Währung", cp.pricestd as "Standardpreis", cp.validfrom as "Gültig ab", cp.validTo as "Gültig bis", uom.name as "Maßeinheit", tc.Name as "Steuerkategorie", cp.invoicableqtybasedon as "Abr. Menge basiert auf" FROM c_campaign_price cp LEFT JOIN m_product p ON cp.m_product_id = p.m_product_id LEFT JOIN c_bpartner bp ON cp.c_bpartner_id = bp.c_bpartner_id LEFT JOIN c_bp_group bpg ON cp.c_bp_group_id = bpg.c_bp_group_id LEFT JOIN m_pricingsystem ps ON cp.m_pricingsystem_id = ps.m_pricingsystem_id LEFT JOIN c_country c ON cp.c_country_id = c.c_country_id LEFT JOIN c_currency cur ON cp.c_currency_id = cur.c_currency_id LEFT JOIN c_uom uom ON cp.c_uom_id = uom.c_uom_id LEFT JOIN c_taxCategory tc ON cp.c_taxcategory_id = tc.c_taxcategory_id WHERE cp.validTo >= ''@Date@''::DATE AND cp.isactive = ''Y'' ORDER BY p.value, cp.validTo;',Updated=TO_TIMESTAMP('2022-04-04 17:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585034
;

-- 2022-04-04T14:21:25.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585034,541174,541101,TO_TIMESTAMP('2022-04-04 17:21:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2022-04-04 17:21:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

