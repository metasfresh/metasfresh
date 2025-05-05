-- 2023-11-21T11:55:38.104Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577762,0,541065,542746,15,'Date',TO_TIMESTAMP('2023-11-21 12:55:38','YYYY-MM-DD HH24:MI:SS'),100,'EE01',0,'Y','N','Y','N','N','N','Datum',10,TO_TIMESTAMP('2023-11-21 12:55:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-21T11:55:38.109Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542746 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-11-21T11:55:50.233Z
UPDATE AD_Process SET SQLStatement='select * from PP_Product_BOM_Recursive_Report(@PP_Product_BOM_ID@,''@Date@'')',Updated=TO_TIMESTAMP('2023-11-21 12:55:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541065
;

