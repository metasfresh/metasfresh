-- Field: Geschäftspartner_OLD -> Lieferant -> Eigene-Kd. Nr.
-- Column: C_BPartner.CustomerNoAtVendor
-- Field: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> Eigene-Kd. Nr.
-- Column: C_BPartner.CustomerNoAtVendor
-- 2025-03-07T09:30:13.715Z
delete from ad_ui_element where ad_field_id in (select ad_field_id from ad_field where ad_tab_id=224 and ad_column_id=560221);
delete from ad_field where ad_tab_id=224 and ad_column_id=560221;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560221,740365,0,224,0,TO_TIMESTAMP('2025-03-07 09:30:12.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Eigene-Kd. Nr. ',0,0,210,0,1,1,TO_TIMESTAMP('2025-03-07 09:30:12.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-03-07T09:30:13.766Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=740365 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-07T09:30:13.850Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544104) 
;

-- 2025-03-07T09:30:13.925Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740365
;

-- 2025-03-07T09:30:13.975Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740365)
;

-- UI Element: Geschäftspartner_OLD -> Lieferant.Eigene-Kd. Nr.
-- Column: C_BPartner.CustomerNoAtVendor
-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Eigene-Kd. Nr.
-- Column: C_BPartner.CustomerNoAtVendor
-- 2025-03-07T09:30:58.533Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740365,0,224,1000033,630671,'F',TO_TIMESTAMP('2025-03-07 09:30:57.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Eigene-Kd. Nr. ',15,0,0,TO_TIMESTAMP('2025-03-07 09:30:57.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: CustomerNoAtVendor
-- 2025-03-07T09:35:34.469Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Customer No At Vendor', PrintName='Customer No At Vendor',Updated=TO_TIMESTAMP('2025-03-07 09:35:34.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=544104 AND AD_Language='en_US'
;

-- 2025-03-07T09:35:34.575Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544104,'en_US') 
;

