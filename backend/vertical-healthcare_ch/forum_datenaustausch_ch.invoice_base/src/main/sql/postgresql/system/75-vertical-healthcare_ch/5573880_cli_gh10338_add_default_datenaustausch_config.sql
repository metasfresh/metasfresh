
-- Insert one default record if none exists yet.
--
-- 2020-12-03T05:43:57.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO HC_Forum_Datenaustausch_Config 
(AD_Client_ID,AD_Org_ID,Created,CreatedBy,Description,ExportedXmlMode,ExportedXmlVersion,HC_Forum_Datenaustausch_Config_ID,ImportedBPartnerLanguage,ImportedMunicipalityBP_Group_ID,ImportedPartientBP_Group_ID,IsActive,StoreDirectory,Updated,UpdatedBy,Via_EAN) 
select 1000000,1000000,TO_TIMESTAMP('2020-12-03 06:43:57','YYYY-MM-DD HH24:MI:SS'),100,'Standard-Datensatz mit Parametern für den Import und Export von XML-Dateien nach https://www.forum-datenaustausch.ch/ .','P','4.4',540000,'de_CH',1000000,1000000,'Y','/tmp',TO_TIMESTAMP('2020-12-03 06:43:57','YYYY-MM-DD HH24:MI:SS'),100,'1234567890123'
where not exists (select 1 from HC_Forum_Datenaustausch_Config)
;

