package de.metas.purchasecandidate;

import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.OrgId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.X_M_DiscountSchema;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

import de.metas.adempiere.model.I_M_Product;
import de.metas.interfaces.I_C_BPartner;
import de.metas.money.Currency;
import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.money.MoneyService;
import de.metas.order.OrderAndLineId;
import de.metas.order.grossprofit.OrderLineWithGrossProfitPriceRepository;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoService;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfoServiceImpl;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate_Alloc;
import de.metas.purchasecandidate.purchaseordercreation.remoteorder.NullVendorGatewayInvoker;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import de.metas.quantity.Quantity;

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

public class PurchaseDemandWithCandidatesServiceTest
{
	// private static final BigDecimal QTY_ORDERED_TEN = new BigDecimal("10");

	/** purchase order quantity */
	private static final BigDecimal PO_QTY_ORDERED_ONE = new BigDecimal("1");

	/** sales order quantity */
	private static final BigDecimal SO_QTY_ORDERED_TEN = new BigDecimal("10");

	private static final BigDecimal QTY_TO_PURCHASE_NINE = new BigDecimal("9");

	private I_C_UOM uom;
	private Currency currency;
	private PurchaseDemandWithCandidatesService purchaseDemandWithCandidatesService;

	private I_C_PurchaseCandidate purchaseCandidateRecord;
	private OrgId orgId;
	private PurchaseDemand purchaseDemand;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		currency = Currency.builder().id(CurrencyId.ofRepoId(102)).precision(2).threeLetterCode("EUR").build();
		OrderAndLineId.ofRepoIds(1000001, 1000002);

		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		productCategory.setName("productCategory.Name");
		saveRecord(productCategory);

		final I_M_Product productRecord = newInstance(I_M_Product.class);
		productRecord.setValue("product.Value");
		productRecord.setName("product.Name");
		productRecord.setC_UOM(uom);
		productRecord.setM_Product_Category(productCategory);
		saveRecord(productRecord);

		final I_M_DiscountSchema discountSchema = newInstance(I_M_DiscountSchema.class);
		discountSchema.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_FlatPercent);
		saveRecord(discountSchema);

		final I_C_BPartner vendor = newInstance(I_C_BPartner.class);
		vendor.setPO_DiscountSchema(discountSchema);
		saveRecord(vendor);

		orgId = OrgId.ofRepoId(1000000);

		purchaseCandidateRecord = newInstance(I_C_PurchaseCandidate.class);
		purchaseCandidateRecord.setAD_Org_ID(orgId.getRepoId());
		purchaseCandidateRecord.setVendor(vendor);
		purchaseCandidateRecord.setM_WarehousePO_ID(30);
		purchaseCandidateRecord.setM_Product(productRecord);
		purchaseCandidateRecord.setDemandReference("DemandReference");
		purchaseCandidateRecord.setC_UOM(uom);
		purchaseCandidateRecord.setQtyToPurchase(QTY_TO_PURCHASE_NINE);
		purchaseCandidateRecord.setPurchaseDatePromised(SystemTime.asTimestamp());
		saveRecord(purchaseCandidateRecord);

		final I_C_OrderLine purchaseOrderLineRecord = newInstance(I_C_OrderLine.class);
		purchaseOrderLineRecord.setQtyOrdered(PO_QTY_ORDERED_ONE);
		purchaseOrderLineRecord.setM_Product(productRecord);
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

		final CurrencyRepository currencyRepository = new CurrencyRepository();

		final PurchaseCandidateRepository purchaseCandidateRepository = new PurchaseCandidateRepository(
				new PurchaseItemRepository(),
				new ReferenceGenerator(),
				bpPurchaseScheduleService);

		final PurchaseProfitInfoService purchaseProfitInfoService = new PurchaseProfitInfoServiceImpl(
				new MoneyService(currencyRepository),
				new OrderLineWithGrossProfitPriceRepository());

		final VendorProductInfoService vendorProductInfoService = new VendorProductInfoService();

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
				.qtyToDeliver(Quantity.of(SO_QTY_ORDERED_TEN, uom))
				.currencyIdOrNull(currency.getId())
				.salesPreparationDate(LocalDateTime.now())
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(1000001, 1000002))
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
		assertThat(candidatesGroup.getPurchasedQty().getAsBigDecimal()).isEqualByComparingTo(PO_QTY_ORDERED_ONE);
		assertThat(candidatesGroup.getQtyToPurchase().getAsBigDecimal()).isEqualByComparingTo(QTY_TO_PURCHASE_NINE);
		assertThat(candidatesGroup.getPurchasedQty().getUOM()).isEqualTo(uom);
		assertThat(candidatesGroup.isReadonly()).isEqualTo(processed);
	}

	@Test
	public void createMissingPurchaseCandidatesGroups()
	{
		POJOLookupMap.get().delete(purchaseCandidateRecord);

		// invoke the method under test
		final List<PurchaseDemandWithCandidates> result = purchaseDemandWithCandidatesService.getOrCreatePurchaseCandidatesGroups(ImmutableList.of(purchaseDemand));

		assertThat(result).isNotNull().hasSize(1);

		final PurchaseDemandWithCandidates purchaseDemandWithCandidates = result.get(0);
		assertThat(purchaseDemandWithCandidates.getPurchaseDemand()).isEqualTo(purchaseDemand);

		final List<PurchaseCandidatesGroup> purchaseCandidatesGroups = purchaseDemandWithCandidates.getPurchaseCandidatesGroups();
		final PurchaseCandidatesGroup candidatesGroup = purchaseCandidatesGroups.get(0);

		assertThat(candidatesGroup.getOrgId()).isEqualTo(orgId);
		assertThat(candidatesGroup.getPurchaseCandidateIds()).isEmpty();
		assertThat(candidatesGroup.getPurchasedQty().getAsBigDecimal()).isEqualByComparingTo(ZERO);
		assertThat(candidatesGroup.getQtyToPurchase().getAsBigDecimal()).isEqualByComparingTo(ZERO);
		assertThat(candidatesGroup.getPurchasedQty().getUOM()).isEqualTo(uom);
		assertThat(candidatesGroup.isReadonly()).isEqualTo(false);

		final PurchaseProfitInfo profitInfo = candidatesGroup.getProfitInfoOrNull();
		assertThat(profitInfo).isNotNull();
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

		assertThat(purchaseCandidatesGroups)
				.filteredOn(g -> g.isReadonly())
				.hasSize(1)
				.allSatisfy(candidatesGroup -> {
					assertThat(candidatesGroup.getOrgId()).isEqualTo(orgId);
					assertThat(candidatesGroup.getPurchaseCandidateIds()).containsOnly(PurchaseCandidateId.ofRepoId(purchaseCandidateRecord.getC_PurchaseCandidate_ID()));
					assertThat(candidatesGroup.getPurchasedQty().getAsBigDecimal()).isEqualByComparingTo(PO_QTY_ORDERED_ONE);
					assertThat(candidatesGroup.getQtyToPurchase().getAsBigDecimal()).isEqualByComparingTo(QTY_TO_PURCHASE_NINE); // the 9 is loaded from out candidate record.
					assertThat(candidatesGroup.getPurchasedQty().getUOM()).isEqualTo(uom);
					assertThat(candidatesGroup.isReadonly()).isEqualTo(true);
				});

		assertThat(purchaseCandidatesGroups)
				.filteredOn(g -> !g.isReadonly())
				.hasSize(1)
				.allSatisfy(candidatesGroup -> {
					assertThat(candidatesGroup.getOrgId()).isEqualTo(orgId);
					assertThat(candidatesGroup.getPurchaseCandidateIds()).isEmpty();
					assertThat(candidatesGroup.getPurchasedQty().getAsBigDecimal()).isEqualByComparingTo(ZERO);
					assertThat(candidatesGroup.getQtyToPurchase().getAsBigDecimal()).isEqualByComparingTo(ZERO);
					assertThat(candidatesGroup.getPurchasedQty().getUOM()).isEqualTo(uom);
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
		assertThat(candidatesGroup.getPurchasedQty().getAsBigDecimal()).isEqualByComparingTo(PO_QTY_ORDERED_ONE);
		assertThat(candidatesGroup.getQtyToPurchase().getAsBigDecimal()).isEqualByComparingTo(QTY_TO_PURCHASE_NINE);
	}

}
