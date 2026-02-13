-- Run mode: SWING_CLIENT

-- Element: null
-- 2026-02-04T15:18:20.207Z
UPDATE AD_Element_Trl SET PO_Help='TEST',Updated=TO_TIMESTAMP('2026-02-04 15:18:20.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574342 AND AD_Language='de_CH'
;

-- 2026-02-04T15:18:20.239Z
UPDATE AD_Element base SET PO_Help=trl.PO_Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:18:33.750Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574342,'de_CH')
;

-- 2026-02-04T15:18:34.645Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584494,0,TO_TIMESTAMP('2026-02-04 15:18:34.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwalten von Produkt-Kategorien','U',' (144 Window Old)','Y','Produkt Kategorie OLD','Produkt Kategorie OLD',TO_TIMESTAMP('2026-02-04 15:18:34.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:18:34.675Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584494 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Produkt Kategorie OLD, InternalName=144 (Todo: Set Internal Name for UI testing)
-- 2026-02-04T15:19:56.498Z
UPDATE AD_Window SET AD_Element_ID=584494, Description='Verwalten von Produkt-Kategorien', Help=' (144 Window Old)', Name='Produkt Kategorie OLD',Updated=TO_TIMESTAMP('2026-02-04 15:19:56.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=144
;

-- 2026-02-04T15:19:56.530Z
UPDATE AD_Window_Trl trl SET Help=' (144 Window Old)',Name='Produkt Kategorie OLD' WHERE AD_Window_ID=144 AND AD_Language='de_DE'
;

-- Name: Produkt Kategorie OLD
-- Action Type: W
-- Window: Produkt Kategorie OLD(144,D)
-- 2026-02-04T15:20:24.387Z
UPDATE AD_Menu SET Description='Verwalten von Produkt-Kategorien', IsActive='Y', Name='Produkt Kategorie OLD',Updated=TO_TIMESTAMP('2026-02-04 15:20:24.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=130
;

-- 2026-02-04T15:20:24.418Z
UPDATE AD_Menu_Trl trl SET Name='Produkt Kategorie OLD' WHERE AD_Menu_ID=130 AND AD_Language='de_DE'
;

-- Name: Produkt Kategorie OLD
-- Action Type: W
-- Window: Produkt Kategorie OLD(144,D)
-- 2026-02-04T15:20:36.653Z
UPDATE AD_Menu SET Description='Verwalten von Produkt-Kategorien', IsActive='Y', Name='Produkt Kategorie OLD',Updated=TO_TIMESTAMP('2026-02-04 15:20:36.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=1000096
;

-- 2026-02-04T15:20:36.682Z
UPDATE AD_Menu_Trl trl SET Name='Produkt Kategorie OLD' WHERE AD_Menu_ID=1000096 AND AD_Language='de_DE'
;

-- 2026-02-04T15:21:09.203Z
UPDATE AD_WF_Node SET Description='Verwalten von Produkt-Kategorien', Help=' (144 Window Old)', Name='Produkt Kategorie OLD',Updated=TO_TIMESTAMP('2026-02-04 15:21:09.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_WF_Node_ID=160
;

-- 2026-02-04T15:21:09.233Z
UPDATE AD_WF_Node_Trl trl SET Help=' (144 Window Old)',Name='Produkt Kategorie OLD' WHERE AD_WF_Node_ID=160 AND AD_Language='de_DE'
;

-- 2026-02-04T15:21:29.571Z
/* DDL */  select update_window_translation_from_ad_element(584494)
;

-- 2026-02-04T15:21:29.610Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=144
;

-- 2026-02-04T15:21:29.642Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(144)
;

-- 2026-02-04T15:26:18.901Z
UPDATE AD_Element SET EntityType='U',Updated=TO_TIMESTAMP('2026-02-04 15:26:18.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574342
;

-- 2026-02-04T15:26:19.021Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574342,'de_DE')
;

-- Window: Produkt Kategorie, InternalName=null
-- 2026-02-04T15:26:58.228Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,574342,0,542066,TO_TIMESTAMP('2026-02-04 15:26:57.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verwalten von Produkt-Kategorien','U','Eine Produktkategorie definiert eine Gruppe von bestimmten Produkten. Diese Gruppen können zur Erzeugung von Preislisten, Definition von Margen und zur einfachen Zuordnung verschiedener Buchhaltungsparamter zu Produkten verwendet werden.','Y','N','N','N','N','N','N','Y','Produkt Kategorie','N',TO_TIMESTAMP('2026-02-04 15:26:57.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2026-02-04T15:26:58.259Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542066 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-02-04T15:26:58.322Z
/* DDL */  select update_window_translation_from_ad_element(574342)
;

-- 2026-02-04T15:26:58.353Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542066
;

-- 2026-02-04T15:26:58.383Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542066)
;

-- Window: Produkt Kategorie, InternalName=null
-- 2026-02-04T15:29:25.096Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='D', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='N', Overrides_Window_ID=144, WindowType='M', WinHeight=NULL, WinWidth=NULL, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2026-02-04 15:29:25.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542066
;

-- Tab: Produkt Kategorie(542066,D) -> Produkt-Kategorie
-- Table: M_Product_Category
-- 2026-02-04T15:29:25.646Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572919,0,548973,209,542066,'Y',TO_TIMESTAMP('2026-02-04 15:29:25.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Kategorien verwalten','D','N','The Product Category defines unique groupings of products.  Product categories can be used in building price lists.','A','M_Product_Category','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Produkt-Kategorie','N',10,0,TO_TIMESTAMP('2026-02-04 15:29:25.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:25.677Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548973 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-04T15:29:25.711Z
/* DDL */  select update_tab_translation_from_ad_element(572919)
;

-- 2026-02-04T15:29:25.736Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548973)
;

-- 2026-02-04T15:29:25.806Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548973
;

-- 2026-02-04T15:29:25.836Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548973, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 189
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Mandant
-- Column: M_Product_Category.AD_Client_ID
-- 2026-02-04T15:29:26.496Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1769,771768,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:26.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',10,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:26.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:26.526Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771768 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:26.556Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-04T15:29:26.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771768
;

-- 2026-02-04T15:29:26.676Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771768)
;

-- 2026-02-04T15:29:26.736Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771768
;

-- 2026-02-04T15:29:26.766Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771768, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 1153
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Aktiv
-- Column: M_Product_Category.IsActive
-- 2026-02-04T15:29:27.109Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1771,771769,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:26.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Aktiv',35,35,1,1,TO_TIMESTAMP('2026-02-04 15:29:26.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:27.135Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771769 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:27.166Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-04T15:29:27.256Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771769
;

-- 2026-02-04T15:29:27.286Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771769)
;

-- 2026-02-04T15:29:27.347Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771769
;

-- 2026-02-04T15:29:27.377Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771769, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 1154
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Name
-- Column: M_Product_Category.Name
-- 2026-02-04T15:29:27.705Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1776,771770,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:27.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',60,'D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Name',30,30,1,1,1,TO_TIMESTAMP('2026-02-04 15:29:27.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:27.736Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:27.765Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2026-02-04T15:29:27.816Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771770
;

-- 2026-02-04T15:29:27.846Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771770)
;

-- 2026-02-04T15:29:27.905Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771770
;

-- 2026-02-04T15:29:27.936Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771770, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 1155
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Beschreibung
-- Column: M_Product_Category.Description
-- 2026-02-04T15:29:28.276Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1777,771771,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:27.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Beschreibung',40,40,2,1,TO_TIMESTAMP('2026-02-04 15:29:27.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:28.306Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:28.336Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-02-04T15:29:28.376Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771771
;

-- 2026-02-04T15:29:28.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771771)
;

-- 2026-02-04T15:29:28.466Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771771
;

-- 2026-02-04T15:29:28.498Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771771, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 1156
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Produkt Kategorie
-- Column: M_Product_Category.M_Product_Category_ID
-- 2026-02-04T15:29:28.865Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2020,771772,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:28.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',14,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',0,'Y','N','N','N','N','N','N','N','N','N','N','Produkt Kategorie',50,65,1,1,TO_TIMESTAMP('2026-02-04 15:29:28.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:28.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:28.925Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2026-02-04T15:29:28.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771772
;

-- 2026-02-04T15:29:28.988Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771772)
;

-- 2026-02-04T15:29:29.049Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771772
;

-- 2026-02-04T15:29:29.080Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771772, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 1160
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Organisation
-- Column: M_Product_Category.AD_Org_ID
-- 2026-02-04T15:29:29.418Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1770,771773,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:29.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Organisation',10,10,1,1,TO_TIMESTAMP('2026-02-04 15:29:29.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:29.448Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771773 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:29.479Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-04T15:29:29.567Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771773
;

-- 2026-02-04T15:29:29.597Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771773)
;

-- 2026-02-04T15:29:29.658Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771773
;

-- 2026-02-04T15:29:29.687Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771773, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2055
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Suchschlüssel
-- Column: M_Product_Category.Value
-- 2026-02-04T15:29:30.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3017,771774,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:29.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',11,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Suchschlüssel',20,20,1,1,TO_TIMESTAMP('2026-02-04 15:29:29.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:30.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771774 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:30.082Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2026-02-04T15:29:30.117Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771774
;

-- 2026-02-04T15:29:30.149Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771774)
;

-- 2026-02-04T15:29:30.210Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771774
;

-- 2026-02-04T15:29:30.240Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771774, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 2118
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Standard
-- Column: M_Product_Category.IsDefault
-- 2026-02-04T15:29:30.569Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4725,771775,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:30.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Default value',1,'D','The Default Checkbox indicates if this record will be used as a default value.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Standard',80,80,1,1,TO_TIMESTAMP('2026-02-04 15:29:30.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:30.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771775 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:30.632Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1103)
;

-- 2026-02-04T15:29:30.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771775
;

-- 2026-02-04T15:29:30.696Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771775)
;

-- 2026-02-04T15:29:30.757Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771775
;

-- 2026-02-04T15:29:30.787Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771775, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3748
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> DB1 %
-- Column: M_Product_Category.PlannedMargin
-- 2026-02-04T15:29:31.157Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5788,771776,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:30.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'geplante Marge in Prozent',26,'D','The Planned Margin Percentage indicates the anticipated margin percentage for this project or project line',0,'Y','N','N','N','N','N','N','N','N','N','N','DB1 %',90,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:30.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:31.189Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771776 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:31.219Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1565)
;

-- 2026-02-04T15:29:31.252Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771776
;

-- 2026-02-04T15:29:31.281Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771776)
;

-- 2026-02-04T15:29:31.343Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771776
;

-- 2026-02-04T15:29:31.373Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771776, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 4459
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Asset-Gruppe
-- Column: M_Product_Category.A_Asset_Group_ID
-- 2026-02-04T15:29:31.700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7975,771777,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:31.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppe von Assets',14,'D','Eine Gruppe von Assets bestimmt Standardkonten. If an asset group is selected in the product category, assets are created when delivering the asset.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Asset-Gruppe',90,90,1,1,TO_TIMESTAMP('2026-02-04 15:29:31.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:31.731Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771777 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:31.763Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1929)
;

-- 2026-02-04T15:29:31.795Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771777
;

-- 2026-02-04T15:29:31.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771777)
;

-- 2026-02-04T15:29:31.886Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771777
;

-- 2026-02-04T15:29:31.916Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771777, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6134
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Self-Service
-- Column: M_Product_Category.IsSelfService
-- 2026-02-04T15:29:32.274Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10262,771778,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:31.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'This is a Self-Service entry or this entry can be changed via Self-Service',1,'D','Self-Service allows users to enter data or update their data.  The flag indicates, that this record was entered or created via Self-Service or that the user can change it via the Self-Service functionality.',0,'Y','N','N','N','N','N','N','N','N','N','Y','Self-Service',100,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:31.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:32.305Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771778 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:32.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2063)
;

-- 2026-02-04T15:29:32.368Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771778
;

-- 2026-02-04T15:29:32.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771778)
;

-- 2026-02-04T15:29:32.458Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771778
;

-- 2026-02-04T15:29:32.489Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771778, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 8614
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Druck - Farbe
-- Column: M_Product_Category.AD_PrintColor_ID
-- 2026-02-04T15:29:32.854Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,11889,771779,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:32.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Farbe für Druck und Anzeige',14,'D','Farbe für Druck und Anzeige',0,'Y','N','N','N','N','N','N','N','N','N','N','Druck - Farbe',110,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:32.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:32.884Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:32.916Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1788)
;

-- 2026-02-04T15:29:32.948Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771779
;

-- 2026-02-04T15:29:32.976Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771779)
;

-- 2026-02-04T15:29:33.036Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771779
;

-- 2026-02-04T15:29:33.065Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771779, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10261
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Materialfluß
-- Column: M_Product_Category.MMPolicy
-- 2026-02-04T15:29:33.395Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13244,771780,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:33.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode für den Materialfluß',14,'D','The Material Movement Policy determines how the stock is flowing (FiFo or LiFo) if a specific Product Instance was not selected.  The policy can not contradict the costing method (e.g. FiFo movement policy and LiFo costing method).',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Materialfluß',70,70,1,1,TO_TIMESTAMP('2026-02-04 15:29:33.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:33.426Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771780 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:33.455Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2685)
;

-- 2026-02-04T15:29:33.486Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771780
;

-- 2026-02-04T15:29:33.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771780)
;

-- 2026-02-04T15:29:33.576Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771780
;

-- 2026-02-04T15:29:33.606Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771780, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 11203
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Parent Product Category
-- Column: M_Product_Category.M_Product_Category_Parent_ID
-- 2026-02-04T15:29:33.931Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,50211,771781,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:33.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Parent Product Category',60,60,1,1,TO_TIMESTAMP('2026-02-04 15:29:33.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:33.964Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:33.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(50070)
;

-- 2026-02-04T15:29:34.026Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771781
;

-- 2026-02-04T15:29:34.055Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771781)
;

-- 2026-02-04T15:29:34.116Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771781
;

-- 2026-02-04T15:29:34.146Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771781, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 50181
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Belegart
-- Column: M_Product_Category.C_DocType_ID
-- 2026-02-04T15:29:34.508Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,501676,771782,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:34.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',14,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.',0,'Y','N','N','N','N','N','N','N','N','N','N','Belegart',120,0,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:34.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:34.538Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771782 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:34.571Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2026-02-04T15:29:34.606Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771782
;

-- 2026-02-04T15:29:34.636Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771782)
;

-- 2026-02-04T15:29:34.696Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771782
;

-- 2026-02-04T15:29:34.727Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771782, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 501677
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Merkmals-Satz
-- Column: M_Product_Category.M_AttributeSet_ID
-- 2026-02-04T15:29:35.062Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550253,771783,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:34.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals-Satz zum Produkt',0,'D','Definieren Sie Merkmals-Sätze um einem Produkt zusätzliche Merkmale und Eigenschaften zuzuordnen. Sie müssen einen Merkmals-Satz anlegen, um Serien- und Los-Nummern verfolgen zu können.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Merkmals-Satz',100,100,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:34.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:35.093Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:35.123Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2017)
;

-- 2026-02-04T15:29:35.155Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771783
;

-- 2026-02-04T15:29:35.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771783)
;

-- 2026-02-04T15:29:35.246Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771783
;

-- 2026-02-04T15:29:35.276Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771783, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553957
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> MRP ausschliessen
-- Column: M_Product_Category.MRP_Exclude
-- 2026-02-04T15:29:35.598Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551772,540077,771784,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:35.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','MRP ausschliessen',130,130,1,1,TO_TIMESTAMP('2026-02-04 15:29:35.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:35.629Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:35.660Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542697)
;

-- 2026-02-04T15:29:35.693Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771784
;

-- 2026-02-04T15:29:35.723Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771784)
;

-- 2026-02-04T15:29:35.784Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771784
;

-- 2026-02-04T15:29:35.814Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771784, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555418
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Verpackungsmaterial
-- Column: M_Product_Category.IsPackagingMaterial
-- 2026-02-04T15:29:36.156Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553158,771785,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:35.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Verpackungsmaterial',110,110,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:35.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:36.194Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771785 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:36.268Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542246)
;

-- 2026-02-04T15:29:36.347Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771785
;

-- 2026-02-04T15:29:36.424Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771785)
;

-- 2026-02-04T15:29:36.504Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771785
;

-- 2026-02-04T15:29:36.535Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771785, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556611
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Gebinde
-- Column: M_Product_Category.isTradingUnit
-- 2026-02-04T15:29:36.876Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,553172,771786,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:36.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Gebinde',120,120,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:36.565000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:36.907Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771786 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:36.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542975)
;

-- 2026-02-04T15:29:36.966Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771786
;

-- 2026-02-04T15:29:36.996Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771786)
;

-- 2026-02-04T15:29:37.059Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771786
;

-- 2026-02-04T15:29:37.092Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771786, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556619
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Zusammenfassungseintrag
-- Column: M_Product_Category.IsSummary
-- 2026-02-04T15:29:37.456Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,504405,771787,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:37.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist ein Zusammenfassungseintrag',1,'D','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','N','N','N','N','N','N','N','N','N','N','Zusammenfassungseintrag',140,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:37.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:37.486Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:37.518Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(416)
;

-- 2026-02-04T15:29:37.546Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771787
;

-- 2026-02-04T15:29:37.577Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771787)
;

-- 2026-02-04T15:29:37.636Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771787
;

-- 2026-02-04T15:29:37.671Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771787, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 562800
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Kompensationsgruppe Schema
-- Column: M_Product_Category.C_CompensationGroup_Schema_ID
-- 2026-02-04T15:29:38.016Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559243,771788,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:37.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Kompensationsgruppe Schema',140,140,1,1,TO_TIMESTAMP('2026-02-04 15:29:37.698000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:38.046Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:38.076Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543889)
;

-- 2026-02-04T15:29:38.106Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771788
;

-- 2026-02-04T15:29:38.136Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771788)
;

-- 2026-02-04T15:29:38.197Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771788
;

-- 2026-02-04T15:29:38.229Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771788, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 562801
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Min. Garantie-Tage
-- Column: M_Product_Category.GuaranteeDaysMin
-- 2026-02-04T15:29:38.586Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559251,771789,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:38.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mindestanzahl Garantie-Tage',10,'D','When selecting batch/products with a guarantee date, the minimum left guarantee days for automatic picking.  You can pick any batch/product manually. ',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Min. Garantie-Tage',150,1,1,TO_TIMESTAMP('2026-02-04 15:29:38.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:38.616Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771789 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:38.646Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2197)
;

-- 2026-02-04T15:29:38.678Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771789
;

-- 2026-02-04T15:29:38.710Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771789)
;

-- 2026-02-04T15:29:38.774Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771789
;

-- 2026-02-04T15:29:38.801Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771789, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 562807
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Produkt-Nummerfolge
-- Column: M_Product_Category.AD_Sequence_ProductValue_ID
-- 2026-02-04T15:29:39.127Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560664,771790,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:38.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Optionale Nummerfolge für Produkt-Suchschlüssel; falls leer wird ggf die Eltern-Produktkategorie überprüft.',10,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Produkt-Nummerfolge',65,65,1,1,TO_TIMESTAMP('2026-02-04 15:29:38.831000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:39.156Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:39.186Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544173)
;

-- 2026-02-04T15:29:39.216Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771790
;

-- 2026-02-04T15:29:39.246Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771790)
;

-- 2026-02-04T15:29:39.307Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771790
;

-- 2026-02-04T15:29:39.339Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771790, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565101
;

-- Field: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> Qualitätsattribut-Satz
-- Column: M_Product_Category.M_QualityAttributeSet_ID
-- 2026-02-04T15:29:39.716Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590152,771791,0,548973,0,TO_TIMESTAMP('2026-02-04 15:29:39.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Qualitätsattribut-Satz zum Produkt',0,'D',0,'',0,'Y','N','Y','Y','N','N','N','N','N','N','N',0,'Qualitätsattribut-Satz',0,160,150,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:39.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:39.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:39.776Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583684)
;

-- 2026-02-04T15:29:39.808Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771791
;

-- 2026-02-04T15:29:39.841Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771791)
;

-- 2026-02-04T15:29:39.904Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771791
;

-- 2026-02-04T15:29:39.926Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771791, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747489
;

-- Tab: Produkt Kategorie(542066,D) -> Buchführung
-- Table: M_Product_Category_Acct
-- 2026-02-04T15:29:40.261Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572970,0,548974,401,542066,'Y',TO_TIMESTAMP('2026-02-04 15:29:39.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Accounting Parameters','D','N','The Accounting Tab defines default accounting parameters.  Any product that uses a product category can inherit its default accounting parameters.  If the Costing method is not defined, the default costing method of the accounting schema is used.','A','M_Product_Category_Acct','Y','N','Y','Y','N','N','Y','Y','Y','N','N','N','Y','Y','Y','N','N','Buchführung','N',20,1,TO_TIMESTAMP('2026-02-04 15:29:39.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:40.291Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548974 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-04T15:29:40.320Z
/* DDL */  select update_tab_translation_from_ad_element(572970)
;

-- 2026-02-04T15:29:40.356Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548974)
;

-- 2026-02-04T15:29:40.416Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548974
;

-- 2026-02-04T15:29:40.446Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548974, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 324
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Mandant
-- Column: M_Product_Category_Acct.AD_Client_ID
-- 2026-02-04T15:29:41.014Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5112,771792,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:40.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2026-02-04 15:29:40.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:41.045Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:41.076Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-04T15:29:41.163Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771792
;

-- 2026-02-04T15:29:41.194Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771792)
;

-- 2026-02-04T15:29:41.253Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771792
;

-- 2026-02-04T15:29:41.282Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771792, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3935
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Organisation
-- Column: M_Product_Category_Acct.AD_Org_ID
-- 2026-02-04T15:29:41.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5113,771793,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:41.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Organisation',20,20,1,1,TO_TIMESTAMP('2026-02-04 15:29:41.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:41.634Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:41.666Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-04T15:29:41.750Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771793
;

-- 2026-02-04T15:29:41.780Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771793)
;

-- 2026-02-04T15:29:41.841Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771793
;

-- 2026-02-04T15:29:41.871Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771793, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3936
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Buchführungs-Schema
-- Column: M_Product_Category_Acct.C_AcctSchema_ID
-- 2026-02-04T15:29:42.208Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5111,771794,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:41.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stammdaten für Buchhaltung',14,'D','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Buchführungs-Schema',40,40,1,1,1,TO_TIMESTAMP('2026-02-04 15:29:41.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:42.239Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:42.269Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(181)
;

-- 2026-02-04T15:29:42.303Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771794
;

-- 2026-02-04T15:29:42.332Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771794)
;

-- 2026-02-04T15:29:42.393Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771794
;

-- 2026-02-04T15:29:42.424Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771794, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3937
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Aktiv
-- Column: M_Product_Category_Acct.IsActive
-- 2026-02-04T15:29:42.760Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5114,771795,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:42.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2026-02-04 15:29:42.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:42.796Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:42.826Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-04T15:29:42.911Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771795
;

-- 2026-02-04T15:29:42.938Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771795)
;

-- 2026-02-04T15:29:43Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771795
;

-- 2026-02-04T15:29:43.032Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771795, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3938
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Produkt Kategorie
-- Column: M_Product_Category_Acct.M_Product_Category_ID
-- 2026-02-04T15:29:43.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5110,771796,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:43.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',14,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Produkt Kategorie',30,30,1,1,TO_TIMESTAMP('2026-02-04 15:29:43.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:43.396Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:43.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2026-02-04T15:29:43.456Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771796
;

-- 2026-02-04T15:29:43.486Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771796)
;

-- 2026-02-04T15:29:43.546Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771796
;

-- 2026-02-04T15:29:43.577Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771796, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3939
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Warenbestand
-- Column: M_Product_Category_Acct.P_Asset_Acct
-- 2026-02-04T15:29:43.907Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5121,771797,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:43.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Warenbestand',26,'D','"Produkt-Asset" bezeichnet das Konto für die Bewertung eines Produktes im Bestand.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Warenbestand',80,80,1,1,TO_TIMESTAMP('2026-02-04 15:29:43.606000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:43.946Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:43.976Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1042)
;

-- 2026-02-04T15:29:44.006Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771797
;

-- 2026-02-04T15:29:44.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771797)
;

-- 2026-02-04T15:29:44.096Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771797
;

-- 2026-02-04T15:29:44.126Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771797, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3940
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Produkt Vertriebsausgaben
-- Column: M_Product_Category_Acct.P_COGS_Acct
-- 2026-02-04T15:29:44.461Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5122,771798,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:44.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Produkt Vertriebsausgaben',26,'D','The Product COGS Account indicates the account used when recording costs associated with this product.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produkt Vertriebsausgaben',120,120,1,1,TO_TIMESTAMP('2026-02-04 15:29:44.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:44.491Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:44.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1043)
;

-- 2026-02-04T15:29:44.556Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771798
;

-- 2026-02-04T15:29:44.586Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771798)
;

-- 2026-02-04T15:29:44.646Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771798
;

-- 2026-02-04T15:29:44.677Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771798, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3941
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Produkt Aufwand
-- Column: M_Product_Category_Acct.P_Expense_Acct
-- 2026-02-04T15:29:44.999Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5120,771799,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:44.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Produkt Aufwand',26,'D','The Product Expense Account indicates the account used to record expenses associated with this product.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Produkt Aufwand',90,90,1,1,TO_TIMESTAMP('2026-02-04 15:29:44.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:45.036Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:45.066Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1044)
;

-- 2026-02-04T15:29:45.096Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771799
;

-- 2026-02-04T15:29:45.126Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771799)
;

-- 2026-02-04T15:29:45.186Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771799
;

-- 2026-02-04T15:29:45.216Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771799, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3942
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Preisdifferenz Bestellung
-- Column: M_Product_Category_Acct.P_PurchasePriceVariance_Acct
-- 2026-02-04T15:29:45.546Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5123,771800,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:45.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.',26,'D','The Purchase Price Variance is used in Standard Costing. It reflects the difference between the Standard Cost and the Purchase Order Price.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Preisdifferenz Bestellung',130,130,1,1,TO_TIMESTAMP('2026-02-04 15:29:45.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:45.576Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:45.606Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1410)
;

-- 2026-02-04T15:29:45.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771800
;

-- 2026-02-04T15:29:45.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771800)
;

-- 2026-02-04T15:29:45.731Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771800
;

-- 2026-02-04T15:29:45.756Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771800, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3943
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Produkt Ertrag
-- Column: M_Product_Category_Acct.P_Revenue_Acct
-- 2026-02-04T15:29:46.086Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5119,771801,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:45.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Produkt Ertrag',26,'D','The Product Revenue Account indicates the account used for recording sales revenue for this product.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produkt Ertrag',170,170,1,1,TO_TIMESTAMP('2026-02-04 15:29:45.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:46.121Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:46.153Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1045)
;

-- 2026-02-04T15:29:46.181Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771801
;

-- 2026-02-04T15:29:46.216Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771801)
;

-- 2026-02-04T15:29:46.277Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771801
;

-- 2026-02-04T15:29:46.306Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771801, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3944
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Process Now
-- Column: M_Product_Category_Acct.Processing
-- 2026-02-04T15:29:46.645Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,5124,114,771802,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:46.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,23,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Process Now',300,300,1,1,TO_TIMESTAMP('2026-02-04 15:29:46.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:46.676Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:46.707Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524)
;

-- 2026-02-04T15:29:46.745Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771802
;

-- 2026-02-04T15:29:46.776Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771802)
;

-- 2026-02-04T15:29:46.836Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771802
;

-- 2026-02-04T15:29:46.866Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771802, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 3945
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Preisdifferenz Einkauf Rechnung
-- Column: M_Product_Category_Acct.P_InvoicePriceVariance_Acct
-- 2026-02-04T15:29:47.205Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6121,771803,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:46.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Preisdifferenz Einkauf Rechnung',26,'D','The Invoice Price Variance is used reflects the difference between the current Costs and the Invoice Price.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Preisdifferenz Einkauf Rechnung',140,140,1,1,TO_TIMESTAMP('2026-02-04 15:29:46.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:47.237Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:47.269Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1624)
;

-- 2026-02-04T15:29:47.302Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771803
;

-- 2026-02-04T15:29:47.332Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771803)
;

-- 2026-02-04T15:29:47.395Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771803
;

-- 2026-02-04T15:29:47.424Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771803, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 4871
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Erhaltene Rabatte
-- Column: M_Product_Category_Acct.P_TradeDiscountRec_Acct
-- 2026-02-04T15:29:47.756Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6122,771804,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:47.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für erhaltene Rabatte',26,'D','The Trade Discount Receivables Account indicates the account for received trade discounts in vendor invoices',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Erhaltene Rabatte',150,150,1,1,TO_TIMESTAMP('2026-02-04 15:29:47.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:47.787Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:47.818Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1626)
;

-- 2026-02-04T15:29:47.850Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771804
;

-- 2026-02-04T15:29:47.879Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771804)
;

-- 2026-02-04T15:29:47.941Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771804
;

-- 2026-02-04T15:29:47.971Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771804, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 4872
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Gewährte Rabatte
-- Column: M_Product_Category_Acct.P_TradeDiscountGrant_Acct
-- 2026-02-04T15:29:48.313Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6123,771805,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:48.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für gewährte Rabatte',26,'D','The Trade Discount Granted Account indicates the account for granted trade discount in sales invoices',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Gewährte Rabatte',160,160,1,1,TO_TIMESTAMP('2026-02-04 15:29:48.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:48.344Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:48.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1625)
;

-- 2026-02-04T15:29:48.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771805
;

-- 2026-02-04T15:29:48.437Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771805)
;

-- 2026-02-04T15:29:48.499Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771805
;

-- 2026-02-04T15:29:48.529Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771805, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 4873
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Kostenrechnungsmethode
-- Column: M_Product_Category_Acct.CostingMethod
-- 2026-02-04T15:29:48.853Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13245,771806,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:48.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gibt an, wie die Kosten berechnet werden',14,'D','"Kostenrechnungsmethode" gibt an, wie die Kosten berechnet werden (Standard, Durchschnitt, LiFo, FiFo). Die Standardmethode ist auf Ebene des Kontenrahmens definiert und kann optional in der Produktkategorie überschrieben werden. Die Methode der Kostenrechnung kann nicht zur Art der Materialbewegung (definiert in der Produktkategorie) im Widerspruch stehen.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Kostenrechnungsmethode',60,60,1,1,TO_TIMESTAMP('2026-02-04 15:29:48.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:48.884Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:48.915Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(241)
;

-- 2026-02-04T15:29:48.946Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771806
;

-- 2026-02-04T15:29:48.977Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771806)
;

-- 2026-02-04T15:29:49.039Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771806
;

-- 2026-02-04T15:29:49.069Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771806, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 11204
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Kostenrechnungsstufe
-- Column: M_Product_Category_Acct.CostingLevel
-- 2026-02-04T15:29:49.392Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14189,771807,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:49.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die unterste Stufe um Kosteninformation zu kumulieren',1,'D','Wenn Sie verschiedene Kosten pro Organisation (Lager) oder pro Charge/Los verwalten möchten dann stellen Sie sicher, dass Sie diese für jede Organistaion und jede Charge bzw. jedes Los definieren. Die Kostenrechnungsstufe ist pro Kontenrahmens definiert und kann durch die Produktkategorie oder  den Kontenrahmen überschrieben werden.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Kostenrechnungsstufe',70,70,1,1,TO_TIMESTAMP('2026-02-04 15:29:49.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:49.424Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:49.454Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2816)
;

-- 2026-02-04T15:29:49.485Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771807
;

-- 2026-02-04T15:29:49.515Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771807)
;

-- 2026-02-04T15:29:49.576Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771807
;

-- 2026-02-04T15:29:49.606Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771807, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12158
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Bezugsnebenkosten
-- Column: M_Product_Category_Acct.P_CostAdjustment_Acct
-- 2026-02-04T15:29:49.930Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14434,771808,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:49.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Bezugsnebenkosten',10,'D','Account used for posting product cost adjustments (e.g. landed costs)',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Bezugsnebenkosten',100,100,1,1,TO_TIMESTAMP('2026-02-04 15:29:49.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:49.962Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:49.994Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2848)
;

-- 2026-02-04T15:29:50.025Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771808
;

-- 2026-02-04T15:29:50.055Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771808)
;

-- 2026-02-04T15:29:50.116Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771808
;

-- 2026-02-04T15:29:50.145Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771808, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12352
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Bestandsverrechnung
-- Column: M_Product_Category_Acct.P_InventoryClearing_Acct
-- 2026-02-04T15:29:50.480Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14433,771809,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:50.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produktbestandsverrechnungskonto',10,'D','',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Bestandsverrechnung',110,110,1,1,TO_TIMESTAMP('2026-02-04 15:29:50.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:50.511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:50.543Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2847)
;

-- 2026-02-04T15:29:50.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771809
;

-- 2026-02-04T15:29:50.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771809)
;

-- 2026-02-04T15:29:50.664Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771809
;

-- 2026-02-04T15:29:50.693Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771809, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 12353
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Burden
-- Column: M_Product_Category_Acct.P_Burden_Acct
-- 2026-02-04T15:29:51.019Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56555,50010,771810,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:50.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Burden account is the account used Manufacturing Order',10,'@#IsLiberoEnabled@=Y','D','The Burden is used for accounting the Burden',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Burden',250,250,1,1,TO_TIMESTAMP('2026-02-04 15:29:50.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:51.047Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:51.084Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53729)
;

-- 2026-02-04T15:29:51.116Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771810
;

-- 2026-02-04T15:29:51.147Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771810)
;

-- 2026-02-04T15:29:51.206Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771810
;

-- 2026-02-04T15:29:51.237Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771810, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56530
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Produktionskosten
-- Column: M_Product_Category_Acct.P_CostOfProduction_Acct
-- 2026-02-04T15:29:51.573Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56553,50010,771811,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:51.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Konto Produktionskosten ist das Konto für den Fertigungsauftrag',22,'@#IsLiberoEnabled@=Y','D','
',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produktionskosten',260,260,1,1,TO_TIMESTAMP('2026-02-04 15:29:51.266000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:51.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:51.636Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53727)
;

-- 2026-02-04T15:29:51.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771811
;

-- 2026-02-04T15:29:51.696Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771811)
;

-- 2026-02-04T15:29:51.756Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771811
;

-- 2026-02-04T15:29:51.788Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771811, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56531
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Produktionsbestand
-- Column: M_Product_Category_Acct.P_FloorStock_Acct
-- 2026-02-04T15:29:52.116Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56547,50010,771812,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:51.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Konto für den Produktionsbestand ist das Konto für den Produktionsauftrag',22,'@#IsLiberoEnabled@=Y','D','',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Produktionsbestand',190,190,1,1,TO_TIMESTAMP('2026-02-04 15:29:51.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:52.147Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:52.177Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53726)
;

-- 2026-02-04T15:29:52.206Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771812
;

-- 2026-02-04T15:29:52.237Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771812)
;

-- 2026-02-04T15:29:52.301Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771812
;

-- 2026-02-04T15:29:52.333Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771812, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56532
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Labor
-- Column: M_Product_Category_Acct.P_Labor_Acct
-- 2026-02-04T15:29:52.660Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56554,50010,771813,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:52.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Labor account is the account used Manufacturing Order',10,'@#IsLiberoEnabled@=Y','D','The Labor is used for accounting the productive Labor
',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Labor',240,240,1,1,TO_TIMESTAMP('2026-02-04 15:29:52.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:52.692Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:52.717Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53728)
;

-- 2026-02-04T15:29:52.755Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771813
;

-- 2026-02-04T15:29:52.785Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771813)
;

-- 2026-02-04T15:29:52.848Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771813
;

-- 2026-02-04T15:29:52.879Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771813, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56533
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Abweichung bei Methodenänderung
-- Column: M_Product_Category_Acct.P_MethodChangeVariance_Acct
-- 2026-02-04T15:29:53.207Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56549,50010,771814,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:52.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Konto Methodenwechselabweichung ist das Konto, das für den Produktionsauftrag',22,'@#IsLiberoEnabled@=Y','D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Abweichung bei Methodenänderung',200,200,1,1,TO_TIMESTAMP('2026-02-04 15:29:52.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:53.238Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:53.270Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53722)
;

-- 2026-02-04T15:29:53.302Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771814
;

-- 2026-02-04T15:29:53.332Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771814)
;

-- 2026-02-04T15:29:53.393Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771814
;

-- 2026-02-04T15:29:53.423Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771814, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56534
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Mischungsabweichung
-- Column: M_Product_Category_Acct.P_MixVariance_Acct
-- 2026-02-04T15:29:53.768Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56552,50010,771815,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:53.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Konto Mixabweichung ist das Konto für den Produktionsauftrag',22,'@#IsLiberoEnabled@=Y','D','',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Mischungsabweichung',230,230,1,1,TO_TIMESTAMP('2026-02-04 15:29:53.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:53.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:53.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53725)
;

-- 2026-02-04T15:29:53.862Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771815
;

-- 2026-02-04T15:29:53.893Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771815)
;

-- 2026-02-04T15:29:53.955Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771815
;

-- 2026-02-04T15:29:53.984Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771815, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56535
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Fremdbearbeitung
-- Column: M_Product_Category_Acct.P_OutsideProcessing_Acct
-- 2026-02-04T15:29:54.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56556,50010,771816,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:54.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Fremdbearbeitungskonto ist das im Produktionsauftrag verwendete Konto.',10,'@#IsLiberoEnabled@=Y','D','',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Fremdbearbeitung',270,270,1,1,TO_TIMESTAMP('2026-02-04 15:29:54.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:54.346Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:54.380Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53730)
;

-- 2026-02-04T15:29:54.411Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771816
;

-- 2026-02-04T15:29:54.442Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771816)
;

-- 2026-02-04T15:29:54.503Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771816
;

-- 2026-02-04T15:29:54.533Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771816, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56536
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Preisabweichung
-- Column: M_Product_Category_Acct.P_RateVariance_Acct
-- 2026-02-04T15:29:54.857Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56551,50010,771817,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:54.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Konto für die Ratenabweichung ist das Konto für den Fertigungsauftrag',22,'@#IsLiberoEnabled@=Y','D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Preisabweichung',220,220,1,1,TO_TIMESTAMP('2026-02-04 15:29:54.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:54.889Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:54.919Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53724)
;

-- 2026-02-04T15:29:54.952Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771817
;

-- 2026-02-04T15:29:54.984Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771817)
;

-- 2026-02-04T15:29:55.037Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771817
;

-- 2026-02-04T15:29:55.066Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771817, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56537
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Verbrauchsabweichung
-- Column: M_Product_Category_Acct.P_UsageVariance_Acct
-- 2026-02-04T15:29:55.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56550,50010,771818,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:55.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Konto "Nutzungsabweichung" ist das Konto für den Fertigungsauftrag',22,'@#IsLiberoEnabled@=Y','D','',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Verbrauchsabweichung',210,210,1,1,TO_TIMESTAMP('2026-02-04 15:29:55.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:55.436Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:55.466Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53723)
;

-- 2026-02-04T15:29:55.496Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771818
;

-- 2026-02-04T15:29:55.526Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771818)
;

-- 2026-02-04T15:29:55.586Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771818
;

-- 2026-02-04T15:29:55.616Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771818, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56538
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Unfertige Leistungen
-- Column: M_Product_Category_Acct.P_WIP_Acct
-- 2026-02-04T15:29:55.950Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56548,50010,771819,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:55.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet',22,'@#IsLiberoEnabled@=Y','D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Unfertige Leistungen',180,180,1,1,TO_TIMESTAMP('2026-02-04 15:29:55.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:55.976Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:56.007Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53721)
;

-- 2026-02-04T15:29:56.036Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771819
;

-- 2026-02-04T15:29:56.066Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771819)
;

-- 2026-02-04T15:29:56.126Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771819
;

-- 2026-02-04T15:29:56.158Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771819, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56539
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Gemeinkosten
-- Column: M_Product_Category_Acct.P_Overhead_Acct
-- 2026-02-04T15:29:56.496Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56569,50010,771820,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:56.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Gemeinkostenkonto ist das im Produktionsauftrag verwendete Konto',22,'@#IsLiberoEnabled@=Y','D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Gemeinkosten',280,280,1,1,TO_TIMESTAMP('2026-02-04 15:29:56.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:56.526Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:56.557Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53731)
;

-- 2026-02-04T15:29:56.589Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771820
;

-- 2026-02-04T15:29:56.617Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771820)
;

-- 2026-02-04T15:29:56.677Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771820
;

-- 2026-02-04T15:29:56.707Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771820, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56552
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Ausschuss
-- Column: M_Product_Category_Acct.P_Scrap_Acct
-- 2026-02-04T15:29:57.050Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,56570,50010,771821,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:56.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Ausschusskonto ist das Konto für den Fertigungsauftrag',22,'@#IsLiberoEnabled@=Y','D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Ausschuss',290,290,1,1,TO_TIMESTAMP('2026-02-04 15:29:56.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:57.081Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:57.112Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53732)
;

-- 2026-02-04T15:29:57.143Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771821
;

-- 2026-02-04T15:29:57.172Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771821)
;

-- 2026-02-04T15:29:57.235Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771821
;

-- 2026-02-04T15:29:57.264Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771821, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 56553
;

-- Field: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> Product Category Acct
-- Column: M_Product_Category_Acct.M_Product_Category_Acct_ID
-- 2026-02-04T15:29:57.629Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,557268,771822,0,548974,0,TO_TIMESTAMP('2026-02-04 15:29:57.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Product Category Acct',310,1,1,TO_TIMESTAMP('2026-02-04 15:29:57.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:57.660Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:57.692Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543434)
;

-- 2026-02-04T15:29:57.723Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771822
;

-- 2026-02-04T15:29:57.752Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771822)
;

-- 2026-02-04T15:29:57.812Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771822
;

-- 2026-02-04T15:29:57.843Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771822, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560360
;

-- Tab: Produkt Kategorie(542066,D) -> Zugeordnete Produkte
-- Table: M_Product
-- 2026-02-04T15:29:58.173Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,2012,572962,0,548975,208,542066,'Y',TO_TIMESTAMP('2026-02-04 15:29:57.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Produkt-Kategorie zugeordnete Produkte','D','N','N','A','M_Product','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Zugeordnete Produkte','N',30,1,TO_TIMESTAMP('2026-02-04 15:29:57.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:58.205Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548975 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-04T15:29:58.237Z
/* DDL */  select update_tab_translation_from_ad_element(572962)
;

-- 2026-02-04T15:29:58.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548975)
;

-- 2026-02-04T15:29:58.329Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548975
;

-- 2026-02-04T15:29:58.360Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548975, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 407
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Beschreibung
-- Column: M_Product.Description
-- 2026-02-04T15:29:58.970Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1411,771823,0,548975,0,TO_TIMESTAMP('2026-02-04 15:29:58.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Beschreibung',10,0,999,1,TO_TIMESTAMP('2026-02-04 15:29:58.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:59.002Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:59.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-02-04T15:29:59.083Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771823
;

-- 2026-02-04T15:29:59.112Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771823)
;

-- 2026-02-04T15:29:59.173Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771823
;

-- 2026-02-04T15:29:59.202Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771823, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5286
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Zusammenfassungseintrag
-- Column: M_Product.IsSummary
-- 2026-02-04T15:29:59.539Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1413,771824,0,548975,0,TO_TIMESTAMP('2026-02-04 15:29:59.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist ein Zusammenfassungseintrag',1,'D','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Zusammenfassungseintrag',70,70,1,1,TO_TIMESTAMP('2026-02-04 15:29:59.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:29:59.571Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:29:59.601Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(416)
;

-- 2026-02-04T15:29:59.635Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771824
;

-- 2026-02-04T15:29:59.665Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771824)
;

-- 2026-02-04T15:29:59.726Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771824
;

-- 2026-02-04T15:29:59.757Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771824, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5287
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Produkt
-- Column: M_Product.M_Product_ID
-- 2026-02-04T15:30:00.113Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1402,771825,0,548975,0,TO_TIMESTAMP('2026-02-04 15:29:59.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',14,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Produkt',80,0,1,1,TO_TIMESTAMP('2026-02-04 15:29:59.789000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:00.144Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:00.174Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-02-04T15:30:00.211Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771825
;

-- 2026-02-04T15:30:00.241Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771825)
;

-- 2026-02-04T15:30:00.301Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771825
;

-- 2026-02-04T15:30:00.329Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771825, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5289
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Mandant
-- Column: M_Product.AD_Client_ID
-- 2026-02-04T15:30:00.664Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1403,771826,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:00.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2026-02-04 15:30:00.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:00.695Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:00.725Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-04T15:30:00.807Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771826
;

-- 2026-02-04T15:30:00.836Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771826)
;

-- 2026-02-04T15:30:00.898Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771826
;

-- 2026-02-04T15:30:00.928Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771826, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5290
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Organisation
-- Column: M_Product.AD_Org_ID
-- 2026-02-04T15:30:01.265Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1404,771827,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:00.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Organisation',20,20,1,1,TO_TIMESTAMP('2026-02-04 15:30:00.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:01.296Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:01.329Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-04T15:30:01.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771827
;

-- 2026-02-04T15:30:01.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771827)
;

-- 2026-02-04T15:30:01.487Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771827
;

-- 2026-02-04T15:30:01.517Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771827, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5291
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Maßeinheit
-- Column: M_Product.C_UOM_ID
-- 2026-02-04T15:30:01.888Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1760,771828,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:01.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',14,'D','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','N','N','N','N','N','N','N','N','N','Maßeinheit',90,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:01.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:01.920Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:01.950Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-02-04T15:30:01.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771828
;

-- 2026-02-04T15:30:02.017Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771828)
;

-- 2026-02-04T15:30:02.078Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771828
;

-- 2026-02-04T15:30:02.108Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771828, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5292
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Lagerhaltig
-- Column: M_Product.IsStocked
-- 2026-02-04T15:30:02.462Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1761,771829,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:02.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Organisation hat dieses Produkt auf Lager',1,'D','"Lagerhaltig" zeigt an, ob die Organisation dieses Produkt auf Lager hält.',0,'Y','N','N','N','N','N','N','N','N','N','N','Lagerhaltig',100,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:02.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:02.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:02.522Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(415)
;

-- 2026-02-04T15:30:02.553Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771829
;

-- 2026-02-04T15:30:02.583Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771829)
;

-- 2026-02-04T15:30:02.643Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771829
;

-- 2026-02-04T15:30:02.673Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771829, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5293
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Wird Eingekauft
-- Column: M_Product.IsPurchased
-- 2026-02-04T15:30:03.037Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1762,771830,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:02.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','',0,'Y','N','N','N','N','N','N','N','N','N','N','Wird Eingekauft',110,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:02.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:03.076Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:03.106Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(403)
;

-- 2026-02-04T15:30:03.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771830
;

-- 2026-02-04T15:30:03.167Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771830)
;

-- 2026-02-04T15:30:03.227Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771830
;

-- 2026-02-04T15:30:03.257Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771830, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5294
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Produkt Kategorie
-- Column: M_Product.M_Product_Category_ID
-- 2026-02-04T15:30:03.592Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2012,771831,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:03.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',14,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produkt Kategorie',30,30,1,1,TO_TIMESTAMP('2026-02-04 15:30:03.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:03.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:03.647Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2026-02-04T15:30:03.689Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771831
;

-- 2026-02-04T15:30:03.717Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771831)
;

-- 2026-02-04T15:30:03.777Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771831
;

-- 2026-02-04T15:30:03.806Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771831, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5295
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Verkauft
-- Column: M_Product.IsSold
-- 2026-02-04T15:30:04.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1763,771832,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:03.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Organisation verkauft dieses Produkt',1,'D','Das Selektionsfeld "Verkauft" zeigt an, ob dieses Produkt von dieser Organisation verkauft wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Verkauft',120,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:03.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:04.202Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:04.233Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(414)
;

-- 2026-02-04T15:30:04.264Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771832
;

-- 2026-02-04T15:30:04.292Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771832)
;

-- 2026-02-04T15:30:04.349Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771832
;

-- 2026-02-04T15:30:04.378Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771832, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5297
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Volumen
-- Column: M_Product.Volume
-- 2026-02-04T15:30:04.748Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1766,771833,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:04.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Volumen eines Produktes',26,'D','Gibt das Volumen eines Produktes in der Volumen-Mengeneinheit des Mandanten an.',0,'Y','N','N','N','N','N','N','N','N','N','N','Volumen',130,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:04.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:04.777Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:04.808Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(627)
;

-- 2026-02-04T15:30:04.841Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771833
;

-- 2026-02-04T15:30:04.870Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771833)
;

-- 2026-02-04T15:30:04.931Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771833
;

-- 2026-02-04T15:30:04.960Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771833, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5298
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Gewicht netto
-- Column: M_Product.Weight
-- 2026-02-04T15:30:05.314Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1767,771834,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:04.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gewicht eines Produktes',26,'D','The Weight indicates the weight  of the product in the Weight UOM of the Client',0,'Y','N','N','N','N','N','N','N','N','N','N','Gewicht netto',140,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:04.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:05.345Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:05.377Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(629)
;

-- 2026-02-04T15:30:05.408Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771834
;

-- 2026-02-04T15:30:05.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771834)
;

-- 2026-02-04T15:30:05.497Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771834
;

-- 2026-02-04T15:30:05.526Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771834, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5299
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> UPC
-- Column: M_Product.UPC
-- 2026-02-04T15:30:05.890Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2304,771835,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:05.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)',29,'D','Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.',0,'Y','N','N','N','N','N','N','N','N','N','N','UPC',150,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:05.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:05.922Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:05.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(603)
;

-- 2026-02-04T15:30:05.984Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771835
;

-- 2026-02-04T15:30:06.014Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771835)
;

-- 2026-02-04T15:30:06.075Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771835
;

-- 2026-02-04T15:30:06.105Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771835, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5300
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> SKU
-- Column: M_Product.SKU
-- 2026-02-04T15:30:06.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2305,771836,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:06.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stock Keeping Unit',29,'D','"SKU" bezeichnet eine Einheit, die zur Identifizierung eines bestimmten Produktes in der Logistikkette dient, meist in Form einer Buchstaben-Nummern-Kombination. Das Feld kann z.B. für einen zusätzlichen Barcode oder Ihr eigenes System verwendet werden.',0,'Y','N','N','N','N','N','N','N','N','N','N','SKU',160,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:06.137000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:06.497Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:06.526Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(549)
;

-- 2026-02-04T15:30:06.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771836
;

-- 2026-02-04T15:30:06.589Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771836)
;

-- 2026-02-04T15:30:06.649Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771836
;

-- 2026-02-04T15:30:06.681Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771836, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5301
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Regalbreite
-- Column: M_Product.ShelfWidth
-- 2026-02-04T15:30:07.048Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2307,771837,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:06.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Benötigte Regalbreite',11,'D','"Regaltiefe" gibt die Abmessung in der Breite an, die in einem Regal für dieses Produkt benötigt wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Regalbreite',170,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:06.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:07.077Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:07.108Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572)
;

-- 2026-02-04T15:30:07.140Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771837
;

-- 2026-02-04T15:30:07.166Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771837)
;

-- 2026-02-04T15:30:07.229Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771837
;

-- 2026-02-04T15:30:07.261Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771837, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5302
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Regalhöhe
-- Column: M_Product.ShelfHeight
-- 2026-02-04T15:30:07.618Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2308,771838,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:07.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Benötigte Regalhöhe',11,'D','"Regaltiefe" gibt die Abmessung in der Höhe an, die in einem Regal für dieses Produkt benötigt wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Regalhöhe',180,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:07.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:07.650Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:07.677Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(571)
;

-- 2026-02-04T15:30:07.707Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771838
;

-- 2026-02-04T15:30:07.737Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771838)
;

-- 2026-02-04T15:30:07.807Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771838
;

-- 2026-02-04T15:30:07.832Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771838, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5303
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Regaltiefe
-- Column: M_Product.ShelfDepth
-- 2026-02-04T15:30:08.197Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2309,771839,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:07.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Benötigte Regaltiefe',11,'D','The Shelf Depth indicates the depth dimension required on a shelf for a product ',0,'Y','N','N','N','N','N','N','N','N','N','N','Regaltiefe',190,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:07.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:08.227Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:08.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(570)
;

-- 2026-02-04T15:30:08.293Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771839
;

-- 2026-02-04T15:30:08.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771839)
;

-- 2026-02-04T15:30:08.383Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771839
;

-- 2026-02-04T15:30:08.415Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771839, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5304
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Einheiten pro Palette
-- Column: M_Product.UnitsPerPallet
-- 2026-02-04T15:30:08.761Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2310,771840,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:08.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einheiten pro Palette',11,'D','"Einheiten pro Palette" gibt an, wieviele Einheiten dieses Produktes auf eine Palette passen.',0,'Y','N','N','N','N','N','N','N','N','N','N','Einheiten pro Palette',200,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:08.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:08.793Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:08.824Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(604)
;

-- 2026-02-04T15:30:08.856Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771840
;

-- 2026-02-04T15:30:08.887Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771840)
;

-- 2026-02-04T15:30:08.950Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771840
;

-- 2026-02-04T15:30:08.979Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771840, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5305
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Auslaufprodukt
-- Column: M_Product.Discontinued
-- 2026-02-04T15:30:09.303Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2703,771841,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:09.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dieses Produkt ist nicht mehr verfügbar',1,'D','Das Selektionsfeld "Eingestellt" zeigt ein Produkt an, das nicht länger verfügbar ist.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Auslaufprodukt',80,80,1,1,TO_TIMESTAMP('2026-02-04 15:30:09.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:09.336Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:09.366Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(278)
;

-- 2026-02-04T15:30:09.398Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771841
;

-- 2026-02-04T15:30:09.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771841)
;

-- 2026-02-04T15:30:09.488Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771841
;

-- 2026-02-04T15:30:09.519Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771841, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5306
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Eingestellt durch
-- Column: M_Product.DiscontinuedBy
-- 2026-02-04T15:30:09.884Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2704,771842,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:09.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Eingestellt durch',14,'D','"Eingestellt durch" zeigt an, welche Person dieses Produkt eingestellt hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Eingestellt durch',210,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:09.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:09.915Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:09.948Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(279)
;

-- 2026-02-04T15:30:09.979Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771842
;

-- 2026-02-04T15:30:10.008Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771842)
;

-- 2026-02-04T15:30:10.068Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771842
;

-- 2026-02-04T15:30:10.099Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771842, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5307
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Notiz / Zeilentext
-- Column: M_Product.DocumentNote
-- 2026-02-04T15:30:10.433Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3014,771843,1001796,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:10.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zusätzliche Information für den Kunden',60,'D','"Notiz" wird für zusätzliche Informationen zu diesem Produkt verwendet.',0,'Y','N','N','N','N','N','N','N','N','N','N','Notiz / Zeilentext',220,0,999,1,TO_TIMESTAMP('2026-02-04 15:30:10.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:10.464Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:10.495Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001796)
;

-- 2026-02-04T15:30:10.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771843
;

-- 2026-02-04T15:30:10.557Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771843)
;

-- 2026-02-04T15:30:10.618Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771843
;

-- 2026-02-04T15:30:10.648Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771843, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5308
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Kommentar/Hilfe
-- Column: M_Product.Help
-- 2026-02-04T15:30:10.969Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3015,771844,1001219,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:10.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Comment or Hint',60,'D','The Help field contains a hint, comment or help about the use of this item.',0,'Y','N','N','N','N','N','N','N','N','N','N','Kommentar/Hilfe',230,0,999,1,TO_TIMESTAMP('2026-02-04 15:30:10.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:11.007Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:11.036Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001219)
;

-- 2026-02-04T15:30:11.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771844
;

-- 2026-02-04T15:30:11.099Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771844)
;

-- 2026-02-04T15:30:11.156Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771844
;

-- 2026-02-04T15:30:11.187Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771844, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5309
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Klassifizierung
-- Column: M_Product.Classification
-- 2026-02-04T15:30:11.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3016,771845,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:11.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Klassifizierung für die Gruppierung',1,'D','Die "Klassifizierung" kann für die optionale Gruppierung von Produkten verwendet werden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Klassifizierung',240,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:11.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:11.577Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:11.611Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(852)
;

-- 2026-02-04T15:30:11.642Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771845
;

-- 2026-02-04T15:30:11.676Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771845)
;

-- 2026-02-04T15:30:11.736Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771845
;

-- 2026-02-04T15:30:11.767Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771845, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5310
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Kundenbetreuer
-- Column: M_Product.SalesRep_ID
-- 2026-02-04T15:30:12.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3391,771846,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:11.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',14,'D','',0,'Y','N','N','N','N','N','N','N','N','N','N','Kundenbetreuer',250,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:11.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:12.168Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:12.196Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063)
;

-- 2026-02-04T15:30:12.232Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771846
;

-- 2026-02-04T15:30:12.257Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771846)
;

-- 2026-02-04T15:30:12.324Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771846
;

-- 2026-02-04T15:30:12.352Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771846, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5311
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Umsatzrealisierung
-- Column: M_Product.C_RevenueRecognition_ID
-- 2026-02-04T15:30:12.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3909,771847,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:12.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode für die Realisierung des Umsatzes',14,'D','"Umsatzrealisierung" gibt an, auf welche Weise der Umsatz zu diesem Produkt realisiert wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Umsatzrealisierung',260,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:12.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:12.742Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:12.767Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1078)
;

-- 2026-02-04T15:30:12.797Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771847
;

-- 2026-02-04T15:30:12.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771847)
;

-- 2026-02-04T15:30:12.897Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771847
;

-- 2026-02-04T15:30:12.926Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771847, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5312
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Stückliste
-- Column: M_Product.IsBOM
-- 2026-02-04T15:30:13.287Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4708,771848,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:12.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stückliste',1,'D','Das Selektionsfeld "Stückliste" zeigt an, ob dieses Produkt aus Produkten auf einer Stückliste besteht.',0,'Y','N','N','N','N','N','N','N','N','N','N','Stückliste',270,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:12.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:13.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:13.349Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1326)
;

-- 2026-02-04T15:30:13.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771848
;

-- 2026-02-04T15:30:13.413Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771848)
;

-- 2026-02-04T15:30:13.477Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771848
;

-- 2026-02-04T15:30:13.507Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771848, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5313
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Print detail records on invoice
-- Column: M_Product.IsInvoicePrintDetails
-- 2026-02-04T15:30:13.859Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4709,771849,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:13.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Detaileinträge der Stückliste auf Rechnung drucken',1,'D','"Detaileinträge auf Rechnung drucken" zeigt an, ob die Produkte der Stückliste anstelle dieses Produktes auf der Rechnung gedruckt werden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Print detail records on invoice ',280,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:13.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:13.889Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771849 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:13.927Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1175)
;

-- 2026-02-04T15:30:13.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771849
;

-- 2026-02-04T15:30:13.987Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771849)
;

-- 2026-02-04T15:30:14.047Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771849
;

-- 2026-02-04T15:30:14.067Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771849, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5314
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Detaileinträge auf Kommissionierschein drucken
-- Column: M_Product.IsPickListPrintDetails
-- 2026-02-04T15:30:14.431Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4710,771850,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:14.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Details der Stückliste auf Kommissionierschein drucken',1,'D','"Details auf Kommissionierschein drucken" zeigt an, ob die Produkte der Stückliste anstelle dieses Produktes auf dem Kommissionierschein gedruckt werden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Detaileinträge auf Kommissionierschein drucken',290,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:14.102000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:14.464Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:14.496Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1176)
;

-- 2026-02-04T15:30:14.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771850
;

-- 2026-02-04T15:30:14.557Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771850)
;

-- 2026-02-04T15:30:14.617Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771850
;

-- 2026-02-04T15:30:14.647Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771850, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5315
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Verified
-- Column: M_Product.IsVerified
-- 2026-02-04T15:30:15.007Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4711,771851,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:14.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The BOM configuration has been verified',1,'D','The Verified check box indicates if the configuration of this product has been verified.  This is used for products that consist of a bill of materials',0,'Y','N','N','N','N','N','N','N','N','N','N','Verified',300,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:14.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:15.039Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771851 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:15.069Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1177)
;

-- 2026-02-04T15:30:15.100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771851
;

-- 2026-02-04T15:30:15.130Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771851)
;

-- 2026-02-04T15:30:15.189Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771851
;

-- 2026-02-04T15:30:15.218Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771851, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5316
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Process Now
-- Column: M_Product.Processing
-- 2026-02-04T15:30:15.574Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,4712,771852,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:15.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,23,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Process Now',310,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:15.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:15.605Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771852 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:15.634Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524)
;

-- 2026-02-04T15:30:15.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771852
;

-- 2026-02-04T15:30:15.699Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771852)
;

-- 2026-02-04T15:30:15.759Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771852
;

-- 2026-02-04T15:30:15.789Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771852, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5317
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Aktiv
-- Column: M_Product.IsActive
-- 2026-02-04T15:30:16.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1405,771853,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:15.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',60,60,1,1,TO_TIMESTAMP('2026-02-04 15:30:15.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:16.146Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771853 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:16.178Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-04T15:30:16.265Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771853
;

-- 2026-02-04T15:30:16.295Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771853)
;

-- 2026-02-04T15:30:16.355Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771853
;

-- 2026-02-04T15:30:16.386Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771853, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5318
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Suchschlüssel
-- Column: M_Product.Value
-- 2026-02-04T15:30:16.727Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,2011,771854,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:16.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Suchschlüssel',40,40,1,999,1,TO_TIMESTAMP('2026-02-04 15:30:16.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:16.759Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:16.790Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2026-02-04T15:30:16.827Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771854
;

-- 2026-02-04T15:30:16.857Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771854)
;

-- 2026-02-04T15:30:16.919Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771854
;

-- 2026-02-04T15:30:16.947Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771854, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5319
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Name
-- Column: M_Product.Name
-- 2026-02-04T15:30:17.284Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,1410,771855,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:16.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',60,'D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Name',50,50,999,1,TO_TIMESTAMP('2026-02-04 15:30:16.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:17.316Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771855 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:17.339Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2026-02-04T15:30:17.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771855
;

-- 2026-02-04T15:30:17.416Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771855)
;

-- 2026-02-04T15:30:17.477Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771855
;

-- 2026-02-04T15:30:17.508Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771855, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5320
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Aufwandsart
-- Column: M_Product.S_ExpenseType_ID
-- 2026-02-04T15:30:17.848Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6771,771856,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:17.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Expense report type',14,'@ProductType@=E','D',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Aufwandsart',100,100,1,1,TO_TIMESTAMP('2026-02-04 15:30:17.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:17.878Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:17.908Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1776)
;

-- 2026-02-04T15:30:17.937Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771856
;

-- 2026-02-04T15:30:17.967Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771856)
;

-- 2026-02-04T15:30:18.027Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771856
;

-- 2026-02-04T15:30:18.057Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771856, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5389
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Ressource
-- Column: M_Product.S_Resource_ID
-- 2026-02-04T15:30:18.387Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,6773,771857,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:18.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ressource',14,'@ProductType@=R','D',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Ressource',110,110,1,1,TO_TIMESTAMP('2026-02-04 15:30:18.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:18.419Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:18.447Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1777)
;

-- 2026-02-04T15:30:18.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771857
;

-- 2026-02-04T15:30:18.507Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771857)
;

-- 2026-02-04T15:30:18.567Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771857
;

-- 2026-02-04T15:30:18.597Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771857, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5391
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Produktart
-- Column: M_Product.ProductType
-- 2026-02-04T15:30:18.928Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7795,771858,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:18.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Art von Produkt',14,'D','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produktart',90,90,1,1,TO_TIMESTAMP('2026-02-04 15:30:18.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:18.959Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:18.989Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1899)
;

-- 2026-02-04T15:30:19.021Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771858
;

-- 2026-02-04T15:30:19.051Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771858)
;

-- 2026-02-04T15:30:19.112Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771858
;

-- 2026-02-04T15:30:19.141Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771858, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5885
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Bild-URL
-- Column: M_Product.ImageURL
-- 2026-02-04T15:30:19.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7962,771859,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:19.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL of  image',40,'D','URL of image; The image is not stored in the database, but retrieved at runtime. The image can be a gif, jpeg or png.',0,'Y','N','N','N','N','N','N','N','N','N','N','Bild-URL',320,0,999,1,TO_TIMESTAMP('2026-02-04 15:30:19.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:19.550Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:19.581Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1720)
;

-- 2026-02-04T15:30:19.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771859
;

-- 2026-02-04T15:30:19.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771859)
;

-- 2026-02-04T15:30:19.702Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771859
;

-- 2026-02-04T15:30:19.733Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771859, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5908
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Beschreibungs-URL
-- Column: M_Product.DescriptionURL
-- 2026-02-04T15:30:20.100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7963,771860,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:19.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL für die Beschreibung',40,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Beschreibungs-URL',330,0,999,1,TO_TIMESTAMP('2026-02-04 15:30:19.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:20.131Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:20.161Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1920)
;

-- 2026-02-04T15:30:20.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771860
;

-- 2026-02-04T15:30:20.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771860)
;

-- 2026-02-04T15:30:20.285Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771860
;

-- 2026-02-04T15:30:20.315Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771860, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 5909
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Versions-Nr.
-- Column: M_Product.VersionNo
-- 2026-02-04T15:30:20.675Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7973,771861,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:20.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Versionsnummer',20,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Versions-Nr.',340,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:20.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:20.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:20.737Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1949)
;

-- 2026-02-04T15:30:20.767Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771861
;

-- 2026-02-04T15:30:20.797Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771861)
;

-- 2026-02-04T15:30:20.857Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771861
;

-- 2026-02-04T15:30:20.887Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771861, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6125
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Mail Template
-- Column: M_Product.R_MailText_ID
-- 2026-02-04T15:30:21.255Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7972,771862,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:20.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Text templates for mailings',14,'D','The Mail Template indicates the mail template for return messages. Mail text can include variables.  The priority of parsing is User/Contact, Business Partner and then the underlying business object (like Request, Dunning, Workflow object).<br>
So, @Name@ would resolve into the User name (if user is defined defined), then Business Partner name (if business partner is defined) and then the Name of the business object if it has a Name.<br>
For Multi-Lingual systems, the template is translated based on the Business Partner''s language selection.',0,'Y','N','N','N','N','N','N','N','N','N','N','Mail Template',350,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:20.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:21.287Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:21.319Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1515)
;

-- 2026-02-04T15:30:21.350Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771862
;

-- 2026-02-04T15:30:21.380Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771862)
;

-- 2026-02-04T15:30:21.438Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771862
;

-- 2026-02-04T15:30:21.467Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771862, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6126
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Garantie-Tage
-- Column: M_Product.GuaranteeDays
-- 2026-02-04T15:30:21.817Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,7974,771863,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:21.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzahl von Tagen, die das Produkt garantiert oder verfügbar ist',11,'D','Ist der Wert 0, gibt es keine Limitierung. Bei anderen Werten wird das Garantie-Datum durch Addition der Anzahl Tage zum Lieferdatum kalkuliert.',0,'Y','N','N','N','N','N','N','N','N','N','N','Garantie-Tage',360,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:21.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:21.857Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:21.887Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1937)
;

-- 2026-02-04T15:30:21.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771863
;

-- 2026-02-04T15:30:21.947Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771863)
;

-- 2026-02-04T15:30:22.007Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771863
;

-- 2026-02-04T15:30:22.039Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771863, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6127
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Merkmals-Satz
-- Column: M_Product.M_AttributeSet_ID
-- 2026-02-04T15:30:22.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8417,771864,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:22.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals-Satz zum Produkt',14,'D','Definieren Sie Merkmals-Sätze um einem Produkt zusätzliche Merkmale und Eigenschaften zuzuordnen. Sie müssen einen Merkmals-Satz anlegen, um Serien- und Los-Nummern verfolgen zu können.',0,'Y','N','N','N','N','N','N','N','N','N','N','Merkmals-Satz',370,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:22.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:22.427Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:22.457Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2017)
;

-- 2026-02-04T15:30:22.487Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771864
;

-- 2026-02-04T15:30:22.520Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771864)
;

-- 2026-02-04T15:30:22.582Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771864
;

-- 2026-02-04T15:30:22.613Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771864, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6341
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Merkmale
-- Column: M_Product.M_AttributeSetInstance_ID
-- 2026-02-04T15:30:22.997Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8418,771865,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:22.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',26,'D','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','N','N','N','N','N','N','N','N','N','Merkmale',380,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:22.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:23.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:23.060Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2026-02-04T15:30:23.100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771865
;

-- 2026-02-04T15:30:23.132Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771865)
;

-- 2026-02-04T15:30:23.187Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771865
;

-- 2026-02-04T15:30:23.223Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771865, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6342
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Fracht-Kategorie
-- Column: M_Product.M_FreightCategory_ID
-- 2026-02-04T15:30:23.587Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9329,771866,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:23.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Fracht-Kategorie',14,'D','Fracht-Kategorien werden verwendet um die Fracht für den gewählten Spediteur zu berechnen',0,'Y','N','N','N','N','N','N','N','N','N','N','Fracht-Kategorie',390,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:23.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:23.617Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:23.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2111)
;

-- 2026-02-04T15:30:23.681Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771866
;

-- 2026-02-04T15:30:23.707Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771866)
;

-- 2026-02-04T15:30:23.772Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771866
;

-- 2026-02-04T15:30:23.802Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771866, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6842
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Lagerort
-- Column: M_Product.M_Locator_ID
-- 2026-02-04T15:30:24.160Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9420,771867,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:23.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort im Lager',23,'D','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.',0,'Y','N','N','N','N','N','N','N','N','N','N','Lagerort',400,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:23.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:24.196Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:24.227Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448)
;

-- 2026-02-04T15:30:24.257Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771867
;

-- 2026-02-04T15:30:24.289Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771867)
;

-- 2026-02-04T15:30:24.350Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771867
;

-- 2026-02-04T15:30:24.377Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771867, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 7647
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Min. Garantie-Tage
-- Column: M_Product.GuaranteeDaysMin
-- 2026-02-04T15:30:24.747Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9889,771868,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:24.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mindestanzahl Garantie-Tage',11,'D','When selecting batch/products with a guarantee date, the minimum left guarantee days for automatic picking.  You can pick any batch/product manually. ',0,'Y','N','N','N','N','N','N','N','N','N','N','Min. Garantie-Tage',410,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:24.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:24.777Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:24.807Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2197)
;

-- 2026-02-04T15:30:24.843Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771868
;

-- 2026-02-04T15:30:24.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771868)
;

-- 2026-02-04T15:30:24.930Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771868
;

-- 2026-02-04T15:30:24.957Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771868, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 8308
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Beworben im Web-Shop
-- Column: M_Product.IsWebStoreFeatured
-- 2026-02-04T15:30:25.307Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10260,771869,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:24.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn selektiert wird dieses Produkt anfänglich oder bei leeren Suchkriterien angezeigt',1,'D','In the display of products in the Web Store, the product is displayed in the inital view or if no search criteria are entered. To be displayed, the product must be in the price list used.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Beworben im Web-Shop',120,120,1,1,TO_TIMESTAMP('2026-02-04 15:30:24.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:25.338Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:25.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2277)
;

-- 2026-02-04T15:30:25.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771869
;

-- 2026-02-04T15:30:25.427Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771869)
;

-- 2026-02-04T15:30:25.487Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771869
;

-- 2026-02-04T15:30:25.525Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771869, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 8609
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Self-Service
-- Column: M_Product.IsSelfService
-- 2026-02-04T15:30:25.877Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,10261,771870,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:25.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'This is a Self-Service entry or this entry can be changed via Self-Service',1,'D','Self-Service allows users to enter data or update their data.  The flag indicates, that this record was entered or created via Self-Service or that the user can change it via the Self-Service functionality.',0,'Y','N','N','N','N','N','N','N','N','N','N','Self-Service',420,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:25.554000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:25.908Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:25.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2063)
;

-- 2026-02-04T15:30:25.977Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771870
;

-- 2026-02-04T15:30:26.007Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771870)
;

-- 2026-02-04T15:30:26.068Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771870
;

-- 2026-02-04T15:30:26.097Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771870, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 8615
;

-- Field: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> Abweichende Lieferadresse
-- Column: M_Product.IsDropShip
-- 2026-02-04T15:30:26.451Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12147,771871,0,548975,0,TO_TIMESTAMP('2026-02-04 15:30:26.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert',1,'D','Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.',0,'Y','N','N','N','N','N','N','N','N','N','N','Abweichende Lieferadresse',430,0,1,1,TO_TIMESTAMP('2026-02-04 15:30:26.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:26.479Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:26.508Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2466)
;

-- 2026-02-04T15:30:26.546Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771871
;

-- 2026-02-04T15:30:26.570Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771871)
;

-- 2026-02-04T15:30:26.637Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771871
;

-- 2026-02-04T15:30:26.667Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771871, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10412
;

-- Tab: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein
-- Table: AD_Product_Category_BoilerPlate
-- 2026-02-04T15:30:27.008Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,575389,579561,0,548976,541765,542066,'Y',TO_TIMESTAMP('2026-02-04 15:30:26.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','AD_Product_Category_BoilerPlate','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Produktkategorie textbaustein',2020,'N',40,1,TO_TIMESTAMP('2026-02-04 15:30:26.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:27.039Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548976 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-04T15:30:27.071Z
/* DDL */  select update_tab_translation_from_ad_element(579561)
;

-- 2026-02-04T15:30:27.103Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548976)
;

-- 2026-02-04T15:30:27.163Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548976
;

-- 2026-02-04T15:30:27.193Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548976, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 544245
;

-- Field: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> Mandant
-- Column: AD_Product_Category_BoilerPlate.AD_Client_ID
-- 2026-02-04T15:30:27.792Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575373,771872,0,548976,0,TO_TIMESTAMP('2026-02-04 15:30:27.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2026-02-04 15:30:27.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:27.823Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:27.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-04T15:30:27.939Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771872
;

-- 2026-02-04T15:30:27.969Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771872)
;

-- 2026-02-04T15:30:28.030Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771872
;

-- 2026-02-04T15:30:28.060Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771872, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 652580
;

-- Field: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> Organisation
-- Column: AD_Product_Category_BoilerPlate.AD_Org_ID
-- 2026-02-04T15:30:28.420Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575374,771873,0,548976,0,TO_TIMESTAMP('2026-02-04 15:30:28.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Organisation',20,1,1,TO_TIMESTAMP('2026-02-04 15:30:28.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:28.451Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:28.482Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-04T15:30:28.568Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771873
;

-- 2026-02-04T15:30:28.599Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771873)
;

-- 2026-02-04T15:30:28.659Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771873
;

-- 2026-02-04T15:30:28.689Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771873, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 652581
;

-- Field: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> Produktkategorie textbaustein
-- Column: AD_Product_Category_BoilerPlate.AD_Product_Category_BoilerPlate_ID
-- 2026-02-04T15:30:29.062Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575375,771874,0,548976,0,TO_TIMESTAMP('2026-02-04 15:30:28.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Produktkategorie textbaustein',30,1,1,TO_TIMESTAMP('2026-02-04 15:30:28.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:29.092Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:29.121Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579561)
;

-- 2026-02-04T15:30:29.150Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771874
;

-- 2026-02-04T15:30:29.177Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771874)
;

-- 2026-02-04T15:30:29.247Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771874
;

-- 2026-02-04T15:30:29.277Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771874, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 652582
;

-- Field: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> Aktiv
-- Column: AD_Product_Category_BoilerPlate.IsActive
-- 2026-02-04T15:30:29.639Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575380,771875,0,548976,0,TO_TIMESTAMP('2026-02-04 15:30:29.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',40,1,1,TO_TIMESTAMP('2026-02-04 15:30:29.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:29.671Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:29.703Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-04T15:30:29.783Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771875
;

-- 2026-02-04T15:30:29.808Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771875)
;

-- 2026-02-04T15:30:29.872Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771875
;

-- 2026-02-04T15:30:29.902Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771875, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 652583
;

-- Field: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> Textbaustein
-- Column: AD_Product_Category_BoilerPlate.AD_BoilerPlate_ID
-- 2026-02-04T15:30:30.267Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575387,771876,0,548976,0,TO_TIMESTAMP('2026-02-04 15:30:29.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Textbaustein',50,1,1,TO_TIMESTAMP('2026-02-04 15:30:29.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:30.297Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:30.327Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(504410)
;

-- 2026-02-04T15:30:30.358Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771876
;

-- 2026-02-04T15:30:30.387Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771876)
;

-- 2026-02-04T15:30:30.447Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771876
;

-- 2026-02-04T15:30:30.477Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771876, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 652584
;

-- Field: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> Reihenfolge
-- Column: AD_Product_Category_BoilerPlate.SeqNo
-- 2026-02-04T15:30:30.827Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575388,771877,0,548976,0,TO_TIMESTAMP('2026-02-04 15:30:30.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','N','N','N','N','N','N','N','N','N','N','Reihenfolge',60,1,1,TO_TIMESTAMP('2026-02-04 15:30:30.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:30.864Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:30.897Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2026-02-04T15:30:30.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771877
;

-- 2026-02-04T15:30:30.957Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771877)
;

-- 2026-02-04T15:30:31.017Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771877
;

-- 2026-02-04T15:30:31.047Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771877, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 652585
;

-- Field: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> Produkt Kategorie
-- Column: AD_Product_Category_BoilerPlate.M_Product_Category_ID
-- 2026-02-04T15:30:31.411Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575389,771878,0,548976,0,TO_TIMESTAMP('2026-02-04 15:30:31.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',10,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',0,'Y','N','N','N','N','N','N','N','N','N','N','Produkt Kategorie',70,1,1,TO_TIMESTAMP('2026-02-04 15:30:31.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:31.443Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:31.470Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2026-02-04T15:30:31.507Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771878
;

-- 2026-02-04T15:30:31.537Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771878)
;

-- 2026-02-04T15:30:31.597Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771878
;

-- 2026-02-04T15:30:31.627Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771878, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 652586
;

-- Tab: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie
-- Table: M_Product_Category_MaxNetAmount
-- 2026-02-04T15:30:31.958Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,590390,583713,0,548977,542501,542066,'Y',TO_TIMESTAMP('2026-02-04 15:30:31.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','M_Product_Category_MaxNetAmount','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Max. Nettobetrag pro Produktkategorie',2020,'N',50,1,TO_TIMESTAMP('2026-02-04 15:30:31.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:31.990Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548977 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-04T15:30:32.017Z
/* DDL */  select update_tab_translation_from_ad_element(583713)
;

-- 2026-02-04T15:30:32.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548977)
;

-- 2026-02-04T15:30:32.107Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548977
;

-- 2026-02-04T15:30:32.138Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548977, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 548195
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Mandant
-- Column: M_Product_Category_MaxNetAmount.AD_Client_ID
-- 2026-02-04T15:30:32.750Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590382,771879,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:32.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2026-02-04 15:30:32.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:32.783Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:32.813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-04T15:30:32.899Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771879
;

-- 2026-02-04T15:30:32.929Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771879)
;

-- 2026-02-04T15:30:32.989Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771879
;

-- 2026-02-04T15:30:33.019Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771879, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747584
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Organisation
-- Column: M_Product_Category_MaxNetAmount.AD_Org_ID
-- 2026-02-04T15:30:33.368Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590383,771880,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:33.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Organisation',20,1,1,TO_TIMESTAMP('2026-02-04 15:30:33.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:33.400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:33.429Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-04T15:30:33.516Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771880
;

-- 2026-02-04T15:30:33.548Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771880)
;

-- 2026-02-04T15:30:33.609Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771880
;

-- 2026-02-04T15:30:33.638Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771880, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747585
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Erstellt
-- Column: M_Product_Category_MaxNetAmount.Created
-- 2026-02-04T15:30:34.003Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590384,771881,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:33.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Erstellt',30,1,1,TO_TIMESTAMP('2026-02-04 15:30:33.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:34.034Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:34.066Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2026-02-04T15:30:34.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771881
;

-- 2026-02-04T15:30:34.159Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771881)
;

-- 2026-02-04T15:30:34.218Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771881
;

-- 2026-02-04T15:30:34.247Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771881, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747586
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Erstellt durch
-- Column: M_Product_Category_MaxNetAmount.CreatedBy
-- 2026-02-04T15:30:34.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590385,771882,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:34.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Erstellt durch',40,1,1,TO_TIMESTAMP('2026-02-04 15:30:34.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:34.635Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:34.665Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2026-02-04T15:30:34.717Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771882
;

-- 2026-02-04T15:30:34.748Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771882)
;

-- 2026-02-04T15:30:34.818Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771882
;

-- 2026-02-04T15:30:34.847Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771882, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747587
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Aktiv
-- Column: M_Product_Category_MaxNetAmount.IsActive
-- 2026-02-04T15:30:35.210Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590386,771883,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:34.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',50,1,1,TO_TIMESTAMP('2026-02-04 15:30:34.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:35.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:35.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-04T15:30:35.367Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771883
;

-- 2026-02-04T15:30:35.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771883)
;

-- 2026-02-04T15:30:35.457Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771883
;

-- 2026-02-04T15:30:35.502Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771883, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747588
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Aktualisiert
-- Column: M_Product_Category_MaxNetAmount.Updated
-- 2026-02-04T15:30:35.860Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590387,771884,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:35.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktualisiert',60,1,1,TO_TIMESTAMP('2026-02-04 15:30:35.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:35.891Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771884 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:35.925Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2026-02-04T15:30:35.975Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771884
;

-- 2026-02-04T15:30:36.005Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771884)
;

-- 2026-02-04T15:30:36.057Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771884
;

-- 2026-02-04T15:30:36.087Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771884, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747589
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Aktualisiert durch
-- Column: M_Product_Category_MaxNetAmount.UpdatedBy
-- 2026-02-04T15:30:36.450Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590388,771885,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:36.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktualisiert durch',70,1,1,TO_TIMESTAMP('2026-02-04 15:30:36.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:36.481Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771885 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:36.513Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2026-02-04T15:30:36.567Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771885
;

-- 2026-02-04T15:30:36.597Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771885)
;

-- 2026-02-04T15:30:36.660Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771885
;

-- 2026-02-04T15:30:36.687Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771885, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747590
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Max. Nettobetrag pro Produktkategorie
-- Column: M_Product_Category_MaxNetAmount.M_Product_Category_MaxNetAmount_ID
-- 2026-02-04T15:30:37.057Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590389,771886,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:36.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Max. Nettobetrag pro Produktkategorie',80,1,1,TO_TIMESTAMP('2026-02-04 15:30:36.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:37.087Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771886 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:37.118Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583713)
;

-- 2026-02-04T15:30:37.147Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771886
;

-- 2026-02-04T15:30:37.177Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771886)
;

-- 2026-02-04T15:30:37.237Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771886
;

-- 2026-02-04T15:30:37.267Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771886, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747591
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Produkt Kategorie
-- Column: M_Product_Category_MaxNetAmount.M_Product_Category_ID
-- 2026-02-04T15:30:37.633Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590390,771887,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:37.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes',10,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produkt Kategorie',90,1,1,TO_TIMESTAMP('2026-02-04 15:30:37.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:37.665Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771887 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:37.687Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453)
;

-- 2026-02-04T15:30:37.728Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771887
;

-- 2026-02-04T15:30:37.758Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771887)
;

-- 2026-02-04T15:30:37.817Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771887
;

-- 2026-02-04T15:30:37.848Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771887, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747592
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Max. Nettobetrag
-- Column: M_Product_Category_MaxNetAmount.MaxNetAmount
-- 2026-02-04T15:30:38.198Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590391,771888,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:37.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Max. Nettobetrag',100,1,1,TO_TIMESTAMP('2026-02-04 15:30:37.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:38.227Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:38.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583714)
;

-- 2026-02-04T15:30:38.287Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771888
;

-- 2026-02-04T15:30:38.319Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771888)
;

-- 2026-02-04T15:30:38.377Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771888
;

-- 2026-02-04T15:30:38.408Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771888, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747593
;

-- Field: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> Währung
-- Column: M_Product_Category_MaxNetAmount.C_Currency_ID
-- 2026-02-04T15:30:38.770Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590392,771889,0,548977,0,TO_TIMESTAMP('2026-02-04 15:30:38.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Währung',110,1,1,TO_TIMESTAMP('2026-02-04 15:30:38.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-04T15:30:38.802Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-04T15:30:38.833Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2026-02-04T15:30:38.869Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771889
;

-- 2026-02-04T15:30:38.898Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771889)
;

-- 2026-02-04T15:30:38.959Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771889
;

-- 2026-02-04T15:30:38.988Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771889, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 747594
;

-- Tab: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D)
-- UI Section: main
-- 2026-02-04T15:30:39.269Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548973,547496,TO_TIMESTAMP('2026-02-04 15:30:39.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-04 15:30:39.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-04T15:30:39.300Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547496 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-04T15:30:39.363Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547496
;

-- 2026-02-04T15:30:39.393Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547496, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540140
;

-- UI Section: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main
-- UI Column: 10
-- 2026-02-04T15:30:39.689Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549154,547496,TO_TIMESTAMP('2026-02-04 15:30:39.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-04 15:30:39.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10
-- UI Element Group: default
-- 2026-02-04T15:30:39.992Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549154,554778,TO_TIMESTAMP('2026-02-04 15:30:39.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-04 15:30:39.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10 -> default.Name
-- Column: M_Product_Category.Name
-- 2026-02-04T15:30:40.507Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771770,0,548973,554778,646760,'F',TO_TIMESTAMP('2026-02-04 15:30:40.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Name',10,20,20,TO_TIMESTAMP('2026-02-04 15:30:40.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10 -> default.Materialfluß
-- Column: M_Product_Category.MMPolicy
-- 2026-02-04T15:30:40.910Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771780,0,548973,554778,646761,'F',TO_TIMESTAMP('2026-02-04 15:30:40.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Materialfluß',20,50,0,TO_TIMESTAMP('2026-02-04 15:30:40.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10
-- UI Element Group: attributes
-- 2026-02-04T15:30:41.181Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549154,554779,TO_TIMESTAMP('2026-02-04 15:30:41.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','attributes',20,TO_TIMESTAMP('2026-02-04 15:30:41.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Hauptkategorie
-- Column: M_Product_Category.M_Product_Category_Parent_ID
-- 2026-02-04T15:30:41.651Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771781,0,548973,554779,646762,'F',TO_TIMESTAMP('2026-02-04 15:30:41.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Hauptkategorie',10,30,0,TO_TIMESTAMP('2026-02-04 15:30:41.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Markmalssatz
-- Column: M_Product_Category.M_AttributeSet_ID
-- 2026-02-04T15:30:42.050Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771783,0,548973,554779,646763,'F',TO_TIMESTAMP('2026-02-04 15:30:41.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Markmalssatz',20,40,30,TO_TIMESTAMP('2026-02-04 15:30:41.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Qualitätsattribut-Satz
-- Column: M_Product_Category.M_QualityAttributeSet_ID
-- 2026-02-04T15:30:42.447Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771791,0,548973,554779,646764,'F',TO_TIMESTAMP('2026-02-04 15:30:42.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Qualitätsattribut-Satz zum Produkt','Y','N','N','Y','N','N','N',0,'Qualitätsattribut-Satz',25,0,0,TO_TIMESTAMP('2026-02-04 15:30:42.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Produktion ausnehmen
-- Column: M_Product_Category.MRP_Exclude
-- 2026-02-04T15:30:42.847Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771784,0,548973,554779,646765,'F',TO_TIMESTAMP('2026-02-04 15:30:42.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Produktion ausnehmen',30,100,0,TO_TIMESTAMP('2026-02-04 15:30:42.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Mind. Haltbarkeit Tage
-- Column: M_Product_Category.GuaranteeDaysMin
-- 2026-02-04T15:30:43.251Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771789,0,548973,554779,646766,'F',TO_TIMESTAMP('2026-02-04 15:30:42.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Mind. Haltbarkeit Tage',40,0,0,TO_TIMESTAMP('2026-02-04 15:30:42.968000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10
-- UI Element Group: description
-- 2026-02-04T15:30:43.523Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549154,554780,TO_TIMESTAMP('2026-02-04 15:30:43.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','description',30,TO_TIMESTAMP('2026-02-04 15:30:43.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 10 -> description.Beschreibung
-- Column: M_Product_Category.Description
-- 2026-02-04T15:30:43.972Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771771,0,548973,554780,646767,'F',TO_TIMESTAMP('2026-02-04 15:30:43.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Beschreibung',10,0,0,TO_TIMESTAMP('2026-02-04 15:30:43.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main
-- UI Column: 20
-- 2026-02-04T15:30:44.240Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549155,547496,TO_TIMESTAMP('2026-02-04 15:30:44.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-04 15:30:44.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20
-- UI Element Group: flags
-- 2026-02-04T15:30:44.512Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549155,554781,TO_TIMESTAMP('2026-02-04 15:30:44.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2026-02-04 15:30:44.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> flags.Aktiv
-- Column: M_Product_Category.IsActive
-- 2026-02-04T15:30:44.984Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771769,0,548973,554781,646768,'F',TO_TIMESTAMP('2026-02-04 15:30:44.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Aktiv',10,60,40,TO_TIMESTAMP('2026-02-04 15:30:44.638000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> flags.Standard
-- Column: M_Product_Category.IsDefault
-- 2026-02-04T15:30:45.388Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771775,0,548973,554781,646769,'F',TO_TIMESTAMP('2026-02-04 15:30:45.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Standard',20,70,0,TO_TIMESTAMP('2026-02-04 15:30:45.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> flags.Verpackungsmaterial
-- Column: M_Product_Category.IsPackagingMaterial
-- 2026-02-04T15:30:45.779Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771785,0,548973,554781,646770,'F',TO_TIMESTAMP('2026-02-04 15:30:45.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Verpackungsmaterial',30,90,0,TO_TIMESTAMP('2026-02-04 15:30:45.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> flags.Gebinde
-- Column: M_Product_Category.isTradingUnit
-- 2026-02-04T15:30:46.169Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771786,0,548973,554781,646771,'F',TO_TIMESTAMP('2026-02-04 15:30:45.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Gebinde',40,80,0,TO_TIMESTAMP('2026-02-04 15:30:45.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20
-- UI Element Group: rest
-- 2026-02-04T15:30:46.435Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549155,554782,TO_TIMESTAMP('2026-02-04 15:30:46.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','rest',15,TO_TIMESTAMP('2026-02-04 15:30:46.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> rest.Suchschlüssel
-- Column: M_Product_Category.Value
-- 2026-02-04T15:30:46.888Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771774,0,548973,554782,646772,'F',TO_TIMESTAMP('2026-02-04 15:30:46.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Suchschlüssel',10,10,10,TO_TIMESTAMP('2026-02-04 15:30:46.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> rest.Produkt-Nummerfolge
-- Column: M_Product_Category.AD_Sequence_ProductValue_ID
-- 2026-02-04T15:30:47.274Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771790,0,548973,554782,646773,'F',TO_TIMESTAMP('2026-02-04 15:30:47.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Produkt-Nummerfolge',15,0,0,TO_TIMESTAMP('2026-02-04 15:30:47.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> rest.Compensation Group
-- Column: M_Product_Category.C_CompensationGroup_Schema_ID
-- 2026-02-04T15:30:47.664Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771788,0,548973,554782,646774,'F',TO_TIMESTAMP('2026-02-04 15:30:47.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Compensation Group',20,0,0,TO_TIMESTAMP('2026-02-04 15:30:47.387000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20
-- UI Element Group: org
-- 2026-02-04T15:30:47.937Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549155,554783,TO_TIMESTAMP('2026-02-04 15:30:47.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2026-02-04 15:30:47.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> org.Organisation
-- Column: M_Product_Category.AD_Org_ID
-- 2026-02-04T15:30:48.388Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771773,0,548973,554783,646775,'F',TO_TIMESTAMP('2026-02-04 15:30:48.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Organisation',10,110,0,TO_TIMESTAMP('2026-02-04 15:30:48.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> main -> 20 -> org.Mandant
-- Column: M_Product_Category.AD_Client_ID
-- 2026-02-04T15:30:48.778Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771768,0,548973,554783,646776,'F',TO_TIMESTAMP('2026-02-04 15:30:48.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2026-02-04 15:30:48.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Tab: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D)
-- UI Section: advanced edit
-- 2026-02-04T15:30:49.042Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548973,547497,TO_TIMESTAMP('2026-02-04 15:30:48.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-04 15:30:48.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'advanced edit')
;

-- 2026-02-04T15:30:49.072Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547497 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-04T15:30:49.132Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547497
;

-- 2026-02-04T15:30:49.162Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547497, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540260
;

-- UI Section: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> advanced edit
-- UI Column: 10
-- 2026-02-04T15:30:49.403Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549156,547497,TO_TIMESTAMP('2026-02-04 15:30:49.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-04 15:30:49.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> advanced edit -> 10
-- UI Element Group: advanced edit
-- 2026-02-04T15:30:49.677Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549156,554784,TO_TIMESTAMP('2026-02-04 15:30:49.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2026-02-04 15:30:49.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produkt-Kategorie(548973,D) -> advanced edit -> 10 -> advanced edit.Anlage Gruppe
-- Column: M_Product_Category.A_Asset_Group_ID
-- 2026-02-04T15:30:50.141Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771777,0,548973,554784,646777,'F',TO_TIMESTAMP('2026-02-04 15:30:49.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Anlage Gruppe',10,0,0,TO_TIMESTAMP('2026-02-04 15:30:49.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Produkt Kategorie(542066,D) -> Buchführung(548974,D)
-- UI Section: main
-- 2026-02-04T15:30:50.456Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548974,547498,TO_TIMESTAMP('2026-02-04 15:30:50.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-04 15:30:50.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-04T15:30:50.486Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547498 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-04T15:30:50.545Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547498
;

-- 2026-02-04T15:30:50.575Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547498, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540141
;

-- UI Section: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main
-- UI Column: 10
-- 2026-02-04T15:30:50.809Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549157,547498,TO_TIMESTAMP('2026-02-04 15:30:50.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-04 15:30:50.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10
-- UI Element Group: default
-- 2026-02-04T15:30:51.070Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549157,554785,TO_TIMESTAMP('2026-02-04 15:30:50.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-04 15:30:50.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Produkt-Kategorie
-- Column: M_Product_Category_Acct.M_Product_Category_ID
-- 2026-02-04T15:30:51.553Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771796,0,548974,554785,646778,'F',TO_TIMESTAMP('2026-02-04 15:30:51.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','N','N','N','N','Produkt-Kategorie',10,0,0,TO_TIMESTAMP('2026-02-04 15:30:51.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Konten kopieren
-- Column: M_Product_Category_Acct.Processing
-- 2026-02-04T15:30:51.972Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771802,0,548974,554785,646779,'F',TO_TIMESTAMP('2026-02-04 15:30:51.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kopieren und überschreiben der Konten der Produkte dieser Kategorie','If you copy and overwrite the current default values, you may have to repeat previous updates (e.g. set the revenue account, ...). If no Accounting Schema is selected all Accounting Schemas will be updated / inserted for products of this category.','Y','N','N','N','N','N','N','Konten kopieren',20,0,0,TO_TIMESTAMP('2026-02-04 15:30:51.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Buchführungs-Schema
-- Column: M_Product_Category_Acct.C_AcctSchema_ID
-- 2026-02-04T15:30:52.394Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771794,0,548974,554785,646780,'F',TO_TIMESTAMP('2026-02-04 15:30:52.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','N','Y','Y','N','N','Buchführungs-Schema',10,10,0,TO_TIMESTAMP('2026-02-04 15:30:52.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Aktiv
-- Column: M_Product_Category_Acct.IsActive
-- 2026-02-04T15:30:52.798Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771795,0,548974,554785,646781,'F',TO_TIMESTAMP('2026-02-04 15:30:52.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',20,20,0,TO_TIMESTAMP('2026-02-04 15:30:52.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Warenbestand
-- Column: M_Product_Category_Acct.P_Asset_Acct
-- 2026-02-04T15:30:53.192Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771797,0,548974,554785,646782,'F',TO_TIMESTAMP('2026-02-04 15:30:52.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Warenbestand','"Produkt-Asset" bezeichnet das Konto für die Bewertung eines Produktes im Bestand.','Y','N','N','Y','Y','N','N','Warenbestand',30,30,0,TO_TIMESTAMP('2026-02-04 15:30:52.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Produkt Aufwand
-- Column: M_Product_Category_Acct.P_Expense_Acct
-- 2026-02-04T15:30:53.592Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771799,0,548974,554785,646783,'F',TO_TIMESTAMP('2026-02-04 15:30:53.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Produkt Aufwand','The Product Expense Account indicates the account used to record expenses associated with this product.','Y','N','N','Y','Y','N','N','Produkt Aufwand',40,40,0,TO_TIMESTAMP('2026-02-04 15:30:53.321000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Bezugsnebenkosten
-- Column: M_Product_Category_Acct.P_CostAdjustment_Acct
-- 2026-02-04T15:30:54.001Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771808,0,548974,554785,646784,'F',TO_TIMESTAMP('2026-02-04 15:30:53.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Bezugsnebenkosten','Account used for posting product cost adjustments (e.g. landed costs)','Y','N','N','Y','Y','N','N','Bezugsnebenkosten',50,50,0,TO_TIMESTAMP('2026-02-04 15:30:53.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Inventory Clearing
-- Column: M_Product_Category_Acct.P_InventoryClearing_Acct
-- 2026-02-04T15:30:54.442Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771809,0,548974,554785,646785,'F',TO_TIMESTAMP('2026-02-04 15:30:54.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Product Inventory Clearing Account','Account used for posting matched product (item) expenses (e.g. AP Invoice, Invoice Match).  You would use a different account then Product Expense, if you want to differentate service related costs from item related costs. The balance on the clearing account should be zero and accounts for the timing difference between invoice receipt and matching.
','Y','N','N','Y','Y','N','N','Inventory Clearing',60,60,0,TO_TIMESTAMP('2026-02-04 15:30:54.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Produkt Vertriebsausgaben
-- Column: M_Product_Category_Acct.P_COGS_Acct
-- 2026-02-04T15:30:54.828Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771798,0,548974,554785,646786,'F',TO_TIMESTAMP('2026-02-04 15:30:54.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Produkt Vertriebsausgaben','The Product COGS Account indicates the account used when recording costs associated with this product.','Y','N','N','Y','Y','N','N','Produkt Vertriebsausgaben',60,70,0,TO_TIMESTAMP('2026-02-04 15:30:54.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Preisdifferenz Bestellung
-- Column: M_Product_Category_Acct.P_PurchasePriceVariance_Acct
-- 2026-02-04T15:30:55.230Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771800,0,548974,554785,646787,'F',TO_TIMESTAMP('2026-02-04 15:30:54.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Differenz zwischen Standard Kosten und Preis in Bestellung.','The Purchase Price Variance is used in Standard Costing. It reflects the difference between the Standard Cost and the Purchase Order Price.','Y','N','N','Y','Y','N','N','Preisdifferenz Bestellung',80,80,0,TO_TIMESTAMP('2026-02-04 15:30:54.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Preisdifferenz Einkauf Rechnung
-- Column: M_Product_Category_Acct.P_InvoicePriceVariance_Acct
-- 2026-02-04T15:30:55.642Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771803,0,548974,554785,646788,'F',TO_TIMESTAMP('2026-02-04 15:30:55.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Preisdifferenz Einkauf Rechnung','The Invoice Price Variance is used reflects the difference between the current Costs and the Invoice Price.','Y','N','N','Y','Y','N','N','Preisdifferenz Einkauf Rechnung',90,90,0,TO_TIMESTAMP('2026-02-04 15:30:55.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Erhaltene Rabatte
-- Column: M_Product_Category_Acct.P_TradeDiscountRec_Acct
-- 2026-02-04T15:30:56.038Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771804,0,548974,554785,646789,'F',TO_TIMESTAMP('2026-02-04 15:30:55.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für erhaltene Rabatte','The Trade Discount Receivables Account indicates the account for received trade discounts in vendor invoices','Y','N','N','Y','Y','N','N','Erhaltene Rabatte',100,100,0,TO_TIMESTAMP('2026-02-04 15:30:55.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Gewährte Rabatte
-- Column: M_Product_Category_Acct.P_TradeDiscountGrant_Acct
-- 2026-02-04T15:30:56.438Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771805,0,548974,554785,646790,'F',TO_TIMESTAMP('2026-02-04 15:30:56.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für gewährte Rabatte','The Trade Discount Granted Account indicates the account for granted trade discount in sales invoices','Y','N','N','Y','Y','N','N','Gewährte Rabatte',110,110,0,TO_TIMESTAMP('2026-02-04 15:30:56.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Produkt Ertrag
-- Column: M_Product_Category_Acct.P_Revenue_Acct
-- 2026-02-04T15:30:56.825Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771801,0,548974,554785,646791,'F',TO_TIMESTAMP('2026-02-04 15:30:56.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konto für Produkt Ertrag','The Product Revenue Account indicates the account used for recording sales revenue for this product.','Y','N','N','Y','Y','N','N','Produkt Ertrag',120,120,0,TO_TIMESTAMP('2026-02-04 15:30:56.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Unfertige Leistungen
-- Column: M_Product_Category_Acct.P_WIP_Acct
-- 2026-02-04T15:30:57.213Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771819,0,548974,554785,646792,'F',TO_TIMESTAMP('2026-02-04 15:30:56.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Das Konto Unfertige Leistungen wird im Produktionaauftrag verwendet','Y','N','N','Y','Y','N','N','Unfertige Leistungen',130,130,0,TO_TIMESTAMP('2026-02-04 15:30:56.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Floor Stock
-- Column: M_Product_Category_Acct.P_FloorStock_Acct
-- 2026-02-04T15:30:57.620Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771812,0,548974,554785,646793,'F',TO_TIMESTAMP('2026-02-04 15:30:57.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Floor Stock account is the account used Manufacturing Order','The Floor Stock is used for accounting the component with Issue method  is set Floor stock  into Bill of Material & Formula Window.

The components with Issue Method  defined as Floor stock is acounting next way:

Debit Floor Stock Account
Credit Work in Process Account ','Y','N','N','Y','Y','N','N','Floor Stock',140,140,0,TO_TIMESTAMP('2026-02-04 15:30:57.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Method Change Variance
-- Column: M_Product_Category_Acct.P_MethodChangeVariance_Acct
-- 2026-02-04T15:30:58.021Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771814,0,548974,554785,646794,'F',TO_TIMESTAMP('2026-02-04 15:30:57.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Method Change Variance account is the account used Manufacturing Order','The Method Change Variance is used in Standard Costing. It reflects the difference between the Standard BOM , Standard Manufacturing Workflow and Manufacturing BOM Manufacturing Workflow.

If you change the method the manufacturing defined in BOM or Workflow Manufacturig then this variance is generate.','Y','N','N','Y','Y','N','N','Method Change Variance',150,150,0,TO_TIMESTAMP('2026-02-04 15:30:57.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Usage Variance
-- Column: M_Product_Category_Acct.P_UsageVariance_Acct
-- 2026-02-04T15:30:58.415Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771818,0,548974,554785,646795,'F',TO_TIMESTAMP('2026-02-04 15:30:58.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Usage Variance account is the account used Manufacturing Order','The Usage Variance is used in Standard Costing. It reflects the difference between the  Quantities of Standard BOM  or Time Standard Manufacturing Workflow and Quantities of Manufacturing BOM or Time Manufacturing Workflow of Manufacturing Order.

If you change the Quantities or Time  defined in BOM or Workflow Manufacturig then this variance is generate.','Y','N','N','Y','Y','N','N','Usage Variance',160,160,0,TO_TIMESTAMP('2026-02-04 15:30:58.141000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Rate Variance
-- Column: M_Product_Category_Acct.P_RateVariance_Acct
-- 2026-02-04T15:30:58.814Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771817,0,548974,554785,646796,'F',TO_TIMESTAMP('2026-02-04 15:30:58.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Rate Variance account is the account used Manufacturing Order','The Rate Variance is used in Standard Costing. It reflects the difference between the Standard Cost Rates and  The Cost Rates of Manufacturing Order.

If you change the Standard Rates then this variance is generate.','Y','N','N','Y','Y','N','N','Rate Variance',170,170,0,TO_TIMESTAMP('2026-02-04 15:30:58.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Mix Variance
-- Column: M_Product_Category_Acct.P_MixVariance_Acct
-- 2026-02-04T15:30:59.210Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771815,0,548974,554785,646797,'F',TO_TIMESTAMP('2026-02-04 15:30:58.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Mix Variance account is the account used Manufacturing Order','The Mix Variance is used when a co-product  received in Inventory  is different the quantity  expected
','Y','N','N','Y','Y','N','N','Mix Variance',180,180,0,TO_TIMESTAMP('2026-02-04 15:30:58.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Labor
-- Column: M_Product_Category_Acct.P_Labor_Acct
-- 2026-02-04T15:30:59.604Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771813,0,548974,554785,646798,'F',TO_TIMESTAMP('2026-02-04 15:30:59.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Labor account is the account used Manufacturing Order','The Labor is used for accounting the productive Labor
','Y','N','N','Y','Y','N','N','Labor',190,190,0,TO_TIMESTAMP('2026-02-04 15:30:59.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Burden
-- Column: M_Product_Category_Acct.P_Burden_Acct
-- 2026-02-04T15:31:00Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771810,0,548974,554785,646799,'F',TO_TIMESTAMP('2026-02-04 15:30:59.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Burden account is the account used Manufacturing Order','The Burden is used for accounting the Burden','Y','N','N','Y','Y','N','N','Burden',200,200,0,TO_TIMESTAMP('2026-02-04 15:30:59.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Cost Of Production
-- Column: M_Product_Category_Acct.P_CostOfProduction_Acct
-- 2026-02-04T15:31:00.391Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771811,0,548974,554785,646800,'F',TO_TIMESTAMP('2026-02-04 15:31:00.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Cost Of Production account is the account used Manufacturing Order','The Cost Of Production is used for accounting Non productive Labor
','Y','N','N','Y','Y','N','N','Cost Of Production',210,210,0,TO_TIMESTAMP('2026-02-04 15:31:00.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Outside Processing
-- Column: M_Product_Category_Acct.P_OutsideProcessing_Acct
-- 2026-02-04T15:31:00.798Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771816,0,548974,554785,646801,'F',TO_TIMESTAMP('2026-02-04 15:31:00.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Outside Processing Account is the account used in Manufacturing Order','The Outside Processing Account is used for accounting the Outside Processing','Y','N','N','Y','Y','N','N','Outside Processing',220,220,0,TO_TIMESTAMP('2026-02-04 15:31:00.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Overhead
-- Column: M_Product_Category_Acct.P_Overhead_Acct
-- 2026-02-04T15:31:01.192Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771820,0,548974,554785,646802,'F',TO_TIMESTAMP('2026-02-04 15:31:00.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Overhead account is the account used  in Manufacturing Order ','Y','N','N','Y','Y','N','N','Overhead',230,230,0,TO_TIMESTAMP('2026-02-04 15:31:00.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Scrap
-- Column: M_Product_Category_Acct.P_Scrap_Acct
-- 2026-02-04T15:31:01.584Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771821,0,548974,554785,646803,'F',TO_TIMESTAMP('2026-02-04 15:31:01.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Scrap account is the account used  in Manufacturing Order ','Y','N','N','Y','Y','N','N','Scrap',240,240,0,TO_TIMESTAMP('2026-02-04 15:31:01.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Kostenrechnungsmethode
-- Column: M_Product_Category_Acct.CostingMethod
-- 2026-02-04T15:31:01.988Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771806,0,548974,554785,646804,'F',TO_TIMESTAMP('2026-02-04 15:31:01.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gibt an, wie die Kosten berechnet werden','"Kostenrechnungsmethode" gibt an, wie die Kosten berechnet werden (Standard, Durchschnitt, LiFo, FiFo). Die Standardmethode ist auf Ebene des Kontenrahmens definiert und kann optional in der Produktkategorie überschrieben werden. Die Methode der Kostenrechnung kann nicht zur Art der Materialbewegung (definiert in der Produktkategorie) im Widerspruch stehen.','Y','N','N','Y','Y','N','N','Kostenrechnungsmethode',250,250,0,TO_TIMESTAMP('2026-02-04 15:31:01.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Kostenrechnungsstufe
-- Column: M_Product_Category_Acct.CostingLevel
-- 2026-02-04T15:31:02.389Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771807,0,548974,554785,646805,'F',TO_TIMESTAMP('2026-02-04 15:31:02.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die unterste Stufe um Kosteninformation zu kumulieren','Wenn Sie verschiedene Kosten pro Organisation (Lager) oder pro Charge/Los verwalten möchten dann stellen Sie sicher, dass Sie diese für jede Organistaion und jede Charge bzw. jedes Los definieren. Die Kostenrechnungsstufe ist pro Kontenrahmens definiert und kann durch die Produktkategorie oder  den Kontenrahmen überschrieben werden.','Y','N','N','Y','Y','N','N','Kostenrechnungsstufe',260,260,0,TO_TIMESTAMP('2026-02-04 15:31:02.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Organisation
-- Column: M_Product_Category_Acct.AD_Org_ID
-- 2026-02-04T15:31:02.782Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771793,0,548974,554785,646806,'F',TO_TIMESTAMP('2026-02-04 15:31:02.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Organisation',270,270,0,TO_TIMESTAMP('2026-02-04 15:31:02.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Produkt Kategorie(542066,D) -> Buchführung(548974,D) -> main -> 10 -> default.Mandant
-- Column: M_Product_Category_Acct.AD_Client_ID
-- 2026-02-04T15:31:03.172Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771792,0,548974,554785,646807,'F',TO_TIMESTAMP('2026-02-04 15:31:02.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',280,0,0,TO_TIMESTAMP('2026-02-04 15:31:02.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Tab: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D)
-- UI Section: main
-- 2026-02-04T15:31:03.496Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548975,547499,TO_TIMESTAMP('2026-02-04 15:31:03.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-04 15:31:03.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-04T15:31:03.526Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547499 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-04T15:31:03.586Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547499
;

-- 2026-02-04T15:31:03.616Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547499, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540142
;

-- UI Section: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main
-- UI Column: 10
-- 2026-02-04T15:31:03.863Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549158,547499,TO_TIMESTAMP('2026-02-04 15:31:03.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-04 15:31:03.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10
-- UI Element Group: default
-- 2026-02-04T15:31:04.138Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549158,554786,TO_TIMESTAMP('2026-02-04 15:31:03.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-04 15:31:03.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Produkt-Kategorie
-- Column: M_Product.M_Product_Category_ID
-- 2026-02-04T15:31:04.614Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771831,0,548975,554786,646808,'F',TO_TIMESTAMP('2026-02-04 15:31:04.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','N','N','N','N','Produkt-Kategorie',10,0,0,TO_TIMESTAMP('2026-02-04 15:31:04.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Suchschlüssel
-- Column: M_Product.Value
-- 2026-02-04T15:31:05.028Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771854,0,548975,554786,646809,'F',TO_TIMESTAMP('2026-02-04 15:31:04.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','Y','N','N','Suchschlüssel',10,10,0,TO_TIMESTAMP('2026-02-04 15:31:04.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Name
-- Column: M_Product.Name
-- 2026-02-04T15:31:05.433Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771855,0,548975,554786,646810,'F',TO_TIMESTAMP('2026-02-04 15:31:05.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','N','Y','Y','N','N','Name',20,20,0,TO_TIMESTAMP('2026-02-04 15:31:05.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Aktiv
-- Column: M_Product.IsActive
-- 2026-02-04T15:31:05.833Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771853,0,548975,554786,646811,'F',TO_TIMESTAMP('2026-02-04 15:31:05.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',30,30,0,TO_TIMESTAMP('2026-02-04 15:31:05.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Zusammenfassungseintrag
-- Column: M_Product.IsSummary
-- 2026-02-04T15:31:06.228Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771824,0,548975,554786,646812,'F',TO_TIMESTAMP('2026-02-04 15:31:05.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist ein Zusammenfassungseintrag','A summary entity represents a branch in a tree rather than an end-node. Summary entities are used for reporting and do not have own values.','Y','N','N','Y','Y','N','N','Zusammenfassungseintrag',40,40,0,TO_TIMESTAMP('2026-02-04 15:31:05.953000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Eingestellt
-- Column: M_Product.Discontinued
-- 2026-02-04T15:31:06.616Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771841,0,548975,554786,646813,'F',TO_TIMESTAMP('2026-02-04 15:31:06.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dieses Produkt ist nicht mehr verfügbar','Das Selektionsfeld "Eingestellt" zeigt ein Produkt an, das nicht länger verfügbar ist.','Y','N','N','Y','Y','N','N','Eingestellt',50,50,0,TO_TIMESTAMP('2026-02-04 15:31:06.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Beworben im Web-Shop
-- Column: M_Product.IsWebStoreFeatured
-- 2026-02-04T15:31:07.008Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771869,0,548975,554786,646814,'F',TO_TIMESTAMP('2026-02-04 15:31:06.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn selektiert wird dieses Produkt anfänglich oder bei leeren Suchkriterien angezeigt','In the display of products in the Web Store, the product is displayed in the inital view or if no search criteria are entered. To be displayed, the product must be in the price list used.','Y','N','N','Y','N','N','N','Beworben im Web-Shop',60,0,0,TO_TIMESTAMP('2026-02-04 15:31:06.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Produktart
-- Column: M_Product.ProductType
-- 2026-02-04T15:31:07.428Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771858,0,548975,554786,646815,'F',TO_TIMESTAMP('2026-02-04 15:31:07.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Art von Produkt','Aus der Produktart ergeben auch sich Vorgaben für die Buchhaltung.','Y','N','N','Y','Y','N','N','Produktart',70,60,0,TO_TIMESTAMP('2026-02-04 15:31:07.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Ressource
-- Column: M_Product.S_Resource_ID
-- 2026-02-04T15:31:07.843Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771857,0,548975,554786,646816,'F',TO_TIMESTAMP('2026-02-04 15:31:07.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ressource','Y','N','N','Y','Y','N','N','Ressource',80,80,0,TO_TIMESTAMP('2026-02-04 15:31:07.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Aufwandsart
-- Column: M_Product.S_ExpenseType_ID
-- 2026-02-04T15:31:08.241Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771856,0,548975,554786,646817,'F',TO_TIMESTAMP('2026-02-04 15:31:07.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Expense report type','Y','N','N','Y','Y','N','N','Aufwandsart',90,70,0,TO_TIMESTAMP('2026-02-04 15:31:07.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Organisation
-- Column: M_Product.AD_Org_ID
-- 2026-02-04T15:31:08.654Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771827,0,548975,554786,646818,'F',TO_TIMESTAMP('2026-02-04 15:31:08.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Organisation',100,90,0,TO_TIMESTAMP('2026-02-04 15:31:08.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Produkt Kategorie(542066,D) -> Zugeordnete Produkte(548975,D) -> main -> 10 -> default.Mandant
-- Column: M_Product.AD_Client_ID
-- 2026-02-04T15:31:09.050Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771826,0,548975,554786,646819,'F',TO_TIMESTAMP('2026-02-04 15:31:08.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',110,0,0,TO_TIMESTAMP('2026-02-04 15:31:08.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Tab: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D)
-- UI Section: main
-- 2026-02-04T15:31:09.378Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548976,547500,TO_TIMESTAMP('2026-02-04 15:31:09.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-02-04 15:31:09.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-04T15:31:09.409Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547500 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-04T15:31:09.468Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547500
;

-- 2026-02-04T15:31:09.498Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547500, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 543355
;

-- UI Section: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> main
-- UI Column: 10
-- 2026-02-04T15:31:09.728Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549159,547500,TO_TIMESTAMP('2026-02-04 15:31:09.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-04 15:31:09.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> main -> 10
-- UI Element Group: main
-- 2026-02-04T15:31:10.540Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549159,554787,TO_TIMESTAMP('2026-02-04 15:31:10.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-02-04 15:31:10.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> main -> 10 -> main.Textbaustein
-- Column: AD_Product_Category_BoilerPlate.AD_BoilerPlate_ID
-- 2026-02-04T15:31:10.984Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771876,0,548976,554787,646820,'F',TO_TIMESTAMP('2026-02-04 15:31:10.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Textbaustein',10,0,0,TO_TIMESTAMP('2026-02-04 15:31:10.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> main -> 10 -> main.Aktiv
-- Column: AD_Product_Category_BoilerPlate.IsActive
-- 2026-02-04T15:31:11.378Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771875,0,548976,554787,646821,'F',TO_TIMESTAMP('2026-02-04 15:31:11.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2026-02-04 15:31:11.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> main -> 10 -> main.Reihenfolge
-- Column: AD_Product_Category_BoilerPlate.SeqNo
-- 2026-02-04T15:31:11.780Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771877,0,548976,554787,646822,'F',TO_TIMESTAMP('2026-02-04 15:31:11.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',30,0,0,TO_TIMESTAMP('2026-02-04 15:31:11.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> main -> 10 -> main.Organisation
-- Column: AD_Product_Category_BoilerPlate.AD_Org_ID
-- 2026-02-04T15:31:12.176Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771873,0,548976,554787,646823,'F',TO_TIMESTAMP('2026-02-04 15:31:11.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',40,0,0,TO_TIMESTAMP('2026-02-04 15:31:11.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Produktkategorie textbaustein(548976,D) -> main -> 10 -> main.Mandant
-- Column: AD_Product_Category_BoilerPlate.AD_Client_ID
-- 2026-02-04T15:31:12.577Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771872,0,548976,554787,646824,'F',TO_TIMESTAMP('2026-02-04 15:31:12.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',50,0,0,TO_TIMESTAMP('2026-02-04 15:31:12.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D)
-- UI Section: main
-- 2026-02-04T15:31:12.912Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548977,547501,TO_TIMESTAMP('2026-02-04 15:31:12.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-04 15:31:12.764000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-04T15:31:12.945Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547501 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-04T15:31:13.006Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547501
;

-- 2026-02-04T15:31:13.038Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547501, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546736
;

-- UI Section: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> main
-- UI Column: 10
-- 2026-02-04T15:31:13.281Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549160,547501,TO_TIMESTAMP('2026-02-04 15:31:13.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-04 15:31:13.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> main -> 10
-- UI Element Group: default
-- 2026-02-04T15:31:13.597Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549160,554788,TO_TIMESTAMP('2026-02-04 15:31:13.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-04 15:31:13.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> main -> 10 -> default.Max. Nettobetrag
-- Column: M_Product_Category_MaxNetAmount.MaxNetAmount
-- 2026-02-04T15:31:14.047Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771888,0,548977,554788,646825,'F',TO_TIMESTAMP('2026-02-04 15:31:13.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Max. Nettobetrag',10,10,0,TO_TIMESTAMP('2026-02-04 15:31:13.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> main -> 10 -> default.Währung
-- Column: M_Product_Category_MaxNetAmount.C_Currency_ID
-- 2026-02-04T15:31:14.447Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771889,0,548977,554788,646826,'F',TO_TIMESTAMP('2026-02-04 15:31:14.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','Y','N','N','Währung',20,20,0,TO_TIMESTAMP('2026-02-04 15:31:14.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Produkt Kategorie(542066,D) -> Max. Nettobetrag pro Produktkategorie(548977,D) -> main -> 10 -> default.Aktiv
-- Column: M_Product_Category_MaxNetAmount.IsActive
-- 2026-02-04T15:31:14.848Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771883,0,548977,554788,646827,'F',TO_TIMESTAMP('2026-02-04 15:31:14.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',30,30,0,TO_TIMESTAMP('2026-02-04 15:31:14.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- Element: null
-- 2026-02-04T15:50:51.981Z
UPDATE AD_Element_Trl SET PO_Help='',Updated=TO_TIMESTAMP('2026-02-04 15:50:51.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574342 AND AD_Language='de_CH'
;

-- 2026-02-04T15:50:52.012Z
UPDATE AD_Element base SET PO_Help=trl.PO_Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:51:23.586Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574342,'de_CH')
;

-- Element: null
-- 2026-02-04T15:52:32.610Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-04 15:52:32.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='en_US'
;

-- 2026-02-04T15:52:32.670Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'en_US')
;

-- Element: null
-- 2026-02-04T15:52:50.996Z
UPDATE AD_Element_Trl SET PrintName='Product Category OLD',Updated=TO_TIMESTAMP('2026-02-04 15:52:50.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='de_DE'
;

-- 2026-02-04T15:52:51.050Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:53:30.461Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584494,'de_DE')
;

-- 2026-02-04T15:53:30.495Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'de_DE')
;

-- Element: null
-- 2026-02-04T15:55:11.381Z
UPDATE AD_Element_Trl SET Description='Maintain Product Categories',Updated=TO_TIMESTAMP('2026-02-04 15:55:11.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='en_US'
;

-- 2026-02-04T15:55:11.412Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:55:18.469Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'en_US')
;

-- Element: null
-- 2026-02-04T15:55:33.828Z
UPDATE AD_Element_Trl SET Description='Eine Produktkategorie definiert eine Gruppe von bestimmten Produkten. Diese Gruppen können zur Erzeugung von Preislisten, Definition von Margen und zur einfachen Zuordnung verschiedener Buchhaltungsparamter zu Produkten verwendet werden.',Updated=TO_TIMESTAMP('2026-02-04 15:55:33.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='de_DE'
;

-- 2026-02-04T15:55:33.855Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:56:14.268Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584494,'de_DE')
;

-- 2026-02-04T15:56:14.296Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'de_DE')
;

-- Element: null
-- 2026-02-04T15:57:12.012Z
UPDATE AD_Element_Trl SET Description='',Updated=TO_TIMESTAMP('2026-02-04 15:57:12.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='de_DE'
;

-- 2026-02-04T15:57:12.042Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:57:23.801Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584494,'de_DE')
;

-- 2026-02-04T15:57:23.831Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'de_DE')
;

-- Element: null
-- 2026-02-04T15:58:21.302Z
UPDATE AD_Element_Trl SET Description='Verwalten von Produkt-Kategorien', Help='Eine Produktkategorie definiert eine Gruppe von bestimmten Produkten. Diese Gruppen können zur Erzeugung von Preislisten, Definition von Margen und zur einfachen Zuordnung verschiedener Buchhaltungsparamter zu Produkten verwendet werden.',Updated=TO_TIMESTAMP('2026-02-04 15:58:21.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='de_DE'
;

-- 2026-02-04T15:58:21.333Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:58:58.107Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584494,'de_DE')
;

-- 2026-02-04T15:58:58.137Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'de_DE')
;

-- Element: null
-- 2026-02-04T15:59:01.250Z
UPDATE AD_Element_Trl SET Help='Eine Produktkategorie definiert eine Gruppe von bestimmten Produkten. Diese Gruppen können zur Erzeugung von Preislisten, Definition von Margen und zur einfachen Zuordnung verschiedener Buchhaltungsparamter zu Produkten verwendet werden.',Updated=TO_TIMESTAMP('2026-02-04 15:59:01.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='de_CH'
;

-- 2026-02-04T15:59:01.280Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:59:07.812Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'de_CH')
;

-- Element: null
-- 2026-02-04T15:59:14.719Z
UPDATE AD_Element_Trl SET Help='Eine Produktkategorie definiert eine Gruppe von bestimmten Produkten. Diese Gruppen können zur Erzeugung von Preislisten, Definition von Margen und zur einfachen Zuordnung verschiedener Buchhaltungsparamter zu Produkten verwendet werden.',Updated=TO_TIMESTAMP('2026-02-04 15:59:14.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='fr_CH'
;

-- 2026-02-04T15:59:14.749Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T15:59:39.112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'fr_CH')
;

-- Element: null
-- 2026-02-04T15:59:56.187Z
UPDATE AD_Element_Trl SET Help='The Product Category allows you to define different groups of products.  These groups can be used in generating Price Lists, defining margins and for easily assigning different accounting parameters for products.',Updated=TO_TIMESTAMP('2026-02-04 15:59:56.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584494 AND AD_Language='en_US'
;

-- 2026-02-04T15:59:56.214Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-04T16:00:11.965Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584494,'en_US')
;

-- Window: Produkt Kategorie, InternalName=null
-- 2026-02-04T16:04:00.098Z
UPDATE AD_Window SET IsOverrideInMenu='Y',Updated=TO_TIMESTAMP('2026-02-04 16:04:00.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542066
;

-- Element: M_Product_Category_Parent_ID
-- 2026-02-06T12:26:40.851Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Übergeordnete Produkt Kategorie', PrintName='Übergeordnete Produkt Kategorie',Updated=TO_TIMESTAMP('2026-02-06 12:26:40.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=50070 AND AD_Language='de_DE'
;

-- 2026-02-06T12:26:40.870Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-06T12:26:57.224Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(50070,'de_DE')
;

-- 2026-02-06T12:26:57.256Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(50070,'de_DE')
;

-- Element: M_Product_Category_Parent_ID
-- 2026-02-06T12:27:00.583Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-06 12:27:00.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=50070 AND AD_Language='fr_CH'
;

-- 2026-02-06T12:27:00.649Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(50070,'fr_CH')
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Mind. Haltbarkeit Tage
-- Column: M_Product_Category.GuaranteeDaysMin
-- 2026-02-06T12:31:13.277Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-06 12:31:13.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646766
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Merkmalssatz
-- Column: M_Product_Category.M_AttributeSet_ID
-- 2026-02-06T12:32:12.705Z
UPDATE AD_UI_Element SET Name='Merkmalssatz',Updated=TO_TIMESTAMP('2026-02-06 12:32:12.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646763
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Merkmalssatz
-- Column: M_Product_Category.M_AttributeSet_ID
-- 2026-02-06T12:43:40.069Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-06 12:43:40.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646763
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> main -> 10 -> default.Merkmals-Satz
-- Column: M_Product_Category.M_AttributeSet_ID
-- 2026-02-06T12:43:53.131Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771783,0,548973,554778,646861,'F',TO_TIMESTAMP('2026-02-06 12:43:52.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals-Satz zum Produkt','Definieren Sie Merkmals-Sätze um einem Produkt zusätzliche Merkmale und Eigenschaften zuzuordnen. Sie müssen einen Merkmals-Satz anlegen, um Serien- und Los-Nummern verfolgen zu können.','Y','N','N','Y','N','N','N',0,'Merkmals-Satz',30,0,0,TO_TIMESTAMP('2026-02-06 12:43:52.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> main -> 10 -> default.Merkmals-Satz
-- Column: M_Product_Category.M_AttributeSet_ID
-- 2026-02-06T12:45:16.716Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2026-02-06 12:45:16.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646861
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> main -> 10 -> default.Materialfluß
-- Column: M_Product_Category.MMPolicy
-- 2026-02-06T12:45:57.291Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2026-02-06 12:45:57.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646761
;

-- 2026-02-06T13:17:41.840Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584501,0,TO_TIMESTAMP('2026-02-06 13:17:41.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Merkmal','U','Produkt-Merkmale wie Farbe, Größe, usw.','Y','Merkmal OLD','Merkmal OLD',TO_TIMESTAMP('2026-02-06 13:17:41.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:17:41.863Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584501 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-02-06T13:20:30.382Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-06 13:20:30.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574064 AND AD_Language='de_DE'
;

-- 2026-02-06T13:20:30.427Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(574064,'de_DE')
;

-- 2026-02-06T13:20:30.448Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574064,'de_DE')
;

-- Element: null
-- 2026-02-06T13:20:37.885Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-06 13:20:37.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584501 AND AD_Language='de_DE'
;

-- 2026-02-06T13:20:37.938Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584501,'de_DE')
;

-- 2026-02-06T13:20:37.961Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584501,'de_DE')
;

-- Element: null
-- 2026-02-06T13:21:27.371Z
UPDATE AD_Element_Trl SET Description='Product Attribute', Help='Product Attribute like Color, Size, etc.', IsTranslated='Y', Name='Attribute OLD', PrintName='Attribute OLD',Updated=TO_TIMESTAMP('2026-02-06 13:21:27.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584501 AND AD_Language='en_US'
;

-- 2026-02-06T13:21:27.392Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-06T13:22:05.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584501,'en_US')
;

-- Element: null
-- 2026-02-06T13:22:05.469Z
UPDATE AD_Element_Trl SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-06 13:22:05.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584501 AND AD_Language='de_DE'
;

-- 2026-02-06T13:22:05.516Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584501,'de_DE')
;

-- 2026-02-06T13:22:05.538Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584501,'de_DE')
;

-- Element: null
-- 2026-02-06T13:23:26.640Z
UPDATE AD_Element_Trl SET IsActive='Y',Updated=TO_TIMESTAMP('2026-02-06 13:23:26.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584501 AND AD_Language='de_DE'
;

-- 2026-02-06T13:23:26.704Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584501,'de_DE')
;

-- 2026-02-06T13:23:26.742Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584501,'de_DE')
;

-- 2026-02-06T13:26:33.981Z
UPDATE AD_Element SET EntityType='U',Updated=TO_TIMESTAMP('2026-02-06 13:26:33.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574064
;

-- 2026-02-06T13:26:34.090Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574064,'de_DE')
;

-- Window: Merkmal OLD, InternalName=260 (Todo: Set Internal Name for UI testing)
-- 2026-02-06T13:27:51.060Z
UPDATE AD_Window SET AD_Element_ID=584501, Description='Produkt-Merkmal', Help='Produkt-Merkmale wie Farbe, Größe, usw.', Name='Merkmal OLD',Updated=TO_TIMESTAMP('2026-02-06 13:27:50.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=260
;

-- 2026-02-06T13:27:51.084Z
UPDATE AD_Window_Trl trl SET Name='Merkmal OLD' WHERE AD_Window_ID=260 AND AD_Language='de_DE'
;

-- Name: Merkmal OLD
-- Action Type: W
-- Window: Merkmal OLD(260,D)
-- 2026-02-06T13:27:56.735Z
UPDATE AD_Menu SET Description='Produkt-Merkmal', IsActive='Y', Name='Merkmal OLD',Updated=TO_TIMESTAMP('2026-02-06 13:27:56.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=1000089
;

-- 2026-02-06T13:27:56.770Z
UPDATE AD_Menu_Trl trl SET Name='Merkmal OLD' WHERE AD_Menu_ID=1000089 AND AD_Language='de_DE'
;

-- Name: Merkmal OLD
-- Action Type: W
-- Window: Merkmal OLD(260,D)
-- 2026-02-06T13:28:29.524Z
UPDATE AD_Menu SET Description='Produkt-Merkmal', IsActive='Y', Name='Merkmal OLD',Updated=TO_TIMESTAMP('2026-02-06 13:28:29.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=359
;

-- 2026-02-06T13:28:29.545Z
UPDATE AD_Menu_Trl trl SET Name='Merkmal OLD' WHERE AD_Menu_ID=359 AND AD_Language='de_DE'
;

-- 2026-02-06T13:28:45.966Z
/* DDL */  select update_window_translation_from_ad_element(584501)
;

-- 2026-02-06T13:28:45.999Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=260
;

-- 2026-02-06T13:28:46.024Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(260)
;

-- Window: Merkmal, InternalName=
-- 2026-02-06T13:30:47.355Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,Help,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,ZoomIntoPriority) VALUES (0,574064,0,542068,TO_TIMESTAMP('2026-02-06 13:30:47.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Merkmal','U','Produkt-Merkmale wie Farbe, Größe, usw.','','Y','N','N','Y','N','N','N','Y','Merkmal','N',TO_TIMESTAMP('2026-02-06 13:30:47.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',100)
;

-- 2026-02-06T13:30:47.375Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542068 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-02-06T13:30:47.426Z
/* DDL */  select update_window_translation_from_ad_element(574064)
;

-- 2026-02-06T13:30:47.450Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542068
;

-- 2026-02-06T13:30:47.470Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542068)
;

-- Window: Merkmal, InternalName=
-- 2026-02-06T13:33:04.658Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='D', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=260, WindowType='M', WinHeight=NULL, WinWidth=NULL, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2026-02-06 13:33:04.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542068
;

-- Tab: Merkmal(542068,D) -> Merkmal
-- Table: M_Attribute
-- 2026-02-06T13:33:05.088Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573090,0,548985,562,542068,'Y',TO_TIMESTAMP('2026-02-06 13:33:04.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Merkmal','D','N','Produkt-Merkmale wie Farbe, Größe, usw. If it is an Instance Attribute, all products have the same value.','N','A','M_Attribute','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','Y','N','N','N','Merkmal','N',10,0,TO_TIMESTAMP('2026-02-06 13:33:04.794000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:05.111Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548985 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-06T13:33:05.129Z
/* DDL */  select update_tab_translation_from_ad_element(573090)
;

-- 2026-02-06T13:33:05.154Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548985)
;

-- 2026-02-06T13:33:05.199Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548985
;

-- 2026-02-06T13:33:05.225Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548985, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 462
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Organisation
-- Column: M_Attribute.AD_Org_ID
-- 2026-02-06T13:33:05.715Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8507,771938,0,548985,85,TO_TIMESTAMP('2026-02-06 13:33:05.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',14,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Organisation',20,20,1,1,TO_TIMESTAMP('2026-02-06 13:33:05.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:05.735Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:05.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-06T13:33:05.849Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771938
;

-- 2026-02-06T13:33:05.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771938)
;

-- 2026-02-06T13:33:05.918Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771938
;

-- 2026-02-06T13:33:05.941Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771938, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6358
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Mandant
-- Column: M_Attribute.AD_Client_ID
-- 2026-02-06T13:33:06.220Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8508,771939,0,548985,75,TO_TIMESTAMP('2026-02-06 13:33:05.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',14,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2026-02-06 13:33:05.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:06.243Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:06.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-06T13:33:06.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771939
;

-- 2026-02-06T13:33:06.366Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771939)
;

-- 2026-02-06T13:33:06.409Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771939
;

-- 2026-02-06T13:33:06.432Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771939, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6359
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Instanz Merkmal
-- Column: M_Attribute.IsInstanceAttribute
-- 2026-02-06T13:33:06.695Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8509,771940,0,548985,100,TO_TIMESTAMP('2026-02-06 13:33:06.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)',1,'D','If selected, the individual instance of the product has this attribute - like the individual Serial or Lot Numbers or  Guarantee Date of a product instance.  If not selected, all instances of the product share the attribute (e.g. color=green).',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Instanz Merkmal',110,110,1,1,TO_TIMESTAMP('2026-02-06 13:33:06.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:06.717Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771940 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:06.742Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2012)
;

-- 2026-02-06T13:33:06.765Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771940
;

-- 2026-02-06T13:33:06.790Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771940)
;

-- 2026-02-06T13:33:06.832Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771940
;

-- 2026-02-06T13:33:06.852Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771940, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6360
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Merkmal
-- Column: M_Attribute.M_Attribute_ID
-- 2026-02-06T13:33:07.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8512,771941,1002236,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:06.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Merkmal',14,'D','Product Attribute like Color, Size',0,'Y','N','N','N','N','N','N','N','N','N','N','Merkmal',120,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:06.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:07.136Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771941 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:07.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002236)
;

-- 2026-02-06T13:33:07.185Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771941
;

-- 2026-02-06T13:33:07.205Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771941)
;

-- 2026-02-06T13:33:07.253Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771941
;

-- 2026-02-06T13:33:07.275Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771941, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6362
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Name
-- Column: M_Attribute.Name
-- 2026-02-06T13:33:07.562Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8513,771942,0,548985,144,TO_TIMESTAMP('2026-02-06 13:33:07.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',60,'D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Name',40,40,1,999,1,TO_TIMESTAMP('2026-02-06 13:33:07.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:07.596Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:07.618Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2026-02-06T13:33:07.654Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771942
;

-- 2026-02-06T13:33:07.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771942)
;

-- 2026-02-06T13:33:07.731Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771942
;

-- 2026-02-06T13:33:07.761Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771942, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6363
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Aktiv
-- Column: M_Attribute.IsActive
-- 2026-02-06T13:33:08.035Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8517,771943,0,548985,44,TO_TIMESTAMP('2026-02-06 13:33:07.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',60,60,1,1,TO_TIMESTAMP('2026-02-06 13:33:07.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:08.069Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:08.091Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-06T13:33:08.175Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771943
;

-- 2026-02-06T13:33:08.195Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771943)
;

-- 2026-02-06T13:33:08.243Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771943
;

-- 2026-02-06T13:33:08.266Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771943, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6364
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Beschreibung
-- Column: M_Attribute.Description
-- 2026-02-06T13:33:08.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8518,771944,0,548985,89,TO_TIMESTAMP('2026-02-06 13:33:08.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Beschreibung',50,50,999,1,TO_TIMESTAMP('2026-02-06 13:33:08.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:08.562Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:08.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-02-06T13:33:08.616Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771944
;

-- 2026-02-06T13:33:08.642Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771944)
;

-- 2026-02-06T13:33:08.689Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771944
;

-- 2026-02-06T13:33:08.709Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771944, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6365
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Pflichtangabe
-- Column: M_Attribute.IsMandatory
-- 2026-02-06T13:33:08.952Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8519,771945,1003095,0,548985,90,TO_TIMESTAMP('2026-02-06 13:33:08.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Data entry is required in this column',1,'D','The field must have a value for the record to be saved to the database.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Pflichtangabe',90,90,1,1,TO_TIMESTAMP('2026-02-06 13:33:08.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:08.974Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771945 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:08.997Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003095)
;

-- 2026-02-06T13:33:09.018Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771945
;

-- 2026-02-06T13:33:09.041Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771945)
;

-- 2026-02-06T13:33:09.083Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771945
;

-- 2026-02-06T13:33:09.106Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771945, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6366
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Merkmal-Suche
-- Column: M_Attribute.M_AttributeSearch_ID
-- 2026-02-06T13:33:09.356Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8545,771946,0,548985,101,TO_TIMESTAMP('2026-02-06 13:33:09.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gemeinsames Such-Merkmal',14,'D','Attributes are specific to a Product Attribute Set (e.g. Size for T-Shirts: S,M,L). If you have multiple attributes and want to search under a common sttribute, you define a search sttribute. Example: have one Size search attribute combining the values of all different sizes (Size for Dress Shirt  XL,L,M,S,XS). The Attribute Search allows you to have all values available for selection.  This eases the maintenance of the individual product attribute.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Merkmal-Suche',100,100,1,1,TO_TIMESTAMP('2026-02-06 13:33:09.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:09.379Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:09.399Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2025)
;

-- 2026-02-06T13:33:09.423Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771946
;

-- 2026-02-06T13:33:09.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771946)
;

-- 2026-02-06T13:33:09.498Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771946
;

-- 2026-02-06T13:33:09.523Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771946, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 6406
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Merkmals Wert Typ
-- Column: M_Attribute.AttributeValueType
-- 2026-02-06T13:33:09.807Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12662,771947,0,548985,125,TO_TIMESTAMP('2026-02-06 13:33:09.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Type of Attribute Value',14,'D','The Attribute Value type deternines the data/validation type',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Merkmals Wert Typ',70,70,1,1,TO_TIMESTAMP('2026-02-06 13:33:09.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:09.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771947 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:09.850Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2574)
;

-- 2026-02-06T13:33:09.875Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771947
;

-- 2026-02-06T13:33:09.897Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771947)
;

-- 2026-02-06T13:33:09.948Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771947
;

-- 2026-02-06T13:33:09.970Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771947, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 10645
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Dynamische Attributvalidierungsregel
-- Column: M_Attribute.AD_Val_Rule_ID
-- 2026-02-06T13:33:10.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551283,771948,575868,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:10.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Validierungsregel für erlaubte Attributewerte, die von der Default-AttributValueHandler Implementierung aufgerufen wird. Der Kontext enthält nur Spalten des jeweiligen M_Attribute-Datensatzes.',10,'@AttributeValueType@=L','D','Ob auch andere AttributValueHandler Implementierungen die dynamische Attributvalidierungsregel benutzen, hängt von deren Implementierung ab.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Dynamische Attributvalidierungsregel',80,80,1,1,TO_TIMESTAMP('2026-02-06 13:33:10.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:10.252Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771948 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:10.272Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575868)
;

-- 2026-02-06T13:33:10.299Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771948
;

-- 2026-02-06T13:33:10.316Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771948)
;

-- 2026-02-06T13:33:10.359Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771948
;

-- 2026-02-06T13:33:10.379Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771948, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 73427
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> AttributeValueHandler Javaklasse
-- Column: M_Attribute.AD_JavaClass_ID
-- 2026-02-06T13:33:10.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549460,771949,575867,0,548985,74,TO_TIMESTAMP('2026-02-06 13:33:10.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Javaklasse, die das Interface IAttributeValueHandler implementiert. Falls leer wird eine Defaultimplementierung benutzt, die die dynamische Validierungsregel aufruft.',0,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','AttributeValueHandler Javaklasse',120,120,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:10.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:10.644Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:10.666Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575867)
;

-- 2026-02-06T13:33:10.690Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771949
;

-- 2026-02-06T13:33:10.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771949)
;

-- 2026-02-06T13:33:10.767Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771949
;

-- 2026-02-06T13:33:10.791Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771949, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552506
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> isPricingRelevant
-- Column: M_Attribute.IsPricingRelevant
-- 2026-02-06T13:33:11.071Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549783,771950,0,548985,107,TO_TIMESTAMP('2026-02-06 13:33:10.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','isPricingRelevant',130,130,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:10.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:11.089Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771950 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:11.112Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542236)
;

-- 2026-02-06T13:33:11.139Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771950
;

-- 2026-02-06T13:33:11.159Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771950)
;

-- 2026-02-06T13:33:11.202Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771950
;

-- 2026-02-06T13:33:11.226Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771950, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552994
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Suchschlüssel
-- Column: M_Attribute.Value
-- 2026-02-06T13:33:11.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550503,771951,0,548985,142,TO_TIMESTAMP('2026-02-06 13:33:11.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',0,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Suchschlüssel',30,30,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:11.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:11.539Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:11.566Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2026-02-06T13:33:11.599Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771951
;

-- 2026-02-06T13:33:11.621Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771951)
;

-- 2026-02-06T13:33:11.659Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771951
;

-- 2026-02-06T13:33:11.689Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771951, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554048
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Maßeinheit
-- Column: M_Attribute.C_UOM_ID
-- 2026-02-06T13:33:11.960Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550505,771952,0,548985,77,TO_TIMESTAMP('2026-02-06 13:33:11.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',0,'D','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Maßeinheit',140,170,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:11.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:11.984Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:12.009Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-02-06T13:33:12.038Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771952
;

-- 2026-02-06T13:33:12.061Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771952)
;

-- 2026-02-06T13:33:12.109Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771952
;

-- 2026-02-06T13:33:12.139Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771952, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554050
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Min. Wert
-- Column: M_Attribute.ValueMin
-- 2026-02-06T13:33:12.420Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550627,771953,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:12.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Minimum Value for a field',16,'@AttributeValueType@=N','D','The Minimum Value indicates the lowest  allowable value for a field.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Min. Wert',150,210,1,1,TO_TIMESTAMP('2026-02-06 13:33:12.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:12.449Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771953 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:12.472Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1060)
;

-- 2026-02-06T13:33:12.489Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771953
;

-- 2026-02-06T13:33:12.522Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771953)
;

-- 2026-02-06T13:33:12.561Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771953
;

-- 2026-02-06T13:33:12.605Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771953, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554198
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Max. Wert
-- Column: M_Attribute.ValueMax
-- 2026-02-06T13:33:12.899Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550628,771954,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:12.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maximum Value for a field',16,'@AttributeValueType@=N','D','The Maximum Value indicates the highest allowable value for a field',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Max. Wert',160,200,1,1,TO_TIMESTAMP('2026-02-06 13:33:12.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:12.924Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771954 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:12.945Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1059)
;

-- 2026-02-06T13:33:12.967Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771954
;

-- 2026-02-06T13:33:12.989Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771954)
;

-- 2026-02-06T13:33:13.031Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771954
;

-- 2026-02-06T13:33:13.052Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771954, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554199
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Ist Bestandsrelevant
-- Column: M_Attribute.IsStorageRelevant
-- 2026-02-06T13:33:13.309Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550884,771955,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:13.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Is used to do attibute matching between storage attributes and order line attributes (ASIs).',0,'D','The flag indicates that an attribute is significant in the (HU) storage. This does not automatically imply that the attribute is actually used in any HU.
But if HU-Attributes are flagged with this, the HU-related quantities will be shown in the MRP produkt info (maybe elsewhere too, in future) with respect to those attributes'' values.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Ist Bestandsrelevant',150,180,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:13.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:13.329Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771955 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:13.356Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542475)
;

-- 2026-02-06T13:33:13.385Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771955
;

-- 2026-02-06T13:33:13.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771955)
;

-- 2026-02-06T13:33:13.452Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771955
;

-- 2026-02-06T13:33:13.482Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771955, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554507
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Auf Belegen auszuweisen
-- Column: M_Attribute.IsAttrDocumentRelevant
-- 2026-02-06T13:33:13.748Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551807,771956,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:13.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn ein Attribute auf Belegen auszuweisen ist, bedeutet das, das Lieferposionen mit unterschiedlichen Attributwerten nicht zu einer Rechnungszeile zusammengefasst werden können.',0,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Auf Belegen auszuweisen',135,140,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:13.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:13.771Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771956 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:13.796Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542706)
;

-- 2026-02-06T13:33:13.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771956
;

-- 2026-02-06T13:33:13.843Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771956)
;

-- 2026-02-06T13:33:13.887Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771956
;

-- 2026-02-06T13:33:13.918Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771956, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555486
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Read Only
-- Column: M_Attribute.IsReadOnlyValues
-- 2026-02-06T13:33:14.181Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,555451,771957,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:13.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Read Only',136,150,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:13.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:14.204Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:14.226Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543239)
;

-- 2026-02-06T13:33:14.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771957
;

-- 2026-02-06T13:33:14.273Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771957)
;

-- 2026-02-06T13:33:14.318Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771957
;

-- 2026-02-06T13:33:14.340Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771957, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 557412
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Transferieren wenn null
-- Column: M_Attribute.IsTransferWhenNull
-- 2026-02-06T13:33:14.608Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556047,771958,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:14.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Transferieren wenn null',137,160,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:14.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:14.631Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:14.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543272)
;

-- 2026-02-06T13:33:14.677Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771958
;

-- 2026-02-06T13:33:14.700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771958)
;

-- 2026-02-06T13:33:14.748Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771958
;

-- 2026-02-06T13:33:14.770Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771958, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 557503
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> High Volume
-- Column: M_Attribute.IsHighVolume
-- 2026-02-06T13:33:15.077Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,558262,771959,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:14.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Use Search instead of Pick list',1,'@AttributeValueType/-@=L','D','The High Volume Checkbox indicates if a search screen will display as opposed to a pick list for selecting records from this table.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','High Volume',170,190,1,1,TO_TIMESTAMP('2026-02-06 13:33:14.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:15.101Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771959 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:15.127Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1174)
;

-- 2026-02-06T13:33:15.151Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771959
;

-- 2026-02-06T13:33:15.170Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771959)
;

-- 2026-02-06T13:33:15.227Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771959
;

-- 2026-02-06T13:33:15.248Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771959, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 560803
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Description Pattern
-- Column: M_Attribute.DescriptionPattern
-- 2026-02-06T13:33:15.550Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560723,771960,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:15.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D',0,'Y','N','Y','N','N','N','N','N','N','N','N','Description Pattern',180,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:15.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:15.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771960 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:15.604Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544191)
;

-- 2026-02-06T13:33:15.637Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771960
;

-- 2026-02-06T13:33:15.657Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771960)
;

-- 2026-02-06T13:33:15.697Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771960
;

-- 2026-02-06T13:33:15.719Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771960, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565139
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Merkmal immer aktualisierbar
-- Column: M_Attribute.IsAlwaysUpdateable
-- 2026-02-06T13:33:15.966Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574266,771961,579314,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:15.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.',0,'D','Wenn die Einstellung aktiviert ist und das Merkmal für eine HU im Status "Geplant" editierbar war, dann bleibt das Merkmal editierbar, auch wenn die HU nicht mehr "Geplant" ist.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Merkmal immer aktualisierbar',160,220,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:15.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:15.990Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771961 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:16.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579314)
;

-- 2026-02-06T13:33:16.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771961
;

-- 2026-02-06T13:33:16.056Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771961)
;

-- 2026-02-06T13:33:16.099Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771961
;

-- 2026-02-06T13:33:16.123Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771961, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 647478
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Dokument gedruckt
-- Column: M_Attribute.IsPrintedInDocument
-- 2026-02-06T13:33:16.421Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582466,771962,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:16.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.',1,'D','Wenn angehakt, wird das Merkmal auf allen referenzierten Belegen gedruckt.',0,'Y','N','N','N','N','N','N','N','N','N','N','Dokument gedruckt',190,1,1,TO_TIMESTAMP('2026-02-06 13:33:16.142000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:16.450Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771962 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:16.471Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580717)
;

-- 2026-02-06T13:33:16.490Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771962
;

-- 2026-02-06T13:33:16.529Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771962)
;

-- 2026-02-06T13:33:16.588Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771962
;

-- 2026-02-06T13:33:16.609Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771962, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 691408
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Druckwert-Überschreibung
-- Column: M_Attribute.PrintValue_Override
-- 2026-02-06T13:33:16.890Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589831,771963,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:16.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wert, der auf Dokumenten anstelle des Standardwerts gedruckt wird, falls ausgefüllt.',0,'D',0,'',0,'Y','N','Y','Y','N','N','N','N','N','N','N',0,'Druckwert-Überschreibung',0,200,230,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:16.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:16.909Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:16.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583566)
;

-- 2026-02-06T13:33:16.959Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771963
;

-- 2026-02-06T13:33:16.999Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771963)
;

-- 2026-02-06T13:33:17.041Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771963
;

-- 2026-02-06T13:33:17.066Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771963, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 741860
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Attributwerte Sortieren nach
-- Column: M_Attribute.AttributeValuesOrderBy
-- 2026-02-06T13:33:17.359Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590596,771964,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:17.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'@AttributeValueType/-@=L','D',0,'Y','N','N','N','N','N','N','N','N','N','N','Attributwerte Sortieren nach',210,1,1,TO_TIMESTAMP('2026-02-06 13:33:17.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:17.379Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771964 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:17.405Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583849)
;

-- 2026-02-06T13:33:17.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771964
;

-- 2026-02-06T13:33:17.453Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771964)
;

-- 2026-02-06T13:33:17.501Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771964
;

-- 2026-02-06T13:33:17.527Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771964, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 751764
;

-- Field: Merkmal(542068,D) -> Merkmal(548985,D) -> Standardwert-Logik (SQL)
-- Column: M_Attribute.DefaultValueSQL
-- 2026-02-06T13:33:17.849Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591423,771965,0,548985,0,TO_TIMESTAMP('2026-02-06 13:33:17.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SQL-Ausdruck, der verwendet wird, um den Standardwert für dieses Attribut zu berechnen. Der Ausdruck kann variable Platzhalter enthalten (z. B. @M_Product_ID@, @C_Order_ID@, @TableName@), die zur Laufzeit durch die tatsächlichen Kontextwerte ersetzt werden. Der resultierende Wert muss dem für das Attribut definierten Wertetyp entsprechen. Wenn dieses Feld leer ist, besitzt das Attribut keinen SQL-basierten Standardwert.',9999999,'D','SQL-Ausdruck, der verwendet wird, um den Standardwert für dieses Attribut zu berechnen.
Der Ausdruck kann variable Platzhalter enthalten (z. B. @M_Product_ID@, @C_Order_ID@, @TableName@), die zur Laufzeit durch die tatsächlichen Kontextwerte ersetzt werden. Der resultierende Wert muss dem für das Attribut definierten Wertetyp entsprechen. Wenn dieses Feld leer ist, besitzt das Attribut keinen SQL-basierten Standardwert.',0,'Y','N','N','N','N','N','N','N','N','N','N','Standardwert-Logik (SQL)',220,1,1,TO_TIMESTAMP('2026-02-06 13:33:17.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:17.880Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771965 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:17.901Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584138)
;

-- 2026-02-06T13:33:17.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771965
;

-- 2026-02-06T13:33:17.950Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771965)
;

-- 2026-02-06T13:33:18.006Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771965
;

-- 2026-02-06T13:33:18.031Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771965, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 755063
;

-- Tab: Merkmal(542068,D) -> Attribut substitute
-- Table: M_AttributeValue_Mapping
-- 2026-02-06T13:33:18.314Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,551495,573722,0,548986,540631,542068,'Y',TO_TIMESTAMP('2026-02-06 13:33:18.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','M_AttributeValue_Mapping','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N','Attribut substitute',8469,'N',25,2,TO_TIMESTAMP('2026-02-06 13:33:18.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:18.340Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548986 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-06T13:33:18.362Z
/* DDL */  select update_tab_translation_from_ad_element(573722)
;

-- 2026-02-06T13:33:18.386Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548986)
;

-- 2026-02-06T13:33:18.431Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548986
;

-- 2026-02-06T13:33:18.454Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548986, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540646
;

-- Field: Merkmal(542068,D) -> Attribut substitute(548986,D) -> Mandant
-- Column: M_AttributeValue_Mapping.AD_Client_ID
-- 2026-02-06T13:33:18.923Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551487,771966,0,548986,0,TO_TIMESTAMP('2026-02-06 13:33:18.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',10,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:18.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:18.946Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:18.980Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-06T13:33:19.057Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771966
;

-- 2026-02-06T13:33:19.102Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771966)
;

-- 2026-02-06T13:33:19.151Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771966
;

-- 2026-02-06T13:33:19.172Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771966, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555152
;

-- Field: Merkmal(542068,D) -> Attribut substitute(548986,D) -> Organisation
-- Column: M_AttributeValue_Mapping.AD_Org_ID
-- 2026-02-06T13:33:19.459Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551488,771967,0,548986,0,TO_TIMESTAMP('2026-02-06 13:33:19.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Organisation',20,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:19.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:19.479Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:19.515Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-06T13:33:19.599Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771967
;

-- 2026-02-06T13:33:19.622Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771967)
;

-- 2026-02-06T13:33:19.669Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771967
;

-- 2026-02-06T13:33:19.690Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771967, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555153
;

-- Field: Merkmal(542068,D) -> Attribut substitute(548986,D) -> Aktiv
-- Column: M_AttributeValue_Mapping.IsActive
-- 2026-02-06T13:33:19.944Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551491,771968,0,548986,0,TO_TIMESTAMP('2026-02-06 13:33:19.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',40,40,1,1,TO_TIMESTAMP('2026-02-06 13:33:19.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:19.959Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771968 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:19.989Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-06T13:33:20.071Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771968
;

-- 2026-02-06T13:33:20.089Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771968)
;

-- 2026-02-06T13:33:20.137Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771968
;

-- 2026-02-06T13:33:20.156Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771968, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555154
;

-- Field: Merkmal(542068,D) -> Attribut substitute(548986,D) -> Attribut substitute
-- Column: M_AttributeValue_Mapping.M_AttributeValue_Mapping_ID
-- 2026-02-06T13:33:20.469Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551494,771969,0,548986,0,TO_TIMESTAMP('2026-02-06 13:33:20.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Attribut substitute',50,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:20.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:20.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771969 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:20.518Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542625)
;

-- 2026-02-06T13:33:20.539Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771969
;

-- 2026-02-06T13:33:20.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771969)
;

-- 2026-02-06T13:33:20.609Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771969
;

-- 2026-02-06T13:33:20.630Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771969, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555155
;

-- Field: Merkmal(542068,D) -> Attribut substitute(548986,D) -> Merkmals-Wert
-- Column: M_AttributeValue_Mapping.M_AttributeValue_ID
-- 2026-02-06T13:33:20.906Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551495,771970,0,548986,0,TO_TIMESTAMP('2026-02-06 13:33:20.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Product Attribute Value',10,'D','Individual value of a product attribute (e.g. green, large, ..)',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Merkmals-Wert',10,10,1,1,TO_TIMESTAMP('2026-02-06 13:33:20.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:20.929Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:20.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2020)
;

-- 2026-02-06T13:33:20.972Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771970
;

-- 2026-02-06T13:33:20.999Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771970)
;

-- 2026-02-06T13:33:21.039Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771970
;

-- 2026-02-06T13:33:21.070Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771970, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555156
;

-- Field: Merkmal(542068,D) -> Attribut substitute(548986,D) -> Merkmals-Wert Nach
-- Column: M_AttributeValue_Mapping.M_AttributeValue_To_ID
-- 2026-02-06T13:33:21.354Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551496,771971,0,548986,0,TO_TIMESTAMP('2026-02-06 13:33:21.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Product Attribute Value To',10,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Merkmals-Wert Nach',20,20,1,1,TO_TIMESTAMP('2026-02-06 13:33:21.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:21.379Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771971 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:21.410Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542626)
;

-- 2026-02-06T13:33:21.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771971
;

-- 2026-02-06T13:33:21.451Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771971)
;

-- 2026-02-06T13:33:21.501Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771971
;

-- 2026-02-06T13:33:21.527Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771971, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555157
;

-- Field: Merkmal(542068,D) -> Attribut substitute(548986,D) -> Beschreibung
-- Column: M_AttributeValue_Mapping.Description
-- 2026-02-06T13:33:21.783Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551497,771972,0,548986,0,TO_TIMESTAMP('2026-02-06 13:33:21.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Beschreibung',30,30,999,1,TO_TIMESTAMP('2026-02-06 13:33:21.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:21.806Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771972 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:21.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-02-06T13:33:21.884Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771972
;

-- 2026-02-06T13:33:21.905Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771972)
;

-- 2026-02-06T13:33:21.955Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771972
;

-- 2026-02-06T13:33:21.976Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771972, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555158
;

-- Tab: Merkmal(542068,D) -> Packvorschrift
-- Table: M_HU_PI_Attribute
-- 2026-02-06T13:33:22.230Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,549267,573733,0,548987,540507,542068,'Y',TO_TIMESTAMP('2026-02-06 13:33:21.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','M_HU_PI_Attribute','Y','N','Y','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N','Packvorschrift',8512,'N',40,1,TO_TIMESTAMP('2026-02-06 13:33:21.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:22.253Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548987 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-06T13:33:22.276Z
/* DDL */  select update_tab_translation_from_ad_element(573733)
;

-- 2026-02-06T13:33:22.307Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548987)
;

-- 2026-02-06T13:33:22.353Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548987
;

-- 2026-02-06T13:33:22.375Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548987, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540591
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Aktualisiert durch
-- Column: M_HU_PI_Attribute.UpdatedBy
-- 2026-02-06T13:33:22.872Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549178,771973,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:22.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',10,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:22.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:22.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:22.919Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2026-02-06T13:33:22.971Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771973
;

-- 2026-02-06T13:33:22.992Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771973)
;

-- 2026-02-06T13:33:23.039Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771973
;

-- 2026-02-06T13:33:23.064Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771973, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554237
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Mandant
-- Column: M_HU_PI_Attribute.AD_Client_ID
-- 2026-02-06T13:33:23.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549172,771974,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:23.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',20,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:23.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:23.389Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:23.409Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-06T13:33:23.487Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771974
;

-- 2026-02-06T13:33:23.509Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771974)
;

-- 2026-02-06T13:33:23.559Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771974
;

-- 2026-02-06T13:33:23.579Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771974, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554238
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Packvorschrift Version
-- Column: M_HU_PI_Attribute.M_HU_PI_Version_ID
-- 2026-02-06T13:33:23.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549268,771975,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:23.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Packvorschrift Version',30,0,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:23.609000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:23.932Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:23.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542134)
;

-- 2026-02-06T13:33:23.979Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771975
;

-- 2026-02-06T13:33:24.009Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771975)
;

-- 2026-02-06T13:33:24.049Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771975
;

-- 2026-02-06T13:33:24.079Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771975, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554239
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Erstellt
-- Column: M_HU_PI_Attribute.Created
-- 2026-02-06T13:33:24.349Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549174,771976,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:24.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt',40,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:24.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:24.369Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:24.389Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2026-02-06T13:33:24.449Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771976
;

-- 2026-02-06T13:33:24.471Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771976)
;

-- 2026-02-06T13:33:24.519Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771976
;

-- 2026-02-06T13:33:24.539Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771976, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554240
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Erstellt durch
-- Column: M_HU_PI_Attribute.CreatedBy
-- 2026-02-06T13:33:24.822Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549175,771977,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:24.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',50,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:24.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:24.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:24.859Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2026-02-06T13:33:24.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771977
;

-- 2026-02-06T13:33:24.940Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771977)
;

-- 2026-02-06T13:33:24.989Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771977
;

-- 2026-02-06T13:33:25.009Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771977, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554241
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Aktualisiert
-- Column: M_HU_PI_Attribute.Updated
-- 2026-02-06T13:33:25.289Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549177,771978,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:25.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',60,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:25.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:25.339Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:25.359Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2026-02-06T13:33:25.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771978
;

-- 2026-02-06T13:33:25.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771978)
;

-- 2026-02-06T13:33:25.484Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771978
;

-- 2026-02-06T13:33:25.505Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771978, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554242
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Organisation
-- Column: M_HU_PI_Attribute.AD_Org_ID
-- 2026-02-06T13:33:25.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549173,771979,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:25.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Organisation',10,10,1,1,TO_TIMESTAMP('2026-02-06 13:33:25.528000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:25.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:25.822Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-06T13:33:25.899Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771979
;

-- 2026-02-06T13:33:25.927Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771979)
;

-- 2026-02-06T13:33:25.971Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771979
;

-- 2026-02-06T13:33:25.992Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771979, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554243
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Merkmal
-- Column: M_HU_PI_Attribute.M_Attribute_ID
-- 2026-02-06T13:33:26.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549267,771980,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:26.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Merkmal',10,'D','Product Attribute like Color, Size',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Merkmal',20,30,1,1,TO_TIMESTAMP('2026-02-06 13:33:26.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:26.294Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:26.319Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2015)
;

-- 2026-02-06T13:33:26.342Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771980
;

-- 2026-02-06T13:33:26.372Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771980)
;

-- 2026-02-06T13:33:26.419Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771980
;

-- 2026-02-06T13:33:26.443Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771980, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554244
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Reihenfolge
-- Column: M_HU_PI_Attribute.SeqNo
-- 2026-02-06T13:33:26.681Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550572,771981,1000652,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:26.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Reihenfolge',30,20,1,1,1,TO_TIMESTAMP('2026-02-06 13:33:26.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:26.702Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:26.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000652)
;

-- 2026-02-06T13:33:26.750Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771981
;

-- 2026-02-06T13:33:26.771Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771981)
;

-- 2026-02-06T13:33:26.832Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771981
;

-- 2026-02-06T13:33:26.852Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771981, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554245
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Aktiv
-- Column: M_HU_PI_Attribute.IsActive
-- 2026-02-06T13:33:27.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549176,771982,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:26.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',40,40,1,1,TO_TIMESTAMP('2026-02-06 13:33:26.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:27.138Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771982 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:27.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-06T13:33:27.240Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771982
;

-- 2026-02-06T13:33:27.260Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771982)
;

-- 2026-02-06T13:33:27.305Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771982
;

-- 2026-02-06T13:33:27.328Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771982, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554246
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Instanz Merkmal
-- Column: M_HU_PI_Attribute.IsInstanceAttribute
-- 2026-02-06T13:33:27.589Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549265,771983,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:27.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)',1,'D','If selected, the individual instance of the product has this attribute - like the individual Serial or Lot Numbers or  Guarantee Date of a product instance.  If not selected, all instances of the product share the attribute (e.g. color=green).',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Instanz Merkmal',50,50,1,1,TO_TIMESTAMP('2026-02-06 13:33:27.349000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:27.609Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771983 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:27.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2012)
;

-- 2026-02-06T13:33:27.659Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771983
;

-- 2026-02-06T13:33:27.679Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771983)
;

-- 2026-02-06T13:33:27.723Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771983
;

-- 2026-02-06T13:33:27.749Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771983, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554247
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Pflichtangabe
-- Column: M_HU_PI_Attribute.IsMandatory
-- 2026-02-06T13:33:28.006Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549266,771984,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:27.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Data entry is required in this column',1,'D','The field must have a value for the record to be saved to the database.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Pflichtangabe',60,60,1,1,TO_TIMESTAMP('2026-02-06 13:33:27.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:28.031Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:28.049Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(392)
;

-- 2026-02-06T13:33:28.076Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771984
;

-- 2026-02-06T13:33:28.099Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771984)
;

-- 2026-02-06T13:33:28.155Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771984
;

-- 2026-02-06T13:33:28.171Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771984, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554248
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Propagation Type
-- Column: M_HU_PI_Attribute.PropagationType
-- 2026-02-06T13:33:28.440Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549430,771985,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:28.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Propagation Type',70,70,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:28.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:28.473Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:28.489Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542193)
;

-- 2026-02-06T13:33:28.519Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771985
;

-- 2026-02-06T13:33:28.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771985)
;

-- 2026-02-06T13:33:28.599Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771985
;

-- 2026-02-06T13:33:28.623Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771985, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554249
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Aggregation Strategy
-- Column: M_HU_PI_Attribute.AggregationStrategy_JavaClass_ID
-- 2026-02-06T13:33:28.891Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549463,771986,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:28.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@PropagationType@=''BOTU'' | @PropagationType@=''IVAL''','D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Aggregation Strategy',80,80,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:28.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:28.919Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:28.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542198)
;

-- 2026-02-06T13:33:28.959Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771986
;

-- 2026-02-06T13:33:28.991Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771986)
;

-- 2026-02-06T13:33:29.034Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771986
;

-- 2026-02-06T13:33:29.057Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771986, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554250
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Splitter Strategy
-- Column: M_HU_PI_Attribute.SplitterStrategy_JavaClass_ID
-- 2026-02-06T13:33:29.339Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549464,771987,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:29.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@PropagationType@=''TOPD'' | @PropagationType@=''IVAL''','D',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Splitter Strategy',90,90,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:29.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:29.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:29.379Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542199)
;

-- 2026-02-06T13:33:29.411Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771987
;

-- 2026-02-06T13:33:29.435Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771987)
;

-- 2026-02-06T13:33:29.488Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771987
;

-- 2026-02-06T13:33:29.515Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771987, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554251
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Maßeinheit
-- Column: M_HU_PI_Attribute.C_UOM_ID
-- 2026-02-06T13:33:29.758Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550506,771988,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:29.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',0,'D','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Maßeinheit',100,100,0,1,1,TO_TIMESTAMP('2026-02-06 13:33:29.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:29.780Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:29.802Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-02-06T13:33:29.830Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771988
;

-- 2026-02-06T13:33:29.852Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771988)
;

-- 2026-02-06T13:33:29.894Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771988
;

-- 2026-02-06T13:33:29.922Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771988, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554252
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Schreibgeschützt
-- Column: M_HU_PI_Attribute.IsReadOnly
-- 2026-02-06T13:33:30.194Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550571,771989,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:29.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Feld / Eintrag / Berecih ist schreibgeschützt',1,'D','The Read Only indicates that this field may only be Read.  It may not be updated.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Schreibgeschützt',110,110,1,1,TO_TIMESTAMP('2026-02-06 13:33:29.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:30.215Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:30.237Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(405)
;

-- 2026-02-06T13:33:30.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771989
;

-- 2026-02-06T13:33:30.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771989)
;

-- 2026-02-06T13:33:30.327Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771989
;

-- 2026-02-06T13:33:30.348Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771989, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554253
;

-- Field: Merkmal(542068,D) -> Packvorschrift(548987,D) -> Handling Units Packing Instructions Attribute
-- Column: M_HU_PI_Attribute.M_HU_PI_Attribute_ID
-- 2026-02-06T13:33:30.650Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549179,771990,0,548987,0,TO_TIMESTAMP('2026-02-06 13:33:30.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D',0,'Y','N','N','N','N','N','N','N','N','N','N','Handling Units Packing Instructions Attribute',120,1,1,TO_TIMESTAMP('2026-02-06 13:33:30.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T13:33:30.689Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T13:33:30.710Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542131)
;

-- 2026-02-06T13:33:30.733Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771990
;

-- 2026-02-06T13:33:30.757Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771990)
;

-- 2026-02-06T13:33:30.801Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771990
;

-- 2026-02-06T13:33:30.822Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771990, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554254
;

-- Tab: Merkmal(542068,D) -> Merkmal(548985,D)
-- UI Section: main
-- 2026-02-06T13:33:31.072Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548985,547507,TO_TIMESTAMP('2026-02-06 13:33:30.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-06 13:33:30.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-06T13:33:31.094Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547507 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-06T13:33:31.145Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547507
;

-- 2026-02-06T13:33:31.172Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547507, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540102
;

-- UI Section: Merkmal(542068,D) -> Merkmal(548985,D) -> main
-- UI Column: 10
-- 2026-02-06T13:33:31.439Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549166,547507,TO_TIMESTAMP('2026-02-06 13:33:31.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-06 13:33:31.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10
-- UI Element Group: default
-- 2026-02-06T13:33:31.712Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549166,554795,TO_TIMESTAMP('2026-02-06 13:33:31.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-06 13:33:31.576000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> default.Name
-- Column: M_Attribute.Name
-- 2026-02-06T13:33:32.139Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771942,0,548985,554795,646862,'F',TO_TIMESTAMP('2026-02-06 13:33:31.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Name',10,10,10,TO_TIMESTAMP('2026-02-06 13:33:31.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> default.Beschreibung
-- Column: M_Attribute.Description
-- 2026-02-06T13:33:32.529Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771944,0,548985,554795,646863,'F',TO_TIMESTAMP('2026-02-06 13:33:32.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Beschreibung',20,20,0,TO_TIMESTAMP('2026-02-06 13:33:32.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> default.Merkmal Typ
-- Column: M_Attribute.AttributeValueType
-- 2026-02-06T13:33:32.838Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771947,0,548985,554795,646864,'F',TO_TIMESTAMP('2026-02-06 13:33:32.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Merkmal Typ',30,30,20,TO_TIMESTAMP('2026-02-06 13:33:32.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> default.High Volume
-- Column: M_Attribute.IsHighVolume
-- 2026-02-06T13:33:33.143Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771959,0,548985,554795,646865,'F',TO_TIMESTAMP('2026-02-06 13:33:32.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','High Volume',40,0,0,TO_TIMESTAMP('2026-02-06 13:33:32.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> default.Sortieren nach
-- Column: M_Attribute.AttributeValuesOrderBy
-- 2026-02-06T13:33:33.469Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771964,0,548985,554795,646866,'F',TO_TIMESTAMP('2026-02-06 13:33:33.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Sortieren nach',50,0,0,TO_TIMESTAMP('2026-02-06 13:33:33.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10
-- UI Element Group: value
-- 2026-02-06T13:33:33.705Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549166,554796,TO_TIMESTAMP('2026-02-06 13:33:33.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','value',15,TO_TIMESTAMP('2026-02-06 13:33:33.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> value.Suchschlüssel
-- Column: M_Attribute.Value
-- 2026-02-06T13:33:34.095Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771951,0,548985,554796,646867,'F',TO_TIMESTAMP('2026-02-06 13:33:33.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Suchschlüssel',10,0,0,TO_TIMESTAMP('2026-02-06 13:33:33.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> value.Druckwert-Überschreibung
-- Column: M_Attribute.PrintValue_Override
-- 2026-02-06T13:33:34.436Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771963,0,548985,554796,646868,'F',TO_TIMESTAMP('2026-02-06 13:33:34.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wert, der auf Dokumenten anstelle des Standardwerts gedruckt wird, falls ausgefüllt.','Y','N','N','Y','N','N','N',0,'Druckwert-Überschreibung',20,0,0,TO_TIMESTAMP('2026-02-06 13:33:34.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10
-- UI Element Group: technical
-- 2026-02-06T13:33:34.638Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549166,554797,TO_TIMESTAMP('2026-02-06 13:33:34.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','technical',20,TO_TIMESTAMP('2026-02-06 13:33:34.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Standardwert-Logik (SQL)
-- Column: M_Attribute.DefaultValueSQL
-- 2026-02-06T13:33:35.030Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771965,0,548985,554797,646869,'F',TO_TIMESTAMP('2026-02-06 13:33:34.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','N','Y','N','N','N','Standardwert-Logik (SQL)',5,0,0,TO_TIMESTAMP('2026-02-06 13:33:34.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Merkmal Suche
-- Column: M_Attribute.M_AttributeSearch_ID
-- 2026-02-06T13:33:35.358Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771946,0,548985,554797,646870,'F',TO_TIMESTAMP('2026-02-06 13:33:35.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Merkmal Suche',10,0,0,TO_TIMESTAMP('2026-02-06 13:33:35.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Java Klasse
-- Column: M_Attribute.AD_JavaClass_ID
-- 2026-02-06T13:33:35.680Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771949,0,548985,554797,646871,'F',TO_TIMESTAMP('2026-02-06 13:33:35.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Java Klasse',20,0,0,TO_TIMESTAMP('2026-02-06 13:33:35.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Maßeinheit
-- Column: M_Attribute.C_UOM_ID
-- 2026-02-06T13:33:35.981Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771952,0,548985,554797,646872,'F',TO_TIMESTAMP('2026-02-06 13:33:35.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Maßeinheit',30,110,0,TO_TIMESTAMP('2026-02-06 13:33:35.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Section: Merkmal(542068,D) -> Merkmal(548985,D) -> main
-- UI Column: 20
-- 2026-02-06T13:33:36.198Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549167,547507,TO_TIMESTAMP('2026-02-06 13:33:36.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-06 13:33:36.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20
-- UI Element Group: flags
-- 2026-02-06T13:33:36.438Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549167,554798,TO_TIMESTAMP('2026-02-06 13:33:36.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2026-02-06 13:33:36.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> flags.Aktiv
-- Column: M_Attribute.IsActive
-- 2026-02-06T13:33:36.802Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771943,0,548985,554798,646873,'F',TO_TIMESTAMP('2026-02-06 13:33:36.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Aktiv',10,40,30,TO_TIMESTAMP('2026-02-06 13:33:36.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> flags.Pflichtangabe
-- Column: M_Attribute.IsMandatory
-- 2026-02-06T13:33:37.129Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771945,0,548985,554798,646874,'F',TO_TIMESTAMP('2026-02-06 13:33:36.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Pflichtangabe',20,50,0,TO_TIMESTAMP('2026-02-06 13:33:36.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> flags.Instanz Merkmal
-- Column: M_Attribute.IsInstanceAttribute
-- 2026-02-06T13:33:37.468Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771940,0,548985,554798,646875,'F',TO_TIMESTAMP('2026-02-06 13:33:37.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Instanz Merkmal',30,70,0,TO_TIMESTAMP('2026-02-06 13:33:37.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> flags.Preisrelevant
-- Column: M_Attribute.IsPricingRelevant
-- 2026-02-06T13:33:37.802Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771950,0,548985,554798,646876,'F',TO_TIMESTAMP('2026-02-06 13:33:37.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Preisrelevant',40,60,0,TO_TIMESTAMP('2026-02-06 13:33:37.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> flags.HU Bestandsrelevant
-- Column: M_Attribute.IsStorageRelevant
-- 2026-02-06T13:33:38.122Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771955,0,548985,554798,646877,'F',TO_TIMESTAMP('2026-02-06 13:33:37.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','HU Bestandsrelevant',50,90,0,TO_TIMESTAMP('2026-02-06 13:33:37.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> flags.Auf Belegen ausweisen
-- Column: M_Attribute.IsAttrDocumentRelevant
-- 2026-02-06T13:33:38.442Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771956,0,548985,554798,646878,'F',TO_TIMESTAMP('2026-02-06 13:33:38.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Auf Belegen ausweisen',60,100,0,TO_TIMESTAMP('2026-02-06 13:33:38.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> flags.Always Updateable
-- Column: M_Attribute.IsAlwaysUpdateable
-- 2026-02-06T13:33:38.754Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771961,0,548985,554798,646879,'F',TO_TIMESTAMP('2026-02-06 13:33:38.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The column is always updateable, even if the record is not active or processed','If selected and if the winow / tab is not read only, you can always update the column.  This might be useful for comments, etc.','Y','N','N','Y','N','N','N',0,'Always Updateable',70,0,0,TO_TIMESTAMP('2026-02-06 13:33:38.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> flags.Dokument gedruckt
-- Column: M_Attribute.IsPrintedInDocument
-- 2026-02-06T13:33:39.065Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771962,0,548985,554798,646880,'F',TO_TIMESTAMP('2026-02-06 13:33:38.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'If this flag is set on Y, then means that the attribute will be shown in all document reports.','Y','N','N','Y','N','N','N',0,'Dokument gedruckt',80,0,0,TO_TIMESTAMP('2026-02-06 13:33:38.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20
-- UI Element Group: org
-- 2026-02-06T13:33:39.301Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549167,554799,TO_TIMESTAMP('2026-02-06 13:33:39.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2026-02-06 13:33:39.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> org.Organisation
-- Column: M_Attribute.AD_Org_ID
-- 2026-02-06T13:33:39.672Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771938,0,548985,554799,646881,'F',TO_TIMESTAMP('2026-02-06 13:33:39.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Organisation',10,120,40,TO_TIMESTAMP('2026-02-06 13:33:39.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 20 -> org.Mandant
-- Column: M_Attribute.AD_Client_ID
-- 2026-02-06T13:33:39.980Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771939,0,548985,554799,646882,'F',TO_TIMESTAMP('2026-02-06 13:33:39.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2026-02-06 13:33:39.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Tab: Merkmal(542068,D) -> Merkmal(548985,D)
-- UI Section: advanced edit
-- 2026-02-06T13:33:40.222Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548985,547508,TO_TIMESTAMP('2026-02-06 13:33:40.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-06 13:33:40.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'advanced edit')
;

-- 2026-02-06T13:33:40.250Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547508 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-06T13:33:40.292Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547508
;

-- 2026-02-06T13:33:40.323Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547508, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540261
;

-- UI Section: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit
-- UI Column: 10
-- 2026-02-06T13:33:40.512Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549168,547508,TO_TIMESTAMP('2026-02-06 13:33:40.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-06 13:33:40.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10
-- UI Element Group: advanced edit
-- 2026-02-06T13:33:40.723Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549168,554800,TO_TIMESTAMP('2026-02-06 13:33:40.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2026-02-06 13:33:40.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.Min. Wert
-- Column: M_Attribute.ValueMin
-- 2026-02-06T13:33:41.105Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771953,0,548985,554800,646883,'F',TO_TIMESTAMP('2026-02-06 13:33:40.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Minimum Value for a field','The Minimum Value indicates the lowest  allowable value for a field.','Y','Y','N','Y','N','N','N','Min. Wert',10,0,0,TO_TIMESTAMP('2026-02-06 13:33:40.823000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.Max. Wert
-- Column: M_Attribute.ValueMax
-- 2026-02-06T13:33:41.434Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771954,0,548985,554800,646884,'F',TO_TIMESTAMP('2026-02-06 13:33:41.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maximum Value for a field','The Maximum Value indicates the highest allowable value for a field','Y','Y','N','Y','N','N','N','Max. Wert',20,0,0,TO_TIMESTAMP('2026-02-06 13:33:41.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.Dynamische Validierung
-- Column: M_Attribute.AD_Val_Rule_ID
-- 2026-02-06T13:33:41.772Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771948,0,548985,554800,646885,'F',TO_TIMESTAMP('2026-02-06 13:33:41.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Regel für die  dynamische Validierung','Diese Regeln bestimmen, wie ein Eintrag als gültig bewertet wird. Sie können Variablen für eine dynamische (kontextbezogene) Validierung verwenden.','Y','Y','N','Y','Y','N','N','Dynamische Validierung',100,80,0,TO_TIMESTAMP('2026-02-06 13:33:41.525000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.Read Only
-- Column: M_Attribute.IsReadOnlyValues
-- 2026-02-06T13:33:42.077Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771957,0,548985,554800,646886,'F',TO_TIMESTAMP('2026-02-06 13:33:41.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'True if the values shall only be set automatically. False if they can be also changed manually, by the user.','Y','Y','N','Y','N','N','N','Read Only',170,0,0,TO_TIMESTAMP('2026-02-06 13:33:41.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.Transferieren wenn null
-- Column: M_Attribute.IsTransferWhenNull
-- 2026-02-06T13:33:42.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771958,0,548985,554800,646887,'F',TO_TIMESTAMP('2026-02-06 13:33:42.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Transfer the attribute from Issue to Receipt even if there are other boxes without the attribute.','Y','Y','N','Y','N','N','N','Transferieren wenn null',180,0,0,TO_TIMESTAMP('2026-02-06 13:33:42.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.Description Pattern
-- Column: M_Attribute.DescriptionPattern
-- 2026-02-06T13:33:42.718Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771960,0,548985,554800,646888,'F',TO_TIMESTAMP('2026-02-06 13:33:42.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Description Pattern',190,0,0,TO_TIMESTAMP('2026-02-06 13:33:42.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Merkmal(542068,D) -> Packvorschrift(548987,D)
-- UI Section: main
-- 2026-02-06T13:33:43.021Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548987,547509,TO_TIMESTAMP('2026-02-06 13:33:42.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-06 13:33:42.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-06T13:33:43.056Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547509 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-06T13:33:43.106Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547509
;

-- 2026-02-06T13:33:43.129Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547509, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540104
;

-- UI Section: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main
-- UI Column: 10
-- 2026-02-06T13:33:43.332Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549169,547509,TO_TIMESTAMP('2026-02-06 13:33:43.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-06 13:33:43.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10
-- UI Element Group: default
-- 2026-02-06T13:33:43.579Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549169,554801,TO_TIMESTAMP('2026-02-06 13:33:43.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-06 13:33:43.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Merkmal
-- Column: M_HU_PI_Attribute.M_Attribute_ID
-- 2026-02-06T13:33:43.985Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771980,0,548987,554801,646889,'F',TO_TIMESTAMP('2026-02-06 13:33:43.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Merkmal','Product Attribute like Color, Size','Y','N','N','N','N','N','N','Merkmal',10,0,0,TO_TIMESTAMP('2026-02-06 13:33:43.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Reihenfolge
-- Column: M_HU_PI_Attribute.SeqNo
-- 2026-02-06T13:33:44.361Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771981,0,548987,554801,646890,'F',TO_TIMESTAMP('2026-02-06 13:33:44.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','Y','N','N','Reihenfolge',10,10,0,TO_TIMESTAMP('2026-02-06 13:33:44.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Split Strategie
-- Column: M_HU_PI_Attribute.SplitterStrategy_JavaClass_ID
-- 2026-02-06T13:33:44.704Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771987,0,548987,554801,646891,'F',TO_TIMESTAMP('2026-02-06 13:33:44.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Split Strategie',20,20,0,TO_TIMESTAMP('2026-02-06 13:33:44.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Propagation Type
-- Column: M_HU_PI_Attribute.PropagationType
-- 2026-02-06T13:33:45.069Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771985,0,548987,554801,646892,'F',TO_TIMESTAMP('2026-02-06 13:33:44.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Propagation Type',30,30,0,TO_TIMESTAMP('2026-02-06 13:33:44.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Maßeinheit
-- Column: M_HU_PI_Attribute.C_UOM_ID
-- 2026-02-06T13:33:45.434Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771988,0,548987,554801,646893,'F',TO_TIMESTAMP('2026-02-06 13:33:45.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','Y','N','N','Maßeinheit',40,40,0,TO_TIMESTAMP('2026-02-06 13:33:45.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Aggregation Strategy
-- Column: M_HU_PI_Attribute.AggregationStrategy_JavaClass_ID
-- 2026-02-06T13:33:45.756Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771986,0,548987,554801,646894,'F',TO_TIMESTAMP('2026-02-06 13:33:45.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Aggregation Strategy',50,50,0,TO_TIMESTAMP('2026-02-06 13:33:45.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Aktiv
-- Column: M_HU_PI_Attribute.IsActive
-- 2026-02-06T13:33:46.075Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771982,0,548987,554801,646895,'F',TO_TIMESTAMP('2026-02-06 13:33:45.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',60,60,0,TO_TIMESTAMP('2026-02-06 13:33:45.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Instanz-Attribut
-- Column: M_HU_PI_Attribute.IsInstanceAttribute
-- 2026-02-06T13:33:46.454Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771983,0,548987,554801,646896,'F',TO_TIMESTAMP('2026-02-06 13:33:46.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)','If selected, the individual instance of the product has this attribute - like the individual Serial or Lot Numbers or  Guarantee Date of a product instance.  If not selected, all instances of the product share the attribute (e.g. color=green).','Y','N','N','Y','Y','N','N','Instanz-Attribut',70,70,0,TO_TIMESTAMP('2026-02-06 13:33:46.179000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Pflichtangabe
-- Column: M_HU_PI_Attribute.IsMandatory
-- 2026-02-06T13:33:46.799Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771984,0,548987,554801,646897,'F',TO_TIMESTAMP('2026-02-06 13:33:46.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Data entry is required in this column','The field must have a value for the record to be saved to the database.','Y','N','N','Y','Y','N','N','Pflichtangabe',80,80,0,TO_TIMESTAMP('2026-02-06 13:33:46.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Schreibgeschützt
-- Column: M_HU_PI_Attribute.IsReadOnly
-- 2026-02-06T13:33:47.105Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771989,0,548987,554801,646898,'F',TO_TIMESTAMP('2026-02-06 13:33:46.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Feld / Eintrag / Berecih ist schreibgeschützt','The Read Only indicates that this field may only be Read.  It may not be updated.','Y','N','N','Y','Y','N','N','Schreibgeschützt',90,90,0,TO_TIMESTAMP('2026-02-06 13:33:46.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Organisation
-- Column: M_HU_PI_Attribute.AD_Org_ID
-- 2026-02-06T13:33:47.443Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,771979,0,548987,554801,646899,'F',TO_TIMESTAMP('2026-02-06 13:33:47.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Organisation',110,100,0,TO_TIMESTAMP('2026-02-06 13:33:47.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Merkmal(542068,D) -> Packvorschrift(548987,D) -> main -> 10 -> default.Mandant
-- Column: M_HU_PI_Attribute.AD_Client_ID
-- 2026-02-06T13:33:47.775Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771974,0,548987,554801,646900,'F',TO_TIMESTAMP('2026-02-06 13:33:47.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Mandant',120,0,0,TO_TIMESTAMP('2026-02-06 13:33:47.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Window: Merkmal, InternalName=
-- 2026-02-06T13:52:39.592Z
UPDATE AD_Window SET IsOverrideInMenu='Y',Updated=TO_TIMESTAMP('2026-02-06 13:52:39.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542068
;


-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Merkmal Suche
-- Column: M_Attribute.M_AttributeSearch_ID
-- 2026-02-06T14:03:11.444Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-06 14:03:11.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646870
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Java Klasse
-- Column: M_Attribute.AD_JavaClass_ID
-- 2026-02-06T14:06:23.993Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-06 14:06:23.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646871
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Standardwert-Logik (SQL)
-- Column: M_Attribute.DefaultValueSQL
-- 2026-02-06T14:08:31.539Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-06 14:08:31.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646869
;

-- Element: IsPricingRelevant
-- 2026-02-06T14:11:30.906Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ist Preisrelevant', PrintName='Ist Preisrelevant',Updated=TO_TIMESTAMP('2026-02-06 14:11:30.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=542236 AND AD_Language='de_DE'
;

-- 2026-02-06T14:11:30.926Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-06T14:11:50.496Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(542236,'de_DE')
;

-- 2026-02-06T14:11:50.516Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542236,'de_DE')
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Maßeinheit
-- Column: M_Attribute.C_UOM_ID
-- 2026-02-06T14:15:04.616Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-02-06 14:15:04.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646872
;

-- 2026-02-06T14:19:00.837Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584502,0,TO_TIMESTAMP('2026-02-06 14:19:00.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','U','Y','Merkmals-Wert OLD','Merkmals-Wert OLD',TO_TIMESTAMP('2026-02-06 14:19:00.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:19:00.857Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584502 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-02-06T14:19:58.246Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-06 14:19:58.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584502 AND AD_Language='en_US'
;

-- 2026-02-06T14:19:58.291Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584502,'en_US')
;

-- Element: null
-- 2026-02-06T14:19:58.654Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-06 14:19:58.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584502 AND AD_Language='de_DE'
;

-- 2026-02-06T14:19:58.699Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584502,'de_DE')
;

-- 2026-02-06T14:19:58.718Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584502,'de_DE')
;

-- Element: null
-- 2026-02-06T14:20:05.280Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-06 14:20:05.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584502 AND AD_Language='de_CH'
;

-- 2026-02-06T14:20:05.325Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584502,'de_CH')
;

-- Element: null
-- 2026-02-06T14:22:19.198Z
UPDATE AD_Element_Trl SET Name='Attribute Value OLD', PrintName='Attribute Value OLD',Updated=TO_TIMESTAMP('2026-02-06 14:22:19.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584502 AND AD_Language='en_US'
;

-- 2026-02-06T14:22:19.222Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-06T14:22:37.299Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584502,'en_US')
;

-- 2026-02-06T14:23:11.610Z
UPDATE AD_Element SET EntityType='U',Updated=TO_TIMESTAMP('2026-02-06 14:23:11.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577408
;

-- 2026-02-06T14:23:11.713Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577408,'de_DE')
;

-- Window: Merkmals-Wert OLD, InternalName=null
-- 2026-02-06T14:24:21.347Z
UPDATE AD_Window SET AD_Element_ID=584502, Description='', Help=NULL, Name='Merkmals-Wert OLD',Updated=TO_TIMESTAMP('2026-02-06 14:24:21.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540825
;

-- 2026-02-06T14:24:21.415Z
UPDATE AD_Window_Trl trl SET Name='Merkmals-Wert OLD' WHERE AD_Window_ID=540825 AND AD_Language='de_DE'
;

-- Name: Merkmals-Wert OLD
-- Action Type: W
-- Window: Merkmals-Wert OLD(540825,D)
-- 2026-02-06T14:24:44.488Z
UPDATE AD_Menu SET Description='', IsActive='Y', Name='Merkmals-Wert OLD',Updated=TO_TIMESTAMP('2026-02-06 14:24:44.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=541408
;

-- 2026-02-06T14:24:44.515Z
UPDATE AD_Menu_Trl trl SET Name='Merkmals-Wert OLD' WHERE AD_Menu_ID=541408 AND AD_Language='de_DE'
;

-- 2026-02-06T14:25:00.909Z
/* DDL */  select update_window_translation_from_ad_element(584502)
;

-- 2026-02-06T14:25:00.933Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540825
;

-- 2026-02-06T14:25:00.973Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(540825)
;

-- Window: Merkmals-Wert, InternalName=null
-- 2026-02-06T14:25:57.889Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,577408,0,542069,TO_TIMESTAMP('2026-02-06 14:25:57.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','U','Y','N','N','Y','N','N','N','Y','Merkmals-Wert','N',TO_TIMESTAMP('2026-02-06 14:25:57.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2026-02-06T14:25:57.920Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542069 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-02-06T14:25:57.965Z
/* DDL */  select update_window_translation_from_ad_element(577408)
;

-- 2026-02-06T14:25:57.989Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542069
;

-- 2026-02-06T14:25:58.010Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542069)
;

-- Window: Merkmals-Wert, InternalName=null
-- 2026-02-06T14:29:26.958Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='D', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=540825, WindowType='M', WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2026-02-06 14:29:26.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542069
;

-- Tab: Merkmals-Wert(542069,D) -> Merkmals-Wert
-- Table: M_AttributeValue
-- 2026-02-06T14:29:27.394Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,DisplayLogic,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,ReadOnlyLogic,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,573818,0,548988,558,542069,'Y',TO_TIMESTAMP('2026-02-06 14:29:27.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Product Attribute Value','','D','N','Individual value of a product attribute (e.g. green, large, ..)','N','A','M_AttributeValue','Y','N','Y','N','N','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Merkmals-Wert','N','',10,0,TO_TIMESTAMP('2026-02-06 14:29:27.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:27.416Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548988 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-06T14:29:27.440Z
/* DDL */  select update_tab_translation_from_ad_element(573818)
;

-- 2026-02-06T14:29:27.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548988)
;

-- 2026-02-06T14:29:27.526Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548988
;

-- 2026-02-06T14:29:27.560Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548988, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 542161
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Merkmals-Wert
-- Column: M_AttributeValue.M_AttributeValue_ID
-- 2026-02-06T14:29:28.016Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8469,771991,1000079,0,548988,0,TO_TIMESTAMP('2026-02-06 14:29:27.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Product Attribute Value',14,'D','Individual value of a product attribute (e.g. green, large, ..)',0,'Y','N','N','N','N','N','N','N','N','N','N','Merkmals-Wert',10,0,1,1,TO_TIMESTAMP('2026-02-06 14:29:27.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:28.036Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771991 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:28.058Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000079)
;

-- 2026-02-06T14:29:28.080Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771991
;

-- 2026-02-06T14:29:28.103Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771991)
;

-- 2026-02-06T14:29:28.149Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771991
;

-- 2026-02-06T14:29:28.170Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771991, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593447
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Mandant
-- Column: M_AttributeValue.AD_Client_ID
-- 2026-02-06T14:29:28.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8464,771992,0,548988,0,TO_TIMESTAMP('2026-02-06 14:29:28.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'@AD_Client_ID@','Mandant für diese Installation.',14,'','D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2026-02-06 14:29:28.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:28.457Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:28.478Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-06T14:29:28.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771992
;

-- 2026-02-06T14:29:28.585Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771992)
;

-- 2026-02-06T14:29:28.630Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771992
;

-- 2026-02-06T14:29:28.652Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771992, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593448
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Organisation
-- Column: M_AttributeValue.AD_Org_ID
-- 2026-02-06T14:29:28.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8458,771993,0,548988,78,TO_TIMESTAMP('2026-02-06 14:29:28.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'0','Organisatorische Einheit des Mandanten',14,'','D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Organisation',20,20,1,1,TO_TIMESTAMP('2026-02-06 14:29:28.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:28.931Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771993 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:28.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-06T14:29:29.014Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771993
;

-- 2026-02-06T14:29:29.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771993)
;

-- 2026-02-06T14:29:29.082Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771993
;

-- 2026-02-06T14:29:29.111Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771993, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593449
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Merkmal
-- Column: M_AttributeValue.M_Attribute_ID
-- 2026-02-06T14:29:29.491Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Reference_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8466,771994,1000921,0,30,548988,113,TO_TIMESTAMP('2026-02-06 14:29:29.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Merkmal',14,'','D','Product Attribute like Color, Size',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Merkmal',30,30,1,1,TO_TIMESTAMP('2026-02-06 14:29:29.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:29.515Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:29.537Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000921)
;

-- 2026-02-06T14:29:29.562Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771994
;

-- 2026-02-06T14:29:29.582Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771994)
;

-- 2026-02-06T14:29:29.627Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771994
;

-- 2026-02-06T14:29:29.648Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771994, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593450
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Suchschlüssel
-- Column: M_AttributeValue.Value
-- 2026-02-06T14:29:29.904Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8459,771995,0,548988,148,TO_TIMESTAMP('2026-02-06 14:29:29.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'','D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Suchschlüssel',40,40,999,1,TO_TIMESTAMP('2026-02-06 14:29:29.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:29.925Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:29.947Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2026-02-06T14:29:29.975Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771995
;

-- 2026-02-06T14:29:29.997Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771995)
;

-- 2026-02-06T14:29:30.040Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771995
;

-- 2026-02-06T14:29:30.063Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771995, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593451
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Name
-- Column: M_AttributeValue.Name
-- 2026-02-06T14:29:30.322Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8468,771996,0,548988,227,TO_TIMESTAMP('2026-02-06 14:29:30.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',60,'','D','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Name',50,50,1,999,1,TO_TIMESTAMP('2026-02-06 14:29:30.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:30.345Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:30.370Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2026-02-06T14:29:30.409Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771996
;

-- 2026-02-06T14:29:30.430Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771996)
;

-- 2026-02-06T14:29:30.476Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771996
;

-- 2026-02-06T14:29:30.495Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771996, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593452
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Beschreibung
-- Column: M_AttributeValue.Description
-- 2026-02-06T14:29:30.778Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8463,771997,0,548988,89,TO_TIMESTAMP('2026-02-06 14:29:30.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'','D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Beschreibung',60,60,999,1,TO_TIMESTAMP('2026-02-06 14:29:30.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:30.800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:30.824Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-02-06T14:29:30.862Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771997
;

-- 2026-02-06T14:29:30.887Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771997)
;

-- 2026-02-06T14:29:30.931Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771997
;

-- 2026-02-06T14:29:30.952Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771997, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593453
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Verfügbare Transaktion
-- Column: M_AttributeValue.AvailableTrx
-- 2026-02-06T14:29:31.234Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550131,771998,0,548988,130,TO_TIMESTAMP('2026-02-06 14:29:30.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Verfügbare Transaktion',70,70,1,1,TO_TIMESTAMP('2026-02-06 14:29:30.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:31.255Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:31.275Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542312)
;

-- 2026-02-06T14:29:31.298Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771998
;

-- 2026-02-06T14:29:31.322Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771998)
;

-- 2026-02-06T14:29:31.365Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771998
;

-- 2026-02-06T14:29:31.388Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771998, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593454
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Aktiv
-- Column: M_AttributeValue.IsActive
-- 2026-02-06T14:29:31.648Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8467,771999,0,548988,44,TO_TIMESTAMP('2026-02-06 14:29:31.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'','D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',80,70,1,1,TO_TIMESTAMP('2026-02-06 14:29:31.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:31.670Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:31.693Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-06T14:29:31.772Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771999
;

-- 2026-02-06T14:29:31.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771999)
;

-- 2026-02-06T14:29:31.839Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 771999
;

-- 2026-02-06T14:29:31.861Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 771999, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593455
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Null Wert
-- Column: M_AttributeValue.IsNullFieldValue
-- 2026-02-06T14:29:32.154Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550944,772000,0,548988,0,TO_TIMESTAMP('2026-02-06 14:29:31.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Null Wert',90,90,1,1,TO_TIMESTAMP('2026-02-06 14:29:31.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:32.177Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:32.201Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541311)
;

-- 2026-02-06T14:29:32.224Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772000
;

-- 2026-02-06T14:29:32.245Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772000)
;

-- 2026-02-06T14:29:32.288Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772000
;

-- 2026-02-06T14:29:32.310Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772000, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 593456
;

-- Field: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> Bild
-- Column: M_AttributeValue.AD_Image_ID
-- 2026-02-06T14:29:32.598Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579287,772001,0,548988,0,TO_TIMESTAMP('2026-02-06 14:29:32.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Image or Icon',10,'D','Images and Icon can be used to display supported graphic formats (gif, jpg, png).
You can either load the image (in the database) or point to a graphic via a URI (i.e. it can point to a resource, http address)',0,'Y','N','N','N','N','N','N','N','N','N','N','Bild',100,1,1,TO_TIMESTAMP('2026-02-06 14:29:32.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-06T14:29:32.630Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-06T14:29:32.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1639)
;

-- 2026-02-06T14:29:32.679Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772001
;

-- 2026-02-06T14:29:32.701Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772001)
;

-- 2026-02-06T14:29:32.758Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772001
;

-- 2026-02-06T14:29:32.780Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772001, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 680615
;

-- Tab: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D)
-- UI Section: main
-- 2026-02-06T14:29:32.970Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548988,547510,TO_TIMESTAMP('2026-02-06 14:29:32.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-02-06 14:29:32.846000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-06T14:29:32.993Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547510 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-06T14:29:33.036Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547510
;

-- 2026-02-06T14:29:33.061Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547510, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 541685
;

-- UI Section: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main
-- UI Column: 10
-- 2026-02-06T14:29:33.237Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549170,547510,TO_TIMESTAMP('2026-02-06 14:29:33.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-06 14:29:33.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10
-- UI Element Group: default
-- 2026-02-06T14:29:33.471Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549170,554802,TO_TIMESTAMP('2026-02-06 14:29:33.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-06 14:29:33.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10 -> default.Merkmal
-- Column: M_AttributeValue.M_Attribute_ID
-- 2026-02-06T14:29:33.837Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771994,0,548988,554802,646901,'F',TO_TIMESTAMP('2026-02-06 14:29:33.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt-Merkmal','Product Attribute like Color, Size','Y','N','N','Y','Y','N','N','Merkmal',10,10,0,TO_TIMESTAMP('2026-02-06 14:29:33.568000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10 -> default.Suchschlüssel
-- Column: M_AttributeValue.Value
-- 2026-02-06T14:29:34.181Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771995,0,548988,554802,646902,'F',TO_TIMESTAMP('2026-02-06 14:29:33.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','Y','N','N','Suchschlüssel',20,20,0,TO_TIMESTAMP('2026-02-06 14:29:33.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10 -> default.Name
-- Column: M_AttributeValue.Name
-- 2026-02-06T14:29:34.561Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771996,0,548988,554802,646903,'F',TO_TIMESTAMP('2026-02-06 14:29:34.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','N','Y','Y','N','N','Name',20,30,0,TO_TIMESTAMP('2026-02-06 14:29:34.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10
-- UI Element Group: data
-- 2026-02-06T14:29:34.779Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549170,554803,TO_TIMESTAMP('2026-02-06 14:29:34.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','data',20,TO_TIMESTAMP('2026-02-06 14:29:34.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10 -> data.Beschreibung
-- Column: M_AttributeValue.Description
-- 2026-02-06T14:29:35.194Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771997,0,548988,554803,646904,'F',TO_TIMESTAMP('2026-02-06 14:29:34.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Beschreibung',20,40,0,TO_TIMESTAMP('2026-02-06 14:29:34.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10 -> data.Bild
-- Column: M_AttributeValue.AD_Image_ID
-- 2026-02-06T14:29:35.499Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772001,0,548988,554803,646905,'F',TO_TIMESTAMP('2026-02-06 14:29:35.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Image or Icon','Images and Icon can be used to display supported graphic formats (gif, jpg, png).
You can either load the image (in the database) or point to a graphic via a URI (i.e. it can point to a resource, http address)','Y','N','N','Y','N','N','N',0,'Bild',30,0,0,TO_TIMESTAMP('2026-02-06 14:29:35.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main
-- UI Column: 20
-- 2026-02-06T14:29:35.718Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549171,547510,TO_TIMESTAMP('2026-02-06 14:29:35.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-06 14:29:35.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 20
-- UI Element Group: Flags
-- 2026-02-06T14:29:35.932Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549171,554804,TO_TIMESTAMP('2026-02-06 14:29:35.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Flags',10,TO_TIMESTAMP('2026-02-06 14:29:35.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 20 -> Flags.Aktiv
-- Column: M_AttributeValue.IsActive
-- 2026-02-06T14:29:36.295Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771999,0,548988,554804,646906,'F',TO_TIMESTAMP('2026-02-06 14:29:36.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N',0,'Aktiv',10,50,0,TO_TIMESTAMP('2026-02-06 14:29:36.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 20 -> Flags.Null Wert
-- Column: M_AttributeValue.IsNullFieldValue
-- 2026-02-06T14:29:36.660Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772000,0,548988,554804,646907,'F',TO_TIMESTAMP('2026-02-06 14:29:36.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'Null Wert',20,60,0,TO_TIMESTAMP('2026-02-06 14:29:36.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 20 -> Flags.Verfügbare Transaktion
-- Column: M_AttributeValue.AvailableTrx
-- 2026-02-06T14:29:36.976Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771998,0,548988,554804,646908,'F',TO_TIMESTAMP('2026-02-06 14:29:36.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Verfügbare Transaktion',30,0,0,TO_TIMESTAMP('2026-02-06 14:29:36.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 20
-- UI Element Group: orgs
-- 2026-02-06T14:29:37.188Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549171,554805,TO_TIMESTAMP('2026-02-06 14:29:37.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','orgs',30,TO_TIMESTAMP('2026-02-06 14:29:37.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 20 -> orgs.Organisation
-- Column: M_AttributeValue.AD_Org_ID
-- 2026-02-06T14:29:37.562Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771993,0,548988,554805,646909,'F',TO_TIMESTAMP('2026-02-06 14:29:37.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N',0,'Organisation',10,70,0,TO_TIMESTAMP('2026-02-06 14:29:37.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 20 -> orgs.Mandant
-- Column: M_AttributeValue.AD_Client_ID
-- 2026-02-06T14:29:37.865Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771992,0,548988,554805,646910,'F',TO_TIMESTAMP('2026-02-06 14:29:37.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2026-02-06 14:29:37.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10 -> data.Bild
-- Column: M_AttributeValue.AD_Image_ID
-- 2026-02-06T15:03:11.583Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-06 15:03:11.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646905
;

-- Window: Merkmals-Wert, InternalName=null
-- 2026-02-06T15:07:30.678Z
UPDATE AD_Window SET IsOverrideInMenu='Y',Updated=TO_TIMESTAMP('2026-02-06 15:07:30.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542069
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Mind. Haltbarkeit Tage
-- Column: M_Product_Category.GuaranteeDaysMin
-- 2026-02-10T08:35:08.115Z
UPDATE AD_UI_Element SET IsAdvancedField='Y', IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-02-10 08:35:08.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646766
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> main -> 10 -> attributes.Mind. Haltbarkeit Tage
-- Column: M_Product_Category.GuaranteeDaysMin
-- 2026-02-10T11:03:05.483Z
UPDATE AD_UI_Element SET IsActive='N', IsAdvancedField='N',Updated=TO_TIMESTAMP('2026-02-10 11:03:05.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646766
;

-- UI Element: Produkt Kategorie(542066,U) -> Produkt-Kategorie(548973,D) -> advanced edit -> 10 -> advanced edit.Min. Garantie-Tage
-- Column: M_Product_Category.GuaranteeDaysMin
-- 2026-02-10T11:04:01.967Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771789,0,548973,554784,646932,'F',TO_TIMESTAMP('2026-02-10 11:04:01.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mindestanzahl Garantie-Tage','When selecting batch/products with a guarantee date, the minimum left guarantee days for automatic picking.  You can pick any batch/product manually. ','Y','Y','N','Y','N','N','N',0,'Min. Garantie-Tage',20,0,0,TO_TIMESTAMP('2026-02-10 11:04:01.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Standardwert-Logik (SQL)
-- Column: M_Attribute.DefaultValueSQL
-- 2026-02-10T11:11:40.999Z
UPDATE AD_UI_Element SET IsActive='N', IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-02-10 11:11:40.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646869
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Merkmal Suche
-- Column: M_Attribute.M_AttributeSearch_ID
-- 2026-02-10T11:14:33.395Z
UPDATE AD_UI_Element SET IsActive='N', IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-02-10 11:14:33.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646870
;
-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> main -> 10 -> technical.Java Klasse
-- Column: M_Attribute.AD_JavaClass_ID
-- 2026-02-10T11:16:52.148Z
UPDATE AD_UI_Element SET IsActive='N', IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-02-10 11:16:52.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646871
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.AttributeValueHandler Javaklasse
-- Column: M_Attribute.AD_JavaClass_ID
-- 2026-02-10T11:38:03.390Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771949,0,548985,554800,646936,'F',TO_TIMESTAMP('2026-02-10 11:38:03.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Javaklasse, die das Interface IAttributeValueHandler implementiert. Falls leer wird eine Defaultimplementierung benutzt, die die dynamische Validierungsregel aufruft.','Y','Y','N','Y','N','N','N',0,'AttributeValueHandler Javaklasse',200,0,0,TO_TIMESTAMP('2026-02-10 11:38:03.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.Merkmal-Suche
-- Column: M_Attribute.M_AttributeSearch_ID
-- 2026-02-10T11:39:41.476Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771946,0,548985,554800,646937,'F',TO_TIMESTAMP('2026-02-10 11:39:41.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gemeinsames Such-Merkmal','Attributes are specific to a Product Attribute Set (e.g. Size for T-Shirts: S,M,L). If you have multiple attributes and want to search under a common sttribute, you define a search sttribute. Example: have one Size search attribute combining the values of all different sizes (Size for Dress Shirt  XL,L,M,S,XS). The Attribute Search allows you to have all values available for selection.  This eases the maintenance of the individual product attribute.','Y','Y','N','Y','N','N','N',0,'Merkmal-Suche',210,0,0,TO_TIMESTAMP('2026-02-10 11:39:41.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmal(542068,D) -> Merkmal(548985,D) -> advanced edit -> 10 -> advanced edit.Standardwert-Logik (SQL)
-- Column: M_Attribute.DefaultValueSQL
-- 2026-02-10T12:03:20.405Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771965,0,548985,554800,646938,'F',TO_TIMESTAMP('2026-02-10 12:03:20.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'SQL-Ausdruck, der verwendet wird, um den Standardwert für dieses Attribut zu berechnen. Der Ausdruck kann variable Platzhalter enthalten (z. B. @M_Product_ID@, @C_Order_ID@, @TableName@), die zur Laufzeit durch die tatsächlichen Kontextwerte ersetzt werden. Der resultierende Wert muss dem für das Attribut definierten Wertetyp entsprechen. Wenn dieses Feld leer ist, besitzt das Attribut keinen SQL-basierten Standardwert.','SQL-Ausdruck, der verwendet wird, um den Standardwert für dieses Attribut zu berechnen.
Der Ausdruck kann variable Platzhalter enthalten (z. B. @M_Product_ID@, @C_Order_ID@, @TableName@), die zur Laufzeit durch die tatsächlichen Kontextwerte ersetzt werden. Der resultierende Wert muss dem für das Attribut definierten Wertetyp entsprechen. Wenn dieses Feld leer ist, besitzt das Attribut keinen SQL-basierten Standardwert.','Y','Y','N','Y','N','N','N',0,'Standardwert-Logik (SQL)',220,0,0,TO_TIMESTAMP('2026-02-10 12:03:20.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Merkmals-Wert(542069,D) -> Merkmals-Wert(548988,D) -> main -> 10 -> data.Bild
-- Column: M_AttributeValue.AD_Image_ID
-- 2026-02-10T12:05:54.491Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2026-02-10 12:05:54.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646905
;
