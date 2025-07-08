-- Run mode: SWING_CLIENT

-- Column: SAP_GLJournalLine.C_Project_ID
-- 2025-07-08T15:28:24.562Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590486,208,0,30,541136,542276,'C_Project_ID',TO_TIMESTAMP('2025-07-08 16:28:23.581','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Financial Project','de.metas.acct',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2025-07-08 16:28:23.581','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-08T15:28:24.619Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590486 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-08T15:28:24.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(208)
;

-- 2025-07-08T15:28:38.833Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournalLine','ALTER TABLE public.SAP_GLJournalLine ADD COLUMN C_Project_ID NUMERIC(10)')
;

-- 2025-07-08T15:28:39.034Z
ALTER TABLE SAP_GLJournalLine ADD CONSTRAINT CProject_SAPGLJournalLine FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
;

-- Field: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal Line(546731,de.metas.acct) -> Projekt
-- Column: SAP_GLJournalLine.C_Project_ID
-- 2025-07-08T15:31:20.690Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590486,748944,0,546731,0,TO_TIMESTAMP('2025-07-08 16:31:19.618','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project',0,'de.metas.acct','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','N','Projekt',0,120,0,1,1,TO_TIMESTAMP('2025-07-08 16:31:19.618','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-08T15:31:20.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-08T15:31:20.791Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2025-07-08T15:31:20.873Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748944
;

-- 2025-07-08T15:31:20.922Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748944)
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal Line(546731,de.metas.acct) -> main -> 20 -> dimension.Projekt
-- Column: SAP_GLJournalLine.C_Project_ID
-- 2025-07-08T15:32:44.601Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,748944,0,546731,550193,634828,'F',TO_TIMESTAMP('2025-07-08 16:32:43.906','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',22,0,0,TO_TIMESTAMP('2025-07-08 16:32:43.906','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal Line(546731,de.metas.acct) -> main -> 20 -> dimension.Projekt
-- Column: SAP_GLJournalLine.C_Project_ID
-- 2025-07-08T15:32:55.527Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-07-08 16:32:55.526','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=634828
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal Line(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2025-07-08T15:32:55.832Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-07-08 16:32:55.831','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=616498
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal Line(546731,de.metas.acct) -> main -> 20 -> dimension.Sales order
-- Column: SAP_GLJournalLine.C_OrderSO_ID
-- 2025-07-08T15:32:56.135Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-07-08 16:32:56.135','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614562
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal Line(546731,de.metas.acct) -> main -> 20 -> dimension.Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2025-07-08T15:32:56.430Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-07-08 16:32:56.43','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614563
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal Line(546731,de.metas.acct) -> main -> 20 -> org&client.Organisation
-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2025-07-08T15:32:56.723Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-07-08 16:32:56.723','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614564
;

