-- 2020-01-22T13:06:24.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544956,0,TO_TIMESTAMP('2020-01-22 14:06:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Menge darf gleich der gelieferten Menge gesetzt werden.','I',TO_TIMESTAMP('2020-01-22 14:06:24','YYYY-MM-DD HH24:MI:SS'),100,'M_ShipmentSchedule.QtyOrdered_Override_MayNotEqual_QtyDelivered')
;

-- 2020-01-22T13:06:24.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544956 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-01-22T13:06:52.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Qty may not be set to delivered qty',Updated=TO_TIMESTAMP('2020-01-22 14:06:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544956
;

-- 2020-01-22T13:07:05.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Die Menge darf nicht gleich der gelieferten Menge gesetzt werden.',Updated=TO_TIMESTAMP('2020-01-22 14:07:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544956
;

-- 2020-01-22T13:07:10.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Die Menge darf nicht gleich der gelieferten Menge gesetzt werden.',Updated=TO_TIMESTAMP('2020-01-22 14:07:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544956
;

-- 2020-01-22T13:07:28.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Quantity may not be set to the delivered quantity',Updated=TO_TIMESTAMP('2020-01-22 14:07:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544956
;

-- 2020-01-22T13:08:49.480Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544957,0,TO_TIMESTAMP('2020-01-22 14:08:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Statt dessen kann der Datensatz geschlossen werden.','I',TO_TIMESTAMP('2020-01-22 14:08:49','YYYY-MM-DD HH24:MI:SS'),100,'M_ShipmentSchedule.QtyOrdered_Override_MayNotEqual_QtyDelivered_Hint')
;

-- 2020-01-22T13:08:49.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544957 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-01-22T13:09:11.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Consider to close the record instead.',Updated=TO_TIMESTAMP('2020-01-22 14:09:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544957
;

-- 2020-01-22T13:09:16.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-01-22 14:09:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544957
;

