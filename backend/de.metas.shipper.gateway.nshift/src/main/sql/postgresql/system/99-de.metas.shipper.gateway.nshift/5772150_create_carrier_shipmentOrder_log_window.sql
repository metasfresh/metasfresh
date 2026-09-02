-- Run mode: SWING_CLIENT

-- 2025-10-02T19:15:24.854Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584082,0,TO_TIMESTAMP('2025-10-02 19:15:24.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Frachtauftrag-Protokoll','Frachtauftrag-Protokoll',TO_TIMESTAMP('2025-10-02 19:15:24.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:15:24.858Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584082 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-02T19:15:56.984Z
UPDATE AD_Element_Trl SET Name='Carrier Order Log', PrintName='Carrier Order Log',Updated=TO_TIMESTAMP('2025-10-02 19:15:56.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584082 AND AD_Language='en_US'
;

-- 2025-10-02T19:15:56.986Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T19:15:57.307Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584082,'en_US')
;

-- Window: Frachtauftrag-Protokoll, InternalName=Carrier_ShipmentOrder_Log
-- 2025-10-02T19:17:35.331Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584082,0,541955,TO_TIMESTAMP('2025-10-02 19:17:35.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Carrier_ShipmentOrder_Log','Y','N','N','N','N','N','N','Y','Frachtauftrag-Protokoll','N',TO_TIMESTAMP('2025-10-02 19:17:35.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Q',0,0,100)
;

-- 2025-10-02T19:17:35.334Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541955 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-10-02T19:17:35.337Z
/* DDL */  select update_window_translation_from_ad_element(584082)
;

-- 2025-10-02T19:17:35.348Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541955
;

-- 2025-10-02T19:17:35.351Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541955)
;

-- Tab: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log
-- Table: Carrier_ShipmentOrder_Log
-- 2025-10-02T19:18:08.118Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584047,0,548454,542537,541955,'Y',TO_TIMESTAMP('2025-10-02 19:18:07.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','Carrier_ShipmentOrder_Log','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Carrier ShipmentOrder Log','N',10,0,TO_TIMESTAMP('2025-10-02 19:18:07.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:08.121Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548454 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-02T19:18:08.123Z
/* DDL */  select update_tab_translation_from_ad_element(584047)
;

-- 2025-10-02T19:18:08.126Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548454)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Mandant
-- Column: Carrier_ShipmentOrder_Log.AD_Client_ID
-- 2025-10-02T19:18:15.045Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591170,754666,0,548454,TO_TIMESTAMP('2025-10-02 19:18:14.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-02 19:18:14.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:15.048Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754666 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:15.050Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-02T19:18:15.715Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754666
;

-- 2025-10-02T19:18:15.717Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754666)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Sektion
-- Column: Carrier_ShipmentOrder_Log.AD_Org_ID
-- 2025-10-02T19:18:15.852Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591172,754667,0,548454,TO_TIMESTAMP('2025-10-02 19:18:15.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-02 19:18:15.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:15.853Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754667 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:15.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-02T19:18:16.043Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754667
;

-- 2025-10-02T19:18:16.045Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754667)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Shipment Order
-- Column: Carrier_ShipmentOrder_Log.Carrier_ShipmentOrder_ID
-- 2025-10-02T19:18:16.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591176,754668,0,548454,TO_TIMESTAMP('2025-10-02 19:18:16.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Shipment Order',TO_TIMESTAMP('2025-10-02 19:18:16.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:16.174Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754668 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:16.176Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584001)
;

-- 2025-10-02T19:18:16.178Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754668
;

-- 2025-10-02T19:18:16.179Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754668)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Carrier ShipmentOrder Log
-- Column: Carrier_ShipmentOrder_Log.Carrier_ShipmentOrder_Log_ID
-- 2025-10-02T19:18:16.327Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591177,754669,0,548454,TO_TIMESTAMP('2025-10-02 19:18:16.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Carrier ShipmentOrder Log',TO_TIMESTAMP('2025-10-02 19:18:16.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:16.329Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754669 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:16.330Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584047)
;

-- 2025-10-02T19:18:16.332Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754669
;

-- 2025-10-02T19:18:16.333Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754669)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Duration (ms)
-- Column: Carrier_ShipmentOrder_Log.DurationMillis
-- 2025-10-02T19:18:16.463Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591179,754670,0,548454,TO_TIMESTAMP('2025-10-02 19:18:16.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Duration (ms)',TO_TIMESTAMP('2025-10-02 19:18:16.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:16.464Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754670 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:16.465Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543760)
;

-- 2025-10-02T19:18:16.470Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754670
;

-- 2025-10-02T19:18:16.471Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754670)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Aktiv
-- Column: Carrier_ShipmentOrder_Log.IsActive
-- 2025-10-02T19:18:16.598Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591180,754671,0,548454,TO_TIMESTAMP('2025-10-02 19:18:16.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-02 19:18:16.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:16.600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754671 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:16.602Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-02T19:18:16.861Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754671
;

-- 2025-10-02T19:18:16.862Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754671)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Fehler
-- Column: Carrier_ShipmentOrder_Log.IsError
-- 2025-10-02T19:18:17.007Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591181,754672,0,548454,TO_TIMESTAMP('2025-10-02 19:18:16.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Fehler ist bei der Durchführung aufgetreten',1,'D','Y','N','N','N','N','N','N','N','Fehler',TO_TIMESTAMP('2025-10-02 19:18:16.866000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:17.008Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754672 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:17.009Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395)
;

-- 2025-10-02T19:18:17.017Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754672
;

-- 2025-10-02T19:18:17.017Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754672)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Nachricht anfordern
-- Column: Carrier_ShipmentOrder_Log.RequestMessage
-- 2025-10-02T19:18:17.227Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591182,754673,0,548454,TO_TIMESTAMP('2025-10-02 19:18:17.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,4000,'D','Y','N','N','N','N','N','N','N','Nachricht anfordern',TO_TIMESTAMP('2025-10-02 19:18:17.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:17.228Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754673 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:17.230Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543758)
;

-- 2025-10-02T19:18:17.232Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754673
;

-- 2025-10-02T19:18:17.233Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754673)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Antwortnachricht
-- Column: Carrier_ShipmentOrder_Log.ResponseMessage
-- 2025-10-02T19:18:17.360Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591183,754674,0,548454,TO_TIMESTAMP('2025-10-02 19:18:17.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,4000,'D','Y','N','N','N','N','N','N','N','Antwortnachricht',TO_TIMESTAMP('2025-10-02 19:18:17.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:17.361Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754674 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:17.362Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543759)
;

-- 2025-10-02T19:18:17.364Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754674
;

-- 2025-10-02T19:18:17.364Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754674)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Request ID
-- Column: Carrier_ShipmentOrder_Log.RequestID
-- 2025-10-02T19:18:17.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591243,754675,0,548454,TO_TIMESTAMP('2025-10-02 19:18:17.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,36,'D','Y','N','N','N','N','N','N','N','Request ID',TO_TIMESTAMP('2025-10-02 19:18:17.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:18:17.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754675 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:18:17.494Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584065)
;

-- 2025-10-02T19:18:17.496Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754675
;

-- 2025-10-02T19:18:17.496Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754675)
;

-- Tab: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D)
-- UI Section: main
-- 2025-10-02T19:19:53.573Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548454,546980,TO_TIMESTAMP('2025-10-02 19:19:53.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-02 19:19:53.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-02T19:19:53.574Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546980 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main
-- UI Column: 10
-- 2025-10-02T19:19:58.415Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548505,546980,TO_TIMESTAMP('2025-10-02 19:19:58.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-02 19:19:58.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10
-- UI Element Group: main
-- 2025-10-02T19:20:10.286Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548505,553595,TO_TIMESTAMP('2025-10-02 19:20:10.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-10-02 19:20:10.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> Erstellt
-- Column: Carrier_ShipmentOrder_Log.Created
-- 2025-10-02T19:20:38.562Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591174,754677,0,548454,0,TO_TIMESTAMP('2025-10-02 19:20:38.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',0,'U',0,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Erstellt',0,0,10,0,1,1,TO_TIMESTAMP('2025-10-02 19:20:38.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:20:38.563Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754677 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T19:20:38.566Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-10-02T19:20:38.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754677
;

-- 2025-10-02T19:20:38.644Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754677)
;

-- Column: Carrier_ShipmentOrder_Log.RequestID
-- 2025-10-02T19:22:14.840Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-02 19:22:14.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591243
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Erstellt
-- Column: Carrier_ShipmentOrder_Log.Created
-- 2025-10-02T19:22:42.861Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754677,0,548454,553595,637634,'F',TO_TIMESTAMP('2025-10-02 19:22:42.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','N','N',0,'Erstellt',10,0,0,TO_TIMESTAMP('2025-10-02 19:22:42.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Fehler
-- Column: Carrier_ShipmentOrder_Log.IsError
-- 2025-10-02T19:22:52.347Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754672,0,548454,553595,637635,'F',TO_TIMESTAMP('2025-10-02 19:22:52.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','N','Y','N','N','N',0,'Fehler',20,0,0,TO_TIMESTAMP('2025-10-02 19:22:52.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Duration (ms)
-- Column: Carrier_ShipmentOrder_Log.DurationMillis
-- 2025-10-02T19:23:15.441Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754670,0,548454,553595,637636,'F',TO_TIMESTAMP('2025-10-02 19:23:15.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Duration (ms)',30,0,0,TO_TIMESTAMP('2025-10-02 19:23:15.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Nachricht anfordern
-- Column: Carrier_ShipmentOrder_Log.RequestMessage
-- 2025-10-02T19:23:25.708Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754673,0,548454,553595,637637,'F',TO_TIMESTAMP('2025-10-02 19:23:25.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Nachricht anfordern',40,0,0,TO_TIMESTAMP('2025-10-02 19:23:25.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Antwortnachricht
-- Column: Carrier_ShipmentOrder_Log.ResponseMessage
-- 2025-10-02T19:23:32.588Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754674,0,548454,553595,637638,'F',TO_TIMESTAMP('2025-10-02 19:23:32.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Antwortnachricht',50,0,0,TO_TIMESTAMP('2025-10-02 19:23:32.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Shipment Order
-- Column: Carrier_ShipmentOrder_Log.Carrier_ShipmentOrder_ID
-- 2025-10-02T19:24:26.979Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754668,0,548454,553595,637639,'F',TO_TIMESTAMP('2025-10-02 19:24:26.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Shipment Order',20,0,0,TO_TIMESTAMP('2025-10-02 19:24:26.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Fehler
-- Column: Carrier_ShipmentOrder_Log.IsError
-- 2025-10-02T19:24:34.185Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-10-02 19:24:34.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637635
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Duration (ms)
-- Column: Carrier_ShipmentOrder_Log.DurationMillis
-- 2025-10-02T19:24:37.164Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2025-10-02 19:24:37.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637636
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Nachricht anfordern
-- Column: Carrier_ShipmentOrder_Log.RequestMessage
-- 2025-10-02T19:24:40.344Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2025-10-02 19:24:40.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637637
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Antwortnachricht
-- Column: Carrier_ShipmentOrder_Log.ResponseMessage
-- 2025-10-02T19:24:44.516Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2025-10-02 19:24:44.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637638
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Erstellt
-- Column: Carrier_ShipmentOrder_Log.Created
-- 2025-10-02T19:25:41.428Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-02 19:25:41.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637634
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Shipment Order
-- Column: Carrier_ShipmentOrder_Log.Carrier_ShipmentOrder_ID
-- 2025-10-02T19:25:41.434Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-02 19:25:41.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637639
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Fehler
-- Column: Carrier_ShipmentOrder_Log.IsError
-- 2025-10-02T19:25:41.439Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-02 19:25:41.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637635
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Antwortnachricht
-- Column: Carrier_ShipmentOrder_Log.ResponseMessage
-- 2025-10-02T19:25:41.444Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-02 19:25:41.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637638
;

-- UI Element: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log(548454,D) -> main -> 10 -> main.Duration (ms)
-- Column: Carrier_ShipmentOrder_Log.DurationMillis
-- 2025-10-02T19:25:41.449Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-02 19:25:41.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637636
;

-- Tab: Frachtauftrag-Protokoll(541955,D) -> Carrier ShipmentOrder Log
-- Table: Carrier_ShipmentOrder_Log
-- 2025-10-02T19:33:37.456Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-10-02 19:33:37.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548454
;

-- Name: Frachtauftrag-Protokoll
-- Action Type: W
-- Window: Frachtauftrag-Protokoll(541955,D)
-- 2025-10-02T19:34:02.853Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584082,542258,0,541955,TO_TIMESTAMP('2025-10-02 19:34:02.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Carrier_ShipmentOrder_Log','Y','N','N','N','N','Frachtauftrag-Protokoll',TO_TIMESTAMP('2025-10-02 19:34:02.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T19:34:02.855Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542258 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-10-02T19:34:02.856Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542258, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542258)
;

-- 2025-10-02T19:34:02.865Z
/* DDL */  select update_menu_translation_from_ad_element(584082)
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2025-10-02T19:34:03.438Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `QR Code Configuration (QRCode_Configuration)`
-- 2025-10-02T19:34:03.440Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542139 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2025-10-02T19:34:03.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2025-10-02T19:34:03.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2025-10-02T19:34:03.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2025-10-02T19:34:03.443Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2025-10-02T19:34:03.444Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2025-10-02T19:34:03.445Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2025-10-02T19:34:03.446Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2025-10-02T19:34:03.446Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2025-10-02T19:34:03.447Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2025-10-02T19:34:03.447Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2025-10-02T19:34:03.449Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2025-10-02T19:34:03.450Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2025-10-02T19:34:03.451Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2025-10-02T19:34:03.451Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2025-10-02T19:34:03.452Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2025-10-02T19:34:03.453Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2025-10-02T19:34:03.454Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2025-10-02T19:34:03.454Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2025-10-02T19:34:03.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2025-10-02T19:34:03.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2025-10-02T19:34:03.457Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2025-10-02T19:34:03.458Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-02T19:34:03.459Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI HU Manager (MobileUI_HUManager)`
-- 2025-10-02T19:34:03.459Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542163 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-02T19:34:03.460Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-02T19:34:03.461Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2025-10-02T19:34:03.462Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2025-10-02T19:34:03.462Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2025-10-02T19:34:03.463Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Generate new HU QR Codes (de.metas.handlingunits.process.GenerateHUQRCodes)`
-- 2025-10-02T19:34:03.464Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542152 AND AD_Tree_ID=10
;

-- Node name: `Distribution Candidate (DD_Order_Candidate)`
-- 2025-10-02T19:34:03.465Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542164 AND AD_Tree_ID=10
;

-- Node name: `Scannable Code Format (C_ScannableCode_Format)`
-- 2025-10-02T19:34:03.466Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542221 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order Log (Dhl_ShipmentOrder_Log)`
-- 2025-10-02T19:34:03.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542218 AND AD_Tree_ID=10
;

-- Node name: `Frachtauftrag-Protokoll`
-- 2025-10-02T19:34:03.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542258 AND AD_Tree_ID=10
;

-- Element: DurationMillis
-- 2025-10-02T20:04:09.024Z
UPDATE AD_Element_Trl SET Name='Dauer (ms)', PrintName='Dauer (ms)',Updated=TO_TIMESTAMP('2025-10-02 20:04:09.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543760 AND AD_Language='de_CH'
;

-- 2025-10-02T20:04:09.026Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:04:09.457Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543760,'de_CH')
;

-- Element: DurationMillis
-- 2025-10-02T20:04:12.060Z
UPDATE AD_Element_Trl SET Name='Dauer (ms)', PrintName='Dauer (ms)',Updated=TO_TIMESTAMP('2025-10-02 20:04:12.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543760 AND AD_Language='de_DE'
;

-- 2025-10-02T20:04:12.061Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:04:12.860Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543760,'de_DE')
;

-- 2025-10-02T20:04:12.861Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543760,'de_DE')
;

-- Element: DurationMillis
-- 2025-10-02T20:04:17.651Z
UPDATE AD_Element_Trl SET Name='Dauer (ms)', PrintName='Dauer (ms)',Updated=TO_TIMESTAMP('2025-10-02 20:04:17.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543760 AND AD_Language='fr_CH'
;

-- 2025-10-02T20:04:17.653Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:04:18.133Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543760,'fr_CH')
;

-- Element: Carrier_ShipmentOrder_ID
-- 2025-10-02T20:04:55.464Z
UPDATE AD_Element_Trl SET Name='Versandauftrag', PrintName='Versandauftrag',Updated=TO_TIMESTAMP('2025-10-02 20:04:55.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584001 AND AD_Language='de_CH'
;

-- 2025-10-02T20:04:55.466Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:04:55.770Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584001,'de_CH')
;

-- Element: Carrier_ShipmentOrder_ID
-- 2025-10-02T20:04:58.054Z
UPDATE AD_Element_Trl SET Name='Versandauftrag', PrintName='Versandauftrag',Updated=TO_TIMESTAMP('2025-10-02 20:04:58.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584001 AND AD_Language='fr_CH'
;

-- 2025-10-02T20:04:58.055Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:04:58.364Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584001,'fr_CH')
;

-- Element: Carrier_ShipmentOrder_ID
-- 2025-10-02T20:05:00.609Z
UPDATE AD_Element_Trl SET Name='Versandauftrag', PrintName='Versandauftrag',Updated=TO_TIMESTAMP('2025-10-02 20:05:00.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584001 AND AD_Language='de_DE'
;

-- 2025-10-02T20:05:00.610Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:05:01.132Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584001,'de_DE')
;

-- 2025-10-02T20:05:01.133Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584001,'de_DE')
;

-- Element: RequestID
-- 2025-10-02T20:06:47.876Z
UPDATE AD_Element_Trl SET Name='Anfrage-ID', PrintName='Anfrage-ID',Updated=TO_TIMESTAMP('2025-10-02 20:06:47.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584065 AND AD_Language='de_CH'
;

-- 2025-10-02T20:06:47.877Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:06:48.137Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584065,'de_CH')
;

-- Element: RequestID
-- 2025-10-02T20:06:49.896Z
UPDATE AD_Element_Trl SET Name='Anfrage-ID', PrintName='Anfrage-ID',Updated=TO_TIMESTAMP('2025-10-02 20:06:49.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584065 AND AD_Language='fr_CH'
;

-- 2025-10-02T20:06:49.897Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:06:50.156Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584065,'fr_CH')
;

-- Element: RequestID
-- 2025-10-02T20:06:52.815Z
UPDATE AD_Element_Trl SET Name='Anfrage-ID', PrintName='Anfrage-ID',Updated=TO_TIMESTAMP('2025-10-02 20:06:52.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584065 AND AD_Language='de_DE'
;

-- 2025-10-02T20:06:52.816Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T20:06:53.270Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584065,'de_DE')
;

-- 2025-10-02T20:06:53.271Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584065,'de_DE')
;

