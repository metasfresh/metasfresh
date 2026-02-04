package de.metas.edi.model.validator;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.model.I_C_BPartner;
import de.metas.util.Services;

@Validator(I_C_BPartner.class)
@Component
public class C_BPartner
{
	private final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);

	@ModelChange(//
			timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = { I_C_BPartner.COLUMNNAME_IsEdiDesadvRecipient, I_C_BPartner.COLUMNNAME_IsEdiInvoicRecipient })
	public void validate(final I_C_BPartner bpartner)
	{
		final boolean ediRecipient = bpartner.isEdiDesadvRecipient() || bpartner.isEdiInvoicRecipient();
		if (!ediRecipient)
		{
			return;
		}

		final List<Exception> feedback = ediDocumentBL.isValidPartner(bpartner);
		if (feedback == null || feedback.isEmpty())
		{
			return;
		}

		final StringBuilder causes = new StringBuilder();
		for (final Exception feedbackElement : feedback)
		{
			causes.append(feedbackElement.getLocalizedMessage()).append("; ");
		}

		throw new AdempiereException("Invalid EDI partner " + causes.toString().trim());
	}
}
