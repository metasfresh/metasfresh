package de.metas.edi.model.validator;

/*
 * #%L
 * de.metas.edi
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

import de.metas.util.Check;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.esb.edi.model.I_C_BPartner_EDI_Setting;

/**
 * Validates {@link I_C_BPartner_EDI_Setting} records to ensure that any partner/location
 * enabled as an EDI recipient has the required GLN configured.
 * <p>
 * Replaces the equivalent validation that was formerly on {@code C_BPartner} (columns
 * {@code IsEdiDesadvRecipient} / {@code IsEdiInvoicRecipient}) before those columns were
 * moved to {@code C_BPartner_EDI_Setting}.
 */
@Interceptor(I_C_BPartner_EDI_Setting.class)
@Component
@RequiredArgsConstructor
public class C_BPartner_EDI_Setting
{
	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_BPartner_EDI_Setting.COLUMNNAME_IsEdiDesadvRecipient,
					I_C_BPartner_EDI_Setting.COLUMNNAME_IsEdiInvoicRecipient,
					I_C_BPartner_EDI_Setting.COLUMNNAME_EdiDesadvRecipientGLN,
					I_C_BPartner_EDI_Setting.COLUMNNAME_EdiInvoicRecipientGLN
			})
	public void validate(final I_C_BPartner_EDI_Setting setting)
	{
		final boolean ediRecipient = setting.isEdiDesadvRecipient() || setting.isEdiInvoicRecipient();
		if (!ediRecipient)
		{
			return;
		}

		final StringBuilder causes = new StringBuilder();

		if (setting.isEdiDesadvRecipient() && Check.isBlank(setting.getEdiDesadvRecipientGLN()))
		{
			causes.append("DESADV recipient GLN (").append(I_C_BPartner_EDI_Setting.COLUMNNAME_EdiDesadvRecipientGLN).append(") is missing; ");
		}
		if (setting.isEdiInvoicRecipient() && Check.isBlank(setting.getEdiInvoicRecipientGLN()))
		{
			causes.append("INVOIC recipient GLN (").append(I_C_BPartner_EDI_Setting.COLUMNNAME_EdiInvoicRecipientGLN).append(") is missing; ");
		}

		if (causes.length() > 0)
		{
			throw new AdempiereException("Invalid EDI partner setting: " + causes.toString().trim())
					.markAsUserValidationError();
		}
	}
}
