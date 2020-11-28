-- 2017-04-29T18:26:01.666
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=545464, IsDocNoControlled='Y',Updated=TO_TIMESTAMP('2017-04-29 18:26:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 
WHERE C_DocType_ID=540941 
AND AD_Client_ID=1000000 AND 'metasfresh' = (select name from AD_Client where AD_Client_ID=1000000)
;

