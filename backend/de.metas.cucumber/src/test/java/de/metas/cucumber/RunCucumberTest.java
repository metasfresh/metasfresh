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

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Use this class to run cucumber tests as junit-4 tests.
 * But note that in IntelliJ you can also run scenarios directly from the feature file.
 * Just make sure that your {@code Scenario} or {@code Background} contain the Step
 * {@code Given infrastructure and metasfresh are running}.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
		glue = "de.metas.cucumber.stepdefs",
		tags = "not @ignore", // use this tag to temporatily ignore single scenarios
		// tags = "@dev:runThisOne", // use this tag to run only particular scenarios
		plugin = {
				"html:target/cucumber.html",
				"json:target/cucumber.1json" /* this json-output is needed for the Jenkins plugin that's supposed to publish it */,
				"junit:target/cucumber-junit.xml" /* thx to https://stackoverflow.com/a/52676659/1012103 */,
				"message:target/cucumber.message"
		})
public class RunCucumberTest
{
}
