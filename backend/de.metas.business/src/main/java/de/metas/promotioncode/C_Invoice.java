/*
 * #%L
 * de.metas.business
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

package de.metas.promotioncode;

import de.metas.adempiere.model.I_C_Order;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Invoice.class)
@Callout(I_C_Invoice.class)
@Component
public class C_Invoice
{
	static final AdMessageKey MSG_C_PromotionCode_DuplicateError = AdMessageKey.of("C_PromotionCode_DuplicateError");

	public C_Invoice()
	{
		final IProgramaticCalloutProvider programmaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = { I_C_Order.COLUMNNAME_C_PromotionCode_ID, I_C_Invoice.COLUMNNAME_C_PromotionCode2_ID })
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Invoice.COLUMNNAME_C_PromotionCode_ID, I_C_Invoice.COLUMNNAME_C_PromotionCode2_ID })
	public void validateNoDuplicatePromotionCode(@NonNull final I_C_Invoice invoice)
	{
		final PromotionCodeId code1 = PromotionCodeId.ofRepoIdOrNull(invoice.getC_PromotionCode_ID());
		final PromotionCodeId code2 = PromotionCodeId.ofRepoIdOrNull(invoice.getC_PromotionCode2_ID());
		if (code1 != null && code1.equals(code2))
		{
			throw new AdempiereException(MSG_C_PromotionCode_DuplicateError)
					.markAsUserValidationError();
		}
	}
}
