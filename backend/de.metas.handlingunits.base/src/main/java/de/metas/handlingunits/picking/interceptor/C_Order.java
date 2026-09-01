package de.metas.handlingunits.picking.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.order.OrderId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order
{
	@NonNull private final PickingJobService pickingJobService;

	// Watches POReference only — intentionally NOT C_BPartner_ID. Switching an existing order's customer
	// to a dummy-GRAI customer (PO reference untouched) is a rare back-office action; it is not re-validated
	// at save and is caught instead by the BEFORE_COMPLETE backstop below. (me03 30607 — deliberate scope.)
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_Order.COLUMNNAME_POReference)
	public void validateDummyGRAIPrerequisitesOnPOReferenceChange(@NonNull final I_C_Order order)
	{
		if (!DocStatus.ofCode(order.getDocStatus()).isCompletedOrClosed())
		{
			return;
		}
		assertDummyGRAIPrerequisites(order);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void validateDummyGRAIPrerequisitesOnComplete(@NonNull final I_C_Order order)
	{
		assertDummyGRAIPrerequisites(order);
	}

	/**
	 * For a completed sales order, fail fast when the order's customer requires dummy GRAIs but the PO reference cannot
	 * form a valid dummy-GRAI serial prefix — so the back-office actor who can fix the PO reference gets the
	 * feedback at data entry / completion, instead of the picker hitting it only at picking completion.
	 */
	private void assertDummyGRAIPrerequisites(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return;
		}
		final OrderId salesOrderId = OrderId.ofRepoId(order.getC_Order_ID());
		final BPartnerId customerId = BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
		pickingJobService.assertDummyGRAIPrerequisitesForSalesOrder(salesOrderId, customerId, order.getPOReference());
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_CLOSE })
	public void beforeClose(@NonNull final I_C_Order order)
	{
		abortPickingJobs(order);
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void beforeReverse(@NonNull final I_C_Order order)
	{
		abortPickingJobs(order);
	}

	private void abortPickingJobs(final @NonNull I_C_Order order)
	{
		if (order.isSOTrx())
		{
			final OrderId salesOrderId = OrderId.ofRepoId(order.getC_Order_ID());
			pickingJobService.abortForSalesOrderId(salesOrderId);
		}
	}
}
