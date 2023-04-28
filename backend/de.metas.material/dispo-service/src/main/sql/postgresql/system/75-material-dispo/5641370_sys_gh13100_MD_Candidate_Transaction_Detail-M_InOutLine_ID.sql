-- Column: MD_Candidate_Demand_Detail.M_InOutLine_ID
-- 2022-06-01T07:21:11.529Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583267,1026,0,30,295,540815,'M_InOutLine_ID',TO_TIMESTAMP('2022-06-01 10:21:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Position auf Versand- oder Wareneingangsbeleg','de.metas.material.dispo',0,10,'"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Versand-/Wareneingangsposition',0,0,TO_TIMESTAMP('2022-06-01 10:21:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-06-01T07:21:11.537Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583267 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-06-01T07:21:11.549Z
/* DDL */  select update_Column_Translation_From_AD_Element(1026) 
;

-- 2022-06-01T07:21:13.598Z
/* DDL */ SELECT public.db_alter_table('MD_Candidate_Demand_Detail','ALTER TABLE public.MD_Candidate_Demand_Detail ADD COLUMN M_InOutLine_ID NUMERIC(10)')
;

-- 2022-06-01T07:21:13.612Z
ALTER TABLE MD_Candidate_Demand_Detail ADD CONSTRAINT MInOutLine_MDCandidateDemandDetail FOREIGN KEY (M_InOutLine_ID) REFERENCES public.M_InOutLine DEFERRABLE INITIALLY DEFERRED
;

-- Drop m_inoutline_id FK constraint
alter table md_candidate_demand_detail drop constraint minoutline_mdcandidatedemanddetail
;

-- truncate MD_Stock && MD_Candidate
truncate MD_Stock cascade
;

truncate MD_Candidate cascade
;