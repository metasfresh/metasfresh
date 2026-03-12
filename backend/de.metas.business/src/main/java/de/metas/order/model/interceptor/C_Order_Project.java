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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.PurchaseOrderProjectListener.ProjectCreatedEvent;
import de.metas.order.PurchaseOrderProjectListenerDispatcher;
import de.metas.order.model.I_C_Order;
import de.metas.organization.OrgId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectTypeId;
import de.metas.project.ProjectTypeRepository;
import de.metas.project.service.CreateProjectRequest;
import de.metas.project.service.ProjectService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class acts as an interceptor for the {@link I_C_Order} model. Its primary purpose is
 * to handle project-related logic for purchase and sales orders.
 * <p>
 * Responsibilities:
 * - Automatically associates a project with a purchase or sales order if certain conditions are met.
 * - Promotes a project from order lines to the order when applicable.
 * - Creates a new project and associates it with an order and its lines if no appropriate project exists.
 * - Propagates the project association from the order to its lines.
 * <p>
 * Dependency injection is used to provide services and repositories required for its operations.
 * <p>
 * Interceptor Details:
 * - This interceptor is registered for the {@link I_C_Order} model and intervenes
 * during the validation timing {@code TIMING_BEFORE_COMPLETE}.
 * <p>
 * Methods:
 * <p>
 * 1. {@code po_populateProjectIfNeeded(I_C_Order order)}:
 * - A public method annotated with {@link @DocValidate}.
 * - Triggered during the {@code TIMING_BEFORE_COMPLETE} lifecycle event.
 * - Schedules the method to populate project information after the current transaction commits.
 * <p>
 * 2. {@code populateProjectIfNeeded(I_C_Order order)}:
 * - A private method that performs the core logic of validating and associating a project with the order.
 * - Checks if an order already has a project association.
 * - Retrieves order lines and examines their associated projects.
 * - Promotes projects from order lines to the order if only one unique project exists.
 * - Creates a new project if no project exists and associates it with the order and its lines.
 * <p>
 * 3. {@code createNewSalesPurchaseOrderProject(I_C_Order order)}:
 * - A private helper method responsible for creating a new project of type "Sales/Purchase Order."
 * - Uses the provided order details to configure the project.
 * <p>
 * 4. {@code setProjectIdToOrderLines(ProjectId newProjectId, List<I_C_OrderLine> lines)}:
 * - A private helper method that assigns a specific project ID to all applicable order lines.
 * - Propagates the project ID changes to the persistent layer and related entities.
 */
@Component
@Interceptor(I_C_Order.class)
@RequiredArgsConstructor
public class C_Order_Project
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ProjectService projectService;
	@NonNull private final ProjectTypeRepository projectTypeRepository;
	@NonNull private final PurchaseOrderProjectListenerDispatcher eventDispatcher;

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void beforeComplete(@NonNull final I_C_Order order)
	{
		populateProjectIfNeeded(order);
	}

	private void populateProjectIfNeeded(final @NonNull I_C_Order purchaseOrder)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(purchaseOrder.getC_Project_ID());
		if (purchaseOrder.isSOTrx() || projectId != null)
		{
			return;
		}
		final List<I_C_OrderLine> lines = orderBL.getLinesByOrderIds(Collections.singleton(OrderId.ofRepoId(purchaseOrder.getC_Order_ID())));
		final Set<ProjectId> orderLineProjectIds = lines.stream()
				.map(ol -> ProjectId.ofRepoIdOrNull(ol.getC_Project_ID()))
				.collect(Collectors.toCollection(HashSet::new));
		final boolean isNullProjectIdsOnLines = orderLineProjectIds.removeIf(Objects::isNull);
		if (!isNullProjectIdsOnLines && orderLineProjectIds.size() == 1)
		{
			//promote the only project found on the lines to the order
			final ProjectId olProjectId = orderLineProjectIds.iterator().next();
			purchaseOrder.setC_Project_ID(olProjectId.getRepoId());
			setProjectIdToOrderLines(olProjectId, lines);
			return;
		}

		if (isNullProjectIdsOnLines)
		{
			final ProjectTypeId projectTypeId = projectTypeRepository.getFirstIdByProjectCategoryAndOrgOrNull(ProjectCategory.SalesPurchaseOrder, OrgId.ofRepoId(purchaseOrder.getAD_Org_ID()), false);
			if (projectTypeId == null)
			{
				//no project type for `Sales/Purchase Order` defined, can't create a new project for this order
				return;
			}

			final ProjectId newProjectId = createNewSalesPurchaseOrderProject(purchaseOrder);
			if (orderLineProjectIds.size() <= 1)
			{
				purchaseOrder.setC_Project_ID(newProjectId.getRepoId());
			}
			setProjectIdToOrderLines(newProjectId, lines);
		}
	}

	private ProjectId createNewSalesPurchaseOrderProject(final @NonNull I_C_Order purchaseOrder)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(purchaseOrder.getC_BPartner_ID());
		return projectService.createProject(CreateProjectRequest.builder()
				.projectCategory(ProjectCategory.SalesPurchaseOrder)
				.orgId(OrgId.ofRepoId(purchaseOrder.getAD_Org_ID()))
				.currencyId(CurrencyId.ofRepoId(purchaseOrder.getC_Currency_ID()))
				.warehouseId(WarehouseId.ofRepoId(purchaseOrder.getM_Warehouse_ID()))
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(bpartnerId, purchaseOrder.getC_BPartner_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, purchaseOrder.getAD_User_ID()))
				.build());
	}

	private void setProjectIdToOrderLines(@NonNull final ProjectId newProjectId, @NonNull final List<I_C_OrderLine> poLines)
	{
		final HashMap<OrderAndLineId, I_C_OrderLine> poLinesUpdated = new HashMap<>();

		poLines.stream()
				.filter(ol -> ProjectId.ofRepoIdOrNull(ol.getC_Project_ID()) == null)
				.forEach(ol -> {
					ol.setC_Project_ID(newProjectId.getRepoId());
					poLinesUpdated.put(OrderAndLineId.ofRepoIds(ol.getC_Order_ID(), ol.getC_OrderLine_ID()), ol);
				});
		if (poLinesUpdated.isEmpty())
		{
			return;
		}

		InterfaceWrapperHelper.saveAll(poLinesUpdated.values());

		eventDispatcher.fireProjectCreatedEvent(ProjectCreatedEvent.builder()
				.projectId(newProjectId)
				.purchaseOrderLineIds(ImmutableSet.copyOf(poLinesUpdated.keySet()))
				.byUserId(Env.getLoggedUserId())
				.build());
	}
}
