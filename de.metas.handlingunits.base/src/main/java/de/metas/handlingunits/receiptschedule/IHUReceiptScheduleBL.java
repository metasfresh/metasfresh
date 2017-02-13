/**
 *
 */
package de.metas.handlingunits.receiptschedule;

import java.awt.image.BufferedImage;

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


import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.ISingletonService;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.InOutGenerateResult;

/**
 * @author cg
 */
public interface IHUReceiptScheduleBL extends ISingletonService
{
	/**
	 * Create Receipts Producer.
	 *
	 * @param ctx
	 * @param resultInitial
	 * @param selectedHUIds <code>null</code> or a set of M_HU_IDs that shall be considered. If called with <code>null</code>, then all (planned?) HUs from the
	 *            {@link de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc} will be assigned to the inOut.
	 * @param createReceiptWithDatePromised if <code>false</code> (the default), then a new InOut is created with the current date from {@link org.compiere.util.Env#getDate(Properties)}. Otherwise it is created with
	 *            the DatePromised value of the receipt schedule's C_Order. To be used e.g. when doing migration work.
	 * @return producer
	 */
	IInOutProducer createInOutProducerFromReceiptScheduleHU(Properties ctx,
			InOutGenerateResult resultInitial,
			Set<Integer> selectedHUIds,
			boolean createReceiptWithDatePromised);

	/**
	 * Mark LU and TU handling units of the allocations as destroyed, and unassign them, if the allocation does not already reference a receipt, if they are active and if they have the status
	 * "Planning".
	 *
	 * Also, the receipt schedule allocations of the destroyed HUs will be deactivated and saved.
	 *
	 * @param allocations
	 * @param trxName
	 */
	void destroyHandlingUnits(List<I_M_ReceiptSchedule_Alloc> allocations, String trxName);

	IProductStorage createProductStorage(de.metas.inoutcandidate.model.I_M_ReceiptSchedule rs);

	IAllocationSource createAllocationSource(I_M_ReceiptSchedule receiptSchedule);

	IDocumentLUTUConfigurationManager createLUTUConfigurationManager(I_M_ReceiptSchedule receiptSchedule);

	IDocumentLUTUConfigurationManager createLUTUConfigurationManager(List<I_M_ReceiptSchedule> receiptSchedules);

	/**
	 * Destroy the handling units from allocations in a huContext
	 *
	 * @param huContext
	 * @param allocs
	 */
	void destroyHandlingUnits(IHUContext huContext, List<I_M_ReceiptSchedule_Alloc> allocs);

	/**
	 * Process receipt schedules and receive given <code>selectedHUs</code> from them.
	 *
	 * @param ctx
	 * @param receiptSchedules
	 * @param selectedHUs HUs to receive
	 * @param storeReceipts if true, receipts shall be stored in the result (particularly useful for testing)
	 * @return receipt generation result
	 */
	InOutGenerateResult processReceiptSchedules(Properties ctx, List<I_M_ReceiptSchedule> receiptSchedules, Set<I_M_HU> selectedHUs, boolean storeReceipts);

	/**
	 * Set request's initial attribute values defaults to be used when new HUs are created.
	 *
	 * Mainly this method is setting the {@link Constants#ATTR_CostPrice}.
	 *
	 * @param request request to be updated
	 * @param receiptSchedules receipt schedule from where to extract the inital attributes
	 * @return updated request (could be the same, but it's not guaranteed)
	 */
	IAllocationRequest setInitialAttributeValueDefaults(IAllocationRequest request, Collection<? extends de.metas.inoutcandidate.model.I_M_ReceiptSchedule> receiptSchedules);

	/**
	 * @see #setInitialAttributeValueDefaults(IAllocationRequest, List)
	 */
	IAllocationRequest setInitialAttributeValueDefaults(IAllocationRequest request, de.metas.inoutcandidate.model.I_M_ReceiptSchedule receiptSchedule);

	void attachPhoto(I_M_ReceiptSchedule receiptSchedule, String filename, BufferedImage image);
}
