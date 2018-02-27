-- 2017-11-20T19:20:25.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540376,'',TO_TIMESTAMP('2017-11-20 19:20:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Product which have no freight and respect the AD_org of th','C',TO_TIMESTAMP('2017-11-20 19:20:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-20T19:20:41.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule_Included (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,AD_Val_Rule_Included_ID,Created,CreatedBy,EntityType,Included_Val_Rule_ID,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540376,540027,TO_TIMESTAMP('2017-11-20 19:20:41','YYYY-MM-DD HH24:MI:SS'),100,'D',540051,'Y',10,TO_TIMESTAMP('2017-11-20 19:20:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-20T19:22:43.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540377,'(
	/* Case: support the case when AD_Org_ID is not yet defined in context (e.g. Info window which pops when u open a window with high volume) */
	@AD_Org_ID/-1@ < 0
	OR M_Product.AD_Org_ID IN (@AD_Org_ID/-1@, 0)
)',TO_TIMESTAMP('2017-11-20 19:22:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Product_of_@AD_Org_ID@_or_0','S',TO_TIMESTAMP('2017-11-20 19:22:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-20T19:22:52.126
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule_Included (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,AD_Val_Rule_Included_ID,Created,CreatedBy,EntityType,Included_Val_Rule_ID,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540376,540028,TO_TIMESTAMP('2017-11-20 19:22:52','YYYY-MM-DD HH24:MI:SS'),100,'D',540377,'Y',20,TO_TIMESTAMP('2017-11-20 19:22:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-20T19:27:20.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='M_Product which have no freight and order''sOrg',Updated=TO_TIMESTAMP('2017-11-20 19:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540376
;

-- 2017-11-20T19:27:41.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540376,Updated=TO_TIMESTAMP('2017-11-20 19:27:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2221
;

