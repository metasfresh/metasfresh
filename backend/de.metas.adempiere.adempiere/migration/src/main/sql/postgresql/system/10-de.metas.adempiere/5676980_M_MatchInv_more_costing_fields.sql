-- Column: M_MatchInv.C_Cost_Type_ID
-- 2023-02-14T07:56:19.858Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586045,582023,0,30,472,'C_Cost_Type_ID',TO_TIMESTAMP('2023-02-14 09:56:19','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Cost Type',0,0,TO_TIMESTAMP('2023-02-14 09:56:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-14T07:56:19.863Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586045 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-14T07:56:19.869Z
/* DDL */  select update_Column_Translation_From_AD_Element(582023) 
;

-- 2023-02-14T07:56:20.939Z
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN C_Cost_Type_ID NUMERIC(10)')
;

-- 2023-02-14T07:56:21.230Z
ALTER TABLE M_MatchInv ADD CONSTRAINT CCostType_MMatchInv FOREIGN KEY (C_Cost_Type_ID) REFERENCES public.C_Cost_Type DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_MatchInv.CostAmount
-- 2023-02-15T10:41:23.461Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586048,582031,0,12,472,'CostAmount',TO_TIMESTAMP('2023-02-15 12:41:23','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Cost Amount',0,0,TO_TIMESTAMP('2023-02-15 12:41:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-15T10:41:23.466Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586048 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-15T10:41:23.474Z
/* DDL */  select update_Column_Translation_From_AD_Element(582031) 
;

-- 2023-02-15T10:41:26.079Z
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN CostAmount NUMERIC')
;

-- Column: M_MatchInv.C_Currency_ID
-- 2023-02-15T10:43:11.345Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586049,193,0,30,472,'C_Currency_ID',TO_TIMESTAMP('2023-02-15 12:43:11','YYYY-MM-DD HH24:MI:SS'),100,'N','The Currency for this record','D',0,10,'Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Currency',0,0,TO_TIMESTAMP('2023-02-15 12:43:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-15T10:43:11.347Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586049 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-15T10:43:11.351Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- 2023-02-15T10:43:12.223Z
/* DDL */ SELECT public.db_alter_table('M_MatchInv','ALTER TABLE public.M_MatchInv ADD COLUMN C_Currency_ID NUMERIC(10)')
;

-- 2023-02-15T10:43:12.364Z
ALTER TABLE M_MatchInv ADD CONSTRAINT CCurrency_MMatchInv FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- UI Column: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10
-- UI Element Group: Cost Matching
-- 2023-02-15T15:04:11.925Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541919,550392,TO_TIMESTAMP('2023-02-15 17:04:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','Cost Matching',30,TO_TIMESTAMP('2023-02-15 17:04:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> Cost Matching.Shipment/Receipt Costs
-- Column: M_MatchInv.M_InOut_Cost_ID
-- 2023-02-15T15:04:28.972Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550392, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-02-15 17:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615724
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Cost Type
-- Column: M_MatchInv.C_Cost_Type_ID
-- 2023-02-15T15:04:52.610Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586045,712597,0,408,TO_TIMESTAMP('2023-02-15 17:04:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Type',TO_TIMESTAMP('2023-02-15 17:04:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T15:04:52.613Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T15:04:52.641Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582023) 
;

-- 2023-02-15T15:04:52.653Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712597
;

-- 2023-02-15T15:04:52.655Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712597)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Cost Amount
-- Column: M_MatchInv.CostAmount
-- 2023-02-15T15:04:57.417Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586048,712598,0,408,TO_TIMESTAMP('2023-02-15 17:04:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Amount',TO_TIMESTAMP('2023-02-15 17:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T15:04:57.418Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712598 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T15:04:57.420Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582031) 
;

-- 2023-02-15T15:04:57.425Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712598
;

-- 2023-02-15T15:04:57.427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712598)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Currency
-- Column: M_MatchInv.C_Currency_ID
-- 2023-02-15T15:05:07.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586049,712599,0,408,TO_TIMESTAMP('2023-02-15 17:05:07','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'D','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2023-02-15 17:05:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-15T15:05:07.549Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-15T15:05:07.550Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-15T15:05:07.602Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712599
;

-- 2023-02-15T15:05:07.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712599)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> Cost Matching.Cost Type
-- Column: M_MatchInv.C_Cost_Type_ID
-- 2023-02-15T15:05:31.814Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712597,0,408,550392,615832,'F',TO_TIMESTAMP('2023-02-15 17:05:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Type',20,0,0,TO_TIMESTAMP('2023-02-15 17:05:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> Cost Matching.Currency
-- Column: M_MatchInv.C_Currency_ID
-- 2023-02-15T15:05:39.871Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712599,0,408,550392,615833,'F',TO_TIMESTAMP('2023-02-15 17:05:39','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','Currency',30,0,0,TO_TIMESTAMP('2023-02-15 17:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Matched Invoices(107,D) -> Match Invoice(408,D) -> main -> 10 -> Cost Matching.Cost Amount
-- Column: M_MatchInv.CostAmount
-- 2023-02-15T15:05:46.076Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712598,0,408,550392,615834,'F',TO_TIMESTAMP('2023-02-15 17:05:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Cost Amount',40,0,0,TO_TIMESTAMP('2023-02-15 17:05:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Cost Type
-- Column: M_MatchInv.C_Cost_Type_ID
-- 2023-02-15T15:06:14.108Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=C',Updated=TO_TIMESTAMP('2023-02-15 17:06:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712597
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Currency
-- Column: M_MatchInv.C_Currency_ID
-- 2023-02-15T15:06:17.340Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=C',Updated=TO_TIMESTAMP('2023-02-15 17:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712599
;

-- Field: Matched Invoices(107,D) -> Match Invoice(408,D) -> Cost Amount
-- Column: M_MatchInv.CostAmount
-- 2023-02-15T15:06:31.934Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=C',Updated=TO_TIMESTAMP('2023-02-15 17:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712598
;

