package de.metas.impex.api.impl;

import java.util.Optional;

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

import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;
import org.compiere.util.Env;

import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.api.InputDataSourceCreateRequest;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;

public class InputDataSourceDAO implements IInputDataSourceDAO
{
	@Override
	public int retrieveInputDataSourceIdByInternalName(final String internalName)
	{
		return retrieveInputDataSource(Env.getCtx(), internalName, /* throwEx */true, ITrx.TRXNAME_None)
				.getAD_InputDataSource_ID();
	}

	@Override
	public I_AD_InputDataSource retrieveInputDataSource(
			final Properties ctx,
			final String internalName,
			final boolean throwEx,
			final String trxName)
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
			final @CacheTrx String trxName)
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
			newInputDataSource.setInternalName(request.getInternalName());
			newInputDataSource.setIsDestination(request.isDestination());
			newInputDataSource.setName(request.getName());
			InterfaceWrapperHelper.save(newInputDataSource);
		}
	}

	@Override
	public Optional<InputDataSourceId> retrieveInputDataSourceIdByExternalId(final ExternalId externalId)
	{
		final InputDataSourceId inputDataSourceId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_InputDataSource.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_InputDataSource.COLUMNNAME_ExternalId, externalId.getValue())
				.create()
				.firstIdOnly(InputDataSourceId::ofRepoIdOrNull);

		return Optional.of(inputDataSourceId);
	}

	@Override
	public Optional<InputDataSourceId> retrieveInputDataSourceIdByValue(final String value)
	{
		final InputDataSourceId inputDataSourceId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_InputDataSource.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_InputDataSource.COLUMNNAME_Value, value)
				.create()
				.firstIdOnly(InputDataSourceId::ofRepoIdOrNull);

		return Optional.of(inputDataSourceId);
	}

}
