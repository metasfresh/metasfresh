-- 13.07.2016 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543914,0,TO_TIMESTAMP('2016-07-13 18:03:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','No Allocation for WriteOff Payments','E',TO_TIMESTAMP('2016-07-13 18:03:33','YYYY-MM-DD HH24:MI:SS'),100,'PaymentAllocationForm_WriteOffPayments_NoAllocation')
;

-- 13.07.2016 18:03
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543914 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 13.07.2016 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Bitte zur Abschreibung von Beträgen Zahlungen nur einzeln auswählen und verarbeiten.',Updated=TO_TIMESTAMP('2016-07-13 18:10:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543914
;

-- 13.07.2016 18:10
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543914
;

-- 13.07.2016 18:12
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2016-07-13 18:12:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543914
;

-- 13.07.2016 18:13
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2016-07-13 18:13:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543914
;

