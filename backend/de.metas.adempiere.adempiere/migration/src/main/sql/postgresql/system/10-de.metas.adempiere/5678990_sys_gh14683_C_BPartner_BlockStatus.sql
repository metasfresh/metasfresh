-- 2023-02-20T10:25:17.397Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582088,0,'IsBlocked',TO_TIMESTAMP('2023-02-20 12:25:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Blocked','Blocked',TO_TIMESTAMP('2023-02-20 12:25:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-20T10:25:17.407Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582088 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Blocked
-- 2023-02-20T10:25:25.802Z
UPDATE AD_Element_Trl SET Name='Gesperrt', PrintName='Gesperrt',Updated=TO_TIMESTAMP('2023-02-20 12:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='de_CH'
;

-- 2023-02-20T10:25:25.829Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'de_CH') 
;

-- Element: Blocked
-- 2023-02-20T10:25:28.659Z
UPDATE AD_Element_Trl SET Name='Gesperrt', PrintName='Gesperrt',Updated=TO_TIMESTAMP('2023-02-20 12:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='de_DE'
;

-- 2023-02-20T10:25:28.664Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'de_DE') 
;

-- Element: Blocked
-- 2023-02-20T10:25:32.069Z
UPDATE AD_Element_Trl SET Name='Gesperrt', PrintName='Gesperrt',Updated=TO_TIMESTAMP('2023-02-20 12:25:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='fr_CH'
;

-- 2023-02-20T10:25:32.074Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'fr_CH') 
;

-- Element: Blocked
-- 2023-02-20T10:25:35.231Z
UPDATE AD_Element_Trl SET Name='Gesperrt', PrintName='Gesperrt',Updated=TO_TIMESTAMP('2023-02-20 12:25:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='nl_NL'
;

-- 2023-02-20T10:25:35.233Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'nl_NL') 
;

-- Element: Blocked
-- 2023-02-20T10:31:09.876Z
UPDATE AD_Element_Trl SET Description='Der Datensatz ist im System gesperrt',Updated=TO_TIMESTAMP('2023-02-20 12:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='de_CH'
;

-- 2023-02-20T10:31:09.886Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'de_CH') 
;

-- Element: Blocked
-- 2023-02-20T10:31:12.016Z
UPDATE AD_Element_Trl SET Description='Der Datensatz ist im System gesperrt',Updated=TO_TIMESTAMP('2023-02-20 12:31:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='de_DE'
;

-- 2023-02-20T10:31:12.018Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'de_DE') 
;

-- Element: Blocked
-- 2023-02-20T10:31:15.382Z
UPDATE AD_Element_Trl SET Description='Der Datensatz ist im System gesperrt',Updated=TO_TIMESTAMP('2023-02-20 12:31:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='fr_CH'
;

-- 2023-02-20T10:31:15.384Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'fr_CH') 
;

-- Element: Blocked
-- 2023-02-20T10:31:17.002Z
UPDATE AD_Element_Trl SET Description='Der Datensatz ist im System gesperrt',Updated=TO_TIMESTAMP('2023-02-20 12:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='nl_NL'
;

-- 2023-02-20T10:31:17.005Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'nl_NL') 
;

-- Element: Blocked
-- 2023-02-20T10:31:23.455Z
UPDATE AD_Element_Trl SET Description='The record is blocked in the system',Updated=TO_TIMESTAMP('2023-02-20 12:31:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582088 AND AD_Language='en_US'
;

-- 2023-02-20T10:31:23.457Z
UPDATE AD_Element SET Description='The record is blocked in the system' WHERE AD_Element_ID=582088
;

-- 2023-02-20T10:31:23.927Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582088,'en_US') 
;

-- 2023-02-20T10:31:23.928Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582088,'en_US') 
;

-- 2023-02-20T11:12:35.288Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582089,0,'BlockStatus',TO_TIMESTAMP('2023-02-20 13:12:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','BlockStatus','BlockStatus',TO_TIMESTAMP('2023-02-20 13:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-20T11:12:35.305Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582089 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: BlockStatus
-- 2023-02-20T11:12:57.237Z
UPDATE AD_Element_Trl SET Name='Sperrstatus', PrintName='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-20 13:12:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582089 AND AD_Language='nl_NL'
;

-- 2023-02-20T11:12:57.242Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582089,'nl_NL') 
;

-- Element: BlockStatus
-- 2023-02-20T11:12:59.988Z
UPDATE AD_Element_Trl SET Name='Sperrstatus', PrintName='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-20 13:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582089 AND AD_Language='fr_CH'
;

-- 2023-02-20T11:12:59.990Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582089,'fr_CH') 
;

-- Element: BlockStatus
-- 2023-02-20T11:13:06.612Z
UPDATE AD_Element_Trl SET Name='Block status', PrintName='Block status',Updated=TO_TIMESTAMP('2023-02-20 13:13:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582089 AND AD_Language='en_US'
;

-- 2023-02-20T11:13:06.614Z
UPDATE AD_Element SET Name='Block status', PrintName='Block status' WHERE AD_Element_ID=582089
;

-- 2023-02-20T11:13:07.503Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582089,'en_US') 
;

-- 2023-02-20T11:13:07.504Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582089,'en_US') 
;

-- Element: BlockStatus
-- 2023-02-20T11:13:12.772Z
UPDATE AD_Element_Trl SET Name='Sperrstatus', PrintName='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-20 13:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582089 AND AD_Language='de_DE'
;

-- 2023-02-20T11:13:12.776Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582089,'de_DE') 
;

-- Element: BlockStatus
-- 2023-02-20T11:13:15.613Z
UPDATE AD_Element_Trl SET Name='Sperrstatus', PrintName='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-20 13:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582089 AND AD_Language='de_CH'
;

-- 2023-02-20T11:13:15.615Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582089,'de_CH') 
;

-- Table: C_BPartner_BlockStatus
-- 2023-02-20T18:54:25.608Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542315,'N',TO_TIMESTAMP('2023-02-20 20:54:25','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'BPartner Block Status','NP','L','C_BPartner_BlockStatus','DTI',TO_TIMESTAMP('2023-02-20 20:54:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:25.613Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542315 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-20T18:54:25.755Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556250,TO_TIMESTAMP('2023-02-20 20:54:25','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_BPartner_BlockStatus',1,'Y','N','Y','Y','C_BPartner_BlockStatus','N',1000000,TO_TIMESTAMP('2023-02-20 20:54:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-20T18:54:25.785Z
CREATE SEQUENCE C_BPARTNER_BLOCKSTATUS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_BPartner_BlockStatus.AD_Client_ID
-- 2023-02-20T18:54:33.679Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586192,102,0,19,542315,'AD_Client_ID',TO_TIMESTAMP('2023-02-20 20:54:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-02-20 20:54:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:33.687Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586192 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:54:33.748Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_BPartner_BlockStatus.AD_Org_ID
-- 2023-02-20T18:54:34.580Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586193,113,0,30,542315,'AD_Org_ID',TO_TIMESTAMP('2023-02-20 20:54:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-02-20 20:54:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:34.583Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:54:34.588Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_BPartner_BlockStatus.Created
-- 2023-02-20T18:54:35.590Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586194,245,0,16,542315,'Created',TO_TIMESTAMP('2023-02-20 20:54:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-02-20 20:54:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:35.591Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586194 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:54:35.593Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_BPartner_BlockStatus.CreatedBy
-- 2023-02-20T18:54:36.306Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586195,246,0,18,110,542315,'CreatedBy',TO_TIMESTAMP('2023-02-20 20:54:36','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-02-20 20:54:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:36.308Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586195 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:54:36.311Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_BPartner_BlockStatus.IsActive
-- 2023-02-20T18:54:36.993Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586196,348,0,20,542315,'IsActive',TO_TIMESTAMP('2023-02-20 20:54:36','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-02-20 20:54:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:36.995Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586196 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:54:36.998Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_BPartner_BlockStatus.Updated
-- 2023-02-20T18:54:37.658Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586197,607,0,16,542315,'Updated',TO_TIMESTAMP('2023-02-20 20:54:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-02-20 20:54:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:37.660Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586197 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:54:37.664Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_BPartner_BlockStatus.UpdatedBy
-- 2023-02-20T18:54:38.439Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586198,608,0,18,110,542315,'UpdatedBy',TO_TIMESTAMP('2023-02-20 20:54:38','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-02-20 20:54:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:38.441Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586198 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:54:38.443Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-02-20T18:54:39.001Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582091,0,'C_BPartner_BlockStatus_ID',TO_TIMESTAMP('2023-02-20 20:54:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','BPartner Block Status','BPartner Block Status',TO_TIMESTAMP('2023-02-20 20:54:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-20T18:54:39.006Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582091 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_BPartner_BlockStatus.C_BPartner_BlockStatus_ID
-- 2023-02-20T18:54:39.678Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586199,582091,0,13,542315,'C_BPartner_BlockStatus_ID',TO_TIMESTAMP('2023-02-20 20:54:38','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','BPartner Block Status',0,0,TO_TIMESTAMP('2023-02-20 20:54:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:54:39.681Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586199 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:54:39.685Z
/* DDL */  select update_Column_Translation_From_AD_Element(582091) 
;

-- Name: BlockStatus
-- 2023-02-20T18:56:07.838Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541720,TO_TIMESTAMP('2023-02-20 20:56:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','BlockStatus',TO_TIMESTAMP('2023-02-20 20:56:07','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-02-20T18:56:07.843Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541720 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: BlockStatus
-- Value: B
-- ValueName: Blocked
-- 2023-02-20T18:56:36.108Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543410,541720,TO_TIMESTAMP('2023-02-20 20:56:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Blocked',TO_TIMESTAMP('2023-02-20 20:56:35','YYYY-MM-DD HH24:MI:SS'),100,'B','Blocked')
;

-- 2023-02-20T18:56:36.110Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543410 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: BlockStatus
-- Value: UB
-- ValueName: Unblocked
-- 2023-02-20T18:56:46.831Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543411,541720,TO_TIMESTAMP('2023-02-20 20:56:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Unblocked',TO_TIMESTAMP('2023-02-20 20:56:46','YYYY-MM-DD HH24:MI:SS'),100,'UB','Unblocked')
;

-- 2023-02-20T18:56:46.832Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543411 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: BlockStatus -> UB_Unblocked
-- 2023-02-20T18:57:22.152Z
UPDATE AD_Ref_List_Trl SET Name='Entsperrt',Updated=TO_TIMESTAMP('2023-02-20 20:57:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543411
;

-- Reference Item: BlockStatus -> UB_Unblocked
-- 2023-02-20T18:57:24.052Z
UPDATE AD_Ref_List_Trl SET Name='Entsperrt',Updated=TO_TIMESTAMP('2023-02-20 20:57:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543411
;

-- Reference Item: BlockStatus -> UB_Unblocked
-- 2023-02-20T18:57:27.177Z
UPDATE AD_Ref_List_Trl SET Name='Entsperrt',Updated=TO_TIMESTAMP('2023-02-20 20:57:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543411
;

-- Reference Item: BlockStatus -> UB_Unblocked
-- 2023-02-20T18:57:29.341Z
UPDATE AD_Ref_List_Trl SET Name='Entsperrt',Updated=TO_TIMESTAMP('2023-02-20 20:57:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543411
;

-- Reference Item: BlockStatus -> B_Blocked
-- 2023-02-20T18:57:40.393Z
UPDATE AD_Ref_List_Trl SET Name='Gesperrt',Updated=TO_TIMESTAMP('2023-02-20 20:57:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543410
;

-- Reference Item: BlockStatus -> B_Blocked
-- 2023-02-20T18:57:42.112Z
UPDATE AD_Ref_List_Trl SET Name='Gesperrt',Updated=TO_TIMESTAMP('2023-02-20 20:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543410
;

-- Reference Item: BlockStatus -> B_Blocked
-- 2023-02-20T18:57:45.681Z
UPDATE AD_Ref_List_Trl SET Name='Gesperrt',Updated=TO_TIMESTAMP('2023-02-20 20:57:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543410
;

-- Reference Item: BlockStatus -> B_Blocked
-- 2023-02-20T18:57:47.426Z
UPDATE AD_Ref_List_Trl SET Name='Gesperrt',Updated=TO_TIMESTAMP('2023-02-20 20:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543410
;

-- Column: C_BPartner_BlockStatus.BlockStatus
-- 2023-02-20T18:58:41.296Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586200,582089,0,17,541720,542315,'BlockStatus',TO_TIMESTAMP('2023-02-20 20:58:41','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Block status',0,0,TO_TIMESTAMP('2023-02-20 20:58:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:58:41.301Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586200 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:58:41.307Z
/* DDL */  select update_Column_Translation_From_AD_Element(582089) 
;

-- 2023-02-20T18:58:43.752Z
/* DDL */ CREATE TABLE public.C_BPartner_BlockStatus (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, BlockStatus VARCHAR(10) NOT NULL, C_BPartner_BlockStatus_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_BPartner_BlockStatus_Key PRIMARY KEY (C_BPartner_BlockStatus_ID))
;

-- Column: C_BPartner_BlockStatus.Reason
-- 2023-02-20T18:59:27.405Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586201,576788,0,14,542315,'Reason',TO_TIMESTAMP('2023-02-20 20:59:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reason',0,0,TO_TIMESTAMP('2023-02-20 20:59:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T18:59:27.407Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586201 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T18:59:27.411Z
/* DDL */  select update_Column_Translation_From_AD_Element(576788) 
;

-- 2023-02-20T18:59:29.150Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_BlockStatus','ALTER TABLE public.C_BPartner_BlockStatus ADD COLUMN Reason VARCHAR(1000)')
;

-- Column: C_BPartner_BlockStatus.C_BPartner_ID
-- 2023-02-20T19:00:03.418Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586202,187,0,19,542315,'C_BPartner_ID',TO_TIMESTAMP('2023-02-20 21:00:03','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Business Partner',0,0,TO_TIMESTAMP('2023-02-20 21:00:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T19:00:03.420Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586202 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T19:00:03.424Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2023-02-20T19:00:05.202Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_BlockStatus','ALTER TABLE public.C_BPartner_BlockStatus ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2023-02-20T19:00:05.223Z
ALTER TABLE C_BPartner_BlockStatus ADD CONSTRAINT CBPartner_CBPartnerBlockStatus FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2023-02-20T19:00:11.192Z
INSERT INTO t_alter_column values('c_bpartner_blockstatus','Reason','VARCHAR(1000)',null,null)
;

-- 2023-02-20T19:00:53.903Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540722,0,542315,TO_TIMESTAMP('2023-02-20 21:00:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','IDX_Partner_CreatedAt','N',TO_TIMESTAMP('2023-02-20 21:00:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-20T19:00:53.911Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540722 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-02-20T19:01:06.726Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586202,541304,540722,0,TO_TIMESTAMP('2023-02-20 21:01:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-02-20 21:01:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-20T19:01:15.553Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586194,541305,540722,0,TO_TIMESTAMP('2023-02-20 21:01:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2023-02-20 21:01:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_BPartner.IsBlocked
-- 2023-02-20T19:09:23.139Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586203,582088,0,20,291,'IsBlocked','CASE WHEN (select BlockStatus from C_BPartner_BlockStatus where C_BPartner_ID = C_BPartner.C_BPartner_ID and isCurrent = ''Y'' limit 1) = ''B'' THEN ''Y'' ELSE ''N'' END',TO_TIMESTAMP('2023-02-20 21:09:22','YYYY-MM-DD HH24:MI:SS'),100,'N','','The record is blocked in the system','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Blocked','1=1',0,0,TO_TIMESTAMP('2023-02-20 21:09:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-20T19:09:23.140Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586203 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-20T19:09:23.143Z
/* DDL */  select update_Column_Translation_From_AD_Element(582088) 
;

-- Value: C_BPartner_UpdateBlockStatus
-- Classname: de.metas.bpartner.process.C_BPartner_UpdateBlockStatus
-- 2023-02-21T09:00:00.083Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585225,'Y','de.metas.bpartner.process.C_BPartner_UpdateBlockStatus','N',TO_TIMESTAMP('2023-02-21 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','N','Y',0,'Update BPartner Block Status','json','N','N','xls','Java',TO_TIMESTAMP('2023-02-21 10:59:59','YYYY-MM-DD HH24:MI:SS'),100,'C_BPartner_UpdateBlockStatus')
;

-- 2023-02-21T09:00:00.087Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585225 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Value: C_BPartner_UpdateBlockStatus
-- Classname: de.metas.bpartner.process.C_BPartner_UpdateBlockStatus
-- 2023-02-21T09:00:49.756Z
UPDATE AD_Process SET Name='Update Block Status',Updated=TO_TIMESTAMP('2023-02-21 11:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585225
;

-- 2023-02-21T09:00:49.758Z
UPDATE AD_Process_Trl trl SET Name='Update Block Status' WHERE AD_Process_ID=585225 AND AD_Language='en_US'
;

-- Process: C_BPartner_UpdateBlockStatus(de.metas.bpartner.process.C_BPartner_UpdateBlockStatus)
-- 2023-02-21T09:00:57.964Z
UPDATE AD_Process_Trl SET Name='Sperrstatus aktualisieren',Updated=TO_TIMESTAMP('2023-02-21 11:00:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585225
;

-- Process: C_BPartner_UpdateBlockStatus(de.metas.bpartner.process.C_BPartner_UpdateBlockStatus)
-- 2023-02-21T09:00:59.939Z
UPDATE AD_Process_Trl SET Name='Sperrstatus aktualisieren',Updated=TO_TIMESTAMP('2023-02-21 11:00:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585225
;

-- Process: C_BPartner_UpdateBlockStatus(de.metas.bpartner.process.C_BPartner_UpdateBlockStatus)
-- 2023-02-21T09:01:03.908Z
UPDATE AD_Process_Trl SET Name='Sperrstatus aktualisieren',Updated=TO_TIMESTAMP('2023-02-21 11:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Process_ID=585225
;

-- Process: C_BPartner_UpdateBlockStatus(de.metas.bpartner.process.C_BPartner_UpdateBlockStatus)
-- 2023-02-21T09:01:05.476Z
UPDATE AD_Process_Trl SET Name='Sperrstatus aktualisieren',Updated=TO_TIMESTAMP('2023-02-21 11:01:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=585225
;

-- Process: C_BPartner_UpdateBlockStatus(de.metas.bpartner.process.C_BPartner_UpdateBlockStatus)
-- ParameterName: BlockStatus
-- 2023-02-21T09:02:44.987Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,AD_Reference_Value_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582089,0,585225,542556,17,541720,'BlockStatus',TO_TIMESTAMP('2023-02-21 11:02:44','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','Y','N','Y','N','Block status',10,TO_TIMESTAMP('2023-02-21 11:02:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:02:44.995Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542556 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-02-21T09:02:45.056Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582089) 
;

-- Process: C_BPartner_UpdateBlockStatus(de.metas.bpartner.process.C_BPartner_UpdateBlockStatus)
-- ParameterName: Reason
-- 2023-02-21T09:03:36.924Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,576788,0,585225,542557,14,'Reason',TO_TIMESTAMP('2023-02-21 11:03:36','YYYY-MM-DD HH24:MI:SS'),100,'D',1000,'Y','N','Y','N','N','N','Reason',20,TO_TIMESTAMP('2023-02-21 11:03:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:03:36.926Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542557 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-02-21T09:03:36.927Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(576788) 
;

-- Process: C_BPartner_UpdateBlockStatus(de.metas.bpartner.process.C_BPartner_UpdateBlockStatus)
-- Table: C_BPartner
-- EntityType: D
-- 2023-02-21T09:04:08.859Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585225,291,541363,TO_TIMESTAMP('2023-02-21 11:04:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',TO_TIMESTAMP('2023-02-21 11:04:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N')
;

-- Field: Business Partner_OLD(123,D) -> Business Partner(220,D) -> Blocked
-- Column: C_BPartner.IsBlocked
-- 2023-02-21T09:06:09.958Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586203,712678,0,220,TO_TIMESTAMP('2023-02-21 11:06:09','YYYY-MM-DD HH24:MI:SS'),100,'The record is blocked in the system',1,'D','Y','N','N','N','N','N','N','N','Blocked',TO_TIMESTAMP('2023-02-21 11:06:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:06:09.961Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712678 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:06:09.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582088) 
;

-- 2023-02-21T09:06:09.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712678
;

-- 2023-02-21T09:06:09.996Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712678)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner(220,D) -> main -> 20 -> tax.Blocked
-- Column: C_BPartner.IsBlocked
-- 2023-02-21T09:07:11.565Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712678,0,220,615889,1000011,'F',TO_TIMESTAMP('2023-02-21 11:07:11','YYYY-MM-DD HH24:MI:SS'),100,'The record is blocked in the system','Y','N','N','Y','N','N','N',0,'Blocked',15,0,0,TO_TIMESTAMP('2023-02-21 11:07:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Business Partner_OLD(123,D) -> BPartner Block Status
-- Table: C_BPartner_BlockStatus
-- 2023-02-21T09:23:49.212Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582091,0,546839,542315,123,'Y',TO_TIMESTAMP('2023-02-21 11:23:47','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_BPartner_BlockStatus','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'BPartner Block Status','N',290,1,TO_TIMESTAMP('2023-02-21 11:23:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:23:49.281Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546839 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-21T09:23:49.287Z
/* DDL */  select update_tab_translation_from_ad_element(582091) 
;

-- 2023-02-21T09:23:49.296Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546839)
;

-- Tab: Business Partner_OLD(123,D) -> BPartner Block Status
-- Table: C_BPartner_BlockStatus
-- 2023-02-21T09:24:08.578Z
UPDATE AD_Tab SET SeqNo=190,Updated=TO_TIMESTAMP('2023-02-21 11:24:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546839
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Client
-- Column: C_BPartner_BlockStatus.AD_Client_ID
-- 2023-02-21T09:24:12.592Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586192,712679,0,546839,TO_TIMESTAMP('2023-02-21 11:24:12','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-21 11:24:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:12.595Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712679 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:12.601Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-21T09:24:13.058Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712679
;

-- 2023-02-21T09:24:13.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712679)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Organisation
-- Column: C_BPartner_BlockStatus.AD_Org_ID
-- 2023-02-21T09:24:13.164Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586193,712680,0,546839,TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:13.166Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712680 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:13.168Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-21T09:24:13.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712680
;

-- 2023-02-21T09:24:13.443Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712680)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Active
-- Column: C_BPartner_BlockStatus.IsActive
-- 2023-02-21T09:24:13.535Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586196,712681,0,546839,TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:13.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712681 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:13.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-21T09:24:13.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712681
;

-- 2023-02-21T09:24:13.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712681)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> BPartner Block Status
-- Column: C_BPartner_BlockStatus.C_BPartner_BlockStatus_ID
-- 2023-02-21T09:24:13.827Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586199,712682,0,546839,TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','BPartner Block Status',TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:13.828Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712682 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:13.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582091) 
;

-- 2023-02-21T09:24:13.834Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712682
;

-- 2023-02-21T09:24:13.835Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712682)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Block status
-- Column: C_BPartner_BlockStatus.BlockStatus
-- 2023-02-21T09:24:13.930Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586200,712683,0,546839,TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Block status',TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:13.931Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712683 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:13.933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582089) 
;

-- 2023-02-21T09:24:13.936Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712683
;

-- 2023-02-21T09:24:13.936Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712683)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Reason
-- Column: C_BPartner_BlockStatus.Reason
-- 2023-02-21T09:24:14.034Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586201,712684,0,546839,TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','N','N','N','N','N','N','N','Reason',TO_TIMESTAMP('2023-02-21 11:24:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:14.035Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712684 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:14.037Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576788) 
;

-- 2023-02-21T09:24:14.052Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712684
;

-- 2023-02-21T09:24:14.052Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712684)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Business Partner
-- Column: C_BPartner_BlockStatus.C_BPartner_ID
-- 2023-02-21T09:24:14.149Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586202,712685,0,546839,TO_TIMESTAMP('2023-02-21 11:24:14','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-02-21 11:24:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:14.151Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712685 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:14.152Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-21T09:24:14.197Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712685
;

-- 2023-02-21T09:24:14.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712685)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Created
-- Column: C_BPartner_BlockStatus.Created
-- 2023-02-21T09:24:31.398Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586194,712686,0,546839,TO_TIMESTAMP('2023-02-21 11:24:31','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created',29,'D','The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','Created',TO_TIMESTAMP('2023-02-21 11:24:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:31.400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712686 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:31.401Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-02-21T09:24:31.508Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712686
;

-- 2023-02-21T09:24:31.509Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712686)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Created By
-- Column: C_BPartner_BlockStatus.CreatedBy
-- 2023-02-21T09:24:36.025Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586195,712687,0,546839,TO_TIMESTAMP('2023-02-21 11:24:35','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records',10,'D','The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','Created By',TO_TIMESTAMP('2023-02-21 11:24:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T09:24:36.027Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712687 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T09:24:36.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-02-21T09:24:36.165Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712687
;

-- 2023-02-21T09:24:36.166Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712687)
;

-- Tab: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D)
-- UI Section: main
-- 2023-02-21T09:24:45.089Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546839,545459,TO_TIMESTAMP('2023-02-21 11:24:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-21 11:24:44','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-21T09:24:45.093Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545459 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> main
-- UI Column: 10
-- 2023-02-21T09:24:50.370Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546667,545459,TO_TIMESTAMP('2023-02-21 11:24:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-21 11:24:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> main -> 10
-- UI Element Group: main
-- 2023-02-21T09:24:57.795Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546667,550408,TO_TIMESTAMP('2023-02-21 11:24:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-02-21 11:24:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> main -> 10 -> main.Block status
-- Column: C_BPartner_BlockStatus.BlockStatus
-- 2023-02-21T09:25:38.954Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712683,0,546839,615890,550408,'F',TO_TIMESTAMP('2023-02-21 11:25:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Block status',10,0,0,TO_TIMESTAMP('2023-02-21 11:25:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> main -> 10 -> main.Reason
-- Column: C_BPartner_BlockStatus.Reason
-- 2023-02-21T09:26:01.706Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712684,0,546839,615891,550408,'F',TO_TIMESTAMP('2023-02-21 11:26:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Reason',20,0,0,TO_TIMESTAMP('2023-02-21 11:26:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> main -> 10 -> main.Created By
-- Column: C_BPartner.CreatedBy
-- 2023-02-21T09:27:37.451Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,545309,0,546839,615892,550408,'F',TO_TIMESTAMP('2023-02-21 11:27:37','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records','The Created By field indicates the user who created this record.','Y','N','N','Y','N','N','N',0,'Created By',30,0,0,TO_TIMESTAMP('2023-02-21 11:27:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> main -> 10 -> main.Created
-- Column: C_BPartner.Created
-- 2023-02-21T09:27:56.123Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,545308,0,546839,615893,550408,'F',TO_TIMESTAMP('2023-02-21 11:27:56','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created','The Created field indicates the date that this record was created.','Y','N','N','Y','N','N','N',0,'Created',40,0,0,TO_TIMESTAMP('2023-02-21 11:27:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Created
-- Column: C_BPartner_BlockStatus.Created
-- 2023-02-21T11:14:45.451Z
UPDATE AD_Field SET DisplayLength=7,Updated=TO_TIMESTAMP('2023-02-21 13:14:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712686
;

-- Field: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> Created By
-- Column: C_BPartner_BlockStatus.CreatedBy
-- 2023-02-21T11:14:56.638Z
UPDATE AD_Field SET DisplayLength=22,Updated=TO_TIMESTAMP('2023-02-21 13:14:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712687
;

-- UI Element: Business Partner_OLD(123,D) -> BPartner Block Status(546839,D) -> main -> 10 -> main.Created
-- Column: C_BPartner.Created
-- 2023-02-21T11:15:44.380Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-02-21 13:15:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615893
;

-- Value: CannotActivateBlockedPartner
-- 2023-02-21T11:22:55.223Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545238,0,TO_TIMESTAMP('2023-02-21 13:22:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gesperrte Partner müssen entsperrt werden, bevor sie aktiviert werden können!','E',TO_TIMESTAMP('2023-02-21 13:22:55','YYYY-MM-DD HH24:MI:SS'),100,'CannotActivateBlockedPartner')
;

-- 2023-02-21T11:22:55.239Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545238 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CannotActivateBlockedPartner
-- 2023-02-21T11:23:05.071Z
UPDATE AD_Message_Trl SET MsgText='Blocked partners must be unblocked before they can be activated!',Updated=TO_TIMESTAMP('2023-02-21 13:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545238
;

-- 2023-02-21T11:23:05.072Z
UPDATE AD_Message SET MsgText='Blocked partners must be unblocked before they can be activated!' WHERE AD_Message_ID=545238
;

-- Value: CannotCompleteOrderWithBlockedPartner
-- 2023-02-21T11:39:40.894Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545239,0,TO_TIMESTAMP('2023-02-21 13:39:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Auftrag kann nicht fertiggestellt werden, da der Partner gesperrt ist!','E',TO_TIMESTAMP('2023-02-21 13:39:40','YYYY-MM-DD HH24:MI:SS'),100,'CannotCompleteOrderWithBlockedPartner')
;

-- 2023-02-21T11:39:40.896Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545239 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CannotCompleteOrderWithBlockedPartner
-- 2023-02-21T11:39:48.460Z
UPDATE AD_Message_Trl SET MsgText='Order cannot be completed because the partner is blocked!',Updated=TO_TIMESTAMP('2023-02-21 13:39:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545239
;

-- 2023-02-21T11:39:48.460Z
UPDATE AD_Message SET MsgText='Order cannot be completed because the partner is blocked!' WHERE AD_Message_ID=545239
;

-- 2023-02-21T13:50:47.717Z
UPDATE AD_Table_Trl SET Name='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-21 15:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542315
;

-- 2023-02-21T13:50:49.486Z
UPDATE AD_Table_Trl SET Name='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-21 15:50:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=542315
;

-- 2023-02-21T13:50:51.683Z
UPDATE AD_Table_Trl SET Name='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-21 15:50:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542315
;

-- 2023-02-21T13:50:54.386Z
UPDATE AD_Table_Trl SET Name='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-21 15:50:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542315
;

-- 2023-02-21T13:51:00.251Z
UPDATE AD_Table_Trl SET Name='Block status',Updated=TO_TIMESTAMP('2023-02-21 15:51:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542315
;

-- 2023-02-21T13:51:00.252Z
UPDATE AD_Table SET Name='Block status' WHERE AD_Table_ID=542315
;

-- Element: C_BPartner_BlockStatus_ID
-- 2023-02-21T13:53:59.360Z
UPDATE AD_Element_Trl SET Name='Sperrstatus', PrintName='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-21 15:53:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582091 AND AD_Language='de_CH'
;

-- 2023-02-21T13:53:59.420Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582091,'de_CH') 
;

-- Element: C_BPartner_BlockStatus_ID
-- 2023-02-21T13:54:01.447Z
UPDATE AD_Element_Trl SET Name='Sperrstatus', PrintName='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-21 15:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582091 AND AD_Language='de_DE'
;

-- 2023-02-21T13:54:01.450Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582091,'de_DE') 
;

-- Element: C_BPartner_BlockStatus_ID
-- 2023-02-21T13:54:11.916Z
UPDATE AD_Element_Trl SET Name='Block status', PrintName='Block status',Updated=TO_TIMESTAMP('2023-02-21 15:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582091 AND AD_Language='en_US'
;

-- 2023-02-21T13:54:11.917Z
UPDATE AD_Element SET Name='Block status', PrintName='Block status' WHERE AD_Element_ID=582091
;

-- 2023-02-21T13:54:12.329Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582091,'en_US') 
;

-- 2023-02-21T13:54:12.331Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582091,'en_US') 
;

-- Element: C_BPartner_BlockStatus_ID
-- 2023-02-21T13:54:14.352Z
UPDATE AD_Element_Trl SET Name='Sperrstatus', PrintName='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-21 15:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582091 AND AD_Language='fr_CH'
;

-- 2023-02-21T13:54:14.355Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582091,'fr_CH') 
;

-- Element: C_BPartner_BlockStatus_ID
-- 2023-02-21T13:54:16.300Z
UPDATE AD_Element_Trl SET Name='Sperrstatus', PrintName='Sperrstatus',Updated=TO_TIMESTAMP('2023-02-21 15:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582091 AND AD_Language='nl_NL'
;

-- 2023-02-21T13:54:16.302Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582091,'nl_NL') 
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Block status
-- Column: C_BPartner_BlockStatus.BlockStatus
-- 2023-02-21T13:56:58.565Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-21 15:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615890
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Reason
-- Column: C_BPartner_BlockStatus.Reason
-- 2023-02-21T13:56:58.570Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-21 15:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615891
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Created
-- Column: C_BPartner.Created
-- 2023-02-21T13:56:58.574Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-21 15:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615893
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Created By
-- Column: C_BPartner.CreatedBy
-- 2023-02-21T13:56:58.578Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-21 15:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615892
;

-- 2023-02-21T14:00:42.612Z
INSERT INTO t_alter_column values('c_bpartner_blockstatus','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-02-21T14:00:47.260Z
INSERT INTO t_alter_column values('c_bpartner_blockstatus','CreatedBy','NUMERIC(10)',null,null)
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Created
-- Column: C_BPartner.Created
-- 2023-02-21T14:08:31.162Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615893
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Created By
-- Column: C_BPartner.CreatedBy
-- 2023-02-21T14:08:33.853Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=615892
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Created
-- Column: C_BPartner_BlockStatus.Created
-- 2023-02-21T14:10:20.396Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712686,0,546839,615900,550408,'F',TO_TIMESTAMP('2023-02-21 16:10:20','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created','The Created field indicates the date that this record was created.','Y','N','N','Y','N','N','N',0,'Created',30,0,0,TO_TIMESTAMP('2023-02-21 16:10:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Created By
-- Column: C_BPartner_BlockStatus.CreatedBy
-- 2023-02-21T14:10:42.406Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712687,0,546839,615901,550408,'F',TO_TIMESTAMP('2023-02-21 16:10:42','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records','The Created By field indicates the user who created this record.','Y','N','N','Y','N','N','N',0,'Created By',40,0,0,TO_TIMESTAMP('2023-02-21 16:10:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Created
-- Column: C_BPartner_BlockStatus.Created
-- 2023-02-21T14:11:00.569Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-21 16:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615900
;

-- UI Element: Business Partner_OLD(123,D) -> Block status(546839,D) -> main -> 10 -> main.Created By
-- Column: C_BPartner_BlockStatus.CreatedBy
-- 2023-02-21T14:11:00.582Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-21 16:11:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615901
;

-- 2023-02-21T14:16:40.922Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582097,0,TO_TIMESTAMP('2023-02-21 16:16:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Blocked Business Partner','Blocked Business Partner',TO_TIMESTAMP('2023-02-21 16:16:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T14:16:40.927Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582097 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-02-21T14:17:14.612Z
UPDATE AD_Element_Trl SET Name='Gesperrte Business Partner', PrintName='Gesperrte Business Partner',Updated=TO_TIMESTAMP('2023-02-21 16:17:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097 AND AD_Language='de_CH'
;

-- 2023-02-21T14:17:14.614Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'de_CH') 
;

-- Element: null
-- 2023-02-21T14:17:16.982Z
UPDATE AD_Element_Trl SET Name='Gesperrte Business Partner', PrintName='Gesperrte Business Partner',Updated=TO_TIMESTAMP('2023-02-21 16:17:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097 AND AD_Language='de_DE'
;

-- 2023-02-21T14:17:16.984Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'de_DE') 
;

-- Element: null
-- 2023-02-21T14:17:20.188Z
UPDATE AD_Element_Trl SET Name='Gesperrte Business Partner', PrintName='Gesperrte Business Partner',Updated=TO_TIMESTAMP('2023-02-21 16:17:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097 AND AD_Language='fr_CH'
;

-- 2023-02-21T14:17:20.189Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'fr_CH') 
;

-- Element: null
-- 2023-02-21T14:17:22.756Z
UPDATE AD_Element_Trl SET Name='Gesperrte Business Partner', PrintName='Gesperrte Business Partner',Updated=TO_TIMESTAMP('2023-02-21 16:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097 AND AD_Language='nl_NL'
;

-- 2023-02-21T14:17:22.757Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'nl_NL') 
;

-- 2023-02-21T14:22:08.772Z
UPDATE AD_Element SET ColumnName='BlockedBPartner',Updated=TO_TIMESTAMP('2023-02-21 16:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097
;

-- 2023-02-21T14:22:08.774Z
UPDATE AD_Column SET ColumnName='BlockedBPartner' WHERE AD_Element_ID=582097
;

-- 2023-02-21T14:22:08.775Z
UPDATE AD_Process_Para SET ColumnName='BlockedBPartner' WHERE AD_Element_ID=582097
;

-- 2023-02-21T14:22:08.784Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'en_US') 
;

-- Column: M_ShipmentSchedule.BlockedBPartner
-- 2023-02-21T14:23:03.174Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586210,582097,0,20,500221,'BlockedBPartner','CASE WHEN (select BlockStatus from C_BPartner_BlockStatus where C_BPartner_ID = M_ShipmentSchedule.C_BPartner_ID and isCurrent = ''Y'' limit 1) = ''B'' THEN ''Y'' ELSE ''N'' END',TO_TIMESTAMP('2023-02-21 16:23:03','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.inoutcandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Blocked Business Partner',0,0,TO_TIMESTAMP('2023-02-21 16:23:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-21T14:23:03.180Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586210 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-21T14:23:03.189Z
/* DDL */  select update_Column_Translation_From_AD_Element(582097) 
;

-- Column: M_ShipmentSchedule.BlockedBPartner
-- 2023-02-21T14:23:14.894Z
UPDATE AD_Column SET DefaultValue='', ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-02-21 16:23:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586210
;

-- Field: Shipment Disposition(500221,de.metas.inoutcandidate) -> Shipment Disposition(500221,de.metas.inoutcandidate) -> Blocked Business Partner
-- Column: M_ShipmentSchedule.BlockedBPartner
-- 2023-02-21T14:23:51.826Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586210,712693,0,500221,TO_TIMESTAMP('2023-02-21 16:23:51','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Blocked Business Partner',TO_TIMESTAMP('2023-02-21 16:23:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T14:23:51.831Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712693 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T14:23:51.836Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582097) 
;

-- 2023-02-21T14:23:51.862Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712693
;

-- 2023-02-21T14:23:51.868Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712693)
;

-- UI Element: Shipment Disposition(500221,de.metas.inoutcandidate) -> Shipment Disposition(500221,de.metas.inoutcandidate) -> main -> 10 -> default.Blocked Business Partner
-- Column: M_ShipmentSchedule.BlockedBPartner
-- 2023-02-21T14:24:34.481Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712693,0,500221,615902,540021,'F',TO_TIMESTAMP('2023-02-21 16:24:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Blocked Business Partner',12,0,0,TO_TIMESTAMP('2023-02-21 16:24:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Element: BlockedBPartner
-- 2023-02-21T14:28:14.265Z
UPDATE AD_Element_Trl SET Name='Gesperrte Geschäftspartner', PrintName='Gesperrte Geschäftspartner',Updated=TO_TIMESTAMP('2023-02-21 16:28:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097 AND AD_Language='de_CH'
;

-- 2023-02-21T14:28:14.269Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'de_CH') 
;

-- Element: BlockedBPartner
-- 2023-02-21T14:28:16.547Z
UPDATE AD_Element_Trl SET Name='Gesperrte Geschäftspartner', PrintName='Gesperrte Geschäftspartner',Updated=TO_TIMESTAMP('2023-02-21 16:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097 AND AD_Language='de_DE'
;

-- 2023-02-21T14:28:16.549Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'de_DE') 
;

-- Element: BlockedBPartner
-- 2023-02-21T14:28:23.380Z
UPDATE AD_Element_Trl SET Name='Gesperrte Geschäftspartner', PrintName='Gesperrte Geschäftspartner',Updated=TO_TIMESTAMP('2023-02-21 16:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097 AND AD_Language='fr_CH'
;

-- 2023-02-21T14:28:23.381Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'fr_CH') 
;

-- Element: BlockedBPartner
-- 2023-02-21T14:28:25.404Z
UPDATE AD_Element_Trl SET Name='Gesperrte Geschäftspartner', PrintName='Gesperrte Geschäftspartner',Updated=TO_TIMESTAMP('2023-02-21 16:28:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582097 AND AD_Language='nl_NL'
;

-- 2023-02-21T14:28:25.405Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582097,'nl_NL') 
;

-- Value: CannotCompleteInvoiceWithBlockedPartner
-- 2023-02-22T13:58:21.243Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545242,0,TO_TIMESTAMP('2023-02-22 15:58:21','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice cannot be completed because the partner is blocked!','E',TO_TIMESTAMP('2023-02-22 15:58:21','YYYY-MM-DD HH24:MI:SS'),100,'CannotCompleteInvoiceWithBlockedPartner')
;

-- 2023-02-22T13:58:21.252Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545242 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CannotCompleteInvoiceWithBlockedPartner
-- 2023-02-22T13:58:26.942Z
UPDATE AD_Message_Trl SET MsgText='Rechnung kann nicht fertiggestellt werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 15:58:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545242
;

-- Value: CannotCompleteInvoiceWithBlockedPartner
-- 2023-02-22T13:58:28.549Z
UPDATE AD_Message_Trl SET MsgText='Rechnung kann nicht fertiggestellt werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 15:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545242
;

-- Value: CannotCompleteInvoiceWithBlockedPartner
-- 2023-02-22T13:58:30.349Z
UPDATE AD_Message_Trl SET MsgText='Rechnung kann nicht fertiggestellt werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 15:58:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545242
;

-- Value: CannotCompleteInvoiceWithBlockedPartner
-- 2023-02-22T13:58:32.540Z
UPDATE AD_Message_Trl SET MsgText='Rechnung kann nicht fertiggestellt werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 15:58:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545242
;

-- Value: CannotCompleteInOutWithBlockedPartner
-- 2023-02-22T13:59:42.395Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545243,0,TO_TIMESTAMP('2023-02-22 15:59:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Document (goods issue/receipt) cannot be completed because the partner is blocked!','E',TO_TIMESTAMP('2023-02-22 15:59:42','YYYY-MM-DD HH24:MI:SS'),100,'CannotCompleteInOutWithBlockedPartner')
;

-- 2023-02-22T13:59:42.398Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545243 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CannotCompleteInOutWithBlockedPartner
-- 2023-02-22T13:59:47.903Z
UPDATE AD_Message_Trl SET MsgText='Beleg (Warenausgang/-eingang) kann nicht fertiggestellt werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 15:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545243
;

-- Value: CannotCompleteInOutWithBlockedPartner
-- 2023-02-22T13:59:49.309Z
UPDATE AD_Message_Trl SET MsgText='Beleg (Warenausgang/-eingang) kann nicht fertiggestellt werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 15:59:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545243
;

-- Value: CannotCompleteInOutWithBlockedPartner
-- 2023-02-22T13:59:50.596Z
UPDATE AD_Message_Trl SET MsgText='Beleg (Warenausgang/-eingang) kann nicht fertiggestellt werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 15:59:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545243
;

-- Value: CannotCompleteInOutWithBlockedPartner
-- 2023-02-22T13:59:52.541Z
UPDATE AD_Message_Trl SET MsgText='Beleg (Warenausgang/-eingang) kann nicht fertiggestellt werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 15:59:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545243
;

-- Value: CannotReceiveHUWithBlockedPartner
-- 2023-02-22T14:02:04.501Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545244,0,TO_TIMESTAMP('2023-02-22 16:02:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Handling Unit cannot be received because the partner is blocked!','E',TO_TIMESTAMP('2023-02-22 16:02:04','YYYY-MM-DD HH24:MI:SS'),100,'CannotReceiveHUWithBlockedPartner')
;

-- 2023-02-22T14:02:04.502Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545244 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CannotReceiveHUWithBlockedPartner
-- 2023-02-22T14:02:11.679Z
UPDATE AD_Message_Trl SET MsgText='Handling Unit kann nicht empfangen werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 16:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545244
;

-- Value: CannotReceiveHUWithBlockedPartner
-- 2023-02-22T14:02:12.878Z
UPDATE AD_Message_Trl SET MsgText='Handling Unit kann nicht empfangen werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 16:02:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545244
;

-- Value: CannotReceiveHUWithBlockedPartner
-- 2023-02-22T14:02:14.429Z
UPDATE AD_Message_Trl SET MsgText='Handling Unit kann nicht empfangen werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 16:02:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545244
;

-- Value: CannotReceiveHUWithBlockedPartner
-- 2023-02-22T14:02:15.932Z
UPDATE AD_Message_Trl SET MsgText='Handling Unit kann nicht empfangen werden, da der Partner gesperrt ist!',Updated=TO_TIMESTAMP('2023-02-22 16:02:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545244
;

-- Value: CannotEnqueueShipmentScheduleWithBlockedPartner
-- 2023-02-22T14:04:15.180Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545245,0,TO_TIMESTAMP('2023-02-22 16:04:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Scheduled shipments with blocked business partners cannot be enqueued for processing!','E',TO_TIMESTAMP('2023-02-22 16:04:15','YYYY-MM-DD HH24:MI:SS'),100,'CannotEnqueueShipmentScheduleWithBlockedPartner')
;

-- 2023-02-22T14:04:15.184Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545245 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: CannotEnqueueShipmentScheduleWithBlockedPartner
-- 2023-02-22T14:04:21.028Z
UPDATE AD_Message_Trl SET MsgText='Disponierte Lieferungen mit gesperrten Geschäftspartnern können nicht zur Verarbeitung eingereiht werden!',Updated=TO_TIMESTAMP('2023-02-22 16:04:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545245
;

-- Value: CannotEnqueueShipmentScheduleWithBlockedPartner
-- 2023-02-22T14:04:22.435Z
UPDATE AD_Message_Trl SET MsgText='Disponierte Lieferungen mit gesperrten Geschäftspartnern können nicht zur Verarbeitung eingereiht werden!',Updated=TO_TIMESTAMP('2023-02-22 16:04:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545245
;

-- Value: CannotEnqueueShipmentScheduleWithBlockedPartner
-- 2023-02-22T14:04:23.884Z
UPDATE AD_Message_Trl SET MsgText='Disponierte Lieferungen mit gesperrten Geschäftspartnern können nicht zur Verarbeitung eingereiht werden!',Updated=TO_TIMESTAMP('2023-02-22 16:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545245
;

-- Value: CannotEnqueueShipmentScheduleWithBlockedPartner
-- 2023-02-22T14:04:25.452Z
UPDATE AD_Message_Trl SET MsgText='Disponierte Lieferungen mit gesperrten Geschäftspartnern können nicht zur Verarbeitung eingereiht werden!',Updated=TO_TIMESTAMP('2023-02-22 16:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545245
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner
-- 2023-02-22T14:06:49.158Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545246,0,TO_TIMESTAMP('2023-02-22 16:06:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','One or more delivery plannings include a blocked partner!','E',TO_TIMESTAMP('2023-02-22 16:06:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner')
;

-- 2023-02-22T14:06:49.161Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545246 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner
-- 2023-02-22T14:06:54.136Z
UPDATE AD_Message_Trl SET MsgText='Mindestens eine Lieferplanung beinhaltet einen gesperrten Partner!',Updated=TO_TIMESTAMP('2023-02-22 16:06:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545246
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner
-- 2023-02-22T14:06:55.334Z
UPDATE AD_Message_Trl SET MsgText='Mindestens eine Lieferplanung beinhaltet einen gesperrten Partner!',Updated=TO_TIMESTAMP('2023-02-22 16:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545246
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner
-- 2023-02-22T14:06:56.493Z
UPDATE AD_Message_Trl SET MsgText='Mindestens eine Lieferplanung beinhaltet einen gesperrten Partner!',Updated=TO_TIMESTAMP('2023-02-22 16:06:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545246
;

-- Value: de.metas.deliveryplanning.DeliveryPlanningService.NoBlockedPartner
-- 2023-02-22T14:06:57.772Z
UPDATE AD_Message_Trl SET MsgText='Mindestens eine Lieferplanung beinhaltet einen gesperrten Partner!',Updated=TO_TIMESTAMP('2023-02-22 16:06:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545246
;

-- Column: M_ReceiptSchedule.BlockedBPartner
-- 2023-02-22T14:16:13.669Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586213,582097,0,20,540524,'BlockedBPartner','CASE WHEN (select BlockStatus from C_BPartner_BlockStatus where C_BPartner_ID = M_ReceiptSchedule.C_BPartner_ID and isCurrent = ''Y'' limit 1) = ''B'' THEN ''Y'' ELSE ''N'' END',TO_TIMESTAMP('2023-02-22 16:16:13','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.inoutcandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Blocked Business Partner','1=1',0,0,TO_TIMESTAMP('2023-02-22 16:16:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-22T14:16:13.676Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586213 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-22T14:16:13.720Z
/* DDL */  select update_Column_Translation_From_AD_Element(582097) 
;

-- Field: Material Receipt Candidates(540196,de.metas.inoutcandidate) -> Material Receipt Candidates(540526,de.metas.inoutcandidate) -> Blocked Business Partner
-- Column: M_ReceiptSchedule.BlockedBPartner
-- 2023-02-22T14:16:33.247Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586213,712696,0,540526,TO_TIMESTAMP('2023-02-22 16:16:33','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Blocked Business Partner',TO_TIMESTAMP('2023-02-22 16:16:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-22T14:16:33.250Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712696 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-22T14:16:33.255Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582097) 
;

-- 2023-02-22T14:16:33.277Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712696
;

-- 2023-02-22T14:16:33.284Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712696)
;

-- UI Element: Material Receipt Candidates(540196,de.metas.inoutcandidate) -> Material Receipt Candidates(540526,de.metas.inoutcandidate) -> main -> 10 -> default.Blocked Business Partner
-- Column: M_ReceiptSchedule.BlockedBPartner
-- 2023-02-22T14:17:20.969Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712696,0,540526,615906,540132,'F',TO_TIMESTAMP('2023-02-22 16:17:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Blocked Business Partner',6,0,0,TO_TIMESTAMP('2023-02-22 16:17:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Delivery_Planning.BlockedBPartner
-- 2023-02-22T14:22:57.730Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586214,582097,0,20,542259,'BlockedBPartner','CASE WHEN (select BlockStatus from C_BPartner_BlockStatus where C_BPartner_ID = M_Delivery_Planning.C_BPartner_ID and isCurrent = ''Y'' limit 1) = ''B'' THEN ''Y'' ELSE ''N'' END',TO_TIMESTAMP('2023-02-22 16:22:57','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Blocked Business Partner','1=1',0,0,TO_TIMESTAMP('2023-02-22 16:22:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-22T14:22:57.731Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586214 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-22T14:22:57.735Z
/* DDL */  select update_Column_Translation_From_AD_Element(582097) 
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Blocked Business Partner
-- Column: M_Delivery_Planning.BlockedBPartner
-- 2023-02-22T14:23:11.482Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586214,712697,0,546674,TO_TIMESTAMP('2023-02-22 16:23:11','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Blocked Business Partner',TO_TIMESTAMP('2023-02-22 16:23:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-22T14:23:11.485Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712697 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-22T14:23:11.487Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582097) 
;

-- 2023-02-22T14:23:11.493Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712697
;

-- 2023-02-22T14:23:11.499Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712697)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.Blocked Business Partner
-- Column: M_Delivery_Planning.BlockedBPartner
-- 2023-02-22T14:24:03.991Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712697,0,546674,615907,550028,'F',TO_TIMESTAMP('2023-02-22 16:24:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Blocked Business Partner',150,0,0,TO_TIMESTAMP('2023-02-22 16:24:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner(220,D) -> main -> 20 -> tax.Blocked
-- Column: C_BPartner.IsBlocked
-- 2023-02-22T17:45:54.028Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-22 19:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615889
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner(220,D) -> main -> 10 -> Greeting & Name.Firma
-- Column: C_BPartner.IsCompany
-- 2023-02-22T17:45:54.035Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-22 19:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000076
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Geschäftspartnergruppe
-- Column: C_BPartner.C_BP_Group_ID
-- 2023-02-22T17:45:54.040Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-22 19:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000222
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner(220,D) -> main -> 10 -> Sprache & Mitarbeiter.Sprache
-- Column: C_BPartner.AD_Language
-- 2023-02-22T17:45:54.044Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-22 19:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000086
;

-- UI Element: Business Partner_OLD(123,D) -> Business Partner(220,D) -> main -> 20 -> flags.Sektion
-- Column: C_BPartner.AD_Org_ID
-- 2023-02-22T17:45:54.049Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-02-22 19:45:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000374
;
