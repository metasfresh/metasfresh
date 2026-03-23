-- 2023-10-04T14:04:06.644Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541420,0,584926,542705,11,'C_Flatrate_Data_ID',TO_TIMESTAMP('2023-10-04 15:04:06','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT C_Flatrate_Data_ID FROM C_Flatrate_Data WHERE C_BPartner_ID=@C_BPartner_ID/-1@','de.metas.contracts',0,'Y','N','Y','N','N','N','Datenerfassung',40,TO_TIMESTAMP('2023-10-04 15:04:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-04T14:04:06.648Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542705 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-04T15:03:23.897Z
UPDATE AD_Process_Para SET DefaultValue='@SQL=SELECT COALESCE((SELECT C_Flatrate_Data_ID FROM C_Flatrate_Data WHERE C_BPartner_ID = @C_BPartner_ID/-1@), -1 )',Updated=TO_TIMESTAMP('2023-10-04 16:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542705
;

-- 2023-10-04T15:23:14.601Z
UPDATE AD_Process_Para SET DisplayLogic='1=2', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2023-10-04 16:23:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542705
;

-- 2023-10-04T14:14:05.411Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584926,291,541428,TO_TIMESTAMP('2023-10-04 15:14:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-10-04 15:14:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

