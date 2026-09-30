-- Read-only monitor window over PP_Order (table 53027): completed-but-not-closed orders
-- (DocStatus='CO') with the CostDifference virtual column (AD_Column 592970), so a controller can
-- review the WIP cost imbalance before running the distribution step.
--
-- IDs allocated from idserver.metas.de: AD_Element 585116/585117, AD_Window 542175, AD_Tab 549352,
--   AD_Field 781749..781761, AD_UI_Section 547860, AD_UI_Column 549604/549605,
--   AD_UI_ElementGroup 555514..555517, AD_UI_Element 652680..652692 (field order), AD_Menu 542348

-- 1) Window caption element: DE base, EN override
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, EntityType, Name, PrintName, Description)
VALUES (585116 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-20 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-20 14:00:00','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Kostenüberwachung Fertigung', 'Kostenüberwachung Fertigung', 'Fertigungsaufträge, die abgeschlossen aber noch nicht geschlossen sind, mit Kostendifferenz')
;

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, PrintName, Description, IsTranslated)
SELECT t.AD_Element_ID, l.AD_Language, t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, t.Name, t.PrintName, t.Description, 'N'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585116
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Manufacturing cost monitoring', PrintName='Manufacturing cost monitoring',
    Description='Manufacturing orders that are completed but not closed, showing the cost difference',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-20 14:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585116 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-20 14:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585116 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-20 14:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585116 AND AD_Language='de_CH'
;

-- 2) AD_Window
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority)
VALUES (0,585116 /*From ID Server*/,0,542175 /*From ID Server*/,TO_TIMESTAMP('2026-07-20 14:00:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','N','Kostenüberwachung Fertigung','N',TO_TIMESTAMP('2026-07-20 14:00:20','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Window t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Window_ID=542175
  AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

select update_window_translation_from_ad_element(585116)
;

DELETE FROM AD_Element_Link WHERE AD_Window_ID=542175
;
select AD_Element_Link_Create_Missing_Window(542175)
;

-- 3) Tab caption element: DE base, EN override
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, EntityType, Name, PrintName, Description)
VALUES (585117 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-20 14:00:25','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-20 14:00:25','YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Fertigungsaufträge', 'Fertigungsaufträge', 'Fertigungsaufträge mit Status abgeschlossen, die noch nicht geschlossen sind')
;

INSERT INTO AD_Element_Trl (AD_Element_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, PrintName, Description, IsTranslated)
SELECT t.AD_Element_ID, l.AD_Language, t.AD_Client_ID, t.AD_Org_ID, 'Y', t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, t.Name, t.PrintName, t.Description, 'N'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585117
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Manufacturing orders', PrintName='Manufacturing orders',
    Description='Manufacturing orders with document status Completed that are not yet closed',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-20 14:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585117 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-20 14:00:35','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585117 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-07-20 14:00:40','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585117 AND AD_Language='de_CH'
;

-- 4) AD_Tab (TabLevel=0, over PP_Order, read-only, DocStatus='CO')
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsGenericZoomTarget,IsGridModeOnly,IsInsertRecord,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause)
VALUES (0,585117 /*From ID Server*/,0,549352 /*From ID Server*/,53027,542175,'N',TO_TIMESTAMP('2026-07-20 14:00:45','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','Y','N','Y','N','N','N','Y','N','N','Y','Y','N','N','Fertigungsaufträge','N',10,0,TO_TIMESTAMP('2026-07-20 14:00:45','YYYY-MM-DD HH24:MI:SS'),100,'DocStatus=''CO''')
;

INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Tab_ID=549352
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

select update_tab_translation_from_ad_element(585117)
;

DELETE FROM AD_Element_Link WHERE AD_Tab_ID=549352
;
select AD_Element_Link_Create_Missing_Tab(549352)
;

-- 5) AD_Field rows, reusing the existing column elements.

-- 5.1 IsActive
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53649,781749 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:00:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','N','N','N','N','N','N','Aktiv',10,0,TO_TIMESTAMP('2026-07-20 14:00:50','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781749
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(348)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781749
;
select AD_Element_Link_Create_Missing_Field(781749)
;

-- 5.2 DocumentNo
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53621,781750 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:00:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Nr.',10,10,TO_TIMESTAMP('2026-07-20 14:00:55','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781750
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(290)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781750
;
select AD_Element_Link_Create_Missing_Field(781750)
;

-- 5.3 C_DocType_ID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53629,781751 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Belegart',20,20,TO_TIMESTAMP('2026-07-20 14:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781751
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(196)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781751
;
select AD_Element_Link_Create_Missing_Field(781751)
;

-- 5.4 M_Product_ID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53623,781752 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Produkt',30,30,TO_TIMESTAMP('2026-07-20 14:01:05','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781752
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(454)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781752
;
select AD_Element_Link_Create_Missing_Field(781752)
;

-- 5.5 CostDifference
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,592970,781753 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Kostendifferenz',40,100,TO_TIMESTAMP('2026-07-20 14:01:10','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781753
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(585115)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781753
;
select AD_Element_Link_Create_Missing_Field(781753)
;

-- 5.6 QtyOrdered
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53670,781754 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Bestellt/ Beauftragt',10,40,TO_TIMESTAMP('2026-07-20 14:01:15','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781754
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(531)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781754
;
select AD_Element_Link_Create_Missing_Field(781754)
;

-- 5.7 QtyDelivered
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53668,781755 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Gelieferte Menge',20,50,TO_TIMESTAMP('2026-07-20 14:01:20','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781755
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(528)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781755
;
select AD_Element_Link_Create_Missing_Field(781755)
;

-- 5.8 C_UOM_ID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53632,781756 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Maßeinheit',30,60,TO_TIMESTAMP('2026-07-20 14:01:25','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781756
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(215)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781756
;
select AD_Element_Link_Create_Missing_Field(781756)
;

-- 5.9 DatePromised
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53641,781757 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Zugesagter Termin',40,70,TO_TIMESTAMP('2026-07-20 14:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781757
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(269)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781757
;
select AD_Element_Link_Create_Missing_Field(781757)
;

-- 5.10 DateFinishSchedule (drives the tab's default sort)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (0,53639,781758 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Datum Fertigstellung geplant',50,80,-1,TO_TIMESTAMP('2026-07-20 14:01:35','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781758
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(53278)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781758
;
select AD_Element_Link_Create_Missing_Field(781758)
;

-- 5.11 M_Warehouse_ID
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53624,781759 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Lager',60,90,TO_TIMESTAMP('2026-07-20 14:01:40','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781759
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(459)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781759
;
select AD_Element_Link_Create_Missing_Field(781759)
;

-- 5.12 DocStatus (kept as a grid column although the tab is already scoped to CO)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53646,781760 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Belegstatus',20,110,TO_TIMESTAMP('2026-07-20 14:01:45','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781760
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(289)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781760
;
select AD_Element_Link_Create_Missing_Field(781760)
;

-- 5.13 AD_Org_ID (last, per the org-last layout rule)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,53683,781761 /*From ID Server*/,0,549352,TO_TIMESTAMP('2026-07-20 14:01:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','Y','N','N','N','N','N','Sektion',10,120,TO_TIMESTAMP('2026-07-20 14:01:50','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=781761
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
select update_FieldTranslation_From_AD_Name_Element(113)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781761
;
select AD_Element_Link_Create_Missing_Field(781761)
;

-- 6) Layout: 1 section, 2 columns (left/right), 4 element groups
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value)
VALUES (0,0,549352,547860 /*From ID Server*/,TO_TIMESTAMP('2026-07-20 14:01:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-07-20 14:01:55','YYYY-MM-DD HH24:MI:SS'),100,'main')
;
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_UI_Section_ID=547860
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Left column
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549604 /*From ID Server*/,547860,TO_TIMESTAMP('2026-07-20 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-07-20 14:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Right column
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549605 /*From ID Server*/,547860,TO_TIMESTAMP('2026-07-20 14:02:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2026-07-20 14:02:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Left column, group 1 (primary): DocumentNo, C_DocType_ID, M_Product_ID, CostDifference
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549604,555514 /*From ID Server*/,TO_TIMESTAMP('2026-07-20 14:02:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2026-07-20 14:02:10','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Left column, group 2: quantities/dates/warehouse
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549604,555515 /*From ID Server*/,TO_TIMESTAMP('2026-07-20 14:02:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',20,NULL,TO_TIMESTAMP('2026-07-20 14:02:15','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Right column, group 1: flags (IsActive first, then DocStatus)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549605,555516 /*From ID Server*/,TO_TIMESTAMP('2026-07-20 14:02:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,NULL,TO_TIMESTAMP('2026-07-20 14:02:20','YYYY-MM-DD HH24:MI:SS'),100)
;
-- Right column, group 2: org (last)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549605,555517 /*From ID Server*/,TO_TIMESTAMP('2026-07-20 14:02:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',20,NULL,TO_TIMESTAMP('2026-07-20 14:02:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 7) AD_UI_Element rows, one per field; all grid-displayed except IsActive.

-- IsActive
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781749,0,549352,555516,652680 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:02:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2026-07-20 14:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;
-- DocumentNo
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781750,0,549352,555514,652681 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:02:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Nr.',10,10,0,TO_TIMESTAMP('2026-07-20 14:02:35','YYYY-MM-DD HH24:MI:SS'),100)
;
-- C_DocType_ID
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781751,0,549352,555514,652682 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:02:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Belegart',20,20,0,TO_TIMESTAMP('2026-07-20 14:02:40','YYYY-MM-DD HH24:MI:SS'),100)
;
-- M_Product_ID
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781752,0,549352,555514,652683 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:02:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Produkt',30,30,0,TO_TIMESTAMP('2026-07-20 14:02:45','YYYY-MM-DD HH24:MI:SS'),100)
;
-- CostDifference
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781753,0,549352,555514,652684 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:02:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Kostendifferenz',40,100,0,TO_TIMESTAMP('2026-07-20 14:02:50','YYYY-MM-DD HH24:MI:SS'),100)
;
-- QtyOrdered
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781754,0,549352,555515,652685 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:02:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Bestellt/ Beauftragt',10,40,0,TO_TIMESTAMP('2026-07-20 14:02:55','YYYY-MM-DD HH24:MI:SS'),100)
;
-- QtyDelivered
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781755,0,549352,555515,652686 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Gelieferte Menge',20,50,0,TO_TIMESTAMP('2026-07-20 14:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;
-- C_UOM_ID
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781756,0,549352,555515,652687 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:03:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Maßeinheit',30,60,0,TO_TIMESTAMP('2026-07-20 14:03:05','YYYY-MM-DD HH24:MI:SS'),100)
;
-- DatePromised
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781757,0,549352,555515,652688 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:03:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Zugesagter Termin',40,70,0,TO_TIMESTAMP('2026-07-20 14:03:10','YYYY-MM-DD HH24:MI:SS'),100)
;
-- DateFinishSchedule
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781758,0,549352,555515,652689 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:03:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Datum Fertigstellung geplant',50,80,0,TO_TIMESTAMP('2026-07-20 14:03:15','YYYY-MM-DD HH24:MI:SS'),100)
;
-- M_Warehouse_ID
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781759,0,549352,555515,652690 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:03:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lager',60,90,0,TO_TIMESTAMP('2026-07-20 14:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;
-- DocStatus
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781760,0,549352,555516,652691 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:03:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Belegstatus',20,110,0,TO_TIMESTAMP('2026-07-20 14:03:25','YYYY-MM-DD HH24:MI:SS'),100)
;
-- AD_Org_ID
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781761,0,549352,555517,652692 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-20 14:03:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Sektion',10,120,0,TO_TIMESTAMP('2026-07-20 14:03:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 8) Menu: reuse the window caption element for the menu label
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy)
VALUES ('W',0,585116,542348 /*From ID Server*/,0,542175,TO_TIMESTAMP('2026-07-20 14:03:35','YYYY-MM-DD HH24:MI:SS'),100,'D','PP_Order_CostImbalance','Y','N','Y','N','N','Kostenüberwachung Fertigung',TO_TIMESTAMP('2026-07-20 14:03:35','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Menu_ID=542348
  AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;
select update_menu_translation_from_ad_element(585116)
;

-- Tree placement: under "Produktion" (Node_ID 1000014), next free SeqNo in that folder
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo)
SELECT t.AD_Client_ID,0, 'Y', TO_TIMESTAMP('2026-07-20 14:03:40','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-20 14:03:40','YYYY-MM-DD HH24:MI:SS'), 100, t.AD_Tree_ID, 542348, 1000014, 7
FROM AD_Tree t
WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116
  AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND e.Node_ID=542348)
;
