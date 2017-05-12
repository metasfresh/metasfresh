
update c_invoiceLine set M_HU_PI_Item_Product_ID = 101 where M_HU_PI_Item_Product_ID = 100;

update C_OlCand set M_HU_PI_Item_Product_ID = 101 where M_HU_PI_Item_Product_ID = 100;

update c_orderline set M_HU_PI_Item_Product_ID = 101 where M_HU_PI_Item_Product_ID = 100;


update c_order set M_HU_PI_Item_Product_ID = 101 where M_HU_PI_Item_Product_ID = 100;

update m_receiptschedule set M_HU_PI_Item_Product_ID = 101 where M_HU_PI_Item_Product_ID = 100;

 
update m_shipmentschedule set M_HU_PI_Item_Product_ID = 101 where M_HU_PI_Item_Product_ID = 100;


create table backup.M_HU_PI_Item_Product_BKP20171011 as select * from M_HU_PI_Item_Product;


delete from M_HU_PI_Item_Product pip
where exists (select * from M_HU_PI_Item i where i.M_HU_PI_Item_ID=pip.M_HU_PI_Item_ID and i.M_HU_PI_Version_ID=100);
