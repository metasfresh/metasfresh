UPDATE M_Locator
SET C_BPartner_Location_ID = (SELECT C_BPartner_Location_ID FROM M_Warehouse WHERE M_Locator.m_warehouse_id = M_Warehouse.m_warehouse_id), Updated=TO_TIMESTAMP('2023-08-09 20:01:24.597', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE C_BPartner_Location_ID IS NULL;

UPDATE C_Order
SET M_Locator_ID = (SELECT M_Locator_ID FROM M_Locator WHERE M_Locator.m_warehouse_id = C_Order.m_warehouse_id AND IsDefault='Y'), Updated=TO_TIMESTAMP('2023-08-09 20:01:24.597', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE M_Locator_ID IS NULL;
