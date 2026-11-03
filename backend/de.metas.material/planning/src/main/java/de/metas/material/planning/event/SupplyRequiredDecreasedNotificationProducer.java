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
import de.metas.document.engine.DocStatus;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.pporder.PPOrderRepository;
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
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
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
	@NonNull private final PPOrderRepository ppOrderRepository;

	public static SupplyRequiredDecreasedNotificationProducer newInstance(@NonNull final PPOrderRepository ppOrderRepository)
	{
		return new SupplyRequiredDecreasedNotificationProducer(ppOrderRepository);
	}

	private SupplyRequiredDecreasedNotificationProducer(@NonNull final PPOrderRepository ppOrderRepository)
	{
		this.ppOrderRepository = ppOrderRepository;
	}

	public void sendNotification(@Nullable final MaterialPlanningContext context, @NonNull final SupplyRequiredDescriptor descriptor, @NonNull final Quantity unhandledQty)
	{
		// a PP_Order BOM-line demand (also when made for a sales order): point to the PP_Order, which is what changed
		final PPOrderId ppOrderId = getPPOrderIdOrNull(descriptor);
		final I_PP_Order ppOrder = ppOrderId == null ? null : getNotifiablePPOrderOrNull(ppOrderId);
		if (ppOrderId != null && ppOrder == null)
		{
			return; // PP_Order deleted/voided/reversed meanwhile: nothing left to reduce, no notification
		}

		final TableRecordReference recordReference = ppOrderId != null
				? TableRecordReference.of(I_PP_Order.Table_Name, ppOrderId)
				: getOrderRecordReference(descriptor);
		if (recordReference == null)
		{
			return;
		}

		final UserNotificationRequest.UserNotificationRequestBuilder notificationBuilder = UserNotificationRequest.builder()
				.topic(EVENTBUS_TOPIC)
				.targetAction(UserNotificationRequest.TargetRecordAction.ofNullable(recordReference))
				.contentADMessage(MSG_SUPPLY_REQUIRED_DECREASED)
				.contentADMessageParam(unhandledQty.toString());

		getRecipients(context, descriptor, ppOrder).forEach(userId -> {
			notificationBuilder.recipientUserId(userId);
			notificationBL.sendAfterCommit(notificationBuilder
					.build());
		});
	}

	@Nullable
	private static PPOrderId getPPOrderIdOrNull(@NonNull final SupplyRequiredDescriptor descriptor)
	{
		final PPOrderRef ppOrderRef = descriptor.getPpOrderRef();
		return ppOrderRef == null ? null : ppOrderRef.getPpOrderId();
	}

	/**
	 * @return the PP_Order, or {@code null} if it no longer exists (the handler runs async, so it may have been deleted)
	 * or is voided/reversed.
	 */
	@Nullable
	private I_PP_Order getNotifiablePPOrderOrNull(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderRepository.getByIdOrNull(ppOrderId);
		return ppOrder == null || DocStatus.ofCode(ppOrder.getDocStatus()).isReversedOrVoided() ? null : ppOrder;
	}

	@Nullable
	private TableRecordReference getOrderRecordReference(final SupplyRequiredDescriptor descriptor)
	{
		final TableRecordReference recordReference = TableRecordReference.ofNullable(I_C_Order.Table_Name, descriptor.getOrderId());
		if (recordReference == null)
		{
			Loggables.withLogger(log, Level.ERROR).addLog("Could not trigger notification for event descriptor {}. Could not identify a DB record to reference.", descriptor);
			return null;
		}
		return recordReference;
	}

	private Set<UserId> getRecipients(@Nullable final MaterialPlanningContext context, @NonNull final SupplyRequiredDescriptor descriptor, @Nullable final I_PP_Order ppOrder)
	{
		final Set<UserId> userIds = new HashSet<>();
		addIfRegularUser(userIds, context == null ? null : context.getProductPlanning().getPlannerId());
		if (ppOrder != null)
		{
			// the sales order (if any) did not change, so its sales rep is not notified
			addIfRegularUser(userIds, UserId.ofRepoIdOrNull(ppOrder.getPlanner_ID()));
			addIfRegularUser(userIds, UserId.ofRepoIdOrNull(ppOrder.getUpdatedBy()));
		}
		else if (descriptor.getOrderId() > 0)
		{
			final OrderId orderId = OrderId.ofRepoId(descriptor.getOrderId());
			final I_C_Order order = orderBL.getById(orderId);
			addIfRegularUser(userIds, UserId.ofRepoIdOrNull(order.getSalesRep_ID()));
			addIfRegularUser(userIds, UserId.ofRepoIdOrNull(InterfaceWrapperHelper.getPO(order).getUpdatedBy()));
		}

		//deadletter recipient
		if (userIds.isEmpty())
		{
			addIfRegularUser(userIds, orgDAO.getOrgInfoById(descriptor.getOrgId()).getSupervisorId());
		}

		return userIds;
	}

	private static void addIfRegularUser(@NonNull final Set<UserId> userIds, @Nullable final UserId userId)
	{
		if (userId != null && userId.isRegularUser())
		{
			userIds.add(userId);
		}
	}
}
