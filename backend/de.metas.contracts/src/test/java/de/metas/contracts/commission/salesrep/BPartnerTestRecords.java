package de.metas.contracts.commission.salesrep;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Nullable;

import org.compiere.model.I_C_BPartner;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.contracts.commission.Beneficiary;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Value
public class BPartnerTestRecords
{
	private static int salesPortnerCodeSequence = 0;

	I_C_BPartner customerRecord;
	BPartnerId customerBPartnerId;

	I_C_BPartner salesRepRecord;
	BPartnerId salesRepBPartnerId;
	String salesRepCode;
	boolean salesRepConnectedToCustomer;

	@Builder
	private BPartnerTestRecords(
			@Nullable final Boolean customerHasSalesRepRequired,
			@Nullable final Boolean createSalesRep,
			@Nullable final Boolean connectSalesRep)
	{
		customerRecord = BusinessTestHelper.createBPartner("customer");
		customerRecord.setIsSalesPartnerRequired(coalesce(customerHasSalesRepRequired, false));
		customerBPartnerId = BPartnerId.ofRepoId(customerRecord.getC_BPartner_ID());

		if (coalesce(createSalesRep, false))
		{
			salesRepRecord = BusinessTestHelper.createBPartner("salesRep");
			final String salesPartnerCode = Integer.toString(++salesPortnerCodeSequence);
			salesRepRecord.setSalesPartnerCode(salesPartnerCode);
			saveRecord(salesRepRecord);
			salesRepBPartnerId = BPartnerId.ofRepoId(salesRepRecord.getC_BPartner_ID());
			salesRepCode = salesPartnerCode;

			salesRepConnectedToCustomer = coalesce(connectSalesRep, false);
			if (salesRepConnectedToCustomer)
			{
				customerRecord.setC_BPartner_SalesRep_ID(salesRepRecord.getC_BPartner_ID());
			}
		}
		else
		{
			salesRepRecord = null;
			salesRepBPartnerId = null;
			salesRepCode = null;
			salesRepConnectedToCustomer = false;
		}
		saveRecord(customerRecord);
	}

	public void assertMatches(@NonNull final DocumentSalesRepDescriptor result)
	{
		assertThat(result.getCustomer().getBPartnerId()).isEqualTo(getCustomerBPartnerId());

		if (result.getSoTrx().isSales())
		{
			assertThat(result.isSalesRepRequired()).isEqualTo(customerRecord.isSalesPartnerRequired());
		}
		else
		{
			assertThat(result.isSalesRepRequired()).as("As the given descriptor is a purchase-trx, is nevers needs a sales rep").isFalse();
		}

		if (salesRepRecord == null)
		{
			assertThat(result.getSalesRep()).as("As there is no sales rep in master data, the given descriptor may not have a sales rep").isNull();
			assertThat(result.getSalesPartnerCode()).as("As there is no sales rep in master data, the given descriptor may not have a sales rep").isNull();
		}
		else if (result.getSoTrx().isPurchase())
		{
			assertThat(result.getSalesRep()).as("As the given descriptor is a purchase-trx, it may not have a sales rep").isNull();
			assertThat(result.getSalesPartnerCode()).as("As the given descriptor is a purchase-trx, it may not have a sales rep").isNull();
		}
		else if (!salesRepConnectedToCustomer)
		{
			assertThat(result.getSalesRep()).as("As the sales rep is not connected to the customer in master data, the given descriptor may not have a sales rep").isNull();
			assertThat(result.getSalesPartnerCode()).as("As the sales rep is not connected to the customer in master data, the given descriptor may not have a sales rep").isNull();
		}
		else
		{
			assertThat(result.getSalesRep())
					.isNotNull()
					.extracting(Beneficiary::getBPartnerId)
					.isEqualTo(salesRepBPartnerId);
			assertThat(result.getSalesPartnerCode()).isEqualTo(salesRepCode);
		}
	}
}
