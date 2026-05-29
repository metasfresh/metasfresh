-- Run mode: SWING_CLIENT

-- 2025-10-03T06:57:47.955Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584083,0,TO_TIMESTAMP('2025-10-03 06:57:47.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','nShift Konfiguration','nShift Konfiguration',TO_TIMESTAMP('2025-10-03 06:57:47.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T06:57:47.963Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584083 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-10-03T06:58:07.375Z
UPDATE AD_Element_Trl SET Name='nShift Configuration',Updated=TO_TIMESTAMP('2025-10-03 06:58:07.375000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584083 AND AD_Language='en_US'
;

-- 2025-10-03T06:58:07.377Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-03T06:58:07.677Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584083,'en_US')
;

-- Element: null
-- 2025-10-03T06:58:11.755Z
UPDATE AD_Element_Trl SET PrintName='nShift Configuration',Updated=TO_TIMESTAMP('2025-10-03 06:58:11.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584083 AND AD_Language='en_US'
;

-- 2025-10-03T06:58:11.756Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-03T06:58:12.028Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584083,'en_US')
;

-- Tab: Lieferweg(142,D) -> nShift Konfiguration
-- Table: Carrier_Config
-- 2025-10-03T07:00:53.944Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584083,0,548455,542540,142,'Y',TO_TIMESTAMP('2025-10-03 07:00:53.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','Carrier_Config','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'nShift Konfiguration','N',70,0,TO_TIMESTAMP('2025-10-03 07:00:53.725000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:00:53.947Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548455 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-03T07:00:53.949Z
/* DDL */  select update_tab_translation_from_ad_element(584083)
;

-- 2025-10-03T07:00:53.961Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548455)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Mandant
-- Column: Carrier_Config.AD_Client_ID
-- 2025-10-03T07:01:26.827Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591217,754692,0,548455,TO_TIMESTAMP('2025-10-03 07:01:26.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-03 07:01:26.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:26.830Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754692 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:26.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-03T07:01:27.419Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754692
;

-- 2025-10-03T07:01:27.421Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754692)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Sektion
-- Column: Carrier_Config.AD_Org_ID
-- 2025-10-03T07:01:27.560Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591218,754693,0,548455,TO_TIMESTAMP('2025-10-03 07:01:27.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-03 07:01:27.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:27.561Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754693 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:27.563Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-03T07:01:27.794Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754693
;

-- 2025-10-03T07:01:27.795Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754693)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> URL
-- Column: Carrier_Config.Base_url
-- 2025-10-03T07:01:27.927Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591223,754694,0,548455,TO_TIMESTAMP('2025-10-03 07:01:27.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','URL',TO_TIMESTAMP('2025-10-03 07:01:27.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:27.928Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754694 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:27.929Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577782)
;

-- 2025-10-03T07:01:27.933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754694
;

-- 2025-10-03T07:01:27.933Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754694)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Carrier Config
-- Column: Carrier_Config.Carrier_Config_ID
-- 2025-10-03T07:01:28.067Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591225,754695,0,548455,TO_TIMESTAMP('2025-10-03 07:01:27.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Carrier Config',TO_TIMESTAMP('2025-10-03 07:01:27.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:28.068Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754695 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:28.070Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584057)
;

-- 2025-10-03T07:01:28.072Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754695
;

-- 2025-10-03T07:01:28.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754695)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Aktiv
-- Column: Carrier_Config.IsActive
-- 2025-10-03T07:01:28.192Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591226,754696,0,548455,TO_TIMESTAMP('2025-10-03 07:01:28.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-03 07:01:28.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:28.194Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754696 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:28.195Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-03T07:01:28.494Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754696
;

-- 2025-10-03T07:01:28.495Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754696)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Lieferweg
-- Column: Carrier_Config.M_Shipper_ID
-- 2025-10-03T07:01:28.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591227,754697,0,548455,TO_TIMESTAMP('2025-10-03 07:01:28.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung',10,'D','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','N','N','N','N','N','Lieferweg',TO_TIMESTAMP('2025-10-03 07:01:28.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:28.638Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754697 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:28.639Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455)
;

-- 2025-10-03T07:01:28.662Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754697
;

-- 2025-10-03T07:01:28.663Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754697)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Nutzer-ID/Login
-- Column: Carrier_Config.UserName
-- 2025-10-03T07:01:28.793Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591231,754698,0,548455,TO_TIMESTAMP('2025-10-03 07:01:28.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','','Y','N','N','N','N','N','N','N','Nutzer-ID/Login',TO_TIMESTAMP('2025-10-03 07:01:28.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:28.794Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754698 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:28.796Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1903)
;

-- 2025-10-03T07:01:28.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754698
;

-- 2025-10-03T07:01:28.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754698)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Kennwort
-- Column: Carrier_Config.Password
-- 2025-10-03T07:01:28.938Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591232,754699,0,548455,TO_TIMESTAMP('2025-10-03 07:01:28.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','N','N','N','N','N','N','Kennwort',TO_TIMESTAMP('2025-10-03 07:01:28.804000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:28.939Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754699 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:28.941Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(498)
;

-- 2025-10-03T07:01:28.947Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754699
;

-- 2025-10-03T07:01:28.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754699)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Zugangs-ID
-- Column: Carrier_Config.Client_Id
-- 2025-10-03T07:01:29.083Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591233,754700,0,548455,TO_TIMESTAMP('2025-10-03 07:01:28.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Zugangs-ID',TO_TIMESTAMP('2025-10-03 07:01:28.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:29.084Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754700 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:29.085Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578777)
;

-- 2025-10-03T07:01:29.088Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754700
;

-- 2025-10-03T07:01:29.089Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754700)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Sicherheitsschlüssel
-- Column: Carrier_Config.Client_Secret
-- 2025-10-03T07:01:29.217Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591234,754701,0,548455,TO_TIMESTAMP('2025-10-03 07:01:29.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Sicherheitsschlüssel',TO_TIMESTAMP('2025-10-03 07:01:29.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:29.218Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754701 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:29.219Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578778)
;

-- 2025-10-03T07:01:29.222Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754701
;

-- 2025-10-03T07:01:29.223Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754701)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Nachverfolgungs-URL
-- Column: Carrier_Config.TrackingURL
-- 2025-10-03T07:01:29.358Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591235,754702,0,548455,TO_TIMESTAMP('2025-10-03 07:01:29.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL des Spediteurs um Sendungen zu verfolgen',255,'D','Die variable @TrackingNo@ in der URL wird durch die tatsächliche Identifizierungsnummer der Sendung ersetzt.','Y','N','N','N','N','N','N','N','Nachverfolgungs-URL',TO_TIMESTAMP('2025-10-03 07:01:29.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:29.359Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754702 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:29.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2127)
;

-- 2025-10-03T07:01:29.364Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754702
;

-- 2025-10-03T07:01:29.365Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754702)
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Actor ID
-- Column: Carrier_Config.ActorId
-- 2025-10-03T07:01:29.507Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591244,754703,0,548455,TO_TIMESTAMP('2025-10-03 07:01:29.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,20,'D','Y','N','N','N','N','N','N','N','Actor ID',TO_TIMESTAMP('2025-10-03 07:01:29.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T07:01:29.508Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754703 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T07:01:29.509Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584080)
;

-- 2025-10-03T07:01:29.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754703
;

-- 2025-10-03T07:01:29.512Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754703)
;

-- Tab: Lieferweg(142,D) -> nShift Konfiguration(548455,D)
-- UI Section: main
-- 2025-10-03T07:04:50.600Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548455,546981,TO_TIMESTAMP('2025-10-03 07:04:50.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 07:04:50.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-03T07:04:50.602Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546981 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main
-- UI Column: 10
-- 2025-10-03T07:04:55.982Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548506,546981,TO_TIMESTAMP('2025-10-03 07:04:55.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 07:04:55.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10
-- UI Element Group: name
-- 2025-10-03T07:05:03.707Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548506,553597,TO_TIMESTAMP('2025-10-03 07:05:03.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','name',10,TO_TIMESTAMP('2025-10-03 07:05:03.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.URL
-- Column: Carrier_Config.Base_url
-- 2025-10-03T07:06:09.350Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754694,0,548455,553597,637655,'F',TO_TIMESTAMP('2025-10-03 07:06:09.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'URL',10,0,0,TO_TIMESTAMP('2025-10-03 07:06:09.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Nutzer-ID/Login
-- Column: Carrier_Config.UserName
-- 2025-10-03T08:02:03.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754698,0,548455,553597,637656,'F',TO_TIMESTAMP('2025-10-03 08:02:03.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Nutzer-ID/Login',20,0,0,TO_TIMESTAMP('2025-10-03 08:02:03.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Kennwort
-- Column: Carrier_Config.Password
-- 2025-10-03T08:02:45.539Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754699,0,548455,553597,637657,'F',TO_TIMESTAMP('2025-10-03 08:02:45.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The Password for this User.  Passwords are required to identify authorized users.  For metasfresh Users, you can change the password via the Process "Reset Password".','Y','N','N','Y','N','N','N',0,'Kennwort',30,0,0,TO_TIMESTAMP('2025-10-03 08:02:45.337000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Zugangs-ID
-- Column: Carrier_Config.Client_Id
-- 2025-10-03T08:02:59.922Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754700,0,548455,553597,637658,'F',TO_TIMESTAMP('2025-10-03 08:02:59.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Zugangs-ID',40,0,0,TO_TIMESTAMP('2025-10-03 08:02:59.721000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Sicherheitsschlüssel
-- Column: Carrier_Config.Client_Secret
-- 2025-10-03T08:03:05.804Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754701,0,548455,553597,637659,'F',TO_TIMESTAMP('2025-10-03 08:03:05.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Sicherheitsschlüssel',50,0,0,TO_TIMESTAMP('2025-10-03 08:03:05.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Actor ID
-- Column: Carrier_Config.ActorId
-- 2025-10-03T08:03:20.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754703,0,548455,553597,637660,'F',TO_TIMESTAMP('2025-10-03 08:03:20.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Actor ID',60,0,0,TO_TIMESTAMP('2025-10-03 08:03:20.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Nachverfolgungs-URL
-- Column: Carrier_Config.TrackingURL
-- 2025-10-03T08:03:30.677Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754702,0,548455,553597,637661,'F',TO_TIMESTAMP('2025-10-03 08:03:30.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL des Spediteurs um Sendungen zu verfolgen','Die variable @TrackingNo@ in der URL wird durch die tatsächliche Identifizierungsnummer der Sendung ersetzt.','Y','N','N','Y','N','N','N',0,'Nachverfolgungs-URL',70,0,0,TO_TIMESTAMP('2025-10-03 08:03:30.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Lieferweg(142,D) -> nShift Konfiguration
-- Table: Carrier_Config
-- 2025-10-03T08:10:50.046Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2025-10-03 08:10:50.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548455
;

-- Table: Carrier_Config
-- 2025-10-03T08:12:27.113Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2025-10-03 08:12:27.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=542540
;

-- Tab: Lieferweg(142,D) -> nShift Konfiguration
-- Table: Carrier_Config
-- 2025-10-03T08:18:43.032Z
UPDATE AD_Tab SET AD_Column_ID=591227, Parent_Column_ID=2077,Updated=TO_TIMESTAMP('2025-10-03 08:18:43.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548455
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Zugangs-ID
-- Column: Carrier_Config.Client_Id
-- 2025-10-03T08:22:51.772Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-10-03 08:22:51.772000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754700
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Sicherheitsschlüssel
-- Column: Carrier_Config.Client_Secret
-- 2025-10-03T08:22:55.451Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-10-03 08:22:55.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=754701
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.URL
-- Column: Carrier_Config.Base_url
-- 2025-10-03T08:23:14.643Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-03 08:23:14.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637655
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Nutzer-ID/Login
-- Column: Carrier_Config.UserName
-- 2025-10-03T08:23:14.649Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-03 08:23:14.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637656
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Kennwort
-- Column: Carrier_Config.Password
-- 2025-10-03T08:23:14.654Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-03 08:23:14.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637657
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Actor ID
-- Column: Carrier_Config.ActorId
-- 2025-10-03T08:23:14.659Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-03 08:23:14.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637660
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Nachverfolgungs-URL
-- Column: Carrier_Config.TrackingURL
-- 2025-10-03T08:23:14.665Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-03 08:23:14.665000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637661
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Nachverfolgungs-URL
-- Column: Carrier_Config.TrackingURL
-- 2025-10-03T08:24:58.372Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637661
;

-- 2025-10-03T08:24:58.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754702
;

-- Field: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> Nachverfolgungs-URL
-- Column: Carrier_Config.TrackingURL
-- 2025-10-03T08:24:58.378Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754702
;

-- 2025-10-03T08:24:58.382Z
DELETE FROM AD_Field WHERE AD_Field_ID=754702
;

-- 2025-10-03T08:24:58.384Z
/* DDL */ SELECT public.db_alter_table('Carrier_Config','ALTER TABLE Carrier_Config DROP COLUMN IF EXISTS TrackingURL')
;

-- Column: Carrier_Config.TrackingURL
-- 2025-10-03T08:24:58.410Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591235
;

-- 2025-10-03T08:24:58.414Z
DELETE FROM AD_Column WHERE AD_Column_ID=591235
;

-- 2025-10-03T08:26:25.808Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540828,0,542540,TO_TIMESTAMP('2025-10-03 08:26:25.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Y','Carrier_Config_M_Shipper_Id_Active_uq','N',TO_TIMESTAMP('2025-10-03 08:26:25.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsActive=''Y''')
;

-- 2025-10-03T08:26:25.810Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540828 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-10-03T08:26:39.155Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591227,541473,540828,0,TO_TIMESTAMP('2025-10-03 08:26:38.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-10-03 08:26:38.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T08:26:44.085Z
CREATE UNIQUE INDEX Carrier_Config_M_Shipper_Id_Active_uq ON Carrier_Config (M_Shipper_ID) WHERE IsActive='Y'
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Actor ID
-- Column: Carrier_Config.ActorId
-- 2025-10-03T08:27:48.215Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2025-10-03 08:27:48.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637660
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Sicherheitsschlüssel
-- Column: Carrier_Config.Client_Secret
-- 2025-10-03T08:27:52.963Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2025-10-03 08:27:52.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637659
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Kennwort
-- Column: Carrier_Config.Password
-- 2025-10-03T08:28:01.583Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2025-10-03 08:28:01.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637657
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Zugangs-ID
-- Column: Carrier_Config.Client_Id
-- 2025-10-03T08:28:05.944Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2025-10-03 08:28:05.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637658
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Nutzer-ID/Login
-- Column: Carrier_Config.UserName
-- 2025-10-03T08:28:09.090Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-10-03 08:28:09.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637656
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.URL
-- Column: Carrier_Config.Base_url
-- 2025-10-03T08:28:12.910Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2025-10-03 08:28:12.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637655
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Aktiv
-- Column: Carrier_Config.IsActive
-- 2025-10-03T08:28:26.802Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754696,0,548455,553597,637662,'F',TO_TIMESTAMP('2025-10-03 08:28:26.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-10-03 08:28:26.607000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Aktiv
-- Column: Carrier_Config.IsActive
-- 2025-10-03T08:28:33.912Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-03 08:28:33.912000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637662
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.URL
-- Column: Carrier_Config.Base_url
-- 2025-10-03T08:28:33.918Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-03 08:28:33.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637655
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Nutzer-ID/Login
-- Column: Carrier_Config.UserName
-- 2025-10-03T08:28:33.923Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-03 08:28:33.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637656
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Kennwort
-- Column: Carrier_Config.Password
-- 2025-10-03T08:28:33.929Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-03 08:28:33.929000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637657
;

-- UI Element: Lieferweg(142,D) -> nShift Konfiguration(548455,D) -> main -> 10 -> name.Actor ID
-- Column: Carrier_Config.ActorId
-- 2025-10-03T08:28:33.934Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-03 08:28:33.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637660
;

-- 2025-10-03T11:34:39.285Z
DROP INDEX IF EXISTS carrier_config_m_shipper_id_active_uq
;

-- 2025-10-03T11:34:39.286Z
CREATE UNIQUE INDEX Carrier_Config_M_Shipper_Id_Active_uq ON Carrier_Config (M_Shipper_ID) WHERE IsActive='Y'
;

-- 2025-10-03T11:35:40.549Z
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder','ALTER TABLE Carrier_ShipmentOrder DROP COLUMN IF EXISTS Receiver_CountryISO2Code')
;

-- Column: Carrier_ShipmentOrder.Receiver_CountryISO2Code
-- 2025-10-03T11:35:40.565Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591057
;

-- 2025-10-03T11:35:40.571Z
DELETE FROM AD_Column WHERE AD_Column_ID=591057
;

-- 2025-10-03T11:35:48.065Z
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder','ALTER TABLE Carrier_ShipmentOrder DROP COLUMN IF EXISTS Shipper_CountryISO2Code')
;

-- Column: Carrier_ShipmentOrder.Shipper_CountryISO2Code
-- 2025-10-03T11:35:48.076Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591072
;

-- 2025-10-03T11:35:48.080Z
DELETE FROM AD_Column WHERE AD_Column_ID=591072
;

-- Window: Versandauftrag, InternalName=null
-- 2025-10-03T11:49:24.151Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584001,0,541956,TO_TIMESTAMP('2025-10-03 11:49:23.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Versandauftrag','N',TO_TIMESTAMP('2025-10-03 11:49:23.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Q',0,0,100)
;

-- 2025-10-03T11:49:24.154Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541956 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-10-03T11:49:24.158Z
/* DDL */  select update_window_translation_from_ad_element(584001)
;

-- 2025-10-03T11:49:24.162Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541956
;

-- 2025-10-03T11:49:24.165Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541956)
;

-- Tab: Versandauftrag(541956,D) -> Versandauftrag
-- Table: Carrier_ShipmentOrder
-- 2025-10-03T11:51:05.514Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584001,0,548456,542532,541956,'Y',TO_TIMESTAMP('2025-10-03 11:51:05.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','Carrier_ShipmentOrder','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Versandauftrag','N',10,0,TO_TIMESTAMP('2025-10-03 11:51:05.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:05.517Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548456 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-03T11:51:05.519Z
/* DDL */  select update_tab_translation_from_ad_element(584001)
;

-- 2025-10-03T11:51:05.524Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548456)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Mandant
-- Column: Carrier_ShipmentOrder.AD_Client_ID
-- 2025-10-03T11:51:10.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591039,754704,0,548456,TO_TIMESTAMP('2025-10-03 11:51:10.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-03 11:51:10.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:10.991Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754704 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:10.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-03T11:51:11.377Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754704
;

-- 2025-10-03T11:51:11.378Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754704)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Sektion
-- Column: Carrier_ShipmentOrder.AD_Org_ID
-- 2025-10-03T11:51:11.524Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591041,754705,0,548456,TO_TIMESTAMP('2025-10-03 11:51:11.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-03 11:51:11.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:11.525Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754705 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:11.526Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-03T11:51:11.660Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754705
;

-- 2025-10-03T11:51:11.661Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754705)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Geschäftspartner
-- Column: Carrier_ShipmentOrder.C_BPartner_ID
-- 2025-10-03T11:51:11.782Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591043,754706,0,548456,TO_TIMESTAMP('2025-10-03 11:51:11.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-10-03 11:51:11.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:11.783Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754706 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:11.784Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2025-10-03T11:51:11.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754706
;

-- 2025-10-03T11:51:11.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754706)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Kundenreferenz
-- Column: Carrier_ShipmentOrder.CustomerReference
-- 2025-10-03T11:51:11.949Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591049,754707,0,548456,TO_TIMESTAMP('2025-10-03 11:51:11.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Kundenreferenz',TO_TIMESTAMP('2025-10-03 11:51:11.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:11.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754707 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:11.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540568)
;

-- 2025-10-03T11:51:11.956Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754707
;

-- 2025-10-03T11:51:11.957Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754707)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Produkt
-- Column: Carrier_ShipmentOrder.Carrier_Product
-- 2025-10-03T11:51:12.096Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591055,754708,0,548456,TO_TIMESTAMP('2025-10-03 11:51:11.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2025-10-03 11:51:11.959000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:12.097Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754708 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:12.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584003)
;

-- 2025-10-03T11:51:12.100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754708
;

-- 2025-10-03T11:51:12.101Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754708)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Empfänger Ländercode (ISO-3)
-- Column: Carrier_ShipmentOrder.Receiver_CountryISO3Code
-- 2025-10-03T11:51:12.369Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591058,754709,0,548456,TO_TIMESTAMP('2025-10-03 11:51:12.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,3,'D','Y','N','N','N','N','N','N','N','Empfänger Ländercode (ISO-3)',TO_TIMESTAMP('2025-10-03 11:51:12.103000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:12.370Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754709 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:12.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584006)
;

-- 2025-10-03T11:51:12.375Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754709
;

-- 2025-10-03T11:51:12.375Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754709)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> E-Mail Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Email
-- 2025-10-03T11:51:12.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591059,754710,0,548456,TO_TIMESTAMP('2025-10-03 11:51:12.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','E-Mail Empfänger',TO_TIMESTAMP('2025-10-03 11:51:12.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:12.550Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754710 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:12.551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584008)
;

-- 2025-10-03T11:51:12.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754710
;

-- 2025-10-03T11:51:12.553Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754710)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Name 1 Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Name1
-- 2025-10-03T11:51:12.724Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591060,754711,0,548456,TO_TIMESTAMP('2025-10-03 11:51:12.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Name 1 Empfänger',TO_TIMESTAMP('2025-10-03 11:51:12.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:12.725Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754711 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:12.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584009)
;

-- 2025-10-03T11:51:12.728Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754711
;

-- 2025-10-03T11:51:12.729Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754711)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Name 2 Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Name2
-- 2025-10-03T11:51:12.867Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591061,754712,0,548456,TO_TIMESTAMP('2025-10-03 11:51:12.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Name 2 Empfänger',TO_TIMESTAMP('2025-10-03 11:51:12.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:12.869Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754712 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:12.870Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584010)
;

-- 2025-10-03T11:51:12.872Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754712
;

-- 2025-10-03T11:51:12.872Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754712)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Telefon Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Phone
-- 2025-10-03T11:51:12.999Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591062,754713,0,548456,TO_TIMESTAMP('2025-10-03 11:51:12.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Telefon Empfänger',TO_TIMESTAMP('2025-10-03 11:51:12.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:13Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754713 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:13.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584011)
;

-- 2025-10-03T11:51:13.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754713
;

-- 2025-10-03T11:51:13.004Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754713)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Straße 1 Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_StreetName1
-- 2025-10-03T11:51:13.133Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591063,754714,0,548456,TO_TIMESTAMP('2025-10-03 11:51:13.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Straße 1 Empfänger',TO_TIMESTAMP('2025-10-03 11:51:13.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:13.134Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754714 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:13.135Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584012)
;

-- 2025-10-03T11:51:13.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754714
;

-- 2025-10-03T11:51:13.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754714)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Straße 2 Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_StreetName2
-- 2025-10-03T11:51:13.268Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591064,754715,0,548456,TO_TIMESTAMP('2025-10-03 11:51:13.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Straße 2 Empfänger',TO_TIMESTAMP('2025-10-03 11:51:13.140000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:13.269Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754715 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:13.270Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584013)
;

-- 2025-10-03T11:51:13.272Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754715
;

-- 2025-10-03T11:51:13.273Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754715)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Hausnummer Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_StreetNumber
-- 2025-10-03T11:51:13.408Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591065,754716,0,548456,TO_TIMESTAMP('2025-10-03 11:51:13.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Hausnummer Empfänger',TO_TIMESTAMP('2025-10-03 11:51:13.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:13.410Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754716 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:13.411Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584014)
;

-- 2025-10-03T11:51:13.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754716
;

-- 2025-10-03T11:51:13.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754716)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> PLZ Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_ZipCode
-- 2025-10-03T11:51:13.538Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591066,754717,0,548456,TO_TIMESTAMP('2025-10-03 11:51:13.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','PLZ Empfänger',TO_TIMESTAMP('2025-10-03 11:51:13.416000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:13.540Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:13.541Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584015)
;

-- 2025-10-03T11:51:13.543Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754717
;

-- 2025-10-03T11:51:13.544Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754717)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Lieferdatum
-- Column: Carrier_ShipmentOrder.ShipmentDate
-- 2025-10-03T11:51:13.671Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591068,754718,0,548456,TO_TIMESTAMP('2025-10-03 11:51:13.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Lieferdatum',TO_TIMESTAMP('2025-10-03 11:51:13.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:13.673Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:13.674Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584016)
;

-- 2025-10-03T11:51:13.676Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754718
;

-- 2025-10-03T11:51:13.676Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754718)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Versandauftrag
-- Column: Carrier_ShipmentOrder.Carrier_ShipmentOrder_ID
-- 2025-10-03T11:51:13.809Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591069,754719,0,548456,TO_TIMESTAMP('2025-10-03 11:51:13.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Versandauftrag',TO_TIMESTAMP('2025-10-03 11:51:13.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:13.810Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754719 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:13.811Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584001)
;

-- 2025-10-03T11:51:13.813Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754719
;

-- 2025-10-03T11:51:13.814Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754719)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Lieferort
-- Column: Carrier_ShipmentOrder.Shipper_City
-- 2025-10-03T11:51:13.941Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591071,754720,0,548456,TO_TIMESTAMP('2025-10-03 11:51:13.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Lieferort',TO_TIMESTAMP('2025-10-03 11:51:13.816000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:13.943Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754720 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:13.944Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584017)
;

-- 2025-10-03T11:51:13.946Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754720
;

-- 2025-10-03T11:51:13.946Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754720)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Lieferant Ländercode (ISO-3)
-- Column: Carrier_ShipmentOrder.Shipper_CountryISO3Code
-- 2025-10-03T11:51:14.078Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591073,754721,0,548456,TO_TIMESTAMP('2025-10-03 11:51:13.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,3,'D','Y','N','N','N','N','N','N','N','Lieferant Ländercode (ISO-3)',TO_TIMESTAMP('2025-10-03 11:51:13.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:14.080Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754721 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:14.081Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584019)
;

-- 2025-10-03T11:51:14.083Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754721
;

-- 2025-10-03T11:51:14.084Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754721)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Lieferant Name 1
-- Column: Carrier_ShipmentOrder.Shipper_Name1
-- 2025-10-03T11:51:14.217Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591074,754722,0,548456,TO_TIMESTAMP('2025-10-03 11:51:14.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Lieferant Name 1',TO_TIMESTAMP('2025-10-03 11:51:14.086000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:14.218Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754722 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:14.219Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584020)
;

-- 2025-10-03T11:51:14.221Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754722
;

-- 2025-10-03T11:51:14.222Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754722)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) ->  Lieferant Name 2
-- Column: Carrier_ShipmentOrder.Shipper_Name2
-- 2025-10-03T11:51:14.354Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591075,754723,0,548456,TO_TIMESTAMP('2025-10-03 11:51:14.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N',' Lieferant Name 2',TO_TIMESTAMP('2025-10-03 11:51:14.223000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:14.355Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754723 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:14.356Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584021)
;

-- 2025-10-03T11:51:14.358Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754723
;

-- 2025-10-03T11:51:14.358Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754723)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Straße 1 Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_StreetName1
-- 2025-10-03T11:51:14.487Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591076,754724,0,548456,TO_TIMESTAMP('2025-10-03 11:51:14.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Straße 1 Lieferant',TO_TIMESTAMP('2025-10-03 11:51:14.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:14.488Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754724 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:14.489Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584022)
;

-- 2025-10-03T11:51:14.491Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754724
;

-- 2025-10-03T11:51:14.491Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754724)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Straße 2 Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_StreetName2
-- 2025-10-03T11:51:14.621Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591077,754725,0,548456,TO_TIMESTAMP('2025-10-03 11:51:14.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Straße 2 Lieferant',TO_TIMESTAMP('2025-10-03 11:51:14.493000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:14.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754725 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:14.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584023)
;

-- 2025-10-03T11:51:14.625Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754725
;

-- 2025-10-03T11:51:14.626Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754725)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Hausnummer Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_StreetNumber
-- 2025-10-03T11:51:14.761Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591078,754726,0,548456,TO_TIMESTAMP('2025-10-03 11:51:14.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Hausnummer Lieferant',TO_TIMESTAMP('2025-10-03 11:51:14.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:14.762Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754726 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:14.764Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584024)
;

-- 2025-10-03T11:51:14.765Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754726
;

-- 2025-10-03T11:51:14.766Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754726)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> PLZ Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_ZipCode
-- 2025-10-03T11:51:14.893Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591079,754727,0,548456,TO_TIMESTAMP('2025-10-03 11:51:14.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','PLZ Lieferant',TO_TIMESTAMP('2025-10-03 11:51:14.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:14.894Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754727 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:14.895Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584025)
;

-- 2025-10-03T11:51:14.897Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754727
;

-- 2025-10-03T11:51:14.897Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754727)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> International Delivery
-- Column: Carrier_ShipmentOrder.InternationalDelivery
-- 2025-10-03T11:51:15.038Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591085,754728,0,548456,TO_TIMESTAMP('2025-10-03 11:51:14.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','International Delivery',TO_TIMESTAMP('2025-10-03 11:51:14.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:15.039Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754728 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:15.040Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577250)
;

-- 2025-10-03T11:51:15.042Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754728
;

-- 2025-10-03T11:51:15.043Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754728)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Aktiv
-- Column: Carrier_ShipmentOrder.IsActive
-- 2025-10-03T11:51:15.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591086,754729,0,548456,TO_TIMESTAMP('2025-10-03 11:51:15.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-03 11:51:15.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:15.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754729 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:15.193Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-03T11:51:15.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754729
;

-- 2025-10-03T11:51:15.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754729)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Lieferweg
-- Column: Carrier_ShipmentOrder.M_Shipper_ID
-- 2025-10-03T11:51:15.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591088,754730,0,548456,TO_TIMESTAMP('2025-10-03 11:51:15.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung',10,'D','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','N','N','N','N','N','Lieferweg',TO_TIMESTAMP('2025-10-03 11:51:15.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:15.550Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754730 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:15.551Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455)
;

-- 2025-10-03T11:51:15.564Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754730
;

-- 2025-10-03T11:51:15.565Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754730)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Transport Auftrag
-- Column: Carrier_ShipmentOrder.M_ShipperTransportation_ID
-- 2025-10-03T11:51:15.689Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591089,754731,0,548456,TO_TIMESTAMP('2025-10-03 11:51:15.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Transport Auftrag',TO_TIMESTAMP('2025-10-03 11:51:15.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:15.691Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754731 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:15.692Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089)
;

-- 2025-10-03T11:51:15.698Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754731
;

-- 2025-10-03T11:51:15.699Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754731)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Empfängerort
-- Column: Carrier_ShipmentOrder.Receiver_City
-- 2025-10-03T11:51:15.845Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591147,754732,0,548456,TO_TIMESTAMP('2025-10-03 11:51:15.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Empfängerort',TO_TIMESTAMP('2025-10-03 11:51:15.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:15.846Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754732 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:15.848Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584037)
;

-- 2025-10-03T11:51:15.850Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754732
;

-- 2025-10-03T11:51:15.851Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754732)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Lieferant EORI-Nummer
-- Column: Carrier_ShipmentOrder.Shipper_EORI
-- 2025-10-03T11:51:15.975Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591148,754733,0,548456,TO_TIMESTAMP('2025-10-03 11:51:15.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','Lieferant EORI-Nummer',TO_TIMESTAMP('2025-10-03 11:51:15.852000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:15.976Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754733 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:15.977Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584038)
;

-- 2025-10-03T11:51:15.979Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754733
;

-- 2025-10-03T11:51:15.980Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754733)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Empfänger EORI-Nummer
-- Column: Carrier_ShipmentOrder.Receiver_EORI
-- 2025-10-03T11:51:16.110Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591149,754734,0,548456,TO_TIMESTAMP('2025-10-03 11:51:15.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','Empfänger EORI-Nummer',TO_TIMESTAMP('2025-10-03 11:51:15.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T11:51:16.111Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754734 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T11:51:16.112Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584039)
;

-- 2025-10-03T11:51:16.115Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754734
;

-- 2025-10-03T11:51:16.116Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754734)
;

-- Tab: Versandauftrag(541956,D) -> Versandauftrag(548456,D)
-- UI Section: main
-- 2025-10-03T11:51:59.275Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548456,546982,TO_TIMESTAMP('2025-10-03 11:51:59.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 11:51:59.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-03T11:51:59.277Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546982 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main
-- UI Column: 10
-- 2025-10-03T11:52:01.959Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548507,546982,TO_TIMESTAMP('2025-10-03 11:52:01.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 11:52:01.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main
-- UI Column: 20
-- 2025-10-03T11:52:03.771Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548508,546982,TO_TIMESTAMP('2025-10-03 11:52:03.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-10-03 11:52:03.635000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10
-- UI Element Group: main
-- 2025-10-03T11:52:22.458Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548507,553598,TO_TIMESTAMP('2025-10-03 11:52:22.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,'primary',TO_TIMESTAMP('2025-10-03 11:52:22.283000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Transport Auftrag
-- Column: Carrier_ShipmentOrder.M_ShipperTransportation_ID
-- 2025-10-03T11:52:35.696Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754731,0,548456,553598,637663,'F',TO_TIMESTAMP('2025-10-03 11:52:35.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Transport Auftrag',10,0,0,TO_TIMESTAMP('2025-10-03 11:52:35.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Lieferweg
-- Column: Carrier_ShipmentOrder.M_Shipper_ID
-- 2025-10-03T11:52:43.335Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754730,0,548456,553598,637664,'F',TO_TIMESTAMP('2025-10-03 11:52:43.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','Y','N','N','N',0,'Lieferweg',20,0,0,TO_TIMESTAMP('2025-10-03 11:52:43.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10
-- UI Element Group: sizes
-- 2025-10-03T11:53:14.933Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548507,553599,TO_TIMESTAMP('2025-10-03 11:53:14.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','sizes',20,TO_TIMESTAMP('2025-10-03 11:53:14.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20
-- UI Element Group: flags
-- 2025-10-03T12:08:11.025Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548508,553600,TO_TIMESTAMP('2025-10-03 12:08:10.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2025-10-03 12:08:10.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> flags.Aktiv
-- Column: Carrier_ShipmentOrder.IsActive
-- 2025-10-03T12:08:23.399Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754729,0,548456,553600,637665,'F',TO_TIMESTAMP('2025-10-03 12:08:23.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2025-10-03 12:08:23.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10
-- UI Element Group: shipper
-- 2025-10-03T12:26:57.002Z
UPDATE AD_UI_ElementGroup SET Name='shipper',Updated=TO_TIMESTAMP('2025-10-03 12:26:57.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553599
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Lieferant Name 1
-- Column: Carrier_ShipmentOrder.Shipper_Name1
-- 2025-10-03T12:27:47.828Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754722,0,548456,553599,637666,'F',TO_TIMESTAMP('2025-10-03 12:27:47.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Lieferant Name 1',10,0,0,TO_TIMESTAMP('2025-10-03 12:27:47.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper. Lieferant Name 2
-- Column: Carrier_ShipmentOrder.Shipper_Name2
-- 2025-10-03T12:27:54.703Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754723,0,548456,553599,637667,'F',TO_TIMESTAMP('2025-10-03 12:27:54.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,' Lieferant Name 2',20,0,0,TO_TIMESTAMP('2025-10-03 12:27:54.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Lieferort
-- Column: Carrier_ShipmentOrder.Shipper_City
-- 2025-10-03T12:28:03.055Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754720,0,548456,553599,637668,'F',TO_TIMESTAMP('2025-10-03 12:28:02.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Lieferort',30,0,0,TO_TIMESTAMP('2025-10-03 12:28:02.857000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Straße 1 Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_StreetName1
-- 2025-10-03T12:28:16.531Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754724,0,548456,553599,637669,'F',TO_TIMESTAMP('2025-10-03 12:28:16.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Straße 1 Lieferant',40,0,0,TO_TIMESTAMP('2025-10-03 12:28:16.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Straße 2 Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_StreetName2
-- 2025-10-03T12:28:21.907Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754725,0,548456,553599,637670,'F',TO_TIMESTAMP('2025-10-03 12:28:21.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Straße 2 Lieferant',50,0,0,TO_TIMESTAMP('2025-10-03 12:28:21.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Hausnummer Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_StreetNumber
-- 2025-10-03T12:28:29.163Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754726,0,548456,553599,637671,'F',TO_TIMESTAMP('2025-10-03 12:28:28.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Hausnummer Lieferant',60,0,0,TO_TIMESTAMP('2025-10-03 12:28:28.961000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.PLZ Lieferant
-- Column: Carrier_ShipmentOrder.Shipper_ZipCode
-- 2025-10-03T12:28:40.513Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754727,0,548456,553599,637672,'F',TO_TIMESTAMP('2025-10-03 12:28:40.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'PLZ Lieferant',70,0,0,TO_TIMESTAMP('2025-10-03 12:28:40.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Lieferant Ländercode (ISO-3)
-- Column: Carrier_ShipmentOrder.Shipper_CountryISO3Code
-- 2025-10-03T12:28:47.348Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754721,0,548456,553599,637673,'F',TO_TIMESTAMP('2025-10-03 12:28:47.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Lieferant Ländercode (ISO-3)',80,0,0,TO_TIMESTAMP('2025-10-03 12:28:47.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20
-- UI Element Group: bpartner
-- 2025-10-03T12:29:39.474Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548508,553601,TO_TIMESTAMP('2025-10-03 12:29:39.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','bpartner',20,TO_TIMESTAMP('2025-10-03 12:29:39.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> bpartner.Geschäftspartner
-- Column: Carrier_ShipmentOrder.C_BPartner_ID
-- 2025-10-03T12:30:02.764Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754706,0,548456,553601,637674,'F',TO_TIMESTAMP('2025-10-03 12:30:02.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',10,0,0,TO_TIMESTAMP('2025-10-03 12:30:02.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> flags.International Delivery
-- Column: Carrier_ShipmentOrder.InternationalDelivery
-- 2025-10-03T12:30:24.103Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754728,0,548456,553600,637675,'F',TO_TIMESTAMP('2025-10-03 12:30:23.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'International Delivery',20,0,0,TO_TIMESTAMP('2025-10-03 12:30:23.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> bpartner.Name 1 Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Name1
-- 2025-10-03T12:31:28.954Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754711,0,548456,553601,637676,'F',TO_TIMESTAMP('2025-10-03 12:31:28.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name 1 Empfänger',20,0,0,TO_TIMESTAMP('2025-10-03 12:31:28.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20
-- UI Element Group: receiver
-- 2025-10-03T12:31:35.269Z
UPDATE AD_UI_ElementGroup SET Name='receiver',Updated=TO_TIMESTAMP('2025-10-03 12:31:35.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=553601
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Name 2 Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Name2
-- 2025-10-03T12:31:43.013Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754712,0,548456,553601,637677,'F',TO_TIMESTAMP('2025-10-03 12:31:42.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name 2 Empfänger',30,0,0,TO_TIMESTAMP('2025-10-03 12:31:42.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Empfängerort
-- Column: Carrier_ShipmentOrder.Receiver_City
-- 2025-10-03T12:32:06.999Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754732,0,548456,553601,637678,'F',TO_TIMESTAMP('2025-10-03 12:32:06.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Empfängerort',40,0,0,TO_TIMESTAMP('2025-10-03 12:32:06.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Straße 1 Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_StreetName1
-- 2025-10-03T12:32:21.691Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754714,0,548456,553601,637679,'F',TO_TIMESTAMP('2025-10-03 12:32:21.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Straße 1 Empfänger',50,0,0,TO_TIMESTAMP('2025-10-03 12:32:21.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Straße 2 Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_StreetName2
-- 2025-10-03T12:32:30.612Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754715,0,548456,553601,637680,'F',TO_TIMESTAMP('2025-10-03 12:32:30.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Straße 2 Empfänger',60,0,0,TO_TIMESTAMP('2025-10-03 12:32:30.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Hausnummer Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_StreetNumber
-- 2025-10-03T12:32:37.043Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754716,0,548456,553601,637681,'F',TO_TIMESTAMP('2025-10-03 12:32:36.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Hausnummer Empfänger',70,0,0,TO_TIMESTAMP('2025-10-03 12:32:36.865000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.PLZ Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_ZipCode
-- 2025-10-03T12:32:43.869Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754717,0,548456,553601,637682,'F',TO_TIMESTAMP('2025-10-03 12:32:43.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'PLZ Empfänger',80,0,0,TO_TIMESTAMP('2025-10-03 12:32:43.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Empfänger Ländercode (ISO-3)
-- Column: Carrier_ShipmentOrder.Receiver_CountryISO3Code
-- 2025-10-03T12:33:03.980Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754709,0,548456,553601,637683,'F',TO_TIMESTAMP('2025-10-03 12:33:03.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Empfänger Ländercode (ISO-3)',90,0,0,TO_TIMESTAMP('2025-10-03 12:33:03.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Lieferant Ländercode (ISO-3)
-- Column: Carrier_ShipmentOrder.Shipper_CountryISO3Code
-- 2025-10-03T12:34:44.541Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637673
;

-- 2025-10-03T12:34:44.542Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754721
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Lieferant Ländercode (ISO-3)
-- Column: Carrier_ShipmentOrder.Shipper_CountryISO3Code
-- 2025-10-03T12:34:44.546Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754721
;

-- 2025-10-03T12:34:44.550Z
DELETE FROM AD_Field WHERE AD_Field_ID=754721
;

-- 2025-10-03T12:34:44.552Z
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder','ALTER TABLE Carrier_ShipmentOrder DROP COLUMN IF EXISTS Shipper_CountryISO3Code')
;

-- Column: Carrier_ShipmentOrder.Shipper_CountryISO3Code
-- 2025-10-03T12:34:44.567Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591073
;

-- 2025-10-03T12:34:44.572Z
DELETE FROM AD_Column WHERE AD_Column_ID=591073
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Empfänger Ländercode (ISO-3)
-- Column: Carrier_ShipmentOrder.Receiver_CountryISO3Code
-- 2025-10-03T12:34:52.186Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637683
;

-- 2025-10-03T12:34:52.188Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754709
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Empfänger Ländercode (ISO-3)
-- Column: Carrier_ShipmentOrder.Receiver_CountryISO3Code
-- 2025-10-03T12:34:52.191Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754709
;

-- 2025-10-03T12:34:52.195Z
DELETE FROM AD_Field WHERE AD_Field_ID=754709
;

-- 2025-10-03T12:34:52.197Z
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder','ALTER TABLE Carrier_ShipmentOrder DROP COLUMN IF EXISTS Receiver_CountryISO3Code')
;

-- Column: Carrier_ShipmentOrder.Receiver_CountryISO3Code
-- 2025-10-03T12:34:52.208Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591058
;

-- 2025-10-03T12:34:52.212Z
DELETE FROM AD_Column WHERE AD_Column_ID=591058
;

-- Column: Carrier_ShipmentOrder.Shipper_CountryISO2Code
-- 2025-10-03T12:37:55.918Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591274,584018,0,10,542532,'XX','Shipper_CountryISO2Code',TO_TIMESTAMP('2025-10-03 12:37:55.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferant Ländercode (ISO-2)',0,0,TO_TIMESTAMP('2025-10-03 12:37:55.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-03T12:37:55.920Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591274 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-03T12:37:56.026Z
/* DDL */  select update_Column_Translation_From_AD_Element(584018)
;

-- 2025-10-03T12:37:59.895Z
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder','ALTER TABLE public.Carrier_ShipmentOrder ADD COLUMN Shipper_CountryISO2Code VARCHAR(2)')
;

-- Column: Carrier_ShipmentOrder.Receiver_CountryISO2Code
-- 2025-10-03T12:38:16.981Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591275,584005,0,10,542532,'XX','Receiver_CountryISO2Code',TO_TIMESTAMP('2025-10-03 12:38:16.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Empfänger Ländercode (ISO-2)',0,0,TO_TIMESTAMP('2025-10-03 12:38:16.799000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-03T12:38:16.983Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591275 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-03T12:38:17.086Z
/* DDL */  select update_Column_Translation_From_AD_Element(584005)
;

-- 2025-10-03T12:38:27.937Z
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder','ALTER TABLE public.Carrier_ShipmentOrder ADD COLUMN Receiver_CountryISO2Code VARCHAR(2)')
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Lieferant Ländercode (ISO-2)
-- Column: Carrier_ShipmentOrder.Shipper_CountryISO2Code
-- 2025-10-03T12:40:08.215Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591274,754735,0,548456,0,TO_TIMESTAMP('2025-10-03 12:40:08.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Lieferant Ländercode (ISO-2)',0,0,10,0,1,1,TO_TIMESTAMP('2025-10-03 12:40:08.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T12:40:08.217Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754735 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T12:40:08.220Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584018)
;

-- 2025-10-03T12:40:08.223Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754735
;

-- 2025-10-03T12:40:08.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754735)
;

-- Field: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> Empfänger Ländercode (ISO-2)
-- Column: Carrier_ShipmentOrder.Receiver_CountryISO2Code
-- 2025-10-03T12:40:14.661Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591275,754736,0,548456,0,TO_TIMESTAMP('2025-10-03 12:40:14.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Empfänger Ländercode (ISO-2)',0,0,20,0,1,1,TO_TIMESTAMP('2025-10-03 12:40:14.469000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T12:40:14.662Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754736 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T12:40:14.664Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584005)
;

-- 2025-10-03T12:40:14.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754736
;

-- 2025-10-03T12:40:14.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754736)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Lieferant Ländercode (ISO-2)
-- Column: Carrier_ShipmentOrder.Shipper_CountryISO2Code
-- 2025-10-03T12:40:54.342Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754735,0,548456,553599,637684,'F',TO_TIMESTAMP('2025-10-03 12:40:54.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Lieferant Ländercode (ISO-2)',80,0,0,TO_TIMESTAMP('2025-10-03 12:40:54.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Empfänger Ländercode (ISO-2)
-- Column: Carrier_ShipmentOrder.Receiver_CountryISO2Code
-- 2025-10-03T12:41:08.855Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754736,0,548456,553601,637685,'F',TO_TIMESTAMP('2025-10-03 12:41:08.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Empfänger Ländercode (ISO-2)',90,0,0,TO_TIMESTAMP('2025-10-03 12:41:08.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.E-Mail Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Email
-- 2025-10-03T12:42:53.598Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754710,0,548456,553601,637686,'F',TO_TIMESTAMP('2025-10-03 12:42:53.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'E-Mail Empfänger',100,0,0,TO_TIMESTAMP('2025-10-03 12:42:53.390000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.E-Mail Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Email
-- 2025-10-03T12:43:07.693Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2025-10-03 12:43:07.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637686
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Empfänger EORI-Nummer
-- Column: Carrier_ShipmentOrder.Receiver_EORI
-- 2025-10-03T12:43:24.234Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754734,0,548456,553601,637687,'F',TO_TIMESTAMP('2025-10-03 12:43:24.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Empfänger EORI-Nummer',100,0,0,TO_TIMESTAMP('2025-10-03 12:43:24.031000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 20 -> receiver.Telefon Empfänger
-- Column: Carrier_ShipmentOrder.Receiver_Phone
-- 2025-10-03T12:43:37.832Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754713,0,548456,553601,637688,'F',TO_TIMESTAMP('2025-10-03 12:43:37.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Telefon Empfänger',120,0,0,TO_TIMESTAMP('2025-10-03 12:43:37.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> shipper.Lieferant EORI-Nummer
-- Column: Carrier_ShipmentOrder.Shipper_EORI
-- 2025-10-03T12:43:52.213Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754733,0,548456,553599,637689,'F',TO_TIMESTAMP('2025-10-03 12:43:52.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Lieferant EORI-Nummer',90,0,0,TO_TIMESTAMP('2025-10-03 12:43:52.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Window: Versandauftrag, InternalName=Carrier_ShipmentOrder 541956
-- 2025-10-03T12:46:01.543Z
UPDATE AD_Window SET InternalName='Carrier_ShipmentOrder 541956',Updated=TO_TIMESTAMP('2025-10-03 12:46:01.541000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541956
;

-- Window: Versandauftrag, InternalName=541956 (Carrier_ShipmentOrder)
-- 2025-10-03T12:46:18.872Z
UPDATE AD_Window SET InternalName='541956 (Carrier_ShipmentOrder)',Updated=TO_TIMESTAMP('2025-10-03 12:46:18.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Window_ID=541956
;

-- Name: Versandauftrag
-- Action Type: W
-- Window: Versandauftrag(541956,D)
-- 2025-10-03T12:46:24.236Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584001,542259,0,541956,TO_TIMESTAMP('2025-10-03 12:46:24.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','541956 (Carrier_ShipmentOrder)','Y','N','N','N','N','Versandauftrag',TO_TIMESTAMP('2025-10-03 12:46:24.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T12:46:24.238Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542259 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-10-03T12:46:24.241Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542259, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542259)
;

-- 2025-10-03T12:46:24.251Z
/* DDL */  select update_menu_translation_from_ad_element(584001)
;

-- Reordering children of `Distribution Management`
-- Node name: `Distribution Network (DD_NetworkDistribution)`
-- 2025-10-03T12:46:24.833Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53088 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2025-10-03T12:46:24.835Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53068 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order Detail`
-- 2025-10-03T12:46:24.835Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53185 AND AD_Tree_ID=10
;

-- Node name: `Distribution List (M_DistributionList)`
-- 2025-10-03T12:46:24.836Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=445 AND AD_Tree_ID=10
;

-- Node name: `Distribution Run (M_DistributionRun)`
-- 2025-10-03T12:46:24.837Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=472 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move (M_Movement)`
-- 2025-10-03T12:46:24.838Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=181 AND AD_Tree_ID=10
;

-- Node name: `Move Confirmation (M_MovementConfirm)`
-- 2025-10-03T12:46:24.839Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=484 AND AD_Tree_ID=10
;

-- Node name: `Rohwaren zurück ins Lager (de.metas.distribution.ddorder.process.DD_Order_GenerateRawMaterialsReturn)`
-- 2025-10-03T12:46:24.840Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540581 AND AD_Tree_ID=10
;

-- Node name: `Versandauftrag`
-- 2025-10-03T12:46:24.841Z
UPDATE AD_TreeNodeMM SET Parent_ID=53066, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542259 AND AD_Tree_ID=10
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2025-10-03T12:46:32.960Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2025-10-03T12:46:32.961Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2025-10-03T12:46:32.962Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2025-10-03T12:46:32.963Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2025-10-03T12:46:32.964Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2025-10-03T12:46:32.965Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2025-10-03T12:46:32.965Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2025-10-03T12:46:32.966Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2025-10-03T12:46:32.967Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2025-10-03T12:46:32.967Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2025-10-03T12:46:32.968Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2025-10-03T12:46:32.969Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2025-10-03T12:46:32.970Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2025-10-03T12:46:32.971Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2025-10-03T12:46:32.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2025-10-03T12:46:32.972Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-03T12:46:32.973Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `Versandauftrag`
-- 2025-10-03T12:46:32.974Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542259 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2025-10-03T12:46:32.975Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-03T12:46:32.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-03T12:46:32.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2025-10-03T12:46:32.977Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2025-10-03T12:46:38.790Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2025-10-03T12:46:38.792Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2025-10-03T12:46:38.793Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2025-10-03T12:46:38.794Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2025-10-03T12:46:38.794Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2025-10-03T12:46:38.795Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2025-10-03T12:46:38.796Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2025-10-03T12:46:38.797Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2025-10-03T12:46:38.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2025-10-03T12:46:38.798Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2025-10-03T12:46:38.799Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2025-10-03T12:46:38.800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2025-10-03T12:46:38.801Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Versandauftrag`
-- 2025-10-03T12:46:38.802Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542259 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2025-10-03T12:46:38.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2025-10-03T12:46:38.803Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2025-10-03T12:46:38.804Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2025-10-03T12:46:38.805Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2025-10-03T12:46:38.806Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2025-10-03T12:46:38.806Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2025-10-03T12:46:38.807Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2025-10-03T12:46:38.808Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2025-10-03T12:46:44.612Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `QR Code Configuration (QRCode_Configuration)`
-- 2025-10-03T12:46:44.613Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542139 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2025-10-03T12:46:44.614Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2025-10-03T12:46:44.615Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2025-10-03T12:46:44.615Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2025-10-03T12:46:44.616Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2025-10-03T12:46:44.617Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2025-10-03T12:46:44.618Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2025-10-03T12:46:44.619Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2025-10-03T12:46:44.620Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2025-10-03T12:46:44.620Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2025-10-03T12:46:44.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2025-10-03T12:46:44.622Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2025-10-03T12:46:44.623Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2025-10-03T12:46:44.624Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2025-10-03T12:46:44.625Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2025-10-03T12:46:44.625Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2025-10-03T12:46:44.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2025-10-03T12:46:44.627Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2025-10-03T12:46:44.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2025-10-03T12:46:44.628Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2025-10-03T12:46:44.629Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2025-10-03T12:46:44.630Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2025-10-03T12:46:44.631Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-03T12:46:44.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Mobile UI HU Manager (MobileUI_HUManager)`
-- 2025-10-03T12:46:44.632Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542163 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-03T12:46:44.633Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-03T12:46:44.634Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2025-10-03T12:46:44.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2025-10-03T12:46:44.635Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2025-10-03T12:46:44.636Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Generate new HU QR Codes (de.metas.handlingunits.process.GenerateHUQRCodes)`
-- 2025-10-03T12:46:44.637Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542152 AND AD_Tree_ID=10
;

-- Node name: `Distribution Candidate (DD_Order_Candidate)`
-- 2025-10-03T12:46:44.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542164 AND AD_Tree_ID=10
;

-- Node name: `Scannable Code Format (C_ScannableCode_Format)`
-- 2025-10-03T12:46:44.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542221 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order Log (Dhl_ShipmentOrder_Log)`
-- 2025-10-03T12:46:44.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542218 AND AD_Tree_ID=10
;

-- Node name: `Versandauftrag`
-- 2025-10-03T12:46:44.640Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542259 AND AD_Tree_ID=10
;

-- Node name: `Carrier Order Log (Carrier_ShipmentOrder_Log)`
-- 2025-10-03T12:46:44.641Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542258 AND AD_Tree_ID=10
;

-- UI Element: Versandauftrag(541956,D) -> Versandauftrag(548456,D) -> main -> 10 -> main.Produkt
-- Column: Carrier_ShipmentOrder.Carrier_Product
-- 2025-10-03T13:05:03.594Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754708,0,548456,553598,637690,'F',TO_TIMESTAMP('2025-10-03 13:05:03.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Produkt',30,0,0,TO_TIMESTAMP('2025-10-03 13:05:03.350000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Versandauftrag(541956,D) -> Shipment Order Parcel
-- Table: Carrier_ShipmentOrder_Parcel
-- 2025-10-03T13:11:38.218Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584036,0,548457,542535,541956,'Y',TO_TIMESTAMP('2025-10-03 13:11:38.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','Carrier_ShipmentOrder_Parcel','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Shipment Order Parcel','N',20,1,TO_TIMESTAMP('2025-10-03 13:11:38.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:38.219Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548457 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-10-03T13:11:38.221Z
/* DDL */  select update_tab_translation_from_ad_element(584036)
;

-- 2025-10-03T13:11:38.225Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548457)
;

-- Tab: Versandauftrag(541956,D) -> Shipment Order Parcel
-- Table: Carrier_ShipmentOrder_Parcel
-- 2025-10-03T13:11:42.746Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-10-03 13:11:42.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548457
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Mandant
-- Column: Carrier_ShipmentOrder_Parcel.AD_Client_ID
-- 2025-10-03T13:11:52.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591129,754737,0,548457,TO_TIMESTAMP('2025-10-03 13:11:51.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-10-03 13:11:51.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:52.087Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754737 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:52.089Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-10-03T13:11:52.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754737
;

-- 2025-10-03T13:11:52.489Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754737)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Sektion
-- Column: Carrier_ShipmentOrder_Parcel.AD_Org_ID
-- 2025-10-03T13:11:52.615Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591130,754738,0,548457,TO_TIMESTAMP('2025-10-03 13:11:52.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-10-03 13:11:52.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:52.616Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754738 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:52.618Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-10-03T13:11:52.739Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754738
;

-- 2025-10-03T13:11:52.740Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754738)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Aktiv
-- Column: Carrier_ShipmentOrder_Parcel.IsActive
-- 2025-10-03T13:11:52.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591133,754739,0,548457,TO_TIMESTAMP('2025-10-03 13:11:52.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-10-03 13:11:52.743000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:52.856Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754739 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:52.858Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-10-03T13:11:53.020Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754739
;

-- 2025-10-03T13:11:53.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754739)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Shipment Order Parcel
-- Column: Carrier_ShipmentOrder_Parcel.Carrier_ShipmentOrder_Parcel_ID
-- 2025-10-03T13:11:53.152Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591136,754740,0,548457,TO_TIMESTAMP('2025-10-03 13:11:53.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Shipment Order Parcel',TO_TIMESTAMP('2025-10-03 13:11:53.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:53.153Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754740 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:53.154Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584036)
;

-- 2025-10-03T13:11:53.156Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754740
;

-- 2025-10-03T13:11:53.157Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754740)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Versandauftrag
-- Column: Carrier_ShipmentOrder_Parcel.Carrier_ShipmentOrder_ID
-- 2025-10-03T13:11:53.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591137,754741,0,548457,TO_TIMESTAMP('2025-10-03 13:11:53.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Versandauftrag',TO_TIMESTAMP('2025-10-03 13:11:53.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:53.279Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754741 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:53.280Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584001)
;

-- 2025-10-03T13:11:53.282Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754741
;

-- 2025-10-03T13:11:53.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754741)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Sendungsnummer
-- Column: Carrier_ShipmentOrder_Parcel.awb
-- 2025-10-03T13:11:53.427Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591138,754742,0,548457,TO_TIMESTAMP('2025-10-03 13:11:53.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,100,'D','Y','N','N','N','N','N','N','N','Sendungsnummer',TO_TIMESTAMP('2025-10-03 13:11:53.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:53.428Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754742 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:53.430Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577217)
;

-- 2025-10-03T13:11:53.434Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754742
;

-- 2025-10-03T13:11:53.434Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754742)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Weight In Kg
-- Column: Carrier_ShipmentOrder_Parcel.WeightInKg
-- 2025-10-03T13:11:53.571Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591139,754743,0,548457,TO_TIMESTAMP('2025-10-03 13:11:53.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Weight In Kg',TO_TIMESTAMP('2025-10-03 13:11:53.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:53.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754743 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:53.573Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577306)
;

-- 2025-10-03T13:11:53.575Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754743
;

-- 2025-10-03T13:11:53.576Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754743)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Paketscheindaten PDF
-- Column: Carrier_ShipmentOrder_Parcel.PdfLabelData
-- 2025-10-03T13:11:53.708Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591140,754744,0,548457,TO_TIMESTAMP('2025-10-03 13:11:53.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D','Y','N','N','N','N','N','N','N','Paketscheindaten PDF',TO_TIMESTAMP('2025-10-03 13:11:53.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:53.709Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754744 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:53.710Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577216)
;

-- 2025-10-03T13:11:53.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754744
;

-- 2025-10-03T13:11:53.713Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754744)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Nachverfolgungs-URL
-- Column: Carrier_ShipmentOrder_Parcel.TrackingURL
-- 2025-10-03T13:11:53.837Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591141,754745,0,548457,TO_TIMESTAMP('2025-10-03 13:11:53.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL des Spediteurs um Sendungen zu verfolgen',255,'D','Die variable @TrackingNo@ in der URL wird durch die tatsächliche Identifizierungsnummer der Sendung ersetzt.','Y','N','N','N','N','N','N','N','Nachverfolgungs-URL',TO_TIMESTAMP('2025-10-03 13:11:53.716000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:53.838Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754745 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:53.839Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2127)
;

-- 2025-10-03T13:11:53.842Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754745
;

-- 2025-10-03T13:11:53.843Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754745)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Height In Cm
-- Column: Carrier_ShipmentOrder_Parcel.HeightInCm
-- 2025-10-03T13:11:53.969Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591142,754746,0,548457,TO_TIMESTAMP('2025-10-03 13:11:53.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Height In Cm',TO_TIMESTAMP('2025-10-03 13:11:53.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:53.970Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754746 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:53.971Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577309)
;

-- 2025-10-03T13:11:53.973Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754746
;

-- 2025-10-03T13:11:53.973Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754746)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Length In Cm
-- Column: Carrier_ShipmentOrder_Parcel.LengthInCm
-- 2025-10-03T13:11:54.104Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591143,754747,0,548457,TO_TIMESTAMP('2025-10-03 13:11:53.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Length In Cm',TO_TIMESTAMP('2025-10-03 13:11:53.975000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:54.106Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754747 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:54.107Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577307)
;

-- 2025-10-03T13:11:54.109Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754747
;

-- 2025-10-03T13:11:54.110Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754747)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Width In Cm
-- Column: Carrier_ShipmentOrder_Parcel.WidthInCm
-- 2025-10-03T13:11:54.235Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591144,754748,0,548457,TO_TIMESTAMP('2025-10-03 13:11:54.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Width In Cm',TO_TIMESTAMP('2025-10-03 13:11:54.112000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:54.236Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754748 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:54.238Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577308)
;

-- 2025-10-03T13:11:54.240Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754748
;

-- 2025-10-03T13:11:54.241Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754748)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Packstück
-- Column: Carrier_ShipmentOrder_Parcel.M_Package_ID
-- 2025-10-03T13:11:54.365Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591145,754749,0,548457,TO_TIMESTAMP('2025-10-03 13:11:54.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Shipment Package',10,'D','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','N','N','N','N','N','Packstück',TO_TIMESTAMP('2025-10-03 13:11:54.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:54.367Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754749 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:54.368Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2410)
;

-- 2025-10-03T13:11:54.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754749
;

-- 2025-10-03T13:11:54.375Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754749)
;

-- Field: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> Paketbeschreibung
-- Column: Carrier_ShipmentOrder_Parcel.PackageDescription
-- 2025-10-03T13:11:54.502Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591146,754750,0,548457,TO_TIMESTAMP('2025-10-03 13:11:54.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Paketbeschreibung',TO_TIMESTAMP('2025-10-03 13:11:54.377000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-03T13:11:54.503Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754750 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-03T13:11:54.504Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577246)
;

-- 2025-10-03T13:11:54.506Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754750
;

-- 2025-10-03T13:11:54.507Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754750)
;

-- Tab: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D)
-- UI Section: main
-- 2025-10-03T13:14:51.163Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548457,546983,TO_TIMESTAMP('2025-10-03 13:14:50.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 13:14:50.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-10-03T13:14:51.165Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546983 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main
-- UI Column: 10
-- 2025-10-03T13:14:55.451Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548509,546983,TO_TIMESTAMP('2025-10-03 13:14:55.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-10-03 13:14:55.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10
-- UI Element Group: main
-- 2025-10-03T13:15:00.627Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548509,553602,TO_TIMESTAMP('2025-10-03 13:15:00.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-10-03 13:15:00.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Packstück
-- Column: Carrier_ShipmentOrder_Parcel.M_Package_ID
-- 2025-10-03T13:15:29.045Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754749,0,548457,553602,637691,'F',TO_TIMESTAMP('2025-10-03 13:15:28.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Shipment Package','A Shipment can have one or more Packages.  A Package may be individually tracked.','Y','N','N','Y','N','N','N',0,'Packstück',10,0,0,TO_TIMESTAMP('2025-10-03 13:15:28.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Length In Cm
-- Column: Carrier_ShipmentOrder_Parcel.LengthInCm
-- 2025-10-03T13:16:17.113Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754747,0,548457,553602,637692,'F',TO_TIMESTAMP('2025-10-03 13:16:16.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Length In Cm',20,0,0,TO_TIMESTAMP('2025-10-03 13:16:16.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Height In Cm
-- Column: Carrier_ShipmentOrder_Parcel.HeightInCm
-- 2025-10-03T13:16:27.397Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754746,0,548457,553602,637693,'F',TO_TIMESTAMP('2025-10-03 13:16:27.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Height In Cm',30,0,0,TO_TIMESTAMP('2025-10-03 13:16:27.222000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Width In Cm
-- Column: Carrier_ShipmentOrder_Parcel.WidthInCm
-- 2025-10-03T13:16:36.989Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754748,0,548457,553602,637694,'F',TO_TIMESTAMP('2025-10-03 13:16:36.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Width In Cm',40,0,0,TO_TIMESTAMP('2025-10-03 13:16:36.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Weight In Kg
-- Column: Carrier_ShipmentOrder_Parcel.WeightInKg
-- 2025-10-03T13:16:42.005Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754743,0,548457,553602,637695,'F',TO_TIMESTAMP('2025-10-03 13:16:41.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Weight In Kg',50,0,0,TO_TIMESTAMP('2025-10-03 13:16:41.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Sendungsnummer
-- Column: Carrier_ShipmentOrder_Parcel.awb
-- 2025-10-03T13:16:49.456Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754742,0,548457,553602,637696,'F',TO_TIMESTAMP('2025-10-03 13:16:49.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Sendungsnummer',60,0,0,TO_TIMESTAMP('2025-10-03 13:16:49.275000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Paketbeschreibung
-- Column: Carrier_ShipmentOrder_Parcel.PackageDescription
-- 2025-10-03T13:17:06.076Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754750,0,548457,553602,637697,'F',TO_TIMESTAMP('2025-10-03 13:17:05.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Paketbeschreibung',70,0,0,TO_TIMESTAMP('2025-10-03 13:17:05.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Nachverfolgungs-URL
-- Column: Carrier_ShipmentOrder_Parcel.TrackingURL
-- 2025-10-03T13:17:10.926Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754745,0,548457,553602,637698,'F',TO_TIMESTAMP('2025-10-03 13:17:10.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'URL des Spediteurs um Sendungen zu verfolgen','Die variable @TrackingNo@ in der URL wird durch die tatsächliche Identifizierungsnummer der Sendung ersetzt.','Y','N','N','Y','N','N','N',0,'Nachverfolgungs-URL',80,0,0,TO_TIMESTAMP('2025-10-03 13:17:10.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Sendungsnummer
-- Column: Carrier_ShipmentOrder_Parcel.awb
-- 2025-10-03T13:18:15.730Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-03 13:18:15.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637696
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Packstück
-- Column: Carrier_ShipmentOrder_Parcel.M_Package_ID
-- 2025-10-03T13:18:15.736Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-03 13:18:15.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637691
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Length In Cm
-- Column: Carrier_ShipmentOrder_Parcel.LengthInCm
-- 2025-10-03T13:18:15.741Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-03 13:18:15.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637692
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Height In Cm
-- Column: Carrier_ShipmentOrder_Parcel.HeightInCm
-- 2025-10-03T13:18:15.746Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-03 13:18:15.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637693
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Width In Cm
-- Column: Carrier_ShipmentOrder_Parcel.WidthInCm
-- 2025-10-03T13:18:15.750Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-03 13:18:15.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637694
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Weight In Kg
-- Column: Carrier_ShipmentOrder_Parcel.WeightInKg
-- 2025-10-03T13:18:15.755Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-03 13:18:15.755000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637695
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Nachverfolgungs-URL
-- Column: Carrier_ShipmentOrder_Parcel.TrackingURL
-- 2025-10-03T13:18:15.759Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-03 13:18:15.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637698
;

-- UI Element: Versandauftrag(541956,D) -> Shipment Order Parcel(548457,D) -> main -> 10 -> main.Paketbeschreibung
-- Column: Carrier_ShipmentOrder_Parcel.PackageDescription
-- 2025-10-03T13:18:25.521Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637697
;

