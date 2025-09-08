package de.metas.contracts.commission.salesrep;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.commission.Beneficiary;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static de.metas.util.Check.isEmpty;

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

@Service
public class DocumentSalesRepDescriptorService
{
	private static final String MSG_CUSTOMER_NEEDS_SALES_PARTNER = "de.metas.contracts.commission.MissingSalesPartner";

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public void updateFromCustomer(@NonNull final DocumentSalesRepDescriptor documentSalesRepDescriptor)
	{
		if (documentSalesRepDescriptor.getCustomer() == null || documentSalesRepDescriptor.getSoTrx().isPurchase())
		{
			documentSalesRepDescriptor.setSalesPartnerCode(null);
			documentSalesRepDescriptor.setSalesRep(null);
			documentSalesRepDescriptor.setSalesRepRequired(false);
			return;
		}

		final I_C_BPartner customerRecord = bpartnerDAO.getById(documentSalesRepDescriptor.getCustomer().getBPartnerId());
		final boolean salesPartnerRequired = customerRecord.isSalesPartnerRequired();

		final BPartnerId salesBPartnerId = BPartnerId.ofRepoIdOrNull(customerRecord.getC_BPartner_SalesRep_ID());
		final String salesPartnerCode;
		if (salesBPartnerId != null)
		{
			final I_C_BPartner salesPartnerRecord = bpartnerDAO.getById(salesBPartnerId);
			salesPartnerCode = salesPartnerRecord.getSalesPartnerCode();
		}
		else
		{
			salesPartnerCode = null;
		}

		documentSalesRepDescriptor.setSalesPartnerCode(salesPartnerCode);
		documentSalesRepDescriptor.setSalesRep(Beneficiary.ofOrNull(salesBPartnerId));
		documentSalesRepDescriptor.setSalesRepRequired(salesPartnerRequired);
	}

	public void updateFromSalesPartnerCode(@NonNull final DocumentSalesRepDescriptor documentSalesRepDescriptor)
	{
		final String salesPartnerCode = documentSalesRepDescriptor.getSalesPartnerCode();
		if (isEmpty(salesPartnerCode, true))
		{
			documentSalesRepDescriptor.setSalesRep(null);
			return;
		}

		final Optional<BPartnerId> salesPartnerId = bpartnerDAO.getBPartnerIdBySalesPartnerCode(
				salesPartnerCode,
				ImmutableSet.of(documentSalesRepDescriptor.getOrgId(), OrgId.ANY));

		documentSalesRepDescriptor.setSalesRep(salesPartnerId.map(Beneficiary::of).orElse(null));
	}

	public void updateFromSalesRep(@NonNull final DocumentSalesRepDescriptor documentSalesRepDescriptor)
	{
		if (documentSalesRepDescriptor.getSalesRep() == null)
		{
			documentSalesRepDescriptor.setSalesPartnerCode(null);
			return;
		}

		final I_C_BPartner salesBPartnerRecord = bpartnerDAO.getById(documentSalesRepDescriptor.getSalesRep().getBPartnerId());
		documentSalesRepDescriptor.setSalesPartnerCode(salesBPartnerRecord.getSalesPartnerCode());

	}

	public AdempiereException createMissingSalesRepException()
	{
		throw new AdempiereException(MSG_CUSTOMER_NEEDS_SALES_PARTNER).markAsUserValidationError();
	}
}
