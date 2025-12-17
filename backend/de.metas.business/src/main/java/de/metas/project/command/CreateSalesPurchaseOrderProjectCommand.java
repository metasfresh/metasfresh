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

package de.metas.project.command;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.money.CurrencyId;
import de.metas.order.model.I_C_Order;
import de.metas.organization.OrgId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.service.CreateProjectRequest;
import de.metas.project.service.ProjectService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.adempiere.warehouse.WarehouseId;

@Builder
@RequiredArgsConstructor
public class CreateSalesPurchaseOrderProjectCommand
{
	private final I_C_Order order;
	private final ProjectService projectService;

	public ProjectId execute()
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		return projectService.createProject(CreateProjectRequest.builder()
				.orgId(OrgId.ofRepoId(order.getAD_Org_ID()))
				.currencyId(CurrencyId.ofRepoId(order.getC_Currency_ID()))
				.warehouseId(WarehouseId.ofRepoId(order.getM_Warehouse_ID()))
				.bpartnerAndLocationId(BPartnerLocationId.ofRepoId(bpartnerId, order.getC_BPartner_Location_ID()))
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, order.getAD_User_ID()))
				.projectCategory(ProjectCategory.SalesPurchaseOrder)
				.build());
	}
}
