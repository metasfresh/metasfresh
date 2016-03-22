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
import java.util.Properties;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Sponsor_%282009_0027_G8%29'>(2009 0027 G8)</a>"
 */
public class MCSponsor extends X_C_Sponsor
{
	// private static final Logger logger = CLogMgt.getLogger(MCSponsor.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 6744258019885754203L;

	public MCSponsor(final Properties ctx, final int C_Sponsor_ID, final String trxName)
	{
		super(ctx, C_Sponsor_ID, trxName);
	}

	public MCSponsor(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{

		final StringBuilder sb = new StringBuilder();
		sb.append("MCSponsor[Id=");
		sb.append(getC_Sponsor_ID());
		sb.append(", SponsorNo=");
		sb.append(getSponsorNo());
		sb.append("]");

		return sb.toString();
	}
}
