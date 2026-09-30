-- Column: C_Proforma_Order_Alloc.C_Invoice_ID
-- 2026-04-28T15:55:28.084Z
UPDATE AD_Column SET AD_Reference_ID=30, IsUpdateable='N',Updated=TO_TIMESTAMP('2026-04-28 15:55:28.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592359
;

-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-28T15:55:33.226Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2026-04-28 15:55:33.226000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592360
;

-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-28T15:55:51.433Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-04-28 15:55:51.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592360
;

-- Column: C_Proforma_Order_Alloc.C_Invoice_ID
-- 2026-04-28T15:55:59.391Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-04-28 15:55:59.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592359
;

-- Window: Proforma Bestellung Zuordnung, InternalName=null
-- 2026-04-28T16:00:35.376Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584755,0,542139,TO_TIMESTAMP('2026-04-28 16:00:35.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','Y','Proforma Bestellung Zuordnung','N',TO_TIMESTAMP('2026-04-28 16:00:35.192000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2026-04-28T16:00:35.382Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542139 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-04-28T16:00:35.390Z
/* DDL */  select update_window_translation_from_ad_element(584755)
;

-- 2026-04-28T16:00:35.398Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542139
;

-- 2026-04-28T16:00:35.403Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542139)
;

-- Tab: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung
-- Table: C_Proforma_Order_Alloc
-- 2026-04-28T16:00:51.872Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584755,0,549178,542590,542139,'Y',TO_TIMESTAMP('2026-04-28 16:00:51.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_Proforma_Order_Alloc','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Proforma Bestellung Zuordnung','N',10,0,TO_TIMESTAMP('2026-04-28 16:00:51.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-28T16:00:51.884Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549178 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-04-28T16:00:51.887Z
/* DDL */  select update_tab_translation_from_ad_element(584755)
;

-- 2026-04-28T16:00:51.892Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549178)
;

-- Field: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> Mandant
-- Column: C_Proforma_Order_Alloc.AD_Client_ID
-- 2026-04-28T16:01:00.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592351,777463,0,549178,TO_TIMESTAMP('2026-04-28 16:01:00.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-04-28 16:01:00.456000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-28T16:01:00.642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-28T16:01:00.646Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-04-28T16:01:01.870Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777463
;

-- 2026-04-28T16:01:01.871Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777463)
;

-- Field: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> Sektion
-- Column: C_Proforma_Order_Alloc.AD_Org_ID
-- 2026-04-28T16:01:01.995Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592352,777464,0,549178,TO_TIMESTAMP('2026-04-28 16:01:01.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-04-28 16:01:01.877000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-28T16:01:01.998Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777464 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-28T16:01:02.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-04-28T16:01:02.465Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777464
;

-- 2026-04-28T16:01:02.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777464)
;

-- Field: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> Aktiv
-- Column: C_Proforma_Order_Alloc.IsActive
-- 2026-04-28T16:01:02.577Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592355,777465,0,549178,TO_TIMESTAMP('2026-04-28 16:01:02.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-04-28 16:01:02.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-28T16:01:02.580Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777465 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-28T16:01:02.584Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-04-28T16:01:03.230Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777465
;

-- 2026-04-28T16:01:03.231Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777465)
;

-- Field: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> Proforma Bestellung Zuordnung
-- Column: C_Proforma_Order_Alloc.C_Proforma_Order_Alloc_ID
-- 2026-04-28T16:01:03.342Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592358,777466,0,549178,TO_TIMESTAMP('2026-04-28 16:01:03.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Proforma Bestellung Zuordnung',TO_TIMESTAMP('2026-04-28 16:01:03.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-28T16:01:03.345Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-28T16:01:03.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584755)
;

-- 2026-04-28T16:01:03.353Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777466
;

-- 2026-04-28T16:01:03.354Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777466)
;

-- Field: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> Rechnung
-- Column: C_Proforma_Order_Alloc.C_Invoice_ID
-- 2026-04-28T16:01:03.477Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592359,777467,0,549178,TO_TIMESTAMP('2026-04-28 16:01:03.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Invoice Identifier',10,'D','The Invoice Document.','Y','N','N','N','N','N','N','N','Rechnung',TO_TIMESTAMP('2026-04-28 16:01:03.357000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-28T16:01:03.481Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777467 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-28T16:01:03.482Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008)
;

-- 2026-04-28T16:01:03.541Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777467
;

-- 2026-04-28T16:01:03.543Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777467)
;

-- Field: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> Auftrag
-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-28T16:01:03.657Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592360,777468,0,549178,TO_TIMESTAMP('2026-04-28 16:01:03.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2026-04-28 16:01:03.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-28T16:01:03.659Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=777468 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-04-28T16:01:03.662Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(558)
;

-- 2026-04-28T16:01:03.707Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=777468
;

-- 2026-04-28T16:01:03.708Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(777468)
;

-- Tab: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D)
-- UI Section: main
-- 2026-04-28T16:01:14.345Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549178,547696,TO_TIMESTAMP('2026-04-28 16:01:14.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-04-28 16:01:14.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-04-28T16:01:14.358Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547696 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main
-- UI Column: 10
-- 2026-04-28T16:01:14.508Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549400,547696,TO_TIMESTAMP('2026-04-28 16:01:14.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-04-28 16:01:14.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main
-- UI Column: 20
-- 2026-04-28T16:01:14.619Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549401,547696,TO_TIMESTAMP('2026-04-28 16:01:14.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-04-28 16:01:14.515000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 10
-- UI Element Group: default
-- 2026-04-28T16:01:14.794Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549400,555169,TO_TIMESTAMP('2026-04-28 16:01:14.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2026-04-28 16:01:14.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 10 -> default.Auftrag
-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-28T16:01:38.147Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777468,0,549178,555169,650094,'F',TO_TIMESTAMP('2026-04-28 16:01:37.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','Y','N','N','Auftrag',10,0,0,TO_TIMESTAMP('2026-04-28 16:01:37.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 10 -> default.Rechnung
-- Column: C_Proforma_Order_Alloc.C_Invoice_ID
-- 2026-04-28T16:01:44.613Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777467,0,549178,555169,650095,'F',TO_TIMESTAMP('2026-04-28 16:01:44.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Invoice Identifier','The Invoice Document.','Y','N','Y','N','N','Rechnung',20,0,0,TO_TIMESTAMP('2026-04-28 16:01:44.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 20
-- UI Element Group: flags
-- 2026-04-28T16:01:53.113Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549401,555170,TO_TIMESTAMP('2026-04-28 16:01:52.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2026-04-28 16:01:52.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Proforma_Order_Alloc.IsActive
-- 2026-04-28T16:02:01.662Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777465,0,549178,555170,650096,'F',TO_TIMESTAMP('2026-04-28 16:02:01.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2026-04-28 16:02:01.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 20
-- UI Element Group: org
-- 2026-04-28T16:02:10.622Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549401,555171,TO_TIMESTAMP('2026-04-28 16:02:10.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2026-04-28 16:02:10.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 20 -> org.Sektion
-- Column: C_Proforma_Order_Alloc.AD_Org_ID
-- 2026-04-28T16:02:20.440Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,777464,0,549178,555171,650097,'F',TO_TIMESTAMP('2026-04-28 16:02:19.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2026-04-28 16:02:19.249000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 10 -> default.Auftrag
-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-28T16:02:27.209Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-04-28 16:02:27.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=650094
;

-- UI Element: Proforma Bestellung Zuordnung(542139,D) -> Proforma Bestellung Zuordnung(549178,D) -> main -> 10 -> default.Rechnung
-- Column: C_Proforma_Order_Alloc.C_Invoice_ID
-- 2026-04-28T16:02:27.215Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-04-28 16:02:27.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=650095
;

-- Column: C_Proforma_Order_Alloc.C_Order_ID
-- 2026-04-28T16:04:19.435Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2026-04-28 16:04:19.435000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592360
;

-- Column: C_Proforma_Order_Alloc.C_Invoice_ID
-- 2026-04-28T16:04:27.182Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=20,Updated=TO_TIMESTAMP('2026-04-28 16:04:27.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592359
;

