package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableSet;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.order.model.I_C_CompensationGroup_Schema;
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

/** The take-over computation: drop-ship detection and the per-record nominal percentage sum. */
class ContractCompensationGroupTakeOverServiceTest
{
	private static final int CATEGORY_ID = 101;
	private static final int OWN_PRODUCT_ID = 201; // own-line discount product
	private static final int BONUS_WARE_ID = 202; // listed
	private static final int BONUS_VERPACKUNG_ID = 203; // not listed
	private static final int OTHER_LISTED_ID = 204; // listed, on no SO line

	private ContractCompensationGroupSettingsRepository settingsRepository;
	private ContractCompensationGroupTakeOverService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		settingsRepository = new ContractCompensationGroupSettingsRepository();
		service = new ContractCompensationGroupTakeOverService(settingsRepository, new ContractCompensationGroupRepository());
	}

	@Test
	void dropShipPurchaseOrder_sumsOnlyListedProductsOfLinkedSalesOrder()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3.0"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7.0"));
		final I_C_Order purchaseOrder = createPurchaseOrder(true, salesOrder.getC_Order_ID());

		final List<TakeOverResult> results = service.computeTakeOvers(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getRecord().getListedCustomerProductIds()).hasSize(1);
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("3.0");
	}

	@Test
	void sumIsNominalArithmeticSum()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID, OTHER_LISTED_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3"),
				new LineSpec(OTHER_LISTED_ID, "3"));
		final I_C_Order purchaseOrder = createPurchaseOrder(true, salesOrder.getC_Order_ID());

		final List<TakeOverResult> results = service.computeTakeOvers(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("6");
	}

	@Test
	void nonDropShipPurchaseOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3.0"));
		final I_C_Order purchaseOrder = createPurchaseOrder(false, salesOrder.getC_Order_ID());

		assertThat(service.computeTakeOvers(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void dropShipWithoutLinkedOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final I_C_Order purchaseOrder = createPurchaseOrder(true, 0);

		assertThat(service.computeTakeOvers(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void recordWhoseListedProductsAreNotOnTheSalesOrder_isDropped()
	{
		final ContractCompensationGroupSettings settings = createSettings(OTHER_LISTED_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3.0"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7.0"));
		final I_C_Order purchaseOrder = createPurchaseOrder(true, salesOrder.getC_Order_ID());

		assertThat(service.computeTakeOvers(purchaseOrder, settings)).isEmpty();
	}

	private ContractCompensationGroupSettings createSettings(final int... listedProductIds)
	{
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);
		final I_C_CompensationGroup_ContractSettings settings = newInstance(I_C_CompensationGroup_ContractSettings.class);
		settings.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(settings);

		final I_C_CompensationGroup_ContractSettings_TakeOver takeOver = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		takeOver.setC_CompensationGroup_ContractSettings_ID(settings.getC_CompensationGroup_ContractSettings_ID());
		takeOver.setM_Product_Category_ID(CATEGORY_ID);
		takeOver.setM_Product_ID(OWN_PRODUCT_ID);
		saveRecord(takeOver);

		for (final int productId : listedProductIds)
		{
			final I_C_CompensationGroup_ContractSettings_TakeOver_Product listed = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
			listed.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOver.getC_CompensationGroup_ContractSettings_TakeOver_ID());
			listed.setM_Product_ID(productId);
			saveRecord(listed);
		}

		return settingsRepository.getBySettingsId(ContractCompensationGroupSettingsId.ofRepoId(settings.getC_CompensationGroup_ContractSettings_ID()));
	}

	private static I_C_Order createPurchaseOrder(final boolean dropShip, final int linkOrderId)
	{
		final I_C_Order po = newInstance(I_C_Order.class);
		po.setIsSOTrx(false);
		po.setIsDropShip(dropShip);
		po.setLink_Order_ID(linkOrderId);
		saveRecord(po);
		return po;
	}

	private static I_C_Order createSalesOrderWithLines(final LineSpec... lines)
	{
		final I_C_Order so = newInstance(I_C_Order.class);
		so.setIsSOTrx(true);
		saveRecord(so);

		final I_C_Order_CompensationGroup group = newInstance(I_C_Order_CompensationGroup.class);
		group.setC_Order_ID(so.getC_Order_ID());
		group.setC_Flatrate_Term_ID(5);
		saveRecord(group);

		for (final LineSpec spec : lines)
		{
			final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
			line.setC_Order_ID(so.getC_Order_ID());
			line.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
			line.setM_Product_ID(spec.productId);
			line.setIsGroupCompensationLine(true);
			line.setGroupCompensationType(X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount);
			line.setGroupCompensationAmtType(X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent);
			line.setGroupCompensationPercentage(new BigDecimal(spec.percentage));
			saveRecord(line);
		}
		return so;
	}

	private static final class LineSpec
	{
		final int productId;
		final String percentage;

		LineSpec(final int productId, final String percentage)
		{
			this.productId = productId;
			this.percentage = percentage;
		}
	}
}
