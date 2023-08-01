-- Value: de.metas.contracts.ShipmentCompleted
-- 2023-07-31T14:58:09.767550200Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545311,0,TO_TIMESTAMP('2023-07-31 15:58:09.46','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Lieferung abgeschlossen','I',TO_TIMESTAMP('2023-07-31 15:58:09.46','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.ShipmentCompleted')
;

-- 2023-07-31T14:58:09.772769400Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545311 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.ShipmentCompleted
-- 2023-07-31T14:59:08.801745Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Shipment completed',Updated=TO_TIMESTAMP('2023-07-31 15:59:08.801','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545311
;

-- Value: de.metas.contracts.ShipmentReversed
-- 2023-07-31T15:00:49.330527200Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545312,0,TO_TIMESTAMP('2023-07-31 16:00:49.034','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Sendung storniert','I',TO_TIMESTAMP('2023-07-31 16:00:49.034','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.ShipmentReversed')
;

-- 2023-07-31T15:00:49.331569800Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545312 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.ShipmentReversed
-- 2023-07-31T15:01:33.007831800Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Shipment reversed',Updated=TO_TIMESTAMP('2023-07-31 16:01:33.007','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545312
;

-- Value: de.metas.contracts.DocActionUnsupported
-- 2023-07-31T15:22:15.500397900Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545313,0,TO_TIMESTAMP('2023-07-31 16:22:15.142','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Nicht unterstützte Modellaktion!','E',TO_TIMESTAMP('2023-07-31 16:22:15.142','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.DocActionUnsupported')
;

-- 2023-07-31T15:22:15.505163400Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545313 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.DocActionUnsupported
-- 2023-07-31T15:22:32.247876Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Unsupported model action!',Updated=TO_TIMESTAMP('2023-07-31 16:22:32.247','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545313
;