package de.metas.document.refid.api.impl;

import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.spi.IReferenceNoGenerator;
import de.metas.security.permissions.Access;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.getTableName;
import static org.adempiere.model.InterfaceWrapperHelper.getTrxName;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public abstract class AbstractReferenceNoDAO implements IReferenceNoDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@Override
	// @Cached(cacheName = I_C_ReferenceNo_Type.Table_Name + "_ForClient")
	public final List<I_C_ReferenceNo_Type> retrieveReferenceNoTypes()
	{
		final List<I_C_ReferenceNo_Type> result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ReferenceNo_Type.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_C_ReferenceNo_Type.COLUMNNAME_C_ReferenceNo_Type_ID)
				.create()
				.list();
		return result;
	}

	@Override
	public final I_C_ReferenceNo_Type retrieveRefNoTypeByClass(
			final Properties ctx,
			@NonNull final Class<? extends IReferenceNoGenerator> clazz)
	{
		final ArrayList<I_C_ReferenceNo_Type> typesWithClass = new ArrayList<I_C_ReferenceNo_Type>();
		for (final I_C_ReferenceNo_Type type : retrieveReferenceNoTypes())
		{
			if (clazz.getName().equals(type.getClassname()))
			{
				typesWithClass.add(type);
			}
		}
		if (typesWithClass.isEmpty())
		{
			throw new AdempiereException("@Missing@: @C_ReferenceNo_Type@ with classname " + clazz.getName());
		}
		if (typesWithClass.size() > 1)
		{
			throw new DBMoreThanOneRecordsFoundException("@C_ReferenceNo_Type@ with classname " + clazz.getName());
		}
		return typesWithClass.get(0);
	}

	@Override
	public final I_C_ReferenceNo getCreateReferenceNo(
			@NonNull final I_C_ReferenceNo_Type type,
			@NonNull final String referenceNo,
			@NonNull final IContextAware ctxAware)
	{
		I_C_ReferenceNo reference = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ReferenceNo.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID, type.getC_ReferenceNo_Type_ID())
				.addEqualsFilter(I_C_ReferenceNo.COLUMNNAME_ReferenceNo, referenceNo)
				.create()
				.setRequiredAccess(Access.WRITE)
				.firstOnly(I_C_ReferenceNo.class); // there is a UC on C_ReferenceNo_Type_ID and ReferenceNo

		if (reference == null)
		{
			reference = newInstance(I_C_ReferenceNo.class, ctxAware);
			reference.setC_ReferenceNo_Type(type);
			reference.setReferenceNo(referenceNo);
		}

		reference.setIsActive(true);

		// Don't save because we let this pleasure to getCreateReferenceNoDoc method.
		// We do this for optimization: in this way getCreateReferenceNoDoc will know that is a new reference and don't need to search for assignments
		// InterfaceWrapperHelper.save(reference);

		return reference;
	}

	@Override
	public final I_C_ReferenceNo_Type retrieveRefNoTypeByName(@NonNull final String typeName)
	{

		final ArrayList<I_C_ReferenceNo_Type> typesWithName = new ArrayList<I_C_ReferenceNo_Type>();
		for (final I_C_ReferenceNo_Type type : retrieveReferenceNoTypes())
		{
			if (typeName.equals(type.getName()))
			{
				typesWithName.add(type);
			}
		}
		if (typesWithName.isEmpty())
		{
			throw new AdempiereException("@Missing@: @C_ReferenceNo_Type@ with name " + typeName);
		}
		if (typesWithName.size() > 1)
		{
			throw new DBMoreThanOneRecordsFoundException("@C_ReferenceNo_Type@ with name " + typeName);
		}
		return typesWithName.get(0);
	}

	@Override
	public final <T> List<T> retrieveAssociatedRecords(
			@NonNull final Object model,
			@NonNull final Class<? extends IReferenceNoGenerator> generatorClazz,
			@NonNull final Class<T> clazz)
	{
		final Properties ctx = getCtx(model);
		final String trxName = getTrxName(model);

		final I_C_ReferenceNo_Type type = retrieveRefNoTypeByClass(ctx, generatorClazz);

		final List<I_C_ReferenceNo> referenceNos = retrieveReferenceNos(model, type);

		final String tableName = getTableName(clazz);

		final List<T> result = new ArrayList<T>();

		for (final I_C_ReferenceNo referenceNo : referenceNos)
		{
			final List<I_C_ReferenceNo_Doc> refNoDocs = retrieveRefNoDocByRefNoAndTableName(referenceNo, tableName);

			for (final I_C_ReferenceNo_Doc refNoDoc : refNoDocs)
			{
				final T referencedDoc = create(ctx, refNoDoc.getRecord_ID(), clazz, trxName);
				result.add(referencedDoc);
			}
		}
		return result;
	}

	protected abstract List<I_C_ReferenceNo_Doc> retrieveRefNoDocByRefNoAndTableName(I_C_ReferenceNo referenceNo, String tableName);

	@Override
	public final List<I_C_ReferenceNo_Doc> retrieveDocAssignments(@NonNull final I_C_ReferenceNo referenceNo)
	{
		return retrieveDocAssignments(ReferenceNoId.ofRepoId(referenceNo.getC_ReferenceNo_ID()));
	}

	@Override
	public final List<I_C_ReferenceNo_Doc> retrieveDocAssignments(@NonNull final ReferenceNoId referenceNoId)
	{
		return Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_C_ReferenceNo_Doc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID, referenceNoId)
				.orderBy(I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_Doc_ID)
				.create()
				.setRequiredAccess(Access.READ)
				.list();
	}

	@Override
	public final I_C_ReferenceNo_Doc getCreateReferenceNoDoc(
			@NonNull final I_C_ReferenceNo referenceNo,
			@NonNull final ITableRecordReference referencedModel)
	{
		boolean isNewReference = false;
		if (referenceNo.getC_ReferenceNo_ID() <= 0)
		{
			save(referenceNo);
			isNewReference = true;
		}

		I_C_ReferenceNo_Doc assignment = null;

		//
		// Search for an already existing assignment, only if the reference was not new
		if (!isNewReference)
		{
			assignment = Services
					.get(IQueryBL.class)
					.createQueryBuilder(I_C_ReferenceNo_Doc.class, referenceNo)
					// .addOnlyActiveRecordsFilter() the old code didn't have an active-check; not sure why, but might be a feature
					.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID, referenceNo.getC_ReferenceNo_ID())
					.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_AD_Table_ID, referencedModel.getAD_Table_ID())
					.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_Record_ID, referencedModel.getRecord_ID())
					.create()
					.firstOnly(I_C_ReferenceNo_Doc.class);
		}

		//
		// Creating new referenceNo assignment
		if (assignment == null)
		{
			assignment = newInstance(I_C_ReferenceNo_Doc.class, referenceNo);
			assignment.setC_ReferenceNo(referenceNo);
			assignment.setAD_Table_ID(referencedModel.getAD_Table_ID());
			assignment.setRecord_ID(referencedModel.getRecord_ID());
		}

		assignment.setIsActive(true);
		save(assignment);

		return assignment;
	}

	@Override
	@NonNull
	public Optional<ReferenceNoId> getReferenceNoId(
			@NonNull final String referenceNo,
			@NonNull final I_C_ReferenceNo_Type invoiceReferenceNoType)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ReferenceNo.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ReferenceNo.COLUMNNAME_ReferenceNo, referenceNo)
				.addEqualsFilter(I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID, invoiceReferenceNoType.getC_ReferenceNo_Type_ID())
				.create()
				.firstOnlyOptional(I_C_ReferenceNo.class)    // there is a UC on C_ReferenceNo_Type_ID and ReferenceNo
				.map(I_C_ReferenceNo::getC_ReferenceNo_ID)
				.map(ReferenceNoId::ofRepoId);
	}

	@Override
	public Optional<I_C_ReferenceNo> retrieveRefNo(@NonNull final TableRecordReference tableRecordReference, @NonNull final I_C_ReferenceNo_Type type)
	{
		final IQuery<I_C_ReferenceNo_Doc> referenceDocSubQuery = queryBL.createQueryBuilder(I_C_ReferenceNo_Doc.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.create();

		return queryBL.createQueryBuilder(I_C_ReferenceNo.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_Type_ID, type.getC_ReferenceNo_Type_ID())
				.addInSubQueryFilter(I_C_ReferenceNo.COLUMNNAME_C_ReferenceNo_ID,
									 I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID,
									 referenceDocSubQuery)
				.create()
				.firstOnlyOptional(I_C_ReferenceNo.class);
	}
}
