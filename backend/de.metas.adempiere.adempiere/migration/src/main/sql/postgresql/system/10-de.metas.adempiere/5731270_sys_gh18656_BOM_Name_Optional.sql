-- UI Element: Stücklistenkonfiguration -> Stückliste.Name
-- Column: PP_Product_BOMVersions.Name
-- UI Element: Stücklistenkonfiguration(541317,EE01) -> Stückliste(544796,EE01) -> main -> 10 -> default.Name
-- Column: PP_Product_BOMVersions.Name
-- 2024-08-13T14:51:11.483Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=594557
;

-- 2024-08-13T14:51:11.499Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=667503
;

-- Field: Stücklistenkonfiguration -> Stückliste -> Name
-- Column: PP_Product_BOMVersions.Name
-- Field: Stücklistenkonfiguration(541317,EE01) -> Stückliste(544796,EE01) -> Name
-- Column: PP_Product_BOMVersions.Name
-- 2024-08-13T14:51:11.511Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=667503
;

-- 2024-08-13T14:51:11.513Z
DELETE FROM AD_Field WHERE AD_Field_ID=667503
;

-- Column: PP_Product_BOMVersions.Name
-- Column: PP_Product_BOMVersions.Name
-- 2024-08-13T14:51:21.379Z
UPDATE AD_Column SET IsIdentifier='N', IsMandatory='N',Updated=TO_TIMESTAMP('2024-08-13 17:51:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=577842
;

-- 2024-08-13T14:51:22.687Z
INSERT INTO t_alter_column values('pp_product_bomversions','Name','VARCHAR(255)',null,null)
;

-- 2024-08-13T14:51:22.695Z
INSERT INTO t_alter_column values('pp_product_bomversions','Name',null,'NULL',null)
;

