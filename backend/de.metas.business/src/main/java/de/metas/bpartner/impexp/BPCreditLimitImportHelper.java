package de.metas.bpartner.impexp;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.creditLimit.CreditLimitTypeId;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.common.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_CreditLimit;

import java.math.BigDecimal;
import java.util.Optional;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ class BPCreditLimitImportHelper
{
	private final BPartnerCreditLimitRepository creditLimitRepo;

	private final CreditLimitTypeId Management_C_CreditLimit_Type_ID = CreditLimitTypeId.ofRepoId(540001);
	private final CreditLimitTypeId Insurance_C_CreditLimit_Type_ID = CreditLimitTypeId.ofRepoId(540000);

	@Builder
	private BPCreditLimitImportHelper(
			@NonNull final BPartnerCreditLimitRepository creditLimitRepo)
	{
		this.creditLimitRepo = creditLimitRepo;
	}

	public final void importRecord(@NonNull final BPCreditLimitImportRequest request)
	{
		if (request.getInsuranceCreditLimit().signum() > 0)
		{
			createUpdateBPCreditLimit(request.getBpartnerId(), request.getInsuranceCreditLimit(), Insurance_C_CreditLimit_Type_ID);
		}

		if (request.getManagementCreditLimit().signum() > 0)
		{
			createUpdateBPCreditLimit(request.getBpartnerId(), request.getManagementCreditLimit(), Management_C_CreditLimit_Type_ID);
		}
	}

	private void createUpdateBPCreditLimit(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BigDecimal amount,
			@NonNull final CreditLimitTypeId typeId)
	{
		final Optional<I_C_BPartner_CreditLimit> bpCreditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bpartnerId, typeId);
		if (bpCreditLimit.isPresent())
		{
			final I_C_BPartner_CreditLimit creditLimit = bpCreditLimit.get();
			creditLimit.setAmount(amount);
			InterfaceWrapperHelper.save(bpCreditLimit);
		}
		else
		{
			final I_C_BPartner_CreditLimit creditLimit = initBPCreditLimit(amount, typeId);
			creditLimit.setC_BPartner_ID(bpartnerId.getRepoId());
			InterfaceWrapperHelper.save(bpCreditLimit);
		}
	}

	@NonNull
	private I_C_BPartner_CreditLimit initBPCreditLimit(@NonNull final BigDecimal amount, @NonNull final CreditLimitTypeId typeId)
	{
		final I_C_BPartner_CreditLimit bpCreditLimit = InterfaceWrapperHelper.newInstance(I_C_BPartner_CreditLimit.class);
		bpCreditLimit.setAmount(amount);
		bpCreditLimit.setC_CreditLimit_Type_ID(typeId.getRepoId());
		bpCreditLimit.setDateFrom(SystemTime.asDayTimestamp());
		bpCreditLimit.setProcessed(true);
		return bpCreditLimit;
	}

}
