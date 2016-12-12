package org.adempiere.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.MRelationExplicitv1.SourceOrTarget;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

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

	private static List<I_AD_Relation> retrieve(final Properties ctx, final int AD_RelationType_ID, final int recordId, final boolean normalDirection, final String trxName)
	{
		final IQueryBuilder<I_AD_Relation> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Relation.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(I_AD_Relation.COLUMNNAME_AD_RelationType_ID, AD_RelationType_ID)
				//
				.orderBy()
				.addColumn(I_AD_Relation.COLUMNNAME_SeqNo)
				.endOrderBy();

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
	public static <T> List<T> retrieveDestinations(
			final Properties ctx //
			, final RelationTypeZoomProvider relationType //
			, final PO sourcePo //
			, final Class<T> targetClass, final String trxName //
	)
	{
		Check.assumeNotNull(sourcePo, "sourcePO not null");
		final String sourceTableName = sourcePo.get_TableName();
		final int sourceRecordId = sourcePo.get_ID();

		return retrieveDestinations(ctx, relationType, sourceTableName, sourceRecordId, targetClass, trxName);
	}

	public static <T> List<T> retrieveDestinations(
			final Properties ctx //
			, final RelationTypeZoomProvider relationType //
			, final String sourceTableName, final int sourceRecordId //
			, final Class<T> targetClass //
			, final String trxName //
	)
	{
		if (!relationType.isExplicit())
		{
			throw new AdempiereException("Param 'type' must be explicit. type=" + relationType);
		}

		final String relationSourceTableName = relationType.getSourceTableName();
		final String relationTargetTableName = relationType.getTargetTableName();

		final boolean useNormalDirection = Objects.equals(relationSourceTableName, sourceTableName);

		if (relationType.isDirected() && !useNormalDirection)
		{
			// there is noting to retrieve
			return ImmutableList.of();
		}
		final List<T> result = new ArrayList<T>();

		String destTableName = InterfaceWrapperHelper.getTableNameOrNull(targetClass);
		if (useNormalDirection)
		{
			if (destTableName == null)
			{
				destTableName = relationTargetTableName;
			}
			else
			{
				Check.assume(destTableName.equals(relationTargetTableName), "Target class {} shall match {}", targetClass, relationTargetTableName);
			}
		}
		else
		{
			if (destTableName == null)
			{
				destTableName = relationSourceTableName;
			}
			else
			{
				Check.assume(destTableName.equals(relationSourceTableName), "Target class {} shall match {}", targetClass, relationSourceTableName);
			}
		}

		final List<I_AD_Relation> records = retrieve(ctx, relationType.getAD_RelationType_ID(), sourceRecordId, useNormalDirection, trxName);

		addToRetrievedDestinations(ctx, useNormalDirection, result, destTableName, targetClass, records, trxName);

		if (Objects.equals(relationSourceTableName, relationTargetTableName))
		{
			records.addAll(retrieve(ctx, relationType.getAD_RelationType_ID(), sourceRecordId, !useNormalDirection, trxName));
			addToRetrievedDestinations(ctx, !useNormalDirection, result, destTableName, targetClass, records, trxName);
		}

		return result;
	}

	private static <T> void addToRetrievedDestinations(
			final Properties ctx //
			, final boolean useNormalDirection //
			, final List<T> result //
			, final String targetTableName //
			, final Class<T> targetClass //
			, final List<I_AD_Relation> records //
			, final String trxName //
	)
	{
		for (final I_AD_Relation rel : records)
		{
			final T destinationPO;
			if (useNormalDirection)
			{
				destinationPO = InterfaceWrapperHelper.create(ctx, targetTableName, rel.getRecord_Target_ID(), targetClass, trxName);
			}
			else
			{
				destinationPO = InterfaceWrapperHelper.create(ctx, targetTableName, rel.getRecord_Source_ID(), targetClass, trxName);
			}
			if (destinationPO == null)
			{
				logger.warn("Destination PO with " + targetClass
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
