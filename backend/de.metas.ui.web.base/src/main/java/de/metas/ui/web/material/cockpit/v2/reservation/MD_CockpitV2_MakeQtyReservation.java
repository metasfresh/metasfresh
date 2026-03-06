package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class MD_CockpitV2_MakeQtyReservation
		extends MaterialCockpitV2BasedProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired private QtyReservationService qtyReservationService;

	private static final String PARAM_QTY_TU = "QtyTU";
	@Param(parameterName = PARAM_QTY_TU, mandatory = true)
	private BigDecimal qtyTUParam;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Exactly one row must be selected");
		}

		final OrderLineId orderLineId = getSalesOrderLineIdOrNull();
		if (orderLineId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No sales order line context");
		}

		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.ofViewRow(getSingleSelectedRow());
		if (!rowVO.getQtyTU().isPositive())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No available TU quantity");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (PARAM_QTY_TU.equals(parameter.getColumnName()))
		{
			return computeDefaultQtyTU();
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.ofViewRow(getSingleSelectedRow());
		final QtyTU qtyTUsToReserve = QtyTU.ofBigDecimal(qtyTUParam);

		qtyReservationService.makeReservation(
				CreateQtyReservationRequest.builder()
						.orderLineId(getSalesOrderLineId())
						.productId(rowVO.getProductId())
						.warehouseId(rowVO.getWarehouseId())
						.supplyType(rowVO.getSupplyType())
						.datePromised(rowVO.getDatePromised())
						.vendorBPartnerId(rowVO.getVendorBPartnerId())
						.attributesKey(rowVO.getAttributesKey())
						.qtyTU(qtyTUsToReserve)
						.qty(rowVO.computeQtyCUToReserve(qtyTUsToReserve))
						.build()
		);

		invalidateView();

		return MSG_OK;
	}

	private BigDecimal computeDefaultQtyTU()
	{
		if (!isSingleSelectedRow())
		{
			return BigDecimal.ZERO;
		}

		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.ofViewRow(getSingleSelectedRow());
		return rowVO.getQtyTU().toBigDecimal().max(BigDecimal.ZERO);
	}
}
