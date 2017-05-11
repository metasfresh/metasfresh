create table backup.M_HU_PI_Item_Product_BKP20171011 as select * from M_HU_PI_Item_Product;


delete from M_HU_PI_Item_Product pip
where exists (select * from M_HU_PI_Item i where i.M_HU_PI_Item_ID=pip.M_HU_PI_Item_ID and i.M_HU_PI_Version_ID=100);
