/******************************************************************************
 * Product: ADempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 2009 www.metas.de *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

/**
 * Formal definition for a set of data record pairs
 *
 * @author Tobias Schoeneberg, www.metas.de - FR [ 2897194 ] Advanced Zoom and RelationTypes
 */
public class MRelationType extends X_AD_RelationType
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5486148151201672913L;

	public MRelationType(Properties ctx, int AD_RelationType_ID, String trxName)
	{
		super(ctx, AD_RelationType_ID, trxName);
	}

	public MRelationType(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

//	/**
//	 *
//	 * @param po for non-directed relation types both the <code>po</code> and <code>AD_Window_ID</code> are used to decide if we need to return the <code>AD_Reference_ID</code> or the relation type's source reference or of its target reference.
//	 * @param AD_Window_ID
//	 * @return
//	 */
//	@Deprecated
//	private int findDestinationRefId(final PO po, final int windowId)	{
//		final IZoomSource source = POZoomSource.of(po, windowId);
//		return findDestinationRefId(source);
//	}

//	@Deprecated
//	private MQuery mkQuery(final PO po, final ITableRefInfo refTable)
//	{
//		final IZoomSource source = POZoomSource.of(po);
//		return mkQuery(source, refTable);
//	}

	public static I_AD_RelationType retrieveForInternalName(final Properties ctx, final String internalName, final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_RelationType.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_RelationType.COLUMNNAME_InternalName, internalName)
				.create()
				.firstOnly(I_AD_RelationType.class);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MRelationType[");
		//
		sb.append(get_ID());
		sb.append(", InternalName=").append(getInternalName());
		sb.append(", Directed=").append(isDirected());

		sb.append(", AD_Reference_Source_ID=").append(getAD_Reference_Source_ID());
		sb.append(", Role_Source=").append(getRole_Source());

		sb.append(", AD_Reference_Target_ID=").append(getAD_Reference_Target_ID());
		sb.append(", Role_Target=").append(getRole_Target()); //

		sb.append("]");

		return sb.toString();
	}

//	/**
//	 *
//	 * @param <T>
//	 *            The po type to return. Note: As the caller has 'type' and 'sourcePO' specified, the destination POs'
//	 *            table is clear. Therefore, the caller also knows the po type. If not, they can still use 'PO' iteself.
//	 * @param sourcePO
//	 * @return
//	 */
//	private <T extends PO> List<T> retrieveDestinations(final PO sourcePO)
//	{
//		if (this.isExplicit())
//		{
//			return MRelation.retrieveDestinations(sourcePO.getCtx(), this, sourcePO, sourcePO.get_TrxName());
//		}
//		final int destinationRefId = findDestinationRefId(sourcePO, -1);
//		final ITableRefInfo destinationRefTable = Services.get(ILookupDAO.class).retrieveTableRefInfo(destinationRefId);
//
//		final MQuery query = mkQuery(sourcePO, destinationRefTable);
//
//		return new Query(sourcePO.getCtx(), query.getZoomTableName(), query.getWhereClause(false), sourcePO.get_TrxName())
//				.setClient_ID()
//				.setOnlyActiveRecords(true)
//				.setOrderBy(query.getZoomColumnName())
//				.list();
//	}
//
//	public <T> List<T> retrieveDestinations(final PO sourcePO, final Class<T> clazz)
//	{
//		final List<PO> list = retrieveDestinations(sourcePO);
//		final List<T> result = new ArrayList<T>(list.size());
//		for (PO po : list)
//		{
//			T o = InterfaceWrapperHelper.create(po, clazz);
//			result.add(o);
//		}
//
//		return result;
//	}
}
