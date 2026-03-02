package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.QtyTU;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.view.IViewRow;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MD_CockpitV2_MakeQtyReservation
		extends MaterialCockpitV2BasedProcess
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired
	private QtyReservationService qtyReservationService;

	private static final String PARAMNAME_QTY_TU = "QtyTU";
	@Param(mandatory = true, parameterName = PARAMNAME_QTY_TU)
	private BigDecimal qtyTUParam;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final OrderLineId orderLineId = getSalesOrderLineIdOrNull();
		if (orderLineId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No sales order line context");
		}

		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Exactly one row must be selected");
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
		if (PARAMNAME_QTY_TU.equals(parameter.getColumnName()))
		{
			return computeDefaultQtyTU();
		}
		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt()
	{
		final OrderLineId orderLineId = getSalesOrderLineId();
		final IViewRow row = getSingleSelectedRow();
		final MaterialCockpitV2RowVO rowVO = MaterialCockpitV2RowVO.ofViewRow(row);

		final QtyTU reserveQtyTU = QtyTU.ofBigDecimal(qtyTUParam);
		final BigDecimal cuQty = computeCuQty(rowVO, reserveQtyTU);

		final CreateQtyReservationRequest request = CreateQtyReservationRequest.builder()
				.orderLineId(orderLineId)
				.productId(rowVO.getProductId())
				.warehouseId(rowVO.getWarehouseId())
				.uomId(rowVO.getUomId())
				.supplyType(rowVO.getSupplyType())
				.datePromised(rowVO.getDatePromised())
				.vendorBPartnerId(rowVO.getVendorBPartnerId())
				.attributesKey(rowVO.getAttributesKey())
				.qtyTU(reserveQtyTU)
				.qty(cuQty)
				.build();

		qtyReservationService.makeReservation(request);

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

	private static BigDecimal computeCuQty(@NonNull final MaterialCockpitV2RowVO rowVO, @NonNull final QtyTU reserveQtyTU)
	{
		final BigDecimal rowQtyTU = rowVO.getQtyTU().toBigDecimal();
		final BigDecimal rowQtyStock = rowVO.getQtyStock();

		if (rowQtyTU.signum() <= 0 || rowQtyStock.signum() <= 0)
		{
			return BigDecimal.ZERO;
		}

		// Proportional: CU = reserveQtyTU * (rowQtyStock / rowQtyTU)
		return reserveQtyTU.toBigDecimal().multiply(rowQtyStock)
				.divide(rowQtyTU, 4, RoundingMode.HALF_UP);
	}
}
