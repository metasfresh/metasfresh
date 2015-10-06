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


import java.util.List;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.ISingletonService;

import de.metas.adempiere.model.I_M_ProductPrice;

public interface IAttributePricingDAO extends ISingletonService
{
	/**
	 * Retrieve all <code>M_ProductPrice_Attribute</code>s for the given <code>productPrice</code>, no matter if they are active or not.
	 * <p>
	 * Note: even if the given <code>productPrice</code> is not attribute-dependent, existing records shall be returned.
	 * 
	 * @param productPrice
	 * @return
	 */
	List<I_M_ProductPrice_Attribute> retrieveAllPriceAttributes(I_M_ProductPrice productPrice);

	List<I_M_ProductPrice_Attribute_Line> retrieveAttributeLines(I_M_ProductPrice_Attribute productPriceAttribute);

	/**
	 * Retrieves the {@link I_M_ProductPrice_Attribute}s for the given {@code productPrice}, using the filters which have been added using {@link #registerQueryFilter(IQueryFilter)}.
	 * <p>
	 * Note: if the given <code>productPrice</code> is not attribute-dependent, then return an empty list.
	 * 
	 * @param productPrice
	 * @return
	 */
	List<I_M_ProductPrice_Attribute> retrieveFilteredPriceAttributes(I_M_ProductPrice productPrice);

	/**
	 * Retrieve all <b>active</b> <code>M_ProductPrice_Attribute</code>s for the given <code>productPrice</code>.
	 * <p>
	 * Note: if the given <code>productPrice</code> is not attribute-dependent, then return an empty list.
	 * 
	 * @param productPrice
	 * @return
	 */
	List<I_M_ProductPrice_Attribute> retrievePriceAttributes(I_M_ProductPrice productPrice);

	/**
	 * Registers another filter to be applied by {@link #retrieveFilteredPriceAttributes(I_M_ProductPrice)}. There can be multiple filters that will be ANDed.
	 * 
	 * @param filter filter to be added to a composite filter
	 */
	void registerQueryFilter(IQueryFilter<I_M_ProductPrice_Attribute> filter);

	/**
	 * Retrieves the "default" {@link I_M_ProductPrice_Attribute} for the given <code>productPrice</code>.
	 * 
	 * If there is no default record and <code>strictDefault</code> is <code>false</code> then the one with lowest SeqNo will be taken.
	 * 
	 * @param productPrice
	 * @param strictDefault if <code>true</code> then the method
	 *            <ul>
	 *            <li>filters by <code>IsDefault='Y'</code></li>
	 *            <li>performs the query using {@link org.compiere.model.IQuery#firstOnly(Class)}, i.e. it verifies that there aren't more than one default record</li>
	 *            </ul>
	 *            if <code>false</code> then the method does not filter by <code>IsDefault</code>, order the result by <code>IsDefault DESC, SeqNo ASC</code> and returns the first result. So if there
	 *            is a default record, it will be returned.
	 * @return product price attribute record or <code>null</code> if the given <code>productPrice</code> is not attribute-dependent or does not have active attributes.
	 */
	I_M_ProductPrice_Attribute retrieveDefaultProductPriceAttribute(I_M_ProductPrice productPrice, boolean strictDefault);
}
