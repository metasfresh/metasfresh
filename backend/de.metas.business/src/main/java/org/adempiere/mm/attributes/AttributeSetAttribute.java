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

package org.adempiere.mm.attributes;

import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * A single attribute from an attribute set. note that we don't have any values in there..this is not about an instance.
 */
@Value
@Builder
public class AttributeSetAttribute
{
	@NonNull AttributeId attributeId;
	int seqNo;
	@NonNull OptionalBoolean mandatoryOnReceipt;
	@NonNull OptionalBoolean mandatoryOnPicking;
	@NonNull OptionalBoolean mandatoryOnShipment;
}
