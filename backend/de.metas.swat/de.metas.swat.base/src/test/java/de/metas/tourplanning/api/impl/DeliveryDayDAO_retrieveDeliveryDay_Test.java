package de.metas.tourplanning.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import de.metas.bpartner.BPartnerId;
import de.metas.invoicecandidate.modelvalidator.C_BPartner;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.tourplanning.TourPlanningTestBase;
import de.metas.tourplanning.api.IDeliveryDayQueryParams;
import de.metas.tourplanning.api.PlainDeliveryDayQueryParams;
import de.metas.tourplanning.model.I_M_DeliveryDay;

/**
 * Tests for {@link DeliveryDayDAO#retrieveDeliveryDay(IContextAware, IDeliveryDayQueryParams)}
 *
 * @author tsa
 *
 */
public class DeliveryDayDAO_retrieveDeliveryDay_Test extends TourPlanningTestBase
{
	@Override
	public void afterInit()
	{
		tour = createTour("tour01");
		tourVersion = createTourVersion(tour, LocalDate.of(2014, 1, 1));
		bpartner = createBPartner("bp1");
		bpLocation = createBPLocation(bpartner);
	}

	@Test
	public void test_StandardUseCase()
	{
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());

		// shall be ignored
		I_C_BPartner otherBPartner = createBPartner("bp2");
		final I_M_DeliveryDay dd4_withoutLocation = createDeliveryDay("10.09.2014 15:00:00.000", 5, otherBPartner.getC_BPartner_ID(), -1);

		testRetrieveDeliveryDay(null, "06.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "07.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd2, "08.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "09.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "10.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "11.09.2014 23:59:59.999");
	}

	@Test
	public void test_M_DeliveryDay_DeliveryDate_Plus_Buffer_ExceedingTheDay()
	{
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 19:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 19:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 19:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());

		testRetrieveDeliveryDay(null, "07.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "08.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd2, "09.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "10.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "11.09.2014 23:59:59.999");
	}

	@Test
	public void test_M_DeliveryDay_SkipProcessed()
	{
		final I_M_DeliveryDay dd1 = createDeliveryDay("07.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		final I_M_DeliveryDay dd2 = createDeliveryDay("08.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
		final I_M_DeliveryDay dd3 = createDeliveryDay("09.09.2014 15:00:00.000", 5, bpartner.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());

		testRetrieveDeliveryDay(null, "06.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "07.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd2, "08.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "09.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "10.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "11.09.2014 23:59:59.999");

		// Make Delivery Day 2 as processed
		dd2.setProcessed(true);
		save(dd2);

		// Re-test again: those who were matched to dd2 now shall be matched to dd1
		testRetrieveDeliveryDay(null, "06.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "07.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd1, "08.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "09.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "10.09.2014 23:59:59.999");
		testRetrieveDeliveryDay(dd3, "11.09.2014 23:59:59.999");
	}

	private IDeliveryDayQueryParams createDeliveryDayQueryParams(final String deliveryDateStr)
	{
		final PlainDeliveryDayQueryParams params = new PlainDeliveryDayQueryParams();

		//params.setBPartnerId(BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID()));
		params.setBPartnerLocationId(BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID()));
		params.setToBeFetched(false);
		params.setDeliveryDate(toZonedDateTime(deliveryDateStr));
		params.setProcessed(false);
		return params;
	}

	/**
	 * Convenient method for calling {@link DeliveryDayDAO#retrieveDeliveryDay(IContextAware, IDeliveryDayQueryParams)}
	 */
	private I_M_DeliveryDay retrieveDeliveryDay(final String deliveryDateStr)
	{
		final IDeliveryDayQueryParams params = createDeliveryDayQueryParams(deliveryDateStr);
		return deliveryDayDAO.retrieveDeliveryDay(contextProvider, params);
	}

	private void testRetrieveDeliveryDay(final I_M_DeliveryDay deliveryDayExpected, final String deliveryDateStr)
	{
		final I_M_DeliveryDay deliveryDayActual = retrieveDeliveryDay(deliveryDateStr);

		assertThat(deliveryDayActual).isEqualTo(deliveryDayExpected).withFailMessage("Invalid M_DeliveryDay for: ", deliveryDateStr);

	}

}
