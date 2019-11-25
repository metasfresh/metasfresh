-- 2019-11-19T11:42:01.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,AD_Org_ID,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,Name,SQLStatement,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-19 13:42:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-19 13:42:01','YYYY-MM-DD HH24:MI:SS'),'N',0,'N','7','N','N','N','N',100,541210,'ExportPriceListSchemaLine','Y','Y','N','N','N',0,'Excel','Y','ExportPriceListSchemaLine','
SELECT l.seqno                                                                                               "Sequence",
       (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               "Product",
       (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) "Product Category",
       (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         "Business Partner",
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 "Taxcategory",
       l.std_addamt                                                                                          "Standard Price Surcharge Amount",
       l.std_rounding                                                                                        "Standard Price Rounding",
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          "Target Taxcategory"
FROM m_discountschemaline l
where l.m_discountschema_id = @M_DiscountSchema_ID@','de.metas.impexp.excel.process.ExportToExcelProcess','D')
;

-- 2019-11-19T11:42:01.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541210 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-11-19T11:43:52.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,AD_Window_ID,AD_Tab_ID,WEBUI_DocumentAction,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2019-11-19 13:43:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',475,TO_TIMESTAMP('2019-11-19 13:43:52','YYYY-MM-DD HH24:MI:SS'),100,0,'N',541210,337,405,'Y','Y','N','N',540755,0,'D')
;

-- 2019-11-19T11:53:00.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Export Price List Schema Lines',Updated=TO_TIMESTAMP('2019-11-19 13:53:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210 AND AD_Language='de_CH'
;

-- 2019-11-19T11:53:04.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Name='Export Price List Schema Lines',Updated=TO_TIMESTAMP('2019-11-19 13:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-19T11:53:11.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Export Price List Schema Lines',Updated=TO_TIMESTAMP('2019-11-19 13:53:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210 AND AD_Language='en_US'
;

-- 2019-11-19T12:15:33.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Preisliste Schemalinien exportieren',Updated=TO_TIMESTAMP('2019-11-19 14:15:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210 AND AD_Language='de_CH'
;

-- 2019-11-19T12:16:46.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsReport,AD_Org_ID,IsDirectPrint,AccessLevel,ShowHelp,IsBetaFunctionality,IsServerProcess,CopyFromProcess,UpdatedBy,AD_Process_ID,Value,AllowProcessReRun,IsUseBPartnerLanguage,IsApplySecuritySettings,RefreshAllAfterExecution,IsOneInstanceOnly,LockWaitTimeout,Type,IsTranslateExcelHeaders,Name,Classname,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-19 14:16:46','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-19 14:16:46','YYYY-MM-DD HH24:MI:SS'),'N',0,'N','7','N','N','N','N',100,541211,'ImportPriceListSchemaLinesFromAttachment','Y','Y','N','N','N',0,'Java','Y','Import Price List Schema Lines From Attachment','de.metas.pricing.process.ImportPriceListSchemaLinesFromAttachment','D')
;

-- 2019-11-19T12:16:46.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_ID=541211 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2019-11-19T12:19:31.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,Created,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,FieldLength,AD_Org_ID,IsCentrallyMaintained,AD_Element_ID,IsEncrypted,Updated,UpdatedBy,ColumnName,IsMandatory,IsAutocomplete,SeqNo,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-19 14:19:31','YYYY-MM-DD HH24:MI:SS'),100,541211,19,'N',541636,0,0,'Y',543390,'N',TO_TIMESTAMP('2019-11-19 14:19:31','YYYY-MM-DD HH24:MI:SS'),100,'AD_AttachmentEntry_ID','Y','N',10,'Anhang','D')
;

-- 2019-11-19T12:19:31.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541636 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2019-11-19T12:21:49.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (Code,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Val_Rule_ID,Type,Name,AD_Org_ID,EntityType) VALUES ('AD_AttachmentEntry_ID IN (
	select r.AD_AttachmentEntry_ID 
	from AD_Attachment_MultiRef r 
	where r.AD_Table_ID=get_Table_ID(''M_DiscountSchema'') and r.Record_ID=@M_DiscountSchema_ID@ )',0,'Y',TO_TIMESTAMP('2019-11-19 14:21:49','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-19 14:21:49','YYYY-MM-DD HH24:MI:SS'),100,540470,'S','AD_AttachmentEntry_for_PriceListSchema_ID',0,'D')
;

-- 2019-11-19T12:22:22.862Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540470,Updated=TO_TIMESTAMP('2019-11-19 14:22:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541636
;

-- 2019-11-19T12:23:07.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (Created,CreatedBy,IsActive,AD_Table_ID,Updated,UpdatedBy,AD_Client_ID,WEBUI_ViewQuickAction,AD_Process_ID,AD_Window_ID,AD_Tab_ID,WEBUI_DocumentAction,WEBUI_ViewAction,WEBUI_IncludedTabTopAction,WEBUI_ViewQuickAction_Default,AD_Table_Process_ID,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2019-11-19 14:23:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',475,TO_TIMESTAMP('2019-11-19 14:23:07','YYYY-MM-DD HH24:MI:SS'),100,0,'N',541211,337,405,'Y','Y','N','N',540756,0,'D')
;

-- 2019-11-19T12:23:47.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET AD_Tab_ID=675,Updated=TO_TIMESTAMP('2019-11-19 14:23:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540756
;

-- 2019-11-19T12:32:48.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Value='ExportPriceListSchemaLines',Updated=TO_TIMESTAMP('2019-11-19 14:32:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-19T12:33:22.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET AD_Tab_ID=NULL,Updated=TO_TIMESTAMP('2019-11-19 14:33:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540756
;

-- 2019-11-19T12:33:24.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET AD_Tab_ID=NULL,Updated=TO_TIMESTAMP('2019-11-19 14:33:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540755
;

-- 2019-11-19T13:41:07.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Name='Schema-position Exportpreisliste',Updated=TO_TIMESTAMP('2019-11-19 15:41:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210 AND AD_Language='de_CH'
;

-- 2019-11-19T13:41:13.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Schema-position Exportpreisliste',Updated=TO_TIMESTAMP('2019-11-19 15:41:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210 AND AD_Language='de_DE'
;

-- 2019-11-19T13:48:23.441Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.pricing.ImportPriceListSchemaLinesFromAttachment',Updated=TO_TIMESTAMP('2019-11-19 15:48:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541211
;

-- 2019-11-19T14:08:52.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Preislistenschemapositionen aus Anhang importieren',Updated=TO_TIMESTAMP('2019-11-19 16:08:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541211 AND AD_Language='de_CH'
;

-- 2019-11-19T14:08:56.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Preislistenschemapositionen aus Anhang importieren',Updated=TO_TIMESTAMP('2019-11-19 16:08:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541211 AND AD_Language='de_DE'
;

-- 2019-11-19T14:09:00.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-19 16:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541211 AND AD_Language='en_US'
;

-- 2019-11-19T14:25:46.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT l.seqno                                                                                               "Sequence",
       (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               "Product",
       (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) "Product Category",
       (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         "Business Partner",
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 "Taxcategory",
       l.std_addamt                                                                                          "Standard Price Surcharge Amount",
       l.std_rounding                                                                                        "Standard Price Rounding",
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          "Target Taxcategory"
--
        ,
       l.List_Base,
       l.C_ConversionType_ID,
       l.Limit_AddAmt,
       l.Limit_Base,
       l.Limit_Discount,
       l.Limit_MaxAmt,
       l.Limit_MinAmt,
       l.Limit_Rounding,
       l.List_AddAmt,
       l.List_Discount,
       l.List_MaxAmt,
       l.List_MinAmt,
       l.List_Rounding,
       l.Std_Base,
       l.Std_Discount,
       l.Std_MaxAmt,
       l.Std_MinAmt


FROM m_discountschemaline l
WHERE l.m_discountschema_id = @M_DiscountSchema_ID@',Updated=TO_TIMESTAMP('2019-11-19 16:25:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-19T14:28:32.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='






SELECT l.seqno,
       (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               m_product_id,
       (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_id,
       (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_id,
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
       l.std_addamt,
       l.std_rounding,
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id,
       -- default values
       l.List_Base,
       l.C_ConversionType_ID,
       l.Limit_AddAmt,
       l.Limit_Base,
       l.Limit_Discount,
       l.Limit_MaxAmt,
       l.Limit_MinAmt,
       l.Limit_Rounding,
       l.List_AddAmt,
       l.List_Discount,
       l.List_MaxAmt,
       l.List_MinAmt,
       l.List_Rounding,
       l.Std_Base,
       l.Std_Discount,
       l.Std_MaxAmt,
       l.Std_MinAmt


FROM m_discountschemaline l
WHERE l.m_discountschema_id = @M_DiscountSchema_ID@',Updated=TO_TIMESTAMP('2019-11-19 16:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-20T08:08:24.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Process SET WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2019-11-20 10:08:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540755
;

-- 2019-11-20T08:14:04.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT l.seqno,
       (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               m_product_id,
       (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_id,
       (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_id,
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
       l.std_addamt,
       l.std_rounding,
       (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id,
   
    -- default values
       l.List_Base,
       l.C_ConversionType_ID,
       l.Limit_AddAmt,
       l.Limit_Base,
       l.Limit_Discount,
       l.Limit_MaxAmt,
       l.Limit_MinAmt,
       l.Limit_Rounding,
       l.List_AddAmt,
       l.List_Discount,
       l.List_MaxAmt,
       l.List_MinAmt,
       l.List_Rounding,
       l.Std_Base,
       l.Std_Discount,
       l.Std_MaxAmt,
       l.Std_MinAmt

FROM m_discountschemaline l
WHERE l.m_discountschema_id = @M_DiscountSchema_ID@',Updated=TO_TIMESTAMP('2019-11-20 10:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-20T09:09:43.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.pricing.process.ImportPriceListSchemaLinesFromAttachment',Updated=TO_TIMESTAMP('2019-11-20 11:09:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541211
;

