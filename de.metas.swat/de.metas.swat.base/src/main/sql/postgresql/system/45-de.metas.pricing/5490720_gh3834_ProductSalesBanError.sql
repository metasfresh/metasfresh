-- 2018-04-13T16:33:15.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544677,0,TO_TIMESTAMP('2018-04-13 16:33:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','MsgText: Product not allowed to be sold to customer {0} because {1}.','I',TO_TIMESTAMP('2018-04-13 16:33:15','YYYY-MM-DD HH24:MI:SS'),100,'ProductSalesBanError')
;

-- 2018-04-13T16:33:15.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544677 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-04-13T18:15:21.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2018-04-13 18:15:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544677
;



-- 2018-04-17T15:05:57.793
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Product not allowed to be sold to customer {0} because {1}.',Updated=TO_TIMESTAMP('2018-04-17 15:05:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544677
;

-- 2018-04-17T15:07:00.565
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Das Produkt darf nicht an den Kunden {0} verkauft werden. Grund: {1}',Updated=TO_TIMESTAMP('2018-04-17 15:07:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544677
;

-- 2018-04-17T15:07:06.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 15:07:06','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544677 AND AD_Language='en_US'
;

-- 2018-04-17T15:07:23.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 15:07:23','YYYY-MM-DD HH24:MI:SS'),MsgText='Das Produkt darf nicht an den Kunden {0} verkauft werden. Grund: {1}' WHERE AD_Message_ID=544677 AND AD_Language='de_CH'
;

-- 2018-04-17T15:10:21.466
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 15:10:21','YYYY-MM-DD HH24:MI:SS'),MsgText='Product not allowed to be sold to customer {0} because {1}.' WHERE AD_Message_ID=544677 AND AD_Language='en_US'
;

-- 2018-04-17T15:10:25.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-04-17 15:10:25','YYYY-MM-DD HH24:MI:SS'),MsgText='Product not allowed to be sold to customer {0} because {1}.' WHERE AD_Message_ID=544677 AND AD_Language='nl_NL'
;

-- 2018-04-17T17:34:57.048
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='ProductSalesExclusionError',Updated=TO_TIMESTAMP('2018-04-17 17:34:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544677
;

