package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.GroupCompensationType;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateCompensationLine;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_C_OrderLine;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;

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

/** The take-over: drop-ship detection, the per-take-over nominal percentage sum, and the append of its own line to the schema's compensation lines. */
class ContractSettingsTakeOverServiceTest
{
	private static final ProductCategoryId CATEGORY_ID = ProductCategoryId.ofRepoId(101);
	private static final ProductCategoryId OTHER_CATEGORY_ID = ProductCategoryId.ofRepoId(102);
	private static final ProductId OWN_PRODUCT_ID = ProductId.ofRepoId(201); // own-line discount product
	private static final ProductId BONUS_WARE_ID = ProductId.ofRepoId(202); // customer discount product
	private static final ProductId BONUS_VERPACKUNG_ID = ProductId.ofRepoId(203); // not a customer discount product
	private static final ProductId OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID = ProductId.ofRepoId(204); // customer discount product, on no SO line

	private static final BPartnerId SERVICE_COMPANY_ID = BPartnerId.ofRepoId(111); // the fee's service company
	private static final BPartnerId OTHER_SERVICE_COMPANY_ID = BPartnerId.ofRepoId(112);
	private static final ZoneId ZONE = ZoneId.of("UTC");
	private static final BPartnerId CUSTOMER_ID = BPartnerId.ofRepoId(2); // the linked SO's invoice partner the fee is keyed by
	private static final ZonedDateTime SO_DATE = LocalDate.parse("2026-05-10").atStartOfDay(ZONE);

	private ContractCompensationGroupSettingsRepository settingsRepository;
	private OrderGroupRepository orderGroupRepositorySpy;
	private ContractServiceFeeTakeOverService feeServiceSpy;
	private ContractSettingsTakeOverRefundService refundServiceMock;
	/** the qualifying refund contracts the stubbed refund lookup knows, in lookup (= term) order */
	private ImmutableListMultimap.Builder<ConditionsId, TakenOverRefund> preparedRefunds;
	private int nextRefundTermRepoId;
	private ContractSettingsTakeOverService service;
	private UomId uomId;
	private ProductId goodsProductId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		settingsRepository = ContractCompensationGroupSettingsRepository.newInstanceForUnitTesting();
		// a spy delegates to the real methods, so it behaves like the real repository in every test
		orderGroupRepositorySpy = Mockito.spy(OrderGroupRepository.newInstanceForUnitTesting());

		feeServiceSpy = Mockito.spy(ContractServiceFeeTakeOverService.newInstanceForUnitTesting());

		// the refund lookup returns, like the real one, only the prepared refunds of the requested conditions, in lookup order
		preparedRefunds = ImmutableListMultimap.builder();
		nextRefundTermRepoId = 900;
		refundServiceMock = Mockito.mock(ContractSettingsTakeOverRefundService.class);
		Mockito.when(refundServiceMock.findTakenOverRefunds(any(), any(), any())).thenAnswer(invocation -> {
			final Set<ConditionsId> requested = invocation.getArgument(2);
			final ImmutableListMultimap.Builder<ConditionsId, TakenOverRefund> result = ImmutableListMultimap.builder();
			preparedRefunds.build().entries().stream()
					.filter(entry -> requested.contains(entry.getKey()))
					.forEach(entry -> result.put(entry.getKey(), entry.getValue()));
			return result.build();
		});

		service = new ContractSettingsTakeOverService(ContractSettingsTakeOverRepository.newInstanceForUnitTesting(), orderGroupRepositorySpy, feeServiceSpy, refundServiceMock);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		final I_M_Product_Category goodsCategory = newInstance(I_M_Product_Category.class);
		saveRecord(goodsCategory);
		final I_M_Product goodsProduct = newInstance(I_M_Product.class);
		goodsProduct.setC_UOM_ID(uomId.getRepoId());
		goodsProduct.setM_Product_Category_ID(goodsCategory.getM_Product_Category_ID());
		saveRecord(goodsProduct);
		goodsProductId = ProductId.ofRepoId(goodsProduct.getM_Product_ID());
	}

	@Test
	void dropShipPurchaseOrder_sumsOnlyCustomerDiscountProductsOfLinkedSalesOrder()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3.0"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7.0"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getTakeOver().getCustomerDiscountProductIds()).hasSize(1);
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("3.0");
	}

	@Test
	void sumIsNominalArithmeticSum()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3"),
				new LineSpec(OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID, "3"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("6");
	}

	@Test
	void takenOverProducts_areOnlyTheCustomerDiscountProductsActuallyOnTheSalesOrder()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getTakenOverProductIds()).containsExactly(BONUS_WARE_ID);
	}

	@Test
	void eachTakenOverContractLineKeepsItsOwnPercentage()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3"),
				new LineSpec(OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID, "1"));
		addContractGroupWithDiscountLine(salesOrder, new LineSpec(BONUS_WARE_ID, "3"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getTakenOverPercentages()).containsExactly(
				ContractSettingsTakeOverMatch.TakenOverPercentage.of(BONUS_WARE_ID, Percent.of(3)),
				ContractSettingsTakeOverMatch.TakenOverPercentage.of(OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID, Percent.of(1)),
				ContractSettingsTakeOverMatch.TakenOverPercentage.of(BONUS_WARE_ID, Percent.of(3)));
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("7");
	}

	@Test
	void onlyPercentDiscountLinesOfContractCreatedGroupsAreTakenOver()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID, BONUS_VERPACKUNG_ID, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3"),
				new LineSpec(BONUS_VERPACKUNG_ID, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty, "0"),
				new LineSpec(OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Surcharge, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, "2"));
		addManualGroupWithDiscountLine(salesOrder, new LineSpec(OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID, "9"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		final List<ContractSettingsTakeOverMatch> results = service.computeMatches(purchaseOrder, settings);

		assertThat(results).hasSize(1);
		assertThat(results.get(0).getTakenOverProductIds()).containsExactly(BONUS_WARE_ID);
		assertThat(results.get(0).getSummedPercent().toBigDecimal()).isEqualByComparingTo("3");
	}

	@Test
	void nonDropShipPurchaseOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3.0"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(false, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void dropShipSalesOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID);
		final I_C_Order linkedSalesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3.0"));
		final OrderDropShipInfo dropShipSalesOrder = OrderDropShipInfo.builder()
				.soTrx(SOTrx.SALES)
				.isDropShip(true)
				.linkedOrderId(OrderId.ofRepoId(linkedSalesOrder.getC_Order_ID()))
				.build();

		assertThat(service.computeMatches(dropShipSalesOrder, settings)).isEmpty();
	}

	@Test
	void dropShipWithoutLinkedOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID);
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, null);

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void settingsWithoutTakeOver_returnsEmptyWithoutReadingTheLinkedSalesOrder()
	{
		final ContractCompensationGroupSettings settings = settingsRepository.getBySettingsId(createSettingsRecord(null));
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3.0"));

		assertThat(service.computeMatches(purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID())), settings)).isEmpty();
		Mockito.verify(orderGroupRepositorySpy, Mockito.never()).retrieveContractCreatedGroupsByOrderId(any());
	}

	@Test
	void recordWhoseCustomerDiscountProductsAreNotOnTheSalesOrder_isDropped()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3.0"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7.0"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void applyToSchema_nothingTakenOver_returnsTheSameSchema()
	{
		final ContractCompensationGroupSettings settings = createSettings(null, BONUS_WARE_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3"));
		final GroupTemplate schema = schema(schemaLine(Percent.of(3), CATEGORY_ID, product("Bonus Vendor", null, null)));

		assertThat(service.applyToSchema(schema, purchaseOrder(false, OrderId.ofRepoId(salesOrder.getC_Order_ID())), settings)).isSameAs(schema);
	}

	@Test
	void applyToSchema_keepsThePercentDiscountLineOfTheSameCategoryAndAppendsAnOwnLine()
	{
		final GroupTemplateCompensationLine vendorLine = schemaLine(Percent.of(3), CATEGORY_ID, product("Bonus Vendor", null, null))
				.toBuilder()
				.description("3% Bonus Vendor")
				.build();

		assertThat(applyBonusWareTakeOverOf3PercentTo(vendorLine)).containsExactly(vendorLine, expectedOwnLine());
	}

	@Test
	void applyToSchema_appendsAnOwnLineWhenTheOnlyLineOfTheCategoryIsARevenueBreakLine()
	{
		final GroupTemplateCompensationLine revenueBreakLine = schemaLine(Percent.of(3), CATEGORY_ID, product("Bonus Vendor", null, null))
				.toBuilder()
				.groupMatcher(group -> false)
				.build();

		assertThat(applyBonusWareTakeOverOf3PercentTo(revenueBreakLine)).containsExactly(revenueBreakLine, expectedOwnLine());
	}

	@Test
	void applyToSchema_appendsAnOwnLineWhenTheSchemaHasNoCompensationLine()
	{
		final List<GroupTemplateCompensationLine> lines = applyBonusWareTakeOverOf3PercentTo();

		assertThat(lines).containsExactly(expectedOwnLine());
	}

	@Test
	void applyToSchema_appendsWhenTheCategoryLineHasNoPercentage()
	{
		final GroupTemplateCompensationLine vendorLine = schemaLine(null, CATEGORY_ID, product("Bonus Vendor", null, null));

		assertThat(applyBonusWareTakeOverOf3PercentTo(vendorLine)).containsExactly(vendorLine, expectedOwnLine());
	}

	@Test
	void applyToSchema_appendsWhenThePercentLineIsOnAnotherCategory()
	{
		final GroupTemplateCompensationLine vendorLine = schemaLine(Percent.of(3), OTHER_CATEGORY_ID, product("Bonus Vendor", null, null));

		assertThat(applyBonusWareTakeOverOf3PercentTo(vendorLine)).containsExactly(vendorLine, expectedOwnLine());
	}

	@Test
	void applyToSchema_appendsWhenTheCategoryLineProductIsASurcharge()
	{
		final GroupTemplateCompensationLine vendorLine = schemaLine(Percent.of(3), CATEGORY_ID, product("Surcharge Vendor", X_C_OrderLine.GROUPCOMPENSATIONTYPE_Surcharge, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent));

		assertThat(applyBonusWareTakeOverOf3PercentTo(vendorLine)).containsExactly(vendorLine, expectedOwnLine());
	}

	@Test
	void applyToSchema_appendsWhenTheCategoryLineProductIsNotAPercentage()
	{
		final GroupTemplateCompensationLine vendorLine = schemaLine(Percent.of(3), CATEGORY_ID, product("Fixed Vendor", X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_PriceAndQty));

		assertThat(applyBonusWareTakeOverOf3PercentTo(vendorLine)).containsExactly(vendorLine, expectedOwnLine());
	}

	@Test
	void applyToSchema_ownLineDescriptionNamesEachTakenOverPercentageWithItsProduct()
	{
		final ProductId bonusWareAId = product("Bonus Ware A", null, null);
		final ProductId bonusWareBId = product("Bonus Ware B", null, null);
		final ContractCompensationGroupSettings settings = createSettings(null, bonusWareAId, bonusWareBId);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(bonusWareAId, "3"),
				new LineSpec(bonusWareBId, "1"));

		final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID())), settings)
				.getCompensationLines();

		assertThat(lines).hasSize(1);
		assertThat(lines.get(0).getPercentage()).isEqualTo(Percent.of(4));
		assertThat(lines.get(0).getDescription()).isEqualTo("3% Bonus Ware A + 1% Bonus Ware B");
	}

	@Test
	void applyToSchema_ownLineDescriptionNamesTheSameProductOncePerContractLine()
	{
		final ProductId bonusWareId = product("Bonus Ware", null, null);
		final ContractCompensationGroupSettings settings = createSettings(null, bonusWareId);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
		addContractGroupWithDiscountLine(salesOrder, new LineSpec(bonusWareId, "3"));

		final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID())), settings)
				.getCompensationLines();

		assertThat(lines).hasSize(1);
		assertThat(lines.get(0).getPercentage()).isEqualTo(Percent.of(6));
		assertThat(lines.get(0).getDescription()).isEqualTo("3% Bonus Ware + 3% Bonus Ware");
	}

	@Test
	void applyToSchema_singleMatchWithServiceFee_appendsTheFeeAsItsOwnLine()
	{
		final ProductId bonusWareId = product("Bonus Ware", null, null);
		final I_InvoiceProcessingServiceCompany feeConfig = createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee", null, null, true);
		final ContractCompensationGroupSettings settings = createSettings(feeConfig, bonusWareId);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

		final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
						schema(),
						purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
						settings)
				.getCompensationLines();

		assertThat(lines).containsExactly(
				expectedOwnLine(),
				expectedServiceFeeLine("2.60", "2.6% Payment Service Fee"));
	}

	@Test
	void applyToSchema_noCustomerBonusWithServiceFee_appendsOnlyTheFeeLineOnTheOnlyTakeOver()
	{
		final ProductId bonusWareId = product("Bonus Ware", null, null);
		final I_InvoiceProcessingServiceCompany feeConfig = createFeeConfig(CUSTOMER_ID, "2.15", "Service Fee B", null, null, true);
		final ContractCompensationGroupSettings settings = createSettings(feeConfig, bonusWareId);
		final I_C_Order salesOrder = createSalesOrderWithLines(); // the customer has no contract bonus

		final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
						schema(),
						purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
						settings)
				.getCompensationLines();

		assertThat(lines).containsExactly(expectedServiceFeeLine("2.15", "2.15% Service Fee B"));
	}

	@Test
	void applyToSchema_noCustomerBonusWithServiceFeeAndTwoTakeOvers_throws()
	{
		final ProductId bonusWareAId = product("Bonus Ware A", null, null);
		final ProductId bonusWareBId = product("Bonus Ware B", null, null);
		final I_InvoiceProcessingServiceCompany feeConfig = createFeeConfig(CUSTOMER_ID, "2.15", "Service Fee B", null, null, true);
		final ContractCompensationGroupSettings settings = createSettingsWithTwoTakeOvers(feeConfig, bonusWareAId, bonusWareBId);
		final I_C_Order salesOrder = createSalesOrderWithLines(); // the customer has no contract bonus

		final OrderDropShipInfo purchaseOrder = purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE);

		assertThatThrownBy(() -> service.applyToSchema(schema(), purchaseOrder, settings))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void applyToSchema_singleMatchWithoutServiceFee_leavesPercentageAndDescriptionUnchanged()
	{
		final ProductId bonusWareId = product("Bonus Ware", null, null);
		final ContractCompensationGroupSettings settings = createSettings(null, bonusWareId);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
		// no fee assignment for CUSTOMER_ID

		final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
						schema(),
						purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
						settings)
				.getCompensationLines();

		assertThat(lines).hasSize(1);
		assertThat(lines.get(0).getPercentage().toBigDecimal()).isEqualByComparingTo("3");
		assertThat(lines.get(0).getDescription()).isEqualTo("3% Bonus Ware");
	}

	@Test
	void applyToSchema_multipleMatchesWithServiceFee_throws()
	{
		final ProductId bonusWareAId = product("Bonus Ware A", null, null);
		final ProductId bonusWareBId = product("Bonus Ware B", null, null);
		final I_InvoiceProcessingServiceCompany feeConfig = createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee", null, null, true);
		final ContractCompensationGroupSettings settings = createSettingsWithTwoTakeOvers(feeConfig, bonusWareAId, bonusWareBId);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(bonusWareAId, "3"),
				new LineSpec(bonusWareBId, "1"));

		final OrderDropShipInfo purchaseOrder = purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE);

		assertThatThrownBy(() -> service.applyToSchema(schema(), purchaseOrder, settings))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void applyToSchema_multipleMatchesWithoutServiceFee_appendsBothLinesWithoutFee()
	{
		final ProductId bonusWareAId = product("Bonus Ware A", null, null);
		final ProductId bonusWareBId = product("Bonus Ware B", null, null);
		final ContractCompensationGroupSettings settings = createSettingsWithTwoTakeOvers(null, bonusWareAId, bonusWareBId);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(bonusWareAId, "3"),
				new LineSpec(bonusWareBId, "1"));
		// no fee assignment for CUSTOMER_ID

		final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
						schema(),
						purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
						settings)
				.getCompensationLines();

		assertThat(lines).hasSize(2);
		assertThat(lines).extracting(GroupTemplateCompensationLine::getDescription)
				.containsExactlyInAnyOrder("3% Bonus Ware A", "1% Bonus Ware B");
	}

	@Nested
	class applyToSchema
	{
		@Test
		void passOnServiceCompanyEmpty_noFeeLine_takeOverLineUnchanged()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettings(null, bonusWareId); // field empty
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
			createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee", null, null, true);

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
							schema(),
							purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
							settings)
					.getCompensationLines();

			assertThat(lines).containsExactly(expectedOwnLine());
		}

		@Test
		void passOnServiceCompanyEmptyAndTwoTakeOvers_noFeeLine_noException()
		{
			final ProductId bonusWareAId = product("Bonus Ware A", null, null);
			final ProductId bonusWareBId = product("Bonus Ware B", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithTwoTakeOvers(null, bonusWareAId, bonusWareBId); // field empty
			final I_C_Order salesOrder = createSalesOrderWithLines(); // no contract bonus -> candidates would be ambiguous if the fee counted
			createFeeConfig(CUSTOMER_ID, "2.15", "Service Fee B", null, null, true);
			final OrderDropShipInfo purchaseOrder = purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE);

			final GroupTemplate schema = schema();

			assertThat(service.applyToSchema(schema, purchaseOrder, settings)).isSameAs(schema);
		}

		@Test
		void passOnServiceCompanyIsAnotherCompany_noFeeLine()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final I_InvoiceProcessingServiceCompany otherCompanyConfig = createFeeConfig(BPartnerId.ofRepoId(3), "1.00", "Other Fee", OTHER_SERVICE_COMPANY_ID, null, true);
			final ContractCompensationGroupSettings settings = createSettings(otherCompanyConfig, bonusWareId);
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
			createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee", null, null, true); // the customer's fee belongs to SERVICE_COMPANY_ID

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
							schema(),
							purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
							settings)
					.getCompensationLines();

			assertThat(lines).containsExactly(expectedOwnLine());
		}

		@Test
		void passOnServiceCompanyReferencesOlderVersionOfTheCustomersCompany_feeLineWithNewerPercent()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			// older version of the company's config: ValidFrom 2025-01-01, record stays active, only the customer's assignment on it is deactivated;
			// the settings reference it, but the fee is resolved from the version valid at SO_DATE (2026-01-01, active assignment)
			final I_InvoiceProcessingServiceCompany olderVersion = createFeeConfig(CUSTOMER_ID, "2.00", "Old Fee", SERVICE_COMPANY_ID, LocalDate.parse("2025-01-01"), false);
			createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee", null, null, true); // newer version, active assignment
			final ContractCompensationGroupSettings settings = createSettings(olderVersion, bonusWareId);
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
							schema(),
							purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
							settings)
					.getCompensationLines();

			assertThat(lines).containsExactly(
					expectedOwnLine(),
					expectedServiceFeeLine("2.60", "2.6% Payment Service Fee"));
		}

		@Test
		void passOnServiceCompanyEmpty_doesNotResolveTheCustomersFee()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettings(null, bonusWareId); // field empty
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
			createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee", null, null, true);

			service.applyToSchema(schema(), purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE), settings);

			Mockito.verify(feeServiceSpy, Mockito.never()).resolveServiceFee(any(), any());
		}

		@Test
		void passOnServiceCompanyReferencesInactiveOlderVersion_stillMatchesByCompany()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final I_InvoiceProcessingServiceCompany olderVersion = createFeeConfig(CUSTOMER_ID, "2.00", "Old Fee", null, LocalDate.parse("2025-01-01"), true);
			olderVersion.setIsActive(false); // the older version was deactivated when the newer one took over
			saveRecord(olderVersion);
			createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee", null, null, true); // active newer version of the same company
			final ContractCompensationGroupSettings settings = createSettings(olderVersion, bonusWareId);
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
							schema(),
							purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
							settings)
					.getCompensationLines();

			assertThat(lines).containsExactly(
					expectedOwnLine(),
					expectedServiceFeeLine("2.60", "2.6% Payment Service Fee"));
		}

		@Test
		void passOnServiceCompanySetButSettingsHaveNoTakeOver_schemaUnchanged()
		{
			final I_InvoiceProcessingServiceCompany feeConfig = createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee", null, null, true);
			final ContractCompensationGroupSettings settings = settingsRepository.getBySettingsId(createSettingsRecord(feeConfig)); // no take-over row
			final I_C_Order salesOrder = createSalesOrderWithLines(); // the customer has no contract bonus
			final GroupTemplate schema = schema();

			assertThat(service.applyToSchema(schema, purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE), settings))
					.isSameAs(schema);
		}

		@Test
		void passOnServiceCompanyIsAnotherCompanyAndTwoTakeOvers_noFeeLine_noException()
		{
			final ProductId bonusWareAId = product("Bonus Ware A", null, null);
			final ProductId bonusWareBId = product("Bonus Ware B", null, null);
			final I_InvoiceProcessingServiceCompany otherCompanyConfig = createFeeConfig(BPartnerId.ofRepoId(3), "1.00", "Other Fee", OTHER_SERVICE_COMPANY_ID, null, true);
			final ContractCompensationGroupSettings settings = createSettingsWithTwoTakeOvers(otherCompanyConfig, bonusWareAId, bonusWareBId);
			final I_C_Order salesOrder = createSalesOrderWithLines(); // no contract bonus -> candidates would be ambiguous if the fee counted
			createFeeConfig(CUSTOMER_ID, "2.15", "Service Fee B", null, null, true); // the customer's fee belongs to SERVICE_COMPANY_ID
			final GroupTemplate schema = schema();

			assertThat(service.applyToSchema(schema, purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE), settings))
					.isSameAs(schema);
		}
	}

	/** Refund contracts of the listed refund conditions are added to the take-over line, whose sum is rounded to the record's decimals. */
	@Nested
	class refunds
	{
		private final ConditionsId RV_WARE_1 = ConditionsId.ofRepoId(301);
		private final ConditionsId RV_WARE_0125 = ConditionsId.ofRepoId(302);
		private final ConditionsId RV_OTHER = ConditionsId.ofRepoId(303);

		@Test
		void contractBonusAndRefunds_summedAndRoundedToTwoDecimals()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 2, ImmutableList.of(RV_WARE_1, RV_WARE_0125), bonusWareId);
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			prepareRefund(RV_WARE_0125, "RV Ware 0,125 % jährlich", "0.125");
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).hasSize(1);
			assertThat(lines.get(0).getPercentage().toBigDecimal()).isEqualByComparingTo("4.13"); // 3 + 1 + 0.125 = 4.125 -> half up
			Mockito.verify(refundServiceMock).findTakenOverRefunds(CUSTOMER_ID, LocalDate.parse("2026-05-10"), ImmutableSet.of(RV_WARE_1, RV_WARE_0125));
		}

		@Test
		void precisionThree_keeps4_125()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 3, ImmutableList.of(RV_WARE_1, RV_WARE_0125), bonusWareId);
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			prepareRefund(RV_WARE_0125, "RV Ware 0,125 % jährlich", "0.125");
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).hasSize(1);
			assertThat(lines.get(0).getPercentage().toBigDecimal()).isEqualByComparingTo("4.125");
		}

		@Test
		void refundOnly_createsTakeOverLine()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 2, ImmutableList.of(RV_WARE_1), bonusWareId);
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			final I_C_Order salesOrder = createSalesOrderWithLines(); // no contract bonus on the SO

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).containsExactly(GroupTemplateCompensationLine.builder()
					.productId(OWN_PRODUCT_ID)
					.compensationType(GroupCompensationType.Discount)
					.percentage(Percent.of(1))
					.appliesToProductCategoryId(CATEGORY_ID)
					.isOwnBase(true)
					.description("1% RV Ware 1 % vierteljährlich")
					.build());
		}

		@Test
		void neitherBonusNorRefund_noTakeOverLine()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 2, ImmutableList.of(RV_WARE_1), bonusWareId);
			// no qualifying refund contract of RV_WARE_1, no contract bonus on the SO
			final I_C_Order salesOrder = createSalesOrderWithLines();
			final GroupTemplate schema = schema();

			assertThat(service.computeMatches(dropShipPO(salesOrder), settings)).isEmpty();
			assertThat(service.applyToSchema(schema, dropShipPO(salesOrder), settings)).isSameAs(schema);
		}

		@Test
		void refundsOfConditionListedOnOtherRecord_countOnlyThere()
		{
			final ProductId bonusWareAId = product("Bonus Ware A", null, null);
			final ProductId bonusWareBId = product("Bonus Ware B", null, null);
			final ContractCompensationGroupSettingsId settingsId = createSettingsRecord(null);
			addTakeOver(settingsId, CATEGORY_ID, OWN_PRODUCT_ID, 2, ImmutableList.of(RV_WARE_1), bonusWareAId);
			addTakeOver(settingsId, OTHER_CATEGORY_ID, OWN_PRODUCT_ID, 2, ImmutableList.of(RV_WARE_0125), bonusWareBId);
			final ContractCompensationGroupSettings settings = settingsRepository.getBySettingsId(settingsId);
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			prepareRefund(RV_WARE_0125, "RV Ware 0,125 % jährlich", "0.125");
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareAId, "3"), new LineSpec(bonusWareBId, "2"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).hasSize(2);
			assertThat(lines.get(0).getAppliesToProductCategoryId()).isEqualTo(CATEGORY_ID);
			assertThat(lines.get(0).getPercentage().toBigDecimal()).isEqualByComparingTo("4");
			assertThat(lines.get(0).getDescription()).isEqualTo("3% Bonus Ware A + 1% RV Ware 1 % vierteljährlich");
			assertThat(lines.get(1).getAppliesToProductCategoryId()).isEqualTo(OTHER_CATEGORY_ID);
			assertThat(lines.get(1).getPercentage().toBigDecimal()).isEqualByComparingTo("2.13");
			assertThat(lines.get(1).getDescription()).isEqualTo("2% Bonus Ware B + 0.125% RV Ware 0,125 % jährlich");
			// one lookup for the union of both records' conditions
			Mockito.verify(refundServiceMock, Mockito.times(1)).findTakenOverRefunds(any(), any(), any());
		}

		@Test
		void description_namesBonusThenRefunds()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 2, ImmutableList.of(RV_WARE_1, RV_WARE_0125), bonusWareId);
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			prepareRefund(RV_WARE_0125, "RV Ware 0,125 % jährlich", "0.125");
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).hasSize(1);
			assertThat(lines.get(0).getDescription()).isEqualTo("3% Bonus Ware + 1% RV Ware 1 % vierteljährlich + 0.125% RV Ware 0,125 % jährlich");
		}

		@Test
		void twoTermsSameCondition_eachCounts()
		{
			// every qualifying refund contract reaches the PO (REQUIREMENTS § 3 "Several bonuses per customer all reach the purchase order");
			// two valid terms of one condition are two contracts, so both count (they would also be settled twice)
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 2, ImmutableList.of(RV_WARE_1), bonusWareId);
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).hasSize(1);
			assertThat(lines.get(0).getPercentage().toBigDecimal()).isEqualByComparingTo("5");
			assertThat(lines.get(0).getDescription()).isEqualTo("3% Bonus Ware + 1% RV Ware 1 % vierteljährlich + 1% RV Ware 1 % vierteljährlich");
		}

		@Test
		void noRefundConditionsListed_noRefundLookup()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 2, ImmutableList.of(), bonusWareId);
			prepareRefund(RV_OTHER, "RV Other", "5"); // a qualifying refund contract of a condition no record lists
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).containsExactly(expectedOwnLine());
			Mockito.verify(refundServiceMock, Mockito.never()).findTakenOverRefunds(any(), any(), argThat(conditionsIds -> !conditionsIds.isEmpty()));
		}

		@Test
		void feeLine_isNotRounded()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final I_InvoiceProcessingServiceCompany feeConfig = createFeeConfig(CUSTOMER_ID, "2.125", "Payment Service Fee", null, null, true);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(feeConfig, 2, ImmutableList.of(RV_WARE_0125), bonusWareId);
			prepareRefund(RV_WARE_0125, "RV Ware 0,125 % jährlich", "0.125");
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).hasSize(2);
			assertThat(lines.get(0).getPercentage().toBigDecimal()).isEqualByComparingTo("3.13"); // take-over line rounded
			assertThat(lines.get(1)).isEqualTo(expectedServiceFeeLine("2.125", "2.125% Payment Service Fee")); // fee line unrounded
		}

		@Test
		void refundOnlyMatch_isTheFeeLinesRecord()
		{
			final ProductId bonusWareAId = product("Bonus Ware A", null, null);
			final ProductId bonusWareBId = product("Bonus Ware B", null, null);
			final I_InvoiceProcessingServiceCompany feeConfig = createFeeConfig(CUSTOMER_ID, "2.15", "Service Fee B", null, null, true);
			final ContractCompensationGroupSettingsId settingsId = createSettingsRecord(feeConfig);
			addTakeOver(settingsId, CATEGORY_ID, OWN_PRODUCT_ID, 2, ImmutableList.of(), bonusWareAId);
			addTakeOver(settingsId, OTHER_CATEGORY_ID, OWN_PRODUCT_ID, 2, ImmutableList.of(RV_WARE_1), bonusWareBId);
			final ContractCompensationGroupSettings settings = settingsRepository.getBySettingsId(settingsId);
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			final I_C_Order salesOrder = createSalesOrderWithLines(); // no contract bonus: only the second record matches, by its refund

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).hasSize(2);
			assertThat(lines.get(0).getAppliesToProductCategoryId()).isEqualTo(OTHER_CATEGORY_ID);
			assertThat(lines.get(0).getDescription()).isEqualTo("1% RV Ware 1 % vierteljährlich");
			assertThat(lines.get(1).getAppliesToProductCategoryId()).isEqualTo(OTHER_CATEGORY_ID); // the fee line goes to the refund-only match's record
			assertThat(lines.get(1).getPercentage().toBigDecimal()).isEqualByComparingTo("2.15");
		}

		@Test
		void refundTakeOverNotAllowed_contractBonusStillTakenOver_noRefund()
		{
			// the linked sales order is reactivated (neither completed/closed nor completing): its contract bonus still reaches the PO, its refunds do not (AC7b)
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 2, ImmutableList.of(RV_WARE_1, RV_WARE_0125), bonusWareId);
			prepareRefund(RV_WARE_1, "RV Ware 1 % vierteljährlich", "1");
			prepareRefund(RV_WARE_0125, "RV Ware 0,125 % jährlich", "0.125");
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
			final OrderDropShipInfo purchaseOrder = dropShipPO(salesOrder).toBuilder().refundTakeOverAllowed(false).build();

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), purchaseOrder, settings).getCompensationLines();

			assertThat(lines).containsExactly(expectedOwnLine());
			Mockito.verify(refundServiceMock, Mockito.never()).findTakenOverRefunds(any(), any(), any());
		}

		@Test
		void vendorLine_unchanged()
		{
			final ProductId bonusWareId = product("Bonus Ware", null, null);
			final ContractCompensationGroupSettings settings = createSettingsWithRefunds(null, 2, ImmutableList.of(RV_WARE_0125), bonusWareId);
			prepareRefund(RV_WARE_0125, "RV Ware 0,125 % jährlich", "0.125");
			final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
			final GroupTemplateCompensationLine vendorLine = schemaLine(Percent.of("2.125"), CATEGORY_ID, product("Bonus Vendor", null, null))
					.toBuilder()
					.description("2.125% Bonus Vendor")
					.build();

			final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(vendorLine), dropShipPO(salesOrder), settings).getCompensationLines();

			assertThat(lines).hasSize(2);
			assertThat(lines.get(0)).isSameAs(vendorLine); // not rounded
			assertThat(lines.get(1).getPercentage().toBigDecimal()).isEqualByComparingTo("3.13");
		}
	}

	/** A drop-ship purchase order whose linked sales order carries a 3% "Bonus Ware" contract discount line that the take-over lists. */
	private List<GroupTemplateCompensationLine> applyBonusWareTakeOverOf3PercentTo(final GroupTemplateCompensationLine... schemaLines)
	{
		final ProductId bonusWareId = product("Bonus Ware", null, null);
		final ContractCompensationGroupSettings settings = createSettings(null, bonusWareId);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));

		return service.applyToSchema(schema(schemaLines), purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID())), settings)
				.getCompensationLines();
	}

	private GroupTemplateCompensationLine expectedOwnLine()
	{
		return GroupTemplateCompensationLine.builder()
				.productId(OWN_PRODUCT_ID)
				.compensationType(GroupCompensationType.Discount)
				.percentage(Percent.of(3))
				.appliesToProductCategoryId(CATEGORY_ID)
				.isOwnBase(true)
				.description("3% Bonus Ware")
				.build();
	}

	private static GroupTemplateCompensationLine expectedServiceFeeLine(final String percent, final String description)
	{
		return GroupTemplateCompensationLine.builder()
				.productId(OWN_PRODUCT_ID)
				.compensationType(GroupCompensationType.Discount)
				.percentage(Percent.of(new BigDecimal(percent)))
				.appliesToProductCategoryId(CATEGORY_ID)
				.isOwnBase(true)
				.description(description)
				.build();
	}

	private static GroupTemplate schema(final GroupTemplateCompensationLine... compensationLines)
	{
		return GroupTemplate.builder()
				.name("vendor schema")
				.regularLinesToAdd(ImmutableList.of())
				.compensationLines(ImmutableList.copyOf(compensationLines))
				.build();
	}

	/** A schema line as loaded from the schema: its compensation type is left to the product. */
	private static GroupTemplateCompensationLine schemaLine(
			@Nullable final Percent percentage,
			final ProductCategoryId categoryId,
			final ProductId productId)
	{
		return GroupTemplateCompensationLine.builder()
				.productId(productId)
				.percentage(percentage)
				.appliesToProductCategoryId(categoryId)
				.build();
	}

	private ProductId product(final String name, @Nullable final String compensationType, @Nullable final String amtType)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uomId.getRepoId());
		product.setGroupCompensationType(compensationType);
		product.setGroupCompensationAmtType(amtType);
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	/** Settings with one take-over; {@code passOnConfig} is the passed-on service company ({@code null} = not set). */
	private ContractCompensationGroupSettings createSettings(@Nullable final I_InvoiceProcessingServiceCompany passOnConfig, final ProductId... customerDiscountProductIds)
	{
		final ContractCompensationGroupSettingsId settingsId = createSettingsRecord(passOnConfig);
		addTakeOver(settingsId, CATEGORY_ID, OWN_PRODUCT_ID, customerDiscountProductIds);
		return settingsRepository.getBySettingsId(settingsId);
	}

	/** Settings with two take-overs, so that a drop-ship PO carrying both products yields two matches. */
	private ContractCompensationGroupSettings createSettingsWithTwoTakeOvers(@Nullable final I_InvoiceProcessingServiceCompany passOnConfig, final ProductId takeOver1Product, final ProductId takeOver2Product)
	{
		final ContractCompensationGroupSettingsId settingsId = createSettingsRecord(passOnConfig);
		addTakeOver(settingsId, CATEGORY_ID, OWN_PRODUCT_ID, takeOver1Product);
		addTakeOver(settingsId, OTHER_CATEGORY_ID, OWN_PRODUCT_ID, takeOver2Product);
		return settingsRepository.getBySettingsId(settingsId);
	}

	/** A take-over with the AD default of 2 decimals (unit tests have no DB default, so it is set explicitly). */
	private static I_C_CompensationGroup_ContractSettings_TakeOver addTakeOver(
			final ContractCompensationGroupSettingsId settingsId,
			final ProductCategoryId categoryId,
			final ProductId ownProductId,
			final ProductId... customerDiscountProductIds)
	{
		return addTakeOver(settingsId, categoryId, ownProductId, 2, ImmutableList.of(), customerDiscountProductIds);
	}

	private static I_C_CompensationGroup_ContractSettings_TakeOver addTakeOver(
			final ContractCompensationGroupSettingsId settingsId,
			final ProductCategoryId categoryId,
			final ProductId ownProductId,
			final int percentagePrecision,
			final List<ConditionsId> refundConditionsIds,
			final ProductId... customerDiscountProductIds)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver takeOver = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		takeOver.setC_CompensationGroup_ContractSettings_ID(settingsId.getRepoId());
		takeOver.setM_Product_Category_ID(categoryId.getRepoId());
		takeOver.setM_Product_ID(ownProductId.getRepoId());
		takeOver.setPercentagePrecision(percentagePrecision);
		saveRecord(takeOver);

		for (final ConditionsId conditionsId : refundConditionsIds)
		{
			final I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond refundCond = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class);
			refundCond.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOver.getC_CompensationGroup_ContractSettings_TakeOver_ID());
			refundCond.setC_Flatrate_Conditions_ID(conditionsId.getRepoId());
			saveRecord(refundCond);
		}

		for (final ProductId productId : customerDiscountProductIds)
		{
			final I_C_CompensationGroup_ContractSettings_TakeOver_Product takeOverProduct = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
			takeOverProduct.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOver.getC_CompensationGroup_ContractSettings_TakeOver_ID());
			takeOverProduct.setM_Product_ID(productId.getRepoId());
			saveRecord(takeOverProduct);
		}
		return takeOver;
	}

	/** One qualifying refund contract of {@code conditionsId} that the stubbed refund lookup returns. */
	private void prepareRefund(final ConditionsId conditionsId, final String conditionsName, final String percent)
	{
		preparedRefunds.put(conditionsId, TakenOverRefund.builder()
				.termId(FlatrateTermId.ofRepoId(nextRefundTermRepoId++))
				.conditionsId(conditionsId)
				.conditionsName(conditionsName)
				.percent(Percent.of(percent))
				.build());
	}

	/** Settings with one take-over of {@link #CATEGORY_ID} listing {@code refundConditionsIds}, with {@code percentagePrecision} decimals. */
	private ContractCompensationGroupSettings createSettingsWithRefunds(
			@Nullable final I_InvoiceProcessingServiceCompany passOnConfig,
			final int percentagePrecision,
			final List<ConditionsId> refundConditionsIds,
			final ProductId... customerDiscountProductIds)
	{
		final ContractCompensationGroupSettingsId settingsId = createSettingsRecord(passOnConfig);
		addTakeOver(settingsId, CATEGORY_ID, OWN_PRODUCT_ID, percentagePrecision, refundConditionsIds, customerDiscountProductIds);
		return settingsRepository.getBySettingsId(settingsId);
	}

	/** A drop-ship purchase order whose linked sales order is keyed by {@link #CUSTOMER_ID} and {@link #SO_DATE}. */
	/** A drop-ship purchase order whose linked sales order is completed, so its refunds may be taken over. */
	private static OrderDropShipInfo dropShipPO(final I_C_Order salesOrder)
	{
		return purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE)
				.toBuilder()
				.refundTakeOverAllowed(true)
				.build();
	}

	/**
	 * An active service-company config record with {@code customerId}'s doc-type-null fee assignment.
	 * {@code null} {@code serviceCompanyId} = {@link #SERVICE_COMPANY_ID}; {@code null} {@code validFrom} = 2026-01-01, before {@link #SO_DATE}.
	 * {@code isAssignmentActive} only governs the customer's assignment on the config.
	 *
	 * @return the saved config record
	 */
	private I_InvoiceProcessingServiceCompany createFeeConfig(
			final BPartnerId customerId,
			final String feePercent,
			final String feeProductName,
			@Nullable final BPartnerId serviceCompanyId,
			@Nullable final LocalDate validFrom,
			final boolean isAssignmentActive)
	{
		final ProductId feeProductId = product(feeProductName, null, null);

		final I_InvoiceProcessingServiceCompany config = newInstance(I_InvoiceProcessingServiceCompany.class);
		config.setIsActive(true);
		config.setServiceCompany_BPartner_ID((serviceCompanyId != null ? serviceCompanyId : SERVICE_COMPANY_ID).getRepoId());
		config.setServiceInvoice_DocType_ID(222);
		config.setServiceFee_Product_ID(feeProductId.getRepoId());
		config.setValidFrom(TimeUtil.asTimestamp((validFrom != null ? validFrom : LocalDate.parse("2026-01-01")).atStartOfDay(ZONE)));
		saveRecord(config);

		final I_InvoiceProcessingServiceCompany_BPartnerAssignment assignment = newInstance(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class);
		assignment.setIsActive(isAssignmentActive);
		assignment.setInvoiceProcessingServiceCompany_ID(config.getInvoiceProcessingServiceCompany_ID());
		assignment.setC_BPartner_ID(customerId.getRepoId());
		assignment.setFeePercentageOfGrandTotal(new BigDecimal(feePercent));
		saveRecord(assignment);

		return config;
	}

	private static ContractCompensationGroupSettingsId createSettingsRecord(@Nullable final I_InvoiceProcessingServiceCompany passOnConfig)
	{
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);
		final I_C_CompensationGroup_ContractSettings settings = newInstance(I_C_CompensationGroup_ContractSettings.class);
		settings.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		if (passOnConfig != null)
		{
			settings.setPassOn_InvoiceProcessingServiceCompany_ID(passOnConfig.getInvoiceProcessingServiceCompany_ID());
		}
		saveRecord(settings);
		return ContractCompensationGroupSettingsId.ofRepoId(settings.getC_CompensationGroup_ContractSettings_ID());
	}

	private static OrderDropShipInfo purchaseOrder(final boolean isDropShip, @Nullable final OrderId linkedOrderId)
	{
		return OrderDropShipInfo.builder()
				.soTrx(SOTrx.PURCHASE)
				.isDropShip(isDropShip)
				.linkedOrderId(linkedOrderId)
				.build();
	}

	/** A drop-ship purchase order carrying the linked sales order's fee key (invoice partner + date). */
	private static OrderDropShipInfo purchaseOrderWithFeeKey(final OrderId linkedOrderId, final BPartnerId invoicePartnerId, final ZonedDateTime soDate)
	{
		return OrderDropShipInfo.builder()
				.soTrx(SOTrx.PURCHASE)
				.isDropShip(true)
				.linkedOrderId(linkedOrderId)
				.invoicePartnerId(invoicePartnerId)
				.soDate(soDate)
				.build();
	}

	private I_C_Order createSalesOrderWithLines(final LineSpec... lines)
	{
		final I_C_Order so = newInstance(I_C_Order.class);
		so.setIsSOTrx(true);
		so.setC_BPartner_ID(1);
		saveRecord(so);

		final I_C_Order_CompensationGroup group = createGroupWithRegularLine(so, FlatrateTermId.ofRepoId(5));
		for (final LineSpec spec : lines)
		{
			createCompensationLine(so, group, spec);
		}
		return so;
	}

	private void addContractGroupWithDiscountLine(final I_C_Order so, final LineSpec spec)
	{
		final I_C_Order_CompensationGroup contractGroup = createGroupWithRegularLine(so, FlatrateTermId.ofRepoId(6));
		createCompensationLine(so, contractGroup, spec);
	}

	private void addManualGroupWithDiscountLine(final I_C_Order so, final LineSpec spec)
	{
		final I_C_Order_CompensationGroup manualGroup = createGroupWithRegularLine(so, null);
		createCompensationLine(so, manualGroup, spec);
	}

	private I_C_Order_CompensationGroup createGroupWithRegularLine(final I_C_Order so, @Nullable final FlatrateTermId flatrateTermId)
	{
		final I_C_Order_CompensationGroup group = newInstance(I_C_Order_CompensationGroup.class);
		group.setC_Order_ID(so.getC_Order_ID());
		group.setC_Flatrate_Term_ID(flatrateTermId != null ? flatrateTermId.getRepoId() : -1);
		saveRecord(group);

		final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
		regularLine.setC_Order_ID(so.getC_Order_ID());
		regularLine.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
		regularLine.setM_Product_ID(goodsProductId.getRepoId());
		regularLine.setC_UOM_ID(uomId.getRepoId());
		regularLine.setLine(10);
		regularLine.setLineNetAmt(new BigDecimal("100"));
		saveRecord(regularLine);
		return group;
	}

	private void createCompensationLine(final I_C_Order so, final I_C_Order_CompensationGroup group, final LineSpec spec)
	{
		final I_C_OrderLine line = newInstance(I_C_OrderLine.class);
		line.setC_Order_ID(so.getC_Order_ID());
		line.setC_Order_CompensationGroup_ID(group.getC_Order_CompensationGroup_ID());
		line.setM_Product_ID(spec.productId.getRepoId());
		line.setC_UOM_ID(uomId.getRepoId());
		line.setLine(20);
		line.setIsGroupCompensationLine(true);
		line.setGroupCompensationType(spec.compensationType);
		line.setGroupCompensationAmtType(spec.amtType);
		line.setGroupCompensationPercentage(new BigDecimal(spec.percentage));
		saveRecord(line);
	}

	private static final class LineSpec
	{
		final ProductId productId;
		final String compensationType;
		final String amtType;
		final String percentage;

		LineSpec(final ProductId productId, final String percentage)
		{
			this(productId, X_C_OrderLine.GROUPCOMPENSATIONTYPE_Discount, X_C_OrderLine.GROUPCOMPENSATIONAMTTYPE_Percent, percentage);
		}

		LineSpec(final ProductId productId, final String compensationType, final String amtType, final String percentage)
		{
			this.productId = productId;
			this.compensationType = compensationType;
			this.amtType = amtType;
			this.percentage = percentage;
		}
	}
}
