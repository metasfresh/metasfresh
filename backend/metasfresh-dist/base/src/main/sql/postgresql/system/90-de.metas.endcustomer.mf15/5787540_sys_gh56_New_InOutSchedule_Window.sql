-- Run mode: SWING_CLIENT

-- 2026-02-10T12:11:34.788Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584508,0,TO_TIMESTAMP('2026-02-10 12:11:34.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Wareneingangsdisposition OLD','Wareneingangsdisposition',TO_TIMESTAMP('2026-02-10 12:11:34.544000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:11:34.811Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584508 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-02-10T12:11:53.673Z
UPDATE AD_Element SET EntityType='U',Updated=TO_TIMESTAMP('2026-02-10 12:11:53.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=574170
;

-- 2026-02-10T12:11:53.756Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574170,'de_DE')
;

-- Element: null
-- 2026-02-10T12:12:58.529Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-10 12:12:58.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584508 AND AD_Language='de_DE'
;

-- 2026-02-10T12:12:58.575Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584508,'de_DE')
;

-- 2026-02-10T12:12:58.599Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584508,'de_DE')
;

-- Element: null
-- 2026-02-10T12:12:59.828Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-10 12:12:59.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584508 AND AD_Language='en_US'
;

-- 2026-02-10T12:12:59.880Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584508,'en_US')
;

-- Element: null
-- 2026-02-10T12:14:19.093Z
UPDATE AD_Element_Trl SET Name='Material Receipt Candidates OLD', PrintName='Material Receipt Candidates',Updated=TO_TIMESTAMP('2026-02-10 12:14:19.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584508 AND AD_Language='en_US'
;

-- 2026-02-10T12:14:19.109Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-10T12:14:56.844Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584508,'en_US')
;

-- Window: Wareneingangsdisposition OLD, InternalName=M_ReceiptSchedule
-- 2026-02-10T12:15:28.426Z
UPDATE AD_Window SET AD_Element_ID=584508, Description=NULL, Help=NULL, Name='Wareneingangsdisposition OLD',Updated=TO_TIMESTAMP('2026-02-10 12:15:28.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=540196
;

-- 2026-02-10T12:15:28.454Z
UPDATE AD_Window_Trl trl SET Name='Wareneingangsdisposition OLD' WHERE AD_Window_ID=540196 AND AD_Language='de_DE'
;

-- Name: Wareneingangsdisposition OLD
-- Action Type: W
-- Window: Wareneingangsdisposition OLD(540196,de.metas.inoutcandidate)
-- 2026-02-10T12:15:58.125Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Wareneingangsdisposition OLD',Updated=TO_TIMESTAMP('2026-02-10 12:15:58.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540495
;

-- 2026-02-10T12:15:58.147Z
UPDATE AD_Menu_Trl trl SET Description=NULL,Name='Wareneingangsdisposition OLD' WHERE AD_Menu_ID=540495 AND AD_Language='de_DE'
;

-- Name: Wareneingangsdisposition OLD
-- Action Type: W
-- Window: Wareneingangsdisposition OLD(540196,de.metas.inoutcandidate)
-- 2026-02-10T12:16:18.569Z
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Wareneingangsdisposition OLD',Updated=TO_TIMESTAMP('2026-02-10 12:16:18.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Menu_ID=540736
;

-- 2026-02-10T12:16:18.590Z
UPDATE AD_Menu_Trl trl SET Description=NULL,Name='Wareneingangsdisposition OLD' WHERE AD_Menu_ID=540736 AND AD_Language='de_DE'
;

-- 2026-02-10T12:16:43.065Z
/* DDL */  select update_window_translation_from_ad_element(584508)
;

-- 2026-02-10T12:16:43.093Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540196
;

-- 2026-02-10T12:16:43.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(540196)
;

-- Window: Wareneingangsdisposition, InternalName=M_ReceiptSchedule NEW
-- 2026-02-10T12:31:49.443Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,574170,0,542082,TO_TIMESTAMP('2026-02-10 12:31:49.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','M_ReceiptSchedule NEW','Y','N','N','Y','N','N','N','Y','Wareneingangsdisposition','N',TO_TIMESTAMP('2026-02-10 12:31:49.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T',0,0,100)
;

-- 2026-02-10T12:31:49.471Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542082 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-02-10T12:31:49.511Z
/* DDL */  select update_window_translation_from_ad_element(574170)
;

-- 2026-02-10T12:31:49.542Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542082
;

-- 2026-02-10T12:31:49.561Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542082)
;

-- Window: Wareneingangsdisposition, InternalName=M_ReceiptSchedule NEW
-- 2026-02-10T12:48:57.862Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='de.metas.inoutcandidate', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=540196, WindowType='T', WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2026-02-10 12:48:57.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542082
;

-- Tab: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition
-- Table: M_ReceiptSchedule
-- 2026-02-10T12:48:58.288Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572637,0,548991,540524,542082,'Y',TO_TIMESTAMP('2026-02-10 12:48:58.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inoutcandidate','N','N','A','M_ReceiptSchedule','Y','N','Y','Y','Y','N','N','N','Y','N','Y','N','Y','N','N','N','N','Wareneingangsdisposition','N',10,0,TO_TIMESTAMP('2026-02-10 12:48:58.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:48:58.312Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548991 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-10T12:48:58.333Z
/* DDL */  select update_tab_translation_from_ad_element(572637)
;

-- 2026-02-10T12:48:58.357Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548991)
;

-- 2026-02-10T12:48:58.400Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548991
;

-- 2026-02-10T12:48:58.421Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548991, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540526
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> abw. Anschrift
-- Column: M_ReceiptSchedule.IsBPartnerAddress_Override
-- 2026-02-10T12:48:58.908Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549497,772028,1000500,0,548991,0,TO_TIMESTAMP('2026-02-10 12:48:58.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','abw. Anschrift',10,0,1,1,TO_TIMESTAMP('2026-02-10 12:48:58.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:48:58.932Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:48:58.957Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000500)
;

-- 2026-02-10T12:48:58.981Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772028
;

-- 2026-02-10T12:48:59.004Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772028)
;

-- 2026-02-10T12:48:59.048Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772028
;

-- 2026-02-10T12:48:59.069Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772028, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552525
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Aktiv
-- Column: M_ReceiptSchedule.IsActive
-- 2026-02-10T12:48:59.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549484,772029,0,548991,0,TO_TIMESTAMP('2026-02-10 12:48:59.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.inoutcandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',20,0,1,1,TO_TIMESTAMP('2026-02-10 12:48:59.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:48:59.396Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:48:59.418Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-10T12:48:59.499Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772029
;

-- 2026-02-10T12:48:59.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772029)
;

-- 2026-02-10T12:48:59.574Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772029
;

-- 2026-02-10T12:48:59.598Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772029, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552526
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Aktualisiert
-- Column: M_ReceiptSchedule.Updated
-- 2026-02-10T12:48:59.898Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549485,772030,0,548991,0,TO_TIMESTAMP('2026-02-10 12:48:59.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.inoutcandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',30,0,1,1,TO_TIMESTAMP('2026-02-10 12:48:59.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:48:59.920Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:48:59.944Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2026-02-10T12:48:59.994Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772030
;

-- 2026-02-10T12:49:00.013Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772030)
;

-- 2026-02-10T12:49:00.078Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772030
;

-- 2026-02-10T12:49:00.101Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772030, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552527
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Aktualisiert durch
-- Column: M_ReceiptSchedule.UpdatedBy
-- 2026-02-10T12:49:00.410Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549486,772031,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:00.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.inoutcandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',40,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:00.122000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:00.447Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:00.470Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2026-02-10T12:49:00.525Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772031
;

-- 2026-02-10T12:49:00.549Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772031)
;

-- 2026-02-10T12:49:00.591Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772031
;

-- 2026-02-10T12:49:00.613Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772031, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552528
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Anschrift-Text
-- Column: M_ReceiptSchedule.BPartnerAddress
-- 2026-02-10T12:49:00.860Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549495,772032,1001259,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:00.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,360,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Anschrift-Text',330,0,999,1,TO_TIMESTAMP('2026-02-10 12:49:00.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:00.883Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:00.906Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001259)
;

-- 2026-02-10T12:49:00.954Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772032
;

-- 2026-02-10T12:49:00.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772032)
;

-- 2026-02-10T12:49:01.026Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772032
;

-- 2026-02-10T12:49:01.065Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772032, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552529
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Anschrift-Text abw.
-- Column: M_ReceiptSchedule.BPartnerAddress_Override
-- 2026-02-10T12:49:01.295Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549496,772033,1001586,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:01.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,360,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Anschrift-Text abw.',340,0,999,1,TO_TIMESTAMP('2026-02-10 12:49:01.087000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:01.318Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:01.350Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001586)
;

-- 2026-02-10T12:49:01.368Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772033
;

-- 2026-02-10T12:49:01.396Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772033)
;

-- 2026-02-10T12:49:01.440Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772033
;

-- 2026-02-10T12:49:01.458Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772033, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552530
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Ansprechpartner
-- Column: M_ReceiptSchedule.AD_User_ID
-- 2026-02-10T12:49:01.702Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549493,772034,1002595,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:01.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact',10,'de.metas.inoutcandidate','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Ansprechpartner',290,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:01.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:01.726Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:01.750Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002595)
;

-- 2026-02-10T12:49:01.773Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772034
;

-- 2026-02-10T12:49:01.793Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772034)
;

-- 2026-02-10T12:49:01.838Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772034
;

-- 2026-02-10T12:49:01.869Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772034, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552531
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Ansprechpartner abw.
-- Column: M_ReceiptSchedule.AD_User_Override_ID
-- 2026-02-10T12:49:02.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549494,772035,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:01.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Ansprechpartner abw.',320,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:01.904000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:02.195Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:02.216Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541409)
;

-- 2026-02-10T12:49:02.244Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772035
;

-- 2026-02-10T12:49:02.263Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772035)
;

-- 2026-02-10T12:49:02.314Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772035
;

-- 2026-02-10T12:49:02.336Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772035, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552532
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bestellungen
-- Column: M_ReceiptSchedule.C_Order_ID
-- 2026-02-10T12:49:02.583Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549488,772036,572882,0,548991,85,TO_TIMESTAMP('2026-02-10 12:49:02.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Related Purchase Orders',30,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Bestellungen',40,20,-1,1,1,TO_TIMESTAMP('2026-02-10 12:49:02.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:02.605Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:02.627Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572882)
;

-- 2026-02-10T12:49:02.650Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772036
;

-- 2026-02-10T12:49:02.669Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772036)
;

-- 2026-02-10T12:49:02.714Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772036
;

-- 2026-02-10T12:49:02.738Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772036, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552533
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Auftragsdatum
-- Column: M_ReceiptSchedule.DateOrdered
-- 2026-02-10T12:49:02.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549490,772037,1000289,0,548991,112,TO_TIMESTAMP('2026-02-10 12:49:02.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags',7,'de.metas.inoutcandidate','Bezeichnet das Datum, an dem die Ware bestellt wurde.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Auftragsdatum',50,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:02.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:03.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:03.039Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000289)
;

-- 2026-02-10T12:49:03.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772037
;

-- 2026-02-10T12:49:03.082Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772037)
;

-- 2026-02-10T12:49:03.133Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772037
;

-- 2026-02-10T12:49:03.149Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772037, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552534
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Auftragsposition
-- Column: M_ReceiptSchedule.C_OrderLine_ID
-- 2026-02-10T12:49:03.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549489,772038,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:03.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition',10,'de.metas.inoutcandidate','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.',0,'Y','N','N','N','N','N','N','N','N','N','N','Auftragsposition',350,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:03.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:03.460Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:03.479Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561)
;

-- 2026-02-10T12:49:03.504Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772038
;

-- 2026-02-10T12:49:03.527Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772038)
;

-- 2026-02-10T12:49:03.583Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772038
;

-- 2026-02-10T12:49:03.605Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772038, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552535
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Belegart
-- Column: M_ReceiptSchedule.C_DocType_ID
-- 2026-02-10T12:49:03.869Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549502,772039,0,548991,138,TO_TIMESTAMP('2026-02-10 12:49:03.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',10,'de.metas.inoutcandidate','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Belegart',10,10,1,1,TO_TIMESTAMP('2026-02-10 12:49:03.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:03.893Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:03.915Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2026-02-10T12:49:03.941Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772039
;

-- 2026-02-10T12:49:03.962Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772039)
;

-- 2026-02-10T12:49:04.007Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772039
;

-- 2026-02-10T12:49:04.037Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772039, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552536
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bestellt/ Beauftragt
-- Column: M_ReceiptSchedule.QtyOrdered
-- 2026-02-10T12:49:04.272Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549515,772040,1000951,0,548991,120,TO_TIMESTAMP('2026-02-10 12:49:04.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt',14,'de.metas.inoutcandidate','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Bestellt/ Beauftragt',90,80,1,1,TO_TIMESTAMP('2026-02-10 12:49:04.059000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:04.301Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:04.324Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000951)
;

-- 2026-02-10T12:49:04.348Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772040
;

-- 2026-02-10T12:49:04.369Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772040)
;

-- 2026-02-10T12:49:04.412Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772040
;

-- 2026-02-10T12:49:04.432Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772040, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552537
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Zugesagter Termin
-- Column: M_ReceiptSchedule.MovementDate
-- 2026-02-10T12:49:04.671Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549491,772041,269,0,548991,143,TO_TIMESTAMP('2026-02-10 12:49:04.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',7,'de.metas.inoutcandidate','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugesagter Termin',160,30,1,1,TO_TIMESTAMP('2026-02-10 12:49:04.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:04.693Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:04.715Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269)
;

-- 2026-02-10T12:49:04.739Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772041
;

-- 2026-02-10T12:49:04.763Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772041)
;

-- 2026-02-10T12:49:04.809Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772041
;

-- 2026-02-10T12:49:04.835Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772041, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552538
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Datensatz-ID
-- Column: M_ReceiptSchedule.Record_ID
-- 2026-02-10T12:49:05.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549521,772042,0,548991,135,TO_TIMESTAMP('2026-02-10 12:49:04.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID',22,'de.metas.inoutcandidate','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Datensatz-ID',30,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:04.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:05.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:05.161Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538)
;

-- 2026-02-10T12:49:05.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772042
;

-- 2026-02-10T12:49:05.214Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772042)
;

-- 2026-02-10T12:49:05.266Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772042
;

-- 2026-02-10T12:49:05.302Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772042, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552539
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> DB-Tabelle
-- Column: M_ReceiptSchedule.AD_Table_ID
-- 2026-02-10T12:49:05.564Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549492,772043,0,548991,213,TO_TIMESTAMP('2026-02-10 12:49:05.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',10,'de.metas.inoutcandidate','The Database Table provides the information of the table definition',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','DB-Tabelle',20,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:05.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:05.588Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:05.611Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2026-02-10T12:49:05.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772043
;

-- 2026-02-10T12:49:05.665Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772043)
;

-- 2026-02-10T12:49:05.710Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772043
;

-- 2026-02-10T12:49:05.731Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772043, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552540
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Erstellt
-- Column: M_ReceiptSchedule.Created
-- 2026-02-10T12:49:06.029Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549482,772044,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:05.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.inoutcandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt',360,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:05.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:06.049Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:06.070Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2026-02-10T12:49:06.132Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772044
;

-- 2026-02-10T12:49:06.154Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772044)
;

-- 2026-02-10T12:49:06.196Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772044
;

-- 2026-02-10T12:49:06.217Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772044, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552541
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Erstellt durch
-- Column: M_ReceiptSchedule.CreatedBy
-- 2026-02-10T12:49:06.504Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549483,772045,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:06.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.inoutcandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',370,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:06.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:06.530Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:06.553Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2026-02-10T12:49:06.606Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772045
;

-- 2026-02-10T12:49:06.880Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772045)
;

-- 2026-02-10T12:49:06.923Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772045
;

-- 2026-02-10T12:49:06.947Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772045, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552542
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Geschäftspartner
-- Column: M_ReceiptSchedule.C_BPartner_ID
-- 2026-02-10T12:49:07.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549499,772046,1002941,0,548991,250,TO_TIMESTAMP('2026-02-10 12:49:06.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'de.metas.inoutcandidate','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Geschäftspartner',270,50,1,1,TO_TIMESTAMP('2026-02-10 12:49:06.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:07.214Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:07.237Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002941)
;

-- 2026-02-10T12:49:07.259Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772046
;

-- 2026-02-10T12:49:07.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772046)
;

-- 2026-02-10T12:49:07.327Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772046
;

-- 2026-02-10T12:49:07.349Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772046, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552544
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Geschäftspartner abw.
-- Column: M_ReceiptSchedule.C_BPartner_Override_ID
-- 2026-02-10T12:49:07.577Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549501,772047,1002296,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:07.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'de.metas.inoutcandidate','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','N','N','N','N','N','N','N','N','Geschäftspartner abw.',300,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:07.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:07.618Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:07.644Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002296)
;

-- 2026-02-10T12:49:07.672Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772047
;

-- 2026-02-10T12:49:07.692Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772047)
;

-- 2026-02-10T12:49:07.736Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772047
;

-- 2026-02-10T12:49:07.758Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772047, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552545
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2026-02-10T12:49:08.007Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549509,772048,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:07.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',1,'de.metas.inoutcandidate','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Lager',130,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:07.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:08.028Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:08.049Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2026-02-10T12:49:08.076Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772048
;

-- 2026-02-10T12:49:08.098Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772048)
;

-- 2026-02-10T12:49:08.157Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772048
;

-- 2026-02-10T12:49:08.181Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772048, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552546
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Lager abw.
-- Column: M_ReceiptSchedule.M_Warehouse_Override_ID
-- 2026-02-10T12:49:08.440Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549510,772049,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:08.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',1,'de.metas.inoutcandidate','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Lager abw.',140,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:08.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:08.461Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:08.483Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(55916)
;

-- 2026-02-10T12:49:08.508Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772049
;

-- 2026-02-10T12:49:08.532Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772049)
;

-- 2026-02-10T12:49:08.575Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772049
;

-- 2026-02-10T12:49:08.605Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772049, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552547
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Lieferart
-- Column: M_ReceiptSchedule.DeliveryRule
-- 2026-02-10T12:49:08.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549503,772050,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:08.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die zeitliche Steuerung von Lieferungen',1,'de.metas.inoutcandidate','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Lieferart',210,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:08.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:08.937Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:08.962Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(555)
;

-- 2026-02-10T12:49:08.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772050
;

-- 2026-02-10T12:49:09.009Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772050)
;

-- 2026-02-10T12:49:09.064Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772050
;

-- 2026-02-10T12:49:09.085Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772050, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552548
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Lieferart abw.
-- Column: M_ReceiptSchedule.DeliveryRule_Override
-- 2026-02-10T12:49:09.320Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549504,772051,1003110,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:09.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Lieferart abw.',240,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:09.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:09.344Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:09.361Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003110)
;

-- 2026-02-10T12:49:09.390Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772051
;

-- 2026-02-10T12:49:09.421Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772051)
;

-- 2026-02-10T12:49:09.466Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772051
;

-- 2026-02-10T12:49:09.487Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772051, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552549
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Lieferung
-- Column: M_ReceiptSchedule.DeliveryViaRule
-- 2026-02-10T12:49:09.766Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549505,772052,0,548991,161,TO_TIMESTAMP('2026-02-10 12:49:09.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird',2,'de.metas.inoutcandidate','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Lieferung',250,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:09.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:09.787Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:09.808Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(274)
;

-- 2026-02-10T12:49:09.849Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772052
;

-- 2026-02-10T12:49:09.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772052)
;

-- 2026-02-10T12:49:09.917Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772052
;

-- 2026-02-10T12:49:09.948Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772052, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552550
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Lieferung durch abw.
-- Column: M_ReceiptSchedule.DeliveryViaRule_Override
-- 2026-02-10T12:49:10.211Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549506,772053,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:09.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Lieferung durch abw.',260,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:09.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:10.235Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:10.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541412)
;

-- 2026-02-10T12:49:10.280Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772053
;

-- 2026-02-10T12:49:10.303Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772053)
;

-- 2026-02-10T12:49:10.351Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772053
;

-- 2026-02-10T12:49:10.379Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772053, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552551
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> M_IolCandHandler
-- Column: M_ReceiptSchedule.M_IolCandHandler_ID
-- 2026-02-10T12:49:10.668Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549507,772054,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:10.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','M_IolCandHandler',380,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:10.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:10.694Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:10.714Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541763)
;

-- 2026-02-10T12:49:10.738Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772054
;

-- 2026-02-10T12:49:10.761Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772054)
;

-- 2026-02-10T12:49:10.804Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772054
;

-- 2026-02-10T12:49:10.831Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772054, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552552
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Mandant
-- Column: M_ReceiptSchedule.AD_Client_ID
-- 2026-02-10T12:49:11.116Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549480,772055,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:10.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.inoutcandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',390,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:10.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:11.139Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:11.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-10T12:49:11.245Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772055
;

-- 2026-02-10T12:49:11.266Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772055)
;

-- 2026-02-10T12:49:11.309Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772055
;

-- 2026-02-10T12:49:11.331Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772055, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552553
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Priorität
-- Column: M_ReceiptSchedule.PriorityRule
-- 2026-02-10T12:49:11.580Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549511,772056,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:11.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Priority of a document',1,'de.metas.inoutcandidate','The Priority indicates the importance (high, medium, low) of this document',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Priorität',200,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:11.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:11.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:11.627Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(522)
;

-- 2026-02-10T12:49:11.650Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772056
;

-- 2026-02-10T12:49:11.687Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772056)
;

-- 2026-02-10T12:49:11.736Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772056
;

-- 2026-02-10T12:49:11.757Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772056, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552554
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Priorität Abw.
-- Column: M_ReceiptSchedule.PriorityRule_Override
-- 2026-02-10T12:49:12.030Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549512,772057,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:11.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Priorität Abw.',230,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:11.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:12.056Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:12.077Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500222)
;

-- 2026-02-10T12:49:12.112Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772057
;

-- 2026-02-10T12:49:12.132Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772057)
;

-- 2026-02-10T12:49:12.184Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772057
;

-- 2026-02-10T12:49:12.206Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772057, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552555
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Produkt
-- Column: M_ReceiptSchedule.M_Product_ID
-- 2026-02-10T12:49:12.493Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549508,772058,0,548991,187,TO_TIMESTAMP('2026-02-10 12:49:12.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'de.metas.inoutcandidate','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Produkt',70,60,1,1,TO_TIMESTAMP('2026-02-10 12:49:12.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:12.516Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:12.540Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-02-10T12:49:12.587Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772058
;

-- 2026-02-10T12:49:12.610Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772058)
;

-- 2026-02-10T12:49:12.654Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772058
;

-- 2026-02-10T12:49:12.675Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772058, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552556
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bewegte Menge
-- Column: M_ReceiptSchedule.QtyMoved
-- 2026-02-10T12:49:12.936Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549516,772059,0,548991,66,TO_TIMESTAMP('2026-02-10 12:49:12.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Bewegte Menge',170,100,1,1,TO_TIMESTAMP('2026-02-10 12:49:12.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:12.959Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:12.980Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542203)
;

-- 2026-02-10T12:49:13.004Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772059
;

-- 2026-02-10T12:49:13.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772059)
;

-- 2026-02-10T12:49:13.077Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772059
;

-- 2026-02-10T12:49:13.101Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772059, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552557
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Menge zu bewegen
-- Column: M_ReceiptSchedule.QtyToMove
-- 2026-02-10T12:49:13.367Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549517,772060,0,548991,139,TO_TIMESTAMP('2026-02-10 12:49:13.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Menge zu bewegen',190,90,1,1,TO_TIMESTAMP('2026-02-10 12:49:13.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:13.405Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:13.439Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542204)
;

-- 2026-02-10T12:49:13.460Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772060
;

-- 2026-02-10T12:49:13.484Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772060)
;

-- 2026-02-10T12:49:13.527Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772060
;

-- 2026-02-10T12:49:13.550Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772060, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552558
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Menge zu bewegen abw.
-- Column: M_ReceiptSchedule.QtyToMove_Override
-- 2026-02-10T12:49:13.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549518,772061,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:13.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','N','Menge zu bewegen abw.',220,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:13.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:13.852Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:13.876Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542205)
;

-- 2026-02-10T12:49:13.900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772061
;

-- 2026-02-10T12:49:13.930Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772061)
;

-- 2026-02-10T12:49:13.972Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772061
;

-- 2026-02-10T12:49:13.996Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772061, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552559
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Organisation
-- Column: M_ReceiptSchedule.AD_Org_ID
-- 2026-02-10T12:49:14.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549481,772062,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:14.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.inoutcandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Organisation',400,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:14.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:14.320Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:14.346Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-10T12:49:14.423Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772062
;

-- 2026-02-10T12:49:14.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772062)
;

-- 2026-02-10T12:49:14.510Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772062
;

-- 2026-02-10T12:49:14.531Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772062, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552560
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Standort
-- Column: M_ReceiptSchedule.C_BPartner_Location_ID
-- 2026-02-10T12:49:14.771Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549500,772063,1000322,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:14.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'de.metas.inoutcandidate','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Standort',280,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:14.553000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:14.799Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:14.821Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000322)
;

-- 2026-02-10T12:49:14.849Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772063
;

-- 2026-02-10T12:49:14.872Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772063)
;

-- 2026-02-10T12:49:14.919Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772063
;

-- 2026-02-10T12:49:14.944Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772063, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552561
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Standort abw.
-- Column: M_ReceiptSchedule.C_BP_Location_Override_ID
-- 2026-02-10T12:49:15.208Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549498,772064,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:14.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'de.metas.inoutcandidate','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Standort abw.',310,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:14.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:15.236Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:15.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541408)
;

-- 2026-02-10T12:49:15.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772064
;

-- 2026-02-10T12:49:15.302Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772064)
;

-- 2026-02-10T12:49:15.349Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772064
;

-- 2026-02-10T12:49:15.379Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772064, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552562
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2026-02-10T12:49:15.650Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549513,772065,1001551,0,548991,87,TO_TIMESTAMP('2026-02-10 12:49:15.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'de.metas.inoutcandidate','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Verarbeitet',350,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:15.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:15.683Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:15.707Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001551)
;

-- 2026-02-10T12:49:15.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772065
;

-- 2026-02-10T12:49:15.750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772065)
;

-- 2026-02-10T12:49:15.792Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772065
;

-- 2026-02-10T12:49:15.815Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772065, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552563
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Wareneingangsdisposition
-- Column: M_ReceiptSchedule.M_ReceiptSchedule_ID
-- 2026-02-10T12:49:16.096Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549487,772066,1002522,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:15.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Wareneingangsdisposition',410,0,2,1,1,TO_TIMESTAMP('2026-02-10 12:49:15.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:16.119Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:16.142Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002522)
;

-- 2026-02-10T12:49:16.177Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772066
;

-- 2026-02-10T12:49:16.199Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772066)
;

-- 2026-02-10T12:49:16.246Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772066
;

-- 2026-02-10T12:49:16.270Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772066, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552564
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Maßeinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- 2026-02-10T12:49:16.547Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549524,772067,0,548991,88,TO_TIMESTAMP('2026-02-10 12:49:16.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'de.metas.inoutcandidate','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Maßeinheit',100,120,1,1,TO_TIMESTAMP('2026-02-10 12:49:16.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:16.569Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:16.590Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-02-10T12:49:16.618Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772067
;

-- 2026-02-10T12:49:16.637Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772067)
;

-- 2026-02-10T12:49:16.680Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772067
;

-- 2026-02-10T12:49:16.702Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772067, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552570
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Kopf-Aggregationsmerkmal
-- Column: M_ReceiptSchedule.HeaderAggregationKey
-- 2026-02-10T12:49:16.945Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549527,772068,1001257,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:16.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Kopf-Aggregationsmerkmal',370,0,999,1,TO_TIMESTAMP('2026-02-10 12:49:16.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:16.969Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:16.990Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001257)
;

-- 2026-02-10T12:49:17.013Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772068
;

-- 2026-02-10T12:49:17.038Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772068)
;

-- 2026-02-10T12:49:17.081Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772068
;

-- 2026-02-10T12:49:17.102Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772068, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552574
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Status
-- Column: M_ReceiptSchedule.Status
-- 2026-02-10T12:49:17.356Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549664,772069,1002611,0,548991,59,TO_TIMESTAMP('2026-02-10 12:49:17.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',2000,'de.metas.inoutcandidate','',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Status',360,0,999,1,TO_TIMESTAMP('2026-02-10 12:49:17.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:17.375Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:17.399Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002611)
;

-- 2026-02-10T12:49:17.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772069
;

-- 2026-02-10T12:49:17.444Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772069)
;

-- 2026-02-10T12:49:17.497Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772069
;

-- 2026-02-10T12:49:17.518Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772069, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552653
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- 2026-02-10T12:49:17.801Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549770,540059,772070,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:17.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Packbeschreibung',410,0,999,1,TO_TIMESTAMP('2026-02-10 12:49:17.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:17.823Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:17.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542233)
;

-- 2026-02-10T12:49:17.876Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772070
;

-- 2026-02-10T12:49:17.898Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772070)
;

-- 2026-02-10T12:49:17.940Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772070
;

-- 2026-02-10T12:49:17.962Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772070, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552990
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Packvorschrift
-- Column: M_ReceiptSchedule.M_HU_PI_Item_Product_ID
-- 2026-02-10T12:49:18.207Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549769,540059,772071,1001546,0,548991,250,TO_TIMESTAMP('2026-02-10 12:49:17.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Packvorschrift',390,140,1,1,TO_TIMESTAMP('2026-02-10 12:49:17.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:18.243Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:18.267Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001546)
;

-- 2026-02-10T12:49:18.293Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772071
;

-- 2026-02-10T12:49:18.315Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772071)
;

-- 2026-02-10T12:49:18.359Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772071
;

-- 2026-02-10T12:49:18.384Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772071, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552991
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Verpackungskapazität
-- Column: M_ReceiptSchedule.QtyItemCapacity
-- 2026-02-10T12:49:18.638Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549771,540059,772072,1002431,0,548991,160,TO_TIMESTAMP('2026-02-10 12:49:18.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Verpackungskapazität',400,130,1,1,TO_TIMESTAMP('2026-02-10 12:49:18.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:18.661Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:18.684Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002431)
;

-- 2026-02-10T12:49:18.709Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772072
;

-- 2026-02-10T12:49:18.732Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772072)
;

-- 2026-02-10T12:49:18.773Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772072
;

-- 2026-02-10T12:49:18.807Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772072, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552992
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Merkmale
-- Column: M_ReceiptSchedule.M_AttributeSetInstance_ID
-- 2026-02-10T12:49:19.143Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549830,772073,0,548991,216,TO_TIMESTAMP('2026-02-10 12:49:18.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',10,'de.metas.inoutcandidate','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Merkmale',80,70,1,1,TO_TIMESTAMP('2026-02-10 12:49:18.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:19.167Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:19.203Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2026-02-10T12:49:19.229Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772073
;

-- 2026-02-10T12:49:19.264Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772073)
;

-- 2026-02-10T12:49:19.316Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772073
;

-- 2026-02-10T12:49:19.338Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772073, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553163
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bereitstellungszeit
-- Column: M_ReceiptSchedule.PreparationTime
-- 2026-02-10T12:49:19.590Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549840,772074,0,548991,136,TO_TIMESTAMP('2026-02-10 12:49:19.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereitstellungszeit',0,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Bereitstellungszeit',60,40,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:19.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:19.620Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:19.643Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542183)
;

-- 2026-02-10T12:49:19.667Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772074
;

-- 2026-02-10T12:49:19.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772074)
;

-- 2026-02-10T12:49:19.742Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772074
;

-- 2026-02-10T12:49:19.763Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772074, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553171
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- 2026-02-10T12:49:20.018Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549867,772075,1000582,0,548991,132,TO_TIMESTAMP('2026-02-10 12:49:19.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,3,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Qualitätsabzug %',110,160,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:19.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:20.050Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:20.071Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000582)
;

-- 2026-02-10T12:49:20.095Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772075
;

-- 2026-02-10T12:49:20.116Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772075)
;

-- 2026-02-10T12:49:20.166Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772075
;

-- 2026-02-10T12:49:20.189Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772075, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553209
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- 2026-02-10T12:49:20.441Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550197,772076,0,548991,105,TO_TIMESTAMP('2026-02-10 12:49:20.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Qualität-Notiz',120,170,0,999,1,TO_TIMESTAMP('2026-02-10 12:49:20.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:20.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:20.483Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542322)
;

-- 2026-02-10T12:49:20.519Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772076
;

-- 2026-02-10T12:49:20.540Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772076)
;

-- 2026-02-10T12:49:20.607Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772076
;

-- 2026-02-10T12:49:20.627Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772076, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553925
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2026-02-10T12:49:20.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550277,772077,0,548991,150,TO_TIMESTAMP('2026-02-10 12:49:20.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Ziel-Lager',150,150,1,1,TO_TIMESTAMP('2026-02-10 12:49:20.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:20.921Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:20.943Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541643)
;

-- 2026-02-10T12:49:20.970Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772077
;

-- 2026-02-10T12:49:20.993Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772077)
;

-- 2026-02-10T12:49:21.036Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772077
;

-- 2026-02-10T12:49:21.059Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772077, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553964
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Verpackungsmaterial
-- Column: M_ReceiptSchedule.IsPackagingMaterial
-- 2026-02-10T12:49:21.321Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550587,772078,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:21.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Verpackungsmaterial',420,0,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:21.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:21.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:21.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542246)
;

-- 2026-02-10T12:49:21.401Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772078
;

-- 2026-02-10T12:49:21.422Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772078)
;

-- 2026-02-10T12:49:21.464Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772078
;

-- 2026-02-10T12:49:21.486Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772078, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554193
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Gebindekonfiguration
-- Column: M_ReceiptSchedule.M_HU_LUTU_Configuration_ID
-- 2026-02-10T12:49:21.749Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550962,540059,772079,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:21.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Gebindekonfiguration',380,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:21.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:21.771Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:21.789Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542486)
;

-- 2026-02-10T12:49:21.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772079
;

-- 2026-02-10T12:49:21.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772079)
;

-- 2026-02-10T12:49:21.889Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772079
;

-- 2026-02-10T12:49:21.915Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772079, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554538
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- 2026-02-10T12:49:22.174Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551296,540059,772080,0,548991,145,TO_TIMESTAMP('2026-02-10 12:49:21.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Bewegte TU-Menge',440,110,1,1,TO_TIMESTAMP('2026-02-10 12:49:21.931000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:22.214Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:22.239Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542576)
;

-- 2026-02-10T12:49:22.264Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772080
;

-- 2026-02-10T12:49:22.281Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772080)
;

-- 2026-02-10T12:49:22.319Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772080
;

-- 2026-02-10T12:49:22.349Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772080, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554875
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> QtyOrderedOverUnder
-- Column: M_ReceiptSchedule.QtyOrderedOverUnder
-- 2026-02-10T12:49:22.609Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550508,772081,1000072,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:22.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','QtyOrderedOverUnder',450,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:22.369000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:22.629Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:22.651Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000072)
;

-- 2026-02-10T12:49:22.679Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772081
;

-- 2026-02-10T12:49:22.699Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772081)
;

-- 2026-02-10T12:49:22.745Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772081
;

-- 2026-02-10T12:49:22.769Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772081, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555022
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Qty Moved (With Issues)
-- Column: M_ReceiptSchedule.QtyMovedWithIssues
-- 2026-02-10T12:49:23.059Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551341,772082,0,548991,30,TO_TIMESTAMP('2026-02-10 12:49:22.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Qty Moved (With Issues)',180,180,1,1,TO_TIMESTAMP('2026-02-10 12:49:22.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:23.082Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:23.099Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542592)
;

-- 2026-02-10T12:49:23.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772082
;

-- 2026-02-10T12:49:23.150Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772082)
;

-- 2026-02-10T12:49:23.192Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772082
;

-- 2026-02-10T12:49:23.214Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772082, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555023
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> QtyOrderedTU
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- 2026-02-10T12:49:23.497Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556335,540059,772083,0,548991,145,TO_TIMESTAMP('2026-02-10 12:49:23.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','QtyOrderedTU',440,110,1,1,TO_TIMESTAMP('2026-02-10 12:49:23.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:23.520Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:23.541Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543296)
;

-- 2026-02-10T12:49:23.564Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772083
;

-- 2026-02-10T12:49:23.588Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772083)
;

-- 2026-02-10T12:49:23.631Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772083
;

-- 2026-02-10T12:49:23.654Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772083, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 557932
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bei Wareneing. mit Ziellager
-- Column: M_ReceiptSchedule.OnMaterialReceiptWithDestWarehouse
-- 2026-02-10T12:49:23.925Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559460,772084,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:23.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Bei Wareneing. mit Ziellager',450,190,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:23.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:23.949Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:23.971Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543911)
;

-- 2026-02-10T12:49:23.994Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772084
;

-- 2026-02-10T12:49:24.017Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772084)
;

-- 2026-02-10T12:49:24.077Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772084
;

-- 2026-02-10T12:49:24.099Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772084, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563020
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Catch Einheit
-- Column: M_ReceiptSchedule.Catch_UOM_ID
-- 2026-02-10T12:49:24.383Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569778,772085,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:24.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Catch Einheit',460,1,1,TO_TIMESTAMP('2026-02-10 12:49:24.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:24.410Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:24.433Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953)
;

-- 2026-02-10T12:49:24.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772085
;

-- 2026-02-10T12:49:24.479Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772085)
;

-- 2026-02-10T12:49:24.527Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772085
;

-- 2026-02-10T12:49:24.549Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772085, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615394
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bewegte Catch-Menge
-- Column: M_ReceiptSchedule.QtyMovedInCatchUOM
-- 2026-02-10T12:49:24.846Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569779,772086,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:24.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Bewegte Catch-Menge',470,1,1,TO_TIMESTAMP('2026-02-10 12:49:24.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:24.881Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:24.904Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577431)
;

-- 2026-02-10T12:49:24.939Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772086
;

-- 2026-02-10T12:49:24.961Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772086)
;

-- 2026-02-10T12:49:25.005Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772086
;

-- 2026-02-10T12:49:25.026Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772086, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615395
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bewegte minderwertige Catch-Menge
-- Column: M_ReceiptSchedule.QtyMovedWithIssuesInCatchUOM
-- 2026-02-10T12:49:25.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569780,772087,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:25.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Bewegte minderwertige Catch-Menge',480,1,1,TO_TIMESTAMP('2026-02-10 12:49:25.049000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:25.349Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:25.376Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577432)
;

-- 2026-02-10T12:49:25.399Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772087
;

-- 2026-02-10T12:49:25.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772087)
;

-- 2026-02-10T12:49:25.462Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772087
;

-- 2026-02-10T12:49:25.489Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772087, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615396
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Lager Eff.
-- Column: M_ReceiptSchedule.M_Warehouse_Effective_ID
-- 2026-02-10T12:49:25.780Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570612,772088,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:25.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lager Eff.',490,1,1,TO_TIMESTAMP('2026-02-10 12:49:25.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:25.812Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:25.843Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577654)
;

-- 2026-02-10T12:49:25.877Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772088
;

-- 2026-02-10T12:49:25.897Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772088)
;

-- 2026-02-10T12:49:25.942Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772088
;

-- 2026-02-10T12:49:25.977Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772088, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615397
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Exportierbar ab
-- Column: M_ReceiptSchedule.CanBeExportedFrom
-- 2026-02-10T12:49:26.259Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571007,772089,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:26.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeitpunkt, ab dem der Datensatz exportiert werden kann',7,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Exportierbar ab',500,1,1,TO_TIMESTAMP('2026-02-10 12:49:26.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:26.281Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:26.304Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577792)
;

-- 2026-02-10T12:49:26.331Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772089
;

-- 2026-02-10T12:49:26.349Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772089)
;

-- 2026-02-10T12:49:26.409Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772089
;

-- 2026-02-10T12:49:26.431Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772089, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615398
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Export Status
-- Column: M_ReceiptSchedule.ExportStatus
-- 2026-02-10T12:49:26.718Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571008,772090,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:26.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,25,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Export Status',510,1,1,TO_TIMESTAMP('2026-02-10 12:49:26.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:26.742Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:26.764Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577791)
;

-- 2026-02-10T12:49:26.786Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772090
;

-- 2026-02-10T12:49:26.809Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772090)
;

-- 2026-02-10T12:49:26.852Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772090
;

-- 2026-02-10T12:49:26.879Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772090, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615399
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Filter-Anz. mit Bestellung
-- Column: M_ReceiptSchedule.FilteredItemsWithSameC_Order_ID
-- 2026-02-10T12:49:27.154Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571516,772091,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:26.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter',0,'de.metas.inoutcandidate','Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.',0,'Y','N','N','N','N','N','N','N','N','N','N','Filter-Anz. mit Bestellung',460,200,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:26.902000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:27.180Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:27.210Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578118)
;

-- 2026-02-10T12:49:27.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772091
;

-- 2026-02-10T12:49:27.257Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772091)
;

-- 2026-02-10T12:49:27.320Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772091
;

-- 2026-02-10T12:49:27.342Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772091, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 617676
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> External resource URL
-- Column: M_ReceiptSchedule.ExternalResourceURL
-- 2026-02-10T12:49:27.597Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571519,772092,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:27.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','External resource URL',460,200,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:27.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:27.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:27.645Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578068)
;

-- 2026-02-10T12:49:27.668Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772092
;

-- 2026-02-10T12:49:27.693Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772092)
;

-- 2026-02-10T12:49:27.736Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772092
;

-- 2026-02-10T12:49:27.823Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772092, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 617690
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Zugesagter Termin abw.
-- Column: M_ReceiptSchedule.DatePromised_Override
-- 2026-02-10T12:49:28.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579326,772093,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:27.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',7,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','N','Zugesagter Termin abw.',520,1,1,TO_TIMESTAMP('2026-02-10 12:49:27.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:28.136Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:28.157Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542184)
;

-- 2026-02-10T12:49:28.182Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772093
;

-- 2026-02-10T12:49:28.205Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772093)
;

-- 2026-02-10T12:49:28.249Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772093
;

-- 2026-02-10T12:49:28.269Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772093, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 680646
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Zugesagter Termin eff.
-- Column: M_ReceiptSchedule.DatePromised_Effective
-- 2026-02-10T12:49:28.554Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579327,772094,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:28.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',7,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugesagter Termin eff.',530,210,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:28.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:28.577Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:28.600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542422)
;

-- 2026-02-10T12:49:28.631Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772094
;

-- 2026-02-10T12:49:28.652Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772094)
;

-- 2026-02-10T12:49:28.694Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772094
;

-- 2026-02-10T12:49:28.716Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772094, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 680647
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2026-02-10T12:49:29.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588327,772095,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:28.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden',0,'de.metas.inoutcandidate','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Referenz',540,220,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:28.739000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:29.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:29.084Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2026-02-10T12:49:29.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772095
;

-- 2026-02-10T12:49:29.132Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772095)
;

-- 2026-02-10T12:49:29.185Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772095
;

-- 2026-02-10T12:49:29.210Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772095, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 728771
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2026-02-10T12:49:29.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589629,772096,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:29.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@MaterialReceiptInfo@!=NULL','de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Wareneingangsinfo',550,230,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:29.233000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:29.509Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:29.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583461)
;

-- 2026-02-10T12:49:29.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772096
;

-- 2026-02-10T12:49:29.594Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772096)
;

-- 2026-02-10T12:49:29.642Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772096
;

-- 2026-02-10T12:49:29.668Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772096, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 735600
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2026-02-10T12:49:29.959Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590640,772097,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:29.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,0,'Y','N','Y','Y','N','N','N','N','N','N','N',0,'KW',0,560,240,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:29.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:29.985Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:30.009Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583880)
;

-- 2026-02-10T12:49:30.033Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772097
;

-- 2026-02-10T12:49:30.054Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772097)
;

-- 2026-02-10T12:49:30.099Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772097
;

-- 2026-02-10T12:49:30.119Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772097, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 752521
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Bestätigt durch Lieferant
-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2026-02-10T12:49:30.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591673,772098,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:30.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,0,'Y','N','Y','Y','N','N','N','N','N','N','N',0,'Bestätigt durch Lieferant',0,570,250,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:30.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:30.449Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:30.469Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584306)
;

-- 2026-02-10T12:49:30.489Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772098
;

-- 2026-02-10T12:49:30.519Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772098)
;

-- 2026-02-10T12:49:30.563Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772098
;

-- 2026-02-10T12:49:30.579Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772098, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 758542
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Externes System
-- Column: M_ReceiptSchedule.ExternalSystem_ID
-- 2026-02-10T12:49:30.866Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591674,772099,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:30.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Externes System',580,1,1,TO_TIMESTAMP('2026-02-10 12:49:30.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:30.890Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:30.916Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583968)
;

-- 2026-02-10T12:49:30.939Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772099
;

-- 2026-02-10T12:49:31.524Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772099)
;

-- 2026-02-10T12:49:33.938Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772099
;

-- 2026-02-10T12:49:33.960Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772099, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 758547
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Externe Datensatz-Kopf-ID
-- Column: M_ReceiptSchedule.ExternalHeaderId
-- 2026-02-10T12:49:34.281Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591675,772100,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:33.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Externe Datensatz-Kopf-ID',590,1,1,TO_TIMESTAMP('2026-02-10 12:49:33.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:34.314Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:34.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575915)
;

-- 2026-02-10T12:49:34.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772100
;

-- 2026-02-10T12:49:34.396Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772100)
;

-- 2026-02-10T12:49:34.439Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772100
;

-- 2026-02-10T12:49:34.469Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772100, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 758548
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Externe Datensatz-Zeilen-ID
-- Column: M_ReceiptSchedule.ExternalLineId
-- 2026-02-10T12:49:34.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591676,772101,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:34.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Externe Datensatz-Zeilen-ID',600,1,1,TO_TIMESTAMP('2026-02-10 12:49:34.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:34.849Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:34.871Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575914)
;

-- 2026-02-10T12:49:34.909Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772101
;

-- 2026-02-10T12:49:34.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772101)
;

-- 2026-02-10T12:49:34.969Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772101
;

-- 2026-02-10T12:49:34.999Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772101, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 758549
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> Projekt
-- Column: M_ReceiptSchedule.C_Project_ID
-- 2026-02-10T12:49:35.338Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572534,772102,0,548991,0,TO_TIMESTAMP('2026-02-10 12:49:35.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Financial Project',0,'@$Element_PJ/''X''@=''Y''','de.metas.inoutcandidate',0,'A Project allows you to track and control internal or external activities.',0,'Y','N','Y','Y','N','N','N','N','N','N','N',0,'Projekt',0,610,250,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:35.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:35.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:35.398Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(208)
;

-- 2026-02-10T12:49:35.425Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772102
;

-- 2026-02-10T12:49:35.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772102)
;

-- 2026-02-10T12:49:35.498Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772102
;

-- 2026-02-10T12:49:35.520Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772102, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 758719
;

-- Tab: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang
-- Table: M_ReceiptSchedule_Alloc
-- 2026-02-10T12:49:36.032Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,549608,572721,0,548992,540530,542082,'Y',TO_TIMESTAMP('2026-02-10 12:49:35.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inoutcandidate','N','N','A','M_ReceiptSchedule_Alloc','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Zugeordneter Wareneingang',549487,'N',20,1,TO_TIMESTAMP('2026-02-10 12:49:35.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ReceiptSchedule_Alloc.M_InOutLine_ID IS NOT NULL')
;

-- 2026-02-10T12:49:36.054Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548992 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-10T12:49:36.094Z
/* DDL */  select update_tab_translation_from_ad_element(572721)
;

-- 2026-02-10T12:49:36.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548992)
;

-- 2026-02-10T12:49:36.162Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548992
;

-- 2026-02-10T12:49:36.197Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548992, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540530
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Aktiv
-- Column: M_ReceiptSchedule_Alloc.IsActive
-- 2026-02-10T12:49:36.734Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549604,772103,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:36.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.inoutcandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',80,80,1,1,TO_TIMESTAMP('2026-02-10 12:49:36.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:36.758Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:36.782Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-10T12:49:36.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772103
;

-- 2026-02-10T12:49:36.888Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772103)
;

-- 2026-02-10T12:49:36.945Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772103
;

-- 2026-02-10T12:49:36.967Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772103, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552642
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Aktualisiert
-- Column: M_ReceiptSchedule_Alloc.Updated
-- 2026-02-10T12:49:37.284Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549605,772104,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:37.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.inoutcandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',90,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:37.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:37.308Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:37.329Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2026-02-10T12:49:37.389Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772104
;

-- 2026-02-10T12:49:37.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772104)
;

-- 2026-02-10T12:49:37.462Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772104
;

-- 2026-02-10T12:49:37.483Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772104, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552643
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Aktualisiert durch
-- Column: M_ReceiptSchedule_Alloc.UpdatedBy
-- 2026-02-10T12:49:37.840Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549606,772105,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:37.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.inoutcandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',100,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:37.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:37.863Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:37.903Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2026-02-10T12:49:37.942Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772105
;

-- 2026-02-10T12:49:37.984Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772105)
;

-- 2026-02-10T12:49:38.028Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772105
;

-- 2026-02-10T12:49:38.050Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772105, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552644
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Erstellt
-- Column: M_ReceiptSchedule_Alloc.Created
-- 2026-02-10T12:49:38.362Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549602,772106,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:38.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.inoutcandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt',110,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:38.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:38.394Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:38.415Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2026-02-10T12:49:38.468Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772106
;

-- 2026-02-10T12:49:38.497Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772106)
;

-- 2026-02-10T12:49:38.545Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772106
;

-- 2026-02-10T12:49:38.567Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772106, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552645
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Erstellt durch
-- Column: M_ReceiptSchedule_Alloc.CreatedBy
-- 2026-02-10T12:49:38.884Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549603,772107,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:38.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.inoutcandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',120,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:38.589000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:38.908Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:38.932Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2026-02-10T12:49:38.984Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772107
;

-- 2026-02-10T12:49:39.008Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772107)
;

-- 2026-02-10T12:49:39.053Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772107
;

-- 2026-02-10T12:49:39.079Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772107, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552646
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Mandant
-- Column: M_ReceiptSchedule_Alloc.AD_Client_ID
-- 2026-02-10T12:49:39.360Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549600,772108,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:39.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.inoutcandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2026-02-10 12:49:39.101000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:39.384Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:39.407Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-10T12:49:39.501Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772108
;

-- 2026-02-10T12:49:39.522Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772108)
;

-- 2026-02-10T12:49:39.566Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772108
;

-- 2026-02-10T12:49:39.595Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772108, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552647
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Organisation
-- Column: M_ReceiptSchedule_Alloc.AD_Org_ID
-- 2026-02-10T12:49:39.848Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549601,772109,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:39.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.inoutcandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Organisation',20,20,1,1,TO_TIMESTAMP('2026-02-10 12:49:39.619000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:39.871Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:39.916Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-10T12:49:39.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772109
;

-- 2026-02-10T12:49:40.009Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772109)
;

-- 2026-02-10T12:49:40.053Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772109
;

-- 2026-02-10T12:49:40.074Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772109, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552648
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Lieferung/Wareneingang
-- Column: M_ReceiptSchedule_Alloc.M_InOut_ID
-- 2026-02-10T12:49:40.335Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549609,772110,1002284,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:40.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material Shipment Document',10,'de.metas.inoutcandidate','The Material Shipment / Receipt ',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lieferung/Wareneingang',30,30,1,1,TO_TIMESTAMP('2026-02-10 12:49:40.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:40.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:40.416Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002284)
;

-- 2026-02-10T12:49:40.438Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772110
;

-- 2026-02-10T12:49:40.457Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772110)
;

-- 2026-02-10T12:49:40.507Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772110
;

-- 2026-02-10T12:49:40.528Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772110, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552649
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Versand-/Wareneingangsposition
-- Column: M_ReceiptSchedule_Alloc.M_InOutLine_ID
-- 2026-02-10T12:49:40.791Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549610,772111,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:40.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Position auf Versand- oder Wareneingangsbeleg',10,'de.metas.inoutcandidate','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Versand-/Wareneingangsposition',40,40,1,1,TO_TIMESTAMP('2026-02-10 12:49:40.550000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:40.820Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:40.843Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1026)
;

-- 2026-02-10T12:49:40.870Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772111
;

-- 2026-02-10T12:49:40.893Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772111)
;

-- 2026-02-10T12:49:40.937Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772111
;

-- 2026-02-10T12:49:40.959Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772111, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552650
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Wareneingangsdispo - Wareneingangszeile
-- Column: M_ReceiptSchedule_Alloc.M_ReceiptSchedule_Alloc_ID
-- 2026-02-10T12:49:41.235Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549607,772112,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:40.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Wareneingangsdispo - Wareneingangszeile',130,1,1,TO_TIMESTAMP('2026-02-10 12:49:40.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:41.258Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:41.294Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542209)
;

-- 2026-02-10T12:49:41.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772112
;

-- 2026-02-10T12:49:41.350Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772112)
;

-- 2026-02-10T12:49:41.393Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772112
;

-- 2026-02-10T12:49:41.413Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772112, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552651
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Wareneingangsdisposition
-- Column: M_ReceiptSchedule_Alloc.M_ReceiptSchedule_ID
-- 2026-02-10T12:49:41.689Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549608,772113,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:41.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Wareneingangsdisposition',70,70,1,1,TO_TIMESTAMP('2026-02-10 12:49:41.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:41.710Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:41.732Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542202)
;

-- 2026-02-10T12:49:41.756Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772113
;

-- 2026-02-10T12:49:41.779Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772113)
;

-- 2026-02-10T12:49:41.822Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772113
;

-- 2026-02-10T12:49:41.845Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772113, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552652
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Zugewiesene Menge
-- Column: M_ReceiptSchedule_Alloc.QtyAllocated
-- 2026-02-10T12:49:42.116Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549831,772114,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:41.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Zugewiesene Menge',90,90,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:41.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:42.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:42.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542253)
;

-- 2026-02-10T12:49:42.184Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772114
;

-- 2026-02-10T12:49:42.206Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772114)
;

-- 2026-02-10T12:49:42.251Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772114
;

-- 2026-02-10T12:49:42.275Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772114, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553166
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> LU
-- Column: M_ReceiptSchedule_Alloc.M_LU_HU_ID
-- 2026-02-10T12:49:42.532Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549837,540059,772115,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:42.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Loading Unit',10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','LU',120,120,1,1,TO_TIMESTAMP('2026-02-10 12:49:42.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:42.557Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:42.580Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542455)
;

-- 2026-02-10T12:49:42.602Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772115
;

-- 2026-02-10T12:49:42.625Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772115)
;

-- 2026-02-10T12:49:42.670Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772115
;

-- 2026-02-10T12:49:42.694Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772115, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553167
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Handling Units Item
-- Column: M_ReceiptSchedule_Alloc.M_HU_Item_ID
-- 2026-02-10T12:49:43.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549838,540059,772116,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:42.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','Y','Y','Handling Units Item',140,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:42.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:43.042Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:43.095Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542130)
;

-- 2026-02-10T12:49:43.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772116
;

-- 2026-02-10T12:49:43.153Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772116)
;

-- 2026-02-10T12:49:43.210Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772116
;

-- 2026-02-10T12:49:43.232Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772116, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553168
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> HU-Transaktionszeile
-- Column: M_ReceiptSchedule_Alloc.M_HU_Trx_Line_ID
-- 2026-02-10T12:49:43.481Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549839,540059,772117,1000196,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:43.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','HU-Transaktionszeile',150,150,1,1,TO_TIMESTAMP('2026-02-10 12:49:43.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:43.503Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772117 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:43.524Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000196)
;

-- 2026-02-10T12:49:43.547Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772117
;

-- 2026-02-10T12:49:43.571Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772117)
;

-- 2026-02-10T12:49:43.614Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772117
;

-- 2026-02-10T12:49:43.636Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772117, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553169
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Zugewiesene HU-Menge
-- Column: M_ReceiptSchedule_Alloc.HU_QtyAllocated
-- 2026-02-10T12:49:43.919Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550201,540059,772118,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:43.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.',10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugewiesene HU-Menge',110,110,1,1,TO_TIMESTAMP('2026-02-10 12:49:43.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:43.941Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772118 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:43.963Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542324)
;

-- 2026-02-10T12:49:43.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772118
;

-- 2026-02-10T12:49:44.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772118)
;

-- 2026-02-10T12:49:44.049Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772118
;

-- 2026-02-10T12:49:44.071Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772118, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553929
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Qualitätsabzug %
-- Column: M_ReceiptSchedule_Alloc.QualityDiscountPercent
-- 2026-02-10T12:49:44.339Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550581,772119,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:44.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Qualitätsabzug %',50,50,1,1,TO_TIMESTAMP('2026-02-10 12:49:44.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:44.358Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:44.385Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542262)
;

-- 2026-02-10T12:49:44.416Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772119
;

-- 2026-02-10T12:49:44.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772119)
;

-- 2026-02-10T12:49:44.508Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772119
;

-- 2026-02-10T12:49:44.533Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772119, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554185
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Qualität-Notiz
-- Column: M_ReceiptSchedule_Alloc.QualityNote
-- 2026-02-10T12:49:44.840Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550582,772120,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:44.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,50,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Qualität-Notiz',60,60,999,1,TO_TIMESTAMP('2026-02-10 12:49:44.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:44.875Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:44.895Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542322)
;

-- 2026-02-10T12:49:44.919Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772120
;

-- 2026-02-10T12:49:44.943Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772120)
;

-- 2026-02-10T12:49:44.999Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772120
;

-- 2026-02-10T12:49:45.023Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772120, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554186
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> TU
-- Column: M_ReceiptSchedule_Alloc.M_TU_HU_ID
-- 2026-02-10T12:49:45.318Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550841,540059,772121,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:45.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Trading Unit',0,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','TU',130,130,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:45.047000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:45.342Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:45.364Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542462)
;

-- 2026-02-10T12:49:45.389Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772121
;

-- 2026-02-10T12:49:45.410Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772121)
;

-- 2026-02-10T12:49:45.461Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772121
;

-- 2026-02-10T12:49:45.485Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772121, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554354
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Minderwertige Menge
-- Column: M_ReceiptSchedule_Alloc.QtyWithIssues
-- 2026-02-10T12:49:45.733Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551340,772122,1001446,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:45.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.',10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Minderwertige Menge',100,100,1,1,TO_TIMESTAMP('2026-02-10 12:49:45.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:45.757Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772122 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:45.789Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001446)
;

-- 2026-02-10T12:49:45.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772122
;

-- 2026-02-10T12:49:45.838Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772122)
;

-- 2026-02-10T12:49:45.881Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772122
;

-- 2026-02-10T12:49:45.903Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772122, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555021
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> CU Handling Unit (VHU)
-- Column: M_ReceiptSchedule_Alloc.VHU_ID
-- 2026-02-10T12:49:46.150Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551598,540059,772123,1000900,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:45.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','CU Handling Unit (VHU)',140,140,1,1,TO_TIMESTAMP('2026-02-10 12:49:45.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:46.171Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772123 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:46.211Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000900)
;

-- 2026-02-10T12:49:46.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772123
;

-- 2026-02-10T12:49:46.253Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772123)
;

-- 2026-02-10T12:49:46.297Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772123
;

-- 2026-02-10T12:49:46.317Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772123, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555258
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> Belegstatus
-- Column: M_ReceiptSchedule_Alloc.DocStatus
-- 2026-02-10T12:49:46.569Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552792,772124,0,548992,0,TO_TIMESTAMP('2026-02-10 12:49:46.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document',0,'de.metas.inoutcandidate','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Belegstatus',35,35,0,1,1,TO_TIMESTAMP('2026-02-10 12:49:46.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:46.592Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:46.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289)
;

-- 2026-02-10T12:49:46.646Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772124
;

-- 2026-02-10T12:49:46.673Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772124)
;

-- 2026-02-10T12:49:46.725Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772124
;

-- 2026-02-10T12:49:46.748Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772124, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556372
;

-- Tab: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment
-- Table: M_HU_Assignment
-- 2026-02-10T12:49:47.015Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,550380,573836,0,548993,540569,542082,'Y',TO_TIMESTAMP('2026-02-10 12:49:46.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inoutcandidate','N','N','A','M_HU_Assignment','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Handling Unit Assignment',549487,'N',20,1,TO_TIMESTAMP('2026-02-10 12:49:46.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_Assignment.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table where tablename = ''M_ReceiptSchedule'')')
;

-- 2026-02-10T12:49:47.035Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548993 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-10T12:49:47.059Z
/* DDL */  select update_tab_translation_from_ad_element(573836)
;

-- 2026-02-10T12:49:47.084Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548993)
;

-- 2026-02-10T12:49:47.141Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548993
;

-- 2026-02-10T12:49:47.164Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548993, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540624
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> Mandant
-- Column: M_HU_Assignment.AD_Client_ID
-- 2026-02-10T12:49:47.673Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550370,772125,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:47.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.inoutcandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2026-02-10 12:49:47.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:47.707Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772125 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:47.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-10T12:49:47.788Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772125
;

-- 2026-02-10T12:49:47.809Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772125)
;

-- 2026-02-10T12:49:47.852Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772125
;

-- 2026-02-10T12:49:47.874Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772125, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554887
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> Organisation
-- Column: M_HU_Assignment.AD_Org_ID
-- 2026-02-10T12:49:48.127Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550371,772126,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:47.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.inoutcandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Organisation',20,20,1,1,TO_TIMESTAMP('2026-02-10 12:49:47.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:48.152Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772126 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:48.172Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-10T12:49:48.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772126
;

-- 2026-02-10T12:49:48.256Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772126)
;

-- 2026-02-10T12:49:48.303Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772126
;

-- 2026-02-10T12:49:48.333Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772126, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554888
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> Aktiv
-- Column: M_HU_Assignment.IsActive
-- 2026-02-10T12:49:48.611Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550374,772127,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:48.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.inoutcandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2026-02-10 12:49:48.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:48.637Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772127 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:48.658Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-10T12:49:48.744Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772127
;

-- 2026-02-10T12:49:48.763Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772127)
;

-- 2026-02-10T12:49:48.807Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772127
;

-- 2026-02-10T12:49:48.827Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772127, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554889
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> M_HU_Assignment
-- Column: M_HU_Assignment.M_HU_Assignment_ID
-- 2026-02-10T12:49:49.102Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550377,772128,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:48.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','M_HU_Assignment',60,1,1,TO_TIMESTAMP('2026-02-10 12:49:48.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:49.124Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:49.146Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542385)
;

-- 2026-02-10T12:49:49.172Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772128
;

-- 2026-02-10T12:49:49.193Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772128)
;

-- 2026-02-10T12:49:49.236Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772128
;

-- 2026-02-10T12:49:49.258Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772128, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554890
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> Handling Units
-- Column: M_HU_Assignment.M_HU_ID
-- 2026-02-10T12:49:49.512Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550378,772129,1000201,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:49.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Handling Units',30,30,1,1,TO_TIMESTAMP('2026-02-10 12:49:49.280000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:49.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772129 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:49.560Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000201)
;

-- 2026-02-10T12:49:49.581Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772129
;

-- 2026-02-10T12:49:49.602Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772129)
;

-- 2026-02-10T12:49:49.653Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772129
;

-- 2026-02-10T12:49:49.680Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772129, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554891
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> DB-Tabelle
-- Column: M_HU_Assignment.AD_Table_ID
-- 2026-02-10T12:49:49.974Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550379,772130,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:49.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',10,'de.metas.inoutcandidate','The Database Table provides the information of the table definition',0,'Y','N','N','N','N','N','N','N','N','N','N','DB-Tabelle',70,1,1,TO_TIMESTAMP('2026-02-10 12:49:49.699000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:50.002Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:50.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2026-02-10T12:49:50.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772130
;

-- 2026-02-10T12:49:50.083Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772130)
;

-- 2026-02-10T12:49:50.119Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772130
;

-- 2026-02-10T12:49:50.146Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772130, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554892
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> Datensatz-ID
-- Column: M_HU_Assignment.Record_ID
-- 2026-02-10T12:49:50.483Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550380,772131,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:50.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID',10,'de.metas.inoutcandidate','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','N','N','N','N','N','N','N','N','N','N','Datensatz-ID',80,1,1,TO_TIMESTAMP('2026-02-10 12:49:50.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:50.507Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772131 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:50.528Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538)
;

-- 2026-02-10T12:49:50.554Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772131
;

-- 2026-02-10T12:49:50.575Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772131)
;

-- 2026-02-10T12:49:50.619Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772131
;

-- 2026-02-10T12:49:50.641Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772131, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554893
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> Menge
-- Column: M_HU_Assignment.Qty
-- 2026-02-10T12:49:50.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550425,772132,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:50.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge',14,'de.metas.inoutcandidate','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Menge',40,40,1,1,TO_TIMESTAMP('2026-02-10 12:49:50.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:50.935Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772132 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:50.957Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2026-02-10T12:49:50.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772132
;

-- 2026-02-10T12:49:51.008Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772132)
;

-- 2026-02-10T12:49:51.055Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772132
;

-- 2026-02-10T12:49:51.077Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772132, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554894
;

-- Field: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> Produkte
-- Column: M_HU_Assignment.Products
-- 2026-02-10T12:49:51.365Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552291,772133,0,548993,0,TO_TIMESTAMP('2026-02-10 12:49:51.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produkte',45,45,1,1,TO_TIMESTAMP('2026-02-10 12:49:51.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:51.389Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772133 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T12:49:51.411Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542806)
;

-- 2026-02-10T12:49:51.437Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772133
;

-- 2026-02-10T12:49:51.461Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772133)
;

-- 2026-02-10T12:49:51.507Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 772133
;

-- 2026-02-10T12:49:51.529Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 772133, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555859
;

-- Tab: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate)
-- UI Section: main
-- 2026-02-10T12:49:51.759Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548991,547513,TO_TIMESTAMP('2026-02-10 12:49:51.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-10 12:49:51.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-10T12:49:51.782Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547513 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-10T12:49:51.828Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547513
;

-- 2026-02-10T12:49:51.864Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547513, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540063
;

-- UI Section: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main
-- UI Column: 10
-- 2026-02-10T12:49:52.131Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549175,547513,TO_TIMESTAMP('2026-02-10 12:49:52.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-10 12:49:52.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: default
-- 2026-02-10T12:49:52.408Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549175,554812,TO_TIMESTAMP('2026-02-10 12:49:52.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-02-10 12:49:52.274000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Geschäftspartner
-- Column: M_ReceiptSchedule.C_BPartner_ID
-- 2026-02-10T12:49:52.925Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,UIStyle,Updated,UpdatedBy,WidgetSize) VALUES (0,772046,0,548991,554812,646939,'F',TO_TIMESTAMP('2026-02-10 12:49:52.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Geschäftspartner',5,30,10,'primary',TO_TIMESTAMP('2026-02-10 12:49:52.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2026-02-10T12:49:53.427Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,772063,0,542451,646939,TO_TIMESTAMP('2026-02-10 12:49:53.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2026-02-10 12:49:53.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T12:49:53.698Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,772034,0,542452,646939,TO_TIMESTAMP('2026-02-10 12:49:53.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,'widget',TO_TIMESTAMP('2026-02-10 12:49:53.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Produkt
-- Column: M_ReceiptSchedule.M_Product_ID
-- 2026-02-10T12:49:54.058Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,UIStyle,Updated,UpdatedBy,WidgetSize) VALUES (0,772058,0,548991,554812,646940,'F',TO_TIMESTAMP('2026-02-10 12:49:53.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Produkt',10,40,20,'',TO_TIMESTAMP('2026-02-10 12:49:53.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2026-02-10T12:49:54.464Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,772071,0,542453,646940,TO_TIMESTAMP('2026-02-10 12:49:54.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2026-02-10 12:49:54.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Merkmale
-- Column: M_ReceiptSchedule.M_AttributeSetInstance_ID
-- 2026-02-10T12:49:54.839Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772073,0,548991,554812,646941,'F',TO_TIMESTAMP('2026-02-10 12:49:54.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','screen','Merkmale',20,50,0,TO_TIMESTAMP('2026-02-10 12:49:54.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Bestellt
-- Column: M_ReceiptSchedule.QtyOrdered
-- 2026-02-10T12:49:55.263Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772040,0,548991,554812,646942,'F',TO_TIMESTAMP('2026-02-10 12:49:54.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Bestellt',30,90,40,TO_TIMESTAMP('2026-02-10 12:49:54.924000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Eingegangen
-- Column: M_ReceiptSchedule.QtyMoved
-- 2026-02-10T12:49:55.660Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772059,0,548991,554812,646943,'F',TO_TIMESTAMP('2026-02-10 12:49:55.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Eingegangen',35,100,0,TO_TIMESTAMP('2026-02-10 12:49:55.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Mengeneinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- 2026-02-10T12:49:55.994Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772067,0,548991,554812,646944,'F',TO_TIMESTAMP('2026-02-10 12:49:55.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Mengeneinheit',40,110,50,TO_TIMESTAMP('2026-02-10 12:49:55.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2026-02-10T12:49:56.374Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772095,0,548991,554812,646945,'F',TO_TIMESTAMP('2026-02-10 12:49:56.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','Y','N','N',0,'Referenz',50,20,0,TO_TIMESTAMP('2026-02-10 12:49:56.116000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: target
-- 2026-02-10T12:49:56.584Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549175,554813,TO_TIMESTAMP('2026-02-10 12:49:56.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','target',20,TO_TIMESTAMP('2026-02-10 12:49:56.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2026-02-10T12:49:56.986Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772096,0,548991,554813,646946,'F',TO_TIMESTAMP('2026-02-10 12:49:56.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Wareneingangsinfo',5,0,0,TO_TIMESTAMP('2026-02-10 12:49:56.690000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2026-02-10T12:49:57.328Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772077,0,548991,554813,646947,'F',TO_TIMESTAMP('2026-02-10 12:49:57.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Ziel-Lager',10,170,0,TO_TIMESTAMP('2026-02-10 12:49:57.078000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- 2026-02-10T12:49:57.660Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772070,0,548991,554813,646948,'F',TO_TIMESTAMP('2026-02-10 12:49:57.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','screen','Packbeschreibung',20,80,0,TO_TIMESTAMP('2026-02-10 12:49:57.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- 2026-02-10T12:49:58.037Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772080,0,548991,554813,646949,'F',TO_TIMESTAMP('2026-02-10 12:49:57.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','screen','Bewegte TU-Menge',30,70,0,TO_TIMESTAMP('2026-02-10 12:49:57.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Bestellte TU-Menge
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- 2026-02-10T12:49:58.403Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772083,0,548991,554813,646950,'F',TO_TIMESTAMP('2026-02-10 12:49:58.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','screen','Bestellte TU-Menge',40,60,0,TO_TIMESTAMP('2026-02-10 12:49:58.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Section: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main
-- UI Column: 20
-- 2026-02-10T12:49:58.627Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549176,547513,TO_TIMESTAMP('2026-02-10 12:49:58.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-10 12:49:58.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20
-- UI Element Group: dates
-- 2026-02-10T12:49:58.874Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549176,554814,TO_TIMESTAMP('2026-02-10 12:49:58.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dates',10,TO_TIMESTAMP('2026-02-10 12:49:58.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Bestellung
-- Column: M_ReceiptSchedule.C_Order_ID
-- 2026-02-10T12:49:59.273Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772036,0,548991,554814,646951,'F',TO_TIMESTAMP('2026-02-10 12:49:58.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Bestellung',10,10,0,TO_TIMESTAMP('2026-02-10 12:49:58.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2026-02-10T12:49:59.618Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772097,0,548991,554814,646952,'F',TO_TIMESTAMP('2026-02-10 12:49:59.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'KW',15,120,0,TO_TIMESTAMP('2026-02-10 12:49:59.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Bestelldatum
-- Column: M_ReceiptSchedule.DateOrdered
-- 2026-02-10T12:49:59.949Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772037,0,548991,554814,646953,'F',TO_TIMESTAMP('2026-02-10 12:49:59.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Bestelldatum',20,0,0,TO_TIMESTAMP('2026-02-10 12:49:59.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- 2026-02-10T12:50:00.276Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772041,0,548991,554814,646954,'F',TO_TIMESTAMP('2026-02-10 12:50:00.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Eingangsdatum',30,130,30,TO_TIMESTAMP('2026-02-10 12:50:00.051000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Zugesagter Termin abw.
-- Column: M_ReceiptSchedule.DatePromised_Override
-- 2026-02-10T12:50:00.628Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772093,0,548991,554814,646955,'F',TO_TIMESTAMP('2026-02-10 12:50:00.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Y','N','N','Y','N','N','N',0,'Zugesagter Termin abw.',32,0,0,TO_TIMESTAMP('2026-02-10 12:50:00.392000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Zuges. Termin (eff.)
-- Column: M_ReceiptSchedule.DatePromised_Effective
-- 2026-02-10T12:50:00.957Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772094,0,548991,554814,646956,'F',TO_TIMESTAMP('2026-02-10 12:50:00.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Y','N','N','Y','N','N','N',0,'Zuges. Termin (eff.)',34,0,0,TO_TIMESTAMP('2026-02-10 12:50:00.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Belegart
-- Column: M_ReceiptSchedule.C_DocType_ID
-- 2026-02-10T12:50:01.298Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772039,0,548991,554814,646957,'F',TO_TIMESTAMP('2026-02-10 12:50:01.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Belegart',40,0,0,TO_TIMESTAMP('2026-02-10 12:50:01.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Column: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20
-- UI Element Group: org
-- 2026-02-10T12:50:01.553Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549176,554815,TO_TIMESTAMP('2026-02-10 12:50:01.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2026-02-10 12:50:01.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Externes System
-- Column: M_ReceiptSchedule.ExternalSystem_ID
-- 2026-02-10T12:50:01.922Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772099,0,548991,554815,646958,'F',TO_TIMESTAMP('2026-02-10 12:50:01.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Externes System',5,0,0,TO_TIMESTAMP('2026-02-10 12:50:01.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2026-02-10T12:50:02.270Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772048,0,548991,554815,646959,'F',TO_TIMESTAMP('2026-02-10 12:50:02.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Lager',10,180,0,TO_TIMESTAMP('2026-02-10 12:50:02.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Organisation
-- Column: M_ReceiptSchedule.AD_Org_ID
-- 2026-02-10T12:50:02.617Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772062,0,548991,554815,646960,'F',TO_TIMESTAMP('2026-02-10 12:50:02.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Organisation',20,0,0,TO_TIMESTAMP('2026-02-10 12:50:02.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Status
-- Column: M_ReceiptSchedule.Status
-- 2026-02-10T12:50:02.995Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772069,0,548991,554815,646961,'F',TO_TIMESTAMP('2026-02-10 12:50:02.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Status',30,0,0,TO_TIMESTAMP('2026-02-10 12:50:02.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2026-02-10T12:50:03.327Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772065,0,548991,554815,646962,'F',TO_TIMESTAMP('2026-02-10 12:50:03.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Verarbeitet',40,160,0,TO_TIMESTAMP('2026-02-10 12:50:03.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate)
-- UI Section: advanced edit
-- 2026-02-10T12:50:03.563Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548991,547514,TO_TIMESTAMP('2026-02-10 12:50:03.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-10 12:50:03.438000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'advanced edit')
;

-- 2026-02-10T12:50:03.586Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547514 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-10T12:50:03.631Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547514
;

-- 2026-02-10T12:50:03.653Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547514, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540571
;

-- UI Section: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit
-- UI Column: 10
-- 2026-02-10T12:50:03.853Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549177,547514,TO_TIMESTAMP('2026-02-10 12:50:03.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-10 12:50:03.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10
-- UI Element Group: advanced edit
-- 2026-02-10T12:50:04.080Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549177,554816,TO_TIMESTAMP('2026-02-10 12:50:03.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2026-02-10 12:50:03.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bereitstellungszeit
-- Column: M_ReceiptSchedule.PreparationTime
-- 2026-02-10T12:50:04.459Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772074,0,548991,554816,646963,'F',TO_TIMESTAMP('2026-02-10 12:50:04.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereitstellungszeit','Y','Y','N','Y','N','N','N','Bereitstellungszeit',60,0,0,TO_TIMESTAMP('2026-02-10 12:50:04.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- 2026-02-10T12:50:04.795Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772075,0,548991,554816,646964,'F',TO_TIMESTAMP('2026-02-10 12:50:04.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','Y','N','N','Qualitätsabzug %',110,140,0,TO_TIMESTAMP('2026-02-10 12:50:04.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- 2026-02-10T12:50:05.192Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772076,0,548991,554816,646965,'F',TO_TIMESTAMP('2026-02-10 12:50:04.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','Y','N','N','Qualität-Notiz',120,150,0,TO_TIMESTAMP('2026-02-10 12:50:04.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lager abw.
-- Column: M_ReceiptSchedule.M_Warehouse_Override_ID
-- 2026-02-10T12:50:05.526Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772049,0,548991,554816,646966,'F',TO_TIMESTAMP('2026-02-10 12:50:05.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','N','Y','N','N','N','Lager abw.',140,0,0,TO_TIMESTAMP('2026-02-10 12:50:05.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bewegt
-- Column: M_ReceiptSchedule.QtyMoved
-- 2026-02-10T12:50:05.933Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772059,0,548991,554816,646967,'F',TO_TIMESTAMP('2026-02-10 12:50:05.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Bewegt',170,0,0,TO_TIMESTAMP('2026-02-10 12:50:05.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qty Moved (With Issues)
-- Column: M_ReceiptSchedule.QtyMovedWithIssues
-- 2026-02-10T12:50:06.305Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772082,0,548991,554816,646968,'F',TO_TIMESTAMP('2026-02-10 12:50:06.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Qty Moved (With Issues)',180,0,0,TO_TIMESTAMP('2026-02-10 12:50:06.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bewegungsmenge
-- Column: M_ReceiptSchedule.QtyToMove
-- 2026-02-10T12:50:06.668Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772060,0,548991,554816,646969,'F',TO_TIMESTAMP('2026-02-10 12:50:06.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Bewegungsmenge',190,0,0,TO_TIMESTAMP('2026-02-10 12:50:06.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Priorität
-- Column: M_ReceiptSchedule.PriorityRule
-- 2026-02-10T12:50:07.022Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772056,0,548991,554816,646970,'F',TO_TIMESTAMP('2026-02-10 12:50:06.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Priority of a document','The Priority indicates the importance (high, medium, low) of this document','Y','Y','N','Y','N','N','N','Priorität',200,0,0,TO_TIMESTAMP('2026-02-10 12:50:06.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferart
-- Column: M_ReceiptSchedule.DeliveryRule
-- 2026-02-10T12:50:07.358Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772050,0,548991,554816,646971,'F',TO_TIMESTAMP('2026-02-10 12:50:07.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','Y','N','Y','N','N','N','Lieferart',210,0,0,TO_TIMESTAMP('2026-02-10 12:50:07.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bewegungsmenge abw.
-- Column: M_ReceiptSchedule.QtyToMove_Override
-- 2026-02-10T12:50:07.710Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772061,0,548991,554816,646972,'F',TO_TIMESTAMP('2026-02-10 12:50:07.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Bewegungsmenge abw.',220,0,0,TO_TIMESTAMP('2026-02-10 12:50:07.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Priorität Abw.
-- Column: M_ReceiptSchedule.PriorityRule_Override
-- 2026-02-10T12:50:08.079Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772057,0,548991,554816,646973,'F',TO_TIMESTAMP('2026-02-10 12:50:07.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Priorität Abw.',230,0,0,TO_TIMESTAMP('2026-02-10 12:50:07.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferart abw.
-- Column: M_ReceiptSchedule.DeliveryRule_Override
-- 2026-02-10T12:50:08.391Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772051,0,548991,554816,646974,'F',TO_TIMESTAMP('2026-02-10 12:50:08.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Lieferart abw.',240,0,0,TO_TIMESTAMP('2026-02-10 12:50:08.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferung
-- Column: M_ReceiptSchedule.DeliveryViaRule
-- 2026-02-10T12:50:08.752Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772052,0,548991,554816,646975,'F',TO_TIMESTAMP('2026-02-10 12:50:08.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','Y','N','Y','N','N','N','Lieferung',250,0,0,TO_TIMESTAMP('2026-02-10 12:50:08.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferung durch abw.
-- Column: M_ReceiptSchedule.DeliveryViaRule_Override
-- 2026-02-10T12:50:09.111Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772053,0,548991,554816,646976,'F',TO_TIMESTAMP('2026-02-10 12:50:08.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Lieferung durch abw.',260,0,0,TO_TIMESTAMP('2026-02-10 12:50:08.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Geschäftspartner abw.
-- Column: M_ReceiptSchedule.C_BPartner_Override_ID
-- 2026-02-10T12:50:09.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772047,0,548991,554816,646977,'F',TO_TIMESTAMP('2026-02-10 12:50:09.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','Y','N','N','N','Geschäftspartner abw.',280,0,0,TO_TIMESTAMP('2026-02-10 12:50:09.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Standort abw.
-- Column: M_ReceiptSchedule.C_BP_Location_Override_ID
-- 2026-02-10T12:50:09.787Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772064,0,548991,554816,646978,'F',TO_TIMESTAMP('2026-02-10 12:50:09.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','Y','N','Y','N','N','N','Standort abw.',290,0,0,TO_TIMESTAMP('2026-02-10 12:50:09.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Ansprechpartner abw.
-- Column: M_ReceiptSchedule.AD_User_Override_ID
-- 2026-02-10T12:50:10.116Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772035,0,548991,554816,646979,'F',TO_TIMESTAMP('2026-02-10 12:50:09.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Ansprechpartner abw.',300,0,0,TO_TIMESTAMP('2026-02-10 12:50:09.881000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Anschrift-Text
-- Column: M_ReceiptSchedule.BPartnerAddress
-- 2026-02-10T12:50:10.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772032,0,548991,554816,646980,'F',TO_TIMESTAMP('2026-02-10 12:50:10.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Anschrift-Text',310,0,0,TO_TIMESTAMP('2026-02-10 12:50:10.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Anschrift-Text abw.
-- Column: M_ReceiptSchedule.BPartnerAddress_Override
-- 2026-02-10T12:50:10.771Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772033,0,548991,554816,646981,'F',TO_TIMESTAMP('2026-02-10 12:50:10.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Anschrift-Text abw.',320,0,0,TO_TIMESTAMP('2026-02-10 12:50:10.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Kopf-Aggregationsmerkmal
-- Column: M_ReceiptSchedule.HeaderAggregationKey
-- 2026-02-10T12:50:11.158Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772068,0,548991,554816,646982,'F',TO_TIMESTAMP('2026-02-10 12:50:10.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Kopf-Aggregationsmerkmal',350,0,0,TO_TIMESTAMP('2026-02-10 12:50:10.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Gebindekonfiguration
-- Column: M_ReceiptSchedule.M_HU_LUTU_Configuration_ID
-- 2026-02-10T12:50:11.507Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772079,0,548991,554816,646983,'F',TO_TIMESTAMP('2026-02-10 12:50:11.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Gebindekonfiguration',360,0,0,TO_TIMESTAMP('2026-02-10 12:50:11.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Verpackungskapazität
-- Column: M_ReceiptSchedule.QtyItemCapacity
-- 2026-02-10T12:50:11.831Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772072,0,548991,554816,646984,'F',TO_TIMESTAMP('2026-02-10 12:50:11.594000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Verpackungskapazität',370,0,0,TO_TIMESTAMP('2026-02-10 12:50:11.594000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Packaging Material
-- Column: M_ReceiptSchedule.IsPackagingMaterial
-- 2026-02-10T12:50:12.161Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772078,0,548991,554816,646985,'F',TO_TIMESTAMP('2026-02-10 12:50:11.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Packaging Material ',390,0,0,TO_TIMESTAMP('2026-02-10 12:50:11.930000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.CanBeExportedFrom
-- Column: M_ReceiptSchedule.CanBeExportedFrom
-- 2026-02-10T12:50:12.479Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772089,0,548991,554816,646986,'F',TO_TIMESTAMP('2026-02-10 12:50:12.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeitpunkt, ab dem der Datensatz exportiert werden kann','Y','N','N','Y','N','N','N',0,'CanBeExportedFrom',400,0,0,TO_TIMESTAMP('2026-02-10 12:50:12.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.ExportStatus
-- Column: M_ReceiptSchedule.ExportStatus
-- 2026-02-10T12:50:12.812Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772090,0,548991,554816,646987,'F',TO_TIMESTAMP('2026-02-10 12:50:12.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'ExportStatus',410,0,0,TO_TIMESTAMP('2026-02-10 12:50:12.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.External resource URL
-- Column: M_ReceiptSchedule.ExternalResourceURL
-- 2026-02-10T12:50:13.140Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772092,0,548991,554816,646988,'F',TO_TIMESTAMP('2026-02-10 12:50:12.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'External resource URL',420,0,0,TO_TIMESTAMP('2026-02-10 12:50:12.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Position
-- Column: M_ReceiptSchedule.C_Project_ID
-- 2026-02-10T12:50:13.489Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772102,0,548991,554816,646989,'F',TO_TIMESTAMP('2026-02-10 12:50:13.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Project','The Project Tab is used to define the Value, Name and Description for each project.  It also is defines the tracks the amounts assigned to, committed to and used for this project. Note that when the project Type is changed, the Phases and Tasks are re-created.','Y','N','N','Y','N','N','N',0,'Position',430,0,0,TO_TIMESTAMP('2026-02-10 12:50:13.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bestätigt durch Lieferant
-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2026-02-10T12:50:13.830Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772098,0,548991,554816,646990,'F',TO_TIMESTAMP('2026-02-10 12:50:13.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Bestätigt durch Lieferant',430,0,0,TO_TIMESTAMP('2026-02-10 12:50:13.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Externe Datensatz-Kopf-ID
-- Column: M_ReceiptSchedule.ExternalHeaderId
-- 2026-02-10T12:50:14.193Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772100,0,548991,554816,646991,'F',TO_TIMESTAMP('2026-02-10 12:50:13.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Externe Datensatz-Kopf-ID',440,0,0,TO_TIMESTAMP('2026-02-10 12:50:13.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Externe Datensatz-Zeilen-ID
-- Column: M_ReceiptSchedule.ExternalLineId
-- 2026-02-10T12:50:14.556Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772101,0,548991,554816,646992,'F',TO_TIMESTAMP('2026-02-10 12:50:14.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Externe Datensatz-Zeilen-ID',450,0,0,TO_TIMESTAMP('2026-02-10 12:50:14.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate)
-- UI Section: main
-- 2026-02-10T12:50:14.830Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548992,547515,TO_TIMESTAMP('2026-02-10 12:50:14.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-10 12:50:14.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-10T12:50:14.854Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547515 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-10T12:50:14.898Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547515
;

-- 2026-02-10T12:50:14.930Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547515, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540064
;

-- UI Section: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main
-- UI Column: 10
-- 2026-02-10T12:50:15.117Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549178,547515,TO_TIMESTAMP('2026-02-10 12:50:14.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-10 12:50:14.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: default
-- 2026-02-10T12:50:15.334Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549178,554817,TO_TIMESTAMP('2026-02-10 12:50:15.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'',TO_TIMESTAMP('2026-02-10 12:50:15.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Mandant
-- Column: M_ReceiptSchedule_Alloc.AD_Client_ID
-- 2026-02-10T12:50:15.743Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772108,0,548992,554817,646993,'F',TO_TIMESTAMP('2026-02-10 12:50:15.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',10,0,0,TO_TIMESTAMP('2026-02-10 12:50:15.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_ReceiptSchedule_Alloc.M_InOut_ID
-- 2026-02-10T12:50:16.087Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772110,0,548992,554817,646994,'F',TO_TIMESTAMP('2026-02-10 12:50:15.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','N','Y','Y','N','N','Lieferung/Wareneingang',20,30,0,TO_TIMESTAMP('2026-02-10 12:50:15.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Qualität-Notiz
-- Column: M_ReceiptSchedule_Alloc.QualityNote
-- 2026-02-10T12:50:16.417Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772120,0,548992,554817,646995,'F',TO_TIMESTAMP('2026-02-10 12:50:16.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Qualität-Notiz',30,70,0,TO_TIMESTAMP('2026-02-10 12:50:16.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Wareneingangsdisposition
-- Column: M_ReceiptSchedule_Alloc.M_ReceiptSchedule_ID
-- 2026-02-10T12:50:16.750Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772113,0,548992,554817,646996,'F',TO_TIMESTAMP('2026-02-10 12:50:16.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Wareneingangsdisposition',40,80,0,TO_TIMESTAMP('2026-02-10 12:50:16.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Aktiv
-- Column: M_ReceiptSchedule_Alloc.IsActive
-- 2026-02-10T12:50:17.123Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772103,0,548992,554817,646997,'F',TO_TIMESTAMP('2026-02-10 12:50:16.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',50,90,0,TO_TIMESTAMP('2026-02-10 12:50:16.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.HU-Transaktionszeile
-- Column: M_ReceiptSchedule_Alloc.M_HU_Trx_Line_ID
-- 2026-02-10T12:50:17.448Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772117,0,548992,554817,646998,'F',TO_TIMESTAMP('2026-02-10 12:50:17.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','HU-Transaktionszeile',60,160,0,TO_TIMESTAMP('2026-02-10 12:50:17.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Handling Unit (LU)
-- Column: M_ReceiptSchedule_Alloc.M_LU_HU_ID
-- 2026-02-10T12:50:17.780Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772115,0,548992,554817,646999,'F',TO_TIMESTAMP('2026-02-10 12:50:17.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Handling Unit (Loading Unit)','Y','N','N','Y','Y','N','N','Handling Unit (LU)',70,130,0,TO_TIMESTAMP('2026-02-10 12:50:17.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Handling Unit (TU)
-- Column: M_ReceiptSchedule_Alloc.M_TU_HU_ID
-- 2026-02-10T12:50:18.120Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772121,0,548992,554817,647000,'F',TO_TIMESTAMP('2026-02-10 12:50:17.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Handling Unit of type Tranding Unit','Y','N','N','Y','Y','N','N','Handling Unit (TU)',80,140,0,TO_TIMESTAMP('2026-02-10 12:50:17.867000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Virtual Handling Unit (VHU)
-- Column: M_ReceiptSchedule_Alloc.VHU_ID
-- 2026-02-10T12:50:18.447Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772123,0,548992,554817,647001,'F',TO_TIMESTAMP('2026-02-10 12:50:18.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Virtual Handling Unit (VHU)',90,150,0,TO_TIMESTAMP('2026-02-10 12:50:18.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Versand-/Wareneingangsposition
-- Column: M_ReceiptSchedule_Alloc.M_InOutLine_ID
-- 2026-02-10T12:50:18.780Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772111,0,548992,554817,647002,'F',TO_TIMESTAMP('2026-02-10 12:50:18.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Position auf Versand- oder Wareneingangsbeleg','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','N','Y','Y','N','N','Versand-/Wareneingangsposition',100,50,0,TO_TIMESTAMP('2026-02-10 12:50:18.534000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Qualitätsabzug %
-- Column: M_ReceiptSchedule_Alloc.QualityDiscountPercent
-- 2026-02-10T12:50:19.140Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772119,0,548992,554817,647003,'F',TO_TIMESTAMP('2026-02-10 12:50:18.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Qualitätsabzug %',110,60,0,TO_TIMESTAMP('2026-02-10 12:50:18.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Zugewiesene Menge
-- Column: M_ReceiptSchedule_Alloc.QtyAllocated
-- 2026-02-10T12:50:19.521Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772114,0,548992,554817,647004,'F',TO_TIMESTAMP('2026-02-10 12:50:19.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Zugewiesene Menge',120,100,0,TO_TIMESTAMP('2026-02-10 12:50:19.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Zugewiesene Menge (HU)
-- Column: M_ReceiptSchedule_Alloc.HU_QtyAllocated
-- 2026-02-10T12:50:20.094Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772118,0,548992,554817,647005,'F',TO_TIMESTAMP('2026-02-10 12:50:19.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Zugewiesene Menge (HU)',130,120,0,TO_TIMESTAMP('2026-02-10 12:50:19.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Minderwertige Menge
-- Column: M_ReceiptSchedule_Alloc.QtyWithIssues
-- 2026-02-10T12:50:20.440Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772122,0,548992,554817,647006,'F',TO_TIMESTAMP('2026-02-10 12:50:20.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.','Y','N','N','Y','Y','N','N','Minderwertige Menge',140,110,0,TO_TIMESTAMP('2026-02-10 12:50:20.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Belegstatus
-- Column: M_ReceiptSchedule_Alloc.DocStatus
-- 2026-02-10T12:50:20.789Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772124,0,548992,554817,647007,'F',TO_TIMESTAMP('2026-02-10 12:50:20.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','Y','N','N','Belegstatus',150,40,0,TO_TIMESTAMP('2026-02-10 12:50:20.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Organisation
-- Column: M_ReceiptSchedule_Alloc.AD_Org_ID
-- 2026-02-10T12:50:21.112Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772109,0,548992,554817,647008,'F',TO_TIMESTAMP('2026-02-10 12:50:20.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Organisation',160,20,0,TO_TIMESTAMP('2026-02-10 12:50:20.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Tab: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate)
-- UI Section: main
-- 2026-02-10T12:50:21.373Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548993,547516,TO_TIMESTAMP('2026-02-10 12:50:21.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2026-02-10 12:50:21.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-10T12:50:21.394Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547516 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2026-02-10T12:50:21.437Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 547516
;

-- 2026-02-10T12:50:21.467Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 547516, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540065
;

-- UI Section: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main
-- UI Column: 10
-- 2026-02-10T12:50:21.653Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549179,547516,TO_TIMESTAMP('2026-02-10 12:50:21.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-10 12:50:21.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: default
-- 2026-02-10T12:50:21.912Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549179,554818,TO_TIMESTAMP('2026-02-10 12:50:21.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'',TO_TIMESTAMP('2026-02-10 12:50:21.738000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Handling Units
-- Column: M_HU_Assignment.M_HU_ID
-- 2026-02-10T12:50:22.329Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772129,0,548993,554818,647009,'F',TO_TIMESTAMP('2026-02-10 12:50:22.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Handling Units',10,30,0,TO_TIMESTAMP('2026-02-10 12:50:22.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Produkte
-- Column: M_HU_Assignment.Products
-- 2026-02-10T12:50:22.664Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772133,0,548993,554818,647010,'F',TO_TIMESTAMP('2026-02-10 12:50:22.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Produkte',20,50,0,TO_TIMESTAMP('2026-02-10 12:50:22.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Aktiv
-- Column: M_HU_Assignment.IsActive
-- 2026-02-10T12:50:23.043Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772127,0,548993,554818,647011,'F',TO_TIMESTAMP('2026-02-10 12:50:22.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',30,60,0,TO_TIMESTAMP('2026-02-10 12:50:22.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Menge
-- Column: M_HU_Assignment.Qty
-- 2026-02-10T12:50:23.399Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772132,0,548993,554818,647012,'F',TO_TIMESTAMP('2026-02-10 12:50:23.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','Y','N','N','Menge',40,40,0,TO_TIMESTAMP('2026-02-10 12:50:23.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Mandant
-- Column: M_HU_Assignment.AD_Client_ID
-- 2026-02-10T12:50:23.744Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772125,0,548993,554818,647013,'F',TO_TIMESTAMP('2026-02-10 12:50:23.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',50,0,0,TO_TIMESTAMP('2026-02-10 12:50:23.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Organisation
-- Column: M_HU_Assignment.AD_Org_ID
-- 2026-02-10T12:50:24.075Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,772126,0,548993,554818,647014,'F',TO_TIMESTAMP('2026-02-10 12:50:23.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Organisation',60,20,0,TO_TIMESTAMP('2026-02-10 12:50:23.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2026-02-10T12:57:36.313Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-10 12:57:36.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646952
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- 2026-02-10T12:57:36.453Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-10 12:57:36.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646965
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- 2026-02-10T12:57:36.623Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-10 12:57:36.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646964
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2026-02-10T12:57:36.781Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-10 12:57:36.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646945
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2026-02-10T12:57:36.990Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-10 12:57:36.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646962
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- 2026-02-10T12:57:37.147Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-10 12:57:37.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646954
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2026-02-10T12:57:37.306Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-10 12:57:37.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646959
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2026-02-10T12:57:37.450Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-02-10 12:57:37.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646947
;

-- Element: QtyOrderedTU
-- 2026-02-10T13:00:35.160Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bestellte TU Menge', PrintName='Bestellte TU Menge',Updated=TO_TIMESTAMP('2026-02-10 13:00:35.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543296 AND AD_Language='de_DE'
;

-- 2026-02-10T13:00:35.188Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-10T13:01:18.052Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543296,'de_DE')
;

-- 2026-02-10T13:01:18.072Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543296,'de_DE')
;

-- Window: Wareneingangsdisposition, InternalName=M_ReceiptSchedule NEW
-- 2026-02-10T13:10:06.931Z
UPDATE AD_Window SET IsOverrideInMenu='Y',Updated=TO_TIMESTAMP('2026-02-10 13:10:06.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=542082
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Eingegangen
-- Column: M_ReceiptSchedule.QtyMoved
-- 2026-02-10T14:08:05.604Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2026-02-10 14:08:05.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646943
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Mengeneinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- 2026-02-10T14:08:50.724Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2026-02-10 14:08:50.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646944
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2026-02-10T14:09:37.232Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-10 14:09:37.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646945
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Externes System
-- Column: M_ReceiptSchedule.ExternalSystem_ID
-- 2026-02-10T14:32:56.607Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-10 14:32:56.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646958
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> default.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2026-02-10T14:39:43.249Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772048,0,548991,554812,647018,'F',TO_TIMESTAMP('2026-02-10 14:39:43.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',60,0,0,TO_TIMESTAMP('2026-02-10 14:39:43.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2026-02-10T14:47:09.197Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-10 14:47:09.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646959
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2026-02-10T14:52:21.094Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-10 14:52:21.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646952
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Belegart
-- Column: M_ReceiptSchedule.C_DocType_ID
-- 2026-02-10T14:52:59.954Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2026-02-10 14:52:59.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646957
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Status
-- Column: M_ReceiptSchedule.Status
-- 2026-02-10T14:54:40.604Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772069,0,548991,554814,647019,'F',TO_TIMESTAMP('2026-02-10 14:54:40.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Status',5,0,0,TO_TIMESTAMP('2026-02-10 14:54:40.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2026-02-10T14:59:54.487Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2026-02-10 14:59:54.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646947
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Status
-- Column: M_ReceiptSchedule.Status
-- 2026-02-10T15:10:14.776Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-10 15:10:14.776000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646961
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2026-02-10T15:12:30.982Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-10 15:12:30.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646947
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2026-02-10T15:17:26.907Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-10 15:17:26.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646947
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.ExportStatus
-- Column: M_ReceiptSchedule.ExportStatus
-- 2026-02-10T15:22:49.851Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2026-02-10 15:22:49.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646987
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.CanBeExportedFrom
-- Column: M_ReceiptSchedule.CanBeExportedFrom
-- 2026-02-10T15:25:08.856Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2026-02-10 15:25:08.856000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646986
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- 2026-02-10T15:32:25.861Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-10 15:32:25.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646948
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bewegt
-- Column: M_ReceiptSchedule.QtyMoved
-- 2026-02-11T14:10:23.905Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-11 14:10:23.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646967
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Position
-- Column: M_ReceiptSchedule.C_Project_ID
-- 2026-02-12T14:32:48.055Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2026-02-12 14:32:48.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646989
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- 2026-02-12T14:34:32.995Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2026-02-12 14:34:32.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646949
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 10 -> target.Bestellte TU-Menge
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- 2026-02-12T14:35:00.347Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2026-02-12 14:35:00.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646950
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2026-02-12T14:35:53.155Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2026-02-12 14:35:53.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646962
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> org.Mandant
-- Column: M_ReceiptSchedule.AD_Client_ID
-- 2026-02-12T14:37:21.087Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772055,0,548991,554815,647828,'F',TO_TIMESTAMP('2026-02-12 14:37:20.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',50,0,0,TO_TIMESTAMP('2026-02-12 14:37:20.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2026-02-12T14:39:03.643Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772065,0,548991,554814,647829,'F',TO_TIMESTAMP('2026-02-12 14:39:03.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',40,0,0,TO_TIMESTAMP('2026-02-12 14:39:03.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Belegart
-- Column: M_ReceiptSchedule.C_DocType_ID
-- 2026-02-12T14:40:18.467Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2026-02-12 14:40:18.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646957
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Bestellung
-- Column: M_ReceiptSchedule.C_Order_ID
-- 2026-02-12T14:40:39.006Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2026-02-12 14:40:39.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646951
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Status
-- Column: M_ReceiptSchedule.Status
-- 2026-02-12T14:41:06.808Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2026-02-12 14:41:06.808000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647019
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Bestelldatum
-- Column: M_ReceiptSchedule.DateOrdered
-- 2026-02-12T14:42:24.335Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2026-02-12 14:42:24.335000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646953
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- 2026-02-12T14:43:15.759Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2026-02-12 14:43:15.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646954
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Zugesagter Termin abw.
-- Column: M_ReceiptSchedule.DatePromised_Override
-- 2026-02-12T14:46:17.175Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2026-02-12 14:46:17.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646955
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Wareneingangsdisposition(548991,de.metas.inoutcandidate) -> main -> 20 -> dates.Zuges. Termin (eff.)
-- Column: M_ReceiptSchedule.DatePromised_Effective
-- 2026-02-12T14:47:45.747Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2026-02-12 14:47:45.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646956
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_ReceiptSchedule_Alloc.M_InOut_ID
-- 2026-02-12T14:51:16.495Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-12 14:51:16.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646994
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Wareneingangsdisposition
-- Column: M_ReceiptSchedule_Alloc.M_ReceiptSchedule_ID
-- 2026-02-12T14:51:16.694Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-12 14:51:16.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646996
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Versand-/Wareneingangsposition
-- Column: M_ReceiptSchedule_Alloc.M_InOutLine_ID
-- 2026-02-12T14:51:16.857Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-02-12 14:51:16.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647002
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Belegstatus
-- Column: M_ReceiptSchedule_Alloc.DocStatus
-- 2026-02-12T14:51:17.009Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-12 14:51:17.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647007
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Qualitätsabzug %
-- Column: M_ReceiptSchedule_Alloc.QualityDiscountPercent
-- 2026-02-12T14:51:17.165Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-12 14:51:17.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647003
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Qualität-Notiz
-- Column: M_ReceiptSchedule_Alloc.QualityNote
-- 2026-02-12T14:51:17.341Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-12 14:51:17.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646995
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Zugewiesene Menge
-- Column: M_ReceiptSchedule_Alloc.QtyAllocated
-- 2026-02-12T14:51:17.515Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-12 14:51:17.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647004
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Minderwertige Menge
-- Column: M_ReceiptSchedule_Alloc.QtyWithIssues
-- 2026-02-12T14:51:17.679Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-12 14:51:17.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647006
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Zugewiesene Menge (HU)
-- Column: M_ReceiptSchedule_Alloc.HU_QtyAllocated
-- 2026-02-12T14:51:17.912Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-12 14:51:17.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647005
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Handling Unit (LU)
-- Column: M_ReceiptSchedule_Alloc.M_LU_HU_ID
-- 2026-02-12T14:51:18.095Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-12 14:51:18.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646999
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Handling Unit (TU)
-- Column: M_ReceiptSchedule_Alloc.M_TU_HU_ID
-- 2026-02-12T14:51:18.239Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-12 14:51:18.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647000
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Virtual Handling Unit (VHU)
-- Column: M_ReceiptSchedule_Alloc.VHU_ID
-- 2026-02-12T14:51:18.396Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-02-12 14:51:18.396000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647001
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.HU-Transaktionszeile
-- Column: M_ReceiptSchedule_Alloc.M_HU_Trx_Line_ID
-- 2026-02-12T14:51:18.543Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-02-12 14:51:18.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646998
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Aktiv
-- Column: M_ReceiptSchedule_Alloc.IsActive
-- 2026-02-12T14:51:18.679Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-02-12 14:51:18.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646997
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548992,de.metas.inoutcandidate) -> main -> 10 -> default.Organisation
-- Column: M_ReceiptSchedule_Alloc.AD_Org_ID
-- 2026-02-12T14:51:18.838Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-02-12 14:51:18.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647008
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Produkte
-- Column: M_HU_Assignment.Products
-- 2026-02-12T14:52:24.760Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-02-12 14:52:24.760000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647010
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Menge
-- Column: M_HU_Assignment.Qty
-- 2026-02-12T14:52:24.937Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-12 14:52:24.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647012
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Aktiv
-- Column: M_HU_Assignment.IsActive
-- 2026-02-12T14:52:25.093Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-12 14:52:25.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647011
;

-- UI Element: Wareneingangsdisposition(542082,de.metas.inoutcandidate) -> Handling Unit Assignment(548993,de.metas.inoutcandidate) -> main -> 10 -> default.Organisation
-- Column: M_HU_Assignment.AD_Org_ID
-- 2026-02-12T14:52:25.282Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-12 14:52:25.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647014
;