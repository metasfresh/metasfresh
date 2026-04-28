-- 2019-04-25T14:16:01.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=NULL, IsDocNoControlled='N',Updated=TO_TIMESTAMP('2019-04-25 14:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540972
;


delete from AD_Sequence where AD_Sequence_ID=555008;