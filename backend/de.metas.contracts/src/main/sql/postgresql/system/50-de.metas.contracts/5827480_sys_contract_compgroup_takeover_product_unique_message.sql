-- AD_Message: ContractCompensationGroup_TakeOverProductNotUnique
-- Refuses listing the same customer discount product on more than one take-over record of one compensation-group contract settings.
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545886 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Das Produkt {0} ist in diesen Einstellungen bereits bei einer anderen Übernahme-Zeile als Kundenrabatt-Produkt hinterlegt.','E',TO_TIMESTAMP('2026-10-01 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'ContractCompensationGroup_TakeOverProductNotUnique')
;

UPDATE AD_Message SET ErrorCode='ContractCompGroup_TakeOverProductUnique', Updated=TO_TIMESTAMP('2026-10-01 11:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545886
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Message_ID=545886
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Product {0} is already listed as a customer discount product on another take-over line of these settings.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545886
;

-- de_DE/de_CH already carry the base (German) text as seeded above; just mark them as translated.
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545886
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545886
;
