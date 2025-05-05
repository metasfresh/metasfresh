-- Table: M_Forwarder
-- 2022-11-28T14:37:56.246Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542266,'N',TO_TIMESTAMP('2022-11-28 15:37:56','YYYY-MM-DD HH24:MI:SS'),100,'Forwarder','D','N','Y','N','N','Y','N','N','N','N','N',0,'Forwarder','NP','L','M_Forwarder','DTI',TO_TIMESTAMP('2022-11-28 15:37:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:37:56.248Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542266 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-11-28T14:37:56.315Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556058,TO_TIMESTAMP('2022-11-28 15:37:56','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Forwarder',1,'Y','N','Y','Y','M_Forwarder','N',1000000,TO_TIMESTAMP('2022-11-28 15:37:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:37:56.322Z
CREATE SEQUENCE M_FORWARDER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Forwarder.AD_Client_ID
-- 2022-11-28T14:38:28.851Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585151,102,0,19,542266,'AD_Client_ID',TO_TIMESTAMP('2022-11-28 15:38:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Client',0,0,TO_TIMESTAMP('2022-11-28 15:38:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:28.853Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585151 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:28.873Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Forwarder.AD_Org_ID
-- 2022-11-28T14:38:29.539Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585152,113,0,30,542266,'AD_Org_ID',TO_TIMESTAMP('2022-11-28 15:38:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Organisation',10,0,TO_TIMESTAMP('2022-11-28 15:38:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:29.541Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585152 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:29.543Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Forwarder.AD_Table_ID
-- 2022-11-28T14:38:30.136Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585153,126,0,19,542266,'AD_Table_ID',TO_TIMESTAMP('2022-11-28 15:38:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Table',0,0,TO_TIMESTAMP('2022-11-28 15:38:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:30.138Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585153 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:30.139Z
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- Column: M_Forwarder.CM_Template_ID
-- 2022-11-28T14:38:30.603Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585154,2978,0,19,542266,'CM_Template_ID',TO_TIMESTAMP('2022-11-28 15:38:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',0,10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','Template',0,0,TO_TIMESTAMP('2022-11-28 15:38:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:30.604Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585154 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:30.606Z
/* DDL */  select update_Column_Translation_From_AD_Element(2978) 
;

-- 2022-11-28T14:38:30.922Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581762,0,'M_Forwarder_ID',TO_TIMESTAMP('2022-11-28 15:38:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Forwarder','Forwarder',TO_TIMESTAMP('2022-11-28 15:38:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:38:30.923Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581762 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Forwarder.M_Forwarder_ID
-- 2022-11-28T14:38:31.365Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585155,581762,0,13,542266,'M_Forwarder_ID',TO_TIMESTAMP('2022-11-28 15:38:30','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','Forwarder',0,0,TO_TIMESTAMP('2022-11-28 15:38:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:31.366Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585155 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:31.368Z
/* DDL */  select update_Column_Translation_From_AD_Element(581762) 
;

-- Column: M_Forwarder.Created
-- 2022-11-28T14:38:31.833Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585156,245,0,16,542266,'Created',TO_TIMESTAMP('2022-11-28 15:38:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,7,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created',0,0,TO_TIMESTAMP('2022-11-28 15:38:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:31.835Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585156 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:31.836Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Forwarder.CreatedBy
-- 2022-11-28T14:38:32.462Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585157,246,0,18,110,542266,'CreatedBy',TO_TIMESTAMP('2022-11-28 15:38:32','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created By',0,0,TO_TIMESTAMP('2022-11-28 15:38:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:32.463Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585157 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:32.465Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Forwarder.Description
-- 2022-11-28T14:38:33.079Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585158,275,0,10,542266,'Description',TO_TIMESTAMP('2022-11-28 15:38:32','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Description',0,0,TO_TIMESTAMP('2022-11-28 15:38:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:33.080Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585158 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:33.081Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: M_Forwarder.IsActive
-- 2022-11-28T14:38:33.592Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585159,348,0,20,542266,'IsActive',TO_TIMESTAMP('2022-11-28 15:38:33','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Active',0,0,TO_TIMESTAMP('2022-11-28 15:38:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:33.593Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585159 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:33.595Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Forwarder.Name
-- 2022-11-28T14:38:34.222Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585160,469,0,10,542266,'Name',TO_TIMESTAMP('2022-11-28 15:38:33','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,120,'E','','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Name',10,1,TO_TIMESTAMP('2022-11-28 15:38:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:34.223Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585160 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:34.225Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: M_Forwarder.OtherClause
-- 2022-11-28T14:38:34.725Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585161,2642,0,14,542266,'OtherClause',TO_TIMESTAMP('2022-11-28 15:38:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',0,2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,0,TO_TIMESTAMP('2022-11-28 15:38:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:34.726Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585161 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:34.728Z
/* DDL */  select update_Column_Translation_From_AD_Element(2642) 
;

-- Column: M_Forwarder.Updated
-- 2022-11-28T14:38:35.194Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585162,607,0,16,542266,'Updated',TO_TIMESTAMP('2022-11-28 15:38:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,7,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated',0,0,TO_TIMESTAMP('2022-11-28 15:38:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:35.195Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585162 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:35.197Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Forwarder.UpdatedBy
-- 2022-11-28T14:38:35.733Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585163,608,0,18,110,542266,'UpdatedBy',TO_TIMESTAMP('2022-11-28 15:38:35','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated By',0,0,TO_TIMESTAMP('2022-11-28 15:38:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:35.735Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585163 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:35.737Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: M_Forwarder.WhereClause
-- 2022-11-28T14:38:36.281Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585164,630,0,14,542266,'WhereClause',TO_TIMESTAMP('2022-11-28 15:38:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',0,2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SQL WHERE',0,0,TO_TIMESTAMP('2022-11-28 15:38:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:38:36.283Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585164 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:38:36.285Z
/* DDL */  select update_Column_Translation_From_AD_Element(630) 
;

-- 2022-11-28T14:38:36.616Z
/* DDL */ CREATE TABLE public.M_Forwarder (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, CM_Template_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Forwarder_ID NUMERIC(10) NOT NULL, Name VARCHAR(120) NOT NULL, OtherClause VARCHAR(2000), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, WhereClause VARCHAR(2000), CONSTRAINT ADTable_MForwarder FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CMTemplate_MForwarder FOREIGN KEY (CM_Template_ID) REFERENCES public.CM_Template DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Forwarder_Key PRIMARY KEY (M_Forwarder_ID))
;

-- 2022-11-28T14:38:36.650Z
INSERT INTO t_alter_column values('m_forwarder','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2022-11-28T14:38:36.657Z
INSERT INTO t_alter_column values('m_forwarder','AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2022-11-28T14:38:36.665Z
INSERT INTO t_alter_column values('m_forwarder','CM_Template_ID','NUMERIC(10)',null,null)
;

-- 2022-11-28T14:38:36.671Z
INSERT INTO t_alter_column values('m_forwarder','M_Forwarder_ID','NUMERIC(10)',null,null)
;

-- 2022-11-28T14:38:36.678Z
INSERT INTO t_alter_column values('m_forwarder','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-11-28T14:38:36.685Z
INSERT INTO t_alter_column values('m_forwarder','CreatedBy','NUMERIC(10)',null,null)
;

-- 2022-11-28T14:38:36.690Z
INSERT INTO t_alter_column values('m_forwarder','Description','VARCHAR(255)',null,null)
;

-- 2022-11-28T14:38:36.694Z
INSERT INTO t_alter_column values('m_forwarder','IsActive','CHAR(1)',null,null)
;

-- 2022-11-28T14:38:36.710Z
INSERT INTO t_alter_column values('m_forwarder','Name','VARCHAR(120)',null,null)
;

-- 2022-11-28T14:38:36.717Z
INSERT INTO t_alter_column values('m_forwarder','OtherClause','VARCHAR(2000)',null,null)
;

-- 2022-11-28T14:38:36.722Z
INSERT INTO t_alter_column values('m_forwarder','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-11-28T14:38:36.727Z
INSERT INTO t_alter_column values('m_forwarder','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2022-11-28T14:38:36.732Z
INSERT INTO t_alter_column values('m_forwarder','WhereClause','VARCHAR(2000)',null,null)
;

-- Column: M_Forwarder.WhereClause
-- 2022-11-28T14:46:11.162Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585164
;

-- 2022-11-28T14:46:11.164Z
DELETE FROM AD_Column WHERE AD_Column_ID=585164
;

-- Column: M_Forwarder.AD_Table_ID
-- 2022-11-28T14:46:24.890Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585153
;

-- 2022-11-28T14:46:24.893Z
DELETE FROM AD_Column WHERE AD_Column_ID=585153
;

-- Column: M_Forwarder.CM_Template_ID
-- 2022-11-28T14:46:32.372Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585154
;

-- 2022-11-28T14:46:32.374Z
DELETE FROM AD_Column WHERE AD_Column_ID=585154
;

-- Column: M_Forwarder.OtherClause
-- 2022-11-28T14:46:40.128Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585161
;

-- 2022-11-28T14:46:40.130Z
DELETE FROM AD_Column WHERE AD_Column_ID=585161
;


-- Element: Forwarder
-- 2022-11-28T14:51:06.108Z
UPDATE AD_Element_Trl SET Name='Spediteur', PrintName='Spediteur',Updated=TO_TIMESTAMP('2022-11-28 15:51:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581696 AND AD_Language='de_DE'
;

-- 2022-11-28T14:51:06.127Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581696,'de_DE') 
;

-- Element: Forwarder
-- 2022-11-28T14:51:08.606Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 15:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581696 AND AD_Language='de_DE'
;

-- 2022-11-28T14:51:08.608Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581696,'de_DE') 
;

-- Element: Forwarder
-- 2022-11-28T14:51:25.919Z
UPDATE AD_Element_Trl SET Name='Spediteur', PrintName='Spediteur',Updated=TO_TIMESTAMP('2022-11-28 15:51:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581696 AND AD_Language='de_CH'
;

-- 2022-11-28T14:51:25.921Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581696,'de_CH') 
;

-- Element: Forwarder
-- 2022-11-28T14:51:30.584Z
UPDATE AD_Element_Trl SET Name='Spediteur', PrintName='Spediteur',Updated=TO_TIMESTAMP('2022-11-28 15:51:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581696 AND AD_Language='nl_NL'
;

-- 2022-11-28T14:51:30.586Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581696,'nl_NL') 
;

-- Window: Forwarder, InternalName=null
-- 2022-11-28T14:52:09.297Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581696,0,541634,TO_TIMESTAMP('2022-11-28 15:52:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Forwarder','N',TO_TIMESTAMP('2022-11-28 15:52:09','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-11-28T14:52:09.298Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541634 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-11-28T14:52:09.300Z
/* DDL */  select update_window_translation_from_ad_element(581696) 
;

-- 2022-11-28T14:52:09.306Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541634
;

-- 2022-11-28T14:52:09.306Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541634)
;

-- Tab: Forwarder(541634,D) -> Forwarder
-- Table: M_Forwarder
-- 2022-11-28T14:53:18.772Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581762,0,546684,542266,541634,'Y',TO_TIMESTAMP('2022-11-28 15:53:18','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_Forwarder','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Forwarder','N',10,0,TO_TIMESTAMP('2022-11-28 15:53:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T14:53:18.774Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546684 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-28T14:53:18.775Z
/* DDL */  select update_tab_translation_from_ad_element(581762) 
;

-- 2022-11-28T14:53:18.780Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546684)
;

-- Column: M_Forwarder.Value
-- 2022-11-28T14:54:22.491Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585165,620,0,10,542266,'Value',TO_TIMESTAMP('2022-11-28 15:54:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Search key for the record in the format required - must be unique','D',0,60,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y',0,'Search Key',0,0,TO_TIMESTAMP('2022-11-28 15:54:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:54:22.492Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585165 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:54:22.494Z
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- 2022-11-28T14:54:28.024Z
/* DDL */ SELECT public.db_alter_table('M_Forwarder','ALTER TABLE public.M_Forwarder ADD COLUMN Value VARCHAR(60)')
;

-- Column: M_Forwarder.Value
-- 2022-11-28T14:54:34.840Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-11-28 15:54:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585165
;

-- Column: M_Forwarder.Value
-- 2022-11-28T14:54:54.834Z
UPDATE AD_Column SET IsCalculated='N', IsUseDocSequence='N',Updated=TO_TIMESTAMP('2022-11-28 15:54:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585165
;

-- 2022-11-28T14:55:02.194Z
INSERT INTO t_alter_column values('m_forwarder','Value','VARCHAR(60)',null,null)
;

-- 2022-11-28T14:55:02.196Z
INSERT INTO t_alter_column values('m_forwarder','Value',null,'NOT NULL',null)
;

-- Column: M_Forwarder.M_SectionCode_ID
-- 2022-11-28T14:55:49.725Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585166,581238,0,19,542266,'M_SectionCode_ID',TO_TIMESTAMP('2022-11-28 15:55:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-11-28 15:55:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:55:49.726Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585166 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:55:49.728Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: M_Forwarder.M_SectionCode_ID
-- 2022-11-28T14:56:06.720Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 15:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585166
;

-- Column: M_Forwarder.Value
-- 2022-11-28T14:56:28.060Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 15:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585165
;

-- 2022-11-28T14:56:59.673Z
/* DDL */ SELECT public.db_alter_table('M_Forwarder','ALTER TABLE public.M_Forwarder ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-11-28T14:56:59.679Z
ALTER TABLE M_Forwarder ADD CONSTRAINT MSectionCode_MForwarder FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Forwarder.C_BPartner_ID
-- 2022-11-28T14:59:17.746Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585167,187,0,30,192,542266,'C_BPartner_ID',TO_TIMESTAMP('2022-11-28 15:59:17','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Business Partner',0,0,TO_TIMESTAMP('2022-11-28 15:59:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T14:59:17.747Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585167 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T14:59:17.749Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: M_Forwarder.C_BPartner_ID
-- 2022-11-28T14:59:28.956Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 15:59:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585167
;

-- 2022-11-28T14:59:34.648Z
/* DDL */ SELECT public.db_alter_table('M_Forwarder','ALTER TABLE public.M_Forwarder ADD COLUMN C_BPartner_ID NUMERIC(10) NOT NULL')
;

-- 2022-11-28T14:59:34.654Z
ALTER TABLE M_Forwarder ADD CONSTRAINT CBPartner_MForwarder FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Client
-- Column: M_Forwarder.AD_Client_ID
-- 2022-11-28T15:00:46.295Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585151,708245,0,546684,TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:46.296Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708245 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:46.298Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-28T15:00:46.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708245
;

-- 2022-11-28T15:00:46.528Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708245)
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Organisation
-- Column: M_Forwarder.AD_Org_ID
-- 2022-11-28T15:00:46.599Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585152,708246,0,546684,TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:46.600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708246 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:46.601Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-28T15:00:46.783Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708246
;

-- 2022-11-28T15:00:46.783Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708246)
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Forwarder
-- Column: M_Forwarder.M_Forwarder_ID
-- 2022-11-28T15:00:46.843Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585155,708247,0,546684,TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Forwarder',TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:46.844Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708247 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:46.845Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581762) 
;

-- 2022-11-28T15:00:46.848Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708247
;

-- 2022-11-28T15:00:46.849Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708247)
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Description
-- Column: M_Forwarder.Description
-- 2022-11-28T15:00:46.912Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585158,708248,0,546684,TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Description',TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:46.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708248 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:46.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-11-28T15:00:46.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708248
;

-- 2022-11-28T15:00:46.959Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708248)
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Active
-- Column: M_Forwarder.IsActive
-- 2022-11-28T15:00:47.023Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585159,708249,0,546684,TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2022-11-28 16:00:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:47.024Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708249 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:47.025Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-28T15:00:47.210Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708249
;

-- 2022-11-28T15:00:47.211Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708249)
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Name
-- Column: M_Forwarder.Name
-- 2022-11-28T15:00:47.274Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585160,708250,0,546684,TO_TIMESTAMP('2022-11-28 16:00:47','YYYY-MM-DD HH24:MI:SS'),100,'',120,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-11-28 16:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:47.275Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708250 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:47.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-11-28T15:00:47.317Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708250
;

-- 2022-11-28T15:00:47.317Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708250)
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Search Key
-- Column: M_Forwarder.Value
-- 2022-11-28T15:00:47.372Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585165,708251,0,546684,TO_TIMESTAMP('2022-11-28 16:00:47','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique',60,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Search Key',TO_TIMESTAMP('2022-11-28 16:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:47.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708251 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:47.374Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2022-11-28T15:00:47.394Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708251
;

-- 2022-11-28T15:00:47.395Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708251)
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Section Code
-- Column: M_Forwarder.M_SectionCode_ID
-- 2022-11-28T15:00:47.447Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585166,708252,0,546684,TO_TIMESTAMP('2022-11-28 16:00:47','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-11-28 16:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:47.448Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708252 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:47.449Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-11-28T15:00:47.456Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708252
;

-- 2022-11-28T15:00:47.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708252)
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Business Partner
-- Column: M_Forwarder.C_BPartner_ID
-- 2022-11-28T15:00:47.517Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585167,708253,0,546684,TO_TIMESTAMP('2022-11-28 16:00:47','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2022-11-28 16:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:00:47.518Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708253 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T15:00:47.519Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2022-11-28T15:00:47.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708253
;

-- 2022-11-28T15:00:47.530Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708253)
;

-- 2022-11-28T15:01:26.073Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708247
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Forwarder
-- Column: M_Forwarder.M_Forwarder_ID
-- 2022-11-28T15:01:26.074Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708247
;

-- 2022-11-28T15:01:26.076Z
DELETE FROM AD_Field WHERE AD_Field_ID=708247
;

-- Tab: Forwarder(541634,D) -> Forwarder(546684,D)
-- UI Section: primary
-- 2022-11-28T15:01:55.937Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546684,545308,TO_TIMESTAMP('2022-11-28 16:01:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-28 16:01:55','YYYY-MM-DD HH24:MI:SS'),100,'primary')
;

-- 2022-11-28T15:01:55.938Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545308 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Forwarder(541634,D) -> Forwarder(546684,D) -> primary
-- UI Column: 10
-- 2022-11-28T15:02:05.399Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546472,545308,TO_TIMESTAMP('2022-11-28 16:02:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-28 16:02:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Forwarder(541634,D) -> Forwarder(546684,D) -> primary
-- UI Column: 20
-- 2022-11-28T15:02:13.967Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546473,545308,TO_TIMESTAMP('2022-11-28 16:02:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-28 16:02:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10
-- UI Element Group: main
-- 2022-11-28T15:02:24.399Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546472,550060,TO_TIMESTAMP('2022-11-28 16:02:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-11-28 16:02:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Forwarder(541634,D) -> Forwarder
-- Table: M_Forwarder
-- 2022-11-28T15:04:00.515Z
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546684
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Description
-- Column: M_Forwarder.Description
-- 2022-11-28T15:04:21.364Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708248
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Active
-- Column: M_Forwarder.IsActive
-- 2022-11-28T15:04:28.075Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708249
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Name
-- Column: M_Forwarder.Name
-- 2022-11-28T15:04:32.335Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708250
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Search Key
-- Column: M_Forwarder.Value
-- 2022-11-28T15:04:36.049Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708251
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Section Code
-- Column: M_Forwarder.M_SectionCode_ID
-- 2022-11-28T15:04:41.177Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708252
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Business Partner
-- Column: M_Forwarder.C_BPartner_ID
-- 2022-11-28T15:04:45.584Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708253
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Organisation
-- Column: M_Forwarder.AD_Org_ID
-- 2022-11-28T15:04:52.070Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708246
;

-- Field: Forwarder(541634,D) -> Forwarder(546684,D) -> Client
-- Column: M_Forwarder.AD_Client_ID
-- 2022-11-28T15:04:55.924Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2022-11-28 16:04:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708245
;

-- Table: M_Forwarder
-- 2022-11-28T15:05:34.094Z
UPDATE AD_Table SET AD_Window_ID=541634,Updated=TO_TIMESTAMP('2022-11-28 16:05:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542266
;

-- Table: M_Forwarder
-- 2022-11-28T15:05:42.135Z
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2022-11-28 16:05:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542266
;


-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Search Key
-- Column: M_Forwarder.Value
-- 2022-11-28T15:09:33.386Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708251,0,546684,550060,613620,'F',TO_TIMESTAMP('2022-11-28 16:09:33','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Search Key',10,0,0,TO_TIMESTAMP('2022-11-28 16:09:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Name
-- Column: M_Forwarder.Name
-- 2022-11-28T15:09:43.955Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708250,0,546684,550060,613621,'F',TO_TIMESTAMP('2022-11-28 16:09:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2022-11-28 16:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Business Partner
-- Column: M_Forwarder.C_BPartner_ID
-- 2022-11-28T15:09:58.291Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708253,0,546684,550060,613622,'F',TO_TIMESTAMP('2022-11-28 16:09:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Business Partner',30,0,0,TO_TIMESTAMP('2022-11-28 16:09:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Section Code
-- Column: M_Forwarder.M_SectionCode_ID
-- 2022-11-28T15:10:08.643Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708252,0,546684,550060,613623,'F',TO_TIMESTAMP('2022-11-28 16:10:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',40,0,0,TO_TIMESTAMP('2022-11-28 16:10:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20
-- UI Element Group: flags
-- 2022-11-28T15:10:27.704Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546473,550062,TO_TIMESTAMP('2022-11-28 16:10:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-11-28 16:10:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> flags.Active
-- Column: M_Forwarder.IsActive
-- 2022-11-28T15:10:36.213Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708249,0,546684,550062,613624,'F',TO_TIMESTAMP('2022-11-28 16:10:36','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2022-11-28 16:10:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20
-- UI Element Group: client
-- 2022-11-28T15:10:44.626Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546473,550063,TO_TIMESTAMP('2022-11-28 16:10:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','client',20,TO_TIMESTAMP('2022-11-28 16:10:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Organisation
-- Column: M_Forwarder.AD_Org_ID
-- 2022-11-28T15:11:14.987Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708246,0,546684,550063,613625,'F',TO_TIMESTAMP('2022-11-28 16:11:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2022-11-28 16:11:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Client
-- Column: M_Forwarder.AD_Client_ID
-- 2022-11-28T15:11:23.394Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708245,0,546684,550063,613626,'F',TO_TIMESTAMP('2022-11-28 16:11:23','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2022-11-28 16:11:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Search Key
-- Column: M_Forwarder.Value
-- 2022-11-28T15:12:00.188Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-11-28 16:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613620
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Name
-- Column: M_Forwarder.Name
-- 2022-11-28T15:12:00.193Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-11-28 16:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613621
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Business Partner
-- Column: M_Forwarder.C_BPartner_ID
-- 2022-11-28T15:12:00.196Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-11-28 16:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613622
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> flags.Active
-- Column: M_Forwarder.IsActive
-- 2022-11-28T15:12:00.199Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-28 16:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613624
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Client
-- Column: M_Forwarder.AD_Client_ID
-- 2022-11-28T15:12:00.203Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-28 16:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613626
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Organisation
-- Column: M_Forwarder.AD_Org_ID
-- 2022-11-28T15:12:00.206Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-11-28 16:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613625
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Section Code
-- Column: M_Forwarder.M_SectionCode_ID
-- 2022-11-28T15:12:00.209Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-11-28 16:12:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613623
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> main.Section Code
-- Column: M_Forwarder.M_SectionCode_ID
-- 2022-11-28T15:12:08.476Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-11-28 16:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613623
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> flags.Active
-- Column: M_Forwarder.IsActive
-- 2022-11-28T15:12:08.480Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-28 16:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613624
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Client
-- Column: M_Forwarder.AD_Client_ID
-- 2022-11-28T15:12:08.484Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-11-28 16:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613626
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Organisation
-- Column: M_Forwarder.AD_Org_ID
-- 2022-11-28T15:12:08.487Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-11-28 16:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613625
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Organisation
-- Column: M_Forwarder.AD_Org_ID
-- 2022-11-28T15:12:13.487Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-11-28 16:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613625
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Client
-- Column: M_Forwarder.AD_Client_ID
-- 2022-11-28T15:12:13.489Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-11-28 16:12:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613626
;

-- Name: Forwarder
-- Action Type: W
-- Window: Forwarder(541634,D)
-- 2022-11-28T15:15:26.700Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581696,542023,0,541634,TO_TIMESTAMP('2022-11-28 16:15:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Forwarder','Y','N','N','N','N','Forwarder',TO_TIMESTAMP('2022-11-28 16:15:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T15:15:26.701Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542023 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-11-28T15:15:26.703Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542023, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542023)
;

-- 2022-11-28T15:15:26.724Z
/* DDL */  select update_menu_translation_from_ad_element(581696) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2022-11-28T15:15:27.278Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2022-11-28T15:15:27.278Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2022-11-28T15:15:27.279Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2022-11-28T15:15:27.279Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2022-11-28T15:15:27.280Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2022-11-28T15:15:27.280Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2022-11-28T15:15:27.281Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2022-11-28T15:15:27.281Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2022-11-28T15:15:27.282Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2022-11-28T15:15:27.283Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2022-11-28T15:15:27.283Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2022-11-28T15:15:27.284Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing`
-- 2022-11-28T15:15:27.284Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning`
-- 2022-11-28T15:15:27.285Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2022-11-28T15:15:27.285Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2022-11-28T15:15:27.286Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2022-11-28T15:15:27.286Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2022-11-28T15:15:27.286Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2022-11-28T15:15:27.287Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2022-11-28T15:15:27.287Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2022-11-28T15:15:27.288Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2022-11-28T15:15:27.288Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2022-11-28T15:15:27.288Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2022-11-28T15:15:27.289Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-11-28T15:15:27.289Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-11-28T15:15:27.289Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-11-28T15:15:27.290Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2022-11-28T15:15:27.290Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2022-11-28T15:15:27.290Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2022-11-28T15:15:27.291Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Forwarder`
-- 2022-11-28T15:15:27.291Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542023 AND AD_Tree_ID=10
;

-- Element: Forwarder
-- 2022-11-28T15:15:47.640Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Spediteur',Updated=TO_TIMESTAMP('2022-11-28 16:15:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581696 AND AD_Language='de_CH'
;

-- 2022-11-28T15:15:47.641Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581696,'de_CH') 
;

-- Element: Forwarder
-- 2022-11-28T15:15:53.684Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Spediteur',Updated=TO_TIMESTAMP('2022-11-28 16:15:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581696 AND AD_Language='de_DE'
;

-- 2022-11-28T15:15:53.686Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581696,'de_DE') 
;

-- Element: Forwarder
-- 2022-11-28T15:16:02.745Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Forwarder',Updated=TO_TIMESTAMP('2022-11-28 16:16:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581696 AND AD_Language='en_US'
;

-- 2022-11-28T15:16:02.747Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581696,'en_US') 
;

-- 2022-11-28T15:16:02.748Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581696,'en_US') 
;

-- Element: Forwarder
-- 2022-11-28T15:16:08.964Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Spediteur',Updated=TO_TIMESTAMP('2022-11-28 16:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581696 AND AD_Language='nl_NL'
;

-- 2022-11-28T15:16:08.965Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581696,'nl_NL') 
;

-- UI Column: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10
-- UI Element Group: main
-- 2022-11-28T15:20:03.645Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2022-11-28 16:20:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550060
;

-- UI Column: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10
-- UI Element Group: section_code
-- 2022-11-28T15:21:14.565Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546472,550064,TO_TIMESTAMP('2022-11-28 16:21:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','section_code',20,TO_TIMESTAMP('2022-11-28 16:21:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> section_code.Section Code
-- Column: M_Forwarder.M_SectionCode_ID
-- 2022-11-28T15:21:45.175Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550064, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-28 16:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613623
;

-- UI Column: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10
-- UI Element Group: desc
-- 2022-11-28T15:23:39.033Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546472,550065,TO_TIMESTAMP('2022-11-28 16:23:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','desc',30,TO_TIMESTAMP('2022-11-28 16:23:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> desc.Description
-- Column: M_Forwarder.Description
-- 2022-11-28T15:23:50.813Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708248,0,546684,550065,613630,'F',TO_TIMESTAMP('2022-11-28 16:23:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Description',10,0,0,TO_TIMESTAMP('2022-11-28 16:23:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_Forwarder.Value
-- 2022-11-28T15:25:41.758Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2022-11-28 16:25:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585165
;

-- 2022-11-28T15:29:35.368Z
INSERT INTO t_alter_column values('m_forwarder','Value','VARCHAR(60)',null,null)
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 10 -> desc.Description
-- Column: M_Forwarder.Description
-- 2022-11-28T15:33:20.731Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-11-28 16:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613630
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> flags.Active
-- Column: M_Forwarder.IsActive
-- 2022-11-28T15:33:20.734Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-11-28 16:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613624
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Organisation
-- Column: M_Forwarder.AD_Org_ID
-- 2022-11-28T15:33:20.737Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-11-28 16:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613625
;

-- UI Element: Forwarder(541634,D) -> Forwarder(546684,D) -> primary -> 20 -> client.Client
-- Column: M_Forwarder.AD_Client_ID
-- 2022-11-28T15:33:20.739Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-11-28 16:33:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613626
;

-- Column: M_Forwarder.C_BPartner_ID
-- 2022-11-29T20:29:36.178Z
UPDATE AD_Column SET AD_Reference_Value_ID=541252,Updated=TO_TIMESTAMP('2022-11-29 21:29:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585167
;

