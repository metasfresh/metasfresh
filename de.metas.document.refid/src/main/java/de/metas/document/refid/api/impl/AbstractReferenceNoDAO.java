package de.metas.document.refid.api.impl;

/*
 * #%L
 * de.metas.document.refid
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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThenOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.document.refid.api.IReferenceNoDAO;
import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.document.refid.spi.IReferenceNoGenerator;
import de.metas.util.Check;

public abstract class AbstractReferenceNoDAO implements IReferenceNoDAO
{
	@Override
	public final  I_C_ReferenceNo_Type retrieveRefNoTypeByClass(final Properties ctx, final Class<? extends IReferenceNoGenerator> clazz)
	{
		Check.assumeNotNull(clazz, "Param 'clazz' not null");

		final ArrayList<I_C_ReferenceNo_Type> typesWithClass = new ArrayList<I_C_ReferenceNo_Type>();
		for (final I_C_ReferenceNo_Type type : retrieveReferenceNoTypes(ctx))
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
			throw new DBMoreThenOneRecordsFoundException("@C_ReferenceNo_Type@ with classname " + clazz.getName());
		}
		return typesWithClass.get(0);
	}

	@Override
	public final I_C_ReferenceNo_Type retrieveRefNoTypeByName(final Properties ctx, final String typeName)
	{
		Check.assumeNotNull(typeName, "Param 'typeName' not null");

		final ArrayList<I_C_ReferenceNo_Type> typesWithName = new ArrayList<I_C_ReferenceNo_Type>();
		for (final I_C_ReferenceNo_Type type : retrieveReferenceNoTypes(ctx))
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
			throw new DBMoreThenOneRecordsFoundException("@C_ReferenceNo_Type@ with name " + typeName);
		}
		return typesWithName.get(0);
	}

	@Override
	public final void removeDocAssignments(List<I_C_ReferenceNo_Doc> docAssignments)
	{
		for (I_C_ReferenceNo_Doc da : docAssignments)
		{
			InterfaceWrapperHelper.delete(da);
		}
	}

	@Override
	public final <T> List<T> retrieveAssociatedRecords(final Object model, final Class<? extends IReferenceNoGenerator> generatorClazz, final Class<T> clazz)
	{
		Check.assumeNotNull(model, "Param 'model' not null");
		Check.assumeNotNull(generatorClazz, "Param 'generatorClazz' not null");
		Check.assumeNotNull(clazz, "Param 'clazz' not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		final I_C_ReferenceNo_Type type = retrieveRefNoTypeByClass(ctx, generatorClazz);

		final List<I_C_ReferenceNo> referenceNos = retrieveReferenceNos(model, type);

		final String tableName = InterfaceWrapperHelper.getTableName(clazz);

		final List<T> result = new ArrayList<T>();

		for (final I_C_ReferenceNo referenceNo : referenceNos)
		{
			final List<I_C_ReferenceNo_Doc> refNoDocs = retrieveRefNoDocByRefNoAndTableName(referenceNo, tableName);

			for (final I_C_ReferenceNo_Doc refNoDoc : refNoDocs)
			{
				final T referencedDoc = InterfaceWrapperHelper.create(ctx, refNoDoc.getRecord_ID(), clazz, trxName);
				result.add(referencedDoc);
			}
		}
		return result;
	}

	protected abstract List<I_C_ReferenceNo_Doc> retrieveRefNoDocByRefNoAndTableName(I_C_ReferenceNo referenceNo, String tableName);
}
