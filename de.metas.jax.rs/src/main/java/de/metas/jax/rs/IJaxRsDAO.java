package de.metas.jax.rs;

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.jax.rs.model.I_AD_JAXRS_Endpoint;

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
/**
 * @author metas-dev <dev@metasfresh.com>
 *
 * @task http://dewiki908/mediawiki/index.php/09848_enable_metasfresh_to_provide_jax-rs_services_%28101763395402%29
 */
public interface IJaxRsDAO extends ISingletonService
{
	/**
	 * Retrieve all records, including those with <code>IsActive='N'</code>.
	 *
	 * @param ctx
	 * @return
	 */
	List<I_AD_JAXRS_Endpoint> retrieveAllEndPoints(Properties ctx);

	/**
	 * Retrieve those records, that have <code>IsActive=Y</code>, have type=server and have <code>IsEndpointActive=Y</code>.
	 *
	 * @param ctx
	 * @return
	 */
	List<I_AD_JAXRS_Endpoint> retrieveServerEndPoints(Properties ctx);

	/**
	 * Retrieve those records, that have <code>IsActive=Y</code>, have type=client and have <code>IsEndpointActive=Y</code>.
	 *
	 * @param ctx
	 * @return
	 */
	List<I_AD_JAXRS_Endpoint> retrieveClientEndpoints(Properties ctx);
}
