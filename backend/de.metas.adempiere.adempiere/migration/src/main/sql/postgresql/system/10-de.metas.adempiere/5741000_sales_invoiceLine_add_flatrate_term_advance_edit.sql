-- Field: Rechnung(167,D) -> Rechnungsposition(270,D) -> Pauschale - Vertragsperiode
-- Column: C_InvoiceLine.C_Flatrate_Term_ID
-- 2024-12-04T14:02:53.034Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579364,734072,0,270,TO_TIMESTAMP('2024-12-04 15:02:52.895','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Pauschale - Vertragsperiode',TO_TIMESTAMP('2024-12-04 15:02:52.895','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-12-04T14:02:53.038Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-12-04T14:02:53.042Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447)
;

-- 2024-12-04T14:02:53.051Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734072
;

-- 2024-12-04T14:02:53.052Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734072)
;

-- UI Element: Rechnung(167,D) -> Rechnungsposition(270,D) -> main -> 10 -> default.Pauschale - Vertragsperiode
-- Column: C_InvoiceLine.C_Flatrate_Term_ID
-- 2024-12-04T14:03:43.200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,734072,0,270,540023,627382,'F',TO_TIMESTAMP('2024-12-04 15:03:43.067','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Pauschale - Vertragsperiode',270,0,0,TO_TIMESTAMP('2024-12-04 15:03:43.067','YYYY-MM-DD HH24:MI:SS.US'),100)
;

