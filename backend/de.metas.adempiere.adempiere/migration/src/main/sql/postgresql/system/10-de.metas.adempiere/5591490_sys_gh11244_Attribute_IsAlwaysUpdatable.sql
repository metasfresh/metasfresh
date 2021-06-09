-- 2021-06-09T08:30:07.985Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574266,2468,0,20,562,'IsAlwaysUpdateable',TO_TIMESTAMP('2021-06-09 11:30:06','YYYY-MM-DD HH24:MI:SS'),100,'N','N','The column is always updateable, even if the record is not active or processed','D',0,1,'If selected and if the winow / tab is not read only, you can always update the column.  This might be useful for comments, etc.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Always Updateable',0,0,TO_TIMESTAMP('2021-06-09 11:30:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-09T08:30:08.128Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574266 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-09T08:30:08.227Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2468) 
;

-- 2021-06-09T08:30:14.463Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_Attribute','ALTER TABLE public.M_Attribute ADD COLUMN IsAlwaysUpdateable CHAR(1) DEFAULT ''N'' CHECK (IsAlwaysUpdateable IN (''Y'',''N'')) NOT NULL')
;

