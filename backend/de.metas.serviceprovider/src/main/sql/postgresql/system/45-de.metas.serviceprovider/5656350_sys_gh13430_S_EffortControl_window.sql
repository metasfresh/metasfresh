-- Window: S_EffortControl, InternalName=Effort control
-- 2022-09-15T05:33:42.879Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581449,0,541615,TO_TIMESTAMP('2022-09-15 08:33:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Effort control','Y','N','N','N','N','N','N','Y','S_EffortControl','N',TO_TIMESTAMP('2022-09-15 08:33:42','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-09-15T05:33:42.887Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541615 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-09-15T05:33:42.899Z
/* DDL */  select update_window_translation_from_ad_element(581449) 
;

-- 2022-09-15T05:33:42.930Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541615
;

-- 2022-09-15T05:33:42.941Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541615)
;

-- Tab: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl
-- Table: S_EffortControl
-- 2022-09-15T05:34:46.835Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581449,0,546631,542215,541615,'Y',TO_TIMESTAMP('2022-09-15 08:34:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','N','N','A','S_EffortControl','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'S_EffortControl','N',10,0,TO_TIMESTAMP('2022-09-15 08:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:46.842Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546631 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-09-15T05:34:46.850Z
/* DDL */  select update_tab_translation_from_ad_element(581449) 
;

-- 2022-09-15T05:34:46.862Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546631)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Mandant
-- Column: S_EffortControl.AD_Client_ID
-- 2022-09-15T05:34:53.463Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584333,707278,0,546631,TO_TIMESTAMP('2022-09-15 08:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.serviceprovider','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-09-15 08:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:53.468Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707278 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:53.474Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-09-15T05:34:54.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707278
;

-- 2022-09-15T05:34:54.334Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707278)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Organisation
-- Column: S_EffortControl.AD_Org_ID
-- 2022-09-15T05:34:54.438Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584334,707279,0,546631,TO_TIMESTAMP('2022-09-15 08:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.serviceprovider','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-09-15 08:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:54.441Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707279 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:54.444Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-09-15T05:34:54.904Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707279
;

-- 2022-09-15T05:34:54.904Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707279)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Aktiv
-- Column: S_EffortControl.IsActive
-- 2022-09-15T05:34:55.005Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584337,707280,0,546631,TO_TIMESTAMP('2022-09-15 08:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.serviceprovider','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-09-15 08:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:55.007Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707280 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:55.009Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-09-15T05:34:55.852Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707280
;

-- 2022-09-15T05:34:55.853Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707280)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> S_EffortControl
-- Column: S_EffortControl.S_EffortControl_ID
-- 2022-09-15T05:34:55.941Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584340,707281,0,546631,TO_TIMESTAMP('2022-09-15 08:34:55','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','S_EffortControl',TO_TIMESTAMP('2022-09-15 08:34:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:55.943Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707281 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:55.945Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581449) 
;

-- 2022-09-15T05:34:55.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707281
;

-- 2022-09-15T05:34:55.951Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707281)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Pending effort sum
-- Column: S_EffortControl.PendingEffortSum
-- 2022-09-15T05:34:56.049Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584341,707282,0,546631,TO_TIMESTAMP('2022-09-15 08:34:55','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Pending effort sum',TO_TIMESTAMP('2022-09-15 08:34:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.051Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707282 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.052Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581450) 
;

-- 2022-09-15T05:34:56.056Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707282
;

-- 2022-09-15T05:34:56.056Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707282)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Effort sum
-- Column: S_EffortControl.EffortSum
-- 2022-09-15T05:34:56.151Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584342,707283,0,546631,TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Effort sum',TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.153Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707283 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.156Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581451) 
;

-- 2022-09-15T05:34:56.167Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707283
;

-- 2022-09-15T05:34:56.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707283)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Budget
-- Column: S_EffortControl.Budget
-- 2022-09-15T05:34:56.254Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584343,707284,0,546631,TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Budget',TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.258Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707284 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.262Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581452) 
;

-- 2022-09-15T05:34:56.275Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707284
;

-- 2022-09-15T05:34:56.276Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707284)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Invoiceable hours
-- Column: S_EffortControl.InvoiceableHours
-- 2022-09-15T05:34:56.375Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584344,707285,0,546631,TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100,14,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Invoiceable hours',TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707285 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.379Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581453) 
;

-- 2022-09-15T05:34:56.386Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707285
;

-- 2022-09-15T05:34:56.386Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707285)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Over buget
-- Column: S_EffortControl.IsOverBuget
-- 2022-09-15T05:34:56.488Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584345,707286,0,546631,TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Over buget',TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.491Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707286 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.496Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581454) 
;

-- 2022-09-15T05:34:56.506Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707286
;

-- 2022-09-15T05:34:56.507Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707286)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Issue closed
-- Column: S_EffortControl.IsIssueClosed
-- 2022-09-15T05:34:56.600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584346,707287,0,546631,TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Issue closed',TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.602Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707287 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.605Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581455) 
;

-- 2022-09-15T05:34:56.614Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707287
;

-- 2022-09-15T05:34:56.615Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707287)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Processed
-- Column: S_EffortControl.IsProcessed
-- 2022-09-15T05:34:56.717Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584347,707288,0,546631,TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Processed',TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.720Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707288 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.725Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581456) 
;

-- 2022-09-15T05:34:56.736Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707288
;

-- 2022-09-15T05:34:56.737Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707288)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Effort aggregation key
-- Column: S_EffortControl.EffortAggregationKey
-- 2022-09-15T05:34:56.837Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584348,707289,0,546631,TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Effort aggregation key',TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.839Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707289 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.842Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581457) 
;

-- 2022-09-15T05:34:56.853Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707289
;

-- 2022-09-15T05:34:56.854Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707289)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Kostenstelle
-- Column: S_EffortControl.C_Activity_ID
-- 2022-09-15T05:34:56.955Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584349,707290,0,546631,TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',10,'de.metas.serviceprovider','Erfassung der zugehörigen Kostenstelle','Y','N','N','N','N','N','N','N','Kostenstelle',TO_TIMESTAMP('2022-09-15 08:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:56.958Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707290 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:56.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2022-09-15T05:34:57.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707290
;

-- 2022-09-15T05:34:57.010Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707290)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Projekt
-- Column: S_EffortControl.C_Project_ID
-- 2022-09-15T05:34:57.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584350,707291,0,546631,TO_TIMESTAMP('2022-09-15 08:34:57','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project',10,'de.metas.serviceprovider','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2022-09-15 08:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:34:57.104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707291 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:34:57.106Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2022-09-15T05:34:57.145Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707291
;

-- 2022-09-15T05:34:57.146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707291)
;

-- 2022-09-15T05:35:06.838Z
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- Tab: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider)
-- UI Section: main
-- 2022-09-15T05:35:13.074Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546631,545254,TO_TIMESTAMP('2022-09-15 08:35:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-09-15 08:35:12','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-09-15T05:35:13.081Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545254 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main
-- UI Column: 10
-- 2022-09-15T05:35:13.287Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546391,545254,TO_TIMESTAMP('2022-09-15 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-09-15 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main
-- UI Column: 20
-- 2022-09-15T05:35:13.393Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546392,545254,TO_TIMESTAMP('2022-09-15 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-09-15 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10
-- UI Element Group: default
-- 2022-09-15T05:35:13.597Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546391,549927,TO_TIMESTAMP('2022-09-15 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-09-15 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10 -> default.Kostenstelle
-- Column: S_EffortControl.C_Activity_ID
-- 2022-09-15T05:35:44.188Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707290,0,546631,612982,549927,'F',TO_TIMESTAMP('2022-09-15 08:35:44','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',10,0,0,TO_TIMESTAMP('2022-09-15 08:35:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10 -> default.Projekt
-- Column: S_EffortControl.C_Project_ID
-- 2022-09-15T05:35:55.370Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707291,0,546631,612983,549927,'F',TO_TIMESTAMP('2022-09-15 08:35:55','YYYY-MM-DD HH24:MI:SS'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',20,0,0,TO_TIMESTAMP('2022-09-15 08:35:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> Aktualisiert
-- Column: S_EffortControl.Updated
-- 2022-09-15T05:36:24.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584338,707292,0,546631,TO_TIMESTAMP('2022-09-15 08:36:24','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.serviceprovider','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2022-09-15 08:36:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:36:24.175Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707292 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:36:24.177Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2022-09-15T05:36:24.318Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707292
;

-- 2022-09-15T05:36:24.318Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707292)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10 -> default.Aktualisiert
-- Column: S_EffortControl.Updated
-- 2022-09-15T05:36:41.075Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707292,0,546631,612984,549927,'F',TO_TIMESTAMP('2022-09-15 08:36:40','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','N','N',0,'Aktualisiert',30,0,0,TO_TIMESTAMP('2022-09-15 08:36:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10
-- UI Element Group: effort
-- 2022-09-15T05:36:50.355Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546391,549928,TO_TIMESTAMP('2022-09-15 08:36:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','effort',20,TO_TIMESTAMP('2022-09-15 08:36:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10 -> effort.Pending effort sum
-- Column: S_EffortControl.PendingEffortSum
-- 2022-09-15T05:37:04.434Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707282,0,546631,612985,549928,'F',TO_TIMESTAMP('2022-09-15 08:37:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Pending effort sum',10,0,0,TO_TIMESTAMP('2022-09-15 08:37:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10 -> effort.Effort sum
-- Column: S_EffortControl.EffortSum
-- 2022-09-15T05:37:11.519Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707283,0,546631,612986,549928,'F',TO_TIMESTAMP('2022-09-15 08:37:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Effort sum',20,0,0,TO_TIMESTAMP('2022-09-15 08:37:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10 -> effort.Budget
-- Column: S_EffortControl.Budget
-- 2022-09-15T05:37:17.624Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707284,0,546631,612987,549928,'F',TO_TIMESTAMP('2022-09-15 08:37:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Budget',30,0,0,TO_TIMESTAMP('2022-09-15 08:37:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 10 -> effort.Invoiceable hours
-- Column: S_EffortControl.InvoiceableHours
-- 2022-09-15T05:37:25.008Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707285,0,546631,612988,549928,'F',TO_TIMESTAMP('2022-09-15 08:37:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Invoiceable hours',40,0,0,TO_TIMESTAMP('2022-09-15 08:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20
-- UI Element Group: flags
-- 2022-09-15T05:37:39.154Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546392,549929,TO_TIMESTAMP('2022-09-15 08:37:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-09-15 08:37:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> flags.Over buget
-- Column: S_EffortControl.IsOverBuget
-- 2022-09-15T05:37:55.328Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707286,0,546631,612989,549929,'F',TO_TIMESTAMP('2022-09-15 08:37:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Over buget',10,0,0,TO_TIMESTAMP('2022-09-15 08:37:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> flags.Issue closed
-- Column: S_EffortControl.IsIssueClosed
-- 2022-09-15T05:38:01.819Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707287,0,546631,612990,549929,'F',TO_TIMESTAMP('2022-09-15 08:38:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Issue closed',20,0,0,TO_TIMESTAMP('2022-09-15 08:38:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> flags.Processed
-- Column: S_EffortControl.IsProcessed
-- 2022-09-15T05:38:09.058Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707288,0,546631,612991,549929,'F',TO_TIMESTAMP('2022-09-15 08:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Processed',30,0,0,TO_TIMESTAMP('2022-09-15 08:38:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20
-- UI Element Group: org
-- 2022-09-15T05:38:18.216Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546392,549930,TO_TIMESTAMP('2022-09-15 08:38:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-09-15 08:38:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> org.Organisation
-- Column: S_EffortControl.AD_Org_ID
-- 2022-09-15T05:38:33.562Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707279,0,546631,612992,549930,'F',TO_TIMESTAMP('2022-09-15 08:38:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2022-09-15 08:38:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> org.Mandant
-- Column: S_EffortControl.AD_Client_ID
-- 2022-09-15T05:38:42.526Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707278,0,546631,612993,549930,'F',TO_TIMESTAMP('2022-09-15 08:38:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2022-09-15 08:38:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl(546631,de.metas.serviceprovider) -> main -> 20 -> flags.Aktiv
-- Column: S_EffortControl.IsActive
-- 2022-09-15T05:39:20.270Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707280,0,546631,612994,549929,'F',TO_TIMESTAMP('2022-09-15 08:39:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',5,0,0,TO_TIMESTAMP('2022-09-15 08:39:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: S_EffortControl(541615,de.metas.serviceprovider) -> S_EffortControl
-- Table: S_EffortControl
-- 2022-09-15T05:39:26.421Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-09-15 08:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546631
;

-- 2022-09-15T05:41:22.049Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581458,0,TO_TIMESTAMP('2022-09-15 08:41:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Budget Issue','Budget Issue',TO_TIMESTAMP('2022-09-15 08:41:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:41:22.053Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581458 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-09-15T05:41:42.684Z
UPDATE AD_Element_Trl SET Name='Budget issue', PrintName='Budget issue',Updated=TO_TIMESTAMP('2022-09-15 08:41:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581458 AND AD_Language='de_DE'
;

-- 2022-09-15T05:41:42.685Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581458,'de_DE') 
;

-- 2022-09-15T05:41:42.687Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581458,'de_DE') 
;

-- Element: null
-- 2022-09-15T05:42:01.380Z
UPDATE AD_Element_Trl SET Name='Budget issue', PrintName='Budget issue',Updated=TO_TIMESTAMP('2022-09-15 08:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581458 AND AD_Language='en_US'
;

-- 2022-09-15T05:42:01.383Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581458,'en_US') 
;

-- Element: null
-- 2022-09-15T05:42:04.475Z
UPDATE AD_Element_Trl SET Name='Budget issue', PrintName='Budget issue',Updated=TO_TIMESTAMP('2022-09-15 08:42:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581458 AND AD_Language='fr_CH'
;

-- 2022-09-15T05:42:04.478Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581458,'fr_CH') 
;

-- Element: null
-- 2022-09-15T05:42:09.214Z
UPDATE AD_Element_Trl SET Name='Budget issue', PrintName='Budget issue',Updated=TO_TIMESTAMP('2022-09-15 08:42:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581458 AND AD_Language='nl_NL'
;

-- 2022-09-15T05:42:09.216Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581458,'nl_NL') 
;

-- Element: null
-- 2022-09-15T05:42:13.398Z
UPDATE AD_Element_Trl SET Name='Budget issue', PrintName='Budget issue',Updated=TO_TIMESTAMP('2022-09-15 08:42:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581458 AND AD_Language='de_CH'
;

-- 2022-09-15T05:42:13.401Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581458,'de_CH') 
;

-- Tab: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue
-- Table: S_Issue
-- 2022-09-15T05:44:03.474Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,584051,581458,0,546632,541468,541615,'Y',TO_TIMESTAMP('2022-09-15 08:44:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','N','N','A','Budget issue','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Budget issue',584349,'N',20,1,TO_TIMESTAMP('2022-09-15 08:44:03','YYYY-MM-DD HH24:MI:SS'),100,'S_Issue.C_Activity_ID=@C_Activity_ID@ AND S_Issue.IsEffortIssue=''N''')
;

-- 2022-09-15T05:44:03.480Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546632 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-09-15T05:44:03.487Z
/* DDL */  select update_tab_translation_from_ad_element(581458) 
;

-- 2022-09-15T05:44:03.495Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546632)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> Budgetiert
-- Column: S_Issue.BudgetedEffort
-- 2022-09-15T05:44:36.722Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570189,707293,0,546632,TO_TIMESTAMP('2022-09-15 08:44:36','YYYY-MM-DD HH24:MI:SS'),100,'Ursprünglich geplanter oder erwarteter Aufwand.',14,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Budgetiert',TO_TIMESTAMP('2022-09-15 08:44:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:44:36.723Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707293 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:44:36.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577381) 
;

-- 2022-09-15T05:44:36.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707293
;

-- 2022-09-15T05:44:36.731Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707293)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> Issue
-- Column: S_Issue.S_Issue_ID
-- 2022-09-15T05:44:59.885Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570203,707294,0,546632,TO_TIMESTAMP('2022-09-15 08:44:59','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Issue',TO_TIMESTAMP('2022-09-15 08:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:44:59.887Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707294 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:44:59.890Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577622) 
;

-- 2022-09-15T05:44:59.895Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707294
;

-- 2022-09-15T05:44:59.895Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707294)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> Issue effort (H:mm)
-- Column: S_Issue.IssueEffort
-- 2022-09-15T05:45:19.313Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570624,707295,0,546632,TO_TIMESTAMP('2022-09-15 08:45:19','YYYY-MM-DD HH24:MI:SS'),100,'Time spent directly on this task in H:mm format.',20,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Issue effort (H:mm)',TO_TIMESTAMP('2022-09-15 08:45:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:45:19.314Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707295 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:45:19.316Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577676) 
;

-- 2022-09-15T05:45:19.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707295
;

-- 2022-09-15T05:45:19.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707295)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> Name
-- Column: S_Issue.Name
-- 2022-09-15T05:45:48.956Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570200,707296,0,546632,TO_TIMESTAMP('2022-09-15 08:45:48','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.serviceprovider','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-09-15 08:45:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:45:48.958Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707296 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:45:48.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-09-15T05:45:49.225Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707296
;

-- 2022-09-15T05:45:49.225Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707296)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> Status
-- Column: S_Issue.Status
-- 2022-09-15T05:46:01.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570687,707297,0,546632,TO_TIMESTAMP('2022-09-15 08:46:01','YYYY-MM-DD HH24:MI:SS'),100,'',25,'de.metas.serviceprovider','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2022-09-15 08:46:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:46:01.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707297 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:46:01.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020) 
;

-- 2022-09-15T05:46:01.505Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707297
;

-- 2022-09-15T05:46:01.506Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707297)
;

-- Tab: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider)
-- UI Section: main
-- 2022-09-15T05:46:10.808Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546632,545255,TO_TIMESTAMP('2022-09-15 08:46:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-09-15 08:46:10','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-09-15T05:46:10.809Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545255 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main
-- UI Column: 10
-- 2022-09-15T05:46:10.912Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546393,545255,TO_TIMESTAMP('2022-09-15 08:46:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-09-15 08:46:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10
-- UI Element Group: default
-- 2022-09-15T05:46:11.026Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546393,549931,TO_TIMESTAMP('2022-09-15 08:46:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-09-15 08:46:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10 -> default.Issue
-- Column: S_Issue.S_Issue_ID
-- 2022-09-15T05:46:34.514Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707294,0,546632,612995,549931,'F',TO_TIMESTAMP('2022-09-15 08:46:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Issue',10,0,0,TO_TIMESTAMP('2022-09-15 08:46:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10 -> default.Name
-- Column: S_Issue.Name
-- 2022-09-15T05:46:42.035Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707296,0,546632,612996,549931,'F',TO_TIMESTAMP('2022-09-15 08:46:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2022-09-15 08:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10 -> default.Issue effort (H:mm)
-- Column: S_Issue.IssueEffort
-- 2022-09-15T05:46:57.229Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707295,0,546632,612997,549931,'F',TO_TIMESTAMP('2022-09-15 08:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Time spent directly on this task in H:mm format.','Y','N','N','Y','N','N','N',0,'Issue effort (H:mm)',30,0,0,TO_TIMESTAMP('2022-09-15 08:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10 -> default.Budgetiert
-- Column: S_Issue.BudgetedEffort
-- 2022-09-15T05:47:04.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707293,0,546632,612998,549931,'F',TO_TIMESTAMP('2022-09-15 08:47:04','YYYY-MM-DD HH24:MI:SS'),100,'Ursprünglich geplanter oder erwarteter Aufwand.','Y','N','N','Y','N','N','N',0,'Budgetiert',40,0,0,TO_TIMESTAMP('2022-09-15 08:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> Abrechenb. Kind-Issues
-- Column: S_Issue.InvoiceableChildEffort
-- 2022-09-15T05:47:27.130Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573828,707298,0,546632,TO_TIMESTAMP('2022-09-15 08:47:26','YYYY-MM-DD HH24:MI:SS'),100,'Summe der abrechenbaren Aufwände aller Kind-Issues',14,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Abrechenb. Kind-Issues',TO_TIMESTAMP('2022-09-15 08:47:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:47:27.131Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707298 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:47:27.133Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579144) 
;

-- 2022-09-15T05:47:27.138Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707298
;

-- 2022-09-15T05:47:27.138Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707298)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10 -> default.Abrechenb. Kind-Issues
-- Column: S_Issue.InvoiceableChildEffort
-- 2022-09-15T05:47:55.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707298,0,546632,612999,549931,'F',TO_TIMESTAMP('2022-09-15 08:47:55','YYYY-MM-DD HH24:MI:SS'),100,'Summe der abrechenbaren Aufwände aller Kind-Issues','Y','N','N','Y','N','N','N',0,'Abrechenb. Kind-Issues',50,0,0,TO_TIMESTAMP('2022-09-15 08:47:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Budget issue(546632,de.metas.serviceprovider) -> main -> 10 -> default.Status
-- Column: S_Issue.Status
-- 2022-09-15T05:48:04.043Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707297,0,546632,613000,549931,'F',TO_TIMESTAMP('2022-09-15 08:48:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Status',60,0,0,TO_TIMESTAMP('2022-09-15 08:48:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue
-- Table: S_Issue
-- 2022-09-15T05:49:00.017Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,584051,577625,0,546633,541468,541615,'Y',TO_TIMESTAMP('2022-09-15 08:48:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','N','N','A','Effort issue','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Effort issue',584349,'N',30,1,TO_TIMESTAMP('2022-09-15 08:48:59','YYYY-MM-DD HH24:MI:SS'),100,'S_Issue.C_Activity_ID=@C_Activity_ID@ AND S_Issue.IsEffortIssue=''Y''')
;

-- 2022-09-15T05:49:00.018Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546633 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-09-15T05:49:00.019Z
/* DDL */  select update_tab_translation_from_ad_element(577625) 
;

-- 2022-09-15T05:49:00.022Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546633)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> Issue
-- Column: S_Issue.S_Issue_ID
-- 2022-09-15T05:49:09.305Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570203,707299,0,546633,TO_TIMESTAMP('2022-09-15 08:49:09','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Issue',TO_TIMESTAMP('2022-09-15 08:49:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:49:09.306Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707299 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:49:09.307Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577622) 
;

-- 2022-09-15T05:49:09.311Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707299
;

-- 2022-09-15T05:49:09.311Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707299)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> Name
-- Column: S_Issue.Name
-- 2022-09-15T05:49:22.263Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570200,707300,0,546633,TO_TIMESTAMP('2022-09-15 08:49:22','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.serviceprovider','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-09-15 08:49:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:49:22.264Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:49:22.265Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-09-15T05:49:22.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707300
;

-- 2022-09-15T05:49:22.477Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707300)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> Budgetiert
-- Column: S_Issue.BudgetedEffort
-- 2022-09-15T05:49:31.922Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570189,707301,0,546633,TO_TIMESTAMP('2022-09-15 08:49:31','YYYY-MM-DD HH24:MI:SS'),100,'Ursprünglich geplanter oder erwarteter Aufwand.',14,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Budgetiert',TO_TIMESTAMP('2022-09-15 08:49:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:49:31.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:49:31.925Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577381) 
;

-- 2022-09-15T05:49:31.929Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707301
;

-- 2022-09-15T05:49:31.930Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707301)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> Issue effort (H:mm)
-- Column: S_Issue.IssueEffort
-- 2022-09-15T05:49:45.442Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570624,707302,0,546633,TO_TIMESTAMP('2022-09-15 08:49:45','YYYY-MM-DD HH24:MI:SS'),100,'Time spent directly on this task in H:mm format.',20,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Issue effort (H:mm)',TO_TIMESTAMP('2022-09-15 08:49:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:49:45.444Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:49:45.446Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577676) 
;

-- 2022-09-15T05:49:45.451Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707302
;

-- 2022-09-15T05:49:45.452Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707302)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> Abrechenb. Kind-Issues
-- Column: S_Issue.InvoiceableChildEffort
-- 2022-09-15T05:50:05.895Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573828,707303,0,546633,TO_TIMESTAMP('2022-09-15 08:50:05','YYYY-MM-DD HH24:MI:SS'),100,'Summe der abrechenbaren Aufwände aller Kind-Issues',14,'de.metas.serviceprovider','Y','N','N','N','N','N','N','N','Abrechenb. Kind-Issues',TO_TIMESTAMP('2022-09-15 08:50:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:50:05.899Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:50:05.903Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579144) 
;

-- 2022-09-15T05:50:05.908Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707303
;

-- 2022-09-15T05:50:05.908Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707303)
;

-- Field: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> Status
-- Column: S_Issue.Status
-- 2022-09-15T05:50:24.379Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570687,707304,0,546633,TO_TIMESTAMP('2022-09-15 08:50:24','YYYY-MM-DD HH24:MI:SS'),100,'',25,'de.metas.serviceprovider','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2022-09-15 08:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T05:50:24.380Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T05:50:24.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020) 
;

-- 2022-09-15T05:50:24.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707304
;

-- 2022-09-15T05:50:24.388Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707304)
;

-- Tab: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider)
-- UI Section: main
-- 2022-09-15T05:50:39.480Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546633,545256,TO_TIMESTAMP('2022-09-15 08:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-09-15 08:50:39','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-09-15T05:50:39.481Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545256 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- UI Section: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main
-- UI Column: 10
-- 2022-09-15T05:50:39.595Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546394,545256,TO_TIMESTAMP('2022-09-15 08:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-09-15 08:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10
-- UI Element Group: default
-- 2022-09-15T05:50:39.708Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546394,549932,TO_TIMESTAMP('2022-09-15 08:50:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-09-15 08:50:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Issue
-- Column: S_Issue.S_Issue_ID
-- 2022-09-15T05:50:59.669Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707299,0,546633,613001,549932,'F',TO_TIMESTAMP('2022-09-15 08:50:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Issue',10,0,0,TO_TIMESTAMP('2022-09-15 08:50:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Name
-- Column: S_Issue.Name
-- 2022-09-15T05:51:05.400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707300,0,546633,613002,549932,'F',TO_TIMESTAMP('2022-09-15 08:51:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2022-09-15 08:51:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Budgetiert
-- Column: S_Issue.BudgetedEffort
-- 2022-09-15T05:51:11.200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707301,0,546633,613003,549932,'F',TO_TIMESTAMP('2022-09-15 08:51:11','YYYY-MM-DD HH24:MI:SS'),100,'Ursprünglich geplanter oder erwarteter Aufwand.','Y','N','N','Y','N','N','N',0,'Budgetiert',30,0,0,TO_TIMESTAMP('2022-09-15 08:51:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Issue effort (H:mm)
-- Column: S_Issue.IssueEffort
-- 2022-09-15T05:51:17.316Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707302,0,546633,613004,549932,'F',TO_TIMESTAMP('2022-09-15 08:51:17','YYYY-MM-DD HH24:MI:SS'),100,'Time spent directly on this task in H:mm format.','Y','N','N','Y','N','N','N',0,'Issue effort (H:mm)',40,0,0,TO_TIMESTAMP('2022-09-15 08:51:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Abrechenb. Kind-Issues
-- Column: S_Issue.InvoiceableChildEffort
-- 2022-09-15T05:51:25.190Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707303,0,546633,613005,549932,'F',TO_TIMESTAMP('2022-09-15 08:51:25','YYYY-MM-DD HH24:MI:SS'),100,'Summe der abrechenbaren Aufwände aller Kind-Issues','Y','N','N','Y','N','N','N',0,'Abrechenb. Kind-Issues',50,0,0,TO_TIMESTAMP('2022-09-15 08:51:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: S_EffortControl(541615,de.metas.serviceprovider) -> Effort issue(546633,de.metas.serviceprovider) -> main -> 10 -> default.Status
-- Column: S_Issue.Status
-- 2022-09-15T05:51:31.543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707304,0,546633,613006,549932,'F',TO_TIMESTAMP('2022-09-15 08:51:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Status',60,0,0,TO_TIMESTAMP('2022-09-15 08:51:31','YYYY-MM-DD HH24:MI:SS'),100)
;

