package de.metas.gplr.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class GPLRReportId implements RepoIdAware
{

	int repoId;

	private GPLRReportId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "GPLR_Report_ID");
	}

	@JsonCreator
	public static GPLRReportId ofRepoId(final int repoId)
	{
		return new GPLRReportId(repoId);
	}

	public static GPLRReportId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new GPLRReportId(repoId) : null;
	}

	@JsonValue
	@Override
	public int getRepoId()
	{
		return repoId;
	}

	public static int toRepoId(@Nullable final GPLRReportId GPLRReportId)
	{
		return GPLRReportId != null ? GPLRReportId.getRepoId() : -1;
	}

	public static boolean equals(@Nullable final GPLRReportId id1, @Nullable final GPLRReportId id2) {return Objects.equals(id1, id2);}
}
