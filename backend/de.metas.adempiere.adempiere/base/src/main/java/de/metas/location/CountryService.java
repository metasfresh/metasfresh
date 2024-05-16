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

package de.metas.location;

import de.metas.location.CountryDisplaySequenceHelper;
import lombok.NonNull;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;
import org.springframework.stereotype.Service;

@Service
public class CountryService
{
	public void assertCountryValidDisplaySequence(@NonNull final I_C_Country country)
	{
        final String displaySequence = country.getDisplaySequence();
		CountryDisplaySequenceHelper.assertValidDisplaySequence(displaySequence);

		final String localDisplaySequence = country.getDisplaySequenceLocal();
		CountryDisplaySequenceHelper.assertValidDisplaySequence(localDisplaySequence);
	}

	public void assertCountrSequencesValidDisplaySequence(@NonNull final I_C_Country_Sequence sequence)
	{
		CountryDisplaySequenceHelper.assertValidDisplaySequence(sequence.getDisplaySequence());
		CountryDisplaySequenceHelper.assertValidDisplaySequence(sequence.getDisplaySequenceLocal());
	}


}
