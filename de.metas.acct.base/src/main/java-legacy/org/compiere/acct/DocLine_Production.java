package org.compiere.acct;

import org.compiere.model.I_M_ProductionLine;
import org.compiere.model.X_M_ProductionLine;

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class DocLine_Production extends DocLine<Doc_Production>
{
	/** Production indicator */
	private boolean m_productionBOM = false;

	public DocLine_Production(X_M_ProductionLine linePO, Doc_Production doc)
	{
		super(linePO, doc);
	}

	/**
	 * Set Production BOM flag
	 *
	 * @param productionBOM flag
	 */
	public final void setProductionBOM(boolean productionBOM)
	{
		m_productionBOM = productionBOM;
	}	// setProductionBOM

	/**
	 * Is this the BOM to be produced
	 *
	 * @return true if BOM
	 */
	public final boolean isProductionBOM()
	{
		return m_productionBOM;
	}	// isProductionBOM

	/**
	 * Get Production Plan
	 * 
	 * @return M_ProductionPlan_ID
	 */
	public final int getM_ProductionPlan_ID()
	{
		final I_M_ProductionLine productionLine = getModel(I_M_ProductionLine.class);
		return productionLine.getM_ProductionPlan_ID();
	}
}
