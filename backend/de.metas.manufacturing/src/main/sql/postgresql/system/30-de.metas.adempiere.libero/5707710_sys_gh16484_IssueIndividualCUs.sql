-- 2023-10-19T08:34:04.392Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582770,0,'NumberOfCUs',TO_TIMESTAMP('2023-10-19 11:34:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Count CUs','Count CUs',TO_TIMESTAMP('2023-10-19 11:34:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T08:34:04.402Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582770 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-10-19T08:34:38.679Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582771,0,'IsReceiveIndividualCUs',TO_TIMESTAMP('2023-10-19 11:34:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Receive Individual CUs','Receive Individual CUs',TO_TIMESTAMP('2023-10-19 11:34:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T08:34:38.682Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582771 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-10-19T08:35:59.808Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,582770,0,540773,542723,11,'NumberOfCUs',TO_TIMESTAMP('2023-10-19 11:35:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','N','N','Count CUs','@IsReceiveIndividualCUs/''N''@=''Y''',60,TO_TIMESTAMP('2023-10-19 11:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T08:35:59.811Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542723 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-19T08:36:38.295Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,582771,0,540773,542724,20,'IsReceiveIndividualCUs',TO_TIMESTAMP('2023-10-19 11:36:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits',0,'Y','N','Y','N','N','N','Receive Individual CUs','1=2',70,TO_TIMESTAMP('2023-10-19 11:36:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-19T08:36:38.296Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542724 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-19T08:37:41.138Z
UPDATE AD_Process_Para SET DisplayLogic='@M_HU_PI_Item_ID/-1@ > 0 & @IsReceiveIndividualCUs/''N''@=''Y''',Updated=TO_TIMESTAMP('2023-10-19 11:37:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541175
;

-- 2023-10-19T08:37:58.398Z
UPDATE AD_Process_Para SET DisplayLogic='@M_HU_PI_Item_Product_ID/-1@>0 & @M_HU_PI_Item_Product_ID/-1@!101 & @IsReceiveIndividualCUs/''N''@=''N''',Updated=TO_TIMESTAMP('2023-10-19 11:37:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541174
;

-- 2023-10-19T08:38:07.115Z
UPDATE AD_Process_Para SET DisplayLogic='@IsReceiveIndividualCUs/''N''@=''N''',Updated=TO_TIMESTAMP('2023-10-19 11:38:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541173
;

-- 2023-10-19T08:38:15.618Z
UPDATE AD_Process_Para SET DisplayLogic='@M_HU_PI_Item_Product_ID/-1@!101 & @IsReceiveIndividualCUs/''N''@=''N''',Updated=TO_TIMESTAMP('2023-10-19 11:38:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541172
;

-- 2023-10-19T08:38:22.650Z
UPDATE AD_Process_Para SET DisplayLogic='@IsReceiveIndividualCUs/''N''@=''N''',Updated=TO_TIMESTAMP('2023-10-19 11:38:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541171
;

-- 2023-10-19T08:38:54.010Z
UPDATE AD_Process_Para SET DisplayLogic='@M_HU_PI_Item_ID/-1@ > 0 & @IsReceiveIndividualCUs/''N''@=''N''',Updated=TO_TIMESTAMP('2023-10-19 11:38:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541175
;

-- 2023-10-19T08:39:01.817Z
UPDATE AD_Process_Para SET DisplayLogic='@IsReceiveIndividualCUs/''N''@=''Y''', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2023-10-19 11:39:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542723
;

-- 2023-10-19T08:39:16.573Z
UPDATE AD_Process_Para SET DisplayLogic='1=2', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2023-10-19 11:39:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542724
;

-- 2023-10-19T10:05:59.334Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545353,0,TO_TIMESTAMP('2023-10-19 13:05:58','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','When Serial No. Sequence is set, the unit of measurement must be EACH.','E',TO_TIMESTAMP('2023-10-19 13:05:58','YYYY-MM-DD HH24:MI:SS'),100,'org.eevolution.model.validator.UOM_ID_MUST_BE_EACH_IF_SEQ_NO_IS_SET')
;

-- 2023-10-19T10:05:59.343Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545353 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-10-19T10:06:50.510Z
UPDATE AD_Message_Trl SET MsgText='Wenn die Seriennummerfolge eingestellt ist, muss die Maßeinheit "Stück" sein.',Updated=TO_TIMESTAMP('2023-10-19 13:06:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545353
;

-- 2023-10-19T10:06:53.598Z
UPDATE AD_Message_Trl SET MsgText='Wenn die Seriennummerfolge eingestellt ist, muss die Maßeinheit "Stück" sein.',Updated=TO_TIMESTAMP('2023-10-19 13:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545353
;

-- 2023-10-19T10:06:56.331Z
UPDATE AD_Message_Trl SET MsgText='Wenn die Seriennummerfolge eingestellt ist, muss die Maßeinheit "Stück" sein.',Updated=TO_TIMESTAMP('2023-10-19 13:06:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545353
;

-- 2023-10-19T10:07:00.693Z
UPDATE AD_Message SET MsgText='Wenn die Seriennummerfolge eingestellt ist, muss die Maßeinheit "Stück" sein.',Updated=TO_TIMESTAMP('2023-10-19 13:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545353
;

-- 2023-10-19T10:07:06.217Z
UPDATE AD_Message_Trl SET MsgText='Wenn die Seriennummerfolge eingestellt ist, muss die Maßeinheit "Stück" sein.',Updated=TO_TIMESTAMP('2023-10-19 13:07:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545353
;

