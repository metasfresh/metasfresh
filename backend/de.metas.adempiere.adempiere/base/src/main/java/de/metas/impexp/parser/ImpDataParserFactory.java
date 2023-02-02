package de.metas.impexp.parser;

import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.parser.csv.CsvImpDataParserFactory;
import de.metas.impexp.parser.xls.Excel97ImpDataParser;
import de.metas.util.FileUtil;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class ImpDataParserFactory
{
	public ImpDataParser createParser(@NonNull final ImpFormat impFormat, @Nullable String filename)
	{
		final String fileExtension = FileUtil.getFileExtension(filename);

		if ("xls".equalsIgnoreCase(fileExtension))
		{
			return Excel97ImpDataParser.builder()
					.skipFirstNRows(impFormat.getSkipFirstNRows())
					.build();
		}
		else
		{
			return CsvImpDataParserFactory.createParser(impFormat);
		}
	}

}
