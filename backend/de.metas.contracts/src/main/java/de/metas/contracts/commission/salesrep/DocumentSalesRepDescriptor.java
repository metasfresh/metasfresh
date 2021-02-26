package de.metas.contracts.commission.salesrep;

import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Customer;
import de.metas.lang.SOTrx;
import de.metas.organization.OrgId;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public abstract class DocumentSalesRepDescriptor
{
	@Getter
	@Setter
	private Beneficiary salesRep;

	@Getter
	@Setter
	private String salesPartnerCode;

	@Getter
	@Setter
	private boolean salesRepRequired;

	@Getter
	private SOTrx soTrx;

	@Getter
	private Customer customer;

	@Getter
	private OrgId orgId;

	@Getter
	private boolean inSyncWithRecord;

	public DocumentSalesRepDescriptor(
			@NonNull final OrgId orgId,
			@NonNull final SOTrx soTrx,
			@Nullable final Customer customer,
			@Nullable final Beneficiary salesRep,
			@Nullable final String salesPartnerCode,
			final boolean salesRepRequired)
	{
		this.orgId = orgId;
		this.soTrx = soTrx;
		this.customer = customer;
		this.salesRep = salesRep;
		this.salesPartnerCode = salesPartnerCode;
		this.salesRepRequired = salesRepRequired;
	}

	public boolean validatesOK()
	{
		if (soTrx.isPurchase())
		{
			return true; // we don't have any business with purchase documents
		}

		if (!isSalesRepRequired())
		{
			return true;
		}

		// if a sales rep is required, then in order to be valid, we need to have a sales rep
		return salesRep != null;
	}

	public abstract void syncToRecord();
}
