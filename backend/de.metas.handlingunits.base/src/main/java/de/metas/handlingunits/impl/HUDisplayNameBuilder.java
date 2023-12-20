package de.metas.handlingunits.impl;

import de.metas.ad_reference.ADReferenceService;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUDisplayNameBuilder;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.Properties;

public class HUDisplayNameBuilder implements IHUDisplayNameBuilder
{
	// services
	private static final Logger logger = LogManager.getLogger(HUDisplayNameBuilder.class);
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

	public HUDisplayNameBuilder(final I_M_HU hu)
	{
		// NOTE: for convenience, we are accepting null HU,
		// because we want to be able to use this method in places where we don't want to check if HU is null or not (e.g. exception messages).
		_hu = hu;
	}

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

		if (X_M_HU.HUSTATUS_Shipped.equals(getM_HU().getHUStatus()))
		{
			final ADReferenceService adReferenceService = ADReferenceService.get();
			final String destroyedStr = adReferenceService.retrieveListNameTrl(getCtx(), X_M_HU.HUSTATUS_AD_Reference_ID, X_M_HU.HUSTATUS_Shipped);
			displayNameBuilder.append(STR_NewLine).append("(").append(escape(destroyedStr)).append(")");
		}
		//
		// Display "Destroyed" if HU was destroyed
		else if (showIfDestroyed && handlingUnitsBL.isDestroyed(getM_HU()))
		{
			final ADReferenceService adReferenceService = ADReferenceService.get();
			final String destroyedStr = adReferenceService.retrieveListNameTrl(getCtx(), X_M_HU.HUSTATUS_AD_Reference_ID, X_M_HU.HUSTATUS_Destroyed);
			displayNameBuilder.append(STR_NewLine).append("(").append(escape(destroyedStr)).append(")");
		}

		final String displayName = displayNameBuilder.toString();
		return displayName;
	}

	private Properties getCtx()
	{
		if (_hu != null)
		{
			return InterfaceWrapperHelper.getCtx(_hu);
		}
		return Env.getCtx();
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

	@Override
	public final String getPIName()
	{
		final I_M_HU hu = getM_HU();

		final String piNameRaw;
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			piNameRaw = getAggregateHuPiName(hu);
		}
		else
		{
			final I_M_HU_PI pi = Services.get(IHandlingUnitsBL.class).getPI(hu);
			piNameRaw = pi != null ? pi.getName() : "?";
		}

		final String piName = escape(piNameRaw);
		return piName;
	}

	private static String getAggregateHuPiName(final I_M_HU hu)
	{
		// note: if HU is an aggregate HU, then there won't be an NPE here.
		final I_M_HU_PI_Item parentPIItem = Services.get(IHandlingUnitsBL.class).getPIItem(hu.getM_HU_Item_Parent());

		if (parentPIItem == null)
		{
			new HUException("Aggregate HU's parent item has no M_HU_PI_Item; parent-item=" + hu.getM_HU_Item_Parent()).throwIfDeveloperModeOrLogWarningElse(logger);
			return "?";
		}

		HuPackingInstructionsId includedPIId = HuPackingInstructionsId.ofRepoIdOrNull(parentPIItem.getIncluded_HU_PI_ID());
		if (includedPIId == null)
		{
			new HUException("Aggregate HU's parent item has M_HU_PI_Item without an Included_HU_PI; parent-item=" + hu.getM_HU_Item_Parent()).throwIfDeveloperModeOrLogWarningElse(logger);
			return "?";
		}

		final I_M_HU_PI included_HU_PI = Services.get(IHandlingUnitsDAO.class).getPackingInstructionById(includedPIId);

		return included_HU_PI.getName();
	}

	// NOTE: this method can be overridden by extending classes in order to optimize how the HUs are counted.
	@Override
	public int getIncludedHUsCount()
	{
		// NOTE: we need to iterate the HUs and count them (instead of doing a COUNT directly on database),
		// because we rely on HU&Items caching
		// and also because in case of aggregated HUs, we need special handling
		final IncludedHUsCounter includedHUsCounter = new IncludedHUsCounter(getM_HU());

		final HUIterator huIterator = new HUIterator();
		huIterator.setListener(includedHUsCounter.toHUIteratorListener());
		huIterator.setEnableStorageIteration(false);
		huIterator.iterate(getM_HU());

		return includedHUsCounter.getHUsCount();
	}

	protected String escape(final String string)
	{
		return StringUtils.maskHTML(string);
	}

	@Override
	public IHUDisplayNameBuilder setShowIfDestroyed(final boolean showIfDestroyed)
	{
		this.showIfDestroyed = showIfDestroyed;
		return this;
	}

}
