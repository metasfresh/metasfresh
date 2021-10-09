package org.adempiere.mm.attributes.spi.impl;

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

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_Attribute;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

/**
 * Common super class of all weight related callouts.
 */
/* package */abstract class AbstractWeightAttributeValueCallout implements IAttributeValueGenerator,
		IAttributeValueCallout
{
	private static final String SYSCONFIG_IsNonWeightableReadOnlyUIOverride = "org.adempiere.mm.attributes.spi.impl.AbstractWeightAttributeValueCallout.IsNonWeightableReadOnlyUIOverride";

	//
	// Logger
	protected final transient Logger logger = LogManager.getLogger(getClass());

	// Services
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	final IHUReceiptScheduleBL huReceiptScheduleBL = Services.get(IHUReceiptScheduleBL.class);

	final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	protected AbstractWeightAttributeValueCallout()
	{
		super();
	}

	/**
	 * Will first call {@link #isExecuteCallout(IAttributeValueContext, IAttributeSet, I_M_Attribute, Object, Object)} to find out if this callout should do <i>anything at all</i>, and if that method
	 * returns <code>true</code>, will call {@link #onValueChanged0(IAttributeValueContext, IAttributeSet, I_M_Attribute, Object, Object)}. Both these methods are abstract.
	 */
	@Override
	public final void onValueChanged(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final Object valueOld,
			final Object valueNew)
	{
		if (!isExecuteCallout(attributeValueContext, attributeSet, attribute, valueOld, valueNew))
		{
			return;
		}
		onValueChanged0(attributeValueContext, attributeSet, attribute, valueOld, valueNew);
	}

	@Override
	public boolean isAlwaysEditableUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		if (isWeightableOnlyIfVHU(ctx))
		{
			final boolean alwaysEditable = !isReadonlyUI(ctx, attributeSet, attribute);
			return alwaysEditable;
		}

		return false;
	}

	@Override
	public final boolean isReadonlyUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		//
		// Decide whether the attribute shall be readonly or not from now on
		final boolean sysconfigIsNonWeightableReadOnlyUIOverride = sysConfigBL.getBooleanValue(SYSCONFIG_IsNonWeightableReadOnlyUIOverride, true);
		if (!isWeightable(attributeSet) && sysconfigIsNonWeightableReadOnlyUIOverride)
		{
			return true;
		}

		//
		// Check: Weightable only if attribute storage is about an virtual HU
		final boolean weightableOnlyIfVHU = isWeightableOnlyIfVHU(ctx);
		if (weightableOnlyIfVHU && !isVirtualHU(attributeSet))
		{
			return true; // readonly
		}

		//
		// Fallback: consider it editable
		return false;
	}

	private boolean isWeightable(final IAttributeSet attributeSet)
	{
		final IWeightable weightable = getWeightableOrNull(attributeSet);
		if (weightable == null)
		{
			return false;
		}

		//
		// Decide whether the attribute shall be readonly or not from now on
		return weightable.isWeightable();
	}

	private boolean isWeightableOnlyIfVHU(final IAttributeValueContext ctx)
	{
		final Boolean weightableOnlyIfVHU = ctx.getParameter(Weightables.PROPERTY_WeightableOnlyIfVHU);
		if (weightableOnlyIfVHU == null)
		{
			return false;
		}
		return weightableOnlyIfVHU;
	}

	private boolean isVirtualHU(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = huAttributesBL.getM_HU_OrNull(attributeSet);

		// Consider attribute sets without HUs as non Virtuals
		if (hu == null)
		{
			return false;
		}

		return handlingUnitsBL.isVirtual(hu);
	}

	/**
	 * See {@link #onValueChanged(IAttributeValueContext, IAttributeSet, I_M_Attribute, Object, Object)}.
	 */
	protected abstract void onValueChanged0(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final Object valueOld,
			final Object valueNew);

	protected final IWeightable getWeightableOrNull(@Nullable final IAttributeSet attributeSet)
	{
		if (attributeSet == null)
		{
			return null;
		}
		else if (attributeSet instanceof IAttributeStorage)
		{
			final IAttributeStorage attributeStorage = (IAttributeStorage)attributeSet;
			return Weightables.wrap(attributeStorage);
		}
		else
		{
			return null;
		}
	}

	/**
	 * See {@link #onValueChanged(IAttributeValueContext, IAttributeSet, I_M_Attribute, Object, Object)}.
	 */
	protected abstract boolean isExecuteCallout(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final Object valueOld,
			final Object valueNew);

	/**
	 * Calculate WeightGross = WeightNet + WeightTare + WeightTareAdjust
	 *
	 * @param attributeSet
	 */
	protected final void recalculateWeightGross(final IAttributeSet attributeSet)
	{
		// final IAttributeStorage attributeStorage = (IAttributeStorage)attributeSet;
		final IWeightable weightable = getWeightableOrNull(attributeSet);

		// NOTE: we calculate WeightGross, no matter if our HU is allowed to be weighted by user
		// final boolean weightUOMFriendly = weightable.isWeightable();

		final BigDecimal weightTareTotal = weightable.getWeightTareTotal();
		final BigDecimal weightNet = weightable.getWeightNet();
		final BigDecimal weightGross = weightNet.add(weightTareTotal);

		weightable.setWeightGross(weightGross);
	}

	protected final void recalculateWeightNet(final IAttributeSet attributeSet)
	{
		final IWeightable weightable = getWeightableOrNull(attributeSet);
		Weightables.updateWeightNet(weightable);
	}

	protected boolean isLUorTUorTopLevelVHU(IAttributeSet attributeSet)
	{
		if (!isVirtualHU(attributeSet))
		{
			return true;
		}

		final I_M_HU hu = huAttributesBL.getM_HU_OrNull(attributeSet);
		final boolean virtualTopLevelHU = handlingUnitsBL.isTopLevel(hu);
		return virtualTopLevelHU;
	}

	@Override
	public final String generateStringValue(final Properties ctx, final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public final Date generateDateValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public final AttributeListValue generateAttributeValue(final Properties ctx, final int tableId, final int recordId, final boolean isSOTrx, final String trxName)
	{
		throw new UnsupportedOperationException("Not supported");
	}

}
