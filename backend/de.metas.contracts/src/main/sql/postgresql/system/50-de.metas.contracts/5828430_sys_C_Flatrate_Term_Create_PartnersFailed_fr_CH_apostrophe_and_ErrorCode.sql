-- fr_CH text of AD_Message C_Flatrate_Term_Create_PartnersFailed: the message text goes through java.text.MessageFormat,
-- where a single apostrophe starts a quoted section and {0} would stay unsubstituted; store the escaped apostrophe (two in the stored text).
UPDATE AD_Message_Trl SET MsgText='Aucun contrat n''''a pu être créé pour {0} partenaire(s) commercial(aux) (tous les autres ont été traités) :', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 14:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545912
;

-- Error codes of the new messages (as for the overlap message)
UPDATE AD_Message SET ErrorCode='C_Flatrate_Term_Create_PartnersFailed', Updated=TO_TIMESTAMP('2026-10-07 14:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545912
;
UPDATE AD_Message SET ErrorCode='C_Flatrate_Term_Create_AndNMore', Updated=TO_TIMESTAMP('2026-10-07 14:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545913
;
