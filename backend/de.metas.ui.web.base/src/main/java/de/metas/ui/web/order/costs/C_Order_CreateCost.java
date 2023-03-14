package de.metas.ui.web.order.costs;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.costs.OrderCostCreateRequest;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.OrderCostType;
import de.metas.order.costs.OrderCostTypeId;
import de.metas.order.costs.calculation_methods.CostCalculationMethod;
import de.metas.order.costs.calculation_methods.CostCalculationMethodParams;
import de.metas.order.costs.calculation_methods.FixedAmountCostCalculationMethodParams;
import de.metas.order.costs.calculation_methods.PercentageCostCalculationMethodParams;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Cost_Type;
import org.compiere.model.I_C_Order;

import java.math.BigDecimal;
import java.util.HashMap;

public class C_Order_CreateCost extends JavaProcess
		implements IProcessPrecondition, IProcessParametersCallout
{
	private final OrderCostService orderCostService = SpringContextHolder.instance.getBean(OrderCostService.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private static final String PARAM_C_BPartner_ID = "C_BPartner_ID";
	@Param(parameterName = PARAM_C_BPartner_ID)
	private BPartnerId p_bpartnerId;

	public static final String PARAM_CostTypeId = I_C_Cost_Type.COLUMNNAME_C_Cost_Type_ID;
	@Param(parameterName = PARAM_CostTypeId)
	private OrderCostTypeId p_costTypeId;

	@Param(parameterName = I_C_Cost_Type.COLUMNNAME_CostCalculationMethod)
	private CostCalculationMethod p_CostCalculationMethod;

	public static final String PARAM_Amount = "Amount";
	@Param(parameterName = PARAM_Amount)
	private BigDecimal p_amountBD;

	public static final String PARAM_Percentage = "Percentage";
	@Param(parameterName = PARAM_Percentage)
	private BigDecimal p_percentageBD;

	@Param(parameterName = "IsAllowInvoicing")
	private boolean p_IsAllowInvoicing;
	@Param(parameterName = "IsInvoiced")
	private boolean p_IsInvoiced;

	@Param(parameterName = "M_Product_ID")
	private ProductId p_InvoiceableProductId;

	private final HashMap<OrderId, I_C_Order> orderCache = new HashMap<>();

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}
		if (context.getSelectedIncludedRecords().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		final I_C_Order order = getOrder(OrderId.ofRepoId(context.getSingleSelectedRecordId()));
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(order.getDocStatus());
		if (!docStatus.isDraftedOrInProgress())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Order shall in Drafted");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_CostTypeId.equals(parameterName))
		{
			final OrderCostType costType = p_costTypeId != null ? orderCostService.getCostTypeById(p_costTypeId) : null;
			p_CostCalculationMethod = costType != null ? costType.getCalculationMethod() : null;
			p_IsAllowInvoicing = isSOTrx() && costType != null && costType.isAllowInvoicing();
			p_InvoiceableProductId = costType != null ? costType.getInvoiceableProductId() : null;
		}
	}

	@Override
	protected String doIt()
	{
		orderCostService.createOrderCost(orderCostCreateRequest());
		return MSG_OK;
	}

	private OrderCostCreateRequest orderCostCreateRequest()
	{
		final OrderCostCreateRequest.OrderLine orderLine;
		if (p_IsInvoiced)
		{
			if (p_InvoiceableProductId == null)
			{
				throw new FillMandatoryException("M_Product_ID");
			}

			orderLine = OrderCostCreateRequest.OrderLine.builder()
					.productId(p_InvoiceableProductId)
					.bpartnerId2(p_bpartnerId)
					.build();
		}
		else
		{
			orderLine = null;
		}

		return OrderCostCreateRequest.builder()
				.bpartnerId(p_bpartnerId)
				.costTypeId(p_costTypeId)
				.orderAndLineIds(getSelectedOrderAndLineIds())
				.costCalculationMethodParams(getCostCalculationMethodParams())
				.addOrderLine(orderLine)
				.build();
	}

	private CostCalculationMethodParams getCostCalculationMethodParams()
	{
		if (CostCalculationMethod.FixedAmount.equals(p_CostCalculationMethod))
		{
			if (p_amountBD == null || p_amountBD.signum() <= 0)
			{
				throw new FillMandatoryException(PARAM_Amount);
			}
			return FixedAmountCostCalculationMethodParams.builder()
					.fixedAmount(Money.of(p_amountBD, getOrderCurrencyId()))
					.build();
		}
		else if (CostCalculationMethod.PercentageOfAmount.equals(p_CostCalculationMethod))
		{
			if (p_percentageBD == null || p_percentageBD.signum() <= 0)
			{
				throw new FillMandatoryException(PARAM_Percentage);
			}
			return PercentageCostCalculationMethodParams.builder()
					.percentage(Percent.of(p_percentageBD))
					.build();
		}
		else
		{
			throw new AdempiereException("Method not handled: " + p_CostCalculationMethod);
		}

	}

	private ImmutableSet<OrderAndLineId> getSelectedOrderAndLineIds()
	{
		final OrderId orderId = getOrderId();
		return getSelectedIncludedRecordIds(I_C_OrderLine.class)
				.stream()
				.map(orderLineRepoId -> OrderAndLineId.ofRepoIds(orderId, orderLineRepoId))
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private OrderId getOrderId()
	{
		return OrderId.ofRepoId(getRecord_ID());
	}

	private I_C_Order getOrder(final OrderId orderId)
	{
		return orderCache.computeIfAbsent(orderId, orderBL::getById);
	}

	private CurrencyId getOrderCurrencyId()
	{
		return CurrencyId.ofRepoId(getOrder(getOrderId()).getC_Currency_ID());
	}

	public boolean isSOTrx()
	{
		return getOrder(getOrderId()).isSOTrx();
	}
}
