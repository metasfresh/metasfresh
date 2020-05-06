
-- 04.12.2015 15:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(''@ValidFrom/-@''=''-'' or M_QualityInsp_LagerKonf_Version.ValidFrom>=''@ValidFrom/-@'')
and
(''@ValidTo/-@''=''-'' or M_QualityInsp_LagerKonf_Version.ValidTo>=''@ValidTo/-@'')',Updated=TO_TIMESTAMP('2015-12-04 15:38:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540313
;

