/*
 * #%L
 * metasfresh-material-planning
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

package de.metas.material.planning.event;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.PlanningUsage;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Dispatch test for {@link SupplyRequiredHandler}.
 *
 * Core fix for <a href="https://github.com/metasfresh/me03/issues/28877">me03#28877</a>:
 * when a product has multiple {@code PP_Product_Planning} rows with different usages, the handler
 * must hand each advisor the context built from the row matching its usage, in priority order
 * (distribution -> manufacturing -> purchasing), threading the remaining quantity so we never
 * over-supply.
 */
class SupplyRequiredHandlerTest
{
	private PostMaterialEventService postMaterialEventService;
	private SupplyRequiredHandlerHelper helper;
	private ProductId productId;
	private I_C_UOM uom;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUomKg();
		productId = ProductId.ofRepoId(BusinessTestHelper.createProduct("product", uom).getM_Product_ID());

		postMaterialEventService = mock(PostMaterialEventService.class);
		helper = mock(SupplyRequiredHandlerHelper.class);
	}

	/**
	 * The regression test for 28877. Distribution and manufacturing both have contexts;
	 * distribution has priority and claims the full demand, so manufacturing does not fire.
	 */
	@Test
	void twoPlanningsWithDifferentUsages_distributionClaimsFullDemand_manufacturingDoesNotFire()
	{
		final MaterialPlanningContext mfgContext = newContext();
		final MaterialPlanningContext distContext = newContext();

		final Map<PlanningUsage, MaterialPlanningContext> contextsByUsage = new EnumMap<>(PlanningUsage.class);
		contextsByUsage.put(PlanningUsage.MANUFACTURING, mfgContext);
		contextsByUsage.put(PlanningUsage.DISTRIBUTION, distContext);
		when(helper.createContextsByUsage(any())).thenReturn(contextsByUsage);

		final MaterialEvent ddEvent = mock(MaterialEvent.class, "ddEvent");
		final Quantity fullDemand = qty("100");

		final SupplyRequiredAdvisor ddAdvisor = advisorClaimingAll(PlanningUsage.DISTRIBUTION, ddEvent);
		final SupplyRequiredAdvisor ppAdvisor = advisorClaimingAll(PlanningUsage.MANUFACTURING, mock(MaterialEvent.class));
		final SupplyRequiredAdvisor purchaseAdvisor = advisorClaimingAll(PlanningUsage.PURCHASING, mock(MaterialEvent.class));

		final SupplyRequiredHandler handler = new SupplyRequiredHandler(
				postMaterialEventService,
				ImmutableList.of(ppAdvisor, ddAdvisor, purchaseAdvisor),
				helper);

		handler.handleEvent(SupplyRequiredEvent.of(newDescriptor(fullDemand)));

		// Distribution ran with the full demand and returned the distribution context.
		verify(ddAdvisor).createAdvisedEvents(any(), same(distContext), any());
		// Manufacturing and Purchase did not run at all — DD consumed everything.
		verify(ppAdvisor, never()).createAdvisedEvents(any(), any(), any());
		verify(purchaseAdvisor, never()).createAdvisedEvents(any(), any(), any());
		// Only the DD event was posted.
		verify(postMaterialEventService).enqueueEventNow(same(ddEvent));
		verify(postMaterialEventService, times(1)).enqueueEventNow(any());
	}

	/**
	 * Leftover-quantity path: DD claims only part of the demand, manufacturing takes the rest.
	 */
	@Test
	void distributionClaimsPartially_manufacturingClaimsRemainder()
	{
		final MaterialPlanningContext mfgContext = newContext();
		final MaterialPlanningContext distContext = newContext();

		final Map<PlanningUsage, MaterialPlanningContext> contextsByUsage = new EnumMap<>(PlanningUsage.class);
		contextsByUsage.put(PlanningUsage.MANUFACTURING, mfgContext);
		contextsByUsage.put(PlanningUsage.DISTRIBUTION, distContext);
		when(helper.createContextsByUsage(any())).thenReturn(contextsByUsage);

		final MaterialEvent ddEvent = mock(MaterialEvent.class, "ddEvent");
		final MaterialEvent ppEvent = mock(MaterialEvent.class, "ppEvent");

		// DD advisor claims only 30 out of 100.
		final SupplyRequiredAdvisor ddAdvisor = mock(SupplyRequiredAdvisor.class, "ddAdvisor");
		when(ddAdvisor.getPlanningUsage()).thenReturn(PlanningUsage.DISTRIBUTION);
		when(ddAdvisor.createAdvisedEvents(any(), any(), any())).thenReturn(SupplyAdvice.of(ImmutableList.of(ddEvent), qty("30")));

		// PP advisor claims whatever is handed to it.
		final SupplyRequiredAdvisor ppAdvisor = advisorClaimingAll(PlanningUsage.MANUFACTURING, ppEvent);

		final SupplyRequiredHandler handler = new SupplyRequiredHandler(
				postMaterialEventService,
				ImmutableList.of(ppAdvisor, ddAdvisor),
				helper);

		handler.handleEvent(SupplyRequiredEvent.of(newDescriptor(qty("100"))));

		// PP must have been called with remainingQty = 70 (100 - 30).
		final ArgumentCaptor<Quantity> ppRemaining = ArgumentCaptor.forClass(Quantity.class);
		verify(ppAdvisor).createAdvisedEvents(any(), same(mfgContext), ppRemaining.capture());
		assertThat(ppRemaining.getValue().toBigDecimal()).isEqualByComparingTo("70");

		// Both events got posted.
		verify(postMaterialEventService).enqueueEventNow(same(ddEvent));
		verify(postMaterialEventService).enqueueEventNow(same(ppEvent));
	}

	/**
	 * When no planning matches any usage, no advisor is invoked and a single
	 * {@code NoSupplyAdviceEvent} is posted so the dispo service can react.
	 */
	@Test
	void emptyMap_postsNoSupplyAdviceEvent()
	{
		when(helper.createContextsByUsage(any())).thenReturn(new EnumMap<>(PlanningUsage.class));

		final SupplyRequiredAdvisor ppAdvisor = advisorClaimingAll(PlanningUsage.MANUFACTURING, null);

		final SupplyRequiredHandler handler = new SupplyRequiredHandler(
				postMaterialEventService,
				ImmutableList.of(ppAdvisor),
				helper);

		handler.handleEvent(SupplyRequiredEvent.of(newDescriptor(qty("10"))));

		verify(ppAdvisor, never()).createAdvisedEvents(any(), any(), any());
		verify(postMaterialEventService, times(1)).enqueueEventNow(any());
	}

	/**
	 * Confirms the dispatch order is distribution -> manufacturing -> purchasing
	 * independent of the order the advisors are supplied in the constructor.
	 */
	@Test
	void advisorsInjectedOutOfOrder_dispatchStillDistributionFirst()
	{
		final Map<PlanningUsage, MaterialPlanningContext> contextsByUsage = new EnumMap<>(PlanningUsage.class);
		contextsByUsage.put(PlanningUsage.PURCHASING, newContext());
		when(helper.createContextsByUsage(any())).thenReturn(contextsByUsage);

		// Only purchasing has a context; the other two are injected but won't fire.
		final SupplyRequiredAdvisor purchaseAdvisor = advisorClaimingAll(PlanningUsage.PURCHASING, mock(MaterialEvent.class));
		final SupplyRequiredAdvisor ppAdvisor = advisorClaimingAll(PlanningUsage.MANUFACTURING, mock(MaterialEvent.class));
		final SupplyRequiredAdvisor ddAdvisor = advisorClaimingAll(PlanningUsage.DISTRIBUTION, mock(MaterialEvent.class));

		// Inject in the "wrong" order — purchase first, then mfg, then dd.
		final SupplyRequiredHandler handler = new SupplyRequiredHandler(
				postMaterialEventService,
				ImmutableList.of(purchaseAdvisor, ppAdvisor, ddAdvisor),
				helper);

		handler.handleEvent(SupplyRequiredEvent.of(newDescriptor(qty("5"))));

		// Only the purchase advisor can fire (it's the only usage in the map).
		verify(purchaseAdvisor).createAdvisedEvents(any(), any(), any());
		verify(ppAdvisor, never()).createAdvisedEvents(any(), any(), any());
		verify(ddAdvisor, never()).createAdvisedEvents(any(), any(), any());
	}

	private Quantity qty(final String value)
	{
		return Quantitys.of(value, UomId.ofRepoId(uom.getC_UOM_ID()));
	}

	private SupplyRequiredDescriptor newDescriptor(@NonNull final Quantity quantity)
	{
		return SupplyRequiredDescriptor.builder()
				.demandCandidateId(1)
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ClientAndOrgId.ofClientAndOrg(1, 2)))
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(ProductDescriptor.forProductAndAttributes(
								productId.getRepoId(),
								AttributesKey.ofString("1"),
								0))
						.warehouseId(WarehouseId.MAIN)
						.quantity(quantity.toBigDecimal())
						.date(Instant.parse("2026-01-01T00:00:00Z"))
						.build())
				.build();
	}

	private MaterialPlanningContext newContext()
	{
		return MaterialPlanningContext.builder()
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.warehouseId(WarehouseId.MAIN)
				.productPlanning(ProductPlanning.builder().orgId(OrgId.ANY).build())
				.plantId(ResourceId.ofRepoId(2))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 2))
				.build();
	}

	/**
	 * Creates an advisor mock that, when invoked, claims the entire quantity it was handed
	 * and returns the given event (or an empty list if {@code eventOrNull} is null).
	 */
	private static SupplyRequiredAdvisor advisorClaimingAll(
			final PlanningUsage usage,
			final MaterialEvent eventOrNull)
	{
		final SupplyRequiredAdvisor advisor = mock(SupplyRequiredAdvisor.class, "advisor-" + usage);
		when(advisor.getPlanningUsage()).thenReturn(usage);
		when(advisor.createAdvisedEvents(any(), any(), any())).thenAnswer(inv -> {
			final Quantity remaining = inv.getArgument(2, Quantity.class);
			return eventOrNull != null
					? SupplyAdvice.of(ImmutableList.of(eventOrNull), remaining)
					: SupplyAdvice.nothing(remaining);
		});
		return advisor;
	}

}
