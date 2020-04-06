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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;

/**
 * Important:
 * <ul>
 * <li>use {@link IAttributeSetInstanceAwareFactoryService#createOrNull(Object)} to get your instance.
 * <li>there is a default implementation that might well do what you want
 * <li>if you need a custom implementation, (e.g. in order to call a custom business logic within a getter), then pls implement and register your own {@link IAttributeSetInstanceAwareFactory}.
 * </ul>
 *
 */
public interface IAttributeSetInstanceAware
{
	// @formatter:off
	String COLUMNNAME_M_Product_ID = I_M_Product.COLUMNNAME_M_Product_ID;
	I_M_Product getM_Product();
	int getM_Product_ID();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_M_AttributeSetInstance_ID = I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID;
	I_M_AttributeSetInstance getM_AttributeSetInstance();
	int getM_AttributeSetInstance_ID();
	void setM_AttributeSetInstance(final I_M_AttributeSetInstance asi);

	void setM_AttributeSetInstance_ID( int M_AttributeSetInstance_ID);
	// @formatter:on

}
