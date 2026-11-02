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
import java.util.regex.PatternSyntaxException;

/**
 * Pure classifier for {@code AD_Issue} rows created while a scenario ran.
 * Each row is EXPECTED (matches a per-scenario tag pattern), KNOWN (matches a registry entry) or UNKNOWN.
 * EXPECTED takes precedence over KNOWN. Patterns are compiled once per {@link #classify} call;
 * a blank or invalid pattern is skipped (it never matches), so it cannot silently match everything.
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
		final List<Pattern> expectedCompiled = compilePatterns(expectedPatterns);
		final List<CompiledKnown> knownCompiled = new ArrayList<>();
		for (final KnownAdIssue entry : registry)
		{
			final Pattern p = compile(entry.getPattern());
			if (p != null)
			{
				knownCompiled.add(new CompiledKnown(p, entry.getIssueUrl()));
			}
		}

		final List<Verdict> expected = new ArrayList<>();
		final List<Verdict> known = new ArrayList<>();
		final List<Verdict> unknown = new ArrayList<>();

		for (final AdIssueRow row : newRows)
		{
			final String summary = row.getSummary();
			if (anyMatch(expectedCompiled, summary))
			{
				expected.add(new Verdict(row, null));
				continue;
			}

			final String knownUrl = firstMatchUrl(knownCompiled, summary);
			if (knownUrl != null)
			{
				known.add(new Verdict(row, knownUrl));
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

	private static List<Pattern> compilePatterns(final Set<String> patterns)
	{
		final List<Pattern> out = new ArrayList<>();
		for (final String p : patterns)
		{
			final Pattern compiled = compile(p);
			if (compiled != null)
			{
				out.add(compiled);
			}
		}
		return out;
	}

	@Nullable
	private static Pattern compile(@Nullable final String pattern)
	{
		if (pattern == null || pattern.trim().isEmpty())
		{
			return null;
		}
		try
		{
			return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		}
		catch (final PatternSyntaxException e)
		{
			return null;
		}
	}

	private static boolean anyMatch(final List<Pattern> patterns, final String summary)
	{
		for (final Pattern p : patterns)
		{
			if (p.matcher(summary).find())
			{
				return true;
			}
		}
		return false;
	}

	@Nullable
	private static String firstMatchUrl(final List<CompiledKnown> known, final String summary)
	{
		for (final CompiledKnown k : known)
		{
			if (k.pattern.matcher(summary).find())
			{
				return k.issueUrl;
			}
		}
		return null;
	}

	@Value
	private static class CompiledKnown
	{
		Pattern pattern;
		String issueUrl;
	}
}
