package org.adempiere.mm.attributes.api.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public class AttributeSetInstanceBL implements IAttributeSetInstanceBL
{
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

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
	public I_M_AttributeInstance getCreateAttributeInstance(
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final AttributeListValue attributeValue)
	{
		// services
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final AttributeId attributeId = attributeValue.getAttributeId();

		if (asiId.isNone())
		{
			throw new AdempiereException("Given 'asiId' may not be 'none'")
					.appendParametersToMessage()
					.setParameter("asiId", asiId)
					.setParameter("attributeValue", attributeValue);
		}

		//
		// Get/Create/Update Attribute Instance
		I_M_AttributeInstance attributeInstance = attributeDAO.retrieveAttributeInstance(asiId, attributeId);
		if (attributeInstance == null)
		{
			attributeInstance = newInstance(I_M_AttributeInstance.class);
		}
		attributeInstance.setM_AttributeSetInstance_ID(asiId.getRepoId());
		attributeInstance.setM_AttributeValue_ID(attributeValue.getId().getRepoId());
		attributeInstance.setValue(attributeValue.getValue());
		attributeInstance.setM_Attribute_ID(attributeId.getRepoId());
		save(attributeInstance);

		return attributeInstance;
	}

	@Override
	public I_M_AttributeInstance getCreateAttributeInstance(
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final AttributeId attributeId)
	{
		if (asiId.isNone())
		{
			throw new AdempiereException("Given 'asiId' may not be 'none'")
					.appendParametersToMessage()
					.setParameter("asiId", asiId)
					.setParameter("attributeId", attributeId);
		}

		// Check if already exists
		final I_M_AttributeInstance instanceExisting = attributeDAO.retrieveAttributeInstance(asiId, attributeId);
		if (instanceExisting != null)
		{
			return instanceExisting;
		}

		// Create New
		final I_M_AttributeInstance instanceNew = newInstance(I_M_AttributeInstance.class);
		instanceNew.setM_Attribute_ID(attributeId.getRepoId());
		instanceNew.setM_AttributeSetInstance_ID(asiId.getRepoId());
		save(instanceNew);
		return instanceNew;
	}

	@Override
	public void setAttributeInstanceValue(
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final AttributeCode attributeCode,
			@Nullable final Object value)
	{
		final I_M_Attribute attributeRecord = attributeDAO.retrieveAttributeByValue(attributeCode);
		final I_M_AttributeInstance attributeInstance = getCreateAttributeInstance(asiId, AttributeId.ofRepoId(attributeRecord.getM_Attribute_ID()));

		setAttributeInstanceValue(attributeRecord, attributeInstance, value);
	}

	@Override
	public void setAttributeInstanceValue(
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final AttributeId attributeId,
			@Nullable final Object value)
	{

		final I_M_Attribute attributeRecord = attributeDAO.getAttributeById(attributeId);
		final I_M_AttributeInstance attributeInstance = getCreateAttributeInstance(asiId, attributeId);

		setAttributeInstanceValue(attributeRecord, attributeInstance, value);
	}

	private void setAttributeInstanceValue(
			@NonNull final I_M_Attribute attributeRecord,
			@NonNull final I_M_AttributeInstance attributeInstance,
			@Nullable final Object value)
	{
		final String attributeValueType = attributeRecord.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			attributeInstance.setValueDate(value == null ? null : Env.parseTimestamp(value.toString()));
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			attributeInstance.setValueNumber(value == null ? null : NumberUtils.asBigDecimal(value.toString(), null));
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType)
				|| X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			attributeInstance.setValue(value == null ? null : value.toString());
		}
		else
		{
			throw new IllegalArgumentException("@NotSupported@ @AttributeValueType@=" + attributeValueType + ", @M_Attribute_ID@=" + attributeRecord.getM_Attribute_ID());
		}
		save(attributeInstance);
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
	public void cloneOrCreateASI(@Nullable final Object to, @Nullable final Object from)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);

		final IAttributeSetInstanceAware toASIAware = attributeSetInstanceAwareFactoryService.createOrNull(to);
		if (toASIAware == null)
		{
			return;
		}
		final IAttributeSetInstanceAware fromASIAware = attributeSetInstanceAwareFactoryService.createOrNull(from);

		// #12728 Create new ASI if none was found in the source
		if (fromASIAware == null || fromASIAware.getM_AttributeSetInstance_ID() <= 0)
		{
			final ProductId productId = ProductId.ofRepoId(toASIAware.getM_Product_ID());
			I_M_AttributeSetInstance newASI = createASI(productId);
			toASIAware.setM_AttributeSetInstance_ID(newASI.getM_AttributeSetInstance_ID());
		}

		//
		// Clone the ASI if it exists
		else
		{
			final I_M_AttributeSetInstance asi = fromASIAware.getM_AttributeSetInstance();
			final I_M_AttributeSetInstance asiCopy = attributeDAO.copy(asi);
			toASIAware.setM_AttributeSetInstance(asiCopy);
		}
	}

	@Override
	public I_M_AttributeSetInstance createASIFromAttributeSet(
			@NonNull final IAttributeSet attributeSet,
			@Nullable final Predicate<I_M_Attribute> filter)
	{
		return createASIWithASFromProductAndInsertAttributeSet(
				null/* productId */,
				attributeSet,
				filter);
	}

	@Override
	public I_M_AttributeSetInstance createASIWithASFromProductAndInsertAttributeSet(
			@Nullable final ProductId productId,
			@NonNull final IAttributeSet attributeSet)
	{
		return createASIWithASFromProductAndInsertAttributeSet(
				productId,
				attributeSet,
				null/* filter */);
	}

	public I_M_AttributeSetInstance createASIWithASFromProductAndInsertAttributeSet(
			@Nullable final ProductId productId,
			@NonNull final IAttributeSet attributeSet,
			@Nullable final Predicate<I_M_Attribute> filter)
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

		final Predicate<I_M_Attribute> effectiveFilter = coalesce(filter, Predicates.alwaysTrue());

		for (final I_M_Attribute atttribute : attributesOrderedById)
		{
			if (effectiveFilter.test(atttribute))
			{
				final I_M_AttributeInstance attributeInstance = //
						createAttributeInstanceForAttributeAndAttributeSet(atttribute, attributeSet);

				attributeInstance.setM_AttributeSetInstance(attributeSetInstance);
				save(attributeInstance);
			}
		}
		return attributeSetInstance;
	}

	private I_M_AttributeInstance createAttributeInstanceForAttributeAndAttributeSet(
			@NonNull final I_M_Attribute attribute,
			@NonNull final IAttributeSet attributeSet)
	{
		final I_M_AttributeInstance attributeInstance = newInstance(I_M_AttributeInstance.class);
		attributeInstance.setM_Attribute_ID(attribute.getM_Attribute_ID());

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

			final AttributeListValue attributeValue = Services.get(IAttributeDAO.class).retrieveAttributeValueOrNull(attribute, stringValue);
			attributeInstance.setM_AttributeValue_ID(attributeValue != null ? attributeValue.getId().getRepoId() : -1);
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			final BigDecimal bigDecimalValue = attributeSet.getValueAsBigDecimal(attribute);
			attributeInstance.setValueNumber(bigDecimalValue);
		}

		return attributeInstance;
	}

	@Override
	public String getASIDescriptionById(final AttributeSetInstanceId asiId)
	{
		if (asiId.isNone())
		{
			return "";
		}

		I_M_AttributeSetInstance asi = Services.get(IAttributeDAO.class).getAttributeSetInstanceById(asiId);
		return asi != null ? asi.getDescription() : "";
	}

	@Override
	public void updateASIAttributeFromModel(@NonNull final AttributeCode attributeCode, @NonNull final Object fromModel)
	{
		UpdateASIAttributeFromModelCommand.builder()
				.attributeSetInstanceBL(this)
				//
				.attributeCode(attributeCode)
				.sourceModel(fromModel)
				//
				.execute();
	}

	@Override
	public boolean isStorageRelevant(@NonNull final I_M_AttributeInstance ai)
	{
		final IAttributesBL attributesService = Services.get(IAttributesBL.class);

		final AttributeId attributeId = AttributeId.ofRepoId(ai.getM_Attribute_ID());
		return attributesService.isStorageRelevant(attributeId);
	}

	@Override
	public ImmutableAttributeSet getImmutableAttributeSetById(@NonNull final AttributeSetInstanceId asiId)
	{
		return attributeDAO.getImmutableAttributeSetById(asiId);
	}

	@Override
	public void syncAttributesToASIAware(@NonNull final IAttributeSet attributeSet, @NonNull final IAttributeSetInstanceAware asiAware)
	{
		final AttributeSetInstanceId oldAsiId = AttributeSetInstanceId.ofRepoIdOrNone(asiAware.getM_AttributeSetInstance_ID());
		final AttributeSetInstanceId asiId;
		if (oldAsiId.isRegular())
		{
			final I_M_AttributeSetInstance asiCopy = ASICopy.newInstance(oldAsiId).copy();
			asiId = AttributeSetInstanceId.ofRepoId(asiCopy.getM_AttributeSetInstance_ID());
		}
		else
		{
			final I_M_AttributeSetInstance asiNew = createASI(ProductId.ofRepoId(asiAware.getM_Product_ID()));
			asiId = AttributeSetInstanceId.ofRepoId(asiNew.getM_AttributeSetInstance_ID());
		}

		for (final I_M_Attribute attributeRecord : attributeSet.getAttributes())
		{
			setAttributeInstanceValue(
					asiId,
					AttributeCode.ofString(attributeRecord.getValue()),
					attributeSet.getValue(attributeRecord));
		}
		asiAware.setM_AttributeSetInstance_ID(asiId.getRepoId());
	}

	@NonNull
	public AttributeSetInstanceId addAttributes(@NonNull final AddAttributesRequest request)
	{
		final AttributeSetInstanceId asiId;

		if (request.getExistingAttributeSetIdOrNone().isNone())
		{
			final I_M_AttributeSetInstance asiNew = createASI(request.getProductId());
			asiId = AttributeSetInstanceId.ofRepoId(asiNew.getM_AttributeSetInstance_ID());
		}
		else
		{
			final I_M_AttributeSetInstance asiCopy = ASICopy.newInstance(request.getExistingAttributeSetIdOrNone()).copy();
			asiId = AttributeSetInstanceId.ofRepoId(asiCopy.getM_AttributeSetInstance_ID());
		}

		request.getAttributeInstanceBasicInfos().forEach(attributeValue -> {
			setAttributeInstanceValue(asiId, attributeValue.getAttributeCode(), attributeValue.getValue());
		});

		return asiId;
	}

	@Override
	@Nullable
	public String getAttributeValueOrNull(@NonNull final AttributeCode attributeCode, @NonNull final AttributeSetInstanceId asiId)
	{
		if (asiId.isNone())
		{
			return null;
		}

		final AttributeId attributeId = attributeDAO.retrieveAttributeIdByValueOrNull(attributeCode);
		if (attributeId == null)
		{
			return null;
		}

		final I_M_AttributeInstance attributeInstance = attributeDAO.retrieveAttributeInstance(asiId, attributeId);

		if (attributeInstance == null)
		{
			return null;
		}

		return attributeInstance.getValue();
	}

}
