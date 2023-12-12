-- 2023-10-04T19:17:53.762Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,541420,0,584911,542707,11,'C_Flatrate_Data_ID',TO_TIMESTAMP('2023-10-04 20:17:53','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=SELECT COALESCE((SELECT C_Flatrate_Data_ID FROM C_Flatrate_Data WHERE C_BPartner_ID = @C_BPartner_ID/-1@), -1 )','1=2','de.metas.contracts',0,'Y','N','Y','N','N','N','Datenerfassung',30,TO_TIMESTAMP('2023-10-04 20:17:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-04T19:17:53.767Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542707 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-10-04T19:20:21.065Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584911,291,541430,TO_TIMESTAMP('2023-10-04 20:20:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-10-04 20:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

