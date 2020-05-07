package de.metas.payment.esr.dataimporter;

import java.io.InputStream;

import org.adempiere.util.Check;

import de.metas.payment.esr.dataimporter.impl.camt54.ESRDataImporterCamt54;
import de.metas.payment.esr.dataimporter.impl.v11.ESRDataImporterV11;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.X_ESR_Import;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class ESRDataLoaderFactory
{
	public IESRDataImporter createImporter(
			@NonNull final I_ESR_Import header,
			@NonNull final InputStream input)
	{
		if (X_ESR_Import.DATATYPE_Camt54.equalsIgnoreCase(header.getDataType()))
		{
			return new ESRDataImporterCamt54(header, input);
		}
		else if (X_ESR_Import.DATATYPE_V11.equalsIgnoreCase(header.getDataType()))
		{
			return new ESRDataImporterV11(input);
		}

		Check.errorIf(true, "The given ESR_Import has unexpected DataType={}; header={}", header.getDataType(), header);
		return null;
	}

	/**
	 * Tries to guess the {@link I_ESR_Import#COLUMN_DataType} from the given {@code fileName}. May return {@code null}.
	 *
	 * @param fileName
	 * @return
	 */
	public String guessTypeFromFileName(@NonNull final String fileName)
	{
		final boolean isV11FileNameEnding = fileName.matches("(?i).*\\.v11");
		if (isV11FileNameEnding)
		{
			return X_ESR_Import.DATATYPE_V11;
		}

		final boolean isCamtFileNameEnding = fileName.matches("(?i).*\\.(xml|camt|camt\\.?54)");
		if (isCamtFileNameEnding)
		{
			return X_ESR_Import.DATATYPE_Camt54;
		}

		return null;
	}
}
