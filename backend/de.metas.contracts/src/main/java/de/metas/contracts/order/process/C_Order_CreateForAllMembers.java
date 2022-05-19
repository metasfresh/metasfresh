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
import de.metas.contracts.bpartner.service.MembershipOrderService;
import de.metas.contracts.bpartner.service.MembershipOrderCreateRequest;
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
	final MembershipOrderService membershipOrderService = SpringContextHolder.instance.getBean(MembershipOrderService.class);

	@Param(parameterName = I_AD_Org.COLUMNNAME_AD_Org_ID, mandatory = true)
	private OrgId p_orgId;

	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_ID, mandatory = true)
	private ProductId p_productId;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_StartDate, mandatory = true)
	private Instant p_startDate;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Conditions_ID, mandatory = true)
	private ConditionsId p_conditionsId;

	@Override
	protected String doIt() throws Exception
	{
		final MembershipOrderCreateRequest request = MembershipOrderCreateRequest.builder()
				.orgId(p_orgId)
				.productId(p_productId)
				.conditionsID(p_conditionsId)
				.startDate(p_startDate)
				.build();

		membershipOrderService.createMembershipContractOrders(request);

		return MSG_OK;
	}
}

