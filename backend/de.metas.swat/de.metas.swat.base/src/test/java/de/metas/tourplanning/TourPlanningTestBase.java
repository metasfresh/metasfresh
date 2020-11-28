package de.metas.tourplanning;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_AD_OrgInfo;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.organization.OrgId;
import de.metas.organization.StoreCreditCardNumberMode;
import de.metas.product.ProductId;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IDeliveryDayDAO;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.tourplanning.api.ITourDAO;
import de.metas.tourplanning.api.ITourInstanceBL;
import de.metas.tourplanning.api.ITourInstanceDAO;
import de.metas.tourplanning.api.impl.DeliveryDayBL;
import de.metas.tourplanning.api.impl.DeliveryDayDAO;
import de.metas.tourplanning.api.impl.ShipmentScheduleDeliveryDayBL;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.tourplanning.model.validator.DeliveryDayAllocableInterceptor;
import de.metas.util.Services;

/**
 * Base class (to be extended) for all Tour Planning tests.
 *
 * @author tsa
 *
 */
@ExtendWith(AdempiereTestWatcher.class)
public abstract class TourPlanningTestBase
{
	protected IContextAware contextProvider;
	protected DateFormat dateTimeFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSSS");
	protected final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	protected final ZoneId timeZone = ZoneId.of("Europe/Berlin");

	//
	// Services
	protected DeliveryDayBL deliveryDayBL;
	protected DeliveryDayDAO deliveryDayDAO;
	protected ShipmentScheduleDeliveryDayBL shipmentScheduleDeliveryDayBL;
	protected ITourDAO tourDAO;
	protected ITourInstanceBL tourInstanceBL;
	protected ITourInstanceDAO tourInstanceDAO;

	// Master data
	protected I_M_Tour tour;
	protected I_M_TourVersion tourVersion;
	protected I_C_BPartner bpartner;
	protected I_C_BPartner_Location bpLocation;
	protected ProductId productId;

	@BeforeEach
	public final void beforeEachTest()
	{
		AdempiereTestHelper.get().init();

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String trxName = trxManager.createTrxName("Dummy_ThreadInherited", true);
		trxManager.setThreadInheritedTrxName(trxName);

		this.contextProvider = PlainContextAware.newWithThreadInheritedTrx();

		//
		// Model Interceptors
		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(new de.metas.tourplanning.model.validator.TourPlanningModuleActivator());

		//
		// Services
		this.tourDAO = Services.get(ITourDAO.class);
		this.deliveryDayBL = (DeliveryDayBL)Services.get(IDeliveryDayBL.class);
		this.deliveryDayDAO = (DeliveryDayDAO)Services.get(IDeliveryDayDAO.class);
		this.shipmentScheduleDeliveryDayBL = (ShipmentScheduleDeliveryDayBL)Services.get(IShipmentScheduleDeliveryDayBL.class);
		this.tourInstanceBL = Services.get(ITourInstanceBL.class);
		this.tourInstanceDAO = Services.get(ITourInstanceDAO.class);

		final I_AD_OrgInfo orgInfo =newInstance(I_AD_OrgInfo.class);
		orgInfo.setAD_Org_ID(OrgId.ANY.getRepoId());
		orgInfo.setStoreCreditCardData(StoreCreditCardNumberMode.DONT_STORE.getCode());
		orgInfo.setTimeZone(timeZone.getId());
		saveRecord(orgInfo);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		afterInit();
	}

	protected abstract void afterInit();

	@AfterEach
	public final void afterEachTest()
	{
		de.metas.common.util.time.SystemTime.resetTimeSource();
	}

	protected I_C_BPartner createBPartner(final String name)
	{
		final I_C_BPartner bpartner =newInstance(I_C_BPartner.class, contextProvider);
		bpartner.setValue(name);
		bpartner.setName(name);
		save(bpartner);
		return bpartner;
	}

	protected I_C_BPartner_Location createBPLocation(final I_C_BPartner bpartner)
	{
		final I_C_BPartner_Location bpLocation =newInstance(I_C_BPartner_Location.class, contextProvider);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		save(bpLocation);

		return bpLocation;
	}

	protected final I_M_Tour createTour(final String name)
	{
		final I_M_Tour tour =newInstance(I_M_Tour.class, contextProvider);
		tour.setName(name);
		save(tour);

		return tour;
	}

	protected final I_M_TourVersion createTourVersion(final I_M_Tour tour, final LocalDate validFrom)
	{
		final I_M_TourVersion tourVersion =newInstance(I_M_TourVersion.class, tour);
		tourVersion.setName(tour.getName() + "-" + validFrom);
		tourVersion.setM_Tour(tour);
		tourVersion.setValidFrom(TimeUtil.asTimestamp(validFrom));
		save(tourVersion);

		return tourVersion;
	}

	protected final LocalDate createDate(final String yyyyMMdd)
	{
		try
		{
			final Date date = dateFormat.parse(yyyyMMdd);
			return TimeUtil.asLocalDate(date);
		}
		catch (ParseException e)
		{
			throw new IllegalArgumentException("Invalid date string: " + yyyyMMdd, e);
		}
	}

	protected ZonedDateTime toZonedDateTime(final String dateTimeStr)
	{
		return TimeUtil.asZonedDateTime(toDateTimeTimestamp(dateTimeStr));
	}

	protected Timestamp toDateTimeTimestamp(final String dateTimeStr)
	{
		try
		{
			final Date date = dateTimeFormat.parse(dateTimeStr);
			return TimeUtil.asTimestamp(date);
		}
		catch (ParseException e)
		{
			throw new RuntimeException("Cannot parse: " + dateTimeStr, e);
		}
	}

	protected void assertProcessed(final boolean processedExpected, final List<I_M_DeliveryDay> deliveryDays)
	{
		for (final I_M_DeliveryDay dd : deliveryDays)
		{
			assertThat(dd.isProcessed()).as("processed flag of " + dd).isEqualTo(processedExpected);
		}
	}

	protected void assertProcessed(final boolean processedExpected, final I_M_Tour_Instance tourInstance)
	{
		assertThat(tourInstance).as("tourInstance").isNotNull();
		assertThat(tourInstance.isProcessed()).as("processed flag of " + tourInstance).isEqualTo(processedExpected);

		final List<I_M_DeliveryDay> deliveryDays = deliveryDayDAO.retrieveDeliveryDaysForTourInstance(tourInstance);
		assertProcessed(processedExpected, deliveryDays);
	}

	/**
	 * Asserts given shipment schedule is allocated to expected delivery day.
	 *
	 * @param deliveryDayExpected
	 * @param shipmentSchedule
	 */
	protected I_M_DeliveryDay_Alloc assertDeliveryDayAlloc(final I_M_DeliveryDay deliveryDayExpected, final I_M_ShipmentSchedule shipmentSchedule)
	{
		final IDeliveryDayAllocable deliveryDayAllocable = shipmentScheduleDeliveryDayBL.asDeliveryDayAllocable(shipmentSchedule);

		final I_M_DeliveryDay_Alloc alloc = deliveryDayDAO.retrieveDeliveryDayAllocForModel(contextProvider, deliveryDayAllocable);
		assertThat(alloc).as("Delivery Day allocation for " + shipmentSchedule).isNotNull();

		final I_M_DeliveryDay deliveryDayActual = alloc.getM_DeliveryDay();
		assertThat(deliveryDayActual == null ? -1 : deliveryDayActual.getM_DeliveryDay_ID())
				.as("delivery day for " + shipmentSchedule)
				.isEqualTo(deliveryDayExpected == null ? -1 : deliveryDayExpected.getM_DeliveryDay_ID());

		return alloc;
	}

	/**
	 * sets the system time to a static value. Note that this is reset after each test by {@link #resetSystemTime()}.
	 *
	 * @param currentTime
	 */
	protected void setSystemTime(final String currentTime)
	{
		SystemTime.setTimeSource(() -> toDateTimeTimestamp(currentTime).getTime());
	}

	protected I_M_DeliveryDay createDeliveryDay(final String deliveryDateTimeStr, final int bufferHours, final int bPartnerId, final int bpLocationId)
	{
		final I_M_DeliveryDay deliveryDay =newInstance(I_M_DeliveryDay.class, contextProvider);
		deliveryDay.setIsActive(true);
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
		assertThat(deliveryDay.getDeliveryDateTimeMax()).as("DeliveryDateTimeMax").isNotNull();

		save(deliveryDay);

		return deliveryDay;
	}

	/**
	 * Create a shipment schedule for the given oder.
	 * <p>
	 * NOTE: we expect that the tests was set up such that {@link DeliveryDayAllocableInterceptor} is fired when the shipment schedule is stored.
	 *
	 * @param order
	 * @return
	 */
	protected I_M_ShipmentSchedule createShipmentSchedule(final I_C_Order order, final int qtyOrdered)
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class, order);
		shipmentSchedule.setC_Order_ID(order.getC_Order_ID());
		shipmentSchedule.setC_BPartner_ID(order.getC_BPartner_ID());
		shipmentSchedule.setC_BPartner_Location_ID(order.getC_BPartner_Location_ID());

		shipmentSchedule.setM_Product_ID(productId.getRepoId());
		shipmentSchedule.setQtyOrdered_Calculated(BigDecimal.valueOf(qtyOrdered));

		shipmentSchedule.setDeliveryDate(order.getDatePromised());
		shipmentSchedule.setPreparationDate(order.getPreparationDate());
		save(shipmentSchedule);

		return shipmentSchedule;
	}

}
