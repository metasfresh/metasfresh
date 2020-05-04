-- 2019-11-18T08:22:14.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Shipper SET Name='DPD Classic', Description='DPD Shipper using CLASSIC product',Updated=TO_TIMESTAMP('2019-11-18 10:22:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Shipper_ID=540007
;

-- 2019-11-18T08:22:33.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE DPD_Shipper_Config SET ShipperProduct='CL',Updated=TO_TIMESTAMP('2019-11-18 10:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE DPD_Shipper_config_ID=540000
;

-- 2019-11-18T08:22:50.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_Shipper (CreatedBy,IsActive,Created,C_BPartner_ID,TrackingURL,Updated,UpdatedBy,M_Shipper_ID,ShipperGateway,AD_Client_ID,IsDefault,Name,Description,AD_Org_ID) VALUES (100,'Y',TO_TIMESTAMP('2019-11-18 10:22:50','YYYY-MM-DD HH24:MI:SS'),2155894,'https://tracking.dpd.de/status/en_US/parcel/',TO_TIMESTAMP('2019-11-18 10:22:50','YYYY-MM-DD HH24:MI:SS'),100,540008,'dpd',1000000,'N','DPD E12','DPD Shipper using E12 product',1000000)
;

-- 2019-11-18T08:23:27.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO DPD_Shipper_Config (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,LoginApiUrl,M_Shipper_ID,PaperFormat,ShipmentServiceApiUrl,DPD_Shipper_config_ID,ShipperProduct) VALUES (1000000,1000000,TO_TIMESTAMP('2019-11-18 10:23:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2019-11-18 10:23:26','YYYY-MM-DD HH24:MI:SS'),100,'https://public-ws-stage.dpd.com/services/LoginService/V2_0/',540008,'A6','https://public-ws-stage.dpd.com/services/ShipmentService/V3_2/',540001,'E12')
;

-- 2019-11-18T08:23:56.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Shipper SET Name='DPD - E12',Updated=TO_TIMESTAMP('2019-11-18 10:23:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Shipper_ID=540008
;

-- 2019-11-18T08:24:01.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_Shipper SET Name='DPD - Classic',Updated=TO_TIMESTAMP('2019-11-18 10:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_Shipper_ID=540007
;

