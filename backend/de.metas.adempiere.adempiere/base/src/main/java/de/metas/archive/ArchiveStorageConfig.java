package de.metas.archive;

import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.nio.file.Path;

@Value
public class ArchiveStorageConfig
{
	@NonNull ArchiveStorageConfigId id;
	@NonNull ArchiveStorageType type;
	@Nullable Filesystem filesystem;

	@Builder
	private ArchiveStorageConfig(
			@NonNull final ArchiveStorageConfigId id,
			@NonNull final ArchiveStorageType type,
			@Nullable final Filesystem filesystem)
	{
		this.id = id;
		this.type = type;
		switch (type)
		{
			case DATABASE -> this.filesystem = null;
			case FILE_SYSTEM -> this.filesystem = Check.assumeNotNull(filesystem, "filesystem");
			default -> throw new AdempiereException("Unknown archive storage type: " + type);
		}
	}

	public Filesystem getFileSystemNotNull() {return Check.assumeNotNull(getFilesystem(), "filesystem not set");}

	//
	//
	//
	//
	//

	@Value
	@Builder
	public static class Filesystem
	{
		@NonNull Path path;
	}
}
