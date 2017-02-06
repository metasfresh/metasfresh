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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Font;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_UOM;
import org.compiere.util.DisplayType;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.handlingunits.IHUCapacityDefinition;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.client.terminal.editor.model.HUKeyVisitorAdapter;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.IHUPOSLayoutConstants;
import de.metas.handlingunits.client.terminal.helper.HUTerminalHelper;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.impl.HUDisplayNameBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IProductStorage;

/* package */class HUKeyNameBuilder extends AbstractHUKeyNameBuilder<HUKey>
{
	// Services
	protected final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	protected final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final DecimalFormat qtyFormat = DisplayType.getNumberFormat(DisplayType.Quantity);

	private final int boxWidthDefault;
	private final int boxWidthPaddingDefault;

	private String lastBuiltName;

	//
	// Pre-build HTML parts
	private String htmlPartHUDisplayName = null;
	private String htmlPartProductStorages = null;
	private String htmlPartWeight = null;

	public HUKeyNameBuilder(final HUKey huKey)
	{
		super(huKey);

		// Layout
		final IHUPOSLayoutConstants layoutConstantsBL = Services.get(IHUPOSLayoutConstants.class);
		final Properties layoutConstants = layoutConstantsBL.getConstants(huKey.getTerminalContext());
		boxWidthDefault = layoutConstantsBL.getConstantAsInt(layoutConstants, IHUPOSLayoutConstants.PROPERTY_HUEditor_KeyFixedWidthForStrings);
		boxWidthPaddingDefault = layoutConstantsBL.getConstantAsInt(layoutConstants, IHUPOSLayoutConstants.PROPERTY_HUEditor_KeyFixedWidthPaddingForStrings);
	}

	@Override
	public String getLastBuiltName()
	{
		return lastBuiltName;
	}

	protected final I_M_HU getM_HU()
	{
		return getKey().getM_HU();
	}

	protected final IAttributeStorage getAttributeSet()
	{
		return getKey().getAttributeSet();
	}

	protected final IWeightable getWeightOrNull()
	{
		return getKey().getWeightOrNull();
	}

	protected final List<IHUProductStorage> getProductStorages()
	{
		final List<IHUProductStorage> productStorages = new ArrayList<>(getKey().getProductStorages());

		Collections.sort(productStorages, IProductStorage.COMPARATOR_ByProductName);

		return productStorages;
	}

	@Cached
	/* package */final I_M_HU_PackingMaterial getM_HU_PackingMaterial()
	{
		final I_M_HU hu = getM_HU();
		final I_M_HU_PackingMaterial pm = handlingUnitsDAO.retrievePackingMaterial(hu);
		return pm;
	}

	@Override
	public void notifyAttributeValueChanged(final IAttributeValue attributeValue)
	{
		if (attributeValue == null)
		{
			return;
		}

		//
		// Check if weight attribute was changed and we have this attribute in our attribute set
		if (getWeightOrNull().isWeightNetAttribute(attributeValue.getM_Attribute()))
		{
			// reset Weight HTML chunk
			htmlPartWeight = null;
		}
	}

	@Override
	public void reset()
	{
		htmlPartHUDisplayName = null;
		htmlPartProductStorages = null;
		htmlPartWeight = null;
	}

	@Override
	public boolean isStale()
	{
		return htmlPartHUDisplayName == null
				|| htmlPartProductStorages == null
				|| htmlPartWeight == null;
	}

	@Override
	public String build()
	{
		final StringBuilder sb = new StringBuilder();

		final String huNameHTML = getHTMLPartHUDisplayName();
		sb.append(huNameHTML);

		//
		// Display CUs inside
		final String productStoragesHTML = getHTMLPartProductStorages();
		sb.append(productStoragesHTML);

		//
		// Display Weight
		final String weightHTML = getHTMLPartWeight();
		sb.append(weightHTML);

		// Center
		sb.insert(0, "<center>").append("</center>");

		lastBuiltName = sb.toString();
		return lastBuiltName;
	}

	protected String getHTMLPartHUDisplayName()
	{
		if (htmlPartHUDisplayName != null)
		{
			return htmlPartHUDisplayName;
		}

		htmlPartHUDisplayName = new HUKeyDisplayNameBuilder()
				.setHUKey(getKey())
				.setShowIncludedHUCount(true)
				.setIncludedHUCountSuffix("TU")
				.setShowHUPINameNextLine(true)
				.setShowIfDestroyed(true) // yes, mainly because in case it happens, 99% it's a bug and we can at least inform the user.
				.build();

		return htmlPartHUDisplayName;
	}

	private String getHTMLPartProductStorages()
	{
		if (htmlPartProductStorages != null)
		{
			return htmlPartProductStorages;
		}

		final StringBuilder sb = new StringBuilder();
		for (final IProductStorage productStorage : getProductStorages())
		{
			final String cuLineHTML = toHTML(productStorage);
			if (Check.isEmpty(cuLineHTML, true))
			{
				continue;
			}

			sb.append("<br>").append(cuLineHTML);
		}

		htmlPartProductStorages = sb.toString();
		return htmlPartProductStorages;
	}

	private String toHTML(final IProductStorage productStorage)
	{
		final HUKey huKey = getKey();

		BigDecimal qty = productStorage.getQty();
		if (qty.signum() == 0)
		{
			return null;
		}

		final I_M_Product product = productStorage.getM_Product();
		final I_C_UOM uom = productStorage.getC_UOM();

		final String qtyStr;
		if (!IHUCapacityDefinition.INFINITY.equals(qty))
		{
			qty = qty.setScale(uom.getStdPrecision(), RoundingMode.HALF_UP);
			qtyStr = qtyFormat.format(qty);
		}
		else if (huKey.isVirtualPI()
				&& huKey.getParent() == huKey.getRoot()) // Stand-alone VPI
		{
			final IHUDocumentLine documentLine = huKey.findDocumentLine();
			qty = documentLine.getQty().setScale(uom.getStdPrecision(), RoundingMode.HALF_UP);
			qtyStr = qtyFormat.format(qty);
		}
		else
		{
			// Silently fallback (** should not happen **): Display infinity symbol
			qtyStr = "\u221e";
		}

		final String cuLineStr = createCULineStr(product.getName());
		final StringBuilder fullName = new StringBuilder(cuLineStr)
				.append("<br>")
				.append("- ").append(qtyStr).append(" x ").append(uom.getUOMSymbol()).append(" -"); // capacity on next line
		return fullName.toString();
	}

	/**
	 * Truncate string if necessary around the product so that it fits in one line.
	 *
	 * Also mask HTML partially inside the product if necessary.
	 *
	 * @param productName
	 * @return truncated CU info string
	 */
	private String createCULineStr(final String productName)
	{
		final HUKey huKey = getKey();

		//
		// FontMetrics are used / created to analyze the string width
		final ITerminalFactory terminalFactory = huKey.getTerminalFactory();
		final Font font = HUTerminalHelper.getDecreasedFontSize(terminalFactory.getDefaultFieldFont()); // 06936: Calculate by decreased font size

		//
		// Actual HUKey / Box width MINUS some pixels so that we get some small border space
		int boxWidth = huKey.getWidth() - boxWidthPaddingDefault;
		if (boxWidth <= 0)
		{
			boxWidth = boxWidthDefault;
		}

		//
		// Do not calculate considering capacity string width if it's on the next line
		String productNameTrunc = productName;

		// 06936: Only start cutting letters if the name without last 3 characters exceeds box width
		// Reason: ... are sometimes shorter and it's acceptable if a maximum of 3 characters "violate" the padding
		if (terminalFactory.computeStringWidth(productNameTrunc.substring(0, 3), font) > boxWidth)
		{
			productNameTrunc = productNameTrunc.substring(0, 3);
			while (boxWidth > 0
					&& terminalFactory.computeStringWidth(productNameTrunc + "...", font) > boxWidth
					&& productNameTrunc.length() > 5) // let's at least keep 5 letters...
			{
				//
				// Keep truncating the product name until it fits within the box
				productNameTrunc = productNameTrunc.substring(0, productNameTrunc.length() - 1).trim();
			}
		}

		//
		// Mask HTML of the product name
		final StringBuilder maskedHTMLCULineBuilder = new StringBuilder()
				.append(Util.maskHTML(productNameTrunc));

		if (!productName.equals(productNameTrunc))
		{
			maskedHTMLCULineBuilder.append("...");
		}

		//
		// Return the trimmed built string
		return maskedHTMLCULineBuilder.toString().trim();
	}

	private String getHTMLPartWeight()
	{
		if (htmlPartWeight != null)
		{
			return htmlPartWeight;
		}

		final StringBuilder sb = new StringBuilder();
		final String weightStr = buildWeightString();
		if (!Check.isEmpty(weightStr, true))
		{
			sb.append("<br>").append(weightStr.trim());
		}

		htmlPartWeight = sb.toString();
		return htmlPartWeight;
	}

	private String buildWeightString()
	{
		final BigDecimal weightActual = getWeightActual();
		final I_M_HU_PackingMaterial pm = getM_HU_PackingMaterial();

		final boolean isActualWeight;
		final String uomType;
		final I_C_UOM uom;
		BigDecimal weightToUse = null;
		if (weightActual != null && weightActual.signum() != 0)
		{
			weightToUse = weightActual;

			uom = getKey().getStorageUOMOrNull();
			if (uom == null)
			{
				return null; // No UOM / Incompatible storage UOMTypes (this is ok, the attribute should be R/O in that case)
			}
			uomType = uom.getUOMType();

			if (!X_C_UOM.UOMTYPE_Weigth.equals(uomType))
			{
				return null; // Non-Weight UOM
			}

			isActualWeight = true;
		}
		else if (pm != null)
		{
			weightToUse = pm.getAllowedPackingWeight();
			if (weightToUse == null || weightToUse.signum() != 0)
			{
				return null; // Invalid weight
			}

			uom = pm == null ? null : pm.getC_UOM_Weight();
			if (uom == null)
			{
				return null; // No PM UOM
			}

			isActualWeight = false;
		}
		else
		{
			return null; // No Actual Weight provided, no Packing Material
		}

		weightToUse = weightToUse.setScale(uom.getStdPrecision(), RoundingMode.HALF_UP);
		final String uomSymbol = uom.getUOMSymbol();

		final StringBuilder pmStr = new StringBuilder();
		pmStr.append(qtyFormat.format(weightToUse)).append(" ").append(uomSymbol);

		if (!isActualWeight)
		{
			pmStr.insert(0, "(").append(")"); // Surround with parentheses if it's the PM weight
		}

		return Util.maskHTML(pmStr.toString());
	}

	/**
	 * @return actual HU's weight or <code>null</code> if not available
	 */
	private BigDecimal getWeightActual()
	{
		return getKey().getWeightActualOrNull();
	}

	@Override
	public void notifyChildChanged(final IHUKey childKey)
	{
		htmlPartHUDisplayName = null; // because contains the count TUs
		htmlPartWeight = null; // because weights can be also affected
		htmlPartProductStorages = null; // It could be that storages have changed
	}

	/**
	 * Extension of {@link HUDisplayNameBuilder} which basically does exactly the same job,
	 * but instead of using the {@link I_M_HU} tree structure, it is using {@link IHUKey} tree structure.
	 * 
	 * @author tsa
	 */
	private static class HUKeyDisplayNameBuilder extends HUDisplayNameBuilder
	{
		private HUKey _huKey = null;

		public HUKeyDisplayNameBuilder setHUKey(final HUKey huKey)
		{
			_huKey = huKey;
			setM_HU(huKey == null ? null : huKey.getM_HU());

			return this;
		}

		protected final HUKey getHUKey()
		{
			Check.assumeNotNull(_huKey, "_huKey not null");
			return _huKey;
		}

		@Override
		public int getIncludedHUsCount()
		{
			final HUKey huKey = getHUKey();

			// TODO: optimization: if not LU and countVHUs=false => return 0;
			// TODO: optimization: if children are not loaded then i think it's better to have and call a DAO method which is counting the TUs directly on database

			final boolean countVHUs = false;
			final IMutable<AtomicInteger> counter = new Mutable<>(new AtomicInteger(0));

			huKey.iterate(new HUKeyVisitorAdapter()
			{
				@Override
				public VisitResult beforeVisit(final IHUKey currentKey)
				{
					final HUKey currentHUKey = HUKey.castIfPossible(currentKey);
					if (currentHUKey == null)
					{
						// we are on an non HUKey node ... keep searching
						return VisitResult.CONTINUE;
					}
					else if (currentHUKey == huKey)
					{
						if (!currentHUKey.isAggregateHU())
						{
							// don't count current HU if it's not an aggregate HU
							return VisitResult.CONTINUE;
						}
						counter.getValue().addAndGet(currentHUKey.getAggregatedHUCount());
					}
					else
					{
						if (currentHUKey.isAggregateHU())
						{
							counter.getValue().addAndGet(currentHUKey.getAggregatedHUCount());
						}
						else
						{
							if (!countVHUs && currentHUKey.isVirtualPI())
							{
								// skip virtual HUs; note that also aggregate HUs are "virtual", but that case is handled not here
								return VisitResult.CONTINUE;
							}
							counter.getValue().incrementAndGet();
						}
					}

					// we are counting only first level => so skip downstream
					return VisitResult.SKIP_DOWNSTREAM;
				}
			});

			return counter.getValue().get();
		}
	}
}
