package de.metas.pricing.attributebased;

/*
 * #%L
 * de.metas.swat.base
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


/**
 * Implemented by models which is are aware of Product Price record.
 * 
 * It is used in attribute pricing engines to fetch explicit product price attribute settings.
 * 
 * To create a new instance, please use {@link ProductPriceAttributeAware#ofModel(Object)}.
 * 
 * @author tsa
 *
 */
public interface IProductPriceAware
{
	//@formatter:off
    public static final String COLUMNNAME_M_ProductPrice_ID = "M_ProductPrice_Attribute_ID";
	public int getM_ProductPrice_ID();
	//@formatter:on

	//@formatter:off
	// NOTE: mainly used for Excel imports (see task 08839)
	public static final String COLUMNNAME_IsExplicitProductPriceAttribute = "IsExplicitProductPriceAttribute";
	public boolean isExplicitProductPriceAttribute();
	//@formatter:on
}
