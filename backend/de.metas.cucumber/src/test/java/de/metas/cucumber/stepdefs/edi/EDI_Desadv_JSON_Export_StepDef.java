package de.metas.cucumber.stepdefs.edi;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for verifying DESADV JSON export results,
 * specifically compensation group (Mischkarton) merging behavior.
 * <p>
 * Uses the last REST API response from {@link TestContext} to inspect
 * the PostgREST JSON output of {@code get_desadv_packs_json_fn}.
 */
@RequiredArgsConstructor
public class EDI_Desadv_JSON_Export_StepDef
{
	private final @NonNull TestContext testContext;
	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Verifies that the DESADV JSON export correctly merges compensation group sub-articles
	 * into the main article's packing entry.
	 * <p>
	 * Inspects the {@code Packings[].LineItems[]} in the last API response and counts:
	 * <ul>
	 *     <li>Total packings (should be reduced after merging)</li>
	 *     <li>Main articles ({@code IsSubArticle=false}): must have {@code MainArticleLine=null}</li>
	 *     <li>Sub-articles ({@code IsSubArticle=true}): must have {@code MainArticleLine > 0}</li>
	 * </ul>
	 * <p>
	 * DataTable columns:
	 * <ul>
	 *     <li>{@code PackingCount} (required) — expected number of packing entries after merging</li>
	 *     <li>{@code MainArticleCount} (required) — expected number of main article line items</li>
	 *     <li>{@code SubArticleCount} (required) — expected number of sub-article line items</li>
	 * </ul>
	 * <p>
	 * Example usage:
	 * <pre>
	 * Then verify DESADV JSON export has compensation group packing:
	 *   | PackingCount | MainArticleCount | SubArticleCount |
	 *   | 1            | 1                | 2               |
	 * </pre>
	 */
	@Then("verify DESADV JSON export has compensation group packing:")
	public void verifyCompensationGroupPacking(@NonNull final DataTable dataTable) throws Exception
	{
		final String responseBody = testContext.getApiResponseBodyAsString();
		final JsonNode root = objectMapper.readTree(responseBody);
		final JsonNode packings = root.path("metasfresh_DESADV").path("Packings");

		assertThat(packings.isArray()).as("Packings should be an array").isTrue();

		DataTableRows.of(dataTable).forEach(row -> {
			final int expectedPackingCount = row.getAsInt("PackingCount");
			assertThat(packings.size())
					.as("Expected %d packings after compensation group merging", expectedPackingCount)
					.isEqualTo(expectedPackingCount);

			final int expectedMainArticles = row.getAsInt("MainArticleCount");
			final int expectedSubArticles = row.getAsInt("SubArticleCount");

			int actualMainArticles = 0;
			int actualSubArticles = 0;
			final List<Integer> subArticleMainLines = new ArrayList<>();

			for (final JsonNode packing : packings)
			{
				final JsonNode lineItems = packing.path("LineItems");
				assertThat(lineItems.isArray()).as("LineItems should be an array").isTrue();

				for (final JsonNode item : lineItems)
				{
					final boolean isSubArticle = item.path("IsSubArticle").asBoolean(false);
					if (isSubArticle)
					{
						actualSubArticles++;
						final JsonNode mainArticleLine = item.path("MainArticleLine");
						assertThat(mainArticleLine.isNull()).as("SubArticle should have MainArticleLine set").isFalse();
						subArticleMainLines.add(mainArticleLine.asInt());
					}
					else
					{
						actualMainArticles++;
						final JsonNode mainArticleLine = item.path("MainArticleLine");
						assertThat(mainArticleLine.isNull() || mainArticleLine.isValueNode() && mainArticleLine.asText().equals("null"))
								.as("Non-sub-article should have MainArticleLine=null")
								.isTrue();
					}
				}
			}

			assertThat(actualMainArticles)
					.as("Expected %d main article line items", expectedMainArticles)
					.isEqualTo(expectedMainArticles);
			assertThat(actualSubArticles)
					.as("Expected %d sub-article line items", expectedSubArticles)
					.isEqualTo(expectedSubArticles);

			// Verify all sub-articles reference a valid main line
			for (final Integer mainLine : subArticleMainLines)
			{
				assertThat(mainLine).as("MainArticleLine should be > 0").isGreaterThan(0);
			}
		});
	}
}
