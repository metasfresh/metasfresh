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

import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;

public class SerialNoGenerator implements IComponentGenerator
{
	private transient final IDocumentNoBuilderFactory documentNoBuilder = SpringContextHolder.instance.getBean(IDocumentNoBuilderFactory.class);

	@Override
	public ImmutableAttributeSet generate(@NonNull final ComponentGeneratorContext context)
	{
		final int qty = context.getQty();
		Check.errorIf(qty != 1, "Only 1 SerialNo attributes exists, so 1 should be generated. Requested qty: {}", qty);

		final ImmutableAttributeSet existingAttributes = context.getExistingAttributes();
		if (!existingAttributes.hasAttribute(AttributeConstants.ATTR_SerialNo))
		{
			return ImmutableAttributeSet.EMPTY;
		}

		final String existingSerialNo = existingAttributes.getValueAsString(AttributeConstants.ATTR_SerialNo);
		if (Check.isNotBlank(existingSerialNo))
		{
			// don't override existing serial no
			return ImmutableAttributeSet.EMPTY;
		}

		final String serialNo = generateAndIncrementSerialNo(context.getSequenceId(), context.getClientId());

		return ImmutableAttributeSet.builder()
				.attributeValue(AttributeConstants.ATTR_SerialNo, serialNo)
				.build();
	}

	@NonNull
	private String generateAndIncrementSerialNo(
			@NonNull final DocSequenceId sequenceId,
			@NonNull final ClientId clientId)
	{
		final String serialNo = documentNoBuilder.forSequenceId(sequenceId)
				.setClientId(clientId)
				.setFailOnError(true)
				.build();
		if (serialNo == null || Check.isBlank(serialNo))
		{
			throw new AdempiereException("Could not generate next serial number for " + sequenceId);
		}

		return serialNo;
	}

	@Override
	public ComponentGeneratorParams getDefaultParameters()
	{
		return ComponentGeneratorParams.EMPTY;
	}
}
