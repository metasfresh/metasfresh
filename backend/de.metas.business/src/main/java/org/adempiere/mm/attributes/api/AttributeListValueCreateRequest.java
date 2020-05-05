package org.adempiere.mm.attributes.api;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValueTrxRestriction;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class AttributeListValueCreateRequest
{
	@NonNull
	AttributeId attributeId;

	@Nullable
	String value;

	@NonNull
	String name;

	@Nullable
	String description;

	@NonNull
	@Default
	AttributeListValueTrxRestriction availableForTrx = AttributeListValueTrxRestriction.ANY_TRANSACTION;

	@Default
	boolean active = true;
}
