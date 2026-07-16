-- Run mode: SWING_CLIENT

-- Field: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> Containernummer
-- Column: C_Customs_Invoice.ContainerNumber
-- 2025-12-18T19:16:32.949Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591809,760955,0,541767,0,TO_TIMESTAMP('2025-12-18 19:16:31.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Containernummer',0,0,20,0,1,1,TO_TIMESTAMP('2025-12-18 19:16:31.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T19:16:33.009Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-18T19:16:33.092Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584381)
;

-- 2025-12-18T19:16:33.160Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760955
;

-- 2025-12-18T19:16:33.220Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760955)
;

-- Field: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> Plombennummer
-- Column: C_Customs_Invoice.SealNumber
-- 2025-12-18T19:17:12.539Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591810,760956,0,541767,0,TO_TIMESTAMP('2025-12-18 19:17:11.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Plombennummer',0,0,30,0,1,1,TO_TIMESTAMP('2025-12-18 19:17:11.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T19:17:12.599Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-18T19:17:12.660Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584382)
;

-- 2025-12-18T19:17:12.721Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760956
;

-- 2025-12-18T19:17:12.964Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760956)
;

-- Field: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> Incoterms
-- Column: C_Customs_Invoice.C_Incoterms_ID
-- 2025-12-18T19:17:45.110Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591811,760957,0,541767,0,TO_TIMESTAMP('2025-12-18 19:17:43.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Incoterms',0,0,40,0,1,1,TO_TIMESTAMP('2025-12-18 19:17:43.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T19:17:45.172Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-18T19:17:45.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927)
;

-- 2025-12-18T19:17:45.293Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760957
;

-- 2025-12-18T19:17:45.356Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760957)
;

-- Field: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> Transportmittel
-- Column: C_Customs_Invoice.ModeOfTransport
-- 2025-12-18T19:18:22.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591812,760958,0,541767,0,TO_TIMESTAMP('2025-12-18 19:18:21.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Transportmittel',0,0,50,0,1,1,TO_TIMESTAMP('2025-12-18 19:18:21.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T19:18:22.251Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-18T19:18:22.310Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584383)
;

-- 2025-12-18T19:18:22.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760958
;

-- 2025-12-18T19:18:22.430Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760958)
;

-- UI Column: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 10
-- UI Element Group: transport
-- 2025-12-18T19:18:50.206Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541692,554117,TO_TIMESTAMP('2025-12-18 19:18:49.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','transport',20,TO_TIMESTAMP('2025-12-18 19:18:49.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 10 -> transport.Containernummer
-- Column: C_Customs_Invoice.ContainerNumber
-- 2025-12-18T19:19:15.721Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760955,0,541767,554117,641294,'F',TO_TIMESTAMP('2025-12-18 19:19:14.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Containernummer',10,0,0,TO_TIMESTAMP('2025-12-18 19:19:14.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 10 -> transport.Plombennummer
-- Column: C_Customs_Invoice.SealNumber
-- 2025-12-18T19:19:37.064Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760956,0,541767,554117,641295,'F',TO_TIMESTAMP('2025-12-18 19:19:36.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Plombennummer',20,0,0,TO_TIMESTAMP('2025-12-18 19:19:36.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 10 -> transport.Incoterms
-- Column: C_Customs_Invoice.C_Incoterms_ID
-- 2025-12-18T19:19:50.785Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760957,0,541767,554117,641296,'F',TO_TIMESTAMP('2025-12-18 19:19:50.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Incoterms',30,0,0,TO_TIMESTAMP('2025-12-18 19:19:50.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnung(541767,D) -> main -> 10 -> transport.Transportmittel
-- Column: C_Customs_Invoice.ModeOfTransport
-- 2025-12-18T19:20:08.677Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760958,0,541767,554117,641297,'F',TO_TIMESTAMP('2025-12-18 19:20:07.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Transportmittel',40,0,0,TO_TIMESTAMP('2025-12-18 19:20:07.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> SSCC18 abw.
-- Column: C_Customs_Invoice_Line.SSCC18_Override
-- 2025-12-18T19:21:23.771Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591814,760959,0,541768,TO_TIMESTAMP('2025-12-18 19:21:22.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','SSCC18 abw.',TO_TIMESTAMP('2025-12-18 19:21:22.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T19:21:23.835Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760959 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-18T19:21:23.893Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584384)
;

-- 2025-12-18T19:21:23.954Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760959
;

-- 2025-12-18T19:21:24.011Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760959)
;

-- Field: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> Einzelpreis
-- Column: C_Customs_Invoice_Line.PriceActual
-- 2025-12-18T19:21:24.996Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591815,760960,0,541768,TO_TIMESTAMP('2025-12-18 19:21:24.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Effektiver Preis',22,'D','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','N','N','N','N','N','N','Einzelpreis',TO_TIMESTAMP('2025-12-18 19:21:24.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T19:21:25.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-18T19:21:25.123Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(519)
;

-- 2025-12-18T19:21:25.185Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760960
;

-- 2025-12-18T19:21:25.247Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760960)
;

-- Field: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> SSCC18
-- Column: C_Customs_Invoice_Line.IPA_SSCC18
-- 2025-12-18T19:26:15.567Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591813,760961,0,541768,0,TO_TIMESTAMP('2025-12-18 19:26:14.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'SSCC18',0,0,30,0,1,1,TO_TIMESTAMP('2025-12-18 19:26:14.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-18T19:26:15.625Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-18T19:26:15.686Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542693)
;

-- 2025-12-18T19:26:15.748Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760961
;

-- 2025-12-18T19:26:15.807Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760961)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.SSCC18
-- Column: C_Customs_Invoice_Line.IPA_SSCC18
-- 2025-12-18T19:26:45.085Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760961,0,541768,542552,641298,'F',TO_TIMESTAMP('2025-12-18 19:26:44.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'SSCC18',22,0,0,TO_TIMESTAMP('2025-12-18 19:26:44.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.SSCC18 abw.
-- Column: C_Customs_Invoice_Line.SSCC18_Override
-- 2025-12-18T19:27:08.006Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760959,0,541768,542552,641299,'F',TO_TIMESTAMP('2025-12-18 19:27:07.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'SSCC18 abw.',70,0,0,TO_TIMESTAMP('2025-12-18 19:27:07.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.SSCC18 abw.
-- Column: C_Customs_Invoice_Line.SSCC18_Override
-- 2025-12-18T19:27:27.885Z
UPDATE AD_UI_Element SET SeqNo=24,Updated=TO_TIMESTAMP('2025-12-18 19:27:27.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641299
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.Einzelpreis
-- Column: C_Customs_Invoice_Line.PriceActual
-- 2025-12-18T19:29:30.556Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760960,0,541768,542552,641300,'F',TO_TIMESTAMP('2025-12-18 19:29:29.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','N','Y','N','N','N',0,'Einzelpreis',42,0,0,TO_TIMESTAMP('2025-12-18 19:29:29.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.SSCC18
-- Column: C_Customs_Invoice_Line.IPA_SSCC18
-- 2025-12-18T19:30:00.874Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-12-18 19:30:00.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641298
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.SSCC18 abw.
-- Column: C_Customs_Invoice_Line.SSCC18_Override
-- 2025-12-18T19:30:01.229Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-12-18 19:30:01.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641299
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.qty
-- Column: C_Customs_Invoice_Line.InvoicedQty
-- 2025-12-18T19:30:01.588Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-12-18 19:30:01.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=559180
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.uom
-- Column: C_Customs_Invoice_Line.C_UOM_ID
-- 2025-12-18T19:30:01.943Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-12-18 19:30:01.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=559181
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.Einzelpreis
-- Column: C_Customs_Invoice_Line.PriceActual
-- 2025-12-18T19:30:02.304Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-12-18 19:30:02.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641300
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.lineNetAmt
-- Column: C_Customs_Invoice_Line.LineNetAmt
-- 2025-12-18T19:30:02.671Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-12-18 19:30:02.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=559182
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.customs tariff
-- Column: C_Customs_Invoice_Line.CustomsTariff
-- 2025-12-18T19:30:03.030Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-12-18 19:30:03.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=560018
;

-- UI Element: Zollrechnung(540643,D) -> Zollrechnungsposition(541768,D) -> main -> 10 -> default.uom
-- Column: C_Customs_Invoice_Line.C_UOM_ID
-- 2025-12-18T19:31:11.038Z
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2025-12-18 19:31:11.038000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=559181
;

