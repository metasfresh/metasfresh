/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.common.util.time.SystemTime;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import java.time.ZonedDateTime;

public class SystemTime_StepDef
{
	@Given("^metasfresh has date and time (.*)$")
	public void metasfresh_has_the_local_time(String zonedDateTime)
	{
		SystemTime.setFixedTimeSource(zonedDateTime);
	}

	@Given("metasfresh has current date and time")
	public void metasfresh_has_current_date_and_time()
	{
		SystemTime.setFixedTimeSource(ZonedDateTime.now());
	}

	@And("we wait for {int} ms")
	public void thread_sleep(final int sleepTime) throws InterruptedException
	{
		Thread.sleep(sleepTime);
	}
}
