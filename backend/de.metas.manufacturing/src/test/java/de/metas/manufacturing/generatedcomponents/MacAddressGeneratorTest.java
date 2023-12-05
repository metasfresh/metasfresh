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
import de.metas.document.sequence.impl.DocumentNoParts;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

class MacAddressGeneratorTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Nested
	public class toMacAddress
	{
		@Test
		void prefixHasColon()
		{
			final DocumentNoParts documentNoParts = DocumentNoParts.builder()
					.prefix("BB:")
					.suffix("")
					.sequenceNumber("1")
					.build();

			assertThat(MacAddressGenerator.toMacAddress(documentNoParts)).isEqualTo(MacAddress.of("BB:00:00:00:00:01"));
		}

		@Test
		void prefixNoColon()
		{
			final DocumentNoParts documentNoParts = DocumentNoParts.builder()
					.prefix("BB:A")
					.suffix("")
					.sequenceNumber("1234")
					.build();

			assertThat(MacAddressGenerator.toMacAddress(documentNoParts)).isEqualTo(MacAddress.of("BB:A0:00:00:04:D2"));
		}

		@Test
		void dashGroupDelimiter()
		{
			final DocumentNoParts documentNoParts = DocumentNoParts.builder()
					.prefix("BB-A")
					.suffix("")
					.sequenceNumber("1234")
					.build();

			assertThat(MacAddressGenerator.toMacAddress(documentNoParts)).isEqualTo(MacAddress.of("BB-A0-00-00-04-D2"));
		}

		@Test
		void noPrefix()
		{
			final DocumentNoParts documentNoParts = DocumentNoParts.builder()
					.prefix("")
					.suffix("")
					.sequenceNumber("1234321")
					.build();

			assertThat(MacAddressGenerator.toMacAddress(documentNoParts)).isEqualTo(MacAddress.of("00:00:00:12:D5:91"));
		}
	}

	@Builder(builderMethodName = "context", builderClassName = "$contextBuilder")
	private ComponentGeneratorContext createContext(
			@NonNull final Integer qty,
			@Nullable final String alreadySetMAC1,
			@Nullable final String alreadySetMAC2)
	{
		final ImmutableAttributeSet.Builder existingAttributes = ImmutableAttributeSet.builder();
		if (alreadySetMAC1 != null)
		{
			existingAttributes.attributeValue(AttributeConstants.RouterMAC1, alreadySetMAC1);
		}
		if (alreadySetMAC2 != null)
		{
			existingAttributes.attributeValue(AttributeConstants.RouterMAC2, alreadySetMAC2);
		}

		return ComponentGeneratorContext.builder()
				.qty(qty)
				.bomLineAttributes(ImmutableAttributeSet.EMPTY)
				.existingAttributes(existingAttributes.build())
				.parameters(ComponentGeneratorParams.builder()
						.sequenceId(DocSequenceId.ofRepoId(123456))
						.build())
				.clientId(ClientId.METASFRESH) // irrelevant
				.build();
	}

	@Nested
	public static class generate
	{
		@BeforeEach
		void beforeEach()
		{
			mkAttribute(AttributeConstants.RouterMAC1);
			mkAttribute(AttributeConstants.RouterMAC2);
			mkAttribute(AttributeConstants.RouterMAC3);
			mkAttribute(AttributeConstants.RouterMAC4);
			mkAttribute(AttributeConstants.RouterMAC5);
			mkAttribute(AttributeConstants.RouterMAC6);
			mkAttribute(AttributeConstants.RouterMAC7);
			mkAttribute(AttributeConstants.RouterMAC8);
		}

		private void mkAttribute(final AttributeCode attributeCode)
		{
			final I_M_Attribute po = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
			po.setName(attributeCode.getCode());
			po.setValue(attributeCode.getCode());
			InterfaceWrapperHelper.save(po);
		}

		private MacAddressGenerator newMacAddressGenerator(@Nullable final String prefix, final int startNo)
		{
			final AtomicInteger next = new AtomicInteger(startNo);

			// we won't use it but we need it as non null param
			final IDocumentNoBuilderFactory documentNoBuilderFactory = Mockito.mock(IDocumentNoBuilderFactory.class);
			return new MacAddressGenerator(documentNoBuilderFactory)
			{
				@Override
				@NonNull DocumentNoParts getAndIncrementSequence(final @NonNull DocSequenceId macAddressSequenceId, final @NonNull ClientId clientId)
				{
					return DocumentNoParts.builder()
							.prefix(prefix)
							.sequenceNumber(String.valueOf(next.getAndIncrement()))
							.suffix("")
							.build();
				}
			};
		}

		@Builder(builderMethodName = "context", builderClassName = "$contextBuilder")
		private ComponentGeneratorContext createContext(
				@NonNull final Integer qty,
				@Nullable final String alreadySetMAC1,
				@Nullable final String alreadySetMAC2,
				@Nullable final String alreadySetMAC3,
				@Nullable final String alreadySetMAC4,
				@Nullable final String alreadySetMAC5,
				@Nullable final String alreadySetMAC6,
				@Nullable final String alreadySetMAC7,
				@Nullable final String alreadySetMAC8) {

			final ImmutableAttributeSet.Builder existingAttributes = ImmutableAttributeSet.builder();
			if (alreadySetMAC1 != null) {
				existingAttributes.attributeValue(AttributeConstants.RouterMAC1, alreadySetMAC1);
			}
			if (alreadySetMAC2 != null) {
				existingAttributes.attributeValue(AttributeConstants.RouterMAC2, alreadySetMAC2);
			}
			if (alreadySetMAC3 != null) {
				existingAttributes.attributeValue(AttributeConstants.RouterMAC3, alreadySetMAC3);
			}
			if (alreadySetMAC4 != null) {
				existingAttributes.attributeValue(AttributeConstants.RouterMAC4, alreadySetMAC4);
			}
			if (alreadySetMAC5 != null) {
				existingAttributes.attributeValue(AttributeConstants.RouterMAC5, alreadySetMAC5);
			}
			if (alreadySetMAC6 != null) {
				existingAttributes.attributeValue(AttributeConstants.RouterMAC6, alreadySetMAC6);
			}
			if (alreadySetMAC7 != null) {
				existingAttributes.attributeValue(AttributeConstants.RouterMAC7, alreadySetMAC7);
			}
			if (alreadySetMAC8 != null) {
				existingAttributes.attributeValue(AttributeConstants.RouterMAC8, alreadySetMAC8);
			}

			return ComponentGeneratorContext.builder()
					.qty(qty)
					.existingAttributes(existingAttributes.build())
					.parameters(ComponentGeneratorParams.builder()
										.sequenceId(DocSequenceId.ofRepoId(123456))
										.build())
					.clientId(ClientId.METASFRESH)
					.build();
		}

		@Test
		void prefixHasColon()
		{
			final MacAddressGenerator generator = newMacAddressGenerator("BB:", 1);
			final ImmutableAttributeSet result = generator.generate(context()
					.qty(1)
					.alreadySetMAC1("")
					.build());

			assertThat(result).isEqualTo(ImmutableAttributeSet.builder()
					.attributeValue(AttributeConstants.RouterMAC1, "BB:00:00:00:00:01")
					.build());
		}

		@Test
		void prefixNoColon()
		{
			final MacAddressGenerator generator = newMacAddressGenerator("BB:A", 1234);
			final ImmutableAttributeSet result = generator.generate(context()
					.qty(2)
					.alreadySetMAC1("already set")
					.alreadySetMAC2("")
					.build());

			assertThat(result).isEqualTo(ImmutableAttributeSet.builder()
					.attributeValue(AttributeConstants.RouterMAC2, "BB:A0:00:00:04:D2")
					.build());
		}

		@Test
		void dashGroupDelimiter()
		{
			final MacAddressGenerator generator = newMacAddressGenerator("BB-A", 1234);
			final ImmutableAttributeSet result = generator.generate(context()
					.qty(1)
					.alreadySetMAC1("")
					.build());

			assertThat(result).isEqualTo(ImmutableAttributeSet.builder()
					.attributeValue(AttributeConstants.RouterMAC1, "BB-A0-00-00-04-D2")
					.build());
		}

		@Test
		void noPrefix()
		{
			final MacAddressGenerator generator = newMacAddressGenerator("", 1234321);
			final ImmutableAttributeSet result = generator.generate(context()
					.qty(1)
					.alreadySetMAC1("")
					.build());

			assertThat(result).isEqualTo(ImmutableAttributeSet.builder()
												 .attributeValue(AttributeConstants.RouterMAC1, "00:00:00:12:D5:91")
												 .build());
		}

		@Test
		void allEightMacAddressesGenerated()
		{
			final MacAddressGenerator generator = newMacAddressGenerator("BB:A", 1234);
			final ImmutableAttributeSet result = generator.generate(context()
																			.qty(8)
																			.alreadySetMAC1("")
																			.alreadySetMAC2("")
																			.alreadySetMAC3("")
																			.alreadySetMAC4("")
																			.alreadySetMAC5("")
																			.alreadySetMAC6("")
																			.alreadySetMAC7("")
																			.alreadySetMAC8("")
																			.build());

			assertThat(result).isEqualTo(ImmutableAttributeSet.builder()
												 .attributeValue(AttributeConstants.RouterMAC1, "BB:A0:00:00:04:D2")
												 .attributeValue(AttributeConstants.RouterMAC2, "BB:A0:00:00:04:D3")
												 .attributeValue(AttributeConstants.RouterMAC3, "BB:A0:00:00:04:D4")
												 .attributeValue(AttributeConstants.RouterMAC4, "BB:A0:00:00:04:D5")
												 .attributeValue(AttributeConstants.RouterMAC5, "BB:A0:00:00:04:D6")
												 .attributeValue(AttributeConstants.RouterMAC6, "BB:A0:00:00:04:D7")
												 .attributeValue(AttributeConstants.RouterMAC7, "BB:A0:00:00:04:D8")
												 .attributeValue(AttributeConstants.RouterMAC8, "BB:A0:00:00:04:D9")
												 .build());
		}

	}
}
