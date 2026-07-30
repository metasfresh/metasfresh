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
package de.metas.cucumber.stepdefs.tax;

import de.metas.acct.vatcode.VATCode;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import de.metas.tax.api.VatCodeId;

public class C_VAT_Code_StepDefData extends StepDefData<VATCode> implements StepDefDataGetIdAware<VatCodeId, VATCode>
{
	public C_VAT_Code_StepDefData()
	{
		super(VATCode.class);
	}

	@Override
	public VatCodeId extractIdFromRecord(final VATCode record)
	{
		return record.getVatCodeId();
	}
}
