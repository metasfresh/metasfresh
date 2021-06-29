/*
 * #%L
 * de.metas.manufacturing
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

package de.metas.manufacturing.generatedcomponents;

import de.metas.util.Check;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

public class CopyAttributesFromBOMLineGenerator implements IComponentGenerator
{
	@Override
	public ImmutableAttributeSet generate(final ComponentGeneratorContext context)
	{
		final ImmutableAttributeSet existingAttributes = context.getExistingAttributes();
		final ImmutableAttributeSet bomLineAttributes = context.getBomLineAttributes();

		final ImmutableAttributeSet.Builder result = ImmutableAttributeSet.builder();
		for (final AttributeCode attributeCode : bomLineAttributes.getAttributeCodes())
		{
			if (context.isOverrideExistingValues()
					|| !existingAttributes.hasAttribute(attributeCode)
					|| Check.isBlank(existingAttributes.getValueAsString(attributeCode)))
			{
				result.attributeValue(attributeCode, bomLineAttributes.getValue(attributeCode));
			}
		}
		return result.build();
	}

	@Override
	public ComponentGeneratorParams getDefaultParameters()
	{
		return ComponentGeneratorParams.EMPTY;
	}
}
