
-- 07.12.2015 15:42
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(
case when ''@ValidFrom/0001-01-01@''=''0001-01-01''
then true
when (M_QualityInsp_LagerKonf_Version.ValidFrom::Date>=''@ValidFrom/0001-01-01@''::Date)
then true
else false
end)
and
(
case when ''@ValidTo/0001-01-01@''=''0001-01-01''
then true
when (M_QualityInsp_LagerKonf_Version.ValidTo::Date>=''@ValidTo/0001-01-01@''::Date)
then true
else false
end)',Updated=TO_TIMESTAMP('2015-12-07 15:42:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540313
;

