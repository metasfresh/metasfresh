
-- 05.02.2016 15:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE M_Product_Category ADD IsPackagingMaterial CHAR(1) DEFAULT 'N' CHECK (IsPackagingMaterial IN ('Y','N')) NOT NULL
;
