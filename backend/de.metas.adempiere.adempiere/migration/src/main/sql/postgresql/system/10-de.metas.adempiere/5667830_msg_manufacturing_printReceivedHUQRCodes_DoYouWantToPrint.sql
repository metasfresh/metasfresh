-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T06:44:09.324Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545218,0,TO_TIMESTAMP('2022-12-09 08:44:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Möchten Sie drucken?','I',TO_TIMESTAMP('2022-12-09 08:44:09','YYYY-MM-DD HH24:MI:SS'),100,'manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?')
;

-- 2022-12-09T06:44:09.327Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545218 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T06:44:17.328Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-09 08:44:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545218
;

-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T06:44:24.384Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Do you want to print?',Updated=TO_TIMESTAMP('2022-12-09 08:44:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545218
;

-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T06:44:25.710Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-09 08:44:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545218
;

-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T09:51:49.536Z
UPDATE AD_Message SET MsgText='Drucken beginnen?',Updated=TO_TIMESTAMP('2022-12-09 11:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545218
;

-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T09:51:55.501Z
UPDATE AD_Message_Trl SET MsgText='Drucken beginnen?',Updated=TO_TIMESTAMP('2022-12-09 11:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545218
;

-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T09:51:59.107Z
UPDATE AD_Message_Trl SET MsgText='Drucken beginnen?',Updated=TO_TIMESTAMP('2022-12-09 11:51:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545218
;

-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T09:52:02.859Z
UPDATE AD_Message_Trl SET MsgText='Drucken beginnen?',Updated=TO_TIMESTAMP('2022-12-09 11:52:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545218
;

-- Value: manufacturing.printReceivedHUQRCodes.DoYouWantToPrint?
-- 2022-12-09T09:52:11.235Z
UPDATE AD_Message_Trl SET MsgText='Start printing?',Updated=TO_TIMESTAMP('2022-12-09 11:52:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545218
;

