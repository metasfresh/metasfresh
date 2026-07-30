package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.qty_reservation.MaterialCockpitV2RowVO;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.qty_reservation.DeleteQtyReservationRequest;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import org.springframework.beans.factory.annotation.Autowired;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class MD_CockpitV2_DeleteQtyReservation
		extends MaterialCockpitV2BasedProcess
		implements IProcessPrecondition
{
	@Autowired private de.metas.inoutcandidate.qty_reservation.QtyReservationService qtyReservationService;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ExplainedOptional<QtyTU> optionalQtyToUnReserveTU = getQtyToUnReserveTU();
		if (!optionalQtyToUnReserveTU.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(optionalQtyToUnReserveTU.getExplanation());
		}

		final QtyTU qtyToUnReserveTU = optionalQtyToUnReserveTU.get();

		return ProcessPreconditionsResolution.accept()
				.withCaptionMapper(caption -> TranslatableStrings.builder()
						.append(caption).append(" (").append(qtyToUnReserveTU.toInt()).append(")")
						.build());
	}

	@Override
	protected String doIt()
	{
		qtyReservationService.deleteReservation(createDeleteQtyReservationRequest().orElseThrow());
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success) {return;}
		invalidateViewSelection();
	}

	private ExplainedOptional<QtyTU> getQtyToUnReserveTU()
	{
		final ExplainedOptional<QtyTU> result = createDeleteQtyReservationRequest().map(qtyReservationService::getReservedQtyTU);
		if (result.isPresent() && result.get().isZero())
		{
			return ExplainedOptional.emptyBecause("Nothing to unreserve");
		}

		return result;
	}

	private ExplainedOptional<DeleteQtyReservationRequest> createDeleteQtyReservationRequest()
	{
		if (!isSingleSelectedRow())
		{
			return ExplainedOptional.emptyBecause("Exactly one row must be selected");
		}

		final MaterialCockpitV2RowVO rowVO = getSingleSelectedMaterialCockpitRow();
		if (!rowVO.isAvailableForUnReservation())
		{
			return ExplainedOptional.emptyBecause("Not eligible for unreservation");
		}

		return ExplainedOptional.of(
				DeleteQtyReservationRequest.builder()
						.orderAndLineId(getSalesOrderAndLineId())
						.supplyType(rowVO.getSupplyType())
						.datePromised(rowVO.getSupplyType().isPlannedSupply() ? rowVO.getDatePromised() : null)
						.build()
		);
	}

}
