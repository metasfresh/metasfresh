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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attributes.sscc18.ISSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBLTests;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.util.Services;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This test is making sure current {@link IAttributeValueGenerator} implementations are not failing when {@link HUAttributeStorage#generateInitialAttributes()} is invoked.
 *
 * @author tsa
 *
 */
public class HUAttributeStorage_generateInitialAttributes_Integration_Test extends AbstractHUTest
{
	private IAttributeStorageFactory attributeStorageFactory;

	private int nextSSCC18SerialNumber;

	private I_M_HU_PI huPI;
	private I_M_HU_PI_Version huPIVersion;

	private final HashMap<Class<? extends IAttributeValueGenerator>, Class<? extends ReferenceListAwareEnum>> //
	registeredEnums = new HashMap<>();

	protected final void registerEnum(
			@NonNull final Class<? extends IAttributeValueGenerator> generatorClass,
			@NonNull final Class<? extends ReferenceListAwareEnum> enumType)
	{
		registeredEnums.put(generatorClass, enumType);
	}

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
		nextSSCC18SerialNumber = 0;

		final SSCC18CodeBL sscc18CodeService = new SSCC18CodeBL(orgId -> ++nextSSCC18SerialNumber);
		Services.registerService(ISSCC18CodeBL.class, sscc18CodeService);

		SSCC18CodeBLTests.setManufacturerCode("0001");
	}

	private void setupHUPIAttribute(
			final I_M_HU_PI huPI,
			final Class<? extends IAttributeValueGenerator> attributeValueGeneratorClass)
	{
		IAttributeValueGenerator attributeValueGenerator;
		try
		{
			attributeValueGenerator = attributeValueGeneratorClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException ex)
		{
			throw new AssertionError("Cannot instantiate " + attributeValueGeneratorClass, ex);
		}

		final Set<String> attributeValueTypes;
		if (attributeValueGenerator.getAttributeValueType() != null)
		{
			attributeValueTypes = ImmutableSet.of(attributeValueGenerator.getAttributeValueType());
		}
		else
		{
			attributeValueTypes = ImmutableSet.of(
					X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40,
					X_M_Attribute.ATTRIBUTEVALUETYPE_Number,
					X_M_Attribute.ATTRIBUTEVALUETYPE_Date,
					X_M_Attribute.ATTRIBUTEVALUETYPE_List);
		}

		for (final String attributeValueType : attributeValueTypes)
		{
			final String attributeName = "ATTR_" + attributeValueGeneratorClass.getSimpleName() + "_" + attributeValueType;
			final boolean isInstanceAttribute = true;
			final I_M_Attribute attribute = new AttributesTestHelper().createM_Attribute(
					attributeName, // Attribute Name
					attributeValueType, // Attribute Type
					attributeValueGeneratorClass,
					uomKg,
					isInstanceAttribute);

			createAttributeValues(attribute, attributeValueGeneratorClass);

			final I_M_HU_PI_Attribute piAttribute = helper.createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attribute)
					.setM_HU_PI(huPI)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
			piAttribute.setIsReadOnly(false);
			piAttribute.setSeqNo(10);
			InterfaceWrapperHelper.save(piAttribute);
		}
	}

	private void createAttributeValues(final I_M_Attribute attribute, final Class<? extends IAttributeValueGenerator> attributeValueGeneratorClass)
	{
		final Class<? extends ReferenceListAwareEnum> enumType = registeredEnums.get(attributeValueGeneratorClass);
		if (enumType != null)
		{
			final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();

			for (final ReferenceListAwareEnum value : ReferenceListAwareEnums.values(enumType))
			{
				attributesTestHelper.createM_AttributeValue(attribute, value.getCode());
			}
		}
	}

	@Test
	public void test()
	{
		for (final Class<? extends IAttributeValueGenerator> attributeValueGeneratorClass : retrieveAttributeValueGeneratorClassnames())
		{
			if (isSkipTesting(attributeValueGeneratorClass))
			{
				continue;
			}
			test(attributeValueGeneratorClass);
		}
	}

	private static final Set<Class<? extends IAttributeValueGenerator>> retrieveAttributeValueGeneratorClassnames()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		final Reflections reflections = new Reflections(new ConfigurationBuilder()
				.addUrls(ClasspathHelper.forClassLoader())
				.setScanners(new SubTypesScanner()));

		final Set<Class<? extends IAttributeValueGenerator>> attributeValueGeneratorClassnames = reflections.getSubTypesOf(IAttributeValueGenerator.class);
		if (attributeValueGeneratorClassnames.isEmpty())
		{
			throw new RuntimeException("No classes found. Might be because for some reason Reflections does not work correctly with maven surefire plugin."
					+ "\n See https://github.com/metasfresh/metasfresh/issues/4773.");
		}

		System.out.println("Found " + attributeValueGeneratorClassnames.size() + " classes in " + stopwatch.toString());

		return attributeValueGeneratorClassnames;
	}

	private boolean isSkipTesting(final Class<? extends IAttributeValueGenerator> attributeValueGeneratorClass)
	{
		// Skip Mocked implementations, used for testing
		final String attributeValueGeneratorClassname = attributeValueGeneratorClass.getName();
		if (attributeValueGeneratorClassname.startsWith("Mocked")
				|| attributeValueGeneratorClassname.indexOf("$Mocked") > 0)
		{
			return true;
		}

		// Skip interfaces
		if (attributeValueGeneratorClass.isInterface())
		{
			return true;
		}

		// Skip abstract classes
		if (Modifier.isAbstract(attributeValueGeneratorClass.getModifiers()))
		{
			return true;
		}

		return false;
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
		testWatcher.putContext(attributeValueGeneratorClass);

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
