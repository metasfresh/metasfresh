-- 2022-02-21T11:25:17.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type_Conditions/''Refund''@!''Refund'' & @Type_Conditions/''Commission''@!''Commission'' & @Type_Conditions/''MediatedCommission''@!''MediatedCommission'' & @Type_Conditions/''MarginCommission''@!''MarginCommission'' & @Type_Conditions/''CallOrder''@!''CallOrder''',Updated=TO_TIMESTAMP('2022-02-21 13:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548120
;

-- 2022-02-21T13:51:34.943Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579333,541447,0,19,260,'C_Flatrate_Term_ID',TO_TIMESTAMP('2022-02-21 15:51:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Pauschale - Vertragsperiode',0,0,TO_TIMESTAMP('2022-02-21 15:51:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-02-21T13:51:34.949Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579333 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-02-21T13:51:34.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(541447) 
;

-- 2022-02-21T13:51:35.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN C_Flatrate_Term_ID NUMERIC(10)')
;

-- 2022-02-21T13:51:37.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_OrderLine ADD CONSTRAINT CFlatrateTerm_COrderLine FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;

