-- Table: C_Cost_Type_Acct
-- 2023-02-16T11:06:08.284Z
UPDATE AD_Table SET IsDeleteable='Y',Updated=TO_TIMESTAMP('2023-02-16 13:06:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542305
;

-- Column: C_Cost_Type.M_CostElement_ID
-- 2023-02-16T11:08:51.220Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586067,2700,0,30,542294,'M_CostElement_ID',TO_TIMESTAMP('2023-02-16 13:08:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Product Cost Element','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cost Element',0,0,TO_TIMESTAMP('2023-02-16 13:08:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-16T11:08:51.223Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586067 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-16T11:08:51.225Z
/* DDL */  select update_Column_Translation_From_AD_Element(2700) 
;

-- 2023-02-16T11:08:52.013Z
/* DDL */ SELECT public.db_alter_table('C_Cost_Type','ALTER TABLE public.C_Cost_Type ADD COLUMN M_CostElement_ID NUMERIC(10)')
;

-- 2023-02-16T11:08:52.022Z
ALTER TABLE C_Cost_Type ADD CONSTRAINT MCostElement_CCostType FOREIGN KEY (M_CostElement_ID) REFERENCES public.M_CostElement DEFERRABLE INITIALLY DEFERRED
;

-- Field: Cost Type(541675,D) -> Cost Type(546807,D) -> Cost Element
-- Column: C_Cost_Type.M_CostElement_ID
-- 2023-02-16T11:09:33.785Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586067,712614,0,546807,TO_TIMESTAMP('2023-02-16 13:09:33','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element',10,'D','Y','N','N','N','N','N','N','N','Cost Element',TO_TIMESTAMP('2023-02-16 13:09:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-16T11:09:33.786Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-16T11:09:33.788Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2700) 
;

-- 2023-02-16T11:09:33.798Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712614
;

-- 2023-02-16T11:09:33.799Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712614)
;

-- UI Element: Cost Type(541675,D) -> Cost Type(546807,D) -> main -> 10 -> distribution & calculation.Cost Element
-- Column: C_Cost_Type.M_CostElement_ID
-- 2023-02-16T11:10:30.779Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712614,0,546807,550342,615844,'F',TO_TIMESTAMP('2023-02-16 13:10:30','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element','Y','N','Y','N','N','Cost Element',30,0,0,TO_TIMESTAMP('2023-02-16 13:10:30','YYYY-MM-DD HH24:MI:SS'),100)
;

