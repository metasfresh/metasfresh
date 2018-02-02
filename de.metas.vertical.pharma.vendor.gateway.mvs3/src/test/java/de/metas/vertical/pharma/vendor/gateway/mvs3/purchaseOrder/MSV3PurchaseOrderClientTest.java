package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.common.collect.ImmutableList;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.vendor.gateway.api.ProductAndQuantity;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequest;
import de.metas.vendor.gateway.api.order.PurchaseOrderRequestItem;
import de.metas.vendor.gateway.api.order.PurchaseOrderResponse;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3ConnectionFactory;
import de.metas.vertical.pharma.vendor.gateway.mvs3.MSV3TestingTools;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3SubstitutionDataPersister;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class,
		MSV3PurchaseOrderDataPersister.class, Msv3FaultInfoDataPersister.class, Msv3SubstitutionDataPersister.class })
public class MSV3PurchaseOrderClientTest
{

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	@Ignore
	public void manualTest()
	{
		MSV3TestingTools.setDBVersion(MSV3PurchaseOrderClientTest.class.getSimpleName());

		final MSV3PurchaseOrderClient msv3PurchaseOrderClient = MSV3PurchaseOrderClient.builder()
				.config(MSV3TestingTools.createMSV3ClientConfig())
				.connectionFactory(new MSV3ConnectionFactory())
				.build();

		final PurchaseOrderRequestItem purchaseOrderRequestItem = new PurchaseOrderRequestItem(new ProductAndQuantity("10055555", BigDecimal.TEN));
		final List<PurchaseOrderRequestItem> purchaseOrderRequestItems = ImmutableList.of(purchaseOrderRequestItem);
		final PurchaseOrderRequest request = new PurchaseOrderRequest(10, 20, purchaseOrderRequestItems);

		final PurchaseOrderResponse purchaseOrderResponse = msv3PurchaseOrderClient.placePurchaseOrder(request);
		assertThat(purchaseOrderResponse).isNotNull();
		assertThat(purchaseOrderResponse.getException()).isNull();
	}

}
