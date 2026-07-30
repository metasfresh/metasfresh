

-- 23.02.2016 11:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543800,0,TO_TIMESTAMP('2016-02-23 11:46:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Saldokorrektur (-) Zu Gunsten Von Uns
<br>Saldokorrektur (+) Zu Gunsten Geschäftspartner','I',TO_TIMESTAMP('2016-02-23 11:46:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.EmptyKorrectPO')
;

-- 23.02.2016 11:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543800 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 23.02.2016 11:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543801,0,TO_TIMESTAMP('2016-02-23 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Saldokorrektur (+) Zu Gunsten Von Uns
<br>Saldokorrektur (-) Zu Gunsten Geschäftspartner','I',TO_TIMESTAMP('2016-02-23 11:46:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.EmptyKorrectSO')
;

-- 23.02.2016 11:46
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543801 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

