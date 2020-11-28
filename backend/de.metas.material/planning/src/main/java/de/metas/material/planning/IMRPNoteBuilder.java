package de.metas.material.planning;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.List;

import org.compiere.model.I_AD_Note;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.eevolution.model.I_PP_MRP;

/**
 * MRP Note ({@link I_AD_Note}) builder.
 *
 * Has convenient methods to add information to the MRP note which will be created.
 *
 * NOTE: after you set all infos that you need please call {@link #collect()} to ask the MRP notes collector to collect this note.
 *
 * @author tsa
 *
 */
public interface IMRPNoteBuilder
{
	IMRPNoteBuilder setMRPContext(final IMaterialPlanningContext mrpContext);

	IMaterialPlanningContext getMRPContext();

	/**
	 * Ask to create the MRP note and to collect it.
	 */
	void collect();

	/**
	 * Create and save the MRP's AD_Note.
	 *
	 * NOTE: DON'T call it directly. It's called by API. Use {@link #collect()} instead.
	 *
	 * @return created and saved note
	 */
	I_AD_Note createMRPNote();

	IMRPNoteBuilder setMRPCode(final String mrpCode);

	IMRPNoteBuilder setAD_Org(final I_AD_Org org);

	IMRPNoteBuilder setPlant(final I_S_Resource plant);

	IMRPNoteBuilder setM_Warehouse(final I_M_Warehouse warehouse);

	IMRPNoteBuilder setM_Product(final I_M_Product product);

	IMRPNoteBuilder setComment(final String comment);

	IMRPNoteBuilder setException(final Exception exception);

	IMRPNoteBuilder addParameter(final String parameterName, final Object parameterValue);

	IMRPNoteBuilder setQtyPlan(final BigDecimal qtyPlan);

	IMRPNoteBuilder addMRPRecord(final I_PP_MRP mrp);

	IMRPNoteBuilder addMRPRecords(List<I_PP_MRP> mrps);

	/**
	 *
	 * @return true if this MRP note was found that is a duplicate of a MRP note which was already reported
	 */
	boolean isDuplicate();

	/**
	 * Flag this note as duplicate.
	 *
	 * @param duplicate
	 */
	void setDuplicate();
}
