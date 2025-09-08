-- Column: DD_OrderLine.DD_NetworkDistribution_ID
-- Column: DD_OrderLine.DD_NetworkDistribution_ID
-- 2024-07-16T18:50:26.357Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588841,53340,0,30,53038,'XX','DD_NetworkDistribution_ID',TO_TIMESTAMP('2024-07-16 21:50:26','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Network Distribution',0,0,TO_TIMESTAMP('2024-07-16 21:50:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-16T18:50:26.359Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588841 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-16T18:50:26.363Z
/* DDL */  select update_Column_Translation_From_AD_Element(53340) 
;

-- 2024-07-16T18:50:27.058Z
/* DDL */ SELECT public.db_alter_table('DD_OrderLine','ALTER TABLE public.DD_OrderLine ADD COLUMN DD_NetworkDistribution_ID NUMERIC(10)')
;

-- 2024-07-16T18:50:27.264Z
ALTER TABLE DD_OrderLine ADD CONSTRAINT DDNetworkDistribution_DDOrderLine FOREIGN KEY (DD_NetworkDistribution_ID) REFERENCES public.DD_NetworkDistribution DEFERRABLE INITIALLY DEFERRED
;

-- Column: DD_OrderLine.DD_NetworkDistributionLine_ID
-- Column: DD_OrderLine.DD_NetworkDistributionLine_ID
-- 2024-07-16T18:50:47.124Z
UPDATE AD_Column SET AD_Val_Rule_ID=540681,Updated=TO_TIMESTAMP('2024-07-16 21:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556809
;

