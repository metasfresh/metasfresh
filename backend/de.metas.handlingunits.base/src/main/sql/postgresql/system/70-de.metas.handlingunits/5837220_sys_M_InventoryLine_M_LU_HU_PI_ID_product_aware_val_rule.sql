-- Inventory line (window Inventur, tab Bestandszählungs Position): the pallet packing instruction dropdown (M_InventoryLine.M_LU_HU_PI_ID)
-- offers only pallet packing instructions that link the line's TU packing instruction through a generic item or through an item for the line's product.
-- New rule = the code of rule 540746 plus that product filter. Rule 540746 stays unchanged, because the order line's pallet dropdown (C_OrderLine.M_LU_HU_PI_ID) uses it too.

-- 2026-11-24T21:20:00Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540814 /*From ID Server*/,'EXISTS (SELECT 1 FROM M_HU_PI_Version lu_piv INNER JOIN M_HU_PI_Item lu_pii ON lu_pii.M_HU_PI_Version_ID = lu_piv.M_HU_PI_Version_ID WHERE lu_piv.M_HU_PI_ID = M_HU_PI.M_HU_PI_ID AND lu_piv.IsActive = ''Y'' AND lu_piv.IsCurrent = ''Y'' AND lu_piv.HU_UnitType = ''LU'' AND lu_pii.IsActive = ''Y'' AND lu_pii.ItemType = ''HU'' AND (lu_pii.M_Product_ID IS NULL OR lu_pii.M_Product_ID = @M_Product_ID/0@) AND lu_pii.included_hu_pi_id IN (SELECT tu_pi.m_HU_PI_ID FROM M_HU_PI_Item_Product tu_piip INNER JOIN M_HU_PI_Item tu_pii ON tu_pii.M_HU_PI_Item_ID = tu_piip.M_HU_PI_Item_ID INNER JOIN M_HU_PI_Version tu_piv ON tu_piv.M_HU_PI_Version_ID = tu_pii.M_HU_PI_Version_ID INNER JOIN M_HU_PI tu_pi ON tu_pi.M_HU_PI_ID = tu_piv.M_HU_PI_ID WHERE tu_piip.M_HU_PI_Item_Product_ID = (CASE WHEN @M_HU_PI_Item_Product_ID/0@ > 0 THEN @M_HU_PI_Item_Product_ID/0@ ELSE 101 END) AND tu_pii.IsActive = ''Y'' AND tu_piv.IsActive = ''Y'' AND tu_piv.IsCurrent = ''Y'' AND tu_piv.HU_UnitType IN (''TU'', ''V'') AND tu_pi.IsActive = ''Y''))',TO_TIMESTAMP('2026-11-24 21:20:00','YYYY-MM-DD HH24:MI:SS'),100,'Like rule 540746, but a pallet item for another product does not count.','de.metas.handlingunits','Y','M_HU_PI LU matching M_HU_PI_Item_Product_ID and M_Product_ID','S',TO_TIMESTAMP('2026-11-24 21:20:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_InventoryLine.M_LU_HU_PI_ID
-- 2026-11-24T21:20:01Z
UPDATE AD_Column SET AD_Val_Rule_ID=540814,Updated=TO_TIMESTAMP('2026-11-24 21:20:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=591167
;
