package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.Properties;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;

import javax.annotation.Nullable;

/**
 * Builder for {@link I_M_HU_Assignment}s. Use {@link IHUAssignmentBL#createHUAssignmentBuilder()} to get an instance.
 * <p>
 * Note: rather than using this builder directly, it might make sense to invoke {@link IHUAssignmentBL#assignHUs(Object, java.util.Collection)}.
 *
 * @author al
 *
 */
public interface IHUAssignmentBuilder
{
	/**
	 * Set document model which will be referenced
	 */
	IHUAssignmentBuilder setModel(Object model);

	/**
	 * Set {@link I_M_HU_Assignment#setM_HU(I_M_HU)}
	 */
	IHUAssignmentBuilder setTopLevelHU(I_M_HU topLevelHU);

	/**
	 * Set {@link I_M_HU_Assignment#setM_LU_HU(I_M_HU)}
	 */
	IHUAssignmentBuilder setM_LU_HU(I_M_HU luHU);

	/**
	 * Set {@link I_M_HU_Assignment#setM_TU_HU(I_M_HU)}
	 */
	IHUAssignmentBuilder setM_TU_HU(I_M_HU tuHU);

	/**
	 * Sets {@link I_M_HU_Assignment#setVHU(I_M_HU)}.
	 */
	IHUAssignmentBuilder setVHU(I_M_HU vhu);

	IHUAssignmentBuilder setQty(BigDecimal qty);

	IHUAssignmentBuilder setIsTransferPackingMaterials(boolean isTransferPackingMaterials);

	IHUAssignmentBuilder setIsActive(boolean isActive);

	boolean isActive();

	boolean isNewAssignment();

	/**
	 * Initialize this builder with an "empty" assignment.
	 */
	IHUAssignmentBuilder initializeAssignment(Properties ctx, @Nullable String trxName);

	/**
	 * Initialize this builder with values from the given pre-existing {@code assignment}.
	 *
	 * @param assignmentRecord this instance's assignment. The builder will operate on this instance and probably change it.
	 */
	IHUAssignmentBuilder setAssignmentRecordToUpdate(I_M_HU_Assignment assignmentRecord);

	IHUAssignmentBuilder setTemplateForNewRecord(I_M_HU_Assignment assignmentRecord);

	/**
	 * Create/modify and save assignment
	 */
	I_M_HU_Assignment build();

	I_M_HU_Assignment getM_HU_Assignment();

}
