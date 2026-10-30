package de.metas.handlingunits.sourcehu;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.product.ProductId;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_M_Warehouse_SourceHUConfig;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link SourceHUsService#addSourceHUMarkerIfCarryingComponents(java.util.Set, ProductId, WarehouseId)}.
 */
public class SourceHUsServiceTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void addSourceHUMarkerIfCarryingComponents_packingOnlyProduct_marksHU()
	{
		final I_C_UOM uom = BusinessTestHelper.createUOM("ea", 0, 0);
		final I_M_Product product = BusinessTestHelper.createProduct("Foil", uom);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		// the product is referenced only by a Packing BOM line
		final I_PP_Product_BOMLine bomLine = newInstance(I_PP_Product_BOMLine.class);
		bomLine.setComponentType(BOMComponentType.Packing.getCode());
		bomLine.setM_Product_ID(productId.getRepoId());
		bomLine.setC_UOM_ID(uom.getC_UOM_ID());
		bomLine.setQtyBOM(BigDecimal.ONE);
		saveRecord(bomLine);

		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		warehouse.setName("WH");
		warehouse.setIsReceiveAsSourceHU(true);
		saveRecord(warehouse);
		final WarehouseId warehouseId = WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());

		final I_M_Warehouse_SourceHUConfig config = newInstance(I_M_Warehouse_SourceHUConfig.class);
		config.setM_Warehouse_ID(warehouseId.getRepoId());
		config.setM_Product_Category_ID(product.getM_Product_Category_ID());
		saveRecord(config);

		final I_M_HU hu = newInstance(I_M_HU.class);
		saveRecord(hu);
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());

		SourceHUsService.get().addSourceHUMarkerIfCarryingComponents(ImmutableSet.of(huId), productId, warehouseId);

		assertThat(Services.get(IQueryBL.class).createQueryBuilder(I_M_Source_HU.class)
				.addEqualsFilter(I_M_Source_HU.COLUMNNAME_M_HU_ID, huId.getRepoId())
				.create()
				.count()).isEqualTo(1);
	}
}
