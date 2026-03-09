package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.inoutcandidate.qty_reservation.CreateQtyReservationRequest;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.project.ProjectId;
import de.metas.project.service.ProjectRepository;
import de.metas.ui.web.order.sales.hu.reservation.process.MaterialCockpitSalesOrderLine;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class MD_CockpitV2_MakeQtyReservation
		extends MaterialCockpitV2BasedProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired private QtyReservationService qtyReservationService;
	@Autowired private ProjectRepository projectRepository;

	private static final String PARAM_QtyOrderedNotReserved_TU = "QtyOrderedNotReserved_TU";
	@Param(parameterName = PARAM_QtyOrderedNotReserved_TU)
	private BigDecimal p_QtyOrderedNotReserved_TU;

	private static final String PARAM_QtyAvailableTU = "QtyAvailableTU";
	@Param(parameterName = PARAM_QtyAvailableTU)
	private BigDecimal p_QtyAvailableTU;

	private static final String PARAM_QtyTU = "QtyTU";
	@Param(parameterName = PARAM_QtyTU, mandatory = true)
	private BigDecimal p_qtyToReserveTU;

	private QtyTU _qtyToReserveMaxTU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Exactly one row must be selected");
		}

		final MaterialCockpitV2RowVO rowVO = getSingleSelectedMaterialCockpitRow();
		if (!rowVO.isAvailableForReservation())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No available TU quantity");
		}

		if (!getQtyOrderedNotReservedTU().isPositive())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Nothing more to reserve");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		final String parameterName = parameter.getColumnName();

		if (PARAM_QtyOrderedNotReserved_TU.equals(parameterName))
		{
			return getQtyOrderedNotReservedTU().toInt();
		}
		else if (PARAM_QtyAvailableTU.equals(parameterName))
		{
			return getQtyAvailableToReserveTU().toInt();
		}
		else if (PARAM_QtyTU.equals(parameterName))
		{
			return getMaxQtyToAllocateTU().toInt();
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final QtyTU qtyToReserveTU = getAndValidateQtyToReserveTUParam();
		final MaterialCockpitV2RowVO rowVO = getSingleSelectedMaterialCockpitRow();

		qtyReservationService.makeReservation(
				CreateQtyReservationRequest.builder()
						.orderAndLineId(getSalesOrderAndLineId())
						.productId(rowVO.getProductId())
						.warehouseId(rowVO.getWarehouseId())
						.supplyType(rowVO.getSupplyType())
						.datePromised(rowVO.getDatePromised())
						.vendorBPartnerId(rowVO.getVendorBPartnerId())
						.attributesKey(rowVO.getAttributesKey())
						.projectId(extractProjectId(rowVO))
						.qtyTU(qtyToReserveTU)
						.qty(rowVO.computeQtyCUToReserve(qtyToReserveTU))
						.build()
		);

		return MSG_OK;
	}

	private ProjectId extractProjectId(final MaterialCockpitV2RowVO rowVO)
	{
		return rowVO.getProjectValue() != null
				? projectRepository.getIdByValueOrNull(rowVO.getProjectValue())
				: null;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success) {return;}
		recreateSelection();
	}

	private @NotNull QtyTU getAndValidateQtyToReserveTUParam()
	{
		final QtyTU qtyToReserveTU = QtyTU.ofBigDecimal(p_qtyToReserveTU);
		if (qtyToReserveTU.isZero())
		{
			throw new AdempiereException("@QtyTU@ <= 0");
		}

		final QtyTU qtyToReserveMaxTU = getMaxQtyToAllocateTU();
		if (qtyToReserveTU.isGreaterThan(qtyToReserveMaxTU))
		{
			throw new AdempiereException("@QtyTU@ > " + qtyToReserveMaxTU.toInt());
		}
		return qtyToReserveTU;
	}

	private QtyTU getMaxQtyToAllocateTU()
	{
		if (_qtyToReserveMaxTU == null)
		{
			_qtyToReserveMaxTU = computeMaxQtyToAllocateTU();
		}
		return _qtyToReserveMaxTU;
	}

	private QtyTU computeMaxQtyToAllocateTU()
	{
		final QtyTU qtyAvailableToReserveTU = getQtyAvailableToReserveTU();
		if (!qtyAvailableToReserveTU.isPositive())
		{
			return QtyTU.ZERO;
		}

		final MaterialCockpitSalesOrderLine salesOrderLine = getSalesOrderLine();
		final QtyTU qtyOrderedTU = salesOrderLine.getQtyOrderedTU();
		final QtyTU qtyReservedTU = qtyReservationService.getReservedQtyTU(salesOrderLine.getId());
		final QtyTU qtyToReserveTU = qtyOrderedTU.subtractOrZero(qtyReservedTU);

		return qtyToReserveTU.min(qtyAvailableToReserveTU);
	}

	private QtyTU getQtyOrderedNotReservedTU()
	{
		final MaterialCockpitSalesOrderLine salesOrderLine = getSalesOrderLine();
		final QtyTU qtyOrderedTU = salesOrderLine.getQtyOrderedTU();
		final QtyTU qtyReservedTU = qtyReservationService.getReservedQtyTU(salesOrderLine.getId());
		return qtyOrderedTU.subtractOrZero(qtyReservedTU);
	}

	private QtyTU getQtyAvailableToReserveTU()
	{
		final MaterialCockpitV2RowVO rowVO = getSingleSelectedMaterialCockpitRow();
		return rowVO.getQtyTU();
	}
}
