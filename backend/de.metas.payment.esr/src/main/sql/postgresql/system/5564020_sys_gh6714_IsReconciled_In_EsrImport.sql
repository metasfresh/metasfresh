-- 2020-05-22T14:22:26.888Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) 
VALUES (0,570817,1105,0,20,540409,'IsReconciled',TO_TIMESTAMP('2020-05-22 17:22:25','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde','de.metas.payment.esr',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Abgeglichen',0,0,TO_TIMESTAMP('2020-05-22 17:22:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-05-22T14:22:27.314Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-05-22T14:22:27.392Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1105) 
;

-- 2020-05-22T14:22:34.288Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('ESR_Import','ALTER TABLE public.ESR_Import ADD COLUMN IsReconciled CHAR(1) DEFAULT ''N'' CHECK (IsReconciled IN (''Y'',''N'')) NOT NULL')
;

