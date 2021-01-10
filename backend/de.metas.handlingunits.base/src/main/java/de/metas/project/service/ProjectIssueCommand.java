/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.project.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.model.MProjectIssue;
import org.compiere.model.MTransaction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.time.ZonedDateTime;

@ToString
class ProjectIssueCommand
{
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final ProjectService projectService;

	private final ProjectIssueRequest request;
	private I_C_Project _project = null; // lazy
	private I_M_HU _hu = null; // lazy
	private LocatorId _locatorId = null; // lazy

	@Builder
	private ProjectIssueCommand(
			@NonNull final ProjectService projectService,
			@NonNull final ProjectIssueRequest request)
	{
		this.projectService = projectService;
		this.request = request;
	}

	public void execute()
	{
		final I_C_ProjectIssue projectIssue = createProjectIssue();
		createProjectLine(projectIssue);

		unloadHU(projectIssue);
		createMTransaction(projectIssue);

	}

	private I_C_ProjectIssue createProjectIssue()
	{
		final I_C_Project project = getProject();
		final Quantity qtyInStockingUOM = uomConversionBL.convertToProductUOM(request.getQty(), request.getProductId());
		final MProjectIssue projectIssue = new MProjectIssue(project);
		projectIssue.setAD_Org_ID(project.getAD_Org_ID());
		projectIssue.setM_Locator_ID(getLocatorId().getRepoId());
		projectIssue.setM_Product_ID(request.getProductId().getRepoId());
		projectIssue.setMovementQty(qtyInStockingUOM.toBigDecimal());
		projectIssue.setMovementDate(TimeUtil.asTimestamp(request.getDate()));
		projectIssue.setProcessed(true);
		InterfaceWrapperHelper.save(projectIssue);
		return projectIssue;
	}

	private I_C_Project getProject()
	{
		return _project == null
				? _project = projectService.getById(request.getProjectId())
				: _project;
	}

	private I_M_HU getHU()
	{
		return _hu == null
				? _hu = handlingUnitsBL.getById(request.getHuId())
				: _hu;
	}

	private LocatorId getLocatorId()
	{
		return _locatorId == null
				? _locatorId = IHandlingUnitsBL.extractLocatorId(getHU())
				: _locatorId;
	}

	private void unloadHU(final I_C_ProjectIssue projectIssue)
	{
		final ProductId productId = request.getProductId();
		final Quantity qty = request.getQty();
		final ZonedDateTime date = request.getDate();
		final I_M_HU hu = getHU();

		HULoader.builder()
				.source(HUListAllocationSourceDestination.of(hu))
				.destination(new GenericAllocationSourceDestination(
						new PlainProductStorage(productId, qty.toInfinite()),
						projectIssue))
				.load(AllocationUtils.builder()
						.setHUContext(handlingUnitsBL.createMutableHUContextForProcessing())
						.setProduct(productId)
						.setQuantity(qty)
						.setDate(date)
						.setFromReferencedModel(null)
						.setForceQtyAllocation(true)
						.create());

		addAssignedHandlingUnit(projectIssue, hu);
	}

	private void createMTransaction(@NonNull final I_C_ProjectIssue projectIssue)
	{
		final I_C_Project project = getProject();
		final OrgId orgId = OrgId.ofRepoId(project.getAD_Org_ID());

		final MTransaction mtrx = new MTransaction(Env.getCtx(),
				orgId.getRepoId(),
				MTransaction.MOVEMENTTYPE_WorkOrderPlus,
				getLocatorId().getRepoId(),
				request.getProductId().getRepoId(),
				AttributeSetInstanceId.NONE.getRepoId(),
				request.getQty().toBigDecimal().negate(),
				TimeUtil.asTimestamp(request.getDate()),
				ITrx.TRXNAME_ThreadInherited);
		mtrx.setC_ProjectIssue_ID(projectIssue.getC_ProjectIssue_ID());
		InterfaceWrapperHelper.save(mtrx);
	}

	private void createProjectLine(@NonNull final I_C_ProjectIssue projectIssue)
	{
		final I_C_Project project = getProject();

		projectService.createProjectLine(CreateProjectLineRequest.builder()
				.projectId(ProjectId.ofRepoId(project.getC_Project_ID()))
				.orgId(OrgId.ofRepoId(project.getAD_Org_ID()))
				.projectIssueId(projectIssue.getC_ProjectIssue_ID())
				.productId(ProductId.ofRepoId(projectIssue.getM_Product_ID()))
				.committedQty(projectIssue.getMovementQty())
				.description(projectIssue.getDescription())
				.build());
	}

	private void addAssignedHandlingUnit(final I_C_ProjectIssue projectIssue, final I_M_HU hu)
	{
		huAssignmentBL.addAssignedHandlingUnits(projectIssue, ImmutableList.of(hu));
	}

}
