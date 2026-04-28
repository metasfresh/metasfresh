-- 2019-03-27T15:36:12.268
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The product {0} is narcotic and may not be sold to the customer {1}.',Updated=TO_TIMESTAMP('2019-03-27 15:36:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544885
;

-- 2019-03-27T16:13:21.945
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-03-27 16:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544885
;

-- 2019-03-27T16:47:54.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544893,0,TO_TIMESTAMP('2019-03-27 16:47:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Das Produkt {0} ist ein Betäubungsmittel und darf nicht vom Lieferanten {1} bezogen werden.
','I',TO_TIMESTAMP('2019-03-27 16:47:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.PharmaPurchaseOrderLineInputValidator.NoNarcoticPermission')
;

-- 2019-03-27T16:47:54.815
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544893 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-03-27T16:48:11.111
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The product {0} is narcotic and may not be obtained from the vendor {1}.',Updated=TO_TIMESTAMP('2019-03-27 16:48:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544893
;

-- 2019-03-27T16:50:09.657
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544894,0,TO_TIMESTAMP('2019-03-27 16:50:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','Das Produkt {0} ist verschreibungspflichig und darf nicht von dem Lieferanten {1} bezogen werden.','I',TO_TIMESTAMP('2019-03-27 16:50:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma.PharmaPurchaseOrderLineInputValidator.NoPrescriptionPermission')
;

-- 2019-03-27T16:50:09.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544894 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-03-27T16:50:24.782
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='The product {0} is a prescription medicine and may not be purchased from the vendor {1}.',Updated=TO_TIMESTAMP('2019-03-27 16:50:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544894
;

