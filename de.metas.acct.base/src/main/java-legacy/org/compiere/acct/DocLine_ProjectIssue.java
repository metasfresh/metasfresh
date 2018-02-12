package org.compiere.acct;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_ProjectIssue;
import org.compiere.util.TimeUtil;

import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostResult;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostDetailService;
import de.metas.quantity.Quantity;

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

public class DocLine_ProjectIssue extends DocLine<Doc_ProjectIssue>
{

	public DocLine_ProjectIssue(I_C_ProjectIssue projectIssue, Doc_ProjectIssue doc)
	{
		super(InterfaceWrapperHelper.getPO(projectIssue), doc);
		setQty(Quantity.of(projectIssue.getMovementQty(), getProductStockingUOM()), true);
	}

	public CostResult getCreateCosts(final I_C_AcctSchema as)
	{
		final ICostDetailService costDetailService = Adempiere.getBean(ICostDetailService.class);

		if (isReversalLine())
		{
			return costDetailService.createReversalCostDetails(CostDetailReverseRequest.builder()
					.acctSchemaId(as.getC_AcctSchema_ID())
					.reversalDocumentRef(CostingDocumentRef.ofProjectIssueId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofProjectIssueId(getReversalLine_ID()))
					.date(TimeUtil.asLocalDate(getDateDoc()))
					.build());
		}
		else
		{
			return costDetailService.createCostDetail(
					CostDetailCreateRequest.builder()
							.acctSchemaId(as.getC_AcctSchema_ID())
							.clientId(getAD_Client_ID())
							.orgId(getAD_Org_ID())
							.productId(getM_Product_ID())
							.attributeSetInstanceId(getM_AttributeSetInstance_ID())
							.documentRef(CostingDocumentRef.ofProjectIssueId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.zero(as.getC_Currency_ID())) // N/A
							.date(TimeUtil.asLocalDate(getDateDoc()))
							.build());
		}
	}
}
