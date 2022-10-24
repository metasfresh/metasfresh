-- Column: PP_ComponentGenerator.Description
-- 2022-10-19T13:41:43.405Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584772,275,0,10,541554,'Description',TO_TIMESTAMP('2022-10-19 16:41:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2022-10-19 16:41:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-19T13:41:43.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584772 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-19T13:41:43.543Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2022-10-19T13:41:50.217Z
/* DDL */ SELECT public.db_alter_table('PP_ComponentGenerator','ALTER TABLE public.PP_ComponentGenerator ADD COLUMN Description VARCHAR(2000)')
;



-- Field: Komponentengenerator -> Komponentengenerator -> Beschreibung
-- Column: PP_ComponentGenerator.Description
-- 2022-10-19T13:49:32.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584772,707807,0,543238,0,TO_TIMESTAMP('2022-10-19 16:49:31','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Beschreibung',0,10,0,1,1,TO_TIMESTAMP('2022-10-19 16:49:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-19T13:49:32.579Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-19T13:49:32.616Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-10-19T13:49:32.790Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707807
;

-- 2022-10-19T13:49:32.823Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707807)
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Beschreibung
-- Column: PP_ComponentGenerator.Description
-- 2022-10-19T13:51:05.569Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707807,0,543238,544666,613286,'F',TO_TIMESTAMP('2022-10-19 16:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',20,0,0,TO_TIMESTAMP('2022-10-19 16:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Reihenfolge
-- Column: PP_ComponentGenerator.AD_Sequence_ID
-- 2022-10-19T13:51:44.733Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-10-19 16:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575582
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Produkt
-- Column: PP_ComponentGenerator.M_Product_ID
-- 2022-10-19T13:51:44.942Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-10-19 16:51:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575577
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Aktiv
-- Column: PP_ComponentGenerator.IsActive
-- 2022-10-19T13:51:45.190Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-10-19 16:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575579
;

-- UI Element: Komponentengenerator -> Komponentengenerator.AD_JavaClass
-- Column: PP_ComponentGenerator.AD_JavaClass_ID
-- 2022-10-19T13:51:45.388Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-10-19 16:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575578
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Beschreibung
-- Column: PP_ComponentGenerator.Description
-- 2022-10-19T13:51:45.583Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-10-19 16:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613286
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Sektion
-- Column: PP_ComponentGenerator.AD_Org_ID
-- 2022-10-19T13:51:45.785Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-10-19 16:51:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575580
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Produkt
-- Column: PP_ComponentGenerator.M_Product_ID
-- 2022-10-19T13:52:56.657Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-10-19 16:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575577
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Aktiv
-- Column: PP_ComponentGenerator.IsActive
-- 2022-10-19T13:52:56.894Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-10-19 16:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575579
;

-- UI Element: Komponentengenerator -> Komponentengenerator.Reihenfolge
-- Column: PP_ComponentGenerator.AD_Sequence_ID
-- 2022-10-19T13:52:57.113Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-10-19 16:52:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575582
;

-- Table: PP_ComponentGenerator
-- 2022-10-19T13:54:25.761Z
UPDATE AD_Table SET AD_Window_ID=541010,Updated=TO_TIMESTAMP('2022-10-19 16:54:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541554
;

