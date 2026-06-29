package de.metas.purchasecandidate.material.event;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.BPartnerProductEffectiveBL;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.PackageDimensions;
import de.metas.product.Product;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.PurchaseCandidateId;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.VendorProductInfoService;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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
	private ProductRepository productRepository;
	private PurchaseCandidateRepository purchaseCandidateRepository;
	private IProductBL productBL;
	private PurchaseCandidateRequestedHandler handler;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		productBL = mock(IProductBL.class);
		Services.registerService(IProductBL.class, productBL);

		bpartnerProductEffectiveBL = mock(BPartnerProductEffectiveBL.class);
		productRepository = mock(ProductRepository.class);
		purchaseCandidateRepository = mock(PurchaseCandidateRepository.class);
		handler = new PurchaseCandidateRequestedHandler(
				productRepository,
				purchaseCandidateRepository,
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

	@Test
	public void handleEvent_notPurchasedProduct_doesNotCreateCandidate()
	{
		// given: a product with IsPurchased=N and enforcement gate ENABLED
		final Product notPurchasedProduct = Product.builder()
				.id(PRODUCT_ID)
				.orgId(ORG_ID)
				.uomId(UomId.ofRepoId(1))
				.value("TEST")
				.productCategoryId(ProductCategoryId.ofRepoId(1))
				.name(TranslatableStrings.anyLanguage("Test Product"))
				.productType("I")
				.packageDimensions(PackageDimensions.UNSPECIFIED)
				.build();
		when(productRepository.getById(PRODUCT_ID)).thenReturn(notPurchasedProduct);
		when(productBL.isPurchased(PRODUCT_ID)).thenReturn(false);
		when(productBL.isPurchaseSalesEnforcementEnabled(any(ClientId.class), any(OrgId.class))).thenReturn(true);

		final PurchaseCandidateRequestedEvent event = PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(5, ORG_ID.getRepoId()))
				.supplyCandidateRepoId(10)
				.purchaseMaterialDescriptor(buildMaterialDescriptorForProduct(PRODUCT_ID))
				.build();

		// when
		handler.handleEvent(event);

		// then: no candidate is saved
		verify(purchaseCandidateRepository, never()).save(any(PurchaseCandidate.class));
	}

	@Test
	public void gateDisabled_notPurchasedProduct_stillCreatesCandidate()
	{
		// given: gate DISABLED — handler bypasses IsPurchased check and throws on missing VendorProductInfo
		final Product notPurchasedProduct = Product.builder()
				.id(PRODUCT_ID)
				.orgId(ORG_ID)
				.uomId(UomId.ofRepoId(1))
				.value("TEST")
				.productCategoryId(ProductCategoryId.ofRepoId(1))
				.name(TranslatableStrings.anyLanguage("Test Product"))
				.productType("I")
				.packageDimensions(PackageDimensions.UNSPECIFIED)
				.build();
		when(productRepository.getById(PRODUCT_ID)).thenReturn(notPurchasedProduct);
		when(productBL.isPurchased(PRODUCT_ID)).thenReturn(false);
		when(productBL.isPurchaseSalesEnforcementEnabled(any(ClientId.class), any(OrgId.class))).thenReturn(false);
		// vendorProductInfosRepo is a mock from @BeforeEach returning empty by default →
		// handler will throw AdempiereException("Missing vendorProductInfos…") proving it
		// passed the IsPurchased gate and continued.

		final PurchaseCandidateRequestedEvent event = PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(5, ORG_ID.getRepoId()))
				.supplyCandidateRepoId(10)
				.purchaseMaterialDescriptor(buildMaterialDescriptorForProduct(PRODUCT_ID))
				.build();

		// when / then: gate is off → handler does NOT return early; it proceeds past the
		// IsPurchased check and throws because VendorProductInfo is missing (mock returns empty)
		assertThatThrownBy(() -> handler.handleEvent(event))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("Missing vendorProductInfos");

		// also confirm the early-return path (guard) was NOT taken
		verify(purchaseCandidateRepository, never()).save(any(PurchaseCandidate.class));
	}

	private static MaterialDescriptor buildMaterialDescriptorForProduct(final ProductId productId)
	{
		return MaterialDescriptor.builder()
				.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(productId.getRepoId()))
				.warehouseId(WarehouseId.ofRepoId(40))
				.quantity(BigDecimal.TEN)
				.date(SystemTime.asInstant())
				.build();
	}

}
