-- 2017-09-22T10:24:12.147
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=316
;

-- 2017-09-22T10:24:12.150
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=316
;

-- 2017-09-22T10:26:23.690
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=667
;

-- 2017-09-22T10:26:23.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=667
;

-- 2017-09-22T10:28:58.099
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540011
;

-- 2017-09-22T10:28:58.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540011
;

-- 2017-09-22T10:29:08.716
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=669
;

-- 2017-09-22T10:29:08.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=669
;

-- 2017-09-22T10:30:30.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=317
;

-- 2017-09-22T10:30:30.254
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=317
;

-- 2017-09-22T10:30:41.952
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=668
;

-- 2017-09-22T10:30:41.954
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=668
;

-- 2017-09-22T10:34:06.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=1000011
;

-- 2017-09-22T10:34:54.670
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=1000000
;

-- 2017-09-22T10:34:59.579
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540040
;

-- 2017-09-22T10:34:59.582
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540040
;

-- 2017-09-22T10:35:25.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540076
;

-- 2017-09-22T10:35:25.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540076
;

-- 2017-09-22T10:35:38.897
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=540001
;

-- 2017-09-22T10:35:38.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=540001
;

-- 2017-09-22T10:36:47.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=540041
;

-- 2017-09-22T10:36:47.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=540041
;

-- 2017-09-22T10:36:57.300
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540008
;

-- 2017-09-22T10:36:57.303
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=540008
;

-- 2017-09-22T10:38:07.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=540009
;

-- 2017-09-22T10:38:07.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=540009
;




--
-- Abo Konditionen
DELETE FROM AD_Menu WHERE ad_window_id=316;

--
-- Vorbestellungsart
DELETE FROM AD_Menu WHERE ad_window_id=317;

--
--C_SubscriptionType_ID -- AD_Element_ID=2385
--
DELETE FROM AD_Field WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2385);
DELETE FROM EXP_FormatLine WHERE AD_Column_ID IN (select AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2385);
DELETE FROM AD_Column WHERE AD_Element_ID=2385;
DELETE FROM AD_Element WHERE AD_Element_ID=2385;

ALTER TABLE M_Product DROP COLUMN IF EXISTS C_SubscriptionType_ID;


DROP VIEW IF EXISTS C_Subscription_BPartner_V;
DROP Table IF EXISTS c_subscriptionchange;
DROP Table IF EXISTS C_Subscription_Delivery;
DROP Table IF EXISTS C_Subscriptioncontrol;
DROP Table IF EXISTS C_Subscription;

-- 2017-09-22T10:44:39.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=2384
;

-- 2017-09-22T10:44:39.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=2384
;

-- 2017-09-22T10:44:44.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=2383
;

-- 2017-09-22T10:44:44.106
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=2383
;

-- 2017-09-22T10:44:47.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=540008
;

-- 2017-09-22T10:44:47.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=540008
;

-- 2017-09-22T10:45:39.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=540077
;

-- 2017-09-22T10:45:39.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=540077
;

-- 2017-09-22T10:45:49.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=540017
;

-- 2017-09-22T10:45:49.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=540017
;
