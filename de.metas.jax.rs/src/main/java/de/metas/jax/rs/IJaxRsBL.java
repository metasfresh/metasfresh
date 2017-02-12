package de.metas.jax.rs;

import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;
import de.metas.jax.rs.model.I_AD_JAXRS_Endpoint;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Service to manage JAX-RS service endpoints.
 * <p>
 * Note that we currently only have endpoints that use JMS as the transport.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IJaxRsBL extends ISingletonService
{
	long DEFAULT_CLIENT_TIMEOUT_MILLIS = 60000L;

	String JMS_QUEUE_REQUEST = "de.metas.jax.rs.metasfresh.request";
	String JMS_QUEUE_RESPONSE = "de.metas.jax.rs.metasfresh.response";

	/**
	 * Get {@link I_AD_JAXRS_Endpoint} records by invoking {@link IJaxRsDAO#retrieveServerEndPoints(Properties)} and put up a JAX-RS endpoint for each of them.<br>
	 * Also, call {@link #destroyServerEndPoints()} to stop any running endpoints before doing this.
	 */
	void createServerEndPoints();

	/**
	 * Get {@link I_AD_JAXRS_Endpoint} records by invoking {@link IJaxRsDAO#retrieveClientEndpoints(Properties)} create a JAX-RS <b>client</b> endpoint for each of them, <br>
	 * and register each of them using {@link org.adempiere.util.Services#registerService(Class, ISingletonService)}.
	 * That way, <code>Services</code> will return the JAX-RS client instead of the implementation and therefore.
	 */
	void createClientEndPoints(Properties ctx);

	/**
	 * Stop all running server endpoints, if there are any.
	 */
	void destroyServerEndPoints();

	/**
	 * Create one or more particular client endpoints for the given <code>request</code> instance.<br>
	 * This method is is intended for the early stages of metasfresh startup, as well as for testing.
	 *
	 * @param serviceInterface
	 */
	<T extends ISingletonService> List<T> createClientEndpointsProgramatically(CreateEndpointRequest<T> request);

	/**
	 * Similar to {@link #createClientEndpoint(CreateEndpointRequest)}.<br>
	 * An endpoint created with this method won't be stopped when {@link #destroyServerEndPoints()} is called.<br>
	 * Note about the given <code>request</code> parameter:
	 * <ul>
	 * <li>here the request's class needs to be an actual concrete implementation class.
	 * <li>the timeout, even if set, is ignored
	 * </ul>
	 *
	 * @param serviceImpl
	 */
	<T extends ISingletonService> void createServerEndPointsProgramatically(CreateEndpointRequest<T> request);

	/**
	 * Update the the list of {@link I_AD_JAXRS_Endpoint} records by loading all {@link I_AD_JavaClass} records that belong to the {@link I_AD_JavaClass_Type} with {@link javax.ws.rs.Path} as its
	 * <code>ClassName>/code>.<br>
	 * In other words, get all known JAX-RX service classes and synchronize the {@link I_AD_JAXRS_Endpoint} accordingly.
	 *
	 *
	 * &#64;param ctx
	 * @param alsoSyncClasses if <code>true</code>, then first invoke {@link de.metas.javaclasses.IJavaClassTypeBL#updateClassRecordsList(de.metas.javaclasses.model.I_AD_JavaClass_Type)}.
	 */
	void updateEndPointsList(Properties ctx, boolean alsoSyncClasses);
}
