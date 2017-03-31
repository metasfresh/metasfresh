package de.metas.handlingunits.client.terminal.receipt.model;

import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.inout.ReceiptCorrectHUsProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;

/**
 * Helper panel used to allow user selecting some HUs and then reverse the receipts which are containing those HUs.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08480_Korrekturm%C3%B6glichkeit_Wareneingang_-_Menge%2C_Packvorschrift%2C_Merkmal_%28109195602347%29
 */
public class ReceiptCorrectHUEditorModel extends HUEditorModel
{
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private ReceiptCorrectHUsProcessor receiptCorrectProcessor;

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

		this.receiptCorrectProcessor = ReceiptCorrectHUsProcessor.builder()
				.setM_ReceiptSchedule(receiptSchedule)
				.build();

		//
		// Retrieve and create HU Keys
		final ITerminalContext terminalContext = getTerminalContext();
		final IHUKeyFactory keyFactory = terminalContext.getService(IHUKeyFactory.class);
		final List<IHUKey> huKeys = receiptCorrectProcessor.getAvailableHUsToReverse()
				.stream()
				.map(hu -> keyFactory.createKey(hu, null))
				.collect(GuavaCollectors.toImmutableList());

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
		final Set<HUKey> huKeysToDestroy = getSelectedHUKeys();
		return receiptCorrectProcessor.getReceiptsToReverse(huKeysToDestroy);
	}

	/**
	 * Reverse given receipts.
	 *
	 * @param receiptsToReverse
	 */
	public void reverseReceipts(final List<I_M_InOut> receiptsToReverse)
	{
		try
		{
			receiptCorrectProcessor.reverseReceipts(receiptsToReverse);
		}
		catch(Exception e)
		{
			throw new TerminalException(e.getLocalizedMessage(), e);
		}
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
		for (final I_M_HU hu : receiptCorrectProcessor.getHUsForReceipts(receiptsToReverse))
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
