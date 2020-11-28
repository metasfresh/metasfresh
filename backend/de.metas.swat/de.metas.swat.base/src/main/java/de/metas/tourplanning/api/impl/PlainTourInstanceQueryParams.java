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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Date;

import de.metas.tourplanning.api.ITourInstanceBL;
import de.metas.tourplanning.api.ITourInstanceQueryParams;
import de.metas.tourplanning.model.I_M_Tour;

/**
 * Plain implementation of {@link ITourInstanceQueryParams}.
 * 
 * NOTE for developers which will extend this class: please make sure that after instantion no default values are set for parameters because there is business logic which is rellying on this when it's
 * trying to match only some parts of the tour instance (e.g. see {@link ITourInstanceBL#isGenericTourInstance(de.metas.tourplanning.model.I_M_Tour_Instance)}.
 * 
 * 
 * @author tsa
 *
 */
public final class PlainTourInstanceQueryParams implements ITourInstanceQueryParams
{
	// NOTE: please make sure everything is set to NULL (i.e. no default values for our parameters)
	private I_M_Tour tour = null;
	private Date deliveryDate = null;
	private Boolean processed = null;
	private Boolean genericTourInstance = null;
	private int shipperTransportationId = -1;

	@Override
	public String toString()
	{
		return "PlainTourInstanceQueryParams ["
				+ "tour=" + tour
				+ ", deliveryDate=" + deliveryDate
				+ ", processed=" + processed
				+ ", genericTourInstance=" + genericTourInstance
				+ ", shipperTransportationId=" + shipperTransportationId
				+ "]";
	}

	@Override
	public I_M_Tour getM_Tour()
	{
		return tour;
	}

	@Override
	public void setM_Tour(I_M_Tour tour)
	{
		this.tour = tour;
	}

	@Override
	public Date getDeliveryDate()
	{
		return deliveryDate;
	}

	@Override
	public void setDeliveryDate(final Date deliveryDate)
	{
		this.deliveryDate = deliveryDate;
	}

	@Override
	public Boolean getProcessed()
	{
		return processed;
	}

	@Override
	public void setProcessed(Boolean processed)
	{
		this.processed = processed;
	}

	@Override
	public Boolean getGenericTourInstance()
	{
		return genericTourInstance;
	}

	@Override
	public void setGenericTourInstance(Boolean genericTourInstance)
	{
		this.genericTourInstance = genericTourInstance;
	}

	@Override
	public int getM_ShipperTransportation_ID()
	{
		return shipperTransportationId;
	}

	@Override
	public void setM_ShipperTransportation_ID(final int shipperTransportationId)
	{
		this.shipperTransportationId = shipperTransportationId;
	}

}
