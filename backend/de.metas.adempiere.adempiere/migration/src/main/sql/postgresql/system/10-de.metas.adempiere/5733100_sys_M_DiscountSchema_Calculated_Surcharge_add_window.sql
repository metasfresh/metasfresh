-- 2024-09-12T06:53:40.560Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583252,0,TO_TIMESTAMP('2024-09-12 06:53:40.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Preislisten Schema - Kalkulierter Zuschlag','Preislisten Schema - Kalkulierter Zuschlag',TO_TIMESTAMP('2024-09-12 06:53:40.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T06:53:40.562Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583252 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-09-12T06:54:05.374Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Price List Schema - Calculated Surcharge', PrintName='Price List Schema - Calculated Surcharge',Updated=TO_TIMESTAMP('2024-09-12 06:54:05.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583252 AND AD_Language='en_US'
;

-- 2024-09-12T06:54:05.377Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583252,'en_US')
;

-- Element: null
-- 2024-09-12T06:54:06.049Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-12 06:54:06.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583252 AND AD_Language='de_DE'
;

-- 2024-09-12T06:54:06.052Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583252,'de_DE')
;

-- 2024-09-12T06:54:06.053Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583252,'de_DE')
;

-- Element: null
-- 2024-09-12T06:54:06.959Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-12 06:54:06.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583252 AND AD_Language='de_CH'
;

-- 2024-09-12T06:54:06.961Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583252,'de_CH')
;

-- Window: Preislisten Schema - Kalkulierter Zuschlag, InternalName=M_DiscountSchema_Calculated_Surcharge
-- Window: Preislisten Schema - Kalkulierter Zuschlag, InternalName=M_DiscountSchema_Calculated_Surcharge
-- 2024-09-12T06:56:50.703Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583252,0,541812,TO_TIMESTAMP('2024-09-12 06:56:50.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','M_DiscountSchema_Calculated_Surcharge','Y','N','N','N','N','N','N','Y','Preislisten Schema - Kalkulierter Zuschlag','N',TO_TIMESTAMP('2024-09-12 06:56:50.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2024-09-12T06:56:50.704Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541812 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-09-12T06:56:50.707Z
/* DDL */  select update_window_translation_from_ad_element(583252)
;

-- 2024-09-12T06:56:50.709Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541812
;

-- 2024-09-12T06:56:50.712Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541812)
;

-- Tab: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge
-- Table: M_DiscountSchema_Calculated_Surcharge
-- Tab: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge
-- Table: M_DiscountSchema_Calculated_Surcharge
-- 2024-09-12T06:59:46.241Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583250,0,547573,542432,541812,'Y',TO_TIMESTAMP('2024-09-12 06:59:46.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','M_DiscountSchema_Calculated_Surcharge','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Price Schema - Calculated Surcharge','N',10,0,TO_TIMESTAMP('2024-09-12 06:59:46.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T06:59:46.244Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547573 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-09-12T06:59:46.246Z
/* DDL */  select update_tab_translation_from_ad_element(583250)
;

-- 2024-09-12T06:59:46.248Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547573)
;

-- Tab: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D)
-- UI Section: main
-- 2024-09-12T06:59:59.227Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547573,546157,TO_TIMESTAMP('2024-09-12 06:59:59.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-09-12 06:59:59.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-09-12T06:59:59.230Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546157 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main
-- UI Column: 10
-- 2024-09-12T06:59:59.324Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547524,546157,TO_TIMESTAMP('2024-09-12 06:59:59.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-09-12 06:59:59.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main
-- UI Column: 20
-- 2024-09-12T06:59:59.439Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547525,546157,TO_TIMESTAMP('2024-09-12 06:59:59.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2024-09-12 06:59:59.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 10
-- UI Element Group: default
-- 2024-09-12T06:59:59.552Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547524,551901,TO_TIMESTAMP('2024-09-12 06:59:59.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-09-12 06:59:59.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge -> Mandant
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Client_ID
-- Field: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> Mandant
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Client_ID
-- 2024-09-12T07:00:15.803Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588964,729872,0,547573,TO_TIMESTAMP('2024-09-12 07:00:15.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-09-12 07:00:15.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:00:15.805Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:00:15.806Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-09-12T07:00:16.145Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729872
;

-- 2024-09-12T07:00:16.146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729872)
;

-- Field: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge -> Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Org_ID
-- Field: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Org_ID
-- 2024-09-12T07:00:16.257Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588965,729873,0,547573,TO_TIMESTAMP('2024-09-12 07:00:16.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-09-12 07:00:16.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:00:16.258Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:00:16.259Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-09-12T07:00:16.340Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729873
;

-- 2024-09-12T07:00:16.341Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729873)
;

-- Field: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge -> Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge.IsActive
-- Field: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge.IsActive
-- 2024-09-12T07:00:16.435Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588968,729874,0,547573,TO_TIMESTAMP('2024-09-12 07:00:16.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-09-12 07:00:16.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:00:16.437Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:00:16.438Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-09-12T07:00:16.522Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729874
;

-- 2024-09-12T07:00:16.523Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729874)
;

-- Field: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge -> Price Schema - Calculated Surcharge
-- Column: M_DiscountSchema_Calculated_Surcharge.M_DiscountSchema_Calculated_Surcharge_ID
-- Field: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> Price Schema - Calculated Surcharge
-- Column: M_DiscountSchema_Calculated_Surcharge.M_DiscountSchema_Calculated_Surcharge_ID
-- 2024-09-12T07:00:16.618Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588971,729875,0,547573,TO_TIMESTAMP('2024-09-12 07:00:16.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Price Schema - Calculated Surcharge',TO_TIMESTAMP('2024-09-12 07:00:16.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:00:16.621Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:00:16.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583250)
;

-- 2024-09-12T07:00:16.627Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729875
;

-- 2024-09-12T07:00:16.628Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729875)
;

-- Field: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge -> Name
-- Column: M_DiscountSchema_Calculated_Surcharge.Name
-- Field: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> Name
-- Column: M_DiscountSchema_Calculated_Surcharge.Name
-- 2024-09-12T07:00:16.722Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588972,729876,0,547573,TO_TIMESTAMP('2024-09-12 07:00:16.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',40,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2024-09-12 07:00:16.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:00:16.724Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:00:16.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2024-09-12T07:00:16.770Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729876
;

-- 2024-09-12T07:00:16.771Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729876)
;

-- Field: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge -> Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge.Description
-- Field: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge.Description
-- 2024-09-12T07:00:16.859Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588973,729877,0,547573,TO_TIMESTAMP('2024-09-12 07:00:16.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,120,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2024-09-12 07:00:16.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:00:16.862Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:00:16.865Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2024-09-12T07:00:16.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729877
;

-- 2024-09-12T07:00:16.920Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729877)
;

-- Field: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge -> Zuschlag Kalkulations SQL
-- Column: M_DiscountSchema_Calculated_Surcharge.Surcharge_Calc_SQL
-- Field: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> Zuschlag Kalkulations SQL
-- Column: M_DiscountSchema_Calculated_Surcharge.Surcharge_Calc_SQL
-- 2024-09-12T07:00:17.029Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588974,729878,0,547573,TO_TIMESTAMP('2024-09-12 07:00:16.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die SQL muss ohne ";" eingeben werden und muss zwei Parameter enthalten: "$1" ( Target_PriceList_Version_ID ) und "$2" ( Source_M_ProductPrice_ID )',2000,'D','Y','N','N','N','N','N','N','N','Zuschlag Kalkulations SQL',TO_TIMESTAMP('2024-09-12 07:00:16.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:00:17.031Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T07:00:17.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583251)
;

-- 2024-09-12T07:00:17.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729878
;

-- 2024-09-12T07:00:17.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729878)
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Name
-- Column: M_DiscountSchema_Calculated_Surcharge.Name
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 10 -> default.Name
-- Column: M_DiscountSchema_Calculated_Surcharge.Name
-- 2024-09-12T07:01:55.526Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729876,0,547573,551901,625327,'F',TO_TIMESTAMP('2024-09-12 07:01:55.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2024-09-12 07:01:55.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge.Description
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 10 -> default.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge.Description
-- 2024-09-12T07:02:25.722Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729877,0,547573,551901,625328,'F',TO_TIMESTAMP('2024-09-12 07:02:25.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',20,0,0,TO_TIMESTAMP('2024-09-12 07:02:25.582000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 10
-- UI Element Group: sql
-- 2024-09-12T07:02:57.504Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547524,551902,TO_TIMESTAMP('2024-09-12 07:02:57.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','sql',20,TO_TIMESTAMP('2024-09-12 07:02:57.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Zuschlag Kalkulations SQL
-- Column: M_DiscountSchema_Calculated_Surcharge.Surcharge_Calc_SQL
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 10 -> sql.Zuschlag Kalkulations SQL
-- Column: M_DiscountSchema_Calculated_Surcharge.Surcharge_Calc_SQL
-- 2024-09-12T07:03:28.218Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,729878,0,547573,551902,625329,'F',TO_TIMESTAMP('2024-09-12 07:03:28.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die SQL muss ohne ";" eingeben werden und muss zwei Parameter enthalten: "$1" ( Target_PriceList_Version_ID ) und "$2" ( Source_M_ProductPrice_ID )','Y','N','N','Y','N','N','N',0,'Zuschlag Kalkulations SQL',10,0,0,TO_TIMESTAMP('2024-09-12 07:03:28.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'XXL')
;

-- UI Column: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 20
-- UI Element Group: flags
-- 2024-09-12T07:03:51.884Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547525,551903,TO_TIMESTAMP('2024-09-12 07:03:51.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2024-09-12 07:03:51.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge.IsActive
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 20 -> flags.Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge.IsActive
-- 2024-09-12T07:04:04.597Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729874,0,547573,551903,625330,'F',TO_TIMESTAMP('2024-09-12 07:04:04.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-09-12 07:04:04.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 20
-- UI Element Group: org
-- 2024-09-12T07:04:15.238Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547525,551904,TO_TIMESTAMP('2024-09-12 07:04:15.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2024-09-12 07:04:15.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Org_ID
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 20 -> org.Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Org_ID
-- 2024-09-12T07:04:37.668Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729873,0,547573,551904,625331,'F',TO_TIMESTAMP('2024-09-12 07:04:37.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2024-09-12 07:04:37.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Mandant
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Client_ID
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 20 -> org.Mandant
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Client_ID
-- 2024-09-12T07:04:48.887Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729872,0,547573,551904,625332,'F',TO_TIMESTAMP('2024-09-12 07:04:48.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-09-12 07:04:48.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Name
-- Column: M_DiscountSchema_Calculated_Surcharge.Name
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 10 -> default.Name
-- Column: M_DiscountSchema_Calculated_Surcharge.Name
-- 2024-09-12T07:05:11.872Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-09-12 07:05:11.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625327
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge.Description
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 10 -> default.Beschreibung
-- Column: M_DiscountSchema_Calculated_Surcharge.Description
-- 2024-09-12T07:05:11.881Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-09-12 07:05:11.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625328
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge.IsActive
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 20 -> flags.Aktiv
-- Column: M_DiscountSchema_Calculated_Surcharge.IsActive
-- 2024-09-12T07:05:11.889Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-12 07:05:11.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625330
;

-- UI Element: Preislisten Schema - Kalkulierter Zuschlag -> Price Schema - Calculated Surcharge.Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Org_ID
-- UI Element: Preislisten Schema - Kalkulierter Zuschlag(541812,D) -> Price Schema - Calculated Surcharge(547573,D) -> main -> 20 -> org.Sektion
-- Column: M_DiscountSchema_Calculated_Surcharge.AD_Org_ID
-- 2024-09-12T07:05:11.895Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-09-12 07:05:11.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625331
;

-- Name: Preislisten Schema - Kalkulierter Zuschlag
-- Action Type: W
-- Window: Preislisten Schema - Kalkulierter Zuschlag(541812,D)
-- 2024-09-12T07:08:15.196Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583252,542169,0,541812,TO_TIMESTAMP('2024-09-12 07:08:15.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','M_DiscountSchema_Calculated_Surcharge','Y','N','N','N','N','Preislisten Schema - Kalkulierter Zuschlag',TO_TIMESTAMP('2024-09-12 07:08:15.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T07:08:15.199Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542169 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-09-12T07:08:15.201Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542169, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542169)
;

-- 2024-09-12T07:08:15.204Z
/* DDL */  select update_menu_translation_from_ad_element(583252)
;

-- Reordering children of `Settings`
-- Node name: `Preislisten Schema - Versandkosten Kalkulationspreis`
-- 2024-09-12T07:08:15.785Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542168 AND AD_Tree_ID=10
;

-- Node name: `Price List Schema (M_DiscountSchema)`
-- 2024-09-12T07:08:16.198Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540777 AND AD_Tree_ID=10
;

-- Node name: `Price List Schema Line (M_DiscountSchemaLine)`
-- 2024-09-12T07:08:16.200Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541402 AND AD_Tree_ID=10
;

-- Node name: `Pricing Rule (C_PricingRule)`
-- 2024-09-12T07:08:16.202Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540778 AND AD_Tree_ID=10
;

-- Node name: `Pricing Conditions Restrictions (C_PriceLimit_Restriction)`
-- 2024-09-12T07:08:16.202Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541078 AND AD_Tree_ID=10
;

-- Node name: `Preislisten Schema - Kalkulierter Zuschlag`
-- 2024-09-12T07:08:16.204Z
UPDATE AD_TreeNodeMM SET Parent_ID=540755, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542169 AND AD_Tree_ID=10
;
