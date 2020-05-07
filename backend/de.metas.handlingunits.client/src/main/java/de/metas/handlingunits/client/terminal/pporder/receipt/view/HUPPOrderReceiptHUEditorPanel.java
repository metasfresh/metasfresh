package de.metas.handlingunits.client.terminal.pporder.receipt.view;

import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.CUKey;
import de.metas.handlingunits.client.terminal.pporder.receipt.model.HUPPOrderReceiptCUKey;
import de.metas.handlingunits.client.terminal.pporder.receipt.model.HUPPOrderReceiptHUEditorModel;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.HUPPOrderIssueReceiptCandidatesProcessor;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;

public final class HUPPOrderReceiptHUEditorPanel extends HUEditorPanel
{
	public HUPPOrderReceiptHUEditorPanel(final HUPPOrderReceiptHUEditorModel model)
	{
		super(model);
	}

	@Override
	protected HUPPOrderReceiptHUEditorModel getHUEditorModel()
	{
		return (HUPPOrderReceiptHUEditorModel)super.getHUEditorModel();
	}

	@Override
	protected final void onDialogOkAfterSave(final ITerminalDialog dialog)
	{
		//
		// Run super first, to make sure model is saved
		super.onDialogOkAfterSave(dialog);

		final HUPPOrderReceiptHUEditorModel model = getHUEditorModel();

		final Set<HUKey> selectedHUKeys = model.getSelectedHUKeys();
		if (selectedHUKeys == null || selectedHUKeys.isEmpty())
		{
			throw new TerminalException("@NoSelection@");
		}

		// get the selected cuKey
		final CUKey cuKey = model.getCUKey();

		final HUPPOrderReceiptCUKey orderReceiptCUKey = (HUPPOrderReceiptCUKey)cuKey;

		Services.get(ITrxManager.class).run(() -> {
			for (final HUKey huKey : selectedHUKeys)
			{
				// load the HU here because in case something will fail, our HUKey's HU won't be affected
				final I_M_HU selectedHU = InterfaceWrapperHelper.load(huKey.getM_HU_ID(), I_M_HU.class);

				//
				// Create receipt candidates for our already existing planning HU
				final IPPOrderReceiptHUProducer producer = orderReceiptCUKey.createReceiptCandidatesProducer();
				producer.createReceiptCandidatesFromPlanningHU(selectedHU);

				//
				// Process the receipt candidates we just created
				// => HU will be activated, a receipt cost collector will be generated, 
				HUPPOrderIssueReceiptCandidatesProcessor.newInstance()
						.setCandidatesToProcess(producer.getCreatedCandidates())
						.process();
			}
		});
	}
}
