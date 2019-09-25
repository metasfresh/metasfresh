package de.metas.impexp.parser;

import org.adempiere.exceptions.AdempiereException;

import de.metas.impexp.format.ImpFormat;
import de.metas.impexp.format.ImpFormatType;
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

final class ImpDataLineParserFactory
{
	public ImpDataLineParser createParser(@NonNull final ImpFormat impFormat)
	{
		final ImpFormatType formatType = impFormat.getFormatType();
		if (ImpFormatType.FIXED_POSITION.equals(formatType))
		{
			return new FixedPositionImpDataLineParser(impFormat);
		}
		else if (ImpFormatType.COMMA_SEPARATED.equals(formatType)
				|| ImpFormatType.SEMICOLON_SEPARATED.equals(formatType)
				|| ImpFormatType.TAB_SEPARATED.equals(formatType))
		{
			return new FlexImpDataLineParser(impFormat);
		}
		else
		{
			throw new AdempiereException("Unsupported format type: " + formatType);
		}
	}
}
