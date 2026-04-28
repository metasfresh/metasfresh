-- 2018-02-09T14:07:56.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544636,0,TO_TIMESTAMP('2018-02-09 14:07:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','ACHTUNG: die Bestellung hat ein abweichenes {0}!','I',TO_TIMESTAMP('2018-02-09 14:07:56','YYYY-MM-DD HH24:MI:SS'),100,'Event_PurchaseOrder_Has_Later_DatePromised')
;

-- 2018-02-09T14:07:56.956
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544636 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-02-09T14:10:42.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='ACHTUNG: Bestellung {0} für Partner {1} {2} wurde mit abweichendem {3} erstellt.', Value='Event_PurchaseOrderCreated_Different_DatePromised',Updated=TO_TIMESTAMP('2018-02-09 14:10:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544636
;

-- 2018-02-09T15:28:17.799
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544638,0,TO_TIMESTAMP('2018-02-09 15:28:17','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','ACHTUNG: Bestellung {0} für Partner {1} {2} wurde mit abweichender Menge erstellt!','I',TO_TIMESTAMP('2018-02-09 15:28:17','YYYY-MM-DD HH24:MI:SS'),100,'Event_PurchaseOrderCreated_Different_Quantity')
;

-- 2018-02-09T15:28:17.800
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544638 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-02-09T15:28:47.750
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='ACHTUNG: Bestellung {0} für Partner {1} {2} wurde mit abweichendem Zusagedatum erstellt!',Updated=TO_TIMESTAMP('2018-02-09 15:28:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544636
;

-- 2018-02-09T15:32:11.820
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544639,0,TO_TIMESTAMP('2018-02-09 15:32:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.purchasecandidate','Y','ACHTUNG: Bestellung {0} für Partner {1} {2} wurde mit abweichender Menge und abweichendem Zusagedatum erstellt!','I',TO_TIMESTAMP('2018-02-09 15:32:11','YYYY-MM-DD HH24:MI:SS'),100,'Event_PurchaseOrderCreated_Different_Quantity_And_DatePromised')
;

-- 2018-02-09T15:32:11.822
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544639 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

