-- 2017-05-15T16:58:28.110
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI SET Description='Internal Packing Item. Don''t use as Packing Item for Products.', Name='Packing Item Template',Updated=TO_TIMESTAMP('2017-05-15 16:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_ID=100
;

-- 2017-05-15T16:58:55.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_HU_PI SET Description='No Packing Item', Name='No Packing Item',Updated=TO_TIMESTAMP('2017-05-15 16:58:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE M_HU_PI_ID=101
;

