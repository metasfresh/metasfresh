-- The 'Sprung zu Prognose' overlay as an ordinary, read-only grid over M_Forecast_ProductQty_V.
--
-- Window and tab reuse AD_Element 585296, the element created for the view's key column, so the caption
-- and its translations live in exactly one place.
--
-- The tab is query-only: the rows are an aggregate of forecast lines, so there is nothing to insert,
-- edit or delete here. Every field is read-only for the same reason.

INSERT INTO AD_Window (AD_Window_ID,AD_Client_ID,AD_Org_ID,AD_Element_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsOneInstanceOnly,IsSOTrx,Name,Updated,UpdatedBy,WindowType)
VALUES (542184 /*From ID Server*/,0,0,585296,TO_TIMESTAMP('2026-08-13 13:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','Prognosemenge pro Produkt',TO_TIMESTAMP('2026-08-13 13:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Q')
;

INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Window_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Window t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542184
AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

INSERT INTO AD_Tab (AD_Tab_ID,AD_Client_ID,AD_Org_ID,AD_Element_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,HasTree,ImportFields,IsActive,IsAdvancedTab,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshViewOnChangeEvents,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (549375 /*From ID Server*/,0,0,585296,542640,542184,TO_TIMESTAMP('2026-08-13 13:00:02','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Y','N','Y','N','N','Y','Y','N','N','N','N',0,'Prognosemenge pro Produkt',10,0,TO_TIMESTAMP('2026-08-13 13:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549375
AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- Push the element's de/en wording into the window and tab translations just created.
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585296)
;

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (782280 /*From ID Server*/,0,0,593294,549375,TO_TIMESTAMP('2026-08-13 13:00:04','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','Y','N','N','N','N','Y','N','Prognose',10,10,0,TO_TIMESTAMP('2026-08-13 13:00:04','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782280
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(2498)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=782280
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(782280)
;

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (782281 /*From ID Server*/,0,0,593299,549375,TO_TIMESTAMP('2026-08-13 13:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','Y','N','N','N','N','Y','N','Belegstatus',20,20,0,TO_TIMESTAMP('2026-08-13 13:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782281
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(289)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=782281
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(782281)
;

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (782282 /*From ID Server*/,0,0,593298,549375,TO_TIMESTAMP('2026-08-13 13:00:06','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','Y','N','N','N','N','Y','N','Zugesagter Termin',30,30,1,TO_TIMESTAMP('2026-08-13 13:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782282
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(269)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=782282
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(782282)
;

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (782283 /*From ID Server*/,0,0,593300,549375,TO_TIMESTAMP('2026-08-13 13:00:07','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','Y','N','N','N','N','Y','N','Menge',40,40,0,TO_TIMESTAMP('2026-08-13 13:00:07','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782283
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(526)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=782283
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(782283)
;

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (782284 /*From ID Server*/,0,0,593301,549375,TO_TIMESTAMP('2026-08-13 13:00:08','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','Y','N','N','N','N','Y','N','Maßeinheit',50,50,0,TO_TIMESTAMP('2026-08-13 13:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782284
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(215)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=782284
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(782284)
;

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsMandatory,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy)
VALUES (782285 /*From ID Server*/,0,0,593287,549375,TO_TIMESTAMP('2026-08-13 13:00:09','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','Y','N','N','N','N','Y','N','Sektion',60,60,0,TO_TIMESTAMP('2026-08-13 13:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=782285
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=782285
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(782285)
;

-- One section / column / element group: the tab is grid-only, but the WebUI still picks the
-- AD_UI_* layout provider over the AD_Field fallback whenever a tab has a persisted section, and
-- that is the provider whose IsDisplayedGrid / SeqNoGrid actually order the grid.
INSERT INTO AD_UI_Section (AD_UI_Section_ID,AD_Client_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy)
VALUES (547883 /*From ID Server*/,0,0,549375,TO_TIMESTAMP('2026-08-13 13:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2026-08-13 13:00:11','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_UI_Column (AD_UI_Column_ID,AD_Client_ID,AD_Org_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (549635 /*From ID Server*/,0,0,547883,TO_TIMESTAMP('2026-08-13 13:00:12','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-08-13 13:00:13','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_UI_ElementGroup (AD_UI_ElementGroup_ID,AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (555561 /*From ID Server*/,0,0,549635,TO_TIMESTAMP('2026-08-13 13:00:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2026-08-13 13:00:15','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (653130 /*From ID Server*/,0,0,782280,549375,555561,'F',TO_TIMESTAMP('2026-08-13 13:00:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Prognose',10,10,0,TO_TIMESTAMP('2026-08-13 13:00:16','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (653131 /*From ID Server*/,0,0,782281,549375,555561,'F',TO_TIMESTAMP('2026-08-13 13:00:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Belegstatus',20,20,0,TO_TIMESTAMP('2026-08-13 13:00:17','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (653132 /*From ID Server*/,0,0,782282,549375,555561,'F',TO_TIMESTAMP('2026-08-13 13:00:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Zugesagter Termin',30,30,0,TO_TIMESTAMP('2026-08-13 13:00:18','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (653133 /*From ID Server*/,0,0,782283,549375,555561,'F',TO_TIMESTAMP('2026-08-13 13:00:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Menge',40,40,0,TO_TIMESTAMP('2026-08-13 13:00:19','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (653134 /*From ID Server*/,0,0,782284,549375,555561,'F',TO_TIMESTAMP('2026-08-13 13:00:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Maßeinheit',50,50,0,TO_TIMESTAMP('2026-08-13 13:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (653135 /*From ID Server*/,0,0,782285,549375,555561,'F',TO_TIMESTAMP('2026-08-13 13:00:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Sektion',60,60,0,TO_TIMESTAMP('2026-08-13 13:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

