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
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.notification.INotificationBL;
import de.metas.notification.UserNotificationRequest;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class SupplyRequiredDecreasedNotificationProducer
{
	private static final Logger log = LogManager.getLogger(SupplyRequiredDecreasedNotificationProducer.class);
	public static final AdMessageKey MSG_SUPPLY_REQUIRED_DECREASED = AdMessageKey.of("de.metas.material.dispo.SupplyRequiredDecreaseFailed");
	public static final Topic EVENTBUS_TOPIC = Topic.builder()
			.name("de.metas.material.dispo.DecreasedEvent.UserNotifications")
			.type(Type.DISTRIBUTED)
			.build();
	@NonNull private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);

	public static SupplyRequiredDecreasedNotificationProducer newInstance()
	{
		return new SupplyRequiredDecreasedNotificationProducer();
	}

	private SupplyRequiredDecreasedNotificationProducer()
	{
	}

	public void sendNotification(@Nullable final MaterialPlanningContext context, @NonNull final SupplyRequiredDescriptor descriptor, @NonNull final Quantity unhandledQty)
	{
		final TableRecordReference recordReference = getTableRecordReferenceFromEventDescriptor(descriptor);
		if (recordReference == null)
		{
			return;
		}

		final UserNotificationRequest.UserNotificationRequestBuilder notificationBuilder = UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC)
				.targetAction(UserNotificationRequest.TargetRecordAction.ofNullable(recordReference))
				.contentADMessage(MSG_SUPPLY_REQUIRED_DECREASED)
				.contentADMessageParam(unhandledQty.toString());

		getRecipients(context, descriptor).forEach(userId -> {
			notificationBuilder.recipientUserId(userId);
			notificationBL.sendAfterCommit(notificationBuilder
					.build());
		});
	}

	@Nullable
	private TableRecordReference getTableRecordReferenceFromEventDescriptor(final SupplyRequiredDescriptor descriptor)
	{
		final TableRecordReference recordReference = TableRecordReference.ofNullable(I_C_Order.Table_Name, descriptor.getOrderId());
		if (recordReference == null)
		{
			Loggables.withLogger(log, Level.ERROR).addLog("Could not trigger notification for event descriptor {}. Could not identify a DB record to reference.", descriptor);
			return null;
		}
		return recordReference;
	}

	private Set<UserId> getRecipients(@Nullable final MaterialPlanningContext context, @NonNull final SupplyRequiredDescriptor descriptor)
	{
		final Set<UserId> userIds = new HashSet<>();
		final UserId plannerId = context == null ? null : context.getProductPlanning().getPlannerId();
		if (plannerId != null && plannerId.isRegularUser())
		{
			userIds.add(plannerId);
		}
		final OrderId orderId = OrderId.ofRepoIdOrNull(descriptor.getOrderId());
		if (orderId != null)
		{
			final I_C_Order order = orderBL.getById(orderId);
			final UserId salesRepUserId = UserId.ofRepoIdOrNull(order.getSalesRep_ID());
			if (salesRepUserId != null && salesRepUserId.isRegularUser())
			{
				userIds.add(salesRepUserId);
			}

			final UserId updatedByUserId = UserId.ofRepoIdOrNull(InterfaceWrapperHelper.getPO(order).getUpdatedBy());
			if (updatedByUserId != null && updatedByUserId.isRegularUser())
			{
				userIds.add(updatedByUserId);
			}
		}

		//deadletter recipient
		if (userIds.isEmpty())
		{
			final UserId supervisorId = orgDAO.getOrgInfoById(descriptor.getOrgId()).getSupervisorId();
			if (supervisorId != null && supervisorId.isRegularUser())
			{
				userIds.add(supervisorId);
			}
		}

		return userIds;
	}
}
