-- 2019-12-13T13:39:41.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('I','All selected Shipments should be Completed',0,'Y',TO_TIMESTAMP('2019-12-13 15:39:41','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-12-13 15:39:41','YYYY-MM-DD HH24:MI:SS'),100,544949,'de.metas.handlingunits.inout.process.M_Shipment_AddToTransportationOrderProcess.AllSelectedShipmentsShouldBeCompleted',0,'D')
;

-- 2019-12-13T13:39:41.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544949 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-12-13T13:39:52.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Alle ausgewählten Lieferungen müssen fertiggestellt sein', IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-13 15:39:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544949
;

-- 2019-12-13T13:39:57.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-13 15:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544949
;

-- 2019-12-13T13:40:03.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Alle ausgewählten Lieferungen müssen fertiggestellt sein',Updated=TO_TIMESTAMP('2019-12-13 15:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544949
;

-- 2019-12-13T14:21:23.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.ui.web.inout.process.M_InOut_AddToTransportationOrderProcess.AllSelectedShipmentsShouldBeCompleted',Updated=TO_TIMESTAMP('2019-12-13 16:21:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544949
;

