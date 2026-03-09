package de.metas.invoicecandidate.modelvalidator;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.order.OrderLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import java.util.List;

@Interceptor(I_C_OrderLine.class)
public class C_OrderLine
{
	private static final Logger logger = LogManager.getLogger(C_OrderLine.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = {
					I_C_OrderLine.COLUMNNAME_QtyOrdered // task 08452: make sure the IC gets invalidated when we sort of "close" a single line
					, I_C_OrderLine.COLUMNNAME_QtyOrderedOverUnder
					, I_C_OrderLine.COLUMNNAME_IsPackagingMaterial
					, I_C_OrderLine.COLUMNNAME_M_Product_ID
			})
	public void invalidateInvoiceCandidates(final I_C_OrderLine ol)
	{
		Services.get(IInvoiceCandidateHandlerBL.class).invalidateCandidatesFor(ol);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteInvoiceCandidates(final I_C_OrderLine ol)
	{
		Services.get(IInvoiceCandDAO.class).deleteAllReferencingInvoiceCandidates(ol);
	}

	/**
	 * When C_Project_ID changes on an order line (e.g. inherited back from a dropship purchase order),
	 * propagate it to the corresponding invoice candidates and shipment schedule.
	 *
	 * @see <a href="https://github.com/metasfresh/me03/issues/28709">me03#28709</a>
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_C_OrderLine.COLUMNNAME_C_Project_ID)
	public void propagateProjectIdToICAndShipmentSchedule(@NonNull final I_C_OrderLine orderLine)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID());
		final int projectId = orderLine.getC_Project_ID();

		propagateProjectIdToInvoiceCandidates(orderLineId, projectId);
		propagateProjectIdToShipmentSchedule(orderLineId, projectId);
	}

	private void propagateProjectIdToInvoiceCandidates(@NonNull final OrderLineId orderLineId, final int projectId)
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = Services.get(IInvoiceCandDAO.class)
				.retrieveInvoiceCandidatesForOrderLineId(orderLineId);
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			if (ic.isProcessed())
			{
				continue;
			}
			if (ic.getC_Project_ID() == projectId)
			{
				continue;
			}

			ic.setC_Project_ID(projectId);
			InterfaceWrapperHelper.save(ic);
			logger.debug("Updated C_Project_ID={} on C_Invoice_Candidate_ID={} from C_OrderLine_ID={}", projectId, ic.getC_Invoice_Candidate_ID(), orderLineId);
		}
	}

	private void propagateProjectIdToShipmentSchedule(@NonNull final OrderLineId orderLineId, final int projectId)
	{
		final I_M_ShipmentSchedule shipmentSchedule;
		try
		{
			shipmentSchedule = Services.get(IShipmentSchedulePA.class).getByOrderLineId(orderLineId);
		}
		catch (final AdempiereException e)
		{
			logger.debug("No shipment schedule found for C_OrderLine_ID={}; skip project propagation", orderLineId, e);
			return;
		}

		if (shipmentSchedule.isProcessed())
		{
			return;
		}
		if (shipmentSchedule.getC_Project_ID() == projectId)
		{
			return;
		}

		shipmentSchedule.setC_Project_ID(projectId);
		InterfaceWrapperHelper.save(shipmentSchedule);
		logger.debug("Updated C_Project_ID={} on M_ShipmentSchedule_ID={} from C_OrderLine_ID={}", projectId, shipmentSchedule.getM_ShipmentSchedule_ID(), orderLineId);
	}
}
