-- 2019-12-06T17:02:47.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544947,0,TO_TIMESTAMP('2019-12-06 19:02:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','The type is not supported.
A cause could be that is not stock.','E',TO_TIMESTAMP('2019-12-06 19:02:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsService.createPickRequest')
;

-- 2019-12-06T17:02:47.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544947 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-12-06T17:02:51.856Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Typ wird nicht unterstützt.
Eine Ursache könnte sein, dass nicht auf Lager ist.',Updated=TO_TIMESTAMP('2019-12-06 19:02:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544947
;

-- 2019-12-06T17:02:56.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-06 19:02:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544947
;

-- 2019-12-06T17:03:03.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Typ wird nicht unterstützt.
Eine Ursache könnte sein, dass nicht auf Lager ist.',Updated=TO_TIMESTAMP('2019-12-06 19:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544947
;

-- 2019-12-06T17:04:23.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Der Typ {0} wird nicht unterstützt.
Eine Ursache könnte sein, dass nicht auf Lager ist.',Updated=TO_TIMESTAMP('2019-12-06 19:04:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544947
;

-- 2019-12-06T17:04:30.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='The type {0} is not supported.
A cause could be that is not stock.',Updated=TO_TIMESTAMP('2019-12-06 19:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544947
;

-- 2019-12-06T17:04:37.021Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Der Typ {0} wird nicht unterstützt.
Eine Ursache könnte sein, dass nicht auf Lager ist.',Updated=TO_TIMESTAMP('2019-12-06 19:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544947
;

-- 2019-12-06T17:10:44.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='Es kann kein Bestand für diese Auftragszeile gefunden werden.',Updated=TO_TIMESTAMP('2019-12-06 19:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544947
;

-- 2019-12-06T17:10:49.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET MsgText='Es kann kein Bestand für diese Auftragszeile gefunden werden.',Updated=TO_TIMESTAMP('2019-12-06 19:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544947
;

-- 2019-12-06T17:10:57.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET MsgText='No onhand qty can be found for this order line.',Updated=TO_TIMESTAMP('2019-12-06 19:10:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544947
;




-- 2019-12-09T08:21:10.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message SET Value='de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsService.UnAllocated_Type_Error',Updated=TO_TIMESTAMP('2019-12-09 10:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=544947
;





-- 2019-12-09T08:33:30.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544948,0,TO_TIMESTAMP('2019-12-09 10:33:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Typ row {0}  wird nicht unterstützt.','E',TO_TIMESTAMP('2019-12-09 10:33:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.pickingV2.productsToPick.rows.ProductsToPickRowsService.TypeRow_NotSupported')
;

-- 2019-12-09T08:33:30.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Message_ID=544948 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2019-12-09T08:33:56.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Type row  {0}  is not supported.',Updated=TO_TIMESTAMP('2019-12-09 10:33:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=544948
;

-- 2019-12-09T08:33:59.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-09 10:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=544948
;



