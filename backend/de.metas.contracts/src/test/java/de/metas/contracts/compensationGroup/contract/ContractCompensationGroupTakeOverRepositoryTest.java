package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.order.OrderId;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.X_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
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

/** Reads of the SO-to-PO take-over: the settings' take-over records and the linked sales order's contract-created discount lines. */
class ContractCompensationGroupTakeOverRepositoryTest
{
	private static final int CATEGORY_ID = 101;
	private static final int PRODUCT_P_ID = 201; // own-line discount product
	private static final int PRODUCT_Q_ID = 202; // listed customer discount product
	private static final int PRODUCT_R_ID = 203; // not listed

	private ContractCompensationGroupSettingsRepository settingsRepository;
	private ContractCompensationGroupRepository groupRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		settingsRepository = new ContractCompensationGroupSettingsRepository();
		groupRepository = new ContractCompensationGroupRepository();
	}

	@Test
	void getTakeOverRecords_returnsActiveRecordWithListedCustomerProducts()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings();
		final ContractSettingsTakeOverId takeOverId = createTakeOver(settingsId, true);
		createListedProduct(takeOverId, PRODUCT_Q_ID, true);
		createListedProduct(takeOverId, PRODUCT_R_ID, false); // inactive -> not listed
		createTakeOver(settingsId, false); // inactive record -> not returned

		final List<TakeOverRecord> records = settingsRepository.getTakeOverRecords(settingsId);

		assertThat(records).containsExactly(TakeOverRecord.builder()
				.takeOverId(takeOverId)
				.productCategoryId(ProductCategoryId.ofRepoId(CATEGORY_ID))
				.ownLineProductId(ProductId.ofRepoId(PRODUCT_P_ID))
				.listedCustomerProductIds(ImmutableSet.of(ProductId.ofRepoId(PRODUCT_Q_ID)))
				.build());
	}

	@Test
	void linkedSalesOrderContractDiscountLines_returnsOnlyContractCreatedPercentageDiscountLines()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		final int contractGroupId = createGroup(order, 5);
		final int manualGroupId = createGroup(order, 0);

		// contract-created percentage discount lines: Q (listed) and R (not listed; filtering by listed products is the caller's job)
		createLine(order, contractGroupId, PRODUCT_Q_ID, true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "3");
		createLine(order, contractGroupId, PRODUCT_R_ID, true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "7");
		// excluded: contract-created amount discount, contract-created surcharge, discount line of a non-contract group, regular line
		createLine(order, contractGroupId, 204, true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty, null);
		createLine(order, contractGroupId, 205, true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Surcharge, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "2");
		createLine(order, manualGroupId, 206, true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "9");
		createLine(order, contractGroupId, 207, false, null, null, null);

		final List<LinkedContractDiscountLine> lines = groupRepository.linkedSalesOrderContractDiscountLines(orderId);

		assertThat(lines).containsExactlyInAnyOrder(
				new LinkedContractDiscountLine(ProductId.ofRepoId(PRODUCT_Q_ID), Percent.of(3)),
				new LinkedContractDiscountLine(ProductId.ofRepoId(PRODUCT_R_ID), Percent.of(7)));
	}

	private static ContractCompensationGroupSettingsId createSettings()
	{
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);
		final I_C_CompensationGroup_ContractSettings settings = newInstance(I_C_CompensationGroup_ContractSettings.class);
		settings.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(settings);
		return ContractCompensationGroupSettingsId.ofRepoId(settings.getC_CompensationGroup_ContractSettings_ID());
	}

	private static ContractSettingsTakeOverId createTakeOver(final ContractCompensationGroupSettingsId settingsId, final boolean active)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		record.setC_CompensationGroup_ContractSettings_ID(settingsId.getRepoId());
		record.setM_Product_Category_ID(CATEGORY_ID);
		record.setM_Product_ID(PRODUCT_P_ID);
		record.setIsActive(active);
		saveRecord(record);
		return ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID());
	}

	private static void createListedProduct(final ContractSettingsTakeOverId takeOverId, final int productId, final boolean active)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver_Product record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
		record.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOverId.getRepoId());
		record.setM_Product_ID(productId);
		record.setIsActive(active);
		saveRecord(record);
	}

	private static int createGroup(final I_C_Order order, final int flatrateTermId)
	{
		final I_C_Order_CompensationGroup group = newInstance(I_C_Order_CompensationGroup.class);
		group.setC_Order_ID(order.getC_Order_ID());
		group.setC_Flatrate_Term_ID(flatrateTermId);
		saveRecord(group);
		return group.getC_Order_CompensationGroup_ID();
	}

	private static void createLine(
			final I_C_Order order,
			final int groupId,
			final int productId,
			final boolean compensationLine,
			final String compensationType,
			final String amtType,
			final String percentage)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setC_Order_CompensationGroup_ID(groupId);
		line.setM_Product_ID(productId);
		line.setIsGroupCompensationLine(compensationLine);
		line.setGroupCompensationType(compensationType);
		line.setGroupCompensationAmtType(amtType);
		line.setGroupCompensationPercentage(percentage != null ? new BigDecimal(percentage) : null);
		saveRecord(line);
	}
}
