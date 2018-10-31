package de.metas.jax.rs.impl;

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.PlainContextAware;

import de.metas.jax.rs.IJaxRsDAO;
import de.metas.jax.rs.model.I_AD_JAXRS_Endpoint;
import de.metas.jax.rs.model.X_AD_JAXRS_Endpoint;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.jax.rs
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class JaxRsDAO implements IJaxRsDAO
{

	@Override
	public List<I_AD_JAXRS_Endpoint> retrieveAllEndPoints(final Properties ctx)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_JAXRS_Endpoint.class, new PlainContextAware(ctx))
				.create()
				.list();
	}

	@Override
	public List<I_AD_JAXRS_Endpoint> retrieveServerEndPoints(final Properties ctx)
	{
		return retrieveEndpoints(ctx, X_AD_JAXRS_Endpoint.ENDPOINTTYPE_Server);
	}

	@Override
	public List<I_AD_JAXRS_Endpoint> retrieveClientEndpoints(final Properties ctx)
	{
		return retrieveEndpoints(ctx, X_AD_JAXRS_Endpoint.ENDPOINTTYPE_Client);
	}

	private List<I_AD_JAXRS_Endpoint> retrieveEndpoints(final Properties ctx, final String endpointtype)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_AD_JAXRS_Endpoint.class, new PlainContextAware(ctx))
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_JAXRS_Endpoint.COLUMNNAME_EndpointType, endpointtype)
				.addEqualsFilter(I_AD_JAXRS_Endpoint.COLUMNNAME_IsEndpointActive, true)
				.create()
				.list();
	}
}
