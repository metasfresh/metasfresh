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
import java.util.Arrays;

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
		return Arrays.stream(M_ShipperTransportation.class.getDeclaredMethods())
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

		assertThat(annotation).as("the method must be a @DocValidate handler").isNotNull();
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
	@DisplayName("the closed-planning guard is wired to complete, re-activate AND void - one condition, three document actions")
	void closedAllocatedPlanningsGuardCoversCompleteReActivateAndVoid()
	{
		final DocValidate onComplete = methodNamed("rejectCompleteWithClosedAllocatedPlannings").getAnnotation(DocValidate.class);
		final DocValidate onReActivate = methodNamed("rejectReActivateWithClosedAllocatedPlannings").getAnnotation(DocValidate.class);
		final DocValidate onVoid = methodNamed("rejectVoidWithClosedAllocatedPlannings").getAnnotation(DocValidate.class);

		assertThat(onComplete).as("the complete guard must be a @DocValidate handler").isNotNull();
		assertThat(onComplete.timings()).containsExactly(ModelValidator.TIMING_BEFORE_COMPLETE);
		assertThat(onReActivate).as("the re-activate guard must be a @DocValidate handler").isNotNull();
		assertThat(onReActivate.timings()).containsExactly(ModelValidator.TIMING_BEFORE_REACTIVATE);
		assertThat(onVoid).as("the void guard must be a @DocValidate handler").isNotNull();
		assertThat(onVoid.timings()).containsExactly(ModelValidator.TIMING_BEFORE_VOID);
	}

	/**
	 * BEFORE, not AFTER: the sibling {@code unlinkDeliveryPlannings} runs on TIMING_AFTER_VOID and deactivates every
	 * allocation, so a guard placed after it would find no closed allocated planning left to object to and would
	 * pass on exactly the case it exists to refuse.
	 */
	@Test
	@DisplayName("the void guard fires BEFORE the unlink that would erase the very state it inspects")
	void voidGuardFiresBeforeTheUnlink()
	{
		final DocValidate onVoid = methodNamed("rejectVoidWithClosedAllocatedPlannings").getAnnotation(DocValidate.class);
		final DocValidate onUnlink = methodNamed("unlinkDeliveryPlannings").getAnnotation(DocValidate.class);

		assertThat(onVoid.timings()).containsExactly(ModelValidator.TIMING_BEFORE_VOID);
		assertThat(onUnlink.timings()).containsExactly(ModelValidator.TIMING_AFTER_VOID);
	}

	@Test
	@DisplayName("the void guard asks the service for its rejection reason, for this very instruction")
	void voidGuardConsultsTheService()
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstruction();

		interceptor.rejectVoidWithClosedAllocatedPlannings(deliveryInstruction);

		Mockito.verify(deliveryPlanningService).getVoidRejectionReason(
				ShipperTransportationId.ofRepoId(deliveryInstruction.getM_ShipperTransportation_ID()));
	}

	@Test
	@DisplayName("the re-activate guard asks the service for its rejection reason, for this very instruction")
	void reActivateGuardConsultsTheService()
	{
		final I_M_ShipperTransportation deliveryInstruction = deliveryInstruction();

		interceptor.rejectReActivateWithClosedAllocatedPlannings(deliveryInstruction);

		Mockito.verify(deliveryPlanningService).getReActivateRejectionReason(
				ShipperTransportationId.ofRepoId(deliveryInstruction.getM_ShipperTransportation_ID()));
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
