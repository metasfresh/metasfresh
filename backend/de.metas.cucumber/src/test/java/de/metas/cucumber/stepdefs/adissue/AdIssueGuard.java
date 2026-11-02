package de.metas.cucumber.stepdefs.adissue;

import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Pure classifier for {@code AD_Issue} rows created while a scenario ran.
 * Each row is EXPECTED (matches a per-scenario tag pattern), KNOWN (matches a registry entry) or UNKNOWN.
 * EXPECTED takes precedence over KNOWN.
 */
public final class AdIssueGuard
{
	private AdIssueGuard()
	{
	}

	@Value
	public static class Verdict
	{
		@NonNull AdIssueRow row;
		/** only set for KNOWN rows */
		@Nullable String issueUrl;
	}

	@Value
	public static class Result
	{
		List<Verdict> expected;
		List<Verdict> known;
		List<Verdict> unknown;
	}

	public static Result classify(
			@NonNull final Collection<AdIssueRow> newRows,
			@NonNull final Set<String> expectedPatterns,
			@NonNull final List<KnownAdIssue> registry)
	{
		final List<Verdict> expected = new ArrayList<>();
		final List<Verdict> known = new ArrayList<>();
		final List<Verdict> unknown = new ArrayList<>();

		for (final AdIssueRow row : newRows)
		{
			if (expectedPatterns.stream().anyMatch(p -> matches(p, row.getSummary())))
			{
				expected.add(new Verdict(row, null));
				continue;
			}

			final KnownAdIssue knownEntry = registry.stream()
					.filter(entry -> matches(entry.getPattern(), row.getSummary()))
					.findFirst()
					.orElse(null);
			if (knownEntry != null)
			{
				known.add(new Verdict(row, knownEntry.getIssueUrl()));
			}
			else
			{
				unknown.add(new Verdict(row, null));
			}
		}

		return new Result(
				Collections.unmodifiableList(expected),
				Collections.unmodifiableList(known),
				Collections.unmodifiableList(unknown));
	}

	private static boolean matches(final String pattern, final String summary)
	{
		return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(summary).find();
	}
}
