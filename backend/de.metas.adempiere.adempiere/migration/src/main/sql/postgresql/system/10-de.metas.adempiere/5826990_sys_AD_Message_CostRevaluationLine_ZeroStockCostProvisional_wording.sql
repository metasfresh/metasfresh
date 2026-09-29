-- Provisional-price hint of the Kosten Neubewertung line quick-input: name the price like the field it belongs to
-- (M_CostRevaluationLine.NewCostPrice, "Neuer Einstandspreis"), i.e. "Einstandspreis" instead of "Kostenpreis".

UPDATE AD_Message SET MsgText='Bei einem Produkt ohne Lagerbestand ist der eingegebene Einstandspreis vorläufig: Der erste Wareneingang berechnet den gleitenden Durchschnittspreis neu und überschreibt diesen Wert.',Updated=TO_TIMESTAMP('2026-09-29 16:30:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545864;

-- the German translations and every language still carrying the (untranslated) German base text
UPDATE AD_Message_Trl SET MsgText='Bei einem Produkt ohne Lagerbestand ist der eingegebene Einstandspreis vorläufig: Der erste Wareneingang berechnet den gleitenden Durchschnittspreis neu und überschreibt diesen Wert.',Updated=TO_TIMESTAMP('2026-09-29 16:30:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545864 AND (AD_Language IN ('de_DE','de_CH') OR IsTranslated='N');
