package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.DocTimingType;

import de.metas.materialtracking.model.I_M_InOutLine;

/*
 * #%L
 * de.metas.materialtracking
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

public class M_InOutLine_MaterialTracking_Handler extends AbstractQualityInspectionHandler
{

	private static final M_InOutLine_MaterialTracking_HandlerDAO dao = new M_InOutLine_MaterialTracking_HandlerDAO();
	
	@Override
	public String getSourceTable()
	{
		return I_M_InOutLine.Table_Name;
	}

	@Override
	public DocTimingType getAutomaticallyCreateMissingCandidatesDocTiming()
	{
		return DocTimingType.AFTER_COMPLETE;
	}

	@Override
	public Iterator<I_M_InOutLine> retrieveAllModelsWithMissingCandidates(
			final Properties ctx,
			final int limit,
			final String trxName)
	{
		return dao.retrieveAllModelsWithMissingCandidates(ctx, limit, trxName);
	}

	@Override
	boolean isInvoiceable(final Object model)
	{
		return dao.isInvoiceable(model);
	}

}
