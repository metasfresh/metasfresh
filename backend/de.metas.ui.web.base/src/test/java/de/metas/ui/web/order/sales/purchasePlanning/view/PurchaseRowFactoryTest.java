package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;

import de.metas.common.util.time.SystemTime;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.pricing.conditions.PricingConditions;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidatesGroup;
import de.metas.purchasecandidate.PurchaseDemandId;
import de.metas.purchasecandidate.VendorProductInfo;
import de.metas.purchasecandidate.grossprofit.PurchaseProfitInfo;
import de.metas.quantity.Quantity;
import de.metas.ui.web.window.datatypes.DocumentId;

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

public class PurchaseRowFactoryTest
{
	private PurchaseRowFactory purchaseRowFactory;

	private CurrencyId currencyId;

	private Quantity ONE;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		currencyId = CurrencyId.ofRepoId(30);

		final I_C_UOM each = PurchaseRowTestTools.createUOM("Ea");
		this.ONE = Quantity.of(BigDecimal.ONE, each);

		purchaseRowFactory = new PurchaseRowFactory(
				new AvailableToPromiseRepository(),
				new DoNothingPurchaseProfitInfoServiceImpl());
	}

	@Test
	public void test()
	{
		final VendorProductInfo vendorProductInfo = createVendorProductInfo();
		final PurchaseCandidate purchaseCandidate = createPurchaseCandidate(30, vendorProductInfo);
		final PurchaseDemandId demandId = PurchaseDemandId.ofId(40);

		final PurchaseRow candidateRow = purchaseRowFactory.lineRowBuilder()
				.purchaseCandidatesGroup(PurchaseCandidatesGroup.of(demandId, purchaseCandidate, vendorProductInfo))
				.convertAmountsToCurrencyId(currencyId)
				.build();

		final DocumentId id = candidateRow.getId();
		final PurchaseRowId purchaseRowId = PurchaseRowId.fromDocumentId(id);

		assertThat(purchaseRowId.getVendorId()).isEqualTo(purchaseCandidate.getVendorId());
		assertThat(purchaseRowId.getPurchaseDemandId()).isEqualTo(demandId);
	}

	public PurchaseCandidate createPurchaseCandidate(final int purchaseCandidateId, final VendorProductInfo vendorProductInfo)
	{
		final PurchaseProfitInfo profitInfo = PurchaseProfitInfo
				.builder()
				.profitSalesPriceActual(Money.of(11, currencyId))
				.profitPurchasePriceActual(Money.of(9, currencyId))
				.purchasePriceActual(Money.of(10, currencyId))
				.build();

		return PurchaseCandidate.builder()
				.id(PurchaseCandidateId.ofRepoIdOrNull(purchaseCandidateId))
				.groupReference(DemandGroupReference.EMPTY)
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(1, 2))
				.orgId(OrgId.ofRepoId(3))
				.warehouseId(WarehouseId.ofRepoId(4))
				.vendorId(vendorProductInfo.getVendorId())
				.vendorProductNo(vendorProductInfo.getVendorProductNo())
				.aggregatePOs(vendorProductInfo.isAggregatePOs())
				.productId(vendorProductInfo.getProductId())
				.attributeSetInstanceId(vendorProductInfo.getAttributeSetInstanceId())
				.qtyToPurchase(ONE)
				.purchaseDatePromised(SystemTime.asZonedDateTime().truncatedTo(ChronoUnit.DAYS))
				.profitInfoOrNull(profitInfo)
				.processed(true) // important in case we expect purchaseRowId.getProcessedPurchaseCandidateId() to be > 0
				.locked(false)
				.build();
	}

	private VendorProductInfo createVendorProductInfo()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		save(bpartner);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("uomSympol");
		save(uom);

		final I_M_Product_Category productCategory = newInstance(I_M_Product_Category.class);
		save(productCategory);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		save(product);

		return VendorProductInfo.builder()
				.vendorId(BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()))
				.defaultVendor(false)
				.product(ProductAndCategoryAndManufacturerId.of(product.getM_Product_ID(), product.getM_Product_Category_ID(), product.getManufacturer_ID()))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.vendorProductNo("productNo")
				.vendorProductName("productName")
				.pricingConditions(PricingConditions.builder()
										   .validFrom(TimeUtil.asInstant(Timestamp.valueOf("2017-01-01 10:10:10.0")))
										   .build())
				.build();
	}

}
