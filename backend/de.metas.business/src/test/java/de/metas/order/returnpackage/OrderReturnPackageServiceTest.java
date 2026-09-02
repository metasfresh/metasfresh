/*
 * #%L
 * de.metas.business
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

package de.metas.order.returnpackage;

import de.metas.order.OrderId;
import de.metas.order.returnpackage.core.repository.OrderReturnPackageRepository;
import de.metas.order.returnpackage.core.service.OrderReturnPackageService;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Order_ReturnPackage;
import org.compiere.model.X_C_Order_ReturnPackage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(AdempiereTestWatcher.class)
class OrderReturnPackageServiceTest
{
	private static final int ORG_ID = 100;

	private OrderReturnPackageService service;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		final OrderReturnPackageRepository repository = new OrderReturnPackageRepository();
		service = new OrderReturnPackageService(repository);
	}

	private I_C_Order createSalesOrder()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setAD_Org_ID(ORG_ID);
		saveRecord(bpartner);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setIsSOTrx(true);
		order.setAD_Org_ID(ORG_ID);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		save(order);
		return order;
	}

	private List<I_C_Order_ReturnPackage> retrievePackages(final OrderId orderId)
	{
		return de.metas.util.Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Order_ReturnPackage.class)
				.addEqualsFilter(I_C_Order_ReturnPackage.COLUMNNAME_C_Order_ID, orderId)
				.orderBy(I_C_Order_ReturnPackage.COLUMNNAME_PalletType)
				.create()
				.list(I_C_Order_ReturnPackage.class);
	}

	@Test
	void createDefaultsForOrder_createsExactlyTwoRows()
	{
		final I_C_Order order = createSalesOrder();

		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		service.createDefaultsForOrder(orderId);

		final List<I_C_Order_ReturnPackage> packages = retrievePackages(orderId);
		assertThat(packages).hasSize(2);

		final I_C_Order_ReturnPackage eur = packages.stream()
				.filter(p -> X_C_Order_ReturnPackage.PALLETTYPE_EUR.equals(p.getPalletType()))
				.findFirst().orElseThrow(() -> new AssertionError("no EUR row"));
		final I_C_Order_ReturnPackage h1 = packages.stream()
				.filter(p -> X_C_Order_ReturnPackage.PALLETTYPE_H1.equals(p.getPalletType()))
				.findFirst().orElseThrow(() -> new AssertionError("no H1 row"));

		for (final I_C_Order_ReturnPackage p : packages)
		{
			assertThat(p.getC_Order_ID()).isEqualTo(order.getC_Order_ID());
			assertThat(p.getQtyDeliveredLU()).isZero(); // Quantity columns default to 0 (the user types the real count)
			assertThat(p.getQtyReturnedLU()).isZero();
		}

		assertThat(eur.getPalletType()).isEqualTo(X_C_Order_ReturnPackage.PALLETTYPE_EUR);
		assertThat(h1.getPalletType()).isEqualTo(X_C_Order_ReturnPackage.PALLETTYPE_H1);
	}

	@Test
	void createDefaultsForOrder_isIdempotent()
	{
		final I_C_Order order = createSalesOrder();

		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		service.createDefaultsForOrder(orderId);
		service.createDefaultsForOrder(orderId); // second call must not create duplicates

		assertThat(retrievePackages(orderId)).hasSize(2);
	}
}
