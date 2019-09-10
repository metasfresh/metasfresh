-- 18.04.2016 12:00
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormat SET Name='Alternativ Lieferschein',Updated=TO_TIMESTAMP('2016-04-18 12:00:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540033
;

-- 18.04.2016 12:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormat SET Name='Alternativ Lieferschein 2',Updated=TO_TIMESTAMP('2016-04-18 12:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540041
;

-- 18.04.2016 12:01
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormat SET Name='Alternativ Lieferschein Zusammenzug',Updated=TO_TIMESTAMP('2016-04-18 12:01:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_PrintFormat_ID=540034
;

-- 18.04.2016 12:09
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_PrintTableFormat_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,540008,'LS_M',540053,102,100,319,'N',TO_TIMESTAMP('2016-04-18 12:09:01','YYYY-MM-DD HH24:MI:SS'),100,0,0,'Y','N','N','Y','Y',540584,'Alternativ Lieferschein 2 Zusammenzug',TO_TIMESTAMP('2016-04-18 12:09:01','YYYY-MM-DD HH24:MI:SS'),100)
;

