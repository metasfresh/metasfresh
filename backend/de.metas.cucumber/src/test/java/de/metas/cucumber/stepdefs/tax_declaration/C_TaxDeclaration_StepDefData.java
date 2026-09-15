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

package de.metas.cucumber.stepdefs.tax_declaration;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.cucumber.stepdefs.StepDefDataGetIdAware;
import org.compiere.model.I_C_TaxDeclaration;

/**
 * Shared step-def state for {@link I_C_TaxDeclaration} records.
 * Implements {@link StepDefDataGetIdAware} so identifiers can be resolved to {@link TaxDeclarationId}.
 */
public class C_TaxDeclaration_StepDefData extends StepDefData<I_C_TaxDeclaration>
		implements StepDefDataGetIdAware<TaxDeclarationId, I_C_TaxDeclaration>
{
	public C_TaxDeclaration_StepDefData()
	{
		super(I_C_TaxDeclaration.class);
	}

	@Override
	public TaxDeclarationId extractIdFromRecord(final I_C_TaxDeclaration record)
	{
		return TaxDeclarationId.ofRepoId(record.getC_TaxDeclaration_ID());
	}
}
