/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.shippingnotification;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.order.IOrderBL;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.MPeriod;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor
public class ShippingNotificationDocumentHandler implements DocumentHandler
{
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final ShippingNotificationService shippingNotificationService;

	private static I_M_Shipping_Notification extractShippingNotification(@NonNull final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_M_Shipping_Notification.class);
	}

	@Override
	public String getSummary(@NonNull final DocumentTableFields docFields)
	{
		return extractShippingNotification(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(@NonNull final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public int getDoc_User_ID(@NonNull final DocumentTableFields docFields)
	{
		return extractShippingNotification(docFields).getCreatedBy();
	}

	@Override
	public InstantAndOrgId getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		final I_M_Shipping_Notification record = extractShippingNotification(docFields);
		return InstantAndOrgId.ofTimestamp(record.getPhysicalClearanceDate(), OrgId.ofRepoId(record.getAD_Org_ID()));
	}

	private static void assertPeriodOpen(final ShippingNotification shippingNotification)
	{
		MPeriod.testPeriodOpen(Env.getCtx(),
				Timestamp.from(shippingNotification.getDateAcct()),
				shippingNotification.getDocTypeId().getRepoId(),
				shippingNotification.getOrgId().getRepoId());
	}

	private void propagatePhysicalClearanceDateToOrderAndShipmentSchedules(
			@NonNull final ShippingNotification shippingNotification,
			@Nullable final Instant physicalClearanceDate)
	{
		orderBL.setPhysicalClearanceDate(shippingNotification.getSalesOrderId(), physicalClearanceDate);
		shipmentScheduleBL.setPhysicalClearanceDate(shippingNotification.getShipmentScheduleIds(), physicalClearanceDate);
	}

	private <R> R updateWhileSaving(@NonNull final DocumentTableFields docFields, @NonNull final Function<ShippingNotification, R> consumer)
	{
		return shippingNotificationService.updateWhileSaving(extractShippingNotification(docFields), consumer);
	}

	private void updateWhileSaving(@NonNull final DocumentTableFields docFields, @NonNull Consumer<ShippingNotification> consumer)
	{
		shippingNotificationService.updateWhileSaving(extractShippingNotification(docFields), consumer);
	}

	@Override
	public String completeIt(@NonNull final DocumentTableFields docFields)
	{
		return updateWhileSaving(docFields, shippingNotification -> {
					assertPeriodOpen(shippingNotification);

					shippingNotification.completeIt();

					if (!shippingNotification.isReversal())
					{
						propagatePhysicalClearanceDateToOrderAndShipmentSchedules(shippingNotification, shippingNotification.getPhysicalClearanceDate());
					}
					else
					{
						propagatePhysicalClearanceDateToOrderAndShipmentSchedules(shippingNotification, null);
					}

					return shippingNotification.getDocStatus().getCode();
				}
		);
	}

	@Override
	public void reverseCorrectIt(final DocumentTableFields docFields)
	{
		updateWhileSaving(docFields, shippingNotification -> {
					assertPeriodOpen(shippingNotification);

					final ShippingNotification reversal = shippingNotification.createReversal();
					shippingNotificationService.completeIt(reversal);

					shippingNotification.setReversalId(reversal.getId());
					shippingNotification.setDocStatus(DocStatus.Reversed);
				}
		);
	}

	@Override
	public void reactivateIt(@NonNull final DocumentTableFields docFields) {throw new UnsupportedOperationException();}
}
