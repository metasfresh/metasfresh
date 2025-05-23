/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package org.adempiere.archive.api;

import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.Value;
import org.compiere.model.I_AD_Archive;

import javax.annotation.Nullable;
import java.util.Optional;

@Value
@Builder
public class ArchiveResult
{
	public static final ArchiveResult EMPTY = ArchiveResult.builder().build();

	@Nullable
	I_AD_Archive archiveRecord;

	byte[] data;

	public boolean isNoArchive() { return archiveRecord == null; }

	public Optional<String> getName() { return archiveRecord == null ? Optional.empty() : StringUtils.trimBlankToOptional(archiveRecord.getName()); }
}
