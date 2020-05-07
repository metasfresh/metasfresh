-- 2017-09-04T10:13:42.273
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2017-09-04 10:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551641
;

-- 2017-09-04T10:18:20.152
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='N',Updated=TO_TIMESTAMP('2017-09-04 10:18:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552467
;

-- 2017-09-04T10:20:02.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2017-09-04 10:20:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549794
;

-- M_ShipementSchedule M_HU_PI_Item_Product_Override_ID needs  val rule that is about the delivery date
-- it's other M_HU_PI_Item_Product_* columns don'T need any dynamic validation rule because htey are read-only
--
-- M_HU_PI_Item_Product_For_Org_and_Product_and_DeliveryDate_Ef
--
-- 2017-09-04T10:21:32.856
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540368,'(M_HU_PI_Item_Product.M_Product_ID=@M_Product_ID@ OR (M_HU_PI_Item_Product.isAllowAnyProduct=''Y'' AND M_HU_PI_Item_Product.M_HU_PI_Item_Product_ID not in (100)))
AND ( M_HU_PI_Item_Product.C_BPartner_ID = @C_BPartner_ID@ OR M_HU_PI_Item_Product.C_BPartner_ID IS NULL)
AND M_HU_PI_Item_Product.ValidFrom <= ''@DeliveryDate_Effective@'' AND ( ''@DeliveryDate_Effective@'' <= M_HU_PI_Item_Product.ValidTo OR M_HU_PI_Item_Product.ValidTo IS NULL )
AND M_HU_PI_Item_Product.M_HU_PI_Item_ID IN
	( SELECT i.M_HU_PI_Item_ID 
	FROM M_HU_PI_Item i 
	WHERE i.M_HU_PI_Version_ID IN
		(SELECT v.M_HU_PI_Version_ID 
		FROM M_HU_PI_Version v 
		WHERE v.HU_UnitType = ''TU''
			OR v.HU_UnitType = ''V''
		)
	)',TO_TIMESTAMP('2017-09-04 10:21:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','M_HU_PI_Item_Product_For_Product_and_DeliveryDat_withVirtual','S',TO_TIMESTAMP('2017-09-04 10:21:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-09-04T10:33:47.467
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Val_Rule SET Description='Uses the sql-column DeliveryDate_Effective',Updated=TO_TIMESTAMP('2017-09-04 10:33:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540368
;

-- 2017-09-04T10:34:18.886
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540368,Updated=TO_TIMESTAMP('2017-09-04 10:34:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550834
;

-- M_ReceiptsSchedule M_HU_PI_Item_Product_ID doesn need any dynamic val rule because it is read-only
-- 2017-09-04T10:42:27.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2017-09-04 10:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549769
;

-- 2017-09-04T10:44:16.862
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2017-09-04 10:44:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551641
;

