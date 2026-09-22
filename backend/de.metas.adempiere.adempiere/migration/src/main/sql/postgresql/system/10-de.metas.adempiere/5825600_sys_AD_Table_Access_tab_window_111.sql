-- Role table access: an AD_Table_Access tab on the Roles window (AD_Window 111, "Rolle - Verwaltung"),
-- so an administrator can configure the per-role table permissions -- including the new
-- IsCanCreateNewRecords flag -- without opening the legacy window 268.
--
-- Modelled on the sibling "Fenster-Zugriff" tab (AD_Tab 304, AD_Window_Access): one AD_UI_Section ->
-- one AD_UI_Column -> one primary AD_UI_ElementGroup holding every field, the parent-link field
-- present but not displayed, AD_Org_ID last in the grid and AD_Client_ID form-only. That single-group
-- shape is what the design rules prescribe for an included/detail tab.
--
-- The tab is bound to the parent Rolle tab through AD_Tab.Parent_Column_ID = AD_Table_Access.AD_Role_ID
-- (the FK in the CHILD table); AD_Tab.AD_Column_ID stays NULL.
--
-- Field order follows the permission's own logic: which table (AD_Table_ID), whether the row counts at
-- all (IsActive), then the access scope (IsExclude), then what may be done with the data -- read-only,
-- create new records, report, export. The new create permission therefore sits in the middle of the
-- flags, not appended after the output rights where it would read as an afterthought.
--
-- SeqNo 45 places the tab directly after "Fenster-Zugriff" (40) and before "Prozess-Zugriff" (50):
-- table access is the data-level sibling of window access.

-- Tab caption. A dedicated element rather than the legacy tab 482's element 572794: window 268 is
-- being retired and its captions must stay independent of this one.
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585480 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-22 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Berechtigung Tabellen-Zugriff','D','Y','Tabellen-Zugriff','Tabellen-Zugriff',TO_TIMESTAMP('2026-09-22 09:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585480 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name='Tabellen-Zugriff', Description='Berechtigung Tabellen-Zugriff', PrintName='Tabellen-Zugriff', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-22 09:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585480 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Tabellen-Zugriff', Description='Berechtigung Tabellen-Zugriff', PrintName='Tabellen-Zugriff', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-22 09:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585480 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Table Access', Description='Table access permissions of this role', PrintName='Table Access', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-22 09:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585480 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET Name='Table Access', Description='Table access permissions of this role', PrintName='Table Access', IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-09-22 09:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585480 AND AD_Language='fr_CH'
;

-- The tab itself. IsSingleRow='N' mirrors tab 304: the administrator works through a grid of table
-- rules, not one record at a time.
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,585480,0,549493 /*From ID Server*/,565,111,'N',TO_TIMESTAMP('2026-09-22 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,'Berechtigung Tabellen-Zugriff','D','N','N','AD_Table_Access','Y','N','Y','Y','N','N','N','Y','Y','N','N','Y','Y','N','N','N','N',0,'Tabellen-Zugriff',8572,'N',45,1,TO_TIMESTAMP('2026-09-22 09:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549493 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;
SELECT update_tab_translation_from_ad_element(585480);
SELECT AD_Element_Link_Create_Missing_Tab(549493);

-- AD_Fields. The parent-link field AD_Role_ID must exist on the tab even though it is never shown --
-- the framework resolves the child-parent link through the tab's FIELDS. AD_Client_ID is form-only
-- (never a grid column) and AD_Org_ID is the last grid column, per the design rules.
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,8572,785046 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','Y','N','Rolle',0,0,TO_TIMESTAMP('2026-09-22 09:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785046);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,8574,785047 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:01','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','N','N','DB-Tabelle',10,10,1,TO_TIMESTAMP('2026-09-22 09:01:01','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785047);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,8570,785048 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:02','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','N','N','Aktiv',20,20,TO_TIMESTAMP('2026-09-22 09:01:02','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785048);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,8844,785049 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:03','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','N','N','Ausschluß',30,30,TO_TIMESTAMP('2026-09-22 09:01:03','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785049);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,8568,785050 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:04','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','N','N','Schreibgeschützt',40,40,TO_TIMESTAMP('2026-09-22 09:01:04','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785050);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593636,785051 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:05','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','N','N','Neue Datensätze anlegen',50,50,TO_TIMESTAMP('2026-09-22 09:01:05','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785051);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,9970,785052 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:06','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','N','N','Kann Berichte erstellen',60,60,TO_TIMESTAMP('2026-09-22 09:01:06','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785052);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,9971,785053 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','N','N','Kann exportieren',70,70,TO_TIMESTAMP('2026-09-22 09:01:07','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785053);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,8573,785054 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','N','N','Sektion',80,80,TO_TIMESTAMP('2026-09-22 09:01:08','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785054);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,8566,785055 /*From ID Server*/,0,549493,TO_TIMESTAMP('2026-09-22 09:01:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','Y','N','Mandant',90,0,TO_TIMESTAMP('2026-09-22 09:01:09','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(785055);

-- The AD_UI_* chain. One section, one column, one primary element group: an included tab keeps all
-- its fields in a single group (design rules, "Included Record Tabs").
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549493,547992 /*From ID Server*/,TO_TIMESTAMP('2026-09-22 09:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-09-22 09:02:00','YYYY-MM-DD HH24:MI:SS'),100,'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547992 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549758 /*From ID Server*/,547992,TO_TIMESTAMP('2026-09-22 09:02:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-09-22 09:02:01','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549758,555777 /*From ID Server*/,TO_TIMESTAMP('2026-09-22 09:02:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2026-09-22 09:02:02','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785046,0,549493,555777,654788 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','Rolle',0,0,0,TO_TIMESTAMP('2026-09-22 09:02:10','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785047,0,549493,555777,654789 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','DB-Tabelle',10,10,0,TO_TIMESTAMP('2026-09-22 09:02:11','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785048,0,549493,555777,654790 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Aktiv',20,20,0,TO_TIMESTAMP('2026-09-22 09:02:12','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785049,0,549493,555777,654791 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ausschluß',30,30,0,TO_TIMESTAMP('2026-09-22 09:02:13','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785050,0,549493,555777,654792 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Schreibgeschützt',40,40,0,TO_TIMESTAMP('2026-09-22 09:02:14','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785051,0,549493,555777,654793 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Neue Datensätze anlegen',50,50,0,TO_TIMESTAMP('2026-09-22 09:02:15','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785052,0,549493,555777,654794 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Kann Berichte erstellen',60,60,0,TO_TIMESTAMP('2026-09-22 09:02:16','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785053,0,549493,555777,654795 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Kann exportieren',70,70,0,TO_TIMESTAMP('2026-09-22 09:02:17','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,WidgetSize,Updated,UpdatedBy) VALUES (0,785054,0,549493,555777,654796 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Sektion',80,80,0,'M',TO_TIMESTAMP('2026-09-22 09:02:18','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,WidgetSize,Updated,UpdatedBy) VALUES (0,785055,0,549493,555777,654797 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-22 09:02:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',90,0,0,'M',TO_TIMESTAMP('2026-09-22 09:02:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Fill any still-missing _Trl rows for the records created above.
SELECT add_missing_translations();

