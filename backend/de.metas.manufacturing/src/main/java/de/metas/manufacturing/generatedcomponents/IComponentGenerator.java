/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.manufacturing.generatedcomponents;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

public interface IComponentGenerator
{

	ImmutableAttributeSet generate(int qty, @NonNull ComponentGeneratorParam parameters, @NonNull ImmutableAttributeSet existingAttributes);

	ImmutableList<AttributeCode> getSupportedAttributes();

	ImmutableMap<String, String> getDefaultParameters();

	 // TODO tbp: does this make sense for MAC?
	default ImmutableList<AttributeCode> computeRemainingAttributesToGenerate(final @NonNull ImmutableAttributeSet existingAttributes)
	{
		final ImmutableList.Builder<AttributeCode> attributesLeftToGenerate = ImmutableList.builder();
		for (final AttributeCode attr : getSupportedAttributes())
		{
			if (existingAttributes.hasAttribute(attr) && existingAttributes.getValueAsString(attr) == null)
			{
				attributesLeftToGenerate.add(attr);
			}
		}
		return attributesLeftToGenerate.build();

	}
}
