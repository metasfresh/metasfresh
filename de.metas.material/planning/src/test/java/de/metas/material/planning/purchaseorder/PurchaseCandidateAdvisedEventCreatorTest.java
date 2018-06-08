package de.metas.material.planning.purchaseorder;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.eevolution.model.I_PP_Product_Planning;
import org.junit.Before;
import org.junit.Test;

import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.ProductPlanningBL;
import de.metas.material.planning.impl.MRPContextFactory;

/*
 * #%L
 * metasfresh-material-planning
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

public class PurchaseCandidateAdvisedEventCreatorTest
{

	private I_PP_Product_Planning productPlanningRecord;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		productPlanningRecord = newInstance(I_PP_Product_Planning.class);
		productPlanningRecord.setIsPurchased("Y");
		save(productPlanningRecord);
	}

	@Test
	public void createPurchaseAdvisedEvent()
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = SupplyRequiredDescriptor.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(10, 20))
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(30))
						.warehouseId(40)
						.quantity(TEN)
						.date(SystemTime.asDate())
						.build())
				.demandCandidateId(50)
				.build();

		final MRPContextFactory mrpContextFactory = new MRPContextFactory(new ProductPlanningBL());
		final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();
		mrpContext.setProductPlanning(productPlanningRecord);

		final PurchaseCandidateAdvisedEventCreator purchaseCandidateAdvisedEventCreator = new PurchaseCandidateAdvisedEventCreator(new PurchaseOrderDemandMatcher());

		// invoke the method under test
		final List<PurchaseCandidateAdvisedEvent> purchaseAdvisedEvent = purchaseCandidateAdvisedEventCreator
				.createPurchaseAdvisedEvent(
						supplyRequiredDescriptor,
						mrpContext);

		assertThat(purchaseAdvisedEvent).hasSize(1);
		assertThat(purchaseAdvisedEvent.get(0).getProductPlanningId()).isEqualTo(productPlanningRecord.getPP_Product_Planning_ID());
	}
}
