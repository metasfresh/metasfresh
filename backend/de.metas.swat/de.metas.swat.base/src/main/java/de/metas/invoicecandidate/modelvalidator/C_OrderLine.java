package de.metas.invoicecandidate.modelvalidator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupService;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final InvoiceCandidateGroupService invoiceCandidateGroupService;

	public C_OrderLine(@NonNull final InvoiceCandidateGroupService invoiceCandidateGroupService)
	{
		this.invoiceCandidateGroupService = invoiceCandidateGroupService;
	}

	@VisibleForTesting
	public static C_OrderLine newInstanceForUnitTesting()
	{
		return SpringContextHolder.getBeanOrSupply(
				C_OrderLine.class,
				() -> new C_OrderLine(InvoiceCandidateGroupService.newInstanceForUnitTesting()));
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_QtyOrdered // task 08452: make sure the IC gets invalidated when we sort of "close" a single line
					, I_C_OrderLine.COLUMNNAME_QtyOrderedOverUnder
					, I_C_OrderLine.COLUMNNAME_IsPackagingMaterial
					, I_C_OrderLine.COLUMNNAME_M_Product_ID
			})
	public void invalidateInvoiceCandidates(final I_C_OrderLine ol)
	{
		invoiceCandidateHandlerBL.invalidateCandidatesFor(ol);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteInvoiceCandidates(final I_C_OrderLine ol)
	{
		invoiceCandDAO.deleteAllReferencingInvoiceCandidates(ol);
	}

	/**
	 * Keeps the invoice candidates' {@code C_Order_CompensationGroup_ID} in sync with the order line
	 * (see {@link InvoiceCandidateGroupService#syncGroupReferenceFromOrderLine}).
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID)
	public void syncInvoiceCandidateGroupReference(@NonNull final I_C_OrderLine ol)
	{
		invoiceCandidateGroupService.syncGroupReferenceFromOrderLine(
				OrderLineId.ofRepoId(ol.getC_OrderLine_ID()),
				OrderGroupRepository.extractGroupIdOrNull(ol));
	}

	/**
	 * When C_Project_ID changes on an order line (e.g. inherited back from a dropship purchase order),
	 * propagate it to the corresponding invoice candidates and shipment schedule.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_C_OrderLine.COLUMNNAME_C_Project_ID)
	public void propagateProjectIdToICAndShipmentSchedule(@NonNull final I_C_OrderLine orderLine)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());
		@Nullable final ProjectId projectId = ProjectId.ofRepoIdOrNull(orderLine.getC_Project_ID());

		invoiceCandBL.updateProjectId(orderLineId, projectId);
		shipmentScheduleBL.updateProjectId(orderLineId, projectId);
	}
}
