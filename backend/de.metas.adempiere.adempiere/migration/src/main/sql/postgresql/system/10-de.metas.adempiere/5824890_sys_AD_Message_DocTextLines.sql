-- AD_Messages for the text-lines modal (de.metas.ui.web.doc_textlines): every refusal the modal can
-- put in front of a user. They replace hardcoded English AdempiereException strings -- a hardcoded
-- string is neither translated nor flagged userFriendlyError, so a German user saw English and the
-- client rendered it as a technical error.
--
-- The base MsgText is German; en_US carries the English override. {0}-style parameters are not used:
-- row ids, positions and lock owners say nothing a user can act on and stay exception parameters for
-- the log.
--
-- The four remaining rejectWithInternalReason(...) cases in doc_textlines.process are deliberately NOT
-- messages: rejectWithInternalReason hides the quick action instead of showing its reason, which is the
-- behaviour wanted there (an article row simply offers no move/delete action, the first row offers no
-- move-up, a closed order offers no launcher) and the behaviour the precedent this launcher copies --
-- WEBUI_Order_ProductsProposal_Launcher -- already has.
--
-- IDs allocated from idserver.metas.de:
--   AD_Message  545837..545846

-- =============================================================================
-- DocTextLines_DocumentIsBeingEditedRightNow  (AD_Message_ID 545837)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545837 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Freitextzeilen dieses Dokuments werden gerade bearbeitet. Bitte versuchen Sie es in einem Moment erneut.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_DocumentIsBeingEditedRightNow')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesDocumentLocked', Updated=TO_TIMESTAMP('2026-09-17 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545837
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545837
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='This document''s text lines are being edited right now. Please try again in a moment.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545837
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545837
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545837
;

-- =============================================================================
-- DocTextLines_NeighbourRowIsBeingRemoved  (AD_Message_ID 545838)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545838 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:00:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Eine benachbarte Zeile wird gerade entfernt, daher kann diese Zeile nicht sicher platziert werden. Bitte versuchen Sie es erneut.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:00:10','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_NeighbourRowIsBeingRemoved')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesNeighbourRowRemoved', Updated=TO_TIMESTAMP('2026-09-17 09:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545838
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545838
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='A neighbouring row is being removed right now, so this row cannot be placed safely. Please try again.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545838
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545838
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545838
;

-- =============================================================================
-- DocTextLines_RowNoLongerExists  (AD_Message_ID 545839)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545839 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die ausgewählte Zeile gehört nicht mehr zu diesem Dokument. Sie wurde entfernt, während dieses Fenster geöffnet war. Bitte schließen Sie das Fenster, öffnen Sie es erneut und wählen Sie dann wieder eine Zeile aus.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_RowNoLongerExists')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesRowNoLongerExists', Updated=TO_TIMESTAMP('2026-09-17 09:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545839
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545839
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='The line you selected is not part of this document any more. It was removed while this window was open. Please close and reopen the window, then select a line again.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545839
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545839
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545839
;

-- =============================================================================
-- DocTextLines_DocumentGainedRows  (AD_Message_ID 545840)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545840 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:00:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dieses Dokument hat seit dem Öffnen dieses Fensters Zeilen erhalten, daher gibt es hier nichts, oberhalb dessen eingefügt werden könnte. Bitte schließen Sie das Fenster, öffnen Sie es erneut und wählen Sie dann wieder eine Zeile aus.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:00:30','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_DocumentGainedRows')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesDocumentGainedRows', Updated=TO_TIMESTAMP('2026-09-17 09:00:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545840
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545840
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='This document has gained lines since this window was opened, so there is nothing here to insert above. Please close and reopen the window, then select a line again.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545840
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545840
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545840
;

-- =============================================================================
-- DocTextLines_LineOrderIsInconsistent  (AD_Message_ID 545841)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545841 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:00:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Interner Fehler: Die Zeilenreihenfolge des Dokuments enthält eine Zeile, die nicht gelesen werden kann. Es wurde nichts geändert.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:00:40','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_LineOrderIsInconsistent')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesLineOrderInconsistent', Updated=TO_TIMESTAMP('2026-09-17 09:00:41','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545841
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545841
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='Internal error: the document''s line order lists a line that cannot be read, so nothing was changed.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545841
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545841
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545841
;

-- =============================================================================
-- DocTextLines_CannotMoveUpAnyFurther  (AD_Message_ID 545842)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545842 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:00:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Diese Zeile kann nicht weiter nach oben verschoben werden. In dieser Richtung gibt es keine weitere Zeile.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:00:50','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_CannotMoveUpAnyFurther')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesCannotMoveUp', Updated=TO_TIMESTAMP('2026-09-17 09:00:51','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545842
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545842
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='This line cannot be moved up any further. There is no line beyond it in that direction.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545842
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545842
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545842
;

-- =============================================================================
-- DocTextLines_CannotMoveDownAnyFurther  (AD_Message_ID 545843)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545843 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Diese Zeile kann nicht weiter nach unten verschoben werden. In dieser Richtung gibt es keine weitere Zeile.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_CannotMoveDownAnyFurther')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesCannotMoveDown', Updated=TO_TIMESTAMP('2026-09-17 09:01:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545843
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545843
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='This line cannot be moved down any further. There is no line beyond it in that direction.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545843
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545843
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545843
;

-- =============================================================================
-- DocTextLines_OnlyTextLinesCanBeChangedHere  (AD_Message_ID 545844)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545844 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:01:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hier können nur Freitextzeilen gelöscht oder verschoben werden. Eine Artikelzeile gehört zum Auftrag selbst und wird im Reiter der Auftragspositionen geändert.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:01:10','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_OnlyTextLinesCanBeChangedHere')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesOnlyTextLines', Updated=TO_TIMESTAMP('2026-09-17 09:01:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545844
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545844
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='Only text lines can be deleted or moved here. An article line belongs to the order itself and is changed on the order''s line tab.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545844
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545844
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545844
;

-- =============================================================================
-- DocTextLines_ArticleLineCannotBeEditedHere  (AD_Message_ID 545845)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545845 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:01:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Artikelzeilen können hier nicht bearbeitet werden. In diesem Fenster sind nur Freitextzeilen bearbeitbar; eine Artikelzeile wird im Reiter der Auftragspositionen geändert.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:01:20','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_ArticleLineCannotBeEditedHere')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesArticleLineReadOnly', Updated=TO_TIMESTAMP('2026-09-17 09:01:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545845
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545845
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='Article lines cannot be edited here. Only text lines are editable in this window; an article line is changed on the order''s line tab.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545845
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545845
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545845
;

-- =============================================================================
-- DocTextLines_PositionGapExhausted  (AD_Message_ID 545846)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545846 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:01:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Zwischen diesen beiden Zeilen ist kein Platz mehr für eine weitere Freitextzeile. Bitte verschieben Sie zuerst eine benachbarte Zeile, um Platz zu schaffen.',NULL,'E',TO_TIMESTAMP('2026-09-17 09:01:30','YYYY-MM-DD HH24:MI:SS'),100,'DocTextLines_PositionGapExhausted')
;

UPDATE AD_Message SET ErrorCode='DocTextLinesPositionGapExhausted', Updated=TO_TIMESTAMP('2026-09-17 09:01:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545846
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545846
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='There is no room left for another text line between these two lines. Please move a neighbouring line first to free up space.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545846
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545846
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:01:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545846
;
