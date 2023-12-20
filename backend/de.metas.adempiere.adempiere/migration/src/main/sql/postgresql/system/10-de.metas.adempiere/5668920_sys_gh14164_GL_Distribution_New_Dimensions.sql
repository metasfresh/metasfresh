-- Column: GL_Distribution.C_Order_ID
-- 2022-12-15T17:14:56.367Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585407,558,0,30,708,'C_Order_ID',TO_TIMESTAMP('2022-12-15 19:14:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sales order',0,0,TO_TIMESTAMP('2022-12-15 19:14:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T17:14:56.401Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585407 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T17:14:56.463Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- 2022-12-15T18:33:02.474Z
/* DDL */ SELECT public.db_alter_table('GL_Distribution','ALTER TABLE public.GL_Distribution ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2022-12-15T18:33:02.516Z
ALTER TABLE GL_Distribution ADD CONSTRAINT COrder_GLDistribution FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: GL_Distribution.M_SectionCode_ID
-- 2022-12-15T18:33:37.596Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585408,581238,0,30,708,'M_SectionCode_ID',TO_TIMESTAMP('2022-12-15 20:33:36','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-12-15 20:33:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T18:33:37.625Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585408 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T18:33:37.682Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-12-15T18:33:42.307Z
/* DDL */ SELECT public.db_alter_table('GL_Distribution','ALTER TABLE public.GL_Distribution ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-12-15T18:33:42.347Z
ALTER TABLE GL_Distribution ADD CONSTRAINT MSectionCode_GLDistribution FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Column: GL_DistributionLine.C_Order_ID
-- 2022-12-15T18:34:06.459Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585409,558,0,30,707,'C_Order_ID',TO_TIMESTAMP('2022-12-15 20:34:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Order','D',0,10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you close an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sales order',0,0,TO_TIMESTAMP('2022-12-15 20:34:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T18:34:06.488Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585409 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T18:34:06.544Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- 2022-12-15T18:34:10.996Z
/* DDL */ SELECT public.db_alter_table('GL_DistributionLine','ALTER TABLE public.GL_DistributionLine ADD COLUMN C_Order_ID NUMERIC(10)')
;

-- 2022-12-15T18:34:11.034Z
ALTER TABLE GL_DistributionLine ADD CONSTRAINT COrder_GLDistributionLine FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- Column: GL_DistributionLine.M_SectionCode_ID
-- 2022-12-15T18:34:27.243Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585410,581238,0,30,707,'M_SectionCode_ID',TO_TIMESTAMP('2022-12-15 20:34:26','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-12-15 20:34:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T18:34:27.272Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585410 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T18:34:27.328Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-12-15T18:34:31.803Z
/* DDL */ SELECT public.db_alter_table('GL_DistributionLine','ALTER TABLE public.GL_DistributionLine ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-12-15T18:34:31.841Z
ALTER TABLE GL_DistributionLine ADD CONSTRAINT MSectionCode_GLDistributionLine FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-12-15T18:40:09.840Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581880,0,'AnyOrder',TO_TIMESTAMP('2022-12-15 20:40:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Any Order','Any Order',TO_TIMESTAMP('2022-12-15 20:40:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T18:40:09.868Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581880 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-15T18:40:31.770Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581881,0,'AnySectionCode',TO_TIMESTAMP('2022-12-15 20:40:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Any Section Code','Any Section Code',TO_TIMESTAMP('2022-12-15 20:40:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T18:40:31.798Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581881 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: GL_Distribution.AnyOrder
-- 2022-12-15T18:40:50.958Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585411,581880,0,20,708,'AnyOrder',TO_TIMESTAMP('2022-12-15 20:40:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Any Order',0,0,TO_TIMESTAMP('2022-12-15 20:40:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T18:40:50.986Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585411 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T18:40:51.043Z
/* DDL */  select update_Column_Translation_From_AD_Element(581880) 
;

-- 2022-12-15T18:40:55.305Z
/* DDL */ SELECT public.db_alter_table('GL_Distribution','ALTER TABLE public.GL_Distribution ADD COLUMN AnyOrder CHAR(1) DEFAULT ''N'' CHECK (AnyOrder IN (''Y'',''N'')) NOT NULL')
;

-- Column: GL_Distribution.AnySectionCode
-- 2022-12-15T18:41:14.435Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585412,581881,0,20,708,'AnySectionCode',TO_TIMESTAMP('2022-12-15 20:41:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Any Section Code',0,0,TO_TIMESTAMP('2022-12-15 20:41:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T18:41:14.463Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585412 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T18:41:14.521Z
/* DDL */  select update_Column_Translation_From_AD_Element(581881) 
;

-- 2022-12-15T18:41:18.861Z
/* DDL */ SELECT public.db_alter_table('GL_Distribution','ALTER TABLE public.GL_Distribution ADD COLUMN AnySectionCode CHAR(1) DEFAULT ''N'' CHECK (AnySectionCode IN (''Y'',''N'')) NOT NULL')
;

-- 2022-12-15T18:42:27.895Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581882,0,'OverwriteOrder',TO_TIMESTAMP('2022-12-15 20:42:27','YYYY-MM-DD HH24:MI:SS'),100,'Overwrite the account segment Order with the value specified','D','If not overwritten, the value of the original account combination is used. If selected, but not specified, the segment is set to null.','Y','Overwrite Order','Overwrite Product',TO_TIMESTAMP('2022-12-15 20:42:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T18:42:27.923Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581882 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-12-15T18:42:54.847Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581883,0,'OverwriteSectionCode',TO_TIMESTAMP('2022-12-15 20:42:54','YYYY-MM-DD HH24:MI:SS'),100,'Overwrite the account segment Sectoin Coder with the value specified','D','If not overwritten, the value of the original account combination is used. If selected, but not specified, the segment is set to null.','Y','Overwrite Section Code','Overwrite Section Code',TO_TIMESTAMP('2022-12-15 20:42:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T18:42:54.875Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581883 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: OverwriteOrder
-- 2022-12-15T18:43:09.441Z
UPDATE AD_Element_Trl SET PrintName='Overwrite Ord',Updated=TO_TIMESTAMP('2022-12-15 20:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581882 AND AD_Language='de_CH'
;

-- 2022-12-15T18:43:09.497Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581882,'de_CH') 
;

-- Element: OverwriteOrder
-- 2022-12-15T18:43:19.988Z
UPDATE AD_Element_Trl SET PrintName='Overwrite Order',Updated=TO_TIMESTAMP('2022-12-15 20:43:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581882 AND AD_Language='de_DE'
;

-- 2022-12-15T18:43:20.045Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581882,'de_DE') 
;

-- Element: OverwriteOrder
-- 2022-12-15T18:43:25.681Z
UPDATE AD_Element_Trl SET PrintName='Overwrite Order',Updated=TO_TIMESTAMP('2022-12-15 20:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581882 AND AD_Language='de_CH'
;

-- 2022-12-15T18:43:25.737Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581882,'de_CH') 
;

-- Element: OverwriteOrder
-- 2022-12-15T18:43:30.349Z
UPDATE AD_Element_Trl SET PrintName='Overwrite Order',Updated=TO_TIMESTAMP('2022-12-15 20:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581882 AND AD_Language='en_US'
;

-- 2022-12-15T18:43:30.405Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581882,'en_US') 
;

-- 2022-12-15T18:43:30.433Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581882,'en_US') 
;

-- Element: OverwriteOrder
-- 2022-12-15T18:43:33.305Z
UPDATE AD_Element_Trl SET PrintName='Overwrite Order',Updated=TO_TIMESTAMP('2022-12-15 20:43:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581882 AND AD_Language='fr_CH'
;

-- 2022-12-15T18:43:33.361Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581882,'fr_CH') 
;

-- Element: OverwriteOrder
-- 2022-12-15T18:43:37.503Z
UPDATE AD_Element_Trl SET PrintName='Overwrite Order',Updated=TO_TIMESTAMP('2022-12-15 20:43:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581882 AND AD_Language='nl_NL'
;

-- 2022-12-15T18:43:37.560Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581882,'nl_NL') 
;

-- Column: GL_DistributionLine.OverwriteOrder
-- 2022-12-15T18:44:10.449Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585413,581882,0,20,707,'OverwriteOrder',TO_TIMESTAMP('2022-12-15 20:44:10','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Overwrite the account segment Order with the value specified','D',0,1,'If not overwritten, the value of the original account combination is used. If selected, but not specified, the segment is set to null.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Overwrite Order',0,0,TO_TIMESTAMP('2022-12-15 20:44:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T18:44:10.478Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585413 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T18:44:10.534Z
/* DDL */  select update_Column_Translation_From_AD_Element(581882) 
;

-- 2022-12-15T18:44:14.895Z
/* DDL */ SELECT public.db_alter_table('GL_DistributionLine','ALTER TABLE public.GL_DistributionLine ADD COLUMN OverwriteOrder CHAR(1) DEFAULT ''N'' CHECK (OverwriteOrder IN (''Y'',''N'')) NOT NULL')
;

-- Column: GL_DistributionLine.OverwriteSectionCode
-- 2022-12-15T18:44:40.172Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585414,581883,0,20,707,'OverwriteSectionCode',TO_TIMESTAMP('2022-12-15 20:44:39','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Overwrite the account segment Sectoin Coder with the value specified','D',0,1,'If not overwritten, the value of the original account combination is used. If selected, but not specified, the segment is set to null.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Overwrite Section Code',0,0,TO_TIMESTAMP('2022-12-15 20:44:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T18:44:40.202Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585414 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T18:44:40.259Z
/* DDL */  select update_Column_Translation_From_AD_Element(581883) 
;

-- 2022-12-15T18:44:44.422Z
/* DDL */ SELECT public.db_alter_table('GL_DistributionLine','ALTER TABLE public.GL_DistributionLine ADD COLUMN OverwriteSectionCode CHAR(1) DEFAULT ''N'' CHECK (OverwriteSectionCode IN (''Y'',''N'')) NOT NULL')
;

-- 2022-12-15T18:50:45.707Z
INSERT INTO t_alter_column values('gl_distributionline','OverwriteOrder','CHAR(1)',null,'N')
;

-- 2022-12-15T18:50:45.747Z
UPDATE GL_DistributionLine SET OverwriteOrder='N' WHERE OverwriteOrder IS NULL
;

