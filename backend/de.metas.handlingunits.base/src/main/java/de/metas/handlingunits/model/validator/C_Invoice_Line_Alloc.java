package de.metas.handlingunits.model.validator;

import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.model.I_C_InvoiceLine;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;

@Validator(I_C_Invoice_Line_Alloc.class)
public class C_Invoice_Line_Alloc
{
	/**
	 * Updates invoice line's QtyTU by summing up the invoice candidate's QtyTUs.
	 *
	 * @param invoiceLineAlloc
	 * @task http://dewiki908/mediawiki/index.php/08469_Produzentenabrechnung:_Quantity_of_LU_wrong_%28100093568946%29
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_AFTER_DELETE
	})
	public void updateQtyTU(final I_C_Invoice_Line_Alloc invoiceLineAlloc)
	{

		final IProductBL productBL = Services.get(IProductBL.class);
		//
		// Get Invoice Line
		if (invoiceLineAlloc.getC_InvoiceLine_ID() <= 0)
		{
			return; // shouldn't happen, but it's not really our business
		}
		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.create(invoiceLineAlloc.getC_InvoiceLine(), I_C_InvoiceLine.class);

		final boolean isFreightCost = productBL
				.getProductType(ProductId.ofRepoId(invoiceLine.getM_Product_ID()))
				.isFreightCost();

		if (isFreightCost)
		{
			// the freight cost doesn't need any Qty TU
			invoiceLine.setQtyEnteredTU(BigDecimal.ZERO);
			save(invoiceLine);
			return;
		}

		// 08469
		final BigDecimal qtyTUs;
		final I_M_InOutLine iol = InterfaceWrapperHelper.create(invoiceLine.getM_InOutLine(), I_M_InOutLine.class);
		if (iol != null)
		{
			qtyTUs = iol.getQtyEnteredTU();
		}
		//
		// Update Invoice Line
		else
		{
			qtyTUs = calculateQtyTUsFromInvoiceCandidates(invoiceLine);
		}
		invoiceLine.setQtyEnteredTU(qtyTUs);
		save(invoiceLine);
	}

	private final BigDecimal calculateQtyTUsFromInvoiceCandidates(final I_C_InvoiceLine invoiceLine)
	{
		BigDecimal qtyEnteredTU = BigDecimal.ZERO;

		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
		final List<I_C_Invoice_Candidate> icForIl = invoiceCandDAO.retrieveIcForIl(invoiceLine);
		for (final I_C_Invoice_Candidate ic : icForIl)
		{
			final de.metas.handlingunits.model.I_C_Invoice_Candidate icExt = InterfaceWrapperHelper.create(ic, de.metas.handlingunits.model.I_C_Invoice_Candidate.class);

			final BigDecimal icQtyEnteredTU = icExt.getQtyEnteredTU();
			if (icQtyEnteredTU != null) // safety
			{
				qtyEnteredTU = qtyEnteredTU.add(icQtyEnteredTU);
			}
		}

		return qtyEnteredTU;
	}

}
