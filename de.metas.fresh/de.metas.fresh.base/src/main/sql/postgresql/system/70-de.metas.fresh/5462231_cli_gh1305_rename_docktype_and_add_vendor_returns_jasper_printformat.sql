-- 2017-05-10T15:17:11.576
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Produktrücklieferung', PrintName='Produktrücklieferung',Updated=TO_TIMESTAMP('2017-05-10 15:17:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000013
;

-- 2017-05-10T15:17:11.587
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET IsTranslated='N' WHERE C_DocType_ID=1000013
;

-- 2017-05-10T15:18:02.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_PrintTableFormat_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,Description,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,540008,'None',540064,103,100,319,'N',TO_TIMESTAMP('2017-05-10 15:18:02','YYYY-MM-DD HH24:MI:SS'),100,'Druckformat Produktrücklieferung',0,0,'Y','N','Y','N','Y',540790,'Produktrücklieferung',TO_TIMESTAMP('2017-05-10 15:18:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-10T15:18:36.280
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_PrintFormat_ID=540064,Updated=TO_TIMESTAMP('2017-05-10 15:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000013
;

