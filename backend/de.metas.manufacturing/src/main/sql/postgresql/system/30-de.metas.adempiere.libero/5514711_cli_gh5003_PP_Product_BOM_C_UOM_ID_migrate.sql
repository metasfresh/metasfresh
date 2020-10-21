create table backup.PP_Product_BOM_BKP20190304_gh5003 as select * from PP_Product_BOM;

update PP_Product_BOM bom set C_UOM_ID=p.C_UOM_ID
FROM M_Product p
where bom.M_Product_ID=p.M_Product_ID
and bom.C_UOM_ID is null;

