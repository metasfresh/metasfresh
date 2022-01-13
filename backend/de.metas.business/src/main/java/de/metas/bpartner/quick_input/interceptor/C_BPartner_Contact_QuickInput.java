/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner.quick_input.interceptor;

import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import de.metas.bpartner.quick_input.service.BPartnerQuickInputService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_BPartner_Contact_QuickInput;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_BPartner_Contact_QuickInput.class)
public class C_BPartner_Contact_QuickInput
{
	private final BPartnerQuickInputService bpartnerQuickInputService;

	public C_BPartner_Contact_QuickInput(
			@NonNull final BPartnerQuickInputService bpartnerQuickInputService)
	{
		this.bpartnerQuickInputService = bpartnerQuickInputService;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(@NonNull final I_C_BPartner_Contact_QuickInput record)
	{
		bpartnerQuickInputService.updateNameAndGreeting(BPartnerQuickInputId.ofRepoId(record.getC_BPartner_QuickInput_ID()));
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(@NonNull final I_C_BPartner_Contact_QuickInput record)
	{
		bpartnerQuickInputService.updateNameAndGreeting(BPartnerQuickInputId.ofRepoId(record.getC_BPartner_QuickInput_ID()));
	}

}
