package org.adempiere.mm.attributes.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.MathContext;
import java.util.Date;
import java.util.Properties;

import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;

public interface IAttributesBL extends ISingletonService
{
	AttributeAction getAttributeAction(Properties ctx);

	IAttributeValueGenerator getAttributeValueGenerator(org.compiere.model.I_M_Attribute attributeParam);

	/**
	 * Returns a new attribute value generator instance for the given <code>attributeParam</code> or <code>null</code> if the given parameter has no <code>AD_JavaClass_ID</code> set.
	 * 
	 * @param attributeParam
	 * @return
	 */
	IAttributeValueGenerator getAttributeValueGeneratorOrNull(org.compiere.model.I_M_Attribute attributeParam);

	/**
	 * Retrieves {@link IAttributeValuesProvider} to be used for given attribute (if any)
	 *
	 * @param attribute
	 * @return {@link IAttributeValuesProvider} or null
	 */
	IAttributeValuesProvider createAttributeValuesProvider(org.compiere.model.I_M_Attribute attribute);

	/**
	 * Gets product attribute by ID.
	 * 
	 * If the attribute is applicable to given product (i.e. it's included in product's attribute set), the attribute will be returned.
	 * Else, null will be returned.
	 * 
	 * @param product
	 * @param attributeId
	 * @return {@link I_M_Attribute} or null
	 */
	I_M_Attribute getAttributeOrNull(I_M_Product product, int attributeId);

	boolean isSameTrx(I_M_AttributeValue attributeValue, boolean isSOTrx);

	/**
	 * @param attribute
	 * @return math context of this attribute or DEFAULT_MATHCONTEXT if the attribute's UOM is null
	 */
	MathContext getMathContext(org.compiere.model.I_M_Attribute attribute);

	/**
	 * Calculates Best-Before date for given product and bpartner.
	 * 
	 * @param ctx
	 * @param productId
	 * @param vendorBPartnerId
	 * @param dateReceipt
	 * @return best-before date or <code>null</code> if it does not apply
	 */
	Date calculateBestBeforeDate(Properties ctx, int productId, int vendorBPartnerId, Date dateReceipt);
}
