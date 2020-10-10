package de.metas.manufacturing.acct;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.doc.AcctDocContext;
import de.metas.document.engine.DocStatus;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.util.Services;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.model.X_C_DocType;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class Doc_PPOrder extends Doc<DocLine<Doc_PPOrder>>
{
	private static final Logger logger = LogManager.getLogger(Doc_PPOrder.class);
	private final IPPOrderCostBL orderCostBL = Services.get(IPPOrderCostBL.class);

	public Doc_PPOrder(final AcctDocContext ctx)
	{
		super(ctx, X_C_DocType.DOCBASETYPE_ManufacturingOrder);

		final I_PP_Order ppOrder = getModel(I_PP_Order.class);
		setDateAcct(ppOrder.getDateOrdered());
	}

	@Override
	protected void loadDocumentDetails()
	{
		// nothing
	}

	private PPOrderId getPPOrderId()
	{
		return PPOrderId.ofRepoId(get_ID());
	}

	private I_PP_Order getPPOrder()
	{
		return getModel(I_PP_Order.class);
	}

	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		final DocStatus docStatus = DocStatus.ofCode(getDocStatus());
		if (docStatus.isCompletedOrClosed())
		{
			createOrderCosts();
		}
		else if (DocStatus.Voided.equals(docStatus))
		{
			logger.debug("Skip creating costs for voided documents");
		}
		else
		{
			throw newPostingException()
					.setPreserveDocumentPostedStatus()
					.setDetailMessage("Invalid document status: " + docStatus);
		}

		return ImmutableList.of();
	}

	private void createOrderCosts()
	{
		if (!orderCostBL.hasPPOrderCosts(getPPOrderId()))
		{
			orderCostBL.createOrderCosts(getPPOrder());
		}
	}
}
