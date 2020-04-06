
COMMENT ON FUNCTION public.hash_column_value_if_needed(character varying)
  IS 'Computes and returns a password hash value for metasfresh passwords that are not hashed yet.
Example use:

update ad_user 
set password=public.hash_column_value_if_needed(password) 
where name=''metasfresh''
';

COMMENT ON FUNCTION public.hash_column_value(character varying)
  IS 'This function is invoked by hash_column_value_if_needed and computes a metasfresh password hash for the given string.
In all cases I can imagine, it makes more sense to use hash_column_value_if_needed rather than calling this function directly.';

COMMENT ON COLUMN public.ad_user.password
  IS 'Note: the system can work with cleartext passwords and passwords that were hashed using the DB-function public.hash_column_value_if_needed

(pls keep this documentation in sync with the "Password" AD_Column';

-- 2018-10-02T06:58:04.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET TechnicalNote='The system can work with cleartext passwords and passwords that were hashed using the DB-function public.hash_column_value_if_needed

(pls keep this documentation in sync with the "Password" AD_Column',Updated=TO_TIMESTAMP('2018-10-02 06:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=417
;

