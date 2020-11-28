
--
-- Drop the UC and recreate a "normal" index
--

-- 2018-08-01T11:41:20.935
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540192
;

-- 2018-08-01T11:41:20.942
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540192
;

DROP INDEX IF EXISTS public.c_flatrate_term_orderlineterm;

CREATE INDEX c_flatrate_term_orderlineterm
  ON public.c_flatrate_term
  USING btree
  (c_orderline_term_id);


--
-- this was "@IsAutoRenew@='Y'"
-- @ExtensionType/None@='EA' | @ExtensionType/None@='EO'-- 2018-08-01T14:10:51.705
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@ExtensionType/None@=''EA'' | @ExtensionType/None@=''EO''',Updated=TO_TIMESTAMP('2018-08-01 14:10:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548482
;

