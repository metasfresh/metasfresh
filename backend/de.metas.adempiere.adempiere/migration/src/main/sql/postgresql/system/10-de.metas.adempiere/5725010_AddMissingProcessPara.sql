-- 2024-05-30T09:54:27.135Z
UPDATE AD_Element SET ColumnName='AsNewPrice',Updated=TO_TIMESTAMP('2024-05-30 12:54:27.135','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583127
;

-- 2024-05-30T09:54:27.580Z
UPDATE AD_Column SET ColumnName='AsNewPrice' WHERE AD_Element_ID=583127
;

-- 2024-05-30T09:54:27.581Z
UPDATE AD_Process_Para SET ColumnName='AsNewPrice' WHERE AD_Element_ID=583127
;

-- 2024-05-30T09:54:27.589Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583127,'de_DE')
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: AsNewPrice
-- 2024-05-30T09:55:24.314Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,583127,0,585382,542831,20,'AsNewPrice',TO_TIMESTAMP('2024-05-30 12:55:24.085','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,'Y','N','Y','N','Y','N','AsNewPrice',50,TO_TIMESTAMP('2024-05-30 12:55:24.085','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-30T09:55:24.317Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542831 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2024-05-30T09:55:24.320Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(583127)
;

-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- ParameterName: AsNewPrice
-- 2024-05-30T10:01:44.192Z
UPDATE AD_Process_Para SET DisplayLogic='@IsScalePrice@=''Y''',Updated=TO_TIMESTAMP('2024-05-30 13:01:44.192','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542831
;

