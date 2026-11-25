package org.adempiere.warehouse;

import de.metas.product.ProductCategoryId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.QueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_M_Warehouse_SourceHUConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseRepositoryTest
{
	private static final ProductCategoryId CATEGORY_1 = ProductCategoryId.ofRepoId(101);
	private static final ProductCategoryId CATEGORY_2 = ProductCategoryId.ofRepoId(102);

	private int sourceHUConfigQueryCount;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		sourceHUConfigQueryCount = 0;

		Services.registerService(IQueryBL.class, new QueryBL()
		{
			@Override
			public <T> IQueryBuilder<T> createQueryBuilder(final Class<T> modelClass, final Properties ctx, @Nullable final String trxName)
			{
				if (I_M_Warehouse_SourceHUConfig.class.equals(modelClass))
				{
					sourceHUConfigQueryCount++;
				}
				return super.createQueryBuilder(modelClass, ctx, trxName);
			}
		});
	}

	private WarehouseId createWarehouse(final String name)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
		warehouse.setName(name);
		warehouse.setIsReceiveAsSourceHU(true);
		InterfaceWrapperHelper.saveRecord(warehouse);
		return WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID());
	}

	private void createSourceHUConfig(final WarehouseId warehouseId, final ProductCategoryId productCategoryId)
	{
		final I_M_Warehouse_SourceHUConfig config = InterfaceWrapperHelper.newInstance(I_M_Warehouse_SourceHUConfig.class);
		config.setM_Warehouse_ID(warehouseId.getRepoId());
		config.setM_Product_Category_ID(productCategoryId.getRepoId());
		InterfaceWrapperHelper.saveRecord(config);
	}

	@Test
	void loadingTheWarehouseMap_loadsSourceHUConfigsWithASingleQuery_andEachWarehouseGetsItsOwnConfigs()
	{
		final WarehouseId warehouse1 = createWarehouse("wh1");
		final WarehouseId warehouse2 = createWarehouse("wh2");
		final WarehouseId warehouse3NoConfigs = createWarehouse("wh3");
		createSourceHUConfig(warehouse1, CATEGORY_1);
		createSourceHUConfig(warehouse2, CATEGORY_2);

		final WarehouseRepository repository = new WarehouseRepository();

		final Warehouse wh1 = repository.getById(warehouse1);
		final Warehouse wh2 = repository.getById(warehouse2);
		final Warehouse wh3 = repository.getById(warehouse3NoConfigs);

		assertThat(sourceHUConfigQueryCount).as("queries on M_Warehouse_SourceHUConfig for one cache load").isEqualTo(1);

		assertThat(wh1.isConfiguredToReceiveAsSourceHU(CATEGORY_1)).isTrue();
		assertThat(wh1.isConfiguredToReceiveAsSourceHU(CATEGORY_2)).isFalse();
		assertThat(wh2.isConfiguredToReceiveAsSourceHU(CATEGORY_1)).isFalse();
		assertThat(wh2.isConfiguredToReceiveAsSourceHU(CATEGORY_2)).isTrue();
		// no configs => applies to every product category
		assertThat(wh3.isConfiguredToReceiveAsSourceHU(CATEGORY_1)).isTrue();
		assertThat(wh3.isConfiguredToReceiveAsSourceHU(CATEGORY_2)).isTrue();
	}
}
