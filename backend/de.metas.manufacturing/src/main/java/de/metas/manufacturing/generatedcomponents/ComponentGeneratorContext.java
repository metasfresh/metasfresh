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
import de.metas.document.sequence.DocSequenceId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;

import java.util.List;

@Value
@Builder
public class ComponentGeneratorContext
{
	int qty;
	@NonNull ImmutableAttributeSet existingAttributes;
	@NonNull ComponentGeneratorParams parameters;
	@NonNull ClientId clientId;

	@NonNull
	public DocSequenceId getSequenceId()
	{
		return getParameters()
				.getSequenceId()
				.orElseThrow(() -> new AdempiereException("Sequence shall be configured"));
	}

	public ImmutableList<AttributeCode> computeRemainingAttributesToGenerate(@NonNull final AttributeCode... supportedAttributes)
	{
		return computeRemainingAttributesToGenerate(ImmutableList.copyOf(supportedAttributes));
	}

	public ImmutableList<AttributeCode> computeRemainingAttributesToGenerate(@NonNull final List<AttributeCode> supportedAttributes)
	{
		final ImmutableList.Builder<AttributeCode> attributesLeftToGenerate = ImmutableList.builder();
		for (final AttributeCode attr : supportedAttributes)
		{
			if (existingAttributes.hasAttribute(attr) && Check.isEmpty(existingAttributes.getValueAsString(attr)))
			{
				attributesLeftToGenerate.add(attr);
			}
		}
		return attributesLeftToGenerate.build();
	}

}
