package de.metas.materialtracking.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.model.I_M_Material_Tracking;

public class MaterialTrackingAttributeBL implements IMaterialTrackingAttributeBL
{
	private static final String M_Attribute_Value_MaterialTracking = "M_Material_Tracking_ID";

	@Override
	public I_M_Attribute getMaterialTrackingAttribute(final Properties ctx)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValue(ctx, M_Attribute_Value_MaterialTracking, I_M_Attribute.class);
		Check.assumeNotNull(attribute, "attribute shall exist for {}", M_Attribute_Value_MaterialTracking);

		return attribute;
	}

	/*
	 * When changing the implementation, please make that the model interceptors' <code>ifColumnsChanged</code> are in sync.
	 * 
	 * (non-Javadoc)
	 * 
	 * @see IMaterialTrackingAttributeBL#createOrUpdateMaterialTrackingAttributeValue(I_M_Material_Tracking)
	 */
	@Override
	public void createOrUpdateMaterialTrackingAttributeValue(final I_M_Material_Tracking materialTracking)
	{
		I_M_AttributeValue attributeValue = materialTracking.getM_AttributeValue();

		//
		// If the attribute value was not already created, create it now
		if (attributeValue == null || attributeValue.getM_AttributeValue_ID() <= 0)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(materialTracking);
			final I_M_Attribute attribute = getMaterialTrackingAttribute(ctx);

			attributeValue = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class, materialTracking);
			attributeValue.setAD_Org_ID(attribute.getAD_Org_ID());
			attributeValue.setM_Attribute(attribute);
		}

		//
		// Update Attribute Value
		final String name = buildName(materialTracking);
		attributeValue.setName(name);

		//
		final String value;
		if (materialTracking.getM_Material_Tracking_ID() <= 0)
		{
			// In case it's a new material tracking use the build name for it because value will always by "0" and we could have unique index violations.
			// Please note that there is a model interceptor which is updating the value after new.
			// (see M_Material_Tracking.updateMaterialTrackingAttributeValue_Value(I_M_Material_Tracking))
			value = name;
		}
		else
		{
			value = getMaterialTrackingIdStr(materialTracking);
		}
		attributeValue.setValue(value);

		final String description = materialTracking.getDescription();
		attributeValue.setDescription(description);

		final boolean isActive = materialTracking.isActive() && !materialTracking.isProcessed();
		attributeValue.setIsActive(isActive);

		//
		// Save it
		InterfaceWrapperHelper.save(attributeValue);

		materialTracking.setM_AttributeValue(attributeValue);
	}

	@Override
	public final String getMaterialTrackingIdStr(final I_M_Material_Tracking materialTracking)
	{
		final int materialTrackingId = materialTracking.getM_Material_Tracking_ID();
		final String materialTrackingIdStr = String.valueOf(materialTrackingId);
		return materialTrackingIdStr;
	}

	private final int getMaterialTrackingIdFromMaterialTrackingIdStr(final String materialTrackingIdStr)
	{
		if (Check.isEmpty(materialTrackingIdStr, true))
		{
			return -1;
		}

		final int materialTrackingId = Integer.parseInt(materialTrackingIdStr.trim());
		return materialTrackingId;
	}

	/**
	 * Build a descriptive name for given material tracking
	 *
	 * @param materialTracking
	 * @return
	 */
	private final String buildName(final I_M_Material_Tracking materialTracking)
	{
		Check.assumeNotNull(materialTracking, "materialTracking not null");

		final StringBuilder sb = new StringBuilder();

		// ID
		// sb.append(materialTracking.getM_Material_Tracking_ID());

		// Lot
		sb.append(materialTracking.getLot());

		// Vendor/BPartner
		final I_C_BPartner bpartner = materialTracking.getC_BPartner();
		if (bpartner != null && bpartner.getC_BPartner_ID() > 0)
		{
			sb.append("_");
			sb.append(bpartner.getName());
		}

		// Tracked product
		final I_M_Product product = materialTracking.getM_Product();
		if (product != null && product.getM_Product_ID() > 0)
		{
			sb.append("_");
			sb.append(product.getName());
		}

		return sb.toString();
	}

	private I_M_AttributeInstance getMaterialTrackingAttributeInstanceOrNull(final I_M_AttributeSetInstance asi, final boolean createIfNotFound)
	{
		if (asi == null || asi.getM_AttributeSetInstance_ID() <= 0)
		{
			return null;
		}

		//
		// Get Material Tracking I_M_Attribute
		final Properties ctx = InterfaceWrapperHelper.getCtx(asi);
		final I_M_Attribute materialTrackingAttribute = getMaterialTrackingAttribute(ctx);
		final int materialTrackingAttributeId = materialTrackingAttribute.getM_Attribute_ID();

		//
		// Retrieve Material Tracking Attribute Instance (from ASI)
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final I_M_AttributeInstance materialTrackingAttributeInstance = attributeDAO.retrieveAttributeInstance(asi, materialTrackingAttributeId);

		if (materialTrackingAttributeInstance != null)
		{
			return materialTrackingAttributeInstance;
		}

		if (!createIfNotFound)
		{
			return null;
		}

		//
		// Create a new one
		final I_M_AttributeInstance materialTrackingAttributeInstanceNew = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class, asi);
		materialTrackingAttributeInstanceNew.setAD_Org_ID(asi.getAD_Org_ID());
		materialTrackingAttributeInstanceNew.setM_AttributeSetInstance(asi);
		materialTrackingAttributeInstanceNew.setM_Attribute(materialTrackingAttribute);
		// NOTE: don't save it

		return materialTrackingAttributeInstanceNew;
	}

	private I_M_AttributeValue getMaterialTrackingAttributeValue(final I_M_Material_Tracking materialTracking)
	{
		if (materialTracking == null)
		{
			return null;
		}

		final I_M_AttributeValue materialTrackingAttributeValue = materialTracking.getM_AttributeValue();
		if (materialTrackingAttributeValue == null || materialTrackingAttributeValue.getM_AttributeValue_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @M_AttributeValue_ID@"
					+ "\n@M_Material_Tracking_ID@: " + materialTracking);
		}

		return materialTrackingAttributeValue;
	}

	@Override
	public void createOrUpdateMaterialTrackingASI(final Object documentLine,
			final I_M_Material_Tracking materialTracking)
	{
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final I_M_AttributeSetInstance documentLineASI;
		final IAttributeSetInstanceAware documentLineASIAware = attributeSetInstanceAwareFactoryService.createOrNull(documentLine);
		Check.assumeNotNull(documentLineASIAware, "IAttributeSetInstanceAwareFactoryService.createOrNull() does not return null for {}", documentLine);

		if (documentLineASIAware.getM_AttributeSetInstance_ID() > 0)
		{
			documentLineASI = attributeDAO.copy(documentLineASIAware.getM_AttributeSetInstance());
		}
		else
		{
			documentLineASI = attributeSetInstanceBL.createASI(documentLineASIAware.getM_Product());
		}

		setM_Material_Tracking(documentLineASI, materialTracking); // update the new ASI
		documentLineASIAware.setM_AttributeSetInstance(documentLineASI); // for inOutLines, this also causes M_InOutLine.M_Material_Tracking_ID to be updated
	}

	@Override
	public void setM_Material_Tracking(final I_M_AttributeSetInstance asi, final I_M_Material_Tracking materialTracking)
	{
		if (asi == null || asi.getM_AttributeSetInstance_ID() <= 0)
		{
			if (materialTracking == null)
			{
				return;
			}

			// When material tracking is specified, we need ASI to be not null
			Check.assumeNotNull(asi, "asi not null");
		}

		//
		// Retrieve Material Tracking Attribute Instance (from ASI)
		final boolean createAttributeInstanceIfNotFound = materialTracking != null;
		final I_M_AttributeInstance materialTrackingAttributeInstance = getMaterialTrackingAttributeInstanceOrNull(asi, createAttributeInstanceIfNotFound);
		if (materialTrackingAttributeInstance == null)
		{
			return;
		}

		//
		// Get corresponding material tracking attribute value
		final I_M_AttributeValue materialTrackingAttributeValue = getMaterialTrackingAttributeValue(materialTracking);

		//
		// Update Attribute Instance
		materialTrackingAttributeInstance.setValue(materialTrackingAttributeValue == null ? null : materialTrackingAttributeValue.getValue());
		materialTrackingAttributeInstance.setM_AttributeValue(materialTrackingAttributeValue);

		InterfaceWrapperHelper.save(materialTrackingAttributeInstance);
	}

	@Override
	public I_M_Material_Tracking getMaterialTrackingOrNull(final I_M_AttributeSetInstance asi)
	{
		if (asi == null || asi.getM_AttributeSetInstance_ID() <= 0)
		{
			return null;
		}

		final I_M_AttributeInstance materialTrackingAttributeInstance = getMaterialTrackingAttributeInstanceOrNull(asi, false); // createIfNotFound=false
		if (materialTrackingAttributeInstance == null)
		{
			return null;
		}

		final I_M_AttributeValue materialTrackingAttributeValue = materialTrackingAttributeInstance.getM_AttributeValue();

		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		return materialTrackingDAO.retrieveMaterialTrackingByAttributeValue(materialTrackingAttributeValue);
	}

	@Override
	public I_M_Material_Tracking getMaterialTracking(final IContextAware context, final IAttributeSet attributeSet)
	{
		final int materialTrackingId = getMaterialTrackingId(context, attributeSet);
		if (materialTrackingId <= 0)
		{
			return null;
		}

		final I_M_Material_Tracking materialTracking = InterfaceWrapperHelper.create(context.getCtx(), materialTrackingId, I_M_Material_Tracking.class, context.getTrxName());
		Check.assume(materialTracking != null && materialTracking.getM_Material_Tracking_ID() == materialTrackingId,
				"Material tracking record shall exist for ID={}", materialTrackingId);

		return materialTracking;
	}

	@Override
	public boolean isMaterialTrackingSet(final IContextAware context, final IAttributeSet attributeSet)
	{
		final int materialTrackingId = getMaterialTrackingId(context, attributeSet);
		return materialTrackingId > 0;
	}

	private final int getMaterialTrackingId(final IContextAware context, final IAttributeSet attributeSet)
	{
		final Properties ctx = context.getCtx();
		final I_M_Attribute materialTrackingAttribute = getMaterialTrackingAttribute(ctx);
		if (!attributeSet.hasAttribute(materialTrackingAttribute))
		{
			return -1;
		}
		final Object materialTrackingIdObj = attributeSet.getValue(materialTrackingAttribute);
		if (materialTrackingIdObj == null)
		{
			return -1;
		}

		final String materialTrackingIdStr = materialTrackingIdObj.toString();
		final int materialTrackingId = getMaterialTrackingIdFromMaterialTrackingIdStr(materialTrackingIdStr);
		return materialTrackingId;
	}

	@Override
	public boolean hasMaterialTrackingAttribute(final I_M_AttributeSetInstance asi)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(asi);
		final I_M_Attribute materialTrackingAttribute = getMaterialTrackingAttribute(ctx);

		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final I_M_AttributeInstance materialTrackingAttributeInstance = attributeDAO.retrieveAttributeInstance(asi, materialTrackingAttribute.getM_Attribute_ID());

		return materialTrackingAttributeInstance != null;
	}
}
