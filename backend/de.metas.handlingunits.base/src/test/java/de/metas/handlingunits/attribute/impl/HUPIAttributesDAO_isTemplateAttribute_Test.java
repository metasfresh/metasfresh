package de.metas.handlingunits.attribute.impl;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Tests for {@link HUPIAttributesDAO#isTemplateAttribute(I_M_HU_PI_Attribute)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class HUPIAttributesDAO_isTemplateAttribute_Test extends AbstractHUTest
{
	private static final boolean ACTIVE = true;
	private static final boolean NOT_ACTIVE = false;

	private HUPIAttributesDAO piAttributesDAO;

	private I_M_Attribute attribute;
	private I_M_HU_PI piTemplate;
	private I_M_HU_PI piLU;

	@Override
	protected void initialize()
	{
		piAttributesDAO = (HUPIAttributesDAO)Services.get(IHUPIAttributesDAO.class);

		attribute = new AttributesTestHelper().createM_Attribute("test-attribute", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);

		piTemplate = helper.huDefNone;
		piLU = helper.createHUDefinition("LI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
	}

	private I_M_HU_PI_Attribute linkAttributeToPI(final I_M_HU_PI pi, final boolean isActive)
	{
		final I_M_HU_PI_Attribute piAttribute = helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attribute)
				.setM_HU_PI(pi)
				.setInstanceAttribute(true));

		piAttribute.setIsActive(isActive);
		InterfaceWrapperHelper.save(piAttribute);
		return piAttribute;
	}

	private void assertTemplateAttribute(final boolean isTemplateAttributeExpected, final I_M_HU_PI_Attribute piAttribute)
	{
		Assertions.assertEquals( isTemplateAttributeExpected, piAttributesDAO.isTemplateAttribute(piAttribute), "IsTemplateAttribute");
	}

	@Test
	public void test_NULL()
	{
		assertThatThrownBy(() -> {
			piAttributesDAO.isTemplateAttribute(null);
		})
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	public void test_OnTemplateLevel()
	{
		final I_M_HU_PI_Attribute piAttribute = linkAttributeToPI(piTemplate, ACTIVE);

		assertTemplateAttribute(true, piAttribute);
	}

	@Test
	public void test_OnTemplateLevelInactive()
	{
		final I_M_HU_PI_Attribute piAttribute = linkAttributeToPI(piTemplate, NOT_ACTIVE);

		assertTemplateAttribute(false, piAttribute);
	}

	@Test
	public void test_NotOnTemplateLevel_OnLULevel()
	{
		final I_M_HU_PI_Attribute piAttribute = linkAttributeToPI(piLU, true);

		assertTemplateAttribute(false, piAttribute);
	}

	@Test
	public void test_OnTemplateLevelInactive_OnLULevel()
	{
		linkAttributeToPI(piTemplate, false);
		final I_M_HU_PI_Attribute piAttribute = linkAttributeToPI(piLU, true);

		assertTemplateAttribute(false, piAttribute);
	}

	@Test
	public void test_OnTemplateLevel_OnLULevel()
	{
		linkAttributeToPI(piTemplate, true);
		final I_M_HU_PI_Attribute piAttribute = linkAttributeToPI(piLU, true);

		assertTemplateAttribute(true, piAttribute);
	}

	@Test
	public void test_OnTemplateLevel_OnLULevelInactive()
	{
		linkAttributeToPI(piTemplate, true);
		final I_M_HU_PI_Attribute piAttribute = linkAttributeToPI(piLU, false);

		assertTemplateAttribute(true, piAttribute);
	}

}
