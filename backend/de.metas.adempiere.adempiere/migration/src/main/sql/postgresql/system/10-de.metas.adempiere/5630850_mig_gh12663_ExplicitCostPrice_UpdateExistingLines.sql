CREATE TABLE backup.BKP_M_InventoryLine_gh12663_18032022
AS
SELECT *
FROM m_inventoryline
;



UPDATE m_inventoryline il
 SET IsExplicitCostPrice = 'Y',
     Updated             = TO_TIMESTAMP('2022-03-18 16:41:56', 'YYYY-MM-DD HH24:MI:SS'),
     UpdatedBy           = 100
 WHERE il.qtybook = il.qtycount
;
