-- Run mode: SWING_CLIENT

-- Field: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> Rechnungszustellung
-- Column: C_BPartner.InvoiceDelivery
-- 2024-07-02T08:31:09.070Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588660,729017,0,220,0,TO_TIMESTAMP('2024-07-02 10:31:08.85','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.document.archive',0,'Y','Y','N','N','N','N','N','N','N','Rechnungszustellung',0,520,0,1,1,TO_TIMESTAMP('2024-07-02 10:31:08.85','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-02T08:31:09.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-02T08:31:09.088Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583161)
;

-- 2024-07-02T08:31:09.104Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729017
;

-- 2024-07-02T08:31:09.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729017)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.Rechnungszustellung
-- Column: C_BPartner.InvoiceDelivery
-- 2024-07-02T08:33:11.751Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729017,0,220,1000011,624961,'F',TO_TIMESTAMP('2024-07-02 10:33:11.601','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungszustellung',90,0,0,TO_TIMESTAMP('2024-07-02 10:33:11.601','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Rechnungszustellung
-- Column: C_BP_Group.InvoiceDelivery
-- 2024-07-02T08:34:37.047Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588661,729020,0,322,0,TO_TIMESTAMP('2024-07-02 10:34:36.911','YYYY-MM-DD HH24:MI:SS.US'),100,0,'U',0,'Y','Y','N','N','N','N','N','N','N','Rechnungszustellung',0,240,0,1,1,TO_TIMESTAMP('2024-07-02 10:34:36.911','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-02T08:34:37.050Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-02T08:34:37.052Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583161)
;

-- 2024-07-02T08:34:37.056Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729020
;

-- 2024-07-02T08:34:37.058Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729020)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 20 -> flags.Rechnungszustellung
-- Column: C_BP_Group.InvoiceDelivery
-- 2024-07-02T08:43:26.507Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729020,0,322,540482,624962,'F',TO_TIMESTAMP('2024-07-02 10:43:26.361','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungszustellung',50,0,0,TO_TIMESTAMP('2024-07-02 10:43:26.361','YYYY-MM-DD HH24:MI:SS.US'),100)
;
