-- 2020-10-05T13:04:37.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.isActive = ''Y'' AND C_DocType.DocBaseType IN (''SOO'', ''POO'') AND C_DocType.IsSOTrx=''@IsSOTrx@'' AND COALESCE(C_DocType.DocSubType,'' '')<>''RM''
AND C_DocType.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2020-10-05 16:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=133
;

