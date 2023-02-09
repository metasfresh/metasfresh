/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved. *
 * Contributor(s): Victor Perez www.e-evolution.com *
 * Teo Sarca, www.arhipac.ro *
 *****************************************************************************/

package org.eevolution.model.reasoner;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import de.metas.product.ResourceId;
import de.metas.resource.Resource;
import de.metas.resource.ResourceService;
import de.metas.resource.ResourceType;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_S_ResourceUnAvailable;
import org.compiere.model.MResourceUnAvailable;
import org.compiere.model.PO;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @author Teo Sarca, <a href="http://www.arhipac.ro">...</a>
 * @author Cristi Pup, http://www.arhipac.ro
 *         <li>BF [ 2854937 ] CRP calculate wrong DateFinishSchedule
 */
public class CRPReasoner
{
	private final ResourceService resourceService = SpringContextHolder.instance.getBean(ResourceService.class);
	private final IPPOrderDAO ordersRepo = Services.get(IPPOrderDAO.class);

	public Properties getCtx()
	{
		return getCtx(null);
	}

	private Properties getCtx(final Object o)
	{
		if (o instanceof PO)
		{
			return ((PO)o).getCtx();
		}
		else
		{
			return Env.getCtx();
		}
	}

	public Stream<I_PP_Order> streamOpenPPOrderIdsOrderedByDatePromised(final ResourceId plantId)
	{
		return ordersRepo.streamOpenPPOrderIdsOrderedByDatePromised(plantId);
	}

	private ResourceType getResourceType(final Resource r)
	{
		return resourceService.getResourceTypeById(r.getResourceTypeId());
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public final boolean isAvailable(final Resource resource)
	{
		return resourceService.getResourceTypeByResourceId(resource.getResourceId()).isAvailable();
	}
	
	/**
	 * Get Next/Previous Available Date
	 */
	private Instant getAvailableDate(final ResourceType resourceType, final Instant dateTime, final boolean isScheduleBackward)
	{
		final int direction = isScheduleBackward ? -1 : +1;

		Instant date = dateTime;
		int daysAdded = 0;
		if (!resourceType.isDayAvailable(date))
		{
			date = date.plus(direction, ChronoUnit.DAYS);
			daysAdded++;
			if (daysAdded >= 7)
			{
				// TODO shall we return null?
				return date;
			}
		}

		return date;
	}

	/**
	 * @param r resource
	 * @return next available date
	 */
	public Instant getAvailableDate(final Resource r, final Instant dateTime, final boolean isScheduleBackward)
	{
		final ResourceType resourceType = getResourceType(r);

		Instant date = dateTime;
		final ArrayList<Object> params = new ArrayList<>();
		String whereClause;
		final String orderByClause;
		final int direction;
		if (isScheduleBackward)
		{
			whereClause = I_S_ResourceUnAvailable.COLUMNNAME_DateFrom + " <= ?";
			params.add(date);
			orderByClause = I_S_ResourceUnAvailable.COLUMNNAME_DateFrom + " DESC";
			direction = 1;
		}
		else
		{
			whereClause = I_S_ResourceUnAvailable.COLUMNNAME_DateTo + " >= ?";
			params.add(date);
			orderByClause = I_S_ResourceUnAvailable.COLUMNNAME_DateTo;
			direction = -1;
		}

		whereClause += " AND " + I_S_ResourceUnAvailable.COLUMNNAME_S_Resource_ID + "=? AND AD_Client_ID=?";
		params.add(r.getResourceId());
		params.add(ClientId.METASFRESH);

		final POResultSet<MResourceUnAvailable> rs = new Query(getCtx(r), I_S_ResourceUnAvailable.Table_Name, whereClause, null)
				.setOrderBy(orderByClause)
				.setParameters(params)
				.scroll();
		try
		{
			while (rs.hasNext())
			{
				final MResourceUnAvailable rua = rs.next();
				if (rua.isUnAvailable(date))
				{
					date = date.plus(direction, ChronoUnit.DAYS);
				}
				date = getAvailableDate(resourceType, date, isScheduleBackward);
			}
		}
		finally
		{
			DB.close(rs);
		}
		//
		date = getAvailableDate(resourceType, dateTime, isScheduleBackward);
		return date;
	}
}
