-- See 5462431_cli_gh1502_DropM_HU_PI_Item_Product_For_NoHU_Fixed_2.sql
/*
UPDATE M_ProductPrice SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE C_Order SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE C_OrderLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE M_InOutLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE M_InventoryLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE C_InvoiceLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE DD_OrderLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE M_ShipmentSchedule SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE C_OLCand SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
-- UPDATE M_HU_PI_Item_Product SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE M_HU SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE M_ProductPrice_Attribute SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE M_ReceiptSchedule SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
-- UPDATE EDI_M_HU_PI_Item_Product_Lookup_UPC_v SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE M_HU_LUTU_Configuration SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE C_Invoice_Detail SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE M_HU_Snapshot SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE PMM_PurchaseCandidate SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE PMM_QtyReport_Event SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE PMM_Product SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE PMM_WeekReport_Event SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE PMM_Week SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;
UPDATE PMM_Balance SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID = 100;




delete from M_HU_PI_Item_Product pip
where exists (select * from M_HU_PI_Item i where i.M_HU_PI_Item_ID=pip.M_HU_PI_Item_ID and i.M_HU_PI_Version_ID=100);
*/