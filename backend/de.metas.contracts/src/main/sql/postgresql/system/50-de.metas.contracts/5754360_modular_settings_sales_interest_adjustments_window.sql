-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig
-- Table: ModCntr_BaseModuleConfig
-- 2025-05-12T14:40:17.310Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,DisplayLogic,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589970,583621,0,547965,542477,541712,'Y',TO_TIMESTAMP('2025-05-12 16:40:17.156','YYYY-MM-DD HH24:MI:SS.US'),100,'@IsSOTrx/''N''@=''Y''','de.metas.contracts','N','N','A','ModCntr_BaseModuleConfig','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Basisbaustein Konfig',586789,'N',40,1,TO_TIMESTAMP('2025-05-12 16:40:17.156','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:17.313Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547965 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-05-12T14:40:17.315Z
/* DDL */  select update_tab_translation_from_ad_element(583621)
;

-- 2025-05-12T14:40:17.323Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547965)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> Mandant
-- Column: ModCntr_BaseModuleConfig.AD_Client_ID
-- 2025-05-12T14:40:42.799Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589959,742172,0,547965,TO_TIMESTAMP('2025-05-12 16:40:42.654','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-05-12 16:40:42.654','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:42.803Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742172 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-12T14:40:42.806Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-05-12T14:40:43.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742172
;

-- 2025-05-12T14:40:43.922Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742172)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> Organisation
-- Column: ModCntr_BaseModuleConfig.AD_Org_ID
-- 2025-05-12T14:40:44.033Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589960,742173,0,547965,TO_TIMESTAMP('2025-05-12 16:40:43.925','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2025-05-12 16:40:43.925','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:44.035Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742173 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-12T14:40:44.037Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-05-12T14:40:44.301Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742173
;

-- 2025-05-12T14:40:44.301Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742173)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> Aktiv
-- Column: ModCntr_BaseModuleConfig.IsActive
-- 2025-05-12T14:40:44.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589963,742174,0,547965,TO_TIMESTAMP('2025-05-12 16:40:44.304','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-05-12 16:40:44.304','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:44.414Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742174 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-12T14:40:44.416Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-05-12T14:40:44.660Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742174
;

-- 2025-05-12T14:40:44.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742174)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> Basisbaustein Konfig
-- Column: ModCntr_BaseModuleConfig.ModCntr_BaseModuleConfig_ID
-- 2025-05-12T14:40:44.779Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589966,742175,0,547965,TO_TIMESTAMP('2025-05-12 16:40:44.664','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Basisbaustein Konfig',TO_TIMESTAMP('2025-05-12 16:40:44.664','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:44.781Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742175 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-12T14:40:44.783Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583621)
;

-- 2025-05-12T14:40:44.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742175
;

-- 2025-05-12T14:40:44.785Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742175)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> Name
-- Column: ModCntr_BaseModuleConfig.Name
-- 2025-05-12T14:40:44.894Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589967,742176,0,547965,TO_TIMESTAMP('2025-05-12 16:40:44.787','YYYY-MM-DD HH24:MI:SS.US'),100,'',40,'de.metas.contracts','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2025-05-12 16:40:44.787','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:44.899Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-12T14:40:44.901Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2025-05-12T14:40:45.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742176
;

-- 2025-05-12T14:40:45.138Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742176)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> Bausteine
-- Column: ModCntr_BaseModuleConfig.ModCntr_Module_ID
-- 2025-05-12T14:40:45.253Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589968,742177,0,547965,TO_TIMESTAMP('2025-05-12 16:40:45.139','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Bausteine',TO_TIMESTAMP('2025-05-12 16:40:45.139','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:45.254Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742177 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-12T14:40:45.255Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582426)
;

-- 2025-05-12T14:40:45.260Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742177
;

-- 2025-05-12T14:40:45.260Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742177)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> Basisbausteine
-- Column: ModCntr_BaseModuleConfig.ModCntr_BaseModule_ID
-- 2025-05-12T14:40:45.356Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589969,742178,0,547965,TO_TIMESTAMP('2025-05-12 16:40:45.263','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Basisbausteine',TO_TIMESTAMP('2025-05-12 16:40:45.263','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:45.358Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742178 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-12T14:40:45.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583622)
;

-- 2025-05-12T14:40:45.362Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742178
;

-- 2025-05-12T14:40:45.362Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742178)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> Einstellungen für modulare Verträge
-- Column: ModCntr_BaseModuleConfig.ModCntr_Settings_ID
-- 2025-05-12T14:40:45.458Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589970,742179,0,547965,TO_TIMESTAMP('2025-05-12 16:40:45.365','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Einstellungen für modulare Verträge',TO_TIMESTAMP('2025-05-12 16:40:45.365','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-12T14:40:45.460Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742179 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-12T14:40:45.462Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425)
;

-- 2025-05-12T14:40:45.464Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742179
;

-- 2025-05-12T14:40:45.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742179)
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts)
-- UI Section: main
-- 2025-05-12T14:41:08.671Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547965,546548,TO_TIMESTAMP('2025-05-12 16:41:08.55','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2025-05-12 16:41:08.55','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2025-05-12T14:41:08.677Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546548 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main
-- UI Column: 10
-- 2025-05-12T14:41:08.840Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547997,546548,TO_TIMESTAMP('2025-05-12 16:41:08.74','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2025-05-12 16:41:08.74','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10
-- UI Element Group: default
-- 2025-05-12T14:41:08.960Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547997,552762,TO_TIMESTAMP('2025-05-12 16:41:08.873','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2025-05-12 16:41:08.873','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: ModCntr_BaseModuleConfig.Name
-- 2025-05-12T14:42:11.881Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742176,0,547965,552762,631490,'F',TO_TIMESTAMP('2025-05-12 16:42:11.751','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2025-05-12 16:42:11.751','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10 -> default.Bausteine
-- Column: ModCntr_BaseModuleConfig.ModCntr_Module_ID
-- 2025-05-12T14:42:46.586Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742177,0,547965,552762,631491,'F',TO_TIMESTAMP('2025-05-12 16:42:46.476','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Bausteine',20,0,0,TO_TIMESTAMP('2025-05-12 16:42:46.476','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10 -> default.Basisbausteine
-- Column: ModCntr_BaseModuleConfig.ModCntr_BaseModule_ID
-- 2025-05-12T14:42:59.920Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742178,0,547965,552762,631492,'F',TO_TIMESTAMP('2025-05-12 16:42:59.641','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Basisbausteine',30,0,0,TO_TIMESTAMP('2025-05-12 16:42:59.641','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10 -> default.Aktiv
-- Column: ModCntr_BaseModuleConfig.IsActive
-- 2025-05-12T14:44:22.261Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742174,0,547965,552762,631493,'F',TO_TIMESTAMP('2025-05-12 16:44:22.141','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',40,0,0,TO_TIMESTAMP('2025-05-12 16:44:22.141','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: ModCntr_BaseModuleConfig.Name
-- 2025-05-12T14:56:49.400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-05-12 16:56:49.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631490
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10 -> default.Bausteine
-- Column: ModCntr_BaseModuleConfig.ModCntr_Module_ID
-- 2025-05-12T14:56:49.410Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-05-12 16:56:49.41','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631491
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10 -> default.Basisbausteine
-- Column: ModCntr_BaseModuleConfig.ModCntr_BaseModule_ID
-- 2025-05-12T14:56:49.416Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-05-12 16:56:49.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631492
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig(547965,de.metas.contracts) -> main -> 10 -> default.Aktiv
-- Column: ModCntr_BaseModuleConfig.IsActive
-- 2025-05-12T14:56:49.420Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-05-12 16:56:49.42','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631493
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Basisbaustein Konfig
-- Table: ModCntr_BaseModuleConfig
-- 2025-05-12T14:57:24.567Z
UPDATE AD_Tab SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-05-12 16:57:24.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547965
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Zinssatz
-- Column: ModCntr_Settings.InterestRate
-- 2025-05-13T09:10:15.834Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2025-05-13 11:10:15.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=728700
;

-- 2025-05-13T10:29:56.740Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583627,0,'FreeInterestDays',TO_TIMESTAMP('2025-05-13 12:29:56.243','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Kostenlose Zinstage','Kostenlose Zinstage',TO_TIMESTAMP('2025-05-13 12:29:56.243','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-13T10:29:56.750Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583627 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: FreeInterestDays
-- 2025-05-13T10:30:32.797Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Free Interest Days', PrintName='Free Interest Days',Updated=TO_TIMESTAMP('2025-05-13 12:30:32.797','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583627 AND AD_Language='en_US'
;

-- 2025-05-13T10:30:32.801Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583627,'en_US')
;

-- Element: FreeInterestDays
-- 2025-05-13T10:30:33.693Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 12:30:33.693','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583627 AND AD_Language='de_CH'
;

-- 2025-05-13T10:30:33.695Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583627,'de_CH')
;

-- Element: FreeInterestDays
-- 2025-05-13T10:30:34.631Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 12:30:34.631','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583627 AND AD_Language='de_DE'
;

-- 2025-05-13T10:30:34.640Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583627,'de_DE')
;

-- 2025-05-13T10:30:34.641Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583627,'de_DE')
;

-- Column: ModCntr_Settings.FreeInterestDays
-- 2025-05-13T10:32:59.583Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589971,583627,0,11,542339,'FreeInterestDays',TO_TIMESTAMP('2025-05-13 12:32:59.446','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','de.metas.contracts',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Kostenlose Zinstage',0,0,TO_TIMESTAMP('2025-05-13 12:32:59.446','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-13T10:32:59.587Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589971 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-13T10:32:59.590Z
/* DDL */  select update_Column_Translation_From_AD_Element(583627)
;

-- 2025-05-13T10:33:19.143Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN FreeInterestDays NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Kostenlose Zinstage
-- Column: ModCntr_Settings.FreeInterestDays
-- 2025-05-13T10:34:16.378Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589971,743162,0,547013,TO_TIMESTAMP('2025-05-13 12:34:15.166','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.contracts','Y','N','N','N','N','N','N','N','Kostenlose Zinstage',TO_TIMESTAMP('2025-05-13 12:34:15.166','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-13T10:34:16.381Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=743162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-13T10:34:16.385Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583627)
;

-- 2025-05-13T10:34:16.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=743162
;

-- 2025-05-13T10:34:16.394Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(743162)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Kostenlose Zinstage
-- Column: ModCntr_Settings.FreeInterestDays
-- 2025-05-13T10:35:00.435Z
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''''@=''Y''',Updated=TO_TIMESTAMP('2025-05-13 12:35:00.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=743162
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Kostenlose Zinstage
-- Column: ModCntr_Settings.FreeInterestDays
-- 2025-05-13T10:39:35.084Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743162,0,547013,551809,631891,'F',TO_TIMESTAMP('2025-05-13 12:39:34.924','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Kostenlose Zinstage',70,0,0,TO_TIMESTAMP('2025-05-13 12:39:34.924','YYYY-MM-DD HH24:MI:SS.US'),100)
;

