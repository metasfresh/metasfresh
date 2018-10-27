package de.metas.bpartner.impexp;

import java.math.BigDecimal;
import java.util.Optional;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_I_BPartner;

import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

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
	private final int Management_C_CreditLimit_Type_ID = 540001;
	private final int Insurance_C_CreditLimit_Type_ID = 540000;

	public static BPCreditLimitImportHelper newInstance()
	{
		return new BPCreditLimitImportHelper();
	}

	private BPCreditLimitImportHelper()
	{
	}

	public final void importRecord(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner = importRecord.getC_BPartner();
		if (importRecord.getCreditLimit().signum() > 0)
		{
			createUpdateBPCreditLimit(bpartner.getC_BPartner_ID(), importRecord.getCreditLimit(), Insurance_C_CreditLimit_Type_ID);
		}

		if (importRecord.getCreditLimit2().signum() > 0)
		{
			createUpdateBPCreditLimit(bpartner.getC_BPartner_ID(), importRecord.getCreditLimit2(), Management_C_CreditLimit_Type_ID);
		}
	}

	private final void createUpdateBPCreditLimit(final int bPartnerId, @NonNull final BigDecimal amount, final int typeId)
	{
		final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
		final Optional<I_C_BPartner_CreditLimit> bpCreditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(bPartnerId, typeId);
		if (bpCreditLimit.isPresent())
		{
			final I_C_BPartner_CreditLimit creditLimit = bpCreditLimit.get();
			creditLimit.setAmount(amount);
			InterfaceWrapperHelper.save(bpCreditLimit);
		}
		else
		{
			final I_C_BPartner_CreditLimit creditLimit = createBPCreditLimit(amount, typeId);
			creditLimit.setC_BPartner_ID(bPartnerId);
			InterfaceWrapperHelper.save(bpCreditLimit);
		}
	}

	private final I_C_BPartner_CreditLimit createBPCreditLimit(@NonNull final BigDecimal amount, final int typeId)
	{
		final I_C_BPartner_CreditLimit bpCreditLimit = InterfaceWrapperHelper.newInstance(I_C_BPartner_CreditLimit.class);
		bpCreditLimit.setAmount(amount);
		bpCreditLimit.setC_CreditLimit_Type_ID(typeId);
		bpCreditLimit.setDateFrom(SystemTime.asDayTimestamp());
		bpCreditLimit.setProcessed(true);
		return bpCreditLimit;
	}

}
