-- 25.02.2016 10:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543807,0,TO_TIMESTAMP('2016-02-25 10:28:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y','Auftrag : {0}, Bestellkontrolle {1}','E',TO_TIMESTAMP('2016-02-25 10:28:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing.C_Print_Job_Instructions.C_Order_MFGWarehouse_Report_Error')
;

-- 25.02.2016 10:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543807 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 25.02.2016 10:28
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2016-02-25 10:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543807
;

-- 25.02.2016 15:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Auftrag : {0}, Bestellkontrolle {1}.',Updated=TO_TIMESTAMP('2016-02-25 15:53:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543807
;

-- 25.02.2016 15:53
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543807
;

