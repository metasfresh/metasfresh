package de.metas.order.createNewFromProposal;

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


import static org.adempiere.util.CustomColNames.C_Order_COMPLETE_ORDER_DISCOUNT;
import static org.adempiere.util.CustomColNames.C_Order_DESCRIPTION_BOTTOM;
import static org.adempiere.util.CustomColNames.C_Order_REF_PROPOSAL_ID;
import static org.adempiere.util.CustomColNames.M_Product_Category_C_DOCTYPE_ID;

import java.sql.Timestamp;
import java.util.HashMap;

import org.adempiere.misc.service.IPOService;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.MDocType;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProductCategory;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;

import de.metas.document.engine.IDocument;

public final class NewFromPoposalService implements INewFromPoposalService {

	private boolean newOrderClompleteIt;

	private Timestamp newOrderDateOrdered;

	private String poReference;

	public I_C_Order create(final MOrder proposal, String newOrderDocSubType,
			final String trxName) {

		if (proposal == null) {
			throw new IllegalArgumentException(
					"Param 'proposal' may not be null");
		}

		String docAction;
		if (newOrderClompleteIt) {
			docAction = IDocument.ACTION_Complete;
		} else {
			docAction = IDocument.ACTION_Prepare;
		}

		final int defaultDoctypeID = retriveDocSubType(newOrderDocSubType)
				.getC_DocType_ID();
		final HashMap<Integer, MOrder> categoryDocTypes = new HashMap<Integer, MOrder>();

		final IPOService poService = Services.get(IPOService.class);

		for (final MOrderLine proposalLine : proposal.getLines()) {

			final I_M_Product_Category productCategory = MProductCategory.get(
					Env.getCtx(), proposalLine.getProduct()
							.getM_Product_Category_ID());
			Integer c_doctype_id = (Integer) poService.getValue(
					productCategory, M_Product_Category_C_DOCTYPE_ID);

			if (c_doctype_id == null) {
				c_doctype_id = defaultDoctypeID;
			}

			MOrder newOrder;

			if (categoryDocTypes.containsKey(c_doctype_id)) {
				newOrder = categoryDocTypes.get(c_doctype_id);
			} else {
				newOrder = new MOrder(Env.getCtx(), 0, trxName);
				if (newOrderDateOrdered != null) {
					newOrder.setDateOrdered(newOrderDateOrdered);
				}
				if (poReference != null) {
					newOrder.setPOReference(poReference);
				}
				poService.setValue(newOrder, C_Order_REF_PROPOSAL_ID, proposal
						.get_ID());

				setParms(proposal, (MOrder) newOrder);

				if (c_doctype_id != null && c_doctype_id > 0) {
					newOrder.setC_DocType_ID(c_doctype_id);
					newOrder.setC_DocTypeTarget_ID(c_doctype_id);
				} else {
					newOrder.setC_DocType_ID(defaultDoctypeID);
					newOrder.setC_DocTypeTarget_ID(defaultDoctypeID);
					c_doctype_id = defaultDoctypeID;
				}
				categoryDocTypes.put(c_doctype_id, newOrder);
				poService.save(newOrder, trxName);
			}

			final boolean counter = true;
			final boolean copyASI = false;
			newOrder.copyLineFrom(counter, copyASI, proposalLine);
		}

		for (final MOrder order : categoryDocTypes.values()) {

			order.setDocAction(docAction);

			if (order.processIt(docAction)) {
				poService.save(order, trxName);
			} else {
				throw new IllegalStateException("Unable to process new order '"
						+ order.toString() + "'; DocAction =  " + docAction);
			}

			poService.setValue(proposal, C_Order_REF_PROPOSAL_ID, order
					.get_ID());

			poService.save(proposal, trxName);

			if (proposal.processIt(IDocument.ACTION_Close)) {
				poService.save(proposal, trxName);
			} else {
				throw new IllegalStateException("Unable to close proposal '"
						+ order.toString() + "'");
			}
			return order;
		}

		return null;
	}

	private void setParms(final MOrder proposalPO, final MOrder newOrderPO) {

		newOrderPO.setClientOrg(proposalPO.getAD_Client_ID(), proposalPO
				.getAD_Org_ID());

		final I_C_Order newOrder = newOrderPO;
		final I_C_Order proposal = proposalPO;

		newOrder.setAD_OrgTrx_ID(proposal.getAD_OrgTrx_ID());
		newOrder.setAD_User_ID(proposal.getAD_User_ID());
		newOrder.setBill_BPartner_ID(proposal.getBill_BPartner_ID());
		newOrder.setBill_Location_ID(proposal.getBill_Location_ID());
		newOrder.setBill_User_ID(proposal.getBill_User_ID());
		newOrder.setC_Activity_ID(proposal.getC_Activity_ID());
		newOrder.setC_BPartner_ID(proposal.getC_BPartner_ID());
		newOrder
				.setC_BPartner_Location_ID(proposal.getC_BPartner_Location_ID());
		newOrder.setC_Campaign_ID(proposal.getC_Campaign_ID());
		newOrder.setC_CashLine_ID(proposal.getC_CashLine_ID());
		newOrder.setC_Charge_ID(proposal.getC_Charge_ID());
		newOrder.setC_ConversionType_ID(proposal.getC_ConversionType_ID());
		newOrder.setC_Currency_ID(proposal.getC_Currency_ID());
		newOrder.setC_Payment_ID(proposal.getC_Payment_ID());
		newOrder.setC_PaymentTerm_ID(proposal.getC_PaymentTerm_ID());
		newOrder.setC_Project_ID(proposal.getC_Project_ID());
		newOrder.setChargeAmt(proposal.getChargeAmt());
		newOrder.setDescription(proposal.getDescription());
		newOrder.setFreightAmt(proposal.getFreightAmt());
		newOrder.setFreightCostRule(proposal.getFreightCostRule());
		newOrder.setInvoiceRule(proposal.getInvoiceRule());
		newOrder.setIsSOTrx(proposal.isSOTrx());
		newOrder.setIsTaxIncluded(proposal.isTaxIncluded());
		newOrder.setM_PriceList_ID(proposal.getM_PriceList_ID());
		newOrder.setM_Shipper_ID(proposal.getM_Shipper_ID());
		newOrder.setM_Warehouse_ID(proposal.getM_Warehouse_ID());
		newOrder.setPay_BPartner_ID(proposal.getPay_BPartner_ID());
		newOrder.setPay_Location_ID(proposal.getPay_Location_ID());
		newOrder.setPaymentRule(proposal.getPaymentRule());
		newOrder.setPriorityRule(proposal.getPriorityRule());
		newOrder.setSalesRep_ID(proposal.getSalesRep_ID());
		newOrder.setSendEMail(proposal.isSendEMail());
		newOrder.setUser1_ID(proposal.getUser1_ID());
		newOrder.setUser2_ID(proposal.getUser2_ID());
		newOrder.setVolume(proposal.getVolume());
		newOrder.setWeight(proposal.getWeight());
		newOrder.setDeliveryViaRule(proposal.getDeliveryViaRule());

		final IPOService poService = Services.get(IPOService.class);

		poService
				.copyValue(proposal, newOrder, C_Order_COMPLETE_ORDER_DISCOUNT);
		poService.copyValue(proposal, newOrder, C_Order_DESCRIPTION_BOTTOM);

	}

	public void setCompleteNewOrder(boolean newOrderClompleteIt) {
		this.newOrderClompleteIt = newOrderClompleteIt;
	}

	public void setNewOrderDateOrdered(Timestamp newOrderDateOrdered) {
		this.newOrderDateOrdered = newOrderDateOrdered;
	}

	public void setPOReference(String poReference) {
		this.poReference = poReference;
	}

	private MDocType retriveDocSubType(final String docSubType) {

		if (docSubType == null) {
			throw new IllegalArgumentException(
					"Param 'subtypeSO' may not be null");
		}

		for (MDocType docType : MDocType.getOfDocBaseType(Env.getCtx(),
				X_C_DocType.DOCBASETYPE_SalesOrder)) {

			if (docSubType.equals(docType.getDocSubType())) {
				return docType;
			}
		}

		throw new IllegalArgumentException(
				"Found no SO doctype with docSubType '" + docSubType + "'");
	}
}
