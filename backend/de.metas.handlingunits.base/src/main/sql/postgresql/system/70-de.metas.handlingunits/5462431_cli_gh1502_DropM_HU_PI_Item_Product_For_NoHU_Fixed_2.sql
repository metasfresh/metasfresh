drop table if exists PIP_ToDelete;
create temporary table PIP_ToDelete as
select *
from M_HU_PI_Item_Product pip
where exists (select * from M_HU_PI_Item i where i.M_HU_PI_Item_ID=pip.M_HU_PI_Item_ID and i.M_HU_PI_Version_ID=100);


UPDATE M_ProductPrice SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE C_Order SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE C_OrderLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
--
UPDATE M_InOutLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_InOutLine SET  M_HU_PI_Item_Product_Override_ID = 101 WHERE M_HU_PI_Item_Product_Override_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_InOutLine SET  M_HU_PI_Item_Product_Calculated_ID = 101 WHERE M_HU_PI_Item_Product_Calculated_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
--
UPDATE M_InventoryLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE C_InvoiceLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE DD_OrderLine SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
--
UPDATE M_ShipmentSchedule SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_ShipmentSchedule SET  M_HU_PI_Item_Product_Override_ID = 101 WHERE M_HU_PI_Item_Product_Override_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_ShipmentSchedule SET  M_HU_PI_Item_Product_Calculated_ID = 101 WHERE M_HU_PI_Item_Product_Calculated_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
--
UPDATE C_OLCand SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_HU SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_ProductPrice_Attribute SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_ReceiptSchedule SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
-- UPDATE EDI_M_HU_PI_Item_Product_Lookup_UPC_v SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_HU_LUTU_Configuration SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE C_Invoice_Detail SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE M_HU_Snapshot SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE PMM_PurchaseCandidate SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE PMM_QtyReport_Event SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE PMM_Product SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE PMM_WeekReport_Event SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE PMM_Week SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);
UPDATE PMM_Balance SET  M_HU_PI_Item_Product_ID = 101 WHERE M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);




delete from M_HU_PI_Item_Product pip where M_HU_PI_Item_Product_ID in (select M_HU_PI_Item_Product_ID from PIP_ToDelete);



