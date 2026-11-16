package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.qty_reservation.DeleteQtyReservationRequest;
import de.metas.inoutcandidate.qty_reservation.MaterialCockpitV2RowVO;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.inoutcandidate.qty_reservation.ReduceQtyReservationRequest;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class MD_CockpitV2_DeleteQtyReservation
		extends MaterialCockpitV2BasedProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired private QtyReservationService qtyReservationService;

	private static final String PARAM_QtyTU_Reserved = "QtyTU_Reserved";
	@Param(parameterName = PARAM_QtyTU_Reserved)
	private BigDecimal p_QtyTU_Reserved;

	private static final String PARAM_QtyAvailableTU = "QtyAvailableTU";
	@Param(parameterName = PARAM_QtyAvailableTU)
	private BigDecimal p_QtyAvailableTU;

	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU, mandatory = true)
	private BigDecimal p_qtyToUnreserveTU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ExplainedOptional<QtyTU> optionalQtyToUnReserveTU = getReversibleQtyTU();
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
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();

		if (PARAM_QtyTU.equals(parameterName) || PARAM_QtyTU_Reserved.equals(parameterName))
		{
			return getReversibleQtyTU().orElse(QtyTU.ZERO).toInt();
		}
		else if (PARAM_QtyAvailableTU.equals(parameterName))
		{
			return getSingleSelectedMaterialCockpitRow().getQtyTU().toInt();
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final DeleteQtyReservationRequest bucket = createDeleteQtyReservationRequest().orElseThrow();
		final QtyTU qtyToUnreserveTU = getAndValidateQtyToUnreserveTUParam();

		qtyReservationService.reduceReservation(
				ReduceQtyReservationRequest.builder()
						.orderAndLineId(bucket.getOrderAndLineId())
						.supplyType(bucket.getSupplyType())
						.datePromised(bucket.getDatePromised())
						.qtyToUnreserveTU(qtyToUnreserveTU)
						.build());
		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success) {return;}
		invalidateViewSelection();
	}

	private QtyTU getAndValidateQtyToUnreserveTUParam()
	{
		final QtyTU qtyToUnreserveTU = QtyTU.ofBigDecimal(p_qtyToUnreserveTU);
		if (!qtyToUnreserveTU.isPositive())
		{
			throw new AdempiereException("@QtyTU@ <= 0");
		}

		final QtyTU reversibleTU = getReversibleQtyTU().orElseThrow();
		if (qtyToUnreserveTU.isGreaterThan(reversibleTU))
		{
			throw new AdempiereException("@QtyTU@ > " + reversibleTU.toInt());
		}
		return qtyToUnreserveTU;
	}

	/**
	 * Reversible = reserved minus already delivered, in TU (summed over the bucket's reservation rows).
	 */
	private ExplainedOptional<QtyTU> getReversibleQtyTU()
	{
		final ExplainedOptional<DeleteQtyReservationRequest> optionalRequest = createDeleteQtyReservationRequest();
		if (!optionalRequest.isPresent())
		{
			return ExplainedOptional.emptyBecause(optionalRequest.getExplanation());
		}

		final DeleteQtyReservationRequest request = optionalRequest.get();
		final QtyTU reversibleTU = qtyReservationService.getTotalReversibleQtyTU(
				request.getOrderAndLineId(), request.getSupplyType(), request.getDatePromised());
		if (!reversibleTU.isPositive())
		{
			return ExplainedOptional.emptyBecause("Nothing to unreserve");
		}

		return ExplainedOptional.of(reversibleTU);
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
