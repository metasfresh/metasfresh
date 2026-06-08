package de.metas.ui.web.material.cockpit.v2.reservation;

import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.inoutcandidate.qty_reservation.MakeQtyReservationCommand;
import de.metas.inoutcandidate.qty_reservation.MaterialCockpitV2RowVO;
import de.metas.inoutcandidate.qty_reservation.QtyReservationService;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Order;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
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
	@NonNull private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
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

		final OrderAndLineId salesOrderAndLineId = getSalesOrderAndLineId();

		MakeQtyReservationCommand.builder()
				.orderLineBL(orderLineBL)
				.qtyReservationService(qtyReservationService)
				.projectRepository(projectRepository)
				.rowVO(rowVO)
				.salesOrderAndLineId(salesOrderAndLineId)
				.qtyToReserveTU(qtyToReserveTU)
				.capacityPerTUFallback(computeCapacityPerTUFromLUTU(salesOrderAndLineId, rowVO.getProductId()))
				.build()
				.execute();

		return MSG_OK;
	}

	/**
	 * Resolves the packing capacity per TU (in the product's stock UOM) from the sales order line's
	 * {@code M_HU_LUTU_Configuration}, used as a fallback by {@link MakeQtyReservationCommand} when
	 * the order line carries no explicit {@code QtyItemCapacity}. The HU LU/TU machinery lives in
	 * {@code de.metas.handlingunits.base}, which is reachable from this WebUI module but not from
	 * {@code de.metas.swat.base} where the command lives.
	 */
	private Quantity computeCapacityPerTUFromLUTU(
			@NonNull final OrderAndLineId salesOrderAndLineId,
			@NonNull final ProductId productId)
	{
		final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);
		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		final UomId stockUomId = Services.get(IProductBL.class).getStockUOMId(productId);

		final I_C_Order orderRecord = orderBL.getById(salesOrderAndLineId.getOrderId());
		final de.metas.handlingunits.model.I_C_OrderLine orderLineRecord = InterfaceWrapperHelper.load(
				salesOrderAndLineId.getOrderLineRepoId(),
				de.metas.handlingunits.model.I_C_OrderLine.class);

		final I_M_HU_PI_Item_Product tuPIItemProduct = hupiItemProductBL.extractHUPIItemProduct(orderRecord, orderLineRecord);

		final I_M_HU_LUTU_Configuration lutuConfigurationInStockUOM = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				productId,
				stockUomId,
				null/* bpartnerId */,
				false/* noLUForVirtualTU */);

		// Pass a zero stock qty so the result reflects the packing instruction's CU-per-TU (the
		// order-line-QtyItemCapacity branch is handled inside the command).
		final Quantity zeroStockQty = Quantitys.of(BigDecimal.ZERO, stockUomId);
		return IHUPIItemProductBL.getQtyCUsPerTUInStockUOM(orderLineRecord, zeroStockQty, lutuConfigurationInStockUOM);
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
