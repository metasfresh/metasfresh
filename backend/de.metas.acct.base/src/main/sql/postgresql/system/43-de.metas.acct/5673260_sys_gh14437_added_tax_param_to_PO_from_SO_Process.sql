-- Name: C_Tax (Manual category, purchase taxes only)
-- 2023-01-24T11:29:02.945Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540618,'SoPoType IN (''P'', ''B'') AND EXISTS(SELECT 1 FROM C_TaxCategory tc WHERE isManualTax = ''Y'' AND tc.C_TaxCategory_ID = C_Tax.C_TaxCategory_ID)',TO_TIMESTAMP('2023-01-24 13:29:02','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','C_Tax (Manual category, purchase taxes only)','S',TO_TIMESTAMP('2023-01-24 13:29:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Process: C_Order_CreatePOFromSOs(de.metas.order.process.C_Order_CreatePOFromSOs)
-- ParameterName: p_C_Tax_ID
-- 2023-01-24T11:32:10.903Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,Description,DisplayLogic,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,213,0,193,542464,30,158,540618,'C_Tax_ID',TO_TIMESTAMP('2023-01-24 13:32:10','YYYY-MM-DD HH24:MI:SS'),100,'Steuerart','0=1','de.metas.order',0,'Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','Y','N','N','N','Steuer',110,TO_TIMESTAMP('2023-01-24 13:32:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-24T11:32:10.904Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542464 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

