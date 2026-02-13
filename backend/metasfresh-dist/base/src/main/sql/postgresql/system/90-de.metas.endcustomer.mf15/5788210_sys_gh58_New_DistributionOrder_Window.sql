-- Run mode: SWING_CLIENT

-- 2026-02-13T14:44:15.466Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584532,0,TO_TIMESTAMP('2026-02-13 14:44:15.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Distribution Order allow create Order inter warehouse to supply a demand ','U','Distribution Order allow create Order inter warehouse to supply a demand ','Y','Distributionsauftrag OLD','Distributionsauftrag OLD',TO_TIMESTAMP('2026-02-13 14:44:15.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:44:15.493Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584532 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-02-13T14:44:27.743Z
UPDATE AD_Element SET EntityType='U',Updated=TO_TIMESTAMP('2026-02-13 14:44:27.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574325
;

-- 2026-02-13T14:44:27.849Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574325,'de_DE')
;

-- Element: null
-- 2026-02-13T14:45:39.827Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Distribution Order OLD', PrintName='Distribution Order OLD',Updated=TO_TIMESTAMP('2026-02-13 14:45:39.827000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584532 AND AD_Language='en_US'
;

-- 2026-02-13T14:45:39.853Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-13T14:45:45.534Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584532,'en_US')
;

-- Element: null
-- 2026-02-13T14:45:50.029Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-13 14:45:50.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584532 AND AD_Language='de_DE'
;

-- 2026-02-13T14:45:50.079Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584532,'de_DE')
;

-- 2026-02-13T14:45:50.101Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584532,'de_DE')
;

-- Window: Distributionsauftrag OLD, InternalName=53012 (Todo: Set Internal Name for UI testing)
-- 2026-02-13T14:46:59.569Z
UPDATE AD_Window SET AD_Element_ID=584532, Description='Distribution Order allow create Order inter warehouse to supply a demand ', Help='Distribution Order allow create Order inter warehouse to supply a demand ', Name='Distributionsauftrag OLD',Updated=TO_TIMESTAMP('2026-02-13 14:46:59.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=53012
;

-- 2026-02-13T14:46:59.591Z
UPDATE AD_Window_Trl trl SET Name='Distributionsauftrag OLD' WHERE AD_Window_ID=53012 AND AD_Language='de_DE'
;

-- Name: Distributionsauftrag OLD
-- Action Type: W
-- Window: Distributionsauftrag OLD(53012,EE01)
-- 2026-02-13T14:47:14.212Z
UPDATE AD_Menu SET Description='Distribution Order allow create Order inter warehouse to supply a demand ', IsActive='Y', Name='Distributionsauftrag OLD',Updated=TO_TIMESTAMP('2026-02-13 14:47:14.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540829
;

-- 2026-02-13T14:47:14.247Z
UPDATE AD_Menu_Trl trl SET Name='Distributionsauftrag OLD' WHERE AD_Menu_ID=540829 AND AD_Language='de_DE'
;

-- Name: Distributionsauftrag OLD
-- Action Type: W
-- Window: Distributionsauftrag OLD(53012,EE01)
-- 2026-02-13T14:47:46.809Z
UPDATE AD_Menu SET Description='Distribution Order allow create Order inter warehouse to supply a demand ', IsActive='Y', Name='Distributionsauftrag OLD',Updated=TO_TIMESTAMP('2026-02-13 14:47:46.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=53068
;

-- 2026-02-13T14:47:46.832Z
UPDATE AD_Menu_Trl trl SET Name='Distributionsauftrag OLD' WHERE AD_Menu_ID=53068 AND AD_Language='de_DE'
;

-- 2026-02-13T14:47:59.098Z
/* DDL */  select update_window_translation_from_ad_element(584532)
;

-- 2026-02-13T14:47:59.137Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=53012
;

-- 2026-02-13T14:47:59.170Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(53012)
;

-- Window: Distributionsauftrag, InternalName=Distributionsauftrag NEW
-- 2026-02-13T14:49:40.307Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,Help,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,ZoomIntoPriority) VALUES (0,574325,0,542091,TO_TIMESTAMP('2026-02-13 14:49:39.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Distribution Order allow create Order inter warehouse to supply a demand ','U','Distribution Order allow create Order inter warehouse to supply a demand ','Distributionsauftrag NEW','Y','N','N','Y','N','N','N','N','Distributionsauftrag','N',TO_TIMESTAMP('2026-02-13 14:49:39.986000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',100)
;

-- 2026-02-13T14:49:40.329Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542091 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-02-13T14:49:40.373Z
/* DDL */  select update_window_translation_from_ad_element(574325)
;

-- 2026-02-13T14:49:40.398Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542091
;

-- 2026-02-13T14:49:40.420Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542091)
;

-- Window: Distributionsauftrag, InternalName=Distributionsauftrag NEW
-- 2026-02-13T14:54:36.622Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='EE01', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='N', Overrides_Window_ID=53012, WindowType='M', WinHeight=NULL, WinWidth=NULL, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2026-02-13 14:54:36.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542091
;

-- Tab: Distributionsauftrag(542091,EE01) -> Distributionsauftrag
-- Table: DD_Order
-- 2026-02-13T14:54:37.190Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,573885,0,53044,549052,53037,542091,'Y',TO_TIMESTAMP('2026-02-13 14:54:36.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Distribution Order allow create Order inter warehouse to supply a demand ','EE01','N','Distribution Order allow create Order inter warehouse to supply a demand ','A','DD_Order','Y','N','Y','Y','Y','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','Distributionsauftrag','N',10,0,TO_TIMESTAMP('2026-02-13 14:54:36.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'DD_Order.IsSimulated=''N''')
;

-- 2026-02-13T14:54:37.214Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549052 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-13T14:54:37.239Z
/* DDL */  select update_tab_translation_from_ad_element(573885)
;

-- 2026-02-13T14:54:37.280Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549052)
;

-- 2026-02-13T14:54:37.377Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 549052
;

-- 2026-02-13T14:54:37.402Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 549052, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 53055
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Aktiv
-- Column: DD_Order.IsActive
-- 2026-02-13T14:54:38.040Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53896,773801,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:37.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',10,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:37.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:38.067Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:38.122Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-13T14:54:38.196Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773801
;

-- 2026-02-13T14:54:38.218Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773801)
;

-- 2026-02-13T14:54:38.262Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773801
;

-- 2026-02-13T14:54:38.283Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773801, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54172
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Referenced Order
-- Column: DD_Order.Ref_Order_ID
-- 2026-02-13T14:54:38.593Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53914,773802,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:38.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Reference to corresponding Sales/Purchase Order',10,'EE01','Reference of the Sales Order Line to the corresponding Purchase Order Line or vice versa.',0,'Y','N','N','N','N','N','N','N','N','N','Y','Referenced Order',20,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:38.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:38.616Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:38.640Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2431)
;

-- 2026-02-13T14:54:38.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773802
;

-- 2026-02-13T14:54:38.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773802)
;

-- 2026-02-13T14:54:38.731Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773802
;

-- 2026-02-13T14:54:38.752Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773802, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54173
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Strittig
-- Column: DD_Order.IsInDispute
-- 2026-02-13T14:54:39.056Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53900,773803,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:38.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'EE01','',0,'Y','N','N','N','N','N','N','N','N','N','N','Strittig',30,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:38.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:39.082Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:39.104Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2543)
;

-- 2026-02-13T14:54:39.130Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773803
;

-- 2026-02-13T14:54:39.155Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773803)
;

-- 2026-02-13T14:54:39.202Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773803
;

-- 2026-02-13T14:54:39.224Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773803, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54174
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Generate To
-- Column: DD_Order.GenerateTo
-- 2026-02-13T14:54:39.503Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53895,773804,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:39.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Generate To',1,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Generate To',40,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:39.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:39.529Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:39.550Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1491)
;

-- 2026-02-13T14:54:39.582Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773804
;

-- 2026-02-13T14:54:39.603Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773804)
;

-- 2026-02-13T14:54:39.645Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773804
;

-- 2026-02-13T14:54:39.668Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773804, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54175
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Selektiert
-- Column: DD_Order.IsSelected
-- 2026-02-13T14:54:39.968Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53904,773805,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:39.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Selektiert',50,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:39.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:39.992Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:40.013Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1321)
;

-- 2026-02-13T14:54:40.045Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773805
;

-- 2026-02-13T14:54:40.068Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773805)
;

-- 2026-02-13T14:54:40.113Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773805
;

-- 2026-02-13T14:54:40.134Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773805, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54176
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Process Now
-- Column: DD_Order.Processing
-- 2026-02-13T14:54:40.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53913,773806,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:40.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Process Now',60,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:40.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:40.455Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:40.479Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524)
;

-- 2026-02-13T14:54:40.512Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773806
;

-- 2026-02-13T14:54:40.532Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773806)
;

-- 2026-02-13T14:54:40.577Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773806
;

-- 2026-02-13T14:54:40.602Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773806, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54177
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> E-Mail senden
-- Column: DD_Order.SendEMail
-- 2026-02-13T14:54:40.893Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53916,773807,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:40.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Enable sending Document EMail',1,'EE01','Send emails with document attached (e.g. Invoice, Delivery Note, etc.)',0,'Y','N','N','N','N','N','N','N','N','N','N','E-Mail senden',70,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:40.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:40.919Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:40.953Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1978)
;

-- 2026-02-13T14:54:40.976Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773807
;

-- 2026-02-13T14:54:41Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773807)
;

-- 2026-02-13T14:54:41.045Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773807
;

-- 2026-02-13T14:54:41.067Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773807, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54178
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Verarbeitet
-- Column: DD_Order.Processed
-- 2026-02-13T14:54:41.352Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53912,773808,1000414,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:41.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'EE01','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','N','Y','Y','Verarbeitet',80,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:41.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:41.376Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:41.400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000414)
;

-- 2026-02-13T14:54:41.430Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773808
;

-- 2026-02-13T14:54:41.454Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773808)
;

-- 2026-02-13T14:54:41.506Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773808
;

-- 2026-02-13T14:54:41.528Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773808, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54179
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Kosten
-- Column: DD_Order.C_Charge_ID
-- 2026-02-13T14:54:41.803Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53872,130,773809,1002300,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:41.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zusätzliche Kosten',22,'EE01','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)',0,'Y','N','N','N','N','N','N','N','N','N','N','Kosten',90,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:41.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:41.824Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:41.848Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002300)
;

-- 2026-02-13T14:54:41.874Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773809
;

-- 2026-02-13T14:54:41.911Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773809)
;

-- 2026-02-13T14:54:41.977Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773809
;

-- 2026-02-13T14:54:41.999Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773809, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54180
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Rechnung
-- Column: DD_Order.C_Invoice_ID
-- 2026-02-13T14:54:42.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53874,773810,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:42.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Invoice Identifier',22,'EE01','The Invoice Document.',0,'Y','N','N','N','N','N','N','N','N','N','N','Rechnung',100,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:42.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:42.351Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:42.372Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008)
;

-- 2026-02-13T14:54:42.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773810
;

-- 2026-02-13T14:54:42.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773810)
;

-- 2026-02-13T14:54:42.463Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773810
;

-- 2026-02-13T14:54:42.490Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773810, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54181
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Auftrag
-- Column: DD_Order.C_Order_ID
-- 2026-02-13T14:54:42.791Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53875,773811,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:42.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',22,'EE01','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.',0,'Y','N','N','N','N','N','N','N','N','N','N','Auftrag',110,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:42.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:42.842Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:42.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558)
;

-- 2026-02-13T14:54:42.894Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773811
;

-- 2026-02-13T14:54:42.916Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773811)
;

-- 2026-02-13T14:54:42.963Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773811
;

-- 2026-02-13T14:54:42.985Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773811, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54182
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Buchungsstatus
-- Column: DD_Order.Posted
-- 2026-02-13T14:54:43.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53910,773812,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:43.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Buchungsstatus',1,'@Processed/N@=''Y''','EE01','Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Buchungsstatus',120,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:43.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:43.290Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:43.312Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1308)
;

-- 2026-02-13T14:54:43.338Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773812
;

-- 2026-02-13T14:54:43.362Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773812)
;

-- 2026-02-13T14:54:43.419Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773812
;

-- 2026-02-13T14:54:43.440Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773812, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54183
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Gebühr
-- Column: DD_Order.ChargeAmt
-- 2026-02-13T14:54:43.715Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53877,130,773813,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:43.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','Y','Gebühr',130,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:43.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:43.735Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:43.759Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(849)
;

-- 2026-02-13T14:54:43.782Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773813
;

-- 2026-02-13T14:54:43.806Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773813)
;

-- 2026-02-13T14:54:43.863Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773813
;

-- 2026-02-13T14:54:43.886Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773813, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54184
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Create Confirm
-- Column: DD_Order.CreateConfirm
-- 2026-02-13T14:54:44.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53878,773814,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:43.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Create Confirm',140,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:43.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:44.202Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:44.225Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2520)
;

-- 2026-02-13T14:54:44.255Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773814
;

-- 2026-02-13T14:54:44.277Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773814)
;

-- 2026-02-13T14:54:44.324Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773814
;

-- 2026-02-13T14:54:44.347Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773814, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54185
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Position(en) kopieren von
-- Column: DD_Order.CreateFrom
-- 2026-02-13T14:54:44.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53879,773815,1002451,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:44.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Prozess, der die Position(en) aus einem bestehenden Beleg kopiert',1,'EE01','Startet einen Prozess, der die Position(en) zu diesem Belegkopf durch Kopie aus einem bestehenden Beleg anlegt.',0,'Y','N','N','N','N','N','N','N','N','N','N','Position(en) kopieren von',150,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:44.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:44.646Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:44.717Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002451)
;

-- 2026-02-13T14:54:44.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773815
;

-- 2026-02-13T14:54:44.779Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773815)
;

-- 2026-02-13T14:54:44.824Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773815
;

-- 2026-02-13T14:54:44.853Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773815, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54186
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Packstück erstellen
-- Column: DD_Order.CreatePackage
-- 2026-02-13T14:54:45.141Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53880,773816,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:44.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Packstück erstellen',160,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:44.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:45.177Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:45.204Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2525)
;

-- 2026-02-13T14:54:45.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773816
;

-- 2026-02-13T14:54:45.258Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773816)
;

-- 2026-02-13T14:54:45.303Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773816
;

-- 2026-02-13T14:54:45.325Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773816, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54187
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Anzahl Handling Units
-- Column: DD_Order.NoPackages
-- 2026-02-13T14:54:45.624Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53907,773817,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:45.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzahl der Handling Units die versendet werden',22,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Anzahl Handling Units',170,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:45.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:45.646Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:45.680Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2113)
;

-- 2026-02-13T14:54:45.704Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773817
;

-- 2026-02-13T14:54:45.725Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773817)
;

-- 2026-02-13T14:54:45.768Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773817
;

-- 2026-02-13T14:54:45.792Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773817, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54188
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Verkaufs-Transaktion
-- Column: DD_Order.IsSOTrx
-- 2026-02-13T14:54:46.066Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53903,773818,1001855,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:45.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist eine Verkaufs-Transaktion',1,'EE01','Das Selektionsfeld "Verkaufs-Transaktion" zeigt an, dass es sich um eine Verkaufs-Transaktion handelt.',0,'Y','N','N','N','N','N','N','N','N','N','N','Verkaufs-Transaktion',180,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:45.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:46.089Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:46.121Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001855)
;

-- 2026-02-13T14:54:46.142Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773818
;

-- 2026-02-13T14:54:46.165Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773818)
;

-- 2026-02-13T14:54:46.208Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773818
;

-- 2026-02-13T14:54:46.228Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773818, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54189
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Mandant
-- Column: DD_Order.AD_Client_ID
-- 2026-02-13T14:54:46.538Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53924,773819,0,549052,65,TO_TIMESTAMP('2026-02-13 14:54:46.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:46.250000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:46.561Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:46.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-13T14:54:46.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773819
;

-- 2026-02-13T14:54:46.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773819)
;

-- 2026-02-13T14:54:46.735Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773819
;

-- 2026-02-13T14:54:46.758Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773819, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54190
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Organisation
-- Column: DD_Order.AD_Org_ID
-- 2026-02-13T14:54:47.025Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53866,773820,0,549052,56,TO_TIMESTAMP('2026-02-13 14:54:46.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',22,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Organisation',20,20,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:46.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:47.064Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:47.103Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-13T14:54:47.187Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773820
;

-- 2026-02-13T14:54:47.209Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773820)
;

-- 2026-02-13T14:54:47.254Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773820
;

-- 2026-02-13T14:54:47.287Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773820, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54191
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Nr.
-- Column: DD_Order.DocumentNo
-- 2026-02-13T14:54:47.549Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsMandatory,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53864,773821,1001786,0,549052,65,TO_TIMESTAMP('2026-02-13 14:54:47.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document sequence number of the document',20,'EE01','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','Y','Y','N','N','N','N','Y','N','N','N','Nr.',30,30,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:47.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:47.571Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:47.608Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001786)
;

-- 2026-02-13T14:54:47.631Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773821
;

-- 2026-02-13T14:54:47.654Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773821)
;

-- 2026-02-13T14:54:47.714Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773821
;

-- 2026-02-13T14:54:47.737Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773821, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54192
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Referenz
-- Column: DD_Order.POReference
-- 2026-02-13T14:54:48.029Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53908,773822,0,549052,62,TO_TIMESTAMP('2026-02-13 14:54:47.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden',22,'EE01','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Referenz',40,60,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:47.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:48.051Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:48.072Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2026-02-13T14:54:48.098Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773822
;

-- 2026-02-13T14:54:48.120Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773822)
;

-- 2026-02-13T14:54:48.163Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773822
;

-- 2026-02-13T14:54:48.185Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773822, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54193
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Beschreibung
-- Column: DD_Order.Description
-- 2026-02-13T14:54:48.453Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53890,773823,0,549052,89,TO_TIMESTAMP('2026-02-13 14:54:48.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Beschreibung',50,70,0,999,1,TO_TIMESTAMP('2026-02-13 14:54:48.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:48.477Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:48.499Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-02-13T14:54:48.532Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773823
;

-- 2026-02-13T14:54:48.555Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773823)
;

-- 2026-02-13T14:54:48.598Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773823
;

-- 2026-02-13T14:54:48.619Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773823, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54194
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Belegart
-- Column: DD_Order.C_DocType_ID
-- 2026-02-13T14:54:48.893Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53873,773824,0,549052,60,TO_TIMESTAMP('2026-02-13 14:54:48.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',22,'EE01','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Belegart',60,80,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:48.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:48.916Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:48.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2026-02-13T14:54:48.967Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773824
;

-- 2026-02-13T14:54:49.003Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773824)
;

-- 2026-02-13T14:54:49.046Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773824
;

-- 2026-02-13T14:54:49.069Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773824, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54195
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Kundenbetreuer
-- Column: DD_Order.SalesRep_ID
-- 2026-02-13T14:54:49.347Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53915,773825,0,549052,104,TO_TIMESTAMP('2026-02-13 14:54:49.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'EE01','',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Kundenbetreuer',70,90,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:49.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:49.369Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:49.391Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1063)
;

-- 2026-02-13T14:54:49.418Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773825
;

-- 2026-02-13T14:54:49.441Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773825)
;

-- 2026-02-13T14:54:49.485Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773825
;

-- 2026-02-13T14:54:49.509Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773825, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54196
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Datum
-- Column: DD_Order.DateOrdered
-- 2026-02-13T14:54:49.758Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53884,773826,1001509,0,549052,97,TO_TIMESTAMP('2026-02-13 14:54:49.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags',7,'EE01','Bezeichnet das Datum, an dem die Ware bestellt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Datum',80,100,-1,1,1,TO_TIMESTAMP('2026-02-13 14:54:49.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:49.781Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:49.804Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001509)
;

-- 2026-02-13T14:54:49.825Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773826
;

-- 2026-02-13T14:54:49.857Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773826)
;

-- 2026-02-13T14:54:49.900Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773826
;

-- 2026-02-13T14:54:49.921Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773826, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54197
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Zugesagter Termin
-- Column: DD_Order.DatePromised
-- 2026-02-13T14:54:50.194Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53886,773827,0,549052,117,TO_TIMESTAMP('2026-02-13 14:54:49.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',10,'EE01','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Zugesagter Termin',90,110,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:49.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:50.217Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:50.239Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269)
;

-- 2026-02-13T14:54:50.265Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773827
;

-- 2026-02-13T14:54:50.289Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773827)
;

-- 2026-02-13T14:54:50.341Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773827
;

-- 2026-02-13T14:54:50.364Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773827, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54198
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Geschäftspartner
-- Column: DD_Order.C_BPartner_ID
-- 2026-02-13T14:54:50.609Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53869,130,773828,1001638,0,549052,106,TO_TIMESTAMP('2026-02-13 14:54:50.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',22,'EE01','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Geschäftspartner',100,120,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:50.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:50.632Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:50.654Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001638)
;

-- 2026-02-13T14:54:50.679Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773828
;

-- 2026-02-13T14:54:50.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773828)
;

-- 2026-02-13T14:54:50.746Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773828
;

-- 2026-02-13T14:54:50.768Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773828, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54199
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Standort
-- Column: DD_Order.C_BPartner_Location_ID
-- 2026-02-13T14:54:50.998Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53870,130,773829,1002339,0,549052,62,TO_TIMESTAMP('2026-02-13 14:54:50.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Auslieferungs-) Adresse des Geschäftspartners',22,'EE01','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Standort',110,130,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:50.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:51.020Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:51.045Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002339)
;

-- 2026-02-13T14:54:51.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773829
;

-- 2026-02-13T14:54:51.094Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773829)
;

-- 2026-02-13T14:54:51.141Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773829
;

-- 2026-02-13T14:54:51.162Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773829, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54200
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Lieferweg
-- Column: DD_Order.M_Shipper_ID
-- 2026-02-13T14:54:51.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53905,130,773830,0,549052,68,TO_TIMESTAMP('2026-02-13 14:54:51.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung',22,'EE01','"Lieferweg" bezeichnet die Art der Warenlieferung.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lieferweg',120,140,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:51.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:51.452Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:51.476Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455)
;

-- 2026-02-13T14:54:51.498Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773830
;

-- 2026-02-13T14:54:51.521Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773830)
;

-- 2026-02-13T14:54:51.566Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773830
;

-- 2026-02-13T14:54:51.588Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773830, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54201
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Lieferkontakt
-- Column: DD_Order.AD_User_ID
-- 2026-02-13T14:54:51.842Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53867,130,773831,1001700,0,549052,86,TO_TIMESTAMP('2026-02-13 14:54:51.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact',22,'EE01','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Lieferkontakt',130,150,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:51.612000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:51.865Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:51.889Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001700)
;

-- 2026-02-13T14:54:51.909Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773831
;

-- 2026-02-13T14:54:51.929Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773831)
;

-- 2026-02-13T14:54:51.973Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773831
;

-- 2026-02-13T14:54:51.994Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773831, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54202
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Lieferung
-- Column: DD_Order.DeliveryViaRule
-- 2026-02-13T14:54:52.260Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53889,130,773832,0,549052,67,TO_TIMESTAMP('2026-02-13 14:54:52.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird',1,'EE01','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lieferung',140,160,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:52.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:52.282Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:52.304Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(274)
;

-- 2026-02-13T14:54:52.336Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773832
;

-- 2026-02-13T14:54:52.359Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773832)
;

-- 2026-02-13T14:54:52.407Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773832
;

-- 2026-02-13T14:54:52.428Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773832, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54203
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Lieferart
-- Column: DD_Order.DeliveryRule
-- 2026-02-13T14:54:52.696Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53888,130,773833,0,549052,60,TO_TIMESTAMP('2026-02-13 14:54:52.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die zeitliche Steuerung von Lieferungen',1,'EE01','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Lieferart',150,170,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:52.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:52.718Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773833 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:52.741Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(555)
;

-- 2026-02-13T14:54:52.776Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773833
;

-- 2026-02-13T14:54:52.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773833)
;

-- 2026-02-13T14:54:52.860Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773833
;

-- 2026-02-13T14:54:52.882Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773833, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54204
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Lager
-- Column: DD_Order.M_Warehouse_ID
-- 2026-02-13T14:54:53.141Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53906,130,773834,0,549052,115,TO_TIMESTAMP('2026-02-13 14:54:52.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',22,'EE01','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lager',160,180,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:52.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:53.165Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:53.204Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2026-02-13T14:54:53.239Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773834
;

-- 2026-02-13T14:54:53.259Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773834)
;

-- 2026-02-13T14:54:53.303Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773834
;

-- 2026-02-13T14:54:53.326Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773834, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54205
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Abweichende Lieferadresse
-- Column: DD_Order.IsDropShip
-- 2026-02-13T14:54:53.642Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53899,773835,0,549052,107,TO_TIMESTAMP('2026-02-13 14:54:53.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert',1,'EE01','Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Abweichende Lieferadresse',170,190,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:53.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:53.665Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:53.699Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2466)
;

-- 2026-02-13T14:54:53.738Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773835
;

-- 2026-02-13T14:54:53.763Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773835)
;

-- 2026-02-13T14:54:53.817Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773835
;

-- 2026-02-13T14:54:53.845Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773835, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54206
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Lieferdatum
-- Column: DD_Order.ShipDate
-- 2026-02-13T14:54:54.140Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53917,130,773836,1002787,0,549052,81,TO_TIMESTAMP('2026-02-13 14:54:53.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Shipment Date/Time',7,'@DeliveryViaRule@=S','EE01','Actual Date/Time of Shipment (pick up)',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lieferdatum',180,200,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:53.869000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:54.163Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:54.192Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002787)
;

-- 2026-02-13T14:54:54.217Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773836
;

-- 2026-02-13T14:54:54.248Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773836)
;

-- 2026-02-13T14:54:54.296Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773836
;

-- 2026-02-13T14:54:54.331Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773836, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54207
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Eingangsdatum
-- Column: DD_Order.DateReceived
-- 2026-02-13T14:54:54.656Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53887,130,773837,0,549052,100,TO_TIMESTAMP('2026-02-13 14:54:54.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, zu dem ein Produkt empfangen wurde',7,'@DeliveryViaRule@=S','EE01','"Eingangsdatum" bezeichnet das Datum, zu dem das Produkt empfangen wurde',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Eingangsdatum',190,210,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:54.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:54.679Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:54.703Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1324)
;

-- 2026-02-13T14:54:54.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773837
;

-- 2026-02-13T14:54:54.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773837)
;

-- 2026-02-13T14:54:54.798Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773837
;

-- 2026-02-13T14:54:54.818Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773837, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54208
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Kommissionierdatum
-- Column: DD_Order.PickDate
-- 2026-02-13T14:54:55.079Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53909,130,773838,0,549052,137,TO_TIMESTAMP('2026-02-13 14:54:54.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum/Zeit der Kommissionierung für die Lieferung',7,'','EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Kommissionierdatum',200,220,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:54.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:55.104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:55.133Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2117)
;

-- 2026-02-13T14:54:55.160Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773838
;

-- 2026-02-13T14:54:55.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773838)
;

-- 2026-02-13T14:54:55.231Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773838
;

-- 2026-02-13T14:54:55.252Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773838, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54209
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Date printed
-- Column: DD_Order.DatePrinted
-- 2026-02-13T14:54:55.531Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53885,130,773839,0,549052,82,TO_TIMESTAMP('2026-02-13 14:54:55.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Date the document was printed.',7,'@DeliveryViaRule@=S','EE01','Indicates the Date that a document was printed.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Date printed',210,230,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:55.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:55.556Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:55.578Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1091)
;

-- 2026-02-13T14:54:55.610Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773839
;

-- 2026-02-13T14:54:55.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773839)
;

-- 2026-02-13T14:54:55.688Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773839
;

-- 2026-02-13T14:54:55.711Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773839, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54210
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Frachtkostenberechnung
-- Column: DD_Order.FreightCostRule
-- 2026-02-13T14:54:55.996Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53894,130,773840,0,549052,149,TO_TIMESTAMP('2026-02-13 14:54:55.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode zur Berechnung von Frachtkosten',1,'@DeliveryViaRule@=''S''','EE01','"Frachtkostenberechnung" gibt an, auf welche Weise die Kosten für Fracht berechnet werden.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Frachtkostenberechnung',220,240,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:55.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:56.021Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:56.045Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1007)
;

-- 2026-02-13T14:54:56.083Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773840
;

-- 2026-02-13T14:54:56.106Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773840)
;

-- 2026-02-13T14:54:56.163Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773840
;

-- 2026-02-13T14:54:56.185Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773840, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54211
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Frachtbetrag
-- Column: DD_Order.FreightAmt
-- 2026-02-13T14:54:56.495Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53893,130,773841,0,549052,84,TO_TIMESTAMP('2026-02-13 14:54:56.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Frachtbetrag',22,'@DeliveryViaRule@=''S'' & @FreightCostRule@=''F''','EE01','"Frachtbetrag" gibt den Betrag für Fracht in diesem Beleg an.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Frachtbetrag',230,250,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:56.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:56.523Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:56.548Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(306)
;

-- 2026-02-13T14:54:56.580Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773841
;

-- 2026-02-13T14:54:56.611Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773841)
;

-- 2026-02-13T14:54:56.658Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773841
;

-- 2026-02-13T14:54:56.678Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773841, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54212
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Priorität
-- Column: DD_Order.PriorityRule
-- 2026-02-13T14:54:56.976Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53911,130,773842,0,549052,59,TO_TIMESTAMP('2026-02-13 14:54:56.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Priority of a document',1,'EE01','The Priority indicates the importance (high, medium, low) of this document',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Priorität',240,260,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:56.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:57.001Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:57.026Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(522)
;

-- 2026-02-13T14:54:57.050Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773842
;

-- 2026-02-13T14:54:57.073Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773842)
;

-- 2026-02-13T14:54:57.118Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773842
;

-- 2026-02-13T14:54:57.139Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773842, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54213
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Gewicht netto
-- Column: DD_Order.Weight
-- 2026-02-13T14:54:57.383Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53925,130,773843,0,549052,60,TO_TIMESTAMP('2026-02-13 14:54:57.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gewicht eines Produktes',22,'EE01','The Weight indicates the weight  of the product in the Weight UOM of the Client',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Gewicht netto',260,280,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:57.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:57.403Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:57.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(629)
;

-- 2026-02-13T14:54:57.452Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773843
;

-- 2026-02-13T14:54:57.474Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773843)
;

-- 2026-02-13T14:54:57.522Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773843
;

-- 2026-02-13T14:54:57.546Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773843, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54215
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Volumen
-- Column: DD_Order.Volume
-- 2026-02-13T14:54:57.864Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53923,130,773844,0,549052,65,TO_TIMESTAMP('2026-02-13 14:54:57.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Volumen eines Produktes',22,'EE01','Gibt das Volumen eines Produktes in der Volumen-Mengeneinheit des Mandanten an.',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Volumen',270,290,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:57.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:57.897Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:57.927Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(627)
;

-- 2026-02-13T14:54:57.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773844
;

-- 2026-02-13T14:54:57.971Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773844)
;

-- 2026-02-13T14:54:58.019Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773844
;

-- 2026-02-13T14:54:58.039Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773844, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54216
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Projekt
-- Column: DD_Order.C_Project_ID
-- 2026-02-13T14:54:58.304Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53876,104,773845,0,549052,54,TO_TIMESTAMP('2026-02-13 14:54:58.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project',22,'@$Element_PJ@=''Y''','EE01','A Project allows you to track and control internal or external activities.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Projekt',290,310,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:58.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:58.325Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773845 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:58.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2026-02-13T14:54:58.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773845
;

-- 2026-02-13T14:54:58.411Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773845)
;

-- 2026-02-13T14:54:58.459Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773845
;

-- 2026-02-13T14:54:58.489Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773845, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54217
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Distributionsauftrag
-- Column: DD_Order.DD_Order_ID
-- 2026-02-13T14:54:58.792Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,Included_Tab_ID,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53883,773846,0,549052,0,TO_TIMESTAMP('2026-02-13 14:54:58.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'EE01',0,53050,'Y','N','Y','N','N','N','N','N','N','N','N','Distributionsauftrag',350,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:58.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:58.819Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:58.842Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53311)
;

-- 2026-02-13T14:54:58.866Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773846
;

-- 2026-02-13T14:54:58.890Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773846)
;

-- 2026-02-13T14:54:58.933Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773846
;

-- 2026-02-13T14:54:58.955Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773846, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54218
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Kostenstelle
-- Column: DD_Order.C_Activity_ID
-- 2026-02-13T14:54:59.235Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53868,104,773847,0,549052,80,TO_TIMESTAMP('2026-02-13 14:54:58.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kostenstelle',22,'@$Element_AY@=''Y''','EE01','Erfassung der zugehörigen Kostenstelle',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Kostenstelle',300,320,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:58.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:59.258Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:59.294Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005)
;

-- 2026-02-13T14:54:59.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773847
;

-- 2026-02-13T14:54:59.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773847)
;

-- 2026-02-13T14:54:59.392Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773847
;

-- 2026-02-13T14:54:59.413Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773847, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54219
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Werbemassnahme
-- Column: DD_Order.C_Campaign_ID
-- 2026-02-13T14:54:59.674Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53871,104,773848,0,549052,115,TO_TIMESTAMP('2026-02-13 14:54:59.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Marketing Campaign',22,'@$Element_MC@=Y','EE01','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Werbemassnahme',310,330,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:59.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:54:59.699Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:54:59.722Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550)
;

-- 2026-02-13T14:54:59.751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773848
;

-- 2026-02-13T14:54:59.771Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773848)
;

-- 2026-02-13T14:54:59.818Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773848
;

-- 2026-02-13T14:54:59.842Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773848, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54220
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Buchende Organisation
-- Column: DD_Order.AD_OrgTrx_ID
-- 2026-02-13T14:55:00.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53865,104,773849,0,549052,141,TO_TIMESTAMP('2026-02-13 14:54:59.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durchführende oder auslösende Organisation',22,'EE01','Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Buchende Organisation',320,340,0,1,1,TO_TIMESTAMP('2026-02-13 14:54:59.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:00.152Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773849 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:00.187Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(112)
;

-- 2026-02-13T14:55:00.215Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773849
;

-- 2026-02-13T14:55:00.239Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773849)
;

-- 2026-02-13T14:55:00.284Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773849
;

-- 2026-02-13T14:55:00.307Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773849, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54221
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Nutzer 1
-- Column: DD_Order.User1_ID
-- 2026-02-13T14:55:00.599Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53921,104,773850,0,549052,61,TO_TIMESTAMP('2026-02-13 14:55:00.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 1',22,'@$Element_U1@=Y','EE01','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Nutzer 1',330,350,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:00.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:00.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:00.671Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(613)
;

-- 2026-02-13T14:55:00.697Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773850
;

-- 2026-02-13T14:55:00.734Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773850)
;

-- 2026-02-13T14:55:00.798Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773850
;

-- 2026-02-13T14:55:00.820Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773850, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54222
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Nutzer 2
-- Column: DD_Order.User2_ID
-- 2026-02-13T14:55:01.052Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53922,104,773851,1001556,0,549052,61,TO_TIMESTAMP('2026-02-13 14:55:00.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 2',22,'@$Element_U2@=Y','EE01','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Nutzer 2',340,360,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:00.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:01.074Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773851 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:01.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001556)
;

-- 2026-02-13T14:55:01.125Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773851
;

-- 2026-02-13T14:55:01.154Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773851)
;

-- 2026-02-13T14:55:01.211Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773851
;

-- 2026-02-13T14:55:01.233Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773851, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54223
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Status
-- Column: DD_Order.DocStatus
-- 2026-02-13T14:55:01.502Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53892,101,773852,1003082,0,549052,121,TO_TIMESTAMP('2026-02-13 14:55:01.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document',2,'EE01','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Status',360,40,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:01.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:01.531Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773852 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:01.555Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003082)
;

-- 2026-02-13T14:55:01.578Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773852
;

-- 2026-02-13T14:55:01.611Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773852)
;

-- 2026-02-13T14:55:01.654Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773852
;

-- 2026-02-13T14:55:01.678Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773852, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54224
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Belegaktion
-- Column: DD_Order.DocAction
-- 2026-02-13T14:55:01.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53891,101,773853,1000571,0,549052,105,TO_TIMESTAMP('2026-02-13 14:55:01.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document Action',2,'EE01','You find the current status in the Document Status field. The options are listed in a popup',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Belegaktion',370,50,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:01.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:01.936Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773853 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:01.959Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000571)
;

-- 2026-02-13T14:55:01.984Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773853
;

-- 2026-02-13T14:55:02.007Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773853)
;

-- 2026-02-13T14:55:02.052Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773853
;

-- 2026-02-13T14:55:02.077Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773853, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54225
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> In Transit
-- Column: DD_Order.IsInTransit
-- 2026-02-13T14:55:02.361Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53901,773854,0,549052,66,TO_TIMESTAMP('2026-02-13 14:55:02.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Movement is in transit',1,'EE01','Material Movement is in transit - shipped, but not received.
The transaction is completed, if confirmed.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','In Transit',380,370,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:02.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:02.383Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773854 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:02.406Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2397)
;

-- 2026-02-13T14:55:02.430Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773854
;

-- 2026-02-13T14:55:02.451Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773854)
;

-- 2026-02-13T14:55:02.507Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773854
;

-- 2026-02-13T14:55:02.530Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773854, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54226
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Freigegeben
-- Column: DD_Order.IsApproved
-- 2026-02-13T14:55:02.822Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53897,773855,0,549052,82,TO_TIMESTAMP('2026-02-13 14:55:02.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeigt an, ob dieser Beleg eine Freigabe braucht',1,'EE01','Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Freigegeben',390,380,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:02.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:02.846Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773855 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:02.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351)
;

-- 2026-02-13T14:55:02.896Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773855
;

-- 2026-02-13T14:55:02.929Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773855)
;

-- 2026-02-13T14:55:02.974Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773855
;

-- 2026-02-13T14:55:03.001Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773855, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54227
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> andrucken
-- Column: DD_Order.IsPrinted
-- 2026-02-13T14:55:03.280Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53902,773856,0,549052,65,TO_TIMESTAMP('2026-02-13 14:55:03.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Indicates if this document / line is printed',1,'EE01','The Printed checkbox indicates if this document or line will included when printing.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','andrucken',400,390,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:03.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:03.305Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:03.329Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(399)
;

-- 2026-02-13T14:55:03.356Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773856
;

-- 2026-02-13T14:55:03.378Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773856)
;

-- 2026-02-13T14:55:03.424Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773856
;

-- 2026-02-13T14:55:03.449Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773856, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54228
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Zugestellt
-- Column: DD_Order.IsDelivered
-- 2026-02-13T14:55:03.743Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53898,773857,1002712,0,549052,69,TO_TIMESTAMP('2026-02-13 14:55:03.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Zugestellt',410,400,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:03.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:03.764Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:03.789Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002712)
;

-- 2026-02-13T14:55:03.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773857
;

-- 2026-02-13T14:55:03.833Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773857)
;

-- 2026-02-13T14:55:03.877Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773857
;

-- 2026-02-13T14:55:03.898Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773857, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54229
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Produktionsstätte
-- Column: DD_Order.PP_Plant_ID
-- 2026-02-13T14:55:04.188Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550579,104,773858,0,549052,110,TO_TIMESTAMP('2026-02-13 14:55:03.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produktionsstätte',280,300,1,1,TO_TIMESTAMP('2026-02-13 14:55:03.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:04.212Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:04.238Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542433)
;

-- 2026-02-13T14:55:04.261Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773858
;

-- 2026-02-13T14:55:04.282Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773858)
;

-- 2026-02-13T14:55:04.328Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773858
;

-- 2026-02-13T14:55:04.349Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773858, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554184
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> MRP Generated Document
-- Column: DD_Order.MRP_Generated
-- 2026-02-13T14:55:04.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551481,540077,773859,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:04.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'This document was generated by MRP',1,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','MRP Generated Document',420,410,1,1,TO_TIMESTAMP('2026-02-13 14:55:04.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:04.659Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:04.681Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542622)
;

-- 2026-02-13T14:55:04.714Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773859
;

-- 2026-02-13T14:55:04.738Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773859)
;

-- 2026-02-13T14:55:04.786Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773859
;

-- 2026-02-13T14:55:04.808Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773859, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555150
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> MRP Allow Cleanup
-- Column: DD_Order.MRP_AllowCleanup
-- 2026-02-13T14:55:05.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551482,540077,773860,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:04.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRP is allowed to remove this document',1,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','MRP Allow Cleanup',430,420,1,1,TO_TIMESTAMP('2026-02-13 14:55:04.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:05.168Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:05.192Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542623)
;

-- 2026-02-13T14:55:05.217Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773860
;

-- 2026-02-13T14:55:05.255Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773860)
;

-- 2026-02-13T14:55:05.303Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773860
;

-- 2026-02-13T14:55:05.325Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773860, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555151
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> To be deleted (MRP)
-- Column: DD_Order.MRP_ToDelete
-- 2026-02-13T14:55:05.667Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551788,540077,773861,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:05.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Indicates if this document is scheduled to be deleted by MRP',1,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','To be deleted (MRP)',440,430,1,1,TO_TIMESTAMP('2026-02-13 14:55:05.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:05.690Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:05.716Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542701)
;

-- 2026-02-13T14:55:05.746Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773861
;

-- 2026-02-13T14:55:05.770Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773861)
;

-- 2026-02-13T14:55:05.814Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773861
;

-- 2026-02-13T14:55:05.838Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773861, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555432
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Lager ab
-- Column: DD_Order.M_Warehouse_From_ID
-- 2026-02-13T14:55:06.132Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsMandatory,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570819,773862,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:05.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'EE01',0,'Y','N','Y','Y','N','N','N','N','Y','N','N','N','Lager ab',450,440,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:05.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:06.156Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:06.178Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577736)
;

-- 2026-02-13T14:55:06.201Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773862
;

-- 2026-02-13T14:55:06.244Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773862)
;

-- 2026-02-13T14:55:06.290Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773862
;

-- 2026-02-13T14:55:06.327Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773862, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 613898
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Lager an
-- Column: DD_Order.M_Warehouse_To_ID
-- 2026-02-13T14:55:06.603Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsMandatory,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570820,773863,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:06.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'EE01',0,'Y','N','Y','Y','N','N','N','N','Y','N','N','N','Lager an',460,450,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:06.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:06.625Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:06.647Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577737)
;

-- 2026-02-13T14:55:06.671Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773863
;

-- 2026-02-13T14:55:06.696Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773863)
;

-- 2026-02-13T14:55:06.751Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773863
;

-- 2026-02-13T14:55:06.772Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773863, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 613899
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Produktionsauftrag
-- Column: DD_Order.Forward_PP_Order_ID
-- 2026-02-13T14:55:07.086Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588876,773864,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:06.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'@Forward_PP_Order_ID/0@>0','EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Produktionsauftrag',470,1,1,TO_TIMESTAMP('2026-02-13 14:55:06.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:07.109Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:07.143Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583182)
;

-- 2026-02-13T14:55:07.182Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773864
;

-- 2026-02-13T14:55:07.217Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773864)
;

-- 2026-02-13T14:55:07.260Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773864
;

-- 2026-02-13T14:55:07.283Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773864, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729786
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2026-02-13T14:55:07.611Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588877,773865,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:07.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'@Forward_PP_Order_BOMLine_ID/0@>0','EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Forward Manufacturing Order BOM Line',480,1,1,TO_TIMESTAMP('2026-02-13 14:55:07.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:07.635Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:07.660Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583183)
;

-- 2026-02-13T14:55:07.682Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773865
;

-- 2026-02-13T14:55:07.703Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773865)
;

-- 2026-02-13T14:55:07.772Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773865
;

-- 2026-02-13T14:55:07.797Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773865, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729787
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Produktplanung
-- Column: DD_Order.PP_Product_Planning_ID
-- 2026-02-13T14:55:08.187Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556806,773866,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:07.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Produktplanung',490,1,1,TO_TIMESTAMP('2026-02-13 14:55:07.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:08.211Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:08.237Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53268)
;

-- 2026-02-13T14:55:08.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773866
;

-- 2026-02-13T14:55:08.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773866)
;

-- 2026-02-13T14:55:08.341Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773866
;

-- 2026-02-13T14:55:08.367Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773866, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 756208
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Verbuchungsfehler
-- Column: DD_Order.PostingError_Issue_ID
-- 2026-02-13T14:55:08.743Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570866,773867,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:08.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Verbuchungsfehler',500,1,1,TO_TIMESTAMP('2026-02-13 14:55:08.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:08.764Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:08.790Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755)
;

-- 2026-02-13T14:55:08.815Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773867
;

-- 2026-02-13T14:55:08.838Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773867)
;

-- 2026-02-13T14:55:08.882Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773867
;

-- 2026-02-13T14:55:08.904Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773867, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 756209
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Verantwortlicher Benutzer
-- Column: DD_Order.AD_User_Responsible_ID
-- 2026-02-13T14:55:09.202Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,577794,773868,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:08.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Verantwortlicher Benutzer',510,1,1,TO_TIMESTAMP('2026-02-13 14:55:08.927000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:09.233Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:09.255Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542007)
;

-- 2026-02-13T14:55:09.287Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773868
;

-- 2026-02-13T14:55:09.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773868)
;

-- 2026-02-13T14:55:09.369Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773868
;

-- 2026-02-13T14:55:09.391Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773868, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 756210
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Simulierter Bedarf
-- Column: DD_Order.IsSimulated
-- 2026-02-13T14:55:09.715Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579356,773869,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:09.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Simulierter Bedarf',520,1,1,TO_TIMESTAMP('2026-02-13 14:55:09.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:09.738Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:09.762Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580611)
;

-- 2026-02-13T14:55:09.789Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773869
;

-- 2026-02-13T14:55:09.827Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773869)
;

-- 2026-02-13T14:55:09.886Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773869
;

-- 2026-02-13T14:55:09.922Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773869, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 756211
;

-- Field: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> Reihenfolge
-- Column: DD_Order.SeqNo
-- 2026-02-13T14:55:10.252Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591592,773870,0,549052,0,TO_TIMESTAMP('2026-02-13 14:55:09.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'EE01','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','N','N','N','N','N','N','N','N','N','N','Reihenfolge',530,1,1,TO_TIMESTAMP('2026-02-13 14:55:09.947000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:10.276Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:10.299Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2026-02-13T14:55:10.328Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773870
;

-- 2026-02-13T14:55:10.364Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773870)
;

-- 2026-02-13T14:55:10.409Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773870
;

-- 2026-02-13T14:55:10.432Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773870, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 756212
;

-- Tab: Distributionsauftrag(542091,EE01) -> Distribution Order Line
-- Table: DD_OrderLine
-- 2026-02-13T14:55:10.706Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,53930,573608,0,549053,53038,542091,'Y',TO_TIMESTAMP('2026-02-13 14:55:10.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Distribution Order Line','EE01','N','A','DD_OrderLine','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N','Distribution Order Line','N',20,1,TO_TIMESTAMP('2026-02-13 14:55:10.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:10.731Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549053 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-13T14:55:10.754Z
/* DDL */  select update_tab_translation_from_ad_element(573608)
;

-- 2026-02-13T14:55:10.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549053)
;

-- 2026-02-13T14:55:10.855Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 549053
;

-- 2026-02-13T14:55:10.893Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 549053, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 53050
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Qty In Transit
-- Column: DD_OrderLine.QtyInTransit
-- 2026-02-13T14:55:11.361Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53927,773871,0,549053,126,TO_TIMESTAMP('2026-02-13 14:55:11.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Qty In Transit',90,90,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:11.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:11.385Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:11.409Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53312)
;

-- 2026-02-13T14:55:11.443Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773871
;

-- 2026-02-13T14:55:11.478Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773871)
;

-- 2026-02-13T14:55:11.530Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773871
;

-- 2026-02-13T14:55:11.559Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773871, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54003
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Buchende Organisation
-- Column: DD_OrderLine.AD_OrgTrx_ID
-- 2026-02-13T14:55:11.854Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53963,773872,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:11.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durchführende oder auslösende Organisation',10,'EE01','Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.',0,'Y','N','N','N','N','N','N','N','N','N','N','Buchende Organisation',100,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:11.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:11.897Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773872 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:11.922Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(112)
;

-- 2026-02-13T14:55:11.946Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773872
;

-- 2026-02-13T14:55:11.968Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773872)
;

-- 2026-02-13T14:55:12.011Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773872
;

-- 2026-02-13T14:55:12.033Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773872, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54004
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Organisation
-- Column: DD_OrderLine.AD_Org_ID
-- 2026-02-13T14:55:12.350Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53964,773873,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:12.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',22,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Organisation',110,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:12.057000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:12.383Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773873 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:12.412Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-13T14:55:12.490Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773873
;

-- 2026-02-13T14:55:12.511Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773873)
;

-- 2026-02-13T14:55:12.571Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773873
;

-- 2026-02-13T14:55:12.595Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773873, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54005
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Kostenstelle
-- Column: DD_OrderLine.C_Activity_ID
-- 2026-02-13T14:55:12.906Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53965,773874,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:12.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kostenstelle',10,'EE01','Erfassung der zugehörigen Kostenstelle',0,'Y','N','N','N','N','N','N','N','N','N','N','Kostenstelle',120,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:12.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:12.944Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773874 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:12.969Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005)
;

-- 2026-02-13T14:55:12.996Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773874
;

-- 2026-02-13T14:55:13.017Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773874)
;

-- 2026-02-13T14:55:13.072Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773874
;

-- 2026-02-13T14:55:13.094Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773874, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54006
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Werbemassnahme
-- Column: DD_OrderLine.C_Campaign_ID
-- 2026-02-13T14:55:13.401Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53966,773875,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:13.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Marketing Campaign',10,'EE01','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.',0,'Y','N','N','N','N','N','N','N','N','N','N','Werbemassnahme',130,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:13.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:13.434Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773875 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:13.457Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(550)
;

-- 2026-02-13T14:55:13.485Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773875
;

-- 2026-02-13T14:55:13.523Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773875)
;

-- 2026-02-13T14:55:13.577Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773875
;

-- 2026-02-13T14:55:13.612Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773875, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54007
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Kosten
-- Column: DD_OrderLine.C_Charge_ID
-- 2026-02-13T14:55:13.921Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53931,773876,1000221,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:13.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zusätzliche Kosten',10,'EE01','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)',0,'Y','N','N','N','N','N','N','N','N','N','N','Kosten',140,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:13.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:13.942Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773876 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:13.967Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000221)
;

-- 2026-02-13T14:55:13.991Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773876
;

-- 2026-02-13T14:55:14.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773876)
;

-- 2026-02-13T14:55:14.073Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773876
;

-- 2026-02-13T14:55:14.114Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773876, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54008
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Projekt
-- Column: DD_OrderLine.C_Project_ID
-- 2026-02-13T14:55:14.453Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53932,773877,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:14.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project',10,'EE01','A Project allows you to track and control internal or external activities.',0,'Y','N','N','N','N','N','N','N','N','N','N','Projekt',150,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:14.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:14.494Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773877 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:14.517Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2026-02-13T14:55:14.544Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773877
;

-- 2026-02-13T14:55:14.570Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773877)
;

-- 2026-02-13T14:55:14.616Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773877
;

-- 2026-02-13T14:55:14.639Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773877, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54009
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Maßeinheit
-- Column: DD_OrderLine.C_UOM_ID
-- 2026-02-13T14:55:14.935Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53933,773878,0,549053,77,TO_TIMESTAMP('2026-02-13 14:55:14.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',22,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Maßeinheit',70,70,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:14.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:14.958Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773878 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:15.002Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-02-13T14:55:15.033Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773878
;

-- 2026-02-13T14:55:15.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773878)
;

-- 2026-02-13T14:55:15.108Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773878
;

-- 2026-02-13T14:55:15.130Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773878, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54010
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Bestätigte Menge
-- Column: DD_OrderLine.ConfirmedQty
-- 2026-02-13T14:55:15.445Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53934,773879,1000528,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:15.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestätigung einer erhaltenen Menge',22,'EE01','Bestätigung einer erhaltenen Menge',0,'Y','N','N','N','N','N','N','N','N','N','N','Bestätigte Menge',160,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:15.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:15.470Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:15.493Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000528)
;

-- 2026-02-13T14:55:15.526Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773879
;

-- 2026-02-13T14:55:15.548Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773879)
;

-- 2026-02-13T14:55:15.591Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773879
;

-- 2026-02-13T14:55:15.612Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773879, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54011
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Beschreibung
-- Column: DD_OrderLine.Description
-- 2026-02-13T14:55:15.893Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53941,773880,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:15.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Beschreibung',170,0,0,999,1,TO_TIMESTAMP('2026-02-13 14:55:15.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:15.916Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773880 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:15.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2026-02-13T14:55:15.993Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773880
;

-- 2026-02-13T14:55:16.016Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773880)
;

-- 2026-02-13T14:55:16.071Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773880
;

-- 2026-02-13T14:55:16.103Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773880, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54012
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Aktiv
-- Column: DD_OrderLine.IsActive
-- 2026-02-13T14:55:16.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53943,773881,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:16.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',180,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:16.134000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:16.437Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:16.461Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-13T14:55:16.549Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773881
;

-- 2026-02-13T14:55:16.590Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773881)
;

-- 2026-02-13T14:55:16.646Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773881
;

-- 2026-02-13T14:55:16.686Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773881, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54013
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Nur Beschreibung
-- Column: DD_OrderLine.IsDescription
-- 2026-02-13T14:55:16.996Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53944,773882,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:16.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nur Beschreibung',1,'EE01','If a line is Description Only, e.g. Product Inventory is not corrected. No accounting transactions are created and the amount or totals are not included in the document.  This for including descriptional detail lines, e.g. for an Work Order.',0,'Y','N','N','N','N','N','N','N','N','N','N','Nur Beschreibung',190,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:16.711000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:17.020Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773882 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:17.055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2183)
;

-- 2026-02-13T14:55:17.082Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773882
;

-- 2026-02-13T14:55:17.104Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773882)
;

-- 2026-02-13T14:55:17.164Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773882
;

-- 2026-02-13T14:55:17.189Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773882, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54014
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Fakturiert
-- Column: DD_OrderLine.IsInvoiced
-- 2026-02-13T14:55:17.486Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53945,773883,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:17.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Fakturiert?',1,'EE01','If selected, invoices are created',0,'Y','N','N','N','N','N','N','N','N','N','N','Fakturiert',200,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:17.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:17.508Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773883 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:17.530Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(387)
;

-- 2026-02-13T14:55:17.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773883
;

-- 2026-02-13T14:55:17.577Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773883)
;

-- 2026-02-13T14:55:17.632Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773883
;

-- 2026-02-13T14:55:17.657Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773883, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54015
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Gelieferte Menge
-- Column: DD_OrderLine.QtyDelivered
-- 2026-02-13T14:55:17.994Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53953,773884,1002729,0,549053,107,TO_TIMESTAMP('2026-02-13 14:55:17.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gelieferte Menge',10,'EE01','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Gelieferte Menge',100,100,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:17.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:18.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773884 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:18.044Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002729)
;

-- 2026-02-13T14:55:18.071Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773884
;

-- 2026-02-13T14:55:18.102Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773884)
;

-- 2026-02-13T14:55:18.160Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773884
;

-- 2026-02-13T14:55:18.194Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773884, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54016
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Reservierte Menge
-- Column: DD_OrderLine.QtyReserved
-- 2026-02-13T14:55:18.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53955,773885,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:18.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Reservierte Menge',10,'EE01','Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.',0,'Y','N','N','N','N','N','N','N','N','N','N','Reservierte Menge',210,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:18.217000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:18.578Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773885 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:18.600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(532)
;

-- 2026-02-13T14:55:18.623Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773885
;

-- 2026-02-13T14:55:18.656Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773885)
;

-- 2026-02-13T14:55:18.703Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773885
;

-- 2026-02-13T14:55:18.725Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773885, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54017
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Lieferdatum
-- Column: DD_OrderLine.DateDelivered
-- 2026-02-13T14:55:19.036Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53938,773886,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:18.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, zu dem die Ware geliefert wurde',8,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Lieferdatum',220,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:18.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:19.074Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773886 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:19.107Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(264)
;

-- 2026-02-13T14:55:19.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773886
;

-- 2026-02-13T14:55:19.173Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773886)
;

-- 2026-02-13T14:55:19.217Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773886
;

-- 2026-02-13T14:55:19.247Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773886, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54018
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Nutzer 1
-- Column: DD_OrderLine.User1_ID
-- 2026-02-13T14:55:19.560Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53960,773887,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:19.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 1',10,'EE01','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.',0,'Y','N','N','N','N','N','N','N','N','N','N','Nutzer 1',230,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:19.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:19.585Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773887 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:19.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(613)
;

-- 2026-02-13T14:55:19.652Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773887
;

-- 2026-02-13T14:55:19.675Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773887)
;

-- 2026-02-13T14:55:19.720Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773887
;

-- 2026-02-13T14:55:19.751Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773887, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54019
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Nutzer 2
-- Column: DD_OrderLine.User2_ID
-- 2026-02-13T14:55:20.076Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53962,773888,1003001,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:19.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 2',10,'EE01','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.',0,'Y','N','N','N','N','N','N','N','N','N','N','Nutzer 2',240,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:19.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:20.099Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773888 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:20.124Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003001)
;

-- 2026-02-13T14:55:20.148Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773888
;

-- 2026-02-13T14:55:20.176Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773888)
;

-- 2026-02-13T14:55:20.249Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773888
;

-- 2026-02-13T14:55:20.283Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773888, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54020
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Mandant
-- Column: DD_OrderLine.AD_Client_ID
-- 2026-02-13T14:55:20.553Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53961,773889,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:20.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',250,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:20.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:20.577Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:20.600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-13T14:55:20.678Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773889
;

-- 2026-02-13T14:55:20.700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773889)
;

-- 2026-02-13T14:55:20.746Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773889
;

-- 2026-02-13T14:55:20.768Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773889, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54021
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Frachtbetrag
-- Column: DD_OrderLine.FreightAmt
-- 2026-02-13T14:55:21.056Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53942,773890,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:20.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Frachtbetrag',10,'EE01','"Frachtbetrag" gibt den Betrag für Fracht in diesem Beleg an.',0,'Y','N','N','N','N','N','N','N','N','N','N','Frachtbetrag',260,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:20.790000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:21.077Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:21.100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(306)
;

-- 2026-02-13T14:55:21.125Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773890
;

-- 2026-02-13T14:55:21.159Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773890)
;

-- 2026-02-13T14:55:21.200Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773890
;

-- 2026-02-13T14:55:21.223Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773890, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54022
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Zeilennetto
-- Column: DD_OrderLine.LineNetAmt
-- 2026-02-13T14:55:21.476Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53946,773891,1000224,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:21.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren',10,'EE01','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.',0,'Y','N','N','N','N','N','N','N','N','N','N','Zeilennetto',270,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:21.245000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:21.499Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:21.525Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000224)
;

-- 2026-02-13T14:55:21.549Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773891
;

-- 2026-02-13T14:55:21.573Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773891)
;

-- 2026-02-13T14:55:21.616Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773891
;

-- 2026-02-13T14:55:21.639Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773891, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54023
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Auftragsdatum
-- Column: DD_OrderLine.DateOrdered
-- 2026-02-13T14:55:21.924Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53939,773892,1002051,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:21.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags',10,'EE01','Bezeichnet das Datum, an dem die Ware bestellt wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Auftragsdatum',280,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:21.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:21.947Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:21.970Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002051)
;

-- 2026-02-13T14:55:21.992Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773892
;

-- 2026-02-13T14:55:22.014Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773892)
;

-- 2026-02-13T14:55:22.070Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773892
;

-- 2026-02-13T14:55:22.092Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773892, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54024
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Distributionsauftrag
-- Column: DD_OrderLine.DD_Order_ID
-- 2026-02-13T14:55:22.386Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53930,773893,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:22.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Distributionsauftrag',290,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:22.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:22.408Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:22.433Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53311)
;

-- 2026-02-13T14:55:22.457Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773893
;

-- 2026-02-13T14:55:22.479Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773893)
;

-- 2026-02-13T14:55:22.522Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773893
;

-- 2026-02-13T14:55:22.546Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773893, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54025
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Distributionsauftragzeile
-- Column: DD_OrderLine.DD_OrderLine_ID
-- 2026-02-13T14:55:22.853Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53937,773894,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:22.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Distributionsauftragzeile',300,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:22.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:22.882Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:22.909Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53313)
;

-- 2026-02-13T14:55:22.932Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773894
;

-- 2026-02-13T14:55:22.953Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773894)
;

-- 2026-02-13T14:55:22.995Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773894
;

-- 2026-02-13T14:55:23.016Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773894, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54026
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Picked Quantity
-- Column: DD_OrderLine.PickedQty
-- 2026-02-13T14:55:23.292Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53951,773895,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:23.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','Y','Picked Quantity',310,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:23.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:23.315Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:23.339Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2422)
;

-- 2026-02-13T14:55:23.362Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773895
;

-- 2026-02-13T14:55:23.386Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773895)
;

-- 2026-02-13T14:55:23.431Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773895
;

-- 2026-02-13T14:55:23.452Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773895, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54027
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Verarbeitet
-- Column: DD_OrderLine.Processed
-- 2026-02-13T14:55:23.729Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53952,773896,1000081,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:23.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'EE01','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Verarbeitet',320,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:23.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:23.753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:23.777Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000081)
;

-- 2026-02-13T14:55:23.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773896
;

-- 2026-02-13T14:55:23.833Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773896)
;

-- 2026-02-13T14:55:23.878Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773896
;

-- 2026-02-13T14:55:23.908Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773896, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54028
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Menge
-- Column: DD_OrderLine.QtyEntered
-- 2026-02-13T14:55:24.150Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53954,773897,1002141,0,549053,54,TO_TIMESTAMP('2026-02-13 14:55:23.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit',22,'EE01','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Menge',60,60,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:23.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:24.174Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:24.196Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002141)
;

-- 2026-02-13T14:55:24.222Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773897
;

-- 2026-02-13T14:55:24.242Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773897)
;

-- 2026-02-13T14:55:24.287Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773897
;

-- 2026-02-13T14:55:24.308Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773897, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54029
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Verworfene Menge
-- Column: DD_OrderLine.ScrappedQty
-- 2026-02-13T14:55:24.592Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53956,773898,1002259,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:24.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durch QA verworfene Menge',22,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Verworfene Menge',330,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:24.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:24.626Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:24.647Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002259)
;

-- 2026-02-13T14:55:24.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773898
;

-- 2026-02-13T14:55:24.708Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773898)
;

-- 2026-02-13T14:55:24.753Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773898
;

-- 2026-02-13T14:55:24.774Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773898, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54030
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Zielmenge
-- Column: DD_OrderLine.TargetQty
-- 2026-02-13T14:55:25.063Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53957,773899,1001319,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:24.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zielmenge der Warenbewegung',22,'EE01','Die Menge, die empfangen worden sein sollte',0,'Y','N','N','N','N','N','N','N','N','N','Y','Zielmenge',340,0,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:24.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:25.106Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:25.129Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001319)
;

-- 2026-02-13T14:55:25.153Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773899
;

-- 2026-02-13T14:55:25.176Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773899)
;

-- 2026-02-13T14:55:25.223Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773899
;

-- 2026-02-13T14:55:25.246Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773899, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54031
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Zeile Nr.
-- Column: DD_OrderLine.Line
-- 2026-02-13T14:55:25.529Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53926,773900,1001121,0,549053,128,TO_TIMESTAMP('2026-02-13 14:55:25.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einzelne Zeile in dem Dokument',22,'EE01','Indicates the unique line for a document.  It will also control the display order of the lines within a document.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Zeile Nr.',10,10,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:25.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:25.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:25.577Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001121)
;

-- 2026-02-13T14:55:25.603Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773900
;

-- 2026-02-13T14:55:25.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773900)
;

-- 2026-02-13T14:55:25.686Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773900
;

-- 2026-02-13T14:55:25.725Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773900, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54032
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Zugesagter Termin
-- Column: DD_OrderLine.DatePromised
-- 2026-02-13T14:55:26.001Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53940,773901,0,549053,103,TO_TIMESTAMP('2026-02-13 14:55:25.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',10,'EE01','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Zugesagter Termin',30,30,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:25.749000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:26.025Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:26.047Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269)
;

-- 2026-02-13T14:55:26.076Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773901
;

-- 2026-02-13T14:55:26.100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773901)
;

-- 2026-02-13T14:55:26.155Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773901
;

-- 2026-02-13T14:55:26.176Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773901, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54033
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Produkt
-- Column: DD_OrderLine.M_Product_ID
-- 2026-02-13T14:55:26.482Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53929,773902,0,549053,169,TO_TIMESTAMP('2026-02-13 14:55:26.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',22,'EE01','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produkt',20,20,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:26.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:26.505Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:26.536Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-02-13T14:55:26.572Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773902
;

-- 2026-02-13T14:55:26.595Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773902)
;

-- 2026-02-13T14:55:26.647Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773902
;

-- 2026-02-13T14:55:26.672Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773902, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54034
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Bestellt/ Beauftragt
-- Column: DD_OrderLine.QtyOrdered
-- 2026-02-13T14:55:26.920Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53928,773903,1001246,0,549053,160,TO_TIMESTAMP('2026-02-13 14:55:26.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt',22,'EE01','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Bestellt/ Beauftragt',80,80,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:26.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:26.943Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773903 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:26.974Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001246)
;

-- 2026-02-13T14:55:27.001Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773903
;

-- 2026-02-13T14:55:27.033Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773903)
;

-- 2026-02-13T14:55:27.088Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773903
;

-- 2026-02-13T14:55:27.110Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773903, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54035
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Lagerort
-- Column: DD_OrderLine.M_Locator_ID
-- 2026-02-13T14:55:27.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53950,773904,0,549053,157,TO_TIMESTAMP('2026-02-13 14:55:27.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort im Lager',22,'EE01','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lagerort',130,120,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:27.133000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:27.457Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:27.480Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448)
;

-- 2026-02-13T14:55:27.510Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773904
;

-- 2026-02-13T14:55:27.550Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773904)
;

-- 2026-02-13T14:55:27.595Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773904
;

-- 2026-02-13T14:55:27.618Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773904, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54036
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Lagerort An
-- Column: DD_OrderLine.M_LocatorTo_ID
-- 2026-02-13T14:55:27.893Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53949,773905,1002432,0,549053,126,TO_TIMESTAMP('2026-02-13 14:55:27.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort, an den Bestand bewegt wird',10,'EE01','"Lagerort An" bezeichnet den Lagerort, auf den ein Warenbestand bewegt wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Lagerort An',140,130,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:27.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:27.914Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:27.936Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002432)
;

-- 2026-02-13T14:55:27.959Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773905
;

-- 2026-02-13T14:55:27.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773905)
;

-- 2026-02-13T14:55:28.036Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773905
;

-- 2026-02-13T14:55:28.057Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773905, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54037
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Merkmale
-- Column: DD_OrderLine.M_AttributeSetInstance_ID
-- 2026-02-13T14:55:28.386Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53948,773906,0,549053,61,TO_TIMESTAMP('2026-02-13 14:55:28.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',22,'EE01','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Merkmale',150,140,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:28.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:28.415Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773906 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:28.444Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2026-02-13T14:55:28.475Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773906
;

-- 2026-02-13T14:55:28.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773906)
;

-- 2026-02-13T14:55:28.547Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773906
;

-- 2026-02-13T14:55:28.570Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773906, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54038
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Attribute Set Instance To
-- Column: DD_OrderLine.M_AttributeSetInstanceTo_ID
-- 2026-02-13T14:55:28.836Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,53947,773907,0,549053,78,TO_TIMESTAMP('2026-02-13 14:55:28.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Target Product Attribute Set Instance',10,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Attribute Set Instance To',160,150,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:28.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:28.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773907 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:28.882Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2799)
;

-- 2026-02-13T14:55:28.927Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773907
;

-- 2026-02-13T14:55:28.950Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773907)
;

-- 2026-02-13T14:55:29.005Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773907
;

-- 2026-02-13T14:55:29.029Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773907, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 54039
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Zugestellt
-- Column: DD_OrderLine.IsDelivered
-- 2026-02-13T14:55:29.320Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550563,773908,0,549053,69,TO_TIMESTAMP('2026-02-13 14:55:29.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugestellt',110,110,1,1,TO_TIMESTAMP('2026-02-13 14:55:29.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:29.344Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:29.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(367)
;

-- 2026-02-13T14:55:29.397Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773908
;

-- 2026-02-13T14:55:29.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773908)
;

-- 2026-02-13T14:55:29.478Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773908
;

-- 2026-02-13T14:55:29.509Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773908, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554088
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Auftragsposition
-- Column: DD_OrderLine.C_OrderLineSO_ID
-- 2026-02-13T14:55:29.837Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550583,773909,0,549053,105,TO_TIMESTAMP('2026-02-13 14:55:29.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition',10,'EE01','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Auftragsposition',180,170,1,1,TO_TIMESTAMP('2026-02-13 14:55:29.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:29.862Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773909 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:29.887Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542435)
;

-- 2026-02-13T14:55:29.914Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773909
;

-- 2026-02-13T14:55:29.937Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773909)
;

-- 2026-02-13T14:55:29.982Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773909
;

-- 2026-02-13T14:55:30.004Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773909, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554189
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Allow Push
-- Column: DD_OrderLine.DD_AllowPush
-- 2026-02-13T14:55:30.280Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550937,773910,0,549053,76,TO_TIMESTAMP('2026-02-13 14:55:30.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Allow pushing materials from suppliers through this path.',1,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Allow Push',200,190,1,1,TO_TIMESTAMP('2026-02-13 14:55:30.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:30.309Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:30.340Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542484)
;

-- 2026-02-13T14:55:30.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773910
;

-- 2026-02-13T14:55:30.392Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773910)
;

-- 2026-02-13T14:55:30.439Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773910
;

-- 2026-02-13T14:55:30.459Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773910, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554515
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Produktionsstätte ab
-- Column: DD_OrderLine.PP_Plant_From_ID
-- 2026-02-13T14:55:30.774Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550942,773911,0,549053,126,TO_TIMESTAMP('2026-02-13 14:55:30.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produktionsstätte ab',170,160,1,1,TO_TIMESTAMP('2026-02-13 14:55:30.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:30.797Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:30.821Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542485)
;

-- 2026-02-13T14:55:30.848Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773911
;

-- 2026-02-13T14:55:30.878Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773911)
;

-- 2026-02-13T14:55:30.927Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773911
;

-- 2026-02-13T14:55:30.956Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773911, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554520
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Menge TU
-- Column: DD_OrderLine.QtyEnteredTU
-- 2026-02-13T14:55:31.208Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550973,773912,1001843,0,549053,72,TO_TIMESTAMP('2026-02-13 14:55:30.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,15,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Menge TU',40,40,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:30.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:31.239Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773912 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:31.264Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001843)
;

-- 2026-02-13T14:55:31.302Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773912
;

-- 2026-02-13T14:55:31.330Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773912)
;

-- 2026-02-13T14:55:31.377Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773912
;

-- 2026-02-13T14:55:31.400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773912, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554549
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Packvorschrift
-- Column: DD_OrderLine.M_HU_PI_Item_Product_ID
-- 2026-02-13T14:55:31.650Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550974,773913,1000515,0,549053,202,TO_TIMESTAMP('2026-02-13 14:55:31.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Packvorschrift',50,50,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:31.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:31.673Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773913 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:31.695Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000515)
;

-- 2026-02-13T14:55:31.719Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773913
;

-- 2026-02-13T14:55:31.754Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773913)
;

-- 2026-02-13T14:55:31.797Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773913
;

-- 2026-02-13T14:55:31.819Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773913, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554550
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Zugestellt abw.
-- Column: DD_OrderLine.IsDelivered_Override
-- 2026-02-13T14:55:32.066Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551434,773914,1002116,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:31.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Zugestellt abw.',120,0,1,1,TO_TIMESTAMP('2026-02-13 14:55:31.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:32.099Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:32.122Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002116)
;

-- 2026-02-13T14:55:32.144Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773914
;

-- 2026-02-13T14:55:32.164Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773914)
;

-- 2026-02-13T14:55:32.208Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773914
;

-- 2026-02-13T14:55:32.230Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773914, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555105
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Geschäftspartner
-- Column: DD_OrderLine.C_BPartner_ID
-- 2026-02-13T14:55:32.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551476,773915,1000110,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:32.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'EE01','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Geschäftspartner',190,180,1,1,TO_TIMESTAMP('2026-02-13 14:55:32.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:32.488Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:32.511Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000110)
;

-- 2026-02-13T14:55:32.536Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773915
;

-- 2026-02-13T14:55:32.560Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773915)
;

-- 2026-02-13T14:55:32.608Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773915
;

-- 2026-02-13T14:55:32.642Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773915, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555144
;

-- Field: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> Keep target plant
-- Column: DD_OrderLine.IsKeepTargetPlant
-- 2026-02-13T14:55:32.917Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551499,773916,0,549053,0,TO_TIMESTAMP('2026-02-13 14:55:32.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'If set, the MRP demand of the distribution order which will be generated will have the same plant is target warehouse.',1,'EE01','This feature shall be used when we want to transfer materials inside the same plant, no matter which is the plant on which the source warehouse is assigned.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Keep target plant',210,200,1,1,TO_TIMESTAMP('2026-02-13 14:55:32.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:32.943Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-13T14:55:32.967Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542627)
;

-- 2026-02-13T14:55:32.990Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773916
;

-- 2026-02-13T14:55:33.012Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773916)
;

-- 2026-02-13T14:55:33.058Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773916
;

-- 2026-02-13T14:55:33.083Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773916, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555160
;

-- Tab: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01)
-- UI Section: main
-- 2026-02-13T14:55:33.362Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549052,547568,TO_TIMESTAMP('2026-02-13 14:55:33.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-13 14:55:33.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-13T14:55:33.383Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547568 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-13T14:55:33.446Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547568
;

-- 2026-02-13T14:55:33.469Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547568, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540188
;

-- UI Section: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main
-- UI Column: 10
-- 2026-02-13T14:55:33.720Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549236,547568,TO_TIMESTAMP('2026-02-13 14:55:33.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-13 14:55:33.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10
-- UI Element Group: default
-- 2026-02-13T14:55:33.941Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549236,554911,TO_TIMESTAMP('2026-02-13 14:55:33.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-13 14:55:33.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Geschäftspartner
-- Column: DD_Order.C_BPartner_ID
-- 2026-02-13T14:55:34.310Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773828,0,549052,554911,647872,'F',TO_TIMESTAMP('2026-02-13 14:55:34.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Geschäftspartner',10,40,10,TO_TIMESTAMP('2026-02-13 14:55:34.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2026-02-13T14:55:34.714Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,773829,0,542463,647872,TO_TIMESTAMP('2026-02-13 14:55:34.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2026-02-13 14:55:34.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-13T14:55:34.955Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,773831,0,542464,647872,TO_TIMESTAMP('2026-02-13 14:55:34.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,'widget',TO_TIMESTAMP('2026-02-13 14:55:34.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Produktionsstätte
-- Column: DD_Order.PP_Plant_ID
-- 2026-02-13T14:55:35.259Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773858,0,549052,554911,647873,'F',TO_TIMESTAMP('2026-02-13 14:55:35.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Produktionsstätte',20,50,0,TO_TIMESTAMP('2026-02-13 14:55:35.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Transit Lager
-- Column: DD_Order.M_Warehouse_ID
-- 2026-02-13T14:55:35.579Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773834,0,549052,554911,647874,'F',TO_TIMESTAMP('2026-02-13 14:55:35.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Transit Lager',30,60,0,TO_TIMESTAMP('2026-02-13 14:55:35.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Kostenstelle
-- Column: DD_Order.C_Activity_ID
-- 2026-02-13T14:55:35.912Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773847,0,549052,554911,647875,'F',TO_TIMESTAMP('2026-02-13 14:55:35.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Kostenstelle',40,120,0,TO_TIMESTAMP('2026-02-13 14:55:35.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Warehouse from
-- Column: DD_Order.M_Warehouse_From_ID
-- 2026-02-13T14:55:36.274Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773862,0,549052,554911,647876,'F',TO_TIMESTAMP('2026-02-13 14:55:35.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'Warehouse from',50,70,0,TO_TIMESTAMP('2026-02-13 14:55:35.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Warehouse to
-- Column: DD_Order.M_Warehouse_To_ID
-- 2026-02-13T14:55:36.617Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773863,0,549052,554911,647877,'F',TO_TIMESTAMP('2026-02-13 14:55:36.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'Warehouse to',60,80,0,TO_TIMESTAMP('2026-02-13 14:55:36.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10
-- UI Element Group: dimensions
-- 2026-02-13T14:55:36.823Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549236,554912,TO_TIMESTAMP('2026-02-13 14:55:36.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dimensions',20,'',TO_TIMESTAMP('2026-02-13 14:55:36.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dimensions.Lieferweg
-- Column: DD_Order.M_Shipper_ID
-- 2026-02-13T14:55:37.208Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773830,0,549052,554912,647878,'F',TO_TIMESTAMP('2026-02-13 14:55:36.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Lieferweg',10,100,0,TO_TIMESTAMP('2026-02-13 14:55:36.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dimensions.Streckengeschäft
-- Column: DD_Order.IsDropShip
-- 2026-02-13T14:55:37.537Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773835,0,549052,554912,647879,'F',TO_TIMESTAMP('2026-02-13 14:55:37.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Streckengeschäft',20,130,0,TO_TIMESTAMP('2026-02-13 14:55:37.297000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dimensions.Volumen
-- Column: DD_Order.Volume
-- 2026-02-13T14:55:37.852Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773844,0,549052,554912,647880,'F',TO_TIMESTAMP('2026-02-13 14:55:37.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Volumen',30,0,0,TO_TIMESTAMP('2026-02-13 14:55:37.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dimensions.Gewicht
-- Column: DD_Order.Weight
-- 2026-02-13T14:55:38.207Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773843,0,549052,554912,647881,'F',TO_TIMESTAMP('2026-02-13 14:55:37.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Gewicht',40,0,0,TO_TIMESTAMP('2026-02-13 14:55:37.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main
-- UI Column: 20
-- 2026-02-13T14:55:38.432Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549237,547568,TO_TIMESTAMP('2026-02-13 14:55:38.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-13 14:55:38.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20
-- UI Element Group: docno
-- 2026-02-13T14:55:38.644Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549237,554913,TO_TIMESTAMP('2026-02-13 14:55:38.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','docno',10,TO_TIMESTAMP('2026-02-13 14:55:38.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Belegart
-- Column: DD_Order.C_DocType_ID
-- 2026-02-13T14:55:39.028Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773824,0,549052,554913,647882,'F',TO_TIMESTAMP('2026-02-13 14:55:38.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Belegart',10,10,20,TO_TIMESTAMP('2026-02-13 14:55:38.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Nr.
-- Column: DD_Order.DocumentNo
-- 2026-02-13T14:55:39.383Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773821,0,549052,554913,647883,'F',TO_TIMESTAMP('2026-02-13 14:55:39.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Nr.',20,20,30,TO_TIMESTAMP('2026-02-13 14:55:39.129000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Datum
-- Column: DD_Order.DateOrdered
-- 2026-02-13T14:55:39.720Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773826,0,549052,554913,647884,'F',TO_TIMESTAMP('2026-02-13 14:55:39.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Datum',30,30,0,TO_TIMESTAMP('2026-02-13 14:55:39.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Zugesagter Termin
-- Column: DD_Order.DatePromised
-- 2026-02-13T14:55:40.052Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773827,0,549052,554913,647885,'F',TO_TIMESTAMP('2026-02-13 14:55:39.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Zugesagter Termin',40,90,0,TO_TIMESTAMP('2026-02-13 14:55:39.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Kommissionierdatum
-- Column: DD_Order.PickDate
-- 2026-02-13T14:55:40.380Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773838,0,549052,554913,647886,'F',TO_TIMESTAMP('2026-02-13 14:55:40.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum/Zeit der Kommissionierung für die Lieferung','Y','N','N','Y','N','N','N',0,'Kommissionierdatum',50,0,0,TO_TIMESTAMP('2026-02-13 14:55:40.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20
-- UI Element Group: referenz
-- 2026-02-13T14:55:40.631Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549237,554914,TO_TIMESTAMP('2026-02-13 14:55:40.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','referenz',20,TO_TIMESTAMP('2026-02-13 14:55:40.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.Freigegeben
-- Column: DD_Order.IsApproved
-- 2026-02-13T14:55:40.986Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773855,0,549052,554914,647887,'F',TO_TIMESTAMP('2026-02-13 14:55:40.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Freigegeben',5,140,0,TO_TIMESTAMP('2026-02-13 14:55:40.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.In Transit
-- Column: DD_Order.IsInTransit
-- 2026-02-13T14:55:41.312Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773854,0,549052,554914,647888,'F',TO_TIMESTAMP('2026-02-13 14:55:41.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','In Transit',20,110,0,TO_TIMESTAMP('2026-02-13 14:55:41.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.Belegstatus
-- Column: DD_Order.DocStatus
-- 2026-02-13T14:55:41.677Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773852,0,549052,554914,647889,'F',TO_TIMESTAMP('2026-02-13 14:55:41.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','N','Y','Y','N','Belegstatus',30,150,40,TO_TIMESTAMP('2026-02-13 14:55:41.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20
-- UI Element Group: posted
-- 2026-02-13T14:55:41.894Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549237,554915,TO_TIMESTAMP('2026-02-13 14:55:41.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','posted',30,TO_TIMESTAMP('2026-02-13 14:55:41.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> posted.Posted
-- Column: DD_Order.Posted
-- 2026-02-13T14:55:42.282Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773812,0,549052,554915,647890,'F',TO_TIMESTAMP('2026-02-13 14:55:41.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Posting status','The Posted field indicates the status of the Generation of General Ledger Accounting Lines ','Y','N','N','Y','Y','N','N',0,'Posted',10,160,0,TO_TIMESTAMP('2026-02-13 14:55:41.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20
-- UI Element Group: manufacturing
-- 2026-02-13T14:55:42.507Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549237,554916,TO_TIMESTAMP('2026-02-13 14:55:42.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','manufacturing',35,TO_TIMESTAMP('2026-02-13 14:55:42.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> manufacturing.Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- 2026-02-13T14:55:42.901Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773864,0,549052,554916,647891,'F',TO_TIMESTAMP('2026-02-13 14:55:42.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Forward Manufacturing Order',10,0,0,TO_TIMESTAMP('2026-02-13 14:55:42.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> manufacturing.Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2026-02-13T14:55:43.232Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773865,0,549052,554916,647892,'F',TO_TIMESTAMP('2026-02-13 14:55:42.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Forward Manufacturing Order BOM Line',20,0,0,TO_TIMESTAMP('2026-02-13 14:55:42.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20
-- UI Element Group: org
-- 2026-02-13T14:55:43.474Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549237,554917,TO_TIMESTAMP('2026-02-13 14:55:43.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',40,TO_TIMESTAMP('2026-02-13 14:55:43.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> org.Organisation
-- Column: DD_Order.AD_Org_ID
-- 2026-02-13T14:55:43.874Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773820,0,549052,554917,647893,'F',TO_TIMESTAMP('2026-02-13 14:55:43.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Organisation',10,170,50,TO_TIMESTAMP('2026-02-13 14:55:43.563000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> org.Mandant
-- Column: DD_Order.AD_Client_ID
-- 2026-02-13T14:55:44.198Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773819,0,549052,554917,647894,'F',TO_TIMESTAMP('2026-02-13 14:55:43.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2026-02-13 14:55:43.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Tab: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01)
-- UI Section: advanced edit
-- 2026-02-13T14:55:44.420Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549052,547569,TO_TIMESTAMP('2026-02-13 14:55:44.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-13 14:55:44.287000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'advanced edit')
;

-- 2026-02-13T14:55:44.443Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547569 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-13T14:55:44.493Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547569
;

-- 2026-02-13T14:55:44.535Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547569, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540531
;

-- UI Section: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit
-- UI Column: 10
-- 2026-02-13T14:55:44.734Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549238,547569,TO_TIMESTAMP('2026-02-13 14:55:44.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-13 14:55:44.608000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10
-- UI Element Group: advanced edit
-- 2026-02-13T14:55:44.973Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549238,554918,TO_TIMESTAMP('2026-02-13 14:55:44.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2026-02-13 14:55:44.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Beschreibung
-- Column: DD_Order.Description
-- 2026-02-13T14:55:45.422Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773823,0,549052,554918,647895,'F',TO_TIMESTAMP('2026-02-13 14:55:45.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Beschreibung',50,0,0,TO_TIMESTAMP('2026-02-13 14:55:45.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Company Agent
-- Column: DD_Order.SalesRep_ID
-- 2026-02-13T14:55:45.804Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773825,0,549052,554918,647896,'F',TO_TIMESTAMP('2026-02-13 14:55:45.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Company Agent','Company Agent to Distribution Order','Y','Y','N','Y','N','N','N','Company Agent',70,0,0,TO_TIMESTAMP('2026-02-13 14:55:45.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Lieferung
-- Column: DD_Order.DeliveryViaRule
-- 2026-02-13T14:55:46.132Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773832,0,549052,554918,647897,'F',TO_TIMESTAMP('2026-02-13 14:55:45.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','Y','N','Y','N','N','N','Lieferung',120,0,0,TO_TIMESTAMP('2026-02-13 14:55:45.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Lieferart
-- Column: DD_Order.DeliveryRule
-- 2026-02-13T14:55:46.454Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773833,0,549052,554918,647898,'F',TO_TIMESTAMP('2026-02-13 14:55:46.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','Y','N','Y','N','N','N','Lieferart',130,0,0,TO_TIMESTAMP('2026-02-13 14:55:46.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Lieferdatum
-- Column: DD_Order.ShipDate
-- 2026-02-13T14:55:46.766Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773836,0,549052,554918,647899,'F',TO_TIMESTAMP('2026-02-13 14:55:46.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Shipment Date/Time','Actual Date/Time of Shipment (pick up)','Y','Y','N','Y','N','N','N','Lieferdatum',160,0,0,TO_TIMESTAMP('2026-02-13 14:55:46.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Eingangsdatum
-- Column: DD_Order.DateReceived
-- 2026-02-13T14:55:47.125Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773837,0,549052,554918,647900,'F',TO_TIMESTAMP('2026-02-13 14:55:46.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, zu dem ein Produkt empfangen wurde','"Eingangsdatum" bezeichnet das Datum, zu dem das Produkt empfangen wurde','Y','Y','N','Y','N','N','N','Eingangsdatum',170,0,0,TO_TIMESTAMP('2026-02-13 14:55:46.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Date printed
-- Column: DD_Order.DatePrinted
-- 2026-02-13T14:55:47.436Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773839,0,549052,554918,647901,'F',TO_TIMESTAMP('2026-02-13 14:55:47.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Date the document was printed.','Indicates the Date that a document was printed.','Y','Y','N','Y','N','N','N','Date printed',190,0,0,TO_TIMESTAMP('2026-02-13 14:55:47.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Frachtkostenberechnung
-- Column: DD_Order.FreightCostRule
-- 2026-02-13T14:55:47.753Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773840,0,549052,554918,647902,'F',TO_TIMESTAMP('2026-02-13 14:55:47.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode zur Berechnung von Frachtkosten','"Frachtkostenberechnung" gibt an, auf welche Weise die Kosten für Fracht berechnet werden.','Y','Y','N','Y','N','N','N','Frachtkostenberechnung',200,0,0,TO_TIMESTAMP('2026-02-13 14:55:47.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Frachtbetrag
-- Column: DD_Order.FreightAmt
-- 2026-02-13T14:55:48.086Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773841,0,549052,554918,647903,'F',TO_TIMESTAMP('2026-02-13 14:55:47.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Frachtbetrag','"Frachtbetrag" gibt den Betrag für Fracht in diesem Beleg an.','Y','Y','N','Y','N','N','N','Frachtbetrag',210,0,0,TO_TIMESTAMP('2026-02-13 14:55:47.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Priorität
-- Column: DD_Order.PriorityRule
-- 2026-02-13T14:55:48.449Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773842,0,549052,554918,647904,'F',TO_TIMESTAMP('2026-02-13 14:55:48.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Priority of a document','The Priority indicates the importance (high, medium, low) of this document','Y','Y','N','Y','N','N','N','Priorität',220,0,0,TO_TIMESTAMP('2026-02-13 14:55:48.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Projekt
-- Column: DD_Order.C_Project_ID
-- 2026-02-13T14:55:48.756Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773845,0,549052,554918,647905,'F',TO_TIMESTAMP('2026-02-13 14:55:48.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Finanzprojekt','Ein Projekt erlaubt, interne oder externe Vorgäng zu verfolgen und zu kontrollieren.','Y','Y','N','Y','N','N','N','Projekt',270,0,0,TO_TIMESTAMP('2026-02-13 14:55:48.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Werbemassnahme
-- Column: DD_Order.C_Campaign_ID
-- 2026-02-13T14:55:49.068Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773848,0,549052,554918,647906,'F',TO_TIMESTAMP('2026-02-13 14:55:48.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Marketing Campaign','The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','Y','N','Y','N','N','N','Werbemassnahme',290,0,0,TO_TIMESTAMP('2026-02-13 14:55:48.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Buchende Organisation
-- Column: DD_Order.AD_OrgTrx_ID
-- 2026-02-13T14:55:49.399Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773849,0,549052,554918,647907,'F',TO_TIMESTAMP('2026-02-13 14:55:49.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Durchführende oder auslösende Organisation','Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.','Y','Y','N','Y','N','N','N','Buchende Organisation',300,0,0,TO_TIMESTAMP('2026-02-13 14:55:49.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Nutzer 1
-- Column: DD_Order.User1_ID
-- 2026-02-13T14:55:49.752Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773850,0,549052,554918,647908,'F',TO_TIMESTAMP('2026-02-13 14:55:49.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 1','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','Y','N','Y','N','N','N','Nutzer 1',310,0,0,TO_TIMESTAMP('2026-02-13 14:55:49.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Nutzer 2
-- Column: DD_Order.User2_ID
-- 2026-02-13T14:55:50.067Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773851,0,549052,554918,647909,'F',TO_TIMESTAMP('2026-02-13 14:55:49.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzerdefiniertes Element Nr. 2','Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','Y','N','Y','N','N','N','Nutzer 2',320,0,0,TO_TIMESTAMP('2026-02-13 14:55:49.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.andrucken
-- Column: DD_Order.IsPrinted
-- 2026-02-13T14:55:50.409Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773856,0,549052,554918,647910,'F',TO_TIMESTAMP('2026-02-13 14:55:50.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Indicates if this document / line is printed','The Printed checkbox indicates if this document or line will included when printing.','Y','Y','N','Y','N','N','N','andrucken',370,0,0,TO_TIMESTAMP('2026-02-13 14:55:50.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Zugestellt
-- Column: DD_Order.IsDelivered
-- 2026-02-13T14:55:50.750Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773857,0,549052,554918,647911,'F',TO_TIMESTAMP('2026-02-13 14:55:50.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Zugestellt',380,0,0,TO_TIMESTAMP('2026-02-13 14:55:50.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.MRP Generated Document
-- Column: DD_Order.MRP_Generated
-- 2026-02-13T14:55:51.085Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773859,0,549052,554918,647912,'F',TO_TIMESTAMP('2026-02-13 14:55:50.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'This document was generated by MRP','Y','Y','N','Y','N','N','N','MRP Generated Document',390,0,0,TO_TIMESTAMP('2026-02-13 14:55:50.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.MRP Allow Cleanup
-- Column: DD_Order.MRP_AllowCleanup
-- 2026-02-13T14:55:51.439Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773860,0,549052,554918,647913,'F',TO_TIMESTAMP('2026-02-13 14:55:51.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'MRP is allowed to remove this document','Y','Y','N','Y','N','N','N','MRP Allow Cleanup',400,0,0,TO_TIMESTAMP('2026-02-13 14:55:51.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.To be deleted (MRP)
-- Column: DD_Order.MRP_ToDelete
-- 2026-02-13T14:55:51.793Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773861,0,549052,554918,647914,'F',TO_TIMESTAMP('2026-02-13 14:55:51.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Indicates if this document is scheduled to be deleted by MRP','Y','Y','N','Y','N','N','N','To be deleted (MRP)',410,0,0,TO_TIMESTAMP('2026-02-13 14:55:51.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> advanced edit -> 10 -> advanced edit.Reihenfolge
-- Column: DD_Order.SeqNo
-- 2026-02-13T14:55:52.120Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773870,0,549052,554918,647915,'F',TO_TIMESTAMP('2026-02-13 14:55:51.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','Y','N','Y','N','N','N','Reihenfolge',420,0,0,TO_TIMESTAMP('2026-02-13 14:55:51.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01)
-- UI Section: main
-- 2026-02-13T14:55:52.398Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549053,547570,TO_TIMESTAMP('2026-02-13 14:55:52.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-13 14:55:52.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-13T14:55:52.423Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547570 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-13T14:55:52.471Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547570
;

-- 2026-02-13T14:55:52.501Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547570, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540189
;

-- UI Section: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main
-- UI Column: 10
-- 2026-02-13T14:55:52.705Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549239,547570,TO_TIMESTAMP('2026-02-13 14:55:52.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-13 14:55:52.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10
-- UI Element Group: default
-- 2026-02-13T14:55:52.928Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549239,554919,TO_TIMESTAMP('2026-02-13 14:55:52.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-13 14:55:52.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Zeile Nr.
-- Column: DD_OrderLine.Line
-- 2026-02-13T14:55:53.282Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773900,0,549053,554919,647916,'F',TO_TIMESTAMP('2026-02-13 14:55:53.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','Y','Y','N','N','Zeile Nr.',10,10,0,TO_TIMESTAMP('2026-02-13 14:55:53.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Produkt
-- Column: DD_OrderLine.M_Product_ID
-- 2026-02-13T14:55:53.598Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773902,0,549053,554919,647917,'F',TO_TIMESTAMP('2026-02-13 14:55:53.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','Y','N','N','Produkt',20,20,0,TO_TIMESTAMP('2026-02-13 14:55:53.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2026-02-13T14:55:53.952Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,773913,0,542465,647917,TO_TIMESTAMP('2026-02-13 14:55:53.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2026-02-13 14:55:53.688000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Menge TU
-- Column: DD_OrderLine.QtyEnteredTU
-- 2026-02-13T14:55:54.236Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773912,0,549053,554919,647918,'F',TO_TIMESTAMP('2026-02-13 14:55:54.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Menge TU',30,30,0,TO_TIMESTAMP('2026-02-13 14:55:54.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Menge
-- Column: DD_OrderLine.QtyEntered
-- 2026-02-13T14:55:54.600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773897,0,549053,554919,647919,'F',TO_TIMESTAMP('2026-02-13 14:55:54.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','Y','N','N','Y','Y','N','N','Menge',40,50,0,TO_TIMESTAMP('2026-02-13 14:55:54.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Packvorschrift
-- Column: DD_OrderLine.M_HU_PI_Item_Product_ID
-- 2026-02-13T14:55:54.923Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773913,0,549053,554919,647920,'F',TO_TIMESTAMP('2026-02-13 14:55:54.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Packvorschrift',50,40,0,TO_TIMESTAMP('2026-02-13 14:55:54.704000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Maßeinheit
-- Column: DD_OrderLine.C_UOM_ID
-- 2026-02-13T14:55:55.279Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773878,0,549053,554919,647921,'F',TO_TIMESTAMP('2026-02-13 14:55:55.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','Y','N','N','Maßeinheit',60,60,0,TO_TIMESTAMP('2026-02-13 14:55:55.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Instance
-- Column: DD_OrderLine.M_AttributeSetInstance_ID
-- 2026-02-13T14:55:55.637Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773906,0,549053,554919,647922,'F',TO_TIMESTAMP('2026-02-13 14:55:55.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Product Attribute Set Instance','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','Y','N','N','Instance',70,70,0,TO_TIMESTAMP('2026-02-13 14:55:55.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Lagerort
-- Column: DD_OrderLine.M_Locator_ID
-- 2026-02-13T14:55:55.958Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773904,0,549053,554919,647923,'F',TO_TIMESTAMP('2026-02-13 14:55:55.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','Y','Y','N','N','Lagerort',80,80,0,TO_TIMESTAMP('2026-02-13 14:55:55.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Instance To
-- Column: DD_OrderLine.M_AttributeSetInstanceTo_ID
-- 2026-02-13T14:55:56.265Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773907,0,549053,554919,647924,'F',TO_TIMESTAMP('2026-02-13 14:55:56.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Target Product Attribute Set Instance','Y','N','N','Y','Y','N','N','Instance To',90,90,0,TO_TIMESTAMP('2026-02-13 14:55:56.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Lagerort An
-- Column: DD_OrderLine.M_LocatorTo_ID
-- 2026-02-13T14:55:56.620Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773905,0,549053,554919,647925,'F',TO_TIMESTAMP('2026-02-13 14:55:56.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort, an den Bestand bewegt wird','"Lagerort An" bezeichnet den Lagerort, auf den ein Warenbestand bewegt wird.','Y','N','N','Y','Y','N','N','Lagerort An',100,100,0,TO_TIMESTAMP('2026-02-13 14:55:56.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Zugestellt
-- Column: DD_OrderLine.IsDelivered
-- 2026-02-13T14:55:56.927Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773908,0,549053,554919,647926,'F',TO_TIMESTAMP('2026-02-13 14:55:56.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Zugestellt',110,0,0,TO_TIMESTAMP('2026-02-13 14:55:56.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Allow Push
-- Column: DD_OrderLine.DD_AllowPush
-- 2026-02-13T14:55:57.270Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773910,0,549053,554919,647927,'F',TO_TIMESTAMP('2026-02-13 14:55:57.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Allow pushing materials from suppliers through this path.','Y','N','N','Y','N','N','N','Allow Push',120,0,0,TO_TIMESTAMP('2026-02-13 14:55:57.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Ordered Quantity
-- Column: DD_OrderLine.QtyOrdered
-- 2026-02-13T14:55:57.607Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773903,0,549053,554919,647928,'F',TO_TIMESTAMP('2026-02-13 14:55:57.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ordered Quantity','The Ordered Quantity indicates the quantity of a product that was ordered.','Y','N','N','Y','N','N','N','Ordered Quantity',130,0,0,TO_TIMESTAMP('2026-02-13 14:55:57.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Auftragsposition
-- Column: DD_OrderLine.C_OrderLineSO_ID
-- 2026-02-13T14:55:57.941Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773909,0,549053,554919,647929,'F',TO_TIMESTAMP('2026-02-13 14:55:57.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','Y','N','N','N','Auftragsposition',140,0,0,TO_TIMESTAMP('2026-02-13 14:55:57.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Gelieferte Menge
-- Column: DD_OrderLine.QtyDelivered
-- 2026-02-13T14:55:58.274Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773884,0,549053,554919,647930,'F',TO_TIMESTAMP('2026-02-13 14:55:58.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gelieferte Menge','Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','N','N','Y','N','N','N','Gelieferte Menge',150,0,0,TO_TIMESTAMP('2026-02-13 14:55:58.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Geschäftspartner
-- Column: DD_OrderLine.C_BPartner_ID
-- 2026-02-13T14:55:58.610Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773915,0,549053,554919,647931,'F',TO_TIMESTAMP('2026-02-13 14:55:58.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N','Geschäftspartner',160,0,0,TO_TIMESTAMP('2026-02-13 14:55:58.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Keep target plant
-- Column: DD_OrderLine.IsKeepTargetPlant
-- 2026-02-13T14:55:58.930Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773916,0,549053,554919,647932,'F',TO_TIMESTAMP('2026-02-13 14:55:58.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'If set, the MRP demand of the distribution order which will be generated will have the same plant is target warehouse.','This feature shall be used when we want to transfer materials inside the same plant, no matter which is the plant on which the source warehouse is assigned.','Y','N','N','Y','N','N','N','Keep target plant',170,0,0,TO_TIMESTAMP('2026-02-13 14:55:58.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Produktionsstätte ab
-- Column: DD_OrderLine.PP_Plant_From_ID
-- 2026-02-13T14:55:59.268Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773911,0,549053,554919,647933,'F',TO_TIMESTAMP('2026-02-13 14:55:59.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Produktionsstätte ab',180,0,0,TO_TIMESTAMP('2026-02-13 14:55:59.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Qty In Transit
-- Column: DD_OrderLine.QtyInTransit
-- 2026-02-13T14:55:59.609Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773871,0,549053,554919,647934,'F',TO_TIMESTAMP('2026-02-13 14:55:59.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Qty In Transit',190,0,0,TO_TIMESTAMP('2026-02-13 14:55:59.393000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Zugesagter Termin
-- Column: DD_OrderLine.DatePromised
-- 2026-02-13T14:55:59.978Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,773901,0,549053,554919,647935,'F',TO_TIMESTAMP('2026-02-13 14:55:59.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','Y','N','N','N','Zugesagter Termin',200,0,0,TO_TIMESTAMP('2026-02-13 14:55:59.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Organisation
-- Column: DD_OrderLine.AD_Org_ID
-- 2026-02-13T14:56:00.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773873,0,549053,554919,647936,'F',TO_TIMESTAMP('2026-02-13 14:56:00.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Organisation',210,110,0,TO_TIMESTAMP('2026-02-13 14:56:00.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distribution Order Line(549053,EE01) -> main -> 10 -> default.Mandant
-- Column: DD_OrderLine.AD_Client_ID
-- 2026-02-13T14:56:00.681Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773889,0,549053,554919,647937,'F',TO_TIMESTAMP('2026-02-13 14:56:00.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Mandant',220,0,0,TO_TIMESTAMP('2026-02-13 14:56:00.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Belegart
-- Column: DD_Order.C_DocType_ID
-- 2026-02-13T15:05:16.123Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-13 15:05:16.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647882
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Datum
-- Column: DD_Order.DateOrdered
-- 2026-02-13T15:05:16.252Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-13 15:05:16.252000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647884
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Geschäftspartner
-- Column: DD_Order.C_BPartner_ID
-- 2026-02-13T15:05:16.391Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-13 15:05:16.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647872
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Kostenstelle
-- Column: DD_Order.C_Activity_ID
-- 2026-02-13T15:05:16.521Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-13 15:05:16.521000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647875
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Produktionsstätte
-- Column: DD_Order.PP_Plant_ID
-- 2026-02-13T15:05:16.695Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-13 15:05:16.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647873
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dimensions.Streckengeschäft
-- Column: DD_Order.IsDropShip
-- 2026-02-13T15:05:16.835Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-13 15:05:16.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647879
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Nr.
-- Column: DD_Order.DocumentNo
-- 2026-02-13T15:05:16.989Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-02-13 15:05:16.987000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647883
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Zugesagter Termin
-- Column: DD_Order.DatePromised
-- 2026-02-13T15:05:17.176Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-13 15:05:17.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647885
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Warehouse from
-- Column: DD_Order.M_Warehouse_From_ID
-- 2026-02-13T15:05:17.303Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-13 15:05:17.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647876
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Transit Lager
-- Column: DD_Order.M_Warehouse_ID
-- 2026-02-13T15:05:17.457Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-13 15:05:17.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647874
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Warehouse to
-- Column: DD_Order.M_Warehouse_To_ID
-- 2026-02-13T15:05:17.625Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-13 15:05:17.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647877
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dimensions.Lieferweg
-- Column: DD_Order.M_Shipper_ID
-- 2026-02-13T15:05:17.779Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-13 15:05:17.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647878
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.In Transit
-- Column: DD_Order.IsInTransit
-- 2026-02-13T15:05:17.923Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-13 15:05:17.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647888
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.Freigegeben
-- Column: DD_Order.IsApproved
-- 2026-02-13T15:05:18.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-13 15:05:18.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647887
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.Belegstatus
-- Column: DD_Order.DocStatus
-- 2026-02-13T15:05:18.219Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-13 15:05:18.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647889
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> posted.Posted
-- Column: DD_Order.Posted
-- 2026-02-13T15:05:18.372Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-02-13 15:05:18.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647890
;

-- UI Element: Distributionsauftrag(542091,EE01) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> org.Organisation
-- Column: DD_Order.AD_Org_ID
-- 2026-02-13T15:05:18.509Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-02-13 15:05:18.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647893
;

-- Window: Distributionsauftrag, InternalName=Distributionsauftrag NEW
-- 2026-02-13T15:06:09.238Z
UPDATE AD_Window SET IsOverrideInMenu='Y',Updated=TO_TIMESTAMP('2026-02-13 15:06:09.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542091
;

-- Window: Distributionsauftrag, InternalName=Distributionsauftrag NEW
-- 2026-02-13T15:06:58.183Z
UPDATE AD_Window SET EntityType='U',Updated=TO_TIMESTAMP('2026-02-13 15:06:58.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542091
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> manufacturing.Forward Manufacturing Order
-- Column: DD_Order.Forward_PP_Order_ID
-- 2026-02-13T15:08:29.132Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-13 15:08:29.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647891
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Warehouse from
-- Column: DD_Order.M_Warehouse_From_ID
-- 2026-02-13T15:08:29.276Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-13 15:08:29.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647876
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Transit Lager
-- Column: DD_Order.M_Warehouse_ID
-- 2026-02-13T15:08:29.407Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-13 15:08:29.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647874
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Warehouse to
-- Column: DD_Order.M_Warehouse_To_ID
-- 2026-02-13T15:08:29.574Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-13 15:08:29.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647877
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dimensions.Lieferweg
-- Column: DD_Order.M_Shipper_ID
-- 2026-02-13T15:08:29.708Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-13 15:08:29.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647878
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.In Transit
-- Column: DD_Order.IsInTransit
-- 2026-02-13T15:08:29.848Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-13 15:08:29.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647888
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.Freigegeben
-- Column: DD_Order.IsApproved
-- 2026-02-13T15:08:30.053Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-13 15:08:30.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647887
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> referenz.Belegstatus
-- Column: DD_Order.DocStatus
-- 2026-02-13T15:08:30.184Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-02-13 15:08:30.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647889
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> posted.Posted
-- Column: DD_Order.Posted
-- 2026-02-13T15:08:30.346Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-02-13 15:08:30.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647890
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> org.Organisation
-- Column: DD_Order.AD_Org_ID
-- 2026-02-13T15:08:30.500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-13 15:08:30.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647893
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Kostenstelle
-- Column: DD_Order.C_Activity_ID
-- 2026-02-13T15:11:13.743Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 15:11:13.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647875
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> default.Produktionsstätte
-- Column: DD_Order.PP_Plant_ID
-- 2026-02-13T15:11:59.438Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 15:11:59.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647873
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dimensions.Streckengeschäft
-- Column: DD_Order.IsDropShip
-- 2026-02-13T15:13:31.380Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 15:13:31.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647879
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> manufacturing.Forward Manufacturing Order BOM Line
-- Column: DD_Order.Forward_PP_Order_BOMLine_ID
-- 2026-02-13T15:15:14.084Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 15:15:14.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647892
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Buchungsstatus
-- Column: DD_Order.Posted
-- 2026-02-13T15:16:50.866Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773812,0,549052,554913,647938,'F',TO_TIMESTAMP('2026-02-13 15:16:50.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Buchungsstatus','Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','N','N','Y','N','N','N',0,'Buchungsstatus',25,0,0,TO_TIMESTAMP('2026-02-13 15:16:50.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10
-- UI Element Group: dates
-- 2026-02-13T15:19:05.519Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549236,554920,TO_TIMESTAMP('2026-02-13 15:19:05.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dates',15,TO_TIMESTAMP('2026-02-13 15:19:05.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dates.Datum
-- Column: DD_Order.DateOrdered
-- 2026-02-13T15:19:30.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773826,0,549052,554920,647939,'F',TO_TIMESTAMP('2026-02-13 15:19:30.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','N','Y','N','N','N',0,'Datum',10,0,0,TO_TIMESTAMP('2026-02-13 15:19:30.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dates.Zugesagter Termin
-- Column: DD_Order.DatePromised
-- 2026-02-13T15:19:53.021Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773827,0,549052,554920,647940,'F',TO_TIMESTAMP('2026-02-13 15:19:52.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','Y','N','N','N',0,'Zugesagter Termin',20,0,0,TO_TIMESTAMP('2026-02-13 15:19:52.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 10 -> dates.Kommissionierdatum
-- Column: DD_Order.PickDate
-- 2026-02-13T15:20:26.212Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773838,0,549052,554920,647941,'F',TO_TIMESTAMP('2026-02-13 15:20:25.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum/Zeit der Kommissionierung für die Lieferung','Y','N','N','Y','N','N','N',0,'Kommissionierdatum',30,0,0,TO_TIMESTAMP('2026-02-13 15:20:25.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Datum
-- Column: DD_Order.DateOrdered
-- 2026-02-13T15:21:09.699Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-13 15:21:09.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647884
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Zugesagter Termin
-- Column: DD_Order.DatePromised
-- 2026-02-13T15:21:44.996Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-13 15:21:44.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647885
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> docno.Kommissionierdatum
-- Column: DD_Order.PickDate
-- 2026-02-13T15:22:04.034Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-13 15:22:04.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647886
;

-- UI Element: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20 -> posted.Posted
-- Column: DD_Order.Posted
-- 2026-02-13T15:22:42.050Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 15:22:42.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647890
;

-- UI Column: Distributionsauftrag(542091,U) -> Distributionsauftrag(549052,EE01) -> main -> 20
-- UI Element Group: posted
-- 2026-02-13T15:23:19.874Z
UPDATE AD_UI_ElementGroup SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-13 15:23:19.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=554915
;

-- Element: null
-- 2026-02-13T15:24:38.697Z
UPDATE AD_Element_Trl SET Description='Distributionsauftragszeile', IsTranslated='Y', Name='Distributionsauftragszeile', PrintName='Distributionsauftragszeile',Updated=TO_TIMESTAMP('2026-02-13 15:24:38.697000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=573608 AND AD_Language='de_DE'
;

-- 2026-02-13T15:24:38.720Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-13T15:25:13.371Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(573608,'de_DE')
;

-- 2026-02-13T15:25:13.387Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573608,'de_DE')
;

-- Element: M_AttributeSetInstanceTo_ID
-- 2026-02-13T15:28:48.188Z
UPDATE AD_Element_Trl SET Description='Merkmale Ziel', Name='Merkmale Ziel', PrintName='Merkmale Ziel',Updated=TO_TIMESTAMP('2026-02-13 15:28:48.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2799 AND AD_Language='de_DE'
;

-- 2026-02-13T15:28:48.212Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-13T15:29:03.486Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2799,'de_DE')
;

-- 2026-02-13T15:29:03.508Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2799,'de_DE')
;

-- Element: M_AttributeSetInstanceTo_ID
-- 2026-02-13T15:29:08.589Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-13 15:29:08.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2799 AND AD_Language='de_DE'
;

-- 2026-02-13T15:29:08.631Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2799,'de_DE')
;

-- 2026-02-13T15:29:08.655Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2799,'de_DE')
;

-- Element: M_AttributeSetInstanceTo_ID
-- 2026-02-13T15:29:17.463Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-13 15:29:17.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2799 AND AD_Language='fr_CH'
;

-- 2026-02-13T15:29:17.507Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2799,'fr_CH')
;

-- Element: M_AttributeSetInstanceTo_ID
-- 2026-02-13T15:29:27.880Z
UPDATE AD_Element_Trl SET Name='Attribute Set Instance To',Updated=TO_TIMESTAMP('2026-02-13 15:29:27.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2799 AND AD_Language='en_US'
;

-- 2026-02-13T15:29:27.901Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-13T15:29:54.852Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2799,'en_US')
;