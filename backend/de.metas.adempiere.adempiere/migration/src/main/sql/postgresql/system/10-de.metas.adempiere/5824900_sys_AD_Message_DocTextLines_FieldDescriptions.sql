-- AD_Messages carrying the field-level help of the text-lines modal's two editable columns.
--
-- Why an AD_Message and not AD_Element.Help: the modal is a Java-backed view whose columns are declared
-- with @ViewColumn, and that annotation has a captionKey but no help attribute. A view column's
-- description is resolved from the AD_Message key "<captionKey>/Description" and reaches the client as
-- the column-header tooltip, so the two caveats a user cannot infer from the screen ship as messages
-- keyed on the captionKeys the modal's two editable columns already use (TextLine, TextLineScope).
-- Until this script runs there is no such message, and the tooltip shows the key literally.
--
-- MsgText carries the field's description, MsgTip the caveat; the framework concatenates the two when it
-- resolves the key, so both reach the tooltip. MsgType is 'I' (info, not an error), so no ErrorCode.
--
-- The three other columns of the modal (Line, M_Product_ID, QtyOrdered) keep their generic captionKeys and
-- are deliberately not given a description here: those keys are shared with every other view in the
-- product, and no such "<captionKey>/Description" message exists anywhere in metasfresh, so creating one
-- would change column tooltips well beyond this feature.
--
-- IDs allocated from idserver.metas.de:
--   AD_Message  545847 (TextLine/Description), 545848 (TextLineScope/Description)

-- =============================================================================
-- TextLine/Description  (AD_Message_ID 545847)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545847 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der Freitext dieser Zeile. Er kann leer sein und wird dann als Leerzeile gedruckt.','Auf den gedruckten Dokumenten erscheinen nur westeuropäische Zeichen: deutsche Umlaute, ß, akzentuierte lateinische Buchstaben, €, Striche und Anführungszeichen. Andere Zeichen, darunter Emojis, werden in der WebUI korrekt angezeigt, erscheinen aber nicht auf dem gedruckten Dokument. Auszeichnungen werden nicht interpretiert: <b> wird wörtlich gedruckt.','I',TO_TIMESTAMP('2026-09-17 09:30:00','YYYY-MM-DD HH24:MI:SS'),100,'TextLine/Description')
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545847
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='The free text of this line. It may be empty, which prints as a blank line.',MsgTip='Only Western European characters appear on the printed documents: German umlauts, ß, accented Latin letters, €, dashes and quotation marks. Other characters, emoji among them, are displayed correctly in the WebUI but do not appear on the printed document. Markup is not interpreted: <b> prints literally.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545847
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545847
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545847
;

-- =============================================================================
-- TextLineScope/Description  (AD_Message_ID 545848)
-- =============================================================================
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgTip,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545848 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-17 09:30:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Legt fest, auf welche Dokumente diese Freitextzeile mitgenommen wird. „Folgezeilen“ bedeutet, dass der Text zu den Artikelzeilen unterhalb gehört und nur auf Dokumente mitgenommen wird, die mindestens eine dieser Artikelzeilen enthalten. „Dokument“ bedeutet, dass der Text auf jedes aus dem Auftrag abgeleitete Dokument mitgenommen wird.','Der Vorschlagswert ergibt sich daraus, wo die Zeile angelegt wird: Eine Freitextzeile ohne Artikelzeile oberhalb erhält „Dokument“, jede andere „Folgezeilen“. Er wird beim Verschieben der Zeile nicht neu berechnet und kann jederzeit geändert werden.','I',TO_TIMESTAMP('2026-09-17 09:30:10','YYYY-MM-DD HH24:MI:SS'),100,'TextLineScope/Description')
;

-- Seed AD_Message_Trl for every active system language using the base (DE) text.
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545848
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Override en_US with the English translation.
UPDATE AD_Message_Trl SET MsgText='Decides which documents this text line is carried onto. “Following Lines” means the text belongs with the article lines beneath it and is carried only onto documents that contain at least one of them. “Document” means the text is carried onto every document derived from the order.',MsgTip='The default is derived from where the line is created: a text line with no article line above it gets “Document”, every other one gets “Following Lines”. It is not recomputed when the line is moved, and it can be changed at any time.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:30:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545848
;

-- Mark de_DE and de_CH as actively translated (same text as base; flips IsTranslated to Y).
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545848
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-17 09:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545848
;
