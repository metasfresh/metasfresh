-- Run mode: SWING_CLIENT

-- Value: de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnComplete.Description

;

-- 2023-09-15T16:12:19.987226Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545336,0,TO_TIMESTAMP('2023-09-15 17:12:19.742','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Auftrag für Produkt {0} {1} mit der Menge {2} wurde fertiggestellt.','I',TO_TIMESTAMP('2023-09-15 17:12:19.742','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnComplete.Description')
;

-- 2023-09-15T16:12:20.004070600Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545336 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnComplete.Description

;

-- 2023-09-15T16:13:50.826377100Z
UPDATE AD_Message_Trl SET MsgText='A shipment line for product {0} , quantity {1} was completed.',Updated=TO_TIMESTAMP('2023-09-15 17:13:50.825','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545336
;

-- Value: de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnComplete.Description

;

-- 2023-09-15T16:14:09.002315600Z
UPDATE AD_Message SET MsgText='Eine Transportlinie für Produkt {0}, Menge {1} wurde abgeschlossen.',Updated=TO_TIMESTAMP('2023-09-15 17:14:08.999','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545336
;

-- 2023-09-15T16:14:09.005507100Z
UPDATE AD_Message_Trl trl SET MsgText='Eine Transportlinie für Produkt {0}, Menge {1} wurde abgeschlossen.' WHERE AD_Message_ID=545336 AND AD_Language='de_DE'
;

-- Value: de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnReverse.Description

;

-- 2023-09-15T16:15:52.609832400Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545337,0,TO_TIMESTAMP('2023-09-15 17:15:52.386','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Eine Versandlinie für Produkt {0}, Menge {1} {1} wurde storniert.','I',TO_TIMESTAMP('2023-09-15 17:15:52.386','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnReverse.Description')
;

-- 2023-09-15T16:15:52.611436300Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545337 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnReverse.Description

;

-- 2023-09-15T17:13:24.865905500Z
UPDATE AD_Message_Trl SET MsgText='A shipment line for product {0} , quantity {1} was reversed.',Updated=TO_TIMESTAMP('2023-09-15 18:13:24.865','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545337
;

-- Value: de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnReverse.Description

;

-- 2023-09-15T17:13:39.396208300Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-15 18:13:39.396','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545337
;

-- Value: de.metas.contracts.modular.impl.ShipmentLineForSOLogHandler.OnComplete.Description

;

-- 2023-09-15T17:13:44.176565300Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-15 18:13:44.176','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545336
;

