-- 2019-09-11T12:04:36.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540450,TO_TIMESTAMP('2019-09-11 15:04:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Pricelist_Version_Base_ID same country and currency','S',TO_TIMESTAMP('2019-09-11 15:04:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-11T12:04:45.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists(select 1 from M_PriceList_Version baseplv
join M_PriceList basepl on baseplv.M_PriceList_ID= pl.M_PriceList_ID
join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList/-1@
where basepl.C_Country_ID = pl.C_Country_ID
and
basepl.C_Currency_ID = pl.C_Currency_ID)',Updated=TO_TIMESTAMP('2019-09-11 15:04:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

-- 2019-09-11T12:05:05.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540450,Updated=TO_TIMESTAMP('2019-09-11 15:05:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4706
;

-- 2019-09-11T12:06:42.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists(select 1 from M_PriceList_Version baseplv
join M_PriceList basepl on baseplv.M_PriceList_ID= basepl.M_PriceList_ID
join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList_ID/-1@
where basepl.C_Country_ID = pl.C_Country_ID
and
basepl.C_Currency_ID = pl.C_Currency_ID)',Updated=TO_TIMESTAMP('2019-09-11 15:06:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

-- 2019-09-11T12:11:14.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(select 1 from M_PriceList_Version baseplv
join M_PriceList basepl on baseplv.M_PriceList_ID= basepl.M_PriceList_ID
join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList_ID/-1@
where basepl.C_Country_ID = pl.C_Country_ID
and
basepl.C_Currency_ID = pl.C_Currency_ID
and
baseplv.M_PriceList_Version_ID = M_PriceList_Version.M_PriceList_Version_ID)',Updated=TO_TIMESTAMP('2019-09-11 15:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

-- 2019-09-11T12:13:15.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='(select 1 from M_PriceList_Version baseplv
join M_PriceList basepl on baseplv.M_PriceList_ID= basepl.M_PriceList_ID
join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList_ID/-1@
where basepl.C_Country_ID = pl.C_Country_ID
and
basepl.C_Currency_ID = pl.C_Currency_ID
)',Updated=TO_TIMESTAMP('2019-09-11 15:13:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

-- 2019-09-11T12:15:08.652Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists (select 1 from M_PriceList_Version baseplv
join M_PriceList basepl on baseplv.M_PriceList_ID= basepl.M_PriceList_ID
join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList_ID/-1@
where basepl.C_Country_ID = pl.C_Country_ID
and
basepl.C_Currency_ID = pl.C_Currency_ID
)',Updated=TO_TIMESTAMP('2019-09-11 15:15:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

-- 2019-09-11T12:26:16.691Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='existsss (select 1 from M_PriceList_Version baseplv
join M_PriceList basepl on baseplv.M_PriceList_ID= basepl.M_PriceList_ID
join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList_ID/-1@
where basepl.C_Country_ID = pl.C_Country_ID
and
basepl.C_Currency_ID = pl.C_Currency_ID
)',Updated=TO_TIMESTAMP('2019-09-11 15:26:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

-- 2019-09-11T12:27:05.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='exists (select 1 from M_PriceList_Version baseplv
join M_PriceList basepl on baseplv.M_PriceList_ID= basepl.M_PriceList_ID
join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList_ID/-1@
where basepl.C_Country_ID = pl.C_Country_ID
and
basepl.C_Currency_ID = pl.C_Currency_ID
)',Updated=TO_TIMESTAMP('2019-09-11 15:27:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

-- 2019-09-11T12:29:38.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_Pricelist_Version_ID in (select M_Pricelist_Version_ID from M_PriceList_Version baseplv
join M_PriceList basepl on baseplv.M_PriceList_ID= basepl.M_PriceList_ID
join M_PriceList pl on pl.M_PriceList_ID = @M_PriceList_ID/-1@
where basepl.C_Country_ID = pl.C_Country_ID
and
basepl.C_Currency_ID = pl.C_Currency_ID
)',Updated=TO_TIMESTAMP('2019-09-11 15:29:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540450
;

