-- Run mode: SWING_CLIENT

-- Fill Description and Help on the existing, reusable AD_Element 585448
-- (ColumnName='DescriptionAboveLine'). The element was created with both fields deliberately empty;
-- the texts below describe the behaviour the field actually has, now that it is observed:
--   * the content prints as a standalone, full-width block immediately above the article row of
--     that very order line, on the order confirmation, the delivery note, the picking list and
--     the invoice;
--   * NULL / empty / whitespace-only prints nothing at all -- no block and no blank vertical gap;
--   * longer text wraps onto several lines and the block grows;
--   * the text is per line: each line's text prints above that line only.
-- The one limitation the Help must state in user terms: the document font is embedded with a
-- Windows-1252 encoding, so any character outside that range (emoji, check marks, arrows, letters
-- outside Western Europe) is silently dropped from the PDF -- it leaves no replacement glyph, even
-- though the WebUI editor displays it fine. Umlauts, sharp s, accented Western European letters and
-- the euro sign are inside the range and print correctly.
--
-- Nothing is promised here that the field does not do: it is not offered as a search/filter
-- criterion in the order-line overview, and it exists on the sales order line only.
--
-- No new IDs are needed -- this script only UPDATEs AD_Element_Trl rows of element 585448 that the
-- element's creation script already seeded (de_CH / de_DE / en_US, plus a German-base fr_CH row
-- that stays IsTranslated='N' and is left untouched, since no French text is available).
-- Language handling mirrors the element's creation script
-- (5823420_sys_C_OrderLine_DescriptionAboveLine.sql): one UPDATE per final language followed by
-- that language's propagation call, with the base language (de_DE) additionally syncing the base
-- AD_Element row via update_ad_element_on_ad_element_trl_update.

-- Element: DescriptionAboveLine (de_CH mirrors de_DE base text)
-- 2026-09-09T19:00:00.000Z
UPDATE AD_Element_Trl SET Description='Freier Text, der auf den Dokumenten als eigener Block direkt über dieser Auftragsposition gedruckt wird.', Help='Der Text wird auf Auftragsbestätigung, Lieferschein, Pickliste und Rechnung als eigener Block über die volle Breite unmittelbar über der Artikelzeile dieser Position gedruckt. Er gilt nur für diese Position; jede Auftragsposition hat ihr eigenes Feld. Ist das Feld leer oder enthält es nur Leerzeichen, wird nichts gedruckt und es bleibt auch keine Leerzeile stehen. Längerer Text wird automatisch auf mehrere Zeilen umgebrochen. Bitte beachten: Emojis und andere Sonderzeichen (zum Beispiel Häkchen oder Pfeile) werden im Eingabefeld angezeigt, fehlen auf dem gedruckten Dokument aber vollständig, ohne Ersatzzeichen. Verwenden Sie daher nur normale Buchstaben, Zahlen und Satzzeichen; Umlaute, ß und das Euro-Zeichen werden korrekt gedruckt.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 19:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585448 AND AD_Language='de_CH'
;

-- 2026-09-09T19:00:01.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585448,'de_CH')
;

-- Element: DescriptionAboveLine (de_DE base language)
-- 2026-09-09T19:00:02.000Z
UPDATE AD_Element_Trl SET Description='Freier Text, der auf den Dokumenten als eigener Block direkt über dieser Auftragsposition gedruckt wird.', Help='Der Text wird auf Auftragsbestätigung, Lieferschein, Pickliste und Rechnung als eigener Block über die volle Breite unmittelbar über der Artikelzeile dieser Position gedruckt. Er gilt nur für diese Position; jede Auftragsposition hat ihr eigenes Feld. Ist das Feld leer oder enthält es nur Leerzeichen, wird nichts gedruckt und es bleibt auch keine Leerzeile stehen. Längerer Text wird automatisch auf mehrere Zeilen umgebrochen. Bitte beachten: Emojis und andere Sonderzeichen (zum Beispiel Häkchen oder Pfeile) werden im Eingabefeld angezeigt, fehlen auf dem gedruckten Dokument aber vollständig, ohne Ersatzzeichen. Verwenden Sie daher nur normale Buchstaben, Zahlen und Satzzeichen; Umlaute, ß und das Euro-Zeichen werden korrekt gedruckt.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 19:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585448 AND AD_Language='de_DE'
;

-- 2026-09-09T19:00:03.000Z
/* DDL */ select update_ad_element_on_ad_element_trl_update(585448,'de_DE')
;

-- 2026-09-09T19:00:04.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585448,'de_DE')
;

-- Element: DescriptionAboveLine (en_US override)
-- 2026-09-09T19:00:05.000Z
UPDATE AD_Element_Trl SET Description='Free text that is printed on the documents as a separate block directly above this order line.', Help='The text is printed on the order confirmation, the delivery note, the picking list and the invoice as a separate, full-width block immediately above the article row of this line. It applies to this line only; every order line has its own field. If the field is empty or contains only blanks, nothing is printed and no empty gap is left behind. Longer text wraps automatically onto several lines. Please note: emoji and other special symbols (a check mark or an arrow, for example) are shown in the input field, but they are missing entirely from the printed document, with no replacement character. Use ordinary letters, digits and punctuation; German umlauts, sharp s, accented Western European letters and the euro sign are printed correctly.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-09 19:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585448 AND AD_Language='en_US'
;

-- 2026-09-09T19:00:06.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585448,'en_US')
;
