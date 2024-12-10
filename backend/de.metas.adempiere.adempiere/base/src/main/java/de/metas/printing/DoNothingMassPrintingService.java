package de.metas.printing;

import lombok.NonNull;
import org.adempiere.archive.api.ArchiveInfo;
import org.springframework.core.io.Resource;

public final class DoNothingMassPrintingService implements IMassPrintingService
{
	public static DoNothingMassPrintingService instance = new DoNothingMassPrintingService();

	private DoNothingMassPrintingService() {}

	@Override
	public void print(@NonNull final Resource reportData, @NonNull final ArchiveInfo archiveInfo) {}
}
