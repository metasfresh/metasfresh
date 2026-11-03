/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.customs;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.customs.CustomsInvoiceId;
import org.compiere.model.I_C_Customs_Invoice;

public class C_Customs_Invoice_StepDefData extends StepDefData<I_C_Customs_Invoice>
		implements StepDefDataGetIdAware<CustomsInvoiceId, I_C_Customs_Invoice>
{
	public C_Customs_Invoice_StepDefData()
	{
		super(I_C_Customs_Invoice.class);
	}

	@Override
	public CustomsInvoiceId extractIdFromRecord(final I_C_Customs_Invoice record)
	{
		return CustomsInvoiceId.ofRepoId(record.getC_Customs_Invoice_ID());
	}
}
