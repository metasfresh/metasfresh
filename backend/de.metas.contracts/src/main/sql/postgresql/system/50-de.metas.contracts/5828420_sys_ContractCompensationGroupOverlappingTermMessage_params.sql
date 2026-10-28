-- AD_Message ContractCompensationGroup_OverlappingTerm: name the invoice partner and the period of the overlapping contract, not only its document no.
UPDATE AD_Message SET MsgText='Dieser Vertrag überschneidet sich mit dem aktiven Vertrag {0} für den Rechnungsempfänger {1} ({2} – {3}) und einen gemeinsamen Belegtyp.', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545877
;

UPDATE AD_Message_Trl SET MsgText='Dieser Vertrag überschneidet sich mit dem aktiven Vertrag {0} für den Rechnungsempfänger {1} ({2} – {3}) und einen gemeinsamen Belegtyp.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545877
;

UPDATE AD_Message_Trl SET MsgText='Dieser Vertrag überschneidet sich mit dem aktiven Vertrag {0} für den Rechnungsempfänger {1} ({2} – {3}) und einen gemeinsamen Belegtyp.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545877
;

UPDATE AD_Message_Trl SET MsgText='This contract overlaps the active contract {0} for the invoice partner {1} ({2} – {3}) and a common document type.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545877
;

UPDATE AD_Message_Trl SET MsgText='Ce contrat chevauche le contrat actif {0} pour le destinataire de la facture {1} ({2} – {3}) et un type de document commun.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545877
;

