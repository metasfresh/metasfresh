-- 2020-07-02T12:08:05.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541328,'S',TO_TIMESTAMP('2020-07-02 15:08:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ShowQRBill',TO_TIMESTAMP('2020-07-02 15:08:04','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2020-07-02T12:09:08.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2020-07-02 15:09:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541328
;

-- 2020-07-06T13:19:46.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET Description='This is used to swtich between QR Bill and ESR. By default, we set on Y, and we show the QR Bill.',Updated=TO_TIMESTAMP('2020-07-06 16:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541328
;

