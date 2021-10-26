-- 2021-08-06T22:01:13.461Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ES_FTS_Config SET ES_DocumentToIndexTemplate='{ "c_bpartner_id": @C_BPartner_ID@, "c_bpartner_location_id": @C_BPartner_Location_ID@, "ad_user_id": @C_BP_Contact_ID@, "value": @Value@, "name": @Name@, "city": @City@, "postal": @Postal@, "address1": @Address1@, "firstname": @Firstname@, "lastname": @Lastname@ }
',Updated=TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

-- 2021-08-06T22:01:13.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),100,'value',540000,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:13.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),100,'lastname',540001,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:13.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),100,'address1',540002,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:14.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),100,'_id',540003,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:14.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100,'c_bpartner_id',540004,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:14.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100,'c_bpartner_location_id',540005,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:14.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100,'name',540006,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:14.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100,'postal',540007,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:14.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100,'ad_user_id',540008,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:14.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100,'city',540009,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-06T22:01:14.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO ES_FTS_Config_Field (AD_Client_ID,AD_Org_ID,Created,CreatedBy,ES_FieldName,ES_FTS_Config_Field_ID,ES_FTS_Config_ID,IsActive,Updated,UpdatedBy) VALUES (0,0,TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100,'firstname',540010,540000,'Y',TO_TIMESTAMP('2021-08-07 01:01:14','YYYY-MM-DD HH24:MI:SS'),100)
;

