/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.invoicecandidate.vatid;

import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.location.CountryId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.TaxNotFoundException;
import de.metas.tax.api.TaxQuery;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * Predicts, before the invoice exists, the tax the invoice line of an invoice candidate will get.
 */
@Component
public class InvoiceCandidateLineTaxResolver
{
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);

	/**
	 * @param shipmentLine the shipment line the invoice line is created for, if any
	 * @return with a shipment line, the tax {@link IInvoiceLineBL#setTaxBasedOnShipment} will set on the invoice line:
	 * the shipment's tax query with the shipment warehouse's country (a candidate invoice has no warehouse) and the candidate's effective tax category;
	 * else (or for a candidate without product, whose line is not re-taxed) the candidate's effective tax, which the invoice line gets as is.
	 */
	@NonNull
	public TaxId getLineTax(@NonNull final I_C_Invoice_Candidate ic, @Nullable final I_M_InOutLine shipmentLine)
	{
		final Tax effectiveTax = invoiceCandBL.getTaxEffective(ic);
		if (shipmentLine == null || ic.getM_Product_ID() <= 0)
		{
			return effectiveTax.getTaxId();
		}

		final I_M_InOut shipment = inOutDAO.getById(InOutId.ofRepoId(shipmentLine.getM_InOut_ID()));
		final WarehouseId warehouseId = WarehouseId.ofRepoId(shipment.getM_Warehouse_ID());
		final CountryId fromCountryId = warehouseBL.getCountryId(warehouseId);

		final TaxQuery taxQuery = invoiceLineBL.createShipmentTaxQuery(shipment, fromCountryId, warehouseId, effectiveTax.getTaxCategoryId());
		final Tax tax = taxDAO.getBy(taxQuery);
		if (tax == null)
		{
			// the invoice line would fail the same way
			throw TaxNotFoundException.builder()
					.taxCategoryId(taxQuery.getTaxCategoryId())
					.isSOTrx(taxQuery.getSoTrx().isSales())
					.orgId(taxQuery.getOrgId())
					.shipDate(taxQuery.getDateOfInterest())
					.shipFromCountryId(fromCountryId)
					.shipToC_Location_ID(taxQuery.getBPartnerLocationId())
					.build();
		}
		return tax.getTaxId();
	}
}
