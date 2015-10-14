package org.eevolution.api;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.model.IContextAware;
import org.compiere.model.I_M_Product;

/**
 * Helper class to update {@link I_M_Product#COLUMNNAME_LowLevel}.
 * 
 * By default, it's updating ALL products for context client.
 * 
 * @author tsa
 *
 */
public interface IProductLowLevelUpdater
{
	/**
	 * Execute Product's LowLevel update
	 * 
	 * @return
	 */
	IProductLowLevelUpdater update();

	/**
	 * @return how many products where updated
	 */
	int getUpdatedCount();

	/**
	 * @return how many errors we got while updating the products
	 */
	int getErrorsCount();

	IProductLowLevelUpdater setContext(final IContextAware context);

	/**
	 * 
	 * @param failOnFirstError true if we shall fail on first error (Default: false, i.e. don't fail)
	 * @return this
	 */
	IProductLowLevelUpdater setFailOnFirstError(boolean failOnFirstError);

}
