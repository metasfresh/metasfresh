UPDATE M_PickingSlot ps
SET M_Locator_ID = x.M_Locator_ID
FROM
	(
		select l.M_Locator_ID, l.M_Warehouse_ID
		from M_Locator l
		where l.IsAfterPickingLocator = 'Y'
		and l.IsActive = 'Y' 
	) x
WHERE x.M_Warehouse_ID = ps.M_Warehouse_ID and x.M_Warehouse_ID <> ps.M_Warehouse_ID;