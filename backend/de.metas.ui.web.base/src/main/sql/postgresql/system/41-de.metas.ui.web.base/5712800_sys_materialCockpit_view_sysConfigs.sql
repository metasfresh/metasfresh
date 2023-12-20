-- 2023-12-07T13:37:29.911Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541668, 'S', TO_TIMESTAMP('2023-12-07 14:37:29', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.ProcurementStatus.IsDisplayed', TO_TIMESTAMP('2023-12-07 14:37:29', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N')
;

-- 2023-12-07T09:59:17.303Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541667, 'S', TO_TIMESTAMP('2023-12-07 10:59:17', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Overwrites "de.metas.ui.web.material.cockpit.field.*.isDisplayed" and hardcoded order(SeqNo).
Input comma seperated fieldnames (available fieldnames: QtyStockEstimateSeqNo_AtDate,productValue,productName,Manufacturer_ID,PackageSize,C_UOM_ID,QtyDemand_SalesOrder_AtDate,QtyDemand_SalesOrder,QtyDemand_DD_Order_AtDate,QtyDemandSum_AtDate,QtySupply_PP_Order_AtDate,QtySupply_PurchaseOrder_AtDate,QtySupply_PurchaseOrder,QtySupply_DD_Order_AtDate,QtySupplySum_AtDate,QtySupplyRequired_AtDate,QtySupplyToSchedule_AtDate,QtyMaterialentnahme_AtDate,ProcurementStatus,HighestPurchasePrice_AtDate,QtyOrdered_PurchaseOrder_AtDate,QtySupply_PurchaseOrder_AtDate,AvailableQty_AtDate,QtyOrdered_SalesOrder_AtDate,RemainingStock_AtDate,PMM_QtyPromised_NextDay)
Can be deactivated with -', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout', TO_TIMESTAMP('2023-12-07 10:59:17', 'YYYY-MM-DD HH24:MI:SS'), 100, '-')
;

-- 2023-12-13T08:01:23.195Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541669, 'S', TO_TIMESTAMP('2023-12-13 08:01:23.018000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, '', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.CurrencyCode', TO_TIMESTAMP('2023-12-13 08:01:23.018000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100,
        'CHF')
;

-- 2023-12-13T08:31:19.417Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541670, 'S', TO_TIMESTAMP('2023-12-13 08:31:19.290000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.IsDisplayed',
        TO_TIMESTAMP('2023-12-13 08:31:19.290000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N')
;

-- 2023-12-13T08:34:03.869Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541671, 'S', TO_TIMESTAMP('2023-12-13 08:34:03.747000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.QtyOrdered_PurchaseOrder_AtDate.IsDisplayed',
        TO_TIMESTAMP('2023-12-13 08:34:03.747000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N')
;

-- 2023-12-13T08:34:41.275Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541672, 'S', TO_TIMESTAMP('2023-12-13 08:34:41.149000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.AvailableQty_AtDate.IsDisplayed',
        TO_TIMESTAMP('2023-12-13 08:34:41.149000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N')
;

-- 2023-12-13T08:35:07.591Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541673, 'S', TO_TIMESTAMP('2023-12-13 08:35:07.463000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.QtyOrdered_SalesOrder_AtDate.IsDisplayed',
        TO_TIMESTAMP('2023-12-13 08:35:07.463000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N')
;

-- 2023-12-13T08:37:45.327Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541674, 'S', TO_TIMESTAMP('2023-12-13 08:37:45.222000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.RemainingStock_AtDate.IsDisplayed',
        TO_TIMESTAMP('2023-12-13 08:37:45.222000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N')
;

-- 2023-12-13T08:38:36.148Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541675, 'S', TO_TIMESTAMP('2023-12-13 08:38:36.034000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.PMM_QtyPromised_NextDay.IsDisplayed',
        TO_TIMESTAMP('2023-12-13 08:38:36.034000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'N')
;

-- 2023-12-13T08:38:55.080Z
UPDATE AD_SysConfig
SET ConfigurationLevel='S', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:38:55.078000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541223
;

-- 2023-12-13T08:38:56.318Z
UPDATE AD_SysConfig
SET ConfigurationLevel='S', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:38:56.316000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541222
;

-- 2023-12-13T08:39:01.285Z
UPDATE AD_SysConfig
SET ConfigurationLevel='S', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:01.283000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541226
;

-- 2023-12-13T08:39:02.465Z
UPDATE AD_SysConfig
SET ConfigurationLevel='S', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:02.463000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541242
;

-- 2023-12-13T08:39:03.534Z
UPDATE AD_SysConfig
SET ConfigurationLevel='S', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:03.531000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541227
;

-- 2023-12-13T08:39:04.460Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:04.458000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541402
;

-- 2023-12-13T08:39:05.428Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:05.426000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541401
;

-- 2023-12-13T08:39:06.937Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:06.935000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541400
;

-- 2023-12-13T08:39:08.367Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:08.365000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541403
;

-- 2023-12-13T08:39:11.355Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:11.353000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541417
;

-- 2023-12-13T08:39:12.332Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:12.330000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541414
;

-- 2023-12-13T08:39:13.196Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:13.194000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541415
;

-- 2023-12-13T08:39:14.496Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:14.494000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541221
;

-- 2023-12-13T08:39:17.303Z
UPDATE AD_SysConfig
SET ConfigurationLevel='S', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:17.302000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541225
;

-- 2023-12-13T08:39:18.383Z
UPDATE AD_SysConfig
SET ConfigurationLevel='S', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:18.380000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541220
;

-- 2023-12-13T08:39:19.767Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:19.765000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541416
;

-- 2023-12-13T08:39:20.790Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:20.788000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541412
;

-- 2023-12-13T08:39:21.657Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:21.655000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541425
;

-- 2023-12-13T08:39:22.479Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:22.476000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541413
;

-- 2023-12-13T08:39:23.856Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:23.854000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541406
;

-- 2023-12-13T08:39:24.867Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:24.864000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541404
;

-- 2023-12-13T08:39:26.315Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:26.308000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541405
;

-- 2023-12-13T08:39:36.297Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:36.294000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541408
;

-- 2023-12-13T08:39:39.112Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:39.110000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541407
;

-- 2023-12-13T08:39:45.271Z
UPDATE AD_SysConfig
SET ConfigurationLevel='O', Description='Can be overwritten by "de.metas.ui.web.material.cockpit.MaterialCockpitViewFactory.layout"', Updated=TO_TIMESTAMP('2023-12-13 08:39:45.270000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_SysConfig_ID = 541409
;

-- 2023-12-13T09:53:12.151Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541676, 'O', TO_TIMESTAMP('2023-12-13 09:53:12.043000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Also add quantities of components of the sold product', 'de.metas.material.cockpit', 'Y', 'de.metas.ui.web.material.cockpit.field.QtyOrdered_SalesOrder_AtDate.BOMSupport',
        TO_TIMESTAMP('2023-12-13 09:53:12.043000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp WITHOUT TIME ZONE AT TIME ZONE 'UTC', 100, 'Y')
;

