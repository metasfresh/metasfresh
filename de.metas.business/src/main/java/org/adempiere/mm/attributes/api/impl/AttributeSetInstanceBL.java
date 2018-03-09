package org.adempiere.mm.attributes.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;

/*
 * #%L
 * de.metas.swat.base
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

import java.text.DateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.DisplayType;

import com.google.common.collect.ImmutableList;

import de.metas.product.IProductBL;
import lombok.NonNull;

public class AttributeSetInstanceBL implements IAttributeSetInstanceBL
{
	@Override
	public String buildDescription(final I_M_AttributeSetInstance asi)
	{
		final boolean verboseDescription = false;
		return buildDescription(asi, verboseDescription);
	}

	@Override
	public String buildDescription(final I_M_AttributeSetInstance asi, final boolean verboseDescription)
	{
		//
		// Guard against null or new ASI
		// In this case it makes no sense to build the Description because there are no attribute instances.
		if (asi == null || InterfaceWrapperHelper.isNew(asi))
		{
			return null;
		}

		// services
		final IAttributeDAO attributesDAO = Services.get(IAttributeDAO.class);

		//
		// Get the M_AttributeSet
		I_M_AttributeSet as = asi.getM_AttributeSet();
		if (as == null && asi.getM_AttributeSet_ID() == AttributeConstants.M_AttributeSet_ID_None)
		{
			// FIXME: this is a workaround because our persistence engine returns NULL in case
			// the ID=0, even if is existing in database
			// Also see task http://dewiki908/mediawiki/index.php/08765_Introduce_AD_Table.IDRangeStart_%28107200611713%29
			as = attributesDAO.retrieveNoAttributeSet();
		}

		final StringBuilder sb = new StringBuilder();

		//
		// Retrieve Attribute Instances and sort them by M_Attribute.SeqNo
		final List<I_M_AttributeInstance> attributeInstances = attributesDAO.retrieveAttributeInstances(asi);
		// TODO: attribute instances shall be sorted by M_AttributeUse.SeqNo

		// Instance Attribute Values
		for (final I_M_AttributeInstance instance : attributeInstances)
		{
			String value = instance.getValue();
			if (Check.isEmpty(value) && instance.getValueDate() != null)
			{
				final DateFormat dateFormat = DisplayType.getDateFormat(DisplayType.Date);
				value = dateFormat.format(instance.getValueDate());
			}
			if (Check.isEmpty(value) && instance.getValueNumber() != null && verboseDescription)
			{
				// only try value number if verboseDescription==true.
				// it looks like this for "empty" ASIs and currently there is no demand for "normal" users : 0_0_0_0_0
				// after all we are just creating a description here that just contains of AI values..not even the attribute name is included.
				value = instance.getValueNumber().toString();
			}

			if (!Check.isEmpty(value, false))
			{
				if (sb.length() > 0)
				{
					sb.append("_");
				}

				final I_M_AttributeValue attributeValue = instance.getM_AttributeValue();
				if (attributeValue != null && attributeValue.getM_AttributeValue_ID() > 0)
				{
					sb.append(attributeValue.getName());
				}
				else
				{
					sb.append(value);
				}
			}
		}

		// SerNo
		if (as.isSerNo() && asi.getSerNo() != null)
		{
			if (sb.length() > 0)
			{
				sb.append("_");
			}
			sb.append(null == as.getSerNoCharSOverwrite() || as.getSerNoCharSOverwrite().isEmpty() ? "#" : as.getSerNoCharSOverwrite());
			sb.append(asi.getSerNo());
			sb.append(null == as.getSerNoCharEOverwrite() || as.getSerNoCharEOverwrite().isEmpty() ? "#" : as.getSerNoCharEOverwrite());
		}

		// Lot
		if (as.isLot() && asi.getLot() != null)
		{
			if (sb.length() > 0)
			{
				sb.append("_");
			}
			sb.append(null == as.getLotCharSOverwrite() || as.getSerNoCharSOverwrite().isEmpty() ? "#" : as.getLotCharSOverwrite());
			sb.append(asi.getLot());
			sb.append(null == as.getLotCharEOverwrite() || as.getSerNoCharEOverwrite().isEmpty() ? "#" : as.getLotCharEOverwrite());
		}

		// GuaranteeDate
		// NOTE: we are not checking if "as.isGuaranteeDate()" because it could be that GuaranteeDate was set even though in attribute set did not mention it (task #09363).
		if (asi.getGuaranteeDate() != null)
		{
			if (sb.length() > 0)
			{
				sb.append("_");
			}
			sb.append(DisplayType.getDateFormat(DisplayType.Date).format(asi.getGuaranteeDate()));
		}
		// Product Attribute Values
		for (final I_M_Attribute attribute : attributesDAO.retrieveAttributes(as, false))
		{
			if (sb.length() > 0)
			{
				sb.append("_");
			}
			sb.append(attribute.getName());
		}

		// NOTE: mark: if there is nothing to show then don't show ASI ID because that number will confuse the user.
		// // In case there is no other description, at least show the ID
		// if (sb.length() <= 0 && asi.getM_AttributeSetInstance_ID() > 0)
		// {
		// sb.append(asi.getM_AttributeSetInstance_ID());
		// }

		return sb.toString();
	}

	@Override
	public void setDescription(final I_M_AttributeSetInstance asi)
	{
		Check.assumeNotNull(asi, "asi not null");
		final String description = buildDescription(asi);
		asi.setDescription(description);
	}

	@Override
	public I_M_AttributeSetInstance createASI(@NonNull final I_M_Product product)
	{
		final I_M_AttributeSetInstance asiNew = newInstance(I_M_AttributeSetInstance.class, product);

		// use the method from the service so if the product doesn't have an AS, it can be taken from product category
		final int productAttributeSet_ID = Services.get(IProductBL.class).getM_AttributeSet_ID(product);

		asiNew.setM_AttributeSet_ID(productAttributeSet_ID);
		InterfaceWrapperHelper.save(asiNew);
		return asiNew;
	}

	@Override
	public I_M_AttributeSetInstance getCreateASI(final IAttributeSetInstanceAware asiAware)
	{
		final I_M_AttributeSetInstance asiExisting = asiAware.getM_AttributeSetInstance();
		if (asiExisting != null)
		{
			return asiExisting;
		}

		//
		// Create new ASI
		final I_M_Product product = asiAware.getM_Product();
		final I_M_AttributeSetInstance asiNew = createASI(product);
		asiAware.setM_AttributeSetInstance(asiNew);
		return asiNew;
	}

	@Override
	public I_M_AttributeInstance getCreateAttributeInstance(final I_M_AttributeSetInstance asi, final I_M_AttributeValue attributeValue)
	{
		Check.assumeNotNull(attributeValue, "attributeValue not null");

		// services
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		// M_Attribute_ID
		final int attributeId = attributeValue.getM_Attribute_ID();
		Check.assume(attributeId > 0, "attributeId > 0 for {}", attributeValue);

		//
		// Get/Create/Update Attribute Instance
		I_M_AttributeInstance attributeInstance = attributeDAO.retrieveAttributeInstance(asi, attributeId);
		if (attributeInstance == null)
		{
			attributeInstance = newInstance(I_M_AttributeInstance.class, asi);
		}
		attributeInstance.setM_AttributeSetInstance(asi);
		attributeInstance.setM_AttributeValue(attributeValue);
		attributeInstance.setValue(attributeValue.getValue());
		attributeInstance.setM_Attribute_ID(attributeId);
		save(attributeInstance);

		return attributeInstance;
	}

	@Override
	public I_M_AttributeInstance getCreateAttributeInstance(final I_M_AttributeSetInstance asi, final int attributeId)
	{
		Check.assumeNotNull(asi, "asi not null");
		Check.assume(attributeId > 0, "attributeId > 0");

		// Check if already exists
		final I_M_AttributeInstance instanceExisting = Services.get(IAttributeDAO.class).retrieveAttributeInstance(asi, attributeId);
		if (instanceExisting != null)
		{
			return instanceExisting;
		}

		// Create New
		final I_M_AttributeInstance instanceNew = newInstance(I_M_AttributeInstance.class, asi);
		instanceNew.setM_Attribute_ID(attributeId);
		instanceNew.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		save(instanceNew);
		return instanceNew;
	}

	@Override
	public void cloneASI(final Object to, final Object from)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);

		final IAttributeSetInstanceAware toASIAware = attributeSetInstanceAwareFactoryService.createOrNull(to);
		if (toASIAware == null)
		{
			return;
		}
		final IAttributeSetInstanceAware fromASIAware = attributeSetInstanceAwareFactoryService.createOrNull(from);
		if (fromASIAware == null)
		{
			return;
		}

		//
		// Clone the ASI
		if (fromASIAware.getM_AttributeSetInstance_ID() > 0)
		{
			final I_M_AttributeSetInstance asi = fromASIAware.getM_AttributeSetInstance();
			final I_M_AttributeSetInstance asiCopy = attributeDAO.copy(asi);
			toASIAware.setM_AttributeSetInstance(asiCopy);
		}
	}

	@Override
	public I_M_AttributeSetInstance createASIFromAttributeSet(@NonNull final IAttributeSet attributeSet)
	{
		final int productId = -1;
		return createASIWithASFromProductAndInsertAttributeSet(productId, attributeSet);
	}

	@Override
	public I_M_AttributeSetInstance createASIWithASFromProductAndInsertAttributeSet(
			final int productId,
			@NonNull final IAttributeSet attributeSet)
	{
		final I_M_AttributeSetInstance attributeSetInstance;
		if (productId > 0)
		{
			final I_M_Product product = load(productId, I_M_Product.class);
			attributeSetInstance = createASI(product);
		}
		else
		{
			attributeSetInstance = newInstance(I_M_AttributeSetInstance.class);
		}
		save(attributeSetInstance);

		final ImmutableList<I_M_Attribute> attributesOrderedById = attributeSet.getAttributes().stream()
				.sorted(Comparator.comparing(I_M_Attribute::getM_Attribute_ID))
				.collect(ImmutableList.toImmutableList());

		for (final I_M_Attribute atttribute : attributesOrderedById)
		{
			final I_M_AttributeInstance attributeInstance = //
					createAttributeInstanceForAttributeAndAttributeSet(atttribute, attributeSet);

			attributeInstance.setM_AttributeSetInstance(attributeSetInstance);
			save(attributeInstance);
		}
		return attributeSetInstance;
	}

	private I_M_AttributeInstance createAttributeInstanceForAttributeAndAttributeSet(
			@NonNull final I_M_Attribute attribute,
			@NonNull final IAttributeSet attributeSet)
	{
		final I_M_AttributeInstance attributeInstance = newInstance(I_M_AttributeInstance.class);
		attributeInstance.setM_Attribute(attribute);

		final String attributeValueType = attributeSet.getAttributeValueType(attribute);
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			final Date dateValue = attributeSet.getValueAsDate(attribute);
			if (dateValue != null)
			{
				attributeInstance.setValueDate(new Timestamp(dateValue.getTime()));
			}
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			final String stringValue = attributeSet.getValueAsString(attribute);
			attributeInstance.setValue(stringValue);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			final String stringValue = attributeSet.getValueAsString(attribute);
			attributeInstance.setValue(stringValue);

			final I_M_AttributeValue attributeValue = Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(attribute, stringValue);
			attributeInstance.setM_AttributeValue(attributeValue);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final BigDecimal bigDecimalValue = attributeSet.getValueAsBigDecimal(attribute);
			attributeInstance.setValueNumber(bigDecimalValue);
		}

		return attributeInstance;
	}

}
