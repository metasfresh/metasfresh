package de.metas.contracts.compensationGroup.contract;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver;
import de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver_Product;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfigRepository;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.lang.SOTrx;
import de.metas.money.MoneyService;
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
import org.compiere.SpringContextHolder;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

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

	private static final ZoneId ZONE = ZoneId.of("UTC");
	private static final BPartnerId CUSTOMER_ID = BPartnerId.ofRepoId(2); // the linked SO's invoice partner the fee is keyed by
	private static final ZonedDateTime SO_DATE = LocalDate.parse("2026-05-10").atStartOfDay(ZONE);

	private ContractCompensationGroupSettingsRepository settingsRepository;
	private OrderGroupRepository orderGroupRepositorySpy;
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

		final CurrencyRepository currencyRepo = new CurrencyRepository();
		SpringContextHolder.registerJUnitBean(currencyRepo);
		final ContractServiceFeeTakeOverService feeService = new ContractServiceFeeTakeOverService(new InvoiceProcessingServiceCompanyService(
				new InvoiceProcessingServiceCompanyConfigRepository(),
				new MoneyService(currencyRepo)));

		service = new ContractSettingsTakeOverService(ContractSettingsTakeOverRepository.newInstanceForUnitTesting(), orderGroupRepositorySpy, feeService);

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
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
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
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
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
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
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
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
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
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID, BONUS_VERPACKUNG_ID, OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
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
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3.0"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(false, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void dropShipSalesOrder_returnsEmpty()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
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
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, null);

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void settingsWithoutTakeOver_returnsEmptyWithoutReadingTheLinkedSalesOrder()
	{
		final ContractCompensationGroupSettings settings = settingsRepository.getBySettingsId(createSettingsRecord());
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(BONUS_WARE_ID, "3.0"));

		assertThat(service.computeMatches(purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID())), settings)).isEmpty();
		Mockito.verify(orderGroupRepositorySpy, Mockito.never()).retrieveContractCreatedGroupsByOrderId(any());
	}

	@Test
	void recordWhoseCustomerDiscountProductsAreNotOnTheSalesOrder_isDropped()
	{
		final ContractCompensationGroupSettings settings = createSettings(OTHER_CUSTOMER_DISCOUNT_PRODUCT_ID);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(BONUS_WARE_ID, "3.0"),
				new LineSpec(BONUS_VERPACKUNG_ID, "7.0"));
		final OrderDropShipInfo purchaseOrder = purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID()));

		assertThat(service.computeMatches(purchaseOrder, settings)).isEmpty();
	}

	@Test
	void applyToSchema_nothingTakenOver_returnsTheSameSchema()
	{
		final ContractCompensationGroupSettings settings = createSettings(BONUS_WARE_ID);
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
		final ContractCompensationGroupSettings settings = createSettings(bonusWareAId, bonusWareBId);
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
		final ContractCompensationGroupSettings settings = createSettings(bonusWareId);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
		addContractGroupWithDiscountLine(salesOrder, new LineSpec(bonusWareId, "3"));

		final List<GroupTemplateCompensationLine> lines = service.applyToSchema(schema(), purchaseOrder(true, OrderId.ofRepoId(salesOrder.getC_Order_ID())), settings)
				.getCompensationLines();

		assertThat(lines).hasSize(1);
		assertThat(lines.get(0).getPercentage()).isEqualTo(Percent.of(6));
		assertThat(lines.get(0).getDescription()).isEqualTo("3% Bonus Ware + 3% Bonus Ware");
	}

	@Test
	void applyToSchema_singleMatchWithServiceFee_foldsFeeIntoPercentageAndDescription()
	{
		final ProductId bonusWareId = product("Bonus Ware", null, null);
		final ContractCompensationGroupSettings settings = createSettings(bonusWareId);
		final I_C_Order salesOrder = createSalesOrderWithLines(new LineSpec(bonusWareId, "3"));
		createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee");

		final List<GroupTemplateCompensationLine> lines = service.applyToSchema(
						schema(),
						purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE),
						settings)
				.getCompensationLines();

		assertThat(lines).hasSize(1);
		assertThat(lines.get(0).getPercentage().toBigDecimal()).isEqualByComparingTo("5.60");
		assertThat(lines.get(0).getDescription()).isEqualTo("3% Bonus Ware + 2.6% Payment Service Fee");
	}

	@Test
	void applyToSchema_singleMatchWithoutServiceFee_leavesPercentageAndDescriptionUnchanged()
	{
		final ProductId bonusWareId = product("Bonus Ware", null, null);
		final ContractCompensationGroupSettings settings = createSettings(bonusWareId);
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
		final ContractCompensationGroupSettings settings = createSettingsWithTwoTakeOvers(bonusWareAId, bonusWareBId);
		final I_C_Order salesOrder = createSalesOrderWithLines(
				new LineSpec(bonusWareAId, "3"),
				new LineSpec(bonusWareBId, "1"));
		createFeeConfig(CUSTOMER_ID, "2.60", "Payment Service Fee");

		final OrderDropShipInfo purchaseOrder = purchaseOrderWithFeeKey(OrderId.ofRepoId(salesOrder.getC_Order_ID()), CUSTOMER_ID, SO_DATE);

		assertThatThrownBy(() -> service.applyToSchema(schema(), purchaseOrder, settings))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void applyToSchema_multipleMatchesWithoutServiceFee_appendsBothLinesWithoutFee()
	{
		final ProductId bonusWareAId = product("Bonus Ware A", null, null);
		final ProductId bonusWareBId = product("Bonus Ware B", null, null);
		final ContractCompensationGroupSettings settings = createSettingsWithTwoTakeOvers(bonusWareAId, bonusWareBId);
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

	/** A drop-ship purchase order whose linked sales order carries a 3% "Bonus Ware" contract discount line that the take-over lists. */
	private List<GroupTemplateCompensationLine> applyBonusWareTakeOverOf3PercentTo(final GroupTemplateCompensationLine... schemaLines)
	{
		final ProductId bonusWareId = product("Bonus Ware", null, null);
		final ContractCompensationGroupSettings settings = createSettings(bonusWareId);
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

	private ContractCompensationGroupSettings createSettings(final ProductId... customerDiscountProductIds)
	{
		final ContractCompensationGroupSettingsId settingsId = createSettingsRecord();
		addTakeOver(settingsId, CATEGORY_ID, OWN_PRODUCT_ID, customerDiscountProductIds);
		return settingsRepository.getBySettingsId(settingsId);
	}

	/** Settings with two take-overs, so that a drop-ship PO carrying both products yields two matches. */
	private ContractCompensationGroupSettings createSettingsWithTwoTakeOvers(final ProductId takeOver1Product, final ProductId takeOver2Product)
	{
		final ContractCompensationGroupSettingsId settingsId = createSettingsRecord();
		addTakeOver(settingsId, CATEGORY_ID, OWN_PRODUCT_ID, takeOver1Product);
		addTakeOver(settingsId, OTHER_CATEGORY_ID, OWN_PRODUCT_ID, takeOver2Product);
		return settingsRepository.getBySettingsId(settingsId);
	}

	private static void addTakeOver(
			final ContractCompensationGroupSettingsId settingsId,
			final ProductCategoryId categoryId,
			final ProductId ownProductId,
			final ProductId... customerDiscountProductIds)
	{
		final I_C_CompensationGroup_ContractSettings_TakeOver takeOver = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver.class);
		takeOver.setC_CompensationGroup_ContractSettings_ID(settingsId.getRepoId());
		takeOver.setM_Product_Category_ID(categoryId.getRepoId());
		takeOver.setM_Product_ID(ownProductId.getRepoId());
		saveRecord(takeOver);

		for (final ProductId productId : customerDiscountProductIds)
		{
			final I_C_CompensationGroup_ContractSettings_TakeOver_Product takeOverProduct = newInstance(I_C_CompensationGroup_ContractSettings_TakeOver_Product.class);
			takeOverProduct.setC_CompensationGroup_ContractSettings_TakeOver_ID(takeOver.getC_CompensationGroup_ContractSettings_TakeOver_ID());
			takeOverProduct.setM_Product_ID(productId.getRepoId());
			saveRecord(takeOverProduct);
		}
	}

	/** An active, doc-type-null fee assignment for {@code customerId}, valid before {@link #SO_DATE}. @return the configured service-fee product. */
	private ProductId createFeeConfig(final BPartnerId customerId, final String feePercent, final String feeProductName)
	{
		final ProductId feeProductId = product(feeProductName, null, null);

		final I_InvoiceProcessingServiceCompany config = newInstance(I_InvoiceProcessingServiceCompany.class);
		config.setIsActive(true);
		config.setServiceCompany_BPartner_ID(111);
		config.setServiceInvoice_DocType_ID(222);
		config.setServiceFee_Product_ID(feeProductId.getRepoId());
		config.setValidFrom(TimeUtil.asTimestamp(LocalDate.parse("2026-01-01").atStartOfDay(ZONE)));
		saveRecord(config);

		final I_InvoiceProcessingServiceCompany_BPartnerAssignment assignment = newInstance(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class);
		assignment.setIsActive(true);
		assignment.setInvoiceProcessingServiceCompany_ID(config.getInvoiceProcessingServiceCompany_ID());
		assignment.setC_BPartner_ID(customerId.getRepoId());
		assignment.setFeePercentageOfGrandTotal(new BigDecimal(feePercent));
		saveRecord(assignment);

		return feeProductId;
	}

	private static ContractCompensationGroupSettingsId createSettingsRecord()
	{
		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);
		final I_C_CompensationGroup_ContractSettings settings = newInstance(I_C_CompensationGroup_ContractSettings.class);
		settings.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
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
