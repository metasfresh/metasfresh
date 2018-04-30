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
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.adempiere.util.time.SystemTime;
import org.compiere.util.DisplayType;

/**
 * Price List Version Model
 *
 * @author Jorg Janke
 * @version $Id: MPriceListVersion.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
@Deprecated
public class MPriceListVersion extends X_M_PriceList_Version
{
	private static final long serialVersionUID = -3607494586575155059L;

	public MPriceListVersion(final Properties ctx, final int M_PriceList_Version_ID, final String trxName)
	{
		super(ctx, M_PriceList_Version_ID, trxName);
	}

	public MPriceListVersion(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getValidFrom() == null)
		{
			setValidFrom(SystemTime.asDayTimestamp());
		}

		if (getName() == null)
		{
			final SimpleDateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
			final String name = dateFormat.format(getValidFrom());
			setName(name);
		}

		return true;
	}

}	// MPriceListVersion
