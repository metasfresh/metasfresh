/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.util.Check;
import lombok.NonNull;

/**
 * Resource Unavailable
 * 
 * @author Jorg Janke
 * @version $Id: MResourceUnAvailable.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 * 
 * @author Teo Sarca, www.arhipac.ro
 */
public class MResourceUnAvailable extends X_S_ResourceUnAvailable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5532695704071630122L;

	/**
	 * Check if a resource is not available
	 */
	public static boolean isUnAvailable(final int resourceRepoId, @NonNull LocalDateTime dateTime)
	{
		Check.assumeGreaterThanZero(resourceRepoId, "resourceRepoId");

		final LocalDate date = dateTime.toLocalDate();
		final String whereClause = COLUMNNAME_S_Resource_ID + "=?"
				+ " AND TRUNC(" + COLUMNNAME_DateFrom + ") <= ?"
				+ " AND TRUNC(" + COLUMNNAME_DateTo + ") >= ?";
		return new Query(Env.getCtx(), MResourceUnAvailable.Table_Name, whereClause, ITrx.TRXNAME_ThreadInherited)
				.setParameters(new Object[] { resourceRepoId, date, date })
				.anyMatch();
	}

	/**
	 * Standard Constructor
	 * 
	 * @param ctx context
	 * @param S_ResourceUnAvailable_ID id
	 * @param trxName trx
	 */
	public MResourceUnAvailable(Properties ctx, int S_ResourceUnAvailable_ID, String trxName)
	{
		super(ctx, S_ResourceUnAvailable_ID, trxName);
	}	// MResourceUnAvailable

	/**
	 * MResourceUnAvailable
	 * 
	 * @param ctx context
	 * @param rs result set
	 */
	public MResourceUnAvailable(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	// MResourceUnAvailable

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		if (getDateTo() == null)
			setDateTo(getDateFrom());
		if (getDateFrom().after(getDateTo()))
		{
			throw new AdempiereException("@DateTo@ > @DateFrom@");
		}
		return true;
	}	// beforeSave

	/**
	 * Check if the resource is unavailable for date
	 * 
	 * @param date
	 * @return true if valid
	 */
	public boolean isUnAvailable(final LocalDateTime dateTime)
	{
		LocalDate date = dateTime.toLocalDate();
		LocalDate dateFrom = TimeUtil.asLocalDate(getDateFrom());
		LocalDate dateTo = TimeUtil.asLocalDate(getDateTo());

		if (dateFrom != null && date.isBefore(dateFrom))
			return false;
		if (dateTo != null && date.isAfter(dateTo))
			return false;
		return true;
	}

}	// MResourceUnAvailable
