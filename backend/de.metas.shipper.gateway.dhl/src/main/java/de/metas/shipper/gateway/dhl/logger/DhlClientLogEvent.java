/*
 * #%L
 * de.metas.shipper.gateway.dhl
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

package de.metas.shipper.gateway.dhl.logger;

import com.google.common.annotations.VisibleForTesting;
import de.metas.shipper.gateway.dhl.model.DhlClientConfig;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.oxm.Marshaller;
import org.springframework.xml.transform.StringResult;

import javax.annotation.Nullable;

@Value
@Builder
public class DhlClientLogEvent
{
	/**
	 * Regexp explanation: .*? means ungreedy (while .* means greedy).
	 */
	public static final String LABEL_DATA_REGEX = "(?m)<labelData>(.*?\\s*?)</labelData>";
	public static final String LABEL_DATA_REPLACEMENT_TEXT = "<labelData>PDF TEXT REMOVED!</labelData>";

	int deliveryOrderRepoId;
	DhlClientConfig config;
	Marshaller marshaller;
	Object requestElement;

	@Nullable
	Object responseElement;

	@Nullable
	Exception responseException;

	long durationMillis;

	String getConfigSummary()
	{
		return config != null ? config.toString() : "";
	}

	@Nullable
	String getRequestAsString()
	{
		return elementToString(requestElement);
	}

	@Nullable
	String getResponseAsString()
	{
		return elementToString(responseElement);
	}

	@Nullable
	private String elementToString(@Nullable final Object element)
	{
		if (element == null)
		{
			return null;
		}

		try
		{
			final StringResult result = new StringResult();
			marshaller.marshal(element, result);

			return cleanupPdfData(result.toString());
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting " + element + " to String", ex);
		}
	}

	/**
	 * remove the pdfdata since it's long and useless and we also attach it to the PO record
	 */
	@NonNull
	@VisibleForTesting
	static String cleanupPdfData(@NonNull final String s)
	{
		return s.replaceAll(LABEL_DATA_REGEX, LABEL_DATA_REPLACEMENT_TEXT);
	}

}
