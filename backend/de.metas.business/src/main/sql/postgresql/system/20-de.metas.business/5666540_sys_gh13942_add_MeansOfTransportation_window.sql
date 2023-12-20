-- Table: M_MeansOfTransportation
-- 2022-11-28T17:22:44.985Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542268,'N',TO_TIMESTAMP('2022-11-28 18:22:44','YYYY-MM-DD HH24:MI:SS'),100,'Means of Transportation','D','N','Y','N','N','Y','N','N','N','N','N',0,'Means of Transportation','NP','L','M_MeansOfTransportation','DTI',TO_TIMESTAMP('2022-11-28 18:22:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T17:22:44.986Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542268 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-11-28T17:22:45.051Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556060,TO_TIMESTAMP('2022-11-28 18:22:44','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_MeansOfTransportation',1,'Y','N','Y','Y','M_MeansOfTransportation','N',1000000,TO_TIMESTAMP('2022-11-28 18:22:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T17:22:45.059Z
CREATE SEQUENCE M_MEANSOFTRANSPORTATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: M_MeansOfTransportation
-- 2022-11-28T17:22:56.498Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2022-11-28 18:22:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542268
;

-- Column: M_MeansOfTransportation.AD_Client_ID
-- 2022-11-28T17:28:04.539Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585179,102,0,19,542268,'AD_Client_ID',TO_TIMESTAMP('2022-11-28 18:28:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Client/Tenant for this installation.','D',0,10,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Client',0,0,TO_TIMESTAMP('2022-11-28 18:28:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:04.540Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585179 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:04.564Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_MeansOfTransportation.AD_Org_ID
-- 2022-11-28T17:28:05.233Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585180,113,0,30,542268,'AD_Org_ID',TO_TIMESTAMP('2022-11-28 18:28:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisational entity within client','D',0,10,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Organisation',10,0,TO_TIMESTAMP('2022-11-28 18:28:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:05.234Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585180 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:05.236Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_MeansOfTransportation.AD_Table_ID
-- 2022-11-28T17:28:05.859Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585181,126,0,19,542268,'AD_Table_ID',TO_TIMESTAMP('2022-11-28 18:28:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Table',0,0,TO_TIMESTAMP('2022-11-28 18:28:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:05.861Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585181 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:05.863Z
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- Column: M_MeansOfTransportation.CM_Template_ID
-- 2022-11-28T17:28:06.365Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585182,2978,0,19,542268,'CM_Template_ID',TO_TIMESTAMP('2022-11-28 18:28:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',0,10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','Template',0,0,TO_TIMESTAMP('2022-11-28 18:28:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:06.366Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585182 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:06.368Z
/* DDL */  select update_Column_Translation_From_AD_Element(2978) 
;

-- 2022-11-28T17:28:06.693Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581776,0,'M_MeansOfTransportation_ID',TO_TIMESTAMP('2022-11-28 18:28:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Means of Transportation','Means of Transportation',TO_TIMESTAMP('2022-11-28 18:28:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T17:28:06.694Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581776 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_MeansOfTransportation.M_MeansOfTransportation_ID
-- 2022-11-28T17:28:07.362Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585183,581776,0,13,542268,'M_MeansOfTransportation_ID',TO_TIMESTAMP('2022-11-28 18:28:06','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','Means of Transportation',0,0,TO_TIMESTAMP('2022-11-28 18:28:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:07.363Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585183 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:07.365Z
/* DDL */  select update_Column_Translation_From_AD_Element(581776) 
;

-- Column: M_MeansOfTransportation.Created
-- 2022-11-28T17:28:07.840Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585184,245,0,16,542268,'Created',TO_TIMESTAMP('2022-11-28 18:28:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','D',0,7,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created',0,0,TO_TIMESTAMP('2022-11-28 18:28:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:07.841Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585184 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:07.843Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_MeansOfTransportation.CreatedBy
-- 2022-11-28T17:28:08.415Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585185,246,0,18,110,542268,'CreatedBy',TO_TIMESTAMP('2022-11-28 18:28:08','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','D',0,10,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created By',0,0,TO_TIMESTAMP('2022-11-28 18:28:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:08.416Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585185 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:08.418Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_MeansOfTransportation.Description
-- 2022-11-28T17:28:08.969Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585186,275,0,10,542268,'Description',TO_TIMESTAMP('2022-11-28 18:28:08','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,255,'','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Description',0,0,TO_TIMESTAMP('2022-11-28 18:28:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:08.970Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585186 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:08.972Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: M_MeansOfTransportation.IsActive
-- 2022-11-28T17:28:09.472Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585187,348,0,20,542268,'IsActive',TO_TIMESTAMP('2022-11-28 18:28:09','YYYY-MM-DD HH24:MI:SS'),100,'N','The record is active in the system','D',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Active',0,0,TO_TIMESTAMP('2022-11-28 18:28:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:09.473Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585187 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:09.475Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_MeansOfTransportation.Name
-- 2022-11-28T17:28:10.130Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585188,469,0,10,542268,'Name',TO_TIMESTAMP('2022-11-28 18:28:09','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,120,'E','','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Name',10,1,TO_TIMESTAMP('2022-11-28 18:28:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:10.131Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585188 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:10.133Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: M_MeansOfTransportation.OtherClause
-- 2022-11-28T17:28:10.614Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585189,2642,0,14,542268,'OtherClause',TO_TIMESTAMP('2022-11-28 18:28:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',0,2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,0,TO_TIMESTAMP('2022-11-28 18:28:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:10.616Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585189 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:10.617Z
/* DDL */  select update_Column_Translation_From_AD_Element(2642) 
;

-- Column: M_MeansOfTransportation.Updated
-- 2022-11-28T17:28:11.099Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585190,607,0,16,542268,'Updated',TO_TIMESTAMP('2022-11-28 18:28:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','D',0,7,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated',0,0,TO_TIMESTAMP('2022-11-28 18:28:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:11.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585190 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:11.102Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_MeansOfTransportation.UpdatedBy
-- 2022-11-28T17:28:11.682Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585191,608,0,18,110,542268,'UpdatedBy',TO_TIMESTAMP('2022-11-28 18:28:11','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','D',0,10,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated By',0,0,TO_TIMESTAMP('2022-11-28 18:28:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:11.683Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585191 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:11.684Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: M_MeansOfTransportation.WhereClause
-- 2022-11-28T17:28:12.239Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585192,630,0,14,542268,'WhereClause',TO_TIMESTAMP('2022-11-28 18:28:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',0,2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','SQL WHERE',0,0,TO_TIMESTAMP('2022-11-28 18:28:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:28:12.240Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585192 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:28:12.242Z
/* DDL */  select update_Column_Translation_From_AD_Element(630) 
;

-- 2022-11-28T17:28:12.572Z
/* DDL */ CREATE TABLE public.M_MeansOfTransportation (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_Table_ID NUMERIC(10) NOT NULL, CM_Template_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(255), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_MeansOfTransportation_ID NUMERIC(10) NOT NULL, Name VARCHAR(120) NOT NULL, OtherClause VARCHAR(2000), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, WhereClause VARCHAR(2000), CONSTRAINT ADTable_MMeansOfTransportation FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CMTemplate_MMeansOfTransportation FOREIGN KEY (CM_Template_ID) REFERENCES public.CM_Template DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_MeansOfTransportation_Key PRIMARY KEY (M_MeansOfTransportation_ID))
;

-- 2022-11-28T17:28:12.609Z
INSERT INTO t_alter_column values('m_meansoftransportation','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2022-11-28T17:28:12.618Z
INSERT INTO t_alter_column values('m_meansoftransportation','AD_Table_ID','NUMERIC(10)',null,null)
;

-- 2022-11-28T17:28:12.626Z
INSERT INTO t_alter_column values('m_meansoftransportation','CM_Template_ID','NUMERIC(10)',null,null)
;

-- 2022-11-28T17:28:12.632Z
INSERT INTO t_alter_column values('m_meansoftransportation','M_MeansOfTransportation_ID','NUMERIC(10)',null,null)
;

-- 2022-11-28T17:28:12.638Z
INSERT INTO t_alter_column values('m_meansoftransportation','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-11-28T17:28:12.657Z
INSERT INTO t_alter_column values('m_meansoftransportation','CreatedBy','NUMERIC(10)',null,null)
;

-- 2022-11-28T17:28:12.663Z
INSERT INTO t_alter_column values('m_meansoftransportation','Description','VARCHAR(255)',null,null)
;

-- 2022-11-28T17:28:12.669Z
INSERT INTO t_alter_column values('m_meansoftransportation','IsActive','CHAR(1)',null,null)
;

-- 2022-11-28T17:28:12.685Z
INSERT INTO t_alter_column values('m_meansoftransportation','Name','VARCHAR(120)',null,null)
;

-- 2022-11-28T17:28:12.691Z
INSERT INTO t_alter_column values('m_meansoftransportation','OtherClause','VARCHAR(2000)',null,null)
;

-- 2022-11-28T17:28:12.697Z
INSERT INTO t_alter_column values('m_meansoftransportation','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-11-28T17:28:12.703Z
INSERT INTO t_alter_column values('m_meansoftransportation','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2022-11-28T17:28:12.709Z
INSERT INTO t_alter_column values('m_meansoftransportation','WhereClause','VARCHAR(2000)',null,null)
;

-- Column: M_MeansOfTransportation.WhereClause
-- 2022-11-28T17:29:17.443Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585192
;

-- 2022-11-28T17:29:17.445Z
DELETE FROM AD_Column WHERE AD_Column_ID=585192
;

-- Column: M_MeansOfTransportation.OtherClause
-- 2022-11-28T17:29:24.650Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585189
;

-- 2022-11-28T17:29:24.653Z
DELETE FROM AD_Column WHERE AD_Column_ID=585189
;

-- Column: M_MeansOfTransportation.CM_Template_ID
-- 2022-11-28T17:29:31.763Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585182
;

-- 2022-11-28T17:29:31.764Z
DELETE FROM AD_Column WHERE AD_Column_ID=585182
;

-- Column: M_MeansOfTransportation.AD_Table_ID
-- 2022-11-28T17:29:37.185Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585181
;

-- 2022-11-28T17:29:37.186Z
DELETE FROM AD_Column WHERE AD_Column_ID=585181
;

-- Column: M_MeansOfTransportation.Value
-- 2022-11-28T17:30:16.119Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585193,620,0,10,542268,'Value',TO_TIMESTAMP('2022-11-28 18:30:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Search key for the record in the format required - must be unique','D',0,60,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y',0,'Search Key',0,0,TO_TIMESTAMP('2022-11-28 18:30:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:30:16.121Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:30:16.122Z
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- Column: M_MeansOfTransportation.Value
-- 2022-11-28T17:30:21.835Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-11-28 18:30:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585193
;

-- Column: M_MeansOfTransportation.Value
-- 2022-11-28T17:30:30.740Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2022-11-28 18:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585193
;

-- Column: M_MeansOfTransportation.Value
-- 2022-11-28T17:30:46.575Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 18:30:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585193
;

-- 2022-11-28T17:30:52.649Z
/* DDL */ SELECT public.db_alter_table('M_MeansOfTransportation','ALTER TABLE public.M_MeansOfTransportation ADD COLUMN Value VARCHAR(60) NOT NULL')
;

-- Column: M_MeansOfTransportation.DescriptionURL
-- 2022-11-28T17:38:25.548Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585199,1920,0,10,542268,'DescriptionURL',TO_TIMESTAMP('2022-11-28 18:38:25','YYYY-MM-DD HH24:MI:SS'),100,'N','URL for the description','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Description URL',0,0,TO_TIMESTAMP('2022-11-28 18:38:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T17:38:25.550Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585199 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T17:38:25.552Z
/* DDL */  select update_Column_Translation_From_AD_Element(1920) 
;

-- 2022-11-28T17:38:34.007Z
/* DDL */ SELECT public.db_alter_table('M_MeansOfTransportation','ALTER TABLE public.M_MeansOfTransportation ADD COLUMN DescriptionURL VARCHAR(255)')
;

-- Element: MeansOfTransportation
-- 2022-11-28T17:54:16.070Z
UPDATE AD_Element_Trl SET Name='Means of Transportation Type', PrintName='Means of Transportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581695 AND AD_Language='de_DE'
;

-- 2022-11-28T17:54:16.071Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581695,'de_DE') 
;

-- Element: MeansOfTransportation
-- 2022-11-28T17:54:28.252Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Means of Transportation Type', PrintName='Means of Transportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581695 AND AD_Language='en_US'
;

-- 2022-11-28T17:54:28.253Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581695,'en_US') 
;

-- 2022-11-28T17:54:28.254Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581695,'en_US') 
;

-- Element: MeansOfTransportation
-- 2022-11-28T17:54:42.312Z
UPDATE AD_Element_Trl SET PrintName='Means of Transportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:54:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581695 AND AD_Language='nl_NL'
;

-- 2022-11-28T17:54:42.314Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581695,'nl_NL') 
;

-- Element: MeansOfTransportation
-- 2022-11-28T17:54:46.802Z
UPDATE AD_Element_Trl SET Name='Means of Transportation Type', PrintName='Means of Transportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581695 AND AD_Language='de_CH'
;

-- 2022-11-28T17:54:46.803Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581695,'de_CH') 
;

-- Element: MeansOfTransportation
-- 2022-11-28T17:54:50.332Z
UPDATE AD_Element_Trl SET Name='Means of Transportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:54:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581695 AND AD_Language='nl_NL'
;

-- 2022-11-28T17:54:50.333Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581695,'nl_NL') 
;

-- Name: Means of Trasportation Type
-- 2022-11-28T17:57:34.905Z
UPDATE AD_Reference SET Name='Means of Trasportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541691
;

-- 2022-11-28T17:57:44.141Z
UPDATE AD_Reference_Trl SET Name='Means of Trasportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:57:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541691
;

-- 2022-11-28T17:57:46.006Z
UPDATE AD_Reference_Trl SET Name='Means of Trasportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Reference_ID=541691
;

-- 2022-11-28T17:57:47.437Z
UPDATE AD_Reference_Trl SET Name='Means of Trasportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541691
;

-- 2022-11-28T17:57:51.037Z
UPDATE AD_Reference_Trl SET Name='Means of Trasportation Type',Updated=TO_TIMESTAMP('2022-11-28 18:57:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541691
;

-- 2022-11-28T18:03:59.380Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581786,0,'Type_MoT',TO_TIMESTAMP('2022-11-28 19:03:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Means Of Transportation Type','Means Of Transportation Type',TO_TIMESTAMP('2022-11-28 19:03:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:03:59.381Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581786 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Type_MoT
-- 2022-11-28T18:04:17.837Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 19:04:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581786 AND AD_Language='en_US'
;

-- 2022-11-28T18:04:17.839Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581786,'en_US') 
;

-- 2022-11-28T18:04:17.839Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581786,'en_US') 
;

-- Column: M_MeansOfTransportation.Type_MoT
-- 2022-11-28T18:05:47.072Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585209,581786,0,17,541691,542268,'Type_MoT',TO_TIMESTAMP('2022-11-28 19:05:47','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Means Of Transportation Type',0,0,TO_TIMESTAMP('2022-11-28 19:05:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T18:05:47.073Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585209 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T18:05:47.075Z
/* DDL */  select update_Column_Translation_From_AD_Element(581786) 
;

-- 2022-11-28T18:06:09.818Z
/* DDL */ SELECT public.db_alter_table('M_MeansOfTransportation','ALTER TABLE public.M_MeansOfTransportation ADD COLUMN Type_MoT VARCHAR(10)')
;

-- Column: M_MeansOfTransportation.Type_MoT
-- 2022-11-28T18:06:37.738Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 19:06:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585209
;

-- 2022-11-28T18:08:41.042Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581787,0,'IMO_MMSI',TO_TIMESTAMP('2022-11-28 19:08:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IMO/MMSI','IMO/MMSI',TO_TIMESTAMP('2022-11-28 19:08:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:08:41.042Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581787 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IMO_MMSI
-- 2022-11-28T18:09:53.552Z
UPDATE AD_Element_Trl SET Description='International Maritime Organization/Maritime Mobile Service Identity', Name='IMO/MMSI Nr', PrintName='IMO/MMSI Nr',Updated=TO_TIMESTAMP('2022-11-28 19:09:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='de_CH'
;

-- 2022-11-28T18:09:53.553Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'de_CH') 
;

-- 2022-11-28T18:10:12.708Z
UPDATE AD_Element SET ColumnName='IMO_MMSI_Number',Updated=TO_TIMESTAMP('2022-11-28 19:10:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787
;

-- 2022-11-28T18:10:12.709Z
UPDATE AD_Column SET ColumnName='IMO_MMSI_Number' WHERE AD_Element_ID=581787
;

-- 2022-11-28T18:10:12.710Z
UPDATE AD_Process_Para SET ColumnName='IMO_MMSI_Number' WHERE AD_Element_ID=581787
;

-- 2022-11-28T18:10:12.710Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'en_US') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:24.193Z
UPDATE AD_Element_Trl SET Name='IMO/MMSI Number', PrintName='IMO/MMSI Number',Updated=TO_TIMESTAMP('2022-11-28 19:10:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='de_CH'
;

-- 2022-11-28T18:10:24.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'de_CH') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:41.093Z
UPDATE AD_Element_Trl SET Name='IMO/MMSI Number',Updated=TO_TIMESTAMP('2022-11-28 19:10:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='de_DE'
;

-- 2022-11-28T18:10:41.094Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'de_DE') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:43.258Z
UPDATE AD_Element_Trl SET Name='IMO/MMSI Number',Updated=TO_TIMESTAMP('2022-11-28 19:10:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='en_US'
;

-- 2022-11-28T18:10:43.259Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581787,'en_US') 
;

-- 2022-11-28T18:10:43.260Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'en_US') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:47.022Z
UPDATE AD_Element_Trl SET Name='IMO/MMSI Number', PrintName='IMO/MMSI Number',Updated=TO_TIMESTAMP('2022-11-28 19:10:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='nl_NL'
;

-- 2022-11-28T18:10:47.023Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'nl_NL') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:48.414Z
UPDATE AD_Element_Trl SET PrintName='IMO/MMSI Number',Updated=TO_TIMESTAMP('2022-11-28 19:10:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='en_US'
;

-- 2022-11-28T18:10:48.416Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581787,'en_US') 
;

-- 2022-11-28T18:10:48.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'en_US') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:50.129Z
UPDATE AD_Element_Trl SET PrintName='IMO/MMSI Number',Updated=TO_TIMESTAMP('2022-11-28 19:10:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='de_DE'
;

-- 2022-11-28T18:10:50.131Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'de_DE') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:53.273Z
UPDATE AD_Element_Trl SET Description='International Maritime Organization/Maritime Mobile Service Identity',Updated=TO_TIMESTAMP('2022-11-28 19:10:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='de_DE'
;

-- 2022-11-28T18:10:53.274Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'de_DE') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:54.268Z
UPDATE AD_Element_Trl SET Description='International Maritime Organization/Maritime Mobile Service Identity',Updated=TO_TIMESTAMP('2022-11-28 19:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='en_US'
;

-- 2022-11-28T18:10:54.269Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581787,'en_US') 
;

-- 2022-11-28T18:10:54.270Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'en_US') 
;

-- Element: IMO_MMSI_Number
-- 2022-11-28T18:10:56.988Z
UPDATE AD_Element_Trl SET Description='International Maritime Organization/Maritime Mobile Service Identity',Updated=TO_TIMESTAMP('2022-11-28 19:10:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581787 AND AD_Language='nl_NL'
;

-- 2022-11-28T18:10:56.989Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581787,'nl_NL') 
;

-- Column: M_MeansOfTransportation.IMO_MMSI_Number
-- 2022-11-28T18:12:11.640Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585210,581787,0,14,542268,'IMO_MMSI_Number',TO_TIMESTAMP('2022-11-28 19:12:11','YYYY-MM-DD HH24:MI:SS'),100,'N','International Maritime Organization/Maritime Mobile Service Identity','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'IMO/MMSI Number',0,0,TO_TIMESTAMP('2022-11-28 19:12:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T18:12:11.641Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585210 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T18:12:11.643Z
/* DDL */  select update_Column_Translation_From_AD_Element(581787) 
;

-- 2022-11-28T18:12:23.999Z
/* DDL */ SELECT public.db_alter_table('M_MeansOfTransportation','ALTER TABLE public.M_MeansOfTransportation ADD COLUMN IMO_MMSI_Number VARCHAR(255)')
;

-- Column: M_MeansOfTransportation.IMO_MMSI_Number
-- 2022-11-28T18:12:40.509Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 19:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585210
;

-- 2022-11-28T18:14:53.846Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581788,0,'Plate_Number',TO_TIMESTAMP('2022-11-28 19:14:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Plate Nr','Plate Nr',TO_TIMESTAMP('2022-11-28 19:14:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:14:53.847Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581788 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_MeansOfTransportation.Plate_Number
-- 2022-11-28T18:15:05.685Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585211,581788,0,14,542268,'Plate_Number',TO_TIMESTAMP('2022-11-28 19:15:05','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Plate Nr',0,0,TO_TIMESTAMP('2022-11-28 19:15:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T18:15:05.686Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585211 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T18:15:05.688Z
/* DDL */  select update_Column_Translation_From_AD_Element(581788) 
;

-- Column: M_MeansOfTransportation.Plate_Number
-- 2022-11-28T18:15:13.414Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 19:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585211
;

-- 2022-11-28T18:15:16.807Z
/* DDL */ SELECT public.db_alter_table('M_MeansOfTransportation','ALTER TABLE public.M_MeansOfTransportation ADD COLUMN Plate_Number VARCHAR(255)')
;

-- 2022-11-28T18:16:10.394Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581789,0,'RTC',TO_TIMESTAMP('2022-11-28 19:16:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','RTC','RTC',TO_TIMESTAMP('2022-11-28 19:16:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:16:10.394Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581789 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_MeansOfTransportation.RTC
-- 2022-11-28T18:16:21.772Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585212,581789,0,14,542268,'RTC',TO_TIMESTAMP('2022-11-28 19:16:21','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'RTC',0,0,TO_TIMESTAMP('2022-11-28 19:16:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T18:16:21.773Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585212 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T18:16:21.775Z
/* DDL */  select update_Column_Translation_From_AD_Element(581789) 
;

-- Column: M_MeansOfTransportation.RTC
-- 2022-11-28T18:16:28.509Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 19:16:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585212
;

-- 2022-11-28T18:16:31.907Z
/* DDL */ SELECT public.db_alter_table('M_MeansOfTransportation','ALTER TABLE public.M_MeansOfTransportation ADD COLUMN RTC VARCHAR(255)')
;

-- 2022-11-28T18:18:10.322Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581790,0,'Plane',TO_TIMESTAMP('2022-11-28 19:18:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Plane','Plane',TO_TIMESTAMP('2022-11-28 19:18:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:18:10.322Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581790 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_MeansOfTransportation.Plane
-- 2022-11-28T18:18:22.772Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585213,581790,0,14,542268,'Plane',TO_TIMESTAMP('2022-11-28 19:18:22','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Plane',0,0,TO_TIMESTAMP('2022-11-28 19:18:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T18:18:22.773Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585213 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T18:18:22.774Z
/* DDL */  select update_Column_Translation_From_AD_Element(581790) 
;

-- Column: M_MeansOfTransportation.Plane
-- 2022-11-28T18:18:30.224Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 19:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585213
;

-- 2022-11-28T18:19:00.343Z
/* DDL */ SELECT public.db_alter_table('M_MeansOfTransportation','ALTER TABLE public.M_MeansOfTransportation ADD COLUMN Plane VARCHAR(255)')
;

-- 2022-11-28T18:19:14.241Z
INSERT INTO t_alter_column values('m_meansoftransportation','RTC','VARCHAR(255)',null,null)
;

-- 2022-11-28T18:19:27.034Z
INSERT INTO t_alter_column values('m_meansoftransportation','Plate_Number','VARCHAR(255)',null,null)
;

-- Column: M_MeansOfTransportation.IMO_MMSI_Number
-- 2022-11-28T18:20:24.854Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2022-11-28 19:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585210
;

-- Column: M_MeansOfTransportation.Plate_Number
-- 2022-11-28T18:20:41.055Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=3,Updated=TO_TIMESTAMP('2022-11-28 19:20:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585211
;

-- Column: M_MeansOfTransportation.RTC
-- 2022-11-28T18:20:52.564Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=4,Updated=TO_TIMESTAMP('2022-11-28 19:20:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585212
;

-- Column: M_MeansOfTransportation.Plane
-- 2022-11-28T18:21:03.484Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=6,Updated=TO_TIMESTAMP('2022-11-28 19:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585213
;

-- Column: M_MeansOfTransportation.C_BPartner_ID
-- 2022-11-28T18:25:40.012Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585214,187,0,30,192,542268,'C_BPartner_ID',TO_TIMESTAMP('2022-11-28 19:25:39','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Partner',0,0,TO_TIMESTAMP('2022-11-28 19:25:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-28T18:25:40.013Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585214 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-28T18:25:40.015Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: M_MeansOfTransportation.C_BPartner_ID
-- 2022-11-28T18:25:48.941Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-11-28 19:25:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585214
;

-- 2022-11-28T18:25:51.417Z
/* DDL */ SELECT public.db_alter_table('M_MeansOfTransportation','ALTER TABLE public.M_MeansOfTransportation ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2022-11-28T18:25:51.422Z
ALTER TABLE M_MeansOfTransportation ADD CONSTRAINT CBPartner_MMeansOfTransportation FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2022-11-28T18:29:09.324Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581791,0,TO_TIMESTAMP('2022-11-28 19:29:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Means of Transportation','Means of Transportation',TO_TIMESTAMP('2022-11-28 19:29:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:29:09.325Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581791 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-11-28T18:29:17.252Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 19:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='en_US'
;

-- 2022-11-28T18:29:17.253Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581791,'en_US') 
;

-- 2022-11-28T18:29:17.254Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'en_US') 
;

-- Element: null
-- 2022-11-28T18:29:34.362Z
UPDATE AD_Element_Trl SET Name='Transportmittel', PrintName='Transportmittel',Updated=TO_TIMESTAMP('2022-11-28 19:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='de_CH'
;

-- 2022-11-28T18:29:34.364Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'de_CH') 
;

-- Element: null
-- 2022-11-28T18:29:36.075Z
UPDATE AD_Element_Trl SET Name='Transportmittel',Updated=TO_TIMESTAMP('2022-11-28 19:29:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='de_DE'
;

-- 2022-11-28T18:29:36.076Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'de_DE') 
;

-- Element: null
-- 2022-11-28T18:29:38.272Z
UPDATE AD_Element_Trl SET PrintName='Transportmittel',Updated=TO_TIMESTAMP('2022-11-28 19:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='de_DE'
;

-- 2022-11-28T18:29:38.273Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'de_DE') 
;

-- Element: null
-- 2022-11-28T18:29:44.673Z
UPDATE AD_Element_Trl SET Name='Transportmittel', PrintName='Transportmittel',Updated=TO_TIMESTAMP('2022-11-28 19:29:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='nl_NL'
;

-- 2022-11-28T18:29:44.674Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'nl_NL') 
;

-- Element: null
-- 2022-11-28T18:30:40.148Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Means of Transportation',Updated=TO_TIMESTAMP('2022-11-28 19:30:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='en_US'
;

-- 2022-11-28T18:30:40.149Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581791,'en_US') 
;

-- 2022-11-28T18:30:40.150Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'en_US') 
;

-- Element: null
-- 2022-11-28T18:30:52.812Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Transportmittel',Updated=TO_TIMESTAMP('2022-11-28 19:30:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='de_DE'
;

-- 2022-11-28T18:30:52.813Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'de_DE') 
;

-- Element: null
-- 2022-11-28T18:31:00.863Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Transportmittel',Updated=TO_TIMESTAMP('2022-11-28 19:31:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='de_CH'
;

-- 2022-11-28T18:31:00.864Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'de_CH') 
;

-- Element: null
-- 2022-11-28T18:31:08.512Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Transportmittel',Updated=TO_TIMESTAMP('2022-11-28 19:31:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581791 AND AD_Language='nl_NL'
;

-- 2022-11-28T18:31:08.513Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581791,'nl_NL') 
;

-- Window: Means of Transportation, InternalName=null
-- 2022-11-28T18:31:15.038Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581791,0,541635,TO_TIMESTAMP('2022-11-28 19:31:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','Y','N','N','N','Y','Means of Transportation','N',TO_TIMESTAMP('2022-11-28 19:31:14','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-11-28T18:31:15.039Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541635 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-11-28T18:31:15.041Z
/* DDL */  select update_window_translation_from_ad_element(581791) 
;

-- 2022-11-28T18:31:15.047Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541635
;

-- 2022-11-28T18:31:15.048Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541635)
;

-- Tab: Means of Transportation(541635,D) -> Means of Transportation
-- Table: M_MeansOfTransportation
-- 2022-11-28T18:32:09.024Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581776,0,546685,542268,541635,'Y',TO_TIMESTAMP('2022-11-28 19:32:08','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_MeansOfTransportation','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Means of Transportation','N',10,0,TO_TIMESTAMP('2022-11-28 19:32:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:09.025Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546685 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-28T18:32:09.027Z
/* DDL */  select update_tab_translation_from_ad_element(581776) 
;

-- 2022-11-28T18:32:09.031Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546685)
;

-- Tab: Means of Transportation(541635,D) -> Means of Transportation
-- Table: M_MeansOfTransportation
-- 2022-11-28T18:32:14.349Z
UPDATE AD_Tab SET IsSingleRow='Y',Updated=TO_TIMESTAMP('2022-11-28 19:32:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546685
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Client
-- Column: M_MeansOfTransportation.AD_Client_ID
-- 2022-11-28T18:32:46.462Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585179,708260,0,546685,TO_TIMESTAMP('2022-11-28 19:32:46','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'D','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','Y','N','Client',TO_TIMESTAMP('2022-11-28 19:32:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:46.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:46.465Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-28T18:32:46.631Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708260
;

-- 2022-11-28T18:32:46.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708260)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Organisation
-- Column: M_MeansOfTransportation.AD_Org_ID
-- 2022-11-28T18:32:46.690Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585180,708261,0,546685,TO_TIMESTAMP('2022-11-28 19:32:46','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'D','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-11-28 19:32:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:46.691Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:46.692Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-28T18:32:46.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708261
;

-- 2022-11-28T18:32:46.846Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708261)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Means of Transportation
-- Column: M_MeansOfTransportation.M_MeansOfTransportation_ID
-- 2022-11-28T18:32:46.922Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585183,708262,0,546685,TO_TIMESTAMP('2022-11-28 19:32:46','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Means of Transportation',TO_TIMESTAMP('2022-11-28 19:32:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:46.922Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:46.923Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581776) 
;

-- 2022-11-28T18:32:46.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708262
;

-- 2022-11-28T18:32:46.927Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708262)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Created
-- Column: M_MeansOfTransportation.Created
-- 2022-11-28T18:32:46.986Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585184,708263,0,546685,TO_TIMESTAMP('2022-11-28 19:32:46','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created',7,'D','The Created field indicates the date that this record was created.','Y','Y','N','N','N','N','N','Created',TO_TIMESTAMP('2022-11-28 19:32:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:46.987Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:46.989Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2022-11-28T18:32:47.023Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708263
;

-- 2022-11-28T18:32:47.023Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708263)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Created By
-- Column: M_MeansOfTransportation.CreatedBy
-- 2022-11-28T18:32:47.071Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585185,708264,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'User who created this records',10,'D','The Created By field indicates the user who created this record.','Y','Y','N','N','N','N','N','Created By',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.072Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.073Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2022-11-28T18:32:47.106Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708264
;

-- 2022-11-28T18:32:47.106Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708264)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Description
-- Column: M_MeansOfTransportation.Description
-- 2022-11-28T18:32:47.158Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585186,708265,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','Y','N','N','N','N','N','Description',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.159Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708265 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.160Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-11-28T18:32:47.210Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708265
;

-- 2022-11-28T18:32:47.210Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708265)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Active
-- Column: M_MeansOfTransportation.IsActive
-- 2022-11-28T18:32:47.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585187,708266,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'D','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','Active',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.276Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-28T18:32:47.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708266
;

-- 2022-11-28T18:32:47.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708266)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Name
-- Column: M_MeansOfTransportation.Name
-- 2022-11-28T18:32:47.532Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585188,708267,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'',120,'D','','Y','Y','N','N','N','N','N','Name',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.533Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.534Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-11-28T18:32:47.571Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708267
;

-- 2022-11-28T18:32:47.571Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708267)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Updated
-- Column: M_MeansOfTransportation.Updated
-- 2022-11-28T18:32:47.625Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585190,708268,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was updated',7,'D','The Updated field indicates the date that this record was updated.','Y','Y','N','N','N','N','N','Updated',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.626Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.627Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2022-11-28T18:32:47.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708268
;

-- 2022-11-28T18:32:47.656Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708268)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Updated By
-- Column: M_MeansOfTransportation.UpdatedBy
-- 2022-11-28T18:32:47.719Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585191,708269,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'User who updated this records',10,'D','The Updated By field indicates the user who updated this record.','Y','Y','N','N','N','N','N','Updated By',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.720Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.721Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2022-11-28T18:32:47.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708269
;

-- 2022-11-28T18:32:47.753Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708269)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Search Key
-- Column: M_MeansOfTransportation.Value
-- 2022-11-28T18:32:47.802Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585193,708270,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique',60,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','Search Key',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.803Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.804Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2022-11-28T18:32:47.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708270
;

-- 2022-11-28T18:32:47.825Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708270)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Description URL
-- Column: M_MeansOfTransportation.DescriptionURL
-- 2022-11-28T18:32:47.881Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585199,708271,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'URL for the description',255,'D','Y','Y','N','N','N','N','N','Description URL',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.882Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.883Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1920) 
;

-- 2022-11-28T18:32:47.889Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708271
;

-- 2022-11-28T18:32:47.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708271)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Means Of Transportation Type
-- Column: M_MeansOfTransportation.Type_MoT
-- 2022-11-28T18:32:47.937Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585209,708272,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Means Of Transportation Type',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:47.937Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:47.938Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581786) 
;

-- 2022-11-28T18:32:47.941Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708272
;

-- 2022-11-28T18:32:47.942Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708272)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> IMO/MMSI Number
-- Column: M_MeansOfTransportation.IMO_MMSI_Number
-- 2022-11-28T18:32:48Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585210,708273,0,546685,TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100,'International Maritime Organization/Maritime Mobile Service Identity',255,'D','Y','Y','N','N','N','N','N','IMO/MMSI Number',TO_TIMESTAMP('2022-11-28 19:32:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:48Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708273 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:48.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581787) 
;

-- 2022-11-28T18:32:48.005Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708273
;

-- 2022-11-28T18:32:48.005Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708273)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Plate Nr
-- Column: M_MeansOfTransportation.Plate_Number
-- 2022-11-28T18:32:48.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585211,708274,0,546685,TO_TIMESTAMP('2022-11-28 19:32:48','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Plate Nr',TO_TIMESTAMP('2022-11-28 19:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:48.061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708274 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:48.062Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581788) 
;

-- 2022-11-28T18:32:48.065Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708274
;

-- 2022-11-28T18:32:48.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708274)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> RTC
-- Column: M_MeansOfTransportation.RTC
-- 2022-11-28T18:32:48.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585212,708275,0,546685,TO_TIMESTAMP('2022-11-28 19:32:48','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','RTC',TO_TIMESTAMP('2022-11-28 19:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:48.116Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708275 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:48.117Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581789) 
;

-- 2022-11-28T18:32:48.121Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708275
;

-- 2022-11-28T18:32:48.121Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708275)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Plane
-- Column: M_MeansOfTransportation.Plane
-- 2022-11-28T18:32:48.176Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585213,708276,0,546685,TO_TIMESTAMP('2022-11-28 19:32:48','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Plane',TO_TIMESTAMP('2022-11-28 19:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:48.177Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708276 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:48.178Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581790) 
;

-- 2022-11-28T18:32:48.181Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708276
;

-- 2022-11-28T18:32:48.181Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708276)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Business Partner
-- Column: M_MeansOfTransportation.C_BPartner_ID
-- 2022-11-28T18:32:48.230Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585214,708277,0,546685,TO_TIMESTAMP('2022-11-28 19:32:48','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','Y','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2022-11-28 19:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T18:32:48.231Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708277 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-28T18:32:48.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2022-11-28T18:32:48.242Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708277
;

-- 2022-11-28T18:32:48.242Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708277)
;

-- Tab: Means of Transportation(541635,D) -> Means of Transportation(546685,D)
-- UI Section: main
-- 2022-11-28T18:33:07.620Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546685,545309,TO_TIMESTAMP('2022-11-28 19:33:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-28 19:33:07','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-28T18:33:07.621Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545309 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main
-- UI Column: 10
-- 2022-11-28T18:33:13.929Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546476,545309,TO_TIMESTAMP('2022-11-28 19:33:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-28 19:33:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main
-- UI Column: 20
-- 2022-11-28T18:33:15.876Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546477,545309,TO_TIMESTAMP('2022-11-28 19:33:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-28 19:33:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10
-- UI Element Group: primary
-- 2022-11-28T18:33:29.616Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546476,550067,TO_TIMESTAMP('2022-11-28 19:33:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2022-11-28 19:33:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> primary.Search Key
-- Column: M_MeansOfTransportation.Value
-- 2022-11-28T18:33:46.030Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708270,0,546685,550067,613634,'F',TO_TIMESTAMP('2022-11-28 19:33:45','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Search Key',10,0,0,TO_TIMESTAMP('2022-11-28 19:33:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> primary.Name
-- Column: M_MeansOfTransportation.Name
-- 2022-11-28T18:33:54.445Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708267,0,546685,550067,613635,'F',TO_TIMESTAMP('2022-11-28 19:33:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2022-11-28 19:33:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> primary.Means Of Transportation Type
-- Column: M_MeansOfTransportation.Type_MoT
-- 2022-11-28T18:34:12.479Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708272,0,546685,550067,613636,'F',TO_TIMESTAMP('2022-11-28 19:34:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Means Of Transportation Type',30,0,0,TO_TIMESTAMP('2022-11-28 19:34:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> primary.IMO/MMSI Number
-- Column: M_MeansOfTransportation.IMO_MMSI_Number
-- 2022-11-28T18:34:42.615Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708273,0,546685,550067,613637,'F',TO_TIMESTAMP('2022-11-28 19:34:42','YYYY-MM-DD HH24:MI:SS'),100,'International Maritime Organization/Maritime Mobile Service Identity','Y','N','N','Y','N','N','N',0,'IMO/MMSI Number',40,0,0,TO_TIMESTAMP('2022-11-28 19:34:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> primary.Plate Nr
-- Column: M_MeansOfTransportation.Plate_Number
-- 2022-11-28T18:34:52.323Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708274,0,546685,550067,613638,'F',TO_TIMESTAMP('2022-11-28 19:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Plate Nr',50,0,0,TO_TIMESTAMP('2022-11-28 19:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> primary.RTC
-- Column: M_MeansOfTransportation.RTC
-- 2022-11-28T18:35:01.996Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708275,0,546685,550067,613639,'F',TO_TIMESTAMP('2022-11-28 19:35:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'RTC',60,0,0,TO_TIMESTAMP('2022-11-28 19:35:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> primary.Plane
-- Column: M_MeansOfTransportation.Plane
-- 2022-11-28T18:35:10.062Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708276,0,546685,550067,613640,'F',TO_TIMESTAMP('2022-11-28 19:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Plane',70,0,0,TO_TIMESTAMP('2022-11-28 19:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 20
-- UI Element Group: flags
-- 2022-11-28T18:35:23.550Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546477,550068,TO_TIMESTAMP('2022-11-28 19:35:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-11-28 19:35:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 20 -> flags.Active
-- Column: M_MeansOfTransportation.IsActive
-- 2022-11-28T18:35:32.452Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708266,0,546685,550068,613641,'F',TO_TIMESTAMP('2022-11-28 19:35:32','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2022-11-28 19:35:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 20
-- UI Element Group: others
-- 2022-11-28T18:35:46.361Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546477,550069,TO_TIMESTAMP('2022-11-28 19:35:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','others',20,TO_TIMESTAMP('2022-11-28 19:35:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 20
-- UI Element Group: section
-- 2022-11-28T18:35:52.093Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546477,550070,TO_TIMESTAMP('2022-11-28 19:35:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','section',30,TO_TIMESTAMP('2022-11-28 19:35:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 20 -> section.Organisation
-- Column: M_MeansOfTransportation.AD_Org_ID
-- 2022-11-28T18:36:01.142Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708261,0,546685,550070,613642,'F',TO_TIMESTAMP('2022-11-28 19:36:01','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2022-11-28 19:36:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 20 -> section.Client
-- Column: M_MeansOfTransportation.AD_Client_ID
-- 2022-11-28T18:36:09.926Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708260,0,546685,550070,613643,'F',TO_TIMESTAMP('2022-11-28 19:36:09','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2022-11-28 19:36:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> IMO/MMSI Number
-- Column: M_MeansOfTransportation.IMO_MMSI_Number
-- 2022-11-28T18:42:08.083Z
UPDATE AD_Field SET DisplayLogic='@Type_MoT@=''Vessel''',Updated=TO_TIMESTAMP('2022-11-28 19:42:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708273
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> RTC
-- Column: M_MeansOfTransportation.RTC
-- 2022-11-28T18:42:46.453Z
UPDATE AD_Field SET DisplayLogic='@Type_MoT@=''Train''',Updated=TO_TIMESTAMP('2022-11-28 19:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708275
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Plane
-- Column: M_MeansOfTransportation.Plane
-- 2022-11-28T18:43:12.623Z
UPDATE AD_Field SET DisplayLogic='@Type_MoT@=''Plane''',Updated=TO_TIMESTAMP('2022-11-28 19:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708276
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Plate Nr
-- Column: M_MeansOfTransportation.Plate_Number
-- 2022-11-28T18:43:42.519Z
UPDATE AD_Field SET DisplayLogic='@Type_MoT@=''Truck''',Updated=TO_TIMESTAMP('2022-11-28 19:43:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708274
;

-- Window: Means of Transportation, InternalName=Means of Transportation
-- 2022-11-28T18:45:30.450Z
UPDATE AD_Window SET InternalName='Means of Transportation',Updated=TO_TIMESTAMP('2022-11-28 19:45:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541635
;

-- Name: Means of Transportation
-- Action Type: W
-- Window: Means of Transportation(541635,D)
-- 2022-11-28T18:45:55.167Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,581791,542024,0,541635,TO_TIMESTAMP('2022-11-28 19:45:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Means of Transportation','Y','N','N','N','N','Means of Transportation',TO_TIMESTAMP('2022-11-28 19:45:55','YYYY-MM-DD HH24:MI:SS'),100,'Means of Transportation')
;

-- 2022-11-28T18:45:55.168Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542024 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-11-28T18:45:55.169Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542024, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542024)
;

-- 2022-11-28T18:45:55.173Z
/* DDL */  select update_menu_translation_from_ad_element(581791) 
;

-- Reordering children of `Product Attributes`
-- Node name: `Lot Control`
-- 2022-11-28T18:45:55.713Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=355 AND AD_Tree_ID=10
;

-- Node name: `Serial No Control`
-- 2022-11-28T18:45:55.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=354 AND AD_Tree_ID=10
;

-- Node name: `Attribute`
-- 2022-11-28T18:45:55.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=359 AND AD_Tree_ID=10
;

-- Node name: `Attribute Set`
-- 2022-11-28T18:45:55.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=353 AND AD_Tree_ID=10
;

-- Node name: `Lot`
-- 2022-11-28T18:45:55.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=356 AND AD_Tree_ID=10
;

-- Node name: `Product Attribute Grid`
-- 2022-11-28T18:45:55.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=565 AND AD_Tree_ID=10
;

-- Node name: `Attribute Search`
-- 2022-11-28T18:45:55.715Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=358 AND AD_Tree_ID=10
;

-- Node name: `Quality Note`
-- 2022-11-28T18:45:55.716Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540734 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation`
-- 2022-11-28T18:45:55.716Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2022-11-28T18:46:22.583Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2022-11-28T18:46:22.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2022-11-28T18:46:22.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2022-11-28T18:46:22.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2022-11-28T18:46:22.585Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2022-11-28T18:46:22.585Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2022-11-28T18:46:22.585Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2022-11-28T18:46:22.586Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2022-11-28T18:46:22.586Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2022-11-28T18:46:22.586Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2022-11-28T18:46:22.586Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2022-11-28T18:46:22.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2022-11-28T18:46:22.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation`
-- 2022-11-28T18:46:22.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2022-11-28T18:46:22.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2022-11-28T18:46:22.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2022-11-28T18:46:22.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2022-11-28T18:46:22.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2022-11-28T18:46:22.588Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2022-11-28T18:46:22.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2022-11-28T18:46:22.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2022-11-28T18:46:22.589Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Reordering children of `Logistics`
-- Node name: `Tour`
-- 2022-11-28T18:46:32.106Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion`
-- 2022-11-28T18:46:32.107Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days`
-- 2022-11-28T18:46:32.107Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order`
-- 2022-11-28T18:46:32.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor`
-- 2022-11-28T18:46:32.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction`
-- 2022-11-28T18:46:32.108Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version`
-- 2022-11-28T18:46:32.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation`
-- 2022-11-28T18:46:32.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material`
-- 2022-11-28T18:46:32.109Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit`
-- 2022-11-28T18:46:32.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code`
-- 2022-11-28T18:46:32.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction`
-- 2022-11-28T18:46:32.110Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing`
-- 2022-11-28T18:46:32.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning`
-- 2022-11-28T18:46:32.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition`
-- 2022-11-28T18:46:32.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery`
-- 2022-11-28T18:46:32.111Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions`
-- 2022-11-28T18:46:32.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order`
-- 2022-11-28T18:46:32.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package`
-- 2022-11-28T18:46:32.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use`
-- 2022-11-28T18:46:32.112Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders`
-- 2022-11-28T18:46:32.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders`
-- 2022-11-28T18:46:32.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order`
-- 2022-11-28T18:46:32.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order`
-- 2022-11-28T18:46:32.113Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-11-28T18:46:32.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-11-28T18:46:32.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-11-28T18:46:32.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung`
-- 2022-11-28T18:46:32.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units`
-- 2022-11-28T18:46:32.114Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code`
-- 2022-11-28T18:46:32.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation`
-- 2022-11-28T18:46:32.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Forwarder`
-- 2022-11-28T18:46:32.115Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542023 AND AD_Tree_ID=10
;

-- 2022-11-28T18:55:19.420Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708262
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Means of Transportation
-- Column: M_MeansOfTransportation.M_MeansOfTransportation_ID
-- 2022-11-28T18:55:19.422Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708262
;

-- 2022-11-28T18:55:19.424Z
DELETE FROM AD_Field WHERE AD_Field_ID=708262
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 20 -> others.Description URL
-- Column: M_MeansOfTransportation.DescriptionURL
-- 2022-11-28T18:55:36.302Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708271,0,546685,550069,613644,'F',TO_TIMESTAMP('2022-11-28 19:55:36','YYYY-MM-DD HH24:MI:SS'),100,'URL for the description','Y','N','N','Y','N','N','N',0,'Description URL',10,0,0,TO_TIMESTAMP('2022-11-28 19:55:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> primary.Business Partner
-- Column: M_MeansOfTransportation.C_BPartner_ID
-- 2022-11-28T18:57:15.237Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708277,0,546685,550067,613645,'F',TO_TIMESTAMP('2022-11-28 19:57:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Business Partner',80,0,0,TO_TIMESTAMP('2022-11-28 19:57:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10
-- UI Element Group: desc
-- 2022-11-28T18:57:30.277Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546476,550071,TO_TIMESTAMP('2022-11-28 19:57:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','desc',20,TO_TIMESTAMP('2022-11-28 19:57:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> desc.IMO/MMSI Number
-- Column: M_MeansOfTransportation.IMO_MMSI_Number
-- 2022-11-28T18:58:29.170Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550071, SeqNo=10,Updated=TO_TIMESTAMP('2022-11-28 19:58:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613637
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> desc.Plate Nr
-- Column: M_MeansOfTransportation.Plate_Number
-- 2022-11-28T18:58:40.806Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550071, SeqNo=20,Updated=TO_TIMESTAMP('2022-11-28 19:58:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613638
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> desc.RTC
-- Column: M_MeansOfTransportation.RTC
-- 2022-11-28T18:58:57.217Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550071, SeqNo=30,Updated=TO_TIMESTAMP('2022-11-28 19:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613639
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> desc.Plane
-- Column: M_MeansOfTransportation.Plane
-- 2022-11-28T18:59:20.195Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550071, SeqNo=40,Updated=TO_TIMESTAMP('2022-11-28 19:59:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613640
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> desc.Description
-- Column: M_MeansOfTransportation.Description
-- 2022-11-28T19:00:27.225Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708265,0,546685,550071,613646,'F',TO_TIMESTAMP('2022-11-28 20:00:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Description',50,0,0,TO_TIMESTAMP('2022-11-28 20:00:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Description
-- Column: M_MeansOfTransportation.Description
-- 2022-11-28T19:01:50.160Z
UPDATE AD_Field SET ColumnDisplayLength=255,Updated=TO_TIMESTAMP('2022-11-28 20:01:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708265
;

-- Column: M_MeansOfTransportation.Plane
-- 2022-11-28T19:04:15.306Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2022-11-28 20:04:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585213
;

-- Column: M_MeansOfTransportation.Plate_Number
-- 2022-11-28T19:04:25.379Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2022-11-28 20:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585211
;

-- Column: M_MeansOfTransportation.RTC
-- 2022-11-28T19:04:33.094Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2022-11-28 20:04:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585212
;

-- Column: M_MeansOfTransportation.IMO_MMSI_Number
-- 2022-11-28T19:05:19.450Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2022-11-28 20:05:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585210
;

-- UI Element: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> main -> 10 -> desc.Description
-- Column: M_MeansOfTransportation.Description
-- 2022-11-28T19:07:42.576Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2022-11-28 20:07:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613646
;

-- 2022-11-28T19:12:18.732Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581792,0,TO_TIMESTAMP('2022-11-28 20:12:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Owner','Owner',TO_TIMESTAMP('2022-11-28 20:12:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T19:12:18.733Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581792 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-11-28T19:12:27.894Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-28 20:12:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581792 AND AD_Language='en_US'
;

-- 2022-11-28T19:12:27.895Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581792,'en_US') 
;

-- 2022-11-28T19:12:27.896Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581792,'en_US') 
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Owner
-- Column: M_MeansOfTransportation.C_BPartner_ID
-- 2022-11-28T19:12:38.014Z
UPDATE AD_Field SET AD_Name_ID=581792, Description=NULL, Help=NULL, Name='Owner',Updated=TO_TIMESTAMP('2022-11-28 20:12:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708277
;

-- 2022-11-28T19:12:38.015Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581792) 
;

-- 2022-11-28T19:12:38.019Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708277
;

-- 2022-11-28T19:12:38.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708277)
;

-- 2022-11-28T19:14:08.205Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581793,0,TO_TIMESTAMP('2022-11-28 20:14:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Type','Type',TO_TIMESTAMP('2022-11-28 20:14:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-28T19:14:08.205Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581793 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Means of Transportation(541635,D) -> Means of Transportation(546685,D) -> Type
-- Column: M_MeansOfTransportation.Type_MoT
-- 2022-11-28T19:15:11.401Z
UPDATE AD_Field SET AD_Name_ID=573673, Description=NULL, Help=NULL, Name='Type',Updated=TO_TIMESTAMP('2022-11-28 20:15:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708272
;

-- 2022-11-28T19:15:11.402Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(573673) 
;

-- 2022-11-28T19:15:11.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708272
;

-- 2022-11-28T19:15:11.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708272)
;

-- Name: M_Forwarder
-- 2022-11-28T19:27:07.442Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541694,TO_TIMESTAMP('2022-11-28 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','M_Forwarder',TO_TIMESTAMP('2022-11-28 20:27:07','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-11-28T19:27:07.443Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541694 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: M_Forwarder
-- Table: M_Forwarder
-- Key: M_Forwarder.M_Forwarder_ID
-- 2022-11-28T19:28:22.234Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,585155,0,541694,542266,TO_TIMESTAMP('2022-11-28 20:28:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-11-28 20:28:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: M_Forwarder
-- Table: M_Forwarder
-- Key: M_Forwarder.M_Forwarder_ID
-- 2022-11-28T19:29:08.272Z
UPDATE AD_Ref_Table SET WhereClause='M_Forwarder.IsAcive=''Y''',Updated=TO_TIMESTAMP('2022-11-28 20:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541694
;

-- Reference: M_Forwarder
-- Table: M_Forwarder
-- Key: M_Forwarder.M_Forwarder_ID
-- 2022-11-28T19:30:33.549Z
UPDATE AD_Ref_Table SET AD_Window_ID=541634,Updated=TO_TIMESTAMP('2022-11-28 20:30:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541694
;

-- Name: M_Forwarder(Active)
-- 2022-11-28T19:30:48.127Z
UPDATE AD_Reference SET Name='M_Forwarder(Active)',Updated=TO_TIMESTAMP('2022-11-28 20:30:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541694
;

-- Name: M_Forwarder(Active)
-- 2022-11-28T19:30:53.312Z
UPDATE AD_Reference SET Description='M_Forwarder(Active)',Updated=TO_TIMESTAMP('2022-11-28 20:30:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541694
;

-- Column: M_Delivery_Planning.M_Forwarder_ID
-- 2022-11-28T19:31:37.123Z
UPDATE AD_Column SET AD_Element_ID=581762, AD_Reference_ID=30, AD_Reference_Value_ID=541694, ColumnName='M_Forwarder_ID', Description=NULL, FieldLength=10, Help=NULL, Name='Forwarder',Updated=TO_TIMESTAMP('2022-11-28 20:31:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585035
;

-- 2022-11-28T19:31:37.124Z
/* DDL */  select update_Column_Translation_From_AD_Element(581762) 
;

-- 2022-11-28T19:31:49.104Z
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning','ALTER TABLE public.M_Delivery_Planning ADD COLUMN M_Forwarder_ID NUMERIC(10)')
;

-- 2022-11-28T19:31:49.110Z
ALTER TABLE M_Delivery_Planning ADD CONSTRAINT MForwarder_MDeliveryPlanning FOREIGN KEY (M_Forwarder_ID) REFERENCES public.M_Forwarder DEFERRABLE INITIALLY DEFERRED
;

