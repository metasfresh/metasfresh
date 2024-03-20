package de.metas.inoutcandidate.process;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ExplainedOptional;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.shippingnotification.ShippingNotificationFromShipmentScheduleProducer;
import de.metas.order.OrderId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;

import java.time.Instant;
import java.util.Objects;

public class C_Order_Generate_Shipping_Notification_ViewSelection extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String PARAM_PhysicalClearanceDate = "PhysicalClearanceDate";
	@Param(parameterName = PARAM_PhysicalClearanceDate, mandatory = true)
	private Instant p_physicalClearanceDate;

	private static final String SYSCONFIG_SelectionMaxSize = "C_Order_Generate_Shipping_Notification_ViewSelection.SelectionMaxSize";
	private static final int DEFAULT_SelectionMaxSize = 100;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ExplainedOptional<ImmutableSet<OrderId>> optionalSelectedOrderIds = getSelectedOrderIds();
		if (!optionalSelectedOrderIds.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(optionalSelectedOrderIds.getExplanation());
		}

		final ImmutableSet<OrderId> selectedOrderIds = optionalSelectedOrderIds.get();
		if (selectedOrderIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return newProducer().checkCanCreateShippingNotification(selectedOrderIds);
	}

	@Override
	protected String doIt()
	{
		final ImmutableSet<OrderId> selectedOrderIds = getSelectedOrderIds().orElseThrow();
		newProducer().createShippingNotification(selectedOrderIds, p_physicalClearanceDate);
		return MSG_OK;
	}

	private ShippingNotificationFromShipmentScheduleProducer newProducer() {return shipmentScheduleBL.newShippingNotificationProducer();}

	private ExplainedOptional<ImmutableSet<OrderId>> getSelectedOrderIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		final ImmutableSet<OrderId> selectedOrderIds;
		if (selectedRowIds.isEmpty())
		{
			selectedOrderIds = ImmutableSet.of();
		}
		else if (selectedRowIds.isAll())
		{
			final IView view = getView();
			final int selectionMaxSize = getSelectionMaxSize();
			if (view.size() > selectionMaxSize)
			{
				return ExplainedOptional.emptyBecause("Reduce selection to maximum " + selectionMaxSize + " rows");
			}

			selectedOrderIds = view.streamByIds(selectedRowIds, QueryLimit.NO_LIMIT)
					.map(row -> row.getId().toId(OrderId::ofRepoIdOrNull))
					.filter(Objects::nonNull)
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			selectedOrderIds = selectedRowIds.toIds(OrderId::ofRepoId);
		}

		return ExplainedOptional.of(selectedOrderIds);
	}

	private int getSelectionMaxSize()
	{
		return sysConfigBL.getIntValue(SYSCONFIG_SelectionMaxSize, DEFAULT_SelectionMaxSize);
	}

}
