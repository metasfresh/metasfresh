-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistribution_ID
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistribution_ID
-- 2024-07-16T18:41:51.519Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588840,53340,0,30,540821,'XX','DD_NetworkDistribution_ID',TO_TIMESTAMP('2024-07-16 21:41:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Network Distribution',0,0,TO_TIMESTAMP('2024-07-16 21:41:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-16T18:41:51.522Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588840 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-16T18:41:51.547Z
/* DDL */  select update_Column_Translation_From_AD_Element(53340) 
;

-- 2024-07-16T18:41:54.160Z
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Dist_Detail','ALTER TABLE public.MD_Candidate_Dist_Detail ADD COLUMN DD_NetworkDistribution_ID NUMERIC(10)')
;

-- 2024-07-16T18:41:54.177Z
ALTER TABLE MD_Candidate_Dist_Detail ADD CONSTRAINT DDNetworkDistribution_MDCandidateDistDetail FOREIGN KEY (DD_NetworkDistribution_ID) REFERENCES public.DD_NetworkDistribution DEFERRABLE INITIALLY DEFERRED
;

-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistributionLine_ID
-- Column: MD_Candidate_Dist_Detail.DD_NetworkDistributionLine_ID
-- 2024-07-16T18:42:12.115Z
UPDATE AD_Column SET AD_Val_Rule_ID=540681,Updated=TO_TIMESTAMP('2024-07-16 21:42:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556804
;

