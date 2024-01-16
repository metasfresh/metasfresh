-- 2021-10-13T13:48:07.006805300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584924,'Y','de.metas.contracts.flatrate.process.C_Flatrate_Term_Change_Product','N',TO_TIMESTAMP('2021-10-13 16:48:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','N','N','N','N','N','N','N','Y','Y',0,'Mitgliederkategorie ändern','json','N','N','xls','Java',TO_TIMESTAMP('2021-10-13 16:48:06','YYYY-MM-DD HH24:MI:SS'),100,'contract_product_update')
;

-- 2021-10-13T13:48:07.011959100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584924 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-10-13T14:00:22.475960100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,454,0,584924,542119,19,540167,'M_Product_ID',TO_TIMESTAMP('2021-10-13 17:00:22','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','de.metas.contracts',0,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','Produkt',10,TO_TIMESTAMP('2021-10-13 17:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-13T14:00:22.478027800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542119 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-10-13T14:00:28.900461100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET FieldLength=10,Updated=TO_TIMESTAMP('2021-10-13 17:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542119
;

-- 2021-10-13T14:00:32.692646100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-10-13 17:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542119
;

-- 2021-10-13T14:01:51.259894500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584924,540320,541010,540359,TO_TIMESTAMP('2021-10-13 17:01:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2021-10-13 17:01:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-10-13T14:09:33.878873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=19,Updated=TO_TIMESTAMP('2021-10-13 17:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- 2021-10-13T14:10:31.126493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2021-10-13 17:10:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542119
;

-- 2021-10-13T14:12:29.513958500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540561,'M_Product.AD_Org_ID=@AD_Org_ID@',TO_TIMESTAMP('2021-10-13 17:12:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Products of org','S',TO_TIMESTAMP('2021-10-13 17:12:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-13T14:12:41.897632900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=18, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2021-10-13 17:12:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=547283
;

-- 2021-10-13T14:12:52.996767600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540561,Updated=TO_TIMESTAMP('2021-10-13 17:12:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542119
;

-- 2021-10-13T14:18:31.749645400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID=@AD_Org_ID/0@',Updated=TO_TIMESTAMP('2021-10-13 17:18:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540561
;

-- 2021-10-13T14:26:21.792734300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-13 17:26:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584924
;

-- 2021-10-13T14:26:24.169058900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-13 17:26:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584924
;

-- 2021-10-13T14:26:48.368439300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Change product',Updated=TO_TIMESTAMP('2021-10-13 17:26:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584924
;

-- 2021-10-13T15:34:15.967515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID=@AD_Org_ID@ AND  exists (select 1 from M_Product.M_Product_Category pc where  M_Product.M_Product_Category_ID = pc.M_Product_Category_ID and pc.pc.M_Product_Category_ID=(select M_Product_Category_ID from m_product where m_product.m_product_id = @M_Product_ID@ )) ',Updated=TO_TIMESTAMP('2021-10-13 18:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540561
;

-- 2021-10-13T15:35:43.301115300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Product.AD_Org_ID =@ AD_Org_ID@
  AND EXISTS(SELECT 1 FROM M_Product_Category pc WHERE M_Product.M_Product_Category_ID = pc.M_Product_Category_ID AND pc.M_Product_Category_ID = (SELECT M_Product_Category_ID FROM m_product WHERE m_product.m_product_id = @M_Product_ID@)) ',Updated=TO_TIMESTAMP('2021-10-13 18:35:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540561
;


-- 2021-10-13T16:21:54.545706800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545066,0,TO_TIMESTAMP('2021-10-13 19:21:54','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Can not change the product. We got invoiced for this contract.','E',TO_TIMESTAMP('2021-10-13 19:21:54','YYYY-MM-DD HH24:MI:SS'),100,'TermHasInvoices')
;

-- 2021-10-13T16:21:54.547811800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545066 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2021-10-13T16:22:21.893029200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Can not change the product. We got invoices for this contract.',Updated=TO_TIMESTAMP('2021-10-13 19:22:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545066
;

-- 2021-10-13T16:22:33.572742700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Kann das Produkt nicht ändern. Wir haben Rechnungen für diesen Vertrag.',Updated=TO_TIMESTAMP('2021-10-13 19:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545066
;

-- 2021-10-13T16:22:38.651508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-13 19:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545066
;

-- 2021-10-13T16:22:43.304816700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Kann das Produkt nicht ändern. Wir haben Rechnungen für diesen Vertrag.',Updated=TO_TIMESTAMP('2021-10-13 19:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545066
;

