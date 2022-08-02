package de.metas.contracts.commission.commissioninstance.businesslogic;

import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierachyAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.margin.MarginAlgorithm;
import de.metas.contracts.commission.commissioninstance.businesslogic.margin.MarginConfig;
import de.metas.contracts.commission.licensefee.algorithm.LicenseFeeAlgorithm;
import de.metas.contracts.commission.licensefee.algorithm.LicenseFeeConfig;
import de.metas.contracts.commission.mediated.algorithm.MediatedCommissionAlgorithm;
import de.metas.contracts.commission.mediated.algorithm.MediatedCommissionConfig;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.commission
 * %%
 * Copyright (C) 2019 metas GmbH
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

public enum CommissionType
{
	HIERARCHY_COMMISSION(HierachyAlgorithm.class, HierarchyConfig.class),
	MEDIATED_COMMISSION(MediatedCommissionAlgorithm.class, MediatedCommissionConfig.class),
	MARGIN_COMMISSION(MarginAlgorithm.class, MarginConfig.class),
	LICENSE_FEE_COMMISSION(LicenseFeeAlgorithm.class, LicenseFeeConfig.class),
	;

	@Getter
	private final Class<? extends CommissionAlgorithm> algorithmClass;

	@Getter
	private final Class<? extends CommissionConfig> configClass;

	CommissionType(
			@NonNull final Class<? extends CommissionAlgorithm> algorithmClass,
			@NonNull final Class<? extends CommissionConfig> configClass)
	{
		this.algorithmClass = algorithmClass;
		this.configClass = configClass;
	}
}
