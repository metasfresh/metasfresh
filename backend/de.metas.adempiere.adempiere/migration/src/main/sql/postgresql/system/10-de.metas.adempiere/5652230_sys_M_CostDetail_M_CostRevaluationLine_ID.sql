-- Column: M_CostDetail.M_CostRevaluation_ID
-- 2022-08-22T10:30:49.772Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584184,581160,0,30,808,'M_CostRevaluation_ID',TO_TIMESTAMP('2022-08-22 13:30:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Cost Revaluation',0,0,TO_TIMESTAMP('2022-08-22 13:30:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T10:30:49.775Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584184 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T10:30:49.809Z
/* DDL */  select update_Column_Translation_From_AD_Element(581160) 
;

-- Column: M_CostDetail.M_CostRevaluationLine_ID
-- 2022-08-22T10:31:29.662Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584185,581162,0,30,808,'M_CostRevaluationLine_ID',TO_TIMESTAMP('2022-08-22 13:31:29','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Cost Revaluation Line',0,0,TO_TIMESTAMP('2022-08-22 13:31:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-22T10:31:29.664Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584185 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-22T10:31:29.669Z
/* DDL */  select update_Column_Translation_From_AD_Element(581162) 
;

-- 2022-08-22T10:31:41.663Z
/* DDL */ SELECT public.db_alter_table('M_CostDetail','ALTER TABLE public.M_CostDetail ADD COLUMN M_CostRevaluationLine_ID NUMERIC(10)')
;

-- 2022-08-22T10:31:41.840Z
ALTER TABLE M_CostDetail ADD CONSTRAINT MCostRevaluationLine_MCostDetail FOREIGN KEY (M_CostRevaluationLine_ID) REFERENCES public.M_CostRevaluationLine DEFERRABLE INITIALLY DEFERRED
;

-- Field: Produktkosten(344,D) -> Kostendetails(748,D) -> Cost Revaluation
-- Column: M_CostDetail.M_CostRevaluation_ID
-- 2022-08-22T12:00:12.015Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584184,705456,0,748,TO_TIMESTAMP('2022-08-22 15:00:11','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Revaluation',TO_TIMESTAMP('2022-08-22 15:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T12:00:12.018Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705456 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-22T12:00:12.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581160) 
;

-- 2022-08-22T12:00:12.038Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705456
;

-- 2022-08-22T12:00:12.044Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705456)
;

-- Field: Produktkosten(344,D) -> Kostendetails(748,D) -> Cost Revaluation Line
-- Column: M_CostDetail.M_CostRevaluationLine_ID
-- 2022-08-22T12:00:12.139Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584185,705457,0,748,TO_TIMESTAMP('2022-08-22 15:00:12','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Revaluation Line',TO_TIMESTAMP('2022-08-22 15:00:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T12:00:12.140Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705457 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-22T12:00:12.141Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581162) 
;

-- 2022-08-22T12:00:12.144Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705457
;

-- 2022-08-22T12:00:12.144Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705457)
;

-- UI Element: Produktkosten(344,D) -> Kostendetails(748,D) -> main -> 20 -> Reference.Cost Revaluation
-- Column: M_CostDetail.M_CostRevaluation_ID
-- 2022-08-22T12:00:48.383Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705456,0,748,612158,544599,'F',TO_TIMESTAMP('2022-08-22 15:00:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Revaluation',130,0,0,TO_TIMESTAMP('2022-08-22 15:00:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktkosten(344,D) -> Kostendetails(748,D) -> main -> 20 -> Reference.Cost Revaluation Line
-- Column: M_CostDetail.M_CostRevaluationLine_ID
-- 2022-08-22T12:01:03.744Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705457,0,748,612159,544599,'F',TO_TIMESTAMP('2022-08-22 15:01:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Revaluation Line',140,0,0,TO_TIMESTAMP('2022-08-22 15:01:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktkosten(344,D) -> Kostendetails(748,D) -> main -> 20 -> Reference.Cost Revaluation
-- Column: M_CostDetail.M_CostRevaluation_ID
-- 2022-08-22T12:01:46.477Z
UPDATE AD_UI_Element SET SeqNo=95,Updated=TO_TIMESTAMP('2022-08-22 15:01:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612158
;

-- UI Element: Produktkosten(344,D) -> Kostendetails(748,D) -> main -> 20 -> Reference.Cost Revaluation Line
-- Column: M_CostDetail.M_CostRevaluationLine_ID
-- 2022-08-22T12:01:52.441Z
UPDATE AD_UI_Element SET SeqNo=96,Updated=TO_TIMESTAMP('2022-08-22 15:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612159
;

-- Field: Produktkosten(344,D) -> Kostendetails(748,D) -> Cost Revaluation
-- Column: M_CostDetail.M_CostRevaluation_ID
-- 2022-08-22T12:02:59.190Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-22 15:02:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705456
;

-- Field: Produktkosten(344,D) -> Kostendetails(748,D) -> Cost Revaluation Line
-- Column: M_CostDetail.M_CostRevaluationLine_ID
-- 2022-08-22T12:03:33.962Z
UPDATE AD_Field SET DisplayLogic='@M_CostRevaluationLine_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-22 15:03:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705457
;

-- Field: Produktkosten(344,D) -> Kostendetails(748,D) -> Cost Revaluation
-- Column: M_CostDetail.M_CostRevaluation_ID
-- 2022-08-22T12:03:44.878Z
UPDATE AD_Field SET DisplayLogic='@M_CostRevaluation_ID/0@>0',Updated=TO_TIMESTAMP('2022-08-22 15:03:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705456
;

-- 2022-08-23T07:34:39.288Z
/* DDL */ SELECT public.db_alter_table('M_CostDetail','ALTER TABLE public.M_CostDetail ADD COLUMN M_CostRevaluation_ID NUMERIC(10)')
;

-- 2022-08-23T07:34:39.440Z
ALTER TABLE M_CostDetail ADD CONSTRAINT MCostRevaluation_MCostDetail FOREIGN KEY (M_CostRevaluation_ID) REFERENCES public.M_CostRevaluation DEFERRABLE INITIALLY DEFERRED
;

