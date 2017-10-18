package de.metas.business;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_UOM;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.javaclasses.model.I_AD_JavaClass;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public final class BusinessTestHelper
{

	public static final BusinessTestHelper INSTANCE = new BusinessTestHelper();

	/**
	 * Default precision
	 */
	private static final int UOM_Precision_0 = 0;

	/**
	 * Standard in ADempiere
	 */
	private static final int UOM_Precision_3 = 3;

	public static final I_C_UOM uomEach = createUOM("Ea", X_C_UOM.UOMTYPE_Weigth, UOM_Precision_0);
	public static final I_C_UOM uomKg = createUomKg();
	public static final I_C_UOM uomPCE = createUOM("PCE", null, UOM_Precision_0);;

	private BusinessTestHelper()
	{

	}

	private static I_C_UOM createUomKg()
	{
		final I_C_UOM uomKg = createUOM("Kg", X_C_UOM.UOMTYPE_Weigth, UOM_Precision_3);
		uomKg.setX12DE355("KGM");
		save(uomKg);
		return uomKg;
	};

	public static I_C_UOM createUOM(final String name, final String uomType, final int stdPrecision)
	{
		final I_C_UOM uom = createUOM(name, stdPrecision, 0);
		uom.setUOMType(uomType);

		save(uom);
		return uom;
	}

	public static I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecission)
	{
		final I_C_UOM uom = createUOM(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecission);
		save(uom);
		return uom;
	}

	public static I_C_UOM createUOM(final String name)
	{
		final String x12de355 = name;
		return createUOM(name, x12de355);
	}

	public static I_C_UOM createUOM(final String name, final String x12de355)
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(x12de355);

		save(uom);

		return uom;
	}

	public static I_C_UOM_Conversion createUOMConversion(
			final I_M_Product product,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo,
			final BigDecimal multiplyRate,
			final BigDecimal divideRate)
	{
		final I_C_UOM_Conversion conversion = InterfaceWrapperHelper.create(Env.getCtx(), I_C_UOM_Conversion.class, ITrx.TRXNAME_None);

		if (product != null)
		{
			conversion.setM_Product_ID(product.getM_Product_ID());
		}
		conversion.setC_UOM_ID(uomFrom.getC_UOM_ID());
		conversion.setC_UOM_To_ID(uomTo.getC_UOM_ID());
		conversion.setMultiplyRate(multiplyRate);
		conversion.setDivideRate(divideRate);

		save(conversion, ITrx.TRXNAME_None);

		return conversion;
	}

	/**
	 *
	 * @param name
	 * @param uom
	 * @param weightKg product weight (Kg); mainly used for packing materials
	 * @return
	 */
	public static I_M_Product createProduct(final String name, final I_C_UOM uom, final BigDecimal weightKg)
	{
		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		if (weightKg != null)
		{
			product.setWeight(weightKg);
		}
		save(product);

		return product;
	}

	public static I_M_Product createProduct(final String name, final I_C_UOM uom)
	{
		final BigDecimal weightKg = null; // N/A
		return createProduct(name, uom, weightKg);
	}

	public static I_M_Attribute createM_Attribute(final String name,
			final String valueType,
			final boolean isInstanceAttribute)
	{
		final Class<?> javaClass = null;
		final I_M_Attribute attr = createM_Attribute(name, valueType, javaClass, isInstanceAttribute);
		save(attr);

		return attr;
	}

	public static I_M_Attribute createM_Attribute(final String name,
			final String valueType,
			final Class<?> javaClass,
			final boolean isInstanceAttribute)
	{
		final I_C_UOM uom = null;
		return createM_Attribute(name, valueType, javaClass, uom, isInstanceAttribute);
	}

	public static I_M_Attribute createM_Attribute(final String name,
			final String valueType,
			final Class<?> javaClass,
			final I_C_UOM uom,
			final boolean isInstanceAttribute)
	{

		final I_AD_JavaClass javaClassDef;
		if (javaClass != null)
		{
			javaClassDef = newInstanceOutOfTrx(I_AD_JavaClass.class);
			javaClassDef.setName(javaClass.getName());
			javaClassDef.setClassname(javaClass.getName());
			save(javaClassDef);
		}
		else
		{
			javaClassDef = null;
		}

		final I_M_Attribute attr;

		// make sure the attribute was not already defined
		final I_M_Attribute existingAttribute = retrieveAttributeBuValue(name);
		if (existingAttribute != null)
		{
			attr = existingAttribute;
		}

		else
		{
			attr = newInstanceOutOfTrx(I_M_Attribute.class);
		}
		attr.setValue(name);
		attr.setName(name);
		attr.setAttributeValueType(valueType);

		//
		// Assume all attributes active and non-mandatory
		attr.setIsActive(true);
		attr.setIsMandatory(false);

		//
		// Configure ASI usage
		attr.setIsInstanceAttribute(isInstanceAttribute);

		//
		// Configure JC
		attr.setAD_JavaClass(javaClassDef);

		//
		// Configure UOM
		attr.setC_UOM(uom);

		save(attr);
		return attr;
	}

	/**
	 * Method needed to make sure the attribute was not already created
	 * Normally, this will never happen anywhere else except testing
	 *
	 * @param name
	 * @return
	 */
	private static I_M_Attribute retrieveAttributeBuValue(String name)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Attribute.class).addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Attribute.COLUMNNAME_Value, name)
				.create()
				.firstOnly(I_M_Attribute.class);
	}

	/**
	 * Creates and saves a simple {@link I_C_BPartner}
	 *
	 * @param nameAndValue
	 * @return
	 */
	public static I_C_BPartner createBPartner(final String nameAndValue)
	{
		final I_C_BPartner bpartner = newInstanceOutOfTrx(I_C_BPartner.class);
		bpartner.setValue(nameAndValue);
		bpartner.setName(nameAndValue);
		save(bpartner);

		return bpartner;
	}

	public static I_C_BPartner_Location createBPartnerLocation(final I_C_BPartner bpartner)
	{
		final I_C_BPartner_Location bpl = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
		bpl.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpl.setIsShipTo(true);
		bpl.setIsBillTo(true);
		save(bpl);
		return bpl;
	}

	/**
	 * Calls {@link #createWarehouse(String, boolean)} with {@code isIssueWarehouse == false}
	 *
	 * @param name
	 * @return
	 */
	public static I_M_Warehouse createWarehouse(final String name)
	{
		final boolean isIssueWarehouse = false;
		return createWarehouse(name, isIssueWarehouse);
	}

	/**
	 * Creates a warehouse and one (default) locator.
	 *
	 * @param name
	 * @param isIssueWarehouse
	 * @return
	 */
	public static I_M_Warehouse createWarehouse(final String name, final boolean isIssueWarehouse)
	{
		final org.adempiere.warehouse.model.I_M_Warehouse warehouse = newInstanceOutOfTrx(org.adempiere.warehouse.model.I_M_Warehouse.class);
		warehouse.setValue(name);
		warehouse.setName(name);
		warehouse.setIsIssueWarehouse(isIssueWarehouse);
		save(warehouse);

		final I_M_Locator locator = createLocator(name + "-default", warehouse);
		locator.setIsDefault(true);
		save(locator);

		return warehouse;
	}

	public static I_M_Locator createLocator(final String name, final I_M_Warehouse warehouse)
	{
		final I_M_Locator locator = newInstanceOutOfTrx(I_M_Locator.class);
		locator.setIsDefault(true);
		locator.setValue(name);
		locator.setIsActive(true);
		locator.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());
		save(locator);
		return locator;
	}
}
