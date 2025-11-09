package org.adempiere.mm.attributes.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class AttributeSetInstanceDAO implements IAttributeSetInstanceDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_AttributeSetInstance getRecordById(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		return load(attributeSetInstanceId, I_M_AttributeSetInstance.class);
	}

	@Override
	public List<I_M_AttributeInstance> retrieveAttributeInstancesByAsiIds(final @NotNull Set<AttributeSetInstanceId> asiIds)
	{
		return queryBL.createQueryBuilder(I_M_AttributeInstance.class)
				.addInArrayFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, asiIds)
				.create()
				.list();
	}

	@Override
	public List<I_M_AttributeInstance> retrieveAttributeInstances(@Nullable final I_M_AttributeSetInstance attributeSetInstance)
	{
		if (attributeSetInstance == null)
		{
			return ImmutableList.of();
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstance.getM_AttributeSetInstance_ID());
		return retrieveAttributeInstances(asiId);
	}

	@Override
	public List<I_M_AttributeInstance> retrieveAttributeInstances(@NonNull final AttributeSetInstanceId asiId)
	{
		if (asiId.isNone())
		{
			return ImmutableList.of();
		}

		return queryBL.createQueryBuilder(I_M_AttributeInstance.class)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, asiId)
				.orderBy(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID) // at least to have a predictable order
				.create()
				.listImmutable(I_M_AttributeInstance.class);
	}

	@Override
	@Nullable
	public I_M_AttributeInstance retrieveAttributeInstance(
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final AttributeId attributeId)
	{
		if (attributeSetInstanceId == null || attributeSetInstanceId.isNone())
		{
			return null;
		}

		return queryBL.createQueryBuilder(I_M_AttributeInstance.class)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, attributeSetInstanceId)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.firstOnly(I_M_AttributeInstance.class);
	}

	@Override
	public Stream<I_M_AttributeInstance> streamAttributeInstances(
			@NonNull final AttributeSetInstanceId asiId,
			@NonNull final Set<AttributeId> attributeIds)
	{
		if (asiId.isNone() || attributeIds.isEmpty())
		{
			return Stream.of();
		}

		return queryBL.createQueryBuilder(I_M_AttributeInstance.class)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, asiId)
				.addInArrayFilter(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID, attributeIds)
				.stream();
	}

	@Override
	public I_M_AttributeInstance createNewAttributeInstance(
			final Properties ctx,
			final I_M_AttributeSetInstance asi,
			@NonNull final AttributeId attributeId,
			final String trxName)
	{
		final I_M_AttributeInstance ai = InterfaceWrapperHelper.create(ctx, I_M_AttributeInstance.class, trxName);
		ai.setAD_Org_ID(asi.getAD_Org_ID());
		ai.setM_AttributeSetInstance(asi);
		ai.setM_Attribute_ID(attributeId.getRepoId());

		return ai;
	}

	@Override
	public void save(@NonNull final I_M_AttributeSetInstance asi)
	{
		saveRecord(asi);
	}

	@Override
	public void save(@NonNull final I_M_AttributeInstance ai)
	{
		saveRecord(ai);
	}

	@Override
	public Set<AttributeId> getAttributeIdsByAttributeSetInstanceId(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_M_AttributeInstance.class)
				.addEqualsFilter(I_M_AttributeInstance.COLUMN_M_AttributeSetInstance_ID, attributeSetInstanceId)
				.create()
				.listDistinct(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID, Integer.class)
				.stream()
				.map(AttributeId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
