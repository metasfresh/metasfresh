/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.order.model.interceptor;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.order.model.I_C_Order;
import de.metas.organization.OrgId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.command.CreateSalesPurchaseOrderProjectCommand;
import de.metas.project.service.ProjectService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Intercepts and validates operations on {@link I_C_Order} entities.
 * This class is responsible for ensuring that project associations for orders are properly managed
 * and internally consistent, particularly during the {@link ModelValidator.TIMING_BEFORE_COMPLETE}
 * lifecycle timing of an order.
 * <p>
 * Responsibilities:
 * - Ensures proper project assignment for orders when certain conditions are met.
 * - Analyzes associated order lines to determine whether a single, identifiable project can
 * be linked to the order.
 * - Creates new projects for orders when no identifiable or existing project is determined.
 * - Propagates the newly assigned project to all related order lines with null project references.
 * <p>
 * Annotations:
 * - {@link Component}: Marks this class as a managed Spring component.
 * - {@link RequiredArgsConstructor}: Generates a constructor accepting final fields as parameters.
 * - {@link Interceptor}: A validation interface associated with {@link I_C_Order}.
 */
@Component
@RequiredArgsConstructor
@Interceptor(I_C_Order.class)
public class C_Order_Project
{
	private final ProjectService projectService;
	private final ProjectTypeRepository projectTypeRepository;
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void po_populateProjectIfNeeded(@NonNull final I_C_Order order)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(order.getC_Project_ID());
		if (order.isSOTrx() || projectId != null)
		{
			return;
		}
		final List<I_C_OrderLine> lines = orderBL.getLinesByOrderIds(Collections.singleton(OrderId.ofRepoId(order.getC_Order_ID())));
		final Set<ProjectId> orderLineProjectIds = lines.stream()
				.map(ol -> ProjectId.ofRepoIdOrNull(ol.getC_Project_ID()))
				.collect(Collectors.toCollection(HashSet::new));
		final boolean isNullProjectIdsOnLines = orderLineProjectIds.removeIf(Objects::isNull);
		if (!isNullProjectIdsOnLines && orderLineProjectIds.size() == 1)
		{
			//promote the only project found on the lines to the order
			setProjectIdToOrderAndLines(orderLineProjectIds.iterator().next(), order, lines);
			return;
		}
		else if (orderLineProjectIds.size() > 1)
		{
			//can't identify a single project for this order's lines.
			return;
		}
		final ProjectTypeId projectTypeId = projectTypeRepository.getFirstIdByProjectCategoryAndOrg(ProjectCategory.SalesPurchaseOrder, OrgId.ofRepoId(order.getAD_Org_ID()));
		if (projectTypeId == null)
		{
			//no project type for `Sales/Purchase Order` defined, can't create a new project for this order
			return;
		}

		final ProjectId newProjectId = CreateSalesPurchaseOrderProjectCommand.builder()
				.projectService(projectService)
				.order(order)
				.build()
				.execute();
		setProjectIdToOrderAndLines(newProjectId, order, lines);
	}

	private static void setProjectIdToOrderAndLines(@NonNull final ProjectId newProjectId, final @NonNull I_C_Order order, @NonNull final List<I_C_OrderLine> lines)
	{
		order.setC_Project_ID(newProjectId.getRepoId());
		lines.stream()
				.filter(ol -> ProjectId.ofRepoIdOrNull(ol.getC_Project_ID()) == null)
				.forEach(ol -> ol.setC_Project_ID(newProjectId.getRepoId()));
		InterfaceWrapperHelper.saveAll(lines);
		//TODO propagate project to any sales order that's generated this PO
	}
}
