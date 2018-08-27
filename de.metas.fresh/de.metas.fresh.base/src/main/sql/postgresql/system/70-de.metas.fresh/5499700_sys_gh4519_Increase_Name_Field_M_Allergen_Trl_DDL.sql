

DROP TABLE IF EXISTS temp_M_Allergen_Trl;

CREATE TABLE temp_M_Allergen_Trl as
Select M_Allergen_ID, ad_language, Name from M_Allergen_Trl;


ALTER TABLE M_Allergen_Trl DROP COLUMN Name;




-- 2018-08-24T10:24:44.369
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Allergen_Trl','ALTER TABLE public.M_Allergen_Trl ADD COLUMN Name VARCHAR(2000)')
;




update M_Allergen_Trl set Name = (select name from temp_M_Allergen_Trl  t where t.M_Allergen_ID = M_Allergen_Trl.M_Allergen_ID AND t.AD_Language = M_Allergen_Trl.AD_Language);



DROP TABLE IF EXISTS temp_M_Allergen_Trl;