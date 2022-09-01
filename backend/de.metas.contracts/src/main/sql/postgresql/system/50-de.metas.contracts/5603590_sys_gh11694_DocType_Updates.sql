-- 2021-09-07T03:54:16.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Bestellvermittlung',Updated=TO_TIMESTAMP('2021-09-07 06:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541017
;

-- 2021-09-07T03:54:16.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Bestellvermittlung', PrintName='Mediated order'  WHERE C_DocType_ID=541017 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-09-07T03:54:18.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Bestellvermittlung',Updated=TO_TIMESTAMP('2021-09-07 06:54:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541017
;

-- 2021-09-07T03:54:18.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Bestellvermittlung', PrintName='Bestellvermittlung'  WHERE C_DocType_ID=541017 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-09-07T03:56:56.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,555524,TO_TIMESTAMP('2021-09-07 06:56:56','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,1,'Y','N','N','N',' Brokerage commission DocNo','N',1000000,TO_TIMESTAMP('2021-09-07 06:56:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-07T03:56:57.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2021-09-07 06:56:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555524
;

-- 2021-09-07T03:57:17.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=555524,Updated=TO_TIMESTAMP('2021-09-07 06:57:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541018
;


-- 2021-09-07T15:14:38.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-09-07 18:14:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541018
;

-- 2021-09-07T15:14:38.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Vermittlungsprovision', PrintName='Brokerage commission'  WHERE C_DocType_ID=541018 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2021-09-07T15:14:40.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Vermittlungsprovision',Updated=TO_TIMESTAMP('2021-09-07 18:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541018
;

-- 2021-09-07T15:14:40.656Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Vermittlungsprovision', PrintName='Vermittlungsprovision'  WHERE C_DocType_ID=541018 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;
