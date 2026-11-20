package de.metas.cucumber.stepdefs.adissue;

import lombok.NonNull;
import lombok.Value;

/** Minimal projection of an {@code AD_Issue} record: its ID and its message ({@code IssueSummary}). */
@Value
public class AdIssueRow
{
	int adIssueId;
	@NonNull String summary;
}
