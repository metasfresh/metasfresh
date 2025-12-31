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

package de.metas.cucumber.stepdefs;

import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cucumber hook that enhances Allure reports with feature metadata.
 * <p>
 * This hook extracts feature IDs (e.g., F00100) from {@code @allure.label.feature:FXXXXX} tags
 * and adds them to the individual test's Allure metadata:
 * <ul>
 *   <li>Feature IDs are added as individual tags (visible in Tags section)</li>
 *   <li>A description is generated with feature headers (visible in Description section)</li>
 * </ul>
 * <p>
 * This ensures Cucumber tests have the same feature visibility as Playwright tests
 * in the Allure report.
 */
public class AllureMetadataHook
{
	private static final Pattern FEATURE_TAG_PATTERN = Pattern.compile("@allure\\.label\\.feature:(F\\d+)");
	private static final Pattern EPIC_TAG_PATTERN = Pattern.compile("@allure\\.label\\.epic:(E\\d+)");

	/**
	 * Before hook that runs before each scenario.
	 * Extracts feature and epic tags and adds them to Allure metadata.
	 */
	@Before(order = 1) // Run early, but after other setup hooks
	public void addAllureMetadata(final Scenario scenario)
	{
		final Collection<String> tags = scenario.getSourceTagNames();

		final List<String> featureIds = extractMatchingValues(tags, FEATURE_TAG_PATTERN);
		final List<String> epicIds = extractMatchingValues(tags, EPIC_TAG_PATTERN);

		// Add feature IDs as individual Allure tags
		for (final String featureId : featureIds)
		{
			Allure.tag(featureId);
		}

		// Build and set description with feature headers
		if (!featureIds.isEmpty() || !epicIds.isEmpty())
		{
			final String description = buildDescription(epicIds, featureIds, scenario.getName());
			Allure.description(description);
		}
	}

	/**
	 * Extracts matching values from tags using the given pattern.
	 */
	private List<String> extractMatchingValues(final Collection<String> tags, final Pattern pattern)
	{
		final List<String> values = new ArrayList<>();
		for (final String tag : tags)
		{
			final Matcher matcher = pattern.matcher(tag);
			if (matcher.matches())
			{
				values.add(matcher.group(1));
			}
		}
		return values;
	}

	/**
	 * Builds a Markdown description with epic and feature headers.
	 * Format matches Playwright test descriptions for consistency.
	 */
	private String buildDescription(
			final List<String> epicIds,
			final List<String> featureIds,
			final String scenarioName)
	{
		final StringBuilder sb = new StringBuilder();

		// Add epic headers
		for (final String epicId : epicIds)
		{
			sb.append("## ").append(epicId).append("\n");
		}

		// Add feature headers
		for (final String featureId : featureIds)
		{
			sb.append("## ").append(featureId).append("\n");
		}

		// Add scenario name as context
		if (scenarioName != null && !scenarioName.isEmpty())
		{
			sb.append("\n### Scenario\n");
			sb.append(scenarioName).append("\n");
		}

		return sb.toString();
	}
}
