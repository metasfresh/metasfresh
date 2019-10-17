-- 2019-04-08T10:20:56.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DefaultWhereClause='exists (select 1 from C_BPartner bp where bp.C_BPartner_ID = AD_User.C_BPartner_ID and bp.IsCompany=''Y and bp.AD_Client_ID > 0'')',Updated=TO_TIMESTAMP('2019-04-08 10:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

-- 2019-04-08T10:27:00.288
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DefaultWhereClause='exists (select 1 from C_BPartner bp where bp.C_BPartner_ID = AD_User.C_BPartner_ID and bp.IsCompany=''Y'' and bp.AD_Client_ID > 0'')',Updated=TO_TIMESTAMP('2019-04-08 10:27:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

-- 2019-04-08T10:27:59.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DefaultWhereClause='exists (select 1 from C_BPartner bp where bp.C_BPartner_ID = AD_User.C_BPartner_ID and bp.IsCompany=''Y'' and bp.AD_Client_ID > 0)',Updated=TO_TIMESTAMP('2019-04-08 10:27:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

-- 2019-04-08T10:28:54.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DefaultWhereClause='exists (select 1 from C_BPartner bp where bp.C_BPartner_ID = AD_User.C_BPartner_ID and bp.IsCompany=''Y'' and bp.AD_Client_ID > 0) and AD_User.AD_Client_ID > 0',Updated=TO_TIMESTAMP('2019-04-08 10:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

-- 2019-04-08T10:59:54.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DefaultWhereClause='', WhereClause='exists (select 1 from C_BPartner bp where bp.C_BPartner_ID = AD_User.C_BPartner_ID and bp.IsCompany=''Y'' and bp.AD_Client_ID > 0) and AD_User.AD_Client_ID > 0',Updated=TO_TIMESTAMP('2019-04-08 10:59:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

-- 2019-04-08T11:00:33.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DefaultWhereClause='exists (select 1 from C_BPartner bp where bp.C_BPartner_ID = AD_User.C_BPartner_ID and bp.IsCompany=''Y'' and bp.AD_Client_ID > 0) and AD_User.AD_Client_ID > 0',Updated=TO_TIMESTAMP('2019-04-08 11:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;


-- 2019-04-08T15:32:01.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET DefaultWhereClause='',Updated=TO_TIMESTAMP('2019-04-08 15:32:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=541699
;

