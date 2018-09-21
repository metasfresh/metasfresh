package org.adempiere.mm.attributes.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;

public class AttributeSetInstanceBL implements IAttributeSetInstanceBL
{
	@Override
	public String buildDescription(@Nullable final I_M_AttributeSetInstance asi)
	{
		final boolean verboseDescription = false;
		return buildDescription(asi, verboseDescription);
	}

	@Override
	public String buildDescription(@Nullable final I_M_AttributeSetInstance asi, final boolean verboseDescription)
	{
		if (asi == null)
		{
			return "";
		}

		return new ASIDescriptionBuilderCommand(asi, verboseDescription)
				.buildDescription();
	}

	@Override
	public void setDescription(@Nullable final I_M_AttributeSetInstance asi)
	{
		if (asi == null)
		{
			return;
		}
		final String description = buildDescription(asi);
		asi.setDescription(description);
	}

	@Override
	public I_M_AttributeSetInstance createASI(@NonNull final ProductId productId)
	{
		final I_M_AttributeSetInstance asiNew = newInstance(I_M_AttributeSetInstance.class);

		// use the method from the service so if the product doesn't have an AS, it can be taken from product category
		final AttributeSetId productAttributeSetId = Services.get(IProductBL.class).getAttributeSetId(productId);

		asiNew.setM_AttributeSet_ID(productAttributeSetId.getRepoId());
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
		final ProductId productId = ProductId.ofRepoId(asiAware.getM_Product_ID());
		final I_M_AttributeSetInstance asiNew = createASI(productId);
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
		final AttributeId attributeId = AttributeId.ofRepoId(attributeValue.getM_Attribute_ID());

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
		attributeInstance.setM_Attribute_ID(attributeId.getRepoId());
		save(attributeInstance);

		return attributeInstance;
	}

	@Override
	public I_M_AttributeInstance getCreateAttributeInstance(@NonNull final I_M_AttributeSetInstance asi, @NonNull final AttributeId attributeId)
	{
		// Check if already exists
		final I_M_AttributeInstance instanceExisting = Services.get(IAttributeDAO.class).retrieveAttributeInstance(asi, attributeId);
		if (instanceExisting != null)
		{
			return instanceExisting;
		}

		// Create New
		final I_M_AttributeInstance instanceNew = newInstance(I_M_AttributeInstance.class, asi);
		instanceNew.setM_Attribute_ID(attributeId.getRepoId());
		instanceNew.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		save(instanceNew);
		return instanceNew;
	}

	@Override
	public void setAttributeInstanceValue(
			@NonNull final I_M_AttributeSetInstance asi,
			@NonNull final I_M_Attribute attribute,
			@NonNull final Object value)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		I_M_AttributeInstance attributeInstance = Services.get(IAttributeDAO.class).retrieveAttributeInstance(asi, attributeId);
		if (attributeInstance == null)
		{
			attributeInstance = getCreateAttributeInstance(asi, attributeId);
		}

		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			attributeInstance.setValueDate(Env.parseTimestamp(value.toString()));
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			attributeInstance.setValueNumber(NumberUtils.asBigDecimal(value.toString(), null));
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType)
				|| X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			attributeInstance.setValue(value.toString());
		}
		else
		{
			throw new IllegalArgumentException("@NotSupported@ @AttributeValueType@=" + attributeValueType + ", @M_Attribute_ID@=" + attribute);
		}

		save(attributeInstance);
	}

	@Override
	public void setAttributeInstanceValue(@NonNull final I_M_AttributeSetInstance asi, @NonNull final AttributeId attributeId, @NonNull final Object value)
	{
		final I_M_Attribute attribute = Services.get(IAttributeDAO.class).getAttributeById(attributeId);
		setAttributeInstanceValue(asi, attribute, value);
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
		final ProductId productId = null;
		return createASIWithASFromProductAndInsertAttributeSet(productId, attributeSet);
	}

	@Override
	public I_M_AttributeSetInstance createASIWithASFromProductAndInsertAttributeSet(
			@Nullable final ProductId productId,
			@NonNull final IAttributeSet attributeSet)
	{
		final I_M_AttributeSetInstance attributeSetInstance;
		if (productId != null)
		{
			attributeSetInstance = createASI(productId);
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
