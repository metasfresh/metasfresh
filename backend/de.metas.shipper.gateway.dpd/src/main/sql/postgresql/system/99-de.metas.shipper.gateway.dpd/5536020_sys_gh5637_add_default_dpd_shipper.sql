-- 2019-11-11T09:05:47.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Shipper (CreatedBy,IsActive,Created,C_BPartner_ID,TrackingURL,Updated,UpdatedBy,M_Shipper_ID,ShipperGateway,AD_Client_ID,IsDefault,Name,Description,AD_Org_ID) VALUES (100,'Y',TO_TIMESTAMP('2019-11-11 11:05:46','YYYY-MM-DD HH24:MI:SS'),2155894,'https://tracking.dpd.de/status/en_US/parcel/',TO_TIMESTAMP('2019-11-11 11:05:46','YYYY-MM-DD HH24:MI:SS'),100,540007,'dpd',1000000,'N','DPD','DPD Shipper',1000000)
;

-- 2019-11-11T09:05:57.187Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DPD_Shipper_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,DPD_Shipper_config_ID,LoginApiUrl,ShipmentServiceApiUrl,M_Shipper_ID) VALUES (1000000,1000000,TO_TIMESTAMP('2019-11-11 11:05:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-11 11:05:57','YYYY-MM-DD HH24:MI:SS'),100,540000,'https://public-ws-stage.dpd.com/services/LoginService/V2_0/','https://public-ws-stage.dpd.com/services/ShipmentService/V3_2/',540007)
;

