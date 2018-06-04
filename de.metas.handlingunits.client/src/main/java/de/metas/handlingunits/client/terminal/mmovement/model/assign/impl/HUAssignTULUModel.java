package de.metas.handlingunits.client.terminal.mmovement.model.assign.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.allocation.transfer.impl.LUTUAssignBuilder;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUKeyFactory;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUKey;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.impl.AbstractLTCUModel;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

/**
 * Model responsible for assigning TUs on LUs (LU zuteilen)
 *
 * @author al
 */
public class HUAssignTULUModel extends AbstractLTCUModel
{
	private static final String ERR_SELECT_ONLY_TUs = "SelectOnlyTUs";

	private static final String ERR_TU_PARTNER_MATCH = "TUPartnerMatch";
	private static final String ERR_TU_LOCATION_MATCH = "TULocationMatch";
	private static final String ERR_TU_LOCATOR_MATCH = "TULocatorMatch";
	private static final String ERR_TU_HUSTATUS_MATCH = "TUHUStatusMatch";

	//
	// Parameters
	private final IHUKey rootKeyInitial;
	private final List<I_M_HU> selectedTUHUs;

	//
	// Parameters used to create the new LU
	private IHUDocumentLine documentLine = null;
	private I_C_BPartner bpartner;
	private int bpLocationId = -1;
	private I_M_Locator locator;
	private String huStatus;
	private boolean _isHUPlanningReceiptOwnerPM = false;

	//
	// Result
	/** Newly created LU */
	private I_M_HU resultLUHU = null;

	public HUAssignTULUModel(final ITerminalContext terminalContext, final IHUKey rootKey, final Set<HUKey> tuKeys)
	{
		super(terminalContext);

		//
		// No point in opening if no TU is selected
		if (tuKeys.isEmpty())
		{
			throw new MaterialMovementException(ERR_SELECT_TU_KEY);
		}

		// Make sure TU Keys are not read-only
		for (final HUKey tuKey : tuKeys)
		{
			if (tuKey.isReadonly())
			{
				throw new TerminalException("@ReadOnly@: " + tuKey.getName());
			}
		}

		rootKeyInitial = rootKey;

		//
		// Configure default selection
		getLUKeyLayout().getKeyLayoutSelectionModel().setAllowMultipleSelection(false);

		//
		// LUs
		{
			final Collection<ITerminalKey> luKeys = createLUKeys(tuKeys);
			getLUKeyLayout().setKeys(luKeys);
		}

		//
		// Retain selected TU-HUs in the model's state for execution
		selectedTUHUs = new ArrayList<>();
		for (final HUKey tuKey : tuKeys)
		{
			final I_M_HU tuHU = tuKey.getM_HU();
			selectedTUHUs.add(tuHU);

			//
			// Bind document line, if any
			if (documentLine == null)
			{
				documentLine = tuKey.findDocumentLineOrNull();
			}

			final I_C_BPartner tuBPartner = tuHU.getC_BPartner();
			if (bpartner == null)
			{
				bpartner = tuBPartner;
			}
			else if (bpartner.getC_BPartner_ID() != tuHU.getC_BPartner_ID())
			{
				throw new AdempiereException(ERR_TU_PARTNER_MATCH, new Object[] { tuHU.getValue(), bpartner.getValue(), tuBPartner });
			}

			final int tuBPLocationId = tuHU.getC_BPartner_Location_ID();
			if (bpLocationId < 0)
			{
				bpLocationId = tuBPLocationId;
			}
			else if (bpLocationId != tuBPLocationId)
			{
				throw new AdempiereException(ERR_TU_LOCATION_MATCH, new Object[] { tuHU.getValue(), bpLocationId, tuBPLocationId });
			}

			final I_M_Locator tuLocator = tuHU.getM_Locator();
			if (locator == null)
			{
				locator = tuLocator;
			}
			else if (locator.getM_Locator_ID() != tuHU.getM_Locator_ID())
			{
				throw new AdempiereException(ERR_TU_LOCATOR_MATCH, new Object[] { tuHU.getValue(), locator.getValue(), tuLocator });
			}

			if (huStatus == null)
			{
				huStatus = tuHU.getHUStatus();
			}
			else if (!huStatus.equals(tuHU.getHUStatus()))
			{
				throw new AdempiereException(ERR_TU_HUSTATUS_MATCH, new Object[] { tuHU.getValue(), huStatus, tuHU.getHUStatus() });
			}
		}
	}

	private Collection<ITerminalKey> createLUKeys(final Set<HUKey> tuKeys)
	{
		final String huUnitType = X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit;
		final Collection<I_M_HU_PI_Item> luPIItems = getLUPIItems(tuKeys, huUnitType);

		final List<ITerminalKey> keys = new ArrayList<>(luPIItems.size());
		for (final I_M_HU_PI_Item luPIItem : luPIItems)
		{
			final I_M_HU_PI luPI = luPIItem.getM_HU_PI_Version().getM_HU_PI();

			final LUKey key = new LUKey(getTerminalContext(), luPI, luPIItem);
			keys.add(key);
		}

		return keys;
	}

	private Collection<I_M_HU_PI_Item> getLUPIItems(final Set<HUKey> huKeys, final String huUnitType)
	{
		final I_C_BPartner bpartner = null; // TODO do we want to filter LUs by BP?

		//
		// Collector for HUs which are not TUs - exception message will be displayed to the user
		final List<String> nonTUValues = new ArrayList<>();

		final Map<Integer, I_M_HU_PI_Item> luPIItems = new HashMap<>();
		for (final HUKey huKey : huKeys)
		{
			final I_M_HU hu = huKey.getM_HU();
			if (!handlingUnitsBL.isTransportUnitOrVirtual(hu)) // NOTE: consider VHUs too (i.e put XX kg of Folie on a Palette)
			{
				nonTUValues.add(hu.getValue());
			}

			//
			// Do not attempt to retrieve further; we'll display an exception message with the collected wrongly selected HUs
			if (!nonTUValues.isEmpty())
			{
				continue;
			}

			final I_M_HU_PI tuPI = handlingUnitsBL.getPI(hu);
			final List<I_M_HU_PI_Item> subLUPIItems = handlingUnitsDAO.retrieveParentPIItemsForParentPI(tuPI, huUnitType, bpartner);

			//
			// Get unique LUs in the map
			for (final I_M_HU_PI_Item luPIItem : subLUPIItems)
			{
				luPIItems.put(luPIItem.getM_HU_PI_Version_ID(), luPIItem); // group them by PIV, not by the item
			}
		}

		//
		// Throw exception if the user selected non-TU HUs
		if (!nonTUValues.isEmpty())
		{
			throw new AdempiereException(ERR_SELECT_ONLY_TUs, new Object[] { nonTUValues.toString() });
		}

		final List<I_M_HU_PI_Item> luPIItemsSorted = new ArrayList<>(luPIItems.values());
		Collections.sort(luPIItemsSorted, new Comparator<I_M_HU_PI_Item>()
		{
			@Override
			public int compare(final I_M_HU_PI_Item o1, final I_M_HU_PI_Item o2)
			{
				return o1.getM_HU_PI_Item_ID() - o2.getM_HU_PI_Item_ID();
			}
		});

		return luPIItemsSorted;
	}

	@Override
	public final void execute() throws MaterialMovementException
	{
		Check.assumeNull(resultLUHU, "resultLUHU null");

		resultLUHU = new LUTUAssignBuilder()
				.setContext(getTerminalContext())
				.setLU_PI(getSelectedLU_PI())
				.setC_BPartner(bpartner)
				.setC_BPartner_Location_ID(bpLocationId)
				.setM_Locator(locator)
				.setHUStatus(huStatus)
				.setHUPlanningReceiptOwnerPM(isHUPlanningReceiptOwnerPM())
				.setTUsToAssign(selectedTUHUs)
				.setDocumentLine(documentLine)
				.build();
	}

	private final I_M_HU_PI getSelectedLU_PI()
	{
		final LUKey selectedLUKey = getLUKeyLayout().getKeyLayoutSelectionModel().getSelectedKey(LUKey.class, ERR_SELECT_LU_KEY);
		final I_M_HU_PI luPI = selectedLUKey.getM_HU_PI();
		return luPI;
	}

	@Override
	protected final void onCUPressed(final ITerminalKey key)
	{
		// nothing
	}

	@Override
	protected final void onTUPressed(final ITerminalKey key)
	{
		// nothing
	}

	@Override
	protected final void onLUPressed(final ITerminalKey key)
	{
		// nothing
	}

	public final void setHUPlanningReceiptOwnerPM(final boolean isHUPlanningReceiptOwnerPM)
	{
		_isHUPlanningReceiptOwnerPM = isHUPlanningReceiptOwnerPM;
	}

	private final boolean isHUPlanningReceiptOwnerPM()
	{
		return _isHUPlanningReceiptOwnerPM;
	}

	public final void refreshRootKey()
	{
		//
		// Remove all original keys from parent & dump storages if empty
		rebuildRootKey(rootKeyInitial);

		//
		// Create New LU-HU Key from the ILUTUJoinKeys
		if (resultLUHU != null)
		{
			final IHUKeyFactory keyFactory = rootKeyInitial.getKeyFactory();
			final IHUKey resultHUKey = keyFactory.createKey(resultLUHU, documentLine);
			rootKeyInitial.addChild(resultHUKey);
		}
	}
}
