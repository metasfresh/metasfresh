package de.metas.invoicecandidate.modelvalidator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupCompensationChangesHandler;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderLineId;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
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
	private final InvoiceCandidateGroupCompensationChangesHandler groupChangesHandler;

	public C_OrderLine(@NonNull final InvoiceCandidateGroupRepository groupsRepo)
	{
		this.groupChangesHandler = InvoiceCandidateGroupCompensationChangesHandler.builder()
				.groupsRepo(groupsRepo)
				.build();
	}

	@VisibleForTesting
	public static C_OrderLine newInstanceForUnitTesting()
	{
		return new C_OrderLine(new InvoiceCandidateGroupRepository(new GroupCompensationLineCreateRequestFactory()));
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
	 * Keeps a not-yet-processed invoice candidate's (denormalized) {@code C_Order_CompensationGroup_ID} in sync
	 * with its order line whenever the line joins or leaves a compensation group. {@code C_OrderLine_Handler}
	 * only copies the group onto the candidate once, at candidate creation — nothing else re-derives it
	 * afterwards, so an order line that changes group membership after its candidate already exists (e.g. a
	 * contract-created group destroyed on reactivation and rebuilt on the next completion) is left with a
	 * stale reference: pointing at an already-destroyed group (an immediate FK violation the moment that
     * group's header row is deleted), or missing from the rebuilt group's percent-discount base (a silently
	 * wrong invoice amount, once the FK case is avoided). A processed candidate is left alone — its group
	 * membership is history, not a live reference to keep current.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_C_OrderLine.COLUMNNAME_C_Order_CompensationGroup_ID)
	public void syncInvoiceCandidateGroupReference(@NonNull final I_C_OrderLine ol)
	{
		final int orderCompensationGroupId = ol.getC_Order_CompensationGroup_ID();

		invoiceCandDAO.retrieveReferencing(TableRecordReference.of(ol))
				.stream()
				.filter(ic -> !ic.isProcessed())
				.filter(ic -> ic.getC_Order_CompensationGroup_ID() != orderCompensationGroupId)
				.forEach(ic -> {
					ic.setC_Order_CompensationGroup_ID(orderCompensationGroupId);
					InterfaceWrapperHelper.saveRecord(ic);

					// Setting the group column alone does not re-trigger the group's percent-discount
					// recompute -- that cascade is wired to a *different* column (NetAmtToInvoice /
					// GroupCompensationPercentage, see C_Invoice_Candidate#handleCompensantionGroupRelatedChanges),
					// which this candidate's own value may not actually change. Invoke the same handler this
					// line's own NetAmtToInvoice-change would have invoked, so the group's compensation lines
					// still get invalidated (and thus recomputed) against the now-current regular-line set.
					groupChangesHandler.onInvoiceCandidateChanged(ic);
				});
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
