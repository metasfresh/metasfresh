package org.adempiere.process;

/*
 * #%L
 * de.metas.swat.base
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


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.order.createNewFromProposal.INewFromPoposalService;
import org.adempiere.order.createNewFromProposal.NewFromPoposalService;
import org.compiere.model.I_C_Order;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

public final class OrderCreateNewFromProposal extends SvrProcess {

	private final static CLogger log = CLogger
			.getCLogger(OrderCreateNewFromProposal.class);

	private MOrder sourceOrder;

	private int newOrderDocType;

	private Timestamp newOrderDateOrdered;

	private String poReference;

	private boolean newOrderClompleteIt = false;

	private final INewFromPoposalService service = new NewFromPoposalService();

	@Override
	protected String doIt() throws Exception {

		// setting the optional service properties
		service.setCompleteNewOrder(newOrderClompleteIt);
		service.setNewOrderDateOrdered(newOrderDateOrdered);
		service.setPOReference(poReference);

		final MDocType docType = MDocType.get(Env.getCtx(), newOrderDocType);

		final I_C_Order newOrder = service.create(sourceOrder, docType
				.getDocSubType(), get_TrxName());

		return newOrder.getDocumentNo();
	}

	@Override
	protected void prepare() {

		sourceOrder = new MOrder(Env.getCtx(), getRecord_ID(), null);

		//
		// check if we are invoked with a legal doc type
		final MDocType sourceOrderDocType = MDocType.get(Env.getCtx(),
				sourceOrder.getC_DocTypeTarget_ID());

		if (!(MDocType.DOCBASETYPE_SalesOrder.equals(sourceOrderDocType
				.getDocBaseType()) //
		&& MDocType.DOCSUBTYPE_Proposal.equals(sourceOrderDocType
				.getDocSubType())//
		)) {
			throw new IllegalStateException(
					"This process may be started for proposals only");
		}

		final ProcessInfoParameter[] para = getParameter();

		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null) {
				// do nothing
			} else if (name.equals("C_DocType_ID")) {
				newOrderDocType = ((BigDecimal) para[i].getParameter())
						.intValue();
			} else if (name.equals("DateOrdered")) {
				newOrderDateOrdered = (Timestamp) para[i].getParameter();
			} else if (name.equals("DocumentNo")) {
				poReference = (String) para[i].getParameter();
			} else if (name.equals("CompleteIt")) {
				
				final String strCompleteIt = (String) para[i].getParameter();
				newOrderClompleteIt = ("Y".equals(strCompleteIt));
			} else {
				log.severe("Unknown Parameter: " + name);
			}
		}
	}
}
