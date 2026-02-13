-- Run mode: SWING_CLIENT

-- 2026-02-12T15:05:36.924Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584524,0,TO_TIMESTAMP('2026-02-12 15:05:36.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Distributionsdisposition_OLD','Distributionsdisposition',TO_TIMESTAMP('2026-02-12 15:05:36.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:05:36.948Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584524 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-02-12T15:07:48.532Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584525,0,TO_TIMESTAMP('2026-02-12 15:07:48.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Distributionsdisposition','Distributionsdisposition',TO_TIMESTAMP('2026-02-12 15:07:48.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:07:48.556Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584525 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-02-12T15:08:59.834Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Distribution Order Candidate', PrintName='Distribution Order Candidate',Updated=TO_TIMESTAMP('2026-02-12 15:08:59.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584525 AND AD_Language='en_US'
;

-- 2026-02-12T15:08:59.862Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-12T15:09:35.976Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584525,'en_US')
;

-- Element: null
-- 2026-02-12T15:09:39.425Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 15:09:39.425000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584525 AND AD_Language='de_DE'
;

-- 2026-02-12T15:09:39.479Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584525,'de_DE')
;

-- 2026-02-12T15:09:39.504Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584525,'de_DE')
;

-- Element: null
-- 2026-02-12T15:11:05.731Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 15:11:05.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584525 AND AD_Language='de_CH'
;

-- 2026-02-12T15:11:05.806Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584525,'de_CH')
;

-- Element: null
-- 2026-02-12T15:11:26.097Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Distribution Order Candidate_OLD',Updated=TO_TIMESTAMP('2026-02-12 15:11:26.097000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584524 AND AD_Language='en_US'
;

-- 2026-02-12T15:11:26.121Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-12T15:11:55.204Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584524,'en_US')
;

-- Element: null
-- 2026-02-12T15:12:00.547Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 15:12:00.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584524 AND AD_Language='de_DE'
;

-- 2026-02-12T15:12:00.597Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584524,'de_DE')
;

-- 2026-02-12T15:12:00.627Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584524,'de_DE')
;

-- Element: null
-- 2026-02-12T15:12:14.165Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 15:12:14.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584524 AND AD_Language='de_CH'
;

-- 2026-02-12T15:12:14.245Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584524,'de_CH')
;

-- Window: Distributionsdisposition_OLD, InternalName=ddOrderCandidate
-- 2026-02-12T15:13:09.640Z
UPDATE AD_Window SET AD_Element_ID=584524, Description=NULL, Help=NULL, Name='Distributionsdisposition_OLD',Updated=TO_TIMESTAMP('2026-02-12 15:13:09.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541807
;

-- 2026-02-12T15:13:09.679Z
UPDATE AD_Window_Trl trl SET Name='Distributionsdisposition_OLD' WHERE AD_Window_ID=541807 AND AD_Language='de_DE'
;

-- Name: Distributionsdisposition_OLD
-- Action Type: W
-- Window: Distributionsdisposition_OLD(541807,EE01)
-- 2026-02-12T15:13:37.618Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Distributionsdisposition_OLD',Updated=TO_TIMESTAMP('2026-02-12 15:13:37.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=542164
;

-- 2026-02-12T15:13:37.644Z
UPDATE AD_Menu_Trl trl SET Name='Distributionsdisposition_OLD' WHERE AD_Menu_ID=542164 AND AD_Language='de_DE'
;

-- 2026-02-12T15:13:53.977Z
/* DDL */  select update_window_translation_from_ad_element(584524)
;

-- 2026-02-12T15:13:54.013Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541807
;

-- 2026-02-12T15:13:54.050Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541807)
;

-- Window: Distributionsdisposition, InternalName=ddOrderCandidate NEW
-- 2026-02-12T15:21:20.763Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584525,0,542090,TO_TIMESTAMP('2026-02-12 15:21:20.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','ddOrderCandidate NEW','Y','N','N','N','N','N','N','Y','Distributionsdisposition','N',TO_TIMESTAMP('2026-02-12 15:21:20.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2026-02-12T15:21:20.796Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542090 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-02-12T15:21:20.846Z
/* DDL */  select update_window_translation_from_ad_element(584525)
;

-- 2026-02-12T15:21:20.870Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542090
;

-- 2026-02-12T15:21:20.907Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542090)
;

-- Window: Distributionsdisposition, InternalName=ddOrderCandidate NEW
-- 2026-02-12T15:25:21.242Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='EE01', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='N', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=541807, WindowType='M', WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2026-02-12 15:25:21.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542090
;

-- Tab: Distributionsdisposition(542090,EE01) -> Distributionsdisposition
-- Table: DD_Order_Candidate
-- 2026-02-12T15:25:21.749Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583179,0,549050,542424,542090,'Y',TO_TIMESTAMP('2026-02-12 15:25:21.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EE01','N','N','A','DD_Order_Candidate','Y','N','Y','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N',0,'Distributionsdisposition','N',10,0,TO_TIMESTAMP('2026-02-12 15:25:21.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:21.771Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549050 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-12T15:25:21.821Z
/* DDL */  select update_tab_translation_from_ad_element(583179)
;

-- 2026-02-12T15:25:21.847Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549050)
;

-- 2026-02-12T15:25:21.912Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 549050
;

-- 2026-02-12T15:25:21.945Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 549050, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 547559
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Mandant
-- Column: DD_Order_Candidate.AD_Client_ID
-- 2026-02-12T15:25:22.695Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588716,773748,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:22.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',10,1,1,TO_TIMESTAMP('2026-02-12 15:25:22.278000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:22.717Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773748 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:22.761Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-12T15:25:22.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773748
;

-- 2026-02-12T15:25:22.876Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773748)
;

-- 2026-02-12T15:25:22.946Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773748
;

-- 2026-02-12T15:25:22.969Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773748, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729067
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Organisation
-- Column: DD_Order_Candidate.AD_Org_ID
-- 2026-02-12T15:25:23.370Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588717,773749,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:22.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Organisation',20,1,1,TO_TIMESTAMP('2026-02-12 15:25:22.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:23.397Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773749 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:23.423Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-12T15:25:23.531Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773749
;

-- 2026-02-12T15:25:23.555Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773749)
;

-- 2026-02-12T15:25:23.605Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773749
;

-- 2026-02-12T15:25:23.628Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773749, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729068
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Aktiv
-- Column: DD_Order_Candidate.IsActive
-- 2026-02-12T15:25:24.029Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588720,773750,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:23.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Aktiv',30,1,1,TO_TIMESTAMP('2026-02-12 15:25:23.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:24.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773750 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:24.080Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-12T15:25:24.164Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773750
;

-- 2026-02-12T15:25:24.187Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773750)
;

-- 2026-02-12T15:25:24.257Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773750
;

-- 2026-02-12T15:25:24.282Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773750, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729069
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Distributionsdisposition
-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2026-02-12T15:25:24.653Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588723,773751,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:24.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Distributionsdisposition',40,1,1,TO_TIMESTAMP('2026-02-12 15:25:24.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:24.680Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773751 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:24.716Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583179)
;

-- 2026-02-12T15:25:24.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773751
;

-- 2026-02-12T15:25:24.775Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773751)
;

-- 2026-02-12T15:25:24.840Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773751
;

-- 2026-02-12T15:25:24.867Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773751, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729070
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- 2026-02-12T15:25:25.286Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588740,773752,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:24.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags',7,'EE01','Bezeichnet das Datum, an dem die Ware bestellt wurde.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Auftragsdatum',50,1,1,TO_TIMESTAMP('2026-02-12 15:25:24.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:25.317Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773752 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:25.344Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(268)
;

-- 2026-02-12T15:25:25.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773752
;

-- 2026-02-12T15:25:25.441Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773752)
;

-- 2026-02-12T15:25:25.494Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773752
;

-- 2026-02-12T15:25:25.525Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773752, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729071
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Supply Date
-- Column: DD_Order_Candidate.SupplyDate
-- 2026-02-12T15:25:25.898Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588742,773753,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:25.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Supply Date',60,1,1,TO_TIMESTAMP('2026-02-12 15:25:25.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:25.929Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773753 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:25.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583210)
;

-- 2026-02-12T15:25:26.005Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773753
;

-- 2026-02-12T15:25:26.031Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773753)
;

-- 2026-02-12T15:25:26.102Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773753
;

-- 2026-02-12T15:25:26.132Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773753, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729072
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- 2026-02-12T15:25:26.541Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588766,773754,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:26.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Lager ab',70,1,1,TO_TIMESTAMP('2026-02-12 15:25:26.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:26.570Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773754 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:26.610Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577736)
;

-- 2026-02-12T15:25:26.636Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773754
;

-- 2026-02-12T15:25:26.671Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773754)
;

-- 2026-02-12T15:25:26.726Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773754
;

-- 2026-02-12T15:25:26.751Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773754, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729073
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2026-02-12T15:25:27.148Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588768,773755,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:26.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Lager Nach',80,1,1,TO_TIMESTAMP('2026-02-12 15:25:26.786000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:27.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773755 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:27.221Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543474)
;

-- 2026-02-12T15:25:27.247Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773755
;

-- 2026-02-12T15:25:27.281Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773755)
;

-- 2026-02-12T15:25:27.336Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773755
;

-- 2026-02-12T15:25:27.389Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773755, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729074
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- 2026-02-12T15:25:27.757Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588774,773756,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:27.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Produktionsstätte zu',90,1,1,TO_TIMESTAMP('2026-02-12 15:25:27.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:27.798Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773756 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:27.827Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583181)
;

-- 2026-02-12T15:25:27.854Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773756
;

-- 2026-02-12T15:25:27.879Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773756)
;

-- 2026-02-12T15:25:27.941Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773756
;

-- 2026-02-12T15:25:27.964Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773756, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729075
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Produktplanung
-- Column: DD_Order_Candidate.PP_Product_Planning_ID
-- 2026-02-12T15:25:28.280Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588775,773757,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:27.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Produktplanung',100,1,1,TO_TIMESTAMP('2026-02-12 15:25:27.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:28.307Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773757 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:28.341Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53268)
;

-- 2026-02-12T15:25:28.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773757
;

-- 2026-02-12T15:25:28.394Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773757)
;

-- 2026-02-12T15:25:28.459Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773757
;

-- 2026-02-12T15:25:28.482Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773757, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729076
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- 2026-02-12T15:25:28.841Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588777,773758,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:28.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'EE01','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Verarbeitet',110,1,1,TO_TIMESTAMP('2026-02-12 15:25:28.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:28.870Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773758 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:28.903Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2026-02-12T15:25:28.932Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773758
;

-- 2026-02-12T15:25:28.959Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773758)
;

-- 2026-02-12T15:25:29.016Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773758
;

-- 2026-02-12T15:25:29.050Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773758, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729077
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- 2026-02-12T15:25:29.406Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588810,773759,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:29.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Produktionsstätte ab',120,1,1,TO_TIMESTAMP('2026-02-12 15:25:29.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:29.443Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773759 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:29.474Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542485)
;

-- 2026-02-12T15:25:29.501Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773759
;

-- 2026-02-12T15:25:29.533Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773759)
;

-- 2026-02-12T15:25:29.584Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773759
;

-- 2026-02-12T15:25:29.611Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773759, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729078
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Menge
-- Column: DD_Order_Candidate.QtyEntered
-- 2026-02-12T15:25:29.975Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588812,773760,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:29.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit',22,'EE01','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt',0,'Y','N','N','N','N','N','N','N','N','Y','N','Menge',130,1,1,TO_TIMESTAMP('2026-02-12 15:25:29.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:30.008Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773760 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:30.034Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2589)
;

-- 2026-02-12T15:25:30.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773760
;

-- 2026-02-12T15:25:30.083Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773760)
;

-- 2026-02-12T15:25:30.146Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773760
;

-- 2026-02-12T15:25:30.176Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773760, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729079
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Menge TU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- 2026-02-12T15:25:30.493Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588813,773761,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:30.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Menge TU',140,1,1,TO_TIMESTAMP('2026-02-12 15:25:30.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:30.520Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773761 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:30.568Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542397)
;

-- 2026-02-12T15:25:30.595Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773761
;

-- 2026-02-12T15:25:30.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773761)
;

-- 2026-02-12T15:25:30.721Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773761
;

-- 2026-02-12T15:25:30.747Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773761, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729080
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- 2026-02-12T15:25:31.093Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588815,773762,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:30.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt',22,'EE01','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Bestellt/ Beauftragt',150,1,1,TO_TIMESTAMP('2026-02-12 15:25:30.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:31.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773762 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:31.164Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(531)
;

-- 2026-02-12T15:25:31.200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773762
;

-- 2026-02-12T15:25:31.233Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773762)
;

-- 2026-02-12T15:25:31.306Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773762
;

-- 2026-02-12T15:25:31.341Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773762, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729081
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Network Distribution
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- 2026-02-12T15:25:31.759Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588821,773763,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:31.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Network Distribution',160,1,1,TO_TIMESTAMP('2026-02-12 15:25:31.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:31.797Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773763 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:31.830Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53340)
;

-- 2026-02-12T15:25:31.860Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773763
;

-- 2026-02-12T15:25:31.897Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773763)
;

-- 2026-02-12T15:25:31.953Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773763
;

-- 2026-02-12T15:25:32.004Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773763, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729082
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- 2026-02-12T15:25:32.349Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588822,773764,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:32.037000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Network Distribution Line',170,1,1,TO_TIMESTAMP('2026-02-12 15:25:32.037000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:32.392Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773764 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:32.415Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53341)
;

-- 2026-02-12T15:25:32.441Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773764
;

-- 2026-02-12T15:25:32.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773764)
;

-- 2026-02-12T15:25:32.565Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773764
;

-- 2026-02-12T15:25:32.599Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773764, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729083
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- 2026-02-12T15:25:32.963Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588823,773765,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:32.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','N','N','N','N','N','N','N','Y','N','Maßeinheit',180,1,1,TO_TIMESTAMP('2026-02-12 15:25:32.639000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:33.004Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773765 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:33.028Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-02-12T15:25:33.070Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773765
;

-- 2026-02-12T15:25:33.112Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773765)
;

-- 2026-02-12T15:25:33.159Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773765
;

-- 2026-02-12T15:25:33.199Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773765, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729084
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Von Lagerort
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- 2026-02-12T15:25:33.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588824,773766,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:33.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'EE01','',0,'Y','N','N','N','N','N','N','N','N','Y','N','Von Lagerort',190,1,1,TO_TIMESTAMP('2026-02-12 15:25:33.282000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:33.654Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773766 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:33.676Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583180)
;

-- 2026-02-12T15:25:33.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773766
;

-- 2026-02-12T15:25:33.735Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773766)
;

-- 2026-02-12T15:25:33.798Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773766
;

-- 2026-02-12T15:25:33.830Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773766, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729085
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Lagerort An
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- 2026-02-12T15:25:34.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588825,773767,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:33.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort, an den Bestand bewegt wird',10,'EE01','"Lagerort An" bezeichnet den Lagerort, auf den ein Warenbestand bewegt wird.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Lagerort An',200,1,1,TO_TIMESTAMP('2026-02-12 15:25:33.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:34.220Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773767 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:34.245Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1029)
;

-- 2026-02-12T15:25:34.280Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773767
;

-- 2026-02-12T15:25:34.332Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773767)
;

-- 2026-02-12T15:25:34.403Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773767
;

-- 2026-02-12T15:25:34.463Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773767, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729086
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- 2026-02-12T15:25:34.814Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588826,773768,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:34.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'EE01','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Produkt',210,1,1,TO_TIMESTAMP('2026-02-12 15:25:34.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:34.850Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773768 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:34.876Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-02-12T15:25:34.911Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773768
;

-- 2026-02-12T15:25:34.938Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773768)
;

-- 2026-02-12T15:25:35.007Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773768
;

-- 2026-02-12T15:25:35.040Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773768, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729087
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Packvorschrift
-- Column: DD_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2026-02-12T15:25:35.386Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588827,773769,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:35.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Packvorschrift',220,1,1,TO_TIMESTAMP('2026-02-12 15:25:35.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:35.444Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773769 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:35.471Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132)
;

-- 2026-02-12T15:25:35.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773769
;

-- 2026-02-12T15:25:35.527Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773769)
;

-- 2026-02-12T15:25:35.574Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773769
;

-- 2026-02-12T15:25:35.598Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773769, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729088
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2026-02-12T15:25:35.938Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588828,773770,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:35.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Forward Manufacturing Order',230,1,1,TO_TIMESTAMP('2026-02-12 15:25:35.622000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:35.963Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:36.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583182)
;

-- 2026-02-12T15:25:36.060Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773770
;

-- 2026-02-12T15:25:36.096Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773770)
;

-- 2026-02-12T15:25:36.142Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773770
;

-- 2026-02-12T15:25:36.166Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773770, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729089
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2026-02-12T15:25:36.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588829,773771,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:36.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'@Forward_PP_Order_BOMLine_ID/0@>0','EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Forward Manufacturing Order BOM Line',240,1,1,TO_TIMESTAMP('2026-02-12 15:25:36.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:36.525Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:36.549Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583183)
;

-- 2026-02-12T15:25:36.572Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773771
;

-- 2026-02-12T15:25:36.599Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773771)
;

-- 2026-02-12T15:25:36.645Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773771
;

-- 2026-02-12T15:25:36.669Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773771, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729090
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Lieferweg
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2026-02-12T15:25:37.066Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588857,773772,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:36.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung',10,'EE01','"Lieferweg" bezeichnet die Art der Warenlieferung.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Lieferweg',250,1,1,TO_TIMESTAMP('2026-02-12 15:25:36.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:37.091Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:37.115Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455)
;

-- 2026-02-12T15:25:37.146Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773772
;

-- 2026-02-12T15:25:37.171Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773772)
;

-- 2026-02-12T15:25:37.234Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773772
;

-- 2026-02-12T15:25:37.267Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773772, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729099
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2026-02-12T15:25:37.617Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588859,773773,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:37.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Simulated',260,1,1,TO_TIMESTAMP('2026-02-12 15:25:37.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:37.654Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773773 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:37.684Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580611)
;

-- 2026-02-12T15:25:37.710Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773773
;

-- 2026-02-12T15:25:37.735Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773773)
;

-- 2026-02-12T15:25:37.801Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773773
;

-- 2026-02-12T15:25:37.825Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773773, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729101
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- 2026-02-12T15:25:38.143Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588860,773774,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:37.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',10,'EE01','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Merkmale',270,1,1,TO_TIMESTAMP('2026-02-12 15:25:37.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:38.180Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773774 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:38.205Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2026-02-12T15:25:38.233Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773774
;

-- 2026-02-12T15:25:38.261Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773774)
;

-- 2026-02-12T15:25:38.319Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773774
;

-- 2026-02-12T15:25:38.364Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773774, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729102
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2026-02-12T15:25:38.714Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588861,773775,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:38.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition',10,'@C_OrderLineSO_ID/0@>0','EE01','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Auftragsposition',280,1,1,TO_TIMESTAMP('2026-02-12 15:25:38.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:38.740Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773775 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:38.762Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542435)
;

-- 2026-02-12T15:25:38.791Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773775
;

-- 2026-02-12T15:25:38.817Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773775)
;

-- 2026-02-12T15:25:38.889Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773775
;

-- 2026-02-12T15:25:38.912Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773775, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729103
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- 2026-02-12T15:25:39.232Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588869,773776,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:38.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Produktionsdisposition',290,1,1,TO_TIMESTAMP('2026-02-12 15:25:38.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:39.267Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773776 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:39.320Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583193)
;

-- 2026-02-12T15:25:39.345Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773776
;

-- 2026-02-12T15:25:39.367Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773776)
;

-- 2026-02-12T15:25:39.411Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773776
;

-- 2026-02-12T15:25:39.434Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773776, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729142
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- 2026-02-12T15:25:39.757Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588870,773777,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:39.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Manufacturing Order Line Candidate',300,1,1,TO_TIMESTAMP('2026-02-12 15:25:39.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:39.789Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773777 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:39.813Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583194)
;

-- 2026-02-12T15:25:39.853Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773777
;

-- 2026-02-12T15:25:39.879Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773777)
;

-- 2026-02-12T15:25:39.938Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773777
;

-- 2026-02-12T15:25:39.960Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773777, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729143
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Verarbeitete Menge
-- Column: DD_Order_Candidate.QtyProcessed
-- 2026-02-12T15:25:40.289Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588897,773778,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:39.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Verarbeitete Menge',310,1,1,TO_TIMESTAMP('2026-02-12 15:25:39.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:40.319Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773778 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:40.341Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580228)
;

-- 2026-02-12T15:25:40.377Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773778
;

-- 2026-02-12T15:25:40.403Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773778)
;

-- 2026-02-12T15:25:40.455Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773778
;

-- 2026-02-12T15:25:40.478Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773778, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729783
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- 2026-02-12T15:25:40.789Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588898,773779,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:40.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Zu verarbeitende Menge',320,1,1,TO_TIMESTAMP('2026-02-12 15:25:40.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:40.814Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:40.839Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580229)
;

-- 2026-02-12T15:25:40.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773779
;

-- 2026-02-12T15:25:40.898Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773779)
;

-- 2026-02-12T15:25:40.950Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773779
;

-- 2026-02-12T15:25:40.975Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773779, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729784
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2026-02-12T15:25:41.334Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588975,773780,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:41.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',10,'@C_OrderSO_ID/0@>0','EE01',0,'Y','N','N','N','N','N','N','N','N','Y','N','Auftrag',330,1,1,TO_TIMESTAMP('2026-02-12 15:25:41.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:41.360Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773780 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:41.383Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543479)
;

-- 2026-02-12T15:25:41.408Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773780
;

-- 2026-02-12T15:25:41.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773780)
;

-- 2026-02-12T15:25:41.489Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773780
;

-- 2026-02-12T15:25:41.515Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773780, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729879
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> Warenausgangsdatum
-- Column: DD_Order_Candidate.DemandDate
-- 2026-02-12T15:25:41.841Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588899,773781,0,549050,0,TO_TIMESTAMP('2026-02-12 15:25:41.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'EE01',0,0,'Y','N','Y','Y','N','N','N','N','N','N','N',0,'Warenausgangsdatum',0,340,10,0,1,1,TO_TIMESTAMP('2026-02-12 15:25:41.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:41.885Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773781 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:41.907Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583209)
;

-- 2026-02-12T15:25:41.933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773781
;

-- 2026-02-12T15:25:41.958Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773781)
;

-- 2026-02-12T15:25:42.020Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773781
;

-- 2026-02-12T15:25:42.065Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773781, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 738685
;

-- Tab: Distributionsdisposition(542090,EE01) -> Distributionsauftrag
-- Table: DD_Order_Candidate_DDOrder
-- 2026-02-12T15:25:42.463Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,588886,574325,0,549051,542429,542090,'Y',TO_TIMESTAMP('2026-02-12 15:25:42.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Distribution Order allow create Order inter warehouse to supply a demand ','EE01','N','Distribution Order allow create Order inter warehouse to supply a demand ','N','A','DD_Order_Candidate_DDOrder','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Distributionsauftrag',588723,'N',20,1,TO_TIMESTAMP('2026-02-12 15:25:42.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:42.484Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549051 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-12T15:25:42.521Z
/* DDL */  select update_tab_translation_from_ad_element(574325)
;

-- 2026-02-12T15:25:42.561Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549051)
;

-- 2026-02-12T15:25:42.641Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 549051
;

-- 2026-02-12T15:25:42.665Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 549051, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 547567
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> Mandant
-- Column: DD_Order_Candidate_DDOrder.AD_Client_ID
-- 2026-02-12T15:25:43.286Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588878,773782,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:42.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',10,0,1,1,TO_TIMESTAMP('2026-02-12 15:25:42.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:43.312Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773782 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:43.336Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-12T15:25:43.417Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773782
;

-- 2026-02-12T15:25:43.442Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773782)
;

-- 2026-02-12T15:25:43.485Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773782
;

-- 2026-02-12T15:25:43.508Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773782, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729773
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> Organisation
-- Column: DD_Order_Candidate_DDOrder.AD_Org_ID
-- 2026-02-12T15:25:43.851Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588879,773783,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:43.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','N','N','Organisation',20,0,1,1,TO_TIMESTAMP('2026-02-12 15:25:43.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:43.885Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773783 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:43.910Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-12T15:25:43.990Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773783
;

-- 2026-02-12T15:25:44.014Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773783)
;

-- 2026-02-12T15:25:44.065Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773783
;

-- 2026-02-12T15:25:44.089Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773783, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729774
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> Aktiv
-- Column: DD_Order_Candidate_DDOrder.IsActive
-- 2026-02-12T15:25:44.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588882,773784,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:44.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',30,0,1,1,TO_TIMESTAMP('2026-02-12 15:25:44.110000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:44.481Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773784 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:44.506Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-12T15:25:44.582Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773784
;

-- 2026-02-12T15:25:44.606Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773784)
;

-- 2026-02-12T15:25:44.704Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773784
;

-- 2026-02-12T15:25:44.745Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773784, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729775
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> DD_Order_Candidate - DD_Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_DDOrder_ID
-- 2026-02-12T15:25:45.049Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588885,773785,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:44.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','DD_Order_Candidate - DD_Order',40,0,1,1,TO_TIMESTAMP('2026-02-12 15:25:44.775000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:45.077Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773785 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:45.100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583203)
;

-- 2026-02-12T15:25:45.142Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773785
;

-- 2026-02-12T15:25:45.166Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773785)
;

-- 2026-02-12T15:25:45.222Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773785
;

-- 2026-02-12T15:25:45.243Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773785, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729776
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> Distributionsdisposition
-- Column: DD_Order_Candidate_DDOrder.DD_Order_Candidate_ID
-- 2026-02-12T15:25:45.552Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588886,773786,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:45.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','N','N','N','N','N','N','N','N','N','Distributionsdisposition',50,0,1,1,TO_TIMESTAMP('2026-02-12 15:25:45.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:45.576Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773786 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:45.601Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583179)
;

-- 2026-02-12T15:25:45.646Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773786
;

-- 2026-02-12T15:25:45.686Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773786)
;

-- 2026-02-12T15:25:45.779Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773786
;

-- 2026-02-12T15:25:45.804Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773786, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729777
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> Distribution Order
-- Column: DD_Order_Candidate_DDOrder.DD_Order_ID
-- 2026-02-12T15:25:46.077Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588887,773787,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:45.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Distribution Order',10,10,1,1,TO_TIMESTAMP('2026-02-12 15:25:45.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:46.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:46.161Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53311)
;

-- 2026-02-12T15:25:46.199Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773787
;

-- 2026-02-12T15:25:46.219Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773787)
;

-- 2026-02-12T15:25:46.271Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773787
;

-- 2026-02-12T15:25:46.294Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773787, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729778
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> Distribution Order Line
-- Column: DD_Order_Candidate_DDOrder.DD_OrderLine_ID
-- 2026-02-12T15:25:46.596Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588888,773788,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:46.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'EE01',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Distribution Order Line',20,20,1,1,TO_TIMESTAMP('2026-02-12 15:25:46.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:46.619Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:46.651Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53313)
;

-- 2026-02-12T15:25:46.677Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773788
;

-- 2026-02-12T15:25:46.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773788)
;

-- 2026-02-12T15:25:46.741Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773788
;

-- 2026-02-12T15:25:46.773Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773788, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729779
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> Menge
-- Column: DD_Order_Candidate_DDOrder.Qty
-- 2026-02-12T15:25:47.102Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588889,773789,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:46.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge',10,'EE01','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Menge',40,40,1,1,TO_TIMESTAMP('2026-02-12 15:25:46.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:47.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773789 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:47.151Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2026-02-12T15:25:47.177Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773789
;

-- 2026-02-12T15:25:47.202Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773789)
;

-- 2026-02-12T15:25:47.256Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773789
;

-- 2026-02-12T15:25:47.279Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773789, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729780
;

-- Field: Distributionsdisposition(542090,EE01) -> Distributionsauftrag(549051,EE01) -> Maßeinheit
-- Column: DD_Order_Candidate_DDOrder.C_UOM_ID
-- 2026-02-12T15:25:47.559Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588890,773790,0,549051,0,TO_TIMESTAMP('2026-02-12 15:25:47.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Maßeinheit',30,30,1,1,TO_TIMESTAMP('2026-02-12 15:25:47.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:47.580Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-12T15:25:47.601Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-02-12T15:25:47.632Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773790
;

-- 2026-02-12T15:25:47.655Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773790)
;

-- 2026-02-12T15:25:47.698Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 773790
;

-- 2026-02-12T15:25:47.721Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 773790, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 729781
;

-- Tab: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01)
-- UI Section: main
-- 2026-02-12T15:25:47.980Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549050,547567,TO_TIMESTAMP('2026-02-12 15:25:47.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-12 15:25:47.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-12T15:25:48.006Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547567 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-12T15:25:48.052Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547567
;

-- 2026-02-12T15:25:48.074Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547567, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546143
;

-- UI Section: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main
-- UI Column: 10
-- 2026-02-12T15:25:48.361Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549234,547567,TO_TIMESTAMP('2026-02-12 15:25:48.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-12 15:25:48.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10
-- UI Element Group: default
-- 2026-02-12T15:25:48.657Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549234,554903,TO_TIMESTAMP('2026-02-12 15:25:48.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-12 15:25:48.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- 2026-02-12T15:25:49.077Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773754,0,549050,554903,647830,'F',TO_TIMESTAMP('2026-02-12 15:25:48.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Lager ab',10,50,0,TO_TIMESTAMP('2026-02-12 15:25:48.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Von Lagerort
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- 2026-02-12T15:25:49.459Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773766,0,549050,554903,647831,'F',TO_TIMESTAMP('2026-02-12 15:25:49.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','N','Y','N','N','N','Von Lagerort',20,0,0,TO_TIMESTAMP('2026-02-12 15:25:49.199000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- 2026-02-12T15:25:49.842Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773759,0,549050,554903,647832,'F',TO_TIMESTAMP('2026-02-12 15:25:49.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Produktionsstätte ab',30,0,0,TO_TIMESTAMP('2026-02-12 15:25:49.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2026-02-12T15:25:50.163Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773755,0,549050,554903,647833,'F',TO_TIMESTAMP('2026-02-12 15:25:49.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Lager Nach',40,60,0,TO_TIMESTAMP('2026-02-12 15:25:49.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Lagerort An
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- 2026-02-12T15:25:50.512Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773767,0,549050,554903,647834,'F',TO_TIMESTAMP('2026-02-12 15:25:50.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lagerort, an den Bestand bewegt wird','"Lagerort An" bezeichnet den Lagerort, auf den ein Warenbestand bewegt wird.','Y','N','N','Y','N','N','N','Lagerort An',50,0,0,TO_TIMESTAMP('2026-02-12 15:25:50.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- 2026-02-12T15:25:50.870Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773756,0,549050,554903,647835,'F',TO_TIMESTAMP('2026-02-12 15:25:50.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Produktionsstätte zu',60,0,0,TO_TIMESTAMP('2026-02-12 15:25:50.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Lieferweg
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2026-02-12T15:25:51.237Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773772,0,549050,554903,647836,'F',TO_TIMESTAMP('2026-02-12 15:25:51.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','Y','N','N','N','Lieferweg',70,0,0,TO_TIMESTAMP('2026-02-12 15:25:51.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10
-- UI Element Group: product&qty
-- 2026-02-12T15:25:51.485Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549234,554904,TO_TIMESTAMP('2026-02-12 15:25:51.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','product&qty',20,TO_TIMESTAMP('2026-02-12 15:25:51.359000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- 2026-02-12T15:25:51.937Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773768,0,549050,554904,647837,'F',TO_TIMESTAMP('2026-02-12 15:25:51.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','Y','N','N','Produkt',10,20,0,TO_TIMESTAMP('2026-02-12 15:25:51.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-12T15:25:52.480Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,773769,0,542462,647837,TO_TIMESTAMP('2026-02-12 15:25:52.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2026-02-12 15:25:52.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Merkmale
-- Column: DD_Order_Candidate.M_AttributeSetInstance_ID
-- 2026-02-12T15:25:52.848Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773774,0,549050,554904,647838,'F',TO_TIMESTAMP('2026-02-12 15:25:52.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N','Merkmale',15,0,0,TO_TIMESTAMP('2026-02-12 15:25:52.602000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- 2026-02-12T15:25:53.215Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773765,0,549050,554904,647839,'F',TO_TIMESTAMP('2026-02-12 15:25:52.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','Y','N','N','Maßeinheit',20,30,0,TO_TIMESTAMP('2026-02-12 15:25:52.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Menge TU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- 2026-02-12T15:25:53.619Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773761,0,549050,554904,647840,'F',TO_TIMESTAMP('2026-02-12 15:25:53.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Menge TU',30,0,0,TO_TIMESTAMP('2026-02-12 15:25:53.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Menge
-- Column: DD_Order_Candidate.QtyEntered
-- 2026-02-12T15:25:53.950Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773760,0,549050,554904,647841,'F',TO_TIMESTAMP('2026-02-12 15:25:53.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','Y','N','N','Y','Y','N','N','Menge',40,40,0,TO_TIMESTAMP('2026-02-12 15:25:53.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- 2026-02-12T15:25:54.300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773762,0,549050,554904,647842,'F',TO_TIMESTAMP('2026-02-12 15:25:54.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','Y','N','N','N','Bestellt/ Beauftragt',50,0,0,TO_TIMESTAMP('2026-02-12 15:25:54.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Verarbeitete Menge
-- Column: DD_Order_Candidate.QtyProcessed
-- 2026-02-12T15:25:54.668Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773778,0,549050,554904,647843,'F',TO_TIMESTAMP('2026-02-12 15:25:54.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Verarbeitete Menge',60,0,0,TO_TIMESTAMP('2026-02-12 15:25:54.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- 2026-02-12T15:25:55.094Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773779,0,549050,554904,647844,'F',TO_TIMESTAMP('2026-02-12 15:25:54.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Zu verarbeitende Menge',70,0,0,TO_TIMESTAMP('2026-02-12 15:25:54.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10
-- UI Element Group: dates
-- 2026-02-12T15:25:55.367Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549234,554905,TO_TIMESTAMP('2026-02-12 15:25:55.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dates',30,TO_TIMESTAMP('2026-02-12 15:25:55.230000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> dates.Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- 2026-02-12T15:25:55.821Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773752,0,549050,554905,647845,'F',TO_TIMESTAMP('2026-02-12 15:25:55.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','N','Y','N','N','N','Auftragsdatum',10,0,0,TO_TIMESTAMP('2026-02-12 15:25:55.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> dates.Zugesagter Termin
-- Column: DD_Order_Candidate.SupplyDate
-- 2026-02-12T15:25:56.232Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773753,0,549050,554905,647846,'F',TO_TIMESTAMP('2026-02-12 15:25:55.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','Y','Y','N','N','Zugesagter Termin',20,10,0,TO_TIMESTAMP('2026-02-12 15:25:55.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> dates.Demand Date
-- Column: DD_Order_Candidate.DemandDate
-- 2026-02-12T15:25:56.622Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773781,0,549050,554905,647847,'F',TO_TIMESTAMP('2026-02-12 15:25:56.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Demand Date',30,0,0,TO_TIMESTAMP('2026-02-12 15:25:56.346000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main
-- UI Column: 20
-- 2026-02-12T15:25:56.890Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549235,547567,TO_TIMESTAMP('2026-02-12 15:25:56.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-12 15:25:56.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20
-- UI Element Group: flags
-- 2026-02-12T15:25:57.187Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549235,554906,TO_TIMESTAMP('2026-02-12 15:25:57.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2026-02-12 15:25:57.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> flags.Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- 2026-02-12T15:25:57.683Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773758,0,549050,554906,647848,'F',TO_TIMESTAMP('2026-02-12 15:25:57.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','Y','N','N','Verarbeitet',10,80,0,TO_TIMESTAMP('2026-02-12 15:25:57.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> flags.Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2026-02-12T15:25:58.101Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773773,0,549050,554906,647849,'F',TO_TIMESTAMP('2026-02-12 15:25:57.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Simulated',20,70,0,TO_TIMESTAMP('2026-02-12 15:25:57.801000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20
-- UI Element Group: planning
-- 2026-02-12T15:25:58.365Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549235,554907,TO_TIMESTAMP('2026-02-12 15:25:58.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','planning',20,TO_TIMESTAMP('2026-02-12 15:25:58.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> planning.Product Planning
-- Column: DD_Order_Candidate.PP_Product_Planning_ID
-- 2026-02-12T15:25:58.827Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773757,0,549050,554907,647850,'F',TO_TIMESTAMP('2026-02-12 15:25:58.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Product Planning',10,0,0,TO_TIMESTAMP('2026-02-12 15:25:58.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> planning.Network Distribution
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- 2026-02-12T15:25:59.208Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773763,0,549050,554907,647851,'F',TO_TIMESTAMP('2026-02-12 15:25:58.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Network Distribution',20,0,0,TO_TIMESTAMP('2026-02-12 15:25:58.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> planning.Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- 2026-02-12T15:25:59.604Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773764,0,549050,554907,647852,'F',TO_TIMESTAMP('2026-02-12 15:25:59.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Network Distribution Line',30,0,0,TO_TIMESTAMP('2026-02-12 15:25:59.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20
-- UI Element Group: forward document
-- 2026-02-12T15:25:59.851Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549235,554908,TO_TIMESTAMP('2026-02-12 15:25:59.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','forward document',30,TO_TIMESTAMP('2026-02-12 15:25:59.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Auftrag
-- Column: DD_Order_Candidate.C_OrderSO_ID
-- 2026-02-12T15:26:00.243Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773780,0,549050,554908,647853,'F',TO_TIMESTAMP('2026-02-12 15:25:59.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','Y','N','N','Y','N','N','N','Auftrag',5,0,0,TO_TIMESTAMP('2026-02-12 15:25:59.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2026-02-12T15:26:00.647Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773775,0,549050,554908,647854,'F',TO_TIMESTAMP('2026-02-12 15:26:00.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','Y','N','N','N','Auftragsposition',10,0,0,TO_TIMESTAMP('2026-02-12 15:26:00.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- 2026-02-12T15:26:01.012Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773776,0,549050,554908,647855,'F',TO_TIMESTAMP('2026-02-12 15:26:00.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Produktionsdisposition',20,0,0,TO_TIMESTAMP('2026-02-12 15:26:00.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Manufacturing Order Line Candidate
-- Column: DD_Order_Candidate.Forward_PP_OrderLine_Candidate_ID
-- 2026-02-12T15:26:01.359Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773777,0,549050,554908,647856,'F',TO_TIMESTAMP('2026-02-12 15:26:01.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Manufacturing Order Line Candidate',30,0,0,TO_TIMESTAMP('2026-02-12 15:26:01.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2026-02-12T15:26:01.743Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773770,0,549050,554908,647857,'F',TO_TIMESTAMP('2026-02-12 15:26:01.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Forward Manufacturing Order',40,0,0,TO_TIMESTAMP('2026-02-12 15:26:01.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2026-02-12T15:26:02.062Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773771,0,549050,554908,647858,'F',TO_TIMESTAMP('2026-02-12 15:26:01.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Forward Manufacturing Order BOM Line',50,0,0,TO_TIMESTAMP('2026-02-12 15:26:01.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20
-- UI Element Group: org&client
-- 2026-02-12T15:26:02.289Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549235,554909,TO_TIMESTAMP('2026-02-12 15:26:02.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org&client',40,TO_TIMESTAMP('2026-02-12 15:26:02.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> org&client.Organisation
-- Column: DD_Order_Candidate.AD_Org_ID
-- 2026-02-12T15:26:02.764Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773749,0,549050,554909,647859,'F',TO_TIMESTAMP('2026-02-12 15:26:02.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N','Organisation',10,0,0,TO_TIMESTAMP('2026-02-12 15:26:02.409000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,EE01) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> org&client.Mandant
-- Column: DD_Order_Candidate.AD_Client_ID
-- 2026-02-12T15:26:03.091Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773748,0,549050,554909,647860,'F',TO_TIMESTAMP('2026-02-12 15:26:02.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',20,0,0,TO_TIMESTAMP('2026-02-12 15:26:02.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Window: Distributionsdisposition, InternalName=ddOrderCandidate NEW
-- 2026-02-12T15:28:09.984Z
UPDATE AD_Window SET EntityType='U', IsOverrideInMenu='Y',Updated=TO_TIMESTAMP('2026-02-12 15:28:09.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542090
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> flags.Simulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2026-02-12T15:39:50.598Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-12 15:39:50.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647849
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Menge
-- Column: DD_Order_Candidate.QtyEntered
-- 2026-02-12T15:39:50.784Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-12 15:39:50.784000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647841
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- 2026-02-12T15:39:50.924Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-12 15:39:50.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647839
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> flags.Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- 2026-02-12T15:39:51.067Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-12 15:39:51.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647848
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> org&client.Organisation
-- Column: DD_Order_Candidate.AD_Org_ID
-- 2026-02-12T15:39:51.228Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-12 15:39:51.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647859
;

-- Element: SupplyDate
-- 2026-02-12T15:49:34.001Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 15:49:34.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583210 AND AD_Language='en_US'
;

-- 2026-02-12T15:49:34.307Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583210,'en_US')
;

-- Element: SupplyDate
-- 2026-02-12T15:49:55.128Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Wareneingangsdatum', PrintName='Wareneingangsdatum',Updated=TO_TIMESTAMP('2026-02-12 15:49:55.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583210 AND AD_Language='de_DE'
;

-- 2026-02-12T15:49:55.154Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-12T15:50:05.238Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583210,'de_DE')
;

-- 2026-02-12T15:50:05.262Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583210,'de_DE')
;

-- Element: IsSimulated
-- 2026-02-12T15:54:10.193Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 15:54:10.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=580611 AND AD_Language='en_US'
;

-- 2026-02-12T15:54:10.253Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580611,'en_US')
;

-- Element: IsSimulated
-- 2026-02-12T15:54:28.956Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Simulierter Bedarf', PrintName='Simulierter Bedarf',Updated=TO_TIMESTAMP('2026-02-12 15:54:28.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=580611 AND AD_Language='de_DE'
;

-- 2026-02-12T15:54:28.979Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-12T15:54:51.296Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580611,'de_DE')
;

-- 2026-02-12T15:54:51.318Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580611,'de_DE')
;

-- Element: DD_NetworkDistribution_ID
-- 2026-02-12T15:56:05.227Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Distributionsnetzwerk', PrintName='Distributionsnetzwerk',Updated=TO_TIMESTAMP('2026-02-12 15:56:05.227000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53340 AND AD_Language='de_DE'
;

-- 2026-02-12T15:56:05.250Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-12T15:56:37.605Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53340,'de_DE')
;

-- 2026-02-12T15:56:37.628Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53340,'de_DE')
;

-- Element: DD_NetworkDistributionLine_ID
-- 2026-02-12T15:57:21.354Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 15:57:21.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53341 AND AD_Language='fr_CH'
;

-- 2026-02-12T15:57:21.401Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53341,'fr_CH')
;

-- Element: DD_NetworkDistributionLine_ID
-- 2026-02-12T15:57:36.675Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Distributionsnetzwerkszeile', PrintName='Distributionsnetzwerkszeile',Updated=TO_TIMESTAMP('2026-02-12 15:57:36.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53341 AND AD_Language='de_CH'
;

-- 2026-02-12T15:57:36.698Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-12T15:57:55.507Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53341,'de_CH')
;

-- Element: Forward_PP_OrderLine_Candidate_ID
-- 2026-02-12T16:00:08.895Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 16:00:08.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583194 AND AD_Language='en_US'
;

-- 2026-02-12T16:00:09.006Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583194,'en_US')
;

-- Element: Forward_PP_OrderLine_Candidate_ID
-- 2026-02-12T16:00:22.228Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktionsauftragszeile', PrintName='Produktionsauftragszeile',Updated=TO_TIMESTAMP('2026-02-12 16:00:22.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583194 AND AD_Language='de_DE'
;

-- 2026-02-12T16:00:22.254Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-12T16:00:59.427Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583194,'de_DE')
;

-- 2026-02-12T16:00:59.451Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583194,'de_DE')
;

-- Element: Forward_PP_Order_ID
-- 2026-02-12T16:02:12.511Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 16:02:12.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583182 AND AD_Language='de_CH'
;

-- 2026-02-12T16:02:12.574Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583182,'de_CH')
;

-- Element: Forward_PP_Order_ID
-- 2026-02-12T16:02:14.756Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-12 16:02:14.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583182 AND AD_Language='en_US'
;

-- 2026-02-12T16:02:14.812Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583182,'en_US')
;

-- Element: Forward_PP_Order_ID
-- 2026-02-12T16:02:31.538Z
UPDATE AD_Element_Trl SET Name='Produktionsauftrag', PrintName='Produktionsauftrag',Updated=TO_TIMESTAMP('2026-02-12 16:02:31.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583182 AND AD_Language='de_CH'
;

-- 2026-02-12T16:02:31.565Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-12T16:02:53.798Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583182,'de_CH')
;

-- Element: Forward_PP_Order_ID
-- 2026-02-13T13:43:49.423Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktionsauftrag', PrintName='Produktionsauftrag',Updated=TO_TIMESTAMP('2026-02-13 13:43:49.422000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583182 AND AD_Language='de_DE'
;

-- 2026-02-13T13:43:49.449Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-13T13:44:23.926Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583182,'de_DE')
;

-- 2026-02-13T13:44:23.950Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583182,'de_DE')
;

-- Element: DD_NetworkDistributionLine_ID
-- 2026-02-13T13:45:46.111Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Distributionsnetzwerkszeile', PrintName='Distributionsnetzwerkszeile',Updated=TO_TIMESTAMP('2026-02-13 13:45:46.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53341 AND AD_Language='de_DE'
;

-- 2026-02-13T13:45:46.140Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-13T13:46:26.615Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53341,'de_DE')
;

-- 2026-02-13T13:46:26.633Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53341,'de_DE')
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- 2026-02-13T13:52:36.802Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 13:52:36.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647842
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> product&qty.Zu verarbeitende Menge
-- Column: DD_Order_Candidate.QtyToProcess
-- 2026-02-13T13:52:59.490Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 13:52:59.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647844
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- 2026-02-13T13:54:17.481Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 13:54:17.480000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647835
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> default.Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- 2026-02-13T13:54:58.195Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 13:54:58.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647832
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> planning.Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- 2026-02-13T13:56:12.417Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 13:56:12.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647852
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Auftragsposition
-- Column: DD_Order_Candidate.C_OrderLineSO_ID
-- 2026-02-13T13:57:39.433Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 13:57:39.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647854
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Produktionsdisposition
-- Column: DD_Order_Candidate.Forward_PP_Order_Candidate_ID
-- 2026-02-13T13:59:01.317Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 13:59:01.316000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647855
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2026-02-13T13:59:53.664Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 13:59:53.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647857
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2026-02-13T14:01:02.018Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-13 14:01:02.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647858
;

-- UI Column: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20
-- UI Element Group: dates
-- 2026-02-13T14:03:27.737Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549235,554910,TO_TIMESTAMP('2026-02-13 14:03:27.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dates',35,TO_TIMESTAMP('2026-02-13 14:03:27.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> dates.Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- 2026-02-13T14:04:35.884Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773752,0,549050,554910,647869,'F',TO_TIMESTAMP('2026-02-13 14:04:35.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','N','Y','N','N','N',0,'Auftragsdatum',10,0,0,TO_TIMESTAMP('2026-02-13 14:04:35.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> dates.Wareneingangsdatum
-- Column: DD_Order_Candidate.SupplyDate
-- 2026-02-13T14:06:31.235Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773753,0,549050,554910,647870,'F',TO_TIMESTAMP('2026-02-13 14:06:30.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Wareneingangsdatum',20,0,0,TO_TIMESTAMP('2026-02-13 14:06:30.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 20 -> dates.Warenausgangsdatum
-- Column: DD_Order_Candidate.DemandDate
-- 2026-02-13T14:07:19.081Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773781,0,549050,554910,647871,'F',TO_TIMESTAMP('2026-02-13 14:07:18.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Warenausgangsdatum',30,0,0,TO_TIMESTAMP('2026-02-13 14:07:18.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> dates.Zugesagter Termin
-- Column: DD_Order_Candidate.SupplyDate
-- 2026-02-13T14:08:02.727Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-13 14:08:02.727000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647846
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> dates.Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- 2026-02-13T14:08:32.263Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-13 14:08:32.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647845
;

-- UI Element: Distributionsdisposition(542090,U) -> Distributionsdisposition(549050,EE01) -> main -> 10 -> dates.Demand Date
-- Column: DD_Order_Candidate.DemandDate
-- 2026-02-13T14:08:48.066Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-13 14:08:48.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647847
;

-- Element: DD_Order_ID
-- 2026-02-13T14:16:27.982Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Distributionsauftrag', PrintName='Distributionsauftrag',Updated=TO_TIMESTAMP('2026-02-13 14:16:27.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53311 AND AD_Language='de_DE'
;

-- 2026-02-13T14:16:28.010Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-13T14:16:58.667Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53311,'de_DE')
;

-- 2026-02-13T14:16:58.703Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53311,'de_DE')
;

-- Element: DD_OrderLine_ID
-- 2026-02-13T14:17:35.323Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Distributionsauftragzeile', PrintName='Distributionsauftragzeile',Updated=TO_TIMESTAMP('2026-02-13 14:17:35.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=53313 AND AD_Language='de_DE'
;

-- 2026-02-13T14:17:35.366Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-13T14:17:55.914Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(53313,'de_DE')
;

-- 2026-02-13T14:17:55.935Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53313,'de_DE')
;
