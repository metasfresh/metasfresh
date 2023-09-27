/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.shippingNotification;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.shipmentschedule.M_ShipmentSchedule_StepDefData;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.shippingnotification.model.I_M_Shipping_NotificationLine;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.shippingnotification.model.I_M_Shipping_NotificationLine.COLUMNNAME_M_Product_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_NotificationLine.COLUMNNAME_M_ShipmentSchedule_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_NotificationLine_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_NotificationLine.COLUMNNAME_M_Shipping_Notification_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_NotificationLine.COLUMNNAME_MovementQty;

public class M_Shipping_NotificationLine_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_Shipping_Notification_StepDefData shippingNotificationTable;
	private final M_Shipping_NotificationLine_StepDefData shippingNotificationLineTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;
	private final M_Product_StepDefData productTable;

	public M_Shipping_NotificationLine_StepDef(
			@NonNull final M_Shipping_Notification_StepDefData shippingNotificationTable,
			@NonNull final M_Shipping_NotificationLine_StepDefData shippingNotificationLineTable,
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.shippingNotificationTable = shippingNotificationTable;
		this.shippingNotificationLineTable = shippingNotificationLineTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
		this.productTable = productTable;
	}

	@And("validate M_Shipping_NotificationLines:")
	public void validate_M_Shipping_NotificationLine(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_M_Shipping_Notification shippingNotification = getShippingNotification(row);
			final I_M_ShipmentSchedule shipmentSchedule = getShipmentSchedule(row);
			final I_M_Shipping_NotificationLine shippingNotificationLine = getShippingNotificationLine(shippingNotification, shipmentSchedule);
			final I_M_Product product = getProduct(row);
			final BigDecimal movementQty = DataTableUtil.extractBigDecimalForColumnName(row, COLUMNNAME_MovementQty);
			final String shippingNotificationLineIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Shipping_NotificationLine_ID + "." + TABLECOLUMN_IDENTIFIER);

			final SoftAssertions softly = new SoftAssertions();
			softly.assertThat(product.getM_Product_ID()).isEqualTo(shippingNotificationLine.getM_Product_ID());
			softly.assertThat(movementQty).isEqualTo(shippingNotificationLine.getMovementQty());
			softly.assertAll();

			shippingNotificationLineTable.putOrReplace(shippingNotificationLineIdentifier, shippingNotificationLine);
		}
	}

	@NonNull
	private I_M_Shipping_Notification getShippingNotification(final Map<String, String> row)
	{
		final String shippingNotificationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Shipping_Notification_ID + "." + TABLECOLUMN_IDENTIFIER);
		return shippingNotificationTable.get(shippingNotificationIdentifier);
	}

	@NonNull
	private I_M_ShipmentSchedule getShipmentSchedule(final Map<String, String> row)
	{
		final String shipmentScheduleIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
		return shipmentScheduleTable.get(shipmentScheduleIdentifier);
	}

	@NonNull
	private I_M_Shipping_NotificationLine getShippingNotificationLine(
			final I_M_Shipping_Notification shippingNotification,
			final I_M_ShipmentSchedule shipmentSchedule)
	{
		return queryBL.createQueryBuilder(I_M_Shipping_NotificationLine.class)
				.addEqualsFilter(COLUMNNAME_M_Shipping_Notification_ID, shippingNotification.getM_Shipping_Notification_ID())
				.addEqualsFilter(COLUMNNAME_M_ShipmentSchedule_ID, shipmentSchedule.getM_ShipmentSchedule_ID())
				.create()
				.firstOnlyNotNull(I_M_Shipping_NotificationLine.class);
	}

	@NonNull
	private I_M_Product getProduct(final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Product_ID + "." + TABLECOLUMN_IDENTIFIER);
		return productTable.get(productIdentifier);
	}

}
