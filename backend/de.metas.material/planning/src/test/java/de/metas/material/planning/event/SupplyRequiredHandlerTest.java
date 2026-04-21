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
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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
 * The core fix for <a href="https://github.com/metasfresh/me03/issues/28877">me03#28877</a>:
 * when a product has two {@code PP_Product_Planning} rows with different usages, the handler
 * must hand each advisor the context built from the row that matches the advisor's usage —
 * not the single "best" row as it did before.
 */
class SupplyRequiredHandlerTest
{
	private PostMaterialEventService postMaterialEventService;
	private SupplyRequiredHandlerHelper helper;

	@BeforeEach
	void setUp()
	{
		postMaterialEventService = mock(PostMaterialEventService.class);
		helper = mock(SupplyRequiredHandlerHelper.class);
	}

	/**
	 * The regression test for 28877. Two plannings exist — one for manufacturing,
	 * one for distribution. The PP advisor must receive the manufacturing context
	 * and the DD advisor must receive the distribution context. Before the fix,
	 * both advisors saw the same "best" context and only one of them fired.
	 */
	@Test
	void twoPlanningsWithDifferentUsages_bothAdvisorsFire_withTheirOwnContext()
	{
		final MaterialPlanningContext mfgContext = newContext();
		final MaterialPlanningContext distContext = newContext();

		final Map<PlanningUsage, MaterialPlanningContext> contextsByUsage = new EnumMap<>(PlanningUsage.class);
		contextsByUsage.put(PlanningUsage.MANUFACTURING, mfgContext);
		contextsByUsage.put(PlanningUsage.DISTRIBUTION, distContext);
		when(helper.createContextsByUsage(any())).thenReturn(contextsByUsage);

		final MaterialEvent ppEvent = mock(MaterialEvent.class, "ppEvent");
		final MaterialEvent ddEvent = mock(MaterialEvent.class, "ddEvent");
		final SupplyRequiredAdvisor ppAdvisor = advisor(PlanningUsage.MANUFACTURING, ppEvent);
		final SupplyRequiredAdvisor ddAdvisor = advisor(PlanningUsage.DISTRIBUTION, ddEvent);
		final SupplyRequiredAdvisor purchaseAdvisor = advisor(PlanningUsage.PURCHASING, null);

		final SupplyRequiredHandler handler = new SupplyRequiredHandler(
				postMaterialEventService,
				ImmutableList.of(ppAdvisor, ddAdvisor, purchaseAdvisor),
				helper);

		handler.handleEvent(SupplyRequiredEvent.of(newDescriptor()));

		// Each advisor got exactly the context for its usage — not the "other" one.
		verify(ppAdvisor).createAdvisedEvents(any(), same(mfgContext));
		verify(ppAdvisor, never()).createAdvisedEvents(any(), same(distContext));

		verify(ddAdvisor).createAdvisedEvents(any(), same(distContext));
		verify(ddAdvisor, never()).createAdvisedEvents(any(), same(mfgContext));

		// Purchasing usage is not in the map -> purchase advisor must not be invoked at all.
		verify(purchaseAdvisor, never()).createAdvisedEvents(any(), any());

		// Two advised events posted, no NoSupplyAdviceEvent.
		verify(postMaterialEventService).enqueueEventNow(same(ppEvent));
		verify(postMaterialEventService).enqueueEventNow(same(ddEvent));
		verify(postMaterialEventService, times(2)).enqueueEventNow(any());
	}

	/**
	 * Preserves the pre-fix behavior for single-row configs: a PP row that declares
	 * both {@code isManufactured=Y} and {@code DD_NetworkDistribution_ID!=null} still
	 * triggers both advisors — and they both receive the same context built from
	 * that one row.
	 */
	@Test
	void singlePlanningMatchingTwoUsages_bothAdvisorsFire_withSameContext()
	{
		final MaterialPlanningContext sharedContext = newContext();

		final Map<PlanningUsage, MaterialPlanningContext> contextsByUsage = new EnumMap<>(PlanningUsage.class);
		contextsByUsage.put(PlanningUsage.MANUFACTURING, sharedContext);
		contextsByUsage.put(PlanningUsage.DISTRIBUTION, sharedContext);
		when(helper.createContextsByUsage(any())).thenReturn(contextsByUsage);

		final MaterialEvent ppEvent = mock(MaterialEvent.class, "ppEvent");
		final MaterialEvent ddEvent = mock(MaterialEvent.class, "ddEvent");
		final SupplyRequiredAdvisor ppAdvisor = advisor(PlanningUsage.MANUFACTURING, ppEvent);
		final SupplyRequiredAdvisor ddAdvisor = advisor(PlanningUsage.DISTRIBUTION, ddEvent);

		final SupplyRequiredHandler handler = new SupplyRequiredHandler(
				postMaterialEventService,
				ImmutableList.of(ppAdvisor, ddAdvisor),
				helper);

		handler.handleEvent(SupplyRequiredEvent.of(newDescriptor()));

		verify(ppAdvisor).createAdvisedEvents(any(), same(sharedContext));
		verify(ddAdvisor).createAdvisedEvents(any(), same(sharedContext));
		verify(postMaterialEventService, times(2)).enqueueEventNow(any());
	}

	/**
	 * When no planning matches any usage, no advisor is invoked and a single
	 * {@code NoSupplyAdviceEvent} is posted so the dispo service can react.
	 */
	@Test
	void emptyMap_postsNoSupplyAdviceEvent()
	{
		when(helper.createContextsByUsage(any())).thenReturn(new EnumMap<>(PlanningUsage.class));

		final SupplyRequiredAdvisor ppAdvisor = advisor(PlanningUsage.MANUFACTURING, null);

		final SupplyRequiredHandler handler = new SupplyRequiredHandler(
				postMaterialEventService,
				ImmutableList.of(ppAdvisor),
				helper);

		handler.handleEvent(SupplyRequiredEvent.of(newDescriptor()));

		verify(ppAdvisor, never()).createAdvisedEvents(any(), any());
		// Exactly one event posted: the NoSupplyAdviceEvent.
		verify(postMaterialEventService, times(1)).enqueueEventNow(any());
	}

	private static SupplyRequiredDescriptor newDescriptor()
	{
		return SupplyRequiredDescriptor.builder()
				.demandCandidateId(1)
				.eventDescriptor(EventDescriptor.ofClientAndOrg(ClientAndOrgId.ofClientAndOrg(1, 2)))
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(ProductDescriptor.forProductAndAttributes(
								1,
								AttributesKey.ofString("1"),
								0))
						.warehouseId(WarehouseId.MAIN)
						.quantity(BigDecimal.TEN)
						.date(Instant.parse("2026-01-01T00:00:00Z"))
						.build())
				.build();
	}

	private static MaterialPlanningContext newContext()
	{
		return MaterialPlanningContext.builder()
				.productId(ProductId.ofRepoId(1))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.warehouseId(WarehouseId.MAIN)
				.productPlanning(ProductPlanning.builder().orgId(OrgId.ANY).build())
				.plantId(ResourceId.ofRepoId(2))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, 2))
				.build();
	}

	private SupplyRequiredAdvisor advisor(
			final PlanningUsage usage,
			final MaterialEvent eventOrNull)
	{
		final SupplyRequiredAdvisor advisor = mock(SupplyRequiredAdvisor.class, "advisor-" + usage);
		when(advisor.getPlanningUsage()).thenReturn(usage);
		final List<? extends MaterialEvent> events = eventOrNull != null
				? ImmutableList.of(eventOrNull)
				: ImmutableList.of();
		when(advisor.createAdvisedEvents(any(), any())).thenAnswer(inv -> events);
		return advisor;
	}
}
