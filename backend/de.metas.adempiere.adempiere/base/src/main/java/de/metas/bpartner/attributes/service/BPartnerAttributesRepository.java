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

package de.metas.bpartner.attributes.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.attributes.BPartnerAttributes;
import de.metas.bpartner.attributes.service.attributes_record_adapter.BPAttributes1RecordAdapter;
import de.metas.bpartner.attributes.service.attributes_record_adapter.BPAttributes2RecordAdapter;
import de.metas.bpartner.attributes.service.attributes_record_adapter.BPAttributes3RecordAdapter;
import de.metas.bpartner.attributes.service.attributes_record_adapter.BPAttributes4RecordAdapter;
import de.metas.bpartner.attributes.service.attributes_record_adapter.BPAttributes5RecordAdapter;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class BPartnerAttributesRepository
{
	private final BPAttributes1RecordAdapter attributes1RecordAdapter = new BPAttributes1RecordAdapter();
	private final BPAttributes2RecordAdapter attributes2RecordAdapter = new BPAttributes2RecordAdapter();
	private final BPAttributes3RecordAdapter attributes3RecordAdapter = new BPAttributes3RecordAdapter();
	private final BPAttributes4RecordAdapter attributes4RecordAdapter = new BPAttributes4RecordAdapter();
	private final BPAttributes5RecordAdapter attributes5RecordAdapter = new BPAttributes5RecordAdapter();

	public BPartnerAttributes getByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return BPartnerAttributes.builder()
				.attributesSet1(attributes1RecordAdapter.getAttributes(bpartnerId))
				.attributesSet2(attributes2RecordAdapter.getAttributes(bpartnerId))
				.attributesSet3(attributes3RecordAdapter.getAttributes(bpartnerId))
				.attributesSet4(attributes4RecordAdapter.getAttributes(bpartnerId))
				.attributesSet5(attributes5RecordAdapter.getAttributes(bpartnerId))
				.build();
	}

	public void saveAttributes(
			@NonNull final BPartnerAttributes bpartnerAttributes,
			@NonNull final BPartnerId bpartnerId)
	{
		attributes1RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet1(), bpartnerId);
		attributes2RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet2(), bpartnerId);
		attributes3RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet3(), bpartnerId);
		attributes4RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet4(), bpartnerId);
		attributes5RecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet5(), bpartnerId);
	}
}
