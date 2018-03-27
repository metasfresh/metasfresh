-- 2018-03-26T16:33:34.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543929, ColumnName='ConfigHostKey', Description=NULL, Help=NULL, Name='Host Key',Updated=TO_TIMESTAMP('2018-03-26 16:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=548298
;

-- 2018-03-26T16:33:34.069
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Host Key', Description=NULL, Help=NULL WHERE AD_Column_ID=548298
;

ALTER TABLE AD_Printer_Matching DROP COLUMN HostKey;


-- 2018-03-26T16:36:29.588
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='@ConfigHostKey@',Updated=TO_TIMESTAMP('2018-03-26 16:36:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551593
;

-- 2018-03-26T16:50:16.689
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=551519
;

-- 2018-03-26T16:50:16.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=551519
;

-- 2018-03-26T16:50:23.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=548298
;

-- 2018-03-26T16:50:23.681
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=548298
;

-- 2018-03-26T16:52:28.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,551594,540843,540241,0,TO_TIMESTAMP('2018-03-26 16:52:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.printing','Y',20,TO_TIMESTAMP('2018-03-26 16:52:28','YYYY-MM-DD HH24:MI:SS'),100)
;


-- 2018-03-26T16:53:29.439
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET ErrorMsg='Pro Druck-Configuration und logischem Drucker is nur ein Datensatz erlaubt.',Updated=TO_TIMESTAMP('2018-03-26 16:53:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540241
;

-- 2018-03-26T16:53:32.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS ad_printer_matching_uq_printer_kostkey
;

-- 2018-03-26T16:53:32.478
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX AD_Printer_Matching_UQ_Printer_Kostkey ON AD_Printer_Matching (AD_Printer_ID,AD_Printer_Config_ID) WHERE IsActive='Y'
;

