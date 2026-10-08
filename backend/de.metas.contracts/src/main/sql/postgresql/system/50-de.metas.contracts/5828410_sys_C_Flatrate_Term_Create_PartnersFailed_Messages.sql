-- Process "Erzeuge Vertrag": end-of-run error listing the partners whose contract could not be created (list capped, remainder as "and N more").
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545912 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Für {0} Geschäftspartner konnte kein Vertrag erstellt werden (alle anderen wurden verarbeitet):','E',TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'C_Flatrate_Term_Create_PartnersFailed')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Message_ID=545912
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Für {0} Geschäftspartner konnte kein Vertrag erstellt werden (alle anderen wurden verarbeitet):', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545912
;

UPDATE AD_Message_Trl SET MsgText='Für {0} Geschäftspartner konnte kein Vertrag erstellt werden (alle anderen wurden verarbeitet):', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545912
;

UPDATE AD_Message_Trl SET MsgText='No contract could be created for {0} business partner(s) (all others were processed):', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545912
;

UPDATE AD_Message_Trl SET MsgText='Aucun contrat n''a pu être créé pour {0} partenaire(s) commercial(aux) (tous les autres ont été traités) :', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545912
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545913 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','… und {0} weitere (vollständige Liste im Prozessprotokoll)','E',TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'C_Flatrate_Term_Create_AndNMore')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Message_ID=545913
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='… und {0} weitere (vollständige Liste im Prozessprotokoll)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545913
;

UPDATE AD_Message_Trl SET MsgText='… und {0} weitere (vollständige Liste im Prozessprotokoll)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545913
;

UPDATE AD_Message_Trl SET MsgText='… and {0} more (full list in the process log)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545913
;

UPDATE AD_Message_Trl SET MsgText='… et {0} autres (liste complète dans le journal du processus)', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-07 12:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545913
;

