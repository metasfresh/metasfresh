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
import java.util.Optional;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.junit.Assert;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

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

	public static final HUExpectation<Object> newVirtualHU()
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU_PI virtualPI = handlingUnitsDAO.retrieveVirtualPI(Env.getCtx());
		return new HUExpectation<>()
				.huPI(virtualPI);
	}

	private I_M_HU_PI _huPI = null;
	private String _instanceName;
	private Optional<String> huStatus = null;
	private Optional<I_M_Locator> locator;

	private I_C_BPartner _bpartner = null;
	private I_C_BPartner_Location _bpartnerLocation = null;

	private List<HUStorageExpectation<Object>> huStorageExpectations = null;
	private List<HUItemExpectation<?>> huItemExpectations = null;
	private HUAttributeExpectations<HUExpectation<ParentExpectationType>> attributesExpectations = null;

	private IMutable<I_M_HU> huCaptor;

	private HUExpectation()
	{
		this(null);
		setContext(PlainContextAware.newOutOfTrx(Env.getCtx()));
	}

	HUExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public HUExpectation<ParentExpectationType> assertExpected(final String message, @NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsDAO.getById(huId);
		return assertExpected(message, hu);
	}

	public HUExpectation<ParentExpectationType> assertExpected(final String message, @NonNull final I_M_HU hu)
	{
		final String prefix = (message == null ? "" : message)
				+ "\nHU: " + hu
				+ "\n"
				+ "\nInvalid ";

		if (huStatus != null)
		{
			Assert.assertEquals(prefix + "HUStatus", huStatus.orElse(null), hu.getHUStatus());
		}

		if (locator != null)
		{
			assertModelEquals(prefix + "M_Locator", locator.orElse(null), IHandlingUnitsBL.extractLocatorOrNull(hu));
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
			Assert.assertEquals(prefix + " C_BPartner", _bpartner, IHandlingUnitsBL.extractBPartnerOrNull(hu));
		}
		if (_bpartnerLocation != null)
		{
			Assert.assertEquals(prefix + " C_BPartner_Location", _bpartnerLocation, IHandlingUnitsBL.extractBPartnerLocationOrNull(hu));
		}

		//
		// Set capture
		if (huCaptor != null)
		{
			huCaptor.setValue(hu);
		}

		if (huStorageExpectations != null)
		{
			for (final HUStorageExpectation<Object> huStorageExpectation : huStorageExpectations)
			{
				huStorageExpectation.assertExpected(hu);
			}
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
		if (this.huStatus != null)
		{
			huStatus = this.huStatus.orElse(null);
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
		if (this.locator != null)
		{
			locator = this.locator.orElse(null);
		}
		else if (parentHUItem != null)
		{
			locator = IHandlingUnitsBL.extractLocatorOrNull(parentHUItem.getM_HU());
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
		hu.setC_BPartner_ID(_bpartner != null ? _bpartner.getC_BPartner_ID() : -1);
		hu.setC_BPartner_Location_ID(_bpartnerLocation != null ? _bpartnerLocation.getC_BPartner_Location_ID() : -1);
		hu.setHUStatus(huStatus);
		hu.setM_Locator_ID(locator != null ? locator.getM_Locator_ID() : -1);
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
			for (final HUItemExpectation<?> huItemExpectation : huItemExpectations)
			{
				huItemExpectation.createHUItem(hu);
			}
		}

		// Capture the HU if required
		if (huCaptor != null)
		{
			huCaptor.setValue(hu);
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
		this.huStatus = Optional.ofNullable(huStatus);
		return this;
	}

	public HUExpectation<ParentExpectationType> locator(I_M_Locator locator)
	{
		this.locator = Optional.ofNullable(locator);
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

	public HUItemExpectation<HUExpectation<ParentExpectationType>> item()
	{
		final HUItemExpectation<HUExpectation<ParentExpectationType>> expectation = new HUItemExpectation<>(this);
		item(expectation);
		return expectation;
	}

	public HUExpectation<ParentExpectationType> item(@NonNull final HUItemExpectation<?> expectation)
	{
		if (huItemExpectations == null)
		{
			huItemExpectations = new ArrayList<>();
		}
		huItemExpectations.add(expectation);
		return this;
	}

	public HUItemExpectation<HUExpectation<ParentExpectationType>> item(final I_M_HU_PI_Item piItem)
	{
		return item()
				.huPIItem(piItem);
	}

	/**
	 * Create and return an expectation for a VHU item. This is usually within a {@link HUItemExpectation#includedVirtualHU()}.
	 * 
	 * @return
	 */
	public HUItemExpectation<HUExpectation<ParentExpectationType>> virtualPIItem()
	{
		final I_M_HU_PI_Item virtualPIItem = handlingUnitsDAO.retrieveVirtualPIItem(Env.getCtx());
		return item(virtualPIItem);
	}

	/**
	 * Gets {@link HUItemExpectation} for given <code>piItem</code>.
	 *
	 * @param piItem
	 * @return
	 * @return {@link HUItemExpectation}; never return null
	 */
	public HUItemExpectation<HUExpectation<ParentExpectationType>> existingItem(@NonNull final I_M_HU_PI_Item piItem)
	{
		Check.assumeNotNull(huItemExpectations, "huItemExpectations not null");

		final List<HUItemExpectation<?>> matchingExpectations = new ArrayList<>();
		for (final HUItemExpectation<?> huItemExpectation : huItemExpectations)
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

			matchingExpectations.add(huItemExpectation);
		}

		if (matchingExpectations.isEmpty())
		{
			throw new IllegalArgumentException("No HU Item Expectation found for " + piItem);
		}
		else if (matchingExpectations.size() > 1)
		{
			throw new IllegalArgumentException("More then one HU Item Expectation found for " + piItem
					+ "\n\n" + matchingExpectations);
		}
		else
		{
			@SuppressWarnings("unchecked")
			final HUItemExpectation<HUExpectation<ParentExpectationType>> expectation = (HUItemExpectation<HUExpectation<ParentExpectationType>>)matchingExpectations.get(0);
			return expectation;
		}
	}

	/**
	 * Capture the given referenced {@link I_M_HU}. While we are:
	 * <ul>
	 * <li>creating the HU: invoke {@link #createHU(I_M_HU_Item)} to generate it
	 * <li>testing: invoke {@link #assertExpected(String, I_M_HU)} to verify that the HU is as expected
	 * </ul>
	 */
	public HUExpectation<ParentExpectationType> capture(final IMutable<I_M_HU> huCaptor)
	{
		this.huCaptor = huCaptor;
		return this;
	}

	public I_M_HU getCapturedHU()
	{
		Check.assumeNotNull(huCaptor, "Expectation {} was not configured to capture HU", this);
		return huCaptor.getValue();
	}

	public HUAttributeExpectations<HUExpectation<ParentExpectationType>> attributesExpectations()
	{
		if (attributesExpectations == null)
		{
			attributesExpectations = new HUAttributeExpectations<>(this);
		}
		return attributesExpectations;
	}

	public HUExpectation<ParentExpectationType> storage(@NonNull final HUStorageExpectation<Object> expectation)
	{
		if (huStorageExpectations == null)
		{
			huStorageExpectations = new ArrayList<>();
		}

		huStorageExpectations.add(expectation);

		return this;
	}
}
