/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.invoice.sequence;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.sequence.ICountryIdProvider;
import de.metas.location.CountryId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Evaluatee;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class InvoiceCountryIdProvider implements ICountryIdProvider
{
	private static final List<String> INVOICE_DOCBASETYPES = Arrays.asList("API", "APC", "ARI", "ARC");

	@Override
	public ProviderResult computeValueInfo(@NonNull final Evaluatee eval)
	{
		if(!I_C_Invoice.Table_Name.equals(InterfaceWrapperHelper.getModelTableName(eval)))
		{
			return ProviderResult.EMPTY;
		}

		if(Objects.equals(eval.get_ValueAsString(I_C_Invoice.COLUMNNAME_IsSOTrx), "N"))
		{
			return ProviderResult.EMPTY;
		}

		final CountryId countryId = CountryId.ofRepoIdOrNull(eval.get_ValueAsInt(I_C_Invoice.COLUMNNAME_C_Tax_Departure_Country_ID, 0));
		return countryId == null ?  ProviderResult.EMPTY : ProviderResult.of(countryId);
	}

	@Override
	public ProviderResult computeValueInfo(final Object documentModel)
	{
		if(!I_C_Invoice.Table_Name.equals(InterfaceWrapperHelper.getModelTableName(documentModel)))
		{
			return ProviderResult.EMPTY;
		}

		final I_C_Invoice invoice = InterfaceWrapperHelper.create(documentModel, I_C_Invoice.class);
		if(!invoice.isSOTrx())
		{
			return ProviderResult.EMPTY;
		}

		final CountryId countryId = CountryId.ofRepoIdOrNull(invoice.getC_Tax_Departure_Country_ID());
		return countryId == null ?  ProviderResult.EMPTY : ProviderResult.of(countryId);
	}

	@Override
	public boolean isHandled(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		final String docBaseTypeCode = docBaseAndSubType.getDocBaseType().getCode();

		return INVOICE_DOCBASETYPES.contains(docBaseTypeCode);

	}
}
