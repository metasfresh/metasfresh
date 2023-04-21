-- 2023-03-13T09:32:29.548Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582135,0,TO_TIMESTAMP('2023-03-13 10:32:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Amount (DC)','Amount (DC)',TO_TIMESTAMP('2023-03-13 10:32:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T09:32:29.552Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582135 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Amount (DC)
-- Column: SAP_GLJournalLine.Amount
-- 2023-03-13T09:32:35.179Z
UPDATE AD_Field SET AD_Name_ID=582135, Description=NULL, Help=NULL, Name='Amount (DC)',Updated=TO_TIMESTAMP('2023-03-13 10:32:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710038
;

-- 2023-03-13T09:32:35.181Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Amount (DC)' WHERE AD_Field_ID=710038 AND AD_Language='en_US'
;

-- 2023-03-13T09:32:35.206Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582135) 
;

-- 2023-03-13T09:32:35.218Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710038
;

-- 2023-03-13T09:32:35.221Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710038)
;

-- 2023-03-13T09:33:19.272Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582136,0,TO_TIMESTAMP('2023-03-13 10:33:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Amount (LC)','Amount (LC)',TO_TIMESTAMP('2023-03-13 10:33:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T09:33:19.274Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582136 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Amount (LC)
-- Column: SAP_GLJournalLine.AmtAcct
-- 2023-03-13T09:33:25.137Z
UPDATE AD_Field SET AD_Name_ID=582136, Description=NULL, Help=NULL, Name='Amount (LC)',Updated=TO_TIMESTAMP('2023-03-13 10:33:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710039
;

-- 2023-03-13T09:33:25.138Z
UPDATE AD_Field_Trl trl SET Description=NULL,Name='Amount (LC)' WHERE AD_Field_ID=710039 AND AD_Language='en_US'
;

-- 2023-03-13T09:33:25.142Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582136) 
;

-- 2023-03-13T09:33:25.148Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710039
;

-- 2023-03-13T09:33:25.150Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710039)
;

-- 2023-03-13T09:34:58.735Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582137,0,TO_TIMESTAMP('2023-03-13 10:34:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','GL Account','GL Account',TO_TIMESTAMP('2023-03-13 10:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T09:34:58.736Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582137 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> GL Account
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2023-03-13T09:35:04.097Z
UPDATE AD_Field SET AD_Name_ID=582137, Description=NULL, Help=NULL, Name='GL Account',Updated=TO_TIMESTAMP('2023-03-13 10:35:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710036
;

-- 2023-03-13T09:35:04.098Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='GL Account' WHERE AD_Field_ID=710036 AND AD_Language='en_US'
;

-- 2023-03-13T09:35:04.100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582137) 
;

-- 2023-03-13T09:35:04.103Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710036
;

-- 2023-03-13T09:35:04.103Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710036)
;

-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2023-03-13T10:34:09.973Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-03-13 11:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585398
;

-- 2023-03-14T10:27:13.937Z
UPDATE AD_Element SET ColumnName='GL_Account_ID',Updated=TO_TIMESTAMP('2023-03-14 11:27:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582137
;

-- 2023-03-14T10:27:13.938Z
UPDATE AD_Column SET ColumnName='GL_Account_ID' WHERE AD_Element_ID=582137
;

-- 2023-03-14T10:27:13.938Z
UPDATE AD_Process_Para SET ColumnName='GL_Account_ID' WHERE AD_Element_ID=582137
;

-- 2023-03-14T10:27:13.940Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582137,'en_US')
;

