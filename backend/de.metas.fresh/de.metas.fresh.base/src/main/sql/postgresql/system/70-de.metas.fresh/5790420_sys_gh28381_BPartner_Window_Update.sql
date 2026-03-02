-- Run mode: SWING_CLIENT

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> advanced edit -> 10 -> advanced edit.Factor
-- Column: C_BPartner.IsFactorer
-- 2026-02-25T14:59:05.429Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540671, SeqNo=170,Updated=TO_TIMESTAMP('2026-02-25 14:59:05.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641335
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> advanced edit -> 10 -> advanced edit.Factor
-- Column: C_BPartner.IsFactorer
-- 2026-02-25T14:59:46.073Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2026-02-25 14:59:46.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641335
;

-- Field: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> Factoring
-- Column: C_BPartner.IsFactoring
-- 2026-02-25T15:01:12.391Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591859,774758,0,220,0,TO_TIMESTAMP('2026-02-25 15:01:11.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.',0,'D',0,'Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Factoring',0,0,450,0,1,1,TO_TIMESTAMP('2026-02-25 15:01:11.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-25T15:01:12.470Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774758 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-25T15:01:12.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584395)
;

-- 2026-02-25T15:01:12.671Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=774758
;

-- 2026-02-25T15:01:12.750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(774758)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.Factoring
-- Column: C_BPartner.IsFactoring
-- 2026-02-25T15:02:04.903Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,774758,0,220,1000011,648451,'F',TO_TIMESTAMP('2026-02-25 15:02:04.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.','Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.','Y','N','N','Y','N','N','N',0,'Factoring',115,0,0,TO_TIMESTAMP('2026-02-25 15:02:04.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Factoring
-- Column: C_BPartner.IsFactoring
-- 2026-02-25T15:02:50.302Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=641336
;

