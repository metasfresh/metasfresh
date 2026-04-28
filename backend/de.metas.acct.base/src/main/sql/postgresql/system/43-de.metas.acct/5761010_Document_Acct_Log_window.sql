-- Run mode: SWING_CLIENT

-- Window: Document Accounting Log, InternalName=docAcctLog
-- 2025-07-16T21:24:44.607Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583814,0,541913,TO_TIMESTAMP('2025-07-16 21:24:44.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.acct','docAcctLog','Y','N','N','N','N','N','N','Y','Document Accounting Log','N',TO_TIMESTAMP('2025-07-16 21:24:44.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Q',0,0,100)
;

-- 2025-07-16T21:24:44.612Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541913 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2025-07-16T21:24:44.785Z
/* DDL */  select update_window_translation_from_ad_element(583814)
;

-- 2025-07-16T21:24:44.812Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541913
;

-- 2025-07-16T21:24:44.815Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541913)
;

-- Tab: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log
-- Table: Document_Acct_Log
-- 2025-07-16T21:25:04.467Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583814,0,548291,542510,541913,'Y',TO_TIMESTAMP('2025-07-16 21:25:04.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.acct','N','N','A','Document_Acct_Log','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Document Accounting Log','N',10,0,TO_TIMESTAMP('2025-07-16 21:25:04.255000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:04.472Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548291 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-07-16T21:25:04.476Z
/* DDL */  select update_tab_translation_from_ad_element(583814)
;

-- 2025-07-16T21:25:04.481Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548291)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Mandant
-- Column: Document_Acct_Log.AD_Client_ID
-- 2025-07-16T21:25:07.539Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590539,750376,0,548291,TO_TIMESTAMP('2025-07-16 21:25:07.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.acct','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-07-16 21:25:07.358000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:07.546Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750376 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:07.550Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-07-16T21:25:08.854Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750376
;

-- 2025-07-16T21:25:08.856Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750376)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Sektion
-- Column: Document_Acct_Log.AD_Org_ID
-- 2025-07-16T21:25:09.038Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590540,750377,0,548291,TO_TIMESTAMP('2025-07-16 21:25:08.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.acct','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-07-16 21:25:08.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:09.040Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750377 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:09.043Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-07-16T21:25:09.288Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750377
;

-- 2025-07-16T21:25:09.290Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750377)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> DB-Tabelle
-- Column: Document_Acct_Log.AD_Table_ID
-- 2025-07-16T21:25:09.413Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590541,750378,0,548291,TO_TIMESTAMP('2025-07-16 21:25:09.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',10,'de.metas.acct','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2025-07-16 21:25:09.293000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:09.416Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750378 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:09.418Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2025-07-16T21:25:09.507Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750378
;

-- 2025-07-16T21:25:09.509Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750378)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Document Accounting Log
-- Column: Document_Acct_Log.Document_Acct_Log_ID
-- 2025-07-16T21:25:09.646Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590543,750379,0,548291,TO_TIMESTAMP('2025-07-16 21:25:09.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','Document Accounting Log',TO_TIMESTAMP('2025-07-16 21:25:09.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:09.648Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750379 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:09.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583814)
;

-- 2025-07-16T21:25:09.654Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750379
;

-- 2025-07-16T21:25:09.655Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750379)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Aktiv
-- Column: Document_Acct_Log.IsActive
-- 2025-07-16T21:25:09.783Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590547,750380,0,548291,TO_TIMESTAMP('2025-07-16 21:25:09.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.acct','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-07-16 21:25:09.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:09.786Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750380 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:09.789Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-07-16T21:25:09.947Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750380
;

-- 2025-07-16T21:25:09.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750380)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Datensatz-ID
-- Column: Document_Acct_Log.Record_ID
-- 2025-07-16T21:25:10.069Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590553,750381,0,548291,TO_TIMESTAMP('2025-07-16 21:25:09.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID',10,'de.metas.acct','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2025-07-16 21:25:09.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:10.072Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750381 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:10.074Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538)
;

-- 2025-07-16T21:25:10.115Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750381
;

-- 2025-07-16T21:25:10.116Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750381)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Ansprechpartner
-- Column: Document_Acct_Log.AD_User_ID
-- 2025-07-16T21:25:10.249Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590554,750382,0,548291,TO_TIMESTAMP('2025-07-16 21:25:10.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact',10,'de.metas.acct','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2025-07-16 21:25:10.123000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:10.251Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750382 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:10.253Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138)
;

-- 2025-07-16T21:25:10.278Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750382
;

-- 2025-07-16T21:25:10.280Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750382)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Probleme
-- Column: Document_Acct_Log.AD_Issue_ID
-- 2025-07-16T21:25:10.406Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590555,750383,0,548291,TO_TIMESTAMP('2025-07-16 21:25:10.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'de.metas.acct','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2025-07-16 21:25:10.285000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:10.408Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750383 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:10.409Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887)
;

-- 2025-07-16T21:25:10.427Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750383
;

-- 2025-07-16T21:25:10.428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750383)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Art
-- Column: Document_Acct_Log.Type
-- 2025-07-16T21:25:10.556Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590556,750384,0,548291,TO_TIMESTAMP('2025-07-16 21:25:10.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',40,'de.metas.acct','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2025-07-16 21:25:10.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:25:10.558Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750384 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:25:10.559Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600)
;

-- 2025-07-16T21:25:10.588Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750384
;

-- 2025-07-16T21:25:10.589Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750384)
;

-- Tab: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct)
-- UI Section: main
-- 2025-07-16T21:26:04.781Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548291,546818,TO_TIMESTAMP('2025-07-16 21:26:04.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-07-16 21:26:04.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-07-16T21:26:04.783Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546818 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main
-- UI Column: 10
-- 2025-07-16T21:26:10.049Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548299,546818,TO_TIMESTAMP('2025-07-16 21:26:09.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-07-16 21:26:09.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main
-- UI Column: 20
-- 2025-07-16T21:26:11.508Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548300,546818,TO_TIMESTAMP('2025-07-16 21:26:11.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-07-16 21:26:11.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10
-- UI Element Group: primary
-- 2025-07-16T21:26:24.738Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548299,553262,TO_TIMESTAMP('2025-07-16 21:26:24.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','primary',10,'primary',TO_TIMESTAMP('2025-07-16 21:26:24.546000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> primary.DB-Tabelle
-- Column: Document_Acct_Log.AD_Table_ID
-- 2025-07-16T21:26:43.990Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750378,0,548291,553262,635285,'F',TO_TIMESTAMP('2025-07-16 21:26:42.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','N','N','DB-Tabelle',10,0,0,TO_TIMESTAMP('2025-07-16 21:26:42.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> primary.Datensatz-ID
-- Column: Document_Acct_Log.Record_ID
-- 2025-07-16T21:26:51.812Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750381,0,548291,553262,635286,'F',TO_TIMESTAMP('2025-07-16 21:26:51.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','Datensatz-ID',20,0,0,TO_TIMESTAMP('2025-07-16 21:26:51.637000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> primary.Art
-- Column: Document_Acct_Log.Type
-- 2025-07-16T21:26:59.676Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750384,0,548291,553262,635287,'F',TO_TIMESTAMP('2025-07-16 21:26:59.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Art',30,0,0,TO_TIMESTAMP('2025-07-16 21:26:59.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> primary.Ansprechpartner
-- Column: Document_Acct_Log.AD_User_ID
-- 2025-07-16T21:27:27.884Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750382,0,548291,553262,635288,'F',TO_TIMESTAMP('2025-07-16 21:27:27.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','Ansprechpartner',40,0,0,TO_TIMESTAMP('2025-07-16 21:27:27.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 20
-- UI Element Group: error
-- 2025-07-16T21:27:56.842Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548300,553263,TO_TIMESTAMP('2025-07-16 21:27:53.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','error',10,TO_TIMESTAMP('2025-07-16 21:27:53.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 20 -> error.Probleme
-- Column: Document_Acct_Log.AD_Issue_ID
-- 2025-07-16T21:28:11.156Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750383,0,548291,553263,635289,'F',TO_TIMESTAMP('2025-07-16 21:28:10.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','','Y','N','Y','N','N','Probleme',10,0,0,TO_TIMESTAMP('2025-07-16 21:28:10.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: Document_Acct_Log.AD_User_ID
-- 2025-07-16T21:28:39.264Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-07-16 21:28:39.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590554
;

-- Column: Document_Acct_Log.Type
-- 2025-07-16T21:28:53.454Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-07-16 21:28:53.453000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590556
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> primary.DB-Tabelle
-- Column: Document_Acct_Log.AD_Table_ID
-- 2025-07-16T21:29:28.618Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-07-16 21:29:28.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635285
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> primary.Datensatz-ID
-- Column: Document_Acct_Log.Record_ID
-- 2025-07-16T21:29:28.627Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-07-16 21:29:28.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635286
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> primary.Art
-- Column: Document_Acct_Log.Type
-- 2025-07-16T21:29:28.636Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-07-16 21:29:28.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635287
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> primary.Ansprechpartner
-- Column: Document_Acct_Log.AD_User_ID
-- 2025-07-16T21:29:28.645Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-07-16 21:29:28.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635288
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 20 -> error.Probleme
-- Column: Document_Acct_Log.AD_Issue_ID
-- 2025-07-16T21:29:28.654Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-07-16 21:29:28.654000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635289
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Document Accounting Log
-- Column: Document_Acct_Log.Document_Acct_Log_ID
-- 2025-07-16T21:29:46.218Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2025-07-16 21:29:46.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=750379
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Erstellt
-- Column: Document_Acct_Log.Created
-- 2025-07-16T21:30:19.974Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590544,750385,0,548291,TO_TIMESTAMP('2025-07-16 21:30:19.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',7,'de.metas.acct','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2025-07-16 21:30:19.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:30:19.977Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750385 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:30:19.979Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-07-16T21:30:20.138Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750385
;

-- 2025-07-16T21:30:20.140Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750385)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Erstellt durch
-- Column: Document_Acct_Log.CreatedBy
-- 2025-07-16T21:30:20.259Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590545,750386,0,548291,TO_TIMESTAMP('2025-07-16 21:30:20.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.acct','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2025-07-16 21:30:20.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:30:20.261Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750386 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:30:20.262Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2025-07-16T21:30:20.324Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750386
;

-- 2025-07-16T21:30:20.326Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750386)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Aktualisiert
-- Column: Document_Acct_Log.Updated
-- 2025-07-16T21:30:20.452Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590550,750387,0,548291,TO_TIMESTAMP('2025-07-16 21:30:20.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'de.metas.acct','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2025-07-16 21:30:20.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:30:20.453Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750387 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:30:20.456Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2025-07-16T21:30:20.559Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750387
;

-- 2025-07-16T21:30:20.560Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750387)
;

-- Field: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> Aktualisiert durch
-- Column: Document_Acct_Log.UpdatedBy
-- 2025-07-16T21:30:20.674Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590551,750388,0,548291,TO_TIMESTAMP('2025-07-16 21:30:20.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.acct','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2025-07-16 21:30:20.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:30:20.676Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750388 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-16T21:30:20.677Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2025-07-16T21:30:20.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750388
;

-- 2025-07-16T21:30:20.714Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750388)
;

-- UI Column: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10
-- UI Element Group: created
-- 2025-07-16T21:30:55.120Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548299,553264,TO_TIMESTAMP('2025-07-16 21:30:54.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','created',20,TO_TIMESTAMP('2025-07-16 21:30:54.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Document Accounting Log(541913,de.metas.acct) -> Document Accounting Log(548291,de.metas.acct) -> main -> 10 -> created.Erstellt
-- Column: Document_Acct_Log.Created
-- 2025-07-16T21:31:07.258Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750385,0,548291,553264,635290,'F',TO_TIMESTAMP('2025-07-16 21:31:07.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','N','N','Erstellt',10,0,0,TO_TIMESTAMP('2025-07-16 21:31:07.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: Document_Acct_Log.Created
-- 2025-07-16T21:31:21.710Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-07-16 21:31:21.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590544
;

-- Name: Document Accounting Log
-- Action Type: W
-- Window: Document Accounting Log(541913,de.metas.acct)
-- 2025-07-16T21:33:34.425Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583814,542238,0,541913,TO_TIMESTAMP('2025-07-16 21:33:34.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.acct','docAcctLog','Y','N','N','N','N','Document Accounting Log',TO_TIMESTAMP('2025-07-16 21:33:34.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-16T21:33:34.429Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542238 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-07-16T21:33:34.434Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542238, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542238)
;

-- 2025-07-16T21:33:34.455Z
/* DDL */  select update_menu_translation_from_ad_element(583814)
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2025-07-16T21:33:35.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-07-16T21:33:35.051Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2025-07-16T21:33:35.053Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2025-07-16T21:33:35.054Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2025-07-16T21:33:35.056Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2025-07-16T21:33:35.057Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2025-07-16T21:33:35.058Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution (GL_Distribution)`
-- 2025-07-16T21:33:35.060Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2025-07-16T21:33:35.061Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2025-07-16T21:33:35.062Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2025-07-16T21:33:35.062Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2025-07-16T21:33:35.064Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2025-07-16T21:33:35.065Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2025-07-16T21:33:35.066Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2025-07-16T21:33:35.067Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2025-07-16T21:33:35.068Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2025-07-16T21:33:35.068Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2025-07-16T21:33:35.069Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2025-07-16T21:33:35.070Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2025-07-16T21:33:35.071Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2025-07-16T21:33:35.071Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2025-07-16T21:33:35.072Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2025-07-16T21:33:35.073Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2025-07-16T21:33:35.073Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2025-07-16T21:33:35.075Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-07-16T21:33:35.075Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-07-16T21:33:35.076Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2025-07-16T21:33:35.077Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2025-07-16T21:33:35.078Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2025-07-16T21:33:35.079Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2025-07-16T21:33:35.080Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2025-07-16T21:33:35.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2025-07-16T21:33:35.083Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2025-07-16T21:33:35.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2025-07-16T21:33:35.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2025-07-16T21:33:35.086Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2025-07-16T21:33:35.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2025-07-16T21:33:35.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2025-07-16T21:33:35.088Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-07-16T21:33:35.089Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2025-07-16T21:33:35.090Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2025-07-16T21:33:35.091Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-07-16T21:33:35.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-07-16T21:33:35.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2025-07-16T21:33:35.093Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2025-07-16T21:33:35.094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log`
-- 2025-07-16T21:33:35.095Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2025-07-16T21:34:01.146Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `Kontenauszug (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-07-16T21:34:01.148Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542131 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2025-07-16T21:34:01.150Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2025-07-16T21:34:01.151Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2025-07-16T21:34:01.152Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2025-07-16T21:34:01.152Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2025-07-16T21:34:01.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `GL Distribution (GL_Distribution)`
-- 2025-07-16T21:34:01.154Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=464 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2025-07-16T21:34:01.154Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2025-07-16T21:34:01.155Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2025-07-16T21:34:01.156Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2025-07-16T21:34:01.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2025-07-16T21:34:01.159Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2025-07-16T21:34:01.160Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2025-07-16T21:34:01.160Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2025-07-16T21:34:01.161Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2025-07-16T21:34:01.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2025-07-16T21:34:01.163Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2025-07-16T21:34:01.164Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2025-07-16T21:34:01.165Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2025-07-16T21:34:01.166Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2025-07-16T21:34:01.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2025-07-16T21:34:01.168Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2025-07-16T21:34:01.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2025-07-16T21:34:01.170Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-07-16T21:34:01.171Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-07-16T21:34:01.172Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2025-07-16T21:34:01.174Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2025-07-16T21:34:01.175Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2025-07-16T21:34:01.175Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2025-07-16T21:34:01.177Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2025-07-16T21:34:01.177Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2025-07-16T21:34:01.179Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2025-07-16T21:34:01.180Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2025-07-16T21:34:01.182Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2025-07-16T21:34:01.183Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2025-07-16T21:34:01.184Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2025-07-16T21:34:01.185Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2025-07-16T21:34:01.185Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-07-16T21:34:01.186Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2025-07-16T21:34:01.187Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2025-07-16T21:34:01.188Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-07-16T21:34:01.189Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-07-16T21:34:01.190Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2025-07-16T21:34:01.191Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2025-07-16T21:34:01.192Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Document Accounting Log`
-- 2025-07-16T21:34:01.194Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542238 AND AD_Tree_ID=10
;

