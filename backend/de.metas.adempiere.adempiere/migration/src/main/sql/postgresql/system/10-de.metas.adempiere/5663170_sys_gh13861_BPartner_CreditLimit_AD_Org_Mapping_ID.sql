-- Column: C_BPartner_CreditLimit.AD_Org_Mapping_ID
-- 2022-11-04T08:55:13.789Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584865,578815,0,30,540929,'AD_Org_Mapping_ID',TO_TIMESTAMP('2022-11-04 10:55:13','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Org Mapping',0,0,TO_TIMESTAMP('2022-11-04 10:55:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-04T08:55:13.806Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584865 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-04T08:55:13.845Z
/* DDL */  select update_Column_Translation_From_AD_Element(578815) 
;

-- 2022-11-04T08:55:15.382Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_CreditLimit','ALTER TABLE public.C_BPartner_CreditLimit ADD COLUMN AD_Org_Mapping_ID NUMERIC(10)')
;

-- 2022-11-04T08:55:15.419Z
ALTER TABLE C_BPartner_CreditLimit ADD CONSTRAINT ADOrgMapping_CBPartnerCreditLimit FOREIGN KEY (AD_Org_Mapping_ID) REFERENCES public.AD_Org_Mapping DEFERRABLE INITIALLY DEFERRED
;

