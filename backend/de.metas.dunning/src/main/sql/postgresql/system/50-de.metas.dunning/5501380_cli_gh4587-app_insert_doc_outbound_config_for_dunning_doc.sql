-- 2018-09-13T14:00:53.842
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_PrintFormat (AD_Client_ID,AD_Org_ID,AD_PrintColor_ID,AD_PrintFont_ID,AD_Printformat_Group,AD_PrintFormat_ID,AD_PrintPaper_ID,AD_Table_ID,CreateCopy,Created,CreatedBy,Description,FooterMargin,HeaderMargin,IsActive,IsDefault,IsForm,IsStandardHeaderFooter,IsTableBased,JasperProcess_ID,Name,Updated,UpdatedBy) VALUES (1000000,1000000,100,136,'',540093,103,540401,'N',TO_TIMESTAMP('2018-09-13 14:00:53','YYYY-MM-DD HH24:MI:SS'),100,'',0,0,'Y','N','N','N','Y',540999,'Mahnbrief mit Rechnungsbelegen',TO_TIMESTAMP('2018-09-13 14:00:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-13T14:02:32.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO C_Doc_Outbound_Config (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,AD_Table_ID,C_Doc_Outbound_Config_ID,Created,CreatedBy,IsActive,IsCreatePrintJob,IsDirectEnqueue,Updated,UpdatedBy) VALUES (1000000,0,540093,540401,540008,TO_TIMESTAMP('2018-09-13 14:02:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y',TO_TIMESTAMP('2018-09-13 14:02:32','YYYY-MM-DD HH24:MI:SS'),100)
--;

-- only insert this on systems that don't yet have any dunning related C_Doc_Outbound_Config
-- insert is with IsActive='Y' so that it's there as a know option, but not creating archives
INSERT INTO C_Doc_Outbound_Config (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,AD_Table_ID,C_Doc_Outbound_Config_ID,Created,CreatedBy,IsActive,IsCreatePrintJob,IsDirectEnqueue,Updated,UpdatedBy) 
SELECT 1000000,0,540093,540401,540008,TO_TIMESTAMP('2018-09-13 14:02:32','YYYY-MM-DD HH24:MI:SS'),100,'N'/*isactive*/,'N','Y',TO_TIMESTAMP('2018-09-13 14:02:32','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (select 1 from C_Doc_Outbound_Config where AD_Table_ID=540401);