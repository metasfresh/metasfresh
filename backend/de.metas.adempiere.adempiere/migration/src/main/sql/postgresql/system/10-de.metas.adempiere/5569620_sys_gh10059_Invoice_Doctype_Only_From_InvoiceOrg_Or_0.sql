-- 2020-10-05T10:38:45.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType IN (''ARC'') AND IsSOTrx=''Y'' AND AD_Org_ID = @AD_Org_ID@',Updated=TO_TIMESTAMP('2020-10-05 13:38:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540218
;

-- 2020-10-05T10:39:20.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType IN (''ARC'') AND IsSOTrx=''Y'' AND AD_Org_ID IN ( @AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2020-10-05 13:39:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540218
;

-- 2020-10-05T10:40:20.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType IN (''ARC'') AND IsSOTrx=''Y'' AND C_DocType.AD_Org_ID IN ( @AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2020-10-05 13:40:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540218
;

-- 2020-10-05T10:45:00.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(	C_DocType.DocBaseType IN (''API'',''APC'')
	OR (C_DocType.DocBaseType IN (''ARC'',''ARI'') AND C_DocType.DocSubType IS NULL) /*only the default types*/
	OR (C_DocType.DocBaseType=''ARC'' AND C_DocType.DocSubType = ''CS'') /*only the RMA-credit-memo*/

) 
AND C_DocType.IsSOTrx=''@IsSOTrx@''
AND C_DocType.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2020-10-05 13:45:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540294
;






-- 2020-10-05T11:07:32.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType IN (''ARI'', ''API'',''ARC'',''APC'')
AND C_DocType.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2020-10-05 14:07:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540304
;

