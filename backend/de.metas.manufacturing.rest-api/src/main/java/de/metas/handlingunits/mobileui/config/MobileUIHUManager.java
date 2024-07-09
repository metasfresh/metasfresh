/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.mobileui.config;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;

import java.util.Comparator;

@Value
@Builder
public class MobileUIHUManager
{
	public static final MobileUIHUManager DEFAULT = builder()
			.attributes(ImmutableList.of())
			.build();

	@NonNull ImmutableList<MobileUIHUManagerAttribute> attributes;
	
	@NonNull
	public ImmutableList<AttributeId> getSortedAttributeIds()
	{
		return attributes.stream()
				.sorted(Comparator.comparing(MobileUIHUManagerAttribute::getSeqNo))
				.map(MobileUIHUManagerAttribute::getAttributeId)
				.collect(ImmutableList.toImmutableList());
	}
}
