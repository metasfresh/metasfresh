package de.metas.handlingunits.client.terminal.aggregate.model;

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


import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.comparator.NullSafeComparator;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.shipping.api.IShipperTransportationDAO;
import de.metas.tourplanning.model.I_M_ShipperTransportation;
import de.metas.tourplanning.model.I_M_Tour;

/**
 * Verdichtung (POS) HU Editor Model (second window)
 *
 * @author tsa
 *
 */
public class AggregateHUEditorModel extends HUEditorModel
{
	// Services
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final IShipperTransportationDAO shipperTransportationDAO = Services.get(IShipperTransportationDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	// private static final transient Logger logger = CLogMgt.getLogger(AggregateHUEditorModel.class);

	public AggregateHUEditorModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);

		//
		// Configure terminal context properties
		terminalContext.setProperty(ILTCUModel.PROPERTY_AllowSameTUInfiniteCapacity, true);
	}

	/**
	 * Retrieves a list of {@link KeyNamePair} for the currently available {@link I_M_ShipperTransportation}s, ordered by
	 * <ul>
	 * <li>M_Tour.Name (ignoring case)
	 * <li>DateDoc
	 * <li>DocumentNo (ignoring case)
	 * </ul>
	 * The KeyNamePair's name is composed of <code>DateDoc-TourName-ShipperName</code>.
	 *
	 * @return
	 */
	public List<KeyNamePair> retrieveOpenShipperTransportations()
	{
		final ITerminalContext terminalContext = getTerminalContext();

		final List<I_M_ShipperTransportation> shipperTransportations = shipperTransportationDAO.retrieveOpenShipperTransportations(terminalContext.getCtx(), I_M_ShipperTransportation.class);

		// request by mark: can someone please do a sorting of the dropdown shown in verdichtung pos for speditionsauftrag. please sort by tour, date, shipper.
		Collections.sort(shipperTransportations, new Comparator<I_M_ShipperTransportation>()
		{
			@Override
			public int compare(final I_M_ShipperTransportation o1, final I_M_ShipperTransportation o2)
			{
				if (o1.getM_Tour_ID() != o2.getM_Tour_ID())
				{
					final String tour1 = o1.getM_Tour_ID() > 0 ? o1.getM_Tour().getName().toUpperCase() : null;
					final String tour2 = o2.getM_Tour_ID() > 0 ? o2.getM_Tour().getName().toUpperCase() : null;
					return new NullSafeComparator<String>().compare(tour1, tour2);
				}

				final int dateCompareResult = new NullSafeComparator<Timestamp>().compare(o1.getDateDoc(), o2.getDateDoc());
				if (dateCompareResult != 0)
				{
					return dateCompareResult;
				}

				if (o1.getM_Shipper_ID() != o2.getM_Shipper_ID())
				{
					final String shipper1 = o1.getM_Shipper_ID() > 0 ? o1.getM_Shipper().getName().toUpperCase() : null;
					final String shipper2 = o2.getM_Tour_ID() > 0 ? o2.getM_Shipper().getName().toUpperCase() : null;
					return new NullSafeComparator<String>().compare(shipper1, shipper2);
				}

				return new NullSafeComparator<String>().compare(o1.getDocumentNo(), o2.getDocumentNo());
			}
		});

		if (shipperTransportations.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @" + de.metas.shipping.model.I_M_ShipperTransportation.COLUMNNAME_M_ShipperTransportation_ID + "@");
		}

		final List<KeyNamePair> result = new ArrayList<>(shipperTransportations.size() + 1);

		// Add none (selected by default)
		result.add(new KeyNamePair(-1, ""));

		for (final I_M_ShipperTransportation shipperTransportation : shipperTransportations)
		{
			final KeyNamePair knp = createKeyNamePair(shipperTransportation);
			result.add(knp);
		}
		return result;
	}

	private final KeyNamePair createKeyNamePair(final I_M_ShipperTransportation shipperTransportation)
	{
		final StringBuilder displayName = new StringBuilder();

		final Date dateDoc = shipperTransportation.getDateDoc();
		if (dateDoc != null)
		{
			final DateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
			if (displayName.length() > 0)
			{
				displayName.append("-");
			}
			displayName.append(dateFormat.format(dateDoc));
		}

		final de.metas.tourplanning.model.I_M_ShipperTransportation swatShipperTransportation = InterfaceWrapperHelper.create(shipperTransportation,
				de.metas.tourplanning.model.I_M_ShipperTransportation.class);

		final I_M_Tour tour = swatShipperTransportation.getM_Tour();

		final String tourName = tour == null ? null : tour.getName();

		if (!Check.isEmpty(tourName, true))
		{
			if (displayName.length() > 0)
			{
				displayName.append("-");
			}

			displayName.append(tourName);
		}

		final I_M_Shipper shipper = shipperTransportation.getM_Shipper();
		if (shipper != null)
		{
			if (displayName.length() > 0)
			{
				displayName.append("-");

			}
			displayName.append(shipper.getName());
		}

		final KeyNamePair knp = new KeyNamePair(shipperTransportation.getM_ShipperTransportation_ID(), displayName.toString());
		return knp;
	}

	public void onShipperTransportationChanged(final KeyNamePair value)
	{
		if (value == null)
		{
			return;
		}

		final int shipperTransportationId = value.getKey();
		if (shipperTransportationId <= 0)
		{
			return;
		}

		addSelectionToShipperTransportation(shipperTransportationId);
	}

	/**
	 * Add all selected HUs to given Shipper Transportation
	 *
	 * @param shipperTransportationId
	 */
	private void addSelectionToShipperTransportation(final int shipperTransportationId)
	{
		//
		// Get selected HU Keys
		final Set<HUKey> huKeys = getSelectedHUKeys();
		if (huKeys.isEmpty())
		{
			return;
		}

		//
		// Validate HUKeys and get HUs from them
		final Set<I_M_HU> hus = new HashSet<>(huKeys.size());
		for (final HUKey huKey : huKeys)
		{
			//
			// If there is no M_HU, skip it. Shall not happen.
			final I_M_HU hu = huKey.getM_HU();
			if (hu == null)
			{
				continue;
			}

			//
			// Add HU to our list
			hus.add(hu);
		}

		//
		// From HUs, create M_Packages and then assign them to selected Shipper Transportation
		trxManager.run(() -> huShipperTransportationBL.addHUsToShipperTransportation(shipperTransportationId, hus));

		//
		// Iterate HU Keys and fire status changed (those HUs got locked)
		for (final HUKey huKey : huKeys)
		{
			huKey.fireStatusChanged();
		}

		// Finally this, we need to refresh.
		// Navigate back to root
		setCurrentHUKey(getRootHUKey());

		//
		// Finally, clear selection.
		clearSelectedKeyIds();
	}

}
