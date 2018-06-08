package de.metas.handlingunits.impl;

import java.util.Properties;

import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUDisplayNameBuilder;
import de.metas.handlingunits.IHUIteratorListener;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.logging.LogManager;

public class HUDisplayNameBuilder implements IHUDisplayNameBuilder
{
	// services
	private static final transient Logger logger = LogManager.getLogger(HUDisplayNameBuilder.class);
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

		if(X_M_HU.HUSTATUS_Shipped.equals(getM_HU().getHUStatus()))
		{
			final IADReferenceDAO adReferenceDAO = Services.get(IADReferenceDAO.class);
			final String destroyedStr = adReferenceDAO.retrieveListNameTrl(getCtx(), X_M_HU.HUSTATUS_AD_Reference_ID, X_M_HU.HUSTATUS_Shipped);
			displayNameBuilder.append(STR_NewLine).append("(").append(escape(destroyedStr)).append(")");
		}
		//
		// Display "Destroyed" if HU was destroyed
		else if (showIfDestroyed && handlingUnitsBL.isDestroyed(getM_HU()))
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
		final I_M_HU_PI included_HU_PI = parentPIItem.getIncluded_HU_PI();
		if (included_HU_PI == null)
		{
			new HUException("Aggregate HU's parent item has M_HU_PI_Item without an Included_HU_PI; parent-item=" + hu.getM_HU_Item_Parent()).throwIfDeveloperModeOrLogWarningElse(logger);
			return "?";
		}

		return included_HU_PI.getName();
	}

	// NOTE: this method can be overridden by extending classes in order to optimize how the HUs are counted. 
	@Override
	public int getIncludedHUsCount()
	{
		final I_M_HU hu = getM_HU();

		// NOTE: we need to iterate the HUs and count them (instead of doing a COUNT directly on database),
		// because we rely on HU&Items caching
		// and also because in case of aggregated HUs, we need special handling

		final IncludedHUsCounter includedHUsCounter = new IncludedHUsCounter(hu, false); // countVHUs=false
		
		final HUIterator huIterator = new HUIterator();
		huIterator.setListener(includedHUsCounter.toHUIteratorListener());
		huIterator.setEnableStorageIteration(false);
		huIterator.iterate(hu);

		return includedHUsCounter.getHUsCount();
	}

	protected String escape(final String string)
	{
		return Util.maskHTML(string);
	}

	@Override
	public IHUDisplayNameBuilder setShowIfDestroyed(final boolean showIfDestroyed)
	{
		this.showIfDestroyed = showIfDestroyed;
		return this;
	}

	/**
	 * Counts included HUs (abstract class).
	 * 
	 * Extending classes shall implement the abstract methods from here, which are some basic operators on <code>HUHolderType</code>.
	 *
	 * @param <HUHolderType> class which contains the HU
	 */
	public static abstract class AbstractIncludedHUsCounter<HUHolderType>
	{
		private final HUHolderType _rootHUObj;
		private final boolean _countVHUs;

		private int _counter = 0;

		protected AbstractIncludedHUsCounter(final HUHolderType rootHUObj, final boolean countVHUs)
		{
			this._rootHUObj = rootHUObj;
			this._countVHUs = countVHUs;
		}

		public final IHUIteratorListener.Result accept(final HUHolderType currentHUObj)
		{
			// In case our holder does not contain a proper HU, we shall continue searching
			if (currentHUObj == null)
			{
				return IHUIteratorListener.Result.CONTINUE;
			}
			
			if (isRootHUKey(currentHUObj))
			{
				if (!isAggregatedHU(currentHUObj))
				{
					// don't count current HU if it's not an aggregate HU
					return IHUIteratorListener.Result.CONTINUE;
				}

				incrementCounter(getAggregatedHUsCount(currentHUObj));
			}
			else
			{
				if (isAggregatedHU(currentHUObj))
				{
					incrementCounter(getAggregatedHUsCount(currentHUObj));
				}
				else
				{
					if (!isCountVHUs() && isVirtualPI(currentHUObj))
					{
						// skip virtual HUs; note that also aggregate HUs are "virtual", but that case is handled not here
						return IHUIteratorListener.Result.CONTINUE;
					}
					incrementCounter(1);
				}
			}

			// we are counting only first level => so skip downstream
			return IHUIteratorListener.Result.SKIP_DOWNSTREAM;
		}

		public final int getHUsCount()
		{
			return _counter;
		}

		protected final boolean isCountVHUs()
		{
			return _countVHUs;
		}

		private final void incrementCounter(final int increment)
		{
			Check.assume(increment >= 0, "increment >= 0 but it was {}", increment);
			_counter += increment;
		}

		protected final boolean isRootHUKey(final HUHolderType huObj)
		{
			return huObj == _rootHUObj;
		}

		/** @return true if the HU is an aggregated HU */
		protected abstract boolean isAggregatedHU(final HUHolderType huObj);

		/**
		 * Called in case the HU is an aggregated HU and it shall return how many HUs are really contained.
		 *
		 * @return how many HUs are aggregated
		 */
		protected abstract int getAggregatedHUsCount(final HUHolderType huObj);

		/** @return true if the HU is a virtual one */
		protected abstract boolean isVirtualPI(final HUHolderType huObj);
	}

	/**
	 * An {@link AbstractIncludedHUsCounter} implementation which works directly on I_M_HU instances
	 */
	private static class IncludedHUsCounter extends AbstractIncludedHUsCounter<I_M_HU>
	{
		private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		private IncludedHUsCounter(final I_M_HU rootHU, final boolean countVHUs)
		{
			super(rootHU, countVHUs);
		}

		@Override
		protected boolean isAggregatedHU(final I_M_HU hu)
		{
			return handlingUnitsBL.isAggregateHU(hu);
		}

		@Override
		protected int getAggregatedHUsCount(final I_M_HU hu)
		{
			final I_M_HU_Item parentHUItem = hu.getM_HU_Item_Parent();
			if (parentHUItem == null)
			{
				// shall not happen
				return 0;
			}
			return parentHUItem.getQty().intValueExact();
		}

		@Override
		protected boolean isVirtualPI(final I_M_HU huObj)
		{
			return handlingUnitsBL.isVirtual(huObj);
		}

		private IHUIteratorListener toHUIteratorListener()
		{
			return new HUIteratorListenerAdapter()
			{
				@Override
				public String toString()
				{
					return MoreObjects.toStringHelper(this).addValue(IncludedHUsCounter.this).toString();
				}

				@Override
				public Result beforeHU(final IMutable<I_M_HU> hu)
				{
					return IncludedHUsCounter.this.accept(hu.getValue());
				}
			};
		}
	}

}
