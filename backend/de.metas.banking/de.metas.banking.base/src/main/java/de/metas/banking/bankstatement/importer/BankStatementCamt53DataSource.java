/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.banking.bankstatement.importer;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.Util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

@Value
@Builder
public class BankStatementCamt53DataSource
{
	@NonNull
	public static BankStatementCamt53DataSource ofFile(@NonNull final String filename)
	{
		return builder()
				.filename(filename)
				.content(Util.readBytes(new File(filename)))
				.build();
	}
	
	@NonNull
	public InputStream getContentAsInputStream()
	{
		return new ByteArrayInputStream(content);
	}

	@NonNull
	String filename;

	byte[] content;
}
