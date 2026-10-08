package de.metas.handlingunits.attribute;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

@Value
@Builder
public class HUAttributeUpdateRequest
{
	/** Attribute to set on the targeted HU(s); HUs without the attribute are skipped silently. */
	@NonNull AttributeCode attributeCode;

	/** Value to write. {@code null} clears the attribute. */
	@Nullable Object attributeValue;

	/** When set, only HUs whose {@code HUStatus} matches are updated; recursive iteration still descends through all children. */
	@Nullable String onlyHUStatus;

	/** When {@code true}, HUs that already carry a non-empty value are left untouched. */
	@Builder.Default boolean onlyIfNotSet = false;
}
