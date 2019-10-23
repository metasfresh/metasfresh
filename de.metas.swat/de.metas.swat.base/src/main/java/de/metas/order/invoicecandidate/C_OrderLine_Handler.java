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
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.adempiere.model.I_C_Order;
import de.metas.document.engine.DocStatus;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.InvoiceCandidateIds;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.compensationGroup.InvoiceCandidateGroupRepository;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.PriceAndTax.PriceAndTaxBuilder;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.order.IOrderLineBL;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationAmtType;
import de.metas.order.compensationGroup.GroupCompensationLine;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.OrderGroupCompensationUtils;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;

/**
 * Converts {@link I_C_OrderLine} to {@link I_C_Invoice_Candidate}.
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
	}

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
	public Iterator<? extends Object> retrieveAllModelsWithMissingCandidates(final int limit)
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
		final IProductBL productBL = Services.get(IProductBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
		final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);

		Check.assume(Env.getAD_Client_ID(ctx) == orderLine.getAD_Client_ID(), "AD_Client_ID of " + orderLine + " and of its Ctx are the same");

		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, trxName);

		ic.setAD_Org_ID(orderLine.getAD_Org_ID());
		ic.setC_ILCandHandler(getHandlerRecord());

		ic.setAD_Table_ID(Services.get(IADTableDAO.class).retrieveTableId(org.compiere.model.I_C_OrderLine.Table_Name));
		ic.setRecord_ID(orderLine.getC_OrderLine_ID());

		ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());

		final int productRecordId = orderLine.getM_Product_ID();
		ic.setM_Product_ID(productRecordId);

		boolean isFreightCostProduct = productBL.isFreightCostProduct(ProductId.ofRepoId(productRecordId));

		ic.setIsFreightCost(isFreightCostProduct);
		ic.setIsPackagingMaterial(orderLine.isPackagingMaterial());
		ic.setC_Charge_ID(orderLine.getC_Charge_ID());

		setOrderedData(ic, orderLine);

		ic.setInvoicableQtyBasedOn(orderLine.getInvoicableQtyBasedOn());
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
				ic.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_OVERRIDE_Immediate); // immediate
			}
		}

		ic.setM_PricingSystem_ID(order.getM_PricingSystem_ID());

		// 05265
		ic.setIsSOTrx(orderLine.getC_Order().isSOTrx());

		ic.setQtyOrderedOverUnder(orderLine.getQtyOrderedOverUnder());

		// 07442 activity and tax

		final ActivityId activityId;
		if (orderLine.getC_Activity_ID() > 0)
		{
			// https://github.com/metasfresh/metasfresh/issues/2299
			activityId = ActivityId.ofRepoId(orderLine.getC_Activity_ID());
		}
		else
		{
			activityId = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(
					ClientId.ofRepoId(orderLine.getAD_Client_ID()),
					OrgId.ofRepoId(orderLine.getAD_Org_ID()),
					ProductId.ofRepoId(orderLine.getM_Product_ID()));
		}
		ic.setC_Activity_ID(ActivityId.toRepoId(activityId));

		final int taxId = Services.get(ITaxBL.class).getTax(
				ctx,
				ic,
				TaxCategoryId.ofRepoIdOrNull(orderLine.getC_TaxCategory_ID()),
				orderLine.getM_Product_ID(),
				order.getDatePromised(), // shipDate
				OrgId.ofRepoId(order.getAD_Org_ID()),
				WarehouseId.ofRepoIdOrNull(order.getM_Warehouse_ID()),
				CoalesceUtil.firstGreaterThanZero(order.getDropShip_Location_ID(), order.getC_BPartner_Location_ID()), // ship location id
				order.isSOTrx());
		ic.setC_Tax_ID(taxId);

		// set Quality Issue Percentage Override

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributes = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asiId);

		Services.get(IInvoiceCandBL.class).setQualityDiscountPercent_Override(ic, attributes);

		// Don't save.
		// That's done by the invoking API-impl, because we want to avoid C_Invoice_Candidate.invalidateCandidates() from being called on every single IC that is created here.
		// Because it's a performance nightmare for orders with a lot of lines
		// InterfaceWrapperHelper.save(ic);

		return ic;
	}

	@Override
	public String getSourceTable()
	{
		return I_C_OrderLine.Table_Name;
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
	 * <li>QtyEntered := C_OrderLine.QtyEntered
	 * <li>C_UOM_ID := C_OrderLine.C_UOM_ID
	 * <li>QtyOrdered := C_OrderLine.QtyOrdered
	 * <li>DateOrdered := C_OrderLine.DateOrdered
	 * <li>C_Order_ID: C_OrderLine.C_Order_ID
	 * <li>C_PaymentTerm_ID: C_OrderLine.C_PaymentTerm_ID/C_Order.C_PaymentTerm_ID
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setOrderedData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final org.compiere.model.I_C_OrderLine orderLine = ic.getC_OrderLine();

		setOrderedData(ic, orderLine);
	}

	private void setOrderedData(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		// prefer priceUOM, if given
		if (orderLine.getPrice_UOM_ID() > 0)
		{
			ic.setQtyEntered(orderLine.getQtyEnteredInPriceUOM());
			ic.setC_UOM_ID(orderLine.getPrice_UOM_ID());
		}
		else
		{
			ic.setQtyEntered(orderLine.getQtyEntered());
			ic.setC_UOM_ID(orderLine.getC_UOM_ID());
		}

		// we use C_OrderLine.QtyOrdered which is fine, but which is also in the product's stocking UOM
		ic.setQtyOrdered(orderLine.getQtyOrdered());
		ic.setDateOrdered(orderLine.getDateOrdered());

		ic.setPresetDateInvoiced(orderLine.getPresetDateInvoiced());

		ic.setC_Order_ID(orderLine.getC_Order_ID());

		setC_PaymentTerm(ic, orderLine);
	}

	private void setC_PaymentTerm(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final org.compiere.model.I_C_OrderLine orderLine)
	{
		if (!ic.isSOTrx())
		{
			return;
		}

		final PaymentTermId paymentTermId = Services.get(IOrderLineBL.class).getPaymentTermId(orderLine);
		ic.setC_PaymentTerm_ID(paymentTermId.getRepoId());
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
	public void setDeliveredData(final I_C_Invoice_Candidate icRecord)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final InvoiceCandidateRecordService invoiceCandidateRecordService = SpringContextHolder.instance.getBean(InvoiceCandidateRecordService.class);

		StockQtyAndUOMQty qtysDelivered = invoiceCandidateRecordService
				.ofRecord(icRecord)
				.computeQtysDelivered();

		if (qtysDelivered.getStockQty().isZero())
		{
			final org.compiere.model.I_C_OrderLine orderLine = icRecord.getC_OrderLine();
			if (orderLine.getQtyDelivered().signum() > 0)
			{
				// fallback to C_OrderLine.QtyDelivered...maybe there are cases with no-item-products, where we have QtyDelivered, but no shipments
				qtysDelivered = StockQtyAndUOMQtys.create(
						orderLine.getQtyDelivered(), ProductId.ofRepoId(orderLine.getM_Product_ID()),
						orderLine.getQtyEntered(), UomId.ofRepoId(orderLine.getC_UOM_ID()));
			}
		}
		icRecord.setQtyDelivered(qtysDelivered.getStockQty().toBigDecimal());
		icRecord.setQtyDeliveredInUOM(qtysDelivered.getUOMQtyNotNull().toBigDecimal());
		//
		// Find out the first shipment/receipt
		final List<I_C_InvoiceCandidate_InOutLine> icIols = invoiceCandDAO.retrieveICIOLAssociationsExclRE(InvoiceCandidateIds.ofRecord(icRecord));
		I_M_InOut firstInOut = null;
		for (final I_C_InvoiceCandidate_InOutLine icIol : icIols)
		{
			final I_M_InOut inOut = icIol.getM_InOutLine().getM_InOut();

			// Consider only completed shipments/receipts
			if (!DocStatus.ofCode(inOut.getDocStatus()).isCompletedOrClosed())
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

		setDeliveredDataFromFirstInOut(icRecord, firstInOut);
	}

	@Override
	public PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(ic.getC_OrderLine(), I_C_OrderLine.class);

		// ts: we *must* use the order line's data
		final PriceAndTaxBuilder priceAndTax = PriceAndTax.builder()
				.invoicableQtyBasedOn(InvoicableQtyBasedOn.fromRecordString(orderLine.getInvoicableQtyBasedOn()))
				.priceEntered(orderLine.getPriceEntered())
				.priceActual(orderLine.getPriceActual())
				.priceUOMId(UomId.ofRepoIdOrNull(orderLine.getPrice_UOM_ID()))
				.taxIncluded(orderLine.getC_Order().isTaxIncluded());

		//
		// Percent Group Compensation Line
		if (ic.isGroupCompensationLine() && GroupCompensationAmtType.Percent.getAdRefListValue().equals(ic.getGroupCompensationAmtType()))
		{
			final InvoiceCandidateGroupRepository groupsRepo = SpringContextHolder.instance.getBean(InvoiceCandidateGroupRepository.class);

			final GroupId groupId = groupsRepo.extractGroupId(ic);
			final Group group = groupsRepo.retrieveGroup(groupId);
			group.updateAllCompensationLines();

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
		ic.setC_BPartner_SalesRep_ID(order.getC_BPartner_SalesRep_ID());
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

	/**
	 * Invalidates the candidate(s) referencing the given order line. If {@link IInvoiceCandBL#isChangedByUpdateProcess(I_C_Invoice_Candidate)} returns <code>false</code> for any given candidate, this
	 * method additionally invalidates all candidates with the same header aggregation key and (depending on invoice schedule) even more dependent candidates.
	 */
	@Override
	public final void invalidateCandidatesFor(final Object model)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(model));
	}

}
