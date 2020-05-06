-- 2018-01-03T20:57:38.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.material.cockpit.stock.process.MD_Stock_Reset_From_M_HUs',Updated=TO_TIMESTAMP('2018-01-03 20:57:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540907
;

-- 2018-01-03T20:59:18.410
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
TRUNCATE TABLE MD_Stock
;

-- 2018-01-03T21:01:18.566
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
TRUNCATE TABLE MD_Stock
;

-- 2018-01-03T22:03:08.779
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=558324
;

-- 2018-01-03T22:03:08.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=558324
;

SELECT public.db_alter_table('MD_Cockpit','ALTER TABLE MD_Cockpit DROP COLUMN qtyonhandstock;');

-- 2018-01-03T22:05:12.090
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)',Updated=TO_TIMESTAMP('2018-01-03 22:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543666
;

-- 2018-01-03T22:05:12.092
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='AttributesKey', Name='AttributesKey (technical)', Description=NULL, Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)' WHERE AD_Element_ID=543666
;

-- 2018-01-03T22:05:12.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AttributesKey', Name='AttributesKey (technical)', Description=NULL, Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)', AD_Element_ID=543666 WHERE UPPER(ColumnName)='ATTRIBUTESKEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-03T22:05:12.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='AttributesKey', Name='AttributesKey (technical)', Description=NULL, Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)' WHERE AD_Element_ID=543666 AND IsCentrallyMaintained='Y'
;

-- 2018-01-03T22:05:12.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AttributesKey (technical)', Description=NULL, Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543666) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543666)
;

-- 2018-01-03T22:05:21.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543666, ColumnName='AttributesKey', Description=NULL, Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)', Name='AttributesKey (technical)',Updated=TO_TIMESTAMP('2018-01-03 22:05:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558484
;

-- 2018-01-03T22:05:21.534
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AttributesKey (technical)', Description=NULL, Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)' WHERE AD_Column_ID=558484
;

-- 2018-01-03T22:05:35.359
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('MD_Stock','ALTER TABLE public.MD_Stock ADD COLUMN AttributesKey VARCHAR(1024) DEFAULT ''-1002'' NOT NULL')
;

SELECT public.db_alter_table('MD_Cockpit','ALTER TABLE MD_Stock DROP COLUMN storageattributeskey');

-- 2018-01-03T22:07:16.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543666, ColumnName='AttributesKey', Description=NULL, Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)', Name='AttributesKey (technical)',Updated=TO_TIMESTAMP('2018-01-03 22:07:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558489
;

-- 2018-01-03T22:07:16.246
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='AttributesKey (technical)', Description=NULL, Help='Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)' WHERE AD_Column_ID=558489
;

