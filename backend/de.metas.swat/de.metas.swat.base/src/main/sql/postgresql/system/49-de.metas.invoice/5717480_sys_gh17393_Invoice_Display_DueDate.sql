-- Run mode: SWING_CLIENT

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Datum Fälligkeit
-- Column: C_Invoice.DueDate
-- 2024-02-15T18:08:21.794Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584270,725185,0,263,TO_TIMESTAMP('2024-02-15 18:08:20.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, zu dem Zahlung fällig wird',7,'D','Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.','Y','N','N','N','N','N','N','N','Datum Fälligkeit',TO_TIMESTAMP('2024-02-15 18:08:20.605000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-02-15T18:08:21.806Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725185 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-15T18:08:21.855Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2000)
;

-- 2024-02-15T18:08:21.881Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725185
;

-- 2024-02-15T18:08:21.887Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725185)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> main -> 20 -> dates.Datum Fälligkeit
-- Column: C_Invoice.DueDate
-- 2024-02-15T18:09:08.076Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725185,0,263,622948,540027,'F',TO_TIMESTAMP('2024-02-15 18:09:07.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, zu dem Zahlung fällig wird','Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.','Y','N','N','Y','N','N','N',0,'Datum Fälligkeit',25,0,0,TO_TIMESTAMP('2024-02-15 18:09:07.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

