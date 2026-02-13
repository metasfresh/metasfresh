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