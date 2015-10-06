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
import java.util.Properties;

public class MPackagingContainer extends X_M_PackagingContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8723030897145824459L;

	public MPackagingContainer(Properties ctx, int M_PackagingContainer_ID, String trxName) {
		super(ctx, M_PackagingContainer_ID, trxName);
	}

	public MPackagingContainer(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	@Override
	public String toString() {

		final StringBuffer sb = new StringBuffer("MPackagingContainer[") //
				.append(get_ID()) //
				.append(", Name=").append(getName()) //
				.append(", M_Product_ID=").append(getM_Product_ID()) //
				.append("]");
		return sb.toString();
	}
	
}
