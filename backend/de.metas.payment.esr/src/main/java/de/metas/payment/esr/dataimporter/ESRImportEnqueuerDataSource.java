package de.metas.payment.esr.dataimporter;

import java.io.File;

import org.compiere.util.Util;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
@Builder
public final class ESRImportEnqueuerDataSource
{
	public static final ESRImportEnqueuerDataSource ofFile(final String filename)
	{
		return builder()
				.filename(filename)
				.content(Util.readBytes(new File(filename)))
				.build();
	}

	@NonNull
	private final String filename;
	private final byte[] content;
	private final int attachmentEntryId;
}
