/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import io.cucumber.plugin.event.TestRunStarted;
import lombok.NonNull;

/**
 * Thx to https://medium.com/@hemanthsridhar/global-hooks-in-cucumber-jvm-afc1be13e487 !
 */
public class CucumberLifeCycleSupportPlugin implements ConcurrentEventListener
{
	private CucumberLifeCycleSupport cucumberLifeCycleSupport = new CucumberLifeCycleSupport();

	@Override
	public void setEventPublisher(@NonNull final EventPublisher eventPublisher)
	{
		eventPublisher.registerHandlerFor(TestRunStarted.class, event -> cucumberLifeCycleSupport.beforeAll());
		eventPublisher.registerHandlerFor(TestRunFinished.class, event -> cucumberLifeCycleSupport.afterAll());
	}
}
