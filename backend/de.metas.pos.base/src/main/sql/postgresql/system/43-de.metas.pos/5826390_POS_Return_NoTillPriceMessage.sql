-- AD_Message IDs allocated from idserver.metas.de:
--   AD_Message 545863 (de.metas.pos.Return.NoTillPrice)

-- ############################################################
-- Message: de.metas.pos.Return.NoTillPrice
-- ############################################################
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,545863 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-25 07:30:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Für dieses Produkt wurde kein Kassenpreis gefunden.','E',TO_TIMESTAMP('2026-09-25 07:30:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.Return.NoTillPrice')
;

UPDATE AD_Message SET ErrorCode='POS_RETURN_NO_TILL_PRICE', Updated=TO_TIMESTAMP('2026-09-25 07:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID=545863
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Message t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=545863
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

UPDATE AD_Message_Trl SET MsgText='No till price was found for this product.',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-25 07:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='en_US' AND AD_Message_ID=545863
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-25 07:30:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_DE' AND AD_Message_ID=545863
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-25 07:30:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Language='de_CH' AND AD_Message_ID=545863
;
