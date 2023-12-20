-- Value: manufacturing.printReceivedHUQRCodes.NoFinishedGoodsAvailableForLabeling
-- 2022-12-10T09:25:02.543Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545220,0,TO_TIMESTAMP('2022-12-10 11:25:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Keine Fertigwaren zur Etikettierung verfügbar.','E',TO_TIMESTAMP('2022-12-10 11:25:02','YYYY-MM-DD HH24:MI:SS'),100,'manufacturing.printReceivedHUQRCodes.NoFinishedGoodsAvailableForLabeling')
;

-- 2022-12-10T09:25:02.545Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545220 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: manufacturing.printReceivedHUQRCodes.NoFinishedGoodsAvailableForLabeling
-- 2022-12-10T09:25:05.615Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-10 11:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545220
;

-- Value: manufacturing.printReceivedHUQRCodes.NoFinishedGoodsAvailableForLabeling
-- 2022-12-10T09:25:16.262Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No finished goods available for labeling.',Updated=TO_TIMESTAMP('2022-12-10 11:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545220
;

-- Value: manufacturing.printReceivedHUQRCodes.NoFinishedGoodsAvailableForLabeling
-- 2022-12-10T09:25:17.718Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-12-10 11:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545220
;

