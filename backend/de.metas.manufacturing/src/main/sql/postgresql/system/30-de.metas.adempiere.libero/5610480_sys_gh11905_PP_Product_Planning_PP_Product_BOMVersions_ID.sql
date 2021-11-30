-- 2021-10-22T10:48:46.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=580087, ColumnName='PP_Product_BOMVersions_ID', Description=NULL, FieldLength=10, Help=NULL, Name='BOMVersions & Formula',Updated=TO_TIMESTAMP('2021-10-22 13:48:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53397
;

-- 2021-10-22T10:48:46.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='BOMVersions & Formula', Description=NULL, Help=NULL WHERE AD_Column_ID=53397
;

-- 2021-10-22T10:48:46.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580087) 
;

-- 2021-10-22T10:48:46.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('PP_Product_Planning','ALTER TABLE public.PP_Product_Planning ADD COLUMN PP_Product_BOMVersions_ID NUMERIC(10)')
;

-- 2021-10-22T10:48:47.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PP_Product_Planning ADD CONSTRAINT PPProductBOMVersions_PPProductPlanning FOREIGN KEY (PP_Product_BOMVersions_ID) REFERENCES public.PP_Product_BOMVersions DEFERRABLE INITIALLY DEFERRED
;

