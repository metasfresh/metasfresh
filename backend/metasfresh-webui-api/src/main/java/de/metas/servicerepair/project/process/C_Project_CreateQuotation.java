/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.servicerepair.project.process;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.order.OrderFactory;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.project.ProjectCategory;
import de.metas.project.ProjectId;
import de.metas.project.ProjectLine;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;

import java.time.ZoneId;
import java.util.Optional;

public class C_Project_CreateQuotation
		extends ServiceOrRepairProjectBasedProcess
		implements IProcessPrecondition
{
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		final ProjectId projectId = ProjectId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (projectId == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		return checkIsServiceOrRepairProject(projectId);
	}

	private ProcessPreconditionsResolution checkIsServiceOrRepairProject(@NonNull final ProjectId projectId)
	{
		final I_C_Project project = projectService.getById(projectId);
		final ProjectCategory projectCategory = ProjectCategory.ofNullableCodeOrGeneral(project.getProjectCategory());
		if (!projectCategory.isServiceOrRepair())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not Service/Repair project");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ProjectId projectId = ProjectId.ofRepoId(getRecord_ID());
		checkIsServiceOrRepairProject(projectId).throwExceptionIfRejected();

		final I_C_Project fromProject = projectService.getById(projectId);

		final OrderFactory orderFactory = newOrderFactory(fromProject);

		for (final ProjectLine line : projectService.getLines(projectId))
		{
			final ProductId productId = line.getProductId();
			final Quantity qty = line.getPlannedQty();

			orderFactory
					.orderLineByProductAndUomOrCreate(productId, qty.getUomId())
					.addQty(qty);
		}

		final I_C_Order order = orderFactory.createDraft();

		getResult().setRecordToOpen(ProcessExecutionResult.RecordsToOpen.builder()
				.record(TableRecordReference.of(I_C_Order.Table_Name, order.getC_Order_ID()))
				.target(ProcessExecutionResult.RecordsToOpen.OpenTarget.SingleDocument)
				.targetTab(ProcessExecutionResult.RecordsToOpen.TargetTab.NEW_TAB)
				.build());

		return MSG_OK;
	}

	private OrderFactory newOrderFactory(@NonNull final I_C_Project project)
	{
		final OrgId orgId = OrgId.ofRepoId(project.getAD_Org_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_SalesOrder)
				.docSubType(X_C_DocType.DOCSUBTYPE_Proposal)
				.adClientId(project.getAD_Client_ID())
				.adOrgId(orgId.getRepoId())
				.build());

		final OrderFactory orderFactory = OrderFactory.newSalesOrder()
				.docType(docTypeId)
				.orgId(orgId)
				.dateOrdered(CoalesceUtil.coalesceSuppliers(
						() -> TimeUtil.asLocalDate(project.getDateContract()),
						() -> SystemTime.asLocalDate()))
				.datePromised(CoalesceUtil.coalesceSuppliers(
						() -> TimeUtil.asZonedDateTime(project.getDateFinish()),
						() -> SystemTime.asZonedDateTimeAtEndOfDay(timeZone)))
				.shipBPartner(
						BPartnerId.ofRepoId(project.getC_BPartner_ID()),
						BPartnerLocationId.ofRepoIdOrNull(project.getC_BPartner_ID(), project.getC_BPartner_Location_ID()),
						BPartnerContactId.ofRepoIdOrNull(project.getC_BPartner_ID(), project.getAD_User_ID()))
				.salesRepId(project.getSalesRep_ID())
				.warehouseId(project.getM_Warehouse_ID())
				.paymentTermId(project.getC_PaymentTerm_ID())
				.campaignId(project.getC_Campaign_ID())
				.projectId(project.getC_Project_ID());

		getPricingSystemId(project).ifPresent(orderFactory::pricingSystemId);

		return orderFactory;
	}

	private Optional<PricingSystemId> getPricingSystemId(final I_C_Project project)
	{
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoIdOrNull(project.getM_PriceList_Version_ID());
		if (priceListVersionId == null)
		{
			return Optional.empty();
		}

		final I_M_PriceList priceList = priceListDAO.getPriceListByPriceListVersionId(priceListVersionId);
		return Optional.of(PricingSystemId.ofRepoId(priceList.getM_PricingSystem_ID()));
	}

}
