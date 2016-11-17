package de.metas.tourplanning.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.adempiere.form.IClientUI;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;
import de.metas.shipping.api.IShipperTransportationBL;
import de.metas.tourplanning.api.ITourInstanceBL;
import de.metas.tourplanning.api.ITourInstanceDAO;
import de.metas.tourplanning.api.ITourInstanceQueryParams;
import de.metas.tourplanning.api.impl.PlainTourInstanceQueryParams;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_ShipperTransportation;
import de.metas.tourplanning.model.I_M_Tour_Instance;

public class M_Tour_Instance_CreateFromSelectedDeliveryDays extends JavaProcess
{
	//
	// Services
	private final ITourInstanceBL tourInstanceBL = Services.get(ITourInstanceBL.class);

	//
	// Process Parameter
	private boolean p_IsCreateShipperTransportation;
	private static final String PARAM_M_ShipperTransportation_ID = "M_ShipperTransportation_ID";
	private int p_M_ShipperTransportation_ID;
	private I_M_ShipperTransportation p_M_ShipperTransportation;
	private int p_Shipper_BPartner_ID;
	private int p_Shipper_Location_ID;
	private int p_M_Shipper_ID;

	//
	private I_M_Tour_Instance _tourInstance = null;
	private I_M_ShipperTransportation _shipperTransportation = null;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			if (para.getParameter() == null)
			{
				continue;
			}
			final String parameterName = para.getParameterName();

			if ("IsCreateShipperTransportation".equals(parameterName))
			{
				p_IsCreateShipperTransportation = para.getParameterAsBoolean();
			}
			else if (PARAM_M_ShipperTransportation_ID.equals(parameterName))
			{
				p_M_ShipperTransportation_ID = para.getParameterAsInt();
			}
			else if ("Shipper_BPartner_ID".equals(parameterName))
			{
				p_Shipper_BPartner_ID = para.getParameterAsInt();
			}
			else if ("Shipper_Location_ID".equals(parameterName))
			{
				p_Shipper_Location_ID = para.getParameterAsInt();
			}
			else if ("M_Shipper_ID".equals(parameterName))
			{
				p_M_Shipper_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_M_DeliveryDay> deliveryDays = retrieveSelectedDeliveryDays();
		if (!deliveryDays.hasNext())
		{
			throw new AdempiereException("@NoSelection@");
		}
		while (deliveryDays.hasNext())
		{
			final I_M_DeliveryDay deliveryDay = deliveryDays.next();

			final I_M_Tour_Instance tourInstance = getCreateTourInstance(deliveryDay);

			tourInstanceBL.assignToTourInstance(deliveryDay, tourInstance);
			InterfaceWrapperHelper.save(deliveryDay);
		}

		return MSG_OK;
	}

	@Override
	protected void postProcess(boolean success)
	{
		if (!success)
		{
			return;
		}

		if (_tourInstance == null)
		{
			return;
		}

		Services.get(IClientUI.class).showWindow(_tourInstance);
	}

	private I_M_Tour_Instance getCreateTourInstance(final I_M_DeliveryDay deliveryDay)
	{
		//
		// Use already existing tour instance if found or created
		if (_tourInstance != null)
		{
			return _tourInstance;
		}

		//
		// If we have M_ShipperTransportation as parameter,
		// try finding an existing Tour Instance for that shipper transportation
		final I_M_ShipperTransportation shipperTransportationParam = getM_ShipperTransportation_Param();
		if (shipperTransportationParam != null)
		{
			final Object contextProvider = this;
			final ITourInstanceQueryParams params = new PlainTourInstanceQueryParams();
			params.setM_ShipperTransportation_ID(shipperTransportationParam.getM_ShipperTransportation_ID());
			params.setProcessed(false);
			_tourInstance = Services.get(ITourInstanceDAO.class).retrieveTourInstance(contextProvider, params);
			if (_tourInstance != null)
			{
				return _tourInstance;
			}
		}

		//
		// Create a new tour instance
		{
			final IContextAware context = this;
			_tourInstance = Services.get(ITourInstanceBL.class).createTourInstanceDraft(context, deliveryDay);

			final I_M_ShipperTransportation shipperTransportation = getCreateShipperTransportation(_tourInstance);
			_tourInstance.setM_ShipperTransportation(shipperTransportation);

			InterfaceWrapperHelper.save(_tourInstance);
			return _tourInstance;
		}
	}

	private I_M_ShipperTransportation getM_ShipperTransportation_Param()
	{
		if (p_IsCreateShipperTransportation)
		{
			return null;
		}

		if (p_M_ShipperTransportation != null)
		{
			return p_M_ShipperTransportation;
		}

		if (p_M_ShipperTransportation_ID <= 0)
		{
			throw new FillMandatoryException(PARAM_M_ShipperTransportation_ID);
		}
		p_M_ShipperTransportation = InterfaceWrapperHelper.create(getCtx(), p_M_ShipperTransportation_ID, I_M_ShipperTransportation.class, getTrxName());
		Check.assumeNotNull(p_M_ShipperTransportation, "shipperTransportation not null");

		// Make sure shipper transportation is not processed
		if (p_M_ShipperTransportation.isProcessed())
		{
			throw new AdempiereException("@M_ShipperTransportation_ID@: @Processed@=@Y@");
		}

		return p_M_ShipperTransportation;
	}

	private I_M_ShipperTransportation getCreateShipperTransportation(final I_M_Tour_Instance tourInstance)
	{
		if (_shipperTransportation != null)
		{
			return _shipperTransportation;
		}

		//
		// Use the shipper transportation passed by paramters if any
		_shipperTransportation = getM_ShipperTransportation_Param();
		if (_shipperTransportation != null)
		{
			return _shipperTransportation;
		}

		//
		// Create a new shipper transportation
		_shipperTransportation = createShipperTransportation(tourInstance);
		return _shipperTransportation;
	}

	private I_M_ShipperTransportation createShipperTransportation(final I_M_Tour_Instance tourInstance)
	{
		Check.assumeNotNull(tourInstance, "tourInstance not null");

		I_M_ShipperTransportation shipperTransportation = InterfaceWrapperHelper.newInstance(I_M_ShipperTransportation.class, this);
		shipperTransportation.setDateDoc(tourInstance.getDeliveryDate());
		shipperTransportation.setShipper_BPartner_ID(p_Shipper_BPartner_ID);
		shipperTransportation.setShipper_Location_ID(p_Shipper_Location_ID);
		shipperTransportation.setM_Shipper_ID(p_M_Shipper_ID);
		Services.get(IShipperTransportationBL.class).setC_DocType(shipperTransportation);
		shipperTransportation.setCollectiveBillReport("N"); // FIXME: why the fuck we need to set that?

		// 07958
		// also set the tour id
		shipperTransportation.setM_Tour(tourInstance.getM_Tour());
		InterfaceWrapperHelper.save(shipperTransportation);

		return shipperTransportation;
	}

	private Iterator<I_M_DeliveryDay> retrieveSelectedDeliveryDays()
	{
		final IQueryBuilder<I_M_DeliveryDay> queryBuilder = retrieveSelectedRecordsQueryBuilder(I_M_DeliveryDay.class);
		queryBuilder.orderBy()
				.clear()
				.addColumn(I_M_DeliveryDay.COLUMN_SeqNo);

		return queryBuilder.create()
				.iterate(I_M_DeliveryDay.class);
	}
}
