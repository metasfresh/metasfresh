package de.metas.ui.web.order.sales.purchasePlanning.view;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.dimension.DimensionService;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.money.CurrencyId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineRepository;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.product.IProductBL;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.BPPurchaseScheduleRepository;
import de.metas.purchasecandidate.BPPurchaseScheduleService;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseDemand;
import de.metas.purchasecandidate.PurchaseDemandWithCandidates;
import de.metas.purchasecandidate.PurchaseDemandWithCandidatesService;
import de.metas.purchasecandidate.ReferenceGenerator;
import de.metas.purchasecandidate.SalesOrderLine;
import de.metas.purchasecandidate.SalesOrderLineRepository;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.purchasecandidate.availability.AvailabilityCheckService;
import de.metas.purchasecandidate.availability.AvailabilityMultiResult;
import de.metas.purchasecandidate.availability.AvailabilityResult;
import de.metas.purchasecandidate.availability.AvailabilityResult.Type;
import de.metas.purchasecandidate.availability.PurchaseCandidatesAvailabilityRequest;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItemRepository;
import de.metas.quantity.Quantity;
import de.metas.ui.web.order.sales.purchasePlanning.view.PurchaseRowsLoader.PurchaseRowsList;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-webui-api
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

public class PurchaseRowsLoaderTest
{
	private AvailabilityCheckService availabilityCheckService;

	private I_M_Product product;
	private I_C_Order salesOrderRecord;
	private I_C_BPartner bPartnerVendor;

	private CurrencyId currencyId;

	private Quantity TEN;

	private I_M_Warehouse warehouse;

	private I_AD_Org org;

	private SalesOrder2PurchaseViewFactory salesOrder2PurchaseViewFactory;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		availabilityCheckService = Mockito.mock(AvailabilityCheckService.class);

		org = newInstance(I_AD_Org.class);
		saveRecord(org);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("testUOMSympol");
		saveRecord(uom);

		this.TEN = Quantity.of(BigDecimal.TEN, uom);

		warehouse = newInstance(I_M_Warehouse.class);
		saveRecord(warehouse);

		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		saveRecord(productCategory);

		product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);

		final I_C_BPartner bPartnerCustomer = newInstance(I_C_BPartner.class);
		bPartnerCustomer.setName("bPartnerCustomer.Name");
		saveRecord(bPartnerCustomer);

		salesOrderRecord = newInstance(I_C_Order.class);
		salesOrderRecord.setC_BPartner_ID(bPartnerCustomer.getC_BPartner_ID());
		salesOrderRecord.setPreparationDate(de.metas.common.util.time.SystemTime.asTimestamp());
		salesOrderRecord.setC_PaymentTerm_ID(30);
		saveRecord(salesOrderRecord);

		bPartnerVendor = newInstance(I_C_BPartner.class);
		bPartnerVendor.setName("bPartnerVendor.Name");
		saveRecord(bPartnerVendor);

		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final DimensionService dimensionService = Mockito.mock(DimensionService.class);
		// wire together a SalesOrder2PurchaseViewFactory
		final PurchaseCandidateRepository purchaseCandidateRepository = new PurchaseCandidateRepository(
				new PurchaseItemRepository(),
				new ReferenceGenerator(),
				new BPPurchaseScheduleService(new BPPurchaseScheduleRepository()),
				dimensionService);

		final DoNothingPurchaseProfitInfoServiceImpl purchaseProfitInfoService = new DoNothingPurchaseProfitInfoServiceImpl();

		final PurchaseDemandWithCandidatesService purchaseDemandWithCandidatesService = new PurchaseDemandWithCandidatesService(
				purchaseCandidateRepository,
				new BPPurchaseScheduleService(new BPPurchaseScheduleRepository()),
				new VendorProductInfoService(new BPartnerBL(new UserRepository())),
				purchaseProfitInfoService);

		final PurchaseRowFactory purchaseRowFactory = new PurchaseRowFactory(
				new AvailableToPromiseRepository(),
				purchaseProfitInfoService);

		salesOrder2PurchaseViewFactory = new SalesOrder2PurchaseViewFactory(
				purchaseDemandWithCandidatesService,
				availabilityCheckService, // mocked
				purchaseCandidateRepository,
				purchaseRowFactory,
				new SalesOrderLineRepository(new OrderLineRepository()));
	}

	@Test
	public void load_and_createAndAddAvailabilityResultRows()
	{
		//
		// set up salesOrderLineRecord
		final I_C_OrderLine salesOrderLineRecord = newInstance(I_C_OrderLine.class);
		salesOrderLineRecord.setAD_Org_ID(org.getAD_Org_ID());
		salesOrderLineRecord.setM_Product_ID(product.getM_Product_ID());
		salesOrderLineRecord.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		salesOrderLineRecord.setC_Order_ID(salesOrderRecord.getC_Order_ID());
		salesOrderLineRecord.setC_Currency_ID(currencyId.getRepoId());
		salesOrderLineRecord.setC_UOM_ID(TEN.getUomId().getRepoId());
		salesOrderLineRecord.setQtyEntered(TEN.toBigDecimal());
		salesOrderLineRecord.setQtyOrdered(TEN.toBigDecimal());
		salesOrderLineRecord.setDatePromised(SystemTime.asTimestamp());
		save(salesOrderLineRecord);

		final SalesOrderLineRepository salesOrderLineRepository = new SalesOrderLineRepository(new OrderLineRepository());
		final SalesOrderLine salesOrderLine = salesOrderLineRepository.ofRecord(salesOrderLineRecord);

		//
		// set up vendorProductInfo
		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.vendorId(BPartnerId.ofRepoId(bPartnerVendor.getC_BPartner_ID()))
				.defaultVendor(false)
				.product(ProductAndCategoryAndManufacturerId.of(product.getM_Product_ID(), product.getM_Product_Category_ID(), product.getManufacturer_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.vendorProductNo("bPartnerProduct.VendorProductNo")
				.vendorProductName("bPartnerProduct.ProductName")
				.pricingConditions(PricingConditions.builder()
										   .validFrom(TimeUtil.asInstant(Timestamp.valueOf("2017-01-01 10:10:10.0")))
										   .build())
				.build();

		//
		// create purchaseCandidate from salesOrderLineRecord and vendorProductInfo
		final PurchaseDemand demand = salesOrder2PurchaseViewFactory.createDemand(salesOrderLine);
		final PurchaseCandidate purchaseCandidate = createPurchaseCandidate(salesOrderLineRecord, vendorProductInfo);

		final ImmutableList<PurchaseDemandWithCandidates> demandWithCandidates = createPurchaseDemandWithCandidates(
				demand,
				purchaseCandidate,
				vendorProductInfo);

		final PurchaseRowsLoader loader = PurchaseRowsLoader.builder()
				.purchaseDemandWithCandidatesList(demandWithCandidates)
				.viewSupplier(() -> null)
				.purchaseRowFactory(new PurchaseRowFactory(
						new AvailableToPromiseRepository(),
						new DoNothingPurchaseProfitInfoServiceImpl()))
				.availabilityCheckService(availabilityCheckService)
				.build();

		//
		// invoke the method under test FIRST PART
		final PurchaseRowsList rowsList = loader.load();

		//
		// Check result
		final List<PurchaseRow> topLevelRows = rowsList.getTopLevelRows();
		assertThat(topLevelRows).hasSize(1);

		final PurchaseRow groupRow = topLevelRows.get(0);
		assertThat(groupRow.getType()).isEqualTo(PurchaseRowType.GROUP);
		assertThat(groupRow.getQtyToPurchase().toBigDecimal()).isEqualByComparingTo(TEN.toBigDecimal());
		assertThat(groupRow.getIncludedRows()).hasSize(1);

		final PurchaseRow purchaseRow = groupRow.getIncludedRows().iterator().next();
		assertThat(purchaseRow.getType()).isEqualTo(PurchaseRowType.LINE);
		assertThat(purchaseRow.getIncludedRows()).isEmpty();

		//
		// set up availabilityCheckService
		{
			final PurchaseCandidatesAvailabilityRequest request = loader.createAvailabilityRequest(rowsList);
			Mockito.doReturn(AvailabilityMultiResult.of(AvailabilityResult.builder()
					.trackingId(request.getTrackingIds().iterator().next())
					.qty(TEN)
					.type(Type.AVAILABLE)
					.build()))
					//
					.when(availabilityCheckService)
					.checkAvailability(request);
		}

		//
		// invoke the method under test SECOND PART
		loader.createAndAddAvailabilityResultRows(rowsList);

		assertThat(purchaseRow.getIncludedRows()).hasSize(1);

		final PurchaseRow availabilityRow = purchaseRow.getIncludedRows().iterator().next();
		assertThat(availabilityRow.getType()).isEqualTo(PurchaseRowType.AVAILABILITY_DETAIL);
		assertThat(availabilityRow.getRowId().toDocumentId()).isNotEqualTo(purchaseRow.getRowId().toDocumentId());
	}

	private static PurchaseCandidate createPurchaseCandidate(
			final I_C_OrderLine orderLine,
			final VendorProductInfo vendorProductInfo)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(orderLine.getC_Currency_ID());

		final PurchaseProfitInfo profitInfo = PurchaseRowTestTools.createProfitInfo(currencyId);

		final ProductId productId = ProductId.ofRepoId(orderLine.getM_Product_ID());
		final I_C_UOM productStockingUOM = Services.get(IProductBL.class).getStockUOM(productId);
		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.groupReference(DemandGroupReference.EMPTY)
				.orgId(OrgId.ofRepoId(20))
				.purchaseDatePromised(TimeUtil.asZonedDateTime(orderLine.getDatePromised()))
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(orderLine.getM_AttributeSetInstance_ID()))
				.qtyToPurchase(Quantity.of(orderLine.getQtyOrdered(), productStockingUOM))
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID()))
				.vendorId(vendorProductInfo.getVendorId())
				.vendorProductNo(vendorProductInfo.getVendorProductNo())
				.aggregatePOs(vendorProductInfo.isAggregatePOs())
				.warehouseId(WarehouseId.ofRepoId(30))
				.profitInfoOrNull(profitInfo)
				.build();
		return purchaseCandidate;
	}

	private static ImmutableList<PurchaseDemandWithCandidates> createPurchaseDemandWithCandidates(
			final PurchaseDemand demand,
			final PurchaseCandidate purchaseCandidate,
			final VendorProductInfo vendorProductInfo)
	{
		return ImmutableList.of(PurchaseDemandWithCandidates.builder()
				.purchaseDemand(demand)
				.purchaseCandidatesGroup(PurchaseCandidatesGroup.of(demand.getId(), purchaseCandidate, vendorProductInfo))
				.build());
	}
}
