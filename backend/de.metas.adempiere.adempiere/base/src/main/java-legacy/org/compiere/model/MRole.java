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
import java.util.Properties;

/**
 * Role Model.
 * Includes AD_User runtime info for Personal Access
 * The class is final, so that you cannot overwrite the security rules.
 *
 * @author Jorg Janke
 * @author Karsten Thiemann FR [ 1782412 ]
 * @author Carlos Ruiz - globalqss - FR [ 1846929 ] - implement ASP
 * @version $Id: MRole.java,v 1.5 2006/08/09 16:38:47 jjanke Exp $
 * 
 * @deprecated Scheduled to be removed. But before deleting it, pls check if we need the "setting default logic" from MRole(ctx, AD_Role_ID, trxName)
 */
@Deprecated
public final class MRole extends X_AD_Role
{
	/**
	 *
	 */
	private static final long serialVersionUID = -6722616714353225616L;

	@SuppressWarnings("unused")
	public MRole(final Properties ctx, final int AD_Role_ID, final String trxName)
	{
		super(ctx, AD_Role_ID, trxName);
		if (is_new())
		{
			// setName (null);
			setIsCanExport(true);
			setIsCanReport(true);
			setIsManual(false);
			setIsPersonalAccess(false);
			setIsPersonalLock(false);
			setIsShowAcct(false);
			setIsAccessAllOrgs(false);
			setUserLevel(USERLEVEL_Organization);
			setPreferenceType(PREFERENCETYPE_Organization);
			setIsChangeLog(false);
			setOverwritePriceLimit(false);
			setIsUseUserOrgAccess(false);
			setMaxQueryRecords(0);
			setConfirmQueryRecords(0);
		}
	}

	@SuppressWarnings("unused")
	public MRole(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}
}	// MRole
