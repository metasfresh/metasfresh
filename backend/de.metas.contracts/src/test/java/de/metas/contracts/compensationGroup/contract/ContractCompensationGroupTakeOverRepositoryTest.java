package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermId;
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

import javax.annotation.Nullable;
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
	private static final ProductCategoryId CATEGORY_ID = ProductCategoryId.ofRepoId(101);
	private static final ProductId PRODUCT_P_ID = ProductId.ofRepoId(201); // own-line discount product
	private static final ProductId PRODUCT_Q_ID = ProductId.ofRepoId(202); // listed customer discount product
	private static final ProductId PRODUCT_R_ID = ProductId.ofRepoId(203); // not listed

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
				.productCategoryId(CATEGORY_ID)
				.ownLineProductId(PRODUCT_P_ID)
				.listedCustomerProductIds(ImmutableSet.of(PRODUCT_Q_ID))
				.build());
	}

	@Test
	void getContractPercentDiscountLines_returnsOnlyContractCreatedPercentageDiscountLines()
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		saveRecord(order);
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		final I_C_Order_CompensationGroup contractGroup = createGroup(order, FlatrateTermId.ofRepoId(5));
		final I_C_Order_CompensationGroup manualGroup = createGroup(order, null);

		// contract-created percentage discount lines: Q (listed) and R (not listed; filtering by listed products is the caller's job)
		createLine(order, contractGroup, PRODUCT_Q_ID, true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "3");
		createLine(order, contractGroup, PRODUCT_R_ID, true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "7");
		// excluded: contract-created amount discount, contract-created surcharge, discount line of a non-contract group, regular line
		createLine(order, contractGroup, ProductId.ofRepoId(204), true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty, null);
		createLine(order, contractGroup, ProductId.ofRepoId(205), true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Surcharge, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "2");
		createLine(order, manualGroup, ProductId.ofRepoId(206), true, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "9");
		createLine(order, contractGroup, ProductId.ofRepoId(207), false, null, null, null);

		final List<LinkedContractDiscountLine> lines = groupRepository.getContractPercentDiscountLines(orderId);

		assertThat(lines).containsExactlyInAnyOrder(
				new LinkedContractDiscountLine(PRODUCT_Q_ID, Percent.of(3)),
				new LinkedContractDiscountLine(PRODUCT_R_ID, Percent.of(7)));
	}

	@Test
	void isProductListedInSameSettings_otherTakeOverOfSameSettings()
	{
		final ContractCompensationGroupSettingsId settingsId = createSettings();
		final ContractSettingsTakeOverId takeOverId1 = createTakeOver(settingsId, true);
		final ContractSettingsTakeOverId takeOverId2 = createTakeOver(settingsId, true);
		final ContractSettingsTakeOverProductId listingId = createListedProduct(takeOverId1, PRODUCT_Q_ID, true);
		createListedProduct(takeOverId1, PRODUCT_R_ID, false);

		assertThat(settingsRepository.isProductListedInSameSettings(takeOverId2, PRODUCT_Q_ID, null)).isTrue();
		assertThat(settingsRepository.isProductListedInSameSettings(takeOverId1, PRODUCT_Q_ID, listingId)).isFalse(); // the record itself
		assertThat(settingsRepository.isProductListedInSameSettings(takeOverId2, PRODUCT_R_ID, null)).isFalse(); // inactive listing
	}

	@Test
	void isProductListedInSameSettings_otherSettingsIgnored()
	{
		final ContractSettingsTakeOverId takeOverOfSettings1 = createTakeOver(createSettings(), true);
		final ContractSettingsTakeOverId takeOverOfSettings2 = createTakeOver(createSettings(), true);
		createListedProduct(takeOverOfSettings1, PRODUCT_Q_ID, true);

		assertThat(settingsRepository.isProductListedInSameSettings(takeOverOfSettings2, PRODUCT_Q_ID, null)).isFalse();
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
		record.setM_Product_Category_ID(CATEGORY_ID.getRepoId());
		record.setM_Product_ID(PRODUCT_P_ID.getRepoId());
		record.setIsActive(active);
		saveRecord(record);
		return ContractSettingsTakeOverId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_ID());
	}

	private static ContractSettingsTakeOverProductId createListedProduct(final ContractSettingsTakeOverId takeOverId, final ProductId productId, final boolean active)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver_Product record = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
		record.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOverId.getRepoId());
		record.setM_Product_ID(productId.getRepoId());
		record.setIsActive(active);
		saveRecord(record);
		return ContractSettingsTakeOverProductId.ofRepoId(record.getC_CompensationGroup_ContractSettings_TakeOver_Product_ID());
	}

	private static I_C_Order_CompensationGroup createGroup(final I_C_Order order, @Nullable final FlatrateTermId flatrateTermId)
	{
		final I_C_Order_CompensationGroup group = newInstance(I_C_Order_CompensationGroup.class);
		group.setC_Order_ID(order.getC_Order_ID());
		group.setC_Flatrate_Term_ID(flatrateTermId != null ? flatrateTermId.getRepoId() : -1);
		saveRecord(group);
		return group;
	}

	private static void createLine(
			final I_C_Order order,
			final I_C_Order_CompensationGroup group,
			final ProductId productId,
			final boolean compensationLine,
			final String compensationType,
			final String amtType,
			final String percentage)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(order.getC_Order_ID());
		line.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
		line.setM_Product_ID(productId.getRepoId());
		line.setIsGroupCompensationLine(compensationLine);
		line.setGroupCompensationType(compensationType);
		line.setGroupCompensationAmtType(amtType);
		line.setGroupCompensationPercentage(percentage != null ? new BigDecimal(percentage) : null);
		saveRecord(line);
	}
}
