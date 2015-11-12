DROP VIEW IF EXISTS X_M_HU_Storage_HUInfo_V;

CREATE OR REPLACE VIEW x_m_hu_storage_huinfo_v AS 
SELECT 
	hus.ad_client_id, hus.ad_org_id, hus.isactive, hus.m_product_id, 
	hu.m_locator_id, generateHUStorageHUKey(hu.m_hu_id) AS hustoragehukey, 
	hupiip.Name AS M_HU_PI_Item_Product,
	hu.m_hu_id, hu.hustatus

	, (CASE
		WHEN 
			-- Exclude HU Storages if the warehouse has IsHUStorageDisabled
			wh.IsHUStorageDisabled<>'Y'
			THEN hus.qty
		ELSE 0
	END) AS hustorageqty
	, (CASE
		WHEN hu.M_HU_Item_Parent_ID IS NULL -- only top levels
			and TRUNC(hus.Updated ,'DD') = TRUNC(now()::DATE, 'DD')
			and wh.M_Warehouse_ID=(
				select COALESCE(value::integer, -1) from AD_SysConfig where Name='de.metas.handlingunits.client.terminal.inventory.model.InventoryHUEditorModel#DirectMove_Warehouse_ID'
				and IsActive='Y'
				order by AD_Client_ID desc, AD_Org_ID desc
				limit 1)
			THEN hus.qty
		ELSE 0
	END) AS QtyMaterialentnahme

FROM (SELECT DISTINCT M_HU_ID FROM X_M_HU_Storage_HuInfo_MV_Recompute) r -- task 08777: start with the recompute table. That way we need to consider much less M_HUs right from the start. this is a HUGE perf bonus
	JOIN M_HU hu ON hu.m_hu_id = r.M_HU_ID AND hu.hustatus IN ('A', 'E')  -- Only active and shipped
    JOIN M_HU_PI_Version piv on (piv.M_HU_PI_Version_ID=hu.M_HU_PI_Version_ID AND piv.HU_UnitType='V') -- we only look for VHU: they are allways there (unlike TUs and LUs) and have the most "atomic" and correct qty.
	JOIN M_HU_PI_Item_Product hupiip ON hupiip.M_HU_PI_Item_Product_ID = hu.M_HU_PI_Item_Product_ID
	JOIN m_hu_storage hus ON (hus.m_hu_id = hu.m_hu_id AND hus.qty <> 0 AND hus.IsActive='Y')
	LEFT JOIN ( 
	SELECT ip.m_product_id, ip.c_bpartner_id, COALESCE(bool_or(ip.haspartner), false) AS hasspecificbp
	FROM ( 
		SELECT valid_pi_item_product_v.m_product_id, valid_pi_item_product_v.c_bpartner_id, valid_pi_item_product_v.isinfinitecapacity, valid_pi_item_product_v.haspartner, valid_pi_item_product_v.m_hu_pi_item_product_id, valid_pi_item_product_v.m_hu_pi_item_id, valid_pi_item_product_v.name
		FROM report.valid_pi_item_product_v) ip
	GROUP BY ip.m_product_id, ip.c_bpartner_id
	) spi 
	ON hu.c_bpartner_id = spi.c_bpartner_id AND hus.m_product_id = spi.m_product_id
	--
	INNER JOIN M_Locator loc ON (loc.M_Locator_ID=hu.M_Locator_ID)
	INNER JOIN M_Warehouse wh ON (wh.M_Warehouse_ID=loc.M_Warehouse_ID)
WHERE
	-- Exclude HU Storages if the warehouse has IsHUStorageDisabled
	wh.IsHUStorageDisabled<>'Y'
;

