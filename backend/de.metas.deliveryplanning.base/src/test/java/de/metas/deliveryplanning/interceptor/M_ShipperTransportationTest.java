/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning.interceptor;

import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.deliveryplanning.DeliveryPlanningService;
import de.metas.event.IEventBusFactory;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.ModelValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Which {@code @DocValidate} timings each interceptor method is wired to, and that each method's BEHAVIOUR
 * matches its annotation.
 * <p>
 * VOID's invoice-candidate invalidation is owned exclusively by {@code unlinkDeliveryPlannings}: re-deriving
 * "active" allocations from a {@code trxManager.runAfterCommit} closure would see none left, because
 * {@link DeliveryPlanningService#unlinkDeliveryPlannings} has already deactivated them in the same
 * transaction. The split into two methods is pinned here so a re-coupling fails loudly.
 */
class M_ShipperTransportationTest
{
	private DeliveryPlanningService deliveryPlanningService;
	private M_ShipperTransportation interceptor;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IBPartnerStatisticsUpdater.class, Mockito.mock(IBPartnerStatisticsUpdater.class));

		deliveryPlanningService = Mockito.mock(DeliveryPlanningService.class);
		interceptor = new M_ShipperTransportation(deliveryPlanningService, Mockito.mock(IEventBusFactory.class));
	}

	private static Method methodNamed(final String name)
	{
		return java.util.Arrays.stream(M_ShipperTransportation.class.getDeclaredMethods())
				.filter(method -> method.getName().equals(name))
				.findFirst()
				.orElseThrow(() -> new AssertionError("No method named " + name));
	}

	private I_M_ShipperTransportation deliveryInstruction()
	{
		final I_M_ShipperTransportation record = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class);
		record.setShipper_BPartner_ID(540001);
		InterfaceWrapperHelper.save(record);
		return record;
	}

	@Test
	@DisplayName("invoice-candidate invalidation fires on complete only - NOT on void, which unlinkDeliveryPlannings already owns")
	void invalidateInvoiceCandidatesTimingsExcludeVoid()
	{
		final DocValidate annotation = methodNamed("invalidateInvoiceCandidatesAfterComplete").getAnnotation(DocValidate.class);

		assertThat(annotation).as("the method must be a @DocValidate handler").isNotNull();
		assertThat(annotation.timings()).containsExactly(ModelValidator.TIMING_AFTER_COMPLETE);
	}

	@Test
	@DisplayName("bpartner statistics still update on both complete and void, unaffected by the split")
	void bpartnerStatisticsTimingsUnchanged()
	{
		final DocValidate annotation = methodNamed("updateBPartnerStatistics").getAnnotation(DocValidate.class);

		assertThat(annotation.timings()).containsExactlyInAnyOrder(ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_VOID);
	}

	@Test
	@DisplayName("updateBPartnerStatistics no longer triggers invoice-candidate invalidation - that is a separate handler now")
	void updateBPartnerStatisticsDoesNotInvalidateInvoiceCandidates()
	{
		interceptor.updateBPartnerStatistics(deliveryInstruction());

		Mockito.verify(deliveryPlanningService, Mockito.never()).invalidateInvoiceCandidatesFor(Mockito.any(ShipperTransportationId.class));
	}

	@Test
	@DisplayName("invalidateInvoiceCandidatesAfterComplete does invoke the service for the instruction")
	void invalidateInvoiceCandidatesAfterCompleteInvokesTheService()
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstruction();

		interceptor.invalidateInvoiceCandidatesAfterComplete(deliveryInstruction);

		// runAfterCommit runs synchronously in this plain-JUnit context, so the deferred call is already observable
		Mockito.verify(deliveryPlanningService).invalidateInvoiceCandidatesFor(
				ShipperTransportationId.ofRepoId(deliveryInstruction.getM_ShipperTransportation_ID()));
	}
}
