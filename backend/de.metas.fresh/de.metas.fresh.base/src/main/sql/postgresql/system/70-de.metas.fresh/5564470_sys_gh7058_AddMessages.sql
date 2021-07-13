-- 2020-07-29T12:02:23.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544994,0,TO_TIMESTAMP('2020-07-29 15:02:23','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','No distribution order lines were found for your product.','I',TO_TIMESTAMP('2020-07-29 15:02:23','YYYY-MM-DD HH24:MI:SS'),100,'DD_Order_NoLine_for_product')
;

-- 2020-07-29T12:02:23.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544994 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-07-29T12:02:35.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Für das Produkt wurden keine Distributionsauftragspositionen gefunden.',Updated=TO_TIMESTAMP('2020-07-29 15:02:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544994
;

-- 2020-07-29T12:02:43.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-29 15:02:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544994
;

-- 2020-07-29T12:02:46.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Für das Produkt wurden keine Distributionsauftragspositionen gefunden.',Updated=TO_TIMESTAMP('2020-07-29 15:02:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544994
;

-- 2020-07-29T12:06:02.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgType='E', Value='de.metas.handlingunits.ddorder.api.impl.DDOrderLinesAllocator.DD_Order_NoLine_for_product',Updated=TO_TIMESTAMP('2020-07-29 15:06:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544994
;

-- 2020-07-29T12:08:16.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Für das Produkt wurden keine Distributionsauftragspositionen gefunden.
Produkt {0}
Produktlagerung {1}',Updated=TO_TIMESTAMP('2020-07-29 15:08:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544994
;

-- 2020-07-29T12:08:31.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No distribution order lines were found for your product.
Product {0}
Product storage {1}',Updated=TO_TIMESTAMP('2020-07-29 15:08:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544994
;

-- 2020-07-29T12:08:36.939Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2020-07-29 15:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544994
;

-- 2020-07-29T12:08:43.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Für das Produkt wurden keine Distributionsauftragspositionen gefunden.
Produkt {0}
Produktlagerung {1}',Updated=TO_TIMESTAMP('2020-07-29 15:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544994
;

-- 2020-07-29T12:10:23.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Für das Produkt wurden keine Distributionsauftragspositionen gefunden.
',Updated=TO_TIMESTAMP('2020-07-29 15:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544994
;

-- 2020-07-29T12:11:22.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No distribution order lines were found for your product.
',Updated=TO_TIMESTAMP('2020-07-29 15:11:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544994
;

-- 2020-07-29T12:11:29.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Für das Produkt wurden keine Distributionsauftragspositionen gefunden.',Updated=TO_TIMESTAMP('2020-07-29 15:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544994
;

-- 2020-07-29T12:15:55.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544995,0,TO_TIMESTAMP('2020-07-29 15:15:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für das Produkt wurde keine HU gefunden.','E',TO_TIMESTAMP('2020-07-29 15:15:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.ddorder.api.impl.HUDDOrderBL.NoHu_For_Product')
;

-- 2020-07-29T12:15:55.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544995 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2020-07-29T12:16:03.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='No HU was found for your product. ',Updated=TO_TIMESTAMP('2020-07-29 15:16:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544995
;

-- 2020-07-29T12:16:07.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-29 15:16:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544995
;

