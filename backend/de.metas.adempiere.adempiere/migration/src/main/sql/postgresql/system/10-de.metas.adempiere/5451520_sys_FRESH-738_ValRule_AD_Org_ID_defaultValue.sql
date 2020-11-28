-- Oct 4, 2016 9:06 PM
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(
	/* Case: support the case when AD_Org_ID is not yet defined in context (e.g. Info window which pops when u open a window with high volume) */
	@AD_Org_ID/-1@ < 0
	OR C_BPartner.AD_Org_ID IN (@AD_Org_ID/-1@, 0)
)
',Updated=TO_TIMESTAMP('2016-10-04 21:06:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540245
;

