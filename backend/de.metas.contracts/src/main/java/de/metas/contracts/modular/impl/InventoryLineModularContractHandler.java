/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.modular.impl;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.IModularContractTypeHandler;
import de.metas.contracts.modular.ModelAction;
import de.metas.contracts.modular.ModularContract_Constants;
import de.metas.contracts.modular.log.LogEntryContractType;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InventoryLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class InventoryLineModularContractHandler implements IModularContractTypeHandler<I_M_InventoryLine>
{
	private static final AdMessageKey MSG_REACTIVATE_NOT_ALLOWED = AdMessageKey.of("de.metas.contracts.modular.impl.InventoryLineModularContractHandler.ReactivateNotAllowed");

	@NonNull
	@Override
	public Class<I_M_InventoryLine> getType()
	{
		return I_M_InventoryLine.class;
	}

	@Override
	public boolean applies(@NonNull final I_M_InventoryLine inventoryLine)
	{
		return FlatrateTermId.ofRepoIdOrNull(inventoryLine.getModular_Flatrate_Term_ID()) != null;
	}

	@Override
	public boolean applies(final @NonNull LogEntryContractType logEntryContractType)
	{
		return logEntryContractType.isModularContractType();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(@NonNull final I_M_InventoryLine inventoryLine)
	{
		return Stream.ofNullable(FlatrateTermId.ofRepoIdOrNull(inventoryLine.getModular_Flatrate_Term_ID()));
	}

	public void validateDocAction(
			@NonNull final I_M_InventoryLine ignored,
			@NonNull final ModelAction action)
	{
		switch (action)
		{
			case COMPLETED, REVERSED, VOIDED ->
			{
			}
			case REACTIVATED -> throw new AdempiereException(MSG_REACTIVATE_NOT_ALLOWED);
			default -> throw new AdempiereException(ModularContract_Constants.MSG_ERROR_DOC_ACTION_UNSUPPORTED);
		}
	}
}
