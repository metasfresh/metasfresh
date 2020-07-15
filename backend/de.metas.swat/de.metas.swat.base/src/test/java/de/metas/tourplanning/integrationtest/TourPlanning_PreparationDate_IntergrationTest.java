package de.metas.tourplanning.integrationtest;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_C_Order;
import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;

public class TourPlanning_PreparationDate_IntergrationTest extends TourPlanningTestBase
{
	//
	// Master data
	private I_M_Tour tour;
	private I_M_TourVersion tourVersion;
	private I_C_BPartner bpartner;
	private I_C_BPartner_Location bpLocation;

	@Override
	protected void afterInit()
	{
		//
		// Create master data
		tour = createTour("tour01");
		tourVersion = createTourVersion(tour, LocalDate.of(2014, 1, 1));
		bpartner = createBPartner("bp1");
		bpLocation = createBPLocation(bpartner);
	}

	@Test
	public void test()
	{
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID()); // => we expect this one to be used
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());

		final String currentTime = "07.09.2014 00:00:00.000";
		setSystemTime(currentTime);

		//
		// Create and test Order's PreparationDate
		final I_C_Order order = createOrder(
				"08.09.2014 23:59:59.999" // date promised
		);
		assertThat(order.getPreparationDate())
				.isEqualTo(toDateTimeTimestamp("08.09.2014 15:00:00.000"))
				.withFailMessage("Invalid order PreparationDate: ", order);

		//
		// Create and validate Shipment Schedule
		final I_M_ShipmentSchedule shipmentSchedule = createShipmentSchedule(order, -1);

		assertThat(shipmentSchedule.getDeliveryDate())
				.isEqualTo(toDateTimeTimestamp("08.09.2014 23:59:59.999"))
				.withFailMessage("Invalid shipment schedule's DeliveryDate: ", shipmentSchedule);

		assertThat(shipmentSchedule.getPreparationDate())
				.isEqualTo(toDateTimeTimestamp("08.09.2014 15:00:00.000"))
				.withFailMessage("Invalid shipment schedule's PreparationDate: ", shipmentSchedule);

		assertDeliveryDayAlloc(dd2, shipmentSchedule);
	}

	@Test
	public void test_NullBPartnerAndNullLocation()
	{
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd = createDeliveryDay("08.09.2014 15:00:00.000", 5, -1, -1);

		final String currentTime = "07.09.2014 00:00:00.000";
		setSystemTime(currentTime);

		//
		// Create and test Order's PreparationDate
		final I_C_Order order = createOrder(
				"08.09.2014 23:59:59.999" // date promised
		);

		assertThat(order.getPreparationDate())
				.isEqualTo(toDateTimeTimestamp("08.09.2014 15:00:00.000"))
				.withFailMessage("Invalid order PreparationDate: ", order);

	}

	@Test
	public void test_WrongBPartnerAndNullLocation()
	{
		final I_C_BPartner partner2 = createBPartner("bp2");
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd = createDeliveryDay("08.09.2014 15:00:00.000", 5, partner2.getC_BPartner_ID(), -1);

		final String currentTime = "07.09.2014 00:00:00.000";
		setSystemTime(currentTime);

		//
		// Create and test Order's PreparationDate
		final I_C_Order order = createOrder(
				"08.09.2014 23:59:59.999" // date promised
		);
		assertThat(order.getPreparationDate())
				.isEqualTo(toDateTimeTimestamp("08.09.2014 23:59:59.999"))
				.withFailMessage("Invalid order PreparationDate: ", order);

	}

	@Test
	public void test_WrongBPartnerAndWrongLocation()
	{
		final I_C_BPartner partner2 = createBPartner("bp2");
		final I_C_BPartner_Location location2 = createBPLocation(partner2);
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd = createDeliveryDay("08.09.2014 15:00:00.000", 5, partner2.getC_BPartner_ID(), location2.getC_BPartner_Location_ID());

		final String currentTime = "07.09.2014 00:00:00.000";
		setSystemTime(currentTime);

		//
		// Create and test Order's PreparationDate
		final I_C_Order order = createOrder(
				"08.09.2014 23:59:59.999" // date promised
		);

		assertThat(order.getPreparationDate())
				.isEqualTo(toDateTimeTimestamp("08.09.2014 23:59:59.999"))
				.withFailMessage("Invalid order PreparationDate: ", order);

	}

	@Test
	public void test_CorrectBPartnerAndNullLocation()
	{
		@SuppressWarnings("unused")
		final I_M_DeliveryDay dd = createDeliveryDay("08.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), -1);

		final String currentTime = "07.09.2014 00:00:00.000";
		setSystemTime(currentTime);

		//
		// Create and test Order's PreparationDate
		final I_C_Order order = createOrder(
				"08.09.2014 23:59:59.999" // date promised
		);
		assertThat(order.getPreparationDate())
				.isEqualTo(toDateTimeTimestamp("08.09.2014 15:00:00.000"))
				.withFailMessage("Invalid order PreparationDate: ", order);

	}

	@Override
	protected I_M_DeliveryDay createDeliveryDay(final String deliveryDateTimeStr, final int bufferHours, final int bPartnerId, final int bpLocationId)
	{
		final I_M_DeliveryDay deliveryDay = newInstance(I_M_DeliveryDay.class, contextProvider);
		deliveryDay.setC_BPartner_ID(bPartnerId);
		deliveryDay.setC_BPartner_Location_ID(bpLocationId);
		deliveryDay.setDeliveryDate(toDateTimeTimestamp(deliveryDateTimeStr));
		deliveryDay.setBufferHours(bufferHours);
		deliveryDay.setIsManual(false);
		deliveryDay.setIsToBeFetched(false);
		deliveryDay.setM_Tour(tour);
		deliveryDay.setM_TourVersion(tourVersion);
		deliveryDay.setM_Tour_Instance(null);
		deliveryDay.setProcessed(false);

		deliveryDayBL.setDeliveryDateTimeMax(deliveryDay);
		assertThat(deliveryDay.getDeliveryDateTimeMax())
				.isNotNull()
				.withFailMessage("DeliveryDateTimeMax shall be set");

		save(deliveryDay);

		return deliveryDay;
	}

	private I_C_Order createOrder(final String datePromisedStr)
	{
		final I_C_Order order = newInstance(I_C_Order.class, contextProvider);
		order.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		order.setC_BPartner_Location_ID(bpLocation.getC_BPartner_Location_ID());

		order.setIsSOTrx(true);
		order.setDatePromised(toDateTimeTimestamp(datePromisedStr));
		order.setPreparationDate(null); // to be updated

		save(order);
		// NOTE: we expect PreparationDate to be set by model validator

		return order;
	}
}
