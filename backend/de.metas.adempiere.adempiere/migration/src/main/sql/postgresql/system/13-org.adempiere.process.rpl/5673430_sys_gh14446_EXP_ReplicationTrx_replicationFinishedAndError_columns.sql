-- 2023-01-25T11:50:34.447Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581955,0,'IsReplicationTrxFinished',TO_TIMESTAMP('2023-01-25 13:50:34','YYYY-MM-DD HH24:MI:SS'),100,'org.adempiere.process.rpl','Y','Replication transaction finished','Replication transaction finished',TO_TIMESTAMP('2023-01-25 13:50:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T11:50:34.457Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581955 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: EXP_ReplicationTrx.IsReplicationTrxFinished
-- 2023-01-25T11:50:53.443Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585607,581955,0,20,540573,'IsReplicationTrxFinished',TO_TIMESTAMP('2023-01-25 13:50:53','YYYY-MM-DD HH24:MI:SS'),100,'N','','org.adempiere.process.rpl',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Replication transaction finished',0,0,TO_TIMESTAMP('2023-01-25 13:50:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T11:50:53.450Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585607 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T11:50:53.500Z
/* DDL */  select update_Column_Translation_From_AD_Element(581955) 
;

-- 2023-01-25T11:51:13.706Z
/* DDL */ SELECT public.db_alter_table('EXP_ReplicationTrx','ALTER TABLE public.EXP_ReplicationTrx ADD COLUMN IsReplicationTrxFinished CHAR(1) CHECK (IsReplicationTrxFinished IN (''Y'',''N''))')
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Replication transaction finished
-- Column: EXP_ReplicationTrx.IsReplicationTrxFinished
-- 2023-01-25T11:51:58.645Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585607,710527,0,540580,TO_TIMESTAMP('2023-01-25 13:51:58','YYYY-MM-DD HH24:MI:SS'),100,1,'org.adempiere.process.rpl','Y','N','N','N','N','N','N','N','Replication transaction finished',TO_TIMESTAMP('2023-01-25 13:51:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T11:51:58.650Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710527 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T11:51:58.655Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581955) 
;

-- 2023-01-25T11:51:58.676Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710527
;

-- 2023-01-25T11:51:58.683Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710527)
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Replication transaction finished
-- Column: EXP_ReplicationTrx.IsReplicationTrxFinished
-- 2023-01-25T11:52:44.758Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-25 13:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710527
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Replication transaction finished
-- Column: EXP_ReplicationTrx.IsReplicationTrxFinished
-- 2023-01-25T11:52:53.245Z
UPDATE AD_Field SET SeqNo=60,Updated=TO_TIMESTAMP('2023-01-25 13:52:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710527
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Replication transaction finished
-- Column: EXP_ReplicationTrx.IsReplicationTrxFinished
-- 2023-01-25T11:53:28.097Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-01-25 13:53:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710527
;



-- Column: EXP_ReplicationTrx.IsError
-- 2023-01-25T14:36:58.977Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585610,2395,0,20,540573,'IsError',TO_TIMESTAMP('2023-01-25 16:36:58','YYYY-MM-DD HH24:MI:SS'),100,'N','','Ein Fehler ist bei der Durchführung aufgetreten','org.adempiere.process.rpl',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fehler',0,0,TO_TIMESTAMP('2023-01-25 16:36:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T14:36:58.982Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585610 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T14:36:58.998Z
/* DDL */  select update_Column_Translation_From_AD_Element(2395)
;

-- 2023-01-25T14:37:00.554Z
/* DDL */ SELECT public.db_alter_table('EXP_ReplicationTrx','ALTER TABLE public.EXP_ReplicationTrx ADD COLUMN IsError CHAR(1) CHECK (IsError IN (''Y'',''N''))')
;

-- Column: EXP_ReplicationTrx.ErrorMsg
-- 2023-01-25T14:37:31.762Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585611,1021,0,10,540573,'ErrorMsg',TO_TIMESTAMP('2023-01-25 16:37:31','YYYY-MM-DD HH24:MI:SS'),100,'N','org.adempiere.process.rpl',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fehlermeldung',0,0,TO_TIMESTAMP('2023-01-25 16:37:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T14:37:31.771Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585611 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T14:37:31.785Z
/* DDL */  select update_Column_Translation_From_AD_Element(1021)
;

-- 2023-01-25T14:37:34.571Z
/* DDL */ SELECT public.db_alter_table('EXP_ReplicationTrx','ALTER TABLE public.EXP_ReplicationTrx ADD COLUMN ErrorMsg VARCHAR(1000)')
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Fehler
-- Column: EXP_ReplicationTrx.IsError
-- 2023-01-25T14:38:28.773Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585610,710530,0,540580,TO_TIMESTAMP('2023-01-25 16:38:28','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten',1,'org.adempiere.process.rpl','Y','N','N','N','N','N','N','N','Fehler',TO_TIMESTAMP('2023-01-25 16:38:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T14:38:28.778Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710530 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T14:38:28.784Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395)
;

-- 2023-01-25T14:38:28.814Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710530
;

-- 2023-01-25T14:38:28.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710530)
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Fehlermeldung
-- Column: EXP_ReplicationTrx.ErrorMsg
-- 2023-01-25T14:38:43.666Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585611,710531,0,540580,TO_TIMESTAMP('2023-01-25 16:38:43','YYYY-MM-DD HH24:MI:SS'),100,1000,'org.adempiere.process.rpl','Y','N','N','N','N','N','N','N','Fehlermeldung',TO_TIMESTAMP('2023-01-25 16:38:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T14:38:43.668Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710531 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T14:38:43.672Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1021)
;

-- 2023-01-25T14:38:43.692Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710531
;

-- 2023-01-25T14:38:43.699Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710531)
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Fehler
-- Column: EXP_ReplicationTrx.IsError
-- 2023-01-25T14:39:09.952Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-25 16:39:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710530
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Fehler
-- Column: EXP_ReplicationTrx.IsError
-- 2023-01-25T14:39:15.703Z
UPDATE AD_Field SET SeqNo=70,Updated=TO_TIMESTAMP('2023-01-25 16:39:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710530
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Fehlermeldung
-- Column: EXP_ReplicationTrx.ErrorMsg
-- 2023-01-25T14:39:37.986Z
UPDATE AD_Field SET DisplayLogic='@IsError@=Y', IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-01-25 16:39:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710531
;

-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Fehlermeldung
-- Column: EXP_ReplicationTrx.ErrorMsg
-- 2023-01-25T14:39:50.295Z
UPDATE AD_Field SET SeqNo=80,Updated=TO_TIMESTAMP('2023-01-25 16:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710531
;


-- Field: Replikationstransaktion(540212,org.adempiere.process.rpl) -> Replikationstransaktion(540580,org.adempiere.process.rpl) -> Fehler
-- Column: EXP_ReplicationTrx.IsError
-- 2023-01-26T09:06:38.264Z
UPDATE AD_Field SET IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-01-26 11:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710530
;



