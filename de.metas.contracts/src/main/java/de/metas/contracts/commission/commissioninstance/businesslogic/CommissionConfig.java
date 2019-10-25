package de.metas.contracts.commission.commissioninstance.businesslogic;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.HierarchyConfig;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTrigger;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;

/*
 * #%L
 * de.metas.contracts
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

/** Defines how a {@link CommissionInstance} is created for a given {@link CommissionTrigger} and {@link Hierarchy}. */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
		@JsonSubTypes.Type(value = HierarchyConfig.class, name = "HierarchyConfig"),
})
public interface CommissionConfig
{
	CommissionType getCommissionType();

	CommissionContract getContractFor(Beneficiary beneficiary);
}
