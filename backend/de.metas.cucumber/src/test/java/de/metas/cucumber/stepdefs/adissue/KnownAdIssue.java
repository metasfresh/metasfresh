package de.metas.cucumber.stepdefs.adissue;

import lombok.NonNull;
import lombok.Value;

/**
 * An entry of the known-bug registry: an {@code AD_Issue} message pattern (case-insensitive regex, matched as substring)
 * together with the URL of the issue that tracks the underlying bug.
 */
@Value
public class KnownAdIssue
{
	@NonNull String pattern;
	@NonNull String issueUrl;
}
