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
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_PP_ComponentGenerator;
import org.compiere.model.I_PP_ComponentGenerator_Param;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class ManufacturingComponentGeneratorServiceTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Services.registerService(IDocumentNoBuilderFactory.class, new DocumentNoBuilderFactory(Optional.empty()));


		{
			mkAttribute(AttributeConstants.RouterPassword);
			mkAttribute(AttributeConstants.RouterMAC1);
			mkAttribute(AttributeConstants.RouterMAC2);
			mkAttribute(AttributeConstants.RouterMAC3);
			mkAttribute(AttributeConstants.RouterMAC4);
			mkAttribute(AttributeConstants.RouterMAC5);
			mkAttribute(AttributeConstants.RouterMAC6);
		}
	}


	@Test
	void generatePassword()
	{
		final ProductId productId = ProductId.ofRepoId(1);
		{

			final int javaClassId;
			{
				final I_AD_JavaClass po = InterfaceWrapperHelper.newInstance(I_AD_JavaClass.class);
				po.setClassname("de.metas.manufacturing.generatedcomponents.PasswordGenerator");
				InterfaceWrapperHelper.save(po);
				javaClassId = po.getAD_JavaClass_ID();
			}

			{
				final I_PP_ComponentGenerator generatorPo = InterfaceWrapperHelper.newInstance(I_PP_ComponentGenerator.class);
				generatorPo.setM_Product_ID(productId.getRepoId());
				generatorPo.setAD_JavaClass_ID(javaClassId);
				InterfaceWrapperHelper.save(generatorPo);

				mkParam(generatorPo.getPP_ComponentGenerator_ID(), PasswordGenerator.PARAM_LENGTH, "50");
				mkParam(generatorPo.getPP_ComponentGenerator_ID(), PasswordGenerator.PARAM_USE_LOWERCASE, "Y");
				mkParam(generatorPo.getPP_ComponentGenerator_ID(), PasswordGenerator.PARAM_USE_UPPERCASE, "Y");
				mkParam(generatorPo.getPP_ComponentGenerator_ID(), PasswordGenerator.PARAM_USE_DIGIT, "Y");
				mkParam(generatorPo.getPP_ComponentGenerator_ID(), PasswordGenerator.PARAM_USE_PUNCTUATION, "Y");
			}

		}

		final ComponentGeneratorRepository componentRepository = new ComponentGeneratorRepository();

		final ManufacturingComponentGeneratorService gs = new ManufacturingComponentGeneratorService(componentRepository);
		final ImmutableAttributeSet actualPasswords = gs.generate(
				GeneratedComponentRequest
						.builder()
						.attributes(ImmutableAttributeSet.builder().attributeValue(AttributeConstants.RouterPassword, null).build())
						.productId(productId)
						.qty(1)
						.build());
		System.out.println(actualPasswords);

		// assertThat(actual).hasSize(20);

		// assertThat(actual.getAttributes()).hasSize(4);
		// System.out.println(actual);
	}

	@Test
	void generatePasswordWrongQty()
	{
		 // TODO tbp: this should throw exception
	}
	void mkParam(final int generatorId, final String name, final String value)
	{
		final I_PP_ComponentGenerator_Param param = InterfaceWrapperHelper.newInstance(I_PP_ComponentGenerator_Param.class);
		param.setPP_ComponentGenerator_ID(generatorId);
		param.setName(name);
		param.setValue(value);
		InterfaceWrapperHelper.save(param);
	}

	void mkAttribute(final AttributeCode attributeCode)
	{
		final I_M_Attribute po = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		po.setName(attributeCode.getCode());
		po.setValue(attributeCode.getCode());
		InterfaceWrapperHelper.save(po);
	}
}
