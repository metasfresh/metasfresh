package de.metas.acct.api;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Properties;

import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.acct.AcctSchemaTestHelper;
import de.metas.organization.OrgId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ProductAcctProviderTest
{
	private ClientId clientId;
	private OrgId orgId;
	private AcctSchemaId acctSchemaId;

	private IProductActivityProvider productActivityProvider;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		final Properties ctx = Env.getCtx();
		clientId = ClientId.ofRepoId(Env.getAD_Client_ID(ctx));
		orgId = OrgId.ofRepoId(Env.getAD_Org_ID(ctx));

		//
		// Master data
		// acctSchemaId = AcctSchemaTestHelper.newAcctSchema().build();
		acctSchemaId = AcctSchemaId.ofRepoId(1);
		AcctSchemaTestHelper.registerAcctSchemaDAOWhichAlwaysProvides(acctSchemaId);

		//
		// Service under test
		Services.registerService(IProductActivityProvider.class, Services.get(IProductAcctDAO.class));
		productActivityProvider = Services.get(IProductActivityProvider.class);
	}

	@Test
	public void test_WithActivity()
	{
		final ActivityId activityId = createActivity();
		final ProductId productId = createProduct(activityId);

		assertThat(getProductActivityId(productId)).isEqualTo(activityId);
	}

	@Test
	public void test_NoActivity()
	{
		final ProductId productId = createProduct(null);

		assertThat(getProductActivityId(productId)).isNull();
	}

	private ActivityId getProductActivityId(final ProductId productId)
	{
		return productActivityProvider.retrieveActivityForAcct(clientId, orgId, productId);
	}

	private ProductId createProduct(final ActivityId activityId)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		saveRecord(product);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final I_M_Product_Acct productAcct = newInstance(I_M_Product_Acct.class);
		productAcct.setC_AcctSchema_ID(acctSchemaId.getRepoId());
		productAcct.setM_Product_ID(productId.getRepoId());
		productAcct.setC_Activity_ID(ActivityId.toRepoId(activityId));
		saveRecord(productAcct);

		return productId;
	}

	private ActivityId createActivity()
	{
		final I_C_Activity activity = newInstance(I_C_Activity.class);
		saveRecord(activity);
		return ActivityId.ofRepoId(activity.getC_Activity_ID());
	}
}
