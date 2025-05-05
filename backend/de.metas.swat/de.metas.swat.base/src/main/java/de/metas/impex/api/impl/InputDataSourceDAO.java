package de.metas.impex.api.impl;

import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.api.InputDataSourceCreateRequest;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBMoreThanOneRecordsFoundException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Properties;

import static de.metas.util.Check.isEmpty;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class InputDataSourceDAO implements IInputDataSourceDAO
{

	@Override
	public I_AD_InputDataSource getById(@NonNull final InputDataSourceId id)
	{
		return loadOutOfTrx(id, I_AD_InputDataSource.class);
	}

	@Override
	public InputDataSourceId retrieveInputDataSourceIdByInternalName(final String internalName)
	{
		final int inputDataSourceRecordId = retrieveInputDataSource(Env.getCtx(), internalName, /* throwEx */true, ITrx.TRXNAME_None)
				.getAD_InputDataSource_ID();

		return InputDataSourceId.ofRepoId(inputDataSourceRecordId);
	}

	@Override
	public I_AD_InputDataSource retrieveInputDataSource(
			final Properties ctx,
			final String internalName,
			final boolean throwEx,
			@Nullable final String trxName)
	{
		final I_AD_InputDataSource result = retriveDataSource(ctx, internalName, trxName);

		if (result == null && throwEx)
		{
			throw new AdempiereException("missing data source for internal name " + internalName);
		}

		return result;
	}

	@Cached(cacheName = I_AD_InputDataSource.Table_Name)
		/* package */ I_AD_InputDataSource retriveDataSource(
			final @CacheCtx Properties ctx,
			final String internalName,
			final @Nullable @CacheTrx String trxName)
	{

		final I_AD_InputDataSource result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_InputDataSource.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_InputDataSource.COLUMNNAME_InternalName, internalName)
				.create()
				.firstOnly(I_AD_InputDataSource.class);

		return result;
	}

	@Override
	public void createIfMissing(@NonNull final InputDataSourceCreateRequest request)
	{
		final I_AD_InputDataSource inputDataSource = retrieveInputDataSource(
				Env.getCtx(),
				request.getInternalName(),
				false, // throwEx
				ITrx.TRXNAME_None);
		if (inputDataSource == null)
		{
			final I_AD_InputDataSource newInputDataSource = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_InputDataSource.class, ITrx.TRXNAME_None);
			newInputDataSource.setEntityType(request.getEntityType());
			newInputDataSource.setValue(request.getInternalName());
			newInputDataSource.setInternalName(request.getInternalName());
			newInputDataSource.setIsDestination(request.isDestination());
			newInputDataSource.setName(request.getName());
			InterfaceWrapperHelper.save(newInputDataSource);
		}
	}

	@Override
	public Optional<InputDataSourceId> retrieveInputDataSourceIdBy(@NonNull final InputDataSourceQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_AD_InputDataSource> queryBuilder = queryBL
				.createQueryBuilder(I_AD_InputDataSource.class)
				.addOnlyActiveRecordsFilter();

		Check.assumeNotNull(query.getOrgId(), "Org Id is missing from InputDataSourceQuery ", query);

		queryBuilder.addInArrayFilter(I_AD_InputDataSource.COLUMNNAME_AD_Org_ID, query.getOrgId(), OrgId.ANY);

		if (!query.getInternalName().isEmpty())
		{
			queryBuilder.addEqualsFilter(I_AD_InputDataSource.COLUMNNAME_InternalName, query.getInternalName());
		}

		if (query.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_InputDataSource.COLUMNNAME_ExternalId, query.getExternalId().getValue());
		}

		if (!isEmpty(query.getValue(), true))
		{
			queryBuilder.addEqualsFilter(I_AD_InputDataSource.COLUMNNAME_Value, query.getValue());
		}

		if (query.getInputDataSourceId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_InputDataSource.COLUMNNAME_AD_InputDataSource_ID, query.getInputDataSourceId());
		}

		try
		{
			final InputDataSourceId firstId = queryBuilder
					.create()
					.firstIdOnly(InputDataSourceId::ofRepoIdOrNull);
			return Optional.ofNullable(firstId);
		}
		catch (final DBMoreThanOneRecordsFoundException e)
		{
			// augment and rethrow
			throw e.appendParametersToMessage().setParameter("inputDataSourceQuery", query);
		}
	}

}
