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

import static org.assertj.core.api.Assertions.assertThat;

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

	@Nested
	public class generate
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
				@Nullable String alreadySetMAC1,
				@Nullable String alreadySetMAC2)
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
					.existingAttributes(existingAttributes.build())
					.parameters(ComponentGeneratorParams.builder()
							.sequenceId(DocSequenceId.ofRepoId(123456))
							.build())
					.clientId(ClientId.METASFRESH) // irrelevant
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
	}
}
