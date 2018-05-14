-- 2018-05-07T11:51:03.204
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Shipper (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,IsDefault,M_Shipper_ID,Name,ShipperGateway,Updated,UpdatedBy) VALUES (1000000,1000000,TO_TIMESTAMP('2018-05-07 11:51:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N',540003,'Der Kurier Shipper','derKurier',TO_TIMESTAMP('2018-05-07 11:51:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-07T12:08:51.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,DecimalPattern,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,0,554534,TO_TIMESTAMP('2018-05-07 12:08:51','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,'00000000000',1,'Y','N','Y','N','Der Kurier Sendungsnummernkreis','N',1000000,TO_TIMESTAMP('2018-05-07 12:08:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-07T12:09:12.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DerKurier_Shipper_Config (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,APIServerBaseURL,Created,CreatedBy,DerKurier_Shipper_Config_ID,DK_CustomerNumber,IsActive,M_Shipper_ID,Updated,UpdatedBy) VALUES (1000000,1000000,554534,'https://leoz.derkurier.de:13000/rs/api/v1',TO_TIMESTAMP('2018-05-07 12:09:12','YYYY-MM-DD HH24:MI:SS'),100,540001,'1234567','Y',540003,TO_TIMESTAMP('2018-05-07 12:09:12','YYYY-MM-DD HH24:MI:SS'),100)
;

