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

package de.metas.contracts.modular.settings;

import de.metas.contracts.modular.ComputingMethodType;
import de.metas.product.ProductId;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
@Builder
public class ModuleConfig
{
	@NonNull ModuleConfigAndSettingsId id;

	@NonNull SeqNo seqNo;

	@NonNull String name;

	@NonNull String invoicingGroup;

	@NonNull ProductId productId;

	@NonNull ModularContractType modularContractType;

	public boolean isMatching(@NonNull final ComputingMethodType computingMethodType)
	{
		return modularContractType.isMatching(computingMethodType);
	}

	public @NonNull ModularContractTypeId getModularContractTypeId() {return modularContractType.getId();}

	@NonNull
	public ComputingMethodType getComputingMethodType()
	{
		return modularContractType.getComputingMethodType();
	}
}
