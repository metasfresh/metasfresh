-- 2022-05-18T11:22:35.400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580861,0,'IsAllDay',TO_TIMESTAMP('2022-05-18 14:22:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','All day','All day',TO_TIMESTAMP('2022-05-18 14:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-18T11:22:35.414Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580861 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: S_ResourceAssignment.IsAllDay
-- 2022-05-18T11:22:49.491Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582995,580861,0,20,485,'IsAllDay',TO_TIMESTAMP('2022-05-18 14:22:49','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'All day',0,0,TO_TIMESTAMP('2022-05-18 14:22:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-18T11:22:49.495Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582995 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-18T11:22:49.528Z
/* DDL */  select update_Column_Translation_From_AD_Element(580861) 
;

-- 2022-05-18T11:22:50.448Z
/* DDL */ SELECT public.db_alter_table('S_ResourceAssignment','ALTER TABLE public.S_ResourceAssignment ADD COLUMN IsAllDay CHAR(1) DEFAULT ''N'' CHECK (IsAllDay IN (''Y'',''N'')) NOT NULL')
;

-- Field: Ressource -> Zuordnung -> All day
-- Column: S_ResourceAssignment.IsAllDay
-- 2022-05-18T11:24:27.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582995,693827,0,415,TO_TIMESTAMP('2022-05-18 14:24:27','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','All day',TO_TIMESTAMP('2022-05-18 14:24:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-18T11:24:27.403Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=693827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-18T11:24:27.410Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580861) 
;

-- 2022-05-18T11:24:27.425Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=693827
;

-- 2022-05-18T11:24:27.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(693827)
;

-- UI Element: Ressource -> Zuordnung.All day
-- Column: S_ResourceAssignment.IsAllDay
-- 2022-05-18T11:24:59.645Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,693827,0,415,606411,543927,'F',TO_TIMESTAMP('2022-05-18 14:24:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','All day',90,0,0,TO_TIMESTAMP('2022-05-18 14:24:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Ressource -> Zuordnung.All day
-- Column: S_ResourceAssignment.IsAllDay
-- 2022-05-18T11:25:18.347Z
UPDATE AD_UI_Element SET SeqNo=45,Updated=TO_TIMESTAMP('2022-05-18 14:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=606411
;

-- UI Element: Ressource -> Zuordnung.bestätigt
-- Column: S_ResourceAssignment.IsConfirmed
-- 2022-05-18T11:25:37.902Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-18 14:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570119
;

-- UI Element: Ressource -> Zuordnung.Menge
-- Column: S_ResourceAssignment.Qty
-- 2022-05-18T11:25:37.908Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-18 14:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570118
;

-- UI Element: Ressource -> Zuordnung.Zuordnung von
-- Column: S_ResourceAssignment.AssignDateFrom
-- 2022-05-18T11:25:37.912Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-18 14:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570116
;

-- UI Element: Ressource -> Zuordnung.Zuordnung bis
-- Column: S_ResourceAssignment.AssignDateTo
-- 2022-05-18T11:25:37.916Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-18 14:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570117
;

-- UI Element: Ressource -> Zuordnung.All day
-- Column: S_ResourceAssignment.IsAllDay
-- 2022-05-18T11:25:37.919Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-18 14:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=606411
;

-- UI Element: Ressource -> Zuordnung.Sektion
-- Column: S_ResourceAssignment.AD_Org_ID
-- 2022-05-18T11:25:37.922Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-05-18 14:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570114
;

-- UI Element: Ressource -> Zuordnung.Menge
-- Column: S_ResourceAssignment.Qty
-- 2022-05-18T11:25:55.512Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-05-18 14:25:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570118
;

-- UI Element: Ressource -> Zuordnung.bestätigt
-- Column: S_ResourceAssignment.IsConfirmed
-- 2022-05-18T11:26:11.363Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-05-18 14:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570119
;

