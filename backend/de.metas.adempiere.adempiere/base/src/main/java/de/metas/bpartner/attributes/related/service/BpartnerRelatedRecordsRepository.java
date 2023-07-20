/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.attributes.related.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.attributes.BPartnerRelatedRecords;
import de.metas.bpartner.attributes.related.service.adapter.BPRelatedRecords1Adapter;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class BpartnerRelatedRecordsRepository
{
	private final BPRelatedRecords1Adapter attributes1RecordAdapter = new BPRelatedRecords1Adapter();

	public BPartnerRelatedRecords getByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return BPartnerRelatedRecords.builder()
				.relatedRecordIds1(attributes1RecordAdapter.getRecords(bpartnerId))
				.build();
	}

	public void saveRelatedRecords(
			@NonNull final BPartnerRelatedRecords relatedRecords,
			@NonNull final BPartnerId bpartnerId)
	{
		attributes1RecordAdapter.saveAttributes(relatedRecords.getRelatedRecordIds1(), bpartnerId);
	}
}
