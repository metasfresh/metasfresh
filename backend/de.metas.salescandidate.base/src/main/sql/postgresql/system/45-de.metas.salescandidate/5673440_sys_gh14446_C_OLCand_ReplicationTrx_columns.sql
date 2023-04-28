-- Column: C_OLCand.IsReplicationTrxFinished
-- 2023-01-25T12:41:58.081Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585608,581955,0,20,540244,'IsReplicationTrxFinished','(select EXP_ReplicationTrx.IsReplicationTrxFinished         from EXP_ReplicationTrx         where EXP_ReplicationTrx.EXP_ReplicationTrx_ID = C_OLCand.EXP_ReplicationTrx_ID)',TO_TIMESTAMP('2023-01-25 14:41:57','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.ordercandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Replication transaction finished',0,0,TO_TIMESTAMP('2023-01-25 14:41:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T12:41:58.090Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585608 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T12:41:58.095Z
/* DDL */  select update_Column_Translation_From_AD_Element(581955) 
;

-- Column: C_OLCand.IsReplicationTrxFinished
-- Source Table: EXP_ReplicationTrx
-- 2023-01-25T12:42:29.167Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585608,0,540110,540244,TO_TIMESTAMP('2023-01-25 14:42:29','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',550411,585607,540573,TO_TIMESTAMP('2023-01-25 14:42:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Replication transaction finished
-- Column: C_OLCand.IsReplicationTrxFinished
-- 2023-01-25T12:44:19.383Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585608,710528,0,540282,TO_TIMESTAMP('2023-01-25 14:44:19','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Replication transaction finished',TO_TIMESTAMP('2023-01-25 14:44:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T12:44:19.385Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710528 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T12:44:19.391Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581955) 
;

-- 2023-01-25T12:44:19.396Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710528
;

-- 2023-01-25T12:44:19.402Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710528)
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Replication transaction finished
-- Column: C_OLCand.IsReplicationTrxFinished
-- 2023-01-25T12:46:18.228Z
UPDATE AD_Field SET DisplayLogic='@EXP_ReplicationTrx_ID/0@ > 0',Updated=TO_TIMESTAMP('2023-01-25 14:46:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710528
;

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> main -> 20 -> flags.Replication transaction finished
-- Column: C_OLCand.IsReplicationTrxFinished
-- 2023-01-25T12:46:55.525Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710528,0,540282,614853,540965,'F',TO_TIMESTAMP('2023-01-25 14:46:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Replication transaction finished',100,0,0,TO_TIMESTAMP('2023-01-25 14:46:55','YYYY-MM-DD HH24:MI:SS'),100)
;



-- 2023-01-25T15:07:33.606Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581956,0,'IsReplicationTrxError',TO_TIMESTAMP('2023-01-25 17:07:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate','Y','Replication trx error','Replication trx error',TO_TIMESTAMP('2023-01-25 17:07:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:07:33.617Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581956 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-01-25T15:08:48.135Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581957,0,'ReplicationTrxErrorMsg',TO_TIMESTAMP('2023-01-25 17:08:47','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.process.rpl','Y','Replication trx error message','Replication trx error message',TO_TIMESTAMP('2023-01-25 17:08:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:08:48.137Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581957 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_OLCand.IsReplicationTrxError
-- 2023-01-25T15:09:14.760Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585612,581956,0,20,540244,'IsReplicationTrxError','(select EXP_ReplicationTrx.iserror         from EXP_ReplicationTrx         where EXP_ReplicationTrx.EXP_ReplicationTrx_ID = C_OLCand.EXP_ReplicationTrx_ID)',TO_TIMESTAMP('2023-01-25 17:09:14','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.ordercandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Replication trx error',0,0,TO_TIMESTAMP('2023-01-25 17:09:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T15:09:14.768Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585612 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T15:09:14.776Z
/* DDL */  select update_Column_Translation_From_AD_Element(581956)
;

-- Column: C_OLCand.IsReplicationTrxError
-- Source Table: EXP_ReplicationTrx
-- 2023-01-25T15:09:47.538Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585612,0,540111,540244,TO_TIMESTAMP('2023-01-25 17:09:47','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',550411,585610,540573,TO_TIMESTAMP('2023-01-25 17:09:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_OLCand.ReplicationTrxErrorMsg
-- 2023-01-25T15:10:15.631Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585613,581957,0,10,540244,'ReplicationTrxErrorMsg','(select EXP_ReplicationTrx.errormsg         from EXP_ReplicationTrx         where EXP_ReplicationTrx.EXP_ReplicationTrx_ID = C_OLCand.EXP_ReplicationTrx_ID)',TO_TIMESTAMP('2023-01-25 17:10:15','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.ordercandidate',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Replication trx error message',0,0,TO_TIMESTAMP('2023-01-25 17:10:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T15:10:15.633Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585613 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T15:10:15.636Z
/* DDL */  select update_Column_Translation_From_AD_Element(581957)
;

-- Column: C_OLCand.ReplicationTrxErrorMsg
-- Source Table: EXP_ReplicationTrx
-- 2023-01-25T15:10:40.387Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,585613,0,540112,540244,TO_TIMESTAMP('2023-01-25 17:10:40','YYYY-MM-DD HH24:MI:SS'),100,'L','Y',550411,585611,540573,TO_TIMESTAMP('2023-01-25 17:10:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Replication trx error
-- Column: C_OLCand.IsReplicationTrxError
-- 2023-01-25T15:11:19.350Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585612,710532,0,540282,TO_TIMESTAMP('2023-01-25 17:11:19','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Replication trx error',TO_TIMESTAMP('2023-01-25 17:11:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:11:19.352Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710532 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T15:11:19.354Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581956)
;

-- 2023-01-25T15:11:19.359Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710532
;

-- 2023-01-25T15:11:19.366Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710532)
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Replication trx error message
-- Column: C_OLCand.ReplicationTrxErrorMsg
-- 2023-01-25T15:11:39.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585613,710533,0,540282,TO_TIMESTAMP('2023-01-25 17:11:39','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Replication trx error message',TO_TIMESTAMP('2023-01-25 17:11:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T15:11:39.467Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710533 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T15:11:39.468Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581957)
;

-- 2023-01-25T15:11:39.472Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710533
;

-- 2023-01-25T15:11:39.473Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710533)
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Replication trx error
-- Column: C_OLCand.IsReplicationTrxError
-- 2023-01-25T15:12:40.973Z
UPDATE AD_Field SET DisplayLogic='@EXP_ReplicationTrx_ID/0@ > 0',Updated=TO_TIMESTAMP('2023-01-25 17:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710532
;

-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Replication trx error message
-- Column: C_OLCand.ReplicationTrxErrorMsg
-- 2023-01-25T15:13:04.770Z
UPDATE AD_Field SET DisplayLogic='@EXP_ReplicationTrx_ID/0@ > 0',Updated=TO_TIMESTAMP('2023-01-25 17:13:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710533
;

-- UI Column: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> main -> 20
-- UI Element Group: replication trx
-- 2023-01-25T15:13:22.339Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540545,550255,TO_TIMESTAMP('2023-01-25 17:13:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','replication trx',20,TO_TIMESTAMP('2023-01-25 17:13:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication transaction finished
-- Column: C_OLCand.IsReplicationTrxFinished
-- 2023-01-25T15:13:34.516Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550255, SeqNo=10,Updated=TO_TIMESTAMP('2023-01-25 17:13:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614853
;

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication trx error
-- Column: C_OLCand.IsReplicationTrxError
-- 2023-01-25T15:13:52.723Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710532,0,540282,614855,550255,'F',TO_TIMESTAMP('2023-01-25 17:13:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Replication trx error',20,0,0,TO_TIMESTAMP('2023-01-25 17:13:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> main -> 20 -> replication trx.Replication trx error message
-- Column: C_OLCand.ReplicationTrxErrorMsg
-- 2023-01-25T15:14:01.926Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,710533,0,540282,614856,550255,'F',TO_TIMESTAMP('2023-01-25 17:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Replication trx error message',30,0,0,TO_TIMESTAMP('2023-01-25 17:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Field: Auftragsdisposition(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Replication trx error message
-- Column: C_OLCand.ReplicationTrxErrorMsg
-- 2023-01-30T10:12:39.607Z
UPDATE AD_Field SET DisplayLogic='@IsReplicationTrxError/N@=''Y''',Updated=TO_TIMESTAMP('2023-01-30 12:12:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710533
;

