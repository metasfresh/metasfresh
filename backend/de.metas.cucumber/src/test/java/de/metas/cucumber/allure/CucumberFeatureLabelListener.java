/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.allure;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.TestResult;

import java.util.List;

/**
 * Makes the Allure report file a tagged cucumber scenario only under the feature(s) its {@code @allure.label.feature:} tag(s) name.
 * <p>
 * {@code allure-cucumber7-jvm} always adds a {@code feature} label carrying the Gherkin {@code Feature:} title,
 * and passes the Gherkin tags through as {@code tag} labels. When generating the report, the Allure CLI turns an
 * {@code allure.label.feature:<value>} tag into one more {@code feature} label, so the scenario ended up under two feature nodes.
 * <p>
 * So if the scenario carries a non-empty feature tag, we drop the {@code feature} label(s) before the result file is written.
 * Scenarios without such a tag keep their Gherkin title as feature. Registered via {@code META-INF/services}.
 */
public class CucumberFeatureLabelListener implements TestLifecycleListener
{
	private static final String LABEL_NAME_FEATURE = "feature";
	private static final String LABEL_NAME_TAG = "tag";
	private static final String FEATURE_TAG_PREFIX = "allure.label.feature:";

	@Override
	public void beforeTestWrite(final TestResult result)
	{
		final List<Label> labels = result.getLabels();
		if (labels == null || labels.stream().noneMatch(CucumberFeatureLabelListener::isNonEmptyFeatureTag))
		{
			return;
		}

		labels.removeIf(label -> LABEL_NAME_FEATURE.equals(label.getName()));
	}

	private static boolean isNonEmptyFeatureTag(final Label label)
	{
		if (!LABEL_NAME_TAG.equals(label.getName()) || label.getValue() == null)
		{
			return false;
		}

		final String tag = label.getValue().startsWith("@") ? label.getValue().substring(1) : label.getValue();
		return tag.startsWith(FEATURE_TAG_PREFIX) && !tag.substring(FEATURE_TAG_PREFIX.length()).trim().isEmpty();
	}
}
