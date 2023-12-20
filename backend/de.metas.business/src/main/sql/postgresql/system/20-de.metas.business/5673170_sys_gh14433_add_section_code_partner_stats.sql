-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-24T02:23:07.554Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585604,581238,0,19,540763,'M_SectionCode_ID',TO_TIMESTAMP('2023-01-24 03:23:07','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2023-01-24 03:23:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-24T02:23:07.557Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585604 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-24T02:23:07.580Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2023-01-24T02:23:25.092Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Stats','ALTER TABLE public.C_BPartner_Stats ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2023-01-24T02:23:25.242Z
ALTER TABLE C_BPartner_Stats ADD CONSTRAINT MSectionCode_CBPartnerStats FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Business Partner_OLD(123,D) -> Statistics(540739,D) -> Section Code
-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-24T02:25:25.631Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585604,710346,0,540739,0,TO_TIMESTAMP('2023-01-24 03:25:25','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Section Code',0,100,0,1,1,TO_TIMESTAMP('2023-01-24 03:25:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-24T02:25:25.634Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710346 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-24T02:25:25.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-01-24T02:25:25.668Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710346
;

-- 2023-01-24T02:25:25.670Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710346)
;

-- Field: Business Partner_OLD(123,D) -> Statistics(540739,D) -> Section Code
-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-24T02:25:28.639Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-01-24 03:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710346
;

-- UI Element: Business Partner_OLD(123,D) -> Statistics(540739,D) -> main -> 10 -> default.Section Code
-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-24T02:26:24.682Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710346,0,540739,1000038,614780,'F',TO_TIMESTAMP('2023-01-24 03:26:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',80,0,0,TO_TIMESTAMP('2023-01-24 03:26:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Statistics(540739,D) -> main -> 10 -> default.Section Code
-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-24T02:27:09.382Z
UPDATE AD_UI_Element SET SeqNo=55,Updated=TO_TIMESTAMP('2023-01-24 03:27:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614780
;

-- UI Element: Business Partner_OLD(123,D) -> Statistics(540739,D) -> main -> 10 -> default.Section Code
-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-24T02:29:07.180Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-24 03:29:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614780
;

-- UI Element: Business Partner_OLD(123,D) -> Statistics(540739,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner_Stats.AD_Org_ID
-- 2023-01-24T02:29:07.186Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-01-24 03:29:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546570
;

-- UI Element: Business Partner_OLD(123,D) -> Statistics(540739,D) -> main -> 10 -> default.Section Code
-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-24T02:29:21.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-01-24 03:29:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614780
;

-- UI Element: Business Partner_OLD(123,D) -> Statistics(540739,D) -> main -> 10 -> default.Aktiv
-- Column: C_BPartner_Stats.IsActive
-- 2023-01-24T02:29:21.503Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-01-24 03:29:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000358
;

-- UI Element: Business Partner_OLD(123,D) -> Statistics(540739,D) -> main -> 10 -> default.Section Code
-- Column: C_BPartner_Stats.M_SectionCode_ID
-- 2023-01-24T02:32:13.326Z
UPDATE AD_UI_Element SET SeqNo=48,Updated=TO_TIMESTAMP('2023-01-24 03:32:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614780
;

