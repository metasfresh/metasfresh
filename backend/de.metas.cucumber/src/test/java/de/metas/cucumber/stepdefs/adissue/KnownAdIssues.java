package de.metas.cucumber.stepdefs.adissue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads the in-repo registry of known bugs: {@code /de/metas/cucumber/adissue/known-ad-issues.json},
 * a JSON array of {@code {"pattern": "...", "issueUrl": "..."}} entries.
 */
@UtilityClass
public class KnownAdIssues
{
	static final String RESOURCE = "/de/metas/cucumber/adissue/known-ad-issues.json";

	/** @return the registry entries; empty if the resource is missing or empty */
	public static List<KnownAdIssue> load()
	{
		try (final InputStream in = KnownAdIssues.class.getResourceAsStream(RESOURCE))
		{
			if (in == null)
			{
				return new ArrayList<>();
			}
			final List<KnownAdIssueJson> entries = new ObjectMapper().readValue(in, new TypeReference<List<KnownAdIssueJson>>() {});
			final List<KnownAdIssue> result = new ArrayList<>();
			if (entries != null)
			{
				for (final KnownAdIssueJson e : entries)
				{
					result.add(new KnownAdIssue(e.pattern, e.issueUrl));
				}
			}
			return result;
		}
		catch (final IOException e)
		{
			throw new IllegalStateException("Unable to read " + RESOURCE, e);
		}
	}

	/** Jackson binding bean. */
	public static class KnownAdIssueJson
	{
		public String pattern;
		public String issueUrl;
	}
}
