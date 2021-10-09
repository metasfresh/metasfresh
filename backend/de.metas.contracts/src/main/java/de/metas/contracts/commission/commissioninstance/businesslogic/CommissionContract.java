package de.metas.contracts.commission.commissioninstance.businesslogic;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.commissioninstance.businesslogic.algorithms.hierarchy.HierarchyContract;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

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

/** Contains settings that can vary between different {@link Beneficiary} even within the same commission instance. */
@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
		@JsonSubTypes.Type(value = HierarchyContract.class, name = "HierarchyContract"),
})
public interface CommissionContract
{
	/** @return never {@code null} */
	FlatrateTermId getId();

	boolean isSimulation();
}
