-- AD_Message: ContractCompensationGroup_TakeOverOwnLineProductNotPercentDiscount
-- Refuses a take-over record whose own-line product is not a percentage discount (it would collapse to 0% and silently lose the take-over).
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545888 /*From ID Server*/,0,TO_TIMESTAMP('2026-10-01 16:30:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','Das Produkt {0} ist kein Prozent-Rabatt-Produkt und kann daher nicht als Rabatt-Produkt einer Übernahme-Zeile verwendet werden.','E',TO_TIMESTAMP('2026-10-01 16:30:00','YYYY-MM-DD HH24:MI:SS'),100,'ContractCompensationGroup_TakeOverOwnLineProductNotPercentDiscount')
;

UPDATE AD_Message SET ErrorCode='ContractCompGroup_OwnLineNotPercent', Updated=TO_TIMESTAMP('2026-10-01 16:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=545888
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Message_ID=545888
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Product {0} is not a percentage discount product and cannot be used as the discount product of a take-over line.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 16:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545888
;

-- de_DE/de_CH already carry the base (German) text as seeded above; just mark them as translated.
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 16:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545888
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 16:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545888
;
