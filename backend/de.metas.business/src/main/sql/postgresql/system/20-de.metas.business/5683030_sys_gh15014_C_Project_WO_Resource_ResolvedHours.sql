-- 2023-03-28T13:18:53.280Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582176,0,'ResourceTotalHours',TO_TIMESTAMP('2023-03-28 16:18:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Reserved hours','Reserved hours',TO_TIMESTAMP('2023-03-28 16:18:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T13:18:53.287Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582176 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-28T13:19:04.150Z
UPDATE AD_Element SET ColumnName='ResourcesTotalHours',Updated=TO_TIMESTAMP('2023-03-28 16:19:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582176
;

-- 2023-03-28T13:19:04.155Z
UPDATE AD_Column SET ColumnName='ResourcesTotalHours', Name='Reserved hours', Description=NULL, Help=NULL WHERE AD_Element_ID=582176
;

-- 2023-03-28T13:19:04.156Z
UPDATE AD_Process_Para SET ColumnName='ResourcesTotalHours', Name='Reserved hours', Description=NULL, Help=NULL, AD_Element_ID=582176 WHERE UPPER(ColumnName)='RESOURCESTOTALHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-28T13:19:04.160Z
UPDATE AD_Process_Para SET ColumnName='ResourcesTotalHours', Name='Reserved hours', Description=NULL, Help=NULL WHERE AD_Element_ID=582176 AND IsCentrallyMaintained='Y'
;

-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-28T13:19:33.231Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586368,582176,0,11,542159,'ResourcesTotalHours',TO_TIMESTAMP('2023-03-28 16:19:33','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reserved hours',0,0,TO_TIMESTAMP('2023-03-28 16:19:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-28T13:19:33.235Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586368 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-28T13:19:33.262Z
/* DDL */  select update_Column_Translation_From_AD_Element(582176) 
;

-- 2023-03-28T13:19:34.283Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Step','ALTER TABLE public.C_Project_WO_Step ADD COLUMN ResourcesTotalHours NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Field: Prüfauftrag -> Prüfschritt -> Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-28T13:20:10.895Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586368,712951,0,546559,0,TO_TIMESTAMP('2023-03-28 16:20:10','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Reserved hours',0,40,0,1,1,TO_TIMESTAMP('2023-03-28 16:20:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T13:20:10.899Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-28T13:20:10.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582176) 
;

-- 2023-03-28T13:20:10.912Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712951
;

-- 2023-03-28T13:20:10.918Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712951)
;

-- Field: Prüfauftrag -> Prüfschritt -> Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-28T13:20:26.755Z
UPDATE AD_Field SET DisplayLogic='@C_ProjectType_ID@ = 540011',Updated=TO_TIMESTAMP('2023-03-28 16:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712951
;

-- UI Element: Prüfauftrag -> Prüfschritt.Reserved hours
-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-28T13:20:37.857Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616068
;

-- 2023-03-28T13:20:37.859Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712947
;

-- Field: Prüfauftrag -> Prüfschritt -> Reserved hours
-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-28T13:20:37.865Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712947
;

-- 2023-03-28T13:20:37.867Z
DELETE FROM AD_Field WHERE AD_Field_ID=712947
;

-- UI Element: Prüfauftrag -> Prüfschritt.Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-28T13:20:58.435Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712951,0,546559,616072,550447,'F',TO_TIMESTAMP('2023-03-28 16:20:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Reserved hours',20,0,0,TO_TIMESTAMP('2023-03-28 16:20:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Prüfschritt.Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T05:19:41.553Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-29 08:19:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616072
;

-- Field: Prüfauftrag -> Prüfschritt -> Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T06:30:05.883Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-29 09:30:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712951
;

-- 2023-03-29T07:34:43.125Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582182,0,TO_TIMESTAMP('2023-03-29 10:34:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Resolved reservations','Resolved reservations',TO_TIMESTAMP('2023-03-29 10:34:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:34:43.134Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582182 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T07:37:55.397Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,582182,0,546855,542159,541553,'Y',TO_TIMESTAMP('2023-03-29 10:37:55','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','ResolvedSteps','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Resolved reservations','N',50,1,TO_TIMESTAMP('2023-03-29 10:37:55','YYYY-MM-DD HH24:MI:SS'),100,'C_ProjectType_ID = 540011')
;

-- 2023-03-29T07:37:55.408Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546855 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-03-29T07:37:55.419Z
/* DDL */  select update_tab_translation_from_ad_element(582182) 
;

-- 2023-03-29T07:37:55.422Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546855)
;

-- Field: Prüfauftrag -> Resolved reservations -> Mandant
-- Column: C_Project_WO_Step.AD_Client_ID
-- 2023-03-29T07:38:11.069Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583223,712954,0,546855,TO_TIMESTAMP('2023-03-29 10:38:10','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-03-29 10:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:11.072Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:11.074Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-03-29T07:38:11.505Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712954
;

-- 2023-03-29T07:38:11.509Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712954)
;

-- Field: Prüfauftrag -> Resolved reservations -> Sektion
-- Column: C_Project_WO_Step.AD_Org_ID
-- 2023-03-29T07:38:11.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583224,712955,0,546855,TO_TIMESTAMP('2023-03-29 10:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-03-29 10:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:11.639Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:11.640Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-03-29T07:38:11.779Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712955
;

-- 2023-03-29T07:38:11.780Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712955)
;

-- Field: Prüfauftrag -> Resolved reservations -> Erstellt
-- Column: C_Project_WO_Step.Created
-- 2023-03-29T07:38:11.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583225,712956,0,546855,TO_TIMESTAMP('2023-03-29 10:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2023-03-29 10:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:11.901Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:11.901Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-03-29T07:38:11.930Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712956
;

-- 2023-03-29T07:38:11.931Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712956)
;

-- Field: Prüfauftrag -> Resolved reservations -> Erstellt durch
-- Column: C_Project_WO_Step.CreatedBy
-- 2023-03-29T07:38:12.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583226,712957,0,546855,TO_TIMESTAMP('2023-03-29 10:38:11','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2023-03-29 10:38:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:12.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:12.031Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-03-29T07:38:12.055Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712957
;

-- 2023-03-29T07:38:12.056Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712957)
;

-- Field: Prüfauftrag -> Resolved reservations -> Aktiv
-- Column: C_Project_WO_Step.IsActive
-- 2023-03-29T07:38:12.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583227,712958,0,546855,TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:12.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:12.182Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-03-29T07:38:12.390Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712958
;

-- 2023-03-29T07:38:12.391Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712958)
;

-- Field: Prüfauftrag -> Resolved reservations -> Aktualisiert
-- Column: C_Project_WO_Step.Updated
-- 2023-03-29T07:38:12.499Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583228,712959,0,546855,TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:12.500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712959 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:12.501Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2023-03-29T07:38:12.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712959
;

-- 2023-03-29T07:38:12.528Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712959)
;

-- Field: Prüfauftrag -> Resolved reservations -> Aktualisiert durch
-- Column: C_Project_WO_Step.UpdatedBy
-- 2023-03-29T07:38:12.635Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583229,712960,0,546855,TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:12.636Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:12.638Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2023-03-29T07:38:12.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712960
;

-- 2023-03-29T07:38:12.656Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712960)
;

-- Field: Prüfauftrag -> Resolved reservations -> Project Step
-- Column: C_Project_WO_Step.C_Project_WO_Step_ID
-- 2023-03-29T07:38:12.769Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583230,712961,0,546855,TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Project Step',TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:12.771Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:12.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580965) 
;

-- 2023-03-29T07:38:12.776Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712961
;

-- 2023-03-29T07:38:12.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712961)
;

-- Field: Prüfauftrag -> Resolved reservations -> Projekt
-- Column: C_Project_WO_Step.C_Project_ID
-- 2023-03-29T07:38:12.889Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583231,712962,0,546855,TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:12.889Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712962 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:12.890Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2023-03-29T07:38:12.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712962
;

-- 2023-03-29T07:38:12.901Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712962)
;

-- Field: Prüfauftrag -> Resolved reservations -> Reihenfolge
-- Column: C_Project_WO_Step.SeqNo
-- 2023-03-29T07:38:12.996Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583232,712963,0,546855,TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2023-03-29 10:38:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:12.998Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:12.999Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2023-03-29T07:38:13.004Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712963
;

-- 2023-03-29T07:38:13.005Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712963)
;

-- Field: Prüfauftrag -> Resolved reservations -> Name
-- Column: C_Project_WO_Step.Name
-- 2023-03-29T07:38:13.108Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583233,712964,0,546855,TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:13.110Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:13.112Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2023-03-29T07:38:13.173Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712964
;

-- 2023-03-29T07:38:13.174Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712964)
;

-- Field: Prüfauftrag -> Resolved reservations -> Beschreibung
-- Column: C_Project_WO_Step.Description
-- 2023-03-29T07:38:13.301Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583234,712965,0,546855,TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:13.304Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:13.304Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-03-29T07:38:13.340Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712965
;

-- 2023-03-29T07:38:13.341Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712965)
;

-- Field: Prüfauftrag -> Resolved reservations -> Startdatum
-- Column: C_Project_WO_Step.DateStart
-- 2023-03-29T07:38:13.445Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583254,712966,0,546855,TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Startdatum',TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:13.447Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:13.449Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53280) 
;

-- 2023-03-29T07:38:13.452Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712966
;

-- 2023-03-29T07:38:13.453Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712966)
;

-- Field: Prüfauftrag -> Resolved reservations -> Endzeitpunkt
-- Column: C_Project_WO_Step.DateEnd
-- 2023-03-29T07:38:13.565Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583255,712967,0,546855,TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Endzeitpunkt',TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:13.566Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:13.568Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579243) 
;

-- 2023-03-29T07:38:13.569Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712967
;

-- 2023-03-29T07:38:13.570Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712967)
;

-- Field: Prüfauftrag -> Resolved reservations -> Teilbericht erstellt
-- Column: C_Project_WO_Step.WOPartialReportDate
-- 2023-03-29T07:38:13.681Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583634,712968,0,546855,TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Datum Erstellung des Teilberichtes',7,'D','Y','N','N','N','N','N','N','N','Teilbericht erstellt',TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:13.684Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:13.687Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581108) 
;

-- 2023-03-29T07:38:13.688Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712968
;

-- 2023-03-29T07:38:13.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712968)
;

-- Field: Prüfauftrag -> Resolved reservations -> SOLL-Anlagenstunden
-- Column: C_Project_WO_Step.WOPlannedResourceDurationHours
-- 2023-03-29T07:38:13.802Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583635,712969,0,546855,TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','SOLL-Anlagenstunden',TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:13.803Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712969 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:13.806Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581109) 
;

-- 2023-03-29T07:38:13.807Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712969
;

-- 2023-03-29T07:38:13.808Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712969)
;

-- Field: Prüfauftrag -> Resolved reservations -> Anlieferdatum
-- Column: C_Project_WO_Step.WODeliveryDate
-- 2023-03-29T07:38:13.913Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583636,712970,0,546855,TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Anlieferdatum an Prüfanlage',7,'D','Y','N','N','N','N','N','N','N','Anlieferdatum',TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:13.915Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:13.918Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581110) 
;

-- 2023-03-29T07:38:13.919Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712970
;

-- 2023-03-29T07:38:13.920Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712970)
;

-- Field: Prüfauftrag -> Resolved reservations -> SOLL-PersonenStd
-- Column: C_Project_WO_Step.WOPlannedPersonDurationHours
-- 2023-03-29T07:38:14.019Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583637,712971,0,546855,TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','SOLL-PersonenStd',TO_TIMESTAMP('2023-03-29 10:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.020Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712971 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581111) 
;

-- 2023-03-29T07:38:14.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712971
;

-- 2023-03-29T07:38:14.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712971)
;

-- Field: Prüfauftrag -> Resolved reservations -> SOLL Prüfschrittende
-- Column: C_Project_WO_Step.WOTargetEndDate
-- 2023-03-29T07:38:14.132Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583638,712972,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','SOLL Prüfschrittende',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.134Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712972 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.137Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581112) 
;

-- 2023-03-29T07:38:14.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712972
;

-- 2023-03-29T07:38:14.140Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712972)
;

-- Field: Prüfauftrag -> Resolved reservations -> SOLL Prüfschrittstart
-- Column: C_Project_WO_Step.WOTargetStartDate
-- 2023-03-29T07:38:14.244Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583639,712973,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','SOLL Prüfschrittstart',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.246Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.247Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581113) 
;

-- 2023-03-29T07:38:14.248Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712973
;

-- 2023-03-29T07:38:14.249Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712973)
;

-- Field: Prüfauftrag -> Resolved reservations -> Status
-- Column: C_Project_WO_Step.WOStepStatus
-- 2023-03-29T07:38:14.375Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583640,712974,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,3,'D','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.376Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.379Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581114) 
;

-- 2023-03-29T07:38:14.380Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712974
;

-- 2023-03-29T07:38:14.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712974)
;

-- Field: Prüfauftrag -> Resolved reservations -> Befund Ausgang
-- Column: C_Project_WO_Step.WOFindingsReleasedDate
-- 2023-03-29T07:38:14.509Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583641,712975,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum an dem der Bericht freigegeben wurde',7,'D','Y','N','N','N','N','N','N','N','Befund Ausgang',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.514Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581115) 
;

-- 2023-03-29T07:38:14.515Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712975
;

-- 2023-03-29T07:38:14.516Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712975)
;

-- Field: Prüfauftrag -> Resolved reservations -> Befund Erstellt
-- Column: C_Project_WO_Step.WOFindingsCreatedDate
-- 2023-03-29T07:38:14.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583642,712976,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum an dem der Bericht erstellt wurde.',7,'D','Y','N','N','N','N','N','N','N','Befund Erstellt',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.626Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581116) 
;

-- 2023-03-29T07:38:14.628Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712976
;

-- 2023-03-29T07:38:14.629Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712976)
;

-- Field: Prüfauftrag -> Resolved reservations -> Externe ID
-- Column: C_Project_WO_Step.ExternalId
-- 2023-03-29T07:38:14.731Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583677,712977,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.732Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2023-03-29T07:38:14.739Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712977
;

-- 2023-03-29T07:38:14.740Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712977)
;

-- Field: Prüfauftrag -> Resolved reservations -> IST Mannstunden
-- Column: C_Project_WO_Step.WOActualManHours
-- 2023-03-29T07:38:14.835Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584045,712978,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','IST Mannstunden',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.836Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.838Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581293) 
;

-- 2023-03-29T07:38:14.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712978
;

-- 2023-03-29T07:38:14.840Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712978)
;

-- Field: Prüfauftrag -> Resolved reservations -> IST Anlagenstunden
-- Column: C_Project_WO_Step.WOActualFacilityHours
-- 2023-03-29T07:38:14.938Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584046,712979,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','IST Anlagenstunden',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:14.939Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:14.941Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581292) 
;

-- 2023-03-29T07:38:14.943Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712979
;

-- 2023-03-29T07:38:14.944Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712979)
;

-- Field: Prüfauftrag -> Resolved reservations -> Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-29T07:38:15.055Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586362,712980,0,546855,TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Resolved hours',TO_TIMESTAMP('2023-03-29 10:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:15.057Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:15.059Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582168) 
;

-- 2023-03-29T07:38:15.061Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712980
;

-- 2023-03-29T07:38:15.062Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712980)
;

-- Field: Prüfauftrag -> Resolved reservations -> Fully Resolved
-- Column: C_Project_WO_Step.IsFullyResolved
-- 2023-03-29T07:38:15.170Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586363,712981,0,546855,TO_TIMESTAMP('2023-03-29 10:38:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Fully Resolved',TO_TIMESTAMP('2023-03-29 10:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:15.172Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:15.174Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582169) 
;

-- 2023-03-29T07:38:15.176Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712981
;

-- 2023-03-29T07:38:15.177Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712981)
;

-- Field: Prüfauftrag -> Resolved reservations -> Reserved hours
-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-29T07:38:15.322Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586364,712982,0,546855,TO_TIMESTAMP('2023-03-29 10:38:15','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Reserved hours',TO_TIMESTAMP('2023-03-29 10:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:15.324Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712982 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:15.326Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582170) 
;

-- 2023-03-29T07:38:15.327Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712982
;

-- 2023-03-29T07:38:15.328Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712982)
;

-- Field: Prüfauftrag -> Resolved reservations -> Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T07:38:15.443Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586368,712983,0,546855,TO_TIMESTAMP('2023-03-29 10:38:15','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Reserved hours',TO_TIMESTAMP('2023-03-29 10:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:38:15.445Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712983 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T07:38:15.447Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582176) 
;

-- 2023-03-29T07:38:15.448Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712983
;

-- 2023-03-29T07:38:15.450Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712983)
;

-- 2023-03-29T07:39:35.188Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546855,545470,TO_TIMESTAMP('2023-03-29 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-29 10:39:35','YYYY-MM-DD HH24:MI:SS'),100,'default')
;

-- 2023-03-29T07:39:35.192Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545470 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-03-29T07:39:38.834Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546684,545470,TO_TIMESTAMP('2023-03-29 10:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-29 10:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:39:40.126Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546685,545470,TO_TIMESTAMP('2023-03-29 10:39:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-03-29 10:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:39:52.759Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546684,550449,TO_TIMESTAMP('2023-03-29 10:39:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-03-29 10:39:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Reihenfolge
-- Column: C_Project_WO_Step.SeqNo
-- 2023-03-29T07:40:22.091Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712963,0,546855,616075,550449,'F',TO_TIMESTAMP('2023-03-29 10:40:21','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',10,0,0,TO_TIMESTAMP('2023-03-29 10:40:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Name
-- Column: C_Project_WO_Step.Name
-- 2023-03-29T07:40:36.601Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712964,0,546855,616076,550449,'F',TO_TIMESTAMP('2023-03-29 10:40:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2023-03-29 10:40:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Beschreibung
-- Column: C_Project_WO_Step.Description
-- 2023-03-29T07:40:48.201Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712965,0,546855,616077,550449,'F',TO_TIMESTAMP('2023-03-29 10:40:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',30,0,0,TO_TIMESTAMP('2023-03-29 10:40:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:41:18.229Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546684,550451,TO_TIMESTAMP('2023-03-29 10:41:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','planning',20,TO_TIMESTAMP('2023-03-29 10:41:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Status
-- Column: C_Project_WO_Step.WOStepStatus
-- 2023-03-29T07:41:32.519Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712974,0,546855,616080,550451,'F',TO_TIMESTAMP('2023-03-29 10:41:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Status',10,0,0,TO_TIMESTAMP('2023-03-29 10:41:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.SOLL-PersonenStd
-- Column: C_Project_WO_Step.WOPlannedPersonDurationHours
-- 2023-03-29T07:43:09.354Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712971,0,546855,616081,550451,'F',TO_TIMESTAMP('2023-03-29 10:43:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SOLL-PersonenStd',20,0,0,TO_TIMESTAMP('2023-03-29 10:43:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.SOLL-Anlagenstunden
-- Column: C_Project_WO_Step.WOPlannedResourceDurationHours
-- 2023-03-29T07:43:18.529Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712969,0,546855,616082,550451,'F',TO_TIMESTAMP('2023-03-29 10:43:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SOLL-Anlagenstunden',30,0,0,TO_TIMESTAMP('2023-03-29 10:43:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.IST Mannstunden
-- Column: C_Project_WO_Step.WOActualManHours
-- 2023-03-29T07:43:27.191Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712978,0,546855,616083,550451,'F',TO_TIMESTAMP('2023-03-29 10:43:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IST Mannstunden',40,0,0,TO_TIMESTAMP('2023-03-29 10:43:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.IST Anlagenstunden
-- Column: C_Project_WO_Step.WOActualFacilityHours
-- 2023-03-29T07:43:37.167Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712979,0,546855,616084,550451,'F',TO_TIMESTAMP('2023-03-29 10:43:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IST Anlagenstunden',50,0,0,TO_TIMESTAMP('2023-03-29 10:43:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:43:48.717Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546684,550452,TO_TIMESTAMP('2023-03-29 10:43:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','reservation',30,TO_TIMESTAMP('2023-03-29 10:43:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T07:44:13.603Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712983,0,546855,616085,550452,'F',TO_TIMESTAMP('2023-03-29 10:44:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Reserved hours',10,0,0,TO_TIMESTAMP('2023-03-29 10:44:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-29T07:44:25.843Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712980,0,546855,616086,550452,'F',TO_TIMESTAMP('2023-03-29 10:44:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Resolved hours',20,0,0,TO_TIMESTAMP('2023-03-29 10:44:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:44:35.782Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546684,550453,TO_TIMESTAMP('2023-03-29 10:44:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','external',40,TO_TIMESTAMP('2023-03-29 10:44:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Externe ID
-- Column: C_Project_WO_Step.ExternalId
-- 2023-03-29T07:44:53.732Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712977,0,546855,616087,550453,'F',TO_TIMESTAMP('2023-03-29 10:44:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe ID',10,0,0,TO_TIMESTAMP('2023-03-29 10:44:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:45:22.746Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546685,550454,TO_TIMESTAMP('2023-03-29 10:45:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',10,TO_TIMESTAMP('2023-03-29 10:45:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Startdatum
-- Column: C_Project_WO_Step.DateStart
-- 2023-03-29T07:45:58.287Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712966,0,546855,616088,550454,'F',TO_TIMESTAMP('2023-03-29 10:45:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Startdatum',10,0,0,TO_TIMESTAMP('2023-03-29 10:45:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Endzeitpunkt
-- Column: C_Project_WO_Step.DateEnd
-- 2023-03-29T07:46:14.704Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712967,0,546855,616089,550454,'F',TO_TIMESTAMP('2023-03-29 10:46:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Endzeitpunkt',20,0,0,TO_TIMESTAMP('2023-03-29 10:46:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:46:27.048Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546685,550455,TO_TIMESTAMP('2023-03-29 10:46:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','WO Dates',20,TO_TIMESTAMP('2023-03-29 10:46:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Befund Erstellt
-- Column: C_Project_WO_Step.WOFindingsCreatedDate
-- 2023-03-29T07:46:39.008Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712976,0,546855,616090,550455,'F',TO_TIMESTAMP('2023-03-29 10:46:38','YYYY-MM-DD HH24:MI:SS'),100,'Datum an dem der Bericht erstellt wurde.','Y','N','N','Y','N','N','N',0,'Befund Erstellt',10,0,0,TO_TIMESTAMP('2023-03-29 10:46:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Befund Ausgang
-- Column: C_Project_WO_Step.WOFindingsReleasedDate
-- 2023-03-29T07:46:51.109Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712975,0,546855,616091,550455,'F',TO_TIMESTAMP('2023-03-29 10:46:50','YYYY-MM-DD HH24:MI:SS'),100,'Datum an dem der Bericht freigegeben wurde','Y','N','N','Y','N','N','N',0,'Befund Ausgang',20,0,0,TO_TIMESTAMP('2023-03-29 10:46:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Teilbericht erstellt
-- Column: C_Project_WO_Step.WOPartialReportDate
-- 2023-03-29T07:47:18.190Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712968,0,546855,616092,550455,'F',TO_TIMESTAMP('2023-03-29 10:47:18','YYYY-MM-DD HH24:MI:SS'),100,'Datum Erstellung des Teilberichtes','Y','N','N','Y','N','N','N',0,'Teilbericht erstellt',30,0,0,TO_TIMESTAMP('2023-03-29 10:47:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Anlieferdatum
-- Column: C_Project_WO_Step.WODeliveryDate
-- 2023-03-29T07:47:30.874Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712970,0,546855,616093,550455,'F',TO_TIMESTAMP('2023-03-29 10:47:30','YYYY-MM-DD HH24:MI:SS'),100,'Anlieferdatum an Prüfanlage','Y','N','N','Y','N','N','N',0,'Anlieferdatum',40,0,0,TO_TIMESTAMP('2023-03-29 10:47:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.SOLL Prüfschrittstart
-- Column: C_Project_WO_Step.WOTargetStartDate
-- 2023-03-29T07:47:44.972Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712973,0,546855,616094,550455,'F',TO_TIMESTAMP('2023-03-29 10:47:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SOLL Prüfschrittstart',50,0,0,TO_TIMESTAMP('2023-03-29 10:47:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.SOLL Prüfschrittende
-- Column: C_Project_WO_Step.WOTargetEndDate
-- 2023-03-29T07:47:53.833Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712972,0,546855,616095,550455,'F',TO_TIMESTAMP('2023-03-29 10:47:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SOLL Prüfschrittende',60,0,0,TO_TIMESTAMP('2023-03-29 10:47:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T07:48:05.833Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546685,550456,TO_TIMESTAMP('2023-03-29 10:48:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2023-03-29 10:48:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Sektion
-- Column: C_Project_WO_Step.AD_Org_ID
-- 2023-03-29T07:48:18.845Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712955,0,546855,616096,550456,'F',TO_TIMESTAMP('2023-03-29 10:48:18','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2023-03-29 10:48:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Mandant
-- Column: C_Project_WO_Step.AD_Client_ID
-- 2023-03-29T07:48:26.444Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712954,0,546855,616097,550456,'F',TO_TIMESTAMP('2023-03-29 10:48:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2023-03-29 10:48:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Resolved reservations.Reihenfolge
-- Column: C_Project_WO_Step.SeqNo
-- 2023-03-29T07:49:03.810Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-29 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616075
;

-- UI Element: Prüfauftrag -> Resolved reservations.Name
-- Column: C_Project_WO_Step.Name
-- 2023-03-29T07:49:03.814Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-29 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616076
;

-- UI Element: Prüfauftrag -> Resolved reservations.Startdatum
-- Column: C_Project_WO_Step.DateStart
-- 2023-03-29T07:49:03.817Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-29 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616088
;

-- UI Element: Prüfauftrag -> Resolved reservations.Endzeitpunkt
-- Column: C_Project_WO_Step.DateEnd
-- 2023-03-29T07:49:03.820Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-29 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616089
;

-- UI Element: Prüfauftrag -> Resolved reservations.Status
-- Column: C_Project_WO_Step.WOStepStatus
-- 2023-03-29T07:49:03.823Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-29 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616080
;

-- UI Element: Prüfauftrag -> Resolved reservations.Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T07:49:03.826Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-29 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616085
;

-- UI Element: Prüfauftrag -> Resolved reservations.Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-29T07:49:03.828Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-29 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616086
;

-- UI Element: Prüfauftrag -> Resolved reservations.Teilbericht erstellt
-- Column: C_Project_WO_Step.WOPartialReportDate
-- 2023-03-29T07:49:03.830Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-29 10:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616092
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T07:50:09.379Z
UPDATE AD_Tab SET WhereClause='C_Project.C_ProjectType_ID = 540011',Updated=TO_TIMESTAMP('2023-03-29 10:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T07:51:52.854Z
UPDATE AD_Tab SET WhereClause='@C_ProjectType_ID@= 540011',Updated=TO_TIMESTAMP('2023-03-29 10:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T07:54:32.914Z
UPDATE AD_Tab SET WhereClause='(select c_projecttype_id from c_project c where c.c_project_id = C_Project_ID) = 540011',Updated=TO_TIMESTAMP('2023-03-29 10:54:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T07:56:22.250Z
UPDATE AD_Tab SET WhereClause='(select c_projecttype_id from c_project c where c.c_project_id = C_Project_WO_Step.C_Project_ID) = 540011',Updated=TO_TIMESTAMP('2023-03-29 10:56:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T08:04:26.541Z
UPDATE AD_Tab SET DisplayLogic='@C_ProjectType_ID@ = 540011', WhereClause='',Updated=TO_TIMESTAMP('2023-03-29 11:04:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- 2023-03-29T08:58:36.497Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712982
;

-- Field: Prüfauftrag -> Resolved reservations -> Reserved hours
-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-29T08:58:36.502Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712982
;

-- 2023-03-29T08:58:36.505Z
DELETE FROM AD_Field WHERE AD_Field_ID=712982
;

-- 2023-03-29T08:59:13.219Z
DELETE FROM AD_SQLColumn_SourceTableColumn WHERE AD_SQLColumn_SourceTableColumn_ID=540144
;

-- Column: C_Project_WO_Step.ReservedHours
-- 2023-03-29T08:59:17.647Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586364
;

-- 2023-03-29T08:59:17.648Z
DELETE FROM AD_Column WHERE AD_Column_ID=586364
;

-- 2023-03-29T09:01:25.263Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712981
;

-- Field: Prüfauftrag -> Resolved reservations -> Fully Resolved
-- Column: C_Project_WO_Step.IsFullyResolved
-- 2023-03-29T09:01:25.268Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712981
;

-- 2023-03-29T09:01:25.270Z
DELETE FROM AD_Field WHERE AD_Field_ID=712981
;

-- Column: C_Project_WO_Step.IsFullyResolved
-- 2023-03-29T09:01:41.724Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586363
;

-- 2023-03-29T09:01:41.725Z
DELETE FROM AD_Column WHERE AD_Column_ID=586363
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T09:09:17.362Z
UPDATE AD_Tab SET DisplayLogic='@C_ProjectType_ID@=540011',Updated=TO_TIMESTAMP('2023-03-29 12:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Column: C_Project_WO_Resource.ResolvedHours
-- 2023-03-29T09:11:54.981Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586374,582168,0,11,542161,'ResolvedHours',TO_TIMESTAMP('2023-03-29 12:11:54','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Resolved hours',0,0,TO_TIMESTAMP('2023-03-29 12:11:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-29T09:11:54.985Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586374 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-29T09:11:54.990Z
/* DDL */  select update_Column_Translation_From_AD_Element(582168) 
;

-- 2023-03-29T09:11:55.598Z
/* DDL */ SELECT public.db_alter_table('C_Project_WO_Resource','ALTER TABLE public.C_Project_WO_Resource ADD COLUMN ResolvedHours NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- UI Element: Prüfauftrag -> Resolved reservations.Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T10:49:28.078Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616085
;

-- 2023-03-29T10:49:28.084Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712983
;

-- Field: Prüfauftrag -> Resolved reservations -> Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T10:49:28.091Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712983
;

-- 2023-03-29T10:49:28.095Z
DELETE FROM AD_Field WHERE AD_Field_ID=712983
;

-- UI Element: Prüfauftrag -> Resolved reservations.Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-29T10:49:31.354Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616086
;

-- 2023-03-29T10:49:31.356Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712980
;

-- Field: Prüfauftrag -> Resolved reservations -> Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-29T10:49:31.363Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712980
;

-- 2023-03-29T10:49:31.367Z
DELETE FROM AD_Field WHERE AD_Field_ID=712980
;

-- UI Element: Prüfauftrag -> Prüfschritt.Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-29T10:49:49.651Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616066
;

-- 2023-03-29T10:49:49.653Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712945
;

-- Field: Prüfauftrag -> Prüfschritt -> Resolved hours
-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-29T10:49:49.654Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712945
;

-- 2023-03-29T10:49:49.656Z
DELETE FROM AD_Field WHERE AD_Field_ID=712945
;

-- UI Element: Prüfauftrag -> Prüfschritt.Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T10:49:52.629Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=616072
;

-- 2023-03-29T10:49:52.630Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712951
;

-- Field: Prüfauftrag -> Prüfschritt -> Reserved hours
-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T10:49:52.632Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=712951
;

-- 2023-03-29T10:49:52.632Z
DELETE FROM AD_Field WHERE AD_Field_ID=712951
;

-- Column: C_Project_WO_Step.ResourcesTotalHours
-- 2023-03-29T10:50:04.138Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586368
;

-- 2023-03-29T10:50:04.140Z
DELETE FROM AD_Column WHERE AD_Column_ID=586368
;

-- Column: C_Project_WO_Step.ResolvedHours
-- 2023-03-29T10:50:08.195Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586362
;

-- 2023-03-29T10:50:08.196Z
DELETE FROM AD_Column WHERE AD_Column_ID=586362
;

-- Field: Prüfauftrag -> Ressource -> Resolved hours
-- Column: C_Project_WO_Resource.ResolvedHours
-- 2023-03-29T10:52:07.243Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586374,712987,0,546560,0,TO_TIMESTAMP('2023-03-29 13:52:07','YYYY-MM-DD HH24:MI:SS'),100,0,'@C_ProjectType_ID@ = 540011','D',0,'Y','Y','Y','N','N','N','N','N','Resolved hours',0,20,0,1,1,TO_TIMESTAMP('2023-03-29 13:52:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T10:52:07.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T10:52:07.252Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582168) 
;

-- 2023-03-29T10:52:07.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712987
;

-- 2023-03-29T10:52:07.259Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712987)
;

-- UI Element: Prüfauftrag -> Ressource.Resolved hours
-- Column: C_Project_WO_Resource.ResolvedHours
-- 2023-03-29T10:52:57.254Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712987,0,546560,616098,549768,'F',TO_TIMESTAMP('2023-03-29 13:52:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Resolved hours',70,0,0,TO_TIMESTAMP('2023-03-29 13:52:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Prüfauftrag -> Ressource.Resolved hours
-- Column: C_Project_WO_Resource.ResolvedHours
-- 2023-03-29T10:53:13.430Z
UPDATE AD_UI_Element SET SeqNo=55,Updated=TO_TIMESTAMP('2023-03-29 13:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616098
;

-- Field: Prüfauftrag -> Ressource -> Resolved hours
-- Column: C_Project_WO_Resource.ResolvedHours
-- 2023-03-29T10:53:53.701Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-29 13:53:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712987
;

-- UI Element: Prüfauftrag -> Ressource.Resolved hours
-- Column: C_Project_WO_Resource.ResolvedHours
-- 2023-03-29T10:54:29.110Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-29 13:54:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616098
;

-- UI Element: Prüfauftrag -> Ressource.SOLL-MannStd
-- Column: C_Project_WO_Resource.WOPlannedPersonDurationHours
-- 2023-03-29T10:54:29.115Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-29 13:54:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612841
;

-- UI Element: Prüfauftrag -> Ressource.Zuordnung von
-- Column: C_Project_WO_Resource.AssignDateFrom
-- 2023-03-29T10:54:29.121Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-29 13:54:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611756
;

-- UI Element: Prüfauftrag -> Ressource.Zuordnung bis
-- Column: C_Project_WO_Resource.AssignDateTo
-- 2023-03-29T10:54:29.127Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-29 13:54:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=611757
;

-- Column: C_Project.ReservationOpen
-- 2023-03-29T11:11:14.113Z
UPDATE AD_Column SET ColumnSQL='CASE             WHEN     C_Project.C_ProjectType_ID = 540007     THEN      select (select sum(c_project_wo_resource.duration- c_project_wo_resource.resolvedHours)            from c_project c      join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id      where c.projectcategory = ''W''      and c.c_projecttype_id = 540011      and C_Project.C_Project_Parent_ID = c.c_project_parent_id))       WHEN   C_Project.C_ProjectType_ID = 540011  THEN     (select (select sum(c_project_wo_resource.duration- c_project_wo_resource.resolvedHours)            from c_project c      join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id      where c.projectcategory = ''W''      and c.c_projecttype_id = 540011      and C_Project.C_Project_Parent_ID = c.c_project_parent_id      and C_Project.C_Project_ID = c.c_project_id))   END',Updated=TO_TIMESTAMP('2023-03-29 14:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- Column: C_Project.ReservationOpen
-- 2023-03-29T11:12:17.502Z
UPDATE AD_Column SET ColumnSQL='CASE             WHEN     C_Project.C_ProjectType_ID = 540007     THEN      (select (select sum(c_project_wo_resource.duration- c_project_wo_resource.resolvedHours)            from c_project c      join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id      where c.projectcategory = ''W''      and c.c_projecttype_id = 540011      and C_Project.C_Project_Parent_ID = c.c_project_parent_id))       WHEN   C_Project.C_ProjectType_ID = 540011  THEN     (select (select sum(c_project_wo_resource.duration- c_project_wo_resource.resolvedHours)            from c_project c      join c_project_wo_resource on c.c_project_id = c_project_wo_resource.c_project_id      where c.projectcategory = ''W''      and c.c_projecttype_id = 540011      and C_Project.C_Project_Parent_ID = c.c_project_parent_id      and C_Project.C_Project_ID = c.c_project_id))   END',Updated=TO_TIMESTAMP('2023-03-29 14:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586366
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:27:12.076Z
UPDATE AD_Tab SET EntityType='de.metas.endcustomer.ip180',Updated=TO_TIMESTAMP('2023-03-29 14:27:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:28:04.253Z
UPDATE AD_Tab SET DisplayLogic='',Updated=TO_TIMESTAMP('2023-03-29 14:28:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:29:24.513Z
UPDATE AD_Tab SET DisplayLogic='@C_ProjectType_ID@=540011',Updated=TO_TIMESTAMP('2023-03-29 14:29:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:29:45.398Z
UPDATE AD_Tab SET DisplayLogic='@C_ProjectType_ID@ = 540011',Updated=TO_TIMESTAMP('2023-03-29 14:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:30:14.280Z
UPDATE AD_Tab SET DisplayLogic='C_ProjectType_ID = 540011',Updated=TO_TIMESTAMP('2023-03-29 14:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:35:02.083Z
UPDATE AD_Tab SET DisplayLogic='@C_Project.C_ProjectType_ID@ = 540011',Updated=TO_TIMESTAMP('2023-03-29 14:35:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:35:35.063Z
UPDATE AD_Tab SET DisplayLogic='C_Project.C_ProjectType_ID = 540011',Updated=TO_TIMESTAMP('2023-03-29 14:35:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:50:40.451Z
UPDATE AD_Tab SET DisplayLogic='',Updated=TO_TIMESTAMP('2023-03-29 14:50:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Resolved reservations
-- Table: C_Project_WO_Step
-- 2023-03-29T11:55:21.546Z
UPDATE AD_Tab SET WhereClause='C_Project_WO_Step.C_Project_WO_Step_ID IN (select C_Project_WO_Step_ID  											from C_Project_WO_Resource r where r.duration - coalesce(r.resolvedHours, 0) = 0)',Updated=TO_TIMESTAMP('2023-03-29 14:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- 2023-03-29T12:43:00.356Z
UPDATE AD_Element_Trl SET Name='Reservierung offen', PrintName='Reservierung offen',Updated=TO_TIMESTAMP('2023-03-29 15:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582172 AND AD_Language='de_CH'
;

-- 2023-03-29T12:43:00.366Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582172,'de_CH') 
;

-- 2023-03-29T12:43:09.594Z
UPDATE AD_Element_Trl SET Name='Reservierung offen', PrintName='Reservierung offen',Updated=TO_TIMESTAMP('2023-03-29 15:43:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582172 AND AD_Language='de_DE'
;

-- 2023-03-29T12:43:09.596Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582172,'de_DE') 
;

-- 2023-03-29T12:43:09.622Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582172,'de_DE') 
;

-- 2023-03-29T12:43:09.624Z
UPDATE AD_Column SET ColumnName='ReservationOpen', Name='Reservierung offen', Description=NULL, Help=NULL WHERE AD_Element_ID=582172
;

-- 2023-03-29T12:43:09.625Z
UPDATE AD_Process_Para SET ColumnName='ReservationOpen', Name='Reservierung offen', Description=NULL, Help=NULL, AD_Element_ID=582172 WHERE UPPER(ColumnName)='RESERVATIONOPEN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-29T12:43:09.626Z
UPDATE AD_Process_Para SET ColumnName='ReservationOpen', Name='Reservierung offen', Description=NULL, Help=NULL WHERE AD_Element_ID=582172 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T12:43:09.627Z
UPDATE AD_Field SET Name='Reservierung offen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582172) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582172)
;

-- 2023-03-29T12:43:09.638Z
UPDATE AD_PrintFormatItem pi SET PrintName='Reservierung offen', Name='Reservierung offen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582172)
;

-- 2023-03-29T12:43:09.640Z
UPDATE AD_Tab SET Name='Reservierung offen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582172
;

-- 2023-03-29T12:43:09.641Z
UPDATE AD_WINDOW SET Name='Reservierung offen', Description=NULL, Help=NULL WHERE AD_Element_ID = 582172
;

-- 2023-03-29T12:43:09.642Z
UPDATE AD_Menu SET   Name = 'Reservierung offen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582172
;

-- 2023-03-29T12:43:10.843Z
UPDATE AD_Element_Trl SET Description='Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.',Updated=TO_TIMESTAMP('2023-03-29 15:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582172 AND AD_Language='de_CH'
;

-- 2023-03-29T12:43:10.845Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582172,'de_CH') 
;

-- 2023-03-29T12:43:14.968Z
UPDATE AD_Element_Trl SET Description='Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.',Updated=TO_TIMESTAMP('2023-03-29 15:43:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582172 AND AD_Language='de_DE'
;

-- 2023-03-29T12:43:14.970Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582172,'de_DE') 
;

-- 2023-03-29T12:43:14.979Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582172,'de_DE') 
;

-- 2023-03-29T12:43:14.979Z
UPDATE AD_Column SET ColumnName='ReservationOpen', Name='Reservierung offen', Description='Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.', Help=NULL WHERE AD_Element_ID=582172
;

-- 2023-03-29T12:43:14.981Z
UPDATE AD_Process_Para SET ColumnName='ReservationOpen', Name='Reservierung offen', Description='Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.', Help=NULL, AD_Element_ID=582172 WHERE UPPER(ColumnName)='RESERVATIONOPEN' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-29T12:43:14.982Z
UPDATE AD_Process_Para SET ColumnName='ReservationOpen', Name='Reservierung offen', Description='Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.', Help=NULL WHERE AD_Element_ID=582172 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T12:43:14.982Z
UPDATE AD_Field SET Name='Reservierung offen', Description='Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582172) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582172)
;

-- 2023-03-29T12:43:14.996Z
UPDATE AD_Tab SET Name='Reservierung offen', Description='Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582172
;

-- 2023-03-29T12:43:14.997Z
UPDATE AD_WINDOW SET Name='Reservierung offen', Description='Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.', Help=NULL WHERE AD_Element_ID = 582172
;

-- 2023-03-29T12:43:14.997Z
UPDATE AD_Menu SET   Name = 'Reservierung offen', Description = 'Handelt es sich bei dem Projekt um einen "Prüfauftrag", wird die Gesamtzahl der nicht aufgelösten Ressourcenstunden für alle Reservierungsaufträge angezeigt, die das gleiche Elternprojekt wie der aktuelle Testauftrag haben. Wenn es sich bei dem Projekt um einen "Reservierungsauftrag" handelt, werden die nicht aufgelösten Stunden seiner Ressourcen angezeigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582172
;

-- 2023-03-29T12:43:29.484Z
UPDATE AD_Element_Trl SET Description='If the project is a "TestOrder", it shows the total number of unresolved resource hours for all "Reservation Orders" that have the same parent project as the current TestOrder. If the project is a Reservation Order, it shows the unresolved hours of its resources.',Updated=TO_TIMESTAMP('2023-03-29 15:43:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582172 AND AD_Language='en_US'
;

-- 2023-03-29T12:43:29.485Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582172,'en_US') 
;

-- 2023-03-29T12:47:06.600Z
UPDATE AD_Element_Trl SET Name='Reservierungsauftrag vorhanden', PrintName='Reservierungsauftrag vorhanden',Updated=TO_TIMESTAMP('2023-03-29 15:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582171 AND AD_Language='de_CH'
;

-- 2023-03-29T12:47:06.601Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582171,'de_CH') 
;

-- 2023-03-29T12:47:16.719Z
UPDATE AD_Element_Trl SET Description='Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.', Name='Reservierungsauftrag vorhanden', PrintName='Reservierungsauftrag vorhanden',Updated=TO_TIMESTAMP('2023-03-29 15:47:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582171 AND AD_Language='de_DE'
;

-- 2023-03-29T12:47:16.720Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582171,'de_DE') 
;

-- 2023-03-29T12:47:16.725Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582171,'de_DE') 
;

-- 2023-03-29T12:47:16.748Z
UPDATE AD_Column SET ColumnName='IsReservationOrderAvailable', Name='Reservierungsauftrag vorhanden', Description='Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.', Help=NULL WHERE AD_Element_ID=582171
;

-- 2023-03-29T12:47:16.749Z
UPDATE AD_Process_Para SET ColumnName='IsReservationOrderAvailable', Name='Reservierungsauftrag vorhanden', Description='Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.', Help=NULL, AD_Element_ID=582171 WHERE UPPER(ColumnName)='ISRESERVATIONORDERAVAILABLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-29T12:47:16.750Z
UPDATE AD_Process_Para SET ColumnName='IsReservationOrderAvailable', Name='Reservierungsauftrag vorhanden', Description='Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.', Help=NULL WHERE AD_Element_ID=582171 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T12:47:16.751Z
UPDATE AD_Field SET Name='Reservierungsauftrag vorhanden', Description='Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582171) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582171)
;

-- 2023-03-29T12:47:16.763Z
UPDATE AD_PrintFormatItem pi SET PrintName='Reservierungsauftrag vorhanden', Name='Reservierungsauftrag vorhanden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582171)
;

-- 2023-03-29T12:47:16.765Z
UPDATE AD_Tab SET Name='Reservierungsauftrag vorhanden', Description='Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582171
;

-- 2023-03-29T12:47:16.766Z
UPDATE AD_WINDOW SET Name='Reservierungsauftrag vorhanden', Description='Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.', Help=NULL WHERE AD_Element_ID = 582171
;

-- 2023-03-29T12:47:16.767Z
UPDATE AD_Menu SET   Name = 'Reservierungsauftrag vorhanden', Description = 'Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582171
;

-- 2023-03-29T12:47:21.992Z
UPDATE AD_Element_Trl SET Description='Zeigt an, ob es mindestens einen Reservierungsauftrag gibt, der dasselbe Elternprojekt wie der aktuelle Testauftrag hat.',Updated=TO_TIMESTAMP('2023-03-29 15:47:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582171 AND AD_Language='de_CH'
;

-- 2023-03-29T12:47:21.993Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582171,'de_CH') 
;

-- 2023-03-29T12:47:36.427Z
UPDATE AD_Element_Trl SET Description='Indicates if there is at least one "ReservationOrder" that has the same parent project as the current TestOrder.',Updated=TO_TIMESTAMP('2023-03-29 15:47:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582171 AND AD_Language='en_US'
;

-- 2023-03-29T12:47:36.430Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582171,'en_US') 
;

-- 2023-03-29T12:48:27.604Z
UPDATE AD_Element_Trl SET Name='Aufgelöste Schritte', PrintName='Aufgelöste Schritte',Updated=TO_TIMESTAMP('2023-03-29 15:48:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582182 AND AD_Language='de_CH'
;

-- 2023-03-29T12:48:27.606Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582182,'de_CH') 
;

-- 2023-03-29T12:49:11.153Z
UPDATE AD_Element_Trl SET Name='Aufgelöste Schritte', PrintName='Aufgelöste Schritte',Updated=TO_TIMESTAMP('2023-03-29 15:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582182 AND AD_Language='de_DE'
;

-- 2023-03-29T12:49:11.155Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582182,'de_DE') 
;

-- 2023-03-29T12:49:11.164Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582182,'de_DE') 
;

-- 2023-03-29T12:49:11.165Z
UPDATE AD_Column SET ColumnName=NULL, Name='Aufgelöste Schritte', Description=NULL, Help=NULL WHERE AD_Element_ID=582182
;

-- 2023-03-29T12:49:11.166Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Aufgelöste Schritte', Description=NULL, Help=NULL WHERE AD_Element_ID=582182 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T12:49:11.166Z
UPDATE AD_Field SET Name='Aufgelöste Schritte', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582182) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582182)
;

-- 2023-03-29T12:49:11.177Z
UPDATE AD_PrintFormatItem pi SET PrintName='Aufgelöste Schritte', Name='Aufgelöste Schritte' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582182)
;

-- 2023-03-29T12:49:11.178Z
UPDATE AD_Tab SET Name='Aufgelöste Schritte', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582182
;

-- 2023-03-29T12:49:11.179Z
UPDATE AD_WINDOW SET Name='Aufgelöste Schritte', Description=NULL, Help=NULL WHERE AD_Element_ID = 582182
;

-- 2023-03-29T12:49:11.179Z
UPDATE AD_Menu SET   Name = 'Aufgelöste Schritte', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582182
;

-- 2023-03-29T12:49:13.292Z
UPDATE AD_Element_Trl SET PrintName='Aufgelöste Reservierungen',Updated=TO_TIMESTAMP('2023-03-29 15:49:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582182 AND AD_Language='de_CH'
;

-- 2023-03-29T12:49:13.294Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582182,'de_CH') 
;

-- 2023-03-29T12:49:16.940Z
UPDATE AD_Element_Trl SET PrintName='Aufgelöste Reservierungen',Updated=TO_TIMESTAMP('2023-03-29 15:49:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582182 AND AD_Language='de_DE'
;

-- 2023-03-29T12:49:16.941Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582182,'de_DE') 
;

-- 2023-03-29T12:49:16.951Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582182,'de_DE') 
;

-- 2023-03-29T12:49:16.951Z
UPDATE AD_PrintFormatItem pi SET PrintName='Aufgelöste Reservierungen', Name='Aufgelöste Schritte' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582182)
;

-- 2023-03-29T12:49:18.424Z
UPDATE AD_Element_Trl SET Name='Aufgelöste Reservierungen',Updated=TO_TIMESTAMP('2023-03-29 15:49:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582182 AND AD_Language='de_CH'
;

-- 2023-03-29T12:49:18.426Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582182,'de_CH') 
;

-- 2023-03-29T12:49:31.473Z
UPDATE AD_Element_Trl SET Name='Aufgelöste Reservierungen',Updated=TO_TIMESTAMP('2023-03-29 15:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582182 AND AD_Language='de_DE'
;

-- 2023-03-29T12:49:31.474Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582182,'de_DE') 
;

-- 2023-03-29T12:49:31.479Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582182,'de_DE') 
;

-- 2023-03-29T12:49:31.480Z
UPDATE AD_Column SET ColumnName=NULL, Name='Aufgelöste Reservierungen', Description=NULL, Help=NULL WHERE AD_Element_ID=582182
;

-- 2023-03-29T12:49:31.480Z
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Aufgelöste Reservierungen', Description=NULL, Help=NULL WHERE AD_Element_ID=582182 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T12:49:31.481Z
UPDATE AD_Field SET Name='Aufgelöste Reservierungen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582182) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582182)
;

-- 2023-03-29T12:49:31.489Z
UPDATE AD_PrintFormatItem pi SET PrintName='Aufgelöste Reservierungen', Name='Aufgelöste Reservierungen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582182)
;

-- 2023-03-29T12:49:31.490Z
UPDATE AD_Tab SET Name='Aufgelöste Reservierungen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582182
;

-- 2023-03-29T12:49:31.491Z
UPDATE AD_WINDOW SET Name='Aufgelöste Reservierungen', Description=NULL, Help=NULL WHERE AD_Element_ID = 582182
;

-- 2023-03-29T12:49:31.491Z
UPDATE AD_Menu SET   Name = 'Aufgelöste Reservierungen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582182
;

-- 2023-03-29T12:50:00.977Z
UPDATE AD_Process_Trl SET Name='Reservierungs-Auflösung rückgängig',Updated=TO_TIMESTAMP('2023-03-29 15:50:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585247
;

-- 2023-03-29T12:50:03.376Z
UPDATE AD_Process SET Description=NULL, Help=NULL, Name='Reservierungs-Auflösung rückgängig',Updated=TO_TIMESTAMP('2023-03-29 15:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585247
;

-- 2023-03-29T12:50:03.371Z
UPDATE AD_Process_Trl SET Name='Reservierungs-Auflösung rückgängig',Updated=TO_TIMESTAMP('2023-03-29 15:50:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585247
;

-- 2023-03-29T12:50:08.691Z
UPDATE AD_Process_Trl SET Description='Setzt die aufgelösten Stunden für die ausgewählten Ressourcen wieder auf 0 zurück.',Updated=TO_TIMESTAMP('2023-03-29 15:50:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585247
;

-- 2023-03-29T12:50:09.975Z
UPDATE AD_Process SET Description='Setzt die aufgelösten Stunden für die ausgewählten Ressourcen wieder auf 0 zurück.', Help=NULL, Name='Reservierungs-Auflösung rückgängig',Updated=TO_TIMESTAMP('2023-03-29 15:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585247
;

-- 2023-03-29T12:50:09.971Z
UPDATE AD_Process_Trl SET Description='Setzt die aufgelösten Stunden für die ausgewählten Ressourcen wieder auf 0 zurück.',Updated=TO_TIMESTAMP('2023-03-29 15:50:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585247
;

-- 2023-03-29T12:50:17.790Z
UPDATE AD_Process_Trl SET Description='Resets the resolved hours for the selected resources back to 0.',Updated=TO_TIMESTAMP('2023-03-29 15:50:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585247
;

-- 2023-03-29T12:51:10.442Z
UPDATE AD_Process_Trl SET Description='Process used to allocate a specified number of hours to the selected resources.',Updated=TO_TIMESTAMP('2023-03-29 15:51:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585246
;

-- 2023-03-29T12:51:14.753Z
UPDATE AD_Process_Trl SET Description='Verfahren, mit dem den ausgewählten Ressourcen eine bestimmte Anzahl von Stunden zugewiesen wird.',Updated=TO_TIMESTAMP('2023-03-29 15:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585246
;

-- 2023-03-29T12:51:16.180Z
UPDATE AD_Process SET Description='Verfahren, mit dem den ausgewählten Ressourcen eine bestimmte Anzahl von Stunden zugewiesen wird.', Help=NULL, Name='Stunden auflösen',Updated=TO_TIMESTAMP('2023-03-29 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585246
;

-- 2023-03-29T12:51:16.176Z
UPDATE AD_Process_Trl SET Description='Verfahren, mit dem den ausgewählten Ressourcen eine bestimmte Anzahl von Stunden zugewiesen wird.',Updated=TO_TIMESTAMP('2023-03-29 15:51:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585246
;

-- 2023-03-29T12:51:39.942Z
UPDATE AD_Element_Trl SET Name='Stunden', PrintName='Stunden',Updated=TO_TIMESTAMP('2023-03-29 15:51:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582174 AND AD_Language='de_CH'
;

-- 2023-03-29T12:51:39.944Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582174,'de_CH') 
;

-- 2023-03-29T12:51:43.609Z
UPDATE AD_Element_Trl SET Name='Stunden', PrintName='Stunden',Updated=TO_TIMESTAMP('2023-03-29 15:51:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582174 AND AD_Language='de_DE'
;

-- 2023-03-29T12:51:43.610Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582174,'de_DE') 
;

-- 2023-03-29T12:51:43.619Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582174,'de_DE') 
;

-- 2023-03-29T12:51:43.640Z
UPDATE AD_Column SET ColumnName='Hours', Name='Stunden', Description=NULL, Help=NULL WHERE AD_Element_ID=582174
;

-- 2023-03-29T12:51:43.641Z
UPDATE AD_Process_Para SET ColumnName='Hours', Name='Stunden', Description=NULL, Help=NULL, AD_Element_ID=582174 WHERE UPPER(ColumnName)='HOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-29T12:51:43.642Z
UPDATE AD_Process_Para SET ColumnName='Hours', Name='Stunden', Description=NULL, Help=NULL WHERE AD_Element_ID=582174 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T12:51:43.642Z
UPDATE AD_Field SET Name='Stunden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582174) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582174)
;

-- 2023-03-29T12:51:43.649Z
UPDATE AD_PrintFormatItem pi SET PrintName='Stunden', Name='Stunden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582174)
;

-- 2023-03-29T12:51:43.650Z
UPDATE AD_Tab SET Name='Stunden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582174
;

-- 2023-03-29T12:51:43.651Z
UPDATE AD_WINDOW SET Name='Stunden', Description=NULL, Help=NULL WHERE AD_Element_ID = 582174
;

-- 2023-03-29T12:51:43.651Z
UPDATE AD_Menu SET   Name = 'Stunden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582174
;

-- 2023-03-29T12:51:50.831Z
UPDATE AD_Element_Trl SET Description='Number of hours that must be allocated to the selected resources. If the entered number of hours is bigger then the current number of unresolved  hours, the resource will get fully resolved, as just the unresolved amount is allocated.',Updated=TO_TIMESTAMP('2023-03-29 15:51:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582174 AND AD_Language='en_US'
;

-- 2023-03-29T12:51:50.832Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582174,'en_US') 
;

-- 2023-03-29T12:51:55.517Z
UPDATE AD_Element_Trl SET Description='Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.',Updated=TO_TIMESTAMP('2023-03-29 15:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582174 AND AD_Language='de_CH'
;

-- 2023-03-29T12:51:55.518Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582174,'de_CH') 
;

-- 2023-03-29T12:51:57.221Z
UPDATE AD_Element_Trl SET Description='Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.',Updated=TO_TIMESTAMP('2023-03-29 15:51:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582174 AND AD_Language='de_DE'
;

-- 2023-03-29T12:51:57.223Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582174,'de_DE') 
;

-- 2023-03-29T12:51:57.229Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582174,'de_DE') 
;

-- 2023-03-29T12:51:57.230Z
UPDATE AD_Column SET ColumnName='Hours', Name='Stunden', Description='Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.', Help=NULL WHERE AD_Element_ID=582174
;

-- 2023-03-29T12:51:57.230Z
UPDATE AD_Process_Para SET ColumnName='Hours', Name='Stunden', Description='Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.', Help=NULL, AD_Element_ID=582174 WHERE UPPER(ColumnName)='HOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2023-03-29T12:51:57.231Z
UPDATE AD_Process_Para SET ColumnName='Hours', Name='Stunden', Description='Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.', Help=NULL WHERE AD_Element_ID=582174 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T12:51:57.232Z
UPDATE AD_Field SET Name='Stunden', Description='Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582174) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582174)
;

-- 2023-03-29T12:51:57.240Z
UPDATE AD_Tab SET Name='Stunden', Description='Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582174
;

-- 2023-03-29T12:51:57.241Z
UPDATE AD_WINDOW SET Name='Stunden', Description='Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.', Help=NULL WHERE AD_Element_ID = 582174
;

-- 2023-03-29T12:51:57.241Z
UPDATE AD_Menu SET   Name = 'Stunden', Description = 'Anzahl der Stunden, die den ausgewählten Ressourcen zugewiesen werden. Wenn die angegebene Stundenzahl größer ist als die aktuelle Zahl von noch nicht zugewiesenen Stunden, dann wird die Ressource vollständig aufgelöst.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582174
;

-- 2023-03-29T13:13:20.875Z
UPDATE AD_Element_Trl SET Name='Aufgelöste Stunden', PrintName='Aufgelöste Stunden',Updated=TO_TIMESTAMP('2023-03-29 16:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582168 AND AD_Language='de_CH'
;

-- 2023-03-29T13:13:20.909Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582168,'de_CH')
;

-- 2023-03-29T13:13:24.310Z
UPDATE AD_Element_Trl SET Name='Aufgelöste Stunden', PrintName='Aufgelöste Stunden',Updated=TO_TIMESTAMP('2023-03-29 16:13:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582168 AND AD_Language='de_DE'
;

-- 2023-03-29T13:13:24.311Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582168,'de_DE')
;

-- 2023-03-29T13:13:24.343Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582168,'de_DE')
;

-- 2023-03-29T13:13:24.344Z
UPDATE AD_Column SET ColumnName='ResolvedHours', Name='Aufgelöste Stunden', Description=NULL, Help=NULL WHERE AD_Element_ID=582168
;

-- 2023-03-29T13:13:24.345Z
UPDATE AD_Process_Para SET ColumnName='ResolvedHours', Name='Aufgelöste Stunden', Description=NULL, Help=NULL, AD_Element_ID=582168 WHERE UPPER(ColumnName)='RESOLVEDHOURS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2023-03-29T13:13:24.347Z
UPDATE AD_Process_Para SET ColumnName='ResolvedHours', Name='Aufgelöste Stunden', Description=NULL, Help=NULL WHERE AD_Element_ID=582168 AND IsCentrallyMaintained='Y'
;

-- 2023-03-29T13:13:24.347Z
UPDATE AD_Field SET Name='Aufgelöste Stunden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=582168) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 582168)
;

-- 2023-03-29T13:13:24.359Z
UPDATE AD_PrintFormatItem pi SET PrintName='Aufgelöste Stunden', Name='Aufgelöste Stunden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=582168)
;

-- 2023-03-29T13:13:24.360Z
UPDATE AD_Tab SET Name='Aufgelöste Stunden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 582168
;

-- 2023-03-29T13:13:24.361Z
UPDATE AD_WINDOW SET Name='Aufgelöste Stunden', Description=NULL, Help=NULL WHERE AD_Element_ID = 582168
;

-- 2023-03-29T13:13:24.362Z
UPDATE AD_Menu SET   Name = 'Aufgelöste Stunden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 582168
;

-- Tab: Prüfauftrag -> Aufgelöste Reservierungen
-- Table: C_Project_WO_Step
-- 2023-03-30T08:16:34.688Z
UPDATE AD_Tab SET WhereClause='C_Project_WO_Step.C_Project_WO_Step_ID IN (select C_Project_WO_Step_ID from C_Project_WO_Resource r join C_Project c on r.budget_project_id = c.c_project_id where r.duration - coalesce(r.resolvedHours, 0) = 0 and c.c_projecttype_id = 540011)',Updated=TO_TIMESTAMP('2023-03-30 11:16:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Aufgelöste Reservierungen
-- Table: C_Project_WO_Step
-- 2023-03-30T08:19:51.067Z
UPDATE AD_Tab SET WhereClause='C_Project_WO_Step.C_Project_WO_Step_ID IN (select C_Project_WO_Step_ID from C_Project_WO_Resource r join C_Project c on r.c_project_id = c.c_project_id where r.duration - coalesce(r.resolvedHours, 0) = 0 and c.c_projecttype_id = 540011)',Updated=TO_TIMESTAMP('2023-03-30 11:19:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Aufgelöste Reservierungen
-- Table: C_Project_WO_Step
-- 2023-03-30T08:21:33.663Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2023-03-30 11:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;

-- Tab: Prüfauftrag -> Aufgelöste Reservierungen
-- Table: C_Project_WO_Step
-- 2023-03-30T11:47:22.996Z
UPDATE AD_Tab SET WhereClause='C_Project_WO_Step.C_Project_WO_Step_ID IN (select C_Project_WO_Step_ID from C_Project_WO_Resource r join C_Project c on r.c_project_id = c.c_project_id where r.duration - coalesce(r.resolvedHours, 0) = 0 and c.c_projecttype_id = 540011 and r.duration <> 0)',Updated=TO_TIMESTAMP('2023-03-30 14:47:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546855
;


-- 2023-03-30T13:36:53.019Z
UPDATE AD_Process_Trl SET Description='Allocates a specified number of hours to the selected resources.',Updated=TO_TIMESTAMP('2023-03-30 16:36:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585246
;

-- 2023-03-30T13:37:00.995Z
UPDATE AD_Process SET Description='Weist den ausgewählten Ressourcen eine bestimmte Anzahl von Stunden zu.', Help=NULL, Name='Stunden auflösen',Updated=TO_TIMESTAMP('2023-03-30 16:37:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585246
;

-- 2023-03-30T13:37:00.892Z
UPDATE AD_Process_Trl SET Description='Weist den ausgewählten Ressourcen eine bestimmte Anzahl von Stunden zu.',Updated=TO_TIMESTAMP('2023-03-30 16:37:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585246
;

-- 2023-03-30T13:37:11.341Z
UPDATE AD_Process_Trl SET Description='Weist den ausgewählten Ressourcen eine bestimmte Anzahl von Stunden zu.',Updated=TO_TIMESTAMP('2023-03-30 16:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585246
;

-- 2023-03-31T06:43:30.568Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=582994,Updated=TO_TIMESTAMP('2023-03-31 09:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

-- 2023-03-31T06:48:09.092Z
UPDATE AD_SQLColumn_SourceTableColumn SET FetchTargetRecordsMethod='L', Link_Column_ID=1349, SQL_GetTargetRecordIdBySourceRecordId='',Updated=TO_TIMESTAMP('2023-03-31 09:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540145
;

-- 2023-03-31T06:49:00.330Z
UPDATE AD_SQLColumn_SourceTableColumn SET Link_Column_ID=583243, Source_Table_ID=542161,Updated=TO_TIMESTAMP('2023-03-31 09:49:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SQLColumn_SourceTableColumn_ID=540146
;

