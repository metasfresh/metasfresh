/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.contracts.flatrate.process;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.process.IProcessPreconditionsContext;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.util.lang.impl.TableRecordReference;

@UtilityClass
public class ProcessUtil
{
	public boolean isFlatFeeContract(@NonNull final IProcessPreconditionsContext context)
	{
		final TableRecordReference reference = TableRecordReference.of(I_C_Flatrate_Term.Table_Name, context.getSingleSelectedRecordId());
		
		final I_C_Flatrate_Term record = reference.getModel(I_C_Flatrate_Term.class);

		return record.getType_Conditions().equals(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee);
	}
}
