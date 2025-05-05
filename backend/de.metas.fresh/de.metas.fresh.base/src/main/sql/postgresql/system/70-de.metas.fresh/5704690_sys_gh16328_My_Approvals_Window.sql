-- 2023-09-29T17:48:00.968977700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582728,0,TO_TIMESTAMP('2023-09-29 18:48:00.048','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Meine Zulassungen','Meine Zulassungen',TO_TIMESTAMP('2023-09-29 18:48:00.048','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:48:01.049046400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582728 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-09-29T17:48:27.113160800Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='My Approvals', PrintName='My Approvals',Updated=TO_TIMESTAMP('2023-09-29 18:48:27.113','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582728 AND AD_Language='en_US'
;

-- 2023-09-29T17:48:27.282927600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582728,'en_US') 
;

-- Window: Meine Zulassungen, InternalName=null
-- 2023-09-29T17:50:02.591375200Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582728,0,541736,TO_TIMESTAMP('2023-09-29 18:50:01.544','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','N','N','N','Y','Meine Zulassungen','N',TO_TIMESTAMP('2023-09-29 18:50:01.544','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-09-29T17:50:02.674678800Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541736 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-09-29T17:50:02.827834700Z
/* DDL */  select update_window_translation_from_ad_element(582728) 
;

-- 2023-09-29T17:50:02.910496800Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541736
;

-- 2023-09-29T17:50:02.987768600Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541736)
;

-- Window: Meine Zulassungen, InternalName=null
-- 2023-09-29T17:50:38.706593100Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='D', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='N', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=541730, WindowType='M', WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2023-09-29 18:50:38.706','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Window_ID=541736
;

-- Tab: Meine Zulassungen(541736,D) -> Approval Requests
-- Table: AD_WF_Approval_Request
-- 2023-09-29T17:50:40.251484Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582675,0,547232,542364,541736,'Y',TO_TIMESTAMP('2023-09-29 18:50:39.173','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','AD_WF_Approval_Request','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Approval Requests','N',10,0,TO_TIMESTAMP('2023-09-29 18:50:39.173','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:40.327995800Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547232 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-09-29T17:50:40.404546400Z
/* DDL */  select update_tab_translation_from_ad_element(582675) 
;

-- 2023-09-29T17:50:40.484076100Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547232)
;

-- 2023-09-29T17:50:40.633839100Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 547232
;

-- 2023-09-29T17:50:40.711570300Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 547232, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 547209
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Mandant
-- Column: AD_WF_Approval_Request.AD_Client_ID
-- 2023-09-29T17:50:42.623572700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587352,720685,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:41.419','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2023-09-29 18:50:41.419','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:42.699895600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720685 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:42.778412600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-09-29T17:50:42.963521800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720685
;

-- 2023-09-29T17:50:43.041608Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720685)
;

-- 2023-09-29T17:50:43.195729100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720685
;

-- 2023-09-29T17:50:43.271685400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720685, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720323
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Organisation
-- Column: AD_WF_Approval_Request.AD_Org_ID
-- 2023-09-29T17:50:44.457646200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587353,720686,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:43.348','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','Organisation',20,1,1,TO_TIMESTAMP('2023-09-29 18:50:43.348','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:44.533747500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720686 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:44.610502500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-09-29T17:50:44.773575700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720686
;

-- 2023-09-29T17:50:44.849517500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720686)
;

-- 2023-09-29T17:50:45.002772100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720686
;

-- 2023-09-29T17:50:45.080209600Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720686, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720324
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Aktiv
-- Column: AD_WF_Approval_Request.IsActive
-- 2023-09-29T17:50:46.272460900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587356,720687,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:45.158','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','Aktiv',30,1,1,TO_TIMESTAMP('2023-09-29 18:50:45.158','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:46.348255200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720687 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:46.425416600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-09-29T17:50:46.571439200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720687
;

-- 2023-09-29T17:50:46.647789500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720687)
;

-- 2023-09-29T17:50:46.803944Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720687
;

-- 2023-09-29T17:50:46.878576200Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720687, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720325
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Approval Requests
-- Column: AD_WF_Approval_Request.AD_WF_Approval_Request_ID
-- 2023-09-29T17:50:48.072729300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587359,720688,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:46.953','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D',0,'Y','N','N','N','N','N','N','N','N','Approval Requests',40,1,1,TO_TIMESTAMP('2023-09-29 18:50:46.953','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:48.147963300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720688 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:48.224292900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582675) 
;

-- 2023-09-29T17:50:48.302241200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720688
;

-- 2023-09-29T17:50:48.377217800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720688)
;

-- 2023-09-29T17:50:48.529654600Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720688
;

-- 2023-09-29T17:50:48.605304600Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720688, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720326
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> DB-Tabelle
-- Column: AD_WF_Approval_Request.AD_Table_ID
-- 2023-09-29T17:50:50.166227500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587360,720689,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:48.97','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',10,'D','The Database Table provides the information of the table definition',0,'Y','N','N','N','N','N','N','N','N','DB-Tabelle',50,1,1,TO_TIMESTAMP('2023-09-29 18:50:48.97','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:50.241526Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:50.317992400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2023-09-29T17:50:50.407088200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720689
;

-- 2023-09-29T17:50:50.482148200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720689)
;

-- 2023-09-29T17:50:50.632999500Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720689
;

-- 2023-09-29T17:50:50.707713200Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720689, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720327
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Datensatz-ID
-- Column: AD_WF_Approval_Request.Record_ID
-- 2023-09-29T17:50:51.923467800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587361,720690,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:50.782','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID',10,'D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','N','N','N','N','N','N','N','N','Datensatz-ID',60,1,1,TO_TIMESTAMP('2023-09-29 18:50:50.782','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:51.998787700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720690 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:52.074801200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2023-09-29T17:50:52.160922Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720690
;

-- 2023-09-29T17:50:52.236713100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720690)
;

-- 2023-09-29T17:50:52.390013800Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720690
;

-- 2023-09-29T17:50:52.467193400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720690, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720328
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Ansprechpartner
-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-09-29T17:50:53.653339600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587362,720691,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:52.533','YYYY-MM-DD HH24:MI:SS.US'),100,'User within the system - Internal or Business Partner Contact',10,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','N','N','N','N','N','N','N','N','Ansprechpartner',70,1,1,TO_TIMESTAMP('2023-09-29 18:50:52.533','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:53.729086800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720691 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:53.804656300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2023-09-29T17:50:53.886140800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720691
;

-- 2023-09-29T17:50:53.960921900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720691)
;

-- 2023-09-29T17:50:54.111676700Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720691
;

-- 2023-09-29T17:50:54.186797400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720691, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720329
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Status
-- Column: AD_WF_Approval_Request.Status
-- 2023-09-29T17:50:55.390389900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587363,720692,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:54.262','YYYY-MM-DD HH24:MI:SS.US'),100,'',1,'D','',0,'Y','N','N','N','N','N','N','N','N','Status',80,1,1,TO_TIMESTAMP('2023-09-29 18:50:54.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:55.466271100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720692 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:55.542331300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(3020) 
;

-- 2023-09-29T17:50:55.621777600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720692
;

-- 2023-09-29T17:50:55.697172100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720692)
;

-- 2023-09-29T17:50:55.851011600Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720692
;

-- 2023-09-29T17:50:55.928038200Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720692, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720330
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Nr.
-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-09-29T17:50:57.128893700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587364,720693,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:56.006','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document',255,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','N','N','N','N','N','N','N','Nr.',90,2,1,1,TO_TIMESTAMP('2023-09-29 18:50:56.006','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:57.205046500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720693 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:57.281717700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2023-09-29T17:50:57.363079600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720693
;

-- 2023-09-29T17:50:57.438131900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720693)
;

-- 2023-09-29T17:50:57.590117Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720693
;

-- 2023-09-29T17:50:57.665270400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720693, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720331
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Dokument Basis Typ
-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-09-29T17:50:58.860742800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587365,720694,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:57.748','YYYY-MM-DD HH24:MI:SS.US'),100,'',3,'D','',0,'Y','N','N','N','N','N','N','N','N','Dokument Basis Typ',100,1,1,1,TO_TIMESTAMP('2023-09-29 18:50:57.748','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:50:58.936700500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720694 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:50:59.012744900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(865) 
;

-- 2023-09-29T17:50:59.091342400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720694
;

-- 2023-09-29T17:50:59.165358700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720694)
;

-- 2023-09-29T17:50:59.319695400Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720694
;

-- 2023-09-29T17:50:59.396254900Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720694, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720332
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Workflow-Aktivität
-- Column: AD_WF_Approval_Request.AD_WF_Activity_ID
-- 2023-09-29T17:51:00.585606400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587366,720695,0,547232,0,TO_TIMESTAMP('2023-09-29 18:50:59.472','YYYY-MM-DD HH24:MI:SS.US'),100,'Workflow-Aktivität',10,'D','The Workflow Activity is the actual Workflow Node in a Workflow Process instance',0,'Y','N','N','N','N','N','N','N','N','Workflow-Aktivität',110,1,1,TO_TIMESTAMP('2023-09-29 18:50:59.472','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:51:00.661544100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720695 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:51:00.738551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2307) 
;

-- 2023-09-29T17:51:00.816209500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720695
;

-- 2023-09-29T17:51:00.890662800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720695)
;

-- 2023-09-29T17:51:01.042576300Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720695
;

-- 2023-09-29T17:51:01.118815100Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720695, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720333
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Datum Antwort
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-09-29T17:51:02.314714700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587367,720696,0,547232,0,TO_TIMESTAMP('2023-09-29 18:51:01.196','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum der Antwort',7,'@DateResponse/@!''''','D','Datum der Antwort',0,'Y','N','N','N','N','N','N','N','N','Datum Antwort',120,1,1,TO_TIMESTAMP('2023-09-29 18:51:01.196','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:51:02.390888500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720696 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:51:02.466810700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2389) 
;

-- 2023-09-29T17:51:02.547113900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720696
;

-- 2023-09-29T17:51:02.623134900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720696)
;

-- 2023-09-29T17:51:02.775094300Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720696
;

-- 2023-09-29T17:51:02.850082400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720696, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720334
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Request Date
-- Column: AD_WF_Approval_Request.DateRequest
-- 2023-09-29T17:51:04.033503Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587368,720697,0,547232,0,TO_TIMESTAMP('2023-09-29 18:51:02.927','YYYY-MM-DD HH24:MI:SS.US'),100,7,'D',0,'Y','N','N','N','N','N','N','N','N','Request Date',130,1,1,TO_TIMESTAMP('2023-09-29 18:51:02.927','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:51:04.110013800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720697 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:51:04.185497100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582676) 
;

-- 2023-09-29T17:51:04.263802300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720697
;

-- 2023-09-29T17:51:04.339952300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720697)
;

-- 2023-09-29T17:51:04.490757400Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720697
;

-- 2023-09-29T17:51:04.567051800Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720697, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720335
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Workflow-Prozess
-- Column: AD_WF_Approval_Request.AD_WF_Process_ID
-- 2023-09-29T17:51:05.765272300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587369,720698,0,547232,0,TO_TIMESTAMP('2023-09-29 18:51:04.643','YYYY-MM-DD HH24:MI:SS.US'),100,'Actual Workflow Process Instance',10,'D','Instance of a workflow execution',0,'Y','N','N','N','N','N','N','N','N','Workflow-Prozess',140,1,1,TO_TIMESTAMP('2023-09-29 18:51:04.643','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:51:05.841308800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720698 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:51:05.917195600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2312) 
;

-- 2023-09-29T17:51:05.996224900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720698
;

-- 2023-09-29T17:51:06.069672800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720698)
;

-- 2023-09-29T17:51:06.222903100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720698
;

-- 2023-09-29T17:51:06.299077700Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720698, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720336
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Reihenfolge
-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-09-29T17:51:07.491822100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587370,720699,0,547232,0,TO_TIMESTAMP('2023-09-29 18:51:06.374','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','N','N','N','N','N','N','N','N','Reihenfolge',150,3,1,1,TO_TIMESTAMP('2023-09-29 18:51:06.374','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:51:07.567506200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720699 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T17:51:07.644128Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2023-09-29T17:51:07.725421300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720699
;

-- 2023-09-29T17:51:07.800210100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720699)
;

-- 2023-09-29T17:51:07.958399400Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 720699
;

-- 2023-09-29T17:51:08.032352Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 720699, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 720354
;

-- Tab: Meine Zulassungen(541736,D) -> Approval Requests(547232,D)
-- UI Section: main
-- 2023-09-29T17:51:08.957355800Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547232,545825,TO_TIMESTAMP('2023-09-29 18:51:08.29','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-09-29 18:51:08.29','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-09-29T17:51:09.034594900Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545825 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-09-29T17:51:09.185567700Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545825
;

-- 2023-09-29T17:51:09.265421900Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545825, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545803
;

-- UI Section: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main
-- UI Column: 10
-- 2023-09-29T17:51:10.227673800Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547098,545825,TO_TIMESTAMP('2023-09-29 18:51:09.536','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-09-29 18:51:09.536','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 10
-- UI Element Group: document info
-- 2023-09-29T17:51:11.277232700Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547098,551180,TO_TIMESTAMP('2023-09-29 18:51:10.573','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','document info',10,TO_TIMESTAMP('2023-09-29 18:51:10.573','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 10 -> document info.Nr.
-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-09-29T17:51:12.847322500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720693,0,547232,551180,620602,'F',TO_TIMESTAMP('2023-09-29 18:51:11.692','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','Y','N','N','Nr.',10,20,0,TO_TIMESTAMP('2023-09-29 18:51:11.692','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 10 -> document info.Dokument Basis Typ
-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-09-29T17:51:14.121343600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720694,0,547232,551180,620603,'F',TO_TIMESTAMP('2023-09-29 18:51:13.151','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','N','Y','Y','N','N','Dokument Basis Typ',20,10,0,TO_TIMESTAMP('2023-09-29 18:51:13.151','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 10 -> document info.DB-Tabelle
-- Column: AD_WF_Approval_Request.AD_Table_ID
-- 2023-09-29T17:51:15.392364100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720689,0,547232,551180,620604,'F',TO_TIMESTAMP('2023-09-29 18:51:14.426','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N','DB-Tabelle',30,0,0,TO_TIMESTAMP('2023-09-29 18:51:14.426','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 10 -> document info.Datensatz-ID
-- Column: AD_WF_Approval_Request.Record_ID
-- 2023-09-29T17:51:16.648478900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720690,0,547232,551180,620605,'F',TO_TIMESTAMP('2023-09-29 18:51:15.693','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','N','N','Datensatz-ID',40,0,0,TO_TIMESTAMP('2023-09-29 18:51:15.693','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 10
-- UI Element Group: workflow ref
-- 2023-09-29T17:51:17.609350900Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547098,551181,TO_TIMESTAMP('2023-09-29 18:51:16.948','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','workflow ref',20,TO_TIMESTAMP('2023-09-29 18:51:16.948','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 10 -> workflow ref.Workflow-Prozess
-- Column: AD_WF_Approval_Request.AD_WF_Process_ID
-- 2023-09-29T17:51:19.041901500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720698,0,547232,551181,620606,'F',TO_TIMESTAMP('2023-09-29 18:51:17.923','YYYY-MM-DD HH24:MI:SS.US'),100,'Actual Workflow Process Instance','Instance of a workflow execution','Y','N','N','Y','N','N','N','Workflow-Prozess',10,0,0,TO_TIMESTAMP('2023-09-29 18:51:17.923','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 10 -> workflow ref.Workflow-Aktivität
-- Column: AD_WF_Approval_Request.AD_WF_Activity_ID
-- 2023-09-29T17:51:20.562871900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720695,0,547232,551181,620607,'F',TO_TIMESTAMP('2023-09-29 18:51:19.344','YYYY-MM-DD HH24:MI:SS.US'),100,'Workflow-Aktivität','The Workflow Activity is the actual Workflow Node in a Workflow Process instance','Y','N','N','Y','N','N','N','Workflow-Aktivität',20,0,0,TO_TIMESTAMP('2023-09-29 18:51:19.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main
-- UI Column: 20
-- 2023-09-29T17:51:21.529002800Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547099,545825,TO_TIMESTAMP('2023-09-29 18:51:20.874','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-09-29 18:51:20.874','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20
-- UI Element Group: date & status
-- 2023-09-29T17:51:22.475622600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547099,551182,TO_TIMESTAMP('2023-09-29 18:51:21.828','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','date & status',10,TO_TIMESTAMP('2023-09-29 18:51:21.828','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Reihenfolge
-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-09-29T17:51:23.909721800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720699,0,547232,551182,620608,'F',TO_TIMESTAMP('2023-09-29 18:51:22.775','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','Y','N','N','Reihenfolge',5,30,0,TO_TIMESTAMP('2023-09-29 18:51:22.775','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Request Date
-- Column: AD_WF_Approval_Request.DateRequest
-- 2023-09-29T17:51:25.191343600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720697,0,547232,551182,620609,'F',TO_TIMESTAMP('2023-09-29 18:51:24.21','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','Y','N','N','Request Date',10,60,0,TO_TIMESTAMP('2023-09-29 18:51:24.21','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Datum Antwort
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-09-29T17:51:26.452306300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720696,0,547232,551182,620610,'F',TO_TIMESTAMP('2023-09-29 18:51:25.494','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum der Antwort','Datum der Antwort','Y','N','N','Y','Y','N','N','Datum Antwort',20,70,0,TO_TIMESTAMP('2023-09-29 18:51:25.494','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Ansprechpartner
-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-09-29T17:51:27.721047200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720691,0,547232,551182,620611,'F',TO_TIMESTAMP('2023-09-29 18:51:26.753','YYYY-MM-DD HH24:MI:SS.US'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','Y','N','N','Ansprechpartner',30,40,0,TO_TIMESTAMP('2023-09-29 18:51:26.753','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Status
-- Column: AD_WF_Approval_Request.Status
-- 2023-09-29T17:51:28.995759700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720692,0,547232,551182,620612,'F',TO_TIMESTAMP('2023-09-29 18:51:28.024','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','N','Y','Y','N','N','Status',40,50,0,TO_TIMESTAMP('2023-09-29 18:51:28.024','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: Meine Zulassungen
-- Action Type: W
-- Window: Meine Zulassungen(541736,D)
-- 2023-09-29T17:56:13.164610400Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582728,542116,0,541736,TO_TIMESTAMP('2023-09-29 18:56:12.064','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Meine Zulassungen','Y','N','N','N','N','Meine Zulassungen',TO_TIMESTAMP('2023-09-29 18:56:12.064','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T17:56:13.244367500Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542116 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-09-29T17:56:13.316112500Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542116, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542116)
;

-- 2023-09-29T17:56:13.404456400Z
/* DDL */  select update_menu_translation_from_ad_element(582728) 
;

-- Reordering children of `System`
-- Node name: `Costing (Freight etc)`
-- 2023-09-29T17:57:28.565343500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542051 AND AD_Tree_ID=10
;

-- Node name: `External system config Leich + Mehl (ExternalSystem_Config_LeichMehl)`
-- 2023-09-29T17:57:28.643157500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541966 AND AD_Tree_ID=10
;

-- Node name: `External system config eBay (ExternalSystem_Config_Ebay)`
-- 2023-09-29T17:57:28.719079500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541925 AND AD_Tree_ID=10
;

-- Node name: `API Audit`
-- 2023-09-29T17:57:28.796596800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `Externe Systeme authentifizieren (de.metas.externalsystem.externalservice.authorization.SendAuthTokenProcess)`
-- 2023-09-29T17:57:28.872925600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541993 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2023-09-29T17:57:28.947023700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2023-09-29T17:57:29.022158600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2023-09-29T17:57:29.098530100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2023-09-29T17:57:29.175264800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2023-09-29T17:57:29.251676Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2023-09-29T17:57:29.334391600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2023-09-29T17:57:29.409865900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2023-09-29T17:57:29.485804600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2023-09-29T17:57:29.564396900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2023-09-29T17:57:29.641425Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2023-09-29T17:57:29.718952400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2023-09-29T17:57:29.797367500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2023-09-29T17:57:29.874315300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2023-09-29T17:57:29.949339700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2023-09-29T17:57:30.027384600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2023-09-29T17:57:30.105040300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2023-09-29T17:57:30.182647500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2023-09-29T17:57:30.258086900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2023-09-29T17:57:30.334275Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2023-09-29T17:57:30.410033500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2023-09-29T17:57:30.486704700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2023-09-29T17:57:30.562713500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2023-09-29T17:57:30.639330300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2023-09-29T17:57:30.716644200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2023-09-29T17:57:30.794384400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2023-09-29T17:57:30.872189400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2023-09-29T17:57:30.948583600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2023-09-29T17:57:31.024673700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2023-09-29T17:57:31.101803200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Configuration (AD_Zebra_Config)`
-- 2023-09-29T17:57:31.177332400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2023-09-29T17:57:31.252450400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2023-09-29T17:57:31.327169400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2023-09-29T17:57:31.402648100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2023-09-29T17:57:31.477768900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2023-09-29T17:57:31.553062900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2023-09-29T17:57:31.628572200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2023-09-29T17:57:31.705396700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2023-09-29T17:57:31.782922800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV Missing Counter Documents (RV_Missing_Counter_Documents)`
-- 2023-09-29T17:57:31.857997Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2023-09-29T17:57:31.935054900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2023-09-29T17:57:32.011674400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2023-09-29T17:57:32.087531Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2023-09-29T17:57:32.164537400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2023-09-29T17:57:32.240583600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2023-09-29T17:57:32.317482800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2023-09-29T17:57:32.392681500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2023-09-29T17:57:32.468425800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2023-09-29T17:57:32.545529800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2023-09-29T17:57:32.620402600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2023-09-29T17:57:32.695692900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Account (I_ElementValue)`
-- 2023-09-29T17:57:32.771471900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2023-09-29T17:57:32.847431300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2023-09-29T17:57:32.924958900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2023-09-29T17:57:32.999962900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2023-09-29T17:57:33.076632600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2023-09-29T17:57:33.153573200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2023-09-29T17:57:33.230355800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2023-09-29T17:57:33.305108600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2023-09-29T17:57:33.381421100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2023-09-29T17:57:33.457601100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2023-09-29T17:57:33.534508Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2023-09-29T17:57:33.611568900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-09-29T17:57:33.690182200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-09-29T17:57:33.765457600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-09-29T17:57:33.842048800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2023-09-29T17:57:33.917543100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2023-09-29T17:57:33.992656300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2023-09-29T17:57:34.068249800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Role Access Update (org.compiere.process.RoleAccessUpdate)`
-- 2023-09-29T17:57:34.144022900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2023-09-29T17:57:34.218241600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2023-09-29T17:57:34.293855900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Change System Base Language (de.metas.process.ExecuteUpdateSQL)`
-- 2023-09-29T17:57:34.369347300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541973 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Configuration (GeocodingConfig)`
-- 2023-09-29T17:57:34.445286800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2023-09-29T17:57:34.521743400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2023-09-29T17:57:34.598766200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `remove_documents_between_dates (de.metas.process.ExecuteUpdateSQL)`
-- 2023-09-29T17:57:34.675155Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541926 AND AD_Tree_ID=10
;

-- Node name: `Letters (C_Letter)`
-- 2023-09-29T17:57:34.752205900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540403 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2023-09-29T17:57:34.828425700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Import Invoice Candidate (I_Invoice_Candidate)`
-- 2023-09-29T17:57:34.904122600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541996 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2023-09-29T17:57:34.980045100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `RabbitMQ Message Audit (RabbitMQ_Message_Audit)`
-- 2023-09-29T17:57:35.056432900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541910 AND AD_Tree_ID=10
;

-- Node name: `External system config SAP (ExternalSystem_Config_SAP)`
-- 2023-09-29T17:57:35.131269600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542022 AND AD_Tree_ID=10
;

-- Node name: `Approval Requests (AD_WF_Approval_Request)`
-- 2023-09-29T17:57:35.209282600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542110 AND AD_Tree_ID=10
;

-- Node name: `Meine Zulassungen`
-- 2023-09-29T17:57:35.284411500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542116 AND AD_Tree_ID=10
;

-- Tab: Meine Zulassungen(541736,D) -> Approval Requests
-- Table: AD_WF_Approval_Request
-- 2023-09-29T18:08:47.059170500Z
UPDATE AD_Tab SET WhereClause='AD_WF_Approval_Request.AD_User_ID=@#AD_User_ID@',Updated=TO_TIMESTAMP('2023-09-29 19:08:47.059','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547232
;

-- 2023-09-29T18:28:58.678631700Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582729,0,TO_TIMESTAMP('2023-09-29 19:28:57.785','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Dokument Typ','Dokument Typ',TO_TIMESTAMP('2023-09-29 19:28:57.785','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T18:28:58.758700900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582729 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-09-29T18:29:23.448606Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Document type', PrintName='Document type',Updated=TO_TIMESTAMP('2023-09-29 19:29:23.448','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582729 AND AD_Language='en_US'
;

-- 2023-09-29T18:29:23.600363100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582729,'en_US') 
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Dokument Typ
-- Column: AD_WF_Approval_Request.DocBaseType
-- 2023-09-29T18:29:36.478510300Z
UPDATE AD_Field SET AD_Name_ID=582729, Description=NULL, Help=NULL, Name='Dokument Typ',Updated=TO_TIMESTAMP('2023-09-29 19:29:36.478','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720694
;

-- 2023-09-29T18:29:36.550498100Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Dokument Typ' WHERE AD_Field_ID=720694 AND AD_Language='de_DE'
;

-- 2023-09-29T18:29:36.640649200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582729) 
;

-- 2023-09-29T18:29:36.718955900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720694
;

-- 2023-09-29T18:29:36.788647400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720694)
;

-- 2023-09-29T18:30:45.428469200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582730,0,TO_TIMESTAMP('2023-09-29 19:30:44.626','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Belege Nr.','Belege Nr.',TO_TIMESTAMP('2023-09-29 19:30:44.626','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T18:30:45.499335700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582730 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-09-29T18:31:31.692892200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receipts No.', PrintName='Receipts No.',Updated=TO_TIMESTAMP('2023-09-29 19:31:31.692','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582730 AND AD_Language='en_US'
;

-- 2023-09-29T18:31:31.848221100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582730,'en_US') 
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Belege Nr.
-- Column: AD_WF_Approval_Request.DocumentNo
-- 2023-09-29T18:32:25.008092700Z
UPDATE AD_Field SET AD_Name_ID=582730, Description=NULL, Help=NULL, Name='Belege Nr.',Updated=TO_TIMESTAMP('2023-09-29 19:32:25.008','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720693
;

-- 2023-09-29T18:32:25.087893400Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Belege Nr.' WHERE AD_Field_ID=720693 AND AD_Language='de_DE'
;

-- 2023-09-29T18:32:25.165690Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582730) 
;

-- 2023-09-29T18:32:25.244086600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720693
;

-- 2023-09-29T18:32:25.317919400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720693)
;

-- 2023-09-29T18:41:03.923446500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582731,0,TO_TIMESTAMP('2023-09-29 19:41:03.133','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Anforderer','Anforderer',TO_TIMESTAMP('2023-09-29 19:41:03.133','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T18:41:03.998532800Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582731 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-09-29T18:41:29.330382300Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Requestor', PrintName='Requestor',Updated=TO_TIMESTAMP('2023-09-29 19:41:29.33','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582731 AND AD_Language='en_US'
;

-- 2023-09-29T18:41:29.487730200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582731,'en_US') 
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Anforderer
-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-09-29T18:41:48.268164800Z
UPDATE AD_Field SET AD_Name_ID=582731, Description=NULL, Help=NULL, Name='Anforderer',Updated=TO_TIMESTAMP('2023-09-29 19:41:48.268','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720691
;

-- 2023-09-29T18:41:48.338369200Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Anforderer' WHERE AD_Field_ID=720691 AND AD_Language='de_DE'
;

-- 2023-09-29T18:41:48.422053600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582731) 
;

-- 2023-09-29T18:41:48.498165100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720691
;

-- 2023-09-29T18:41:48.571602Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720691)
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Freigabedatum
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-09-29T18:43:00.463759400Z
UPDATE AD_Field SET AD_Name_ID=581602, Description=NULL, Help=NULL, Name='Freigabedatum',Updated=TO_TIMESTAMP('2023-09-29 19:43:00.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720696
;

-- 2023-09-29T18:43:00.540152600Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Freigabedatum' WHERE AD_Field_ID=720696 AND AD_Language='de_DE'
;

-- 2023-09-29T18:43:00.615813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581602) 
;

-- 2023-09-29T18:43:00.695358200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720696
;

-- 2023-09-29T18:43:00.770221500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720696)
;

-- Element: ClearanceDate
-- 2023-09-29T18:43:37.089889200Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-29 19:43:37.089','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=581602 AND AD_Language='de_DE'
;

-- 2023-09-29T18:43:37.238276600Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581602,'de_DE') 
;

-- 2023-09-29T18:43:37.317941600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581602,'de_DE') 
;

-- Element: DateRequest
-- 2023-09-29T18:45:46.687636800Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Anfragedatum', PrintName='Anfragedatum',Updated=TO_TIMESTAMP('2023-09-29 19:45:46.687','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582676 AND AD_Language='de_DE'
;

-- 2023-09-29T18:45:46.767644400Z
UPDATE AD_Element SET Name='Anfragedatum', PrintName='Anfragedatum' WHERE AD_Element_ID=582676
;

-- 2023-09-29T18:45:58.344952100Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582676,'de_DE') 
;

-- 2023-09-29T18:45:58.417761700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582676,'de_DE') 
;

-- 2023-09-29T18:48:29.878073800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582732,0,TO_TIMESTAMP('2023-09-29 19:48:28.988','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Freigabestatus','Freigabestatus',TO_TIMESTAMP('2023-09-29 19:48:28.988','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T18:48:29.957218600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582732 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-09-29T18:48:54.217259500Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Release status', PrintName='Release status',Updated=TO_TIMESTAMP('2023-09-29 19:48:54.217','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582732 AND AD_Language='en_US'
;

-- 2023-09-29T18:48:54.367171500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582732,'en_US') 
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Freigabestatus
-- Column: AD_WF_Approval_Request.Status
-- 2023-09-29T18:49:21.821734700Z
UPDATE AD_Field SET AD_Name_ID=582732, Description=NULL, Help=NULL, Name='Freigabestatus',Updated=TO_TIMESTAMP('2023-09-29 19:49:21.821','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720692
;

-- 2023-09-29T18:49:21.900874600Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Freigabestatus' WHERE AD_Field_ID=720692 AND AD_Language='de_DE'
;

-- 2023-09-29T18:49:21.978474400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582732) 
;

-- 2023-09-29T18:49:22.056720900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720692
;

-- 2023-09-29T18:49:22.131930400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720692)
;

-- Column: AD_WF_Approval_Request.C_Activity_ID
-- 2023-09-29T18:53:37.536124800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587487,1005,0,19,542364,'C_Activity_ID',TO_TIMESTAMP('2023-09-29 19:53:36.116','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Kostenstelle','D',0,10,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenstelle',0,0,TO_TIMESTAMP('2023-09-29 19:53:36.116','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-29T18:53:37.611048Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587487 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-29T18:53:51.195817200Z
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- 2023-09-29T18:54:41.310522700Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Approval_Request','ALTER TABLE public.AD_WF_Approval_Request ADD COLUMN C_Activity_ID NUMERIC(10)')
;

-- 2023-09-29T18:54:41.405964200Z
ALTER TABLE AD_WF_Approval_Request ADD CONSTRAINT CActivity_ADWFApprovalRequest FOREIGN KEY (C_Activity_ID) REFERENCES public.C_Activity DEFERRABLE INITIALLY DEFERRED
;

-- Column: AD_WF_Approval_Request.C_BPartner_Vendor_ID
-- 2023-09-29T19:02:46.554508500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587488,542647,0,30,192,542364,540268,'C_BPartner_Vendor_ID',TO_TIMESTAMP('2023-09-29 20:02:45.362','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferant',0,0,TO_TIMESTAMP('2023-09-29 20:02:45.362','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-29T19:02:46.633585500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587488 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-29T19:03:00.712152800Z
/* DDL */  select update_Column_Translation_From_AD_Element(542647) 
;

-- Column: AD_WF_Approval_Request.C_BPartner_Vendor_ID
-- 2023-09-29T19:15:19.577801800Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2023-09-29 20:15:19.577','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587488
;

-- 2023-09-29T19:15:55.829543Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Approval_Request','ALTER TABLE public.AD_WF_Approval_Request ADD COLUMN C_BPartner_Vendor_ID NUMERIC(10)')
;

-- 2023-09-29T19:15:55.916221600Z
ALTER TABLE AD_WF_Approval_Request ADD CONSTRAINT CBPartnerVendor_ADWFApprovalRequest FOREIGN KEY (C_BPartner_Vendor_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: AD_WF_Approval_Request.C_Project_ID
-- 2023-09-29T19:16:40.742831400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587489,208,0,19,542364,'C_Project_ID',TO_TIMESTAMP('2023-09-29 20:16:39.327','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Financial Project','D',0,10,'A Project allows you to track and control internal or external activities.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Projekt',0,0,TO_TIMESTAMP('2023-09-29 20:16:39.327','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-29T19:16:40.818188100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587489 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-29T19:16:51.850781600Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- 2023-09-29T19:17:43.468007800Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Approval_Request','ALTER TABLE public.AD_WF_Approval_Request ADD COLUMN C_Project_ID NUMERIC(10)')
;

-- 2023-09-29T19:17:43.547845800Z
ALTER TABLE AD_WF_Approval_Request ADD CONSTRAINT CProject_ADWFApprovalRequest FOREIGN KEY (C_Project_ID) REFERENCES public.C_Project DEFERRABLE INITIALLY DEFERRED
;

-- Column: AD_WF_Approval_Request.TotalAmt
-- 2023-09-29T19:18:23.394728500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587490,1539,0,12,542364,'TotalAmt',TO_TIMESTAMP('2023-09-29 20:18:22.417','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gesamtbetrag',0,0,TO_TIMESTAMP('2023-09-29 20:18:22.417','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-29T19:18:23.467873100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587490 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-29T19:18:31.760894200Z
/* DDL */  select update_Column_Translation_From_AD_Element(1539) 
;

-- Column: AD_WF_Approval_Request.TotalAmt
-- 2023-09-29T19:19:24.487273700Z
UPDATE AD_Column SET DefaultValue='0',Updated=TO_TIMESTAMP('2023-09-29 20:19:24.487','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587490
;

-- Column: AD_WF_Approval_Request.TotalAmt
-- 2023-09-29T19:19:40.117398Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-09-29 20:19:40.117','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587490
;

-- Column: AD_WF_Approval_Request.TotalAmt
-- 2023-09-29T19:19:57.358407600Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2023-09-29 20:19:57.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587490
;

-- 2023-09-29T19:20:17.424135200Z
/* DDL */ SELECT public.db_alter_table('AD_WF_Approval_Request','ALTER TABLE public.AD_WF_Approval_Request ADD COLUMN TotalAmt NUMERIC')
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Kostenstelle
-- Column: AD_WF_Approval_Request.C_Activity_ID
-- 2023-09-29T19:21:10.477666200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587487,720700,0,547232,TO_TIMESTAMP('2023-09-29 20:21:09.362','YYYY-MM-DD HH24:MI:SS.US'),100,'Kostenstelle',10,'D','Erfassung der zugehörigen Kostenstelle','Y','N','N','N','N','N','N','N','Kostenstelle',TO_TIMESTAMP('2023-09-29 20:21:09.362','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T19:21:10.557210700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720700 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T19:21:10.637061200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2023-09-29T19:21:10.717022Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720700
;

-- 2023-09-29T19:21:10.797025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720700)
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Lieferant
-- Column: AD_WF_Approval_Request.C_BPartner_Vendor_ID
-- 2023-09-29T19:21:11.977540800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587488,720701,0,547232,TO_TIMESTAMP('2023-09-29 20:21:10.948','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Lieferant',TO_TIMESTAMP('2023-09-29 20:21:10.948','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T19:21:12.057589200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720701 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T19:21:12.127256100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542647) 
;

-- 2023-09-29T19:21:12.207085500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720701
;

-- 2023-09-29T19:21:12.282940400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720701)
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Projekt
-- Column: AD_WF_Approval_Request.C_Project_ID
-- 2023-09-29T19:21:13.536796500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587489,720702,0,547232,TO_TIMESTAMP('2023-09-29 20:21:12.438','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project',10,'D','A Project allows you to track and control internal or external activities.','Y','N','N','N','N','N','N','N','Projekt',TO_TIMESTAMP('2023-09-29 20:21:12.438','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T19:21:13.608556100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720702 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T19:21:13.688287200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208) 
;

-- 2023-09-29T19:21:13.768217100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720702
;

-- 2023-09-29T19:21:13.847283300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720702)
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Gesamtbetrag
-- Column: AD_WF_Approval_Request.TotalAmt
-- 2023-09-29T19:21:15.097291100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587490,720703,0,547232,TO_TIMESTAMP('2023-09-29 20:21:14.078','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Gesamtbetrag',TO_TIMESTAMP('2023-09-29 20:21:14.078','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T19:21:15.177183400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720703 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-29T19:21:15.247253900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1539) 
;

-- 2023-09-29T19:21:15.327276800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720703
;

-- 2023-09-29T19:21:15.405071100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720703)
;

-- 2023-09-29T19:24:22.056462200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582733,0,TO_TIMESTAMP('2023-09-29 20:24:21.224','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Gesamt netto','Gesamt netto',TO_TIMESTAMP('2023-09-29 20:24:21.224','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-29T19:24:22.126507700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582733 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-09-29T19:24:42.716485600Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Total net', PrintName='Total net',Updated=TO_TIMESTAMP('2023-09-29 20:24:42.716','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582733 AND AD_Language='en_US'
;

-- 2023-09-29T19:24:42.866768600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582733,'en_US') 
;

-- Field: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> Gesamt netto
-- Column: AD_WF_Approval_Request.TotalAmt
-- 2023-09-29T19:24:55.326580100Z
UPDATE AD_Field SET AD_Name_ID=582733, Description=NULL, Help=NULL, Name='Gesamt netto',Updated=TO_TIMESTAMP('2023-09-29 20:24:55.326','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720703
;

-- 2023-09-29T19:24:55.407077700Z
UPDATE AD_Field_Trl trl SET Name='Gesamt netto' WHERE AD_Field_ID=720703 AND AD_Language='de_DE'
;

-- 2023-09-29T19:24:55.476708300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582733) 
;

-- 2023-09-29T19:24:55.556496100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720703
;

-- 2023-09-29T19:24:55.636485300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720703)
;

-- UI Column: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20
-- UI Element Group: project
-- 2023-09-29T19:26:35.891874700Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547099,551183,TO_TIMESTAMP('2023-09-29 20:26:34.996','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','project',20,TO_TIMESTAMP('2023-09-29 20:26:34.996','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> project.Lieferant
-- Column: AD_WF_Approval_Request.C_BPartner_Vendor_ID
-- 2023-09-29T19:27:20.743847300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720701,0,547232,551183,620613,'F',TO_TIMESTAMP('2023-09-29 20:27:19.794','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lieferant',10,0,0,TO_TIMESTAMP('2023-09-29 20:27:19.794','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> project.Kostenstelle
-- Column: AD_WF_Approval_Request.C_Activity_ID
-- 2023-09-29T19:28:11.022715700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720700,0,547232,551183,620614,'F',TO_TIMESTAMP('2023-09-29 20:28:10.055','YYYY-MM-DD HH24:MI:SS.US'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',20,0,0,TO_TIMESTAMP('2023-09-29 20:28:10.055','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> project.Projekt
-- Column: AD_WF_Approval_Request.C_Project_ID
-- 2023-09-29T19:28:28.335815900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720702,0,547232,551183,620615,'F',TO_TIMESTAMP('2023-09-29 20:28:27.365','YYYY-MM-DD HH24:MI:SS.US'),100,'Financial Project','A Project allows you to track and control internal or external activities.','Y','N','N','Y','N','N','N',0,'Projekt',30,0,0,TO_TIMESTAMP('2023-09-29 20:28:27.365','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> project.Gesamt netto
-- Column: AD_WF_Approval_Request.TotalAmt
-- 2023-09-29T19:28:52.925481600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720703,0,547232,551183,620616,'F',TO_TIMESTAMP('2023-09-29 20:28:51.977','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Gesamt netto',40,0,0,TO_TIMESTAMP('2023-09-29 20:28:51.977','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Reihenfolge
-- Column: AD_WF_Approval_Request.SeqNo
-- 2023-09-29T19:29:47.278613700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-09-29 20:29:47.278','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620608
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Ansprechpartner
-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-09-29T19:29:47.741795700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-09-29 20:29:47.741','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620611
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Status
-- Column: AD_WF_Approval_Request.Status
-- 2023-09-29T19:29:48.205687300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-09-29 20:29:48.205','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620612
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Request Date
-- Column: AD_WF_Approval_Request.DateRequest
-- 2023-09-29T19:29:48.655626200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-29 20:29:48.655','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620609
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Datum Antwort
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-09-29T19:29:49.115577900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-29 20:29:49.115','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620610
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> project.Lieferant
-- Column: AD_WF_Approval_Request.C_BPartner_Vendor_ID
-- 2023-09-29T19:31:01.115531500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-09-29 20:31:01.115','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620613
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Ansprechpartner
-- Column: AD_WF_Approval_Request.AD_User_ID
-- 2023-09-29T19:31:01.565353800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-09-29 20:31:01.565','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620611
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> project.Kostenstelle
-- Column: AD_WF_Approval_Request.C_Activity_ID
-- 2023-09-29T19:31:02.025433600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-29 20:31:02.025','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620614
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> project.Projekt
-- Column: AD_WF_Approval_Request.C_Project_ID
-- 2023-09-29T19:31:02.475402400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-29 20:31:02.475','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620615
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Status
-- Column: AD_WF_Approval_Request.Status
-- 2023-09-29T19:31:02.935454600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-29 20:31:02.935','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620612
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> project.Gesamt netto
-- Column: AD_WF_Approval_Request.TotalAmt
-- 2023-09-29T19:31:03.395558800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-09-29 20:31:03.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620616
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Request Date
-- Column: AD_WF_Approval_Request.DateRequest
-- 2023-09-29T19:31:03.845496900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-09-29 20:31:03.845','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620609
;

-- UI Element: Meine Zulassungen(541736,D) -> Approval Requests(547232,D) -> main -> 20 -> date & status.Datum Antwort
-- Column: AD_WF_Approval_Request.DateResponse
-- 2023-09-29T19:31:04.320433100Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-09-29 20:31:04.32','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620610
;

