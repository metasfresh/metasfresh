package de.metas.handlingunits.inout.impl;

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

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import de.metas.handlingunits.HUAssignmentListenerAdapter;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;

/**
 * Listener triggered by {@link IHUAssignmentBL} when a HU is assigned/un-assigned from an Receipt Line.
 *
 * In case HUs are assigned to a receipt, their HUStatus will be set to Active
 *
 * @author tsa
 *
 */
public final class ReceiptInOutLineHUAssignmentListener extends HUAssignmentListenerAdapter
{
	public static final transient ReceiptInOutLineHUAssignmentListener instance = new ReceiptInOutLineHUAssignmentListener();

	private ReceiptInOutLineHUAssignmentListener()
	{
	}

	@Override
	public void onHUAssigned(final I_M_HU hu, final Object model, final String trxName)
	{
		if (!InterfaceWrapperHelper.isInstanceOf(model, I_M_InOutLine.class))
		{
			// does not apply
			return;
		}

		final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(model, I_M_InOutLine.class);

		//
		// Make sure we deal with a receipt
		final I_M_InOut inout = inoutLine.getM_InOut();
		if (inout.isSOTrx())
		{
			return;
		}

		//
		// Active HU
		activateHU(hu, inoutLine, trxName);
	}

	private void activateHU(final I_M_HU hu, final I_M_InOutLine inoutLine, final String trxName)
	{
		//
		// Get running context
		final Properties ctx = InterfaceWrapperHelper.getCtx(hu);
		final IContextAware contextProvider = PlainContextAware.newWithTrxName(ctx, trxName);
		Services.get(ITrxManager.class).assertTrxNameNotNull(trxName);

		//
		// Activate the HU, set it's locator and reset BPartner
		// NOTE: we need to do it in a HUContextProcessor because we are also changing HU attributes,
		// which needs to be propagated and the HU attribute transactions needs to be processed.
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		huTrxBL.createHUContextProcessorExecutor(contextProvider)
				.run(new IHUContextProcessor()
				{
					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						activeHUAfterReceipt(huContext, hu, inoutLine);
						return NULL_RESULT; // don't care
					}
				});
	}

	/**
	 * Activate the HU after receipt. Mainly it is:
	 * <ul>
	 * <li>changing the HU's status to {@link X_M_HU#HUSTATUS_Active}.
	 * <li>setting HU's locator to <code>receiptLocatorId</code>
	 * <li>reseting the vendor: HU's BPartner is set to null, subproducer attribute is set to HU's BPartner if it was not set before (see {@link #resetVendor(IHUContext, I_M_HU)})
	 * <li>setting the HU's {@link Constants#ATTR_PurchaseOrderLine_ID} and {@link Constants#ATTR_ReceiptInOutLine_ID} attributes.
	 * </ul>
	 *
	 * NOTE: we assume this method is executed in a {@link IHUContextProcessor}.
	 *
	 * @param huContext
	 * @param hu
	 * @param receiptLine
	 */
	private final void activeHUAfterReceipt(final IHUContext huContext, final I_M_HU hu, final I_M_InOutLine receiptLine)
	{
		//
		// Activate HU (i.e. it's not a Planning HU anymore)
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		handlingUnitsBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Active);

		//
		// Update HU's Locator
		final int receiptLocatorId = receiptLine.getM_Locator_ID(); // the locator where this HU was received
		hu.setM_Locator_ID(receiptLocatorId);

		//
		// Update BPartner
		resetVendor(huContext, hu);

		//
		// Set PurchaseOrderLine_ID and ReceiptInOutLine_ID attributes (task 09741)
		{
			final IAttributeStorage huAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);

			final org.compiere.model.I_M_Attribute attr_PurchaseOrderLine = huAttributeStorage.getAttributeByValueKeyOrNull(Constants.ATTR_PurchaseOrderLine_ID);
			if (attr_PurchaseOrderLine != null)
			{
				huAttributeStorage.setValue(attr_PurchaseOrderLine, receiptLine.getC_OrderLine_ID());
			}

			final org.compiere.model.I_M_Attribute attr_ReceiptInOutLine = huAttributeStorage.getAttributeByValueKeyOrNull(Constants.ATTR_ReceiptInOutLine_ID);
			if (attr_ReceiptInOutLine != null)
			{
				huAttributeStorage.setValue(attr_ReceiptInOutLine, receiptLine.getM_InOutLine_ID());
			}
		}

		// Save changed HU
		InterfaceWrapperHelper.save(hu, huContext.getTrxName());
	}

	/**
	 * Set's HU's BPartner to <code>null</code>.
	 *
	 * If there is any SubProducer attribute and is not yet set, we copy the old HU's BPartner to it.
	 *
	 * @param huContext
	 * @param hu
	 * @task http://dewiki908/mediawiki/index.php/08027_Lieferdispo_Freigabe_nach_BPartner_%28100002853810%29
	 */
	private final void resetVendor(final IHUContext huContext, final I_M_HU hu)
	{
		//
		// Get the HU's BPartner.
		// We will need it to set the SubProducer.
		final int bpartnerId = hu.getC_BPartner_ID();

		//
		// Reset HU's BPartner & BP Location
		hu.setC_BPartner(null);
		hu.setC_BPartner_Location(null);

		//
		// If there was no partner, we have nothing to set
		if (bpartnerId <= 0)
		{
			return;
		}

		//
		// If HU does not support the SubProducer attribute, we have nothing to do
		final IAttributeStorage huAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
		final org.compiere.model.I_M_Attribute attribute_subProducer = huAttributeStorage.getAttributeByValueKeyOrNull(Constants.ATTR_SubProducerBPartner_Value);
		if (attribute_subProducer == null)
		{
			return;
		}

		//
		// Don't override existing value
		final int subproducerBPartnerId = huAttributeStorage.getValueAsInt(attribute_subProducer);
		if (subproducerBPartnerId > 0)
		{
			return;
		}

		//
		// Sets the ex-Vendor BPartner ID as SubProducer.
		huAttributeStorage.setValue(attribute_subProducer, bpartnerId);
	}
		}
