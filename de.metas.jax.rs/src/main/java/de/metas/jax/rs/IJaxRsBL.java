package de.metas.jax.rs;

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.db.CConnection;

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
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public interface IJaxRsBL extends ISingletonService
{
	/**
	 * Get {@link I_AD_JAXRS_Endpoint} records by invoking {@link IJaxRsDAO#retrieveServerEndPoints(Properties)} and put up a JAX-RS endpoint for each of them.<br>
	 * Also, call {@link #stopServerEndPoints()} to stop any running endpoints before doing this.
	 */
	void startServerEndPoints(Properties ctx);

	/**
	 * Get {@link I_AD_JAXRS_Endpoint} records by invoking {@link IJaxRsDAO#retrieveClientEndpoints(Properties)} create a JAX-RS <b>client</b> endpoint for each of them, <br>
	 * and register each of them using {@link org.adempiere.util.Services#registerService(Class, ISingletonService)}.
	 * That way, <code>Services</code> will return the JAX-RS client instead of the implementation and therefore.
	 */
	void registerClientEndPoints(Properties ctx);

	/**
	 * Stop all running server endpoints, if there are any.
	 */
	void stopServerEndPoints();

	/**
	 * Create a particular client endpoint for the given <code>serviceInterface</code>.<br>
	 * This method is is intended for the early stages of metasfresh startup, as well as for testing.
	 *
	 * @param cConnection mayb e <code>null</code>. The connection from which to get the server and port. If <code>null</code>, then use {@link CConnection#get()}. Note that in the early stages of
	 *            startup, we can't rely on {@link CConnection#get()} to work for us.
	 * @param serviceInterface
	 */
	<T extends ISingletonService> T createClientEndpoint(CConnection cConnection, Class<T> serviceInterface);

	/**
	 * Similar to {@link #startClientEndPoint(Class)}. Note that here we need the actual concrete impelmentation class.
	 *
	 * @param serviceImpl
	 */
	void startServerEndPoint(Class<? extends ISingletonService> serviceImpl);

	/**
	 * Update the the list of {@link I_AD_JAXRS_Endpoint} records by loading all {@link I_AD_JavaClass} records that belong to the {@link I_AD_JavaClass_Type} with {@link javax.ws.rs.Path} as its
	 * <code>ClassName>/code>.<br>
	 * In other words, get all known JAX-RX service classes and synchronize the {@link I_AD_JAXRS_Endpoint} accordingly.
	 *
	 *
	 * @param ctx
	 * @param alsoSyncClasses if <code>true</code>, then first invoke {@link de.metas.javaclasses.IJavaClassTypeBL#updateClassRecordsList(de.metas.javaclasses.model.I_AD_JavaClass_Type)}.
	 */
	void updateEndPointsList(Properties ctx, boolean alsoSyncClasses);
}
