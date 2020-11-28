-- 2017-05-17T09:50:01.693
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_PrintTableFormat_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,Description,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,540008,'None',540067,103,100,319,'N',TO_TIMESTAMP('2017-05-17 09:50:01','YYYY-MM-DD HH24:MI:SS'),100,'Druckformat für Kundenwarenrückgabe',0,0,'Y','N','Y','N','Y',540792,'Kundenwarenrückgabe',TO_TIMESTAMP('2017-05-17 09:50:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-17T09:50:30.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_PrintFormat_ID=540067,Updated=TO_TIMESTAMP('2017-05-17 09:50:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=1000015
;

