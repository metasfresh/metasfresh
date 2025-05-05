-- Column: C_Order_Cost_Detail.QtyReceived
-- 2023-02-07T13:47:37.091Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585934,542547,0,29,542297,'QtyReceived',TO_TIMESTAMP('2023-02-07 15:47:36','YYYY-MM-DD HH24:MI:SS'),100,'N','0','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Empfangene Menge',0,0,TO_TIMESTAMP('2023-02-07 15:47:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-07T13:47:37.093Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585934 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-07T13:47:37.096Z
/* DDL */  select update_Column_Translation_From_AD_Element(542547) 
;

-- 2023-02-07T13:47:38.023Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost_Detail','ALTER TABLE public.C_Order_Cost_Detail ADD COLUMN QtyReceived NUMERIC DEFAULT 0 NOT NULL')
;

-- 2023-02-07T13:48:08.567Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582042,0,'CostAmountReceived',TO_TIMESTAMP('2023-02-07 15:48:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Amount Received','Cost Amount Received',TO_TIMESTAMP('2023-02-07 15:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-07T13:48:08.568Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582042 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Order_Cost_Detail.CostAmountReceived
-- 2023-02-07T13:48:17.648Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585935,582042,0,12,542297,'CostAmountReceived',TO_TIMESTAMP('2023-02-07 15:48:17','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cost Amount Received',0,0,TO_TIMESTAMP('2023-02-07 15:48:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-07T13:48:17.649Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585935 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-07T13:48:17.652Z
/* DDL */  select update_Column_Translation_From_AD_Element(582042) 
;

-- 2023-02-07T13:48:20.253Z
/* DDL */ SELECT public.db_alter_table('C_Order_Cost_Detail','ALTER TABLE public.C_Order_Cost_Detail ADD COLUMN CostAmountReceived NUMERIC DEFAULT 0 NOT NULL')
;

-- Column: C_Order_Cost_Detail.QtyReceived
-- 2023-02-07T13:50:05.686Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-07 15:50:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585934
;

-- Table: M_InOut_Cost
-- 2023-02-08T08:28:17.957Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('4',0,0,0,542299,'N',TO_TIMESTAMP('2023-02-08 10:28:17','YYYY-MM-DD HH24:MI:SS'),100,'U','N','Y','N','N','Y','N','N','N','N','N',0,'Shipment/Receipt Costs','NP','L','M_InOut_Cost','DTI',TO_TIMESTAMP('2023-02-08 10:28:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:17.960Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542299 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-08T08:28:18.116Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556209,TO_TIMESTAMP('2023-02-08 10:28:18','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_InOut_Cost',1,'Y','N','Y','Y','M_InOut_Cost','N',1000000,TO_TIMESTAMP('2023-02-08 10:28:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T08:28:18.128Z
CREATE SEQUENCE M_INOUT_COST_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: M_InOut_Cost
-- 2023-02-08T08:28:30.758Z
UPDATE AD_Table SET AccessLevel='1', EntityType='D',Updated=TO_TIMESTAMP('2023-02-08 10:28:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542299
;

-- Column: M_InOut_Cost.AD_Client_ID
-- 2023-02-08T08:28:40.450Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585937,102,0,19,542299,'AD_Client_ID',TO_TIMESTAMP('2023-02-08 10:28:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Client',0,0,TO_TIMESTAMP('2023-02-08 10:28:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:40.451Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585937 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:28:40.455Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_InOut_Cost.AD_Org_ID
-- 2023-02-08T08:28:41.393Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585938,113,0,30,542299,'AD_Org_ID',TO_TIMESTAMP('2023-02-08 10:28:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-02-08 10:28:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:41.395Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585938 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:28:41.397Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_InOut_Cost.Created
-- 2023-02-08T08:28:42.171Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585939,245,0,16,542299,'Created',TO_TIMESTAMP('2023-02-08 10:28:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,29,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created',0,0,TO_TIMESTAMP('2023-02-08 10:28:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:42.173Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585939 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:28:42.175Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_InOut_Cost.CreatedBy
-- 2023-02-08T08:28:42.949Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585940,246,0,18,110,542299,'CreatedBy',TO_TIMESTAMP('2023-02-08 10:28:42','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Created By',0,0,TO_TIMESTAMP('2023-02-08 10:28:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:42.951Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585940 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:28:42.953Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_InOut_Cost.IsActive
-- 2023-02-08T08:28:43.705Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585941,348,0,20,542299,'IsActive',TO_TIMESTAMP('2023-02-08 10:28:43','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Active',0,0,TO_TIMESTAMP('2023-02-08 10:28:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:43.706Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585941 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:28:43.708Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_InOut_Cost.Updated
-- 2023-02-08T08:28:44.482Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585942,607,0,16,542299,'Updated',TO_TIMESTAMP('2023-02-08 10:28:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,29,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated',0,0,TO_TIMESTAMP('2023-02-08 10:28:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:44.484Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585942 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:28:44.487Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_InOut_Cost.UpdatedBy
-- 2023-02-08T08:28:45.272Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585943,608,0,18,110,542299,'UpdatedBy',TO_TIMESTAMP('2023-02-08 10:28:44','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Updated By',0,0,TO_TIMESTAMP('2023-02-08 10:28:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:45.274Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585943 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:28:45.276Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-02-08T08:28:45.907Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582043,0,'M_InOut_Cost_ID',TO_TIMESTAMP('2023-02-08 10:28:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Shipment/Receipt Costs','Shipment/Receipt Costs',TO_TIMESTAMP('2023-02-08 10:28:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T08:28:45.908Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582043 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_InOut_Cost.M_InOut_Cost_ID
-- 2023-02-08T08:28:46.536Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585944,582043,0,13,542299,'M_InOut_Cost_ID',TO_TIMESTAMP('2023-02-08 10:28:45','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Shipment/Receipt Costs',0,0,TO_TIMESTAMP('2023-02-08 10:28:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:28:46.537Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585944 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:28:46.540Z
/* DDL */  select update_Column_Translation_From_AD_Element(582043) 
;

-- Column: M_InOut_Cost.M_InOut_ID
-- 2023-02-08T08:31:20.889Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585945,1025,0,30,542299,'M_InOut_ID',TO_TIMESTAMP('2023-02-08 10:31:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Material Shipment Document','D',0,10,'The Material Shipment / Receipt ','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Shipment/ Receipt',0,0,TO_TIMESTAMP('2023-02-08 10:31:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:31:20.893Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585945 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:31:20.902Z
/* DDL */  select update_Column_Translation_From_AD_Element(1025) 
;

-- Column: M_InOut_Cost.M_InOutLine_ID
-- 2023-02-08T08:31:47.701Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585946,1026,0,30,542299,190,'M_InOutLine_ID',TO_TIMESTAMP('2023-02-08 10:31:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Line on Receipt document','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Receipt Line',0,0,TO_TIMESTAMP('2023-02-08 10:31:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:31:47.703Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585946 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:31:47.706Z
/* DDL */  select update_Column_Translation_From_AD_Element(1026) 
;

-- Column: M_InOut_Cost.C_Order_Cost_ID
-- 2023-02-08T08:32:06.779Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585947,582030,0,30,542299,'C_Order_Cost_ID',TO_TIMESTAMP('2023-02-08 10:32:06','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Order Cost',0,0,TO_TIMESTAMP('2023-02-08 10:32:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:32:06.780Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585947 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:32:06.785Z
/* DDL */  select update_Column_Translation_From_AD_Element(582030) 
;

-- Column: C_Order_Cost_Detail.C_Order_Cost_ID
-- 2023-02-08T08:33:09.632Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-08 10:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585915
;

-- Column: C_Order_Cost_Detail.C_OrderLine_ID
-- 2023-02-08T08:33:21.602Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=20,Updated=TO_TIMESTAMP('2023-02-08 10:33:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585913
;

-- Column: C_Order_Cost_Detail.CostAmount
-- 2023-02-08T08:33:33.029Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=30,Updated=TO_TIMESTAMP('2023-02-08 10:33:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585933
;

-- Column: M_InOut_Cost.C_Order_Cost_Detail_ID
-- 2023-02-08T08:33:41.064Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585948,582032,0,30,542299,'C_Order_Cost_Detail_ID',TO_TIMESTAMP('2023-02-08 10:33:40','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Order Cost Detail',0,0,TO_TIMESTAMP('2023-02-08 10:33:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:33:41.066Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585948 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:33:41.069Z
/* DDL */  select update_Column_Translation_From_AD_Element(582032) 
;

-- Column: M_InOut_Cost.C_Order_ID
-- 2023-02-08T08:34:00.037Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585949,558,0,30,542299,'C_Order_ID',TO_TIMESTAMP('2023-02-08 10:33:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Sales order',0,0,TO_TIMESTAMP('2023-02-08 10:33:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:34:00.039Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585949 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:34:00.041Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- Column: M_InOut_Cost.C_OrderLine_ID
-- 2023-02-08T08:34:14.888Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585950,561,0,30,542299,'C_OrderLine_ID',TO_TIMESTAMP('2023-02-08 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Orderline',0,0,TO_TIMESTAMP('2023-02-08 10:34:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:34:14.889Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585950 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:34:14.892Z
/* DDL */  select update_Column_Translation_From_AD_Element(561) 
;

-- Column: M_InOut_Cost.C_UOM_ID
-- 2023-02-08T08:34:36.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585951,215,0,30,542299,'C_UOM_ID',TO_TIMESTAMP('2023-02-08 10:34:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Unit of Measure','D',0,10,'The UOM defines a unique non monetary Unit of Measure','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'UOM',0,0,TO_TIMESTAMP('2023-02-08 10:34:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:34:36.095Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585951 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:34:36.098Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- Column: M_InOut_Cost.Qty
-- 2023-02-08T08:34:54.262Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585952,526,0,29,542299,'Qty',TO_TIMESTAMP('2023-02-08 10:34:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Quantity','D',0,10,'The Quantity indicates the number of a specific product or item for this document.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Quantity',0,0,TO_TIMESTAMP('2023-02-08 10:34:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:34:54.263Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585952 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:34:54.267Z
/* DDL */  select update_Column_Translation_From_AD_Element(526) 
;

-- Column: M_InOut_Cost.C_Currency_ID
-- 2023-02-08T08:35:07.066Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585953,193,0,30,542299,'C_Currency_ID',TO_TIMESTAMP('2023-02-08 10:35:06','YYYY-MM-DD HH24:MI:SS'),100,'N','The Currency for this record','D',0,10,'Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Currency',0,0,TO_TIMESTAMP('2023-02-08 10:35:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:35:07.067Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585953 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:35:07.069Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Column: M_InOut_Cost.CostAmount
-- 2023-02-08T08:35:26.559Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585954,582031,0,12,542299,'CostAmount',TO_TIMESTAMP('2023-02-08 10:35:26','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cost Amount',0,0,TO_TIMESTAMP('2023-02-08 10:35:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T08:35:26.560Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585954 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T08:35:26.563Z
/* DDL */  select update_Column_Translation_From_AD_Element(582031) 
;

-- Window: Shipment/Receipt Costs, InternalName=null
-- 2023-02-08T09:49:08.213Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582043,0,541677,TO_TIMESTAMP('2023-02-08 11:49:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Shipment/Receipt Costs','N',TO_TIMESTAMP('2023-02-08 11:49:07','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-02-08T09:49:08.216Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541677 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-02-08T09:49:08.222Z
/* DDL */  select update_window_translation_from_ad_element(582043) 
;

-- 2023-02-08T09:49:08.225Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541677
;

-- 2023-02-08T09:49:08.226Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541677)
;

-- Tab: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs
-- Table: M_InOut_Cost
-- 2023-02-08T09:49:25.616Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582043,0,546813,542299,541677,'Y',TO_TIMESTAMP('2023-02-08 11:49:25','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_InOut_Cost','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Shipment/Receipt Costs','N',10,0,TO_TIMESTAMP('2023-02-08 11:49:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:25.618Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546813 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-08T09:49:25.620Z
/* DDL */  select update_tab_translation_from_ad_element(582043) 
;

-- 2023-02-08T09:49:25.625Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546813)
;

-- Window: Shipment/Receipt Costs, InternalName=M_InOut_Cost
-- 2023-02-08T09:49:30.714Z
UPDATE AD_Window SET InternalName='M_InOut_Cost',Updated=TO_TIMESTAMP('2023-02-08 11:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541677
;

-- Tab: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs
-- Table: M_InOut_Cost
-- 2023-02-08T09:49:37.990Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-08 11:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546813
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Client
-- Column: M_InOut_Cost.AD_Client_ID
-- 2023-02-08T09:49:43.781Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585937,712258,0,546813,TO_TIMESTAMP('2023-02-08 11:49:43','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-02-08 11:49:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:43.783Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:43.785Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-08T09:49:43.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712258
;

-- 2023-02-08T09:49:43.999Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712258)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Organisation
-- Column: M_InOut_Cost.AD_Org_ID
-- 2023-02-08T09:49:44.117Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585938,712259,0,546813,TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:44.118Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:44.120Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-08T09:49:44.293Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712259
;

-- 2023-02-08T09:49:44.294Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712259)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Active
-- Column: M_InOut_Cost.IsActive
-- 2023-02-08T09:49:44.423Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585941,712260,0,546813,TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:44.425Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:44.427Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-08T09:49:44.610Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712260
;

-- 2023-02-08T09:49:44.611Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712260)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Shipment/Receipt Costs
-- Column: M_InOut_Cost.M_InOut_Cost_ID
-- 2023-02-08T09:49:44.746Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585944,712261,0,546813,TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Shipment/Receipt Costs',TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:44.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:44.749Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582043) 
;

-- 2023-02-08T09:49:44.751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712261
;

-- 2023-02-08T09:49:44.752Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712261)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Shipment/ Receipt
-- Column: M_InOut_Cost.M_InOut_ID
-- 2023-02-08T09:49:44.858Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585945,712262,0,546813,TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document',10,'D','The Material Shipment / Receipt ','Y','N','N','N','N','N','N','N','Shipment/ Receipt',TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:44.859Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:44.860Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1025) 
;

-- 2023-02-08T09:49:44.865Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712262
;

-- 2023-02-08T09:49:44.865Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712262)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Receipt Line
-- Column: M_InOut_Cost.M_InOutLine_ID
-- 2023-02-08T09:49:44.974Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585946,712263,0,546813,TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100,'Line on Receipt document',10,'D','Y','N','N','N','N','N','N','N','Receipt Line',TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:44.976Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:44.977Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1026) 
;

-- 2023-02-08T09:49:44.991Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712263
;

-- 2023-02-08T09:49:44.992Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712263)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Order Cost
-- Column: M_InOut_Cost.C_Order_Cost_ID
-- 2023-02-08T09:49:45.097Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585947,712264,0,546813,TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Cost',TO_TIMESTAMP('2023-02-08 11:49:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:45.098Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:45.100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582030) 
;

-- 2023-02-08T09:49:45.102Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712264
;

-- 2023-02-08T09:49:45.103Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712264)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Order Cost Detail
-- Column: M_InOut_Cost.C_Order_Cost_Detail_ID
-- 2023-02-08T09:49:45.210Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585948,712265,0,546813,TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Order Cost Detail',TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:45.211Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712265 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:45.212Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582032) 
;

-- 2023-02-08T09:49:45.215Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712265
;

-- 2023-02-08T09:49:45.216Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712265)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Sales order
-- Column: M_InOut_Cost.C_Order_ID
-- 2023-02-08T09:49:45.325Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585949,712266,0,546813,TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Order',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Sales order',TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:45.327Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:45.329Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558) 
;

-- 2023-02-08T09:49:45.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712266
;

-- 2023-02-08T09:49:45.346Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712266)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Orderline
-- Column: M_InOut_Cost.C_OrderLine_ID
-- 2023-02-08T09:49:45.457Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585950,712267,0,546813,TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Orderline',TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:45.458Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:45.459Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2023-02-08T09:49:45.466Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712267
;

-- 2023-02-08T09:49:45.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712267)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> UOM
-- Column: M_InOut_Cost.C_UOM_ID
-- 2023-02-08T09:49:45.577Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585951,712268,0,546813,TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure',10,'D','The UOM defines a unique non monetary Unit of Measure','Y','N','N','N','N','N','N','N','UOM',TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:45.579Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:45.580Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-02-08T09:49:45.611Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712268
;

-- 2023-02-08T09:49:45.612Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712268)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Quantity
-- Column: M_InOut_Cost.Qty
-- 2023-02-08T09:49:45.698Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585952,712269,0,546813,TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Quantity',10,'D','The Quantity indicates the number of a specific product or item for this document.','Y','N','N','N','N','N','N','N','Quantity',TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:45.699Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:45.700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526) 
;

-- 2023-02-08T09:49:45.716Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712269
;

-- 2023-02-08T09:49:45.717Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712269)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Currency
-- Column: M_InOut_Cost.C_Currency_ID
-- 2023-02-08T09:49:45.828Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585953,712270,0,546813,TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:45.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:45.831Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-08T09:49:45.849Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712270
;

-- 2023-02-08T09:49:45.851Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712270)
;

-- Field: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> Cost Amount
-- Column: M_InOut_Cost.CostAmount
-- 2023-02-08T09:49:45.946Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585954,712271,0,546813,TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Amount',TO_TIMESTAMP('2023-02-08 11:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:49:45.947Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-08T09:49:45.949Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582031) 
;

-- 2023-02-08T09:49:45.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712271
;

-- 2023-02-08T09:49:45.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712271)
;

-- Tab: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D)
-- UI Section: main
-- 2023-02-08T09:50:15.774Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546813,545440,TO_TIMESTAMP('2023-02-08 11:50:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-08 11:50:15','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-08T09:50:15.775Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545440 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main
-- UI Column: 10
-- 2023-02-08T09:50:19.802Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546640,545440,TO_TIMESTAMP('2023-02-08 11:50:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-08 11:50:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10
-- UI Element Group: all
-- 2023-02-08T09:50:27.665Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546640,550365,TO_TIMESTAMP('2023-02-08 11:50:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','all',10,TO_TIMESTAMP('2023-02-08 11:50:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> all.Sales order
-- Column: M_InOut_Cost.C_Order_ID
-- 2023-02-08T09:51:07.703Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712266,0,546813,550365,615639,'F',TO_TIMESTAMP('2023-02-08 11:51:07','YYYY-MM-DD HH24:MI:SS'),100,'Order','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Sales order',10,0,0,TO_TIMESTAMP('2023-02-08 11:51:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> all.Orderline
-- Column: M_InOut_Cost.C_OrderLine_ID
-- 2023-02-08T09:51:17.901Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712267,0,546813,550365,615640,'F',TO_TIMESTAMP('2023-02-08 11:51:17','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Orderline',20,0,0,TO_TIMESTAMP('2023-02-08 11:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> all.Shipment/ Receipt
-- Column: M_InOut_Cost.M_InOut_ID
-- 2023-02-08T09:51:25.561Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712262,0,546813,550365,615641,'F',TO_TIMESTAMP('2023-02-08 11:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','Y','N','N','Shipment/ Receipt',30,0,0,TO_TIMESTAMP('2023-02-08 11:51:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> all.Receipt Line
-- Column: M_InOut_Cost.M_InOutLine_ID
-- 2023-02-08T09:51:32.956Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712263,0,546813,550365,615642,'F',TO_TIMESTAMP('2023-02-08 11:51:32','YYYY-MM-DD HH24:MI:SS'),100,'Line on Receipt document','Y','N','Y','N','N','Receipt Line',40,0,0,TO_TIMESTAMP('2023-02-08 11:51:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> all.Order Cost
-- Column: M_InOut_Cost.C_Order_Cost_ID
-- 2023-02-08T09:51:42.935Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712264,0,546813,550365,615643,'F',TO_TIMESTAMP('2023-02-08 11:51:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Order Cost',50,0,0,TO_TIMESTAMP('2023-02-08 11:51:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10 -> all.Order Cost Detail
-- Column: M_InOut_Cost.C_Order_Cost_Detail_ID
-- 2023-02-08T09:51:49.808Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712265,0,546813,550365,615644,'F',TO_TIMESTAMP('2023-02-08 11:51:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Order Cost Detail',60,0,0,TO_TIMESTAMP('2023-02-08 11:51:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 10
-- UI Element Group: references
-- 2023-02-08T09:52:06.235Z
UPDATE AD_UI_ElementGroup SET Name='references',Updated=TO_TIMESTAMP('2023-02-08 11:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550365
;

-- UI Section: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main
-- UI Column: 20
-- 2023-02-08T09:52:10.020Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546641,545440,TO_TIMESTAMP('2023-02-08 11:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-08 11:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20
-- UI Element Group: amt & qty
-- 2023-02-08T09:52:18.235Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546641,550366,TO_TIMESTAMP('2023-02-08 11:52:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','amt & qty',10,TO_TIMESTAMP('2023-02-08 11:52:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.UOM
-- Column: M_InOut_Cost.C_UOM_ID
-- 2023-02-08T09:52:27.130Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712268,0,546813,550366,615645,'F',TO_TIMESTAMP('2023-02-08 11:52:26','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Measure','The UOM defines a unique non monetary Unit of Measure','Y','N','Y','N','N','UOM',10,0,0,TO_TIMESTAMP('2023-02-08 11:52:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Quantity
-- Column: M_InOut_Cost.Qty
-- 2023-02-08T09:52:34.308Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712269,0,546813,550366,615646,'F',TO_TIMESTAMP('2023-02-08 11:52:33','YYYY-MM-DD HH24:MI:SS'),100,'Quantity','The Quantity indicates the number of a specific product or item for this document.','Y','N','Y','N','N','Quantity',20,0,0,TO_TIMESTAMP('2023-02-08 11:52:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Currency
-- Column: M_InOut_Cost.C_Currency_ID
-- 2023-02-08T09:52:41Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712270,0,546813,550366,615647,'F',TO_TIMESTAMP('2023-02-08 11:52:40','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','Currency',30,0,0,TO_TIMESTAMP('2023-02-08 11:52:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> amt & qty.Cost Amount
-- Column: M_InOut_Cost.CostAmount
-- 2023-02-08T09:52:47.893Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712271,0,546813,550366,615648,'F',TO_TIMESTAMP('2023-02-08 11:52:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Amount',40,0,0,TO_TIMESTAMP('2023-02-08 11:52:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20
-- UI Element Group: org & client
-- 2023-02-08T09:52:55.180Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546641,550367,TO_TIMESTAMP('2023-02-08 11:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','org & client',20,TO_TIMESTAMP('2023-02-08 11:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> org & client.Organisation
-- Column: M_InOut_Cost.AD_Org_ID
-- 2023-02-08T09:53:05.084Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712259,0,546813,550367,615649,'F',TO_TIMESTAMP('2023-02-08 11:53:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2023-02-08 11:53:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment/Receipt Costs(541677,D) -> Shipment/Receipt Costs(546813,D) -> main -> 20 -> org & client.Client
-- Column: M_InOut_Cost.AD_Client_ID
-- 2023-02-08T09:53:10.925Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712258,0,546813,550367,615650,'F',TO_TIMESTAMP('2023-02-08 11:53:10','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','N','N','Client',20,0,0,TO_TIMESTAMP('2023-02-08 11:53:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Shipment/Receipt Costs
-- Action Type: W
-- Window: Shipment/Receipt Costs(541677,D)
-- 2023-02-08T09:53:57.002Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582043,542053,0,541677,TO_TIMESTAMP('2023-02-08 11:53:56','YYYY-MM-DD HH24:MI:SS'),100,'D','M_InOut_Cost','Y','N','N','N','N','Shipment/Receipt Costs',TO_TIMESTAMP('2023-02-08 11:53:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-08T09:53:57.004Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542053 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-02-08T09:53:57.006Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542053, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542053)
;

-- 2023-02-08T09:53:57.020Z
/* DDL */  select update_menu_translation_from_ad_element(582043) 
;

-- Reordering children of `Costing (Freight etc)`
-- Node name: `Cost Type (C_Cost_Type)`
-- 2023-02-08T09:53:57.604Z
UPDATE AD_TreeNodeMM SET Parent_ID=542051, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Shipment/Receipt Costs`
-- 2023-02-08T09:53:57.605Z
UPDATE AD_TreeNodeMM SET Parent_ID=542051, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542053 AND AD_Tree_ID=10
;

-- 2023-02-08T09:54:07.035Z
/* DDL */ CREATE TABLE public.M_InOut_Cost (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, C_Order_Cost_Detail_ID NUMERIC(10) NOT NULL, C_Order_Cost_ID NUMERIC(10) NOT NULL, C_Order_ID NUMERIC(10) NOT NULL, C_OrderLine_ID NUMERIC(10) NOT NULL, CostAmount NUMERIC NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_InOut_Cost_ID NUMERIC(10) NOT NULL, M_InOut_ID NUMERIC(10) NOT NULL, M_InOutLine_ID NUMERIC(10) NOT NULL, Qty NUMERIC NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCurrency_MInOutCost FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrderCostDetail_MInOutCost FOREIGN KEY (C_Order_Cost_Detail_ID) REFERENCES public.C_Order_Cost_Detail DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrderCost_MInOutCost FOREIGN KEY (C_Order_Cost_ID) REFERENCES public.C_Order_Cost DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrder_MInOutCost FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT COrderLine_MInOutCost FOREIGN KEY (C_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CUOM_MInOutCost FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_InOut_Cost_Key PRIMARY KEY (M_InOut_Cost_ID), CONSTRAINT MInOut_MInOutCost FOREIGN KEY (M_InOut_ID) REFERENCES public.M_InOut DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MInOutLine_MInOutCost FOREIGN KEY (M_InOutLine_ID) REFERENCES public.M_InOutLine DEFERRABLE INITIALLY DEFERRED)
;

-- Name: M_InOut_Cost
-- 2023-02-08T11:49:50.338Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541714,TO_TIMESTAMP('2023-02-08 13:49:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_InOut_Cost',TO_TIMESTAMP('2023-02-08 13:49:49','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-02-08T11:49:50.348Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541714 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_InOut_Cost
-- Table: M_InOut_Cost
-- Key: M_InOut_Cost.M_InOut_Cost_ID
-- 2023-02-08T11:50:02.407Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,585944,0,541714,542299,TO_TIMESTAMP('2023-02-08 13:50:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-02-08 13:50:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_InOut_Cost.M_InOutLine_ID
-- 2023-02-08T11:50:31.734Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-08 13:50:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585946
;

-- Column: M_InOut_Cost.CostAmount
-- 2023-02-08T11:50:42.706Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2023-02-08 13:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585954
;

-- Column: M_InOut_Cost.Reversal_ID
-- 2023-02-08T11:51:06.036Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585955,53457,0,30,541714,542299,'Reversal_ID',TO_TIMESTAMP('2023-02-08 13:51:05','YYYY-MM-DD HH24:MI:SS'),100,'N','ID of document reversal','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reversal ID',0,0,TO_TIMESTAMP('2023-02-08 13:51:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-08T11:51:06.038Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585955 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-08T11:51:06.041Z
/* DDL */  select update_Column_Translation_From_AD_Element(53457) 
;

-- 2023-02-08T11:51:08.858Z
/* DDL */ SELECT public.db_alter_table('M_InOut_Cost','ALTER TABLE public.M_InOut_Cost ADD COLUMN Reversal_ID NUMERIC(10)')
;

-- 2023-02-08T11:51:08.864Z
ALTER TABLE M_InOut_Cost ADD CONSTRAINT Reversal_MInOutCost FOREIGN KEY (Reversal_ID) REFERENCES public.M_InOut_Cost DEFERRABLE INITIALLY DEFERRED
;

