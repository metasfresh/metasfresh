package de.metas.handlingunits.attribute.impl;

import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.X_M_Attribute;
import org.junit.Assert;
import org.junit.Test;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;

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
 *
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

		attribute = helper.createM_Attribute("test-attribute", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);

		piTemplate = helper.huDefNone;
		piLU = helper.createHUDefinition("LI", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
	}

	private I_M_HU_PI_Attribute linkAttributeToPI(final I_M_HU_PI pi, final boolean isActive)
	{
		final I_M_HU_PI_Attribute piAttribute = helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attribute)
				.setM_HU_PI(pi)
				.setInstanceAttribute(true));

		piAttribute.setIsActive(isActive);
		InterfaceWrapperHelper.save(piAttribute);
		return piAttribute;
	}

	private void assertTemplateAttribute(final boolean isTemplateAttributeExpected, final I_M_HU_PI_Attribute piAttribute)
	{
		Assert.assertEquals("IsTemplateAttribute", isTemplateAttributeExpected, piAttributesDAO.isTemplateAttribute(piAttribute));
	}

	@Test(expected = NullPointerException.class)
	public void test_NULL()
	{
		piAttributesDAO.isTemplateAttribute(null);
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
