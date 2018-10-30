package de.metas.dunning_gateway.spi;

import java.util.Optional;

import de.metas.dunning_gateway.spi.model.DunningToExport;

/*
 * #%L
 * metasfresh-invoice.gateway.commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

/** SPI to extend for different formats and protocols. */
public interface DunningExportClientFactory
{
	public static final String ATTATCHMENT_TAGNAME_EXPORT_PROVIDER = "DunningExportProviderId";

	String getDunningExportProviderId();

	/** @return empty if the given factory can't provide an export client */
	Optional<DunningExportClient> newClientForDunning(DunningToExport invoice);
}
