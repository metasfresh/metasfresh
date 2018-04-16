package de.metas.ui.web.order.sales.purchasePlanning.view;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.dispo.commons.repository.AvailableToPromiseRepository;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.VendorProductInfo;
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
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test()
	{
		final PurchaseCandidate purchaseCandidate = createPurchaseCandidate(30);
		final PurchaseRowFactory purchaseRowFactory = new PurchaseRowFactory(new AvailableToPromiseRepository());

		final PurchaseRow candidateRow = purchaseRowFactory
				.rowFromPurchaseCandidateBuilder()
				.purchaseCandidate(purchaseCandidate)
				.vendorProductInfo(purchaseCandidate.getVendorProductInfo())
				.datePromised(SystemTime.asTimestamp())
				.build();

		final DocumentId id = candidateRow.getId();
		final PurchaseRowId purchaseRowId = PurchaseRowId.fromDocumentId(id);

		assertThat(purchaseRowId.getVendorBPartnerId()).isEqualTo(purchaseCandidate.getVendorBPartnerId());
		assertThat(purchaseRowId.getSalesOrderLineId()).isEqualTo(purchaseCandidate.getSalesOrderLineId());
		assertThat(purchaseRowId.getProcessedPurchaseCandidateId()).isEqualTo(30);

	}

	public static PurchaseCandidate createPurchaseCandidate(final int purchaseCandidateId)
	{
		final I_C_BPartner bPartner = newInstance(I_C_BPartner.class);
		save(bPartner);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("uomSympol");
		save(uom);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM(uom);
		save(product);

		final VendorProductInfo vendorProductInfo = VendorProductInfo.builder()
				.bPartnerProductId(10)
				.vendorBPartnerId(bPartner.getC_BPartner_ID())
				.productId(product.getM_Product_ID())
				.productNo("productNo")
				.productName("productName")
				.build();

		return PurchaseCandidate.builder()
				.purchaseCandidateId(purchaseCandidateId)
				.salesOrderId(1)
				.salesOrderLineId(2)
				.orgId(3)
				.warehouseId(4)
				.productId(product.getM_Product_ID())
				.uomId(uom.getC_UOM_ID())
				.vendorBPartnerId(bPartner.getC_BPartner_ID())
				.vendorProductInfo(vendorProductInfo)
				.qtyToPurchase(BigDecimal.ONE)
				.dateRequired(SystemTime.asDayTimestamp())
				.processed(true) // imporant if we expect purchaseRowId.getProcessedPurchaseCandidateId() to be > 0
				.locked(false)
				.build();
	}

}
