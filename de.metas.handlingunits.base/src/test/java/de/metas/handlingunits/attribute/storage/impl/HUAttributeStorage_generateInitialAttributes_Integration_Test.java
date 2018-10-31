package de.metas.handlingunits.attribute.storage.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBLTests;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.util.Services;

/**
 * This test is making sure current {@link IAttributeValueGenerator} implementations are not failing when {@link HUAttributeStorage#generateInitialAttributes()} is invoked.
 *
 * @author tsa
 *
 */
public class HUAttributeStorage_generateInitialAttributes_Integration_Test extends AbstractHUTest
{
	/**
	 * Retrieves all {@link IAttributeValueGenerator} implementation classes from ALL class loaders which are available atm.
	 *
	 * @return
	 */
	private static final Set<Class<? extends IAttributeValueGenerator>> retrieveAttributeValueGeneratorClassnames()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forClassLoader())
				.setScanners(new SubTypesScanner()));

		final Set<Class<? extends IAttributeValueGenerator>> attributeValueGeneratorClassnames = reflections.getSubTypesOf(IAttributeValueGenerator.class);

		System.out.println("Found " + attributeValueGeneratorClassnames.size() + " classes in " + stopwatch.toString());

		return attributeValueGeneratorClassnames;
	}

	private IAttributeStorageFactory attributeStorageFactory;

	private I_M_HU_PI huPI;
	private I_M_HU_PI_Version huPIVersion;

	@Override
	protected void initialize()
	{
		attributeStorageFactory = helper.getHUContext().getHUAttributeStorageFactory();

		huPI = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		huPIVersion = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(huPI);

		setupSSCC18AttributeValueGenerator();
	}

	private void setupSSCC18AttributeValueGenerator()
	{
		SSCC18CodeBLTests.setManufacturerCode("0001");
	}

	private void setupHUPIAttribute(final I_M_HU_PI huPI, final Class<? extends IAttributeValueGenerator> attributeValueGeneratorClass)
	{
		IAttributeValueGenerator attributeValueGenerator;
		try
		{
			attributeValueGenerator = attributeValueGeneratorClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new AssertionError("Cannot instantiate " + attributeValueGeneratorClass, e);
		}

		final boolean isInstanceAttribute = true;
		final I_M_Attribute attribute = new AttributesTestHelper().createM_Attribute(
				"ATTR_" + attributeValueGeneratorClass.getSimpleName(), // Attribute Name
				attributeValueGenerator.getAttributeValueType(), // Attribute Type
				attributeValueGeneratorClass,
				uomKg,
				isInstanceAttribute);

		final I_M_HU_PI_Attribute piAttribute = helper.createM_HU_PI_Attribute(new HUPIAttributeBuilder(attribute)
				.setM_HU_PI(huPI)
				.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
		piAttribute.setIsReadOnly(false);
		piAttribute.setSeqNo(10);
		InterfaceWrapperHelper.save(piAttribute);
	}

	@Test
	public void test()
	{
		for (final Class<? extends IAttributeValueGenerator> attributeValueGeneratorClass : retrieveAttributeValueGeneratorClassnames())
		{
			test(attributeValueGeneratorClass);
		}
	}

	/**
	 * Create a new {@link I_M_HU_PI_Attribute} configuration for given <code>attributeValueGeneratorClass</code>.
	 *
	 * The create a new {@link HUAttributeStorage} instance and invoke {@link HUAttributeStorage#generateInitialAttributes()}.
	 *
	 * @param attributeValueGeneratorClass
	 */
	public void test(final Class<? extends IAttributeValueGenerator> attributeValueGeneratorClass)
	{
		// Skip Mocked implementations, used for testing
		final String attributeValueGeneratorClassname = attributeValueGeneratorClass.getName();
		if (attributeValueGeneratorClassname.startsWith("Mocked")
				|| attributeValueGeneratorClassname.indexOf("$Mocked") > 0)
		{
			return;
		}

		// Skip interfaces
		if (attributeValueGeneratorClass.isInterface())
		{
			return;
		}

		// Skip abstract classes
		if (Modifier.isAbstract(attributeValueGeneratorClass.getModifiers()))
		{
			return;
		}

		System.out.println("Testing: " + attributeValueGeneratorClass);

		//
		// Create an M_HU_PI_Attribute for given generator
		setupHUPIAttribute(huPI, attributeValueGeneratorClass);

		//
		// Create a new dummy HU
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, helper.getContextProvider());
		hu.setM_HU_PI_Version(huPIVersion);
		InterfaceWrapperHelper.save(hu);

		//
		// Create a new HU Attribute Storage instance
		final HUAttributeStorage attributesStorage = new HUAttributeStorage(attributeStorageFactory, hu);

		//
		// Ask it to generate it's initial attributes and hope to not fail ;)
		final Map<AttributeId, Object> defaultAttributesValue = ImmutableMap.of();
		attributesStorage.generateInitialAttributes(defaultAttributesValue);
	}
}
