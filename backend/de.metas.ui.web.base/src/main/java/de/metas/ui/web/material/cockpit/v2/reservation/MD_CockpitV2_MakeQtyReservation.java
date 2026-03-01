package de.metas.ui.web.material.cockpit.v2.reservation;

import com.google.common.collect.ImmutableMap;
import de.metas.material.cockpit.model.I_QtyDemand_QtySupply_V;
import de.metas.order.OrderLineId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.ui.web.order.sales.hu.reservation.process.WEBUI_C_OrderLineSO_Launch_HUEditor;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

public class MD_CockpitV2_MakeQtyReservation
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@Autowired
	private QtyReservationService qtyReservationService;

	private static final String PARAMNAME_QTY_TU = "QtyTU";
	@Param(mandatory = true, parameterName = PARAMNAME_QTY_TU)
	private BigDecimal qtyTU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final OrderLineId orderLineId = getOrderLineIdOrNull();
		if (orderLineId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No sales order line context");
		}

		if (!isSingleSelectedRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Exactly one row must be selected");
		}

		final IViewRow row = getSingleSelectedRow();
		final BigDecimal availableQtyTU = row.getFieldValueAsBigDecimal(COLUMNNAME_QtyTU, BigDecimal.ZERO);
		if (availableQtyTU.signum() <= 0)
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
		final OrderLineId orderLineId = getOrderLineId();
		final IViewRow row = getSingleSelectedRow();

		final ProductId productId = row.getFieldValueAsRepoId(
				I_QtyDemand_QtySupply_V.COLUMNNAME_M_Product_ID, ProductId::ofRepoId);
		final WarehouseId warehouseId = row.getFieldValueAsRepoId(
				I_QtyDemand_QtySupply_V.COLUMNNAME_M_Warehouse_ID, WarehouseId::ofRepoId);
		final UomId uomId = row.getFieldValueAsRepoId(
				I_QtyDemand_QtySupply_V.COLUMNNAME_C_UOM_ID, UomId::ofRepoId);

		final String supplyType = getSupplyType(row);
		final Timestamp datePromised = getDatePromised(row);
		final Integer vendorBPartnerId = getVendorBPartnerId(row);
		final String attributesKey = getAttributesKey(row);

		final BigDecimal cuQty = computeCuQty(row, qtyTU);

		final CreateQtyReservationRequest request = CreateQtyReservationRequest.builder()
				.orderLineId(orderLineId)
				.productId(productId)
				.warehouseId(warehouseId)
				.uomId(uomId)
				.supplyType(supplyType)
				.datePromised(datePromised)
				.vendorBPartnerId(vendorBPartnerId)
				.attributesKey(attributesKey)
				.qtyTU(qtyTU)
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

		final IViewRow row = getSingleSelectedRow();
		final BigDecimal availableQtyTU = row.getFieldValueAsBigDecimal(COLUMNNAME_QtyTU, BigDecimal.ZERO);

		// Default to all available TU on the selected row
		return availableQtyTU.max(BigDecimal.ZERO);
	}

	private BigDecimal computeCuQty(@NonNull final IViewRow row, @NonNull final BigDecimal reserveQtyTU)
	{
		final BigDecimal rowQtyTU = row.getFieldValueAsBigDecimal(COLUMNNAME_QtyTU, BigDecimal.ZERO);
		final BigDecimal rowQtyStock = row.getFieldValueAsBigDecimal(
				I_QtyDemand_QtySupply_V.COLUMNNAME_QtyStock, BigDecimal.ZERO);

		if (rowQtyTU.signum() <= 0 || rowQtyStock.signum() <= 0)
		{
			return BigDecimal.ZERO;
		}

		// Proportional: CU = reserveQtyTU * (rowQtyStock / rowQtyTU)
		return reserveQtyTU.multiply(rowQtyStock)
				.divide(rowQtyTU, 4, RoundingMode.HALF_UP);
	}

	@NonNull
	private OrderLineId getOrderLineId()
	{
		final OrderLineId orderLineId = getOrderLineIdOrNull();
		if (orderLineId == null)
		{
			throw new AdempiereException("No sales order line context");
		}
		return orderLineId;
	}

	@Nullable
	private OrderLineId getOrderLineIdOrNull()
	{
		final ImmutableMap<String, Object> params = getView().getParameters();
		final Object value = params.get(WEBUI_C_OrderLineSO_Launch_HUEditor.VIEW_PARAM_PARENT_SALES_ORDER_LINE_ID);
		if (value instanceof OrderLineId)
		{
			return (OrderLineId)value;
		}
		return null;
	}

	@NonNull
	private static String getSupplyType(@NonNull final IViewRow row)
	{
		final Object value = row.getFieldNameAndJsonValues().get(COLUMNNAME_SupplyType);
		if (value instanceof String)
		{
			return (String)value;
		}
		return CreateQtyReservationRequest.SUPPLY_TYPE_ON_HAND;
	}

	@Nullable
	private static Timestamp getDatePromised(@NonNull final IViewRow row)
	{
		final Object value = row.getFieldNameAndJsonValues().get(COLUMNNAME_DatePromised);
		if (value instanceof Timestamp)
		{
			return (Timestamp)value;
		}
		return null;
	}

	@Nullable
	private static Integer getVendorBPartnerId(@NonNull final IViewRow row)
	{
		final int id = row.getFieldValueAsInt(COLUMNNAME_C_BPartner_Vendor_ID, -1);
		return id > 0 ? id : null;
	}

	@Nullable
	private static String getAttributesKey(@NonNull final IViewRow row)
	{
		final Object value = row.getFieldNameAndJsonValues().get(
				I_QtyDemand_QtySupply_V.COLUMNNAME_AttributesKey);
		return value instanceof String ? (String)value : null;
	}

	// Column names from the SE203 view (not in I_QtyDemand_QtySupply_V)
	private static final String COLUMNNAME_QtyTU = "QtyTU";
	private static final String COLUMNNAME_SupplyType = "SupplyType";
	private static final String COLUMNNAME_DatePromised = "DatePromised";
	private static final String COLUMNNAME_C_BPartner_Vendor_ID = "C_BPartner_Vendor_ID";
}
