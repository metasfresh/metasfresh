
-- New column in ad_user


-- 2020-07-17T13:05:24.546Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,AD_Org_ID,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AD_Reference_Value_ID,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID) VALUES (30,10,1.000000000000,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-07-17 16:05:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N',0,'N','Y','N',TO_TIMESTAMP('2020-07-17 16:05:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N',114,'N',541163,571003,'N','N','N','N','N','N','N','N',0,'D','N','N','C_Title_ID','N','Titel','N',0,0,578005)
;

-- 2020-07-17T13:05:24.924Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571003 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T13:05:24.982Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578005)
;

-- 2020-07-17T13:05:43.002Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('AD_User','ALTER TABLE public.AD_User ADD COLUMN C_Title_ID NUMERIC(10)')
;

-- 2020-07-17T13:05:43.945Z
-- URL zum Konzept
ALTER TABLE AD_User ADD CONSTRAINT CTitle_ADUser FOREIGN KEY (C_Title_ID) REFERENCES public.C_Title DEFERRABLE INITIALLY DEFERRED
;


-- end of new column adding part
