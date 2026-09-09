-- AD_Messages backing the WebUI view "open empty, load on filter" guard
-- (when queryIfNoFilters=false and no filter is applied, the view returns a zero-row selection
-- with EmptyReason text+hint instead of scanning the table).
--
-- These two message keys are referenced by the WebUI view framework to render the empty-view hint
-- when a view is opened without filters (queryIfNoFilters=false guard fires).
-- They had no AD_Message rows in the dictionary (the guard had no active caller until now).
-- Without them the empty view would show blank text. First active caller: window 542159
-- "Bestand pro Woche" (MD_Stock_PerWeek_V).
--
-- Both are display/info strings (MsgType='I'), no parameters, no ErrorCode.
-- Base text is German (DE); en_US translation provided. de_DE/de_CH marked translated.

-- =============================================================================
-- webui.view.emptyReason.pleaseFilterFirst.text  (AD_Message_ID 545759)
-- =============================================================================

-- 2026-06-17T00:00:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545759 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-17 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Keine Zeilen angezeigt – bitte zuerst filtern.','I',TO_TIMESTAMP('2026-06-17 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.emptyReason.pleaseFilterFirst.text')
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-06-17T00:00:01.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545759 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-06-17T00:00:02.000Z
UPDATE AD_Message_Trl SET MsgText='No rows shown – please apply a filter first.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-17 00:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545759 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base).
-- 2026-06-17T00:00:03.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-17 00:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545759 /*From ID Server*/
;

-- 2026-06-17T00:00:04.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-17 00:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545759 /*From ID Server*/
;

-- =============================================================================
-- webui.view.emptyReason.pleaseFilterFirst.hint  (AD_Message_ID 545760)
-- =============================================================================

-- 2026-06-17T00:00:05.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545760 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-17 00:00:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geben Sie einen Filter ein (z. B. Produkt, Lager oder Zeitraum), um Zeilen anzuzeigen.','I',TO_TIMESTAMP('2026-06-17 00:00:05','YYYY-MM-DD HH24:MI:SS'),100,'webui.view.emptyReason.pleaseFilterFirst.hint')
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
-- 2026-06-17T00:00:06.000Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545760 /*From ID Server*/
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
-- 2026-06-17T00:00:07.000Z
UPDATE AD_Message_Trl SET MsgText='Enter a filter (e.g. product, warehouse or date range) to display rows.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-17 00:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545760 /*From ID Server*/
;

-- Mark de_DE and de_CH as actively translated (same text as base).
-- 2026-06-17T00:00:08.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-17 00:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545760 /*From ID Server*/
;

-- 2026-06-17T00:00:09.000Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-17 00:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545760 /*From ID Server*/
;
