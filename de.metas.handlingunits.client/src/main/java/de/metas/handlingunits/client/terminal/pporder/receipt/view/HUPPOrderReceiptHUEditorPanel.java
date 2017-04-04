package de.metas.handlingunits.client.terminal.pporder.receipt.view;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.handlingunits.IHandlingUnitsBL;
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

		// TODO: decide to delete following because not needed and will be implemented differently
//		if (!(cuKey instanceof HUPPOrderReceiptCUKey))
//		{
//			model.doMoveForwardSelectedHUs();
//			return;
//		}

		final HUPPOrderReceiptCUKey orderReceiptCUKey = (HUPPOrderReceiptCUKey)cuKey;

		Services.get(ITrxManager.class).run(() -> {
			for (final HUKey huKey : selectedHUKeys)
			{
				final I_M_HU selectedHU = huKey.getM_HU();

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

		//
		// Complete forward DD Order if exists.
		// We need to do this after first MO receipt, to allow the Bereitsteller to move the materials forward.
		// task 07395
		// TODO: decide to delete following because not needed and will be implemented differently
//		Services.get(IDDOrderBL.class).completeForwardDDOrdersIfNeeded(model.getPP_Order());
	}

	@Override
	protected void onDialogOkBeforeSave(final ITerminalDialog dialog)
	{
		// 08077
		// Logic moved from de.metas.handlingunits.pporder.api.impl.PPOrderHUAssignmentListener.onHUAssigned(I_M_HU, Object, String)
		// This is the place when we want the HUs to be activated
		// service
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		final HUPPOrderReceiptHUEditorModel model = getHUEditorModel();

		final Set<HUKey> selectedHUKeys = model.getSelectedHUKeys();
		if (selectedHUKeys == null || selectedHUKeys.isEmpty())
		{
			throw new TerminalException("@NoSelection@");
		}

		final List<I_M_HU> selectedHUs = selectedHUKeys.stream().map(HUKey::getM_HU).collect(ImmutableList.toImmutableList());
		handlingUnitsBL.setHUStatusActive(selectedHUs);
	}

}
