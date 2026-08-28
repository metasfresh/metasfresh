/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.tourplanning.process;

import de.metas.document.DocBaseType;
import de.metas.process.ProcessInfo;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.X_M_ShipperTransportation;
import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.user.UserId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * The transport order {@code M_Tour_Instance_CreateFromSelectedDeliveryDays} creates for a selected
 * {@code M_DeliveryDay} must carry the direction the delivery day's {@code IsToBeFetched} flag implies -
 * fetched from a vendor is {@code Incoming}, delivered to a customer is {@code Outgoing} - never a default.
 */
class M_Tour_Instance_CreateFromSelectedDeliveryDaysTest extends TourPlanningTestBase
{
	private I_C_BPartner bpartner;
	private I_C_BPartner_Location bpLocation;

	@Override
	protected void afterInit()
	{
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		createShipperTransportationDocType();

		tour = createTour("Tour1");
		tourVersion = createTourVersion(tour, LocalDate.of(2026, 1, 1));

		bpartner = createBPartner("M_Tour_Instance_CreateFromSelectedDeliveryDaysTest-BPartner");
		bpartner.setIsVendor(true); // IsToBeFetched=Y requires a vendor BPartner - see M_DeliveryDay#checkIsToBeFetched
		save(bpartner);
		bpLocation = createBPLocation(bpartner);
	}

	@Test
	void isToBeFetched_deliveryDay_yieldsIncomingTransportOrder() throws Exception
	{
		final I_M_DeliveryDay deliveryDay = createDeliveryDayToBeFetched(true);

		final I_M_ShipperTransportation shipperTransportation = runProcessAndGetShipperTransportation(deliveryDay);

		assertThat(shipperTransportation.getTransportDirection())
				.as("a delivery day fetched from a vendor must produce an Incoming transport order")
				.isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Incoming);
	}

	@Test
	void notToBeFetched_deliveryDay_yieldsOutgoingTransportOrder() throws Exception
	{
		final I_M_DeliveryDay deliveryDay = createDeliveryDayToBeFetched(false);

		final I_M_ShipperTransportation shipperTransportation = runProcessAndGetShipperTransportation(deliveryDay);

		assertThat(shipperTransportation.getTransportDirection())
				.as("a delivery day delivered to a customer must produce an Outgoing transport order")
				.isEqualTo(X_M_ShipperTransportation.TRANSPORTDIRECTION_Outgoing);
	}

	private I_M_DeliveryDay createDeliveryDayToBeFetched(final boolean isToBeFetched)
	{
		final I_M_DeliveryDay deliveryDay = createDeliveryDay(
				"01.01.2026 10:00:00.0000",
				2,
				bpartner.getC_BPartner_ID(),
				bpLocation.getC_BPartner_Location_ID());
		deliveryDay.setIsToBeFetched(isToBeFetched);
		save(deliveryDay);
		return deliveryDay;
	}

	/** Runs the process exactly as the WebUI would for a single selected delivery day, requesting a new transport order. */
	private I_M_ShipperTransportation runProcessAndGetShipperTransportation(final I_M_DeliveryDay deliveryDay) throws Exception
	{
		final M_Tour_Instance_CreateFromSelectedDeliveryDays process = new M_Tour_Instance_CreateFromSelectedDeliveryDays();
		process.init(ProcessInfo.builder()
				.setRecord(I_M_DeliveryDay.Table_Name, deliveryDay.getM_DeliveryDay_ID())
				.setCtx(Env.getCtx())
				.build());

		final Field isCreateShipperTransportationField = M_Tour_Instance_CreateFromSelectedDeliveryDays.class.getDeclaredField("p_IsCreateShipperTransportation");
		isCreateShipperTransportationField.setAccessible(true);
		isCreateShipperTransportationField.set(process, true);

		process.doIt();

		final I_M_DeliveryDay reloadedDeliveryDay = load(deliveryDay.getM_DeliveryDay_ID(), I_M_DeliveryDay.class);
		final I_M_Tour_Instance tourInstance = reloadedDeliveryDay.getM_Tour_Instance();
		assertThat(tourInstance).as("delivery day must be assigned to the created tour instance").isNotNull();

		return load(tourInstance.getM_ShipperTransportation_ID(), I_M_ShipperTransportation.class);
	}

	private void createShipperTransportationDocType()
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setName("Transport Order");
		docType.setDocBaseType(DocBaseType.ShipperTransportation.getCode());
		save(docType);
	}
}
