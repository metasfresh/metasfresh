package de.metas.edi.model;

/*
 * #%L
 * de.metas.edi
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


import de.metas.pricing.attributebased.IProductPriceAttributeAware;

public interface I_C_OLCand extends de.metas.handlingunits.model.I_C_OLCand, IProductPriceAttributeAware
{
	//@formatter:off
	// NOTE: mainly used for Excel imports (see task 08839)
    public static final String COLUMNNAME_M_ProductPrice_ID = "M_ProductPrice_ID";
	public void setM_ProductPrice_ID (int M_ProductPrice_ID);
	public int getM_ProductPrice_ID();
	public org.compiere.model.I_M_ProductPrice getM_ProductPrice() throws RuntimeException;
	public void setM_ProductPrice(org.compiere.model.I_M_ProductPrice M_ProductPrice);
	//@formatter:on

	//@formatter:off
	// NOTE: mainly used for Excel imports (see task 08839)
    //public static final String COLUMNNAME_M_ProductPrice_Attribute_ID = "M_ProductPrice_Attribute_ID";
	public void setM_ProductPrice_Attribute_ID (int M_ProductPrice_Attribute_ID);
	@Override
	public int getM_ProductPrice_Attribute_ID();
	public de.metas.pricing.attributebased.I_M_ProductPrice_Attribute getM_ProductPrice_Attribute() throws RuntimeException;
	public void setM_ProductPrice_Attribute(de.metas.pricing.attributebased.I_M_ProductPrice_Attribute M_ProductPrice_Attribute);
	//@formatter:on

	//@formatter:off
	// NOTE: mainly used for Excel imports (see task 08839)
	//public static final String COLUMNNAME_IsExplicitProductPriceAttribute = "IsExplicitProductPriceAttribute";
	@Override
	public boolean isExplicitProductPriceAttribute();
	public void setIsExplicitProductPriceAttribute(final boolean IsExplicitProductPriceAttribute);
	//@formatter:on
}
