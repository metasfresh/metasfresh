-- Run mode: SWING_CLIENT

-- 2025-11-12T19:18:04.347Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584206,0,TO_TIMESTAMP('2025-11-12 19:18:03.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Inventurdaten eingeben','D','Das Fenster "Inventur" ermöglicht, Listen für Inventurzählergebnisse zu erzeugen. Die Zahlen können dann verarbeitet werden und aktualisieren die Warenbestandsdaten im System. Normalerweise werden Sie die Positionen automatisch erstellen wollen. Wenn Sie sie manuell erstellen, stellen Sie sicher, dass der Buchwert korrekt ist.','Y','Inventur Mobile','Inventur Mobile',TO_TIMESTAMP('2025-11-12 19:18:03.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:18:04.430Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584206 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-11-12T19:19:10.988Z
UPDATE AD_Element_Trl SET Description='Enter Physical Inventory', Help='The Physical Inventory Window allows you to generate inventory count lists.  These counts can then be processed which will update the actual inventory with the new counts. Normally you would create inventory count lines automaticelly. If you create them manually, make sure that the book value is correct.', IsTranslated='Y', Name='Physical Inventory Mobile', PrintName='Physical Inventory Mobile',Updated=TO_TIMESTAMP('2025-11-12 19:19:10.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584206 AND AD_Language='en_US'
;

-- 2025-11-12T19:19:11.051Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-12T19:19:21.971Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584206,'en_US')
;

-- Window: Inventur Mobile, InternalName=null
-- 2025-11-12T19:19:43.923Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584206,0,541969,TO_TIMESTAMP('2025-11-12 19:19:43.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Inventurdaten eingeben','D','Das Fenster "Inventur" ermöglicht, Listen für Inventurzählergebnisse zu erzeugen. Die Zahlen können dann verarbeitet werden und aktualisieren die Warenbestandsdaten im System. Normalerweise werden Sie die Positionen automatisch erstellen wollen. Wenn Sie sie manuell erstellen, stellen Sie sicher, dass der Buchwert korrekt ist.','Y','N','N','N','N','N','N','Y','Inventur Mobile','N',TO_TIMESTAMP('2025-11-12 19:19:43.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-11-12T19:19:43.985Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541969 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-11-12T19:19:44.118Z
/* DDL */  select update_window_translation_from_ad_element(584206)
;

-- 2025-11-12T19:19:44.191Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541969
;

-- 2025-11-12T19:19:44.256Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541969)
;

-- Window: Inventur Mobile, InternalName=null
-- 2025-11-12T19:20:11.232Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='D', IsActive='Y', IsBetaFunctionality='Y', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=null, WindowType='T', WinHeight=NULL, WinWidth=NULL, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2025-11-12 19:20:11.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541969
;

-- 2025-11-12T19:20:11.481Z
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 541969
;

-- 2025-11-12T19:20:11.543Z
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541969, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 168
;

-- Tab: Inventur Mobile(541969,D) -> Bestandszählung
-- Table: M_Inventory
-- 2025-11-12T19:20:12.719Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,572633,0,291,548510,321,541969,'Y',TO_TIMESTAMP('2025-11-12 19:20:11.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestandszählung','D','N','The Inventory Count Tab defines the parameters for a manual count and adjustment of inventory.  When creating the inventory count list automatically, only the actual stored products are included. The exception is when you select the equals 0 (=0) option, then zero on hand records of all stocked products is created for that location.','A','M_Inventory','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Bestandszählung','N',10,0,TO_TIMESTAMP('2025-11-12 19:20:11.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'/*whereclause from AD_Tab_ID=255*/ M_Inventory.M_Inventory_ID in (select i.M_Inventory_ID from  M_Inventory i  join C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  where  dt.DocBaseType = ''MMI'' and dt.DocSubType IN (''IAH'',''ISH'',''VIY''))')
;

-- 2025-11-12T19:20:12.783Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548510 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-11-12T19:20:12.845Z
/* DDL */  select update_tab_translation_from_ad_element(572633)
;

-- 2025-11-12T19:20:12.909Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548510)
;

-- 2025-11-12T19:20:13.036Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548510
;

-- 2025-11-12T19:20:13.099Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548510, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 255
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Mandant
-- Column: M_Inventory.AD_Client_ID
-- 2025-11-12T19:20:14.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3543,756035,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:13.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2025-11-12 19:20:13.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:14.691Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:14.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-11-12T19:20:14.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756035
;

-- 2025-11-12T19:20:15.012Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756035)
;

-- 2025-11-12T19:20:15.136Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756035
;

-- 2025-11-12T19:20:15.198Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756035, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2681
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Sektion
-- Column: M_Inventory.AD_Org_ID
-- 2025-11-12T19:20:16.136Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3544,756036,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:15.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2025-11-12 19:20:15.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:16.198Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:16.261Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-11-12T19:20:16.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756036
;

-- 2025-11-12T19:20:16.482Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756036)
;

-- 2025-11-12T19:20:16.613Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756036
;

-- 2025-11-12T19:20:16.675Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756036, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2682
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Beschreibung
-- Column: M_Inventory.Description
-- 2025-11-12T19:20:17.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3551,756037,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:16.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Beschreibung',50,50,999,1,TO_TIMESTAMP('2025-11-12 19:20:16.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:17.667Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:17.735Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-11-12T19:20:17.842Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756037
;

-- 2025-11-12T19:20:17.904Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756037)
;

-- 2025-11-12T19:20:18.035Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756037
;

-- 2025-11-12T19:20:18.097Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756037, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2683
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Aktiv
-- Column: M_Inventory.IsActive
-- 2025-11-12T19:20:19.097Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3545,756038,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:18.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',60,0,1,1,TO_TIMESTAMP('2025-11-12 19:20:18.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:19.159Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:19.222Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-11-12T19:20:19.367Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756038
;

-- 2025-11-12T19:20:19.429Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756038)
;

-- 2025-11-12T19:20:19.555Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756038
;

-- 2025-11-12T19:20:19.617Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756038, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2684
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Inventur
-- Column: M_Inventory.M_Inventory_ID
-- 2025-11-12T19:20:20.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3542,756039,1001147,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:19.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Parameter für eine physische Inventur',14,'D','Bezeichnet die eindeutigen Parameter für eine physische Inventur',0,'Y','N','N','N','N','N','N','N','N','N','N','Inventur',70,0,1,1,TO_TIMESTAMP('2025-11-12 19:20:19.680000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:20.613Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:20.677Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001147)
;

-- 2025-11-12T19:20:20.746Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756039
;

-- 2025-11-12T19:20:20.811Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756039)
;

-- 2025-11-12T19:20:20.942Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756039
;

-- 2025-11-12T19:20:21.003Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756039, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2685
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Bewegungsdatum
-- Column: M_Inventory.MovementDate
-- 2025-11-12T19:20:21.946Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3552,756040,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:21.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde',14,'D','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Bewegungsdatum',80,80,1,1,TO_TIMESTAMP('2025-11-12 19:20:21.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:22.008Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:22.074Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1037)
;

-- 2025-11-12T19:20:22.149Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756040
;

-- 2025-11-12T19:20:22.212Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756040)
;

-- 2025-11-12T19:20:22.343Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756040
;

-- 2025-11-12T19:20:22.405Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756040, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2686
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Nr.
-- Column: M_Inventory.DocumentNo
-- 2025-11-12T19:20:23.308Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsMandatory,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3550,756041,1000601,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:22.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document sequence number of the document',20,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','Y','Y','N','N','N','N','Y','N','N','N','Nr.',30,30,-1,1,1,TO_TIMESTAMP('2025-11-12 19:20:22.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:23.371Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:23.434Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000601)
;

-- 2025-11-12T19:20:23.505Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756041
;

-- 2025-11-12T19:20:23.566Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756041)
;

-- 2025-11-12T19:20:23.702Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756041
;

-- 2025-11-12T19:20:23.768Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756041, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2687
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Verarbeitet
-- Column: M_Inventory.Processed
-- 2025-11-12T19:20:24.712Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3553,756042,1001197,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:23.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'D','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Verarbeitet',90,0,1,1,TO_TIMESTAMP('2025-11-12 19:20:23.829000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:24.773Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:24.838Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001197)
;

-- 2025-11-12T19:20:24.906Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756042
;

-- 2025-11-12T19:20:24.967Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756042)
;

-- 2025-11-12T19:20:25.091Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756042
;

-- 2025-11-12T19:20:25.154Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756042, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2688
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Process Now
-- Column: M_Inventory.Processing
-- 2025-11-12T19:20:26.161Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3554,756043,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:25.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D',0,'Y','N','N','N','N','N','N','N','N','N','Y','Process Now',100,0,1,1,TO_TIMESTAMP('2025-11-12 19:20:25.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:26.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:26.285Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524)
;

-- 2025-11-12T19:20:26.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756043
;

-- 2025-11-12T19:20:26.436Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756043)
;

-- 2025-11-12T19:20:26.572Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756043
;

-- 2025-11-12T19:20:26.635Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756043, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2689
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Generate List
-- Column: M_Inventory.GenerateList
-- 2025-11-12T19:20:27.594Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3817,756044,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:26.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Generate List',23,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Generate List',90,90,1,1,TO_TIMESTAMP('2025-11-12 19:20:26.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:27.656Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:27.719Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1096)
;

-- 2025-11-12T19:20:27.792Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756044
;

-- 2025-11-12T19:20:27.855Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756044)
;

-- 2025-11-12T19:20:27.988Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756044
;

-- 2025-11-12T19:20:28.050Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756044, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2922
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Permanente Stichtagsinventur
-- Column: M_Inventory.M_PerpetualInv_ID
-- 2025-11-12T19:20:28.992Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3818,756045,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:28.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Rules for generating physical inventory',14,'D','The Perpetual Inventory identifies the Perpetual Inventory rule which generated this Physical Inventory.',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Permanente Stichtagsinventur
',70,70,1,1,TO_TIMESTAMP('2025-11-12 19:20:28.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:29.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:29.116Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1109)
;

-- 2025-11-12T19:20:29.182Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756045
;

-- 2025-11-12T19:20:29.244Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756045)
;

-- 2025-11-12T19:20:29.367Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756045
;

-- 2025-11-12T19:20:29.429Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756045, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2923
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Lager
-- Column: M_Inventory.M_Warehouse_ID
-- 2025-11-12T19:20:30.306Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3815,756046,578611,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:29.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager, das zur selben Organisation wie der Inventurbeleg gehört und das eine Zuordnung zur "Inventur"-Belegart hat',14,'@C_DocType_ID@!0','D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lager',60,60,1,1,TO_TIMESTAMP('2025-11-12 19:20:29.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:30.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:30.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578611)
;

-- 2025-11-12T19:20:30.493Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756046
;

-- 2025-11-12T19:20:30.555Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756046)
;

-- 2025-11-12T19:20:30.688Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756046
;

-- 2025-11-12T19:20:30.748Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756046, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2924
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Update Quantities
-- Column: M_Inventory.UpdateQty
-- 2025-11-12T19:20:31.688Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3816,756047,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:30.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,23,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Update Quantities',100,100,1,1,TO_TIMESTAMP('2025-11-12 19:20:30.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:31.748Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:31.809Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1138)
;

-- 2025-11-12T19:20:31.879Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756047
;

-- 2025-11-12T19:20:31.940Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756047)
;

-- 2025-11-12T19:20:32.066Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756047
;

-- 2025-11-12T19:20:32.128Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756047, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2925
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Buchungsstatus
-- Column: M_Inventory.Posted
-- 2025-11-12T19:20:33.071Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6535,756048,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:32.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Buchungsstatus',23,'@Processed/N@=''Y''','D','Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Buchungsstatus',210,210,1,1,TO_TIMESTAMP('2025-11-12 19:20:32.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:33.135Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:33.195Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1308)
;

-- 2025-11-12T19:20:33.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756048
;

-- 2025-11-12T19:20:33.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756048)
;

-- 2025-11-12T19:20:33.442Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756048
;

-- 2025-11-12T19:20:33.504Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756048, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5142
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Nutzer 2
-- Column: M_Inventory.User2_ID
-- 2025-11-12T19:20:34.383Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9587,104,756049,1003048,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:33.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 2',14,'@$Element_U2@=Y','D','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Nutzer 2',160,160,1,1,TO_TIMESTAMP('2025-11-12 19:20:33.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:34.446Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:34.509Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003048)
;

-- 2025-11-12T19:20:34.571Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756049
;

-- 2025-11-12T19:20:34.631Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756049)
;

-- 2025-11-12T19:20:34.763Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756049
;

-- 2025-11-12T19:20:34.825Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756049, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 7811
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Nutzer 1
-- Column: M_Inventory.User1_ID
-- 2025-11-12T19:20:35.751Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9588,104,756050,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:34.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 1',14,'@$Element_U1@=Y','D','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Nutzer 1',150,150,1,1,TO_TIMESTAMP('2025-11-12 19:20:34.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:35.816Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:35.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(613)
;

-- 2025-11-12T19:20:35.956Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756050
;

-- 2025-11-12T19:20:36.017Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756050)
;

-- 2025-11-12T19:20:36.147Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756050
;

-- 2025-11-12T19:20:36.207Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756050, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 7812
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Kostenstelle
-- Column: M_Inventory.C_Activity_ID
-- 2025-11-12T19:20:37.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9589,104,756051,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:36.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kostenstelle',14,'@$Element_AY@=Y','D','Erfassung der zugehörigen Kostenstelle',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Kostenstelle',120,120,1,1,TO_TIMESTAMP('2025-11-12 19:20:36.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:37.495Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:37.555Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005)
;

-- 2025-11-12T19:20:37.622Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756051
;

-- 2025-11-12T19:20:37.684Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756051)
;

-- 2025-11-12T19:20:37.819Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756051
;

-- 2025-11-12T19:20:37.882Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756051, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 7813
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Werbemassnahme
-- Column: M_Inventory.C_Campaign_ID
-- 2025-11-12T19:20:38.809Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9590,104,756052,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:37.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Marketing Campaign',14,'@$Element_MC@=Y','D','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Werbemassnahme',130,130,1,1,TO_TIMESTAMP('2025-11-12 19:20:37.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:38.873Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:38.935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550)
;

-- 2025-11-12T19:20:39.015Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756052
;

-- 2025-11-12T19:20:39.076Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756052)
;

-- 2025-11-12T19:20:39.206Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756052
;

-- 2025-11-12T19:20:39.267Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756052, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 7814
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Buchende Organisation
-- Column: M_Inventory.AD_OrgTrx_ID
-- 2025-11-12T19:20:40.211Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9591,104,756053,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:39.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durchführende oder auslösende Organisation',14,'@$Element_OT@=Y','D','Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Buchende Organisation',140,140,1,1,TO_TIMESTAMP('2025-11-12 19:20:39.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:40.272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:40.335Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(112)
;

-- 2025-11-12T19:20:40.408Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756053
;

-- 2025-11-12T19:20:40.469Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756053)
;

-- 2025-11-12T19:20:40.598Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756053
;

-- 2025-11-12T19:20:40.658Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756053, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 7815
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Projekt
-- Column: M_Inventory.C_Project_ID
-- 2025-11-12T19:20:41.593Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9592,104,756054,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:40.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project',14,'@$Element_PJ@=Y','D','A Project allows you to track and control internal or external activities.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Projekt',110,110,1,1,TO_TIMESTAMP('2025-11-12 19:20:40.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:41.658Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:41.722Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2025-11-12T19:20:41.793Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756054
;

-- 2025-11-12T19:20:41.855Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756054)
;

-- 2025-11-12T19:20:41.979Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756054
;

-- 2025-11-12T19:20:42.042Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756054, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 7816
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Freigabe-Betrag
-- Column: M_Inventory.ApprovalAmt
-- 2025-11-12T19:20:42.987Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12411,756055,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:42.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegfreigabe-Betrag',26,'D','Freigabe-Betrag für den Workflow',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Freigabe-Betrag',180,180,1,1,TO_TIMESTAMP('2025-11-12 19:20:42.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:43.049Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:43.114Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2533)
;

-- 2025-11-12T19:20:43.186Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756055
;

-- 2025-11-12T19:20:43.248Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756055)
;

-- 2025-11-12T19:20:43.372Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756055
;

-- 2025-11-12T19:20:43.433Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756055, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10558
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Bestandsliste verarbeiten
-- Column: M_Inventory.DocAction
-- 2025-11-12T19:20:44.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12412,756056,1001045,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:43.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestandsliste verarbeiten und Bestand aktualisieren',23,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Bestandsliste verarbeiten',200,200,1,1,TO_TIMESTAMP('2025-11-12 19:20:43.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:44.392Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:44.455Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001045)
;

-- 2025-11-12T19:20:44.525Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756056
;

-- 2025-11-12T19:20:44.587Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756056)
;

-- 2025-11-12T19:20:44.712Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756056
;

-- 2025-11-12T19:20:44.774Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756056, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10559
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Status
-- Column: M_Inventory.DocStatus
-- 2025-11-12T19:20:45.621Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12413,756057,1000655,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:44.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document',14,'D','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Status',190,190,1,1,TO_TIMESTAMP('2025-11-12 19:20:44.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:45.684Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:45.748Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000655)
;

-- 2025-11-12T19:20:45.813Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756057
;

-- 2025-11-12T19:20:45.875Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756057)
;

-- 2025-11-12T19:20:46.007Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756057
;

-- 2025-11-12T19:20:46.068Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756057, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10560
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Freigegeben
-- Column: M_Inventory.IsApproved
-- 2025-11-12T19:20:47.006Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12414,101,756058,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:46.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeigt an, ob dieser Beleg eine Freigabe braucht',1,'D','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Freigegeben',170,170,1,1,TO_TIMESTAMP('2025-11-12 19:20:46.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:47.069Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:47.129Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351)
;

-- 2025-11-12T19:20:47.196Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756058
;

-- 2025-11-12T19:20:47.256Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756058)
;

-- 2025-11-12T19:20:47.379Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756058
;

-- 2025-11-12T19:20:47.442Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756058, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10561
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Belegart
-- Column: M_Inventory.C_DocType_ID
-- 2025-11-12T19:20:48.743Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Reference_ID,AD_Tab_ID,AD_Val_Rule_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12797,756059,0,19,548510,540393,0,TO_TIMESTAMP('2025-11-12 19:20:47.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',14,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Belegart',40,40,1,1,TO_TIMESTAMP('2025-11-12 19:20:47.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:48.808Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:48.871Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2025-11-12T19:20:48.944Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756059
;

-- 2025-11-12T19:20:49.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756059)
;

-- 2025-11-12T19:20:49.134Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756059
;

-- 2025-11-12T19:20:49.197Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756059, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10812
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Purchase Order
-- Column: M_Inventory.C_PO_Order_ID
-- 2025-11-12T19:20:50.399Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Tab_ID,AD_Val_Rule_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585929,756061,0,18,290,548510,540235,0,TO_TIMESTAMP('2025-11-12 19:20:49.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@DocSubType@=''ISD'' | @DocSubType@=''IOD''','D',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Purchase Order',220,220,0,1,1,TO_TIMESTAMP('2025-11-12 19:20:49.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:50.462Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:50.526Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582200)
;

-- 2025-11-12T19:20:50.601Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756061
;

-- 2025-11-12T19:20:50.665Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756061)
;

-- 2025-11-12T19:20:50.792Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756061
;

-- 2025-11-12T19:20:50.855Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756061, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712217
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Geschäftspartner
-- Column: M_Inventory.C_BPartner_ID
-- 2025-11-12T19:20:51.840Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585930,756062,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:50.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',0,'@DocSubType@=''ISD'' | @DocSubType@=''IOD''','D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Geschäftspartner',230,220,0,1,1,TO_TIMESTAMP('2025-11-12 19:20:50.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:51.902Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:51.964Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2025-11-12T19:20:52.029Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756062
;

-- 2025-11-12T19:20:52.091Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756062)
;

-- 2025-11-12T19:20:52.215Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756062
;

-- 2025-11-12T19:20:52.275Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756062, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 712218
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Standort
-- Column: M_Inventory.C_BPartner_Location_ID
-- 2025-11-12T19:20:53.244Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586383,756063,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:52.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'@DocSubType@=''ISD'' | @DocSubType@=''IOD''','D','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','N','N','N','N','N','N','N','Y','N','Standort',240,1,1,TO_TIMESTAMP('2025-11-12 19:20:52.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:53.305Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:53.368Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189)
;

-- 2025-11-12T19:20:53.438Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756063
;

-- 2025-11-12T19:20:53.499Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756063)
;

-- 2025-11-12T19:20:53.620Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756063
;

-- 2025-11-12T19:20:53.681Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756063, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 713584
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Kommissionieraufgabe
-- Column: M_Inventory.M_Picking_Job_ID
-- 2025-11-12T19:20:54.678Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587899,756064,0,548510,0,TO_TIMESTAMP('2025-11-12 19:20:53.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'@M_Picking_Job_ID/0@>0','D',0,'Y','N','N','N','N','N','N','N','N','Y','N','Kommissionieraufgabe',250,1,1,TO_TIMESTAMP('2025-11-12 19:20:53.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:54.739Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:54.801Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580042)
;

-- 2025-11-12T19:20:54.876Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756064
;

-- 2025-11-12T19:20:54.937Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756064)
;

-- 2025-11-12T19:20:55.062Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756064
;

-- 2025-11-12T19:20:55.123Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756064, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 725151
;

-- Tab: Inventur Mobile(541969,D) -> Bestandszählungs Position
-- Table: M_InventoryLine
-- 2025-11-12T19:20:56.041Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572624,0,548511,322,541969,'Y',TO_TIMESTAMP('2025-11-12 19:20:55.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestandszählung Position','D','N','The Inventory Count Line defines the counts of the individual products in inventory. Normalerweise werden Sie die Positionen automatisch erstellen wollen. Wenn Sie sie manuell erstellen, stellen Sie sicher, dass der Buchwert korrekt ist. You can set the Organization, if it is a charge, otherwise it is set to the header organization.','A','M_InventoryLine','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N','Bestandszählungs Position','N',20,1,TO_TIMESTAMP('2025-11-12 19:20:55.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:56.102Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548511 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-11-12T19:20:56.165Z
/* DDL */  select update_tab_translation_from_ad_element(572624)
;

-- 2025-11-12T19:20:56.229Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548511)
;

-- 2025-11-12T19:20:56.351Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548511
;

-- 2025-11-12T19:20:56.413Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548511, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 256
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- 2025-11-12T19:20:57.823Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3556,756065,0,548511,0,TO_TIMESTAMP('2025-11-12 19:20:56.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2025-11-12 19:20:56.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:57.887Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:57.948Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-11-12T19:20:58.105Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756065
;

-- 2025-11-12T19:20:58.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756065)
;

-- 2025-11-12T19:20:58.293Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756065
;

-- 2025-11-12T19:20:58.354Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756065, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2690
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2025-11-12T19:20:59.283Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3557,756066,0,548511,0,TO_TIMESTAMP('2025-11-12 19:20:58.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2025-11-12 19:20:58.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:20:59.345Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:20:59.411Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-11-12T19:20:59.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756066
;

-- 2025-11-12T19:20:59.636Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756066)
;

-- 2025-11-12T19:20:59.761Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756066
;

-- 2025-11-12T19:20:59.824Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756066, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2691
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Beschreibung
-- Column: M_InventoryLine.Description
-- 2025-11-12T19:21:00.765Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3568,756067,0,548511,0,TO_TIMESTAMP('2025-11-12 19:20:59.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Beschreibung',120,100,999,1,TO_TIMESTAMP('2025-11-12 19:20:59.888000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:00.825Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:00.889Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-11-12T19:21:00.988Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756067
;

-- 2025-11-12T19:21:01.050Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756067)
;

-- 2025-11-12T19:21:01.175Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756067
;

-- 2025-11-12T19:21:01.237Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756067, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2692
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Aktiv
-- Column: M_InventoryLine.IsActive
-- 2025-11-12T19:21:02.243Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3558,756068,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:01.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',130,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:01.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:02.306Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:02.368Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-11-12T19:21:02.528Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756068
;

-- 2025-11-12T19:21:02.590Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756068)
;

-- 2025-11-12T19:21:02.715Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756068
;

-- 2025-11-12T19:21:02.777Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756068, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2693
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- 2025-11-12T19:21:03.648Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3563,756069,1001030,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:02.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Parameter für eine physische Inventur',14,'D','Bezeichnet die eindeutigen Parameter für eine physische Inventur',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Inventur',30,30,1,1,TO_TIMESTAMP('2025-11-12 19:21:02.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:03.711Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:03.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001030)
;

-- 2025-11-12T19:21:03.842Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756069
;

-- 2025-11-12T19:21:03.903Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756069)
;

-- 2025-11-12T19:21:04.028Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756069
;

-- 2025-11-12T19:21:04.090Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756069, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2694
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Inventur-Position
-- Column: M_InventoryLine.M_InventoryLine_ID
-- 2025-11-12T19:21:05.081Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3555,756070,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:04.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Eindeutige Position in einem Inventurdokument',14,'D','"Inventur-Position" bezeichnet die Poition in dem Inventurdokument (wenn zutreffend) für diese Transaktion.',0,'Y','N','N','N','N','N','N','N','N','N','N','Inventur-Position',140,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:04.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:05.144Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:05.206Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1028)
;

-- 2025-11-12T19:21:05.278Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756070
;

-- 2025-11-12T19:21:05.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756070)
;

-- 2025-11-12T19:21:05.467Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756070
;

-- 2025-11-12T19:21:05.530Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756070, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2695
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2025-11-12T19:21:06.459Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3564,756071,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:05.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort im Lager',14,'D','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lagerort',50,50,1,1,TO_TIMESTAMP('2025-11-12 19:21:05.592000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:06.520Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:06.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448)
;

-- 2025-11-12T19:21:06.653Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756071
;

-- 2025-11-12T19:21:06.715Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756071)
;

-- 2025-11-12T19:21:06.843Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756071
;

-- 2025-11-12T19:21:06.907Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756071, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2696
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2025-11-12T19:21:07.847Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3565,756072,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:06.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',26,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produkt',60,60,1,1,TO_TIMESTAMP('2025-11-12 19:21:06.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:07.910Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:07.972Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2025-11-12T19:21:08.062Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756072
;

-- 2025-11-12T19:21:08.123Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756072)
;

-- 2025-11-12T19:21:08.247Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756072
;

-- 2025-11-12T19:21:08.306Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756072, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2697
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2025-11-12T19:21:09.176Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3566,756073,1002630,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:08.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Buchmenge',26,'D','"Buchmenge" zeigt die im System gespeicherte Menge für ein Produkt im Bestand an.',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Buchmenge',110,90,1,1,TO_TIMESTAMP('2025-11-12 19:21:08.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:09.241Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:09.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002630)
;

-- 2025-11-12T19:21:09.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756073
;

-- 2025-11-12T19:21:09.437Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756073)
;

-- 2025-11-12T19:21:09.572Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756073
;

-- 2025-11-12T19:21:09.633Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756073, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2698
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2025-11-12T19:21:10.501Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3567,756074,1003107,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:09.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gezählte Menge',26,'D','"Zählmenge" zeigt die tatsächlich ermittelte Menge für ein Produkt im Bestand an.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Zählmenge',100,80,1,1,TO_TIMESTAMP('2025-11-12 19:21:09.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:10.564Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:10.625Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003107)
;

-- 2025-11-12T19:21:10.690Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756074
;

-- 2025-11-12T19:21:10.750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756074)
;

-- 2025-11-12T19:21:10.874Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756074
;

-- 2025-11-12T19:21:10.937Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756074, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2699
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Zeile Nr.
-- Column: M_InventoryLine.Line
-- 2025-11-12T19:21:11.816Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3819,756075,1001228,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:11.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einzelne Zeile in dem Dokument',11,'D','Indicates the unique line for a document.  It will also control the display order of the lines within a document.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Zeile Nr.',40,40,1,1,1,TO_TIMESTAMP('2025-11-12 19:21:11.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:11.878Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:11.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001228)
;

-- 2025-11-12T19:21:12.005Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756075
;

-- 2025-11-12T19:21:12.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756075)
;

-- 2025-11-12T19:21:12.197Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756075
;

-- 2025-11-12T19:21:12.258Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756075, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2926
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2025-11-12T19:21:13.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8550,756076,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:12.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',26,'D','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Merkmale',80,70,1,1,TO_TIMESTAMP('2025-11-12 19:21:12.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:13.241Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:13.306Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2025-11-12T19:21:13.377Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756076
;

-- 2025-11-12T19:21:13.437Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756076)
;

-- 2025-11-12T19:21:13.561Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756076
;

-- 2025-11-12T19:21:13.621Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756076, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6555
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Kosten
-- Column: M_InventoryLine.C_Charge_ID
-- 2025-11-12T19:21:14.479Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9950,756077,1001991,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:13.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zusätzliche Kosten',14,'@InventoryType@=C','D','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Kosten',140,120,1,1,TO_TIMESTAMP('2025-11-12 19:21:13.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:14.542Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:14.604Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001991)
;

-- 2025-11-12T19:21:14.673Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756077
;

-- 2025-11-12T19:21:14.734Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756077)
;

-- 2025-11-12T19:21:14.866Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756077
;

-- 2025-11-12T19:21:14.928Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756077, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 8289
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Inventur-Art
-- Column: M_InventoryLine.InventoryType
-- 2025-11-12T19:21:15.863Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9951,756078,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:14.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',14,'D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Inventur-Art',130,110,1,1,TO_TIMESTAMP('2025-11-12 19:21:14.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:15.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:15.987Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2202)
;

-- 2025-11-12T19:21:16.054Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756078
;

-- 2025-11-12T19:21:16.117Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756078)
;

-- 2025-11-12T19:21:16.238Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756078
;

-- 2025-11-12T19:21:16.302Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756078, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 8290
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Verarbeitet
-- Column: M_InventoryLine.Processed
-- 2025-11-12T19:21:17.378Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12071,756079,1002830,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:16.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'D','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Verarbeitet',150,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:16.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:17.441Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:17.504Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002830)
;

-- 2025-11-12T19:21:17.576Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756079
;

-- 2025-11-12T19:21:17.642Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756079)
;

-- 2025-11-12T19:21:17.767Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756079
;

-- 2025-11-12T19:21:17.827Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756079, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10336
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Internal Use Qty
-- Column: M_InventoryLine.QtyInternalUse
-- 2025-11-12T19:21:18.755Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13023,756080,1000718,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:17.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Internal Use Quantity removed from Inventory',26,'D','Quantity of product inventory used internally (positive if taken out - negative if returned)',0,'Y','N','N','N','N','N','N','N','N','N','N','Internal Use Qty',160,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:17.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:18.816Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:18.877Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000718)
;

-- 2025-11-12T19:21:18.944Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756080
;

-- 2025-11-12T19:21:19.005Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756080)
;

-- 2025-11-12T19:21:19.127Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756080
;

-- 2025-11-12T19:21:19.191Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756080, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10999
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> UPC
-- Column: M_InventoryLine.UPC
-- 2025-11-12T19:21:20.226Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14102,756081,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:19.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)',30,'D','Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.',0,'Y','N','N','N','N','N','N','N','N','N','N','UPC',170,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:19.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:20.290Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:20.358Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(603)
;

-- 2025-11-12T19:21:20.423Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756081
;

-- 2025-11-12T19:21:20.491Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756081)
;

-- 2025-11-12T19:21:20.625Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756081
;

-- 2025-11-12T19:21:20.691Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756081, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12143
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Suchschlüssel
-- Column: M_InventoryLine.Value
-- 2025-11-12T19:21:22.006Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14101,756082,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:20.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','N','N','N','N','N','N','N','N','N','Suchschlüssel',180,0,999,1,TO_TIMESTAMP('2025-11-12 19:21:20.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:22.070Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:22.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2025-11-12T19:21:22.214Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756082
;

-- 2025-11-12T19:21:22.276Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756082)
;

-- 2025-11-12T19:21:22.412Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756082
;

-- 2025-11-12T19:21:22.472Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756082, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12144
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2025-11-12T19:21:23.337Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559545,756083,1002227,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:22.535000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'@HUAggregationType/''S''@=''S''','D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Handling Units',170,1,1,TO_TIMESTAMP('2025-11-12 19:21:22.535000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:23.399Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:23.462Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002227)
;

-- 2025-11-12T19:21:23.535Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756083
;

-- 2025-11-12T19:21:23.596Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756083)
;

-- 2025-11-12T19:21:23.723Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756083
;

-- 2025-11-12T19:21:23.783Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756083, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563119
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- 2025-11-12T19:21:24.709Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556506,756084,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:23.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'@HUAggregationType/''S''@=''S''','D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Packvorschrift',190,1,1,TO_TIMESTAMP('2025-11-12 19:21:23.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:24.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:24.835Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132)
;

-- 2025-11-12T19:21:24.904Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756084
;

-- 2025-11-12T19:21:24.966Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756084)
;

-- 2025-11-12T19:21:25.088Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756084
;

-- 2025-11-12T19:21:25.148Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756084, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563120
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2025-11-12T19:21:26.076Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560761,756085,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:25.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sagt aus, ob das entsprechende Item as inventarisiert/gezählt gilt.',0,'D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Gezählt',150,130,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:25.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:26.138Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:26.204Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1835)
;

-- 2025-11-12T19:21:26.267Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756085
;

-- 2025-11-12T19:21:26.328Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756085)
;

-- 2025-11-12T19:21:26.458Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756085
;

-- 2025-11-12T19:21:26.521Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756085, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565595
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2025-11-12T19:21:27.450Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560762,756086,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:26.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugewiesen an',160,140,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:26.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:27.513Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:27.574Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544205)
;

-- 2025-11-12T19:21:27.644Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756086
;

-- 2025-11-12T19:21:27.706Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756086)
;

-- 2025-11-12T19:21:27.831Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756086
;

-- 2025-11-12T19:21:27.893Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756086, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565596
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Maßeinheit
-- Column: M_InventoryLine.C_UOM_ID
-- 2025-11-12T19:21:28.810Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556504,756087,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:27.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',26,'D','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Maßeinheit',70,150,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:27.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:28.880Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:28.942Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2025-11-12T19:21:29.017Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756087
;

-- 2025-11-12T19:21:29.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756087)
;

-- 2025-11-12T19:21:29.203Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756087
;

-- 2025-11-12T19:21:29.262Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756087, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565597
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> HU Aggregation
-- Column: M_InventoryLine.HUAggregationType
-- 2025-11-12T19:21:30.180Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,567654,756088,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:29.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sagt aus, ob sich die betreffende Position immer auf eine einzelne HU oder potentiell auch auch mehrere HUs bezieht.',1,'D',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','HU Aggregation',180,1,1,TO_TIMESTAMP('2025-11-12 19:21:29.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:30.245Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:30.307Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576580)
;

-- 2025-11-12T19:21:30.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756088
;

-- 2025-11-12T19:21:30.441Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756088)
;

-- 2025-11-12T19:21:30.563Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756088
;

-- 2025-11-12T19:21:30.626Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756088, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 580077
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Kostenpreis
-- Column: M_InventoryLine.CostPrice
-- 2025-11-12T19:21:31.570Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569690,756089,0,548511,10,TO_TIMESTAMP('2025-11-12 19:21:30.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','Y','N','N','N','N','N','N','N','N','Kostenpreis',90,160,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:30.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:31.643Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:31.706Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577400)
;

-- 2025-11-12T19:21:31.782Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756089
;

-- 2025-11-12T19:21:31.842Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756089)
;

-- 2025-11-12T19:21:31.964Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756089
;

-- 2025-11-12T19:21:32.024Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756089, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593143
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Kostenpreis überschreiben
-- Column: M_InventoryLine.IsExplicitCostPrice
-- 2025-11-12T19:21:33.040Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582463,756090,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:32.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Kostenpreis überschreiben',200,170,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:32.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:33.103Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:33.168Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580714)
;

-- 2025-11-12T19:21:33.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756090
;

-- 2025-11-12T19:21:33.294Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756090)
;

-- 2025-11-12T19:21:33.416Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756090
;

-- 2025-11-12T19:21:33.481Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756090, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 691414
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2025-11-12T19:21:34.453Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588189,756091,0,548511,0,TO_TIMESTAMP('2025-11-12 19:21:33.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.',9999999,'@HUAggregationType/''S''@=''S'' & @RenderedQRCode@!''''','D',0,'Y','N','N','N','N','N','N','N','N','Y','N','Rendered QR Code',210,1,1,TO_TIMESTAMP('2025-11-12 19:21:33.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:34.515Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:34.578Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580597)
;

-- 2025-11-12T19:21:34.645Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756091
;

-- 2025-11-12T19:21:34.711Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756091)
;

-- 2025-11-12T19:21:34.835Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756091
;

-- 2025-11-12T19:21:34.897Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756091, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 728051
;

-- Tab: Inventur Mobile(541969,D) -> Merkmale
-- Table: M_InventoryLineMA
-- 2025-11-12T19:21:35.839Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573304,0,548512,763,541969,'Y',TO_TIMESTAMP('2025-11-12 19:21:34.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Product Instance Attribute Material Allocation','D','N','N','A','M_InventoryLineMA','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Merkmale','N',30,2,TO_TIMESTAMP('2025-11-12 19:21:34.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:35.903Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548512 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-11-12T19:21:35.965Z
/* DDL */  select update_tab_translation_from_ad_element(573304)
;

-- 2025-11-12T19:21:36.031Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548512)
;

-- 2025-11-12T19:21:36.154Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548512
;

-- 2025-11-12T19:21:36.215Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548512, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 749
;

-- Field: Inventur Mobile(541969,D) -> Merkmale(548512,D) -> Aktiv
-- Column: M_InventoryLineMA.IsActive
-- 2025-11-12T19:21:38.453Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13358,756092,0,548512,0,TO_TIMESTAMP('2025-11-12 19:21:36.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',10,0,1,1,TO_TIMESTAMP('2025-11-12 19:21:36.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:38.515Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:38.577Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-11-12T19:21:38.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756092
;

-- 2025-11-12T19:21:38.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756092)
;

-- 2025-11-12T19:21:38.899Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756092
;

-- 2025-11-12T19:21:38.961Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756092, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12181
;

-- Field: Inventur Mobile(541969,D) -> Merkmale(548512,D) -> Merkmale
-- Column: M_InventoryLineMA.M_AttributeSetInstance_ID
-- 2025-11-12T19:21:39.888Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13355,756093,0,548512,0,TO_TIMESTAMP('2025-11-12 19:21:39.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',22,'D','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Merkmale',40,40,1,1,TO_TIMESTAMP('2025-11-12 19:21:39.022000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:39.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:40.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2025-11-12T19:21:40.094Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756093
;

-- 2025-11-12T19:21:40.157Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756093)
;

-- 2025-11-12T19:21:40.281Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756093
;

-- 2025-11-12T19:21:40.340Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756093, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12182
;

-- Field: Inventur Mobile(541969,D) -> Merkmale(548512,D) -> Mandant
-- Column: M_InventoryLineMA.AD_Client_ID
-- 2025-11-12T19:21:41.320Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13356,756094,0,548512,0,TO_TIMESTAMP('2025-11-12 19:21:40.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2025-11-12 19:21:40.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:41.382Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:41.444Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-11-12T19:21:41.599Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756094
;

-- 2025-11-12T19:21:41.675Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756094)
;

-- 2025-11-12T19:21:41.802Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756094
;

-- 2025-11-12T19:21:41.875Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756094, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12183
;

-- Field: Inventur Mobile(541969,D) -> Merkmale(548512,D) -> Bewegungs-Menge
-- Column: M_InventoryLineMA.MovementQty
-- 2025-11-12T19:21:42.730Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13363,756095,1001402,0,548512,0,TO_TIMESTAMP('2025-11-12 19:21:41.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge eines bewegten Produktes.',22,'D','Die "Bewegungs-Menge" bezeichnet die Menge einer Ware, die bewegt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Bewegungs-Menge',50,50,1,1,TO_TIMESTAMP('2025-11-12 19:21:41.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:42.792Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:42.863Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001402)
;

-- 2025-11-12T19:21:42.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756095
;

-- 2025-11-12T19:21:42.988Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756095)
;

-- 2025-11-12T19:21:43.112Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756095
;

-- 2025-11-12T19:21:43.174Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756095, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12184
;

-- Field: Inventur Mobile(541969,D) -> Merkmale(548512,D) -> Sektion
-- Column: M_InventoryLineMA.AD_Org_ID
-- 2025-11-12T19:21:44.094Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13357,756096,0,548512,0,TO_TIMESTAMP('2025-11-12 19:21:43.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2025-11-12 19:21:43.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:44.155Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:44.217Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-11-12T19:21:44.366Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756096
;

-- 2025-11-12T19:21:44.427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756096)
;

-- 2025-11-12T19:21:44.552Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756096
;

-- 2025-11-12T19:21:44.614Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756096, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12185
;

-- Field: Inventur Mobile(541969,D) -> Merkmale(548512,D) -> Inventur-Position
-- Column: M_InventoryLineMA.M_InventoryLine_ID
-- 2025-11-12T19:21:45.543Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13354,756097,0,548512,0,TO_TIMESTAMP('2025-11-12 19:21:44.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Eindeutige Position in einem Inventurdokument',22,'D','"Inventur-Position" bezeichnet die Poition in dem Inventurdokument (wenn zutreffend) für diese Transaktion.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Inventur-Position',30,30,1,1,TO_TIMESTAMP('2025-11-12 19:21:44.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:45.604Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:45.665Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1028)
;

-- 2025-11-12T19:21:45.733Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756097
;

-- 2025-11-12T19:21:45.793Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756097)
;

-- 2025-11-12T19:21:45.918Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756097
;

-- 2025-11-12T19:21:45.980Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756097, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12186
;

-- Field: Inventur Mobile(541969,D) -> Merkmale(548512,D) -> M_InventoryLineMA
-- Column: M_InventoryLineMA.M_InventoryLineMA_ID
-- 2025-11-12T19:21:46.972Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555404,756098,0,548512,0,TO_TIMESTAMP('2025-11-12 19:21:46.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','M_InventoryLineMA',60,1,1,TO_TIMESTAMP('2025-11-12 19:21:46.042000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:47.034Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:21:47.096Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543224)
;

-- 2025-11-12T19:21:47.164Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756098
;

-- 2025-11-12T19:21:47.225Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756098)
;

-- 2025-11-12T19:21:47.347Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 756098
;

-- 2025-11-12T19:21:47.407Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 756098, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 557391
;

-- Tab: Inventur Mobile(541969,D) -> Bestandszählung(548510,D)
-- UI Section: main
-- 2025-11-12T19:21:48.172Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548510,547042,TO_TIMESTAMP('2025-11-12 19:21:47.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-11-12 19:21:47.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-11-12T19:21:48.234Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547042 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-11-12T19:21:48.359Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547042
;

-- 2025-11-12T19:21:48.427Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547042, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540679
;

-- UI Section: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main
-- UI Column: 10
-- 2025-11-12T19:21:49.249Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548593,547042,TO_TIMESTAMP('2025-11-12 19:21:48.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-11-12 19:21:48.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10
-- UI Element Group: default
-- 2025-11-12T19:21:50.080Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548593,553755,TO_TIMESTAMP('2025-11-12 19:21:49.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-11-12 19:21:49.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Inventory.C_BPartner_ID
-- 2025-11-12T19:21:51.374Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756062,0,548510,553755,638632,'F',TO_TIMESTAMP('2025-11-12 19:21:50.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',50,0,0,TO_TIMESTAMP('2025-11-12 19:21:50.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:21:52.577Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,756063,0,542179,638632,TO_TIMESTAMP('2025-11-12 19:21:51.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-11-12 19:21:51.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Lager
-- Column: M_Inventory.M_Warehouse_ID
-- 2025-11-12T19:21:53.588Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756046,0,548510,553755,638633,'F',TO_TIMESTAMP('2025-11-12 19:21:52.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','Y','N','N','Lager',60,40,0,TO_TIMESTAMP('2025-11-12 19:21:52.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Permanente Stichtagsinventur
-- Column: M_Inventory.M_PerpetualInv_ID
-- 2025-11-12T19:21:55.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756045,0,548510,553755,638634,'F',TO_TIMESTAMP('2025-11-12 19:21:53.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Rules for generating physical inventory','The Perpetual Inventory identifies the Perpetual Inventory rule which generated this Physical Inventory.','Y','N','N','Y','N','N','N','Permanente Stichtagsinventur
',70,0,0,TO_TIMESTAMP('2025-11-12 19:21:53.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Projekt
-- Column: M_Inventory.C_Project_ID
-- 2025-11-12T19:21:57.413Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756054,0,548510,553755,638635,'F',TO_TIMESTAMP('2025-11-12 19:21:55.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Finanzprojekt','Ein Projekt erlaubt, interne oder externe Vorgäng zu verfolgen und zu kontrollieren.','Y','N','N','Y','N','N','N','Projekt',110,0,0,TO_TIMESTAMP('2025-11-12 19:21:55.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Kostenstelle
-- Column: M_Inventory.C_Activity_ID
-- 2025-11-12T19:21:58.549Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756051,0,548510,553755,638636,'F',TO_TIMESTAMP('2025-11-12 19:21:57.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','Y','Y','N','Kostenstelle',120,60,30,TO_TIMESTAMP('2025-11-12 19:21:57.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Werbemassnahme
-- Column: M_Inventory.C_Campaign_ID
-- 2025-11-12T19:21:59.665Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756052,0,548510,553755,638637,'F',TO_TIMESTAMP('2025-11-12 19:21:58.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Marketing Campaign','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','N','N','Y','N','N','N','Werbemassnahme',130,0,0,TO_TIMESTAMP('2025-11-12 19:21:58.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Buchende Organisation
-- Column: M_Inventory.AD_OrgTrx_ID
-- 2025-11-12T19:22:00.802Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756053,0,548510,553755,638638,'F',TO_TIMESTAMP('2025-11-12 19:21:59.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durchführende oder auslösende Organisation','Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.','Y','N','N','Y','N','N','N','Buchende Organisation',140,0,0,TO_TIMESTAMP('2025-11-12 19:21:59.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Nutzer 1
-- Column: M_Inventory.User1_ID
-- 2025-11-12T19:22:01.941Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756050,0,548510,553755,638639,'F',TO_TIMESTAMP('2025-11-12 19:22:01.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 1','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','N','N','Y','N','N','N','Nutzer 1',150,0,0,TO_TIMESTAMP('2025-11-12 19:22:01.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Nutzer 2
-- Column: M_Inventory.User2_ID
-- 2025-11-12T19:22:03.003Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756049,0,548510,553755,638640,'F',TO_TIMESTAMP('2025-11-12 19:22:02.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 2','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','N','N','Y','N','N','N','Nutzer 2',160,0,0,TO_TIMESTAMP('2025-11-12 19:22:02.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Belegstatus
-- Column: M_Inventory.DocStatus
-- 2025-11-12T19:22:04.059Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756057,0,548510,553755,638641,'F',TO_TIMESTAMP('2025-11-12 19:22:03.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','Y','N','N','Belegstatus',190,90,0,TO_TIMESTAMP('2025-11-12 19:22:03.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Bestandsliste verarbeiten
-- Column: M_Inventory.DocAction
-- 2025-11-12T19:22:05.131Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756056,0,548510,553755,638642,'F',TO_TIMESTAMP('2025-11-12 19:22:04.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestandsliste verarbeiten und Bestand aktualisieren','Y','N','N','Y','N','N','N','Bestandsliste verarbeiten',200,0,0,TO_TIMESTAMP('2025-11-12 19:22:04.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10
-- UI Element Group: referenced
-- 2025-11-12T19:22:05.924Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548593,553756,TO_TIMESTAMP('2025-11-12 19:22:05.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','referenced',15,TO_TIMESTAMP('2025-11-12 19:22:05.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> referenced.Picking Job
-- Column: M_Inventory.M_Picking_Job_ID
-- 2025-11-12T19:22:07.113Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756064,0,548510,553756,638643,'F',TO_TIMESTAMP('2025-11-12 19:22:06.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Picking Job',10,0,0,TO_TIMESTAMP('2025-11-12 19:22:06.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10
-- UI Element Group: description
-- 2025-11-12T19:22:07.924Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548593,553757,TO_TIMESTAMP('2025-11-12 19:22:07.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','description',20,TO_TIMESTAMP('2025-11-12 19:22:07.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Freigegeben
-- Column: M_Inventory.IsApproved
-- 2025-11-12T19:22:09.145Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756058,0,548510,553757,638644,'F',TO_TIMESTAMP('2025-11-12 19:22:08.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeigt an, ob dieser Beleg eine Freigabe braucht','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','N','N','Y','Y','N','N','Freigegeben',10,70,0,TO_TIMESTAMP('2025-11-12 19:22:08.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Freigabe-Betrag
-- Column: M_Inventory.ApprovalAmt
-- 2025-11-12T19:22:10.225Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756055,0,548510,553757,638645,'F',TO_TIMESTAMP('2025-11-12 19:22:09.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegfreigabe-Betrag','Freigabe-Betrag für den Workflow','Y','N','N','Y','Y','N','N','Freigabe-Betrag',20,80,0,TO_TIMESTAMP('2025-11-12 19:22:09.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Beschreibung
-- Column: M_Inventory.Description
-- 2025-11-12T19:22:11.288Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756037,0,548510,553757,638646,'F',TO_TIMESTAMP('2025-11-12 19:22:10.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Beschreibung',30,50,0,TO_TIMESTAMP('2025-11-12 19:22:10.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main
-- UI Column: 20
-- 2025-11-12T19:22:12.110Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548594,547042,TO_TIMESTAMP('2025-11-12 19:22:11.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-11-12 19:22:11.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20
-- UI Element Group: document
-- 2025-11-12T19:22:12.929Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548594,553758,TO_TIMESTAMP('2025-11-12 19:22:12.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','document',10,TO_TIMESTAMP('2025-11-12 19:22:12.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> document.Belegart
-- Column: M_Inventory.C_DocType_ID
-- 2025-11-12T19:22:14.133Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756059,0,548510,553758,638647,'F',TO_TIMESTAMP('2025-11-12 19:22:13.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','Y','Y','N','Belegart',10,10,10,TO_TIMESTAMP('2025-11-12 19:22:13.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> document.Nr.
-- Column: M_Inventory.DocumentNo
-- 2025-11-12T19:22:15.203Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,756041,0,548510,553758,638648,'F',TO_TIMESTAMP('2025-11-12 19:22:14.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','Y','N','N','Nr.',20,20,0,TO_TIMESTAMP('2025-11-12 19:22:14.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> document.Bewegungsdatum
-- Column: M_Inventory.MovementDate
-- 2025-11-12T19:22:16.271Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,756040,0,548510,553758,638649,'F',TO_TIMESTAMP('2025-11-12 19:22:15.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewqegung.','Y','N','N','Y','Y','Y','N','Bewegungsdatum',30,30,20,TO_TIMESTAMP('2025-11-12 19:22:15.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> document.PO No.
-- Column: M_Inventory.C_PO_Order_ID
-- 2025-11-12T19:22:17.331Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756061,0,548510,553758,638650,'F',TO_TIMESTAMP('2025-11-12 19:22:16.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Purchase order number','Y','N','N','Y','N','N','N',0,'PO No.',40,0,0,TO_TIMESTAMP('2025-11-12 19:22:16.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20
-- UI Element Group: posted
-- 2025-11-12T19:22:18.144Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548594,553759,TO_TIMESTAMP('2025-11-12 19:22:17.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','posted',20,TO_TIMESTAMP('2025-11-12 19:22:17.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> posted.Verbucht
-- Column: M_Inventory.Posted
-- 2025-11-12T19:22:19.369Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756048,0,548510,553759,638651,'F',TO_TIMESTAMP('2025-11-12 19:22:18.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Posting status','The Posted field indicates the status of the Generation of General Ledger Accounting Lines ','Y','N','N','Y','Y','N','N','Verbucht',10,100,0,TO_TIMESTAMP('2025-11-12 19:22:18.413000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20
-- UI Element Group: org
-- 2025-11-12T19:22:20.184Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548594,553760,TO_TIMESTAMP('2025-11-12 19:22:19.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',30,TO_TIMESTAMP('2025-11-12 19:22:19.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> org.Sektion
-- Column: M_Inventory.AD_Org_ID
-- 2025-11-12T19:22:21.401Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,756036,0,548510,553760,638652,'F',TO_TIMESTAMP('2025-11-12 19:22:20.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','Y','N','Sektion',10,110,40,TO_TIMESTAMP('2025-11-12 19:22:20.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> org.Mandant
-- Column: M_Inventory.AD_Client_ID
-- 2025-11-12T19:22:22.471Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,756035,0,548510,553760,638653,'F',TO_TIMESTAMP('2025-11-12 19:22:21.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2025-11-12 19:22:21.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Tab: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D)
-- UI Section: main
-- 2025-11-12T19:22:23.410Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548511,547043,TO_TIMESTAMP('2025-11-12 19:22:22.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-11-12 19:22:22.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-11-12T19:22:23.472Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547043 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-11-12T19:22:23.596Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547043
;

-- 2025-11-12T19:22:23.658Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547043, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540680
;

-- UI Section: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main
-- UI Column: 10
-- 2025-11-12T19:22:24.424Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548595,547043,TO_TIMESTAMP('2025-11-12 19:22:23.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-11-12 19:22:23.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10
-- UI Element Group: default
-- 2025-11-12T19:22:25.320Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548595,553761,TO_TIMESTAMP('2025-11-12 19:22:24.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-11-12 19:22:24.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- 2025-11-12T19:22:26.608Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756069,0,548511,553761,638654,'F',TO_TIMESTAMP('2025-11-12 19:22:25.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Parameter für eine physische Inventur','Bezeichnet die eindeutigen Parameter für eine physische Inventur','Y','N','N','N','N','N','N','Inventur',10,0,0,TO_TIMESTAMP('2025-11-12 19:22:25.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Kosten
-- Column: M_InventoryLine.C_Charge_ID
-- 2025-11-12T19:22:27.835Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756077,0,548511,553761,638655,'F',TO_TIMESTAMP('2025-11-12 19:22:26.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zusätzliche Kosten','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)','Y','N','N','N','N','N','N','Kosten',20,0,0,TO_TIMESTAMP('2025-11-12 19:22:26.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Zeile Nr.
-- Column: M_InventoryLine.Line
-- 2025-11-12T19:22:28.917Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,756075,0,548511,553761,638656,'F',TO_TIMESTAMP('2025-11-12 19:22:28.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','Y','Y','N','N','Zeile Nr.',10,10,0,TO_TIMESTAMP('2025-11-12 19:22:28.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2025-11-12T19:22:30.002Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,756072,0,548511,553761,638657,'F',TO_TIMESTAMP('2025-11-12 19:22:29.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','Y','N','N','Produkt',20,20,0,TO_TIMESTAMP('2025-11-12 19:22:29.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Maßeinheit
-- Column: M_InventoryLine.C_UOM_ID
-- 2025-11-12T19:22:31.071Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756087,0,548511,553761,638658,'F',TO_TIMESTAMP('2025-11-12 19:22:30.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Maßeinheit',25,30,0,TO_TIMESTAMP('2025-11-12 19:22:30.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2025-11-12T19:22:32.167Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756076,0,548511,553761,638659,'F',TO_TIMESTAMP('2025-11-12 19:22:31.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','Y','N','N','Merkmale',30,40,0,TO_TIMESTAMP('2025-11-12 19:22:31.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2025-11-12T19:22:33.232Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756071,0,548511,553761,638660,'F',TO_TIMESTAMP('2025-11-12 19:22:32.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','Y','Y','N','N','Lagerort',40,50,0,TO_TIMESTAMP('2025-11-12 19:22:32.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.IsExplicitCostPrice
-- Column: M_InventoryLine.IsExplicitCostPrice
-- 2025-11-12T19:22:34.308Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756090,0,548511,553761,638661,'F',TO_TIMESTAMP('2025-11-12 19:22:33.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'IsExplicitCostPrice',43,0,0,TO_TIMESTAMP('2025-11-12 19:22:33.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Cost Price
-- Column: M_InventoryLine.CostPrice
-- 2025-11-12T19:22:35.359Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756089,0,548511,553761,638662,'F',TO_TIMESTAMP('2025-11-12 19:22:34.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Cost Price',45,0,0,TO_TIMESTAMP('2025-11-12 19:22:34.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2025-11-12T19:22:36.422Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756073,0,548511,553761,638663,'F',TO_TIMESTAMP('2025-11-12 19:22:35.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Buchmenge','"Buchmenge" zeigt die im System gespeicherte Menge für ein Produkt im Bestand an.','Y','N','N','Y','Y','N','N','Buchmenge',50,60,0,TO_TIMESTAMP('2025-11-12 19:22:35.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2025-11-12T19:22:37.499Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756074,0,548511,553761,638664,'F',TO_TIMESTAMP('2025-11-12 19:22:36.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gezählte Menge','"Zählmenge" zeigt die tatsächlich ermittelte Menge für ein Produkt im Bestand an.','Y','N','N','Y','Y','N','N','Zählmenge',60,70,0,TO_TIMESTAMP('2025-11-12 19:22:36.668000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- 2025-11-12T19:22:38.888Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756084,0,548511,553761,638665,'F',TO_TIMESTAMP('2025-11-12 19:22:37.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Packvorschrift',70,0,0,TO_TIMESTAMP('2025-11-12 19:22:37.748000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Handling Unit
-- Column: M_InventoryLine.M_HU_ID
-- 2025-11-12T19:22:40.029Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756083,0,548511,553761,638666,'F',TO_TIMESTAMP('2025-11-12 19:22:39.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Handling Unit',80,80,0,TO_TIMESTAMP('2025-11-12 19:22:39.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Rendered QR Code
-- Column: M_InventoryLine.RenderedQRCode
-- 2025-11-12T19:22:41.189Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756091,0,548511,553761,638667,'F',TO_TIMESTAMP('2025-11-12 19:22:40.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'It''s the QR code which is directly incorporated in the QR code image. Nothing more, nothing less.','Y','Y','N','Y','N','N','N','Rendered QR Code',85,0,0,TO_TIMESTAMP('2025-11-12 19:22:40.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.Description
-- 2025-11-12T19:22:42.324Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756067,0,548511,553761,638668,'F',TO_TIMESTAMP('2025-11-12 19:22:41.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Beschreibung',90,90,0,TO_TIMESTAMP('2025-11-12 19:22:41.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Inventory Type
-- Column: M_InventoryLine.InventoryType
-- 2025-11-12T19:22:43.393Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756078,0,548511,553761,638669,'F',TO_TIMESTAMP('2025-11-12 19:22:42.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Type of inventory difference','The type of inventory difference determines which account is used. The default is the Inventory Difference account defined for the warehouse.  Alternatively, you could select any charge.  This allows you to account for Internal Use or extraordinary inventory losses.','Y','N','N','Y','Y','N','N','Inventory Type',100,100,0,TO_TIMESTAMP('2025-11-12 19:22:42.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2025-11-12T19:22:44.503Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,756066,0,548511,553761,638670,'F',TO_TIMESTAMP('2025-11-12 19:22:43.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Sektion',110,110,0,TO_TIMESTAMP('2025-11-12 19:22:43.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- 2025-11-12T19:22:45.592Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,756065,0,548511,553761,638671,'F',TO_TIMESTAMP('2025-11-12 19:22:44.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',120,0,0,TO_TIMESTAMP('2025-11-12 19:22:44.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2025-11-12T19:22:46.737Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756085,0,548511,553761,638672,'F',TO_TIMESTAMP('2025-11-12 19:22:45.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Gezählt',130,0,0,TO_TIMESTAMP('2025-11-12 19:22:45.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2025-11-12T19:22:47.851Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756086,0,548511,553761,638673,'F',TO_TIMESTAMP('2025-11-12 19:22:46.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Zugewiesen an',140,0,0,TO_TIMESTAMP('2025-11-12 19:22:46.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.HUAggregationType
-- Column: M_InventoryLine.HUAggregationType
-- 2025-11-12T19:22:48.943Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756088,0,548511,553761,638674,'F',TO_TIMESTAMP('2025-11-12 19:22:48.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'HUAggregationType',150,0,0,TO_TIMESTAMP('2025-11-12 19:22:48.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:25:11.279Z
UPDATE AD_Window_Trl SET Name='Inventur Mobile',Updated=TO_TIMESTAMP('2025-11-12 19:25:11.074000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Window_ID=541969
;

-- 2025-11-12T19:25:11.346Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-12T19:25:14.839Z
UPDATE AD_Window_Trl SET Name='Inventur Mobile',Updated=TO_TIMESTAMP('2025-11-12 19:25:14.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Window_ID=541969
;

-- 2025-11-12T19:25:14.914Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='it_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-12T19:25:34.789Z
UPDATE AD_Window_Trl SET Description='Physical Inventory Mobile', Help='The Physical Inventory Window allows you to generate inventory count lists.  These counts can then be processed which will update the actual inventory with the new counts. Normally you would create inventory count lines automaticelly. If you create them manually, make sure that the book value is correct.', Name='Physical Inventory Mobile',Updated=TO_TIMESTAMP('2025-11-12 19:25:34.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Window_ID=541969
;

-- 2025-11-12T19:25:34.902Z
UPDATE AD_Window base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='en_GB' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-12T19:25:51.435Z
UPDATE AD_Window_Trl SET Name='Inventur Mobile',Updated=TO_TIMESTAMP('2025-11-12 19:25:51.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Window_ID=541969
;

-- 2025-11-12T19:25:51.520Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-12T19:25:55.700Z
UPDATE AD_Window_Trl SET Name='Inventur Mobile',Updated=TO_TIMESTAMP('2025-11-12 19:25:55.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Window_ID=541969
;

-- 2025-11-12T19:25:55.765Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-11-12T19:27:05.180Z
UPDATE AD_Window_Trl SET Name='Physical Inventory Mobile',Updated=TO_TIMESTAMP('2025-11-12 19:27:04.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Window_ID=541969
;

-- 2025-11-12T19:27:05.336Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Name: Inventur Mobile
-- Action Type: W
-- Window: Inventur Mobile(541969,D)
-- 2025-11-12T19:27:49.714Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584206,542269,0,541969,TO_TIMESTAMP('2025-11-12 19:27:48.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Inventurdaten eingeben','D','Inventur Mobile','Y','N','N','N','N','Inventur Mobile',TO_TIMESTAMP('2025-11-12 19:27:48.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:27:49.782Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542269 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-11-12T19:27:49.867Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542269, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542269)
;

-- 2025-11-12T19:27:49.947Z
/* DDL */  select update_menu_translation_from_ad_element(584206)
;

-- Reordering children of `Warehouse Management`
-- Node name: `Users assigned to workplace`
-- 2025-11-12T19:27:55.548Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542121 AND AD_Tree_ID=10
;

-- Node name: `Workplace`
-- 2025-11-12T19:27:55.613Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542120 AND AD_Tree_ID=10
;

-- Node name: `Warehouse`
-- 2025-11-12T19:27:55.677Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type`
-- 2025-11-12T19:27:55.752Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move`
-- 2025-11-12T19:27:55.829Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme`
-- 2025-11-12T19:27:55.897Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase`
-- 2025-11-12T19:27:55.983Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule`
-- 2025-11-12T19:27:56.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast`
-- 2025-11-12T19:27:56.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Forecast Lines`
-- 2025-11-12T19:27:56.211Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542225 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit`
-- 2025-11-12T19:27:56.278Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-11-12T19:27:56.348Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-11-12T19:27:56.437Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-11-12T19:27:56.514Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory`
-- 2025-11-12T19:27:56.583Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting`
-- 2025-11-12T19:27:56.651Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate`
-- 2025-11-12T19:27:56.729Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule Quantity Details`
-- 2025-11-12T19:27:56.817Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542226 AND AD_Tree_ID=10
;

-- Node name: `Inventur Mobile`
-- 2025-11-12T19:27:56.883Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542269 AND AD_Tree_ID=10
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Permanente Stichtagsinventur
-- Column: M_Inventory.M_PerpetualInv_ID
-- 2025-11-12T19:32:52.700Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638634
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Kostenstelle
-- Column: M_Inventory.C_Activity_ID
-- 2025-11-12T19:33:03.304Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638636
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Freigegeben
-- Column: M_Inventory.IsApproved
-- 2025-11-12T19:33:52.367Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638644
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Freigabe-Betrag
-- Column: M_Inventory.ApprovalAmt
-- 2025-11-12T19:33:56.919Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638645
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> org.Sektion
-- Column: M_Inventory.AD_Org_ID
-- 2025-11-12T19:34:31.816Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638652
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> org.Mandant
-- Column: M_Inventory.AD_Client_ID
-- 2025-11-12T19:34:35.528Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638653
;

-- UI Column: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20
-- UI Element Group: org
-- 2025-11-12T19:34:41.399Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=553760
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Lager
-- Column: M_Inventory.M_Warehouse_ID
-- 2025-11-12T19:37:18.660Z
UPDATE AD_Field SET DefaultValue='540008',Updated=TO_TIMESTAMP('2025-11-12 19:37:18.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756046
;

-- Column: M_Inventory.C_DocType_ID
-- 2025-11-12T19:39:03.169Z
UPDATE AD_Column SET IsAutoApplyValidationRule='N',Updated=TO_TIMESTAMP('2025-11-12 19:39:03.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=12797
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.Description
-- 2025-11-12T19:40:31.306Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-11-12 19:40:31.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638668
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Handling Unit
-- Column: M_InventoryLine.M_HU_ID
-- 2025-11-12T19:40:31.672Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-11-12 19:40:31.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638666
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Inventory Type
-- Column: M_InventoryLine.InventoryType
-- 2025-11-12T19:40:32.040Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-11-12 19:40:32.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638669
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2025-11-12T19:40:32.415Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-11-12 19:40:32.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638670
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> document.Belegart
-- Column: M_Inventory.C_DocType_ID
-- 2025-11-12T19:41:39.504Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-11-12 19:41:39.504000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638647
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> document.Nr.
-- Column: M_Inventory.DocumentNo
-- 2025-11-12T19:41:39.871Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-11-12 19:41:39.871000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638648
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> document.Bewegungsdatum
-- Column: M_Inventory.MovementDate
-- 2025-11-12T19:41:40.238Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-11-12 19:41:40.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638649
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Lager
-- Column: M_Inventory.M_Warehouse_ID
-- 2025-11-12T19:41:40.604Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-11-12 19:41:40.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638633
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Beschreibung
-- Column: M_Inventory.Description
-- 2025-11-12T19:41:40.974Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-11-12 19:41:40.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638646
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Belegstatus
-- Column: M_Inventory.DocStatus
-- 2025-11-12T19:41:41.340Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-11-12 19:41:41.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638641
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 20 -> posted.Verbucht
-- Column: M_Inventory.Posted
-- 2025-11-12T19:41:41.705Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-11-12 19:41:41.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638651
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Erstellt durch
-- Column: M_Inventory.CreatedBy
-- 2025-11-12T19:44:17.470Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3547,756099,0,548510,0,TO_TIMESTAMP('2025-11-12 19:44:16.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',0,'D',0,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Erstellt durch',0,0,230,0,1,1,TO_TIMESTAMP('2025-11-12 19:44:16.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T19:44:17.534Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T19:44:17.601Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2025-11-12T19:44:17.726Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756099
;

-- 2025-11-12T19:44:17.790Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756099)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Erstellt durch
-- Column: M_Inventory.CreatedBy
-- 2025-11-12T19:45:47.699Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756099,0,548510,553757,638675,'F',TO_TIMESTAMP('2025-11-12 19:45:46.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','N','N',0,'Erstellt durch',40,0,0,TO_TIMESTAMP('2025-11-12 19:45:46.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> default.Belegstatus
-- Column: M_Inventory.DocStatus
-- 2025-11-12T19:46:09.546Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-11-12 19:46:09.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638641
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Erstellt durch
-- Column: M_Inventory.CreatedBy
-- 2025-11-12T19:46:09.921Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-11-12 19:46:09.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638675
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.IsExplicitCostPrice
-- Column: M_InventoryLine.IsExplicitCostPrice
-- 2025-11-12T19:48:17.478Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638661
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Cost Price
-- Column: M_InventoryLine.CostPrice
-- 2025-11-12T19:48:21.900Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638662
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Inventory Type
-- Column: M_InventoryLine.InventoryType
-- 2025-11-12T19:48:58.447Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638669
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2025-11-12T19:49:03.136Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638670
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- 2025-11-12T19:49:07.846Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638671
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2025-11-12T19:49:16.193Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638673
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.HUAggregationType
-- Column: M_InventoryLine.HUAggregationType
-- 2025-11-12T19:49:20.753Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=638674
;

-- Run mode: SWING_CLIENT

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Erstellt durch
-- Column: M_Inventory.CreatedBy
-- 2025-11-12T19:59:31.069Z
UPDATE AD_Field SET FilterOperator='E', IsFilterField='Y',Updated=TO_TIMESTAMP('2025-11-12 19:59:31.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756099
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> main -> 10 -> description.Erstellt durch
-- Column: M_Inventory.CreatedBy
-- 2025-11-12T20:00:23.365Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2025-11-12 20:00:23.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=638675
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Erstellt durch
-- Column: M_Inventory.CreatedBy
-- 2025-11-12T20:03:58.939Z
UPDATE AD_Field SET SelectionColumnSeqNo=35.000000000000,Updated=TO_TIMESTAMP('2025-11-12 20:03:58.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756099
;

-- Column: M_Inventory.CreatedBy
-- 2025-11-12T20:05:49.228Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-11-12 20:05:49.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=3547
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Erstellt durch
-- Column: M_Inventory.CreatedBy
-- 2025-11-12T20:07:30.726Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=110,Updated=TO_TIMESTAMP('2025-11-12 20:07:30.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756099
;

-- Column: M_InventoryLine.DifferenceQty
-- 2025-11-12T20:11:26.791Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591511,2526,0,29,322,'XX','DifferenceQty','(QtyCount::numeric - QtyBook::numeric)',TO_TIMESTAMP('2025-11-12 20:11:25.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Unterschiedsmenge','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Differenz',0,0,TO_TIMESTAMP('2025-11-12 20:11:25.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-11-12T20:11:26.863Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591511 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-12T20:11:27.079Z
/* DDL */  select update_Column_Translation_From_AD_Element(2526)
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Differenz
-- Column: M_InventoryLine.DifferenceQty
-- 2025-11-12T20:12:07.914Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591511,756100,0,548511,0,TO_TIMESTAMP('2025-11-12 20:12:06.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Unterschiedsmenge',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Differenz',0,0,180,0,1,1,TO_TIMESTAMP('2025-11-12 20:12:06.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-11-12T20:12:07.978Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=756100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-11-12T20:12:08.050Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2526)
;

-- 2025-11-12T20:12:08.144Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=756100
;

-- 2025-11-12T20:12:08.206Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(756100)
;

-- UI Element: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> main -> 10 -> default.Differenz
-- Column: M_InventoryLine.DifferenceQty
-- 2025-11-12T20:12:59.627Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,756100,0,548511,553761,638676,'F',TO_TIMESTAMP('2025-11-12 20:12:58.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Unterschiedsmenge','Y','N','N','Y','N','N','N',0,'Differenz',65,0,0,TO_TIMESTAMP('2025-11-12 20:12:58.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählungs Position(548511,D) -> Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2025-11-12T20:14:11.810Z
UPDATE AD_Field SET DefaultValue='Y',Updated=TO_TIMESTAMP('2025-11-12 20:14:11.810000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756085
;

-- Column: M_Inventory.C_DocType_ID
-- 2025-11-14T13:16:29.349Z
UPDATE AD_Column SET IsAutoApplyValidationRule='N',Updated=TO_TIMESTAMP('2025-11-14 13:16:29.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=12797
;

-- Field: Inventur Mobile(541969,D) -> Bestandszählung(548510,D) -> Belegart
-- Column: M_Inventory.C_DocType_ID
-- 2025-11-14T13:34:05.303Z
UPDATE AD_Field SET DefaultValue='@SQL=SELECT C_DocType_ID FROM C_DocType WHERE DocBaseType=''MMI'' AND AD_Org_ID=@#AD_Org_ID@ ORDER BY  IsDefault=''Y'' DESC LIMIT 1',Updated=TO_TIMESTAMP('2025-11-14 13:34:05.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=756059
;
