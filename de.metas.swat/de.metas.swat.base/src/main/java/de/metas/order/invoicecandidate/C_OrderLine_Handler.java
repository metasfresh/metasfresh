package de.metas.order.invoicecandidate;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Activity;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.IDocumentBL;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax.PriceAndTaxBuilder;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupCompensationUtils;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;

/**
 * Converts {@link I_C_OrderLine} to {@link I_C_Invoice_Candidate}.
 *
 */
public class C_OrderLine_Handler extends AbstractInvoiceCandidateHandler
{
	/**
	 * @return <code>false</code>, the candidates will be created by {@link C_Order_Handler}.
	 */
	@Override
	public boolean isCreateMissingCandidatesAutomatically()
	{
		return false;
	}

	/**
	 * @return <code>false</code>, the candidates will be created by {@link C_Order_Handler}.
	 */
	@Override
	public boolean isCreateMissingCandidatesAutomatically(Object model)
	{
		return false;
	};

	/**
	 * @see C_Order_Handler#expandRequest(InvoiceCandidateGenerateRequest)
	 */
	@Override
	public Object getModelForInvoiceCandidateGenerateScheduling(final Object model)
	{
		//
		// Retrieve order
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();
		return order;
	}

	@Override
	public Iterator<I_C_OrderLine> retrieveAllModelsWithMissingCandidates(final int limit)
	{
		return Services.get(IC_OrderLine_HandlerDAO.class).retrieveMissingOrderLinesQuery(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.create()
				.list(I_C_OrderLine.class)
				.iterator();
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		final I_C_OrderLine orderLine = request.getModel(I_C_OrderLine.class);

		final I_C_Invoice_Candidate ic = createCandidateForOrderLine(orderLine);
		return InvoiceCandidateGenerateResult.of(this, ic);
	}

	private I_C_Invoice_Candidate createCandidateForOrderLine(final I_C_OrderLine orderLine)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);

		Check.assume(Env.getAD_Client_ID(ctx) == orderLine.getAD_Client_ID(), "AD_Client_ID of " + orderLine + " and of its Ctx are the same");

		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);

		ic.setAD_Org_ID(orderLine.getAD_Org_ID());
		ic.setC_ILCandHandler(getHandlerRecord());

		ic.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(org.compiere.model.I_C_OrderLine.Table_Name));
		ic.setRecord_ID(orderLine.getC_OrderLine_ID());

		ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		ic.setM_Product_ID(orderLine.getM_Product_ID());
		ic.setIsPackagingMaterial(orderLine.isPackagingMaterial());
		ic.setC_Charge_ID(orderLine.getC_Charge_ID());
		ic.setQtyOrdered(orderLine.getQtyOrdered());
		ic.setDateOrdered(orderLine.getDateOrdered());
		ic.setPriceActual(orderLine.getPriceActual());
		ic.setPrice_UOM_ID(orderLine.getPrice_UOM_ID()); // 07090 when we set PiceActual, we shall also set PriceUOM.
		ic.setPriceEntered(orderLine.getPriceEntered()); // cg : task 04917
		ic.setDiscount(orderLine.getDiscount()); // cg: 04868
		ic.setC_Currency_ID(orderLine.getC_Currency_ID());

		ic.setQtyToInvoice(BigDecimal.ZERO); // to be computed

		ic.setDescription(orderLine.getDescription()); // 03439

		final I_C_Order order = InterfaceWrapperHelper.create(orderLine.getC_Order(), I_C_Order.class);

		setBPartnerData(ic, orderLine);
		setGroupCompensationData(ic, orderLine);

		//
		// Invoice Rule(s)
		{
			ic.setInvoiceRule(order.getInvoiceRule());

			// If we are dealing with a non-receivable service set the InvoiceRule_Override to Immediate
			// because we want to invoice those right away (08408)
			if (isNotReceivebleService(ic))
			{
				ic.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_OVERRIDE_Sofort); // immediate
			}
		}

		ic.setM_PricingSystem_ID(order.getM_PricingSystem_ID());

		// 05265
		ic.setIsSOTrx(orderLine.getC_Order().isSOTrx());

		ic.setQtyOrderedOverUnder(orderLine.getQtyOrderedOverUnder());

		// 07442 activity and tax

		final I_C_Activity activity;
		if (orderLine.getC_Activity_ID() > 0)
		{
			// https://github.com/metasfresh/metasfresh/issues/2299
			activity = orderLine.getC_Activity();
		}
		else
		{
			final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(orderLine);
			activity = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(contextProvider, orderLine.getAD_Org(), orderLine.getM_Product());
		}
		ic.setC_Activity(activity);

		final int taxId = Services.get(ITaxBL.class).getTax(
				ctx, ic, orderLine.getC_TaxCategory_ID(), orderLine.getM_Product_ID(), orderLine.getC_Charge_ID() // chargeId
				, order.getDatePromised() // billDate
				, order.getDatePromised() // shipDate
				, order.getAD_Org_ID(), order.getM_Warehouse(), order.getBill_Location_ID() // bill location id
				, order.getC_BPartner_Location_ID() // ship location id
				, order.isSOTrx() // isSOTrx
				, trxName);

		ic.setC_Tax_ID(taxId);

		// set Quality Issue Percentage Override

		final I_M_AttributeSetInstance asi = orderLine.getM_AttributeSetInstance();
		final List<I_M_AttributeInstance> instances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(asi);

		Services.get(IInvoiceCandBL.class).setQualityDiscountPercent_Override(ic, instances);

		// Don't save.
		// That's done by the invoking API-impl, because we want to avoid C_Invoice_Candidate.invalidateCandidates() from being called on every single IC that is created here.
		// Because it's a performance nightmare for orders with a lot of lines
		// InterfaceWrapperHelper.save(ic);

		return ic;
	}

	/**
	 * Invalidates the candidate(s) referencing the given order line. If {@link IInvoiceCandBL#isChangedByUpdateProcess(I_C_Invoice_Candidate)} returns <code>false</code> for any given candidate, this
	 * method additionally invalidates all candidates with the same header aggregation key and (depending on invoice schedule) even more dependent candidates.
	 */
	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
		invalidateForOrderLine(ol);
	}

	private void invalidateForOrderLine(
			final I_C_OrderLine orderLine)
	{
		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);

		final List<I_C_Invoice_Candidate> ics = invoiceCandDB
				.fetchInvoiceCandidates(ctx, org.compiere.model.I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID(), trxName);
		for (final I_C_Invoice_Candidate ic : ics)
		{
			invoiceCandDB.invalidateCand(ic);
		}
	}

	@Override
	public String getSourceTable()
	{
		return org.compiere.model.I_C_OrderLine.Table_Name;
	}

	/**
	 * Returns <code>true</code>.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return true;
	}

	/**
	 * <ul>
	 * <li>QtyOrdered := C_OrderLine.QtyOrdered
	 * <li>DateOrdered := C_OrderLine.DateOrdered
	 * <li>C_Order_ID: C_OrderLine.C_Order_ID
	 * <li>C_PaymentTerm_ID: C_OrderLine.C_PaymentTerm_ID/C_Order.C_PaymentTerm_ID
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setOrderedData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		final org.compiere.model.I_C_OrderLine orderLine = ic.getC_OrderLine();

		// we use C_OrderLine.QtyOrdered which is fine, but which is also in the product's stocking UOM
		ic.setQtyOrdered(orderLine.getQtyOrdered());
		ic.setDateOrdered(orderLine.getDateOrdered());
		ic.setC_Order_ID(orderLine.getC_Order_ID());

		setC_PaymentTerm(ic);
	}

	private void setC_PaymentTerm(final I_C_Invoice_Candidate ic)
	{
		if (!ic.isSOTrx())
		{
			return;
		}

		final org.compiere.model.I_C_OrderLine orderLine = ic.getC_OrderLine();

		if (orderLine.getC_PaymentTerm_Override_ID() > 0)
		{
			ic.setC_PaymentTerm_ID(orderLine.getC_PaymentTerm_Override_ID());
		}
		else
		{
			final org.compiere.model.I_C_Order order = orderLine.getC_Order();
			ic.setC_PaymentTerm_ID(order.getC_PaymentTerm_ID());
		}
	}

	/**
	 * Sets {@link I_C_Invoice_Candidate#COLUMNNAME_QtyDelivered C_Invoice_Candidate.QtyDelivered} to {@link I_C_OrderLine#COLUMNNAME_QtyDelivered C_OrderLine.QtyDelivered}.
	 * <p>
	 * Sets {@link I_C_Invoice_Candidate#COLUMNNAME_DeliveryDate C_Invoice_Candidate.DeliveryDate} the MovementDate of the first InOut that is referenced by this ic via
	 * {@link I_C_InvoiceCandidate_InOutLine}, and {@link I_C_Invoice_Candidate#COLUMNNAME_M_InOut_ID C_Invoice_Candidate.M_InOut_ID} to that inout's ID. <br>
	 * "First" means the one with first <code>MovementDate</code> or (if the date)the smallest <code>M_InOut_ID</code>.<br>
	 * <p>
	 * If the given ic has no InOut, then <code>QtyDelivered</code>, <code>DeliveryDate</code> and <code>M_InOut_ID</code> are set to <code>null</code>.
	 *
	 */
	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		final org.compiere.model.I_C_OrderLine orderLine = ic.getC_OrderLine();

		ic.setQtyDelivered(orderLine.getQtyDelivered());

		//
		// Find out the first shipment/receipt
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final List<I_C_InvoiceCandidate_InOutLine> icIols = invoiceCandDAO.retrieveICIOLAssociationsExclRE(ic);
		I_M_InOut firstInOut = null;
		for (final I_C_InvoiceCandidate_InOutLine icIol : icIols)
		{
			final I_M_InOut inOut = icIol.getM_InOutLine().getM_InOut();

			// Consider only completed shipments/receipts
			if (!docActionBL.isDocumentCompletedOrClosed(inOut))
			{
				continue;
			}

			if (firstInOut == null)
			{
				firstInOut = inOut;
			}
			else if (firstInOut.getMovementDate().after(inOut.getMovementDate())
					|| firstInOut.getMovementDate().equals(inOut.getMovementDate()) && firstInOut.getM_InOut_ID() > inOut.getM_InOut_ID())
			{
				firstInOut = inOut;
			}
		}

		setDeliveredDataFromFirstInOut(ic, firstInOut);
	}

	@Override
	public PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(ic.getC_OrderLine(), I_C_OrderLine.class);

		// ts: we *must* use the order line's data
		final PriceAndTaxBuilder priceAndTax = PriceAndTax.builder()
				.priceEntered(orderLine.getPriceEntered())
				.priceActual(orderLine.getPriceActual())
				.priceUOMId(orderLine.getPrice_UOM_ID())
				.taxIncluded(orderLine.getC_Order().isTaxIncluded());

		//
		// Percent Group Compensation Line
		if (ic.isGroupCompensationLine() && GroupCompensationAmtType.Percent.getAdRefListValue().equals(ic.getGroupCompensationAmtType()))
		{
			final InvoiceCandidateGroupRepository groupsRepo = Adempiere.getBean(InvoiceCandidateGroupRepository.class);

			final GroupId groupId = groupsRepo.extractGroupId(ic);
			final Group group = groupsRepo.retrieveGroup(groupId);
			group.updateAllPercentageLines();

			final GroupCompensationLine compensationLine = group.getCompensationLineById(groupsRepo.extractLineId(ic));
			priceAndTax.priceEntered(compensationLine.getPrice());
			priceAndTax.priceActual(compensationLine.getPrice());
			priceAndTax.compensationGroupBaseAmt(compensationLine.getBaseAmt());
			// NOTE: we assume AmtType does not change so nor the Qty (which in this case shall be ONE)
		}

		return priceAndTax.build();
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final org.compiere.model.I_C_OrderLine orderLine = ic.getC_OrderLine();
		setBPartnerData(ic, orderLine);
	}

	private void setBPartnerData(final I_C_Invoice_Candidate ic, final org.compiere.model.I_C_OrderLine orderLine)
	{
		final org.compiere.model.I_C_Order order = orderLine.getC_Order();
		ic.setBill_BPartner_ID(order.getBill_BPartner_ID());
		ic.setBill_Location_ID(order.getBill_Location_ID());
		ic.setBill_User_ID(order.getBill_User_ID());

	}

	@Override
	public void setC_UOM_ID(final I_C_Invoice_Candidate ic)
	{
		final org.compiere.model.I_C_OrderLine orderLine = ic.getC_OrderLine();
		ic.setC_UOM_ID(orderLine.getC_UOM_ID());
	}

	private void setGroupCompensationData(final I_C_Invoice_Candidate ic, final I_C_OrderLine fromOrderLine)
	{
		if (!OrderGroupCompensationUtils.isInGroup(fromOrderLine))
		{
			return;
		}

		ic.setC_Order_CompensationGroup_ID(fromOrderLine.getC_Order_CompensationGroup_ID());
		ic.setIsGroupCompensationLine(fromOrderLine.isGroupCompensationLine());
		ic.setGroupCompensationType(fromOrderLine.getGroupCompensationType());
		ic.setGroupCompensationAmtType(fromOrderLine.getGroupCompensationAmtType());
		ic.setGroupCompensationPercentage(fromOrderLine.getGroupCompensationPercentage());
	}

}
