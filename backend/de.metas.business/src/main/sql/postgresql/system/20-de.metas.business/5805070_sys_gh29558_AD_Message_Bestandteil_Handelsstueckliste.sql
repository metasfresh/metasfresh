-- 2026-05-27T00:00:00.000Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545730 /*From ID Server*/,0,TO_TIMESTAMP('2026-05-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',$msg$Bestandteil Handelsstückliste$msg$,'I',TO_TIMESTAMP('2026-05-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'BundleComponentWithoutCharge')
;

-- 2026-05-27T00:00:00.001Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545730 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2026-05-27T00:00:00.004Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Component of bundle',Updated=TO_TIMESTAMP('2026-05-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545730
;

-- 2026-05-27T00:00:00.005Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText=$msg$Bestandteil Handelsstückliste$msg$,Updated=TO_TIMESTAMP('2026-05-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545730
;

-- 2026-05-27T00:00:00.006Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText=$msg$Bestandteil Handelsstückliste$msg$,Updated=TO_TIMESTAMP('2026-05-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545730
;

-- 2026-05-27T00:00:00.007Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText=$msg$Bestandteil Handelsstückliste$msg$,Updated=TO_TIMESTAMP('2026-05-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545730
;
