package de.metas.purchasecandidate.material.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.BPartnerProductEffectiveBL;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.VendorProductInfoService;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.Optional;

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

public class PurchaseCandidateRequestedHandlerTest
{
	private static final BPartnerId VENDOR_ID = BPartnerId.ofRepoId(101);
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(202);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);

	private BPartnerProductEffectiveBL bpartnerProductEffectiveBL;
	private PurchaseCandidateRequestedHandler handler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		bpartnerProductEffectiveBL = mock(BPartnerProductEffectiveBL.class);
		handler = new PurchaseCandidateRequestedHandler(
				mock(ProductRepository.class),
				mock(PurchaseCandidateRepository.class),
				mock(PostMaterialEventService.class),
				mock(VendorProductInfoService.class),
				bpartnerProductEffectiveBL);
	}

	@Test
	public void createCandidateCreatedEvent()
	{
		final PurchaseCandidateRequestedEvent requestedEvent = PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(5, 6))
				.supplyCandidateRepoId(10)
				.purchaseMaterialDescriptor(PurchaseCandidateAdvisedEventCreatorTest.createMaterialDescriptor())
				.build();
		final BPartnerId vendorId = BPartnerId.ofRepoId(30);
		final PurchaseCandidateId purchaseCandidateId = PurchaseCandidateId.ofRepoId(20);

		final PurchaseCandidateCreatedEvent result = PurchaseCandidateRequestedHandler.createCandidateCreatedEvent(requestedEvent,
				vendorId,
				purchaseCandidateId);

		assertThat(result.getVendorId()).isEqualTo(vendorId.getRepoId());
		assertThat(result.getSupplyCandidateRepoId()).isEqualTo(10);
	}

	@Test
	public void computePurchaseDateOrdered_vendorAwareSet_usesVendorAwareValue()
	{
		when(bpartnerProductEffectiveBL.getPurchaseTransportDaysIfSet(VENDOR_ID, PRODUCT_ID, ORG_ID))
				.thenReturn(Optional.of(3));
		final ProductPlanning planning = ProductPlanning.builder().orgId(ORG_ID).attributeSetInstanceId(org.adempiere.mm.attributes.AttributeSetInstanceId.NONE).leadTimeDays(99).build();
		final ZonedDateTime datePromised = ZonedDateTime.parse("2026-06-15T00:00:00+02:00[Europe/Berlin]");

		final ZonedDateTime result = handler.computePurchaseDateOrderedOrNull(datePromised, VENDOR_ID, PRODUCT_ID, ORG_ID, planning);

		assertThat(result).isEqualTo(datePromised.minusDays(3));
	}

	@Test
	public void computePurchaseDateOrdered_vendorAwareEmpty_fallsBackToProductPlanning()
	{
		when(bpartnerProductEffectiveBL.getPurchaseTransportDaysIfSet(VENDOR_ID, PRODUCT_ID, ORG_ID))
				.thenReturn(Optional.empty());
		final ProductPlanning planning = ProductPlanning.builder().orgId(ORG_ID).attributeSetInstanceId(org.adempiere.mm.attributes.AttributeSetInstanceId.NONE).leadTimeDays(7).build();
		final ZonedDateTime datePromised = ZonedDateTime.parse("2026-06-15T00:00:00+02:00[Europe/Berlin]");

		final ZonedDateTime result = handler.computePurchaseDateOrderedOrNull(datePromised, VENDOR_ID, PRODUCT_ID, ORG_ID, planning);

		assertThat(result).isEqualTo(datePromised.minusDays(7));
	}

	@Test
	public void computePurchaseDateOrdered_vendorAwareEmptyAndNoPlanning_returnsNull()
	{
		when(bpartnerProductEffectiveBL.getPurchaseTransportDaysIfSet(VENDOR_ID, PRODUCT_ID, ORG_ID))
				.thenReturn(Optional.empty());
		final ZonedDateTime datePromised = ZonedDateTime.parse("2026-06-15T00:00:00+02:00[Europe/Berlin]");

		final ZonedDateTime result = handler.computePurchaseDateOrderedOrNull(datePromised, VENDOR_ID, PRODUCT_ID, ORG_ID, null);

		assertThat(result).isNull();
	}

	@Test
	public void computePurchaseDateOrdered_vendorAwareSetToZero_usesZero_notPlanningFallback()
	{
		when(bpartnerProductEffectiveBL.getPurchaseTransportDaysIfSet(VENDOR_ID, PRODUCT_ID, ORG_ID))
				.thenReturn(Optional.of(0));
		final ProductPlanning planning = ProductPlanning.builder().orgId(ORG_ID).attributeSetInstanceId(org.adempiere.mm.attributes.AttributeSetInstanceId.NONE).leadTimeDays(99).build();
		final ZonedDateTime datePromised = ZonedDateTime.parse("2026-06-15T00:00:00+02:00[Europe/Berlin]");

		final ZonedDateTime result = handler.computePurchaseDateOrderedOrNull(datePromised, VENDOR_ID, PRODUCT_ID, ORG_ID, planning);

		assertThat(result).isEqualTo(datePromised); // minusDays(0)
	}

}
