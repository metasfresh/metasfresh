package de.metas.acct.api;

import java.util.Optional;

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

import java.util.Properties;

import org.adempiere.service.ClientId;
import org.compiere.model.I_M_Product_Category_Acct;

import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.ISingletonService;

/**
 * @author al
 */
public interface IProductAcctDAO extends IProductActivityProvider, ISingletonService
{
	/**
	 * Gets the product activity from product accounting record using the matching accounting schema for given client/organization.
	 *
	 * @return activity or null
	 */
	@Override
	ActivityId retrieveActivityForAcct(ClientId clientId, OrgId orgId, ProductId productId);

	Optional<AccountId> getProductAcct(AcctSchemaId acctSchemaId, ProductId productId, ProductAcctType acctType);

	ActivityId getProductActivityId(ProductId productId);

	/**
	 * 
	 * @param ctx
	 * @param acctSchemaId
	 * @return default product category accounting; never returns null
	 */
	I_M_Product_Category_Acct retrieveDefaultProductCategoryAcct(Properties ctx, AcctSchemaId acctSchemaId);

	/**
	 * @param acctSchema
	 * @return default product category accounting; never returns null
	 */
	I_M_Product_Category_Acct retrieveDefaultProductCategoryAcct(AcctSchema acctSchema);
}
