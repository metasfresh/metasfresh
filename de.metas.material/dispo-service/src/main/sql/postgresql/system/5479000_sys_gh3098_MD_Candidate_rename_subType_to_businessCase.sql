-- 2017-11-30T17:30:18.603
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='MD_Candidate_BusinessCase',Updated=TO_TIMESTAMP('2017-11-30 17:30:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540709
;

-- 2017-11-30T17:30:49.370
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='MD_Candidate_BusinessCase', Name='Geschäftsvorfall', PrintName='Geschäftsvorfall',Updated=TO_TIMESTAMP('2017-11-30 17:30:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543312
;

-- 2017-11-30T17:30:49.372
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MD_Candidate_BusinessCase', Name='Geschäftsvorfall', Description=NULL, Help=NULL WHERE AD_Element_ID=543312
;

-- 2017-11-30T17:30:49.385
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MD_Candidate_BusinessCase', Name='Geschäftsvorfall', Description=NULL, Help=NULL, AD_Element_ID=543312 WHERE UPPER(ColumnName)='MD_CANDIDATE_BUSINESSCASE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-11-30T17:30:49.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MD_Candidate_BusinessCase', Name='Geschäftsvorfall', Description=NULL, Help=NULL WHERE AD_Element_ID=543312 AND IsCentrallyMaintained='Y'
;

-- 2017-11-30T17:30:49.388
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschäftsvorfall', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543312) AND IsCentrallyMaintained='Y'
;

-- 2017-11-30T17:30:49.403
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geschäftsvorfall', Name='Geschäftsvorfall' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543312)
;

ALTER TABLE public.md_candidate RENAME md_candidate_subtype TO MD_Candidate_BusinessCase;
