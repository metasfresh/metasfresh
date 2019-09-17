package de.metas.contracts.commission.services;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.time.LocalDate;

import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.businesslogic.CommissionConfig;
import de.metas.contracts.commission.businesslogic.algorithms.HierarchyConfig;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Service
public class CommissionConfigFactory
{
	ImmutableList<CommissionConfig> createFor(@NonNull final ContractRequest contractRequest)
	{
		final I_C_BPartner bpartnerRecord = loadOutOfTrx(contractRequest.getBPartnerId(), I_C_BPartner.class);
		if (!bpartnerRecord.isSalesRep())
		{
			return ImmutableList.of();
		}
		return ImmutableList.of(new HierarchyConfig());
	}

	@Builder
	@Value
	public static class ContractRequest
	{
		@NonNull
		BPartnerId bPartnerId;

		@NonNull
		ProductId productId;

		@NonNull
		LocalDate date;
	}
}
