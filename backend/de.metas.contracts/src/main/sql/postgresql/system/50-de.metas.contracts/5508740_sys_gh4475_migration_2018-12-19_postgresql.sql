-- 2018-12-19T15:29:13.228
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Help='This table allows to assign normal (a.k.a. "assignable") invoice candidates to candidates that reference a refund contract C_FlatrateTerm.
The assignable candidates all match that respective term via their product and bill bpartner',Updated=TO_TIMESTAMP('2018-12-19 15:29:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540981
;

--
-- unrelated - make the C_Invoice_Candidate LineNetAmt field readonly
--
-- 2019-01-02T17:35:24.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-01-02 17:35:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555477
;


--
-- Add messages to display on inconsistent refund configs
--
-- 2019-01-03T21:02:52.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('E','Datensätze der Rückvergütungskonfiguration müssen alle auf einem Betrags oder alle auf einem Prozentwert basieren',0,'Y',TO_TIMESTAMP('2019-01-03 21:02:52','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-01-03 21:02:52','YYYY-MM-DD HH24:MI:SS'),100,544816,'de.metas.constracts.refund.C_Flatrate_RefundConfig_SameRefundBase',0,'de.metas.contracts')
;

-- 2019-01-03T21:02:52.584
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544816 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-03T21:08:07.809
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('E','Datensätze der Rückvergütungskonfiguration müssen alle den selben Staffelmodus haben.',0,'Y',TO_TIMESTAMP('2019-01-03 21:08:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-01-03 21:08:07','YYYY-MM-DD HH24:MI:SS'),100,544817,'de.metas.constracts.refund.C_Flatrate_RefundConfig_SameRefundMode',0,'de.metas.contracts')
;

-- 2019-01-03T21:08:07.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544817 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-03T21:12:29.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('E','Beim Staffelmodus "Gesamtrückvergütung" müssen alle Datensätze der Rückvergütungskonfiguration den selben Abrechnungsterminplan haben.',0,'Y',TO_TIMESTAMP('2019-01-03 21:12:29','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-01-03 21:12:29','YYYY-MM-DD HH24:MI:SS'),100,544818,'de.metas.constracts.refund.C_Flatrate_RefundConfig_SameInvoiceSchedule',0,'de.metas.contracts')
;

-- 2019-01-03T21:12:29.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544818 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-03T21:15:07.432
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (MsgType,MsgText,AD_Client_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Message_ID,Value,AD_Org_ID,EntityType) VALUES ('E','Beim Staffelmodus "Gesamtrückvergütung" müssen alle Datensätze der Rückvergütungskonfiguration den selben Rückvergutungsmodus haben.',0,'Y',TO_TIMESTAMP('2019-01-03 21:15:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-01-03 21:15:07','YYYY-MM-DD HH24:MI:SS'),100,544819,'de.metas.constracts.refund.C_Flatrate_RefundConfig_SameRefundInvoiceType',0,'de.metas.contracts')
;

-- 2019-01-03T21:15:07.434
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544819 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-01-03T21:15:28.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Alle Datensätze der Rückvergütungskonfiguration müssen den selben Staffelmodus haben.',Updated=TO_TIMESTAMP('2019-01-03 21:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544817
;

-- 2019-01-03T21:15:46.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Die Datensätze der Rückvergütungskonfiguration müssen alle auf einem Betrags oder alle auf einem Prozentwert basieren',Updated=TO_TIMESTAMP('2019-01-03 21:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544816
;

-- 2019-01-03T21:17:52.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:17:52','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544818 AND AD_Language='de_CH'
;

-- 2019-01-03T21:19:55.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:19:55','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='With refund mode "accumulated", all given refund config records need to all have the same invoice schedule.' WHERE AD_Message_ID=544818 AND AD_Language='en_US'
;

-- 2019-01-03T21:21:04.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:21:04','YYYY-MM-DD HH24:MI:SS'),MsgText='Since refund mode is "accumulated", all given refund config records need to have the same refund invoice type.' WHERE AD_Message_ID=544819 AND AD_Language='en_US'
;

-- 2019-01-03T21:21:07.637
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:21:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544819 AND AD_Language='en_US'
;

-- 2019-01-03T21:21:36.510
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Beim Staffelmodus "Gesamtrückvergütung" müssen alle Datensätze der Rückvergütungskonfiguration die selbe Rückvergütungsbelegart haben.',Updated=TO_TIMESTAMP('2019-01-03 21:21:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544819
;

-- 2019-01-03T21:21:46.179
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:21:46','YYYY-MM-DD HH24:MI:SS'),MsgText='Beim Staffelmodus "Gesamtrückvergütung" müssen alle Datensätze der Rückvergütungskonfiguration die selbe Rückvergütungsbelegart haben.' WHERE AD_Message_ID=544819 AND AD_Language='nl_NL'
;

-- 2019-01-03T21:21:57.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:21:57','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Beim Staffelmodus "Gesamtrückvergütung" müssen alle Datensätze der Rückvergütungskonfiguration die selbe Rückvergütungsbelegart haben.' WHERE AD_Message_ID=544819 AND AD_Language='de_CH'
;

-- 2019-01-03T21:22:16.928
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:22:16','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544816 AND AD_Language='de_CH'
;

-- 2019-01-03T21:23:43.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:23:43','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='All refund config records need to have the same refund base.' WHERE AD_Message_ID=544816 AND AD_Language='en_US'
;

-- 2019-01-03T21:24:36.050
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:24:36','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='All refund config records need to have the same refund mode.' WHERE AD_Message_ID=544817 AND AD_Language='en_US'
;

-- 2019-01-03T21:24:44.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:24:44','YYYY-MM-DD HH24:MI:SS'),MsgText='Alle Datensätze der Rückvergütungskonfiguration müssen den selben Staffelmodus haben.' WHERE AD_Message_ID=544817 AND AD_Language='nl_NL'
;

-- 2019-01-03T21:24:49.195
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-03 21:24:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='Alle Datensätze der Rückvergütungskonfiguration müssen den selben Staffelmodus haben.' WHERE AD_Message_ID=544817 AND AD_Language='de_CH'
;

