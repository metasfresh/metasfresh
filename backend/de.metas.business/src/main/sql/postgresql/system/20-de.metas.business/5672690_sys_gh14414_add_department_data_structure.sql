-- Table: M_Department
-- 2023-01-19T19:26:51.112Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542284,'N',TO_TIMESTAMP('2023-01-19 20:26:51','YYYY-MM-DD HH24:MI:SS'),100,'','D','N','Y','N','N','Y','N','Y','N','N','N',0,'Department','NP','L','M_Department','DTI',TO_TIMESTAMP('2023-01-19 20:26:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:26:51.113Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542284 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-01-19T19:26:51.175Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556193,TO_TIMESTAMP('2023-01-19 20:26:51','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Department',1,'Y','N','Y','Y','M_Department','N',1000000,TO_TIMESTAMP('2023-01-19 20:26:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:26:51.184Z
CREATE SEQUENCE M_DEPARTMENT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: M_Department
-- 2023-01-19T19:26:58.892Z
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2023-01-19 20:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542284
;

-- Column: M_Department.AD_Client_ID
-- 2023-01-19T19:27:42.377Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585562,102,0,19,542284,'AD_Client_ID',TO_TIMESTAMP('2023-01-19 20:27:42','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Client',0,0,TO_TIMESTAMP('2023-01-19 20:27:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:42.379Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585562 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:42.402Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Department.AD_Org_ID
-- 2023-01-19T19:27:43.623Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585563,113,0,30,542284,'AD_Org_ID',TO_TIMESTAMP('2023-01-19 20:27:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Organisation',10,0,TO_TIMESTAMP('2023-01-19 20:27:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:43.625Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585563 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:43.626Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Department.AD_Table_ID
-- 2023-01-19T19:27:44.377Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585564,126,0,19,542284,'AD_Table_ID',TO_TIMESTAMP('2023-01-19 20:27:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Table',0,0,TO_TIMESTAMP('2023-01-19 20:27:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:44.378Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585564 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:44.380Z
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- Column: M_Department.CM_Template_ID
-- 2023-01-19T19:27:45.042Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585565,2978,0,19,542284,'CM_Template_ID',TO_TIMESTAMP('2023-01-19 20:27:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',0,10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','Template',0,0,TO_TIMESTAMP('2023-01-19 20:27:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:45.043Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585565 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:45.045Z
/* DDL */  select update_Column_Translation_From_AD_Element(2978) 
;

-- 2023-01-19T19:27:45.453Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581944,0,'M_Department_ID',TO_TIMESTAMP('2023-01-19 20:27:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Department','Department',TO_TIMESTAMP('2023-01-19 20:27:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:27:45.456Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581944 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Department.M_Department_ID
-- 2023-01-19T19:27:46.062Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585566,581944,0,13,542284,'M_Department_ID',TO_TIMESTAMP('2023-01-19 20:27:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','Department',0,0,TO_TIMESTAMP('2023-01-19 20:27:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:46.064Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585566 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:46.065Z
/* DDL */  select update_Column_Translation_From_AD_Element(581944) 
;

-- Column: M_Department.Created
-- 2023-01-19T19:27:46.700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585567,245,0,16,542284,'Created',TO_TIMESTAMP('2023-01-19 20:27:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,7,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created',0,0,TO_TIMESTAMP('2023-01-19 20:27:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:46.702Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585567 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:46.703Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Department.CreatedBy
-- 2023-01-19T19:27:47.502Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585568,246,0,18,110,542284,'CreatedBy',TO_TIMESTAMP('2023-01-19 20:27:47','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created By',0,0,TO_TIMESTAMP('2023-01-19 20:27:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:47.504Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585568 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:47.505Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Department.Description
-- 2023-01-19T19:27:48.228Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585569,275,0,10,542284,'Description',TO_TIMESTAMP('2023-01-19 20:27:47','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Description',0,0,TO_TIMESTAMP('2023-01-19 20:27:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:48.229Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585569 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:48.231Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: M_Department.IsActive
-- 2023-01-19T19:27:48.908Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585570,348,0,20,542284,'IsActive',TO_TIMESTAMP('2023-01-19 20:27:48','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Active',0,0,TO_TIMESTAMP('2023-01-19 20:27:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:48.910Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585570 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:48.911Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Department.Name
-- 2023-01-19T19:27:49.700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585571,469,0,10,542284,'Name',TO_TIMESTAMP('2023-01-19 20:27:49','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,120,'E','','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Name',10,1,TO_TIMESTAMP('2023-01-19 20:27:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:49.702Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585571 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:49.703Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: M_Department.OtherClause
-- 2023-01-19T19:27:50.362Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585572,2642,0,14,542284,'OtherClause',TO_TIMESTAMP('2023-01-19 20:27:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',0,2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,0,TO_TIMESTAMP('2023-01-19 20:27:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:50.364Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585572 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:50.365Z
/* DDL */  select update_Column_Translation_From_AD_Element(2642) 
;

-- Column: M_Department.Updated
-- 2023-01-19T19:27:51.013Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585573,607,0,16,542284,'Updated',TO_TIMESTAMP('2023-01-19 20:27:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,7,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated',0,0,TO_TIMESTAMP('2023-01-19 20:27:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:51.015Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585573 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:51.016Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Department.UpdatedBy
-- 2023-01-19T19:27:51.792Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585574,608,0,18,110,542284,'UpdatedBy',TO_TIMESTAMP('2023-01-19 20:27:51','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated By',0,0,TO_TIMESTAMP('2023-01-19 20:27:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:51.793Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585574 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:51.795Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: M_Department.WhereClause
-- 2023-01-19T19:27:52.530Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585575,630,0,14,542284,'WhereClause',TO_TIMESTAMP('2023-01-19 20:27:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',0,2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SQL WHERE',0,0,TO_TIMESTAMP('2023-01-19 20:27:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:27:52.531Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585575 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:27:52.532Z
/* DDL */  select update_Column_Translation_From_AD_Element(630) 
;

-- 2023-01-19T19:27:52.961Z
/* DDL */ CREATE TABLE public.M_Department (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, CM_Template_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Department_ID NUMERIC(10) NOT NULL, Name VARCHAR(120) NOT NULL, OtherClause VARCHAR(2000), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, WhereClause VARCHAR(2000), CONSTRAINT ADTable_MDepartment FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CMTemplate_MDepartment FOREIGN KEY (CM_Template_ID) REFERENCES public.CM_Template DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Department_Key PRIMARY KEY (M_Department_ID))
;

-- 2023-01-19T19:27:53.006Z
INSERT INTO t_alter_column values('m_department','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:27:53.015Z
INSERT INTO t_alter_column values('m_department','AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:27:53.023Z
INSERT INTO t_alter_column values('m_department','CM_Template_ID','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:27:53.031Z
INSERT INTO t_alter_column values('m_department','M_Department_ID','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:27:53.038Z
INSERT INTO t_alter_column values('m_department','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-01-19T19:27:53.044Z
INSERT INTO t_alter_column values('m_department','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:27:53.049Z
INSERT INTO t_alter_column values('m_department','Description','VARCHAR(255)',null,null)
;

-- 2023-01-19T19:27:53.056Z
INSERT INTO t_alter_column values('m_department','IsActive','CHAR(1)',null,null)
;

-- 2023-01-19T19:27:53.074Z
INSERT INTO t_alter_column values('m_department','Name','VARCHAR(120)',null,null)
;

-- 2023-01-19T19:27:53.080Z
INSERT INTO t_alter_column values('m_department','OtherClause','VARCHAR(2000)',null,null)
;

-- 2023-01-19T19:27:53.088Z
INSERT INTO t_alter_column values('m_department','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-01-19T19:27:53.093Z
INSERT INTO t_alter_column values('m_department','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:27:53.099Z
INSERT INTO t_alter_column values('m_department','WhereClause','VARCHAR(2000)',null,null)
;

-- Column: M_Department.Value
-- 2023-01-19T19:29:41.605Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585576,620,0,10,542284,'Value',TO_TIMESTAMP('2023-01-19 20:29:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Search key for the record in the format required - must be unique','D',0,255,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','N','Y','Y',0,'Search Key',0,0,TO_TIMESTAMP('2023-01-19 20:29:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:29:41.607Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585576 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:29:41.609Z
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- Column: M_Department.Value
-- 2023-01-19T19:30:12.774Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-19 20:30:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585576
;

-- Column: M_Department.WhereClause
-- 2023-01-19T19:32:18.494Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585575
;

-- 2023-01-19T19:32:18.496Z
DELETE FROM AD_Column WHERE AD_Column_ID=585575
;

-- Column: M_Department.OtherClause
-- 2023-01-19T19:32:33.699Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585572
;

-- 2023-01-19T19:32:33.702Z
DELETE FROM AD_Column WHERE AD_Column_ID=585572
;

-- Column: M_Department.CM_Template_ID
-- 2023-01-19T19:32:54.707Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585565
;

-- 2023-01-19T19:32:54.708Z
DELETE FROM AD_Column WHERE AD_Column_ID=585565
;

-- Column: M_Department.AD_Table_ID
-- 2023-01-19T19:33:09.418Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585564
;

-- 2023-01-19T19:33:09.420Z
DELETE FROM AD_Column WHERE AD_Column_ID=585564
;

-- Table: M_Department_SectionCode
-- 2023-01-19T19:39:36.429Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542285,'N',TO_TIMESTAMP('2023-01-19 20:39:36','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','Y','Y','N','Y','N','N','N',0,'Department SectionCode','NP','L','M_Department_SectionCode','DTI',TO_TIMESTAMP('2023-01-19 20:39:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:39:36.430Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542285 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-01-19T19:39:36.488Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556194,TO_TIMESTAMP('2023-01-19 20:39:36','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Department_SectionCode',1,'Y','N','Y','Y','M_Department_SectionCode','N',1000000,TO_TIMESTAMP('2023-01-19 20:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:39:36.493Z
CREATE SEQUENCE M_DEPARTMENT_SECTIONCODE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Department_SectionCode.AD_Client_ID
-- 2023-01-19T19:40:28.577Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585577,102,0,19,542285,'AD_Client_ID',TO_TIMESTAMP('2023-01-19 20:40:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Client',0,0,TO_TIMESTAMP('2023-01-19 20:40:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:28.578Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585577 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:28.581Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Department_SectionCode.AD_Org_ID
-- 2023-01-19T19:40:29.652Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585578,113,0,30,542285,'AD_Org_ID',TO_TIMESTAMP('2023-01-19 20:40:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Organisation',10,0,TO_TIMESTAMP('2023-01-19 20:40:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:29.653Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585578 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:29.654Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Department_SectionCode.AD_Table_ID
-- 2023-01-19T19:40:30.439Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585579,126,0,19,542285,'AD_Table_ID',TO_TIMESTAMP('2023-01-19 20:40:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Table',0,0,TO_TIMESTAMP('2023-01-19 20:40:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:30.441Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585579 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:30.442Z
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- Column: M_Department_SectionCode.CM_Template_ID
-- 2023-01-19T19:40:31.099Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585580,2978,0,19,542285,'CM_Template_ID',TO_TIMESTAMP('2023-01-19 20:40:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',0,10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','Template',0,0,TO_TIMESTAMP('2023-01-19 20:40:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:31.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585580 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:31.101Z
/* DDL */  select update_Column_Translation_From_AD_Element(2978) 
;

-- 2023-01-19T19:40:31.540Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581945,0,'M_Department_SectionCode_ID',TO_TIMESTAMP('2023-01-19 20:40:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Department SectionCode','Department SectionCode',TO_TIMESTAMP('2023-01-19 20:40:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:40:31.542Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581945 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Department_SectionCode.M_Department_SectionCode_ID
-- 2023-01-19T19:40:32.145Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585581,581945,0,13,542285,'M_Department_SectionCode_ID',TO_TIMESTAMP('2023-01-19 20:40:31','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','Department SectionCode',0,0,TO_TIMESTAMP('2023-01-19 20:40:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:32.146Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585581 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:32.147Z
/* DDL */  select update_Column_Translation_From_AD_Element(581945) 
;

-- Column: M_Department_SectionCode.Created
-- 2023-01-19T19:40:32.791Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585582,245,0,16,542285,'Created',TO_TIMESTAMP('2023-01-19 20:40:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,7,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created',0,0,TO_TIMESTAMP('2023-01-19 20:40:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:32.793Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585582 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:32.794Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Department_SectionCode.CreatedBy
-- 2023-01-19T19:40:33.565Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585583,246,0,18,110,542285,'CreatedBy',TO_TIMESTAMP('2023-01-19 20:40:33','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created By',0,0,TO_TIMESTAMP('2023-01-19 20:40:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:33.566Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585583 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:33.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Department_SectionCode.Description
-- 2023-01-19T19:40:34.321Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585584,275,0,10,542285,'Description',TO_TIMESTAMP('2023-01-19 20:40:34','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Description',0,0,TO_TIMESTAMP('2023-01-19 20:40:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:34.322Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585584 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:34.324Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: M_Department_SectionCode.IsActive
-- 2023-01-19T19:40:35.013Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585585,348,0,20,542285,'IsActive',TO_TIMESTAMP('2023-01-19 20:40:34','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Active',0,0,TO_TIMESTAMP('2023-01-19 20:40:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:35.014Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585585 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:35.015Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Department_SectionCode.Name
-- 2023-01-19T19:40:35.770Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585586,469,0,10,542285,'Name',TO_TIMESTAMP('2023-01-19 20:40:35','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,120,'E','','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Name',10,1,TO_TIMESTAMP('2023-01-19 20:40:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:35.771Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585586 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:35.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: M_Department_SectionCode.OtherClause
-- 2023-01-19T19:40:36.465Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585587,2642,0,14,542285,'OtherClause',TO_TIMESTAMP('2023-01-19 20:40:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',0,2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,0,TO_TIMESTAMP('2023-01-19 20:40:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:36.467Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585587 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:36.468Z
/* DDL */  select update_Column_Translation_From_AD_Element(2642) 
;

-- Column: M_Department_SectionCode.Updated
-- 2023-01-19T19:40:37.107Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585588,607,0,16,542285,'Updated',TO_TIMESTAMP('2023-01-19 20:40:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,7,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated',0,0,TO_TIMESTAMP('2023-01-19 20:40:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:37.108Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585588 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:37.109Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Department_SectionCode.UpdatedBy
-- 2023-01-19T19:40:37.855Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585589,608,0,18,110,542285,'UpdatedBy',TO_TIMESTAMP('2023-01-19 20:40:37','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated By',0,0,TO_TIMESTAMP('2023-01-19 20:40:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:37.856Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585589 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:37.857Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: M_Department_SectionCode.WhereClause
-- 2023-01-19T19:40:38.584Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585590,630,0,14,542285,'WhereClause',TO_TIMESTAMP('2023-01-19 20:40:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',0,2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SQL WHERE',0,0,TO_TIMESTAMP('2023-01-19 20:40:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:40:38.585Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585590 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:40:38.587Z
/* DDL */  select update_Column_Translation_From_AD_Element(630) 
;

-- 2023-01-19T19:40:39.021Z
/* DDL */ CREATE TABLE public.M_Department_SectionCode (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, CM_Template_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Department_SectionCode_ID NUMERIC(10) NOT NULL, Name VARCHAR(120) NOT NULL, OtherClause VARCHAR(2000), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, WhereClause VARCHAR(2000), CONSTRAINT ADTable_MDepartmentSectionCode FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CMTemplate_MDepartmentSectionCode FOREIGN KEY (CM_Template_ID) REFERENCES public.CM_Template DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Department_SectionCode_Key PRIMARY KEY (M_Department_SectionCode_ID))
;

-- 2023-01-19T19:40:39.052Z
INSERT INTO t_alter_column values('m_department_sectioncode','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:40:39.059Z
INSERT INTO t_alter_column values('m_department_sectioncode','AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:40:39.066Z
INSERT INTO t_alter_column values('m_department_sectioncode','CM_Template_ID','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:40:39.072Z
INSERT INTO t_alter_column values('m_department_sectioncode','M_Department_SectionCode_ID','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:40:39.077Z
INSERT INTO t_alter_column values('m_department_sectioncode','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-01-19T19:40:39.082Z
INSERT INTO t_alter_column values('m_department_sectioncode','CreatedBy','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:40:39.088Z
INSERT INTO t_alter_column values('m_department_sectioncode','Description','VARCHAR(255)',null,null)
;

-- 2023-01-19T19:40:39.095Z
INSERT INTO t_alter_column values('m_department_sectioncode','IsActive','CHAR(1)',null,null)
;

-- 2023-01-19T19:40:39.117Z
INSERT INTO t_alter_column values('m_department_sectioncode','Name','VARCHAR(120)',null,null)
;

-- 2023-01-19T19:40:39.123Z
INSERT INTO t_alter_column values('m_department_sectioncode','OtherClause','VARCHAR(2000)',null,null)
;

-- 2023-01-19T19:40:39.129Z
INSERT INTO t_alter_column values('m_department_sectioncode','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2023-01-19T19:40:39.135Z
INSERT INTO t_alter_column values('m_department_sectioncode','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2023-01-19T19:40:39.141Z
INSERT INTO t_alter_column values('m_department_sectioncode','WhereClause','VARCHAR(2000)',null,null)
;

-- Column: M_Department_SectionCode.AD_Table_ID
-- 2023-01-19T19:41:46.149Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585579
;

-- 2023-01-19T19:41:46.150Z
DELETE FROM AD_Column WHERE AD_Column_ID=585579
;

-- Column: M_Department_SectionCode.WhereClause
-- 2023-01-19T19:41:54.901Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585590
;

-- 2023-01-19T19:41:54.902Z
DELETE FROM AD_Column WHERE AD_Column_ID=585590
;

-- Column: M_Department_SectionCode.OtherClause
-- 2023-01-19T19:41:59.566Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585587
;

-- 2023-01-19T19:41:59.567Z
DELETE FROM AD_Column WHERE AD_Column_ID=585587
;

-- Column: M_Department_SectionCode.Name
-- 2023-01-19T19:42:04.006Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585586
;

-- 2023-01-19T19:42:04.008Z
DELETE FROM AD_Column WHERE AD_Column_ID=585586
;

-- Column: M_Department_SectionCode.Description
-- 2023-01-19T19:42:08.451Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585584
;

-- 2023-01-19T19:42:08.453Z
DELETE FROM AD_Column WHERE AD_Column_ID=585584
;

-- Column: M_Department_SectionCode.CM_Template_ID
-- 2023-01-19T19:42:13.005Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585580
;

-- 2023-01-19T19:42:13.006Z
DELETE FROM AD_Column WHERE AD_Column_ID=585580
;

-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T19:44:37.750Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585591,581944,0,19,542285,'M_Department_ID',TO_TIMESTAMP('2023-01-19 20:44:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Department',0,0,TO_TIMESTAMP('2023-01-19 20:44:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:44:37.751Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585591 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:44:37.752Z
/* DDL */  select update_Column_Translation_From_AD_Element(581944) 
;

-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T19:44:49.106Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-19 20:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585591
;

-- 2023-01-19T19:45:03.816Z
/* DDL */ SELECT public.db_alter_table('M_Department_SectionCode','ALTER TABLE public.M_Department_SectionCode ADD COLUMN M_Department_ID NUMERIC(10) NOT NULL')
;

-- 2023-01-19T19:45:03.823Z
ALTER TABLE M_Department_SectionCode ADD CONSTRAINT MDepartment_MDepartmentSectionCode FOREIGN KEY (M_Department_ID) REFERENCES public.M_Department DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Department_SectionCode.M_SectionCode_ID
-- 2023-01-19T19:47:28.824Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585592,581238,0,19,542285,'M_SectionCode_ID',TO_TIMESTAMP('2023-01-19 20:47:28','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-01-19 20:47:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T19:47:28.825Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585592 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T19:47:28.827Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2023-01-19T19:47:32.048Z
/* DDL */ SELECT public.db_alter_table('M_Department_SectionCode','ALTER TABLE public.M_Department_SectionCode ADD COLUMN M_SectionCode_ID NUMERIC(10) NOT NULL')
;

-- 2023-01-19T19:47:32.054Z
ALTER TABLE M_Department_SectionCode ADD CONSTRAINT MSectionCode_MDepartmentSectionCode FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2023-01-19T19:51:00.220Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581946,0,TO_TIMESTAMP('2023-01-19 20:51:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Department','Department',TO_TIMESTAMP('2023-01-19 20:51:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:51:00.221Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581946 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Department, InternalName=null
-- 2023-01-19T19:51:54.339Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581946,0,541663,TO_TIMESTAMP('2023-01-19 20:51:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Department','N',TO_TIMESTAMP('2023-01-19 20:51:54','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-01-19T19:51:54.341Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541663 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-01-19T19:51:54.343Z
/* DDL */  select update_window_translation_from_ad_element(581946) 
;

-- 2023-01-19T19:51:54.348Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541663
;

-- 2023-01-19T19:51:54.350Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541663)
;

-- Window: Department, InternalName=Department
-- 2023-01-19T19:52:06.286Z
UPDATE AD_Window SET InternalName='Department',Updated=TO_TIMESTAMP('2023-01-19 20:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541663
;

-- Tab: Department(541663,D) -> Department
-- Table: HR_Department
-- 2023-01-19T19:52:43.209Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,53390,0,546743,53088,541663,'Y',TO_TIMESTAMP('2023-01-19 20:52:43','YYYY-MM-DD HH24:MI:SS'),100,'Department of the organization','D','N','The Department of the organization','N','A','HR_Department','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Department','N',10,0,TO_TIMESTAMP('2023-01-19 20:52:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:52:43.210Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546743 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-01-19T19:52:43.212Z
/* DDL */  select update_tab_translation_from_ad_element(53390) 
;

-- 2023-01-19T19:52:43.219Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546743)
;

-- Tab: Department(541663,D) -> Department
-- Table: M_Department
-- 2023-01-19T19:54:12.762Z
UPDATE AD_Tab SET AD_Element_ID=581944, AD_Table_ID=542284, CommitWarning=NULL, Description=NULL, Help=NULL, InternalName='M_Department', Name='Department',Updated=TO_TIMESTAMP('2023-01-19 20:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546743
;

-- 2023-01-19T19:54:12.763Z
UPDATE AD_Tab_Trl trl SET Description=NULL,Help=NULL WHERE AD_Tab_ID=546743 AND AD_Language='en_US'
;

-- 2023-01-19T19:54:12.766Z
/* DDL */  select update_tab_translation_from_ad_element(581944) 
;

-- 2023-01-19T19:54:12.772Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546743)
;

-- Field: Department(541663,D) -> Department(546743,D) -> Client
-- Column: M_Department.AD_Client_ID
-- 2023-01-19T19:55:05.834Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585562,710284,0,546743,TO_TIMESTAMP('2023-01-19 20:55:05','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-01-19 20:55:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:55:05.835Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710284 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:55:05.838Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-19T19:55:06.237Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710284
;

-- 2023-01-19T19:55:06.237Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710284)
;

-- Field: Department(541663,D) -> Department(546743,D) -> Organisation
-- Column: M_Department.AD_Org_ID
-- 2023-01-19T19:55:06.296Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585563,710285,0,546743,TO_TIMESTAMP('2023-01-19 20:55:06','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-01-19 20:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:55:06.297Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710285 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:55:06.298Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-19T19:55:06.482Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710285
;

-- 2023-01-19T19:55:06.482Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710285)
;

-- Field: Department(541663,D) -> Department(546743,D) -> Department
-- Column: M_Department.M_Department_ID
-- 2023-01-19T19:55:06.549Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585566,710286,0,546743,TO_TIMESTAMP('2023-01-19 20:55:06','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-01-19 20:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:55:06.550Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710286 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:55:06.550Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-01-19T19:55:06.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710286
;

-- 2023-01-19T19:55:06.552Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710286)
;

-- Field: Department(541663,D) -> Department(546743,D) -> Description
-- Column: M_Department.Description
-- 2023-01-19T19:55:06.611Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585569,710287,0,546743,TO_TIMESTAMP('2023-01-19 20:55:06','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Description',TO_TIMESTAMP('2023-01-19 20:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:55:06.612Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710287 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:55:06.613Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-01-19T19:55:06.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710287
;

-- 2023-01-19T19:55:06.713Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710287)
;

-- Field: Department(541663,D) -> Department(546743,D) -> Active
-- Column: M_Department.IsActive
-- 2023-01-19T19:55:06.783Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585570,710288,0,546743,TO_TIMESTAMP('2023-01-19 20:55:06','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-01-19 20:55:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:55:06.784Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710288 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:55:06.785Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-19T19:55:07.006Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710288
;

-- 2023-01-19T19:55:07.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710288)
;

-- Field: Department(541663,D) -> Department(546743,D) -> Name
-- Column: M_Department.Name
-- 2023-01-19T19:55:07.082Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585571,710289,0,546743,TO_TIMESTAMP('2023-01-19 20:55:07','YYYY-MM-DD HH24:MI:SS'),100,'',120,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-01-19 20:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:55:07.083Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710289 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:55:07.084Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-01-19T19:55:07.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710289
;

-- 2023-01-19T19:55:07.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710289)
;

-- Field: Department(541663,D) -> Department(546743,D) -> Search Key
-- Column: M_Department.Value
-- 2023-01-19T19:55:07.212Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585576,710290,0,546743,TO_TIMESTAMP('2023-01-19 20:55:07','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique',255,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Search Key',TO_TIMESTAMP('2023-01-19 20:55:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:55:07.213Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710290 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:55:07.214Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2023-01-19T19:55:07.240Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710290
;

-- 2023-01-19T19:55:07.240Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710290)
;

-- Tab: Department(541663,D) -> Department SectionCode
-- Table: M_Department_SectionCode
-- 2023-01-19T19:56:09.473Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581945,0,546744,542285,541663,'Y',TO_TIMESTAMP('2023-01-19 20:56:09','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_Department_SectionCode','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Department SectionCode',585566,'N',20,1,TO_TIMESTAMP('2023-01-19 20:56:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:56:09.474Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546744 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-01-19T19:56:09.476Z
/* DDL */  select update_tab_translation_from_ad_element(581945) 
;

-- 2023-01-19T19:56:09.494Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546744)
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Client
-- Column: M_Department_SectionCode.AD_Client_ID
-- 2023-01-19T19:56:22.712Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585577,710291,0,546744,TO_TIMESTAMP('2023-01-19 20:56:22','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-01-19 20:56:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:56:22.713Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710291 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:56:22.715Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-19T19:56:22.866Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710291
;

-- 2023-01-19T19:56:22.866Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710291)
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Organisation
-- Column: M_Department_SectionCode.AD_Org_ID
-- 2023-01-19T19:56:22.925Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585578,710292,0,546744,TO_TIMESTAMP('2023-01-19 20:56:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-01-19 20:56:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:56:22.926Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710292 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:56:22.927Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-19T19:56:23.072Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710292
;

-- 2023-01-19T19:56:23.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710292)
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Department SectionCode
-- Column: M_Department_SectionCode.M_Department_SectionCode_ID
-- 2023-01-19T19:56:23.132Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585581,710293,0,546744,TO_TIMESTAMP('2023-01-19 20:56:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Department SectionCode',TO_TIMESTAMP('2023-01-19 20:56:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:56:23.133Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710293 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:56:23.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581945) 
;

-- 2023-01-19T19:56:23.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710293
;

-- 2023-01-19T19:56:23.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710293)
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Active
-- Column: M_Department_SectionCode.IsActive
-- 2023-01-19T19:56:23.199Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585585,710294,0,546744,TO_TIMESTAMP('2023-01-19 20:56:23','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-01-19 20:56:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:56:23.200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710294 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:56:23.201Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-19T19:56:23.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710294
;

-- 2023-01-19T19:56:23.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710294)
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Department
-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T19:56:23.444Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585591,710295,0,546744,TO_TIMESTAMP('2023-01-19 20:56:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-01-19 20:56:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:56:23.444Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710295 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:56:23.445Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-01-19T19:56:23.449Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710295
;

-- 2023-01-19T19:56:23.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710295)
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Section Code
-- Column: M_Department_SectionCode.M_SectionCode_ID
-- 2023-01-19T19:56:23.501Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585592,710296,0,546744,TO_TIMESTAMP('2023-01-19 20:56:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2023-01-19 20:56:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:56:23.502Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710296 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T19:56:23.503Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-01-19T19:56:23.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710296
;

-- 2023-01-19T19:56:23.511Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710296)
;

-- Name: Department
-- Action Type: W
-- Window: Department(541663,D)
-- 2023-01-19T19:59:31.923Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581946,542041,0,541663,TO_TIMESTAMP('2023-01-19 20:59:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Department','Y','N','N','N','N','Department',TO_TIMESTAMP('2023-01-19 20:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T19:59:31.925Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542041 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-01-19T19:59:31.927Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542041, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542041)
;

-- 2023-01-19T19:59:31.930Z
/* DDL */  select update_menu_translation_from_ad_element(581946) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2023-01-19T19:59:32.484Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2023-01-19T19:59:32.485Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2023-01-19T19:59:32.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2023-01-19T19:59:32.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2023-01-19T19:59:32.486Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2023-01-19T19:59:32.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2023-01-19T19:59:32.487Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2023-01-19T19:59:32.488Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2023-01-19T19:59:32.488Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2023-01-19T19:59:32.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2023-01-19T19:59:32.489Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2023-01-19T19:59:32.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing`
-- 2023-01-19T19:59:32.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning`
-- 2023-01-19T19:59:32.490Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Delivery Instruction`
-- 2023-01-19T19:59:32.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542032 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2023-01-19T19:59:32.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2023-01-19T19:59:32.491Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2023-01-19T19:59:32.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2023-01-19T19:59:32.492Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2023-01-19T19:59:32.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2023-01-19T19:59:32.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2023-01-19T19:59:32.494Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2023-01-19T19:59:32.494Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2023-01-19T19:59:32.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2023-01-19T19:59:32.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-01-19T19:59:32.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-01-19T19:59:32.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-01-19T19:59:32.497Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2023-01-19T19:59:32.497Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2023-01-19T19:59:32.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2023-01-19T19:59:32.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation`
-- 2023-01-19T19:59:32.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Department`
-- 2023-01-19T19:59:32.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542041 AND AD_Tree_ID=10
;

-- Tab: Department(541663,D) -> Department(546743,D)
-- UI Section: main
-- 2023-01-19T20:00:30.656Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546743,545376,TO_TIMESTAMP('2023-01-19 21:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-19 21:00:30','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-01-19T20:00:30.657Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545376 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Department(541663,D) -> Department(546743,D) -> main
-- UI Column: 10
-- 2023-01-19T20:00:44.117Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546557,545376,TO_TIMESTAMP('2023-01-19 21:00:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-19 21:00:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Department(541663,D) -> Department(546743,D) -> main
-- UI Column: 20
-- 2023-01-19T20:00:48.096Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546558,545376,TO_TIMESTAMP('2023-01-19 21:00:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-01-19 21:00:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Department(541663,D) -> Department(546743,D) -> main -> 10
-- UI Element Group: primary
-- 2023-01-19T20:01:04.097Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546557,550231,TO_TIMESTAMP('2023-01-19 21:01:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,TO_TIMESTAMP('2023-01-19 21:01:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 10 -> primary.Search Key
-- Column: M_Department.Value
-- 2023-01-19T20:02:27.414Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710290,0,546743,550231,614740,'F',TO_TIMESTAMP('2023-01-19 21:02:27','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Search Key',10,0,0,TO_TIMESTAMP('2023-01-19 21:02:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 10 -> primary.Name
-- Column: M_Department.Name
-- 2023-01-19T20:02:37.232Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710289,0,546743,550231,614741,'F',TO_TIMESTAMP('2023-01-19 21:02:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2023-01-19 21:02:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Department(541663,D) -> Department(546743,D) -> main -> 20
-- UI Element Group: flag
-- 2023-01-19T20:03:05.366Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546558,550232,TO_TIMESTAMP('2023-01-19 21:03:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2023-01-19 21:03:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 20 -> flag.Active
-- Column: M_Department.IsActive
-- 2023-01-19T20:03:17.257Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710288,0,546743,550232,614742,'F',TO_TIMESTAMP('2023-01-19 21:03:17','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2023-01-19 21:03:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Department(541663,D) -> Department(546743,D) -> main -> 20
-- UI Element Group: org
-- 2023-01-19T20:03:27.534Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546558,550233,TO_TIMESTAMP('2023-01-19 21:03:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2023-01-19 21:03:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 20 -> org.Organisation
-- Column: M_Department.AD_Org_ID
-- 2023-01-19T20:03:38.214Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710285,0,546743,550233,614743,'F',TO_TIMESTAMP('2023-01-19 21:03:38','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-01-19 21:03:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 20 -> org.Client
-- Column: M_Department.AD_Client_ID
-- 2023-01-19T20:03:49.073Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710284,0,546743,550233,614744,'F',TO_TIMESTAMP('2023-01-19 21:03:49','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2023-01-19 21:03:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Department(541663,D) -> Department SectionCode(546744,D)
-- UI Section: main
-- 2023-01-19T20:04:07.212Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546744,545377,TO_TIMESTAMP('2023-01-19 21:04:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-19 21:04:07','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-01-19T20:04:07.213Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545377 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Department(541663,D) -> Department SectionCode(546744,D) -> main
-- UI Column: 10
-- 2023-01-19T20:04:11.493Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546559,545377,TO_TIMESTAMP('2023-01-19 21:04:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-19 21:04:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10
-- UI Element Group: main
-- 2023-01-19T20:04:30.477Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546559,550234,TO_TIMESTAMP('2023-01-19 21:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-01-19 21:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Department
-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T20:04:44.755Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710295,0,546744,550234,614745,'F',TO_TIMESTAMP('2023-01-19 21:04:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Department',10,0,0,TO_TIMESTAMP('2023-01-19 21:04:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Section Code
-- Column: M_Department_SectionCode.M_SectionCode_ID
-- 2023-01-19T20:04:58.889Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710296,0,546744,550234,614746,'F',TO_TIMESTAMP('2023-01-19 21:04:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',20,0,0,TO_TIMESTAMP('2023-01-19 21:04:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Active
-- Column: M_Department_SectionCode.IsActive
-- 2023-01-19T20:05:18.817Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710294,0,546744,550234,614747,'F',TO_TIMESTAMP('2023-01-19 21:05:18','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',30,0,0,TO_TIMESTAMP('2023-01-19 21:05:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Organisation
-- Column: M_Department_SectionCode.AD_Org_ID
-- 2023-01-19T20:05:31.137Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710292,0,546744,550234,614748,'F',TO_TIMESTAMP('2023-01-19 21:05:31','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',40,0,0,TO_TIMESTAMP('2023-01-19 21:05:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Client
-- Column: M_Department_SectionCode.AD_Client_ID
-- 2023-01-19T20:05:41.822Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710291,0,546744,550234,614749,'F',TO_TIMESTAMP('2023-01-19 21:05:41','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',50,0,0,TO_TIMESTAMP('2023-01-19 21:05:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:09:31.978Z
/* DDL */ SELECT public.db_alter_table('M_Department','ALTER TABLE public.M_Department ADD COLUMN Value VARCHAR(255) NOT NULL')
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Department
-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T20:12:51.126Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2023-01-19 21:12:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614745
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Department
-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T20:17:50.124Z
UPDATE AD_Field SET DefaultValue='M_Department_ID',Updated=TO_TIMESTAMP('2023-01-19 21:17:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710295
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Department
-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T20:18:09.914Z
UPDATE AD_Field SET DefaultValue='@M_Department_ID@',Updated=TO_TIMESTAMP('2023-01-19 21:18:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710295
;

-- Column: M_Department_SectionCode.ValidFrom
-- 2023-01-19T20:21:56.197Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585593,617,0,15,542285,'ValidFrom',TO_TIMESTAMP('2023-01-19 21:21:56','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,7,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Valid From',0,0,TO_TIMESTAMP('2023-01-19 21:21:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T20:21:56.198Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585593 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T20:21:56.199Z
/* DDL */  select update_Column_Translation_From_AD_Element(617) 
;

-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T20:22:35.640Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-19 21:22:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585591
;

-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T20:22:50.657Z
UPDATE AD_Column SET IsSelectionColumn='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-19 21:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585591
;

-- 2023-01-19T20:23:11.910Z
/* DDL */ SELECT public.db_alter_table('M_Department_SectionCode','ALTER TABLE public.M_Department_SectionCode ADD COLUMN ValidFrom TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_Department_SectionCode.ValidTo
-- 2023-01-19T20:23:52.356Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585594,618,0,15,542285,'ValidTo',TO_TIMESTAMP('2023-01-19 21:23:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Valid to including this date (last day)','D',0,7,'The Valid To date indicates the last day of a date range','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Valid to',0,0,TO_TIMESTAMP('2023-01-19 21:23:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-19T20:23:52.357Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585594 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-19T20:23:52.358Z
/* DDL */  select update_Column_Translation_From_AD_Element(618) 
;

-- Column: M_Department_SectionCode.ValidFrom
-- 2023-01-19T20:24:09.358Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-01-19 21:24:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585593
;

-- 2023-01-19T20:24:47.281Z
INSERT INTO t_alter_column values('m_department_sectioncode','ValidFrom','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2023-01-19T20:24:47.284Z
INSERT INTO t_alter_column values('m_department_sectioncode','ValidFrom',null,'NOT NULL',null)
;

-- 2023-01-19T20:25:11.371Z
/* DDL */ SELECT public.db_alter_table('M_Department_SectionCode','ALTER TABLE public.M_Department_SectionCode ADD COLUMN ValidTo TIMESTAMP WITHOUT TIME ZONE')
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Valid From
-- Column: M_Department_SectionCode.ValidFrom
-- 2023-01-19T20:26:06.298Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585593,710297,0,546744,0,TO_TIMESTAMP('2023-01-19 21:26:06','YYYY-MM-DD HH24:MI:SS'),100,'',0,'D','',0,'Y','Y','Y','N','N','N','N','N','Valid From',0,10,0,1,1,TO_TIMESTAMP('2023-01-19 21:26:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:26:06.299Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710297 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:26:06.301Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(617) 
;

-- 2023-01-19T20:26:06.316Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710297
;

-- 2023-01-19T20:26:06.317Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710297)
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Valid to
-- Column: M_Department_SectionCode.ValidTo
-- 2023-01-19T20:26:32.232Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585594,710298,0,546744,0,TO_TIMESTAMP('2023-01-19 21:26:32','YYYY-MM-DD HH24:MI:SS'),100,'Valid to including this date (last day)',0,'D','The Valid To date indicates the last day of a date range',0,'Y','Y','Y','N','N','N','N','N','Valid to',0,20,0,1,1,TO_TIMESTAMP('2023-01-19 21:26:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:26:32.233Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710298 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-19T20:26:32.234Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(618) 
;

-- 2023-01-19T20:26:32.251Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710298
;

-- 2023-01-19T20:26:32.251Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710298)
;

-- Field: Department(541663,D) -> Department SectionCode(546744,D) -> Valid to
-- Column: M_Department_SectionCode.ValidTo
-- 2023-01-19T20:26:38.254Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-01-19 21:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710298
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Department
-- Column: M_Department_SectionCode.M_Department_ID
-- 2023-01-19T20:26:53.401Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=614745
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Valid From
-- Column: M_Department_SectionCode.ValidFrom
-- 2023-01-19T20:27:50.754Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710297,0,546744,550234,614750,'F',TO_TIMESTAMP('2023-01-19 21:27:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Valid From',60,0,0,TO_TIMESTAMP('2023-01-19 21:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Valid From
-- Column: M_Department_SectionCode.ValidFrom
-- 2023-01-19T20:28:19.004Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-01-19 21:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614750
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Section Code
-- Column: M_Department_SectionCode.M_SectionCode_ID
-- 2023-01-19T20:28:49.453Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-01-19 21:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614746
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Valid From
-- Column: M_Department_SectionCode.ValidFrom
-- 2023-01-19T20:28:49.457Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-01-19 21:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614750
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Active
-- Column: M_Department_SectionCode.IsActive
-- 2023-01-19T20:28:49.459Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-01-19 21:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614747
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Organisation
-- Column: M_Department_SectionCode.AD_Org_ID
-- 2023-01-19T20:28:49.461Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-19 21:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614748
;

-- UI Element: Department(541663,D) -> Department SectionCode(546744,D) -> main -> 10 -> main.Client
-- Column: M_Department_SectionCode.AD_Client_ID
-- 2023-01-19T20:28:49.464Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-19 21:28:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614749
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 10 -> primary.Search Key
-- Column: M_Department.Value
-- 2023-01-19T20:29:19.765Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-01-19 21:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614740
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 10 -> primary.Name
-- Column: M_Department.Name
-- 2023-01-19T20:29:19.767Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-01-19 21:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614741
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 20 -> flag.Active
-- Column: M_Department.IsActive
-- 2023-01-19T20:29:19.769Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-01-19 21:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614742
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 20 -> org.Organisation
-- Column: M_Department.AD_Org_ID
-- 2023-01-19T20:29:19.770Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-19 21:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614743
;

-- UI Element: Department(541663,D) -> Department(546743,D) -> main -> 20 -> org.Client
-- Column: M_Department.AD_Client_ID
-- 2023-01-19T20:29:19.772Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-19 21:29:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614744
;

-- 2023-01-19T20:34:35.520Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540720,0,542284,TO_TIMESTAMP('2023-01-19 21:34:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','m_department_unique_value','N',TO_TIMESTAMP('2023-01-19 21:34:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:34:35.522Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540720 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-01-19T20:35:07.236Z
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2023-01-19 21:35:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540720
;

-- 2023-01-19T20:35:35.414Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,585576,541300,540720,0,TO_TIMESTAMP('2023-01-19 21:35:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-01-19 21:35:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-19T20:36:24.979Z
UPDATE AD_Index_Table SET ErrorMsg='Value of Department already in use',Updated=TO_TIMESTAMP('2023-01-19 21:36:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540720
;

-- 2023-01-19T20:36:24.980Z
UPDATE AD_Index_Table_Trl trl SET ErrorMsg='Value of Department already in use' WHERE AD_Index_Table_ID=540720 AND AD_Language='en_US'
;

-- 2023-01-19T20:37:02.728Z
CREATE UNIQUE INDEX m_department_unique_value ON M_Department (Value)
;


-- remove unwanted columns copied from the template table

ALTER TABLE M_Department
    DROP COLUMN AD_Table_ID
;

ALTER TABLE M_Department
    DROP COLUMN CM_Template_ID
;

ALTER TABLE M_Department
    DROP COLUMN OtherClause
;

ALTER TABLE M_Department
    DROP COLUMN WhereClause
;


ALTER TABLE M_Department_SectionCode
    DROP COLUMN AD_Table_ID
;

ALTER TABLE M_Department_SectionCode
    DROP COLUMN CM_Template_ID
;

ALTER TABLE M_Department_SectionCode
    DROP COLUMN OtherClause
;

ALTER TABLE M_Department_SectionCode
    DROP COLUMN WhereClause
;

ALTER TABLE M_Department_SectionCode
    DROP COLUMN Name
;


ALTER TABLE M_Department_SectionCode
    DROP COLUMN Description
;

