package de.metas.frontend_testing.masterdata.attribute;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeUse;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Creates (or upserts, by {@code Value}) an {@code M_Attribute} - a LIST-type one included, with its allowed
 * values - and optionally links it into one or more existing {@code M_AttributeSet}s via {@code M_AttributeUse}.
 * <p>
 * No repository owns {@code M_Attribute}/{@code M_AttributeUse} creation (only {@code M_AttributeValue} has one,
 * {@link IAttributeDAO#createAttributeValue}, used below) - this mirrors the equivalent cucumber step defs
 * ({@code M_Attribute_StepDef}, {@code M_AttributeUse_StepDef}), which write the records the same way.
 */
@Builder
public class CreateAttributeCommand
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateAttributeRequest request;
	@NonNull private final Identifier identifier;

	public JsonCreateAttributeResponse execute()
	{
		final I_M_Attribute attributeRecord = createOrUpdateAttribute();
		createListValues(attributeRecord);
		linkToAttributeSets(attributeRecord);

		final AttributeId attributeId = AttributeId.ofRepoId(attributeRecord.getM_Attribute_ID());
		context.putIdentifier(identifier, attributeId);

		return JsonCreateAttributeResponse.builder()
				.id(attributeId)
				.attributeValue(attributeRecord.getValue())
				.build();
	}

	private I_M_Attribute createOrUpdateAttribute()
	{
		final String value = StringUtils.trimBlankToNull(request.getValue()) != null
				? StringUtils.trimBlankToNull(request.getValue())
				: identifier.toUniqueString();

		final I_M_Attribute existing = queryBL.createQueryBuilder(I_M_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Attribute.COLUMNNAME_Value, value)
				.create()
				.firstOnly(I_M_Attribute.class);

		final I_M_Attribute record = existing != null ? existing : InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		record.setValue(value);
		final String name = StringUtils.trimBlankToNull(request.getName());
		record.setName(name != null ? name : value);
		// Only set on a NEW record (defaulting to STRING) or when explicitly requested - upserting an existing
		// attribute (e.g. a shared fixture referenced by Value across scenarios) with an omitted type must NOT
		// silently downgrade it back to STRING, matching how the sibling isMandatory/isStorageRelevant fields
		// below are guarded.
		if (existing == null || request.getAttributeValueType() != null)
		{
			record.setAttributeValueType(resolveAttributeValueType(request.getAttributeValueType()).getCode());
		}

		if (request.getIsMandatory() != null)
		{
			record.setIsMandatory(request.getIsMandatory());
		}
		if (request.getIsStorageRelevant() != null)
		{
			record.setIsStorageRelevant(request.getIsStorageRelevant());
		}

		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}

	private void createListValues(@NonNull final I_M_Attribute attributeRecord)
	{
		final List<JsonCreateAttributeRequest.ListValue> listValues = request.getListValues();
		if (listValues == null || listValues.isEmpty())
		{
			return;
		}

		final AttributeId attributeId = AttributeId.ofRepoId(attributeRecord.getM_Attribute_ID());
		for (final JsonCreateAttributeRequest.ListValue listValue : listValues)
		{
			if (attributeDAO.retrieveAttributeValueOrNull(attributeId, listValue.getValue()) != null)
			{
				continue;
			}

			attributeDAO.createAttributeValue(AttributeListValueCreateRequest.builder()
					.attributeId(attributeId)
					.value(listValue.getValue())
					.name(StringUtils.trimBlankToNull(listValue.getName()) != null ? listValue.getName() : listValue.getValue())
					.build());
		}
	}

	private void linkToAttributeSets(@NonNull final I_M_Attribute attributeRecord)
	{
		final List<String> attributeSetNames = request.getAttributeSetNames();
		if (attributeSetNames == null || attributeSetNames.isEmpty())
		{
			return;
		}

		for (final String attributeSetName : attributeSetNames)
		{
			linkToAttributeSet(attributeRecord, attributeSetName);
		}
	}

	private void linkToAttributeSet(@NonNull final I_M_Attribute attributeRecord, @NonNull final String attributeSetName)
	{
		final I_M_AttributeSet attributeSet = queryBL.createQueryBuilder(I_M_AttributeSet.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_AttributeSet.COLUMNNAME_Name, attributeSetName)
				.create()
				.firstOnly(I_M_AttributeSet.class);
		if (attributeSet == null)
		{
			throw new AdempiereException("M_AttributeSet with name `" + attributeSetName + "` not found");
		}

		final I_M_AttributeUse existing = queryBL.createQueryBuilder(I_M_AttributeUse.class)
				.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID, attributeSet.getM_AttributeSet_ID())
				.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_Attribute_ID, attributeRecord.getM_Attribute_ID())
				.create()
				.firstOnly(I_M_AttributeUse.class);
		if (existing != null)
		{
			if (!existing.isActive())
			{
				existing.setIsActive(true);
				InterfaceWrapperHelper.saveRecord(existing);
			}
			return;
		}

		final int nextSeqNo = queryBL.createQueryBuilder(I_M_AttributeUse.class)
				.addEqualsFilter(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID, attributeSet.getM_AttributeSet_ID())
				.create()
				.maxInt(I_M_AttributeUse.COLUMNNAME_SeqNo) + 10;

		final I_M_AttributeUse attributeUse = InterfaceWrapperHelper.newInstance(I_M_AttributeUse.class);
		attributeUse.setM_AttributeSet_ID(attributeSet.getM_AttributeSet_ID());
		attributeUse.setM_Attribute_ID(attributeRecord.getM_Attribute_ID());
		attributeUse.setSeqNo(nextSeqNo);
		InterfaceWrapperHelper.saveRecord(attributeUse);
	}

	/**
	 * Accepts either the enum name ({@code "STRING"}, {@code "NUMBER"}, {@code "DATE"}, {@code "LIST"}) or the
	 * AD ref-list code ({@code "S"}, {@code "N"}, {@code "D"}, {@code "L"}). Defaults to {@link AttributeValueType#STRING}.
	 */
	private static AttributeValueType resolveAttributeValueType(@Nullable final String requestType)
	{
		final String trimmed = StringUtils.trimBlankToNull(requestType);
		if (trimmed == null)
		{
			return AttributeValueType.STRING;
		}
		for (final AttributeValueType candidate : AttributeValueType.values())
		{
			if (candidate.name().equalsIgnoreCase(trimmed))
			{
				return candidate;
			}
		}
		return AttributeValueType.ofCode(trimmed);
	}
}
