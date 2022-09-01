package org.compiere.model;

import de.metas.common.util.time.SystemTime;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.sql.ResultSet;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Properties;

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
	public static boolean isUnAvailable(final int resourceRepoId, @NonNull Instant date)
	{
		Check.assumeGreaterThanZero(resourceRepoId, "resourceRepoId");

		final String whereClause = COLUMNNAME_S_Resource_ID + "=?"
				+ " AND TRUNC(" + COLUMNNAME_DateFrom + ") <= TRUNC(?)"
				+ " AND TRUNC(" + COLUMNNAME_DateTo + ") >= TRUNC(?)";
		return new Query(Env.getCtx(), MResourceUnAvailable.Table_Name, whereClause, ITrx.TRXNAME_ThreadInherited)
				.setParameters(resourceRepoId, date, date)
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
	 * @return true if valid
	 */
	public boolean isUnAvailable(final Instant dateTime)
	{
		final ZoneId zoneId = SystemTime.zoneId();
		LocalDate date = dateTime.atZone(zoneId).toLocalDate();
		LocalDate dateFrom = TimeUtil.asLocalDate(getDateFrom(), zoneId);
		LocalDate dateTo = TimeUtil.asLocalDate(getDateTo(), zoneId);

		if (dateFrom != null && date.isBefore(dateFrom))
			return false;
		if (dateTo != null && date.isAfter(dateTo))
			return false;
		return true;
	}

}	// MResourceUnAvailable
