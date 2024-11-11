package de.metas.purchasecandidate;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.interfaces.I_C_BPartner;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.money.grossprofit.ProfitPriceActualFactory;
import de.metas.order.OrderAndLineId;
import de.metas.order.grossprofit.OrderLineWithGrossProfitPriceRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfoUpdateRequest;
import de.metas.payment.grossprofit.PaymentProfitPriceActualComponentProvider;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.pricing.conditions.BreakValueType;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.document.dimension.PurchaseCandidateDimensionFactory;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoService;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoServiceImpl;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import de.metas.quantity.Quantity;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_M_DiscountSchema;
import org.compiere.model.X_M_DiscountSchemaBreak;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, PaymentTermService.class })
public class PurchaseDemandWithCandidatesServiceTest
{
	private static final BigDecimal NINE = new BigDecimal("9");

	private static final BigDecimal TWENTY = new BigDecimal("20");

	/**
	 * purchase order quantity
	 */
	private static final BigDecimal PO_QTY_ORDERED_ONE = new BigDecimal("1");

	/**
	 * sales order quantity
	 */
	private static final BigDecimal SO_QTY_ORDERED_TEN = new BigDecimal("10");

	private static final BigDecimal QTY_TO_PURCHASE_NINE = NINE;

	private I_C_UOM uomRecord;
	private CurrencyId currencyId;
	private PurchaseDemandWithCandidatesService purchaseDemandWithCandidatesService;

	private I_C_PurchaseCandidate purchaseCandidateRecord;
	private OrgId orgId;
	private PurchaseDemand purchaseDemand;

	private I_M_DiscountSchemaBreak discountSchemaBreakRecord;

	private I_C_PaymentTerm paymentTermRecord;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);

		currencyId = PlainCurrencyDAO.prepareCurrency()
				.currencyCode(CurrencyCode.EUR)
				.precision(CurrencyPrecision.TWO)
				.build()
				.getId();

		final I_C_OrderLine salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setC_OrderLine_ID(10);
		salesOrderLineRecord.setC_Currency_ID(currencyId.getRepoId());
		salesOrderLineRecord.setProfitPriceActual(TWENTY);
		saveRecord(salesOrderLineRecord);

		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		productCategory.setName("productCategory.Name");
		saveRecord(productCategory);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setValue("product.Value");
		productRecord.setName("product.Name");
		productRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		productRecord.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		saveRecord(productRecord);

		final I_M_DiscountSchema discountSchemaRecord = newInstance(I_M_DiscountSchema.class);
		discountSchemaRecord.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_Breaks);
		discountSchemaRecord.setBreakValueType(BreakValueType.QUANTITY.getCode());
		discountSchemaRecord.setValidFrom(Timestamp.valueOf("2017-01-01 10:10:10.0"));
		saveRecord(discountSchemaRecord);

		paymentTermRecord = newInstance(I_C_PaymentTerm.class);
		saveRecord(paymentTermRecord);

		discountSchemaBreakRecord = newInstance(I_M_DiscountSchemaBreak.class);
		discountSchemaBreakRecord.setM_DiscountSchema(discountSchemaRecord);
		discountSchemaBreakRecord.setC_PaymentTerm_ID(paymentTermRecord.getC_PaymentTerm_ID());
		discountSchemaBreakRecord.setPaymentDiscount(TEN);
		discountSchemaBreakRecord.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_Fixed);
		discountSchemaBreakRecord.setPriceStdFixed(TEN);
		discountSchemaBreakRecord.setIsValid(true); // invalid records will be ignored
		discountSchemaBreakRecord.setC_Currency_ID(currencyId.getRepoId());
		saveRecord(discountSchemaBreakRecord);

		final I_C_BPartner vendorRecord = newInstance(I_C_BPartner.class);
		vendorRecord.setPO_DiscountSchema(discountSchemaRecord);
		saveRecord(vendorRecord);

		orgId = OrgId.ofRepoId(1000000);
		Services.get(IOrgDAO.class).createOrUpdateOrgInfo(OrgInfoUpdateRequest.builder()
																  .orgId(orgId)
																  .build());

		purchaseCandidateRecord = newInstance(I_C_PurchaseCandidate.class);
		purchaseCandidateRecord.setAD_Org_ID(orgId.getRepoId());
		purchaseCandidateRecord.setVendor_ID(vendorRecord.getC_BPartner_ID());
		purchaseCandidateRecord.setM_WarehousePO_ID(30);
		purchaseCandidateRecord.setM_Product_ID(productRecord.getM_Product_ID());
		purchaseCandidateRecord.setDemandReference("DemandReference");
		purchaseCandidateRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		purchaseCandidateRecord.setQtyToPurchase(QTY_TO_PURCHASE_NINE);
		purchaseCandidateRecord.setPurchaseDatePromised(SystemTime.asTimestamp());
		saveRecord(purchaseCandidateRecord);

		final I_C_OrderLine purchaseOrderLineRecord = salesOrderLineRecord;
		purchaseOrderLineRecord.setQtyOrdered(PO_QTY_ORDERED_ONE);
		purchaseOrderLineRecord.setM_Product_ID(productRecord.getM_Product_ID());
		purchaseOrderLineRecord.setC_Order_ID(40);
		saveRecord(purchaseOrderLineRecord);

		final I_C_PurchaseCandidate_Alloc purchaseCandidateAllocRecord = newInstance(I_C_PurchaseCandidate_Alloc.class);
		purchaseCandidateAllocRecord.setC_PurchaseCandidate(purchaseCandidateRecord);
		purchaseCandidateAllocRecord.setC_OrderPO_ID(purchaseOrderLineRecord.getC_Order_ID());
		purchaseCandidateAllocRecord.setC_OrderLinePO(purchaseOrderLineRecord);
		purchaseCandidateAllocRecord.setDatePromised(SystemTime.asTimestamp());
		purchaseCandidateAllocRecord.setRemotePurchaseOrderId(NullVendorGatewayInvoker.NO_REMOTE_PURCHASE_ID);
		saveRecord(purchaseCandidateAllocRecord);

		final BPPurchaseScheduleService bpPurchaseScheduleService = new BPPurchaseScheduleService(new BPPurchaseScheduleRepository());

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new PurchaseCandidateDimensionFactory());

		final DimensionService dimensionService = new DimensionService(dimensionFactories);

		final CurrencyRepository currencyRepository = new CurrencyRepository();

		final PurchaseCandidateRepository purchaseCandidateRepository = new PurchaseCandidateRepository(
				new PurchaseItemRepository(),
				new ReferenceGenerator(),
				bpPurchaseScheduleService,
				dimensionService
		);

		final MoneyService moneyService = new MoneyService(currencyRepository);
		final ProfitPriceActualFactory profitPriceActualFactory = new ProfitPriceActualFactory(Optional.of(ImmutableList.of(new PaymentProfitPriceActualComponentProvider(moneyService))));

		final PurchaseProfitInfoService purchaseProfitInfoService = new PurchaseProfitInfoServiceImpl(
				moneyService,
				new OrderLineWithGrossProfitPriceRepository(),
				profitPriceActualFactory);

		final VendorProductInfoService vendorProductInfoService = new VendorProductInfoService(new BPartnerBL(new UserRepository()));

		purchaseDemandWithCandidatesService = new PurchaseDemandWithCandidatesService(
				purchaseCandidateRepository,
				bpPurchaseScheduleService,
				vendorProductInfoService,
				purchaseProfitInfoService);

		purchaseDemand = PurchaseDemand.builder()
				.orgId(orgId)
				.warehouseId(WarehouseId.ofRepoId(540008))
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.qtyToDeliver(Quantity.of(SO_QTY_ORDERED_TEN, uomRecord))
				.currencyIdOrNull(currencyId)
				.salesPreparationDate(ZonedDateTime.now())
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(salesOrderLineRecord.getC_Order_ID(), salesOrderLineRecord.getC_OrderLine_ID()))
				.existingPurchaseCandidateId(PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID()))
				.build();

	}

	@Test
	public void getExistingPurchaseCandidatesGroups_purchasecandidate_processed()
	{
		final boolean processed = true;
		getExistingPurchaseCandidatesGroups(processed);
	}

	@Test
	public void getExistingPurchaseCandidatesGroups_purchasecandidate_unprocessed()
	{
		final boolean processed = false;
		getExistingPurchaseCandidatesGroups(processed);
	}

	private void getExistingPurchaseCandidatesGroups(final boolean processed)
	{
		purchaseCandidateRecord.setProcessed(processed);
		saveRecord(purchaseCandidateRecord);

		// invoke the method under test
		final ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> result = purchaseDemandWithCandidatesService.getExistingPurchaseCandidatesGroups(ImmutableList.of(purchaseDemand));

		assertThat(result).isNotNull();

		final ImmutableList<PurchaseCandidatesGroup> candidatesGroups = result.get(purchaseDemand);
		assertThat(candidatesGroups).hasSize(1);

		final PurchaseCandidatesGroup candidatesGroup = candidatesGroups.get(0);
		assertThat(candidatesGroup.getOrgId()).isEqualTo(orgId);
		assertThat(candidatesGroup.getPurchaseCandidateIds()).containsOnly(PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID()));
		assertThat(candidatesGroup.getPurchasedQty().toBigDecimal()).isEqualByComparingTo(PO_QTY_ORDERED_ONE);
		assertThat(candidatesGroup.getQtyToPurchase().toBigDecimal()).isEqualByComparingTo(QTY_TO_PURCHASE_NINE);
		assertThat(candidatesGroup.getPurchasedQty().getUOM()).isEqualTo(uomRecord);
		assertThat(candidatesGroup.isReadonly()).isEqualTo(processed);
	}

	@Test
	public void createMissingPurchaseCandidatesGroups()
	{
		// invoke the method under test
		final ImmutableListMultimap<PurchaseDemand, PurchaseCandidatesGroup> //
				result = purchaseDemandWithCandidatesService.createMissingPurchaseCandidatesGroups(ImmutableList.of(purchaseDemand), ImmutableMap.of());

		assertThat(result).isNotNull();

		final ImmutableList<PurchaseCandidatesGroup> purchaseCandidatesGroups = result.get(purchaseDemand);
		assertThat(purchaseCandidatesGroups).hasSize(1);

		final PurchaseCandidatesGroup candidatesGroup = purchaseCandidatesGroups.get(0);

		assertThat(candidatesGroup.getOrgId()).isEqualTo(orgId);
		assertThat(candidatesGroup.getPurchaseCandidateIds()).isEmpty();
		assertThat(candidatesGroup.getPurchasedQty().toBigDecimal()).isEqualByComparingTo(ZERO);
		assertThat(candidatesGroup.getQtyToPurchase().toBigDecimal()).isEqualByComparingTo(ZERO);
		assertThat(candidatesGroup.getPurchasedQty().getUOM()).isEqualTo(uomRecord);
		assertThat(candidatesGroup.isReadonly()).isEqualTo(false);

		final PurchaseProfitInfo profitInfo = candidatesGroup.getProfitInfoOrNull();
		assertThat(profitInfo).isNotNull();
		assertThat(profitInfo.getCommonCurrency()).isEqualTo(currencyId);
		assertThat(profitInfo.getPurchasePriceActual()).isPresent();
		assertThat(profitInfo.getPurchasePriceActual()).hasValue(Money.of(TEN, currencyId)); // coming from the discount schema break
		assertThat(profitInfo.getProfitSalesPriceActual()).isPresent();
		assertThat(profitInfo.getProfitSalesPriceActual()).hasValue(Money.of(TWENTY, currencyId)); // coming from the sales order line record
		assertThat(profitInfo.getProfitPurchasePriceActual()).isPresent();
		assertThat(profitInfo.getProfitPurchasePriceActual()).hasValue(Money.of(NINE, currencyId)); // coming from the discount schema break (10% payment discount!)
	}

	/**
	 * Runs {@link PurchaseDemandWithCandidatesService#getOrCreatePurchaseCandidatesGroups(List)}
	 * with a processed {@link I_C_PurchaseCandidate} in the background.
	 * Expects the system to return two candidate groups: a read-only representation of the processed purchase candidate,
	 * but also a "new" group which the user can then edit.
	 */
	@Test
	public void getOrCreatePurchaseCandidatesGroups_purchasecandidate_processed()
	{
		purchaseCandidateRecord.setProcessed(true);
		saveRecord(purchaseCandidateRecord);

		// invoke the method under test
		final List<PurchaseDemandWithCandidates> result = purchaseDemandWithCandidatesService.getOrCreatePurchaseCandidatesGroups(ImmutableList.of(purchaseDemand));

		assertThat(result).isNotNull().hasSize(1);

		final PurchaseDemandWithCandidates purchaseDemandWithCandidates = result.get(0);
		assertThat(purchaseDemandWithCandidates.getPurchaseDemand()).isEqualTo(purchaseDemand);

		final List<PurchaseCandidatesGroup> purchaseCandidatesGroups = purchaseDemandWithCandidates.getPurchaseCandidatesGroups();
		assertThat(purchaseCandidatesGroups).isNotNull().hasSize(2); // one group for the "1" we already ordered, one of the 9 we still need to order

		// the new group needs to inherit the older group's reference
		// TODO
		// assertThat(purchaseCandidatesGroups.get(0).getDemandGroupReferences()).hasSize(1);
		// assertThat(purchaseCandidatesGroups.get(1).getDemandGroupReferences()).hasSize(1);
		// assertThat(purchaseCandidatesGroups.get(0).getDemandGroupReferences().get(0)).isEqualTo(purchaseCandidatesGroups.get(1).getDemandGroupReferences().get(0));

		assertThat(purchaseCandidatesGroups)
				.filteredOn(g -> g.isReadonly())
				.hasSize(1)
				.allSatisfy(candidatesGroup -> {
					assertThat(candidatesGroup.getOrgId()).isEqualTo(orgId);
					assertThat(candidatesGroup.getPurchaseCandidateIds()).containsOnly(PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID()));
					assertThat(candidatesGroup.getPurchasedQty().toBigDecimal()).isEqualByComparingTo(PO_QTY_ORDERED_ONE);
					assertThat(candidatesGroup.getQtyToPurchase().toBigDecimal()).isEqualByComparingTo(QTY_TO_PURCHASE_NINE); // the 9 is loaded from out candidate record.
					assertThat(candidatesGroup.getPurchasedQty().getUOM()).isEqualTo(uomRecord);
					assertThat(candidatesGroup.isReadonly()).isEqualTo(true);
				});

		assertThat(purchaseCandidatesGroups)
				.filteredOn(g -> !g.isReadonly())
				.hasSize(1)
				.allSatisfy(candidatesGroup -> {
					assertThat(candidatesGroup.getOrgId()).isEqualTo(orgId);
					assertThat(candidatesGroup.getPurchaseCandidateIds()).isEmpty();
					assertThat(candidatesGroup.getPurchasedQty().toBigDecimal()).isEqualByComparingTo(ZERO);
					assertThat(candidatesGroup.getQtyToPurchase().toBigDecimal()).isEqualByComparingTo(ZERO);
					assertThat(candidatesGroup.getPurchasedQty().getUOM()).isEqualTo(uomRecord);
					assertThat(candidatesGroup.isReadonly()).isEqualTo(false);
				});
	}

	/**
	 * Runs {@link PurchaseDemandWithCandidatesService#getOrCreatePurchaseCandidatesGroups(List)}
	 * with an unprocessed {@link I_C_PurchaseCandidate} in the background.
	 * Expects the system to return one candidate group which the user can then edit.
	 */
	@Test
	public void getOrCreatePurchaseCandidatesGroups_purchasecandidate_unprocessed()
	{
		// invoke the method under test
		final List<PurchaseDemandWithCandidates> result = purchaseDemandWithCandidatesService.getOrCreatePurchaseCandidatesGroups(ImmutableList.of(purchaseDemand));

		assertThat(result).isNotNull().hasSize(1);

		final PurchaseDemandWithCandidates purchaseDemandWithCandidates = result.get(0);
		assertThat(purchaseDemandWithCandidates.getPurchaseDemand()).isEqualTo(purchaseDemand);

		final List<PurchaseCandidatesGroup> purchaseCandidatesGroups = purchaseDemandWithCandidates.getPurchaseCandidatesGroups();
		assertThat(purchaseCandidatesGroups).isNotNull().hasSize(1);

		final PurchaseCandidatesGroup candidatesGroup = purchaseCandidatesGroups.get(0);
		assertThat(candidatesGroup.isReadonly()).isFalse();
		assertThat(candidatesGroup.getPurchasedQty().toBigDecimal()).isEqualByComparingTo(PO_QTY_ORDERED_ONE);
		assertThat(candidatesGroup.getQtyToPurchase().toBigDecimal()).isEqualByComparingTo(QTY_TO_PURCHASE_NINE);
	}

}
