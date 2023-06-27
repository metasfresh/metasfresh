-- Column: M_MatchInv.CostAmountInvoiced
-- 2023-03-07T22:20:01.577Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586285,582072,0,12,472,'CostAmountInvoiced',TO_TIMESTAMP('2023-03-08 00:20:01','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Cost Amount Invoiced',0,0,TO_TIMESTAMP('2023-03-08 00:20:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-07T22:20:01.579Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586285 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-07T22:20:01.623Z
/* DDL */  select update_Column_Translation_From_AD_Element(582072) 
;

-- Column: M_MatchInv.CostAmountInvoiced
-- 2023-03-07T22:20:07.857Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-03-08 00:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586285
;

-- 2023-03-07T22:20:12.466Z
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN CostAmountInvoiced NUMERIC')
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Cost Amount Invoiced
-- Column: M_MatchInv.CostAmountInvoiced
-- 2023-03-07T22:20:35.081Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586285,712779,0,408,TO_TIMESTAMP('2023-03-08 00:20:34','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Amount Invoiced',TO_TIMESTAMP('2023-03-08 00:20:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-07T22:20:35.083Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-07T22:20:35.084Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582072) 
;

-- 2023-03-07T22:20:35.098Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712779
;

-- 2023-03-07T22:20:35.099Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712779)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> Cost Matching.Cost Amount Invoiced
-- Column: M_MatchInv.CostAmountInvoiced
-- 2023-03-07T22:21:01.278Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712779,0,408,550392,615974,'F',TO_TIMESTAMP('2023-03-08 00:21:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Amount Invoiced',60,0,0,TO_TIMESTAMP('2023-03-08 00:21:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Cost Amount Invoiced
-- Column: M_MatchInv.CostAmountInvoiced
-- 2023-03-07T22:21:33.285Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=C',Updated=TO_TIMESTAMP('2023-03-08 00:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712779
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> Cost Matching.Cost Element
-- Column: M_MatchInv.M_CostElement_ID
-- 2023-03-07T23:13:53.776Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2023-03-08 01:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615853
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> Cost Matching.Cost Amount
-- Column: M_MatchInv.CostAmount
-- 2023-03-07T23:13:59.307Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-03-08 01:13:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615834
;

