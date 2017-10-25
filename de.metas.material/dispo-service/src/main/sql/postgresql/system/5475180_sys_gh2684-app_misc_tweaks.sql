--
-- DD_NetworkDistributionLine.DD_NetworkDistributionLine_ID
--
-- 2017-10-23T14:03:34.668
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=10,Updated=TO_TIMESTAMP('2017-10-23 14:03:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=54296
;
--
-- adjust length of MD_Candidate_Type column
--
-- 2017-10-23T14:06:12.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=20,Updated=TO_TIMESTAMP('2017-10-23 14:06:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556481
;

-- 2017-10-23T14:06:16.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('md_candidate','MD_Candidate_Type','VARCHAR(20)',null,null)
;
