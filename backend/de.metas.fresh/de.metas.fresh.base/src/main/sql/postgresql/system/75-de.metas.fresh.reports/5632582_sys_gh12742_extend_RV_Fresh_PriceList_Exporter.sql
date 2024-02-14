
-- 2022-03-30T13:38:12.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53458,0,584659,542240,30,138,NULL,'DropShip_BPartner_ID',TO_TIMESTAMP('2022-03-30 15:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner to ship to','de.metas.ordercandidate',0,'If empty the business partner will be shipped to.','Y','Y','Y','N','N','N','Lieferempfänger',70,TO_TIMESTAMP('2022-03-30 15:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-30T13:38:12.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542240 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-03-30T13:39:46.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,53459,0,584659,542241,30,159,52022,'DropShip_Location_ID',TO_TIMESTAMP('2022-03-30 15:39:46','YYYY-MM-DD HH24:MI:SS'),100,'Business Partner Location for shipping to','U',0,'Y','N','Y','N','N','N','Lieferadresse',80,TO_TIMESTAMP('2022-03-30 15:39:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-30T13:39:46.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542241 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2022-03-30T13:40:11.711Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.ordercandidate',Updated=TO_TIMESTAMP('2022-03-30 15:40:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542241
;

-- 2022-03-30T13:40:47.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2022-03-30 15:40:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541746
;

-- 2022-03-30T13:40:56.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2022-03-30 15:40:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542240
;

-- 2022-03-30T13:41:02.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2022-03-30 15:41:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542241
;

-- 2022-03-30T13:41:14.935Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2022-03-30 15:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541733
;

-- 2022-03-30T13:41:31.421Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2022-03-30 15:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

-- 2022-03-30T13:44:39.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM report.fresh_pricelist_details_template_report(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, ''@p_show_product_price_pi_flag/NULL@'', @DropShip_BPartner_ID/NULL@, @DropShip_Location_ID/NULL@) where show_product_price_pi_flag = ''N'' UNION ALL SELECT * FROM report.fresh_pricelist_details_template_report_With_PP_PI(@C_BPartner_ID/NULL@, @M_PriceList_Version_ID/NULL@,''@#AD_Language@'',@C_BPartner_Location_ID/NULL@, ''@p_show_product_price_pi_flag/NULL@'', @DropShip_BPartner_ID/NULL@, @DropShip_Location_ID/NULL@) where show_product_price_pi_flag = ''Y'';', TechnicalNote='Exports the SQL-Statement''s data into and excel template file, using the java libary jxls',Updated=TO_TIMESTAMP('2022-03-30 15:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584659
;

