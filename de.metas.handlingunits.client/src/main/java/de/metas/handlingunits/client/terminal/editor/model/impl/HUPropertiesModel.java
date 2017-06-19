package de.metas.handlingunits.client.terminal.editor.model.impl;

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
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;

import de.metas.adempiere.form.terminal.AbstractPropertiesPanelModel;
import de.metas.adempiere.form.terminal.IPropertiesPanelModel;
import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.lookup.SimpleTableLookup;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.impl.HUAndItemsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.i18n.IMsgBL;

/**
 * Implementation of {@link IPropertiesPanelModel} which allows user to edit some HU fields.
 *
 * @author tsa
 *
 */
public class HUPropertiesModel extends AbstractPropertiesPanelModel
{
	// Services
	private static final transient Logger logger = LogManager.getLogger(HUPropertiesModel.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	private final I_M_HU hu;

	private final List<String> propertyNames = new ArrayList<>();
	private final List<String> propertyNamesRO = Collections.unmodifiableList(propertyNames);
	private final List<IHUProperty> properties = new ArrayList<IHUProperty>();

	private boolean editable;

	public HUPropertiesModel(final ITerminalContext terminalContext, final I_M_HU hu)
	{
		super(terminalContext);

		Check.assumeNotNull(hu, "hu not null");
		this.hu = hu;

		// Make sure HU is fresh
		// e.g. in case we modify the HU's BP on one level (which is propagated top-down) and then we navigate to another level, at that level the HU is not refreshed
		InterfaceWrapperHelper.refresh(hu);

		// Allow user to edit it only on top level
		editable = isEditable(hu);

		addProperty(new IHUProperty()
		{
			private KeyNamePair value;

			@Override
			public void loadFromModel()
			{
				final I_C_BPartner bpartner = hu.getC_BPartner();
				if (bpartner == null || bpartner.getC_BPartner_ID() <= 0)
				{
					value = KeyNamePair.EMPTY;
				}
				else
				{
					value = new KeyNamePair(bpartner.getC_BPartner_ID(), bpartner.getName());
				}
			}

			@Override
			public void saveToModel()
			{
				final int bpartnerId = value == null || value.getKey() <= 0 ? -1 : value.getKey();
				hu.setC_BPartner_ID(bpartnerId);
			}

			@Override
			public String getPropertyName()
			{
				return I_M_HU.COLUMNNAME_C_BPartner_ID;
			}

			@Override
			public String getPropertyDisplayName()
			{
				return Services.get(IMsgBL.class).translate(terminalContext.getCtx(), I_M_HU.COLUMNNAME_C_BPartner_ID);
			}

			@Override
			public NamePair getValue()
			{
				return value;
			}

			@Override
			public void setValue(final NamePair value)
			{
				this.value = (KeyNamePair)value;
			}

			@Override
			public int getDisplayType()
			{
				return DisplayType.Search;
			}

			@Override
			public List<? extends NamePair> getAvailableValues()
			{
				throw new IllegalStateException("Available values not supported for C_BPartner_ID property");
			}

			@Override
			public ITerminalLookup getLookup()
			{
				return new SimpleTableLookup<>(I_C_BPartner.class, I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_BPartner.COLUMNNAME_Name);
			}

			@Override
			public void onPropertyChanged(final IHUProperty property)
			{
				// don't care
			}
		});

		//
		//
		addProperty(new IHUProperty()
		{
			private KeyNamePair value;
			private List<KeyNamePair> availableValues;

			@Override
			public void loadFromModel()
			{
				final I_C_BPartner_Location bpLocation = hu.getC_BPartner_Location();
				value = toKeyNamePair(bpLocation);
			}

			@Override
			public void saveToModel()
			{
				final int bpLocationId = value == null || value.getKey() <= 0 ? -1 : value.getKey();
				hu.setC_BPartner_Location_ID(bpLocationId);
			}

			@Override
			public void setValue(final NamePair value)
			{
				this.value = (KeyNamePair)value;
			}

			@Override
			public NamePair getValue()
			{
				return value;
			}

			private KeyNamePair toKeyNamePair(final I_C_BPartner_Location bpLocation)
			{
				if (bpLocation == null || bpLocation.getC_BPartner_Location_ID() <= 0)
				{
					return KeyNamePair.EMPTY;
				}

				return new KeyNamePair(bpLocation.getC_BPartner_Location_ID(), bpLocation.getName());
			}

			@Override
			public String getPropertyName()
			{
				return I_M_HU.COLUMNNAME_C_BPartner_Location_ID;
			}

			@Override
			public String getPropertyDisplayName()
			{
				return Services.get(IMsgBL.class).translate(terminalContext.getCtx(), I_M_HU.COLUMNNAME_C_BPartner_Location_ID);
			}

			@Override
			public int getDisplayType()
			{
				return DisplayType.List;
			}

			@Override
			public List<KeyNamePair> getAvailableValues()
			{
				if (availableValues != null)
				{
					return availableValues;
				}

				final int bpartnerId = getPropertyValueAsInt(I_M_HU.COLUMNNAME_C_BPartner_ID);
				final List<de.metas.adempiere.model.I_C_BPartner_Location> bpLocations = bpartnerDAO.retrieveBPartnerLocations(
						terminalContext.getCtx(),
						bpartnerId,
						ITrx.TRXNAME_None);
				final List<KeyNamePair> result = new ArrayList<KeyNamePair>(bpLocations.size());
				result.add(KeyNamePair.EMPTY);
				for (final I_C_BPartner_Location bpLocation : bpLocations)
				{
					result.add(toKeyNamePair(bpLocation));
				}

				availableValues = Collections.unmodifiableList(result);
				return availableValues;
			}

			@Override
			public ITerminalLookup getLookup()
			{
				throw new IllegalStateException("Lookup not supported for C_BPartner_ID property");
			}

			@Override
			public void onPropertyChanged(final IHUProperty property)
			{
				if (I_M_HU.COLUMNNAME_C_BPartner_ID.equals(property.getPropertyName()))
				{
					availableValues = null;
					validateCurrentValue();
					firePropertyValueChanged(getPropertyName());
				}
			}

			private void validateCurrentValue()
			{
				final int currentBPLocationId = value == null ? -1 : value.getKey();

				//
				// Iterate available values
				KeyNamePair suggestedValue = KeyNamePair.EMPTY;
				boolean suggestedValueFound = false;
				final List<KeyNamePair> availableValues = getAvailableValues();
				for (final KeyNamePair availableValue : availableValues)
				{
					if (availableValue == null)
					{
						continue;
					}

					final int bpLocationId = availableValue.getKey();
					if (currentBPLocationId > 0 && currentBPLocationId == bpLocationId)
					{
						return; // valid
					}

					if (bpLocationId > 0)
					{
						if (suggestedValueFound)
						{
							suggestedValue = KeyNamePair.EMPTY;
						}
						else
						{
							suggestedValue = availableValue;
							suggestedValueFound = true;
						}
					}
				}

				if (!suggestedValueFound)
				{
					suggestedValue = KeyNamePair.EMPTY;
				}

				setValue(suggestedValue); // reset value
			}
		});
	}

	/**
	 * Checks if given HU is editable
	 *
	 * @param hu
	 * @return true if is editable
	 */
	private boolean isEditable(final I_M_HU hu)
	{
		// Allow editing HU properties only for active HUs (07419)
		final String huStatus = hu.getHUStatus();
		if (!X_M_HU.HUSTATUS_Active.equals(huStatus))
		{
			return false;
		}

		// Allow editing HU properties only for top level HUs (07419)
		// ... because we are actually editing BP and BPL which are propagated top-down
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		if (!handlingUnitsBL.isTopLevel(hu))
		{
			return false;
		}

		return true;
	}

	private void addProperty(final IHUProperty property)
	{
		Check.assumeNotNull(property, "property not null");

		properties.add(property);
		propertyNames.add(property.getPropertyName());

		property.loadFromModel();
	}

	@Override
	public String getPropertyDisplayName(final String propertyName)
	{
		return getHUPropertyByPropertyName(propertyName).getPropertyDisplayName();
	}

	@Override
	public int getDisplayType(final String propertyName)
	{
		return getHUPropertyByPropertyName(propertyName).getDisplayType();
	}

	private IHUProperty getHUPropertyByPropertyName(final String propertyName)
	{
		for (final IHUProperty huProperty : properties)
		{
			if (Check.equals(huProperty.getPropertyName(), propertyName))
			{
				return huProperty;
			}
		}

		return null;
	}

	@Override
	public Object getPropertyValue(final String propertyName)
	{
		final IHUProperty property = getHUPropertyByPropertyName(propertyName);
		return property.getValue();
	}

	public final int getPropertyValueAsInt(final String propertyName)
	{
		final Object value = getPropertyValue(propertyName);
		if (value == null)
		{
			return 0;
		}
		else if (value instanceof KeyNamePair)
		{
			return ((KeyNamePair)value).getKey();
		}
		else if (value instanceof Number)
		{
			return ((Number)value).intValue();
		}
		else
		{
			throw new AdempiereException("Cannot convert " + propertyName + "'s value '" + value + "' to integer");
		}
	}

	@Override
	public List<? extends NamePair> getPropertyAvailableValues(final String propertyName)
	{
		return getHUPropertyByPropertyName(propertyName).getAvailableValues();
	}

	@Override
	public ITerminalLookup getPropertyLookup(final String propertyName)
	{
		return getHUPropertyByPropertyName(propertyName).getLookup();
	}

	@Override
	public void setPropertyValue(final String propertyName, final Object value)
	{
		final KeyNamePair knp = (KeyNamePair)value;
		final IHUProperty property = getHUPropertyByPropertyName(propertyName);
		property.setValue(knp);

		//
		// Announce everyone that a property value was changed
		for (final IHUProperty p : properties)
		{
			p.onPropertyChanged(property);
		}
	}

	@Override
	public boolean isEditable(final String propertyName)
	{
		return editable;
	}

	public void setEditable(final boolean editable)
	{
		this.editable = editable;
	}

	@Override
	public List<String> getPropertyNames()
	{
		return propertyNamesRO;
	}

	@Override
	public void commitEdit()
	{
		// Do nothing if this model is already disposed
		if (isDisposed())
		{
			final HUException ex = new HUException("Commiting the edit for a disposed model is not allowed: " + this);
			logger.warn(ex.getLocalizedMessage(), ex);
			return;
		}

		// Do nothing if the model is not editable
		if (!editable)
		{
			return;
		}

		// Iterate all existing properties and ask them to save theirselfs to model.
		for (final IHUProperty property : properties)
		{
			property.saveToModel();
		}

		// task 07600: Save HU in new transaction (note that the HU's old transaction will be allocated back on the HU afterwards)
		HUAndItemsDAO.instance.saveHU(hu);
	}
}

interface IHUProperty
{
	void loadFromModel();

	void saveToModel();

	String getPropertyName();

	String getPropertyDisplayName();

	NamePair getValue();

	void setValue(NamePair value);

	int getDisplayType();

	List<? extends NamePair> getAvailableValues();

	/**
	 * Gets lookup to use in case {@link #getDisplayType()} returns {@link DisplayType#Search}
	 *
	 * @return lookup
	 */
	ITerminalLookup getLookup();

	void onPropertyChanged(final IHUProperty property);
}
