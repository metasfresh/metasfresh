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
import de.metas.order.IOrderBL;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Service;

import static org.adempiere.model.InterfaceWrapperHelper.save;

@Service
public class CallOrderContractService
{
	private static final AdMessageKey MSG_MISSING_FLATRATE_TERM = AdMessageKey.of("MISSING_FLATRATE_TERM");
	private static final AdMessageKey MSG_WRONG_TYPE_CONDITIONS = AdMessageKey.of("WRONG_TYPE_CONDITIONS");
	private static final AdMessageKey MSG_BPARTNERS_DO_NOT_MATCH = AdMessageKey.of("BPARTNERS_DO_NOT_MATCH");
	private static final AdMessageKey MSG_PRODUCTS_DO_NOT_MATCH = AdMessageKey.of("PRODUCTS_DO_NOT_MATCH");

	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);

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

		if (flatrateTermId == null)
		{
			throw new AdempiereException(MSG_MISSING_FLATRATE_TERM, callOrderLine.getLine()).markAsUserValidationError();
		}

		final I_C_Flatrate_Term callOrderContract = flatrateBL.getById(flatrateTermId);

		if (!isCallOrderContract(callOrderContract))
		{
			throw new AdempiereException(MSG_WRONG_TYPE_CONDITIONS, X_C_Flatrate_Term.TYPE_CONDITIONS_CallOrder, callOrderLine.getLine())
					.appendParametersToMessage()
					.markAsUserValidationError();
		}

		validateProduct(callOrderContract, callOrderLine);
		validateBillPartner(callOrderContract, callOrder);

		return flatrateTermId;
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

	private void validateProduct(@NonNull final I_C_Flatrate_Term callOrderContract, @NonNull final I_C_OrderLine ol)
	{
		final ProductId orderLineProduct = ProductId.ofRepoId(ol.getM_Product_ID());
		final ProductId contractProductId = ProductId.ofRepoIdOrNull(callOrderContract.getM_Product_ID());

		if (!orderLineProduct.equals(contractProductId))
		{
			throw new AdempiereException(MSG_PRODUCTS_DO_NOT_MATCH,
										 ProductId.toRepoId(contractProductId),
										 orderLineProduct.getRepoId())
					.markAsUserValidationError();
		}
	}

	private void validateBillPartner(@NonNull final I_C_Flatrate_Term contract, @NonNull final I_C_Order order)
	{
		final BPartnerId orderBillBPartnerId = orderBL.getBillToLocationId(order).getBpartnerId();
		final BPartnerId contractBillPartnerId = BPartnerId.ofRepoId(contract.getBill_BPartner_ID());

		if (!contractBillPartnerId.equals(orderBillBPartnerId))
		{
			throw new AdempiereException(MSG_BPARTNERS_DO_NOT_MATCH,
										 "C_Flatrate_Term.BPartner_ID = " + contractBillPartnerId.getRepoId(),
										 "C_Order.Bill_BPartner_ID = " + orderBillBPartnerId.getRepoId())
					.markAsUserValidationError();
		}
	}

	private boolean isCallOrderContract(@NonNull final I_C_Flatrate_Term contract)
	{
		return TypeConditions.CALL_ORDER.getCode().equals(contract.getType_Conditions());
	}
}
