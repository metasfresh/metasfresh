-- 2023-05-17T10:49:50.334Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545265,0,TO_TIMESTAMP('2023-05-17 13:49:50.055','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Die Kommissionierung ausgelieferter HUs kann nicht rückgängig gemacht werden!','I',TO_TIMESTAMP('2023-05-17 13:49:50.055','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits.shipmentschedule.process.Delivered_HUs_Cannot_Be_Unpicked')
;

-- 2023-05-17T10:49:50.347Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545265 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-05-17T10:50:03.696Z
UPDATE AD_Message_Trl SET MsgText='Delivered HUs cannot be unpicked !',Updated=TO_TIMESTAMP('2023-05-17 13:50:03.695','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545265
;

