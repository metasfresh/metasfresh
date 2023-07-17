-- Column: M_Product.S_Resource_Group_ID
-- 2022-05-27T09:03:13.516Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583197,580932,0,30,208,'S_Resource_Group_ID',TO_TIMESTAMP('2022-05-27 12:03:13','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Resource Group',0,0,TO_TIMESTAMP('2022-05-27 12:03:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-27T09:03:13.523Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583197 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-27T09:03:13.555Z
/* DDL */  select update_Column_Translation_From_AD_Element(580932) 
;

-- 2022-05-27T09:03:14.484Z
/* DDL */ SELECT public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN S_Resource_Group_ID NUMERIC(10)')
;

-- 2022-05-27T09:03:16.346Z
ALTER TABLE M_Product ADD CONSTRAINT SResourceGroup_MProduct FOREIGN KEY (S_Resource_Group_ID) REFERENCES public.S_Resource_Group DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Product.S_Resource_ID
-- 2022-05-27T09:03:43.126Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2022-05-27 12:03:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6773
;

