-- 2018-03-22T17:23:43.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544661,0,TO_TIMESTAMP('2018-03-22 17:23:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','ACHTUNG: Beim Verarbeiten der Bestelldispo-Zeilen zum Lieferanten {0}_{1} ist ein Fehler aufgetreten!','I',TO_TIMESTAMP('2018-03-22 17:23:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate.Event_ErrorWhilePlacingRemotePurchaseOrder')
;

-- 2018-03-22T17:23:43.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544661 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-03-22T17:23:48.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.purchasecandidate.Event_NoRemotePurchaseOrderWasPlaced',Updated=TO_TIMESTAMP('2018-03-22 17:23:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544648
;

