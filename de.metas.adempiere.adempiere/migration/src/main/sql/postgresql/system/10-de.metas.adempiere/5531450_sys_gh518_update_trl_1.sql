-- 2019-09-20T11:16:21.176Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Payment Request for invoice already exists.',Updated=TO_TIMESTAMP('2019-09-20 14:16:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=542985
;

-- 2019-09-20T11:16:25.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-20 14:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=542985
;

-- 2019-09-20T11:57:47.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Could not find the scanned bank account: {}.

Please either select a business partner in the dialog so that the bank account is automatically created, or make sure that a matching bank account exists and re-scan.',Updated=TO_TIMESTAMP('2019-09-20 14:57:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=542877
;

-- 2019-09-20T11:58:04.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {}

Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-09-20 14:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=542877
;

-- 2019-09-20T11:59:18.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544936,0,TO_TIMESTAMP('2019-09-20 14:59:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Rechnung ist nicht fertig gestellt.','E',TO_TIMESTAMP('2019-09-20 14:59:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.process.paymentdocumentform.AlmightyKeeperOfEverything.InvoiceIsNotCompleted')
;

-- 2019-09-20T11:59:18.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544936 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-09-20T11:59:33.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Invoice is not completed.',Updated=TO_TIMESTAMP('2019-09-20 14:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544936
;

-- 2019-09-20T11:59:40.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-20 14:59:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544936
;

-- 2019-09-20T12:00:28.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-09-20 15:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=542877
;

-- 2019-09-20T12:00:30.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {}  Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-09-20 15:00:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Message_ID=542877
;

-- 2019-09-20T12:00:33.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {}  Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-09-20 15:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=542877
;

-- 2019-09-20T12:00:34.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Zu den eingelesenen Konto-Information konnte kein bestehendes Konto ermittelt werden.  Bitte erstellen Sie manuell ein GeschÃ¤ftspartner-Konto oder geben im Eltern-Dialog einen GeschÃ¤ftspartner an. damit das Konto hier on-the-fly erstellt werden kann.',Updated=TO_TIMESTAMP('2019-09-20 15:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=542877
;

-- 2019-09-20T12:00:35.645Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {}  Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-09-20 15:00:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Message_ID=542877
;

-- 2019-09-20T12:00:37.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {}  Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-09-20 15:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=542877
;

-- 2019-09-20T12:00:40.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Das eingescannte Bankkonto wurde nicht gefunden: {}  Bitte wähle im Dialog einen Geschäftspartner, so dass das Konto automatisch erzeugt wird. Oder erstelle manuell ein passendes Bankkonto und wiederhole den Scan.',Updated=TO_TIMESTAMP('2019-09-20 15:00:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=542877
;

-- 2019-09-20T12:01:34.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544937,0,TO_TIMESTAMP('2019-09-20 15:01:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Es ist keine Rechnung ausgewählt.','I',TO_TIMESTAMP('2019-09-20 15:01:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.process.paymentdocumentform.AlmightyKeeperOfEverything.NoInvoiceSelected')
;

-- 2019-09-20T12:01:34.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544937 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-09-20T12:01:48.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No invoice selected.',Updated=TO_TIMESTAMP('2019-09-20 15:01:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544937
;

-- 2019-09-20T12:02:22.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544938,0,TO_TIMESTAMP('2019-09-20 15:02:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Konnte keine Zahlungsaufforderung erstellen.','E',TO_TIMESTAMP('2019-09-20 15:02:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking.process.paymentdocumentform.AlmightyKeeperOfEverything.CouldNotCreatePaymentRequest')
;

-- 2019-09-20T12:02:22.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544938 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-09-20T12:02:31.012Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Could not create payment request.',Updated=TO_TIMESTAMP('2019-09-20 15:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544938
;

