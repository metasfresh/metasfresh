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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

@UtilityClass
public class ComponentGeneratorUtil
{
	@VisibleForTesting
	static final String PARAM_AD_SEQUENCE_ID = "AD_Sequence_ID";

	ImmutableList<AttributeCode> computeRemainingAttributesToGenerate(@NonNull final ImmutableAttributeSet existingAttributes, @NonNull final ImmutableList<AttributeCode> supportedAttributes)
	{
		final ImmutableList.Builder<AttributeCode> attributesLeftToGenerate = ImmutableList.builder();
		for (final AttributeCode attr : supportedAttributes)
		{
			if (existingAttributes.hasAttribute(attr) && existingAttributes.getValueAsString(attr) == null)
			{
				attributesLeftToGenerate.add(attr);
			}
		}
		return attributesLeftToGenerate.build();
	}
}
