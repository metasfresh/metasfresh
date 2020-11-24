package de.metas.contracts.commission.salesrep;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Nullable;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.bpartner.BPartnerId;
import de.metas.lang.SOTrx;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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


@Value class InvoiceTestRecord
{
	I_C_Invoice invoiceRecord;

	/**
	 *
	 * @param soTrx defaults to {@code false} if {@code null}
	 * @param customerBPartnerId defaults to {@code -1} if {@code null}
	 * @param salesRepBPartnerId defaults to {@code -1} if {@code null}
	 * @param salesPartnerRequired defaults to {@code false} if {@code null}
	 * @param salesPartnerCode taken as-is, also if {@code null}
	 */
	@Builder
	private InvoiceTestRecord(
			@Nullable final SOTrx soTrx,
			@Nullable final BPartnerId customerBPartnerId,
			@Nullable final BPartnerId salesRepBPartnerId,
			@Nullable final Boolean salesPartnerRequired,
			@Nullable final String salesPartnerCode)
	{
		invoiceRecord = newInstance(I_C_Invoice.class);
		invoiceRecord.setIsSOTrx(SOTrx.toBoolean(soTrx));
		invoiceRecord.setIsSalesPartnerRequired(coalesce(salesPartnerRequired, false));
		invoiceRecord.setSalesPartnerCode(salesPartnerCode);
		invoiceRecord.setC_BPartner_ID(BPartnerId.toRepoId(customerBPartnerId));
		invoiceRecord.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(salesRepBPartnerId));

		saveRecord(invoiceRecord);
	}

	void assertMatches(@NonNull final DocumentSalesRepDescriptor result)
	{
		assertThat(result).isInstanceOf(InvoiceRecordSalesRepDescriptor.class);

		assertThat(result.isSalesRepRequired()).isEqualTo(invoiceRecord.isSalesPartnerRequired());
		assertThat(result.getSoTrx().toBoolean()).isEqualTo(invoiceRecord.isSOTrx());

		// C_BPartner_ID
		if (invoiceRecord.getC_BPartner_ID() <= 0)
		{
			assertThat(result.getCustomer()).isNull();
		}
		else
		{
			assertThat(result.getCustomer().getBPartnerId().getRepoId()).isEqualTo(invoiceRecord.getC_BPartner_ID());
		}

		// C_BPartner_SalesRep_ID
		if (invoiceRecord.getC_BPartner_SalesRep_ID() <= 0)
		{
			assertThat(result.getSalesRep()).isNull();
		}
		else
		{
			assertThat(result.getSalesRep().getBPartnerId().getRepoId()).isEqualTo(invoiceRecord.getC_BPartner_SalesRep_ID());
		}

		assertThat(result.getSalesPartnerCode()).isEqualTo(invoiceRecord.getSalesPartnerCode());
	}
}