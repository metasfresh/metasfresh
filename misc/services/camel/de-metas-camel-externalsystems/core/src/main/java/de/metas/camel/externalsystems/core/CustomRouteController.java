/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.core;

import de.metas.camel.externalsystems.common.CamelRoutesGroupEnum;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Route;
import org.apache.camel.spi.RouteController;
import org.springframework.stereotype.Service;

import java.util.Comparator;

import static de.metas.camel.externalsystems.core.authorization.CustomMessageFromMFRouteBuilder.CUSTOM_FROM_MF_ROUTE_ID;
import static de.metas.camel.externalsystems.core.authorization.CustomMessageToMFRouteBuilder.CUSTOM_TO_MF_ROUTE_ID;

@Service
public class CustomRouteController
{
	public void startAllRoutes(final Exchange exchange)
	{
		final RouteController routeController = exchange.getContext().getRouteController();

		exchange.getContext().getRoutes().stream()
				.filter(route -> (route.getGroup() == null
						|| !route.getGroup().equals(CamelRoutesGroupEnum.START_ON_DEMAND.getCode()))
						&& route.getStartupOrder() == null)
				.forEach(route -> startRoute(route, routeController, "Failed to start routes that are supposed to be up first after authorization! The problematic route : " + route.getRouteId() + "."));

		exchange.getContext().getRoutes().stream()
				.filter(route -> route.getStartupOrder() != null)
				.sorted(Comparator.comparing(Route::getStartupOrder))
				.forEach(route -> startRoute(route, routeController, "Failed to start routes that are supposed to be up at the end after authorization! The problematic route : " + route.getRouteId() + "."));
	}

	public void startAuthRoutes(final RouteController routeController) throws Exception
	{
		routeController.startRoute(CUSTOM_TO_MF_ROUTE_ID);
		routeController.startRoute(CUSTOM_FROM_MF_ROUTE_ID);
	}

	public void stopAllRoutes(final Exchange exchange)
	{
		final CamelContext currentEventContext = exchange.getContext();
		final RouteController routeController = exchange.getContext().getRouteController();
		currentEventContext.getRoutes().forEach(route -> {
			try
			{
				if (routeController.getRouteStatus(route.getRouteId()).isStarted())
				{
					routeController.stopRoute(route.getRouteId());
				}
			}
			catch (final Exception e)
			{
				throw new RuntimeException("Could not stop route : " + route.getRouteId() + ".", e);
			}
		});
	}

	private void startRoute(final Route route, final RouteController routeController, final String errorMessage)
	{
		try
		{
			routeController.startRoute(route.getRouteId());
		}
		catch (final Exception e)
		{
			throw new RuntimeException(errorMessage, e);
		}
	}
}
