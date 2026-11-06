package de.metas.cucumber.stepdefs.adissue;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AdIssueGuardTest
{
	private static AdIssueRow row(final int id, final String summary)
	{
		return new AdIssueRow(id, summary);
	}

	private static Set<String> patterns(final String... p)
	{
		return new HashSet<>(Arrays.asList(p));
	}

	@Test
	void expectedTagMatch()
	{
		final AdIssueGuard.Result result = AdIssueGuard.classify(
				Collections.singletonList(row(1, "Something NullPointerException happened")),
				patterns("nullpointer"),
				Collections.emptyList());

		assertThat(result.getExpected()).hasSize(1);
		assertThat(result.getKnown()).isEmpty();
		assertThat(result.getUnknown()).isEmpty();
	}

	@Test
	void registryMatch_carriesUrl()
	{
		final List<KnownAdIssue> registry = Collections.singletonList(new KnownAdIssue("duplicate key", "https://example.org/bug/1"));

		final AdIssueGuard.Result result = AdIssueGuard.classify(
				Collections.singletonList(row(2, "ERROR: Duplicate key value violates")),
				Collections.emptySet(),
				registry);

		assertThat(result.getKnown()).hasSize(1);
		assertThat(result.getKnown().get(0).getIssueUrl()).isEqualTo("https://example.org/bug/1");
		assertThat(result.getKnown().get(0).getRow().getAdIssueId()).isEqualTo(2);
		assertThat(result.getUnknown()).isEmpty();
		assertThat(result.getExpected()).isEmpty();
	}

	@Test
	void unknownRow()
	{
		final AdIssueGuard.Result result = AdIssueGuard.classify(
				Collections.singletonList(row(3, "boom")),
				patterns("other"),
				Collections.singletonList(new KnownAdIssue("nope", "https://example.org/x")));

		assertThat(result.getUnknown()).hasSize(1);
		assertThat(result.getUnknown().get(0).getRow().getAdIssueId()).isEqualTo(3);
		assertThat(result.getKnown()).isEmpty();
		assertThat(result.getExpected()).isEmpty();
	}

	@Test
	void mixedBatch_expectedWinsOverKnown()
	{
		final List<KnownAdIssue> registry = Arrays.asList(
				new KnownAdIssue("known-one", "https://example.org/k1"),
				new KnownAdIssue("both", "https://example.org/both"));

		final AdIssueGuard.Result result = AdIssueGuard.classify(
				Arrays.asList(row(1, "expected-one"), row(2, "KNOWN-ONE here"), row(3, "who knows"), row(4, "both expected-one")),
				patterns("expected-one"),
				registry);

		assertThat(result.getExpected()).extracting(v -> v.getRow().getAdIssueId()).containsExactly(1, 4);
		assertThat(result.getKnown()).extracting(v -> v.getRow().getAdIssueId()).containsExactly(2);
		assertThat(result.getUnknown()).extracting(v -> v.getRow().getAdIssueId()).containsExactly(3);
	}

	@Test
	void emptyInputs()
	{
		final AdIssueGuard.Result result = AdIssueGuard.classify(Collections.emptyList(), Collections.emptySet(), Collections.emptyList());
		assertThat(result.getExpected()).isEmpty();
		assertThat(result.getKnown()).isEmpty();
		assertThat(result.getUnknown()).isEmpty();
	}

	@Test
	void unknownWhenNoPatternsAtAll()
	{
		final AdIssueGuard.Result result = AdIssueGuard.classify(
				Arrays.asList(row(1, "a"), row(2, "b")), Collections.emptySet(), Collections.emptyList());
		assertThat(result.getUnknown()).hasSize(2);
	}

	@Test
	void onePatternMatchesMultipleRows()
	{
		final AdIssueGuard.Result result = AdIssueGuard.classify(
				Arrays.asList(row(1, "timeout A"), row(2, "Timeout B"), row(3, "other")),
				patterns("timeout"),
				Collections.emptyList());

		assertThat(result.getExpected()).hasSize(2);
		assertThat(result.getUnknown()).hasSize(1);
	}
}
