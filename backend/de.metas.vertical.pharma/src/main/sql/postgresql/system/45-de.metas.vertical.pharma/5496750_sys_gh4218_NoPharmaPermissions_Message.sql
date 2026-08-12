-- 2018-06-27T18:24:31.003
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544756,0,TO_TIMESTAMP('2018-06-27 18:24:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Keine Lieferberechtigung','I',TO_TIMESTAMP('2018-06-27 18:24:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.PharmaService.NoPharmaShipmentPermissions')
;

-- 2018-06-27T18:24:31.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544756 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-06-27T18:24:48.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-27 18:24:48','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='No Pharma Shipment Permissions.' WHERE AD_Message_ID=544756 AND AD_Language='en_US'
;

-- 2018-06-27T18:24:50.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Keine Lieferberechtigung.',Updated=TO_TIMESTAMP('2018-06-27 18:24:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544756
;

-- 2018-06-27T18:24:57.097
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-27 18:24:57','YYYY-MM-DD HH24:MI:SS'),MsgText='Keine Lieferberechtigung.' WHERE AD_Message_ID=544756 AND AD_Language='nl_NL'
;

-- 2018-06-27T18:24:59.877
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-06-27 18:24:59','YYYY-MM-DD HH24:MI:SS'),MsgText='Keine Lieferberechtigung.' WHERE AD_Message_ID=544756 AND AD_Language='de_CH'
;

-- 2018-06-27T18:30:02.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.vertical.pharma.PharmaBPartnerRepository.NoPharmaShipmentPermissions',Updated=TO_TIMESTAMP('2018-06-27 18:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544756
;

-- 2018-06-27T18:33:31.241
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.vertical.pharma.PharmaOrderLineQuickInputValidator.NoPharmaShipmentPermissions',Updated=TO_TIMESTAMP('2018-06-27 18:33:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544756
;

