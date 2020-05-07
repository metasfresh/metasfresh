

DROP INDEX public.m_iolcandhandler_log_reference;
CREATE INDEX m_iolcandhandler_log_reference ON public.m_iolcandhandler_log (record_id, ad_table_id);
-- 2019-12-20T21:45:42.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544951,0,TO_TIMESTAMP('2019-12-20 22:45:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inoutcandidate','Y','Lieferdispo-Datensatz-ID {0} wurde schon verarbeitet','I',TO_TIMESTAMP('2019-12-20 22:45:42','YYYY-MM-DD HH24:MI:SS'),100,'ShipmentScheduleAlreadyProcessed')
;

-- 2019-12-20T21:45:42.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544951 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-12-20T21:46:07.278Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Shipment disposition record ID {0} is already processed',Updated=TO_TIMESTAMP('2019-12-20 22:46:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544951
;

-- 2019-12-20T21:46:11.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-20 22:46:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544951
;

