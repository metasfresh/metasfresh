

-- 21.01.2016 07:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543751,0,TO_TIMESTAMP('2016-01-21 07:43:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','Dem Produkt {0} der Organisation {1} ist innerhalb der Organisation {2} kein Zielprodukt zugeordnet.','E',TO_TIMESTAMP('2016-01-21 07:43:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order.CounterDocMissingMappedProduct')
;

-- 21.01.2016 07:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543751 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
