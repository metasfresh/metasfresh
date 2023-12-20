-- Column: M_MatchInv.M_CostElement_ID
-- 2023-02-17T11:00:11.418Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586143,2700,0,30,472,'M_CostElement_ID',TO_TIMESTAMP('2023-02-17 13:00:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Product Cost Element','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cost Element',0,0,TO_TIMESTAMP('2023-02-17 13:00:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-17T11:00:11.437Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586143 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-17T11:00:11.443Z
/* DDL */  select update_Column_Translation_From_AD_Element(2700) 
;

-- 2023-02-17T11:00:13.057Z
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN M_CostElement_ID NUMERIC(10)')
;

-- 2023-02-17T11:00:13.375Z
ALTER TABLE M_MatchInv ADD CONSTRAINT MCostElement_MMatchInv FOREIGN KEY (M_CostElement_ID) REFERENCES public.M_CostElement DEFERRABLE INITIALLY DEFERRED
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Cost Element
-- Column: M_MatchInv.M_CostElement_ID
-- 2023-02-17T11:00:29.789Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586143,712631,0,408,TO_TIMESTAMP('2023-02-17 13:00:29','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element',10,'D','Y','N','N','N','N','N','N','N','Cost Element',TO_TIMESTAMP('2023-02-17 13:00:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-17T11:00:29.792Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712631 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-17T11:00:29.795Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2700) 
;

-- 2023-02-17T11:00:29.805Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712631
;

-- 2023-02-17T11:00:29.808Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712631)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> Cost Matching.Cost Element
-- Column: M_MatchInv.M_CostElement_ID
-- 2023-02-17T11:01:00.341Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712631,0,408,550392,615853,'F',TO_TIMESTAMP('2023-02-17 13:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element','Y','N','Y','N','N','Cost Element',50,0,0,TO_TIMESTAMP('2023-02-17 13:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Cost Element
-- Column: M_MatchInv.M_CostElement_ID
-- 2023-02-17T11:01:32.077Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=C',Updated=TO_TIMESTAMP('2023-02-17 13:01:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712631
;

