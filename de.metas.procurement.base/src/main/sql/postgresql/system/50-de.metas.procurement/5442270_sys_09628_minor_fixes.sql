--
-- C_Flatrate_Term.C_UOM_ID
--
-- 11.03.2016 12:38
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2016-03-11 12:38:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=546003
;

--
-- validation rule for C_Flatrate_Term_Create_ProcurementContract
--
-- 11.03.2016 12:43
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540325,'exists (
select 1 from PMM_Product pmmp 
where pmmp.IsActive=''Y''
	AND pmmp.M_Product_ID=M_Product.M_Product_ID 
	AND pmmp.M_HU_PI_Item_Product_ID IS NOT NULL 
	AND (pmmp.C_BPartner_ID IS NULL OR pmmp.C_BPartner_ID=@C_BPartner_ID@)
)',TO_TIMESTAMP('2016-03-11 12:43:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.procurement','Y','M_Product from PMM_Product','S',TO_TIMESTAMP('2016-03-11 12:43:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.03.2016 12:44
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540325,Updated=TO_TIMESTAMP('2016-03-11 12:44:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540917
;

--
-- fix tab level or producrement-dataentries
--
-- 11.03.2016 12:49
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=2,Updated=TO_TIMESTAMP('2016-03-11 12:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540730
;

