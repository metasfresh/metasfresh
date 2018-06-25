package de.metas.tourplanning.api.impl;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.TimeUtil;

import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IDeliveryDayDAO;
import de.metas.tourplanning.api.ITourBL;
import de.metas.tourplanning.api.PlainDeliveryDayQueryParams;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_TourVersion;
import de.metas.tourplanning.model.I_M_TourVersionLine;
import de.metas.tourplanning.model.validator.DeliveryDayAllocableInterceptor;
import de.metas.tourplanning.spi.CompositeDeliveryDayHandler;
import de.metas.tourplanning.spi.IDeliveryDayCreateHandler;
import de.metas.tourplanning.spi.IDeliveryDayHandler;
import lombok.NonNull;

public class DeliveryDayBL implements IDeliveryDayBL
{
	private final CompositeDeliveryDayHandler deliveryDayHandlers = new CompositeDeliveryDayHandler();
	private final Map<String, DeliveryDayAllocableInterceptor> tableName2deliveryDayAllocableInteceptor = new HashMap<>();

	public DeliveryDayBL()
	{
		super();
	}

	@Override
	public void registerDeliveryDayHandler(@NonNull final IDeliveryDayHandler handler)
	{
		deliveryDayHandlers.addDeliveryDayHandler(handler);

		if (handler instanceof IDeliveryDayCreateHandler)
		{
			final IDeliveryDayCreateHandler createHandler = (IDeliveryDayCreateHandler)handler;
			final DeliveryDayAllocableInterceptor interceptor = new DeliveryDayAllocableInterceptor(createHandler);
			final DeliveryDayAllocableInterceptor interceptorOld = tableName2deliveryDayAllocableInteceptor.get(interceptor.getModelTableName());
			if (interceptorOld != null)
			{
				throw new AdempiereException("Cannot register create handler interceptor '" + interceptor + " because an old one was already registered: " + interceptorOld);
			}

			Services.get(IModelInterceptorRegistry.class).addModelInterceptor(interceptor);
		}
	}

	@Override
	public IDeliveryDayHandler getDeliveryDayHandlers()
	{
		return deliveryDayHandlers;
	}

	@Override
	public I_M_DeliveryDay createDeliveryDay(final I_M_TourVersionLine tourVersionLine, final Date deliveryDate, final String trxName)
	{
		Check.assumeNotNull(tourVersionLine, "tourVersionLine not null");
		Check.assumeNotNull(deliveryDate, "deliveryDate not null");

		final int bpartnerId = tourVersionLine.getC_BPartner_ID();
		final int bpartnerLocationId = tourVersionLine.getC_BPartner_Location_ID();
		final boolean isToBeFetched = tourVersionLine.isToBeFetched();
		final int bufferHours = tourVersionLine.getBufferHours();
		final int seqNo = tourVersionLine.getSeqNo();
		final I_M_TourVersion tourVersion = tourVersionLine.getM_TourVersion();
		final int tourId = tourVersion.getM_Tour_ID();
		final int adOrgId = tourVersion.getAD_Org_ID();

		//
		// Get Preparation Date+Time
		// If we do not have preparation time, do not try to generate.
		final Timestamp preparationTime = Services.get(ITourBL.class).getPreparationDateTime(tourVersion, deliveryDate);
		if (preparationTime == null)
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(tourVersionLine);
		final I_M_DeliveryDay deliveryDay = InterfaceWrapperHelper.create(ctx, I_M_DeliveryDay.class, trxName);
		deliveryDay.setC_BPartner_ID(bpartnerId);
		deliveryDay.setAD_Org_ID(adOrgId);
		deliveryDay.setC_BPartner_Location_ID(bpartnerLocationId);
		deliveryDay.setIsManual(false);
		deliveryDay.setM_Tour_ID(tourId);
		deliveryDay.setM_TourVersion(tourVersion);
		deliveryDay.setIsToBeFetched(isToBeFetched);
		deliveryDay.setSeqNo(seqNo);

		// Dates
		deliveryDay.setDeliveryDate(preparationTime);
		deliveryDay.setBufferHours(bufferHours);
		setDeliveryDateTimeMax(deliveryDay);

		InterfaceWrapperHelper.save(deliveryDay);

		return deliveryDay;
	}

	@Override
	public I_M_DeliveryDay_Alloc getCreateDeliveryDayAlloc(final IContextAware context, final IDeliveryDayAllocable model)
	{
		final IDeliveryDayDAO deliveryDayDAO = Services.get(IDeliveryDayDAO.class);

		final PlainDeliveryDayQueryParams deliveryDayMatchingParams = createDeliveryDayQueryParams(model);

		//
		//
		I_M_DeliveryDay_Alloc deliveryDayAlloc = deliveryDayDAO.retrieveDeliveryDayAllocForModel(context, model);
		if (deliveryDayAlloc != null)
		{
			final I_M_DeliveryDay deliveryDayCurrent = deliveryDayAlloc.getM_DeliveryDay();

			if (!deliveryDayDAO.isDeliveryDayMatches(deliveryDayCurrent, deliveryDayMatchingParams))
			{
				// Delivery Date is not matching anymore => we need to destroy this allocation
				InterfaceWrapperHelper.delete(deliveryDayAlloc);
				deliveryDayAlloc = null;
			}
		}

		//
		// Create a new Delivery Day Allocation if we don't have one yet
		// ... and get the the Delivery Day record
		if (deliveryDayAlloc == null)
		{
			deliveryDayMatchingParams.setProcessed(false); // only not processed delivery days
			final I_M_DeliveryDay deliveryDay = deliveryDayDAO.retrieveDeliveryDay(context, deliveryDayMatchingParams);
			if (deliveryDay != null)
			{
				deliveryDayAlloc = createDeliveryDayAlloc(context, deliveryDay, model);
			}
		}

		return deliveryDayAlloc;
	}

	private PlainDeliveryDayQueryParams createDeliveryDayQueryParams(final IDeliveryDayAllocable deliveryDayAllocable)
	{
		final PlainDeliveryDayQueryParams params = new PlainDeliveryDayQueryParams();
		params.setC_BPartner_Location_ID(deliveryDayAllocable.getC_BPartner_Location_ID());
		params.setDeliveryDate(deliveryDayAllocable.getDeliveryDate());
		params.setToBeFetched(deliveryDayAllocable.isToBeFetched());

		return params;
	}

	private I_M_DeliveryDay_Alloc createDeliveryDayAlloc(final IContextAware context, final I_M_DeliveryDay deliveryDay, final IDeliveryDayAllocable deliveryDayAllocable)
	{
		Check.assume(!deliveryDay.isProcessed(), "Delivery day shall not be processed: {}", deliveryDay);

		final String tableName = deliveryDayAllocable.getTableName();
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		final I_M_DeliveryDay_Alloc deliveryDayAlloc = InterfaceWrapperHelper.newInstance(I_M_DeliveryDay_Alloc.class, context);
		deliveryDayAlloc.setAD_Org_ID(deliveryDayAllocable.getAD_Org_ID());
		deliveryDayAlloc.setM_DeliveryDay(deliveryDay);
		deliveryDayAlloc.setAD_Table_ID(adTableId);
		deliveryDayAlloc.setRecord_ID(deliveryDayAllocable.getRecord_ID());
		deliveryDayAlloc.setM_Product_ID(deliveryDayAllocable.getM_Product_ID());

		deliveryDayHandlers.updateDeliveryDayAllocFromModel(deliveryDayAlloc, deliveryDayAllocable);

		InterfaceWrapperHelper.save(deliveryDayAlloc);

		return deliveryDayAlloc;
	}

	@Override
	public void inactivate(final I_M_DeliveryDay deliveryDay, final String trxName)
	{
		Check.assumeNotNull(deliveryDay, "deliveryDay not null");
		deliveryDay.setIsActive(false);
		InterfaceWrapperHelper.save(deliveryDay, trxName);
	}

	@Override
	public void invalidate(final I_M_DeliveryDay deliveryDay)
	{
		Check.assumeNotNull(deliveryDay, "deliveryDay not null");

		// TODO: implement
	}

	/**
	 * Sets DeliveryDateTimeMax = DeliveryDate + BufferHours.
	 *
	 * @param deliveryDay
	 */
	@Override
	public void setDeliveryDateTimeMax(final I_M_DeliveryDay deliveryDay)
	{
		final Timestamp deliveryDate = deliveryDay.getDeliveryDate();
		final int bufferHours = deliveryDay.getBufferHours();
		final Timestamp deliveryDateTimeMax = TimeUtil.addHours(deliveryDate, bufferHours);

		deliveryDay.setDeliveryDateTimeMax(deliveryDateTimeMax);
	}

	@Override
	public Timestamp calculatePreparationDateOrNull(
			final IContextAware context,
			final boolean isSOTrx,
			final Timestamp dateOrdered,
			final Timestamp datePromised,
			final int bpartnerLocationId)
	{
		// #1211
		// Note: I am commenting this out instead of deleting it for traceability. This functionality changed several times in the past.
		// task 09004: add the current time as parameter and fetch the closest next fit delivery day
		// final Timestamp calculationTime = SystemTime.asTimestamp();

		// task #1211
		// The search will initially be made for the first deliveryDay of the day of the promised date
		// For example, if there are 3 deliveryDay entries for a certain date and the products are
		// promised to be shipped in that day's evening, the first deliveryDay of that day will be chosen.
		Timestamp preparationDay = TimeUtil.getDay(datePromised);

		// the date+time when the calculation is made, generically named DateOrdered.
		// It will usually be when the date+time when the order was created, or the system time
		final Timestamp calculationTime = dateOrdered;

		//
		// Create Delivery Day Query Parameters
		final PlainDeliveryDayQueryParams deliveryDayQueryParams = new PlainDeliveryDayQueryParams();
		deliveryDayQueryParams.setC_BPartner_Location_ID(bpartnerLocationId);
		deliveryDayQueryParams.setDeliveryDate(datePromised);
		deliveryDayQueryParams.setToBeFetched(!isSOTrx);
		deliveryDayQueryParams.setProcessed(false);
		deliveryDayQueryParams.setCalculationTime(calculationTime);
		deliveryDayQueryParams.setPreparationDay(preparationDay);

		//
		// Find matching delivery day record
		final IDeliveryDayDAO deliveryDayDAO = Services.get(IDeliveryDayDAO.class);
		I_M_DeliveryDay dd = deliveryDayDAO.retrieveDeliveryDay(context, deliveryDayQueryParams);

		// task #1211
		// If there are no deliveryDay entries for the given date that are before the promised date/time,
		// select the last available deliveryDay that is before the promised date/time
		if (dd == null)
		{
			preparationDay = null;
			deliveryDayQueryParams.setPreparationDay(preparationDay);
			dd = deliveryDayDAO.retrieveDeliveryDay(context, deliveryDayQueryParams);

		}

		//
		// Extract PreparationDate from DeliveryDay record
		final Timestamp preparationDate;
		if (dd == null)
		{
			preparationDate = null;
		}
		else
		{
			preparationDate = dd.getDeliveryDate();
		}

		return preparationDate;
	}
}
