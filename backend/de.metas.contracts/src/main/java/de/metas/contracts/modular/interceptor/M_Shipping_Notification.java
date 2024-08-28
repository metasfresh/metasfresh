/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.interceptor;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContractService;
import de.metas.contracts.modular.computing.DocStatusChangedEvent;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.shippingnotification.ShippingNotificationId;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Cost_Collector;
import org.springframework.stereotype.Component;

import static de.metas.contracts.modular.ModelAction.COMPLETED;
import static de.metas.contracts.modular.ModelAction.REVERSED;

@Component
@Interceptor(I_M_Shipping_Notification.class)
@RequiredArgsConstructor
public class M_Shipping_Notification
{
	@NonNull
	private final ModularContractService contractService;
	@NonNull
	private final ShippingNotificationService shippingNotificationService;

	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(@NonNull final I_M_Shipping_Notification shippingNotification)
	{
		invokeHandlerForEachLine(shippingNotification, COMPLETED);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void afterReverse(@NonNull final I_M_Shipping_Notification shippingNotification)
	{
		invokeHandlerForEachLine(shippingNotification, REVERSED);
	}

	private void invokeHandlerForEachLine(
			@NonNull final I_M_Shipping_Notification record,
			@NonNull final ModelAction modelAction)
	{
		final ShippingNotificationId notificationId = ShippingNotificationId.ofRepoId(record.getM_Shipping_Notification_ID());
		shippingNotificationService.getLines(notificationId)
				.forEach(line -> contractService.scheduleLogCreation(DocStatusChangedEvent.builder()
																			 .tableRecordReference(TableRecordReference.of(line))
																			 .logEntryContractTypes(ImmutableSet.of(LogEntryContractType.MODULAR_CONTRACT))
																			 .modelAction(modelAction)
																			 .userInChargeId(Env.getLoggedUserId())
																			 .build()));
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_M_Shipping_Notification.COLUMNNAME_M_Warehouse_ID)
	public void setShipFrom_Partner_ID(@NonNull final I_M_Shipping_Notification shipingNotificationRecord)
	{
		if (shipingNotificationRecord.getM_Warehouse_ID() > 0)
		{
			final BPartnerId partnerId = warehouseBL.getBPartnerId(WarehouseId.ofRepoId(shipingNotificationRecord.getM_Warehouse_ID()));
			shipingNotificationRecord.setShipFrom_Partner_ID(partnerId.getRepoId());
		}
	}
}
