package de.metas.archive;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_AD_Archive_Storage;

@RequiredArgsConstructor
@Getter
public enum ArchiveStorageType implements ReferenceListAwareEnum
{
	DATABASE(X_AD_Archive_Storage.TYPE_Database),
	FILE_SYSTEM(X_AD_Archive_Storage.TYPE_FileSystem),
	;
	
	private static final ReferenceListAwareEnums.ValuesIndex<ArchiveStorageType> index = ReferenceListAwareEnums.index(values());

	@NonNull final String code;
	
	public static ArchiveStorageType ofCode(@NonNull String code) { return index.ofCode(code);}
}
