package org.compiere.acct;

<<<<<<< HEAD
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ProjectIssue;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.acct.api.AcctSchema;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.quantity.Quantity;
<<<<<<< HEAD
=======
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ProjectIssue;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

	public CostAmount getCreateCosts(final AcctSchema as)
	{
		if (isReversalLine())
		{
			return services.createReversalCostDetails(CostDetailReverseRequest.builder()
<<<<<<< HEAD
					.acctSchemaId(as.getId())
					.reversalDocumentRef(CostingDocumentRef.ofProjectIssueId(get_ID()))
					.initialDocumentRef(CostingDocumentRef.ofProjectIssueId(getReversalLine_ID()))
					.date(getDateAcct())
					.build())
					.getTotalAmountToPost(as);
=======
							.acctSchemaId(as.getId())
							.reversalDocumentRef(CostingDocumentRef.ofProjectIssueId(get_ID()))
							.initialDocumentRef(CostingDocumentRef.ofProjectIssueId(getReversalLine_ID()))
							.date(getDateAcctAsInstant())
							.build())
					.getMainAmountToPost(as);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else
		{
			return services.createCostDetail(
<<<<<<< HEAD
					CostDetailCreateRequest.builder()
							.acctSchemaId(as.getId())
							.clientId(getClientId())
							.orgId(getOrgId())
							.productId(getProductId())
							.attributeSetInstanceId(getAttributeSetInstanceId())
							.documentRef(CostingDocumentRef.ofProjectIssueId(get_ID()))
							.qty(getQty())
							.amt(CostAmount.zero(as.getCurrencyId())) // N/A
							.date(getDateAcct())
							.build())
					.getTotalAmountToPost(as);
=======
							CostDetailCreateRequest.builder()
									.acctSchemaId(as.getId())
									.clientId(getClientId())
									.orgId(getOrgId())
									.productId(getProductId())
									.attributeSetInstanceId(getAttributeSetInstanceId())
									.documentRef(CostingDocumentRef.ofProjectIssueId(get_ID()))
									.qty(getQty())
									.amt(CostAmount.zero(as.getCurrencyId()))// N/A
									.date(getDateAcctAsInstant())
									.build())
					.getMainAmountToPost(as);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}
}
