/*
 * #%L
 * de-metas-camel-shipping
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

package de.metas.camel.shipping;

import de.metas.common.filemaker.DATABASE;
import de.metas.common.filemaker.FMPXMLRESULT;
import de.metas.common.filemaker.FMPXMLRESULT.FMPXMLRESULTBuilder;
import de.metas.common.filemaker.PRODUCT;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.camel.Exchange;

@UtilityClass
public class JsonToXmlProcessorCommonUtil
{
	public FMPXMLRESULTBuilder createFmpxmlresultBuilder(
			final @NonNull Exchange exchange,
			final int numberOfItems)
	{
		final String databaseName = exchange.getContext().resolvePropertyPlaceholders("{{receiptCandidate.FMPXMLRESULT.DATABASE.NAME}}");

		return FMPXMLRESULT.builder()
				.errorCode("0")
				.product(new PRODUCT())
				.database(DATABASE.builder()
						.name(databaseName)
						.records(Integer.toString(numberOfItems))
						.build());
	}
}
