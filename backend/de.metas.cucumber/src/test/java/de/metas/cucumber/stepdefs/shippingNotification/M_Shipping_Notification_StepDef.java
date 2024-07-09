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

import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.C_Order_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.ItemProvider;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.StepDefDocAction;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.auction.C_Auction_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Calendar_StepDefData;
import de.metas.cucumber.stepdefs.calendar.C_Year_StepDefData;
import de.metas.cucumber.stepdefs.org.AD_Org_StepDefData;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.shippingnotification.ShippingNotificationFromShipmentScheduleProducer;
import de.metas.order.OrderId;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Auction;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_Locator;

import java.time.Instant;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_AD_Org_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_AD_User_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_C_Auction_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_C_BPartner_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_C_BPartner_Location_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_C_Harvesting_Calendar_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_C_Order_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_Description;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_DocStatus;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_Harvesting_Year_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_M_Locator_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_M_Shipping_Notification_ID;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_POReference;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_PhysicalClearanceDate;
import static de.metas.shippingnotification.model.I_M_Shipping_Notification.COLUMNNAME_Reversal_ID;

public class M_Shipping_Notification_StepDef
{
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final M_Shipping_Notification_StepDefData shippingNotificationTable;
	private final C_Order_StepDefData orderTable;
	private final C_BPartner_StepDefData bPartnerTable;
	private final C_BPartner_Location_StepDefData bPartnerLocationTable;
	private final AD_User_StepDefData userTable;
	private final M_Locator_StepDefData locatorTable;
	private final C_Calendar_StepDefData calendarTable;
	private final C_Year_StepDefData yearTable;
	private final C_Auction_StepDefData auctionTable;
	private final AD_Org_StepDefData orgTable;

	public M_Shipping_Notification_StepDef(
			@NonNull final M_Shipping_Notification_StepDefData shippingNotificationTable,
			@NonNull final C_Order_StepDefData orderTable,
			@NonNull final C_BPartner_StepDefData bPartnerTable,
			@NonNull final C_BPartner_Location_StepDefData bPartnerLocationTable,
			@NonNull final AD_User_StepDefData userTable,
			@NonNull final M_Locator_StepDefData locatorTable,
			@NonNull final C_Calendar_StepDefData calendarTable,
			@NonNull final C_Year_StepDefData yearTable,
			@NonNull final C_Auction_StepDefData auctionTable,
			@NonNull final AD_Org_StepDefData orgTable)
	{
		this.shippingNotificationTable = shippingNotificationTable;
		this.orderTable = orderTable;
		this.bPartnerTable = bPartnerTable;
		this.bPartnerLocationTable = bPartnerLocationTable;
		this.userTable = userTable;
		this.locatorTable = locatorTable;
		this.calendarTable = calendarTable;
		this.yearTable = yearTable;
		this.auctionTable = auctionTable;
		this.orgTable = orgTable;
	}

	@And("generate M_Shipping_Notification:")
	public void generate_M_Shipping_Notification(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String salesOrderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final Instant physicalClearanceDate = DataTableUtil.extractLocalDateForColumnName(row, COLUMNNAME_PhysicalClearanceDate)
					.atStartOfDay(SystemTime.zoneId())
					.toInstant();

			final I_C_Order salesOrder = orderTable.get(salesOrderIdentifier);
			final OrderId salesOrderId = OrderId.ofRepoId(salesOrder.getC_Order_ID());

			final ShippingNotificationFromShipmentScheduleProducer shippingNotificationProducer = shipmentScheduleBL.newShippingNotificationProducer();
			shippingNotificationProducer.checkCanCreateShippingNotification(salesOrderId).throwExceptionIfRejected();
			shippingNotificationProducer.createShippingNotification(salesOrderId, physicalClearanceDate);
		}
	}

	@And("^after not more than (.*)s, M_Shipping_Notifications are found$")
	public void find_M_Shipping_Notification(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String orderIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_C_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Order order = orderTable.get(orderIdentifier);

			final I_M_Shipping_Notification shippingNotification = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> load_ShippingNotification(row, order));

			// either invoice bpartner or dropship bpartner (dropship takes precedence over invoice)

			final String bPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bPartnerIdentifier))
			{
				final I_C_BPartner bPartner = bPartnerTable.get(bPartnerIdentifier);
				softly.assertThat(bPartner.getC_BPartner_ID()).isEqualTo(shippingNotification.getC_BPartner_ID()).isEqualTo(order.getC_BPartner_ID());
			}

			final String dropShipBPartnerIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_DropShip_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropShipBPartnerIdentifier))
			{
				final I_C_BPartner dropShipBPartner = bPartnerTable.get(dropShipBPartnerIdentifier);
				softly.assertThat(dropShipBPartner.getC_BPartner_ID()).isEqualTo(shippingNotification.getC_BPartner_ID()).isEqualTo(order.getDropShip_BPartner_ID());
			}

			// either invoice bpartner location or dropship bpartner location (dropship takes precedence over invoice)

			final String bPartnerLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_C_BPartner_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bPartnerLocationIdentifier))
			{
				final I_C_BPartner_Location bPartnerLocation = bPartnerLocationTable.get(bPartnerLocationIdentifier);
				softly.assertThat(bPartnerLocation.getC_BPartner_Location_ID()).isEqualTo(shippingNotification.getC_BPartner_Location_ID()).isEqualTo(order.getC_BPartner_Location_ID());
			}

			final String dropShipBPartnerLocationIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, I_C_Order.COLUMNNAME_DropShip_Location_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropShipBPartnerLocationIdentifier))
			{
				final I_C_BPartner_Location dropShipBPartnerLocation = bPartnerLocationTable.get(dropShipBPartnerLocationIdentifier);
				softly.assertThat(dropShipBPartnerLocation.getC_BPartner_Location_ID()).isEqualTo(shippingNotification.getC_BPartner_Location_ID()).isEqualTo(order.getDropShip_Location_ID());
			}

			// either invoice contact or dropship contact (dropship takes precedence over invoice)

			final String contactIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_AD_User_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(contactIdentifier))
			{
				final I_AD_User contact = userTable.get(contactIdentifier);
				softly.assertThat(contact.getAD_User_ID()).isEqualTo(shippingNotification.getAD_User_ID()).isEqualTo(order.getAD_User_ID());
			}

			final String dropShipContactIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Order.COLUMNNAME_DropShip_User_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(dropShipContactIdentifier))
			{
				final I_AD_User dropShipContact = userTable.get(dropShipContactIdentifier);
				softly.assertThat(dropShipContact.getAD_User_ID()).isEqualTo(shippingNotification.getAD_User_ID()).isEqualTo(order.getDropShip_User_ID());
			}

			final String orgIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AD_Org_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_AD_Org org = orgTable.get(orgIdentifier);
			softly.assertThat(org.getAD_Org_ID()).isEqualTo(shippingNotification.getAD_Org_ID()).isEqualTo(order.getAD_Org_ID());

			final String locatorIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Locator_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_Locator locator = locatorTable.get(locatorIdentifier);
			softly.assertThat(locator.getM_Locator_ID()).isEqualTo(shippingNotification.getM_Locator_ID()).isEqualTo(order.getM_Locator_ID());

			final String harvestingCalendarIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_C_Harvesting_Calendar_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(harvestingCalendarIdentifier))
			{
				final I_C_Calendar harvestingCalendar = calendarTable.get(harvestingCalendarIdentifier);
				softly.assertThat(harvestingCalendar.getC_Calendar_ID()).isEqualTo(shippingNotification.getC_Harvesting_Calendar_ID()).isEqualTo(order.getC_Harvesting_Calendar_ID());
			}

			final String harvestingYearIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, COLUMNNAME_Harvesting_Year_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(harvestingYearIdentifier))
			{
				final I_C_Year harvestingYear = yearTable.get(harvestingYearIdentifier);
				softly.assertThat(harvestingYear.getC_Year_ID()).isEqualTo(shippingNotification.getHarvesting_Year_ID()).isEqualTo(order.getHarvesting_Year_ID());
			}

			final String auctionIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_C_Auction_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(auctionIdentifier))
			{
				final I_C_Auction auction = auctionTable.get(auctionIdentifier);
				softly.assertThat(auction.getC_Auction_ID()).isEqualTo(shippingNotification.getC_Auction_ID()).isEqualTo(order.getC_Auction_ID());
			}

			final String poReference = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_POReference);
			if (Check.isNotBlank(poReference))
			{
				softly.assertThat(poReference).isEqualTo(shippingNotification.getPOReference()).isEqualTo(order.getPOReference());
			}

			final String description = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_Description);
			if (Check.isNotBlank(description))
			{
				softly.assertThat(description).isEqualTo(shippingNotification.getDescription()).isEqualTo(order.getDescription());
			}
			final String shippingNotificationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Shipping_Notification_ID + "." + TABLECOLUMN_IDENTIFIER);
			shippingNotificationTable.putOrReplace(shippingNotificationIdentifier, shippingNotification);
		}

		softly.assertAll();
	}

	@And("^after not more than (.*)s, locate reversal M_Shipping_Notifications$")
	public void find_reversal_M_Shipping_Notification(final int timeoutSec, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final SoftAssertions softly = new SoftAssertions();

		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_M_Shipping_Notification reversalShippingNotification = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, () -> load_reversal_ShippingNotification(row));

			softly.assertThat(reversalShippingNotification).isNotNull();

			final String reversalShippingNotificationIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_Shipping_Notification_ID + "." + TABLECOLUMN_IDENTIFIER);
			shippingNotificationTable.putOrReplace(reversalShippingNotificationIdentifier, reversalShippingNotification);
		}
		softly.assertAll();
	}

	@And("^the ShippingNotification identified by (.*) is (reversed)$")
	public void shippingNotification_action(@NonNull final String notificationIdentifier, @NonNull final String action)
	{
		final I_M_Shipping_Notification shippingNotification = shippingNotificationTable.get(notificationIdentifier);

		switch (StepDefDocAction.valueOf(action))
		{
			case reversed ->
			{
				shippingNotification.setDocAction(IDocument.ACTION_Complete);
				documentBL.processEx(shippingNotification, IDocument.ACTION_Reverse_Correct, IDocument.STATUS_Reversed);
			}
			default -> throw new AdempiereException("Unhandled shippingNotification action")
					.appendParametersToMessage()
					.setParameter("action:", action);
		}
	}

	@NonNull
	private ItemProvider.ProviderResult<I_M_Shipping_Notification> load_ShippingNotification(@NonNull final Map<String, String> row, @NonNull final I_C_Order order)
	{
		final String docStatus = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_DocStatus);

		final I_M_Shipping_Notification shippingNotificationRecord = queryBL.createQueryBuilder(I_M_Shipping_Notification.class)
				.addEqualsFilter(COLUMNNAME_C_Order_ID, order.getC_Order_ID())
				.addInArrayFilter(COLUMNNAME_DocStatus, DocStatus.ofNullableCodeOrUnknown(docStatus))
				.create()
				.firstOnlyOrNull(I_M_Shipping_Notification.class);

		if (shippingNotificationRecord == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No I_M_Shipping_Notification found for row=" + row);
		}

		return ItemProvider.ProviderResult.resultWasFound(shippingNotificationRecord);
	}

	@NonNull
	private ItemProvider.ProviderResult<I_M_Shipping_Notification> load_reversal_ShippingNotification(@NonNull final Map<String, String> row)
	{
		final String shippingNotificationToReverseIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_Reversal_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_Shipping_Notification shippingNotificationToReverse = shippingNotificationTable.get(shippingNotificationToReverseIdentifier);

		final I_M_Shipping_Notification reversalShippingNotificationRecord = queryBL.createQueryBuilder(I_M_Shipping_Notification.class)
				.addEqualsFilter(COLUMNNAME_Reversal_ID, shippingNotificationToReverse.getM_Shipping_Notification_ID())
				.create()
				.firstOnlyOrNull(I_M_Shipping_Notification.class);

		if (reversalShippingNotificationRecord == null)
		{
			return ItemProvider.ProviderResult.resultWasNotFound("No reversal I_M_Shipping_Notification found for row=" + row);
		}

		return ItemProvider.ProviderResult.resultWasFound(reversalShippingNotificationRecord);
	}
}
