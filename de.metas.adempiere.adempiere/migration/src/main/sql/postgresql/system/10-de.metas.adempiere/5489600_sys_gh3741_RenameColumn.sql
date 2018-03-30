-- 2018-03-28T15:25:02.627
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1047, ColumnName='Processed', Description='Checkbox sagt aus, ob der Beleg verarbeitet wurde. ', Help='Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.', Name='Verarbeitet',Updated=TO_TIMESTAMP('2018-03-28 15:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559089
;

-- 2018-03-28T15:25:02.630
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Verarbeitet', Description='Checkbox sagt aus, ob der Beleg verarbeitet wurde. ', Help='Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.' WHERE AD_Column_ID=559089
;

-- 2018-03-28T15:25:24.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Name='Freigegeben',Updated=TO_TIMESTAMP('2018-03-28 15:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559089
;

-- 2018-03-28T15:25:24.169
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Freigegeben', Description='Checkbox sagt aus, ob der Beleg verarbeitet wurde. ', Help='Verarbeitete Belege dürfen in der Regel nich mehr geändert werden.' WHERE AD_Column_ID=559089
;



-- 2018-03-28T15:35:24.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_CreditLimit','ALTER TABLE public.C_BPartner_CreditLimit ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;


UPDATE C_BPartner_CreditLimit SET Processed = IsApproved;

COMMIT;

/* DDL */ SELECT public.db_alter_table('C_BPartner_CreditLimit','ALTER TABLE public.C_BPartner_CreditLimit DROP COLUMN IsApproved');


