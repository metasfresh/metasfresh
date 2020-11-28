-- 14.10.2015 21:27
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_ILCandHandler (AD_Client_ID,AD_Org_ID,C_ILCandHandler_ID,Classname,Created,CreatedBy,EntityType,Is_AD_User_InCharge_UI_Setting,IsActive,Name,TableName,Updated,UpdatedBy) VALUES (0,0,540013,'de.metas.invoicecandidate.spi.impl.M_InOut_Handler',TO_TIMESTAMP('2015-10-14 21:27:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','N','Y','M_InOut handler','M_InOut',TO_TIMESTAMP('2015-10-14 21:27:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.10.2015 21:30
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ILCandHandler SET Classname='de.metas.invoicecandidate.spi.impl.M_InOutLine_Handler', Is_AD_User_InCharge_UI_Setting='N', Name='M_InOutLine handler', TableName='M_InOutLine',Updated=TO_TIMESTAMP('2015-10-14 21:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ILCandHandler_ID=540011
;

-- 14.10.2015 21:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_ILCandHandler (AD_Client_ID,AD_Org_ID,AD_User_InCharge_ID,C_ILCandHandler_ID,Classname,Created,CreatedBy,EntityType,Is_AD_User_InCharge_UI_Setting,IsActive,Name,TableName,Updated,UpdatedBy) VALUES (0,0,1000000,540014,'de.metas.invoicecandidate.spi.impl.C_Order_Handler',TO_TIMESTAMP('2015-10-14 21:31:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','N','Y','Auftrag','C_Order',TO_TIMESTAMP('2015-10-14 21:31:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 14.10.2015 21:32
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_ILCandHandler SET Classname='de.metas.invoicecandidate.spi.impl.C_OrderLine_Handler', Is_AD_User_InCharge_UI_Setting='Y', TableName='C_OrderLine',Updated=TO_TIMESTAMP('2015-10-14 21:32:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_ILCandHandler_ID=540001
;

