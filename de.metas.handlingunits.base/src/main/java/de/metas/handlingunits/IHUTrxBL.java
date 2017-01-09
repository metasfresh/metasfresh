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
import java.util.Date;
import java.util.List;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;

import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;

/**
 *
 * Service to create and "destroy" handling units (instances). HUs can be created be transferring material from a {@link org.compiere.model.I_M_Transaction} (such as a material receipt, method
 * {@link #transferIncomingToHUs(I_M_Transaction, I_M_HU_PI)}) or by transferring material out of an existing HU (method {@link #transferMaterialToNewHUs(I_M_HU, BigDecimal, I_M_Product, I_M_HU_PI)}
 * ).<br>
 * <br>
 * HUs can be "destroyed" by transferring material to an <code>I_M_Transaction</code> (such as a shipment, method {@link #transferHUsToOutgoing(I_M_Transaction, List)} or by transferring material to
 * existing HUs. Note that the meaning of "destroyed is currently still a bit fuzzy. It might be a flag to in {@link I_M_HU} to that may be set to "true" when the HU doesn't contain an material
 * anymore, to indicate that this instance won't be used anymore.<br>
 * <br>
 * All methods in this class create one {@link de.metas.handlingunits.model.I_M_HU_Trx_Hdr} with at least two {@link de.metas.handlingunits.model.I_M_HU_Trx_Line}s. Each trx-line references either a
 * {@link de.metas.handlingunits.model.I_M_HU_Item} or an {@link org.compiere.model.I_M_TransactionAllocation}.
 *
 */
public interface IHUTrxBL extends ISingletonService
{
	/**
	 * Transfer <code>request</code> from <code>source</code> to <code>destination</code>
	 *
	 * @param huContext
	 * @param source
	 * @param destination
	 * @param request
	 */
	void transfer(IHUContext huContext, IAllocationSource source, IAllocationDestination destination, IAllocationRequest request);

	/**
	 * Extracts (or maybe "detaches", makes into "stand-alone" HUs) the given qty of HU items that match the given definition form the given source HU(s). One or more source HUs are modified in the
	 * process. The method also creates a {@link I_M_HU_Trx_Hdr} to document from source items (of <code>sourceHU</code>) were extracted into "standalone" HUs. That trx-hdr has one line for every
	 * {@link de.metas.handlingunits.model.I_M_HU_Item} that was extracted from the source HU(s) and one line for every {@link I_M_HU} that is now a "stand-alone" HU.
	 *
	 * @param sourceHUs
	 * @param huQty of HUs to be extracted
	 * @param destinationHuPI the definition of the HUs that shall be extracted
	 * @return the HUs that are now "standalone"
	 */
	List<I_M_HU> extractIncludedHUs(List<I_M_HU> sourceHUs, int huQty, I_M_HU_PI destinationHuPI);

	/**
	 * Create and <b>process</b> transaction lines for the candidates (i.e. {@link IHUTransaction}s) included in the given {@code result}.
	 *
	 * @param huContext
	 * @param result
	 */
	void createTrx(IHUContext huContext, IAllocationResult result);

	Date getDateTrx(I_M_HU_Trx_Line line);

	/**
	 * Register given {@link IHUTrxListener}.
	 *
	 * If <code>trxListener</code> was already registered then it won't be registered again.
	 *
	 * @param trxListener
	 */
	void addListener(IHUTrxListener trxListener);

	/** @return all system registered transaction listeners (wrapped by a composite) */
	IHUTrxListener getHUTrxListeners();

	/** @return all system registered transaction listeners */
	List<IHUTrxListener> getHUTrxListenersList();

	/**
	 *
	 * @param trxLine
	 * @param modelClass
	 * @return the record that is referenced by the given {@code trxLine} via its {@code AD_Table_ID} and {@code Record_ID} values, or <code>null</code>.
	 */
	<T> T getReferencedObjectOrNull(I_M_HU_Trx_Line trxLine, Class<T> modelClass);

	void setReferencedObject(I_M_HU_Trx_Line trxLine, Object referencedModel);

	/**
	 * Create reversals of given transaction lines and process them.
	 *
	 * All reversals will be grouped in one transaction header.
	 *
	 * <code>trxLines</code> shall contain the transaction but also the counterpart transaction. If not, an error will be thrown.
	 *
	 * @param huContext
	 * @param trxLines
	 */
	void reverseTrxLines(IHUContext huContext, List<I_M_HU_Trx_Line> trxLines);

	/**
	 * @param huContext
	 * @param parentHUItem can be <code>null</code> to indicate that the HU shall be removed from its parent. If the given item has the same <code>M_HU_Item_ID</code> as the given <code>hu</code>'s
	 *            <code>M_HU_Item_Parent_ID</code>, or if both this parameter and the hu's parent item are <code>null</code>, then the method does nothing.
	 * @param hu
	 * @param destroyOldParentIfEmptyStorage if true, it will check if old parent is empty after removing given HU from it and if yes, it will destroy it
	 */
	void setParentHU(IHUContext huContext, I_M_HU_Item parentHUItem, I_M_HU hu, boolean destroyOldParentIfEmptyStorage);

	/**
	 * Same as calling {@link #setParentHU(IHUContext, I_M_HU_Item, I_M_HU, boolean)} with <code>destroyOldParentIfEmptyStorage</code>=<code>false</code>.
	 *
	 * @param huContext
	 * @param parentHUItem
	 * @param hu
	 */
	void setParentHU(IHUContext huContext, I_M_HU_Item parentHUItem, I_M_HU hu);

	IHUContextProcessorExecutor createHUContextProcessorExecutor(IHUContext huContext);

	IHUContextProcessorExecutor createHUContextProcessorExecutor(IContextAware context);
}
