-- 2018-02-26T10:46:51.578
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544648,0,TO_TIMESTAMP('2018-02-26 10:46:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','ACHTUNG: Für die Bestelldispo-Zeilen zum Lieferanten {0}_{1} wurde vom System des Lieferanten kein Auftrag entgegen genommen.','I',TO_TIMESTAMP('2018-02-26 10:46:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate.Event_NoRemotePruchaseOrderWasPlaced')
;

-- 2018-02-26T10:46:51.581
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544648 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-02-26T10:47:02.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='ACHTUNG: Für die Bestelldispo-Zeilen zum Lieferanten {0}_{1} wurde vom System des Lieferanten kein Auftrag entgegen genommen!',Updated=TO_TIMESTAMP('2018-02-26 10:47:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544648
;

