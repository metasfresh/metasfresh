-- 2023-10-04T15:04:40.145Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585322,'Y','de.metas.contracts.flatrate.process.C_Flatrate_Term_Change_Product_for_Partner','N',TO_TIMESTAMP('2023-10-04 18:04:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','N','Y','Y',0,'Mitgliederkategorie ändern','json','N','N','xls','Java',TO_TIMESTAMP('2023-10-04 18:04:39','YYYY-MM-DD HH24:MI:SS'),100,'contract_product_update_for_partner')
;

-- 2023-10-04T15:04:40.157Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585322 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2023-10-04T15:05:27.460Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,585322,542706,19,'M_Product_ID',TO_TIMESTAMP('2023-10-04 18:05:27','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','U',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','Y','N','Produkt',10,TO_TIMESTAMP('2023-10-04 18:05:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-04T15:05:27.462Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542706 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-04T15:09:19.476Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540659,'M_Product.AD_Org_ID = (select AD_Org_ID from C_BPartner bp where bp.C_BPartner_id = @C_BPartner@) 
AND EXISTS(SELECT 1 FROM M_Product_Category pc WHERE M_Product.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.M_Product_Category_ID = (SELECT M_Product_Category_ID FROM m_product WHERE m_product.m_product_id =  M_Product.M_Product_ID))',TO_TIMESTAMP('2023-10-04 18:09:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Products of partner''s org','S',TO_TIMESTAMP('2023-10-04 18:09:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-04T15:09:35.822Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540659,Updated=TO_TIMESTAMP('2023-10-04 18:09:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542706
;

-- 2023-10-04T15:09:40.859Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-10-04 18:09:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542706
;

-- 2023-10-04T15:10:12.393Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585322,291,541429,TO_TIMESTAMP('2023-10-04 18:10:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-10-04 18:10:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- 2023-10-05T12:37:53.788Z
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID = (select AD_Org_ID from C_BPartner bp where bp.C_BPartner_id = @C_BPartner_ID@) ',Updated=TO_TIMESTAMP('2023-10-05 15:37:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540659
;

