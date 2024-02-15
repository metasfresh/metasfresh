package de.metas.handlingunits.attribute;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;

import java.util.Optional;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value(staticConstructor = "of")
@ToString
public class HUAndPIAttributes
{
	@NonNull ImmutableList<I_M_HU_Attribute> huAttributes;
	@NonNull PIAttributes piAttributes;

	public Optional<I_M_HU_Attribute> getHUAttributeById(@NonNull final AttributeId attributeId)
	{
		return huAttributes.stream()
				.filter(huAttribute -> huAttribute.getM_Attribute_ID() == attributeId.getRepoId())
				.findFirst();
	}
}
