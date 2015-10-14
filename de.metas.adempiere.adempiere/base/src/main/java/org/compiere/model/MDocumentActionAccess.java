/**
 * 
 */
package org.compiere.model;

/*
 * #%L
 * ADempiere ERP - Base
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
 * @author teo_sarca
 *
 */
public class MDocumentActionAccess extends X_AD_Document_Action_Access
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 542723128079484691L;

	public MDocumentActionAccess(Properties ctx, int ignored, String trxName)
	{
		super(ctx, ignored, trxName);
		if (ignored != 0)
			throw new IllegalArgumentException("Multi-Key");
	}

	public MDocumentActionAccess(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * Parent Constructor
	 * @param parent
	 * @param AD_Role_ID
	 */
	public MDocumentActionAccess(MDocType parent, final I_AD_Role role)
	{
		super (parent.getCtx(), 0, parent.get_TrxName());

		setClientOrgFromModel(role);

		setC_DocType_ID(parent.getC_DocType_ID());
		setAD_Role_ID(role.getAD_Role_ID());
	}
}
