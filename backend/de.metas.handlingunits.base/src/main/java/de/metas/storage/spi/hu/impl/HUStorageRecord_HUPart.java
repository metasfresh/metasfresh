package de.metas.storage.spi.hu.impl;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Locator;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;

/**
 * Part of the {@link HUStorageRecord} which provides all informations that are coming from {@link I_M_HU}.
 *
 * @author tsa
 *
 */
@VisibleForTesting
public class HUStorageRecord_HUPart
{
	private final I_M_HU hu;
	private final IAttributeStorage huAttributes;

	public HUStorageRecord_HUPart(final I_M_HU hu, final IAttributeStorage huAttributes)
	{
		super();
		Check.assumeNotNull(hu, "hu not null");
		this.hu = hu;

		Check.assumeNotNull(huAttributes, "huAttributes not null");
		this.huAttributes = huAttributes;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public IAttributeStorage getAttributes()
	{
		return huAttributes;
	}

	public I_M_Locator getM_Locator()
	{
		return IHandlingUnitsBL.extractLocatorOrNull(hu);
	}

	public I_C_BPartner getC_BPartner()
	{
		return IHandlingUnitsBL.extractBPartnerOrNull(hu);
	}

	public I_C_BPartner_Location getC_BPartner_Location()
	{
		return IHandlingUnitsBL.extractBPartnerLocationOrNull(hu);
	}

	public I_M_HU getM_HU()
	{
		return hu;
	}
}
