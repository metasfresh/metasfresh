/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.location.adapter;

import de.metas.contracts.model.I_C_Flatrate_Term;
import lombok.NonNull;

public class ContractDocumentLocationAdapterFactory
{
	public static ContractBillLocationAdapter billLocationAdapter(@NonNull final I_C_Flatrate_Term term)
	{
		return new ContractBillLocationAdapter(term);
	}

	public static ContractDropshipLocationAdapter dropShipLocationAdapter(@NonNull final I_C_Flatrate_Term term)
	{
		return new ContractDropshipLocationAdapter(term);
	}
}
