package org.adempiere.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MRelationExplicitv1.SourceOrTarget;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.model.Query;
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
	
	private static List<I_AD_Relation> retrieve(final Properties ctx, final I_AD_RelationType type, final int recordId, final boolean normalDirection, final String trxName)
	{
		final IQueryBuilder<I_AD_Relation> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Relation.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_AD_Relation.COLUMNNAME_AD_RelationType_ID, type.getAD_RelationType_ID())
				//
				.orderBy()
				.addColumn(I_AD_Relation.COLUMNNAME_SeqNo)
				.endOrderBy()
				;

		if (normalDirection)
		{
			queryBuilder.addEqualsFilter(I_AD_Relation.COLUMNNAME_Record_Source_ID, recordId);
		}
		else
		{
			queryBuilder.addEqualsFilter(I_AD_Relation.COLUMNNAME_Record_Target_ID, recordId);
		}
		
		return queryBuilder.create().list(I_AD_Relation.class);
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
		final String sourceTableName = sourcePo.get_TableName();
		final int sourceRecordId = sourcePo.get_ID();

		return retrieveDestinations(ctx, type, sourceTableName, sourceRecordId, trxName);
	}

	public static <T extends PO> List<T> retrieveDestinations(final Properties ctx, final I_AD_RelationType type, final String sourceTableName, final int sourceRecordId, final String trxName)
	{
		if (!type.isExplicit())
		{
			throw new AdempiereException("Param 'type' must be explicit. type=" + type);
		}

		final ILookupDAO lookupDAO = Services.get(ILookupDAO.class);
		final ITableRefInfo refTargetTable = lookupDAO.retrieveTableRefInfo(type.getAD_Reference_Target_ID());
		final ITableRefInfo refSourceTable = lookupDAO.retrieveTableRefInfo(type.getAD_Reference_Source_ID());

		final boolean useNormalDirection = Objects.equals(refSourceTable.getTableName(), sourceTableName);

		if (type.isDirected() && !useNormalDirection)
		{
			// there is noting to retrieve
			return Collections.emptyList();
		}
		final List<T> result = new ArrayList<T>();

		final String destTableName;
		if (useNormalDirection)
		{
			destTableName = refTargetTable.getTableName();
		}
		else
		{
			destTableName = refSourceTable.getTableName();
		}

		final List<I_AD_Relation> records = retrieve(ctx, type, sourceRecordId, useNormalDirection, trxName);

		addToRetrievedDestinations(ctx, useNormalDirection, result, destTableName, records, trxName);

		if(Objects.equals(refSourceTable.getTableName(), refTargetTable.getTableName()))
		{
			records.addAll(retrieve(ctx, type, sourceRecordId, !useNormalDirection, trxName));
			addToRetrievedDestinations(ctx, !useNormalDirection, result, destTableName, records, trxName);
		}

		return result;
	}

	private static <T extends PO> void addToRetrievedDestinations(
			final Properties ctx //
			, final boolean useNormalDirection //
			, final List<T> result //
			, final String destTableName //
			, final List<I_AD_Relation> records //
			, final String trxName //
	)
	{
		for (final I_AD_Relation rel : records)
		{
			final T destinationPO;
			if (useNormalDirection)
			{
				destinationPO = (T)TableModelLoader.instance.getPO(ctx, destTableName, rel.getRecord_Target_ID(), trxName);
			}
			else
			{
				destinationPO = (T)TableModelLoader.instance.getPO(ctx, destTableName, rel.getRecord_Source_ID(), trxName);
			}
			if (destinationPO == null)
			{
				logger.warn("Destination PO with table=" + destTableName
						+ " and Record_ID=" + (useNormalDirection ? rel.getRecord_Target_ID() : rel.getRecord_Source_ID())
						+ " doesn't exist; Deleting " + rel);
				InterfaceWrapperHelper.delete(rel);
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
