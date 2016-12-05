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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_AD_RelationType;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Relation_Explicit_v1;

public class MRelationExplicitv1 extends X_AD_Relation_Explicit_v1
{
	private static final long serialVersionUID = 1L;

	public enum SourceOrTarget
	{
		SOURCE, TARGET, BOTH
	};

	public MRelationExplicitv1(Properties ctx, int AD_Relation_Explicit_v1_ID, String trxName)
	{
		super(ctx, AD_Relation_Explicit_v1_ID, trxName);
	}

	public MRelationExplicitv1(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * 
	 * @param po
	 * @param sourceAndTarget
	 *            if false, only those records are returned, that have the given po as their source (unless they have an
	 *            undirected relation type)
	 * @return
	 */
	public static List<MRelationExplicitv1> retrieveFor(final PO po, final SourceOrTarget sourceOrTarget)
	{
		return retrieveFor(po, null, sourceOrTarget);
	}

	public static List<MRelationExplicitv1> retrieveFor(final PO po, final I_AD_RelationType relType, final SourceOrTarget sourceOrTarget)
	{
		final StringBuilder wc = new StringBuilder();
		final List<Object> params = new ArrayList<Object>();

		// these two params are used in any case
		params.add(po.get_Table_ID());
		params.add(po.get_ID());

		switch (sourceOrTarget)
		{
			case SOURCE:
				wc.append(COLUMNNAME_AD_Table_Source_ID + "=? AND " + COLUMNNAME_Record_Source_ID + "=?");
				break;
			case TARGET:
				wc.append(COLUMNNAME_AD_Table_Target_ID + "=? AND " + COLUMNNAME_Record_Target_ID + "=?");
				break;
			case BOTH:
				wc.append("(" + COLUMNNAME_AD_Table_Source_ID + "=? AND " + COLUMNNAME_Record_Source_ID + "=?)");
				wc.append("OR ");
				wc.append("(" + COLUMNNAME_AD_Table_Target_ID + "=? AND " + COLUMNNAME_Record_Target_ID + "=?)");
				params.add(po.get_Table_ID());
				params.add(po.get_ID());
				break;
		}
	

		if (relType != null && relType.getAD_RelationType_ID() > 0)
		{
			wc.append(" AND " + COLUMNNAME_AD_Relation_ID + "=?");
			params.add(relType.getAD_RelationType_ID());
		}
		return new Query(po.getCtx(), Table_Name, wc.toString(), po.get_TrxName())
				.setParameters(params)
				.setClient_ID()
				.list();
	}
}
