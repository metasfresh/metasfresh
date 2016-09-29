package org.adempiere.model;

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


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.MRelationExplicitv1.SourceOrTarget;
import org.adempiere.util.Check;
import org.compiere.model.MRefTable;
import org.compiere.model.MReference;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

public class MRelation extends X_AD_Relation
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1702404196742737543L;

	private static final Logger logger = LogManager.getLogger(MRelation.class);

	public MRelation(Properties ctx, int AD_Relation_ID, String trxName)
	{
		super(ctx, AD_Relation_ID, trxName);
	}

	public MRelation(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("MRelation[Id=");
		sb.append(get_ID());
		sb.append(", AD_RelationType_ID=");
		sb.append(getAD_RelationType_ID());
		sb.append(", Record_Source_ID=");
		sb.append(getRecord_Source_ID());
		sb.append(", Record_Target_ID=");
		sb.append(getRecord_Target_ID());
		sb.append("]");

		return sb.toString();
	}

	// TODO: Check if sourceId and targetId are elements of the relation defined by type
	public static void add(final Properties ctx, final I_AD_RelationType type, final int sourceId, final int targetId, final String trxName)
	{
		if (!type.isExplicit())
		{
			throw new AdempiereException("Param 'type' must be explicit. type=" + type);
		}

		final MRelation newRel = new MRelation(ctx, 0, trxName);
		newRel.setRecord_Source_ID(sourceId);
		newRel.setRecord_Target_ID(targetId);
		newRel.setAD_RelationType_ID(type.getAD_RelationType_ID());

		newRel.saveEx();
	}

	public static void deleteForPO(final PO po)
	{
		// TODO: optimize
		final Set<Integer> idsToDelete = new HashSet<Integer>();

		for (final MRelationExplicitv1 expRel : MRelationExplicitv1.retrieveFor(po, SourceOrTarget.BOTH))
		{
			if (idsToDelete.add(expRel.getAD_Relation_ID()))
			{
				final MRelation relToDelete = new MRelation(po.getCtx(), expRel.getAD_Relation_ID(), po.get_TrxName());
				relToDelete.deleteEx(false);
			}
		}
	}
	
	private static List<MRelation> retrieve(final Properties ctx, final I_AD_RelationType type, final int recordId, final boolean normalDirection, final String trxName)
	{

		final StringBuilder wc = new StringBuilder();
		wc.append(COLUMNNAME_AD_RelationType_ID + "=? AND ");

		if (normalDirection)
		{
			wc.append(COLUMNNAME_Record_Source_ID + "=?");
		}
		else
		{
			wc.append(COLUMNNAME_Record_Target_ID + "=?");
		}

		return new Query(ctx, Table_Name, wc.toString(), trxName)
				.setParameters(type.getAD_RelationType_ID(), recordId)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.setOrderBy(COLUMNNAME_SeqNo)
				.list();
	}

	/**
	 * 
	 * @param <T>
	 *            The po type to return. Note: As the caller has 'type' and 'sourcePO' specified, the destination POs'
	 *            table is clear. Therefore, the caller also knows the po type. If not, they can still use 'PO' iteself.
	 * @param ctx
	 * @param type
	 * @param sourcePo
	 * @param trxName
	 * @return
	 */
	public static <T extends PO> List<T> retrieveDestinations(final Properties ctx, final I_AD_RelationType type, final PO sourcePo, final String trxName)
	{
		Check.assumeNotNull(sourcePo, "sourcePO not null");
		final int sourceTableId = sourcePo.get_Table_ID();
		final int sourceRecordId = sourcePo.get_ID();

		return retrieveDestinations(ctx, type, sourceTableId, sourceRecordId, trxName);
	}

	public static void mkTransition(
			final Properties ctx,
			final I_AD_RelationType typeAB, final I_AD_RelationType typeBC, final I_AD_RelationType transitionTypeAC,
			final int pivotTableId, final int pivotRecordId,
			final String trxName)
	{
		checkParam(typeAB.isExplicit(),
				"typeAB expected to be explicit; typeAB=" + typeAB);

		checkParam(typeBC.isExplicit(),
				"typeBC expected to be explicit; typeBC=" + typeBC);

		checkParam(transitionTypeAC.isExplicit(),
				"transitionTypeAC expected to be explicit; transitionTypeAC=" + transitionTypeAC);

		checkParam(typeAB.getAD_Reference_Source_ID() == transitionTypeAC.getAD_Reference_Source_ID(),
				"source-references of typeAB and transitionTypeAC expected to be equal; typeAB=" + typeAB + "; transitionTypeAC=" + transitionTypeAC);

		checkParam(typeBC.getAD_Reference_Target_ID() == transitionTypeAC.getAD_Reference_Target_ID(),
				"target-references of typeBC and transitionTypeAC expected to be equal; typeBC=" + typeBC + "; typeBC=" + typeBC);

		final MRefTable targetRefTableTypeAB = MReference.retrieveRefTable(ctx, typeAB.getAD_Reference_Target_ID(), trxName);
		checkParam(targetRefTableTypeAB.getAD_Table_ID() == pivotTableId,
				"AD_Table_ID of typeAB's target-reference expected to be equal to pivotTableId; targetRefTableTypeAB=" + targetRefTableTypeAB + "; pivotTableId=" + pivotTableId);

		final MRefTable sourceRefTableTypeBC = MReference.retrieveRefTable(ctx, typeBC.getAD_Reference_Source_ID(), trxName);
		checkParam(sourceRefTableTypeBC.getAD_Table_ID() == pivotTableId,
				"AD_Table_ID of typeBC's source-reference expected to be equal to pivotTableId; sourceRefTableTypeBC=" + sourceRefTableTypeBC + "; pivotTableId=" + pivotTableId);

		final StringBuilder sqlInsert = new StringBuilder();
		sqlInsert.append(" INSERT INTO AD_Relation (");
		sqlInsert.append(" 	Record_Source_ID, Record_Target_ID, AD_relationType_ID, ");
		sqlInsert.append(" 	AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)");
		sqlInsert.append(" ");
		sqlInsert.append(" SELECT ");
		sqlInsert.append(" 	ab.Record_Source_ID, bc.Record_Target_ID, ?, ");
		sqlInsert.append(" 	?, ?, ?, ?, ?, ?");
		sqlInsert.append(" FROM AD_Relation ab, AD_Relation bc ");
		sqlInsert.append(" WHERE");
		sqlInsert.append(" 	ab.Record_Target_ID=?");
		sqlInsert.append(" 	AND ab.Record_Target_ID=bc.Record_Source_ID");
		sqlInsert.append(" 	AND NOT EXISTS (");
		sqlInsert.append(" 		select 1 ");
		sqlInsert.append(" 		from AD_Relation ac_ex ");
		sqlInsert.append(" 		where ac_ex.AD_relationType_ID=?");
		sqlInsert.append(" 			and ac_ex.Record_Source_ID=ab.Record_Source_ID and ac_ex.Record_Target_ID=bc.Record_Target_ID");
		sqlInsert.append(" 	)");
		sqlInsert.append(" ;");

		Trx.run(trxName, new TrxRunnable()
		{
			@Override
			public void run(String trxName)
			{
				final PreparedStatement pstmt = DB.prepareStatement(sqlInsert.toString(), trxName);

				try
				{
					pstmt.setInt(1, transitionTypeAC.getAD_RelationType_ID()); // AD_relationType_ID
					pstmt.setInt(2, Env.getAD_Client_ID(ctx)); // AD_Client_ID
					pstmt.setInt(3, Env.getAD_Org_ID(ctx)); // AD_Org_ID
					pstmt.setTimestamp(4, new Timestamp(System.currentTimeMillis())); // Created
					pstmt.setInt(5, Env.getAD_User_ID(ctx)); // CreatedBy
					pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis())); // Updated
					pstmt.setInt(7, Env.getAD_User_ID(ctx)); // UpdatedBy
					pstmt.setInt(8, pivotRecordId); // ab.Record_Target_ID
					pstmt.setInt(9, transitionTypeAC.getAD_RelationType_ID()); // ac_ex.AD_relationType_ID

					final int insertCnt = pstmt.executeUpdate();
					logger.info("Inserted " + insertCnt + " new AD_Relation records for relType=" + transitionTypeAC);
				}
				catch (SQLException e)
				{
					throw new DBException(e);
				}
			}
		});
	}

	public static <T> List<T> retrieveDestinations(final Properties ctx, final I_AD_RelationType type, final int sourceTableId, final int sourceRecordId, final String trxName)
	{
		if (!type.isExplicit())
		{
			throw new AdempiereException("Param 'type' must be explicit. type=" + type);
		}

		final MReference refTarget = (MReference)type.getAD_Reference_Target();
		final MRefTable refTargetTable = refTarget.retrieveRefTable();

		final MReference refSource = (MReference)type.getAD_Reference_Source();
		final MRefTable refSourceTable = refSource.retrieveRefTable();

		final boolean useNormalDirection = refSourceTable.getAD_Table_ID() == sourceTableId;

		if (type.isDirected() && !useNormalDirection)
		{
			// there is noting to retrieve
			return Collections.emptyList();
		}
		final List<T> result = new ArrayList<T>();

		final MTable destTable;
		if (useNormalDirection)
		{
			destTable = (MTable)refTargetTable.getAD_Table();
		}
		else
		{
			destTable = (MTable)refSourceTable.getAD_Table();
		}

		final List<MRelation> records = retrieve(ctx, type, sourceRecordId, useNormalDirection, trxName);

		addToRetrievedDestinations(useNormalDirection, result, destTable, records, trxName);

		if (refSourceTable.getAD_Table_ID() == refTargetTable.getAD_Table_ID())
		{
			records.addAll(retrieve(ctx, type, sourceRecordId, !useNormalDirection, trxName));
			addToRetrievedDestinations(!useNormalDirection, result, destTable, records, trxName);
		}

		return result;
	}

	private static <T> void addToRetrievedDestinations(final boolean useNormalDirection, final List<T> result, final MTable destTable, final List<MRelation> records, final String trxName)
	{
		for (final MRelation rel : records)
		{
			final T destinationPO;
			if (useNormalDirection)
			{
				destinationPO = (T)destTable.getPO(rel.getRecord_Target_ID(), trxName);
			}
			else
			{
				destinationPO = (T)destTable.getPO(rel.getRecord_Source_ID(), trxName);
			}
			if (destinationPO == null)
			{
				logger.warn("Destination PO with table=" + destTable
						+ " and Record_ID=" + (useNormalDirection ? rel.getRecord_Target_ID() : rel.getRecord_Source_ID())
						+ " doesn't exist; Deleting " + rel);
				rel.deleteEx(false);
				continue;
			}
			result.add(destinationPO);
		}
	}

	public static void add(final Properties ctx, final String internalName, final int sourceId, final int targetId, final String trxName)
	{
		final MRelationType relType = new Query(ctx, I_AD_RelationType.Table_Name, I_AD_RelationType.COLUMNNAME_InternalName + "=?", trxName)
				.setParameters(internalName)
				.setOnlyActiveRecords(true)
				.first();

		if (relType == null)
		{
			throw new AdempiereException("Missing AD_RelationType with InternalName=" + internalName);
		}

		add(ctx, relType, sourceId, targetId, trxName);
	}

	public static void checkParam(final boolean condition, final String errorMsg)
	{
		if (condition)
		{
			return;
		}
		throw new AdempiereException(errorMsg);
	}

}
