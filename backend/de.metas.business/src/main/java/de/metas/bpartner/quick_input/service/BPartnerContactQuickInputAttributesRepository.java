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

import de.metas.bpartner.attributes.BPartnerAttributes;
import de.metas.bpartner.quick_input.BPartnerContactQuickInputId;
import de.metas.bpartner.quick_input.service.attributes_record_adapter.BPContactQuickInputAttributesRecordAdapter;
import lombok.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class BPartnerContactQuickInputAttributesRepository
{
	private final BPContactQuickInputAttributesRecordAdapter attributesRecordAdapter = new BPContactQuickInputAttributesRecordAdapter();

	public BPartnerAttributes getByBPartnerContactQuickInputId(@NonNull final BPartnerContactQuickInputId bpartnerContactQuickInputId)
	{
		return BPartnerAttributes.builder()
				.attributesSet1(attributesRecordAdapter.getAttributes(bpartnerContactQuickInputId))
				.build();
	}

	public void saveAttributes(
			@NonNull final BPartnerAttributes bpartnerAttributes,
			@NonNull final BPartnerContactQuickInputId bpartnerContactQuickInputId)
	{
		attributesRecordAdapter.saveAttributes(bpartnerAttributes.getAttributesSet1(), bpartnerContactQuickInputId);
	}
}
