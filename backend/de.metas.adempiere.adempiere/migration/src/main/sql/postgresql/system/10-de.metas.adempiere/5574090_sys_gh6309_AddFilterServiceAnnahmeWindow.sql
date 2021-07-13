-- 2020-12-04T14:29:51.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET WhereClause='M_InOut.MovementType IN (''C+'') and exists(select 1 from c_doctype dt where M_InOut.c_doctype_id = dt.c_doctype_id and dt.docsubtype = ''SR'')',Updated=TO_TIMESTAMP('2020-12-04 16:29:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543272
;