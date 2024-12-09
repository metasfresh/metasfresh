-- Column: Fact_Acct.M_CostElement_ID
-- 2023-02-16T17:04:41.741Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586116,2700,0,30,270,'M_CostElement_ID',TO_TIMESTAMP('2023-02-16 19:04:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Product Cost Element','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Cost Element',0,0,TO_TIMESTAMP('2023-02-16 19:04:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-16T17:04:41.745Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586116 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-16T17:04:41.747Z
/* DDL */  select update_Column_Translation_From_AD_Element(2700) 
;

-- 2023-02-16T17:04:42.647Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN M_CostElement_ID NUMERIC(10)')
;

-- 2023-02-16T17:04:42.837Z
ALTER TABLE Fact_Acct ADD CONSTRAINT MCostElement_FactAcct FOREIGN KEY (M_CostElement_ID) REFERENCES public.M_CostElement DEFERRABLE INITIALLY DEFERRED
;

-- Column: Fact_Acct_Transactions_View.M_CostElement_ID
-- 2023-02-16T17:07:14.579Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586117,2700,0,30,541485,'M_CostElement_ID',TO_TIMESTAMP('2023-02-16 19:07:14','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element','D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Cost Element',TO_TIMESTAMP('2023-02-16 19:07:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-16T17:07:14.581Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586117 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-16T17:07:14.583Z
/* DDL */  select update_Column_Translation_From_AD_Element(2700) 
;

-- Field: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> Cost Element
-- Column: Fact_Acct_Transactions_View.M_CostElement_ID
-- 2023-02-16T17:07:55.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586117,712630,0,242,TO_TIMESTAMP('2023-02-16 19:07:55','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element',10,'D','Y','N','N','N','N','N','N','N','Cost Element',TO_TIMESTAMP('2023-02-16 19:07:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-16T17:07:55.271Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712630 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-16T17:07:55.272Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2700) 
;

-- 2023-02-16T17:07:55.277Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712630
;

-- 2023-02-16T17:07:55.278Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712630)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> references.Cost Element
-- Column: Fact_Acct_Transactions_View.M_CostElement_ID
-- 2023-02-16T17:12:11.051Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712630,0,242,540306,615852,'F',TO_TIMESTAMP('2023-02-16 19:12:10','YYYY-MM-DD HH24:MI:SS'),100,'Product Cost Element','Y','N','Y','N','N','Cost Element',60,0,0,TO_TIMESTAMP('2023-02-16 19:12:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> references.Cost Element
-- Column: Fact_Acct_Transactions_View.M_CostElement_ID
-- 2023-02-16T17:12:31.194Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-02-16 19:12:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615852
;

-- UI Element: Accounting Transactions(162,D) -> Accounting Transactions(242,D) -> main -> 20 -> references.Cost Element
-- Column: Fact_Acct_Transactions_View.M_CostElement_ID
-- 2023-02-16T17:12:44.330Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2023-02-16 19:12:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615852
;

