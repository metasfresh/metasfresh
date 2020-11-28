package de.metas.handlingunits.document.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Abstract implementation of {@link IHUAllocations} which:
 * <ul>
 * <li>deals with HU assignment
 * <li>deals with overall logic
 * <li>expects from actual extending classes to implement the HU Allocation logic
 * </ul>
 *
 * @author tsa
 *
 */
public abstract class AbstractHUAllocations implements IHUAllocations
{
	//
	// Services
	private final transient IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final transient IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters
	private final IContextAware contextProvider;
	private final Object documentLineModel;
	private final IProductStorage productStorage;

	/**
	 * Currently assigned HUs to this {@link #documentLineModel}.
	 *
	 * NOTE: this is a cached value which will be discarded it's stale.
	 */
	private List<I_M_HU> assignedHUs = null;
	private String assignedHUsTrxName = null;

	public AbstractHUAllocations(final Object documentLineModel, final IProductStorage productStorage)
	{
		super();

		Check.assumeNotNull(documentLineModel, "documentLineModel not null");
		this.documentLineModel = documentLineModel;

		final Properties ctx = InterfaceWrapperHelper.getCtx(documentLineModel);
		contextProvider = trxManager.createThreadContextAware(ctx);

		this.productStorage = productStorage;
	}

	/**
	 * Creates allocations for HUs. By allocations, we mean records that can later be represented by {@link IHUAllocations}. Current example is {@code M_ReceiptSchedule_Alloc}.
	 *
	 * @param deleteOldTUAllocations if true, delete ALL old allocations between the TU and the document (be careful with this, as it might delete allocations which are still desired)
	 */
	protected abstract void createAllocation(I_M_HU luHU,
			I_M_HU tuHU,
			I_M_HU vhu,
			StockQtyAndUOMQty qtyToAllocate,
			boolean deleteOldTUAllocations);

	/**
	 * Deletes allocation records between the given <code>husToUnassign</code> and specific records.
	 *
	 * Note that this is not about {@link I_M_HU_Assignment} records, but about specific records like <code>M_ReceiptSchedule_Alloc</code>.
	 *
	 * @param husToUnassign
	 */
	protected abstract void deleteAllocations(final Collection<I_M_HU> husToUnassign);

	/**
	 * Delete all "document line" to HU allocations.
	 */
	protected abstract void deleteAllocations();

	@OverridingMethodsMustInvokeSuper
	protected Object getDocumentLineModel()
	{
		return documentLineModel;
	}

	protected final IContextAware getContextProvider()
	{
		return contextProvider;
	}

	protected final String getTrxName()
	{
		return contextProvider.getTrxName();
	}

	@Override
	public final void clearAssignmentsAndAllocations()
	{
		final String trxName = getTrxName();
		deleteAllocations();
		huAssignmentBL.unassignAllHUs(getDocumentLineModel(), trxName);
	}

	@Override
	public final void allocate(
			@Nullable final I_M_HU luHU,
			@Nullable final I_M_HU tuHU,
			@Nullable final I_M_HU vhu,
			@NonNull final StockQtyAndUOMQty qtyToAllocate,
			final boolean deleteOldTUAllocations)
	{
		//
		// Create LU/TU/VHU to document line qty allocation
		createAllocation(luHU, tuHU, vhu, qtyToAllocate, deleteOldTUAllocations);

		//
		// Create HU to document line assignment
		if (luHU != null)
		{
			// Case: we have a TU on a LU => assign the LU to our document line
			addAssignedHUs(Collections.singleton(luHU));
		}
		else if (tuHU != null)
		{
			// Case: we have a free TU (not on a LU) => assign the TU to our document line
			addAssignedHUs(Collections.singleton(tuHU));
		}
		else if (vhu != null)
		{
			// Case: we have a free VHU
			addAssignedHUs(Collections.singleton(vhu));
		}
		else
		{
			throw new AdempiereException("LU, TU or VHU shall be not null")
					.appendParametersToMessage()
					.setParameter("deleteOldTUAllocations", deleteOldTUAllocations)
					.setParameter("qtyToAllocate", qtyToAllocate);
		}

		//
		// Mark product storage as staled
		markStorageStaled();
	}

	@Override
	public final List<I_M_HU> getAssignedHUs()
	{
		final String trxName = getTrxName();
		if (assignedHUs != null && trxManager.isSameTrxName(trxName, assignedHUsTrxName))
		{
			assignedHUs = null;
		}

		if (assignedHUs == null)
		{
			final Object documentLineModel = getDocumentLineModel();
			assignedHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(documentLineModel, trxName);
			assignedHUsTrxName = trxName;
		}
		return assignedHUs;
	}

	/**
	 * Invokes {@link IHUAssignmentBL#assignHUs(Object, Collection, String)}.
	 */
	@Override
	public final void addAssignedHUs(final Collection<I_M_HU> husToAssign)
	{
		huAssignmentBL.assignHUs(getDocumentLineModel(), husToAssign, getTrxName());

		//
		// Reset cached values
		assignedHUs = null;
		markStorageStaled();
	}

	private final void markStorageStaled()
	{
		if (productStorage == null)
		{
			return;
		}

		productStorage.markStaled();
	}

	@Override
	public final void removeAssignedHUs(final Collection<I_M_HU> husToUnassign)
	{
		final Object documentLineModel = getDocumentLineModel();
		final String trxName = getTrxName();
		huAssignmentBL.unassignHUs(documentLineModel, husToUnassign, trxName);
		deleteAllocations(husToUnassign);

		//
		// Reset cached values
		assignedHUs = null;
		markStorageStaled();
	}

	@Override
	public final void destroyAssignedHU(final I_M_HU huToDestroy)
	{
		Check.assumeNotNull(huToDestroy, "huToDestroy not null");

		InterfaceWrapperHelper.setThreadInheritedTrxName(huToDestroy);

		removeAssignedHUs(Collections.singleton(huToDestroy));

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(huToDestroy);
		final IHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(contextProvider);

		handlingUnitsBL.markDestroyed(huContext, huToDestroy);
	}

	@Override
	public String toString()
	{
		return "AbstractHUAllocations [contextProvider=" + contextProvider + ", documentLineModel=" + documentLineModel + ", productStorage=" + productStorage + ", assignedHUs=" + assignedHUs + ", assignedHUsTrxName=" + assignedHUsTrxName + "]";
	}

}
