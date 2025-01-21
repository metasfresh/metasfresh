package de.metas.distribution.workflows_api;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.workflows_api.facets.DistributionFacetIdsCollection;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.order.IOrderBL;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.util.Services;
import de.metas.workflow.rest_api.model.WFProcessId;
import de.metas.workflow.rest_api.model.WorkflowLauncher;
import de.metas.workflow.rest_api.model.WorkflowLauncherCaption;
import de.metas.workflow.rest_api.model.WorkflowLaunchersList;
import de.metas.workflow.rest_api.model.WorkflowLaunchersQuery;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetGroupList;
import de.metas.workflow.rest_api.model.facets.WorkflowLaunchersFacetQuery;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_Order;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_Order;

import java.util.Optional;

class DistributionWorkflowLaunchersProvider
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPPOrderBL ppOrderBL = Services.get(IPPOrderBL.class);
	private final DistributionRestService distributionRestService;

	public DistributionWorkflowLaunchersProvider(
			final @NonNull DistributionRestService distributionRestService)
	{
		this.distributionRestService = distributionRestService;
	}

	public WorkflowLaunchersList provideLaunchers(@NonNull WorkflowLaunchersQuery query)
	{
		if (query.getFilterByQRCode() != null)
		{
			throw new AdempiereException("Invalid QR Code: " + query.getFilterByQRCode());
		}

		final ImmutableList<WorkflowLauncher> launchers = distributionRestService.streamJobReferencesForUser(
						DDOrderReferenceQuery.builder()
								.responsibleId(query.getUserId())
								.suggestedLimit(query.getLimit().orElse(QueryLimit.NO_LIMIT))
								.activeFacetIds(DistributionFacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getFacetIds()))
								.build()
				)
				.map(this::toWorkflowLauncher)
				.collect(ImmutableList.toImmutableList());

		return WorkflowLaunchersList.builder()
				.launchers(launchers)
				.timestamp(SystemTime.asInstant())
				.build();
	}

	private WorkflowLauncher toWorkflowLauncher(@NonNull final DDOrderReference ddOrderReference)
	{
		if (ddOrderReference.isJobStarted())
		{
			return WorkflowLauncher.builder()
					.applicationId(DistributionMobileApplication.APPLICATION_ID)
					.caption(computeCaption(ddOrderReference))
					.startedWFProcessId(WFProcessId.ofIdPart(DistributionMobileApplication.APPLICATION_ID, ddOrderReference.getDdOrderId()))
					.build();
		}
		else
		{
			return WorkflowLauncher.builder()
					.applicationId(DistributionMobileApplication.APPLICATION_ID)
					.caption(computeCaption(ddOrderReference))
					.wfParameters(DistributionWFProcessStartParams.builder()
							.ddOrderId(ddOrderReference.getDdOrderId())
							.build()
							.toParams())
					.build();
		}
	}

	@NonNull
	private WorkflowLauncherCaption computeCaption(@NonNull final DDOrderReference ddOrderReference)
	{
		final TranslatableStringBuilder captionBuilder = TranslatableStrings.builder();
		getSourceDocumentTypeAndNo(ddOrderReference)
				.ifPresent(sourceDocTypeAndNo -> captionBuilder
						.append(sourceDocTypeAndNo.getLeft())
						.append(" ")
						.append(sourceDocTypeAndNo.getRight())
						.append(" | "));

		captionBuilder
				.append(warehouseBL.getWarehouseName(ddOrderReference.getFromWarehouseId()))
				.append(" > ")
				.append(warehouseBL.getWarehouseName(ddOrderReference.getToWarehouseId()))
				.append(" | ")
				.appendDateTime(ddOrderReference.getDatePromised().toZonedDateTime(orgDAO::getTimeZone));

		if (ddOrderReference.getProductId() != null)
		{
			captionBuilder.append(" | ").append(productBL.getProductValueAndName(ddOrderReference.getProductId()));
		}

		if (ddOrderReference.getQty() != null)
		{
			captionBuilder.append(" | ").appendQty(ddOrderReference.getQty().toBigDecimal(), ddOrderReference.getQty().getUOMSymbol());
		}

		return WorkflowLauncherCaption.of(captionBuilder.build());
	}

	@NonNull
	private Optional<ImmutablePair<ITranslatableString, String>> getSourceDocumentTypeAndNo(@NonNull final DDOrderReference ddOrderReference)
	{
		final ITranslatableString docTypeName;
		final String documentNo;
		if (ddOrderReference.getSalesOrderId() != null)
		{
			final I_C_Order order = orderBL.getById(ddOrderReference.getSalesOrderId());
			docTypeName = docTypeBL.getNameById(DocTypeId.ofRepoId(order.getC_DocType_ID()));
			documentNo = order.getDocumentNo();
		}
		else if (ddOrderReference.getPpOrderId() != null)
		{
			final I_PP_Order ppOrder = ppOrderBL.getById(ddOrderReference.getPpOrderId());
			docTypeName = docTypeBL.getNameById(DocTypeId.ofRepoId(ppOrder.getC_DocType_ID()));
			documentNo = ppOrder.getDocumentNo();
		}
		else
		{
			return Optional.empty();
		}

		return Optional.of(ImmutablePair.of(docTypeName, documentNo));
	}

	public WorkflowLaunchersFacetGroupList getFacets(@NonNull final WorkflowLaunchersFacetQuery query)
	{
		return distributionRestService.getFacets(
						DDOrderReferenceQuery.builder()
								.responsibleId(query.getUserId())
								.activeFacetIds(DistributionFacetIdsCollection.ofWorkflowLaunchersFacetIds(query.getActiveFacetIds()))
								.build()
				)
				.toWorkflowLaunchersFacetGroupList(query.getActiveFacetIds());
	}

}
