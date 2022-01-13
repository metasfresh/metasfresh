-- Update normal tax rate name
-- 2021-06-10T12:41:56.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory SET Name='Normale MWSt',Updated=TO_TIMESTAMP('2021-06-10 15:41:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_TaxCategory_ID=1000009
;

-- 2021-06-10T12:41:56.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl trl SET Description=NULL, Name='Normale MWSt'  WHERE C_TaxCategory_ID=1000009 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-10T13:18:01.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Normale MWSt',Updated=TO_TIMESTAMP('2021-06-10 16:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=1000009
;

-- Add EN Trl for Normal Tax Rate Category
-- 2021-06-11T07:43:10.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Standard VAT',Updated=TO_TIMESTAMP('2021-06-11 10:43:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=1000009
;

-- 2021-06-11T07:43:16.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Standard VAT',Updated=TO_TIMESTAMP('2021-06-11 10:43:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND C_TaxCategory_ID=1000009
;

-- 2021-06-11T07:43:18.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 10:43:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND C_TaxCategory_ID=1000009
;

-- Update reduced tax rate name
-- 2021-06-10T12:44:06.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory SET Name='Reduzierte MWSt',Updated=TO_TIMESTAMP('2021-06-10 15:44:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_TaxCategory_ID=1000010
;

-- 2021-06-10T12:44:06.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl trl SET Description=NULL, Name='Reduzierte MWSt'  WHERE C_TaxCategory_ID=1000010 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- Add EN TRL for reduced tax rate category
-- 2021-06-11T07:40:59.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Reduced VAT',Updated=TO_TIMESTAMP('2021-06-11 10:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND C_TaxCategory_ID=1000010
;

-- 2021-06-11T07:41:03.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 10:41:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND C_TaxCategory_ID=1000010
;

-- 2021-06-11T07:41:26.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Reduced VAT',Updated=TO_TIMESTAMP('2021-06-11 10:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=1000010
;

-- 2021-06-11T07:41:26.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 10:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=1000010
;




-- Add Super Reduced and Parking VATs
-- 2021-06-11T09:05:57.619Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_TaxCategory (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_TaxCategory_ID,IsActive,Name,ProductType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-06-11 12:05:57','YYYY-MM-DD HH24:MI:SS'),100,540006,'Y','Stark ermäßigte MwSt','I',TO_TIMESTAMP('2021-06-11 12:05:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T09:05:57.622Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_TaxCategory_Trl (AD_Language,C_TaxCategory_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_TaxCategory_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_TaxCategory t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_TaxCategory_ID=540006 AND NOT EXISTS (SELECT 1 FROM C_TaxCategory_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_TaxCategory_ID=t.C_TaxCategory_ID)
;

-- 2021-06-11T09:06:35.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Super-reduced VAT',Updated=TO_TIMESTAMP('2021-06-11 12:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=540006
;

-- 2021-06-11T09:06:35.377Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=540006
;

-- 2021-06-11T09:09:16.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_TaxCategory (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_TaxCategory_ID,IsActive,Name,ProductType,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2021-06-11 12:09:16','YYYY-MM-DD HH24:MI:SS'),100,540007,'Y','Parken MwSt.','I',TO_TIMESTAMP('2021-06-11 12:09:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T09:09:16.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_TaxCategory_Trl (AD_Language,C_TaxCategory_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_TaxCategory_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_TaxCategory t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_TaxCategory_ID=540007 AND NOT EXISTS (SELECT 1 FROM C_TaxCategory_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_TaxCategory_ID=t.C_TaxCategory_ID)
;

-- 2021-06-11T09:09:39.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Parking VAT',Updated=TO_TIMESTAMP('2021-06-11 12:09:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=540007
;

-- 2021-06-11T09:09:39.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 12:09:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_TaxCategory_ID=540007
;

-- Add DE TRL for Parking VAT
-- 2021-06-11T11:02:13.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory SET Name='Vorläufige MwSt',Updated=TO_TIMESTAMP('2021-06-11 14:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_TaxCategory_ID=540007
;

-- 2021-06-11T11:02:13.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl trl SET Description=NULL, Name='Vorläufige MwSt'  WHERE C_TaxCategory_ID=540007 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-06-11T11:02:39.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Vorläufige MwSt',Updated=TO_TIMESTAMP('2021-06-11 14:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_TaxCategory_ID=540007
;

-- 2021-06-11T11:02:45.414Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_TaxCategory_Trl SET Name='Vorläufige MwSt',Updated=TO_TIMESTAMP('2021-06-11 14:02:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_TaxCategory_ID=540007
;