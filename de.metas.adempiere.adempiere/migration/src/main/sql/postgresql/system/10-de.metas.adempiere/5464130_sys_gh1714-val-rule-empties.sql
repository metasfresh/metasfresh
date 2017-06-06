-- 2017-05-31T15:54:18.450
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2017-05-31 15:54:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3539
;

-- 2017-06-01T17:04:20.794
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540317,Updated=TO_TIMESTAMP('2017-06-01 17:04:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557810
;

-- 2017-06-01T17:05:27.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=30, AD_Val_Rule_ID=540317,Updated=TO_TIMESTAMP('2017-06-01 17:05:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557634
;

-- 2017-06-01T17:12:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='EXISTS (select 1 from M_Product_Category pc where pc.M_Product_Category_ID = M_Product.M_Product_Category_ID and pc.isTradingUnit = ''Y'')',Updated=TO_TIMESTAMP('2017-06-01 17:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540317
;

