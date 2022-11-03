-- Column: M_HU_Attribute.IsUnique
-- 2022-11-01T08:19:17.457Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584862,540471,0,20,540504,'IsUnique',TO_TIMESTAMP('2022-11-01 10:19:17.219','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Unique',0,0,TO_TIMESTAMP('2022-11-01 10:19:17.219','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-11-01T08:19:17.459Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584862 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-01T08:19:17.488Z
/* DDL */  select update_Column_Translation_From_AD_Element(540471) 
;

-- 2022-11-01T08:19:18.416Z
/* DDL */ SELECT public.db_alter_table('M_HU_Attribute','ALTER TABLE public.M_HU_Attribute ADD COLUMN IsUnique CHAR(1) DEFAULT ''N'' CHECK (IsUnique IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_HU_Attribute.IsUnique
-- 2022-11-01T08:19:26.506Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2022-11-01 10:19:26.503','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584862
;

