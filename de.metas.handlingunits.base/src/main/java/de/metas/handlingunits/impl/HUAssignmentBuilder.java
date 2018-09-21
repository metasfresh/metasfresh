package de.metas.handlingunits.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

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

import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.handlingunits.IHUAssignmentBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.util.Check;
import lombok.NonNull;

/**
 * Default HU Assignment builder
 *
 * @author al
 */
/* package */class HUAssignmentBuilder implements IHUAssignmentBuilder
{
	private Object model = null;
	private I_M_HU topLevelHU = null;

	private I_M_HU luHU = null;
	private I_M_HU tuHU = null;
	private I_M_HU vhu = null;

	private BigDecimal qty = null;
	private boolean isTransferPackingMaterials = false;

	private boolean isActive = true;

	private I_M_HU_Assignment assignment = null;

	@Override
	public IHUAssignmentBuilder setModel(@NonNull final Object model)
	{
		this.model = model;
		return this;
	}

	@Override
	public IHUAssignmentBuilder setTopLevelHU(final I_M_HU topLevelHU)
	{
		this.topLevelHU = topLevelHU;
		return this;
	}

	@Override
	public IHUAssignmentBuilder setM_LU_HU(final I_M_HU luHU)
	{
		this.luHU = luHU;
		return this;
	}

	@Override
	public IHUAssignmentBuilder setM_TU_HU(final I_M_HU tuHU)
	{
		this.tuHU = tuHU;
		return this;
	}

	@Override
	public IHUAssignmentBuilder setVHU(final I_M_HU vhu)
	{
		this.vhu = vhu;
		return this;
	}

	@Override
	public IHUAssignmentBuilder setQty(final BigDecimal qty)
	{
		this.qty = qty;
		return this;
	}

	@Override
	public IHUAssignmentBuilder setIsTransferPackingMaterials(final boolean isTransferPackingMaterials)
	{
		this.isTransferPackingMaterials = isTransferPackingMaterials;
		return this;
	}

	@Override
	public IHUAssignmentBuilder setIsActive(final boolean isActive)
	{
		this.isActive = isActive;
		return this;
	}

	@Override
	public boolean isActive()
	{
		return isActive;
	}

	@Override
	public I_M_HU_Assignment getM_HU_Assignment()
	{
		return assignment;
	}

	@Override
	public boolean isNewAssignment()
	{
		return InterfaceWrapperHelper.isNew(assignment);
	}

	@Override
	public IHUAssignmentBuilder initializeAssignment(final Properties ctx, final String trxName)
	{
		final I_M_HU_Assignment assignment = InterfaceWrapperHelper.create(ctx, I_M_HU_Assignment.class, trxName);
		setAssignmentRecordToUpdate(assignment);
		return this;
	}

	@Override
	public IHUAssignmentBuilder setTemplateForNewRecord(final I_M_HU_Assignment assignmentRecord)
	{
		this.assignment = newInstance(I_M_HU_Assignment.class);
		return updateFromRecord(assignmentRecord);

	}

	@Override
	public IHUAssignmentBuilder setAssignmentRecordToUpdate(@NonNull final I_M_HU_Assignment assignmentRecord)
	{
		this.assignment = assignmentRecord;
		return updateFromRecord(assignmentRecord);
	}

	private IHUAssignmentBuilder updateFromRecord(final I_M_HU_Assignment assignmentRecord)
	{
		setTopLevelHU(assignmentRecord.getM_HU());
		setM_LU_HU(assignmentRecord.getM_LU_HU());
		setM_TU_HU(assignmentRecord.getM_TU_HU());
		setVHU(assignmentRecord.getVHU());
		setQty(assignmentRecord.getQty());
		setIsTransferPackingMaterials(assignmentRecord.isTransferPackingMaterials());
		setIsActive(assignmentRecord.isActive());

		return this;
	}

	@Override
	public I_M_HU_Assignment build()
	{
		Check.assumeNotNull(model, "model not null");
		Check.assumeNotNull(topLevelHU, "topLevelHU not null");
		Check.assumeNotNull(assignment, "assignment not null");

		TableRecordCacheLocal.setReferencedValue(assignment, model);

		assignment.setM_HU(topLevelHU);

		assignment.setM_LU_HU(luHU);
		assignment.setM_TU_HU(tuHU);
		assignment.setVHU(vhu);

		assignment.setQty(qty);
		assignment.setIsTransferPackingMaterials(isTransferPackingMaterials);

		assignment.setIsActive(isActive);

		InterfaceWrapperHelper.save(assignment);
		return assignment;
	}
}
