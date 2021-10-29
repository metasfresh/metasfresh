/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.order.process;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.bpartner.service.MembershipContractRequest;
import de.metas.contracts.bpartner.service.MembershipContractService;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.product.ProductId;
import de.metas.product.model.I_M_Product;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Org;

import java.time.Instant;

public class C_Order_CreateForAllMembers extends JavaProcess

{
	final MembershipContractService membershipContractService = SpringContextHolder.instance.getBean(MembershipContractService.class);

	@Param(parameterName = I_AD_Org.COLUMNNAME_AD_Org_ID, mandatory = true)
	private int p_AD_Org_ID;

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_ID, mandatory = true)
	private int p_M_Product_ID;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_StartDate, mandatory = true)
	private Instant p_startDate;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, mandatory = true)
	private int p_C_FLatrate_Condition_ID;

	@Override
	protected String doIt() throws Exception
	{
		final MembershipContractRequest request = MembershipContractRequest.builder()
				.orgId(OrgId.ofRepoId(p_AD_Org_ID))
				.productId(ProductId.ofRepoId(p_M_Product_ID))
				.conditionsID(ConditionsId.ofRepoId(p_C_FLatrate_Condition_ID))
				.startDate(p_startDate)
				.build();

		membershipContractService.createMembershipContractOrders(request);

		return MSG_OK;
	}
}

