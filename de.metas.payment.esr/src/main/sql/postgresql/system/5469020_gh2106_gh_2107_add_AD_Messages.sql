-- 2017-08-02T21:05:06.080
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544467,0,TO_TIMESTAMP('2017-08-02 21:05:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Die TRansaction enthält keine ESR Referenznummer','E',TO_TIMESTAMP('2017-08-02 21:05:05','YYYY-MM-DD HH24:MI:SS'),100,'CAMT54_Missing_ESR_Reference')
;

-- 2017-08-02T21:05:06.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544467 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-08-02T21:05:13.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Die Transaktion enthält keine ESR Referenznummer',Updated=TO_TIMESTAMP('2017-08-02 21:05:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544467
;

-- 2017-08-02T21:05:26.708
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='ESR_CAMT54_Missing_ESR_Reference',Updated=TO_TIMESTAMP('2017-08-02 21:05:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544467
;

-- 2017-08-02T21:10:43.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544468,0,TO_TIMESTAMP('2017-08-02 21:10:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Die Transaktion is keine Gutschrift (CreditDebitCode={0})','E',TO_TIMESTAMP('2017-08-02 21:10:43','YYYY-MM-DD HH24:MI:SS'),100,'ESR_CAMT54_UnsupportedCreditDebitCode')
;

-- 2017-08-02T21:10:43.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544468 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-08-02T21:17:40.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544469,0,TO_TIMESTAMP('2017-08-02 21:17:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Die Währung {0} der Kopfdatensatzes unterscheidet sich von der Währung {1} der Transaktion.','E',TO_TIMESTAMP('2017-08-02 21:17:40','YYYY-MM-DD HH24:MI:SS'),100,'ESR_CAMT54_BankAccountMismatch')
;

-- 2017-08-02T21:17:40.990
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544469 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2017-08-03T09:23:25.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Die Transaktion enthält keine Referenznummer',Updated=TO_TIMESTAMP('2017-08-03 09:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544467
;

-- 2017-08-03T09:24:52.801
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544470,0,TO_TIMESTAMP('2017-08-03 09:24:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Die Referenznummer der Transaktion ist nicht explizit as ESR-Nummer gekennzeichnet.','E',TO_TIMESTAMP('2017-08-03 09:24:52','YYYY-MM-DD HH24:MI:SS'),100,'ESR_CAMT54_Ambigous_Reference')
;

-- 2017-08-03T09:24:52.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544470 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

