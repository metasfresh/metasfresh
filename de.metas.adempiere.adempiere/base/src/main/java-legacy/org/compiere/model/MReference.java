/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2009 www.metas.de                                            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.util.Services;
import org.compiere.util.DisplayType;

/**
 * Note: maybe what you are looking for is here: {@link DisplayType}.
 *
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 *
 */
public class MReference extends X_AD_Reference {

	/**
	 *
	 */
	private static final long serialVersionUID = 3298182955450711914L;

	public MReference(Properties ctx, int AD_Reference_ID, String trxName) {
		super(ctx, AD_Reference_ID, trxName);
	}

	public MReference(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);
	}

	/**
	 * @param ctx
	 * @param referenceId
	 * @param trxName
	 * @return MRefTable
	 * @deprecated Please consider using {@link ILookupDAO#retrieveTableRefInfo(int)}
	 */
	@Deprecated
	public static I_AD_Ref_Table retrieveRefTable(final Properties ctx, final int referenceId, final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Ref_Table.class, ctx, trxName)
				.addEqualsFilter(I_AD_Ref_Table.COLUMNNAME_AD_Reference_ID, referenceId)
				.create()
				.firstOnly(I_AD_Ref_Table.class);
	}

}
