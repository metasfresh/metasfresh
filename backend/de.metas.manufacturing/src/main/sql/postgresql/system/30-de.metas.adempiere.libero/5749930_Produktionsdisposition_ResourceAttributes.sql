-- Run mode: SWING_CLIENT

-- UI Column: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10
-- UI Element Group: attributes
-- 2025-03-24T18:06:08.167Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544698,552660,TO_TIMESTAMP('2025-03-24 18:06:07.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','attributes',30,TO_TIMESTAMP('2025-03-24 18:06:07.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> attributes.Attributes
-- 2025-03-24T18:06:36.176Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,544794,552660,630840,'A',TO_TIMESTAMP('2025-03-24 18:06:35.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Attributes',10,0,0,TO_TIMESTAMP('2025-03-24 18:06:35.991000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> attributes.Produktionsstätte
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2025-03-24T18:08:55.896Z
UPDATE AD_UI_Element SET AD_Field_ID=667477, Name='Produktionsstätte',Updated=TO_TIMESTAMP('2025-03-24 18:08:55.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630840
;

-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> attributes.Resource Attributes
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2025-03-25T08:42:03.796Z
UPDATE AD_UI_Element SET Name='Resource Attributes',Updated=TO_TIMESTAMP('2025-03-25 08:42:03.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630840
;

-- UI Element: Produktionsdisposition(541316,EE01) -> Produktionsdisposition(544794,EE01) -> main -> 10 -> attributes.Resource Attributes
-- Column: PP_Order_Candidate.S_Resource_ID
-- 2025-03-25T08:47:21.689Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2025-03-25 08:47:21.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=630840
;

