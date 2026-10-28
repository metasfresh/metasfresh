-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545857 (de.metas.pos.CashWithdrawal.NoOrgBPartner)

-- ############################################################
-- Message: de.metas.pos.CashWithdrawal.NoOrgBPartner
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545857 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-24 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Der Organisation der Kasse ist kein Geschäftspartner zugeordnet.','E',TO_TIMESTAMP('2026-09-24 11:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.CashWithdrawal.NoOrgBPartner')
;

UPDATE AD_Message SET ErrorCode='CASH_WITHDRAWAL_NO_ORG_BPARTNER', Updated=TO_TIMESTAMP('2026-09-24 11:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545857
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545857
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='No business partner is linked to the organization of the POS terminal.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545857
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545857
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-24 11:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545857
;
