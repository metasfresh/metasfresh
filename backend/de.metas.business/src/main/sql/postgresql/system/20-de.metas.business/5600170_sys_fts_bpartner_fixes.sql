-- 2021-08-06T12:10:40.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE ES_FTS_Config SET ES_DocumentToIndexTemplate='{ 
"c_bpartner_id": @C_BPartner_ID@, 
"c_bpartner_location_id": @C_BPartner_Location_ID@, 
"ad_user_id": @C_BP_Contact_ID@, 
"value": @Value@, 
"name": @Name@, 
"city": @City@, 
"postal": @Postal@, 
"address1": @Address1@, 
"firstname": @Firstname@, 
"lastname": @Lastname@ 
}

',Updated=TO_TIMESTAMP('2021-08-06 15:10:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ES_FTS_Config_ID=540000
;

