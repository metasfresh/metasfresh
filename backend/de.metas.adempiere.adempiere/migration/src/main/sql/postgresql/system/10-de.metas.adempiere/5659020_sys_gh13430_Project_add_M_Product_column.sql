-- Column: C_Project.M_Product_ID
-- 2022-10-05T14:49:53.851Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584675,454,0,18,540272,203,'M_Product_ID',TO_TIMESTAMP('2022-10-05 17:49:53.556','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2022-10-05 17:49:53.556','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-05T14:49:53.871Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584675 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-05T14:49:53.919Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2022-10-05T14:50:03.537Z
/* DDL */ SELECT public.db_alter_table('C_Project','ALTER TABLE public.C_Project ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2022-10-05T14:50:03.776Z
ALTER TABLE C_Project ADD CONSTRAINT MProduct_CProject FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Field: Projekt(540668,D) -> Projekt(541830,U) -> Produkt
-- Column: C_Project.M_Product_ID
-- 2022-10-05T14:50:54.816Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584675,707474,0,541830,TO_TIMESTAMP('2022-10-05 17:50:54.582','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2022-10-05 17:50:54.582','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-05T14:50:54.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707474 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-05T14:50:54.843Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-10-05T14:50:55.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707474
;

-- 2022-10-05T14:50:55.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707474)
;


-- UI Column: Projekt(540668,D) -> Projekt(541830,U) -> 1000028 -> 20
-- UI Element Group: product
-- 2022-10-05T14:51:54.953Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541784,549957,TO_TIMESTAMP('2022-10-05 17:51:54.74','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','product',12,TO_TIMESTAMP('2022-10-05 17:51:54.74','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Projekt(540668,D) -> Projekt(541830,U) -> 1000028 -> 20 -> product.Produkt
-- Column: C_Project.M_Product_ID
-- 2022-10-05T14:52:12.519Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707474,0,541830,613122,549957,'F',TO_TIMESTAMP('2022-10-05 17:52:12.376','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2022-10-05 17:52:12.376','YYYY-MM-DD HH24:MI:SS.US'),100)
;


-- UI Element: Projekt(540668,D) -> Projekt(541830,U) -> 1000028 -> 20 -> partner.Standort
-- Column: C_Project.C_BPartner_Location_ID
-- 2022-10-12T13:44:40.659Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,582288,0,541830,613231,542696,'F',TO_TIMESTAMP('2022-10-12 16:44:40.247','YYYY-MM-DD HH24:MI:SS.US'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','Y','N','N','N','N','N',0,'Standort',12,0,0,TO_TIMESTAMP('2022-10-12 16:44:40.247','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Projekt(540668,D) -> Projekt(541830,U) -> 1000028 -> 20 -> partner.Ansprechpartner
-- Column: C_Project.AD_User_ID
-- 2022-10-12T13:45:19.037Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,582289,0,541830,613232,542696,'F',TO_TIMESTAMP('2022-10-12 16:45:18.893','YYYY-MM-DD HH24:MI:SS.US'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','Y','N','N','N','N','N',0,'Ansprechpartner',15,0,0,TO_TIMESTAMP('2022-10-12 16:45:18.893','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Projekt(540668,D) -> Projekt(541830,U) -> 1000028 -> 20 -> partner.Ansprechpartner
-- Column: C_Project.AD_User_ID
-- 2022-10-12T13:45:47.784Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-10-12 16:45:47.784','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613232
;

-- UI Element: Projekt(540668,D) -> Projekt(541830,U) -> 1000028 -> 20 -> partner.Standort
-- Column: C_Project.C_BPartner_Location_ID
-- 2022-10-12T13:45:54.172Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-10-12 16:45:54.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613231
;

-- UI Element: Projekt(540668,D) -> Projekt(541830,U) -> 1000028 -> 20 -> partner.Standort
-- Column: C_Project.C_BPartner_Location_ID
-- 2022-10-12T13:47:11.512Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2022-10-12 16:47:11.512','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613231
;

-- UI Element: Projekt(540668,D) -> Projekt(541830,U) -> 1000028 -> 20 -> partner.Ansprechpartner
-- Column: C_Project.AD_User_ID
-- 2022-10-12T13:47:18.157Z
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2022-10-12 16:47:18.157','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613232
;

