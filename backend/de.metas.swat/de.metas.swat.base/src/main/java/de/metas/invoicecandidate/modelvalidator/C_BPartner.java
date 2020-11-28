package de.metas.invoicecandidate.modelvalidator;

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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;

import de.metas.bpartner.BPartnerId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_BPartner;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.NonNull;

@Validator(I_C_BPartner.class)
public class C_BPartner
{

	/**
	 * If <code>C_InvoiceSchedule_ID</code> changes, then this MV calls {@link IInvoiceCandDAO#invalidateCandsForBPartnerInvoiceRule(org.compiere.model.I_C_BPartner)} to invalidate those ICs that
	 * might be concerned by the change.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,//
			ifColumnsChanged = I_C_BPartner.COLUMNNAME_C_InvoiceSchedule_ID)
	public void onInvoiceRuleChange(@NonNull final I_C_BPartner bPartnerRecord) throws Exception
	{
		try (final MDCCloseable bPartnerRecordMDC = TableRecordMDC.putTableRecordReference(bPartnerRecord))
		{
			final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

			final BPartnerId bpartnerid = BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID());
			invoiceCandDAO.invalidateCandsForBPartnerInvoiceRule(bpartnerid);
		}
	}

	/**
	 * Invalidate all invoice candidates of given partner, in case the header/line aggregation definitions were changed.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,//
			ifColumnsChanged = { I_C_BPartner.COLUMNNAME_PO_Invoice_Aggregation_ID, I_C_BPartner.COLUMNNAME_PO_InvoiceLine_Aggregation_ID, I_C_BPartner.COLUMNNAME_SO_Invoice_Aggregation_ID, I_C_BPartner.COLUMNNAME_SO_InvoiceLine_Aggregation_ID })
	public void onInvoiceAggregationChanged(@NonNull final I_C_BPartner bPartnerRecord)
	{
		try (final MDCCloseable bPartnerRecordMDC = TableRecordMDC.putTableRecordReference(bPartnerRecord))
		{
			final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
			invoiceCandDAO.invalidateCandsForBPartner(bPartnerRecord);
		}
	}
}
