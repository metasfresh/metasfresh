/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.window.descriptor.sql.productLookup;

import de.metas.bpartner.BPartnerId;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseQuery;
import de.metas.ui.web.material.adapter.AvailabilityInfoResultForWebui;
import de.metas.ui.web.material.adapter.AvailableToPromiseAdapter;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;
/**
 * Note: ATP stands for "available to promise"
 */
public class ATPProductLookupEnricher
{
	final @Nullable
	BPartnerId bpartnerId;
	@NonNull
	final ZonedDateTime dateOrNull;
	private final AvailableToPromiseAdapter availableToPromiseAdapter;

	@Builder
	public ATPProductLookupEnricher(
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ZonedDateTime dateOrNull,
			@NonNull final AvailableToPromiseAdapter availableToPromiseAdapter)

	{
		this.bpartnerId = bpartnerId;
		this.dateOrNull = dateOrNull;
		this.availableToPromiseAdapter = availableToPromiseAdapter;
	}

	public List<AvailabilityInfoResultForWebui.Group> getAvailabilityInfoGroups(@NonNull final LookupValuesList productLookupValues)
	{
		final AvailableToPromiseQuery query = AvailableToPromiseQuery.builder()
				.productIds(productLookupValues.getKeysAsInt())
				.storageAttributesKeyPatterns(availableToPromiseAdapter.getPredefinedStorageAttributeKeys())
				.date(dateOrNull)
				.bpartner(BPartnerClassifier.specificOrNone(bpartnerId))
				.build();
		final AvailabilityInfoResultForWebui availableStock = availableToPromiseAdapter.retrieveAvailableStock(query);
		return availableStock.getGroups();
	}

}
