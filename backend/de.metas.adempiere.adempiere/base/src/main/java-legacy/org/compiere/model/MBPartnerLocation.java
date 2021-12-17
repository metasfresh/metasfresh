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

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;

import java.sql.ResultSet;
import java.util.Properties;

/**
 * Partner Location Model
 *
 * @author Jorg Janke
 * @version $Id: MBPartnerLocation.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MBPartnerLocation extends X_C_BPartner_Location
{
	public MBPartnerLocation(final Properties ctx, final int C_BPartner_Location_ID, final String trxName)
	{
		super(ctx, C_BPartner_Location_ID, trxName);
		if (is_new())
		{
			setName(".");
			//
			setIsShipTo(true);
			setIsRemitTo(true);
			setIsPayFrom(true);
			setIsBillTo(true);
		}
	}

	@Deprecated
	public MBPartnerLocation(final I_C_BPartner bp)
	{
		this(InterfaceWrapperHelper.getCtx(bp),
				0,
				InterfaceWrapperHelper.getTrxName(bp));
		setClientOrg(InterfaceWrapperHelper.getPO(bp));
		// may (still) be 0
		set_ValueNoCheck("C_BPartner_ID", bp.getC_BPartner_ID());
	}

	public MBPartnerLocation(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		return "MBPartnerLocation[ID=" + get_ID()
				+ ",C_Location_ID=" + getC_Location_ID()
				+ ",Name=" + getName()
				+ "]";
	}

	@Override
	protected boolean beforeSave(final boolean newRecord)
	{
		if (getC_Location_ID() <= 0)
		{
			throw new FillMandatoryException(COLUMNNAME_C_Location_ID);
		}

		return true;
	}

	

}
