-- 2018-05-04T22:42:33.263
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='Not mandatory while the delivery order is still drafted.',Updated=TO_TIMESTAMP('2018-05-04 22:42:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559861
;

-- 2018-05-04T22:42:37.200
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','DK_DesiredDeliveryDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2018-05-04T22:42:37.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','DK_DesiredDeliveryDate',null,'NULL',null)
;

-- 2018-05-04T22:43:12.912
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='Not mandatory while the delivery order is still drafted.',Updated=TO_TIMESTAMP('2018-05-04 22:43:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559862
;

-- 2018-05-04T22:43:17
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','DK_DesiredDeliveryTime_From','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2018-05-04T22:43:17.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','DK_DesiredDeliveryTime_From',null,'NULL',null)
;

-- 2018-05-04T22:43:30.994
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='Not mandatory while the delivery order is still drafted.',Updated=TO_TIMESTAMP('2018-05-04 22:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559863
;

-- 2018-05-04T22:43:34.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','DK_DesiredDeliveryTime_To','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2018-05-04T22:43:34.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','DK_DesiredDeliveryTime_To',null,'NULL',null)
;

-- 2018-05-04T22:48:41.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N', TechnicalNote='Not mandatory while the delivery order is still drafted.',Updated=TO_TIMESTAMP('2018-05-04 22:48:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559874
;

-- 2018-05-04T22:48:42.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','DK_Consignee_DesiredStation','VARCHAR(10)',null,null)
;

-- 2018-05-04T22:48:42.749
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('derkurier_deliveryorderline','DK_Consignee_DesiredStation',null,'NULL',null)
;

