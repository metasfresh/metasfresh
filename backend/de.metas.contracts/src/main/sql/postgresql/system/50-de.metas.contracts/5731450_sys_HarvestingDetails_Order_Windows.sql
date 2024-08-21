

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Erntekalender
-- Column: C_Order.C_Harvesting_Calendar_ID
-- 2024-08-20T10:39:56.754Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,
                      IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,
                      MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587165,729794,0,186,0,
                                                                                                                               TO_TIMESTAMP('2024-08-20 10:39:56.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.contracts',0,0,'Y','Y','Y','N','N','N','N','N','N',
                                                                                                                               'N',0,'Erntekalender',0,0,770,0,1,1,TO_TIMESTAMP('2024-08-20 10:39:56.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T10:39:56.759Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;



-- 2024-08-20T10:39:56.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729794
;

-- 2024-08-20T10:39:56.804Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729794)
;


-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Erntejahr
-- Column: C_Order.Harvesting_Year_ID
-- 2024-08-20T10:44:54.747Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,
                      EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,
                      IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES
    (0,587166,729795,0,186,0,TO_TIMESTAMP('2024-08-20 10:44:54.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.contracts',
     0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Erntejahr',0,0,770,0,1,1,TO_TIMESTAMP('2024-08-20 10:44:54.587000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T10:44:54.749Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T10:44:54.750Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471)
;

-- 2024-08-20T10:44:54.754Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729795
;

-- 2024-08-20T10:44:54.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729795)
;




-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Erntekalender
-- Column: C_Order.C_Harvesting_Calendar_ID
-- 2024-08-20T10:49:28.203Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729794,0,186,540499,625258,'F',TO_TIMESTAMP('2024-08-20 10:49:27.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Erntekalender',540,0,0,TO_TIMESTAMP('2024-08-20 10:49:27.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Erntejahr
-- Column: C_Order.Harvesting_Year_ID
-- 2024-08-20T10:49:39.543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729795,0,186,540499,625259,'F',TO_TIMESTAMP('2024-08-20 10:49:39.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Erntejahr',550,0,0,TO_TIMESTAMP('2024-08-20 10:49:39.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;



















-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Erntekalender
-- Column: C_Order.C_Harvesting_Calendar_ID
-- 2024-08-20T10:55:29.771Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,
IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,
MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587165,729798,0,294,0,
TO_TIMESTAMP('2024-08-20 10:55:29.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.contracts',0,0,'Y','Y','Y','N','N','N','N','N','N',
'N',0,'Erntekalender',0,0,230,0,1,1,TO_TIMESTAMP('2024-08-20 10:55:29.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T10:55:29.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T10:55:29.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157)
;

-- 2024-08-20T10:55:29.778Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729798
;

-- 2024-08-20T10:55:29.779Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729798)
;





















-- Field: Bestellung(181,D) -> Bestellung(294,D) -> Erntejahr
-- Column: C_Order.Harvesting_Year_ID
-- 2024-08-20T10:55:39.381Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,
IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,
SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587166,729799,0,294,0,
TO_TIMESTAMP('2024-08-20 10:55:39.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.contracts',0,0,'Y','Y','Y','N','N','N','N','N','N','N',
0,'Erntejahr',0,0,240,0,1,1,TO_TIMESTAMP('2024-08-20 10:55:39.261000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T10:55:39.382Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T10:55:39.383Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471)
;

-- 2024-08-20T10:55:39.386Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729799
;

-- 2024-08-20T10:55:39.387Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729799)
;



-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Erntekalender
-- Column: C_Order.C_Harvesting_Calendar_ID
-- 2024-08-20T13:42:37.929Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729798,0,294,540961,625260,'F',TO_TIMESTAMP('2024-08-20 13:42:37.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Erntekalender',370,0,0,TO_TIMESTAMP('2024-08-20 13:42:37.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Erntejahr
-- Column: C_Order.Harvesting_Year_ID
-- 2024-08-20T13:42:46.909Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729799,0,294,540961,625261,'F',TO_TIMESTAMP('2024-08-20 13:42:46.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Erntejahr',380,0,0,TO_TIMESTAMP('2024-08-20 13:42:46.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Erntejahr
-- Column: C_Order.Harvesting_Year_ID
-- 2024-08-20T13:46:35.111Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-08-20 13:46:35.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625261
;

-- UI Element: Bestellung(181,D) -> Bestellung(294,D) -> advanced edit -> 10 -> advanced edit.Erntekalender
-- Column: C_Order.C_Harvesting_Calendar_ID
-- 2024-08-20T13:46:37.436Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-08-20 13:46:37.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625260
;


-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Auktion
-- Column: C_Order.C_Auction_ID
-- 2024-08-20T13:47:21.747Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-08-20 13:47:21.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=620467
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Erntekalender
-- Column: C_Order.C_Harvesting_Calendar_ID
-- 2024-08-20T13:47:22.540Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-08-20 13:47:22.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625258
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Erntejahr
-- Column: C_Order.Harvesting_Year_ID
-- 2024-08-20T13:47:25.002Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-08-20 13:47:25.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625259
;


