package org.eevolution.mrp.api;

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


import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.IMultitonService;

import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;

/**
 * Multiton service used to delete MRP documents.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08470_speed_up_MRP_cleanup_%28100715712605%29
 */
public interface IMRPDocumentDeleteService extends IMultitonService
{
	/**
	 * Delete all MRP documents which are matching the given query.
	 * 
	 * If those documents have lazy delete support, if will be just flagged as MRP_ToDelete.
	 * 
	 * @param modelsQuery
	 * @return how many documents were deleted/enqueued to be deleted.
	 */
	<ModelType> int delete(final IQueryBuilder<ModelType> modelsQuery);

	/**
	 * Delete MRP documents NOW, even if they have lazy delete support.
	 * 
	 * @param modelsQuery
	 * @return how many documents were deleted
	 */
	<ModelType> int deleteNow(final IQueryBuilder<ModelType> modelsQuery);

	/**
	 * Delete all MRP documents which were enqueued to be deleted.
	 * 
	 * NOTE: this could be a very long running method.
	 * 
	 * @return how many documents were deleted
	 */
	int deletePending();

	/**
	 * Sets context to be used (mainly for MRP notes reporting)
	 * 
	 * @param mrpContext
	 * @return this
	 */
	IMRPDocumentDeleteService setMRPContext(final IMaterialPlanningContext mrpContext);

	/**
	 * Sets MRP notes collector to be used
	 * 
	 * @param mrpNotesCollector
	 * @return this
	 */
	IMRPDocumentDeleteService setMRPNotesCollector(final IMRPNotesCollector mrpNotesCollector);

	/**
	 * Sets if {@link #deletePending()} can be interrupted when Thread is interrupted.
	 * 
	 * @param interruptible
	 * @return this
	 */
	IMRPDocumentDeleteService setInterruptible(boolean interruptible);

	/**
	 * Sets how many records shall be deleted (maximum) by {@link #deletePending()}.
	 * 
	 * @param maxPendingDocumentsToDelete how many records to be deleted (maximum). If the number is negative or zero it means "as many as they are".
	 * @return this
	 */
	IMRPDocumentDeleteService setMaxPendingDocumentsToDelete(int maxPendingDocumentsToDelete);
}
