package de.metas.contracts.commission.invoicecandidate;

import java.math.BigDecimal;
import java.util.Iterator;
import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.contracts.commission.model.I_C_Commission_Instance;
import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class CommissionShareHandler extends AbstractInvoiceCandidateHandler
{
	public static final ProductId COMMISSION_PRODUCT_ID = ProductId.ofRepoId(540420);

	@Override
	public Iterator<? extends Object> retrieveAllModelsWithMissingCandidates(int limit)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(InvoiceCandidateGenerateRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invalidateCandidatesFor(@NonNull final Object model)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		invoiceCandDAO.invalidateCandsThatReference(TableRecordReference.of(model));
	}

	/** @return {@code C_Commission_Instance} */
	@Override
	public String getSourceTable()
	{
		return I_C_Commission_Instance.Table_Name;
	}

	/** @return {@code false} */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * <ul>
	 * <li>QtyEntered := sum of all 3 C_Commission_Share.PointsSum_* columns
	 * <li>C_UOM_ID := {@link #COMMISSION_PRODUCT_ID}'s stock UOM
	 * <li>QtyOrdered := QtyEntered
	 * <li>DateOrdered := C_Commission_Share.Created
	 * <li>C_Order_ID: -1
	 * </ul>
	 */
	@Override
	public void setOrderedData(I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = getCommissionShareRecord(ic);
		final UomId uomId = Services.get(IProductBL.class).getStockUOMId(COMMISSION_PRODUCT_ID);

		final BigDecimal allPoints = commissionShareRecord.getPointsSum_Forecasted()
				.add(commissionShareRecord.getPointsSum_Invoiceable())
				.add(commissionShareRecord.getPointsSum_Invoiced());

		ic.setQtyEntered(allPoints);
		ic.setC_UOM_ID(uomId.getRepoId());
		ic.setQtyOrdered(allPoints);
		ic.setDateOrdered(commissionShareRecord.getCreated());
		ic.setC_Order_ID(-1);
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := DateOrdered
	 * <li>M_InOut_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = getCommissionShareRecord(ic);

		ic.setQtyDelivered(commissionShareRecord.getPointsSum_Invoiced());
		ic.setDeliveryDate(ic.getDateOrdered());
		ic.setM_InOut_ID(-1);
	}

	private I_C_Commission_Share getCommissionShareRecord(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = TableRecordReference
				.ofReferenced(ic)
				.getModel(I_C_Commission_Share.class);
		return commissionShareRecord;
	}

	@Override
	public void setBPartnerData(@NonNull final I_C_Invoice_Candidate ic)
	{
		final I_C_Commission_Share commissionShareRecord = getCommissionShareRecord(ic);
		ic.setBill_BPartner_ID(commissionShareRecord.getC_BPartner_SalesRep_ID());
	}

}
