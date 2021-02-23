package de.metas.purchasecandidate.purchaseordercreation.remoteorder;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.ImmutableList;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.bpartner.BPartnerId;
import de.metas.money.grossprofit.ProfitPriceActualFactory;
import de.metas.order.OrderAndLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.purchasecandidate.DemandGroupReference;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateTestTool;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseItem;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, ProfitPriceActualFactory.class })
public class NullVendorGatewayInvokerTest
{
	@Test
	public void placeRemotePurchaseOrder()
	{
		final I_C_UOM EACH = createUOM("Ea");

		final PurchaseCandidate purchaseCandidate = PurchaseCandidate.builder()
				.groupReference(DemandGroupReference.EMPTY)
				.orgId(OrgId.ofRepoId(10))
				.warehouseId(WarehouseId.ofRepoId(60))
				.purchaseDatePromised(SystemTime.asZonedDateTime())
				.vendorId(BPartnerId.ofRepoId(30))
				.productId(ProductId.ofRepoId(20))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(21))
				.vendorProductNo("vendorProductNo_20")
				.qtyToPurchase(Quantity.of(TEN, EACH))
				.salesOrderAndLineIdOrNull(OrderAndLineId.ofRepoIds(40, 50))
				.profitInfoOrNull(PurchaseCandidateTestTool.createPurchaseProfitInfo())
				.build();

		final List<PurchaseItem> purchaseItems = NullVendorGatewayInvoker.INSTANCE.placeRemotePurchaseOrder(ImmutableList.of(purchaseCandidate));

		assertThat(purchaseItems).hasSize(1);
		assertThat(purchaseItems.get(0)).isInstanceOf(PurchaseOrderItem.class);

		final PurchaseOrderItem purchaseOrderItem = (PurchaseOrderItem)purchaseItems.get(0);
		assertThat(purchaseOrderItem.getRemotePurchaseOrderId()).isEqualTo(NullVendorGatewayInvoker.NO_REMOTE_PURCHASE_ID);
	}

	private I_C_UOM createUOM(final String name)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		save(uom);
		return uom;
	}

}
