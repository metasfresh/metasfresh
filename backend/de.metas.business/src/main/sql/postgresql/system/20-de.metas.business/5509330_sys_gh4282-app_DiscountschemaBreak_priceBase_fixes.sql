
--
-- fix errors regarding missing PricesBase context value; also trl
--
-- 2019-01-11T07:23:13.482
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Preissystem',Updated=TO_TIMESTAMP('2019-01-11 07:23:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541611
;

-- 2019-01-11T07:23:20.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:20','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preissystem' WHERE AD_Ref_List_ID=541611 AND AD_Language='de_CH'
;

-- 2019-01-11T07:23:25.845
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541611 AND AD_Language='en_US'
;

-- 2019-01-11T07:23:32.542
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Preissystem' WHERE AD_Ref_List_ID=541611 AND AD_Language='de_DE'
;

-- 2019-01-11T07:23:35.958
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:35','YYYY-MM-DD HH24:MI:SS'),Name='Pricing system' WHERE AD_Ref_List_ID=541611 AND AD_Language='en_US'
;

-- 2019-01-11T07:23:42.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:23:42','YYYY-MM-DD HH24:MI:SS'),Name='Preissystem' WHERE AD_Ref_List_ID=541611 AND AD_Language='nl_NL'
;

-- 2019-01-11T07:24:19.224
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Name='Festpreis',Updated=TO_TIMESTAMP('2019-01-11 07:24:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=541612
;

-- 2019-01-11T07:24:22.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:24:22','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Festpreis' WHERE AD_Ref_List_ID=541612 AND AD_Language='de_CH'
;

-- 2019-01-11T07:24:25.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:24:25','YYYY-MM-DD HH24:MI:SS'),Name='Festpreis' WHERE AD_Ref_List_ID=541612 AND AD_Language='nl_NL'
;

-- 2019-01-11T07:24:28.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:24:28','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Ref_List_ID=541612 AND AD_Language='en_US'
;

-- 2019-01-11T07:24:32.412
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2019-01-11 07:24:32','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Festpreis' WHERE AD_Ref_List_ID=541612 AND AD_Language='de_DE'
;

-- 2019-01-11T07:24:51.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='P', IsMandatory='Y',Updated=TO_TIMESTAMP('2019-01-11 07:24:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559657
;

-- 2019-01-11T07:24:59.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_discountschemabreak','PriceBase','CHAR(1)',null,'P')
;

-- 2019-01-11T07:24:59.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_DiscountSchemaBreak SET PriceBase='P' WHERE PriceBase IS NULL
;

-- 2019-01-11T07:24:59.351
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_discountschemabreak','PriceBase',null,'NOT NULL',null)
;

-- 2019-01-11T07:25:53.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase/P@ = F',Updated=TO_TIMESTAMP('2019-01-11 07:25:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559659
;

-- 2019-01-11T07:25:56.840
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase/P@ = F',Updated=TO_TIMESTAMP('2019-01-11 07:25:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560678
;

-- 2019-01-11T07:26:03.788
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase/P@ = P',Updated=TO_TIMESTAMP('2019-01-11 07:26:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559655
;

-- 2019-01-11T07:26:06.825
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@M_Product_ID/0@!0',Updated=TO_TIMESTAMP('2019-01-11 07:26:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6610
;

