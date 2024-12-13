-- Table: C_Invoice_Acct
-- 2023-01-12T09:08:19.149Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('2',0,0,0,542278,'N',TO_TIMESTAMP('2023-01-12 11:08:18','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Invoice Accounting Overrides','NP','L','C_Invoice_Acct','DTI',TO_TIMESTAMP('2023-01-12 11:08:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:19.151Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542278 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-01-12T09:08:19.269Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556189,TO_TIMESTAMP('2023-01-12 11:08:19','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Invoice_Acct',1,'Y','N','Y','Y','C_Invoice_Acct','N',1000000,TO_TIMESTAMP('2023-01-12 11:08:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T09:08:19.280Z
CREATE SEQUENCE C_INVOICE_ACCT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Invoice_Acct.AD_Client_ID
-- 2023-01-12T09:08:26.160Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585469,102,0,19,542278,'AD_Client_ID',TO_TIMESTAMP('2023-01-12 11:08:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-01-12 11:08:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:26.165Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585469 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:26.207Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: C_Invoice_Acct.AD_Org_ID
-- 2023-01-12T09:08:27.725Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585470,113,0,30,542278,'AD_Org_ID',TO_TIMESTAMP('2023-01-12 11:08:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-01-12 11:08:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:27.727Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585470 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:27.730Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: C_Invoice_Acct.Created
-- 2023-01-12T09:08:28.687Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585471,245,0,16,542278,'Created',TO_TIMESTAMP('2023-01-12 11:08:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-01-12 11:08:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:28.689Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585471 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:28.692Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: C_Invoice_Acct.CreatedBy
-- 2023-01-12T09:08:29.563Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585472,246,0,18,110,542278,'CreatedBy',TO_TIMESTAMP('2023-01-12 11:08:29','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-01-12 11:08:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:29.565Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585472 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:29.569Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: C_Invoice_Acct.IsActive
-- 2023-01-12T09:08:30.419Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585473,348,0,20,542278,'IsActive',TO_TIMESTAMP('2023-01-12 11:08:30','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-01-12 11:08:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:30.420Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585473 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:30.423Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: C_Invoice_Acct.Updated
-- 2023-01-12T09:08:31.346Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585474,607,0,16,542278,'Updated',TO_TIMESTAMP('2023-01-12 11:08:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-01-12 11:08:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:31.348Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585474 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:31.350Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: C_Invoice_Acct.UpdatedBy
-- 2023-01-12T09:08:32.255Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585475,608,0,18,110,542278,'UpdatedBy',TO_TIMESTAMP('2023-01-12 11:08:31','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-01-12 11:08:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:32.258Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585475 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:32.264Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-01-12T09:08:32.976Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581915,0,'C_Invoice_Acct_ID',TO_TIMESTAMP('2023-01-12 11:08:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Accounting Overrides','Invoice Accounting Overrides',TO_TIMESTAMP('2023-01-12 11:08:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T09:08:32.981Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581915 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Invoice_Acct.C_Invoice_Acct_ID
-- 2023-01-12T09:08:33.702Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585476,581915,0,13,542278,'C_Invoice_Acct_ID',TO_TIMESTAMP('2023-01-12 11:08:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Invoice Accounting Overrides',0,0,TO_TIMESTAMP('2023-01-12 11:08:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:33.704Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585476 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:33.707Z
/* DDL */  select update_Column_Translation_From_AD_Element(581915) 
;

-- Column: C_Invoice_Acct.C_Invoice_ID
-- 2023-01-12T09:08:56.454Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585477,1008,0,30,542278,'C_Invoice_ID',TO_TIMESTAMP('2023-01-12 11:08:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Invoice Identifier','D',0,10,'The Invoice Document.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Invoice',0,0,TO_TIMESTAMP('2023-01-12 11:08:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:08:56.456Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585477 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:08:56.459Z
/* DDL */  select update_Column_Translation_From_AD_Element(1008) 
;

-- Column: C_Invoice_Acct.C_InvoiceLine_ID
-- 2023-01-12T09:09:11.171Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585478,1076,0,30,542278,'C_InvoiceLine_ID',TO_TIMESTAMP('2023-01-12 11:09:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Rechnungszeile','D',0,10,'Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',0,'Invoice Line',0,0,TO_TIMESTAMP('2023-01-12 11:09:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:09:11.174Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585478 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:09:11.177Z
/* DDL */  select update_Column_Translation_From_AD_Element(1076) 
;

-- Column: C_Invoice_Acct.C_AcctSchema_ID
-- 2023-01-12T09:09:44.548Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585479,181,0,30,542278,'C_AcctSchema_ID',TO_TIMESTAMP('2023-01-12 11:09:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Rules for accounting','D',0,10,'An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Accounting Schema',0,0,TO_TIMESTAMP('2023-01-12 11:09:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:09:44.550Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585479 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:09:44.553Z
/* DDL */  select update_Column_Translation_From_AD_Element(181) 
;

-- Column: C_Invoice_Acct.C_Invoice_ID
-- 2023-01-12T09:09:56.686Z
UPDATE AD_Column SET IsParent='N',Updated=TO_TIMESTAMP('2023-01-12 11:09:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585477
;

-- Column: C_Invoice_Acct.AccountName
-- 2023-01-12T09:11:21.952Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585480,577539,0,10,542278,'AccountName',TO_TIMESTAMP('2023-01-12 11:11:21','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,100,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Account Name',0,0,TO_TIMESTAMP('2023-01-12 11:11:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:11:21.954Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585480 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:11:21.957Z
/* DDL */  select update_Column_Translation_From_AD_Element(577539) 
;

-- Column: C_Invoice_Acct.C_ElementValue_ID
-- 2023-01-12T09:11:42.143Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585481,198,0,30,542278,'C_ElementValue_ID',TO_TIMESTAMP('2023-01-12 11:11:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Account Element','D',0,10,'Account Elements can be natural accounts or user defined values.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Account Element',0,0,TO_TIMESTAMP('2023-01-12 11:11:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-12T09:11:42.145Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585481 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-12T09:11:42.150Z
/* DDL */  select update_Column_Translation_From_AD_Element(198) 
;

-- 2023-01-12T11:13:30.856Z
/* DDL */ CREATE TABLE public.C_Invoice_Acct (AccountName VARCHAR(100), AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_AcctSchema_ID NUMERIC(10) NOT NULL, C_ElementValue_ID NUMERIC(10) NOT NULL, C_Invoice_Acct_ID NUMERIC(10) NOT NULL, C_Invoice_ID NUMERIC(10) NOT NULL, C_InvoiceLine_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CAcctSchema_CInvoiceAcct FOREIGN KEY (C_AcctSchema_ID) REFERENCES public.C_AcctSchema DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CElementValue_CInvoiceAcct FOREIGN KEY (C_ElementValue_ID) REFERENCES public.C_ElementValue DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Invoice_Acct_Key PRIMARY KEY (C_Invoice_Acct_ID), CONSTRAINT CInvoice_CInvoiceAcct FOREIGN KEY (C_Invoice_ID) REFERENCES public.C_Invoice DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CInvoiceLine_CInvoiceAcct FOREIGN KEY (C_InvoiceLine_ID) REFERENCES public.C_InvoiceLine DEFERRABLE INITIALLY DEFERRED)
;

-- Window: Invoice Accounting Overrides, InternalName=null
-- 2023-01-12T11:14:01.909Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581915,0,541659,TO_TIMESTAMP('2023-01-12 13:14:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Invoice Accounting Overrides','N',TO_TIMESTAMP('2023-01-12 13:14:01','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-01-12T11:14:01.915Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541659 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-01-12T11:14:01.925Z
/* DDL */  select update_window_translation_from_ad_element(581915) 
;

-- 2023-01-12T11:14:01.973Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541659
;

-- 2023-01-12T11:14:01.985Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541659)
;

-- Tab: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides
-- Table: C_Invoice_Acct
-- 2023-01-12T11:14:27.930Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581915,0,546735,542278,541659,'Y',TO_TIMESTAMP('2023-01-12 13:14:26','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_Invoice_Acct','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Invoice Accounting Overrides','N',10,0,TO_TIMESTAMP('2023-01-12 13:14:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:14:27.936Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546735 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-01-12T11:14:27.946Z
/* DDL */  select update_tab_translation_from_ad_element(581915) 
;

-- 2023-01-12T11:14:27.960Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546735)
;

-- Window: Invoice Accounting Overrides, InternalName=C_Invoice_Acct
-- 2023-01-12T11:14:31.059Z
UPDATE AD_Window SET InternalName='C_Invoice_Acct',Updated=TO_TIMESTAMP('2023-01-12 13:14:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541659
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Client
-- Column: C_Invoice_Acct.AD_Client_ID
-- 2023-01-12T11:14:55.736Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585469,710148,0,546735,TO_TIMESTAMP('2023-01-12 13:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-01-12 13:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:14:55.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:14:55.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-12T11:15:00.405Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710148
;

-- 2023-01-12T11:15:00.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710148)
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Organisation
-- Column: C_Invoice_Acct.AD_Org_ID
-- 2023-01-12T11:15:00.537Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585470,710149,0,546735,TO_TIMESTAMP('2023-01-12 13:15:00','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-01-12 13:15:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:15:00.540Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:15:00.543Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-12T11:15:01.976Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710149
;

-- 2023-01-12T11:15:01.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710149)
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Active
-- Column: C_Invoice_Acct.IsActive
-- 2023-01-12T11:15:02.118Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585473,710150,0,546735,TO_TIMESTAMP('2023-01-12 13:15:01','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-01-12 13:15:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:15:02.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:15:02.125Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-12T11:15:03.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710150
;

-- 2023-01-12T11:15:03.644Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710150)
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Invoice Accounting Overrides
-- Column: C_Invoice_Acct.C_Invoice_Acct_ID
-- 2023-01-12T11:15:03.777Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585476,710151,0,546735,TO_TIMESTAMP('2023-01-12 13:15:03','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Invoice Accounting Overrides',TO_TIMESTAMP('2023-01-12 13:15:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:15:03.780Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:15:03.783Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581915) 
;

-- 2023-01-12T11:15:03.790Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710151
;

-- 2023-01-12T11:15:03.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710151)
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Invoice
-- Column: C_Invoice_Acct.C_Invoice_ID
-- 2023-01-12T11:15:03.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585477,710152,0,546735,TO_TIMESTAMP('2023-01-12 13:15:03','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier',10,'D','The Invoice Document.','Y','N','N','N','N','N','N','N','Invoice',TO_TIMESTAMP('2023-01-12 13:15:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:15:03.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:15:03.916Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008) 
;

-- 2023-01-12T11:15:04.036Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710152
;

-- 2023-01-12T11:15:04.039Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710152)
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Invoice Line
-- Column: C_Invoice_Acct.C_InvoiceLine_ID
-- 2023-01-12T11:15:04.160Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585478,710153,0,546735,TO_TIMESTAMP('2023-01-12 13:15:04','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile',10,'D','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','N','N','N','N','N','Invoice Line',TO_TIMESTAMP('2023-01-12 13:15:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:15:04.163Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:15:04.166Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1076) 
;

-- 2023-01-12T11:15:04.196Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710153
;

-- 2023-01-12T11:15:04.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710153)
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Accounting Schema
-- Column: C_Invoice_Acct.C_AcctSchema_ID
-- 2023-01-12T11:15:04.336Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585479,710154,0,546735,TO_TIMESTAMP('2023-01-12 13:15:04','YYYY-MM-DD HH24:MI:SS'),100,'Rules for accounting',10,'D','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','N','N','N','N','N','N','N','Accounting Schema',TO_TIMESTAMP('2023-01-12 13:15:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:15:04.340Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:15:04.344Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(181) 
;

-- 2023-01-12T11:15:04.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710154
;

-- 2023-01-12T11:15:04.434Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710154)
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Account Name
-- Column: C_Invoice_Acct.AccountName
-- 2023-01-12T11:15:04.559Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585480,710155,0,546735,TO_TIMESTAMP('2023-01-12 13:15:04','YYYY-MM-DD HH24:MI:SS'),100,100,'D','Y','N','N','N','N','N','N','N','Account Name',TO_TIMESTAMP('2023-01-12 13:15:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:15:04.562Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:15:04.566Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577539) 
;

-- 2023-01-12T11:15:04.572Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710155
;

-- 2023-01-12T11:15:04.574Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710155)
;

-- Field: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> Account Element
-- Column: C_Invoice_Acct.C_ElementValue_ID
-- 2023-01-12T11:15:04.695Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585481,710156,0,546735,TO_TIMESTAMP('2023-01-12 13:15:04','YYYY-MM-DD HH24:MI:SS'),100,'Account Element',10,'D','Account Elements can be natural accounts or user defined values.','Y','N','N','N','N','N','N','N','Account Element',TO_TIMESTAMP('2023-01-12 13:15:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-12T11:15:04.697Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-12T11:15:04.701Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(198) 
;

-- 2023-01-12T11:15:04.719Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710156
;

-- 2023-01-12T11:15:04.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710156)
;

-- Tab: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D)
-- UI Section: main
-- 2023-01-12T11:15:22.448Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546735,545368,TO_TIMESTAMP('2023-01-12 13:15:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-12 13:15:22','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-01-12T11:15:22.453Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545368 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main
-- UI Column: 10
-- 2023-01-12T11:15:26.510Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546547,545368,TO_TIMESTAMP('2023-01-12 13:15:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-12 13:15:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main
-- UI Column: 20
-- 2023-01-12T11:15:28.096Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546548,545368,TO_TIMESTAMP('2023-01-12 13:15:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-01-12 13:15:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10
-- UI Element Group: invoice&matching criteria
-- 2023-01-12T11:15:52.808Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546547,550214,TO_TIMESTAMP('2023-01-12 13:15:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','invoice&matching criteria',10,TO_TIMESTAMP('2023-01-12 13:15:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10 -> invoice&matching criteria.Invoice
-- Column: C_Invoice_Acct.C_Invoice_ID
-- 2023-01-12T11:16:06.177Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710152,0,546735,550214,614649,'F',TO_TIMESTAMP('2023-01-12 13:16:05','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Identifier','The Invoice Document.','Y','N','Y','N','N','Invoice',10,0,0,TO_TIMESTAMP('2023-01-12 13:16:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10 -> invoice&matching criteria.Invoice Line
-- Column: C_Invoice_Acct.C_InvoiceLine_ID
-- 2023-01-12T11:16:16.792Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710153,0,546735,550214,614650,'F',TO_TIMESTAMP('2023-01-12 13:16:16','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungszeile','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','Y','N','N','Invoice Line',20,0,0,TO_TIMESTAMP('2023-01-12 13:16:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10 -> invoice&matching criteria.Accounting Schema
-- Column: C_Invoice_Acct.C_AcctSchema_ID
-- 2023-01-12T11:16:28.165Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710154,0,546735,550214,614651,'F',TO_TIMESTAMP('2023-01-12 13:16:28','YYYY-MM-DD HH24:MI:SS'),100,'Rules for accounting','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','N','Y','N','N','Accounting Schema',30,0,0,TO_TIMESTAMP('2023-01-12 13:16:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10 -> invoice&matching criteria.Account Name
-- Column: C_Invoice_Acct.AccountName
-- 2023-01-12T11:16:42.289Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710155,0,546735,550214,614652,'F',TO_TIMESTAMP('2023-01-12 13:16:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Account Name',40,0,0,TO_TIMESTAMP('2023-01-12 13:16:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20
-- UI Element Group: flags
-- 2023-01-12T11:17:24.385Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546548,550215,TO_TIMESTAMP('2023-01-12 13:17:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-01-12 13:17:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20 -> flags.Active
-- Column: C_Invoice_Acct.IsActive
-- 2023-01-12T11:17:36.017Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710150,0,546735,550215,614653,'F',TO_TIMESTAMP('2023-01-12 13:17:35','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','Y','N','N','Active',10,0,0,TO_TIMESTAMP('2023-01-12 13:17:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20
-- UI Element Group: overrides
-- 2023-01-12T11:17:50.825Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546548,550216,TO_TIMESTAMP('2023-01-12 13:17:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','overrides',20,TO_TIMESTAMP('2023-01-12 13:17:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20 -> overrides.Account Element
-- Column: C_Invoice_Acct.C_ElementValue_ID
-- 2023-01-12T11:18:06.501Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710156,0,546735,550216,614654,'F',TO_TIMESTAMP('2023-01-12 13:18:06','YYYY-MM-DD HH24:MI:SS'),100,'Account Element','Account Elements can be natural accounts or user defined values.','Y','N','Y','N','N','Account Element',10,0,0,TO_TIMESTAMP('2023-01-12 13:18:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10 -> invoice&matching criteria.Invoice
-- Column: C_Invoice_Acct.C_Invoice_ID
-- 2023-01-12T11:18:26.884Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-01-12 13:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614649
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10 -> invoice&matching criteria.Invoice Line
-- Column: C_Invoice_Acct.C_InvoiceLine_ID
-- 2023-01-12T11:18:26.898Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-01-12 13:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614650
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10 -> invoice&matching criteria.Accounting Schema
-- Column: C_Invoice_Acct.C_AcctSchema_ID
-- 2023-01-12T11:18:26.912Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-01-12 13:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614651
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 10 -> invoice&matching criteria.Account Name
-- Column: C_Invoice_Acct.AccountName
-- 2023-01-12T11:18:26.927Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-01-12 13:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614652
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20 -> overrides.Account Element
-- Column: C_Invoice_Acct.C_ElementValue_ID
-- 2023-01-12T11:18:26.938Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-01-12 13:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614654
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20 -> flags.Active
-- Column: C_Invoice_Acct.IsActive
-- 2023-01-12T11:18:26.948Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-01-12 13:18:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614653
;

-- Column: C_Invoice_Acct.C_Invoice_ID
-- 2023-01-12T11:19:02.926Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-12 13:19:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585477
;

-- Name: C_InvoiceLine of C_Invoice_ID
-- 2023-01-12T11:20:00.789Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540613,'C_InvoiceLine.C_Invoice_ID=@C_Invoice_ID@',TO_TIMESTAMP('2023-01-12 13:20:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_InvoiceLine of C_Invoice_ID','S',TO_TIMESTAMP('2023-01-12 13:20:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Invoice_Acct.C_InvoiceLine_ID
-- 2023-01-12T11:20:22.895Z
UPDATE AD_Column SET AD_Val_Rule_ID=540613, IsUpdateable='N',Updated=TO_TIMESTAMP('2023-01-12 13:20:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585478
;

-- Column: C_Invoice_Acct.AccountName
-- 2023-01-12T11:20:35.529Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-12 13:20:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585480
;

-- Column: C_Invoice_Acct.C_AcctSchema_ID
-- 2023-01-12T11:20:45.688Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-12 13:20:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585479
;

-- Column: C_Invoice_Acct.C_ElementValue_ID
-- 2023-01-12T11:21:19.976Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-01-12 13:21:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585481
;

-- Table: C_Invoice_Acct
-- 2023-01-12T11:22:30.073Z
UPDATE AD_Table SET AccessLevel='1',Updated=TO_TIMESTAMP('2023-01-12 13:22:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542278
;

-- UI Column: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20
-- UI Element Group: org&client
-- 2023-01-12T11:23:11.840Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546548,550217,TO_TIMESTAMP('2023-01-12 13:23:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',30,TO_TIMESTAMP('2023-01-12 13:23:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20 -> org&client.Organisation
-- Column: C_Invoice_Acct.AD_Org_ID
-- 2023-01-12T11:23:22.826Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710149,0,546735,550217,614655,'F',TO_TIMESTAMP('2023-01-12 13:23:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2023-01-12 13:23:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20 -> org&client.Client
-- Column: C_Invoice_Acct.AD_Client_ID
-- 2023-01-12T11:23:30.514Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710148,0,546735,550217,614656,'F',TO_TIMESTAMP('2023-01-12 13:23:29','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','N','N','Client',20,0,0,TO_TIMESTAMP('2023-01-12 13:23:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice Accounting Overrides(541659,D) -> Invoice Accounting Overrides(546735,D) -> main -> 20 -> org&client.Organisation
-- Column: C_Invoice_Acct.AD_Org_ID
-- 2023-01-12T11:23:37.104Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-12 13:23:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614655
;

