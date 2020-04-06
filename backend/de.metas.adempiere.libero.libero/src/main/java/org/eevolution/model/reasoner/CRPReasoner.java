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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Stream;

import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceUnAvailable;
import org.compiere.model.MResourceUnAvailable;
import org.compiere.model.PO;
import org.compiere.model.POResultSet;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.model.I_PP_Order;

import de.metas.material.planning.IResourceDAO;
import de.metas.material.planning.ResourceType;
import de.metas.product.ResourceId;
import de.metas.util.Services;

/**
 * @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
 * @version 1.0, October 14th 2005
 *
 * @author Teo Sarca, http://www.arhipac.ro
 * @author Cristi Pup, http://www.arhipac.ro
 *         <li>BF [ 2854937 ] CRP calculate wrong DateFinishSchedule
 *         https://sourceforge.net/tracker/?func=detail&atid=934929&aid=2854937&group_id=176962
 */
public class CRPReasoner
{
	protected final IResourceDAO resourcesRepo = Services.get(IResourceDAO.class);
	protected final IPPOrderDAO ordersRepo = Services.get(IPPOrderDAO.class);

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

	protected final ResourceType getResourceType(final I_S_Resource r)
	{
		final ResourceId resourceId = ResourceId.ofRepoId(r.getS_Resource_ID());
		return getResourceType(resourceId);
	}

	protected final ResourceType getResourceType(final ResourceId resourceId)
	{
		return resourcesRepo.getResourceTypeByResourceId(resourceId);
	}

	protected final boolean isAvailable(final ResourceId resourceId, final LocalDateTime dateTime)
	{
		final ResourceType resourceType = getResourceType(resourceId);
		return resourceType.isDayAvailable(TimeUtil.asLocalDate(dateTime)) && !MResourceUnAvailable.isUnAvailable(resourceId.getRepoId(), dateTime);
	}

	protected final boolean isAvailable(final I_S_Resource resource, final LocalDateTime dateTime)
	{
		final ResourceType resourceType = getResourceType(resource);
		return resourceType.isDayAvailable(TimeUtil.asLocalDate(dateTime)) && !MResourceUnAvailable.isUnAvailable(resource.getS_Resource_ID(), dateTime);
	}

	public final boolean isAvailable(final I_S_Resource resource)
	{
		return getResourceType(resource).isAvailable();
	}

	/**
	 * Get Next/Previous Available Date
	 *
	 * @param resourceType
	 * @param dateTime
	 * @param isScheduleBackward
	 * @return
	 */
	private LocalDateTime getAvailableDate(final ResourceType resourceType, final LocalDateTime dateTime, final boolean isScheduleBackward)
	{
		final int direction = isScheduleBackward ? -1 : +1;

		LocalDateTime date = dateTime;
		int daysAdded = 0;
		if (!resourceType.isDayAvailable(date.toLocalDate()))
		{
			date = date.plusDays(1 * direction);
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
	 * @param dateTime
	 * @return next available date
	 */
	public LocalDateTime getAvailableDate(final I_S_Resource r, final LocalDateTime dateTime, final boolean isScheduleBackward)
	{
		final ResourceType resourceType = getResourceType(r);

		LocalDateTime date = dateTime;
		final ArrayList<Object> params = new ArrayList<>();
		String whereClause;
		String orderByClause;
		int direction;
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
		params.add(r.getS_Resource_ID());
		params.add(r.getAD_Client_ID());

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
					date = date.plusDays(1 * direction);
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
