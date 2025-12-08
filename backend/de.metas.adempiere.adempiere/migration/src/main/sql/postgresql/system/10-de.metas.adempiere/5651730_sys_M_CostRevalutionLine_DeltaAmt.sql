-- 2022-08-18T19:55:03.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584117,2840,0,12,542191,'DeltaAmt',TO_TIMESTAMP('2022-08-18 22:55:03','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Difference Amount','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Delta Amount',0,0,TO_TIMESTAMP('2022-08-18 22:55:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-18T19:55:03.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584117 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-18T19:55:03.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(2840) 
;

-- 2022-08-18T19:55:04.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN DeltaAmt NUMERIC DEFAULT 0 NOT NULL')
;

-- 2022-08-18T20:26:06.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.M_CostRevaluation_Detail (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DeltaAmt NUMERIC NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_CostDetail_ID NUMERIC(10), M_CostRevaluation_Detail_ID NUMERIC(10) NOT NULL, M_CostRevaluation_ID NUMERIC(10) NOT NULL, M_CostRevaluationLine_ID NUMERIC(10) NOT NULL, NewAmt NUMERIC NOT NULL, NewCostPrice NUMERIC NOT NULL, OldAmt NUMERIC NOT NULL, OldCostPrice NUMERIC NOT NULL, Qty NUMERIC NOT NULL, RevaluationType VARCHAR(3) NOT NULL, SeqNo NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCurrency_MCostRevaluationDetail FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CUOM_MCostRevaluationDetail FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MCostDetail_MCostRevaluationDetail FOREIGN KEY (M_CostDetail_ID) REFERENCES public.M_CostDetail DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_CostRevaluation_Detail_Key PRIMARY KEY (M_CostRevaluation_Detail_ID), CONSTRAINT MCostRevaluation_MCostRevaluationDetail FOREIGN KEY (M_CostRevaluation_ID) REFERENCES public.M_CostRevaluation DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MCostRevaluationLine_MCostRevaluationDetail FOREIGN KEY (M_CostRevaluationLine_ID) REFERENCES public.M_CostRevaluationLine DEFERRABLE INITIALLY DEFERRED)
;

