package org.adempiere.mm.attributes.api.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.ASICopy;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.mm.attributes.asi_aware.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.asi_aware.factory.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.Null;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

public class AttributeSetInstanceBL implements IAttributeSetInstanceBL
{
	private static final Logger logger = LogManager.getLogger(AttributeSetInstanceBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IAttributeSetInstanceDAO asiDAO = Services.get(IAttributeSetInstanceDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);

	@Override
	public I_M_AttributeSetInstance getById(@NonNull final AttributeSetInstanceId asiId)
	{
		return asiDAO.getRecordById(asiId);
	}

	@Override
	public I_M_AttributeSetInstance copy(@NonNull final I_M_AttributeSetInstance fromASI)
	{
		return ASICopy.newInstance(fromASI).copy();
	}

	@Override
	public ASICopy prepareCopy(final I_M_AttributeSetInstance fromASI)
	{
		return ASICopy.newInstance(fromASI);
	}

	@Override
	public AttributeSetInstanceId copy(@NonNull final AttributeSetInstanceId asiSourceId)
	{
		final I_M_AttributeSetInstance asiSourceRecord = getById(asiSourceId);
		final I_M_AttributeSetInstance asiTargetRecord = copy(asiSourceRecord);
		return AttributeSetInstanceId.ofRepoId(asiTargetRecord.getM_AttributeSetInstance_ID());
	}

	@Override
	public boolean nullSafeASIEquals(
			@Nullable final AttributeSetInstanceId firstASIId,
			@Nullable final AttributeSetInstanceId secondASIId)
	{
		if (firstASIId == null && secondASIId == null)
		{
			return true;
		}

		if (firstASIId == null || secondASIId == null)
		{
			return false;
		}

		final ImmutableAttributeSet firstAttributeSet = getImmutableAttributeSetById(firstASIId);
		final ImmutableAttributeSet secondAttributeSet = getImmutableAttributeSetById(secondASIId);

		return firstAttributeSet.equals(secondAttributeSet);
	}

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
		// use the method from the service so if the product doesn't have an AS, it can be taken from the product category
		final AttributeSetId productAttributeSetId = productBL.getAttributeSetId(productId);

		return createASI(productAttributeSetId);
	}

	@NotNull
	private I_M_AttributeSetInstance createASI(@NonNull final AttributeSetId productAttributeSetId)
	{
		final I_M_AttributeSetInstance asiNew = newInstance(I_M_AttributeSetInstance.class);
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
		I_M_AttributeInstance attributeInstance = getAttributeInstance(asiId, attributeId);
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
		final I_M_AttributeInstance instanceExisting = getAttributeInstance(asiId, attributeId);
		if (instanceExisting != null)
		{
			return instanceExisting;
		}

		// Create New
		final I_M_AttributeInstance instanceNew = newInstance(I_M_AttributeInstance.class);
		instanceNew.setM_AttributeSetInstance_ID(asiId.getRepoId());
		instanceNew.setM_Attribute_ID(attributeId.getRepoId());
		save(instanceNew);
		return instanceNew;
	}

	private void createOrUpdateAttributeInstances(
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final List<CreateAttributeInstanceReq> requests)
	{
		if (requests.isEmpty()) {return;}

		final ImmutableMap<AttributeCode, CreateAttributeInstanceReq> requestsByAttributeCode = Maps.uniqueIndex(requests, CreateAttributeInstanceReq::getAttributeCode);
		final Collection<Attribute> attributes = attributeDAO.getAttributesByCodes(requestsByAttributeCode.keySet());
		final ImmutableSet<AttributeId> attributeIds = attributes.stream().map(Attribute::getAttributeId).collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<AttributeId, I_M_AttributeInstance> aiRecords = asiDAO.streamAttributeInstances(asiId, attributeIds)
				.collect(ImmutableMap.toImmutableMap(
						aiRecord -> AttributeId.ofRepoId(aiRecord.getM_Attribute_ID()),
						aiRecord -> aiRecord
				));

		attributes.forEach((attribute) -> {
			final CreateAttributeInstanceReq request = requestsByAttributeCode.get(attribute.getAttributeCode());
			I_M_AttributeInstance aiRecord = aiRecords.get(attribute.getAttributeId());
			if (aiRecord == null)
			{
				aiRecord = InterfaceWrapperHelper.newInstance(I_M_AttributeInstance.class);
				aiRecord.setM_AttributeSetInstance_ID(asiId.getRepoId());
				aiRecord.setM_Attribute_ID(attribute.getAttributeId().getRepoId());
			}

			updateAttributeInstanceRecord(aiRecord, request.getValue(), attribute);
			asiDAO.save(aiRecord);
		});

		//attributes.forEach(attribute -> setAttributeInstanceValue(asiId, attribute.getAttributeCode(), attribute.getValue()));
	}

	@Override
	public void setAttributeInstanceValue(
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final AttributeCode attributeCode,
			@Nullable final Object value)
	{
		final Attribute attribute = attributeDAO.getAttributeByCode(attributeCode);
		final I_M_AttributeInstance attributeInstance = getCreateAttributeInstance(asiId, attribute.getAttributeId());

		setAttributeInstanceValue(attribute, attributeInstance, value);
	}

	@Override
	public void setAttributeInstanceValue(
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final AttributeId attributeId,
			@Nullable final Object value)
	{

		final Attribute attribute = attributeDAO.getAttributeById(attributeId);
		final I_M_AttributeInstance attributeInstance = getCreateAttributeInstance(asiId, attributeId);

		setAttributeInstanceValue(attribute, attributeInstance, value);
	}

	private void setAttributeInstanceValue(
			@NonNull final Attribute attribute,
			@NonNull final I_M_AttributeInstance attributeInstance,
			@Nullable final Object value)
	{
		updateAttributeInstanceRecord(attributeInstance, value, attribute);
		save(attributeInstance);
	}

	private static void updateAttributeInstanceRecord(
			final @NotNull I_M_AttributeInstance attributeInstance,
			final @Nullable Object value,
			final @NotNull Attribute attribute)
	{
		final AttributeValueType valueType = attribute.getValueType();
		if (AttributeValueType.DATE.equals(valueType))
		{
			attributeInstance.setValueDate(value == null ? null : Env.parseTimestamp(value.toString()));
		}
		else if (AttributeValueType.NUMBER.equals(valueType))
		{
			attributeInstance.setValueNumber(value == null ? null : NumberUtils.asBigDecimal(value.toString(), null));
		}
		else if (AttributeValueType.STRING.equals(valueType)
				|| AttributeValueType.LIST.equals(valueType))
		{
			attributeInstance.setValue(value == null ? null : value.toString());
		}
		else
		{
			throw new IllegalArgumentException("@NotSupported@ @AttributeValueType@=" + value + ", @M_Attribute_ID@=" + attribute.getAttributeCode());
		}
	}

	@Override
	public void cloneASI(final Object to, final Object from)
	{
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
			final I_M_AttributeSetInstance asiCopy = copy(asi);
			toASIAware.setM_AttributeSetInstance(asiCopy);
		}
	}

	@Override
	public void cloneOrCreateASI(@Nullable final Object to, @Nullable final Object from)
	{
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
			final I_M_AttributeSetInstance asiCopy = copy(asi);
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

		final Predicate<I_M_Attribute> effectiveFilter = coalesceNotNull(filter, Predicates.alwaysTrue());

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

			final AttributeListValue attributeValue = attributeDAO.retrieveAttributeValueOrNull(attribute, stringValue);
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

		I_M_AttributeSetInstance asi = getById(asiId);
		return asi != null ? asi.getDescription() : "";
	}

	@Override
	public void updateASIAttributeFromModel(@NonNull final AttributeCode attributeCode, @NonNull final Object fromModel)
	{
		UpdateASIAttributeFromModelCommand.builder()
				.attributeSetInstanceAwareFactoryService(attributeSetInstanceAwareFactoryService)
				.attributeDAO(attributeDAO)
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
		final AttributeId attributeId = AttributeId.ofRepoId(ai.getM_Attribute_ID());
		return attributeDAO.getAttributeRecordById(attributeId).isStorageRelevant();
	}

	@Override
	public ImmutableAttributeSet getImmutableAttributeSetById(@NonNull final AttributeSetInstanceId asiId)
	{
		if (asiId.isNone())
		{
			return ImmutableAttributeSet.EMPTY;
		}

		final List<I_M_AttributeInstance> instances = getAttributeInstances(asiId);
		return toImmutableAttributeSet(instances);
	}

	private ImmutableAttributeSet toImmutableAttributeSet(final Collection<I_M_AttributeInstance> instances)
	{
		final ImmutableAttributeSet.Builder builder = ImmutableAttributeSet.builder();
		for (final I_M_AttributeInstance instance : instances)
		{
			final AttributeId attributeId = AttributeId.ofRepoId(instance.getM_Attribute_ID());
			final Object value = extractAttributeInstanceValue(instance);
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(instance.getM_AttributeValue_ID());
			builder.attributeValue(attributeId, value, attributeValueId);
		}
		return builder.build();
	}

	private Object extractAttributeInstanceValue(final I_M_AttributeInstance record)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(record.getM_Attribute_ID());
		final Attribute attribute = attributeDAO.getAttributeById(attributeId);
		final AttributeValueType valueType = attribute.getValueType();
		if (AttributeValueType.DATE.equals(valueType))
		{
			return record.getValueDate();
		}
		else if (AttributeValueType.LIST.equals(valueType))
		{
			return record.getValue();
		}
		else if (AttributeValueType.NUMBER.equals(valueType))
		{
			return record.getValueNumber();
		}
		else if (AttributeValueType.STRING.equals(valueType))
		{
			return record.getValue();
		}
		else
		{
			throw new AdempiereException("Unsupported attributeValueType=" + valueType)
					.setParameter("record", record)
					.setParameter("attribute", attribute)
					.appendParametersToMessage();
		}
	}

	@Override
	public Map<AttributeSetInstanceId, ImmutableAttributeSet> getAttributesForASIs(@NonNull final Set<AttributeSetInstanceId> asiIds)
	{
		Check.assumeNotEmpty(asiIds, "asiIds is not empty");

		final Map<AttributeSetInstanceId, List<I_M_AttributeInstance>> instancesByAsiId = asiDAO.retrieveAttributeInstancesByAsiIds(asiIds)
				.stream()
				.collect(Collectors.groupingBy(ai -> AttributeSetInstanceId.ofRepoId(ai.getM_AttributeSetInstance_ID())));

		final ImmutableMap.Builder<AttributeSetInstanceId, ImmutableAttributeSet> result = ImmutableMap.builder();
		instancesByAsiId.forEach((asiId, instances) -> result.put(asiId, toImmutableAttributeSet(instances)));

		return result.build();
	}

	@Override
	public List<I_M_AttributeInstance> getAttributeInstances(final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (attributeSetInstanceId.isNone())
		{
			return ImmutableList.of();
		}

		final I_M_AttributeSetInstance asi = getById(attributeSetInstanceId);
		return getAttributeInstances(asi);
	}

	@Override
	public List<I_M_AttributeInstance> getAttributeInstances(final I_M_AttributeSetInstance attributeSetInstance)
	{
		//
		// Ordering by M_AttributeUse.SeqNo
		final AttributeSetId attributeSetId = AttributeSetId.ofRepoIdOrNone(attributeSetInstance.getM_AttributeSet_ID());
		final Comparator<I_M_AttributeInstance> order = createAttributeInstanceOrderComparator(attributeSetId);

		return asiDAO.retrieveAttributeInstances(attributeSetInstance)
				.stream()
				.sorted(order)
				.collect(ImmutableList.toImmutableList());
	}

	private Comparator<I_M_AttributeInstance> createAttributeInstanceOrderComparator(final AttributeSetId attributeSetId)
	{
		final List<AttributeId> attributeIds = attributeDAO.getAttributeSetDescriptorById(attributeSetId).getAttributeIdsInOrder();
		if (attributeIds.isEmpty())
		{
			return Comparator.comparing(I_M_AttributeInstance::getM_Attribute_ID);
		}

		return FixedOrderByKeyComparator.notMatchedAtTheEnd(
				attributeIds,
				ai -> AttributeId.ofRepoId(ai.getM_Attribute_ID()));
	}

	@Nullable
	@Override
	public I_M_AttributeInstance getAttributeInstance(
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final AttributeId attributeId)
	{
		return asiDAO.retrieveAttributeInstance(attributeSetInstanceId, attributeId);
	}

	@Override
	public I_M_AttributeInstance createNewAttributeInstance(
			final Properties ctx,
			final I_M_AttributeSetInstance asi,
			@NonNull final AttributeId attributeId,
			final String trxName)
	{
		return asiDAO.createNewAttributeInstance(ctx, asi, attributeId, trxName);
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

		createOrUpdateAttributeInstances(asiId, request.getAttributeInstanceBasicInfos());

		return asiId;
	}

	@Override
	public void save(@NonNull final I_M_AttributeSetInstance asi)
	{
		asiDAO.save(asi);
	}

	@Override
	public void save(@NonNull final I_M_AttributeInstance ai)
	{
		asiDAO.save(ai);
	}

	@Override
	public Set<AttributeId> getAttributeIdsByAttributeSetInstanceId(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		return asiDAO.getAttributeIdsByAttributeSetInstanceId(attributeSetInstanceId);
	}

	@Override
	public AttributeSetInstanceId setInitialAttributes(
			@NonNull final ProductId productId,
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final Evaluatee evalCtx)
	{
		final ImmutableList<Attribute> attributes = productBL.getAttributeSet(productId)
				.getAttributesInOrder()
				.stream()
				.filter(Attribute::isDefaultValueSet)
				.collect(ImmutableList.toImmutableList());
		if (attributes.isEmpty())
		{
			return asiId;
		}

		final ImmutableAttributeSet existingASI = getImmutableAttributeSetById(asiId);

		final ArrayList<CreateAttributeInstanceReq> attributesToInit = new ArrayList<>();
		for (final Attribute attribute : attributes)
		{
			if (existingASI.isValueSet(attribute))
			{
				continue;
			}

			generateDefaultValue(attribute, evalCtx)
					.ifPresent(defaultValue -> attributesToInit.add(
							CreateAttributeInstanceReq.builder()
									.attributeCode(attribute.getAttributeCode())
									.value(Null.unbox(defaultValue))
									.build()
					));
		}

		if (attributesToInit.isEmpty()) {return AttributeSetInstanceId.NONE;}

		return addAttributes(
				AddAttributesRequest.builder()
						.productId(productId)
						.existingAttributeSetIdOrNone(AttributeSetInstanceId.NONE)
						.attributeInstanceBasicInfos(attributesToInit)
						.build()
		);
	}

	private static Optional<Object> generateDefaultValue(final Attribute attribute, final Evaluatee evalCtx)
	{
		try
		{
			if (attribute.getDefaultValueSQL() != null)
			{
				final String defaultValueSQL = attribute.getDefaultValueSQL().evaluate(evalCtx, IExpressionEvaluator.OnVariableNotFound.Fail);
				final Object defaultValue = attribute.getValueType().map(new AttributeValueType.CaseMapper<Object>()
				{
					@Override
					public Object string()
					{
						return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, defaultValueSQL);
					}

					@Override
					public Object number()
					{
						return DB.getSQLValueBDEx(ITrx.TRXNAME_ThreadInherited, defaultValueSQL);
					}

					@Override
					public Object date()
					{
						return DB.getSQLValueTSEx(ITrx.TRXNAME_ThreadInherited, defaultValueSQL);
					}

					@Override
					public Object list()
					{
						// TODO: resolve the M_AttributeValue
						return DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, defaultValueSQL);
					}
				});

				return Optional.of(Null.box(defaultValue));
			}
			else
			{
				return Optional.empty();
			}
		}
		catch (Exception ex)
		{
			logger.warn("Failed generating default value for {}. Returning empty.", attribute, ex);
			return Optional.empty();
		}
	}
}
