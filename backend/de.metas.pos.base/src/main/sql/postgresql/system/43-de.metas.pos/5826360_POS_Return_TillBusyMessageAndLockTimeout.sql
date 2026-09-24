-- IDs allocated from idserver.metas.de:
--   AD_Message 545862 (de.metas.pos.Return.TillBusy)
--   AD_SysConfig 541857 (de.metas.pos.Return.LockTimeoutMillis)

-- ############################################################
-- Message: de.metas.pos.Return.TillBusy
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545862 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-25 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Für diese Kasse läuft noch eine andere Rücknahme. Bitte versuchen Sie es erneut.','E',TO_TIMESTAMP('2026-09-25 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.TillBusy')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_TILL_BUSY', Updated=TO_TIMESTAMP('2026-09-25 09:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545862
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545862
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='Another return on this till is still in progress. Please retry.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-25 09:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545862
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-25 09:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545862
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-25 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545862
;

-- ############################################################
-- SysConfig: de.metas.pos.Return.LockTimeoutMillis
-- How long POSReturnService#createReturn waits to acquire the cross-transaction POS-terminal
-- advisory lock (pg_try_advisory_lock, polled) before giving up and rejecting with TillBusy above.
-- ############################################################
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value)
VALUES (0,0,541857 /*From ID Server*/,'S',TO_TIMESTAMP('2026-09-25 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,'How long (ms) a POS product return waits to acquire the terminal''s cross-transaction lock before rejecting with TillBusy','de.metas.pos','Y','de.metas.pos.Return.LockTimeoutMillis',TO_TIMESTAMP('2026-09-25 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,'30000')
;
