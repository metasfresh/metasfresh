/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.qrcodes.model;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;

import javax.annotation.Nullable;

@Value
@Builder
public class QRCodeConfiguration
{
	@NonNull QRCodeConfigurationId id;
	@NonNull String name;
	@Getter(AccessLevel.NONE) boolean isOneQrCodeForAggregatedHUs;
	@Getter(AccessLevel.NONE) boolean isOneQrCodeForMatchingAttributes;

	@Nullable ImmutableSet<AttributeId> groupByAttributeIds;

	public boolean isGroupingByAttributesEnabled()
	{
		return isOneQrCodeForMatchingAttributes && !Check.isEmpty(groupByAttributeIds);
	}

	public boolean isOneQRCodeForAggregatedTUsEnabled()
	{
		return isOneQrCodeForAggregatedHUs || isGroupingByAttributesEnabled();
	}
}
