
/*
-- For customers that already use the material cockpit and might have different settings,
-- you can save them like this:
INSERT INTO public.ad_sysconfig (ad_sysconfig_id, ad_client_id, ad_org_id, created, updated, createdby, updatedby, isactive, name, value, description, entitytype, configurationlevel)
SELECT NEXTVAL('ad_sysconfig_seq') as ad_sysconfig_id,
       1000000 AS ad_client_id, /*create them on client-level*/
       0       AS ad_org_id,
       created,
       updated,
       createdby,
       updatedby,
       isactive,
       name,
       value,
       description,
       entitytype,
       configurationlevel
FROM AD_SysConfig
WHERE Name ILIKE 'de.metas.ui.web.material.cockpit.field%IsDisplayed';
*/

create table backup.ad_Sysconfig_20211210_1 as select * from ad_Sysconfig;
--update ad_sysconfig set Value=bkp.value from backup.ad_Sysconfig_20211210_1 bkp where bkp.ad_sysconfig_id=ad_sysconfig.ad_sysconfig_id;

UPDATE AD_Sysconfig
SET Value='N'
WHERE AD_Client_ID = 0 -- override the settings on system-level and leave possible settings on client-level untouched
  AND Name ILIKE 'de.metas.ui.web.material.cockpit.field.%.IsDisplayed'
  AND Name NOT IN (
                   'de.metas.ui.web.material.cockpit.field.C_UOM_ID.IsDisplayed',
    --    'de.metas.ui.web.material.cockpit.field.Manufacturer_ID.IsDisplayed,N
    --                                                         de.metas.ui.web.material.cockpit.field.PackageSize.IsDisplayed,N
    --                                                         de.metas.ui.web.material.cockpit.field.QtyAvailableToPromiseEstimate.IsDisplayed,N
    --                                                         de.metas.ui.web.material.cockpit.field.QtyDemandSum.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.QtyDemand_DD_Order.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.QtyDemand_PP_Order.IsDisplayed,Y
                   'de.metas.ui.web.material.cockpit.field.QtyDemand_SalesOrder.IsDisplayed',
    --                                                         de.metas.ui.web.material.cockpit.field.QtyExpectedSurplus.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.QtyInventoryCount.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.QtyInventoryTime.IsDisplayed,N
    --                                                         de.metas.ui.web.material.cockpit.field.QtyMaterialentnahme.IsDisplayed,N
                   'de.metas.ui.web.material.cockpit.field.QtyStockCurrent.IsDisplayed'
                       'de.metas.ui.web.material.cockpit.field.QtyStockEstimateCount.IsDisplayed',
    --                                                         'de.metas.ui.web.material.cockpit.field.QtyStockEstimateTime.IsDisplayed'
    --                                                         de.metas.ui.web.material.cockpit.field.QtySupplyRequired.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.QtySupplySum.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.QtySupplyToSchedule.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.QtySupply_DD_Order.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.QtySupply_PP_Order.IsDisplayed,Y
                   'de.metas.ui.web.material.cockpit.field.QtySupply_PurchaseOrder.IsDisplayed'
    --                                                         de.metas.ui.web.material.cockpit.field.pmmQtyPromised.IsDisplayed,N
    --                                                         de.metas.ui.web.material.cockpit.field.productCategoryOrSubRowName.IsDisplayed,Y
    --                                                         de.metas.ui.web.material.cockpit.field.qtyRequiredForProduction.IsDisplayed,Y
    );
