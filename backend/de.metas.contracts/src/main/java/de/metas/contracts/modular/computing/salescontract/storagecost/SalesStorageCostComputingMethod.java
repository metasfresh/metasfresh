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

package de.metas.contracts.modular.computing.salescontract.storagecost;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.modular.ComputingMethodType;
import de.metas.contracts.modular.ModularContractProvider;
import de.metas.contracts.modular.computing.AbstractStorageCostComputingMethod;
import de.metas.contracts.modular.computing.ComputingMethodService;
import de.metas.contracts.modular.settings.ModularContractSettings;
import de.metas.inout.InOutLineId;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class SalesStorageCostComputingMethod extends AbstractStorageCostComputingMethod
{
	@NonNull private final ModularContractProvider contractProvider;

    public SalesStorageCostComputingMethod(
			@NonNull final ModularContractProvider contractProvider,
			@NonNull final ComputingMethodService computingMethodService
	)
	{
		super(computingMethodService, contractProvider, ComputingMethodType.SalesStorageCost);
		this.contractProvider = contractProvider;
	}

    @Override
	public boolean isApplicableForSettings(final @NonNull TableRecordReference recordRef, final @NonNull ModularContractSettings settings)
	{
		return settings.getSoTrx().isSales();
	}

	@Override
	public @NonNull Stream<FlatrateTermId> streamContractIds(final @NonNull TableRecordReference recordRef)
	{
		return recordRef.getIdIfTableName(I_M_InOutLine.Table_Name, InOutLineId::ofRepoId)
				.map(contractProvider::streamModularSalesContractsForShipmentLine)
				.orElseGet(Stream::empty);
	}
}
