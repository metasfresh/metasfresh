package de.metas.datev;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;

@Value
public class DATEVExportId implements RepoIdAware
{
	int repoId;

	private DATEVExportId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "DATEV_Export_ID");
	}

	@JsonCreator
	public static DATEVExportId ofRepoId(final int repoId) {return new DATEVExportId(repoId);}

	@Nullable
	public static DATEVExportId ofRepoIdOrNull(final int repoId) {return repoId > 0 ? new DATEVExportId(repoId) : null;}

	public static int toRepoId(@Nullable final DATEVExportId id) {return id != null ? id.getRepoId() : -1;}

	@Override
	@JsonValue
	public int getRepoId() {return repoId;}
}
