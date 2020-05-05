-- 2018-08-13T14:18:23.357
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544771,0,TO_TIMESTAMP('2018-08-13 14:18:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','Die Inventur kann nicht fertiggestellt werden, da nicht alle Zeilen gezählt sind.','E',TO_TIMESTAMP('2018-08-13 14:18:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.inventory.model.validator.NotAllLinesCounted')
;

-- 2018-08-13T14:18:23.367
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544771 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-08-13T14:18:47.902
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',MsgText='The inventory cannot be completed. Not all the lines are counted.' WHERE AD_Message_ID=544771 AND AD_Language='en_US'
;

-- 2018-08-13T14:20:37.563
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.inventory.model.interceptor.NotAllLinesCounted',Updated=TO_TIMESTAMP('2018-08-13 14:20:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544771
;

