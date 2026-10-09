-- Run mode: SWING_CLIENT

-- Column: SAP_GLJournal.C_Project_ID
-- 2025-07-03T15:21:10.838Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590481,208,0,30,541136,542275,'C_Project_ID',TO_TIMESTAMP('2025-07-03 16:21:09.819','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Financial Project','de.metas.acct',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2025-07-03 16:21:09.819','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-07-03T15:21:10.897Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590481 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-03T15:21:11.030Z
/* DDL */  select update_Column_Translation_From_AD_Element(208)
;

-- 2025-07-03T15:21:43.875Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournal','ALTER TABLE public.SAP_GLJournal ADD COLUMN C_Project_ID NUMERIC(10)')
;

-- 2025-07-03T15:21:44.099Z
ALTER TABLE SAP_GLJournal ADD CONSTRAINT CProject_SAPGLJournal FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
;

-- Field: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> Projekt
-- Column: SAP_GLJournal.C_Project_ID
-- 2025-07-03T15:24:02.318Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590481,748559,0,546730,0,TO_TIMESTAMP('2025-07-03 16:24:01.263','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project',0,'D','A Project allows you to track and control internal or external activities.',0,'Y','Y','Y','N','N','N','N','N','N','Projekt',0,10,0,1,1,TO_TIMESTAMP('2025-07-03 16:24:01.263','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-07-03T15:24:02.372Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=748559 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-03T15:24:02.420Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2025-07-03T15:24:02.519Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=748559
;

-- 2025-07-03T15:24:02.574Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(748559)
;

-- UI Column: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 10
-- UI Element Group: project
-- 2025-07-03T15:25:11.139Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546535,553178,TO_TIMESTAMP('2025-07-03 16:25:10.445','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','project',50,TO_TIMESTAMP('2025-07-03 16:25:10.445','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: SAP Buchführungs Journal_OLD(541656,de.metas.acct) -> SAP Buchführungs Journal(546730,de.metas.acct) -> main -> 10 -> project.Projekt
-- Column: SAP_GLJournal.C_Project_ID
-- 2025-07-03T15:25:31.311Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,748559,0,546730,553178,634567,'F',TO_TIMESTAMP('2025-07-03 16:25:30.554','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',10,0,0,TO_TIMESTAMP('2025-07-03 16:25:30.554','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: SAP_GLJournal.C_Project_ID
-- 2025-07-03T15:28:40.921Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-07-03 16:28:40.921','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=590481
;

