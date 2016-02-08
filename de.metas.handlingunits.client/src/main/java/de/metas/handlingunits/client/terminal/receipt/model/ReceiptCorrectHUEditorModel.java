package de.metas.handlingunits.client.terminal.receipt.model;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.process.DocAction;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.document.IDocActionBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.util.HUByIdComparator;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;

/**
 * Helper panel used to allow user selecting some HUs and then reverse the receipts which are containing those HUs.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08480_Korrekturm%C3%B6glichkeit_Wareneingang_-_Menge%2C_Packvorschrift%2C_Merkmal_%28109195602347%29
 */
public class ReceiptCorrectHUEditorModel extends HUEditorModel
{
	// services
	private final transient IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final transient IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private static final transient String PROPERTY_M_InOut_ID = I_M_InOut.class.getName();

	private final Map<Integer, Set<I_M_HU>> inoutId2hus = new HashMap<>();
	private final Set<Integer> seenHUIds = new HashSet<>(); // used not add an HU more than once

	public ReceiptCorrectHUEditorModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);

		setAllowSelectingReadonlyKeys(true);
		setDisplayBarcode(true); // yes, because user will scan/search for the HU which he/she fucked up
	}

	/**
	 * Load the HUs from given receipt schedule
	 *
	 * @param receiptSchedule
	 */
	public void loadFromReceiptSchedule(final I_M_ReceiptSchedule receiptSchedule)
	{
		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");

		//
		// Retrieve and create HU Keys
		final ITerminalContext terminalContext = getTerminalContext();
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);
		final List<IHUKey> huKeys = new ArrayList<>();
		final List<I_M_InOut> receipts = receiptScheduleDAO.retrieveCompletedReceipts(receiptSchedule);
		for (final I_M_InOut receipt : receipts)
		{
			final int inoutId = receipt.getM_InOut_ID();
			final Set<I_M_HU> husForReceipt = new TreeSet<>(HUByIdComparator.instance);
			husForReceipt.addAll(huInOutDAO.retrieveHandlingUnits(receipt));
			inoutId2hus.put(inoutId, husForReceipt);

			for (final I_M_HU hu : husForReceipt)
			{
				if (!seenHUIds.add(hu.getM_HU_ID()))
				{
					continue;
				}

				// TODO: check if is a top level HU

				final IHUKey huKey = keyFactory.createKey(hu, null);
				huKey.setProperty(PROPERTY_M_InOut_ID, receipt);
				huKeys.add(huKey);
			}
		}
		//
		if (huKeys.isEmpty())
		{
			throw new TerminalException("@NotFound@ @M_HU_ID@");
		}

		//
		// Create and set Root HU Key
		final IHUKeyFactory huKeyFactory = getHUKeyFactory();
		final IHUKey rootKey = huKeyFactory.createRootKey();
		rootKey.setReadonly(true); // make sure besides selecting HUs, the user is not allowed to change them; not here!
		rootKey.addChildren(huKeys);
		setRootHUKey(rootKey);

	}

	/**
	 * Get the receipts to be reversed based on what HUs the user selected
	 *
	 * @return
	 */
	public List<I_M_InOut> getReceiptsToReverse()
	{
		final List<I_M_InOut> receiptsToReverse = new ArrayList<>();

		//
		// Collect receipts to reverse
		final Set<HUKey> huKeysToDestroy = getSelectedHUKeys();
		for (final HUKey huKey : huKeysToDestroy)
		{
			final I_M_InOut receipt = huKey.getProperty(PROPERTY_M_InOut_ID);
			if (receipt == null)
			{
				// it could be that user selected not a top level HU.... skip it for now
				continue;
			}

			receiptsToReverse.add(receipt);
		}

		return receiptsToReverse;
	}

	/**
	 * Get all HUs which are assigned to given receipts.
	 *
	 * @param receipts
	 * @return
	 */
	private Set<I_M_HU> getHUsForReceipts(final Collection<? extends I_M_InOut> receipts)
	{
		final Set<I_M_HU> hus = new TreeSet<>(HUByIdComparator.instance);

		for (final I_M_InOut receipt : receipts)
		{
			final int inoutId = receipt.getM_InOut_ID();
			final Set<I_M_HU> husForReceipt = inoutId2hus.get(inoutId);
			if (Check.isEmpty(husForReceipt))
			{
				continue;
			}

			hus.addAll(husForReceipt);
		}

		return hus;
	}

	/**
	 * Reverse given receipts.
	 *
	 * @param receiptsToReverse
	 */
	public void reverseReceipts(final List<I_M_InOut> receiptsToReverse)
	{
		if (receiptsToReverse.isEmpty())
		{
			throw new TerminalException("@NotFound@ @M_InOut_ID@");
		}

		//
		// Reverse those receipts
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		docActionBL.processDocumentsList(receiptsToReverse, DocAction.ACTION_Reverse_Correct, DocAction.STATUS_Reversed);
	}

	/**
	 * Build a detailed info about what will be affected if given receipts will be reversed.
	 *
	 * @param receiptsToReverse
	 * @return
	 */
	public String buildReceiptsToReverseInfo(final List<I_M_InOut> receiptsToReverse)
	{
		if (Check.isEmpty(receiptsToReverse))
		{
			return "";
		}

		//
		// Receipts info
		final StringBuilder sbReceipts = new StringBuilder();
		for (final I_M_InOut receipt : receiptsToReverse)
		{
			if (sbReceipts.length() > 0)
			{
				sbReceipts.append(", ");
			}
			sbReceipts.append(receipt.getDocumentNo());
		}

		//
		// HUs info
		final StringBuilder sbHUsInfo = new StringBuilder();
		for (final I_M_HU hu : getHUsForReceipts(receiptsToReverse))
		{
			final String huDisplayName = handlingUnitsBL.getDisplayName(hu);

			if (sbHUsInfo.length() > 0)
			{
				sbHUsInfo.append(", ");
			}
			sbHUsInfo.append(huDisplayName);
		}

		//
		// Build info
		final StringBuilder info = new StringBuilder();
		info.append(msgBL.translate(getCtx(), I_M_InOut.COLUMNNAME_M_InOut_ID))
				.append(": ")
				.append(sbReceipts);
		info.append("\n")
				.append(msgBL.translate(getCtx(), I_M_HU.COLUMNNAME_M_HU_ID))
				.append(": ")
				.append(sbHUsInfo);

		return info.toString();
	}

	public static final ReceiptCorrectHUEditorModel cast(final HUEditorModel huEditorModel)
	{
		final ReceiptCorrectHUEditorModel receiptCorrectHUEditorModel = (ReceiptCorrectHUEditorModel)huEditorModel;
		return receiptCorrectHUEditorModel;
	}

}
