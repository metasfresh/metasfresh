
--- Delete Haddex Date fields


-- 2021-01-21T09:32:25.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=572311)
;

-- 2021-01-21T09:32:39.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=572311)
;

-- 2021-01-21T09:32:39.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=572311)
;

-- 2021-01-21T09:32:39.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=572311)
;



-- Delete Haddex Control No fields

-- 2021-01-21T09:32:25.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=572312)
;

-- 2021-01-21T09:32:39.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=572312)
;

-- 2021-01-21T09:32:39.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=572312)
;

-- 2021-01-21T09:32:39.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID IN (select AD_Field_ID from AD_Field where AD_Column_ID=572312)
;







---------------------------------
---- BACKUP FOR M_Product table to make sure we don't use the old haddex data, if any
---------------------------------



CREATE TABLE backup.BKP_M_Product_ForHaddex_21012021 as SELECT * from M_Product;





----------------------------


SELECT public.db_alter_table('M_Product','ALTER TABLE M_Product DROP COLUMN HaddexControlNr');
;

SELECT public.db_alter_table('M_Product','ALTER TABLE M_Product DROP COLUMN DateHaddexCheck');
;








-- 2021-01-21T09:42:51.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572312
;

-- 2021-01-21T09:42:51.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=572312
;

-- 2021-01-21T09:42:59.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=572311
;

-- 2021-01-21T09:42:59.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=572311
;











-- 2021-01-21T16:33:50.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=576003
;

