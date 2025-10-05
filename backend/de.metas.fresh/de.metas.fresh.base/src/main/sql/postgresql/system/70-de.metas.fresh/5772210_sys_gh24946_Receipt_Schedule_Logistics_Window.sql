-- Run mode: SWING_CLIENT

-- 2025-10-02T18:22:36.945Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584081,0,TO_TIMESTAMP('2025-10-02 18:22:36.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Wareneingangsdisposition Logistik','Wareneingangsdisposition Logistik',TO_TIMESTAMP('2025-10-02 18:22:36.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:22:37.024Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584081 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-02T18:23:10.157Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Receipt Schedule Logistics', PrintName='Receipt Schedule Logistics',Updated=TO_TIMESTAMP('2025-10-02 18:23:10.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584081 AND AD_Language='en_US'
;

-- 2025-10-02T18:23:10.235Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T18:23:20.394Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584081,'en_US')
;

-- Window: Wareneingangsdisposition Logistik, InternalName=null
-- 2025-10-02T18:23:50.066Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584081,0,541954,TO_TIMESTAMP('2025-10-02 18:23:49.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Wareneingangsdisposition Logistik','N',TO_TIMESTAMP('2025-10-02 18:23:49.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2025-10-02T18:23:50.144Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541954 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-10-02T18:23:50.302Z
/* DDL */  select update_window_translation_from_ad_element(584081)
;

-- 2025-10-02T18:23:50.381Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541954
;

-- 2025-10-02T18:23:50.459Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541954)
;

----------------------------------------------------------------

-- Window: Wareneingangsdisposition - Einkauf, InternalName=null
-- 2025-10-02T18:26:40.267Z
UPDATE AD_Window SET IsOverrideInMenu='N', Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2025-10-02 18:26:40.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541928
;

----------------------------------------------------------------

-- Window: Wareneingangsdisposition Logistik, InternalName=null
-- 2025-10-02T18:27:45.130Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='de.metas.inoutcandidate', IsActive='Y', IsBetaFunctionality='N', IsDefault='N', IsEnableRemoteCacheInvalidation='Y', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=540196, WindowType='T', WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2025-10-02 18:27:45.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541954
;

-- 2025-10-02T18:27:45.437Z
DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 541954
;

-- 2025-10-02T18:27:45.515Z
INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 541954, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 540196
;

-- Tab: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition
-- Table: M_ReceiptSchedule
-- 2025-10-02T18:27:46.877Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,572637,0,548451,540524,541954,'Y',TO_TIMESTAMP('2025-10-02 18:27:45.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inoutcandidate','N','N','A','M_ReceiptSchedule','Y','N','Y','Y','Y','N','N','N','Y','N','Y','N','Y','N','N','N','N','Wareneingangsdisposition','N',10,0,TO_TIMESTAMP('2025-10-02 18:27:45.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:27:46.956Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548451 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-02T18:27:47.034Z
/* DDL */  select update_tab_translation_from_ad_element(572637)
;

-- 2025-10-02T18:27:47.115Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548451)
;

-- 2025-10-02T18:27:47.269Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548451
;

-- 2025-10-02T18:27:47.348Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548451, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540526
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> abw. Anschrift
-- Column: M_ReceiptSchedule.IsBPartnerAddress_Override
-- 2025-10-02T18:27:49.188Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549497,754565,1000500,0,548451,0,TO_TIMESTAMP('2025-10-02 18:27:48.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','abw. Anschrift',10,0,1,1,TO_TIMESTAMP('2025-10-02 18:27:48.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:27:49.268Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754565 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:27:49.346Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000500)
;

-- 2025-10-02T18:27:49.425Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754565
;

-- 2025-10-02T18:27:49.502Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754565)
;

-- 2025-10-02T18:27:49.657Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754565
;

-- 2025-10-02T18:27:49.735Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754565, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552525
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Aktiv
-- Column: M_ReceiptSchedule.IsActive
-- 2025-10-02T18:27:50.935Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549484,754566,0,548451,0,TO_TIMESTAMP('2025-10-02 18:27:49.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.inoutcandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv',20,0,1,1,TO_TIMESTAMP('2025-10-02 18:27:49.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:27:51.014Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754566 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:27:51.092Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-02T18:27:51.287Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754566
;

-- 2025-10-02T18:27:51.368Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754566)
;

-- 2025-10-02T18:27:51.522Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754566
;

-- 2025-10-02T18:27:51.599Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754566, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552526
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Aktualisiert
-- Column: M_ReceiptSchedule.Updated
-- 2025-10-02T18:27:52.816Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549485,754567,0,548451,0,TO_TIMESTAMP('2025-10-02 18:27:51.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.inoutcandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',30,0,1,1,TO_TIMESTAMP('2025-10-02 18:27:51.677000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:27:52.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754567 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:27:52.973Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2025-10-02T18:27:53.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754567
;

-- 2025-10-02T18:27:53.169Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754567)
;

-- 2025-10-02T18:27:53.324Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754567
;

-- 2025-10-02T18:27:53.401Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754567, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552527
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Aktualisiert durch
-- Column: M_ReceiptSchedule.UpdatedBy
-- 2025-10-02T18:27:54.603Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549486,754568,0,548451,0,TO_TIMESTAMP('2025-10-02 18:27:53.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.inoutcandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',40,0,1,1,TO_TIMESTAMP('2025-10-02 18:27:53.479000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:27:54.681Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754568 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:27:54.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2025-10-02T18:27:54.871Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754568
;

-- 2025-10-02T18:27:54.950Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754568)
;

-- 2025-10-02T18:27:55.108Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754568
;

-- 2025-10-02T18:27:55.186Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754568, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552528
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Anschrift-Text
-- Column: M_ReceiptSchedule.BPartnerAddress
-- 2025-10-02T18:27:56.244Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549495,754569,1001259,0,548451,0,TO_TIMESTAMP('2025-10-02 18:27:55.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,360,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Anschrift-Text',330,0,999,1,TO_TIMESTAMP('2025-10-02 18:27:55.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:27:56.323Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754569 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:27:56.404Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001259)
;

-- 2025-10-02T18:27:56.483Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754569
;

-- 2025-10-02T18:27:56.560Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754569)
;

-- 2025-10-02T18:27:56.718Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754569
;

-- 2025-10-02T18:27:56.796Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754569, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552529
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Anschrift-Text abw.
-- Column: M_ReceiptSchedule.BPartnerAddress_Override
-- 2025-10-02T18:27:57.848Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549496,754570,1001586,0,548451,0,TO_TIMESTAMP('2025-10-02 18:27:56.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,360,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Anschrift-Text abw.',340,0,999,1,TO_TIMESTAMP('2025-10-02 18:27:56.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:27:57.927Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754570 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:27:58.008Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001586)
;

-- 2025-10-02T18:27:58.090Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754570
;

-- 2025-10-02T18:27:58.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754570)
;

-- 2025-10-02T18:27:58.326Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754570
;

-- 2025-10-02T18:27:58.405Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754570, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552530
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Ansprechpartner
-- Column: M_ReceiptSchedule.AD_User_ID
-- 2025-10-02T18:27:59.456Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549493,754571,1002595,0,548451,0,TO_TIMESTAMP('2025-10-02 18:27:58.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact',10,'de.metas.inoutcandidate','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Ansprechpartner',290,0,1,1,TO_TIMESTAMP('2025-10-02 18:27:58.485000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:27:59.533Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754571 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:27:59.610Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002595)
;

-- 2025-10-02T18:27:59.689Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754571
;

-- 2025-10-02T18:27:59.765Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754571)
;

-- 2025-10-02T18:27:59.919Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754571
;

-- 2025-10-02T18:27:59.995Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754571, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552531
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Ansprechpartner abw.
-- Column: M_ReceiptSchedule.AD_User_Override_ID
-- 2025-10-02T18:28:01.125Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549494,754572,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:00.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Ansprechpartner abw.',320,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:00.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:01.203Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754572 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:01.281Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541409)
;

-- 2025-10-02T18:28:01.359Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754572
;

-- 2025-10-02T18:28:01.435Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754572)
;

-- 2025-10-02T18:28:01.591Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754572
;

-- 2025-10-02T18:28:01.669Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754572, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552532
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bestellungen
-- Column: M_ReceiptSchedule.C_Order_ID
-- 2025-10-02T18:28:02.729Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549488,754573,572882,0,548451,85,TO_TIMESTAMP('2025-10-02 18:28:01.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Related Purchase Orders',30,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Bestellungen',40,20,-1,1,1,TO_TIMESTAMP('2025-10-02 18:28:01.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:02.805Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754573 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:02.883Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572882)
;

-- 2025-10-02T18:28:02.963Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754573
;

-- 2025-10-02T18:28:03.038Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754573)
;

-- 2025-10-02T18:28:03.193Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754573
;

-- 2025-10-02T18:28:03.270Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754573, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552533
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Auftragsdatum
-- Column: M_ReceiptSchedule.DateOrdered
-- 2025-10-02T18:28:04.316Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549490,754574,1000289,0,548451,112,TO_TIMESTAMP('2025-10-02 18:28:03.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum des Auftrags',7,'de.metas.inoutcandidate','Bezeichnet das Datum, an dem die Ware bestellt wurde.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Auftragsdatum',50,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:03.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:04.396Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754574 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:04.477Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000289)
;

-- 2025-10-02T18:28:04.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754574
;

-- 2025-10-02T18:28:04.637Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754574)
;

-- 2025-10-02T18:28:04.796Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754574
;

-- 2025-10-02T18:28:04.874Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754574, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552534
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Auftragsposition
-- Column: M_ReceiptSchedule.C_OrderLine_ID
-- 2025-10-02T18:28:06.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549489,754575,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:04.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition',10,'de.metas.inoutcandidate','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.',0,'Y','N','N','N','N','N','N','N','N','N','N','Auftragsposition',350,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:04.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:06.163Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754575 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:06.241Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561)
;

-- 2025-10-02T18:28:06.321Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754575
;

-- 2025-10-02T18:28:06.397Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754575)
;

-- 2025-10-02T18:28:06.553Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754575
;

-- 2025-10-02T18:28:06.630Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754575, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552535
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Belegart
-- Column: M_ReceiptSchedule.C_DocType_ID
-- 2025-10-02T18:28:07.775Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549502,754576,0,548451,138,TO_TIMESTAMP('2025-10-02 18:28:06.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',10,'de.metas.inoutcandidate','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Belegart',10,10,1,1,TO_TIMESTAMP('2025-10-02 18:28:06.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:07.853Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754576 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:07.933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2025-10-02T18:28:08.018Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754576
;

-- 2025-10-02T18:28:08.097Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754576)
;

-- 2025-10-02T18:28:08.253Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754576
;

-- 2025-10-02T18:28:08.331Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754576, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552536
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bestellt/ Beauftragt
-- Column: M_ReceiptSchedule.QtyOrdered
-- 2025-10-02T18:28:09.379Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549515,754577,1000951,0,548451,120,TO_TIMESTAMP('2025-10-02 18:28:08.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt',14,'de.metas.inoutcandidate','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Bestellt/ Beauftragt',90,80,1,1,TO_TIMESTAMP('2025-10-02 18:28:08.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:09.459Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754577 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:09.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000951)
;

-- 2025-10-02T18:28:09.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754577
;

-- 2025-10-02T18:28:09.696Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754577)
;

-- 2025-10-02T18:28:09.850Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754577
;

-- 2025-10-02T18:28:09.929Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754577, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552537
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Zugesagter Termin
-- Column: M_ReceiptSchedule.MovementDate
-- 2025-10-02T18:28:10.991Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549491,754578,269,0,548451,143,TO_TIMESTAMP('2025-10-02 18:28:10.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',7,'de.metas.inoutcandidate','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugesagter Termin',160,30,1,1,TO_TIMESTAMP('2025-10-02 18:28:10.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:11.070Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754578 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:11.147Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269)
;

-- 2025-10-02T18:28:11.228Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754578
;

-- 2025-10-02T18:28:11.307Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754578)
;

-- 2025-10-02T18:28:11.468Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754578
;

-- 2025-10-02T18:28:11.546Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754578, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552538
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Datensatz-ID
-- Column: M_ReceiptSchedule.Record_ID
-- 2025-10-02T18:28:12.701Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549521,754579,0,548451,135,TO_TIMESTAMP('2025-10-02 18:28:11.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID',22,'de.metas.inoutcandidate','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Datensatz-ID',30,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:11.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:12.779Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:12.860Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538)
;

-- 2025-10-02T18:28:12.942Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754579
;

-- 2025-10-02T18:28:13.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754579)
;

-- 2025-10-02T18:28:13.175Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754579
;

-- 2025-10-02T18:28:13.251Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754579, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552539
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> DB-Tabelle
-- Column: M_ReceiptSchedule.AD_Table_ID
-- 2025-10-02T18:28:14.382Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549492,754580,0,548451,213,TO_TIMESTAMP('2025-10-02 18:28:13.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',10,'de.metas.inoutcandidate','The Database Table provides the information of the table definition',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','DB-Tabelle',20,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:13.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:14.460Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754580 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:14.538Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2025-10-02T18:28:14.622Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754580
;

-- 2025-10-02T18:28:14.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754580)
;

-- 2025-10-02T18:28:14.853Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754580
;

-- 2025-10-02T18:28:14.931Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754580, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552540
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Erstellt
-- Column: M_ReceiptSchedule.Created
-- 2025-10-02T18:28:16.153Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549482,754581,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:15.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.inoutcandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt',360,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:15.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:16.231Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754581 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:16.309Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-10-02T18:28:16.414Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754581
;

-- 2025-10-02T18:28:16.490Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754581)
;

-- 2025-10-02T18:28:16.645Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754581
;

-- 2025-10-02T18:28:16.721Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754581, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552541
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Erstellt durch
-- Column: M_ReceiptSchedule.CreatedBy
-- 2025-10-02T18:28:17.937Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549483,754582,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:16.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.inoutcandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',370,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:16.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:18.014Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754582 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:18.091Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2025-10-02T18:28:18.191Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754582
;

-- 2025-10-02T18:28:18.267Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754582)
;

-- 2025-10-02T18:28:18.423Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754582
;

-- 2025-10-02T18:28:18.499Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754582, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552542
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Geschäftspartner
-- Column: M_ReceiptSchedule.C_BPartner_ID
-- 2025-10-02T18:28:19.550Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549499,754583,1002941,0,548451,250,TO_TIMESTAMP('2025-10-02 18:28:18.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'de.metas.inoutcandidate','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Geschäftspartner',270,50,1,1,TO_TIMESTAMP('2025-10-02 18:28:18.577000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:19.626Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754583 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:19.705Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002941)
;

-- 2025-10-02T18:28:19.783Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754583
;

-- 2025-10-02T18:28:19.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754583)
;

-- 2025-10-02T18:28:20.014Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754583
;

-- 2025-10-02T18:28:20.092Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754583, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552544
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Geschäftspartner abw.
-- Column: M_ReceiptSchedule.C_BPartner_Override_ID
-- 2025-10-02T18:28:21.145Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549501,754584,1002296,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:20.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'de.metas.inoutcandidate','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','N','Y','N','N','N','N','N','N','N','N','Geschäftspartner abw.',300,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:20.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:21.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754584 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:21.300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002296)
;

-- 2025-10-02T18:28:21.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754584
;

-- 2025-10-02T18:28:21.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754584)
;

-- 2025-10-02T18:28:21.613Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754584
;

-- 2025-10-02T18:28:21.690Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754584, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552545
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2025-10-02T18:28:22.812Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549509,754585,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:21.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',1,'de.metas.inoutcandidate','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Lager',130,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:21.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:22.889Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754585 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:22.967Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2025-10-02T18:28:23.050Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754585
;

-- 2025-10-02T18:28:23.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754585)
;

-- 2025-10-02T18:28:23.287Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754585
;

-- 2025-10-02T18:28:23.364Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754585, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552546
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Lager abw.
-- Column: M_ReceiptSchedule.M_Warehouse_Override_ID
-- 2025-10-02T18:28:24.495Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549510,754586,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:23.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',1,'de.metas.inoutcandidate','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Lager abw.',140,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:23.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:24.575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754586 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:24.653Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(55916)
;

-- 2025-10-02T18:28:24.733Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754586
;

-- 2025-10-02T18:28:24.812Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754586)
;

-- 2025-10-02T18:28:24.966Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754586
;

-- 2025-10-02T18:28:25.043Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754586, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552547
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Lieferart
-- Column: M_ReceiptSchedule.DeliveryRule
-- 2025-10-02T18:28:26.168Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549503,754587,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:25.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die zeitliche Steuerung von Lieferungen',1,'de.metas.inoutcandidate','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Lieferart',210,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:25.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:26.250Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754587 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:26.329Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(555)
;

-- 2025-10-02T18:28:26.410Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754587
;

-- 2025-10-02T18:28:26.488Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754587)
;

-- 2025-10-02T18:28:26.641Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754587
;

-- 2025-10-02T18:28:26.718Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754587, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552548
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Lieferart abw.
-- Column: M_ReceiptSchedule.DeliveryRule_Override
-- 2025-10-02T18:28:27.777Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549504,754588,1003110,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:26.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Lieferart abw.',240,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:26.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:27.855Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754588 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:27.933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1003110)
;

-- 2025-10-02T18:28:28.012Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754588
;

-- 2025-10-02T18:28:28.090Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754588)
;

-- 2025-10-02T18:28:28.246Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754588
;

-- 2025-10-02T18:28:28.323Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754588, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552549
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Lieferung
-- Column: M_ReceiptSchedule.DeliveryViaRule
-- 2025-10-02T18:28:29.476Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549505,754589,0,548451,161,TO_TIMESTAMP('2025-10-02 18:28:28.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird',2,'de.metas.inoutcandidate','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Lieferung',250,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:28.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:29.554Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754589 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:29.632Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(274)
;

-- 2025-10-02T18:28:29.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754589
;

-- 2025-10-02T18:28:29.788Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754589)
;

-- 2025-10-02T18:28:29.946Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754589
;

-- 2025-10-02T18:28:30.022Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754589, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552550
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Lieferung durch abw.
-- Column: M_ReceiptSchedule.DeliveryViaRule_Override
-- 2025-10-02T18:28:31.136Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549506,754590,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:30.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Lieferung durch abw.',260,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:30.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:31.214Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754590 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:31.290Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541412)
;

-- 2025-10-02T18:28:31.370Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754590
;

-- 2025-10-02T18:28:31.446Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754590)
;

-- 2025-10-02T18:28:31.601Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754590
;

-- 2025-10-02T18:28:31.677Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754590, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552551
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> M_IolCandHandler
-- Column: M_ReceiptSchedule.M_IolCandHandler_ID
-- 2025-10-02T18:28:32.879Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549507,754591,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:31.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','M_IolCandHandler',380,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:31.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:32.958Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754591 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:33.035Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541763)
;

-- 2025-10-02T18:28:33.116Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754591
;

-- 2025-10-02T18:28:33.194Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754591)
;

-- 2025-10-02T18:28:33.349Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754591
;

-- 2025-10-02T18:28:33.425Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754591, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552552
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Mandant
-- Column: M_ReceiptSchedule.AD_Client_ID
-- 2025-10-02T18:28:34.643Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549480,754592,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:33.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.inoutcandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','N','N','Y','N','Mandant',390,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:33.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:34.721Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754592 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:34.800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-02T18:28:34.929Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754592
;

-- 2025-10-02T18:28:35.005Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754592)
;

-- 2025-10-02T18:28:35.160Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754592
;

-- 2025-10-02T18:28:35.237Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754592, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552553
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Priorität
-- Column: M_ReceiptSchedule.PriorityRule
-- 2025-10-02T18:28:36.377Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549511,754593,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:35.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Priority of a document',1,'de.metas.inoutcandidate','The Priority indicates the importance (high, medium, low) of this document',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Priorität',200,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:35.315000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:36.454Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:36.533Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(522)
;

-- 2025-10-02T18:28:36.619Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754593
;

-- 2025-10-02T18:28:36.698Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754593)
;

-- 2025-10-02T18:28:36.858Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754593
;

-- 2025-10-02T18:28:36.939Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754593, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552554
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Priorität Abw.
-- Column: M_ReceiptSchedule.PriorityRule_Override
-- 2025-10-02T18:28:38.082Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549512,754594,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:37.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Priorität Abw.',230,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:37.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:38.163Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754594 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:38.240Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500222)
;

-- 2025-10-02T18:28:38.317Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754594
;

-- 2025-10-02T18:28:38.397Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754594)
;

-- 2025-10-02T18:28:38.554Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754594
;

-- 2025-10-02T18:28:38.630Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754594, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552555
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Produkt
-- Column: M_ReceiptSchedule.M_Product_ID
-- 2025-10-02T18:28:39.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549508,754595,0,548451,187,TO_TIMESTAMP('2025-10-02 18:28:38.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'de.metas.inoutcandidate','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Produkt',70,60,1,1,TO_TIMESTAMP('2025-10-02 18:28:38.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:39.855Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:39.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2025-10-02T18:28:40.027Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754595
;

-- 2025-10-02T18:28:40.104Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754595)
;

-- 2025-10-02T18:28:40.258Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754595
;

-- 2025-10-02T18:28:40.335Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754595, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552556
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bewegte Menge
-- Column: M_ReceiptSchedule.QtyMoved
-- 2025-10-02T18:28:41.474Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549516,754596,0,548451,66,TO_TIMESTAMP('2025-10-02 18:28:40.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Bewegte Menge',170,100,1,1,TO_TIMESTAMP('2025-10-02 18:28:40.411000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:41.552Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:41.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542203)
;

-- 2025-10-02T18:28:41.708Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754596
;

-- 2025-10-02T18:28:41.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754596)
;

-- 2025-10-02T18:28:41.941Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754596
;

-- 2025-10-02T18:28:42.018Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754596, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552557
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Menge zu bewegen
-- Column: M_ReceiptSchedule.QtyToMove
-- 2025-10-02T18:28:43.167Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549517,754597,0,548451,139,TO_TIMESTAMP('2025-10-02 18:28:42.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Menge zu bewegen',190,90,1,1,TO_TIMESTAMP('2025-10-02 18:28:42.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:43.246Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:43.324Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542204)
;

-- 2025-10-02T18:28:43.402Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754597
;

-- 2025-10-02T18:28:43.481Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754597)
;

-- 2025-10-02T18:28:43.637Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754597
;

-- 2025-10-02T18:28:43.715Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754597, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552558
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Menge zu bewegen abw.
-- Column: M_ReceiptSchedule.QtyToMove_Override
-- 2025-10-02T18:28:44.854Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549518,754598,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:43.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','N','Menge zu bewegen abw.',220,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:43.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:44.931Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754598 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:45.009Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542205)
;

-- 2025-10-02T18:28:45.088Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754598
;

-- 2025-10-02T18:28:45.165Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754598)
;

-- 2025-10-02T18:28:45.324Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754598
;

-- 2025-10-02T18:28:45.400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754598, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552559
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Sektion
-- Column: M_ReceiptSchedule.AD_Org_ID
-- 2025-10-02T18:28:46.601Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549481,754599,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:45.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.inoutcandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','N','Y','N','Sektion',400,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:45.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:46.676Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:46.753Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-02T18:28:46.881Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754599
;

-- 2025-10-02T18:28:46.957Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754599)
;

-- 2025-10-02T18:28:47.110Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754599
;

-- 2025-10-02T18:28:47.188Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754599, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552560
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Standort
-- Column: M_ReceiptSchedule.C_BPartner_Location_ID
-- 2025-10-02T18:28:48.235Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549500,754600,1000322,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:47.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'de.metas.inoutcandidate','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Standort',280,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:47.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:48.311Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:48.389Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000322)
;

-- 2025-10-02T18:28:48.468Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754600
;

-- 2025-10-02T18:28:48.545Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754600)
;

-- 2025-10-02T18:28:48.698Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754600
;

-- 2025-10-02T18:28:48.774Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754600, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552561
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Standort abw.
-- Column: M_ReceiptSchedule.C_BP_Location_Override_ID
-- 2025-10-02T18:28:49.895Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549498,754601,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:48.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'de.metas.inoutcandidate','Identifiziert die Adresse des Geschäftspartners',0,'Y','N','Y','N','N','N','N','N','N','N','Y','Standort abw.',310,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:48.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:49.972Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:50.050Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541408)
;

-- 2025-10-02T18:28:50.128Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754601
;

-- 2025-10-02T18:28:50.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754601)
;

-- 2025-10-02T18:28:50.359Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754601
;

-- 2025-10-02T18:28:50.435Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754601, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552562
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2025-10-02T18:28:51.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549513,754602,1001551,0,548451,87,TO_TIMESTAMP('2025-10-02 18:28:50.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Beleg verarbeitet wurde. ',1,'de.metas.inoutcandidate','Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Verarbeitet',350,0,1,1,TO_TIMESTAMP('2025-10-02 18:28:50.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:51.568Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754602 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:51.645Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001551)
;

-- 2025-10-02T18:28:51.725Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754602
;

-- 2025-10-02T18:28:51.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754602)
;

-- 2025-10-02T18:28:51.957Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754602
;

-- 2025-10-02T18:28:52.035Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754602, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552563
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Wareneingangsdisposition
-- Column: M_ReceiptSchedule.M_ReceiptSchedule_ID
-- 2025-10-02T18:28:53.185Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549487,754603,1002522,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:52.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Wareneingangsdisposition',410,0,2,1,1,TO_TIMESTAMP('2025-10-02 18:28:52.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:53.266Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:53.346Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002522)
;

-- 2025-10-02T18:28:53.426Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754603
;

-- 2025-10-02T18:28:53.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754603)
;

-- 2025-10-02T18:28:53.660Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754603
;

-- 2025-10-02T18:28:53.739Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754603, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552564
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Maßeinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- 2025-10-02T18:28:54.868Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549524,754604,0,548451,88,TO_TIMESTAMP('2025-10-02 18:28:53.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'de.metas.inoutcandidate','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Maßeinheit',100,120,1,1,TO_TIMESTAMP('2025-10-02 18:28:53.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:54.947Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754604 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:55.025Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2025-10-02T18:28:55.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754604
;

-- 2025-10-02T18:28:55.187Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754604)
;

-- 2025-10-02T18:28:55.342Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754604
;

-- 2025-10-02T18:28:55.420Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754604, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552570
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Kopf-Aggregationsmerkmal
-- Column: M_ReceiptSchedule.HeaderAggregationKey
-- 2025-10-02T18:28:56.479Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549527,754605,1001257,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:55.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Kopf-Aggregationsmerkmal',370,0,999,1,TO_TIMESTAMP('2025-10-02 18:28:55.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:56.559Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754605 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:56.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001257)
;

-- 2025-10-02T18:28:56.716Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754605
;

-- 2025-10-02T18:28:56.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754605)
;

-- 2025-10-02T18:28:56.952Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754605
;

-- 2025-10-02T18:28:57.030Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754605, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552574
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Status
-- Column: M_ReceiptSchedule.Status
-- 2025-10-02T18:28:58.095Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549664,754606,1002611,0,548451,59,TO_TIMESTAMP('2025-10-02 18:28:57.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',2000,'de.metas.inoutcandidate','',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Status',360,0,999,1,TO_TIMESTAMP('2025-10-02 18:28:57.107000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:58.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754606 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:58.250Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002611)
;

-- 2025-10-02T18:28:58.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754606
;

-- 2025-10-02T18:28:58.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754606)
;

-- 2025-10-02T18:28:58.562Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754606
;

-- 2025-10-02T18:28:58.640Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754606, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552653
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- 2025-10-02T18:28:59.775Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549770,540059,754607,0,548451,0,TO_TIMESTAMP('2025-10-02 18:28:58.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','Y','Packbeschreibung',410,0,999,1,TO_TIMESTAMP('2025-10-02 18:28:58.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:28:59.855Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754607 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:28:59.933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542233)
;

-- 2025-10-02T18:29:00.013Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754607
;

-- 2025-10-02T18:29:00.091Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754607)
;

-- 2025-10-02T18:29:00.245Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754607
;

-- 2025-10-02T18:29:00.322Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754607, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552990
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Packvorschrift
-- Column: M_ReceiptSchedule.M_HU_PI_Item_Product_ID
-- 2025-10-02T18:29:01.401Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549769,540059,754608,1001546,0,548451,250,TO_TIMESTAMP('2025-10-02 18:29:00.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Packvorschrift',390,140,1,1,TO_TIMESTAMP('2025-10-02 18:29:00.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:01.479Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754608 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:01.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001546)
;

-- 2025-10-02T18:29:01.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754608
;

-- 2025-10-02T18:29:01.718Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754608)
;

-- 2025-10-02T18:29:01.879Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754608
;

-- 2025-10-02T18:29:01.955Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754608, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552991
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Verpackungskapazität
-- Column: M_ReceiptSchedule.QtyItemCapacity
-- 2025-10-02T18:29:03.006Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549771,540059,754609,1002431,0,548451,160,TO_TIMESTAMP('2025-10-02 18:29:02.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Verpackungskapazität',400,130,1,1,TO_TIMESTAMP('2025-10-02 18:29:02.033000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:03.082Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754609 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:03.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002431)
;

-- 2025-10-02T18:29:03.241Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754609
;

-- 2025-10-02T18:29:03.317Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754609)
;

-- 2025-10-02T18:29:03.478Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754609
;

-- 2025-10-02T18:29:03.555Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754609, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552992
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Merkmale
-- Column: M_ReceiptSchedule.M_AttributeSetInstance_ID
-- 2025-10-02T18:29:04.693Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549830,754610,0,548451,216,TO_TIMESTAMP('2025-10-02 18:29:03.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Merkmals Ausprägungen zum Produkt',10,'de.metas.inoutcandidate','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Merkmale',80,70,1,1,TO_TIMESTAMP('2025-10-02 18:29:03.633000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:04.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754610 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:04.852Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2025-10-02T18:29:04.935Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754610
;

-- 2025-10-02T18:29:05.014Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754610)
;

-- 2025-10-02T18:29:05.167Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754610
;

-- 2025-10-02T18:29:05.243Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754610, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553163
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bereitstellungszeit
-- Column: M_ReceiptSchedule.PreparationTime
-- 2025-10-02T18:29:06.388Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549840,754611,0,548451,136,TO_TIMESTAMP('2025-10-02 18:29:05.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereitstellungszeit',0,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Bereitstellungszeit',60,40,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:05.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:06.467Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754611 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:06.546Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542183)
;

-- 2025-10-02T18:29:06.625Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754611
;

-- 2025-10-02T18:29:06.702Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754611)
;

-- 2025-10-02T18:29:06.858Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754611
;

-- 2025-10-02T18:29:06.936Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754611, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553171
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- 2025-10-02T18:29:07.991Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549867,754612,1000582,0,548451,132,TO_TIMESTAMP('2025-10-02 18:29:07.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,3,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Qualitätsabzug %',110,160,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:07.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:08.070Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:08.148Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000582)
;

-- 2025-10-02T18:29:08.229Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754612
;

-- 2025-10-02T18:29:08.307Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754612)
;

-- 2025-10-02T18:29:08.462Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754612
;

-- 2025-10-02T18:29:08.540Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754612, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553209
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- 2025-10-02T18:29:09.661Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550197,754613,0,548451,105,TO_TIMESTAMP('2025-10-02 18:29:08.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Qualität-Notiz',120,170,0,999,1,TO_TIMESTAMP('2025-10-02 18:29:08.617000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:09.741Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:09.819Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542322)
;

-- 2025-10-02T18:29:09.897Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754613
;

-- 2025-10-02T18:29:09.975Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754613)
;

-- 2025-10-02T18:29:10.131Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754613
;

-- 2025-10-02T18:29:10.209Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754613, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553925
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2025-10-02T18:29:11.357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550277,754614,0,548451,150,TO_TIMESTAMP('2025-10-02 18:29:10.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Ziel-Lager',150,150,1,1,TO_TIMESTAMP('2025-10-02 18:29:10.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:11.435Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:11.512Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541643)
;

-- 2025-10-02T18:29:11.593Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754614
;

-- 2025-10-02T18:29:11.672Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754614)
;

-- 2025-10-02T18:29:11.826Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754614
;

-- 2025-10-02T18:29:11.903Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754614, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553964
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Verpackungsmaterial
-- Column: M_ReceiptSchedule.IsPackagingMaterial
-- 2025-10-02T18:29:13.038Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550587,754615,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:11.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Verpackungsmaterial',420,0,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:11.979000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:13.118Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754615 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:13.199Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542246)
;

-- 2025-10-02T18:29:13.279Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754615
;

-- 2025-10-02T18:29:13.357Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754615)
;

-- 2025-10-02T18:29:13.513Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754615
;

-- 2025-10-02T18:29:13.590Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754615, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554193
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Gebindekonfiguration
-- Column: M_ReceiptSchedule.M_HU_LUTU_Configuration_ID
-- 2025-10-02T18:29:14.715Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550962,540059,754616,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:13.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','Y','N','Gebindekonfiguration',380,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:13.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:14.795Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754616 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:14.873Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542486)
;

-- 2025-10-02T18:29:14.953Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754616
;

-- 2025-10-02T18:29:15.030Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754616)
;

-- 2025-10-02T18:29:15.190Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754616
;

-- 2025-10-02T18:29:15.274Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754616, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554538
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- 2025-10-02T18:29:16.411Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551296,540059,754617,0,548451,145,TO_TIMESTAMP('2025-10-02 18:29:15.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Bewegte TU-Menge',440,110,1,1,TO_TIMESTAMP('2025-10-02 18:29:15.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:16.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:16.567Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542576)
;

-- 2025-10-02T18:29:16.645Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754617
;

-- 2025-10-02T18:29:16.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754617)
;

-- 2025-10-02T18:29:16.880Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754617
;

-- 2025-10-02T18:29:16.957Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754617, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554875
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> QtyOrderedOverUnder
-- Column: M_ReceiptSchedule.QtyOrderedOverUnder
-- 2025-10-02T18:29:18.100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550508,754618,1000072,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:17.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,22,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','QtyOrderedOverUnder',450,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:17.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:18.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754618 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:18.262Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000072)
;

-- 2025-10-02T18:29:18.342Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754618
;

-- 2025-10-02T18:29:18.420Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754618)
;

-- 2025-10-02T18:29:18.577Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754618
;

-- 2025-10-02T18:29:18.656Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754618, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555022
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Qty Moved (With Issues)
-- Column: M_ReceiptSchedule.QtyMovedWithIssues
-- 2025-10-02T18:29:19.788Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551341,754619,0,548451,30,TO_TIMESTAMP('2025-10-02 18:29:18.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','Y','Qty Moved (With Issues)',180,180,1,1,TO_TIMESTAMP('2025-10-02 18:29:18.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:19.870Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754619 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:19.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542592)
;

-- 2025-10-02T18:29:20.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754619
;

-- 2025-10-02T18:29:20.114Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754619)
;

-- 2025-10-02T18:29:20.277Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754619
;

-- 2025-10-02T18:29:20.353Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754619, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555023
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> QtyOrderedTU
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- 2025-10-02T18:29:21.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556335,540059,754620,0,548451,145,TO_TIMESTAMP('2025-10-02 18:29:20.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','QtyOrderedTU',440,110,1,1,TO_TIMESTAMP('2025-10-02 18:29:20.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:21.567Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:21.645Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543296)
;

-- 2025-10-02T18:29:21.723Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754620
;

-- 2025-10-02T18:29:21.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754620)
;

-- 2025-10-02T18:29:21.958Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754620
;

-- 2025-10-02T18:29:22.034Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754620, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 557932
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bei Wareneing. mit Ziellager
-- Column: M_ReceiptSchedule.OnMaterialReceiptWithDestWarehouse
-- 2025-10-02T18:29:23.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559460,754621,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:22.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate','',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Bei Wareneing. mit Ziellager',450,190,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:22.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:23.251Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:23.330Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543911)
;

-- 2025-10-02T18:29:23.410Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754621
;

-- 2025-10-02T18:29:23.487Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754621)
;

-- 2025-10-02T18:29:23.643Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754621
;

-- 2025-10-02T18:29:23.724Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754621, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 563020
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Catch Einheit
-- Column: M_ReceiptSchedule.Catch_UOM_ID
-- 2025-10-02T18:29:24.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569778,754622,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:23.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Aus dem Produktstamm übenommene Catch Weight Einheit.',10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Catch Einheit',460,1,1,TO_TIMESTAMP('2025-10-02 18:29:23.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:25.026Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754622 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:25.104Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576953)
;

-- 2025-10-02T18:29:25.184Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754622
;

-- 2025-10-02T18:29:25.261Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754622)
;

-- 2025-10-02T18:29:25.417Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754622
;

-- 2025-10-02T18:29:25.493Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754622, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615394
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bewegte Catch-Menge
-- Column: M_ReceiptSchedule.QtyMovedInCatchUOM
-- 2025-10-02T18:29:26.700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569779,754623,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:25.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Bewegte Catch-Menge',470,1,1,TO_TIMESTAMP('2025-10-02 18:29:25.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:26.778Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754623 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:26.856Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577431)
;

-- 2025-10-02T18:29:26.937Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754623
;

-- 2025-10-02T18:29:27.014Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754623)
;

-- 2025-10-02T18:29:27.173Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754623
;

-- 2025-10-02T18:29:27.254Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754623, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615395
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Bewegte minderwertige Catch-Menge
-- Column: M_ReceiptSchedule.QtyMovedWithIssuesInCatchUOM
-- 2025-10-02T18:29:28.568Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569780,754624,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:27.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Bewegte minderwertige Catch-Menge',480,1,1,TO_TIMESTAMP('2025-10-02 18:29:27.333000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:28.647Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754624 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:28.725Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577432)
;

-- 2025-10-02T18:29:28.804Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754624
;

-- 2025-10-02T18:29:28.881Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754624)
;

-- 2025-10-02T18:29:29.042Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754624
;

-- 2025-10-02T18:29:29.118Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754624, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615396
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Lager Eff.
-- Column: M_ReceiptSchedule.M_Warehouse_Effective_ID
-- 2025-10-02T18:29:30.328Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570612,754625,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:29.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lager Eff.',490,1,1,TO_TIMESTAMP('2025-10-02 18:29:29.198000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:30.407Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754625 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:30.483Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577654)
;

-- 2025-10-02T18:29:30.563Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754625
;

-- 2025-10-02T18:29:30.641Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754625)
;

-- 2025-10-02T18:29:30.797Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754625
;

-- 2025-10-02T18:29:30.875Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754625, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615397
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Exportierbar ab
-- Column: M_ReceiptSchedule.CanBeExportedFrom
-- 2025-10-02T18:29:32.084Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571007,754626,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:30.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeitpunkt, ab dem der Datensatz exportiert werden kann',7,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Exportierbar ab',500,1,1,TO_TIMESTAMP('2025-10-02 18:29:30.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:32.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754626 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:32.241Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577792)
;

-- 2025-10-02T18:29:32.319Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754626
;

-- 2025-10-02T18:29:32.397Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754626)
;

-- 2025-10-02T18:29:32.552Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754626
;

-- 2025-10-02T18:29:32.630Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754626, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615398
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Export Status
-- Column: M_ReceiptSchedule.ExportStatus
-- 2025-10-02T18:29:33.856Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571008,754627,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:32.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,25,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Export Status',510,1,1,TO_TIMESTAMP('2025-10-02 18:29:32.707000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:33.935Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754627 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:34.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577791)
;

-- 2025-10-02T18:29:34.092Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754627
;

-- 2025-10-02T18:29:34.169Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754627)
;

-- 2025-10-02T18:29:34.326Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754627
;

-- 2025-10-02T18:29:34.404Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754627, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 615399
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Filter-Anz. mit Bestellung
-- Column: M_ReceiptSchedule.FilteredItemsWithSameC_Order_ID
-- 2025-10-02T18:29:35.544Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571516,754628,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:34.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzahl der Datensätze aus der selben Bestellung, je nach aktuell angewendetem Filter',0,'de.metas.inoutcandidate','Wenn Datensätze über die REST-API exportiert werden und die Anzahl der Positionen das Limit pro Aufruf übersteigt, dann erlaubt es diese Zahl dem Aufrufenden zu erkennen, ob es noch weitere Positionen für die selbe Befellung gibt.',0,'Y','N','N','N','N','N','N','N','N','N','N','Filter-Anz. mit Bestellung',460,200,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:34.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:35.626Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754628 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:35.704Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578118)
;

-- 2025-10-02T18:29:35.783Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754628
;

-- 2025-10-02T18:29:35.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754628)
;

-- 2025-10-02T18:29:36.015Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754628
;

-- 2025-10-02T18:29:36.091Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754628, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 617676
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> External resource URL
-- Column: M_ReceiptSchedule.ExternalResourceURL
-- 2025-10-02T18:29:37.225Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,571519,754629,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:36.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','External resource URL',460,200,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:36.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:37.303Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754629 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:37.380Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578068)
;

-- 2025-10-02T18:29:37.459Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754629
;

-- 2025-10-02T18:29:37.536Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754629)
;

-- 2025-10-02T18:29:37.693Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754629
;

-- 2025-10-02T18:29:37.782Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754629, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 617690
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Zugesagter Termin abw.
-- Column: M_ReceiptSchedule.DatePromised_Override
-- 2025-10-02T18:29:38.999Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579326,754630,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:37.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',7,'de.metas.inoutcandidate',0,'Y','N','Y','N','N','N','N','N','N','N','N','Zugesagter Termin abw.',520,1,1,TO_TIMESTAMP('2025-10-02 18:29:37.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:39.079Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754630 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:39.158Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542184)
;

-- 2025-10-02T18:29:39.237Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754630
;

-- 2025-10-02T18:29:39.314Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754630)
;

-- 2025-10-02T18:29:39.468Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754630
;

-- 2025-10-02T18:29:39.545Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754630, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 680646
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Zugesagter Termin eff.
-- Column: M_ReceiptSchedule.DatePromised_Effective
-- 2025-10-02T18:29:40.752Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579327,754631,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:39.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',7,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugesagter Termin eff.',530,210,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:39.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:40.832Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754631 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:40.908Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542422)
;

-- 2025-10-02T18:29:40.986Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754631
;

-- 2025-10-02T18:29:41.062Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754631)
;

-- 2025-10-02T18:29:41.219Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754631
;

-- 2025-10-02T18:29:41.296Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754631, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 680647
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2025-10-02T18:29:42.512Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588327,754632,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:41.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden',0,'de.metas.inoutcandidate','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Referenz',540,220,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:41.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:42.590Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754632 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:42.667Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952)
;

-- 2025-10-02T18:29:42.749Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754632
;

-- 2025-10-02T18:29:42.825Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754632)
;

-- 2025-10-02T18:29:42.980Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754632
;

-- 2025-10-02T18:29:43.056Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754632, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 728771
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2025-10-02T18:29:44.265Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589629,754633,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:43.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'@MaterialReceiptInfo@!=NULL','de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Wareneingangsinfo',550,230,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:43.135000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:44.344Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754633 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:44.420Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583461)
;

-- 2025-10-02T18:29:44.500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754633
;

-- 2025-10-02T18:29:44.578Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754633)
;

-- 2025-10-02T18:29:44.733Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754633
;

-- 2025-10-02T18:29:44.810Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754633, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 735600
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-10-02T18:29:46.041Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590640,754634,0,548451,0,TO_TIMESTAMP('2025-10-02 18:29:44.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,0,'Y','N','Y','Y','N','N','N','N','N','N','N',0,'KW',0,560,240,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:44.889000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:46.119Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754634 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:46.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583880)
;

-- 2025-10-02T18:29:46.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754634
;

-- 2025-10-02T18:29:46.360Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754634)
;

-- 2025-10-02T18:29:46.516Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754634
;

-- 2025-10-02T18:29:46.593Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754634, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 752521
;

-- Tab: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang
-- Table: M_ReceiptSchedule_Alloc
-- 2025-10-02T18:29:47.735Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,549608,572721,0,548452,540530,541954,'Y',TO_TIMESTAMP('2025-10-02 18:29:46.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inoutcandidate','N','N','A','M_ReceiptSchedule_Alloc','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Zugeordneter Wareneingang',549487,'N',20,1,TO_TIMESTAMP('2025-10-02 18:29:46.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_ReceiptSchedule_Alloc.M_InOutLine_ID IS NOT NULL')
;

-- 2025-10-02T18:29:47.815Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548452 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-02T18:29:47.893Z
/* DDL */  select update_tab_translation_from_ad_element(572721)
;

-- 2025-10-02T18:29:47.973Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548452)
;

-- 2025-10-02T18:29:48.129Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548452
;

-- 2025-10-02T18:29:48.208Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548452, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540530
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Aktiv
-- Column: M_ReceiptSchedule_Alloc.IsActive
-- 2025-10-02T18:29:49.967Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549604,754635,0,548452,0,TO_TIMESTAMP('2025-10-02 18:29:48.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.inoutcandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',80,80,1,1,TO_TIMESTAMP('2025-10-02 18:29:48.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:50.045Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754635 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:50.124Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-02T18:29:50.257Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754635
;

-- 2025-10-02T18:29:50.336Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754635)
;

-- 2025-10-02T18:29:50.493Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754635
;

-- 2025-10-02T18:29:50.572Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754635, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552642
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Aktualisiert
-- Column: M_ReceiptSchedule_Alloc.Updated
-- 2025-10-02T18:29:51.785Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549605,754636,0,548452,0,TO_TIMESTAMP('2025-10-02 18:29:50.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.inoutcandidate','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',90,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:50.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:51.865Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754636 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:51.947Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2025-10-02T18:29:52.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754636
;

-- 2025-10-02T18:29:52.140Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754636)
;

-- 2025-10-02T18:29:52.294Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754636
;

-- 2025-10-02T18:29:52.372Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754636, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552643
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Aktualisiert durch
-- Column: M_ReceiptSchedule_Alloc.UpdatedBy
-- 2025-10-02T18:29:53.589Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549606,754637,0,548452,0,TO_TIMESTAMP('2025-10-02 18:29:52.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.inoutcandidate','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',100,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:52.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:53.668Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754637 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:53.747Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2025-10-02T18:29:53.854Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754637
;

-- 2025-10-02T18:29:53.932Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754637)
;

-- 2025-10-02T18:29:54.087Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754637
;

-- 2025-10-02T18:29:54.165Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754637, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552644
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Erstellt
-- Column: M_ReceiptSchedule_Alloc.Created
-- 2025-10-02T18:29:55.379Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549602,754638,0,548452,0,TO_TIMESTAMP('2025-10-02 18:29:54.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.inoutcandidate','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt',110,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:54.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:55.456Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754638 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:55.533Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-10-02T18:29:55.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754638
;

-- 2025-10-02T18:29:55.717Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754638)
;

-- 2025-10-02T18:29:55.873Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754638
;

-- 2025-10-02T18:29:55.950Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754638, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552645
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Erstellt durch
-- Column: M_ReceiptSchedule_Alloc.CreatedBy
-- 2025-10-02T18:29:57.157Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549603,754639,0,548452,0,TO_TIMESTAMP('2025-10-02 18:29:56.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.inoutcandidate','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',120,0,1,1,TO_TIMESTAMP('2025-10-02 18:29:56.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:57.236Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754639 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:57.313Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2025-10-02T18:29:57.419Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754639
;

-- 2025-10-02T18:29:57.495Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754639)
;

-- 2025-10-02T18:29:57.652Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754639
;

-- 2025-10-02T18:29:57.729Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754639, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552646
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Mandant
-- Column: M_ReceiptSchedule_Alloc.AD_Client_ID
-- 2025-10-02T18:29:58.865Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549600,754640,0,548452,0,TO_TIMESTAMP('2025-10-02 18:29:57.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.inoutcandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2025-10-02 18:29:57.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:29:58.943Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754640 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:29:59.022Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-02T18:29:59.152Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754640
;

-- 2025-10-02T18:29:59.229Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754640)
;

-- 2025-10-02T18:29:59.387Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754640
;

-- 2025-10-02T18:29:59.465Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754640, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552647
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Sektion
-- Column: M_ReceiptSchedule_Alloc.AD_Org_ID
-- 2025-10-02T18:30:00.610Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549601,754641,0,548452,0,TO_TIMESTAMP('2025-10-02 18:29:59.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.inoutcandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Sektion',20,20,1,1,TO_TIMESTAMP('2025-10-02 18:29:59.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:00.690Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754641 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:00.767Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-02T18:30:00.903Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754641
;

-- 2025-10-02T18:30:00.980Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754641)
;

-- 2025-10-02T18:30:01.136Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754641
;

-- 2025-10-02T18:30:01.212Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754641, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552648
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Lieferung/Wareneingang
-- Column: M_ReceiptSchedule_Alloc.M_InOut_ID
-- 2025-10-02T18:30:02.287Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549609,754642,1002284,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:01.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material Shipment Document',10,'de.metas.inoutcandidate','The Material Shipment / Receipt ',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Lieferung/Wareneingang',30,30,1,1,TO_TIMESTAMP('2025-10-02 18:30:01.290000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:02.367Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754642 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:02.447Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1002284)
;

-- 2025-10-02T18:30:02.527Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754642
;

-- 2025-10-02T18:30:02.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754642)
;

-- 2025-10-02T18:30:02.759Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754642
;

-- 2025-10-02T18:30:02.840Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754642, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552649
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Versand-/Wareneingangsposition
-- Column: M_ReceiptSchedule_Alloc.M_InOutLine_ID
-- 2025-10-02T18:30:03.988Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549610,754643,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:02.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Position auf Versand- oder Wareneingangsbeleg',10,'de.metas.inoutcandidate','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Versand-/Wareneingangsposition',40,40,1,1,TO_TIMESTAMP('2025-10-02 18:30:02.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:04.068Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754643 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:04.148Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1026)
;

-- 2025-10-02T18:30:04.227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754643
;

-- 2025-10-02T18:30:04.303Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754643)
;

-- 2025-10-02T18:30:04.456Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754643
;

-- 2025-10-02T18:30:04.535Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754643, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552650
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Wareneingangsdispo - Wareneingangszeile
-- Column: M_ReceiptSchedule_Alloc.M_ReceiptSchedule_Alloc_ID
-- 2025-10-02T18:30:05.758Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549607,754644,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:04.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','Wareneingangsdispo - Wareneingangszeile',130,1,1,TO_TIMESTAMP('2025-10-02 18:30:04.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:05.838Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754644 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:05.919Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542209)
;

-- 2025-10-02T18:30:05.998Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754644
;

-- 2025-10-02T18:30:06.076Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754644)
;

-- 2025-10-02T18:30:06.232Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754644
;

-- 2025-10-02T18:30:06.310Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754644, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552651
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Wareneingangsdisposition
-- Column: M_ReceiptSchedule_Alloc.M_ReceiptSchedule_ID
-- 2025-10-02T18:30:07.449Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549608,754645,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:06.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Wareneingangsdisposition',70,70,1,1,TO_TIMESTAMP('2025-10-02 18:30:06.388000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:07.528Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754645 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:07.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542202)
;

-- 2025-10-02T18:30:07.688Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754645
;

-- 2025-10-02T18:30:07.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754645)
;

-- 2025-10-02T18:30:07.922Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754645
;

-- 2025-10-02T18:30:08Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754645, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 552652
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Zugewiesene Menge
-- Column: M_ReceiptSchedule_Alloc.QtyAllocated
-- 2025-10-02T18:30:09.139Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549831,754646,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:08.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Zugewiesene Menge',90,90,0,1,1,TO_TIMESTAMP('2025-10-02 18:30:08.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:09.217Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754646 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:09.294Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542253)
;

-- 2025-10-02T18:30:09.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754646
;

-- 2025-10-02T18:30:09.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754646)
;

-- 2025-10-02T18:30:09.603Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754646
;

-- 2025-10-02T18:30:09.681Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754646, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553166
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> LU
-- Column: M_ReceiptSchedule_Alloc.M_LU_HU_ID
-- 2025-10-02T18:30:10.825Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549837,540059,754647,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:09.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Loading Unit',10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','LU',120,120,1,1,TO_TIMESTAMP('2025-10-02 18:30:09.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:10.903Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754647 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:10.982Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542455)
;

-- 2025-10-02T18:30:11.062Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754647
;

-- 2025-10-02T18:30:11.138Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754647)
;

-- 2025-10-02T18:30:11.293Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754647
;

-- 2025-10-02T18:30:11.370Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754647, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553167
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Handling Units Item
-- Column: M_ReceiptSchedule_Alloc.M_HU_Item_ID
-- 2025-10-02T18:30:12.591Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549838,540059,754648,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:11.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','Y','Y','Handling Units Item',140,0,1,1,TO_TIMESTAMP('2025-10-02 18:30:11.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:12.668Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754648 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:12.746Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542130)
;

-- 2025-10-02T18:30:12.824Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754648
;

-- 2025-10-02T18:30:12.900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754648)
;

-- 2025-10-02T18:30:13.053Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754648
;

-- 2025-10-02T18:30:13.129Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754648, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553168
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> HU-Transaktionszeile
-- Column: M_ReceiptSchedule_Alloc.M_HU_Trx_Line_ID
-- 2025-10-02T18:30:14.190Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549839,540059,754649,1000196,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:13.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','HU-Transaktionszeile',150,150,1,1,TO_TIMESTAMP('2025-10-02 18:30:13.207000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:14.268Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754649 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:14.345Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000196)
;

-- 2025-10-02T18:30:14.423Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754649
;

-- 2025-10-02T18:30:14.499Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754649)
;

-- 2025-10-02T18:30:14.654Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754649
;

-- 2025-10-02T18:30:14.731Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754649, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553169
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Zugewiesene HU-Menge
-- Column: M_ReceiptSchedule_Alloc.HU_QtyAllocated
-- 2025-10-02T18:30:15.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550201,540059,754650,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:14.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugeordnete (Lager-)Menge aus der jeweiligen HU, in der Maßeinheit des Wareneingangs-Dispo-Datensatzes.',10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Zugewiesene HU-Menge',110,110,1,1,TO_TIMESTAMP('2025-10-02 18:30:14.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:15.952Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754650 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:16.029Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542324)
;

-- 2025-10-02T18:30:16.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754650
;

-- 2025-10-02T18:30:16.183Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754650)
;

-- 2025-10-02T18:30:16.337Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754650
;

-- 2025-10-02T18:30:16.413Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754650, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 553929
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Qualitätsabzug %
-- Column: M_ReceiptSchedule_Alloc.QualityDiscountPercent
-- 2025-10-02T18:30:17.550Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550581,754651,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:16.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Qualitätsabzug %',50,50,1,1,TO_TIMESTAMP('2025-10-02 18:30:16.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:17.627Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754651 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:17.707Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542262)
;

-- 2025-10-02T18:30:17.787Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754651
;

-- 2025-10-02T18:30:17.866Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754651)
;

-- 2025-10-02T18:30:18.020Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754651
;

-- 2025-10-02T18:30:18.097Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754651, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554185
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Qualität-Notiz
-- Column: M_ReceiptSchedule_Alloc.QualityNote
-- 2025-10-02T18:30:19.234Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550582,754652,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:18.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,50,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Qualität-Notiz',60,60,999,1,TO_TIMESTAMP('2025-10-02 18:30:18.175000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:19.312Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754652 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:19.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542322)
;

-- 2025-10-02T18:30:19.466Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754652
;

-- 2025-10-02T18:30:19.544Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754652)
;

-- 2025-10-02T18:30:19.699Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754652
;

-- 2025-10-02T18:30:19.777Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754652, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554186
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> TU
-- Column: M_ReceiptSchedule_Alloc.M_TU_HU_ID
-- 2025-10-02T18:30:20.939Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550841,540059,754653,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:19.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Trading Unit',0,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','TU',130,130,0,1,1,TO_TIMESTAMP('2025-10-02 18:30:19.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:21.018Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754653 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:21.095Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542462)
;

-- 2025-10-02T18:30:21.175Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754653
;

-- 2025-10-02T18:30:21.252Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754653)
;

-- 2025-10-02T18:30:21.407Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754653
;

-- 2025-10-02T18:30:21.485Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754653, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554354
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Minderwertige Menge
-- Column: M_ReceiptSchedule_Alloc.QtyWithIssues
-- 2025-10-02T18:30:22.545Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551340,754654,1001446,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:21.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.',10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Minderwertige Menge',100,100,1,1,TO_TIMESTAMP('2025-10-02 18:30:21.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:22.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754654 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:22.702Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1001446)
;

-- 2025-10-02T18:30:22.781Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754654
;

-- 2025-10-02T18:30:22.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754654)
;

-- 2025-10-02T18:30:23.014Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754654
;

-- 2025-10-02T18:30:23.092Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754654, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555021
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> CU Handling Unit (VHU)
-- Column: M_ReceiptSchedule_Alloc.VHU_ID
-- 2025-10-02T18:30:24.156Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_FieldGroup_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,551598,540059,754655,1000900,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:23.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','CU Handling Unit (VHU)',140,140,1,1,TO_TIMESTAMP('2025-10-02 18:30:23.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:24.234Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754655 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:24.312Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000900)
;

-- 2025-10-02T18:30:24.390Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754655
;

-- 2025-10-02T18:30:24.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754655)
;

-- 2025-10-02T18:30:24.619Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754655
;

-- 2025-10-02T18:30:24.696Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754655, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555258
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> Belegstatus
-- Column: M_ReceiptSchedule_Alloc.DocStatus
-- 2025-10-02T18:30:25.825Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552792,754656,0,548452,0,TO_TIMESTAMP('2025-10-02 18:30:24.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document',0,'de.metas.inoutcandidate','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Belegstatus',35,35,0,1,1,TO_TIMESTAMP('2025-10-02 18:30:24.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:25.903Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754656 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:25.986Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289)
;

-- 2025-10-02T18:30:26.085Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754656
;

-- 2025-10-02T18:30:26.168Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754656)
;

-- 2025-10-02T18:30:26.335Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754656
;

-- 2025-10-02T18:30:26.413Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754656, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 556372
;

-- Tab: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment
-- Table: M_HU_Assignment
-- 2025-10-02T18:30:27.542Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,550380,573836,0,548453,540569,541954,'Y',TO_TIMESTAMP('2025-10-02 18:30:26.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inoutcandidate','N','N','A','M_HU_Assignment','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N','Handling Unit Assignment',549487,'N',20,1,TO_TIMESTAMP('2025-10-02 18:30:26.491000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M_HU_Assignment.AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table where tablename = ''M_ReceiptSchedule'')')
;

-- 2025-10-02T18:30:27.619Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548453 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-02T18:30:27.699Z
/* DDL */  select update_tab_translation_from_ad_element(573836)
;

-- 2025-10-02T18:30:27.781Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548453)
;

-- 2025-10-02T18:30:27.937Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 548453
;

-- 2025-10-02T18:30:28.012Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 548453, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 540624
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> Mandant
-- Column: M_HU_Assignment.AD_Client_ID
-- 2025-10-02T18:30:29.758Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550370,754657,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:28.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.inoutcandidate','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','Y','Y','N','N','N','N','N','Y','N','Mandant',10,10,1,1,TO_TIMESTAMP('2025-10-02 18:30:28.705000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:29.835Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754657 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:29.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-02T18:30:30.046Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754657
;

-- 2025-10-02T18:30:30.122Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754657)
;

-- 2025-10-02T18:30:30.279Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754657
;

-- 2025-10-02T18:30:30.355Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754657, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554887
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> Sektion
-- Column: M_HU_Assignment.AD_Org_ID
-- 2025-10-02T18:30:31.502Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550371,754658,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:30.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.inoutcandidate','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','Y','N','N','N','N','N','N','Y','Sektion',20,20,1,1,TO_TIMESTAMP('2025-10-02 18:30:30.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:31.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754658 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:31.659Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-02T18:30:31.796Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754658
;

-- 2025-10-02T18:30:31.872Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754658)
;

-- 2025-10-02T18:30:32.027Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754658
;

-- 2025-10-02T18:30:32.104Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754658, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554888
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> Aktiv
-- Column: M_HU_Assignment.IsActive
-- 2025-10-02T18:30:33.228Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550374,754659,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:32.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.inoutcandidate','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Aktiv',50,50,1,1,TO_TIMESTAMP('2025-10-02 18:30:32.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:33.307Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754659 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:33.387Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-02T18:30:33.524Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754659
;

-- 2025-10-02T18:30:33.600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754659)
;

-- 2025-10-02T18:30:33.756Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754659
;

-- 2025-10-02T18:30:33.832Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754659, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554889
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> M_HU_Assignment
-- Column: M_HU_Assignment.M_HU_Assignment_ID
-- 2025-10-02T18:30:35.054Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550377,754660,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:33.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','N','N','N','N','N','N','N','N','N','M_HU_Assignment',60,1,1,TO_TIMESTAMP('2025-10-02 18:30:33.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:35.132Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754660 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:35.210Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542385)
;

-- 2025-10-02T18:30:35.290Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754660
;

-- 2025-10-02T18:30:35.367Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754660)
;

-- 2025-10-02T18:30:35.525Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754660
;

-- 2025-10-02T18:30:35.604Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754660, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554890
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> Handling Units
-- Column: M_HU_Assignment.M_HU_ID
-- 2025-10-02T18:30:36.670Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550378,754661,1000201,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:35.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Handling Units',30,30,1,1,TO_TIMESTAMP('2025-10-02 18:30:35.687000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:36.748Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754661 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:36.828Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1000201)
;

-- 2025-10-02T18:30:36.907Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754661
;

-- 2025-10-02T18:30:36.984Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754661)
;

-- 2025-10-02T18:30:37.142Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754661
;

-- 2025-10-02T18:30:37.219Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754661, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554891
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> DB-Tabelle
-- Column: M_HU_Assignment.AD_Table_ID
-- 2025-10-02T18:30:38.506Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550379,754662,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:37.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',10,'de.metas.inoutcandidate','The Database Table provides the information of the table definition',0,'Y','N','N','N','N','N','N','N','N','N','N','DB-Tabelle',70,1,1,TO_TIMESTAMP('2025-10-02 18:30:37.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:38.586Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754662 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:38.668Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2025-10-02T18:30:38.751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754662
;

-- 2025-10-02T18:30:38.829Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754662)
;

-- 2025-10-02T18:30:38.985Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754662
;

-- 2025-10-02T18:30:39.063Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754662, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554892
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> Datensatz-ID
-- Column: M_HU_Assignment.Record_ID
-- 2025-10-02T18:30:40.302Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550380,754663,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:39.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID',10,'de.metas.inoutcandidate','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','N','N','N','N','N','N','N','N','N','N','Datensatz-ID',80,1,1,TO_TIMESTAMP('2025-10-02 18:30:39.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:40.380Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754663 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:40.457Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538)
;

-- 2025-10-02T18:30:40.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754663
;

-- 2025-10-02T18:30:40.616Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754663)
;

-- 2025-10-02T18:30:40.772Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754663
;

-- 2025-10-02T18:30:40.849Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754663, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554893
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> Menge
-- Column: M_HU_Assignment.Qty
-- 2025-10-02T18:30:41.987Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,550425,754664,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:40.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge',14,'de.metas.inoutcandidate','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Menge',40,40,1,1,TO_TIMESTAMP('2025-10-02 18:30:40.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:42.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754664 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:42.141Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2025-10-02T18:30:42.222Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754664
;

-- 2025-10-02T18:30:42.299Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754664)
;

-- 2025-10-02T18:30:42.453Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754664
;

-- 2025-10-02T18:30:42.532Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754664, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 554894
;

-- Field: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> Produkte
-- Column: M_HU_Assignment.Products
-- 2025-10-02T18:30:43.662Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,552291,754665,0,548453,0,TO_TIMESTAMP('2025-10-02 18:30:42.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'de.metas.inoutcandidate',0,'Y','N','Y','Y','N','N','N','N','N','N','N','Produkte',45,45,1,1,TO_TIMESTAMP('2025-10-02 18:30:42.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:43.742Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754665 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-02T18:30:43.821Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542806)
;

-- 2025-10-02T18:30:43.900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754665
;

-- 2025-10-02T18:30:43.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754665)
;

-- 2025-10-02T18:30:44.135Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 754665
;

-- 2025-10-02T18:30:44.212Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 754665, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 555859
;

-- Tab: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate)
-- UI Section: main
-- 2025-10-02T18:30:45.132Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548451,546976,TO_TIMESTAMP('2025-10-02 18:30:44.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2025-10-02 18:30:44.467000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-02T18:30:45.209Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546976 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-10-02T18:30:45.367Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546976
;

-- 2025-10-02T18:30:45.444Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546976, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540063
;

-- UI Section: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main
-- UI Column: 10
-- 2025-10-02T18:30:46.411Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548500,546976,TO_TIMESTAMP('2025-10-02 18:30:45.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-02 18:30:45.719000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: default
-- 2025-10-02T18:30:47.404Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548500,553588,TO_TIMESTAMP('2025-10-02 18:30:46.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-10-02 18:30:46.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> default.Geschäftspartner
-- Column: M_ReceiptSchedule.C_BPartner_ID
-- 2025-10-02T18:30:48.887Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,UIStyle,Updated,UpdatedBy,WidgetSize) VALUES (0,754583,0,548451,553588,637563,'F',TO_TIMESTAMP('2025-10-02 18:30:47.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Geschäftspartner',5,30,10,'primary',TO_TIMESTAMP('2025-10-02 18:30:47.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-10-02T18:30:50.301Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754600,0,542163,637563,TO_TIMESTAMP('2025-10-02 18:30:49.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-10-02 18:30:49.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:30:51.217Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754571,0,542164,637563,TO_TIMESTAMP('2025-10-02 18:30:50.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,'widget',TO_TIMESTAMP('2025-10-02 18:30:50.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> default.Produkt
-- Column: M_ReceiptSchedule.M_Product_ID
-- 2025-10-02T18:30:52.358Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,UIStyle,Updated,UpdatedBy,WidgetSize) VALUES (0,754595,0,548451,553588,637564,'F',TO_TIMESTAMP('2025-10-02 18:30:51.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Produkt',10,40,20,'',TO_TIMESTAMP('2025-10-02 18:30:51.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-10-02T18:30:53.777Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,754608,0,542165,637564,TO_TIMESTAMP('2025-10-02 18:30:52.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,'widget',TO_TIMESTAMP('2025-10-02 18:30:52.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> default.Merkmale
-- Column: M_ReceiptSchedule.M_AttributeSetInstance_ID
-- 2025-10-02T18:30:54.931Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754610,0,548451,553588,637565,'F',TO_TIMESTAMP('2025-10-02 18:30:53.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','screen','Merkmale',20,50,0,TO_TIMESTAMP('2025-10-02 18:30:53.933000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> default.Bestellt
-- Column: M_ReceiptSchedule.QtyOrdered
-- 2025-10-02T18:30:56.240Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754577,0,548451,553588,637566,'F',TO_TIMESTAMP('2025-10-02 18:30:55.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Bestellt',30,90,40,TO_TIMESTAMP('2025-10-02 18:30:55.244000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> default.Eingegangen
-- Column: M_ReceiptSchedule.QtyMoved
-- 2025-10-02T18:30:57.546Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754596,0,548451,553588,637567,'F',TO_TIMESTAMP('2025-10-02 18:30:56.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Eingegangen',35,100,0,TO_TIMESTAMP('2025-10-02 18:30:56.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> default.Mengeneinheit
-- Column: M_ReceiptSchedule.C_UOM_ID
-- 2025-10-02T18:30:58.855Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754604,0,548451,553588,637568,'F',TO_TIMESTAMP('2025-10-02 18:30:57.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Mengeneinheit',40,110,50,TO_TIMESTAMP('2025-10-02 18:30:57.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> default.Referenz
-- Column: M_ReceiptSchedule.POReference
-- 2025-10-02T18:31:00.161Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754632,0,548451,553588,637569,'F',TO_TIMESTAMP('2025-10-02 18:30:59.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','Y','N','N',0,'Referenz',50,20,0,TO_TIMESTAMP('2025-10-02 18:30:59.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: target
-- 2025-10-02T18:31:01.137Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548500,553589,TO_TIMESTAMP('2025-10-02 18:31:00.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','target',20,TO_TIMESTAMP('2025-10-02 18:31:00.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> target.Wareneingangsinfo
-- Column: M_ReceiptSchedule.MaterialReceiptInfo
-- 2025-10-02T18:31:02.591Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754633,0,548451,553589,637570,'F',TO_TIMESTAMP('2025-10-02 18:31:01.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Wareneingangsinfo',5,0,0,TO_TIMESTAMP('2025-10-02 18:31:01.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> target.Ziel-Lager
-- Column: M_ReceiptSchedule.M_Warehouse_Dest_ID
-- 2025-10-02T18:31:03.886Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754614,0,548451,553589,637571,'F',TO_TIMESTAMP('2025-10-02 18:31:02.901000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Ziel-Lager',10,170,0,TO_TIMESTAMP('2025-10-02 18:31:02.901000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> target.Packbeschreibung
-- Column: M_ReceiptSchedule.PackDescription
-- 2025-10-02T18:31:05.184Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754607,0,548451,553589,637572,'F',TO_TIMESTAMP('2025-10-02 18:31:04.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','screen','Packbeschreibung',20,80,0,TO_TIMESTAMP('2025-10-02 18:31:04.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> target.Bewegte TU-Menge
-- Column: M_ReceiptSchedule.QtyMovedTU
-- 2025-10-02T18:31:06.491Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754617,0,548451,553589,637573,'F',TO_TIMESTAMP('2025-10-02 18:31:05.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','screen','Bewegte TU-Menge',30,70,0,TO_TIMESTAMP('2025-10-02 18:31:05.494000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 10 -> target.Bestellte TU-Menge
-- Column: M_ReceiptSchedule.QtyOrderedTU
-- 2025-10-02T18:31:07.796Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754620,0,548451,553589,637574,'F',TO_TIMESTAMP('2025-10-02 18:31:06.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','screen','Bestellte TU-Menge',40,60,0,TO_TIMESTAMP('2025-10-02 18:31:06.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Section: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main
-- UI Column: 20
-- 2025-10-02T18:31:08.781Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548501,546976,TO_TIMESTAMP('2025-10-02 18:31:08.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-10-02 18:31:08.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20
-- UI Element Group: dates
-- 2025-10-02T18:31:09.756Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548501,553590,TO_TIMESTAMP('2025-10-02 18:31:09.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dates',10,TO_TIMESTAMP('2025-10-02 18:31:09.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> dates.Bestellung
-- Column: M_ReceiptSchedule.C_Order_ID
-- 2025-10-02T18:31:11.227Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754573,0,548451,553590,637575,'F',TO_TIMESTAMP('2025-10-02 18:31:10.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Bestellung',10,10,0,TO_TIMESTAMP('2025-10-02 18:31:10.073000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> dates.KW
-- Column: M_ReceiptSchedule.CalendarWeek
-- 2025-10-02T18:31:12.541Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754634,0,548451,553590,637576,'F',TO_TIMESTAMP('2025-10-02 18:31:11.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N',0,'KW',15,120,0,TO_TIMESTAMP('2025-10-02 18:31:11.537000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> dates.Bestelldatum
-- Column: M_ReceiptSchedule.DateOrdered
-- 2025-10-02T18:31:13.838Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754574,0,548451,553590,637577,'F',TO_TIMESTAMP('2025-10-02 18:31:12.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Bestelldatum',20,0,0,TO_TIMESTAMP('2025-10-02 18:31:12.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> dates.Eingangsdatum
-- Column: M_ReceiptSchedule.MovementDate
-- 2025-10-02T18:31:15.154Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754578,0,548451,553590,637578,'F',TO_TIMESTAMP('2025-10-02 18:31:14.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','Y','N','Eingangsdatum',30,130,30,TO_TIMESTAMP('2025-10-02 18:31:14.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> dates.Zugesagter Termin abw.
-- Column: M_ReceiptSchedule.DatePromised_Override
-- 2025-10-02T18:31:16.455Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754630,0,548451,553590,637579,'F',TO_TIMESTAMP('2025-10-02 18:31:15.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Y','N','N','Y','N','N','N',0,'Zugesagter Termin abw.',32,0,0,TO_TIMESTAMP('2025-10-02 18:31:15.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> dates.Zuges. Termin (eff.)
-- Column: M_ReceiptSchedule.DatePromised_Effective
-- 2025-10-02T18:31:17.762Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754631,0,548451,553590,637580,'F',TO_TIMESTAMP('2025-10-02 18:31:16.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','Y','N','N','Y','N','N','N',0,'Zuges. Termin (eff.)',34,0,0,TO_TIMESTAMP('2025-10-02 18:31:16.767000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> dates.Belegart
-- Column: M_ReceiptSchedule.C_DocType_ID
-- 2025-10-02T18:31:19.060Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754576,0,548451,553590,637581,'F',TO_TIMESTAMP('2025-10-02 18:31:18.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Belegart',40,0,0,TO_TIMESTAMP('2025-10-02 18:31:18.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Column: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20
-- UI Element Group: org
-- 2025-10-02T18:31:20.033Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548501,553591,TO_TIMESTAMP('2025-10-02 18:31:19.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-10-02 18:31:19.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> org.Lager
-- Column: M_ReceiptSchedule.M_Warehouse_ID
-- 2025-10-02T18:31:21.489Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754585,0,548451,553591,637582,'F',TO_TIMESTAMP('2025-10-02 18:31:20.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Lager',10,180,0,TO_TIMESTAMP('2025-10-02 18:31:20.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> org.Sektion
-- Column: M_ReceiptSchedule.AD_Org_ID
-- 2025-10-02T18:31:22.789Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754599,0,548451,553591,637583,'F',TO_TIMESTAMP('2025-10-02 18:31:21.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Sektion',20,0,0,TO_TIMESTAMP('2025-10-02 18:31:21.797000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> org.Status
-- Column: M_ReceiptSchedule.Status
-- 2025-10-02T18:31:24.088Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754606,0,548451,553591,637584,'F',TO_TIMESTAMP('2025-10-02 18:31:23.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N','Status',30,0,0,TO_TIMESTAMP('2025-10-02 18:31:23.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> main -> 20 -> org.Verarbeitet
-- Column: M_ReceiptSchedule.Processed
-- 2025-10-02T18:31:25.390Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754602,0,548451,553591,637585,'F',TO_TIMESTAMP('2025-10-02 18:31:24.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Verarbeitet',40,160,0,TO_TIMESTAMP('2025-10-02 18:31:24.401000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate)
-- UI Section: advanced edit
-- 2025-10-02T18:31:26.358Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548451,546977,TO_TIMESTAMP('2025-10-02 18:31:25.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-10-02 18:31:25.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'advanced edit')
;

-- 2025-10-02T18:31:26.435Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546977 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-10-02T18:31:26.589Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546977
;

-- 2025-10-02T18:31:26.668Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546977, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540571
;

-- UI Section: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit
-- UI Column: 10
-- 2025-10-02T18:31:27.561Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548502,546977,TO_TIMESTAMP('2025-10-02 18:31:26.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-02 18:31:26.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10
-- UI Element Group: advanced edit
-- 2025-10-02T18:31:28.525Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548502,553592,TO_TIMESTAMP('2025-10-02 18:31:27.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','advanced edit',10,'primary',TO_TIMESTAMP('2025-10-02 18:31:27.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bereitstellungszeit
-- Column: M_ReceiptSchedule.PreparationTime
-- 2025-10-02T18:31:30.031Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754611,0,548451,553592,637586,'F',TO_TIMESTAMP('2025-10-02 18:31:28.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bereitstellungszeit','Y','Y','N','Y','N','N','N','Bereitstellungszeit',60,0,0,TO_TIMESTAMP('2025-10-02 18:31:28.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualitätsabzug %
-- Column: M_ReceiptSchedule.QualityDiscountPercent
-- 2025-10-02T18:31:31.330Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754612,0,548451,553592,637587,'F',TO_TIMESTAMP('2025-10-02 18:31:30.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','Y','N','N','Qualitätsabzug %',110,140,0,TO_TIMESTAMP('2025-10-02 18:31:30.341000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qualität-Notiz
-- Column: M_ReceiptSchedule.QualityNote
-- 2025-10-02T18:31:32.655Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754613,0,548451,553592,637588,'F',TO_TIMESTAMP('2025-10-02 18:31:31.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','Y','N','N','Qualität-Notiz',120,150,0,TO_TIMESTAMP('2025-10-02 18:31:31.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lager abw.
-- Column: M_ReceiptSchedule.M_Warehouse_Override_ID
-- 2025-10-02T18:31:33.950Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754586,0,548451,553592,637589,'F',TO_TIMESTAMP('2025-10-02 18:31:32.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','N','Y','N','N','N','Lager abw.',140,0,0,TO_TIMESTAMP('2025-10-02 18:31:32.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bewegt
-- Column: M_ReceiptSchedule.QtyMoved
-- 2025-10-02T18:31:35.271Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754596,0,548451,553592,637590,'F',TO_TIMESTAMP('2025-10-02 18:31:34.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Bewegt',170,0,0,TO_TIMESTAMP('2025-10-02 18:31:34.262000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Qty Moved (With Issues)
-- Column: M_ReceiptSchedule.QtyMovedWithIssues
-- 2025-10-02T18:31:36.565Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754619,0,548451,553592,637591,'F',TO_TIMESTAMP('2025-10-02 18:31:35.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Qty Moved (With Issues)',180,0,0,TO_TIMESTAMP('2025-10-02 18:31:35.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bewegungsmenge
-- Column: M_ReceiptSchedule.QtyToMove
-- 2025-10-02T18:31:37.876Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754597,0,548451,553592,637592,'F',TO_TIMESTAMP('2025-10-02 18:31:36.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Bewegungsmenge',190,0,0,TO_TIMESTAMP('2025-10-02 18:31:36.872000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Priorität
-- Column: M_ReceiptSchedule.PriorityRule
-- 2025-10-02T18:31:39.184Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754593,0,548451,553592,637593,'F',TO_TIMESTAMP('2025-10-02 18:31:38.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Priority of a document','The Priority indicates the importance (high, medium, low) of this document','Y','Y','N','Y','N','N','N','Priorität',200,0,0,TO_TIMESTAMP('2025-10-02 18:31:38.185000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferart
-- Column: M_ReceiptSchedule.DeliveryRule
-- 2025-10-02T18:31:40.489Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754587,0,548451,553592,637594,'F',TO_TIMESTAMP('2025-10-02 18:31:39.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die zeitliche Steuerung von Lieferungen','The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','Y','N','Y','N','N','N','Lieferart',210,0,0,TO_TIMESTAMP('2025-10-02 18:31:39.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Bewegungsmenge abw.
-- Column: M_ReceiptSchedule.QtyToMove_Override
-- 2025-10-02T18:31:41.785Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754598,0,548451,553592,637595,'F',TO_TIMESTAMP('2025-10-02 18:31:40.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Bewegungsmenge abw.',220,0,0,TO_TIMESTAMP('2025-10-02 18:31:40.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Priorität Abw.
-- Column: M_ReceiptSchedule.PriorityRule_Override
-- 2025-10-02T18:31:43.107Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754594,0,548451,553592,637596,'F',TO_TIMESTAMP('2025-10-02 18:31:42.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Priorität Abw.',230,0,0,TO_TIMESTAMP('2025-10-02 18:31:42.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferart abw.
-- Column: M_ReceiptSchedule.DeliveryRule_Override
-- 2025-10-02T18:31:44.397Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754588,0,548451,553592,637597,'F',TO_TIMESTAMP('2025-10-02 18:31:43.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Lieferart abw.',240,0,0,TO_TIMESTAMP('2025-10-02 18:31:43.414000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferung
-- Column: M_ReceiptSchedule.DeliveryViaRule
-- 2025-10-02T18:31:45.712Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754589,0,548451,553592,637598,'F',TO_TIMESTAMP('2025-10-02 18:31:44.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','Y','N','Y','N','N','N','Lieferung',250,0,0,TO_TIMESTAMP('2025-10-02 18:31:44.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferung durch abw.
-- Column: M_ReceiptSchedule.DeliveryViaRule_Override
-- 2025-10-02T18:31:47.041Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754590,0,548451,553592,637599,'F',TO_TIMESTAMP('2025-10-02 18:31:46.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Lieferung durch abw.',260,0,0,TO_TIMESTAMP('2025-10-02 18:31:46.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Geschäftspartner abw.
-- Column: M_ReceiptSchedule.C_BPartner_Override_ID
-- 2025-10-02T18:31:48.340Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754584,0,548451,553592,637600,'F',TO_TIMESTAMP('2025-10-02 18:31:47.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','Y','N','N','N','Geschäftspartner abw.',280,0,0,TO_TIMESTAMP('2025-10-02 18:31:47.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Standort abw.
-- Column: M_ReceiptSchedule.C_BP_Location_Override_ID
-- 2025-10-02T18:31:49.635Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754601,0,548451,553592,637601,'F',TO_TIMESTAMP('2025-10-02 18:31:48.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','Y','N','Y','N','N','N','Standort abw.',290,0,0,TO_TIMESTAMP('2025-10-02 18:31:48.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Ansprechpartner abw.
-- Column: M_ReceiptSchedule.AD_User_Override_ID
-- 2025-10-02T18:31:50.925Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754572,0,548451,553592,637602,'F',TO_TIMESTAMP('2025-10-02 18:31:49.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Ansprechpartner abw.',300,0,0,TO_TIMESTAMP('2025-10-02 18:31:49.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Anschrift-Text
-- Column: M_ReceiptSchedule.BPartnerAddress
-- 2025-10-02T18:31:52.224Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754569,0,548451,553592,637603,'F',TO_TIMESTAMP('2025-10-02 18:31:51.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Anschrift-Text',310,0,0,TO_TIMESTAMP('2025-10-02 18:31:51.236000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Anschrift-Text abw.
-- Column: M_ReceiptSchedule.BPartnerAddress_Override
-- 2025-10-02T18:31:53.532Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754570,0,548451,553592,637604,'F',TO_TIMESTAMP('2025-10-02 18:31:52.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Anschrift-Text abw.',320,0,0,TO_TIMESTAMP('2025-10-02 18:31:52.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Kopf-Aggregationsmerkmal
-- Column: M_ReceiptSchedule.HeaderAggregationKey
-- 2025-10-02T18:31:54.824Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754605,0,548451,553592,637605,'F',TO_TIMESTAMP('2025-10-02 18:31:53.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Kopf-Aggregationsmerkmal',350,0,0,TO_TIMESTAMP('2025-10-02 18:31:53.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Gebindekonfiguration
-- Column: M_ReceiptSchedule.M_HU_LUTU_Configuration_ID
-- 2025-10-02T18:31:56.124Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754616,0,548451,553592,637606,'F',TO_TIMESTAMP('2025-10-02 18:31:55.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Gebindekonfiguration',360,0,0,TO_TIMESTAMP('2025-10-02 18:31:55.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Verpackungskapazität
-- Column: M_ReceiptSchedule.QtyItemCapacity
-- 2025-10-02T18:31:57.415Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754609,0,548451,553592,637607,'F',TO_TIMESTAMP('2025-10-02 18:31:56.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Verpackungskapazität',370,0,0,TO_TIMESTAMP('2025-10-02 18:31:56.430000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Packaging Material
-- Column: M_ReceiptSchedule.IsPackagingMaterial
-- 2025-10-02T18:31:59.014Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754615,0,548451,553592,637608,'F',TO_TIMESTAMP('2025-10-02 18:31:57.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N','Packaging Material ',390,0,0,TO_TIMESTAMP('2025-10-02 18:31:57.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.CanBeExportedFrom
-- Column: M_ReceiptSchedule.CanBeExportedFrom
-- 2025-10-02T18:32:00.403Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754626,0,548451,553592,637609,'F',TO_TIMESTAMP('2025-10-02 18:31:59.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeitpunkt, ab dem der Datensatz exportiert werden kann','Y','N','N','Y','N','N','N',0,'CanBeExportedFrom',400,0,0,TO_TIMESTAMP('2025-10-02 18:31:59.325000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.ExportStatus
-- Column: M_ReceiptSchedule.ExportStatus
-- 2025-10-02T18:32:01.711Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754627,0,548451,553592,637610,'F',TO_TIMESTAMP('2025-10-02 18:32:00.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'ExportStatus',410,0,0,TO_TIMESTAMP('2025-10-02 18:32:00.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Wareneingangsdisposition(548451,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.External resource URL
-- Column: M_ReceiptSchedule.ExternalResourceURL
-- 2025-10-02T18:32:03.038Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754629,0,548451,553592,637611,'F',TO_TIMESTAMP('2025-10-02 18:32:02.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'External resource URL',420,0,0,TO_TIMESTAMP('2025-10-02 18:32:02.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate)
-- UI Section: main
-- 2025-10-02T18:32:04.188Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548452,546978,TO_TIMESTAMP('2025-10-02 18:32:03.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2025-10-02 18:32:03.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-02T18:32:04.266Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546978 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-10-02T18:32:04.419Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546978
;

-- 2025-10-02T18:32:04.496Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546978, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540064
;

-- UI Section: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main
-- UI Column: 10
-- 2025-10-02T18:32:05.400Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548503,546978,TO_TIMESTAMP('2025-10-02 18:32:04.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-02 18:32:04.728000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: default
-- 2025-10-02T18:32:06.384Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548503,553593,TO_TIMESTAMP('2025-10-02 18:32:05.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'',TO_TIMESTAMP('2025-10-02 18:32:05.715000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Mandant
-- Column: M_ReceiptSchedule_Alloc.AD_Client_ID
-- 2025-10-02T18:32:07.892Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754640,0,548452,553593,637612,'F',TO_TIMESTAMP('2025-10-02 18:32:06.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',10,0,0,TO_TIMESTAMP('2025-10-02 18:32:06.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_ReceiptSchedule_Alloc.M_InOut_ID
-- 2025-10-02T18:32:09.265Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754642,0,548452,553593,637613,'F',TO_TIMESTAMP('2025-10-02 18:32:08.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Material Shipment Document','The Material Shipment / Receipt ','Y','N','N','Y','Y','N','N','Lieferung/Wareneingang',20,30,0,TO_TIMESTAMP('2025-10-02 18:32:08.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Qualität-Notiz
-- Column: M_ReceiptSchedule_Alloc.QualityNote
-- 2025-10-02T18:32:10.622Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754652,0,548452,553593,637614,'F',TO_TIMESTAMP('2025-10-02 18:32:09.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Qualität-Notiz',30,70,0,TO_TIMESTAMP('2025-10-02 18:32:09.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Wareneingangsdisposition
-- Column: M_ReceiptSchedule_Alloc.M_ReceiptSchedule_ID
-- 2025-10-02T18:32:11.997Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754645,0,548452,553593,637615,'F',TO_TIMESTAMP('2025-10-02 18:32:10.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Wareneingangsdisposition',40,80,0,TO_TIMESTAMP('2025-10-02 18:32:10.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Aktiv
-- Column: M_ReceiptSchedule_Alloc.IsActive
-- 2025-10-02T18:32:13.343Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754635,0,548452,553593,637616,'F',TO_TIMESTAMP('2025-10-02 18:32:12.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',50,90,0,TO_TIMESTAMP('2025-10-02 18:32:12.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.HU-Transaktionszeile
-- Column: M_ReceiptSchedule_Alloc.M_HU_Trx_Line_ID
-- 2025-10-02T18:32:14.714Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754649,0,548452,553593,637617,'F',TO_TIMESTAMP('2025-10-02 18:32:13.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','HU-Transaktionszeile',60,160,0,TO_TIMESTAMP('2025-10-02 18:32:13.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Handling Unit (LU)
-- Column: M_ReceiptSchedule_Alloc.M_LU_HU_ID
-- 2025-10-02T18:32:16.095Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754647,0,548452,553593,637618,'F',TO_TIMESTAMP('2025-10-02 18:32:15.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Handling Unit (Loading Unit)','Y','N','N','Y','Y','N','N','Handling Unit (LU)',70,130,0,TO_TIMESTAMP('2025-10-02 18:32:15.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Handling Unit (TU)
-- Column: M_ReceiptSchedule_Alloc.M_TU_HU_ID
-- 2025-10-02T18:32:17.495Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754653,0,548452,553593,637619,'F',TO_TIMESTAMP('2025-10-02 18:32:16.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Handling Unit of type Tranding Unit','Y','N','N','Y','Y','N','N','Handling Unit (TU)',80,140,0,TO_TIMESTAMP('2025-10-02 18:32:16.410000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Virtual Handling Unit (VHU)
-- Column: M_ReceiptSchedule_Alloc.VHU_ID
-- 2025-10-02T18:32:18.871Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754655,0,548452,553593,637620,'F',TO_TIMESTAMP('2025-10-02 18:32:17.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Virtual Handling Unit (VHU)',90,150,0,TO_TIMESTAMP('2025-10-02 18:32:17.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Versand-/Wareneingangsposition
-- Column: M_ReceiptSchedule_Alloc.M_InOutLine_ID
-- 2025-10-02T18:32:20.243Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754643,0,548452,553593,637621,'F',TO_TIMESTAMP('2025-10-02 18:32:19.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Position auf Versand- oder Wareneingangsbeleg','"Versand-/Wareneingangsposition" bezeichnet eine einzelne Zeile/Position auf einem Versand- oder Wareneingangsbeleg.','Y','N','N','Y','Y','N','N','Versand-/Wareneingangsposition',100,50,0,TO_TIMESTAMP('2025-10-02 18:32:19.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Qualitätsabzug %
-- Column: M_ReceiptSchedule_Alloc.QualityDiscountPercent
-- 2025-10-02T18:32:21.618Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754651,0,548452,553593,637622,'F',TO_TIMESTAMP('2025-10-02 18:32:20.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Qualitätsabzug %',110,60,0,TO_TIMESTAMP('2025-10-02 18:32:20.551000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Zugewiesene Menge
-- Column: M_ReceiptSchedule_Alloc.QtyAllocated
-- 2025-10-02T18:32:22.996Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754646,0,548452,553593,637623,'F',TO_TIMESTAMP('2025-10-02 18:32:21.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Zugewiesene Menge',120,100,0,TO_TIMESTAMP('2025-10-02 18:32:21.928000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Zugewiesene Menge (HU)
-- Column: M_ReceiptSchedule_Alloc.HU_QtyAllocated
-- 2025-10-02T18:32:24.363Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754650,0,548452,553593,637624,'F',TO_TIMESTAMP('2025-10-02 18:32:23.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Zugewiesene Menge (HU)',130,120,0,TO_TIMESTAMP('2025-10-02 18:32:23.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Minderwertige Menge
-- Column: M_ReceiptSchedule_Alloc.QtyWithIssues
-- 2025-10-02T18:32:25.730Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754654,0,548452,553593,637625,'F',TO_TIMESTAMP('2025-10-02 18:32:24.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mengen-Summe der zugeordneten Lieferzeilen, die mit "im Disput" markiert sind und nicht fakturiert werden sollen.','Y','N','N','Y','Y','N','N','Minderwertige Menge',140,110,0,TO_TIMESTAMP('2025-10-02 18:32:24.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Belegstatus
-- Column: M_ReceiptSchedule_Alloc.DocStatus
-- 2025-10-02T18:32:27.077Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754656,0,548452,553593,637626,'F',TO_TIMESTAMP('2025-10-02 18:32:26.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','Y','Y','N','N','Belegstatus',150,40,0,TO_TIMESTAMP('2025-10-02 18:32:26.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Zugeordneter Wareneingang(548452,de.metas.inoutcandidate) -> main -> 10 -> default.Sektion
-- Column: M_ReceiptSchedule_Alloc.AD_Org_ID
-- 2025-10-02T18:32:28.446Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754641,0,548452,553593,637627,'F',TO_TIMESTAMP('2025-10-02 18:32:27.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Sektion',160,20,0,TO_TIMESTAMP('2025-10-02 18:32:27.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Tab: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate)
-- UI Section: main
-- 2025-10-02T18:32:29.578Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548453,546979,TO_TIMESTAMP('2025-10-02 18:32:28.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','',10,TO_TIMESTAMP('2025-10-02 18:32:28.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-02T18:32:29.656Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546979 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2025-10-02T18:32:29.809Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 546979
;

-- 2025-10-02T18:32:29.887Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 546979, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540065
;

-- UI Section: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> main
-- UI Column: 10
-- 2025-10-02T18:32:30.777Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548504,546979,TO_TIMESTAMP('2025-10-02 18:32:30.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-02 18:32:30.119000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> main -> 10
-- UI Element Group: default
-- 2025-10-02T18:32:32.052Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548504,553594,TO_TIMESTAMP('2025-10-02 18:32:31.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'',TO_TIMESTAMP('2025-10-02 18:32:31.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> main -> 10 -> default.Handling Units
-- Column: M_HU_Assignment.M_HU_ID
-- 2025-10-02T18:32:33.603Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754661,0,548453,553594,637628,'F',TO_TIMESTAMP('2025-10-02 18:32:32.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Handling Units',10,30,0,TO_TIMESTAMP('2025-10-02 18:32:32.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> main -> 10 -> default.Produkte
-- Column: M_HU_Assignment.Products
-- 2025-10-02T18:32:34.976Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754665,0,548453,553594,637629,'F',TO_TIMESTAMP('2025-10-02 18:32:33.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Produkte',20,50,0,TO_TIMESTAMP('2025-10-02 18:32:33.916000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> main -> 10 -> default.Aktiv
-- Column: M_HU_Assignment.IsActive
-- 2025-10-02T18:32:36.382Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754659,0,548453,553594,637630,'F',TO_TIMESTAMP('2025-10-02 18:32:35.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','Y','N','N','Aktiv',30,60,0,TO_TIMESTAMP('2025-10-02 18:32:35.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> main -> 10 -> default.Menge
-- Column: M_HU_Assignment.Qty
-- 2025-10-02T18:32:37.748Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754664,0,548453,553594,637631,'F',TO_TIMESTAMP('2025-10-02 18:32:36.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','Y','N','N','Menge',40,40,0,TO_TIMESTAMP('2025-10-02 18:32:36.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> main -> 10 -> default.Mandant
-- Column: M_HU_Assignment.AD_Client_ID
-- 2025-10-02T18:32:39.116Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754657,0,548453,553594,637632,'F',TO_TIMESTAMP('2025-10-02 18:32:38.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',50,0,0,TO_TIMESTAMP('2025-10-02 18:32:38.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate) -> Handling Unit Assignment(548453,de.metas.inoutcandidate) -> main -> 10 -> default.Sektion
-- Column: M_HU_Assignment.AD_Org_ID
-- 2025-10-02T18:32:40.481Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754658,0,548453,553594,637633,'F',TO_TIMESTAMP('2025-10-02 18:32:39.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N','Sektion',60,20,0,TO_TIMESTAMP('2025-10-02 18:32:39.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- Window: Wareneingangsdisposition Logistik, InternalName=null
-- 2025-10-02T18:33:05.812Z
UPDATE AD_Window SET Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2025-10-02 18:33:05.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541954
;

-- 2025-10-02T18:36:33.792Z
UPDATE AD_Window_Trl SET Name='Wareneingangsdisposition Logistik',Updated=TO_TIMESTAMP('2025-10-02 18:36:33.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Window_ID=541954
;

-- 2025-10-02T18:36:33.871Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T18:36:46.270Z
UPDATE AD_Window_Trl SET Name='Wareneingangsdisposition Logistik',Updated=TO_TIMESTAMP('2025-10-02 18:36:46.039000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Window_ID=541954
;

-- 2025-10-02T18:36:46.347Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-02T18:37:07.351Z
UPDATE AD_Window_Trl SET Name='Receipt Schedule Logistics',Updated=TO_TIMESTAMP('2025-10-02 18:37:07.121000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Window_ID=541954
;

-- 2025-10-02T18:37:07.428Z
UPDATE AD_Window base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Window_Trl trl  WHERE trl.AD_Window_ID=base.AD_Window_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Name: Wareneingangsdisposition Logistik
-- Action Type: W
-- Window: Wareneingangsdisposition Logistik(541954,de.metas.inoutcandidate)
-- 2025-10-02T18:39:51.050Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584081,542257,0,541954,TO_TIMESTAMP('2025-10-02 18:39:49.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.inoutcandidate','Wareneingangsdisposition Logistik','Y','N','N','N','N','Wareneingangsdisposition Logistik',TO_TIMESTAMP('2025-10-02 18:39:49.937000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T18:39:51.128Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542257 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-10-02T18:39:51.211Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542257, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542257)
;

-- 2025-10-02T18:39:51.299Z
/* DDL */  select update_menu_translation_from_ad_element(584081)
;

-- Reordering children of `Material Receipt`
-- Node name: `Material Receipt`
-- 2025-10-02T18:40:05.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000083 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt Candidates - Purchase`
-- 2025-10-02T18:40:05.251Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540736 AND AD_Tree_ID=10
;

-- Node name: `Receipt Disposiotion Export Revision`
-- 2025-10-02T18:40:05.329Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541486 AND AD_Tree_ID=10
;

-- Node name: `Empties Receive`
-- 2025-10-02T18:40:05.407Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540781 AND AD_Tree_ID=10
;

-- Node name: `Vendor Returns`
-- 2025-10-02T18:40:05.485Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540782 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-02T18:40:05.566Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000058 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-02T18:40:05.647Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000066 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-02T18:40:05.726Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000076 AND AD_Tree_ID=10
;

-- Node name: `Wareneingangsdisposition Logistik`
-- 2025-10-02T18:40:05.804Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000017, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542257 AND AD_Tree_ID=10
;


