-- 2018-05-31T18:28:22.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_LUTU_Configuration.M_Product_ID=@M_Product_ID/-1@
 AND M_HU_LUTU_Configuration.QtyCU = @QtyEntered/0@
',Updated=TO_TIMESTAMP('2018-05-31 18:28:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540396
;

-- 2018-05-31T18:28:28.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_LUTU_Configuration.M_Product_ID=@M_Product_ID/-1@
 AND M_HU_LUTU_Configuration.QtyCU = @QtyEntered@
',Updated=TO_TIMESTAMP('2018-05-31 18:28:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540396
;

-- 2018-05-31T18:32:46.682
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_LUTU_Configuration.M_Product_ID=@M_Product_ID/-1@
 AND M_HU_LUTU_Configuration.QtyCU = @QtyEntered/0@
',Updated=TO_TIMESTAMP('2018-05-31 18:32:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540396
;







-- 2018-05-31T18:56:06.698
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_LUTU_Configuration.M_Product_ID=@M_Product_ID/-1@
 AND 

COALESCE (M_HU_LUTU_Configuration.QtyCU, 1) * COALESCE (M_HU_LUTU_Configuration.QtyTU, 1) * COALESCE(M_HU_LUTU_Configuration.QtyLU, 1)

= @QtyEntered/0@
',Updated=TO_TIMESTAMP('2018-05-31 18:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540396
;

-- 2018-05-31T18:56:16.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU_LUTU_Configuration.M_Product_ID=@M_Product_ID/-1@
 AND 
COALESCE (M_HU_LUTU_Configuration.QtyCU, 1) * COALESCE (M_HU_LUTU_Configuration.QtyTU, 1) * COALESCE(M_HU_LUTU_Configuration.QtyLU, 1) = @QtyEntered/0@
',Updated=TO_TIMESTAMP('2018-05-31 18:56:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540396
;


