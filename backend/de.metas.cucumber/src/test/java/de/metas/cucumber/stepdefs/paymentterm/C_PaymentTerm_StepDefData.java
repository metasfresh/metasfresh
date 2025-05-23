/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.paymentterm;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.payment.paymentterm.PaymentTermId;
import org.compiere.model.I_C_PaymentTerm;

public class C_PaymentTerm_StepDefData extends StepDefData<I_C_PaymentTerm> implements StepDefDataGetIdAware<PaymentTermId, I_C_PaymentTerm>
{
	public C_PaymentTerm_StepDefData()
	{
		super(I_C_PaymentTerm.class);
	}

	@Override
	public PaymentTermId extractIdFromRecord(final I_C_PaymentTerm record)
	{
		return PaymentTermId.ofRepoId(record.getC_PaymentTerm_ID());
	}
}
