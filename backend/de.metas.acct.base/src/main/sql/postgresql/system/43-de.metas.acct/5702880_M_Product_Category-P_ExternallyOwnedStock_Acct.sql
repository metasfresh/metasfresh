-- Run mode: SWING_CLIENT

-- Column: M_Product_Category_Acct.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:43:05.775801400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587476,582720,0,25,401,'P_ExternallyOwnedStock_Acct',TO_TIMESTAMP('2023-09-15 11:43:05.611','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externally Owned Stock',0,0,TO_TIMESTAMP('2023-09-15 11:43:05.611','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-15T08:43:05.778398500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587476 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-15T08:43:05.781526200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582720)
;

-- 2023-09-15T08:43:07.278719Z
/* DDL */ SELECT public.db_alter_table('M_Product_Category_Acct','ALTER TABLE public.M_Product_Category_Acct ADD COLUMN P_ExternallyOwnedStock_Acct NUMERIC(10)')
;

-- 2023-09-15T08:43:07.286080500Z
ALTER TABLE M_Product_Category_Acct ADD CONSTRAINT PExternallyOwnedStockA_MProductCategoryAcct FOREIGN KEY (P_ExternallyOwnedStock_Acct) REFERENCES public.C_ValidCombination DEFERRABLE INITIALLY DEFERRED
;

-- Field: Produkt Kategorie_OLD(144,D) -> Buchführung(324,D) -> Externally Owned Stock

;

-- Column: M_Product_Category_Acct.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:43:31.420165100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587476,720486,0,324,TO_TIMESTAMP('2023-09-15 11:43:31.253','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Externally Owned Stock',TO_TIMESTAMP('2023-09-15 11:43:31.253','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-15T08:43:31.422849600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720486 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-15T08:43:31.425484600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582720)
;

-- 2023-09-15T08:43:31.428593Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720486
;

-- 2023-09-15T08:43:31.430151400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720486)
;

-- UI Element: Produkt Kategorie_OLD(144,D) -> Buchführung(324,D) -> main -> 10 -> default.Externally Owned Stock

;

-- Column: M_Product_Category_Acct.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:44:23.746409700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720486,0,324,540288,620476,'F',TO_TIMESTAMP('2023-09-15 11:44:23.577','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Externally Owned Stock',290,0,0,TO_TIMESTAMP('2023-09-15 11:44:23.577','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Produkt Kategorie_OLD(144,D) -> Buchführung(324,D) -> main -> 10 -> default.Externally Owned Stock

;

-- Column: M_Product_Category_Acct.P_ExternallyOwnedStock_Acct

;

-- 2023-09-15T08:45:18.256668Z
UPDATE AD_UI_Element SET SeqNo=35,Updated=TO_TIMESTAMP('2023-09-15 11:45:18.256','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620476
;

