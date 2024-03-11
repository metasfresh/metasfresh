/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.document.engine.IDocumentBL;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import static org.adempiere.model.InterfaceWrapperHelper.save;

@Service
public class CallOrderContractService
{
	private static final AdMessageKey MSG_MISSING_FLATRATE_TERM = AdMessageKey.of("MISSING_FLATRATE_TERM");
	private static final AdMessageKey MSG_WRONG_TYPE_CONDITIONS = AdMessageKey.of("WRONG_TYPE_CONDITIONS");
	private static final AdMessageKey MSG_BPARTNERS_DO_NOT_MATCH = AdMessageKey.of("BPARTNERS_DO_NOT_MATCH");
	private static final AdMessageKey MSG_PRODUCTS_DO_NOT_MATCH = AdMessageKey.of("PRODUCTS_DO_NOT_MATCH");
	private static final AdMessageKey MSG_SALES_CALL_ORDER_CONTRACT_TRX_NOT_MATCH = AdMessageKey.of("SALES_CALL_ORDER_CONTRACT_TRX_NOT_MATCH");
	private static final AdMessageKey MSG_PURCHASE_CALL_ORDER_CONTRACT_TRX_NOT_MATCH = AdMessageKey.of("PURCHASE_CALL_ORDER_CONTRACT_TRX_NOT_MATCH");

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	@NonNull
	public I_C_Flatrate_Term createCallOrderContract(@NonNull final I_C_OrderLine orderLine)
	{
		final I_C_Order order = orderBL.getById(OrderId.ofRepoId(orderLine.getC_Order_ID()));

		final I_C_Flatrate_Term newTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, orderLine);

		//order references
		newTerm.setC_OrderLine_Term_ID(orderLine.getC_OrderLine_ID());
		newTerm.setC_Order_Term_ID(orderLine.getC_Order_ID());

		Check.assume(orderLine.getC_Flatrate_Conditions_ID() > 0, "C_Flatrate_Conditions_ID must be set!");
		newTerm.setC_Flatrate_Conditions_ID(orderLine.getC_Flatrate_Conditions_ID());

		//qty of the whole contract
		newTerm.setPlannedQtyPerUnit(orderLine.getQtyEnteredInPriceUOM());
		newTerm.setC_UOM_ID(orderLine.getPrice_UOM_ID());

		//price & tax
		newTerm.setM_Product_ID(orderLine.getM_Product_ID());
		newTerm.setPriceActual(orderLine.getPriceActual());
		newTerm.setC_Currency_ID(orderLine.getC_Currency_ID());

		Check.assume(orderLine.getC_TaxCategory_ID() > 0, "C_TaxCategory_ID must be set!");
		newTerm.setC_TaxCategory_ID(orderLine.getC_TaxCategory_ID());
		newTerm.setIsTaxIncluded(order.isTaxIncluded());

		//contract dates
		newTerm.setStartDate(order.getDatePromised());
		newTerm.setMasterStartDate(order.getDatePromised());

		//bill partner info
		final BPartnerLocationAndCaptureId billToLocationId = orderBL.getBillToLocationId(order);

		final BPartnerContactId billToContactId = BPartnerContactId.ofRepoIdOrNull(billToLocationId.getBpartnerId(), order.getBill_User_ID());
		ContractDocumentLocationAdapterFactory
				.billLocationAdapter(newTerm)
				.setFrom(billToLocationId, billToContactId);

		final I_C_BPartner billPartner = bPartnerDAO.getById(billToLocationId.getBpartnerId());
		final I_C_Flatrate_Data existingData = flatrateDAO.retrieveOrCreateFlatrateData(billPartner);
		newTerm.setC_Flatrate_Data(existingData);

		//doc
		newTerm.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Drafted);
		newTerm.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);

		save(newTerm);

		documentBL.processEx(newTerm, X_C_Flatrate_Term.DOCACTION_Complete, X_C_Flatrate_Term.DOCSTATUS_Completed);

		return newTerm;
	}

	public boolean isCallOrderContractLine(@NonNull final I_C_OrderLine ol)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(ol.getC_Flatrate_Conditions_ID());

		if (conditionsId == null)
		{
			return false;
		}

		final I_C_Flatrate_Conditions typeConditions = flatrateDAO.getConditionsById(conditionsId);

		return TypeConditions.CALL_ORDER.getCode().equals(typeConditions.getType_Conditions());
	}

	public boolean isCallOrderContract(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term contract = flatrateBL.getById(flatrateTermId);

		return isCallOrderContract(contract);
	}

	@NonNull
	public FlatrateTermId validateCallOrderLine(@NonNull final I_C_OrderLine callOrderLine, @NonNull final I_C_Order callOrder)
	{
		final FlatrateTermId flatrateTermId = FlatrateTermId.ofRepoIdOrNull(callOrderLine.getC_Flatrate_Term_ID());
		final int orderLineSeqNo = callOrderLine.getLine();

		if (flatrateTermId == null)
		{
			throw new AdempiereException(MSG_MISSING_FLATRATE_TERM, orderLineSeqNo).markAsUserValidationError();
		}

		final I_C_Flatrate_Term callOrderContract = flatrateBL.getById(flatrateTermId);

		if (!isCallOrderContract(callOrderContract))
		{
			throw new AdempiereException(MSG_WRONG_TYPE_CONDITIONS, orderLineSeqNo)
					.markAsUserValidationError();
		}

		final ProductId orderLineProductId = ProductId.ofRepoId(callOrderLine.getM_Product_ID());
		validateProduct(callOrderContract, orderLineProductId, orderLineSeqNo);

		final BPartnerId orderBillBPartnerId = orderBL.getBillToLocationId(callOrder).getBpartnerId();
		validateBillPartner(callOrderContract, orderBillBPartnerId, orderLineSeqNo);

		validateSOTrx(callOrderContract, SOTrx.ofBoolean(callOrder.isSOTrx()), callOrderLine.getLine());

		return flatrateTermId;
	}

	public void validateCallOrderInvoiceLine(@NonNull final I_C_InvoiceLine invoiceLine, @NonNull final FlatrateTermId callOrderContractId)
	{
		final I_C_Flatrate_Term callOrderContract = flatrateDAO.getById(callOrderContractId);
		final int invoiceLineSeqNo = invoiceLine.getLine();

		final ProductId invoiceLineProductId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
		validateProduct(callOrderContract, invoiceLineProductId, invoiceLineSeqNo);

		final I_C_Invoice invoice = invoiceBL.getById(InvoiceId.ofRepoId(invoiceLine.getC_Invoice_ID()));
		final BPartnerId billPartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		validateBillPartner(callOrderContract, billPartnerId, invoiceLineSeqNo);

		validateSOTrx(callOrderContract, SOTrx.ofBoolean(invoice.isSOTrx()), invoiceLineSeqNo);
	}

	public void validateCallOrderInOutLine(@NonNull final I_M_InOutLine inOutLine, @NonNull final FlatrateTermId callOrderContractId)
	{
		final I_C_Flatrate_Term callOrderContract = flatrateDAO.getById(callOrderContractId);
		final int inOutLineSeqNo = inOutLine.getLine();

		final ProductId inOutLineProductId = ProductId.ofRepoId(inOutLine.getM_Product_ID());
		validateProduct(callOrderContract, inOutLineProductId, inOutLineSeqNo);

		final I_M_InOut inOut = inOutBL.getById(InOutId.ofRepoId(inOutLine.getM_InOut_ID()));
		validateSOTrx(callOrderContract, SOTrx.ofBoolean(inOut.isSOTrx()), inOutLineSeqNo);
	}

	public boolean doesContractMatchOrderLine(@NonNull final I_C_OrderLine callOrderLine, @NonNull final I_C_Flatrate_Term callOrderContract)
	{
		if (!isCallOrderContract(callOrderContract))
		{
			return false;
		}

		final ProductId orderLineProduct = ProductId.ofRepoId(callOrderLine.getM_Product_ID());
		final ProductId contractProductId = ProductId.ofRepoIdOrNull(callOrderContract.getM_Product_ID());

		if (!orderLineProduct.equals(contractProductId))
		{
			return false;
		}

		final BPartnerId orderBillBPartnerId = orderBL.getBillToLocationId(callOrderLine.getC_Order()).getBpartnerId();
		final BPartnerId contractBillPartnerId = BPartnerId.ofRepoId(callOrderContract.getBill_BPartner_ID());

		return contractBillPartnerId.equals(orderBillBPartnerId);
	}

	private void validateProduct(
			@NonNull final I_C_Flatrate_Term callOrderContract,
			@NonNull final ProductId documentLineProductId,
			final int documentLineSeqNo)
	{
		final ProductId contractProductId = ProductId.ofRepoIdOrNull(callOrderContract.getM_Product_ID());

		if (!documentLineProductId.equals(contractProductId))
		{
			final I_M_Product documentLineProduct = productDAO.getById(documentLineProductId);

			final String contractProductValue = contractProductId != null
					? productDAO.getById(contractProductId).getValue()
					: null;

			throw new AdempiereException(MSG_PRODUCTS_DO_NOT_MATCH,
										 documentLineProduct.getValue(),
										 documentLineSeqNo,
										 contractProductValue)
					.markAsUserValidationError();
		}
	}

	private void validateBillPartner(
			@NonNull final I_C_Flatrate_Term callOrderContract,
			@NonNull final BPartnerId documentBillPartnerId,
			final int documentLineSeqNo)
	{
		final BPartnerId contractBillPartnerId = BPartnerId.ofRepoId(callOrderContract.getBill_BPartner_ID());

		if (!contractBillPartnerId.equals(documentBillPartnerId))
		{
			final I_C_BPartner documentBillPartner = bPartnerDAO.getById(documentBillPartnerId);
			final I_C_BPartner contractPartner = bPartnerDAO.getById(contractBillPartnerId);

			throw new AdempiereException(MSG_BPARTNERS_DO_NOT_MATCH,
										 documentBillPartner.getValue(),
										 contractPartner.getValue(),
										 documentLineSeqNo)
					.markAsUserValidationError();
		}
	}

	private boolean isCallOrderContract(@NonNull final I_C_Flatrate_Term contract)
	{
		return TypeConditions.CALL_ORDER.getCode().equals(contract.getType_Conditions());
	}

	private void validateSOTrx(
			@NonNull final I_C_Flatrate_Term contract,
			@NonNull final SOTrx documentSOTrx,
			final int documentLineSeqNo)
	{
		final OrderId initiatingContractOrderId = OrderId.ofRepoIdOrNull(contract.getC_Order_Term_ID());

		Check.assumeNotNull(initiatingContractOrderId, "C_Order_Term_ID cannot be null on CallOrder contracts!");

		final I_C_Order initiatingContractOrder = orderBL.getById(initiatingContractOrderId);

		if (initiatingContractOrder.isSOTrx() == documentSOTrx.toBoolean())
		{
			return;
		}

		if (initiatingContractOrder.isSOTrx())
		{
			throw new AdempiereException(MSG_SALES_CALL_ORDER_CONTRACT_TRX_NOT_MATCH,
										 contract.getDocumentNo(),
										 documentLineSeqNo)
					.markAsUserValidationError();
		}

		throw new AdempiereException(MSG_PURCHASE_CALL_ORDER_CONTRACT_TRX_NOT_MATCH,
									 contract.getDocumentNo(),
									 documentLineSeqNo)
				.markAsUserValidationError();
	}
}
