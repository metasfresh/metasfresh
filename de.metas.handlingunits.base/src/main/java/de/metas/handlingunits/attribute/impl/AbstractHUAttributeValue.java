package de.metas.handlingunits.attribute.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IAttributeAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.IAttributeStrategyFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferStrategy;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;

public abstract class AbstractHUAttributeValue extends AbstractAttributeValue
{
	//
	// Services
	private final IAttributeStrategyFactory attributeStrategyFactory = Services.get(IAttributeStrategyFactory.class);
	private final I_M_HU_PI_Attribute huPIAttribute;
	
	private volatile Boolean _definedByTemplate; // lazy

	public AbstractHUAttributeValue(final IAttributeStorage attributeStorage, final I_M_HU_PI_Attribute huPIAttribute, final Boolean isTemplateAttribute)
	{
		super(attributeStorage, huPIAttribute.getM_Attribute());
		this.huPIAttribute = huPIAttribute;
		
		this._definedByTemplate = isTemplateAttribute; // null is OK
	}

	@Override
	public String getPropagationType()
	{
		final String propagationType = huPIAttribute.getPropagationType();
		return propagationType;
	}

	@Override
	public IAttributeAggregationStrategy retrieveAggregationStrategy()
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(huPIAttribute);
		final int adJavaClassId = huPIAttribute.getAggregationStrategy_JavaClass_ID();
		final IAttributeAggregationStrategy aggregationStrategy = attributeStrategyFactory
				.retrieveStrategy(ctx, adJavaClassId, IAttributeAggregationStrategy.class);

		return aggregationStrategy;
	}

	@Override
	public IAttributeSplitterStrategy retrieveSplitterStrategy()
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(huPIAttribute);
		final int adJavaClassId = huPIAttribute.getSplitterStrategy_JavaClass_ID();
		final IAttributeSplitterStrategy splitterStrategy = attributeStrategyFactory
				.retrieveStrategy(ctx, adJavaClassId, IAttributeSplitterStrategy.class);

		return splitterStrategy;
	}

	@Override
	public IHUAttributeTransferStrategy retrieveTransferStrategy()
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(huPIAttribute);
		final int adJavaClassId = huPIAttribute.getHU_TansferStrategy_JavaClass_ID();
		final IHUAttributeTransferStrategy transferStrategy = attributeStrategyFactory
				.retrieveStrategy(ctx, adJavaClassId, IHUAttributeTransferStrategy.class);

		return transferStrategy;
	}

	@Override
	public boolean isUseInASI()
	{
		return huPIAttribute.isUseInASI();
	}
	
	@Override
	public boolean isDefinedByTemplate()
	{
		if(_definedByTemplate == null)
		{
			synchronized (this)
			{
				if(_definedByTemplate == null)
				{
					_definedByTemplate = Services.get(IHUPIAttributesDAO.class).isTemplateAttribute(huPIAttribute);
				}
			}
		}
		return _definedByTemplate;
	}

	public I_M_HU_PI_Attribute getM_HU_PI_Attribute()
	{
		return huPIAttribute;
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		final I_C_UOM uom = huPIAttribute.getC_UOM();
		if (uom != null && uom.getC_UOM_ID() > 0)
		{
			return uom;
		}

		// fallback to M_Attribute's UOM
		return super.getC_UOM();
	}

	@Override
	public boolean isReadonlyUI()
	{
		return huPIAttribute.isReadOnly();
	}
	
	@Override
	public boolean isDisplayedUI()
	{
		return huPIAttribute.isDisplayed();
	}

	@Override
	public int getDisplaySeqNo()
	{
		final int seqNo = huPIAttribute.getSeqNo();
		if (seqNo > 0)
		{
			return seqNo;
		}

		// Fallback: if SeqNo was not set, return max int (i.e. show them last)
		return Integer.MAX_VALUE;
	}
}
