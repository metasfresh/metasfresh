package org.eevolution.mrp.spi;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Set;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.util.lang.IMutable;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPExecutor;

import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.pporder.LiberoException;


public interface IMRPSupplyProducer
{
	Set<String> getSourceTableNames();

	Class<?> getDocumentClass();

	/**
	 * Checks if this producer can create supply in given context.
	 * 
	 * @param mrpContext
	 * @param notAppliesReason if it does not apply, in this variable you can get the reason (human readable)
	 * @return true if yes
	 */
	boolean applies(final IMaterialPlanningContext mrpContext, final IMutable<String> notAppliesReason);

	void onRecordChange(final Object model, final ModelChangeType changeType);

	void onDocumentChange(Object model, DocTimingType timing);

	/**
	 * 
	 * @param tableName
	 * @return true if this producer supports recreating MRP records for given table
	 */
	boolean isRecreatedMRPRecordsSupported(final String tableName);

	/**
	 * Delete and create {@link I_PP_MRP} records again.
	 * 
	 * @param model
	 * @throws LiberoException if model's table name is not supported (see {@link #isRecreatedMRPRecordsSupported(String)})
	 */
	void recreateMRPRecords(Object model);

	void createSupply(IMRPCreateSupplyRequest request);

	/**
	 * Delete generated documents
	 * 
	 * @param mrpContext
	 * @param executor
	 */
	void cleanup(final IMaterialPlanningContext mrpContext, final IMRPExecutor executor);

	void onQtyOnHandReservation(IMaterialPlanningContext mrpContext,
			IMRPExecutor mrpExecutor,
			IMRPDemandToSupplyAllocation mrpDemandToSupplyAllocation);
}
