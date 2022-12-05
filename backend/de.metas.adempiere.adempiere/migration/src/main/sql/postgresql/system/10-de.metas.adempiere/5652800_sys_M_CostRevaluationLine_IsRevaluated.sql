-- 2022-08-24T12:57:27.754Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581374,0,'IsRevaluated',TO_TIMESTAMP('2022-08-24 15:57:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Revaluated','Revaluated',TO_TIMESTAMP('2022-08-24 15:57:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T12:57:27.755Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581374 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_CostRevaluationLine.IsRevaluated
-- 2022-08-24T12:57:37.640Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584206,581374,0,20,542191,'IsRevaluated',TO_TIMESTAMP('2022-08-24 15:57:37','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Revaluated',0,0,TO_TIMESTAMP('2022-08-24 15:57:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-24T12:57:37.643Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584206 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-24T12:57:37.682Z
/* DDL */  select update_Column_Translation_From_AD_Element(581374) 
;

-- 2022-08-24T12:57:38.582Z
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN IsRevaluated CHAR(1) DEFAULT ''N'' CHECK (IsRevaluated IN (''Y'',''N'')) NOT NULL')
;

-- Field: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> Revaluated
-- Column: M_CostRevaluationLine.IsRevaluated
-- 2022-08-24T12:58:06.582Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584206,705519,0,546465,TO_TIMESTAMP('2022-08-24 15:58:06','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Revaluated',TO_TIMESTAMP('2022-08-24 15:58:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T12:58:06.584Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705519 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T12:58:06.587Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581374) 
;

-- 2022-08-24T12:58:06.600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705519
;

-- 2022-08-24T12:58:06.603Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705519)
;

-- Field: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> Revaluated
-- Column: M_CostRevaluationLine.IsRevaluated
-- 2022-08-24T12:58:17.835Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-24 15:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705519
;

-- Column: M_CostRevaluationLine.NewCostPrice
-- 2022-08-24T12:59:33.620Z
UPDATE AD_Column SET ReadOnlyLogic='@IsRevaluated/N@=Y',Updated=TO_TIMESTAMP('2022-08-24 15:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583796
;

-- Field: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> Delta Amount
-- Column: M_CostRevaluationLine.DeltaAmt
-- 2022-08-24T13:00:01.050Z
UPDATE AD_Field SET DisplayLogic='@IsRevaluated/N@=Y',Updated=TO_TIMESTAMP('2022-08-24 16:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705347
;

-- UI Element: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> main -> 10 -> revaluation result.Revaluated
-- Column: M_CostRevaluationLine.IsRevaluated
-- 2022-08-24T13:01:07.374Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705519,0,546465,612199,549828,'F',TO_TIMESTAMP('2022-08-24 16:01:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Revaluated',20,0,0,TO_TIMESTAMP('2022-08-24 16:01:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> main -> 10 -> revaluation result.Delta Amount
-- Column: M_CostRevaluationLine.DeltaAmt
-- 2022-08-24T13:01:18.214Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-08-24 16:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612136
;

-- UI Element: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> main -> 10 -> revaluation result.Revaluated
-- Column: M_CostRevaluationLine.IsRevaluated
-- 2022-08-24T13:01:29.320Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2022-08-24 16:01:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612199
;

-- UI Element: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> main -> 10 -> revaluation result.Revaluated
-- Column: M_CostRevaluationLine.IsRevaluated
-- 2022-08-24T13:01:53.667Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-08-24 16:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612199
;

-- UI Element: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> main -> 10 -> revaluation result.Delta Amount
-- Column: M_CostRevaluationLine.DeltaAmt
-- 2022-08-24T13:01:53.671Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-08-24 16:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612136
;

-- UI Element: Kosten Neubewertung(541568,D) -> Kosten Neubewertung Position(546465,D) -> main -> 20 -> client & org.Organisation
-- Column: M_CostRevaluationLine.AD_Org_ID
-- 2022-08-24T13:01:53.676Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-08-24 16:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610538
;

