-- Tab: Budget-Issue(540859,de.metas.serviceprovider) -> Issue
-- Table: S_Issue
-- 2022-10-11T08:48:05.426Z
UPDATE AD_Tab SET ReadOnlyLogic='@Processed@=''Y''',Updated=TO_TIMESTAMP('2022-10-11 11:48:05.425','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=542341
;

-- Tab: Effort issue(540871,de.metas.serviceprovider) -> Issue
-- Table: S_Issue
-- 2022-10-11T08:48:31.530Z
UPDATE AD_Tab SET ReadOnlyLogic='@Processed@=''Y''',Updated=TO_TIMESTAMP('2022-10-11 11:48:31.53','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=542340
;

-- Tab: Time Booking(540907,de.metas.serviceprovider) -> S_TimeBooking
-- Table: S_TimeBooking
-- 2022-10-11T08:51:06.498Z
UPDATE AD_Tab SET ReadOnlyLogic='@IsBudgetIssueInvoiced@=''Y''',Updated=TO_TIMESTAMP('2022-10-11 11:51:06.498','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=542445
;


-- UI Element: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> main -> 20 -> flags.Verarbeitet
-- Column: S_Issue.Processed
-- 2022-10-13T14:27:32.455Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,598887,0,542341,613264,543570,'F',TO_TIMESTAMP('2022-10-13 17:27:32.204','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',80,0,0,TO_TIMESTAMP('2022-10-13 17:27:32.204','YYYY-MM-DD HH24:MI:SS.US'),100)
;