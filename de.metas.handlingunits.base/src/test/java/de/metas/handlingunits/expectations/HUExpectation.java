package de.metas.handlingunits.expectations;

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
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.junit.Assert;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;

/**
 * Used to specify expectations related to a single HU. Hint: use within the greater framework of {@link HUsExpectation}
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <ParentExpectationType>
 */
public class HUExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	public static final HUExpectation<Object> newExpectation()
	{
		return new HUExpectation<>();
	}

	private I_M_HU_PI _huPI = null;
	private String _instanceName;
	private String _huStatus = null;
	private boolean _huStatusSet = false;
	private I_M_Locator _locator;
	private boolean _locatorSet;

	private I_C_BPartner _bpartner = null;
	private I_C_BPartner_Location _bpartnerLocation = null;

	private List<HUItemExpectation<HUExpectation<ParentExpectationType>>> huItemExpectations = null;

	/** Capture: HU */
	private IMutable<I_M_HU> _huToSetRef;

	/** HU attributes expectations */
	private HUAttributeExpectations<HUExpectation<ParentExpectationType>> attributesExpectations = null;

	public HUExpectation()
	{
		this(null);
		setContext(PlainContextAware.newOutOfTrx(Env.getCtx()));
	}

	public HUExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public HUExpectation<ParentExpectationType> assertExpected(final String message, final I_M_HU hu)
	{
		Assert.assertNotNull("hu not null", hu);

		final String prefix = (message == null ? "" : message)
				+ "\nHU: " + hu
				+ "\n"
				+ "\nInvalid ";

		if (_huStatusSet)
		{
			Assert.assertEquals(prefix + "HUStatus", _huStatus, hu.getHUStatus());
		}

		if (_locatorSet)
		{
			assertModelEquals(prefix + "M_Locator", _locator, hu.getM_Locator());
		}

		if (_huPI != null)
		{
			final I_M_HU_PI actual_huPI = Services.get(IHandlingUnitsBL.class).getPI(hu);
			assertModelEquals(prefix + "HU PI", _huPI, actual_huPI);
		}

		if (huItemExpectations != null)
		{
			final List<I_M_HU_Item> huItems = handlingUnitsDAO.retrieveItems(hu);
			assertExpected(prefix + " HU Items", huItems);
		}

		if (_bpartner != null)
		{
			Assert.assertEquals(prefix + " C_BPartner", _bpartner, hu.getC_BPartner());
		}
		if (_bpartnerLocation != null)
		{
			Assert.assertEquals(prefix + " C_BPartner_Location", _bpartnerLocation, hu.getC_BPartner_Location());
		}

		//
		// Set capture
		if (_huToSetRef != null)
		{
			_huToSetRef.setValue(hu);
		}

		if (attributesExpectations != null)
		{
			attributesExpectations.assertExpected(prefix + " Attributes", hu);
		}

		return this;
	}

	private HUExpectation<ParentExpectationType> assertExpected(final String message, final List<I_M_HU_Item> huItems)
	{
		final int count = huItems.size();
		final int expectedCount = huItemExpectations.size();

		Assert.assertEquals(message + " HU Items count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final I_M_HU_Item huItem = huItems.get(i);

			final String prefix = (message == null ? "" : message)
					+ "\n HU Item Index: " + (i + 1) + "/" + count;

			huItemExpectations.get(i).assertExpected(prefix, huItem);
		}

		return this;
	}

	public I_M_HU createHU()
	{
		final I_M_HU_Item parentHUItem = null;
		return createHU(parentHUItem);
	}

	public I_M_HU createHU(final I_M_HU_Item parentHUItem)
	{
		final IMutable<I_M_HU> huRef = new Mutable<>();
		Services.get(ITrxManager.class).run(getContext().getTrxName(), new TrxRunnableAdapter()
		{

			@Override
			public void run(final String localTrxName)
			{
				final I_M_HU hu = createHU_InTrx(parentHUItem);
				huRef.setValue(hu);
			}
		});

		return huRef.getValue();
	}

	private final I_M_HU createHU_InTrx(final I_M_HU_Item parentHUItem)
	{
		//
		// Get HUStatus to set
		final String huStatus;
		if (isHUStatusSet())
		{
			huStatus = getHUStatus();
		}
		else if (parentHUItem != null)
		{
			huStatus = parentHUItem.getM_HU().getHUStatus();
		}
		else
		{
			huStatus = null;
		}

		//
		// Get M_Locator to set
		final I_M_Locator locator;
		if (_locatorSet)
		{
			locator = _locator;
		}
		else if (parentHUItem != null)
		{
			locator = parentHUItem.getM_HU().getM_Locator();
		}
		else
		{
			locator = null;
		}

		//
		// Create and save HU
		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, getContext());
		POJOWrapper.setInstanceName(hu, _instanceName);
		hu.setM_HU_PI_Version(getM_HU_PI_Version());
		hu.setM_HU_Item_Parent(parentHUItem);
		hu.setC_BPartner(_bpartner);
		hu.setC_BPartner_Location(_bpartnerLocation);
		hu.setHUStatus(huStatus);
		hu.setM_Locator(locator);
		InterfaceWrapperHelper.save(hu);

		//
		// Create HU Attributes
		if (attributesExpectations != null)
		{
			attributesExpectations.createHUAttributes(hu);
		}

		//
		// Create HU Items
		if (huItemExpectations != null)
		{
			for (final HUItemExpectation<HUExpectation<ParentExpectationType>> huItemExpectation : huItemExpectations)
			{
				huItemExpectation.createHUItem(hu);
			}
		}

		// Capture the HU if required
		if (_huToSetRef != null)
		{
			_huToSetRef.setValue(hu);
		}

		return hu;
	}

	/**
	 * Sets instance name to be set when we create the HU.
	 * 
	 * @param instanceName
	 */
	public HUExpectation<ParentExpectationType> instanceName(final String instanceName)
	{
		this._instanceName = instanceName;
		return this;
	}

	public HUExpectation<ParentExpectationType> huStatus(final String huStatus)
	{
		this._huStatus = huStatus;
		this._huStatusSet = true;
		return this;
	}

	public HUExpectation<ParentExpectationType> locator(I_M_Locator locator)
	{
		this._locator = locator;
		this._locatorSet = true;
		return this;
	}

	public HUExpectation<ParentExpectationType> bPartner(final I_C_BPartner bpartner)
	{
		this._bpartner = bpartner;
		return this;
	}

	public HUExpectation<ParentExpectationType> bPartnerLocation(final I_C_BPartner_Location bpartnerLocation)
	{
		this._bpartnerLocation = bpartnerLocation;
		return this;
	}

	public String getHUStatus()
	{
		return _huStatus;
	}

	private final boolean isHUStatusSet()
	{
		return _huStatusSet;
	}

	/**
	 * Expects the HU to have a {@link I_M_HU_PI_Version} that references the given <code>huPI</code>.
	 * 
	 * @param huPI
	 * @return
	 */
	public HUExpectation<ParentExpectationType> huPI(final I_M_HU_PI huPI)
	{
		this._huPI = huPI;
		return this;
	}

	public I_M_HU_PI getM_HU_PI()
	{
		return _huPI;
	}

	private I_M_HU_PI_Version getM_HU_PI_Version()
	{
		final I_M_HU_PI pi = getM_HU_PI();
		return handlingUnitsDAO.retrievePICurrentVersion(pi);
	}

	public HUItemExpectation<HUExpectation<ParentExpectationType>> newHUItemExpectation()
	{
		final HUItemExpectation<HUExpectation<ParentExpectationType>> expectation = new HUItemExpectation<>(this);
		if (huItemExpectations == null)
		{
			huItemExpectations = new ArrayList<>();
		}
		huItemExpectations.add(expectation);
		return expectation;
	}

	public HUItemExpectation<HUExpectation<ParentExpectationType>> newHUItemExpectation(final I_M_HU_PI_Item piItem)
	{
		return newHUItemExpectation()
				.huPIItem(piItem);
	}

	/**
	 * Create and return an expectation for a VHU item. This is usually within a {@link HUItemExpectation#newIncludedVirtualHU()}.
	 * 
	 * @return
	 */
	public HUItemExpectation<HUExpectation<ParentExpectationType>> newVirtualHUItemExpectation()
	{
		final I_M_HU_PI_Item virtualPIItem = handlingUnitsDAO.retrieveVirtualPIItem(Env.getCtx());
		return newHUItemExpectation(virtualPIItem);
	}

	/**
	 * Gets {@link HUItemExpectation} for given <code>piItem</code>.
	 *
	 * @param piItem
	 * @return
	 * @return {@link HUItemExpectation}; never return null
	 */
	public HUItemExpectation<HUExpectation<ParentExpectationType>> huItemExpectation(final I_M_HU_PI_Item piItem)
	{
		Check.assumeNotNull(piItem, "piItem not null");
		Check.assumeNotNull(huItemExpectations, "huItemExpectations not null");

		final List<HUItemExpectation<HUExpectation<ParentExpectationType>>> result = new ArrayList<>();
		for (final HUItemExpectation<HUExpectation<ParentExpectationType>> huItemExpectation : huItemExpectations)
		{
			final I_M_HU_PI_Item current_piItem = huItemExpectation.getM_HU_PI_Item();
			if (current_piItem == null)
			{
				continue;
			}

			if (current_piItem.getM_HU_PI_Item_ID() != piItem.getM_HU_PI_Item_ID())
			{
				continue;
			}

			result.add(huItemExpectation);
		}

		if (result.isEmpty())
		{
			throw new IllegalArgumentException("No HU Item Expectation found for " + piItem);
		}
		else if (result.size() > 1)
		{
			throw new IllegalArgumentException("More then one HU Item Expectation found for " + piItem
					+ "\n\n" + result);
		}

		return result.get(0);
	}

	/**
	 * Capture the given referenced {@link I_M_HU}. While we are:
	 * <ul>
	 * <li>creating the HU: invoke {@link #createHU(I_M_HU_Item)} to generate it
	 * <li>testing: invoke {@link #assertExpected(String, I_M_HU)} to verify that the HU is as expected
	 * </ul>
	 *
	 * @param huToSetRef
	 * @return this
	 */
	public HUExpectation<ParentExpectationType> capture(final IMutable<I_M_HU> huToSetRef)
	{
		this._huToSetRef = huToSetRef;
		return this;
	}

	public I_M_HU getCapturedHU()
	{
		Check.assumeNotNull(_huToSetRef, "Expectation {} was not configured to capture HU", this);
		return _huToSetRef.getValue();
	}

	public HUAttributeExpectations<HUExpectation<ParentExpectationType>> attributesExpectations()
	{
		if (attributesExpectations == null)
		{
			attributesExpectations = new HUAttributeExpectations<>(this);
		}
		return attributesExpectations;
	}
}
