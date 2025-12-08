-- 2024-09-11T16:16:18.918Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583249,0,TO_TIMESTAMP('2024-09-11 16:16:18.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Preislisten Schema - Versandkosten Kalkulationspreis','Preislisten Schema - Versandkosten Kalkulationspreis',TO_TIMESTAMP('2024-09-11 16:16:18.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:16:18.924Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583249 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-09-11T16:17:04.335Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Price List Schema - Freight Cost Calculation Price', PrintName='Price List Schema - Freight Cost Calculation Price',Updated=TO_TIMESTAMP('2024-09-11 16:17:04.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583249 AND AD_Language='en_US'
;

-- 2024-09-11T16:17:04.337Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583249,'en_US')
;

-- Element: null
-- 2024-09-11T16:17:05.120Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 16:17:05.120000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583249 AND AD_Language='de_CH'
;

-- 2024-09-11T16:17:05.122Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583249,'de_CH')
;

-- Element: null
-- 2024-09-11T16:17:07.657Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-11 16:17:07.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583249 AND AD_Language='de_DE'
;

-- 2024-09-11T16:17:07.660Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583249,'de_DE')
;

-- 2024-09-11T16:17:07.662Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583249,'de_DE')
;

-- Window: Preislisten Schema - Versandkosten Kalkulationspreis, InternalName=M_DiscountSchema_Calculated_Surcharge_Price
-- Window: Preislisten Schema - Versandkosten Kalkulationspreis, InternalName=M_DiscountSchema_Calculated_Surcharge_Price
-- 2024-09-11T16:21:21.220Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583249,0,541811,TO_TIMESTAMP('2024-09-11 16:21:21.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','M_DiscountSchema_Calculated_Surcharge_Price','Y','N','N','N','N','N','N','Y','Preislisten Schema - Versandkosten Kalkulationspreis','N',TO_TIMESTAMP('2024-09-11 16:21:21.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2024-09-11T16:21:21.222Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541811 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-09-11T16:21:21.225Z
/* DDL */  select update_window_translation_from_ad_element(583249)
;

-- 2024-09-11T16:21:21.234Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541811
;

-- 2024-09-11T16:21:21.236Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541811)
;

-- Tab: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price
-- Table: M_DiscountSchema_Calculated_Surcharge_Price
-- Tab: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price
-- Table: M_DiscountSchema_Calculated_Surcharge_Price
-- 2024-09-11T16:22:56.551Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583247,0,547572,542431,541811,'Y',TO_TIMESTAMP('2024-09-11 16:22:56.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','M_DiscountSchema_Calculated_Surcharge_Price','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Price Schema - Calculated Surcharge Price','N',10,0,TO_TIMESTAMP('2024-09-11 16:22:56.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:22:56.556Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547572 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-09-11T16:22:56.560Z
/* DDL */  select update_tab_translation_from_ad_element(583247)
;

-- 2024-09-11T16:22:56.565Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547572)
;

-- Tab: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D)
-- UI Section: main
-- 2024-09-11T16:22:59.991Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547572,546156,TO_TIMESTAMP('2024-09-11 16:22:59.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-09-11 16:22:59.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-09-11T16:22:59.994Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546156 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main
-- UI Column: 10
-- 2024-09-11T16:23:00.106Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547522,546156,TO_TIMESTAMP('2024-09-11 16:23:00.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-09-11 16:23:00.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main
-- UI Column: 20
-- 2024-09-11T16:23:00.185Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547523,546156,TO_TIMESTAMP('2024-09-11 16:23:00.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-09-11 16:23:00.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10
-- UI Element Group: default
-- 2024-09-11T16:23:00.300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547522,551898,TO_TIMESTAMP('2024-09-11 16:23:00.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-09-11 16:23:00.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Mandant
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Client_ID
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Mandant
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Client_ID
-- 2024-09-11T16:24:00.493Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588949,729863,0,547572,TO_TIMESTAMP('2024-09-11 16:24:00.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-09-11 16:24:00.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:00.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:00.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-09-11T16:24:00.883Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729863
;

-- 2024-09-11T16:24:00.884Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729863)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Org_ID
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Org_ID
-- 2024-09-11T16:24:00.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588950,729864,0,547572,TO_TIMESTAMP('2024-09-11 16:24:00.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-09-11 16:24:00.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:00.991Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:00.994Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-09-11T16:24:01.087Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729864
;

-- 2024-09-11T16:24:01.088Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729864)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.IsActive
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.IsActive
-- 2024-09-11T16:24:01.178Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588953,729865,0,547572,TO_TIMESTAMP('2024-09-11 16:24:01.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-09-11 16:24:01.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:01.179Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:01.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-09-11T16:24:01.279Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729865
;

-- 2024-09-11T16:24:01.280Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729865)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Price Schema - Calculated Surcharge Price
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.M_DiscountSchema_Calculated_Surcharge_Price_ID
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Price Schema - Calculated Surcharge Price
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.M_DiscountSchema_Calculated_Surcharge_Price_ID
-- 2024-09-11T16:24:01.373Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588956,729866,0,547572,TO_TIMESTAMP('2024-09-11 16:24:01.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Price Schema - Calculated Surcharge Price',TO_TIMESTAMP('2024-09-11 16:24:01.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:01.374Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:01.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583247)
;

-- 2024-09-11T16:24:01.377Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729866
;

-- 2024-09-11T16:24:01.378Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729866)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Name
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Name
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Name
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Name
-- 2024-09-11T16:24:01.456Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588957,729867,0,547572,TO_TIMESTAMP('2024-09-11 16:24:01.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',40,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2024-09-11 16:24:01.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:01.459Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:01.460Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2024-09-11T16:24:01.509Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729867
;

-- 2024-09-11T16:24:01.511Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729867)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Description
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Description
-- 2024-09-11T16:24:01.599Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588958,729868,0,547572,TO_TIMESTAMP('2024-09-11 16:24:01.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,120,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2024-09-11 16:24:01.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:01.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:01.602Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2024-09-11T16:24:01.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729868
;

-- 2024-09-11T16:24:01.670Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729868)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Kalkulatorische Versandkosten
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Freight_Cost_Calc_Price
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Kalkulatorische Versandkosten
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Freight_Cost_Calc_Price
-- 2024-09-11T16:24:01.769Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588959,729869,0,547572,TO_TIMESTAMP('2024-09-11 16:24:01.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'D','Y','N','N','N','N','N','N','N','Kalkulatorische Versandkosten',TO_TIMESTAMP('2024-09-11 16:24:01.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:01.771Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:01.772Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583248)
;

-- 2024-09-11T16:24:01.775Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729869
;

-- 2024-09-11T16:24:01.776Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729869)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Region
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Region_ID
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Region
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Region_ID
-- 2024-09-11T16:24:01.866Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588960,729870,0,547572,TO_TIMESTAMP('2024-09-11 16:24:01.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert eine geographische Region',10,'D','"Region" bezeichnet eine Region oder einen Bundesstaat in diesem Land.','Y','N','N','N','N','N','N','N','Region',TO_TIMESTAMP('2024-09-11 16:24:01.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:01.867Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:01.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(209)
;

-- 2024-09-11T16:24:01.873Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729870
;

-- 2024-09-11T16:24:01.874Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729870)
;

-- Field: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price -> Währung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Currency_ID
-- Field: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> Währung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Currency_ID
-- 2024-09-11T16:24:01.961Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588961,729871,0,547572,TO_TIMESTAMP('2024-09-11 16:24:01.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2024-09-11 16:24:01.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:24:01.963Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-11T16:24:01.963Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2024-09-11T16:24:01.973Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729871
;

-- 2024-09-11T16:24:01.974Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729871)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Name
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Name
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Name
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Name
-- 2024-09-11T16:24:54.955Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729867,0,547572,551898,625319,'F',TO_TIMESTAMP('2024-09-11 16:24:54.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2024-09-11 16:24:54.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Region
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Region_ID
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Region
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Region_ID
-- 2024-09-11T16:25:07.157Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729870,0,547572,551898,625320,'F',TO_TIMESTAMP('2024-09-11 16:25:07.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert eine geographische Region','"Region" bezeichnet eine Region oder einen Bundesstaat in diesem Land.','Y','N','N','Y','N','N','N',0,'Region',20,0,0,TO_TIMESTAMP('2024-09-11 16:25:07.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Kalkulatorische Versandkosten
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Freight_Cost_Calc_Price
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Kalkulatorische Versandkosten
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Freight_Cost_Calc_Price
-- 2024-09-11T16:26:40.089Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729869,0,547572,551898,625321,'F',TO_TIMESTAMP('2024-09-11 16:26:39.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Kalkulatorische Versandkosten',30,0,0,TO_TIMESTAMP('2024-09-11 16:26:39.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Description
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Description
-- 2024-09-11T16:27:06.658Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729868,0,547572,551898,625322,'F',TO_TIMESTAMP('2024-09-11 16:27:06.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',40,0,0,TO_TIMESTAMP('2024-09-11 16:27:06.532000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Währung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Currency_ID
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Währung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Currency_ID
-- 2024-09-11T16:27:19.313Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729871,0,547572,551898,625323,'F',TO_TIMESTAMP('2024-09-11 16:27:19.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',50,0,0,TO_TIMESTAMP('2024-09-11 16:27:19.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Description
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Description
-- 2024-09-11T16:28:39.832Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2024-09-11 16:28:39.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625322
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Region
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Region_ID
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Region
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Region_ID
-- 2024-09-11T16:28:53.244Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2024-09-11 16:28:53.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625320
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Kalkulatorische Versandkosten
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Freight_Cost_Calc_Price
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Kalkulatorische Versandkosten
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Freight_Cost_Calc_Price
-- 2024-09-11T16:29:06.433Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2024-09-11 16:29:06.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625321
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Name
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Name
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Name
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Name
-- 2024-09-11T16:29:22.602Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-11 16:29:22.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625319
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Description
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Description
-- 2024-09-11T16:29:22.611Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-09-11 16:29:22.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625322
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Region
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Region_ID
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Region
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Region_ID
-- 2024-09-11T16:29:22.620Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-11 16:29:22.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625320
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Kalkulatorische Versandkosten
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Freight_Cost_Calc_Price
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Kalkulatorische Versandkosten
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.Freight_Cost_Calc_Price
-- 2024-09-11T16:29:22.626Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-11 16:29:22.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625321
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Währung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Currency_ID
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 10 -> default.Währung
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.C_Currency_ID
-- 2024-09-11T16:29:22.633Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-09-11 16:29:22.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625323
;

-- UI Column: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 20
-- UI Element Group: flags
-- 2024-09-11T16:30:18.828Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547523,551899,TO_TIMESTAMP('2024-09-11 16:30:18.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2024-09-11 16:30:18.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 20
-- UI Element Group: org
-- 2024-09-11T16:30:29.466Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547523,551900,TO_TIMESTAMP('2024-09-11 16:30:29.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2024-09-11 16:30:29.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.IsActive
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 20 -> flags.Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.IsActive
-- 2024-09-11T16:31:29.738Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729865,0,547572,551899,625324,'F',TO_TIMESTAMP('2024-09-11 16:31:29.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-09-11 16:31:29.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Org_ID
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 20 -> org.Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Org_ID
-- 2024-09-11T16:33:00.726Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729864,0,547572,551900,625325,'F',TO_TIMESTAMP('2024-09-11 16:33:00.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2024-09-11 16:33:00.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Mandant
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Client_ID
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 20 -> org.Mandant
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Client_ID
-- 2024-09-11T16:33:13.208Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729863,0,547572,551900,625326,'F',TO_TIMESTAMP('2024-09-11 16:33:13.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-09-11 16:33:13.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.IsActive
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 20 -> flags.Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.IsActive
-- 2024-09-11T16:33:50.410Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-09-11 16:33:50.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625324
;

-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis -> Price Schema - Calculated Surcharge Price.Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Org_ID
-- UI Element: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D) -> Price Schema - Calculated Surcharge Price(547572,D) -> main -> 20 -> org.Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge_Price.AD_Org_ID
-- 2024-09-11T16:33:50.419Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-09-11 16:33:50.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625325
;

-- Name: Preislisten Schema - Versandkosten Kalkulationspreis
-- Action Type: null
-- 2024-09-11T16:38:37.486Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,583249,542168,0,TO_TIMESTAMP('2024-09-11 16:38:37.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','M_DiscountSchema_Calculated_Surcharge_Price','Y','N','N','N','N','Preislisten Schema - Versandkosten Kalkulationspreis',TO_TIMESTAMP('2024-09-11 16:38:37.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-11T16:38:37.487Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542168 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-09-11T16:38:37.489Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542168, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542168)
;

-- 2024-09-11T16:38:37.495Z
/* DDL */  select update_menu_translation_from_ad_element(583249)
;

-- Reordering children of `Settings`
-- Node name: `Attribute`
-- 2024-09-11T16:38:38.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541598 AND AD_Tree_ID=10
;

-- Node name: `Dimension`
-- 2024-09-11T16:38:38.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540610 AND AD_Tree_ID=10
;

-- Node name: `Product Translation (M_Product_Trl)`
-- 2024-09-11T16:38:38.098Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541054 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Translation (PP_Product_BOM_Trl)`
-- 2024-09-11T16:38:38.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541096 AND AD_Tree_ID=10
;

-- Node name: `Product Businesspartner Translation (C_BPartner_Product_Trl)`
-- 2024-09-11T16:38:38.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541068 AND AD_Tree_ID=10
;

-- Node name: `Unit of Measure (C_UOM)`
-- 2024-09-11T16:38:38.100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540794 AND AD_Tree_ID=10
;

-- Node name: `Unit of Measure Translation (C_UOM_Trl)`
-- 2024-09-11T16:38:38.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541101 AND AD_Tree_ID=10
;

-- Node name: `Compensation Group Schema (C_CompensationGroup_Schema)`
-- 2024-09-11T16:38:38.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541040 AND AD_Tree_ID=10
;

-- Node name: `Compensation Group Schema Category (C_CompensationGroup_Schema_Category)`
-- 2024-09-11T16:38:38.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541726 AND AD_Tree_ID=10
;

-- Node name: `Product Category (M_Product_Category)`
-- 2024-09-11T16:38:38.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000096 AND AD_Tree_ID=10
;

-- Node name: `Shop Category (M_Shop_Category)`
-- 2024-09-11T16:38:38.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541722 AND AD_Tree_ID=10
;

-- Node name: `Product Category Trl (M_Product_Category_Trl)`
-- 2024-09-11T16:38:38.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541130 AND AD_Tree_ID=10
;

-- Node name: `Preislisten Schema - Versandkosten Kalkulationspreis`
-- 2024-09-11T16:38:38.105Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000037, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542168 AND AD_Tree_ID=10
;

-- Reordering children of `Settings`
-- Node name: `Preislisten Schema - Versandkosten Kalkulationspreis`
-- 2024-09-11T16:38:50.964Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542168 AND AD_Tree_ID=10
;

-- Node name: `Price List Schema (M_DiscountSchema)`
-- 2024-09-11T16:38:50.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540777 AND AD_Tree_ID=10
;

-- Node name: `Price List Schema Line (M_DiscountSchemaLine)`
-- 2024-09-11T16:38:50.967Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541402 AND AD_Tree_ID=10
;

-- Node name: `Pricing Rule (C_PricingRule)`
-- 2024-09-11T16:38:50.968Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540778 AND AD_Tree_ID=10
;

-- Node name: `Pricing Conditions Restrictions (C_PriceLimit_Restriction)`
-- 2024-09-11T16:38:50.969Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541078 AND AD_Tree_ID=10
;

-- Name: Preislisten Schema - Versandkosten Kalkulationspreis
-- Action Type: W
-- Window: Preislisten Schema - Versandkosten Kalkulationspreis(541811,D)
-- 2024-09-11T16:40:33.182Z
UPDATE AD_Menu SET Action='W', AD_Window_ID=541811,Updated=TO_TIMESTAMP('2024-09-11 16:40:33.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542168
;

