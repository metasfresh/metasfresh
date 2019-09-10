-- 27.08.2015 17:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Preference WHERE AD_Client_ID=1000000 AND AD_Org_ID=0 AND AD_User_ID IS NULL AND AD_Window_ID IS NULL AND Attribute='DeliveryRule'
;

-- 27.08.2015 17:56
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Preference (AD_Preference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID, AD_User_ID, Attribute, Value) VALUES (540062,1000000,0, 'Y',now(),100,now(),100, NULL,NULL,'DeliveryRule','F')
;

-- 27.08.2015 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Preference WHERE AD_Client_ID=1000000 AND AD_Org_ID=0 AND AD_User_ID IS NULL AND AD_Window_ID IS NULL AND Attribute='DeliveryViaRule'
;

-- 27.08.2015 17:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Preference (AD_Preference_ID, AD_Client_ID, AD_Org_ID, IsActive, Created,CreatedBy,Updated,UpdatedBy,AD_Window_ID, AD_User_ID, Attribute, Value) VALUES (540063,1000000,0, 'Y',now(),100,now(),100, NULL,NULL,'DeliveryViaRule','P')
;

