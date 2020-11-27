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
import com.google.common.collect.ImmutableMap;
import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoParts;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import static de.metas.manufacturing.generatedcomponents.ComponentGeneratorUtil.PARAM_AD_SEQUENCE_ID;

/**
 * A MAC Address looks like this: 01:23:45:67:89:AB
 */
@Service
public class MacAddressGenerator implements IComponentGenerator
{
	private static final ImmutableMap<String, String> DEFAULT_PARAMETERS = ImmutableMap.<String, String>builder().build();
	private static final int NUMBER_OF_DIGITS = 12;

	private final ImmutableList<AttributeCode> supportedAttributes = ImmutableList.of(
			AttributeConstants.RouterMAC1,
			AttributeConstants.RouterMAC2,
			AttributeConstants.RouterMAC3,
			AttributeConstants.RouterMAC4,
			AttributeConstants.RouterMAC5,
			AttributeConstants.RouterMAC6
	);

	private transient final IDocumentNoBuilderFactory documentNoBuilder = SpringContextHolder.instance.getBean(IDocumentNoBuilderFactory.class);

	@Override
	public ImmutableAttributeSet generate(final int qty, final @NonNull ImmutableAttributeSet existingAttributes, final @NonNull ComponentGeneratorParams parameters)
	{
		Check.errorIf(qty < 1 || qty > 6, "Qty of Mac Addresses should be between 1 and 6. Requested qty: {}", qty);

		final ImmutableList<AttributeCode> attributesToGenerate = ComponentGeneratorUtil.computeRemainingAttributesToGenerate(existingAttributes, supportedAttributes);

		if (attributesToGenerate.isEmpty())
		{
			return ImmutableAttributeSet.EMPTY;
		}

		/*
			Explanation: we have to generate 2 mac addresses, but 2 or more are already generated => nothing to do.
		 */
		if (qty - noOfAlreadyGenerated(attributesToGenerate) <= 0)
		{
			return ImmutableAttributeSet.EMPTY;
		}

		final DocSequenceId sequenceId = DocSequenceId.ofRepoIdOrNull(StringUtils.toIntegerOrZero(parameters.getValue(PARAM_AD_SEQUENCE_ID)));
		if (sequenceId == null)
		{
			throw new AdempiereException("Mandatory parameter " + PARAM_AD_SEQUENCE_ID + " has invalid value.");
		}

		final ImmutableAttributeSet.Builder result = ImmutableAttributeSet.builder();

		for (int i = 0; i < qty - noOfAlreadyGenerated(attributesToGenerate); i++)
		{
			final AttributeCode attributeCode = attributesToGenerate.get(i);
			final MacAddress macAddress = generateNextMacAddress(sequenceId);
			result.attributeValue(attributeCode, macAddress.getAddress());
		}

		return result.build();
	}

	private int noOfAlreadyGenerated(@NonNull final ImmutableList<AttributeCode> attributesToGenerate)
	{
		return supportedAttributes.size() - attributesToGenerate.size();
	}

	@Override
	public ImmutableMap<String, String> getDefaultParameters()
	{
		return DEFAULT_PARAMETERS;
	}

	@NonNull
	private MacAddress generateNextMacAddress(@NonNull final DocSequenceId macAddressSequenceId)
	{
		final IDocumentNoBuilder sequenceGenerator = documentNoBuilder.forSequenceId(macAddressSequenceId)
				.setClientId(Env.getClientId())
				.setFailOnError(true);

		final DocumentNoParts documentNoParts = sequenceGenerator.buildParts();

		if (documentNoParts == null)
		{
			throw new AdempiereException("Could not retrieve next sequence number. Maybe mandatory parameter " + PARAM_AD_SEQUENCE_ID + " has invalid value?");
		}

		return generateNextMacAddress0(documentNoParts);
	}

	@VisibleForTesting
	@NonNull
	MacAddress generateNextMacAddress0(@NonNull final DocumentNoParts documentNoParts)
	{
		final String GROUP_DELIMITER = detectGroupDelimiter(documentNoParts.getPrefix(), MacAddress.GROUP_DELIMITER);
		final String prefix = StringUtils.nullToEmpty(StringUtils.replace(documentNoParts.getPrefix(), GROUP_DELIMITER, ""));
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

		final StringBuilder result = new StringBuilder(prefix + sequenceNo);
		{
			final int initialStep = 2;
			int i = initialStep;
			while (i < result.length())
			{
				result.insert(i, GROUP_DELIMITER);
				i += initialStep + GROUP_DELIMITER.length();
			}
		}

		return MacAddress.of(result.toString().toUpperCase());
	}

	@NonNull
	private String detectGroupDelimiter(@Nullable final String prefix, @SuppressWarnings("SameParameterValue") @NonNull final String defaultGroupDelimiter)
	{
		if (prefix == null)
		{
			return defaultGroupDelimiter;
		}

		if (prefix.contains(":"))
		{
			return ":";
		}

		if (prefix.contains("-"))
		{
			return "-";
		}

		return defaultGroupDelimiter;
	}
}
