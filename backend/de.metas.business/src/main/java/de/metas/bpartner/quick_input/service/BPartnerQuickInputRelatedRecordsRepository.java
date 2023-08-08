/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.quick_input.service;

import de.metas.bpartner.attributes.BPartnerRelatedRecords;
import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import de.metas.bpartner.quick_input.service.attributes_record_adapter.BPQuickInputRelatedRecords1RecordAdapter;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class BPartnerQuickInputRelatedRecordsRepository
{
	private final BPQuickInputRelatedRecords1RecordAdapter attributes1RecordAdapter = new BPQuickInputRelatedRecords1RecordAdapter();

	public BPartnerRelatedRecords getByBPartnerQuickInputId(@NonNull final BPartnerQuickInputId bpartnerQuickInputId)
	{
		return BPartnerRelatedRecords.builder()
				.relatedRecordIds1(attributes1RecordAdapter.getRecords(bpartnerQuickInputId))
				.build();
	}

	public void saveAttributes(
			@NonNull final BPartnerRelatedRecords relatedRecords,
			@NonNull final BPartnerQuickInputId bpartnerQuickInputId)
	{
		attributes1RecordAdapter.saveAttributes(relatedRecords.getRelatedRecordIds1(), bpartnerQuickInputId);
	}

}
