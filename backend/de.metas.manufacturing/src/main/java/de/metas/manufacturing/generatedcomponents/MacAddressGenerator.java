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
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoParts;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * A MAC Address looks like this: 01:23:45:67:89:AB
 */
public class MacAddressGenerator implements IComponentGenerator
{
	private static final int NUMBER_OF_DIGITS = 12;

	private static final String EMPTY_MAC_ADDRESS = "-";

	private final ImmutableList<AttributeCode> supportedAttributes = ImmutableList.of(
			AttributeConstants.RouterMAC1,
			AttributeConstants.RouterMAC2,
			AttributeConstants.RouterMAC3,
			AttributeConstants.RouterMAC4,
			AttributeConstants.RouterMAC5,
			AttributeConstants.RouterMAC6,
			AttributeConstants.RouterMAC7,
			AttributeConstants.RouterMAC8
	);

	private final IDocumentNoBuilderFactory documentNoBuilder;

	// IMPORTANT: this ctor is required for the API to instantiate this class
	public MacAddressGenerator()
	{
		this(SpringContextHolder.instance.getBean(IDocumentNoBuilderFactory.class));
	}

	@VisibleForTesting
	MacAddressGenerator(
			@NonNull final IDocumentNoBuilderFactory documentNoBuilder)
	{
		this.documentNoBuilder = documentNoBuilder;
	}

	@Override
	public ImmutableAttributeSet generate(@NonNull final ComponentGeneratorContext context)
	{
		final int qty = context.getQty();
		Check.errorIf(qty < 1 || qty > supportedAttributes.size(),
					  "Qty of Mac Addresses should be between 1 and {} but it was {}", supportedAttributes.size(), qty);

		//
		// Count how many attributes were already generated.
		// Identify which are the attributes which are not already generated (the free slots).
		int countAlreadyGenerated = 0;
		final ArrayList<AttributeCode> attributesAvailableToGenerate = new ArrayList<>();
		final ImmutableAttributeSet existingAttributes = context.getExistingAttributes();
		for (final AttributeCode attributeCode : supportedAttributes)
		{
			if (!existingAttributes.hasAttribute(attributeCode))
			{
				continue;
			}

			final boolean alreadyGenerated = !context.isOverrideExistingValues()
					&& Check.isNotBlank(existingAttributes.getValueAsString(attributeCode));
			if (alreadyGenerated)
			{
				countAlreadyGenerated++;
			}
			else
			{
				attributesAvailableToGenerate.add(attributeCode);
			}
		}

		//
		// Compute how much we still have to generate.
		final int countRemainingToGenerate = qty - countAlreadyGenerated;
		if (countRemainingToGenerate <= 0)
		{
			return ImmutableAttributeSet.EMPTY;
		}
		final int availableSize = attributesAvailableToGenerate.size();
		if (countRemainingToGenerate > availableSize)
		{
			throw new AdempiereException("We still have to generate " + countRemainingToGenerate + " but only " + attributesAvailableToGenerate + " are available and not already generated");
		}

		//
		// Generate the remaining ones
		final ImmutableAttributeSet.Builder result = ImmutableAttributeSet.builder();
		for (int i = 0; i < countRemainingToGenerate; i++)
		{
			final AttributeCode attributeCode = attributesAvailableToGenerate.get(i);
			final DocumentNoParts documentNoParts = getAndIncrementSequence(context.getSequenceId(), context.getClientId());
			final MacAddress macAddress = toMacAddress(documentNoParts);

			result.attributeValue(attributeCode, macAddress.getAddress());
		}

		for (int i = countRemainingToGenerate; i < availableSize; i++)
		{
			final AttributeCode attributeCode = attributesAvailableToGenerate.get(i);
			result.attributeValue(attributeCode, EMPTY_MAC_ADDRESS);
		}

		return result.build();
	}

	@Override
	public ComponentGeneratorParams getDefaultParameters()
	{
		return ComponentGeneratorParams.EMPTY;
	}

	@NonNull
	@VisibleForTesting
	DocumentNoParts getAndIncrementSequence(
			@NonNull final DocSequenceId macAddressSequenceId,
			@NonNull final ClientId clientId)
	{
		final DocumentNoParts documentNoParts = documentNoBuilder.forSequenceId(macAddressSequenceId)
				.setClientId(clientId)
				.setFailOnError(true)
				.buildParts();
		if (documentNoParts == null)
		{
			throw new AdempiereException("Could not retrieve next sequence number.");
		}
		return documentNoParts;
	}

	@VisibleForTesting
	@NonNull
	static MacAddress toMacAddress(@NonNull final DocumentNoParts documentNoParts)
	{
		final String groupSeparator = detectGroupSeparator(documentNoParts.getPrefix());
		final String prefix = StringUtils.nullToEmpty(StringUtils.replace(documentNoParts.getPrefix(), groupSeparator, ""));
		final String sequenceNo;

		{
			final String sequenceNumberHexa;

			try
			{
				final int unsignedInt = Integer.parseUnsignedInt(documentNoParts.getSequenceNumber());
				sequenceNumberHexa = Integer.toHexString(unsignedInt);
			}
			catch (final NumberFormatException e)
			{
				throw new AdempiereException("Wrong sequence format");
			}

			final int prefixLen = prefix.length();
			final int neededDigits = NUMBER_OF_DIGITS - prefixLen;
			sequenceNo = Strings.padStart(sequenceNumberHexa, neededDigits, '0');
		}

		final String result = StringUtils.insertSeparatorEveryNCharacters(prefix + sequenceNo, groupSeparator, 2);

		return MacAddress.of(result.toUpperCase());
	}

	@NonNull
	private static String detectGroupSeparator(@Nullable final String prefix)
	{
		if (prefix == null)
		{
			return MacAddress.DEFAULT_GROUP_SEPARATOR;
		}

		if (prefix.contains(":"))
		{
			return ":";
		}

		if (prefix.contains("-"))
		{
			return "-";
		}

		return MacAddress.DEFAULT_GROUP_SEPARATOR;
	}
}
