package de.metas.handlingunits.impl;

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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.handlingunits.IHUDisplayNameBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.logging.LogManager;

public class HUDisplayNameBuilder implements IHUDisplayNameBuilder
{
	// services
	private static final transient Logger logger = LogManager.getLogger(HUDisplayNameBuilder.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	// Options
	private boolean showIncludedHUCount = false;
	private String includedHUCountSuffix = null;
	private boolean showHUPINameNextLine = false;
	private boolean showIfDestroyed = false; // legacy-default=false

	// Formatting
	private final String STR_NewLine = "<br>";

	/** HU */
	private I_M_HU _hu;

	@Override
	public String build()
	{
		// guard against null
		// NOTE: for convenience, we are not throwing an exception here but we just return ""
		// because we want to be able to use this method in places where we don't want to check if HU is null or not (e.g. exception messages).
		if (!isHUSet())
		{
			return "";
		}

		//
		// Get HU Value
		final String huValue = getHUValue();

		//
		// Get PI Name
		final String piName = getPIName();

		final StringBuilder displayNameBuilder = new StringBuilder(huValue);

		//
		// 07618: Display included HU count next to the HU Value if needed
		if (isShowIncludedHUCount())
		{
			final int includedHUCount = getIncludedHUsCount();
			if (includedHUCount > 0)
			{
				displayNameBuilder.append(" - ").append(includedHUCount);
				final String includedHUCountSuffix = getIncludedHUCountSuffix();
				if (includedHUCountSuffix != null)
				{
					displayNameBuilder.append(" ").append(includedHUCountSuffix.trim());
				}
			}
		}

		//
		// Decide separator
		if (isShowHUPINameNextLine())
		{
			displayNameBuilder.append(STR_NewLine);
		}
		else
		{
			displayNameBuilder.append(" ");
		}
		displayNameBuilder.append("(").append(piName).append(")");

		//
		// Display "Destroyed" if HU was destroyed
		if (showIfDestroyed && handlingUnitsBL.isDestroyed(getM_HU()))
		{
			final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
			final String destroyedStr = adReferenceDAO.retrieveListNameTrl(getCtx(), X_M_HU.HUSTATUS_AD_Reference_ID, X_M_HU.HUSTATUS_Destroyed);
			displayNameBuilder.append(STR_NewLine).append("(").append(escape(destroyedStr)).append(")");
		}

		final String displayName = displayNameBuilder.toString();
		return displayName;
	}

	private final Properties getCtx()
	{
		if (_hu != null)
		{
			return InterfaceWrapperHelper.getCtx(_hu);
		}
		return Env.getCtx();
	}

	protected IHUDisplayNameBuilder setM_HU(final I_M_HU hu)
	{
		_hu = hu;
		return this;
	}

	protected boolean isHUSet()
	{
		return _hu != null;
	}

	protected I_M_HU getM_HU()
	{
		Check.assumeNotNull(_hu, "_hu not null");
		return _hu;
	}

	@Override
	public boolean isShowIncludedHUCount()
	{
		return showIncludedHUCount;
	}

	@Override
	public IHUDisplayNameBuilder setShowIncludedHUCount(final boolean showIncludedHUCount)
	{
		this.showIncludedHUCount = showIncludedHUCount;
		return this;
	}

	@Override
	public String getIncludedHUCountSuffix()
	{
		return includedHUCountSuffix;
	}

	@Override
	public IHUDisplayNameBuilder setIncludedHUCountSuffix(final String includedHUCountSuffix)
	{
		this.includedHUCountSuffix = includedHUCountSuffix;
		return this;
	}

	@Override
	public boolean isShowHUPINameNextLine()
	{
		return showHUPINameNextLine;
	}

	@Override
	public IHUDisplayNameBuilder setShowHUPINameNextLine(final boolean showHUPINameNextLine)
	{
		this.showHUPINameNextLine = showHUPINameNextLine;
		return this;
	}

	protected String getHUValue()
	{
		final I_M_HU hu = getM_HU();

		String huValue = hu.getValue();
		if (Check.isEmpty(huValue, true))
		{
			huValue = String.valueOf(hu.getM_HU_ID());
		}
		return huValue;
	}

	/**
	 * Return either the name of this instance's {@link #getM_HU()}'s {@code M_HU_PI_Version}
	 * or - if it's an aggregate HU - the name of the PI that is represented.
	 * 
	 * @return
	 */
	protected String getPIName()
	{
		final I_M_HU hu = getM_HU();

		final String piNameRaw;
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			piNameRaw = getAggregateHuPiName(hu);
		}
		else
		{
			final I_M_HU_PI_Version piVersion = hu.getM_HU_PI_Version();
			if (piVersion == null || piVersion.getM_HU_PI_Version_ID() <= 0)
			{
				piNameRaw = "?";
			}
			else
			{
				piNameRaw = piVersion.getM_HU_PI().getName();
			}
		}

		String piName = escape(piNameRaw);
		return piName;
	}

	private String getAggregateHuPiName(final I_M_HU hu)
	{
		// note: if hu is an aggregate HU, then there won't be an NPE here.
		final I_M_HU_PI_Item parentPIItem = hu.getM_HU_Item_Parent().getM_HU_PI_Item();

		if (parentPIItem == null)
		{
			new HUException("Aggregate HU's parent item has no M_HU_PI_Item; parent-item=" + hu.getM_HU_Item_Parent()).throwIfDeveloperModeOrLogWarningElse(logger);
			return "?";
		}
		final I_M_HU_PI included_HU_PI = parentPIItem.getIncluded_HU_PI();
		if (included_HU_PI == null)
		{
			new HUException("Aggregate HU's parent item has M_HU_PI_Item without an Included_HU_PI; parent-item=" + hu.getM_HU_Item_Parent()).throwIfDeveloperModeOrLogWarningElse(logger);
			return "?";
		}

		return included_HU_PI.getName();
	}

	/**
	 * Gets included HUs count for current HU.
	 *
	 * NOTE: this method can be overridden by extending classes in order to optimize how the HUs are counted.
	 *
	 * @return included HUs count for {@link #getM_HU()}
	 */
	protected int getIncludedHUsCount()
	{
		final I_M_HU hu = getM_HU();

		// NOTE: we need to retrieve the HUs and count them (instead of doing a COUNT directly on database),
		// because we rely on HU&Items caching
		final List<I_M_HU> includedHUs = handlingUnitsDAO.retrieveIncludedHUs(hu);
		int includedHUCount = includedHUs.size();
		if (includedHUCount <= 0)
		{
			return 0;
		}

		// Exclude VHUs from counting
		for (final I_M_HU includedHU : includedHUs)
		{
			if (handlingUnitsBL.isVirtual(includedHU))
			{
				includedHUCount--;
			}
		}

		return includedHUCount;
	}

	protected String escape(final String string)
	{
		return Util.maskHTML(string);
	}

	@Override
	public IHUDisplayNameBuilder setShowIfDestroyed(boolean showIfDestroyed)
	{
		this.showIfDestroyed = showIfDestroyed;
		return this;
	}
}
