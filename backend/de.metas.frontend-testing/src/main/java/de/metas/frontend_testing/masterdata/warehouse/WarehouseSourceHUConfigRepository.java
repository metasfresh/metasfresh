package de.metas.frontend_testing.masterdata.warehouse;

import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.product.ProductCategoryId;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Warehouse_SourceHUConfig;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Persists {@code M_Warehouse_SourceHUConfig} rows of a test warehouse.
 */
class WarehouseSourceHUConfigRepository
{
	void create(@NonNull final WarehouseId warehouseId, @NonNull final ProductCategoryId productCategoryId)
	{
		final I_M_Warehouse_SourceHUConfig config = newInstance(I_M_Warehouse_SourceHUConfig.class);
		config.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId());
		config.setM_Warehouse_ID(warehouseId.getRepoId());
		config.setM_Product_Category_ID(productCategoryId.getRepoId());
		saveRecord(config);
	}
}
