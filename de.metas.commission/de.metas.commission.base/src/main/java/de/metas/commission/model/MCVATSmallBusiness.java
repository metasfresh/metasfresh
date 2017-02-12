package de.metas.commission.model;

/*
 * #%L
 * de.metas.commission.base
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


import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.MiscUtils;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.Query;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.commission.interfaces.I_C_BPartner;

/**
 * 
 * @author ts
 * 
 * @see "<a href='http://dewiki908/mediawiki/index.php/Kleinunternehmer-Regel_%282009_0023_G143%29'>(2009 0023 G143)</a>"
 */
public class MCVATSmallBusiness extends X_C_VAT_SmallBusiness
{

	public final static Timestamp DEFAULT_VALID_FROM = MiscUtils.toTimeStamp("2000-01-01");
	public final static Timestamp DEFAULT_VALID_TO = MiscUtils.toTimeStamp("9999-12-31");

	/**
	 * 
	 */
	private static final long serialVersionUID = -7510796397329345805L;

	public MCVATSmallBusiness(final Properties ctx, final int C_VAT_SmallBusiness_ID, final String trxName)
	{
		super(ctx, C_VAT_SmallBusiness_ID, trxName);

		if (C_VAT_SmallBusiness_ID <= 0)
		{
			setValidFrom(MCVATSmallBusiness.DEFAULT_VALID_FROM);
			setValidTo(MCVATSmallBusiness.DEFAULT_VALID_TO);
		}
	}

	public MCVATSmallBusiness(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public static boolean retrieveIsTaxExempt(final I_C_BPartner bPartner, final Timestamp date)
	{

		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final int bPartnerId = bPartner.getC_BPartner_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		return retrieveIsTaxExempt(ctx, bPartnerId, date, trxName);

	}

	@Cached
	public static boolean retrieveIsTaxExempt(
			final @CacheCtx Properties ctx,
			final int bPartnerId,
			final Timestamp date,
			final @CacheIgnore String trxName)
	{
		final String whereClause =
				I_C_VAT_SmallBusiness.COLUMNNAME_C_BPartner_ID + "=? AND "
						+ I_C_VAT_SmallBusiness.COLUMNNAME_ValidFrom + "<=? AND "
						+ I_C_VAT_SmallBusiness.COLUMNNAME_ValidTo + ">=?";

		final Object[] parameters = { bPartnerId, date, date };

		final int id = new Query(ctx, I_C_VAT_SmallBusiness.Table_Name, whereClause, trxName)
				.setParameters(parameters).setOnlyActiveRecords(true).firstId();

		return id > 0;
	}
}
