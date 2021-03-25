
-- 2021-03-23T13:42:01.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='M_InOut.MovementType IN (''C+'') AND /*exclude service-repair*/ exists(select 1 from c_doctype dt where M_InOut.c_doctype_id = dt.c_doctype_id and dt.docsubtype!=''SR'')',Updated=TO_TIMESTAMP('2021-03-23 14:42:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540782
;
