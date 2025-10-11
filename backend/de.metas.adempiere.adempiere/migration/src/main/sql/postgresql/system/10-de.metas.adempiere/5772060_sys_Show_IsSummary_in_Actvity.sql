-- UI Element: Kostenstelle(134,D) -> Aktivität(249,D) -> main -> 20 -> flags.Zusammenfassungseintrag
-- Column: C_Activity.IsSummary
-- 2025-10-03T14:38:07.572Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2619,0,249,540311,637721,'F',TO_TIMESTAMP('2025-10-03 14:38:07.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist ein Zusammenfassungseintrag','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','N','N','Y','N','N','N',0,'Zusammenfassungseintrag',20,0,0,TO_TIMESTAMP('2025-10-03 14:38:07.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Activity.IsSummary
-- 2025-10-03T14:46:48.330Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-03 14:46:48.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=3467
;

-- Name: C_Activity_of_@AD_Org_ID@_or_0
-- 2025-10-03T15:02:32.005Z
UPDATE AD_Val_Rule SET Code='C_Activity.AD_Org_ID IN (@AD_Org_ID@, 0) AND C_Activity.IsSummary=''N''',Updated=TO_TIMESTAMP('2025-10-03 15:02:32.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540253
;

