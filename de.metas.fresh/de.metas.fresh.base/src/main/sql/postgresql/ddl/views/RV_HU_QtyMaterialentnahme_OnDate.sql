DROP VIEW IF EXISTS "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate;
CREATE OR REPLACE VIEW "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate AS
SELECT 
	COALESCE(SUM(t.MovementQty), 0) AS QtyMaterialentnahme,
	TRUNC(t.MovementDate,'DD') as Updated_Date,
	t.M_Product_ID,
	t.M_AttributeSetInstance_ID
FROM M_Transaction t 
	inner join M_Locator loc on loc.M_Locator_ID=t.M_Locator_ID
		and loc.M_Warehouse_ID=(
			select COALESCE(value::integer, -1) from AD_SysConfig 
			where Name='de.metas.handlingunits.client.terminal.inventory.model.InventoryHUEditorModel#DirectMove_Warehouse_ID'
				and IsActive='Y'
			order by AD_Client_ID desc, AD_Org_ID desc
			limit 1)
GROUP BY TRUNC(t.MovementDate,'DD'), t.M_Product_ID, t.M_AttributeSetInstance_ID
;
COMMENT ON VIEW "de.metas.fresh".RV_HU_QtyMaterialentnahme_OnDate IS 'Task 08476: Despite the name this view is based on M_Transaction, but not that said M_Transactions are created for certain HUs selected by the user. In the user''s context, the whole use-cate/workflow is called "Materialentnahme"';
