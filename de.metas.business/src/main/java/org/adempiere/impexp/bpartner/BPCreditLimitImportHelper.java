package org.adempiere.impexp.bpartner;

import java.math.BigDecimal;

import org.adempiere.impexp.IImportInterceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

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

	private BPartnerImportProcess process;

	private BPCreditLimitImportHelper()
	{
	}

	public BPCreditLimitImportHelper setProcess(final BPartnerImportProcess process)
	{
		this.process = process;
		return this;
	}

	public final I_C_BPartner_CreditLimit importRecord(final I_I_BPartner importRecord)
	{
		I_C_BPartner_CreditLimit bpCreditLimit = null;
		if (importRecord.getCreditLimit().signum() > 0)
		{
			bpCreditLimit = createBPCreditLimit(importRecord.getCreditLimit(), Insurance_C_CreditLimit_Type_ID);
		}
		else if (importRecord.getCreditLimit2().signum() > 0)
		{
			bpCreditLimit = createBPCreditLimit(importRecord.getCreditLimit2(), Management_C_CreditLimit_Type_ID);
		}

		if (bpCreditLimit != null)
		{
			final I_C_BPartner bpartner = importRecord.getC_BPartner();
			bpCreditLimit.setC_BPartner(bpartner);
			ModelValidationEngine.get().fireImportValidate(process, importRecord, bpCreditLimit, IImportInterceptor.TIMING_AFTER_IMPORT);
			InterfaceWrapperHelper.save(bpCreditLimit);
		}

		return bpCreditLimit;
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
