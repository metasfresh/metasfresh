-- 2023-06-08T10:40:35.105Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582401,0,'FEC_RemainingAmount',TO_TIMESTAMP('2023-06-08 13:40:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Remaining FEC','Remaining FEC',TO_TIMESTAMP('2023-06-08 13:40:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-08T10:40:35.106Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582401 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- ParameterName: FEC_RemainingAmount
-- 2023-06-08T10:42:05.415Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582401,0,585186,542638,12,'FEC_RemainingAmount',TO_TIMESTAMP('2023-06-08 13:42:05','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','N','Y','N','N','N','Remaining FEC',30,TO_TIMESTAMP('2023-06-08 13:42:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-08T10:42:05.417Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542638 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-06-08T10:42:05.421Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582401)
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- ParameterName: FEC_RemainingAmount
-- 2023-06-08T10:42:14.913Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2023-06-08 13:42:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542638
;

-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- ParameterName: FEC_RemainingAmount
-- 2023-06-08T10:45:31.571Z
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-06-08 13:45:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542638
;



-- Process: C_Order_AllocateToForexContract(de.metas.forex.webui.process.C_Order_AllocateToForexContract)
-- ParameterName: Amount
-- 2023-06-08T08:46:32.033Z
UPDATE AD_Process_Para SET AD_Element_ID=2677, Description='Amount allocated to this document', Help=NULL, Name='Allocated Amount',Updated=TO_TIMESTAMP('2023-06-08 11:46:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542457
;

-- 2023-06-08T08:46:32.036Z
UPDATE AD_Process_Para_Trl trl SET Description='Amount allocated to this document',Help=NULL,Name='Allocated Amount' WHERE AD_Process_Para_ID=542457 AND AD_Language='en_US'
;

-- 2023-06-08T08:46:32.062Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(2677) 
;

-- Column: C_ForeignExchangeContract.FEC_ValidityDate
-- 2023-06-08T10:30:59.896Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=40,Updated=TO_TIMESTAMP('2023-06-08 13:30:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585546
;

-- Column: C_ForeignExchangeContract.FEC_MaturityDate
-- 2023-06-08T10:31:08.249Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=50,Updated=TO_TIMESTAMP('2023-06-08 13:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585547
;

