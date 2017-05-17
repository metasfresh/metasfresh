-- 2017-05-05T16:38:27.487
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_PrintTableFormat_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,Description,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,540008,'None',540062,103,100,321,'N',TO_TIMESTAMP('2017-05-05 16:38:27','YYYY-MM-DD HH24:MI:SS'),100,'Druckformat Entsorgung',0,0,'Y','N','Y','N','Y','Entsorgung',TO_TIMESTAMP('2017-05-05 16:38:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-05-05T17:02:13.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormat SET JasperProcess_ID=540789,Updated=TO_TIMESTAMP('2017-05-05 17:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540062
;

-- 2017-05-05T17:02:22.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_PrintFormat_ID=540062,Updated=TO_TIMESTAMP('2017-05-05 17:02:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540948
;

