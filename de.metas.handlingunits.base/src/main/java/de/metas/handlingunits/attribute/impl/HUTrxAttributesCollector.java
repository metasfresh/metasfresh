package de.metas.handlingunits.attribute.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUTransactionAttribute;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.AttributeStorageListenerAdapter;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.impl.MutableHUTransactionAttribute;
import de.metas.handlingunits.model.X_M_HU_Trx_Attribute;
import de.metas.logging.LogManager;

/**
 * Listens on {@link IAttributeStorage}s changes and logs the change.
 *
 * The current changes can be fetched by {@link #getAndClearTransactions()} and then the hu transaction processor will create actual transactions for them.
 *
 * NOTE: this implementation is NOT thread-safe, but we assume it's used only locally and in one processing thread.
 *
 * @author tsa
 *
 */
/* package */class HUTrxAttributesCollector extends AttributeStorageListenerAdapter
{
	// services
	private static final transient Logger logger = LogManager.getLogger(HUTrxAttributesCollector.class);

	/** Is this collector disposed? */
	private boolean _disposed = false;

	/**
	 * List of current attribute transaction candidates
	 */
	private List<IHUTransactionAttribute> transactions = null;

	/* package */ HUTrxAttributesCollector()
	{
		super();
	}

	@Override
	public String toString()
	{
		return "HUTrxAttributesCollector [_disposed=" + _disposed + ", transactions=" + transactions + ", uniqueKeyIndex=" + uniqueKeyIndex + "]";
	}

	/**
	 * Warns the user that there it could be an internal error if this object is about to be garbage collected,
	 * and we still have collected transactions.
	 */
	@Override
	protected void finalize() throws Throwable
	{
		if (transactions != null && !transactions.isEmpty())
		{
			new AdempiereException(
					"Possible development error on " + HUTrxAttributesCollector.class
							+ "\n There are still collected " + transactions.size() + "transactions."
							+ "\n Disposed flag: " + _disposed)
									.throwIfDeveloperModeOrLogWarningElse(logger);
		}
	};

	/**
	 * Flags this collector as disposed.
	 */
	public void dispose()
	{
		//
		// Check if there are still pending collected transactions.
		// If yes, throw exception/log warning because in most of the cases this is a programatic error.
		if (!isEmpty())
		{
			final HUException ex = new HUException("Possible development error: we are disponsing an attribute transactions collector which has pending transactions"
					+ "\n Collector: " + this);
			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				throw ex;
			}
			logger.warn(ex.getLocalizedMessage(), ex);
		}
		this._disposed = true;
	}

	/**
	 * Makes sure this collector was not already disposed, because if it was disposed, there is no point to add more transactions to it
	 * because they will be lost.
	 */
	private final void assertNotDisposed()
	{
		Check.assume(!_disposed, "Collector shall not be disposed: {}", this);
	}

	/**
	 * Creates Attribute Transaction candidate based on <code>attributeValue</code>.
	 *
	 * @param storage
	 * @param attributeValue
	 * @param operation attribute operation (save/drop)
	 * @return attribute transaction candidate
	 */
	private IHUTransactionAttribute createTrxAttribute(final IAttributeStorage storage, final IAttributeValue attributeValue, final String operation)
	{
		final MutableHUTransactionAttribute huTrxAttribute = new MutableHUTransactionAttribute();

		//
		// Set Attribute Transaction operation (Save/Drop)
		huTrxAttribute.setOperation(operation);

		//
		// Update our "huTrxAttribute" from attributeValue
		huTrxAttribute.setM_Attribute(attributeValue.getM_Attribute());
		huTrxAttribute.setValueString(attributeValue.getValueAsString());
		huTrxAttribute.setValueNumber(attributeValue.getValueAsBigDecimal());
		huTrxAttribute.setValueDate(attributeValue.getValueAsDate());
		huTrxAttribute.setValueStringInitial(attributeValue.getValueInitialAsString());
		huTrxAttribute.setValueNumberInitial(attributeValue.getValueInitialAsBigDecimal());
		huTrxAttribute.setValueDateInitial(attributeValue.getValueInitialAsDate());

		//
		// Update our "huTrxAttribute" with storage specific settings
		storage.updateHUTrxAttribute(huTrxAttribute, attributeValue);

		// NOTE: we are not saving it because:
		// * trx hdr/line is not set
		// * this is a candidate and not a real transaction at this stage

		return huTrxAttribute;
	}

	/**
	 * Create Attribute Transaction candidate (see {@link #createTrxAttribute(IAttributeStorage, IAttributeValue, String)}) and adds it to the list of current transaction candidates.
	 *
	 * @param storage
	 * @param attributeValue
	 * @param operation attribute operation (save/drop)
	 * @return created attribute transaction candidate
	 */
	private IHUTransactionAttribute createAndAddTrxAttribute(final IAttributeStorage storage, final IAttributeValue attributeValue, final String operation)
	{
		assertNotDisposed();

		//
		// Create HU Attribute Transaction candidate
		final IHUTransactionAttribute huTrxAttribute = createTrxAttribute(storage, attributeValue, operation);
		if (huTrxAttribute == null)
		{
			return null;
		}

		//
		// Init our transactions list (if needed)
		if (transactions == null)
		{
			transactions = new ArrayList<IHUTransactionAttribute>();
		}

		//
		// Add our transaction to list
		transactions.add(huTrxAttribute);

		//
		// Return it
		return huTrxAttribute;
	}

	@Override
	public void onAttributeValueCreated(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue)
	{
		createAndAddTrxAttribute(storage, attributeValue, X_M_HU_Trx_Attribute.OPERATION_Save);
	}

	@Override
	public void onAttributeValueChanged(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue, final Object valueOld)
	{
		createAndAddTrxAttribute(storage, attributeValue, X_M_HU_Trx_Attribute.OPERATION_Save);
	}

	@Override
	public void onAttributeValueDeleted(final IAttributeValueContext attributeValueContext, final IAttributeStorage storage, final IAttributeValue attributeValue)
	{
		createAndAddTrxAttribute(storage, attributeValue, X_M_HU_Trx_Attribute.OPERATION_Drop);
	}

	/**
	 * Gets current collected {@link IHUTransactionAttribute}s (HU attribute changes).
	 *
	 * After this call the collected transaction attributes list will be cleared.
	 *
	 * @return current collected {@link IHUTransactionAttribute}s
	 */
	public List<IHUTransactionAttribute> getAndClearTransactions()
	{
		final List<IHUTransactionAttribute> retValue;
		if (transactions == null)
		{
			retValue = Collections.emptyList();
		}
		else
		{
			retValue = transactions;
		}

		transactions = null;

		removeDuplicateTransactions(retValue);
		return retValue;
	}

	/**
	 * @return true if there are no attribute transactions collected
	 */
	public final boolean isEmpty()
	{
		return transactions == null ? true : transactions.isEmpty();
	}

	/**
	 * Removes all collected transactions (if any).
	 */
	public final void clearTransactions()
	{
		transactions = null;
	}

	/**
	 * Iterates given list (from end to begining) and deletes all duplicate transactions, keeping only the last one.
	 *
	 * Two transactions are considered duplicate if their keys (see {@link #mkKey(IHUTransactionAttribute)}) are equal.
	 *
	 * @param transactions
	 */
	private final void removeDuplicateTransactions(final List<IHUTransactionAttribute> transactions)
	{
		if (transactions.isEmpty())
		{
			return;
		}

		//
		// Check our transactions history and if we find a transaction which is shadowed by our newly created transaction
		// then just remove it from list and keep only ours
		final int size = transactions.size();
		final Set<ArrayKey> seenTrxs = new HashSet<ArrayKey>(size);
		final ListIterator<IHUTransactionAttribute> it = transactions.listIterator(size);
		while (it.hasPrevious())
		{
			final IHUTransactionAttribute trx = it.previous();
			final ArrayKey trxKey = mkKey(trx);

			final boolean uniqueTransaction = seenTrxs.add(trxKey);

			if (!uniqueTransaction)
			{
				// Already seen transaction => remove it
				it.remove();
				continue;
			}

		}
	}

	/**
	 * Used by {@link #mkKey(IHUTransactionAttribute)} in case we want to make sure a given key is unique.
	 *
	 * In this case it will use and increment this value.
	 */
	private int uniqueKeyIndex = 1;

	/**
	 * Creates transaction's unique key.
	 *
	 * @param trx
	 * @return
	 */
	private final ArrayKey mkKey(final IHUTransactionAttribute trx)
	{
		final int attributeId = trx.getM_Attribute().getM_Attribute_ID();
		final String operation = trx.getOperation();

		final Object referencedObject = trx.getReferencedObject();
		final String referencedObjectTableName;
		final int referencedObjectId;
		final int uniqueDiscriminant;
		if (referencedObject == null)
		{
			referencedObjectTableName = null;
			referencedObjectId = -1;
			uniqueDiscriminant = uniqueKeyIndex++; // we want to have a unique discriminant
		}
		else
		{
			referencedObjectTableName = InterfaceWrapperHelper.getModelTableName(referencedObject);
			referencedObjectId = InterfaceWrapperHelper.getId(referencedObject);
			uniqueDiscriminant = 0; // nop, we don't want to have a unique discriminant
		}

		return Util.mkKey(uniqueDiscriminant, attributeId, operation, referencedObjectTableName, referencedObjectId);
	}
}
