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

import de.metas.camel.externalsystems.common.CamelRoutesGroup;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Route;
import org.apache.camel.spi.RouteController;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CustomRouteController
{
	@NonNull
	private final CamelContext camelContext;

	public CustomRouteController(final @NonNull CamelContext camelContext)
	{
		this.camelContext = camelContext;
	}

	public void startAllRoutes()
	{
		getRoutes()
				.stream()
				.filter(CustomRouteController::isReadyToStart)
				.sorted(Comparator.comparing(Route::getStartupOrder, Comparator.nullsFirst(Comparator.naturalOrder())))
				.forEach(this::start);
	}

	public void stopAllRoutes()
	{
		getRoutes()
				.stream()
				.filter(CustomRouteController::canBeStopped)
				.forEach(this::stop);
	}

	public void startAlwaysRunningRoutes()
	{
		getRoutes()
				.stream()
				.filter(CustomRouteController::isAlwaysRunning)
				.forEach(this::start);
	}

	private void start(final Route route)
	{
		try
		{
			getRouteController().startRoute(route.getRouteId());
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Exception caught when trying to start route=" + route.getRouteId(), e);
		}
	}

	private void stop(final Route route)
	{
		try
		{
			getRouteController().stopRoute(route.getRouteId(), 100, TimeUnit.MILLISECONDS);
		}
		catch (final Exception e)
		{
			throw new RuntimeException("Exception caught when trying to suspend route=" + route.getRouteId(), e);
		}
	}

	@NonNull
	private List<Route> getRoutes()
	{
		return camelContext.getRoutes();
	}

	@NonNull
	private RouteController getRouteController()
	{
		return camelContext.getRouteController();
	}

	private static boolean isReadyToStart(@NonNull final Route route)
	{
		final boolean isStartOnDemand = CamelRoutesGroup.ofCodeOptional(route.getGroup())
				.map(CamelRoutesGroup::isStartOnDemand)
				.orElse(false);

		return !isStartOnDemand;
	}

	private static boolean canBeStopped(@NonNull final Route route)
	{
		return !isAlwaysRunning(route);
	}

	private static boolean isAlwaysRunning(@NonNull final Route route)
	{
		return CamelRoutesGroup.ofCodeOptional(route.getGroup())
				.map(CamelRoutesGroup::isAlwaysOn)
				.orElse(false);
	}
}
