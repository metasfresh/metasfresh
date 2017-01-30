-- 28.01.2017 18:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544252,0,TO_TIMESTAMP('2017-01-28 18:00:02','YYYY-MM-DD HH24:MI:SS'),0,'de.metas.fresh.ordercheckup','Y','Event_ReceiptReversed','I',TO_TIMESTAMP('2017-01-28 18:00:02','YYYY-MM-DD HH24:MI:SS'),0,'Event_ReceiptReversed')
;

-- 28.01.2017 18:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544252 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Jan 28, 2017 6:23 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.inout', MsgText='Wareneingang {0} für Partner {1} {2} wurde *VOIDED*.',Updated=TO_TIMESTAMP('2017-01-28 18:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544252
;

-- Jan 28, 2017 6:23 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544252
;

-- Jan 28, 2017 6:24 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-01-28 18:24:19','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Receipt {0} for partner {1} {2} was reversed.' WHERE AD_Message_ID=544252 AND AD_Language='en_US'
;

-- Jan 28, 2017 6:24 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Receipt {0} for partner {1} {2} was reversed.',Updated=TO_TIMESTAMP('2017-01-28 18:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544252
;

-- Jan 28, 2017 6:24 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=544252
;

-- Jan 28, 2017 6:24 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-01-28 18:24:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544252 AND AD_Language='en_US'
;

-- Jan 28, 2017 6:25 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544254,0,TO_TIMESTAMP('2017-01-28 18:25:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inout','Y','Shipment {0} for partner {1} {2} was reversed.','I',TO_TIMESTAMP('2017-01-28 18:25:43','YYYY-MM-DD HH24:MI:SS'),100,'Event_ShipmentReversed')
;

-- Jan 28, 2017 6:25 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544254 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Jan 28, 2017 6:25 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-01-28 18:25:51','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544254 AND AD_Language='en_US'
;

