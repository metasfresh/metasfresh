package org.adempiere.model;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Collection;
import java.util.Properties;

import org.compiere.model.I_M_Package;
import org.compiere.model.Query;

public class MPackageInfo extends X_M_PackageInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4483674486610858756L;

	public MPackageInfo(Properties ctx, int M_PackageInfo_ID, String trxName) {
		super(ctx, M_PackageInfo_ID, trxName);
	}

	public MPackageInfo(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	public static Collection<MPackageInfo> retrieveForPackage(final I_M_Package pack) {

		final String whereClause = COLUMNNAME_M_Package_ID + "=?";
		final Object[] parameters = { pack.getM_Package_ID() };

		Properties ctx = InterfaceWrapperHelper.getCtx(pack);
		String trxName = InterfaceWrapperHelper.getTrxName(pack);
		return new Query(ctx, Table_Name, whereClause, trxName).setParameters(parameters).list();
	}

}
