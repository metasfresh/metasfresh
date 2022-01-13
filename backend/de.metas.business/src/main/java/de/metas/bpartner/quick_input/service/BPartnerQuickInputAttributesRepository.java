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

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.attributes.BPartnerAttributes;
import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import de.metas.bpartner.quick_input.service.attributes_record_adapter.BPQuickInputAttributes1RecordAdapter;
import de.metas.bpartner.quick_input.service.attributes_record_adapter.BPQuickInputAttributes2RecordAdapter;
import de.metas.bpartner.quick_input.service.attributes_record_adapter.BPQuickInputAttributes3RecordAdapter;
import de.metas.bpartner.quick_input.service.attributes_record_adapter.BPQuickInputAttributes4RecordAdapter;
import de.metas.bpartner.quick_input.service.attributes_record_adapter.BPQuickInputAttributes5RecordAdapter;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class BPartnerQuickInputAttributesRepository
{
	private final BPQuickInputAttributes1RecordAdapter attributes1RecordAdapter = new BPQuickInputAttributes1RecordAdapter();
	private final BPQuickInputAttributes2RecordAdapter attributes2RecordAdapter = new BPQuickInputAttributes2RecordAdapter();
	private final BPQuickInputAttributes3RecordAdapter attributes3RecordAdapter = new BPQuickInputAttributes3RecordAdapter();
	private final BPQuickInputAttributes4RecordAdapter attributes4RecordAdapter = new BPQuickInputAttributes4RecordAdapter();
	private final BPQuickInputAttributes5RecordAdapter attributes5RecordAdapter = new BPQuickInputAttributes5RecordAdapter();

	public BPartnerAttributes getByBPartnerQuickInputId(@NonNull final BPartnerQuickInputId bpartnerQuickInputId)
	{
		return BPartnerAttributes.builder()
				.attributesSet1(attributes1RecordAdapter.getAttributes(bpartnerQuickInputId))
				.attributesSet2(attributes2RecordAdapter.getAttributes(bpartnerQuickInputId))
				.attributesSet3(attributes3RecordAdapter.getAttributes(bpartnerQuickInputId))
				.attributesSet4(attributes4RecordAdapter.getAttributes(bpartnerQuickInputId))
				.attributesSet5(attributes5RecordAdapter.getAttributes(bpartnerQuickInputId))
				.build();
	}

	public void saveAttributes(
			@NonNull final BPartnerAttributes bpartnerAttributes,
			@NonNull final BPartnerQuickInputId bpartnerQuickInputId)
	{
		attributes1RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet1(), bpartnerQuickInputId);
		attributes2RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet2(), bpartnerQuickInputId);
		attributes3RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet3(), bpartnerQuickInputId);
		attributes4RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet4(), bpartnerQuickInputId);
		attributes5RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet5(), bpartnerQuickInputId);
	}

}
