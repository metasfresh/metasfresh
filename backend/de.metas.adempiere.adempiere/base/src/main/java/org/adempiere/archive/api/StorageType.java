package org.adempiere.archive.api;

import lombok.Getter;
import lombok.NonNull;

public enum StorageType
{
	Database("DB"),
	Filesystem("FS");

	@Getter
	private final String code;

	StorageType(@NonNull final String code)
	{
		this.code = code;
	}
}
