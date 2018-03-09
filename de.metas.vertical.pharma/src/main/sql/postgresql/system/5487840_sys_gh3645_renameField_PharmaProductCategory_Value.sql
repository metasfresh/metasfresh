-- 2018-03-09T13:13:32.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PharmaProductCategory_Name', Name='Pharma Product Category Name', PrintName='Pharma Product Category Name',Updated=TO_TIMESTAMP('2018-03-09 13:13:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543924
;

-- 2018-03-09T13:13:32.697
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PharmaProductCategory_Name', Name='Pharma Product Category Name', Description=NULL, Help=NULL WHERE AD_Element_ID=543924
;

-- 2018-03-09T13:13:32.699
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PharmaProductCategory_Name', Name='Pharma Product Category Name', Description=NULL, Help=NULL, AD_Element_ID=543924 WHERE UPPER(ColumnName)='PHARMAPRODUCTCATEGORY_NAME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-03-09T13:13:32.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PharmaProductCategory_Name', Name='Pharma Product Category Name', Description=NULL, Help=NULL WHERE AD_Element_ID=543924 AND IsCentrallyMaintained='Y'
;

-- 2018-03-09T13:13:32.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Pharma Product Category Name', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543924) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543924)
;

-- 2018-03-09T13:13:32.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Pharma Product Category Name', Name='Pharma Product Category Name' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543924)
;


/* DDL */ SELECT public.db_alter_table('I_Product','ALTER TABLE public.I_Product RENAME COLUMN PharmaProductCategory_Value TO PharmaProductCategory_Name')
;


-- 2018-03-09T13:18:45.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540421,Updated=TO_TIMESTAMP('2018-03-09 13:18:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540953
;

-- 2018-03-09T13:18:57.525
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2018-03-09 13:18:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563099
;

