-- 2018-02-17T13:26:26.947
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormat (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormat_ID,Name,CSVEncoding,CSVFieldDelimiter,CSVFieldQuote) VALUES (0,0,TO_TIMESTAMP('2018-02-17 13:26:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 13:26:26','YYYY-MM-DD HH24:MI:SS'),100,540000,'default','UTF-8','\t','-')
;

-- 2018-02-17T13:32:27.503
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540000,0,0,559149,'Umsatz',TO_TIMESTAMP('2018-02-17 13:32:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 13:32:27','YYYY-MM-DD HH24:MI:SS'),100,540000,10)
;

-- 2018-02-17T13:33:00.211
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540000,0,0,559148,'Gegenkonto',TO_TIMESTAMP('2018-02-17 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 13:33:00','YYYY-MM-DD HH24:MI:SS'),100,540001,20)
;

-- 2018-02-17T13:34:07.155
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540000,0,0,559151,'Belegfeld1',TO_TIMESTAMP('2018-02-17 13:34:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 13:34:07','YYYY-MM-DD HH24:MI:SS'),100,540002,30)
;

-- 2018-02-17T13:35:34.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540000,0,0,559152,'Belegfeld2',TO_TIMESTAMP('2018-02-17 13:35:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 13:35:34','YYYY-MM-DD HH24:MI:SS'),100,540003,40)
;

-- 2018-02-17T13:36:03.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540000,0,0,559152,'Datum',TO_TIMESTAMP('2018-02-17 13:36:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 13:36:03','YYYY-MM-DD HH24:MI:SS'),100,540004,50)
;

-- 2018-02-17T13:36:27.810
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540000,0,0,559147,'Konto',TO_TIMESTAMP('2018-02-17 13:36:27','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 13:36:27','YYYY-MM-DD HH24:MI:SS'),100,540005,60)
;

-- 2018-02-17T13:36:56.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540000,0,0,559150,'KSt1',TO_TIMESTAMP('2018-02-17 13:36:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 13:36:55','YYYY-MM-DD HH24:MI:SS'),100,540006,70)
;

-- 2018-02-17T14:28:57.906
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DATEV_ExportFormatColumn SET AD_Column_ID=559219, FormatPattern='ddMMyy',Updated=TO_TIMESTAMP('2018-02-17 14:28:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540003
;

-- 2018-02-17T14:29:17.696
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DATEV_ExportFormatColumn SET FormatPattern='dd.MM.yyyy',Updated=TO_TIMESTAMP('2018-02-17 14:29:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540004
;

-- 2018-02-17T14:29:32.949
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DATEV_ExportFormatColumn SET AD_Column_ID=559218,Updated=TO_TIMESTAMP('2018-02-17 14:29:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE DATEV_ExportFormatColumn_ID=540006
;

-- 2018-02-17T14:44:25.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DATEV_ExportFormatColumn (DATEV_ExportFormat_ID,AD_Client_ID,AD_Org_ID,AD_Column_ID,Name,Created,CreatedBy,IsActive,Updated,UpdatedBy,DATEV_ExportFormatColumn_ID,SeqNo) VALUES (540000,0,0,559221,'Buchungstext',TO_TIMESTAMP('2018-02-17 14:44:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-02-17 14:44:25','YYYY-MM-DD HH24:MI:SS'),100,540007,80)
;

-- 2018-02-17T14:53:10.442
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DATEV_ExportFormat SET CSVFieldQuote='', DecimalSeparator=',',Updated=TO_TIMESTAMP('2018-02-17 14:53:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE DATEV_ExportFormat_ID=540000
;

