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


-- Column: S_Issue.InvoicingErrorMsg
-- 2022-10-19T14:30:22.899Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584773,579263,0,14,541468,'InvoicingErrorMsg',TO_TIMESTAMP('2022-10-19 17:30:22.603','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Fehler, der beim Versuch aufgetreten ist, diesen Datensatz zu Fakturieren.','de.metas.serviceprovider',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Fehlermeldung','1=1',0,0,TO_TIMESTAMP('2022-10-19 17:30:22.603','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-19T14:30:22.903Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584773 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-19T14:30:22.950Z
/* DDL */  select update_Column_Translation_From_AD_Element(579263)
;

-- 2022-10-19T14:30:26.468Z
/* DDL */ SELECT public.db_alter_table('S_Issue','ALTER TABLE public.S_Issue ADD COLUMN InvoicingErrorMsg VARCHAR(2000)')
;

-- Field: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Fehlermeldung
-- Column: S_Issue.InvoicingErrorMsg
-- 2022-10-19T14:31:22.730Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584773,707808,0,542341,TO_TIMESTAMP('2022-10-19 17:31:22.555','YYYY-MM-DD HH24:MI:SS.US'),100,'Fehler, der beim Versuch aufgetreten ist, diesen Datensatz zu Fakturieren.',2000,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Fehlermeldung',TO_TIMESTAMP('2022-10-19 17:31:22.555','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-19T14:31:22.733Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-19T14:31:22.736Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579263)
;

-- 2022-10-19T14:31:22.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707808
;

-- 2022-10-19T14:31:22.766Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707808)
;

-- Field: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Fehlermeldung
-- Column: S_Issue.InvoicingErrorMsg
-- 2022-10-19T14:36:40.276Z
UPDATE AD_Field SET DisplayLogic='@InvoicingErrorMsg@!""',Updated=TO_TIMESTAMP('2022-10-19 17:36:40.276','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=707808
;

-- Tab: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider)
-- UI Section: error
-- 2022-10-19T14:37:56.467Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542341,545283,TO_TIMESTAMP('2022-10-19 17:37:56.346','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',40,TO_TIMESTAMP('2022-10-19 17:37:56.346','YYYY-MM-DD HH24:MI:SS.US'),100,'error')
;

-- 2022-10-19T14:37:56.470Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545283 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> error
-- UI Column: 10
-- 2022-10-19T14:38:16.198Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546427,545283,TO_TIMESTAMP('2022-10-19 17:38:16.033','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-10-19 17:38:16.033','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> error -> 10
-- UI Element Group: error
-- 2022-10-19T14:38:25.769Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546427,549982,TO_TIMESTAMP('2022-10-19 17:38:25.635','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','error',10,TO_TIMESTAMP('2022-10-19 17:38:25.635','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> error -> 10 -> error.Fehlermeldung
-- Column: S_Issue.InvoicingErrorMsg
-- 2022-10-19T14:41:01.154Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707808,0,542341,613287,549982,'F',TO_TIMESTAMP('2022-10-19 17:41:00.988','YYYY-MM-DD HH24:MI:SS.US'),100,'Fehler, der beim Versuch aufgetreten ist, diesen Datensatz zu Fakturieren.','Y','N','N','Y','N','N','N',0,'Fehlermeldung',10,0,0,TO_TIMESTAMP('2022-10-19 17:41:00.988','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Effort issue(540871,de.metas.serviceprovider) -> Issue(542340,de.metas.serviceprovider) -> Fehlermeldung
-- Column: S_Issue.InvoicingErrorMsg
-- 2022-10-19T14:41:32.797Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584773,707809,0,542340,TO_TIMESTAMP('2022-10-19 17:41:32.638','YYYY-MM-DD HH24:MI:SS.US'),100,'Fehler, der beim Versuch aufgetreten ist, diesen Datensatz zu Fakturieren.',2000,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Fehlermeldung',TO_TIMESTAMP('2022-10-19 17:41:32.638','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-19T14:41:32.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-19T14:41:32.801Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579263)
;

-- 2022-10-19T14:41:32.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707809
;

-- 2022-10-19T14:41:32.805Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707809)
;

-- Field: Effort issue(540871,de.metas.serviceprovider) -> Issue(542340,de.metas.serviceprovider) -> Fehlermeldung
-- Column: S_Issue.InvoicingErrorMsg
-- 2022-10-19T14:41:58.361Z
UPDATE AD_Field SET DisplayLogic='@InvoicingErrorMsg@!""',Updated=TO_TIMESTAMP('2022-10-19 17:41:58.361','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=707809
;

-- Tab: Effort issue(540871,de.metas.serviceprovider) -> Issue(542340,de.metas.serviceprovider)
-- UI Section: error
-- 2022-10-19T14:42:33.032Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542340,545284,TO_TIMESTAMP('2022-10-19 17:42:32.91','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',30,TO_TIMESTAMP('2022-10-19 17:42:32.91','YYYY-MM-DD HH24:MI:SS.US'),100,'error')
;

-- 2022-10-19T14:42:33.033Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545284 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Effort issue(540871,de.metas.serviceprovider) -> Issue(542340,de.metas.serviceprovider) -> error
-- UI Column: 10
-- 2022-10-19T14:42:36.903Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546428,545284,TO_TIMESTAMP('2022-10-19 17:42:36.802','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-10-19 17:42:36.802','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Effort issue(540871,de.metas.serviceprovider) -> Issue(542340,de.metas.serviceprovider) -> error -> 10
-- UI Element Group: error
-- 2022-10-19T14:42:42.368Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546428,549983,TO_TIMESTAMP('2022-10-19 17:42:42.247','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','error',10,TO_TIMESTAMP('2022-10-19 17:42:42.247','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Effort issue(540871,de.metas.serviceprovider) -> Issue(542340,de.metas.serviceprovider) -> error -> 10 -> error.Fehlermeldung
-- Column: S_Issue.InvoicingErrorMsg
-- 2022-10-19T14:42:56.208Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707809,0,542340,613288,549983,'F',TO_TIMESTAMP('2022-10-19 17:42:56.076','YYYY-MM-DD HH24:MI:SS.US'),100,'Fehler, der beim Versuch aufgetreten ist, diesen Datensatz zu Fakturieren.','Y','N','N','Y','N','N','N',0,'Fehlermeldung',10,0,0,TO_TIMESTAMP('2022-10-19 17:42:56.076','YYYY-MM-DD HH24:MI:SS.US'),100)
;

