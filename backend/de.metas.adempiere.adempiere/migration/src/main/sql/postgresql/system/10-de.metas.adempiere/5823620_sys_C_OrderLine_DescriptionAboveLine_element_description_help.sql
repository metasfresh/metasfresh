-- Run mode: SWING_CLIENT

-- Fill Description and Help on the existing reusable AD_Element 585448
-- (ColumnName='DescriptionAboveLine'), which was created with both fields deliberately empty.
-- The texts themselves are below; they are not restated here.
--
-- Two things worth knowing rather than re-deriving:
--   * No new IDs are needed. This script only UPDATEs AD_Element_Trl rows that the element's
--     creation script (5823420) already seeded -- de_CH / de_DE / en_US -- plus one German-base
--     fr_CH row that stays IsTranslated='N' and is left untouched, since no French text exists.
--   * The Help states a real, verified limitation: the document font resolves to Liberation Sans
--     with Cp1252 encoding, so any character outside Windows-1252 (emoji, check marks, arrows,
--     letters outside Western Europe) is dropped from the PDF silently -- no replacement glyph --
--     although the WebUI editor shows it. Umlauts, sharp s, accented Western European letters and
--     the euro sign are inside the range and print. Confirmed at source and reproduced through
--     iText with the shipped TTF, not assumed.
--
-- Language handling mirrors 5823420: one UPDATE per language followed by that language's
-- propagation call, with the base language (de_DE) additionally syncing the base AD_Element row.

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
