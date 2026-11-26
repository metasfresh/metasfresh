-- AD_Message 545864 (M_CostRevaluationLine_ZeroStockCostProvisional): German zero-stock hint names the document "Kosten Neubewertung", like the window

UPDATE AD_Message
SET MsgText='Bei einem Produkt ohne Lagerbestand ist der eingegebene Einstandspreis vorläufig: Wird vor dem Buchen dieser Kosten Neubewertung Ware eingebucht, bucht sie Bestand × (neu − aktuell); danach berechnet jeder Wareneingang den gleitenden Durchschnittspreis neu.',
    Updated=TO_TIMESTAMP('2026-10-01 15:10:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545864
;

UPDATE AD_Message_Trl
SET MsgText='Bei einem Produkt ohne Lagerbestand ist der eingegebene Einstandspreis vorläufig: Wird vor dem Buchen dieser Kosten Neubewertung Ware eingebucht, bucht sie Bestand × (neu − aktuell); danach berechnet jeder Wareneingang den gleitenden Durchschnittspreis neu.',
    Updated=TO_TIMESTAMP('2026-10-01 15:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545864 AND AD_Language <> 'en_US'
;
