package de.metas.archive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
public class ArchiveStorageConfigId implements RepoIdAware
{
	public static final ArchiveStorageConfigId DATABASE = new ArchiveStorageConfigId(540000);

	int repoId;

	private ArchiveStorageConfigId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, "AD_Archive_Storage_ID");
	}

	@JsonCreator
	public static ArchiveStorageConfigId ofRepoId(final int repoId)
	{
		if (repoId == DATABASE.repoId)
		{
			return DATABASE;
		}
		else
		{
			return new ArchiveStorageConfigId(repoId);
		}
	}

	@Nullable
	public static ArchiveStorageConfigId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? ofRepoId(repoId) : null;
	}

	public static Optional<ArchiveStorageConfigId> optionalOfRepoId(final int repoId)
	{
		return Optional.ofNullable(ArchiveStorageConfigId.ofRepoIdOrNull(repoId));
	}

	public static int toRepoId(@Nullable final ArchiveStorageConfigId ArchiveStorageConfigId)
	{
		return ArchiveStorageConfigId != null ? ArchiveStorageConfigId.getRepoId() : -1;
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}

}
