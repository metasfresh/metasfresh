package de.metas.frontend_testing.masterdata.warehouse;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.product.ProductCategoryId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Warehouse_SourceHUConfig;
import org.compiere.model.I_M_Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WarehouseCommandTest
{
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
	}

	private WarehouseCommand.WarehouseCommandBuilder commandBuilder(final Identifier identifier, final JsonWarehouseRequest request)
	{
		return WarehouseCommand.builder()
				.context(context)
				.request(request)
				.identifier(identifier);
	}

	@Test
	public void isQualityReturnWarehouse_createsOne_whenNoneExists()
	{
		final JsonWarehouseResponse response = commandBuilder(Identifier.ofString("quality"), JsonWarehouseRequest.builder().isQualityReturnWarehouse(true).build())
				.build()
				.execute();

		final I_M_Warehouse warehouse = InterfaceWrapperHelper.load(response.getWarehouseId(), I_M_Warehouse.class);
		assertThat(warehouse.isQualityReturnWarehouse()).isTrue();
		assertThat(Services.get(IHUWarehouseDAO.class).retrieveFirstQualityReturnWarehouseId())
				.isEqualTo(WarehouseId.ofRepoId(response.getWarehouseId()));
	}

	@Test
	public void isQualityReturnWarehouse_reusesTheExistingOne_insteadOfCreatingASecond()
	{
		final JsonWarehouseResponse first = commandBuilder(Identifier.ofString("quality1"), JsonWarehouseRequest.builder().isQualityReturnWarehouse(true).build())
				.build()
				.execute();

		// A second masterdata request (a fresh context, as a new Playwright test run would send) must
		// resolve to the SAME warehouse the production code (DB-wide "first match") will actually use.
		final MasterdataContext secondContext = new MasterdataContext();
		final JsonWarehouseResponse second = WarehouseCommand.builder()
				.context(secondContext)
				.request(JsonWarehouseRequest.builder().isQualityReturnWarehouse(true).build())
				.identifier(Identifier.ofString("quality2"))
				.build()
				.execute();

		assertThat(second.getWarehouseId()).isEqualTo(first.getWarehouseId());
		assertThat(Services.get(IHUWarehouseDAO.class).retrieveFirstQualityReturnWarehouseId())
				.isEqualTo(WarehouseId.ofRepoId(first.getWarehouseId()));
	}

	@Test
	public void isQualityReturnWarehouse_reuse_doesNotMutateTheSharedWarehouseOrItsDefaultLocator()
	{
		final JsonWarehouseResponse first = commandBuilder(Identifier.ofString("quality1"), JsonWarehouseRequest.builder().isQualityReturnWarehouse(true).build())
				.build()
				.execute();
		final String firstLocatorValue = InterfaceWrapperHelper.load(first.getLocatorId(), I_M_Locator.class).getValue();
		final int firstPickingGroupId = InterfaceWrapperHelper.load(first.getWarehouseId(), I_M_Warehouse.class).getM_Warehouse_PickingGroup_ID();

		// A later run (fresh context, different identifier, even naming a picking group) reuses the shared warehouse.
		final JsonWarehouseResponse second = WarehouseCommand.builder()
				.context(new MasterdataContext())
				.request(JsonWarehouseRequest.builder().isQualityReturnWarehouse(true).pickingGroup("someGroup").build())
				.identifier(Identifier.ofString("quality2"))
				.build()
				.execute();

		assertThat(second.getWarehouseId()).isEqualTo(first.getWarehouseId());
		assertThat(second.getLocatorId()).isEqualTo(first.getLocatorId());
		assertThat(second.getLocatorCode()).isEqualTo(firstLocatorValue);
		assertThat(InterfaceWrapperHelper.load(first.getLocatorId(), I_M_Locator.class).getValue()).isEqualTo(firstLocatorValue);
		assertThat(InterfaceWrapperHelper.load(first.getWarehouseId(), I_M_Warehouse.class).getM_Warehouse_PickingGroup_ID()).isEqualTo(firstPickingGroupId);
	}

	@Test
	public void receiveAsSourceHU_withCategories()
	{
		final I_M_Product_Category categoryRecord = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class);
		categoryRecord.setValue("foil");
		categoryRecord.setName("foil");
		InterfaceWrapperHelper.saveRecord(categoryRecord);
		final Identifier categoryIdentifier = Identifier.ofString("foilCategory");
		context.putIdentifier(categoryIdentifier, ProductCategoryId.ofRepoId(categoryRecord.getM_Product_Category_ID()));

		final JsonWarehouseResponse response = commandBuilder(
				Identifier.ofString("wh"),
				JsonWarehouseRequest.builder()
						.receiveAsSourceHU(true)
						.sourceHUProductCategories(ImmutableList.of(categoryIdentifier))
						.build())
				.build()
				.execute();

		assertThat(InterfaceWrapperHelper.load(response.getWarehouseId(), I_M_Warehouse.class).isReceiveAsSourceHU()).isTrue();
		final List<I_M_Warehouse_SourceHUConfig> configs = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Warehouse_SourceHUConfig.class)
				.addEqualsFilter(I_M_Warehouse_SourceHUConfig.COLUMNNAME_M_Warehouse_ID, response.getWarehouseId())
				.create()
				.list();
		assertThat(configs).hasSize(1);
		assertThat(configs.get(0).getM_Product_Category_ID()).isEqualTo(categoryRecord.getM_Product_Category_ID());
	}
}
