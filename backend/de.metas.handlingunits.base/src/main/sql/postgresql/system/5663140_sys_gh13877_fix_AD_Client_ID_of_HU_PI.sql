
/*
select * from M_HU_PI_Version WHERE M_HU_PI_Version_ID IN (100, 101) AND AD_Client_ID=0;
select * from M_HU_PI_Attribute WHERE M_HU_PI_Version_ID IN (100, 101) AND AD_Client_ID=0;
select * from M_HU_PI_Item WHERE M_HU_PI_Version_ID IN (100, 101) AND AD_Client_ID=0;
select * from M_HU_PI_Item_Product WHERE M_HU_PI_Item_Product_ID =101 AND AD_Client_ID=0;
*/

CREATE TABLE backup.M_HU_PI_Version_Fix_AD_Client_ID_20221104 AS SELECT * FROM M_HU_PI_Version;
CREATE TABLE backup.M_HU_PI_Attribute_Fix_AD_Client_ID_20221104 AS SELECT * FROM M_HU_PI_Attribute;
CREATE TABLE backup.M_HU_PI_Item_Fix_AD_Client_ID_20221104 AS SELECT * FROM M_HU_PI_Item;
CREATE TABLE backup.M_HU_PI_Item_Product_Fix_AD_Client_ID_20221104 AS SELECT * FROM M_HU_PI_Item_Product;

UPDATE M_HU_PI_Version SET UpdatedBy=99, Updated='2022-11-04 06:01', AD_Client_ID=1000000 WHERE M_HU_PI_Version_ID IN (100, 101) AND AD_Client_ID=0;
UPDATE M_HU_PI_Attribute SET UpdatedBy=99, Updated='2022-11-04 06:01', AD_Client_ID=1000000 WHERE M_HU_PI_Version_ID IN (100, 101) AND AD_Client_ID=0;
UPDATE M_HU_PI_Item SET UpdatedBy=99, Updated='2022-11-04 06:01', AD_Client_ID=1000000 WHERE M_HU_PI_Version_ID IN (100, 101) AND AD_Client_ID=0;
UPDATE M_HU_PI_Item_Product SET UpdatedBy=99, Updated='2022-11-04 06:01', AD_Client_ID=1000000 WHERE M_HU_PI_Item_Product_ID=101 AND AD_Client_ID=0;


