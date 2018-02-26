-- 2018-02-26T07:47:32.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540388,'M_HU.IsActive = ''Y'' AND M_HU.HUSatus = ''A'' /* active */
AND COALESCE(M_HU.M_HU_Item_Parent,0)<=0 /* toplevel */
AND M_HU.Locator_ID = @M_Locator_ID@
AND EXISTS (select 1 from M_HU_Storage hus where hus.IsActive = ''Y'' AND hus.M_HU_ID = M_HU.M_HU_ID AND hus.M_Product_ID = @M_Product_ID@)
',TO_TIMESTAMP('2018-02-26 07:47:32','YYYY-MM-DD HH24:MI:SS'),100,'Filters for top level HUs with status "active" and a given @M_Locator_ID@ and @M_Product_ID@','de.metas.handlingunits','Y','M_HU_Toplevel active with @M_Product_ID@ and @M_Locator_ID@','S',TO_TIMESTAMP('2018-02-26 07:47:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-26T07:48:12.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540388,Updated=TO_TIMESTAMP('2018-02-26 07:48:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541259
;

-- 2018-02-26T08:00:17.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU.IsActive = ''Y'' AND M_HU.HUStatus = ''A'' /* active */
AND COALESCE(M_HU.M_HU_Item_Parent_ID, 0) <= 0 /* toplevel */
AND M_HU.M_Locator_ID = @M_Locator_ID@
AND EXISTS (select 1 from M_HU_Storage hus where hus.IsActive = ''Y'' AND hus.M_HU_ID = M_HU.M_HU_ID AND hus.M_Product_ID = @M_Product_ID@)
',Updated=TO_TIMESTAMP('2018-02-26 08:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540388
;

-- 2018-02-26T08:04:09.896
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Name='M_HU_Toplevel active with @M_Product_ID@ and @M_Locator_ID@',Updated=TO_TIMESTAMP('2018-02-26 08:04:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540388
;

-- 2018-02-26T08:04:27.640
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Description='Filters for top level HUs with status "active" and a given @M_Locator_ID@ and @M_Product_ID@',Updated=TO_TIMESTAMP('2018-02-26 08:04:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540388
;


--
-- unrelated: fix SQL error in rule M_HU_Toplevel active
--
-- 2018-02-26T08:01:46.165
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Code='M_HU.HUStatus=''A'' AND COALESCE(M_HU.M_HU_Item_Parent_ID, 0)<=0
', Description='Filters for top level HUs with status "active"',Updated=TO_TIMESTAMP('2018-02-26 08:01:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540363
;

