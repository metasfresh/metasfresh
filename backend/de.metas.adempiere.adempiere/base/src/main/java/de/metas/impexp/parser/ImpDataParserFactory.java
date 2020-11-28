package de.metas.impexp.parser;

import de.metas.impexp.format.ImpFormat;
import lombok.NonNull;

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
	private final ImpDataLineParserFactory lineParserFactory = new ImpDataLineParserFactory();

	public ImpDataParser createParser(@NonNull final ImpFormat impFormat)
	{
		final ImpDataLineParser lineParser = lineParserFactory.createParser(impFormat);

		return ImpDataParser.builder()
				.multiline(impFormat.isMultiLine())
				.lineParser(lineParser)
				.charset(impFormat.getCharset())
				.skipFirstNRows(impFormat.getSkipFirstNRows())
				.build();
	}

}
