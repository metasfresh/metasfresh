-- Run mode: SWING_CLIENT

-- Field: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> Eigene-Kd. Nr.
-- Column: C_BPartner.CustomerNoAtVendor
-- 2024-11-07T09:48:17.700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560221,733463,0,224,0,TO_TIMESTAMP('2024-11-07 11:48:17.468','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Eigene-Kd. Nr. ',0,200,0,1,1,TO_TIMESTAMP('2024-11-07 11:48:17.468','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-07T09:48:17.713Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=733463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-07T09:48:17.744Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544104)
;

-- 2024-11-07T09:48:17.771Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=733463
;

-- 2024-11-07T09:48:17.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(733463)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Lieferant(224,D) -> main -> 10 -> default.Eigene-Kd. Nr.
-- Column: C_BPartner.CustomerNoAtVendor
-- 2024-11-07T09:49:24.910Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,733463,0,224,1000033,627000,'F',TO_TIMESTAMP('2024-11-07 11:49:24.754','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Eigene-Kd. Nr. ',116,0,0,TO_TIMESTAMP('2024-11-07 11:49:24.754','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Window: Packvorschriften, InternalName=_Packvorschrift
-- 2024-11-07T10:08:59.886Z
UPDATE AD_Window SET IsOverrideInMenu='Y', Overrides_Window_ID=540188,Updated=TO_TIMESTAMP('2024-11-07 12:08:59.884','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=540343
;

