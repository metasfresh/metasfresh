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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.FileSystemResultsWriter;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.TestResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.*;

/**
 * The labels below are shaped exactly as {@code allure-cucumber7-jvm} writes them: the Gherkin {@code Feature:} title as
 * {@code feature} label, every Gherkin tag as {@code tag} label without the leading {@code @}, and a file-level tag that is
 * repeated at scenario level appearing twice.
 */
class CucumberFeatureLabelListenerTest
{
	private static final String GHERKIN_TITLE = "MD_Stock_PerWeek_V shows cumulative projected stock";

	private final CucumberFeatureLabelListener listener = new CucumberFeatureLabelListener();

	@Test
	void taggedScenario_losesGherkinTitleFeatureLabel_otherLabelsUnchanged()
	{
		final TestResult result = resultWithLabels(
				label("tag", "from:cucumber"),
				label("tag", "allure.label.epic:E0155_Material_Disposition"),
				label("tag", "allure.label.feature:F19100"),
				label("tag", "allure.label.epic:E0155_Material_Disposition"),
				label("tag", "allure.label.feature:F19100"),
				label("feature", GHERKIN_TITLE),
				label("story", "QtyATP at week-end"),
				label("suite", GHERKIN_TITLE),
				label("package", "de.metas.cucumber.features.material.MD_Stock_PerWeek_V"));

		listener.beforeTestWrite(result);

		assertThat(namesAndValues(result)).containsExactly(
				"tag=from:cucumber",
				"tag=allure.label.epic:E0155_Material_Disposition",
				"tag=allure.label.feature:F19100",
				"tag=allure.label.epic:E0155_Material_Disposition",
				"tag=allure.label.feature:F19100",
				"story=QtyATP at week-end",
				"suite=" + GHERKIN_TITLE,
				"package=de.metas.cucumber.features.material.MD_Stock_PerWeek_V");
	}

	@Test
	void scenarioOutlineExampleRow_losesGherkinTitleFeatureLabel()
	{
		final TestResult result = resultWithLabels(
				label("tag", "allure.label.feature:F00701_Sales_Invoice_Candidates"),
				label("feature", GHERKIN_TITLE),
				label("story", "Outline <qty> - Examples #1.2"));

		listener.beforeTestWrite(result);

		assertThat(namesAndValues(result)).containsExactly(
				"tag=allure.label.feature:F00701_Sales_Invoice_Candidates",
				"story=Outline <qty> - Examples #1.2");
	}

	@Test
	void twoDifferentFeatureTags_bothTagsKept_gherkinTitleDropped()
	{
		final TestResult result = resultWithLabels(
				label("tag", "allure.label.feature:F00700_Invoicing"),
				label("tag", "allure.label.feature:F01010"),
				label("feature", GHERKIN_TITLE));

		listener.beforeTestWrite(result);

		assertThat(namesAndValues(result)).containsExactly(
				"tag=allure.label.feature:F00700_Invoicing",
				"tag=allure.label.feature:F01010");
	}

	@Test
	void featureTagWithLeadingAt_isRecognized()
	{
		final TestResult result = resultWithLabels(
				label("tag", "@allure.label.feature:F19100"),
				label("feature", GHERKIN_TITLE));

		listener.beforeTestWrite(result);

		assertThat(namesAndValues(result)).containsExactly("tag=@allure.label.feature:F19100");
	}

	@Test
	void untaggedScenario_unchanged()
	{
		final TestResult result = resultWithLabels(
				label("tag", "from:cucumber"),
				label("tag", "allure.label.epic:E0155_Material_Disposition"),
				label("feature", GHERKIN_TITLE),
				label("suite", GHERKIN_TITLE));

		listener.beforeTestWrite(result);

		assertThat(namesAndValues(result)).containsExactly(
				"tag=from:cucumber",
				"tag=allure.label.epic:E0155_Material_Disposition",
				"feature=" + GHERKIN_TITLE,
				"suite=" + GHERKIN_TITLE);
	}

	@Test
	void emptyFeatureTagValue_countsAsNoFeatureTag()
	{
		final TestResult result = resultWithLabels(
				label("tag", "allure.label.feature:"),
				label("feature", GHERKIN_TITLE));

		listener.beforeTestWrite(result);

		assertThat(namesAndValues(result)).containsExactly(
				"tag=allure.label.feature:",
				"feature=" + GHERKIN_TITLE);
	}

	/**
	 * Goes through the real {@link AllureLifecycle}, which loads its listeners via {@link java.util.ServiceLoader}.
	 * So this also proves that the listener is registered in {@code META-INF/services} and is applied to the written result file.
	 */
	@Test
	void writtenResultFile_hasNoGherkinTitleFeatureLabel(@TempDir final Path resultsDir) throws Exception
	{
		final AllureLifecycle lifecycle = new AllureLifecycle(new FileSystemResultsWriter(resultsDir));
		final String uuid = UUID.randomUUID().toString();
		lifecycle.scheduleTestCase(resultWithLabels(
				label("tag", "allure.label.epic:E0155_Material_Disposition"),
				label("tag", "allure.label.feature:F19100"),
				label("feature", GHERKIN_TITLE))
				.setUuid(uuid)
				.setName("QtyATP at week-end"));
		lifecycle.startTestCase(uuid);
		lifecycle.stopTestCase(uuid);
		lifecycle.writeTestCase(uuid);

		final JsonNode written = new ObjectMapper().readTree(resultsDir.resolve(uuid + "-result.json").toFile());
		final List<String> writtenLabels = StreamSupport.stream(written.get("labels").spliterator(), false)
				.map(node -> node.get("name").asText() + "=" + node.get("value").asText())
				.collect(Collectors.toList());
		assertThat(writtenLabels).containsExactly(
				"tag=allure.label.epic:E0155_Material_Disposition",
				"tag=allure.label.feature:F19100");
	}

	private static Label label(final String name, final String value)
	{
		return new Label().setName(name).setValue(value);
	}

	private static TestResult resultWithLabels(final Label... labels)
	{
		return new TestResult().setLabels(new ArrayList<>(Arrays.asList(labels)));
	}

	private static List<String> namesAndValues(final TestResult result)
	{
		return result.getLabels()
				.stream()
				.map(label -> label.getName() + "=" + label.getValue())
				.collect(Collectors.toList());
	}
}
