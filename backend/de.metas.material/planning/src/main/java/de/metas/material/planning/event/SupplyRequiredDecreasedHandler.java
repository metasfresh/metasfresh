/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.material.planning.event;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredDecreasedEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.PO;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;

@Service
@Profile(Profiles.PROFILE_MaterialDispo) // we want only one component to bother itself with SupplyRequiredEvents
@RequiredArgsConstructor
public class SupplyRequiredDecreasedHandler implements MaterialEventHandler<SupplyRequiredDecreasedEvent>
{
	private static final Logger log = LogManager.getLogger(SupplyRequiredDecreasedHandler.class);
	public static final AdMessageKey MSG_SUPPLY_REQUIRED_DECREASED = AdMessageKey.of("de.metas.material.dispo.SupplyRequiredDecreaseFailed");
	public static final String M_SHIPMENT_SCHEDULE_TABLE_NAME = "M_ShipmentSchedule"; //because I_M_ShipmentSchedule is not visible in this package
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.invoicecandidate.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();

	private final INotificationBL notificationBL = Services.get(INotificationBL.class);

	@NonNull private final List<SupplyRequiredAdvisor> supplyRequiredAdvisors;
	@NonNull private final SupplyRequiredHandlerHelper helper;

	@Override
	public Collection<Class<? extends SupplyRequiredDecreasedEvent>> getHandledEventType()
	{
		return ImmutableList.of(SupplyRequiredDecreasedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final SupplyRequiredDecreasedEvent event)
	{
		final SupplyRequiredDescriptor descriptor = event.getSupplyRequiredDescriptor();
		final MaterialPlanningContext context = helper.createContextOrNull(descriptor);
		final MaterialDescriptor materialDescriptor = descriptor.getMaterialDescriptor();
		Quantity remainingQtyToHandle = Quantitys.of(materialDescriptor.getQuantity(), ProductId.ofRepoId(materialDescriptor.getProductId()));
		Loggables.withLogger(log, Level.DEBUG).addLog("Trying to decrease supply of  {} by {}.", descriptor.getOrderId(), remainingQtyToHandle);
		if (context != null)
		{
			for (final SupplyRequiredAdvisor advisor : supplyRequiredAdvisors)
			{
				if (remainingQtyToHandle.signum() > 0)
				{
					remainingQtyToHandle = advisor.handleQuantityDecrease(event, remainingQtyToHandle);
				}
			}
		}

		if (remainingQtyToHandle.signum() > 0)
		{
			Loggables.withLogger(log, Level.WARN).addLog("Could not decrease the qty for event {}. Qty left: {}", event, remainingQtyToHandle);
			sendNotification(descriptor, remainingQtyToHandle);
		}
	}

	private void sendNotification(final SupplyRequiredDescriptor descriptor, final Quantity unhandledQty)
	{
		final TableRecordReference recordReference = getTableRecordReferenceFromEventDescriptor(descriptor);
		if (recordReference == null)
		{
			return;
		}
		final PO po = InterfaceWrapperHelper.getPO(recordReference.getModel(PlainContextAware.newWithThreadInheritedTrx(), Object.class));
		notificationBL.sendAfterCommit(UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC)
				.targetAction(UserNotificationRequest.TargetRecordAction.ofNullable(recordReference))
				.recipientUserId(UserId.ofRepoId(po.getUpdatedBy()))
				.contentADMessage(MSG_SUPPLY_REQUIRED_DECREASED)
						.contentADMessageParam(unhandledQty.toString())
				.build());
	}

	@Nullable
	private TableRecordReference getTableRecordReferenceFromEventDescriptor(final SupplyRequiredDescriptor descriptor)
	{
		final TableRecordReference recordReference = getOriginatingRecordRef(descriptor);
		if (recordReference == null)
		{
			Loggables.withLogger(log, Level.ERROR).addLog("Could not trigger notification for event descriptor {}. Could not identify a DB record to reference.", descriptor);
			return null;
		}
		return recordReference;
	}

	private @Nullable TableRecordReference getOriginatingRecordRef(final SupplyRequiredDescriptor descriptor)
	{
		return coalesceSuppliers(
				() -> TableRecordReference.ofNullable(I_C_Order.Table_Name, descriptor.getOrderId()),
				() -> TableRecordReference.ofNullable(I_C_OrderLine.Table_Name, descriptor.getOrderLineId()),
				() -> TableRecordReference.ofNullable(M_SHIPMENT_SCHEDULE_TABLE_NAME, descriptor.getShipmentScheduleId()),
				() -> TableRecordReference.ofNullable(I_M_Forecast.Table_Name, descriptor.getForecastId())
		);
	}

}