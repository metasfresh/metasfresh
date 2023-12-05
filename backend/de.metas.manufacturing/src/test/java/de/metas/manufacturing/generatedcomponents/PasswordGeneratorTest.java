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

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.product.ProductId;
import de.metas.util.StringUtils;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_PP_ComponentGenerator;
import org.compiere.model.I_PP_ComponentGenerator_Param;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordGeneratorTest
{
	@Nested
	class GetPasswordTest
	{
		private final PasswordGenerator generator = new PasswordGenerator();

		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 })
		void passwordLength(final int length)
		{
			assertThat(generator.generatePassword(length, true, true, true, true, "", 0)).hasSize(length);
		}

		@Test
		void noLowercase()
		{
			assertThat(generator.generatePassword(20, false, true, true, true, "", 0).split(""))
					.noneMatch(s -> Character.isLowerCase(s.charAt(0)));
		}

		@Test
		void noUppercase()
		{
			assertThat(generator.generatePassword(20, true, false, true, true, "", 0).split(""))
					.noneMatch(s -> Character.isUpperCase(s.charAt(0)));
		}

		@Test
		void noDigits()
		{
			assertThat(generator.generatePassword(20, true, true, false, true, "", 0).split(""))
					.noneMatch(s -> Character.isDigit(s.charAt(0)));
		}

		@Test
		void noPunctuation()
		{
			assertThat(generator.generatePassword(20, true, true, true, false, "", 0).split(""))
					.allMatch(s -> Character.isLetterOrDigit(s.charAt(0)));
		}

		@Test
		void splitByDashes()
		{
			assertThat(generator.generatePassword(14, true, true, true, false, "-", 4).split(""))
					.hasSize(14)
					.matches(chars -> chars[4].equals("-") && chars[9].equals("-"));
		}

		@Test
		void splitByDoubleDashes()
		{
			assertThat(generator.generatePassword(16, true, true, true, false, "--", 4).split(""))
					.hasSize(16)
					.matches(chars -> chars[4].equals("-")
							&& chars[5].equals("-")
							&& chars[10].equals("-")
							&& chars[11].equals("-"));
		}
	}

	@Nested
	class GeneratorServiceTest
	{
		final ProductId passwordProductId = ProductId.ofRepoId(1);
		int passwordJavaClassId;
		int passwordGeneratorId;

		final ComponentGeneratorRepository componentRepository = new ComponentGeneratorRepository();
		final ManufacturingComponentGeneratorService generatorService = new ManufacturingComponentGeneratorService(componentRepository);

		@BeforeEach
		void beforeEach()
		{
			AdempiereTestHelper.get().init();

			{
				mkAttribute(AttributeConstants.RouterPassword);
				mkAttribute(AttributeConstants.RouterMAC1);
				mkAttribute(AttributeConstants.RouterMAC2);
				mkAttribute(AttributeConstants.RouterMAC3);
				mkAttribute(AttributeConstants.RouterMAC4);
				mkAttribute(AttributeConstants.RouterMAC5);
				mkAttribute(AttributeConstants.RouterMAC6);
				mkAttribute(AttributeConstants.RouterMAC7);
				mkAttribute(AttributeConstants.RouterMAC8);
				mkAttribute(AttributeConstants.ATTR_LotNumber);
				mkAttribute(AttributeConstants.ATTR_SerialNo);
				mkAttribute(AttributeConstants.ATTR_BestBeforeDate);
			}
			{
				final I_AD_JavaClass po = InterfaceWrapperHelper.newInstance(I_AD_JavaClass.class);
				po.setClassname("de.metas.manufacturing.generatedcomponents.PasswordGenerator");
				InterfaceWrapperHelper.save(po);
				passwordJavaClassId = po.getAD_JavaClass_ID();

				final I_PP_ComponentGenerator generatorPo = InterfaceWrapperHelper.newInstance(I_PP_ComponentGenerator.class);
				generatorPo.setM_Product_ID(passwordProductId.getRepoId());
				generatorPo.setAD_JavaClass_ID(passwordJavaClassId);
				InterfaceWrapperHelper.save(generatorPo);
				passwordGeneratorId = generatorPo.getPP_ComponentGenerator_ID();
			}
		}

		@Test
		void noPasswordExists()
		{
			mkPasswordParams(14, true, true, true, true, "-", 4);

			final ImmutableAttributeSet actualPasswords = generatorService.generate(
					GeneratedComponentRequest.builder()
							.targetHUAttributes(ImmutableAttributeSet.builder().attributeValue(AttributeConstants.RouterPassword, null).build())
							.bomLineAttributes(ImmutableAttributeSet.EMPTY)
							.productId(passwordProductId)
							.qty(1)
							.clientId(ClientId.METASFRESH)
							.build());

			assertThat(actualPasswords.getAttributes()).hasSize(1);
		}

		@Test
		void passwordExists()
		{
			mkPasswordParams(14, true, true, true, true, "-", 4);

			final ImmutableAttributeSet actualPasswords = generatorService.generate(
					GeneratedComponentRequest.builder()
							.targetHUAttributes(ImmutableAttributeSet.builder().attributeValue(AttributeConstants.RouterPassword, "1234").build())
							.bomLineAttributes(ImmutableAttributeSet.EMPTY)
							.productId(passwordProductId)
							.qty(1)
							.clientId(ClientId.METASFRESH)
							.build());

			assertThat(actualPasswords.getAttributes()).hasSize(0);
		}

		@SuppressWarnings({ "ConstantConditions", "SameParameterValue" })
		private void mkPasswordParams(
				final int totalLength,
				final boolean useLowercase,
				final boolean useUppercase,
				final boolean useDigit,
				final boolean usePunctuation,
				final String groupSeparator,
				final int groupSize)
		{
			mkParam(passwordGeneratorId, PasswordGenerator.PARAM_TOTAL_LENGTH, Integer.toString(totalLength));
			mkParam(passwordGeneratorId, PasswordGenerator.PARAM_USE_LOWERCASE, StringUtils.ofBoolean(useLowercase));
			mkParam(passwordGeneratorId, PasswordGenerator.PARAM_USE_UPPERCASE, StringUtils.ofBoolean(useUppercase));
			mkParam(passwordGeneratorId, PasswordGenerator.PARAM_USE_DIGIT, StringUtils.ofBoolean(useDigit));
			mkParam(passwordGeneratorId, PasswordGenerator.PARAM_USE_PUNCTUATION, StringUtils.ofBoolean(usePunctuation));
			mkParam(passwordGeneratorId, PasswordGenerator.PARAM_GROUP_SEPARATOR, groupSeparator);
			mkParam(passwordGeneratorId, PasswordGenerator.PARAM_GROUP_SIZE, Integer.toString(groupSize));
		}

		private void mkParam(final int generatorId, final String name, final String value)
		{
			final I_PP_ComponentGenerator_Param param = InterfaceWrapperHelper.newInstance(I_PP_ComponentGenerator_Param.class);
			param.setPP_ComponentGenerator_ID(generatorId);
			param.setName(name);
			param.setParamValue(value);
			InterfaceWrapperHelper.save(param);
		}

		private void mkAttribute(final AttributeCode attributeCode)
		{
			final I_M_Attribute po = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
			po.setName(attributeCode.getCode());
			po.setValue(attributeCode.getCode());
			InterfaceWrapperHelper.save(po);
		}
	}
}
