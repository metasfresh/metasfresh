
UPDATE M_PickingSlot ps
SET M_Locator_ID = ( select l.M_Locator_ID from M_Locator l 
		where l.IsAfterPickingLocator = 'Y'
		and l.IsActive = 'Y' and l.M_Warehouse_ID = ps.M_Warehouse_ID
		limit 1
	) ;
	