/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.edi;

import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.esb.edi.model.I_C_BPartner_EDI_Setting;

/**
 * Stores created {@link I_C_BPartner_EDI_Setting} records by identifier alias for cross-step references.
 */
public class C_BPartner_EDI_Setting_StepDefData extends StepDefData<I_C_BPartner_EDI_Setting>
{
	public C_BPartner_EDI_Setting_StepDefData()
	{
		super(I_C_BPartner_EDI_Setting.class);
	}
}
