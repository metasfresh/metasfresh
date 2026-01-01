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

/**
 * Placeholder for Cucumber Allure metadata enhancement.
 * <p>
 * Note: Feature tags (F00100, etc.) are added to individual test results
 * via post-processing in CI. See: .github/scripts/add-feature-tags-to-allure.sh
 * <p>
 * The AllureCucumber7Jvm plugin handles @allure.label.feature:FXXXXX tags
 * for the Behaviors tree hierarchy. The CI script copies these "feature"
 * labels to "tag" labels so they appear in the individual test Tags section.
 */
public class AllureMetadataHook
{
	// Feature tags are added via CI post-processing, not runtime hooks.
	// See: .github/scripts/add-feature-tags-to-allure.sh
}
