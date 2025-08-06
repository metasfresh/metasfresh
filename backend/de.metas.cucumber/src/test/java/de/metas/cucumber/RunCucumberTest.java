/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

/**
 * Use this class to run cucumber tests as junit-5 tests.
 * But note that in IntelliJ you can also run scenarios directly from the feature file.
 * Just make sure that your {@code Scenario} or {@code Background} contain this Step:
 * {@code Given infrastructure and metasfresh are running}.
 * <p>
 * Also note that this class is invoked when the cucumber-tests are run from {@code mvn test}
 */
@Suite
@IncludeEngines("cucumber")
@SelectPackages("de.metas.cucumber")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "de.metas.cucumber.stepdefs")
@ConfigurationParameter(key = Constants.FEATURES_PROPERTY_NAME, value = "classpath:de/metas/cucumber/features")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "not @ignore")
@ConfigurationParameter(key = Constants.PLUGIN_PROPERTY_NAME, value = "html:target/cucumber.html,json:target/cucumber.json,junit:target/cucumber-junit.xml,message:target/cucumber.message")
public class RunCucumberTest
{
}

