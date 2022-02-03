package de.metas.handlingunits.qrcodes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.JsonObjectMapperHolder;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
import de.metas.handlingunits.model.I_M_HU_QRCode;
import de.metas.handlingunits.qrcodes.model.HUOrAggregatedTUItemId;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAssignment;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Repository
public class HUQRCodesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

	public Optional<HUQRCodeAssignment> getHUAssignmentByQRCode(@NonNull final HUQRCode huQRCode)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addEqualsFilter(I_M_HU_QRCode.COLUMNNAME_UniqueId, huQRCode.getId().getAsString())
				.create()
				.firstOnlyOptional(I_M_HU_QRCode.class)
				.flatMap(HUQRCodesRepository::toHUQRCodeAssignment);
	}

	private static Optional<HUQRCodeAssignment> toHUQRCodeAssignment(final I_M_HU_QRCode record)
	{
		final HUOrAggregatedTUItemId huOrAggregatedTUItemId = extractHUOrAggregatedTUItemIdOrNull(record);
		if (huOrAggregatedTUItemId == null)
		{
			return Optional.empty();
		}

		final HUQRCodeUniqueId id = HUQRCodeUniqueId.ofJson(record.getUniqueId());
		return Optional.of(HUQRCodeAssignment.of(id, huOrAggregatedTUItemId));
	}

	@Nullable
	private static HUOrAggregatedTUItemId extractHUOrAggregatedTUItemIdOrNull(final I_M_HU_QRCode record)
	{
		final HuId huId = HuId.ofRepoIdOrNull(record.getM_HU_ID());
		final HuItemId aggregatedTUItemId = HuItemId.ofRepoIdOrNull(record.getAggregate_HU_Item_ID());
		if (huId != null && aggregatedTUItemId != null)
		{
			throw new AdempiereException("HU_ID and Aggregate_HU_Item_ID shall not be set at the same time: " + record);
		}
		else if (huId != null)
		{
			return HUOrAggregatedTUItemId.ofHuId(huId);
		}
		else if (aggregatedTUItemId != null)
		{
			return HUOrAggregatedTUItemId.ofAggregatedTUItemId(aggregatedTUItemId);
		}
		else // both null
		{
			return null;
		}
	}

	public void createNew(@NonNull HUQRCode qrCode, @Nullable HUOrAggregatedTUItemId huOrAggregatedTUItemId)
	{
		final I_M_HU_QRCode record = InterfaceWrapperHelper.newInstance(I_M_HU_QRCode.class);
		record.setUniqueId(qrCode.getId().getAsString());
		record.setattributes(toJsonString(qrCode));
		updateRecordAssignment(record, huOrAggregatedTUItemId);
		InterfaceWrapperHelper.save(record);
	}

	private void updateRecordAssignment(
			final @NonNull I_M_HU_QRCode record,
			final @Nullable HUOrAggregatedTUItemId huOrAggregatedTUItemId)
	{
		final HuId huId = huOrAggregatedTUItemId != null ? huOrAggregatedTUItemId.getHuIdOrNull() : null;
		record.setM_HU_ID(HuId.toRepoId(huId));

		final HuItemId aggregatedTUItemId = huOrAggregatedTUItemId != null ? huOrAggregatedTUItemId.getAggregateTUItemIdOrNull() : null;
		record.setAggregate_HU_Item_ID(HuItemId.toRepoId(aggregatedTUItemId));
	}

	private String toJsonString(final @NonNull HUQRCode qrCode)
	{
		try
		{
			return jsonObjectMapper.writeValueAsString(qrCode);
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Failed converting HUQRCode to JSON", e)
					.setParameter("qrCode", qrCode);
		}
	}

	public Optional<HUQRCode> getQRCodeByHuId(@NonNull final HuId huId)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addEqualsFilter(I_M_HU_QRCode.COLUMNNAME_M_HU_ID, huId)
				.create()
				.firstOnlyOptional(I_M_HU_QRCode.class)
				.map(this::toHUQRCode);
	}

	public ImmutableSetMultimap<HUOrAggregatedTUItemId, HUQRCode> getQRCodeByHuOrAggregatedTUItemIds(@NonNull final Collection<HUOrAggregatedTUItemId> huOrAggregatedTUItemIds)
	{
		if (huOrAggregatedTUItemIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final IQueryBuilder<I_M_HU_QRCode> queryBuilder = queryBL.createQueryBuilder(I_M_HU_QRCode.class);

		final HashSet<HuId> huIds = new HashSet<>();
		final HashSet<HuItemId> aggregatedTUItemIds = new HashSet<>();
		for (HUOrAggregatedTUItemId huOrAggregatedTUItemId : huOrAggregatedTUItemIds)
		{
			if (huOrAggregatedTUItemId.isHuId())
			{
				huIds.add(huOrAggregatedTUItemId.getHuId());
			}
			if (huOrAggregatedTUItemId.isAggregatedTU())
			{
				aggregatedTUItemIds.add(huOrAggregatedTUItemId.getAggregatedTUItemId());
			}
		}

		final ICompositeQueryFilter<I_M_HU_QRCode> huOrAggregatedTUItemsFilter = queryBuilder.addCompositeQueryFilter().setJoinOr();
		if (!huIds.isEmpty())
		{
			huOrAggregatedTUItemsFilter.addInArrayFilter(I_M_HU_QRCode.COLUMNNAME_M_HU_ID, huIds);
		}
		if (!aggregatedTUItemIds.isEmpty())
		{
			huOrAggregatedTUItemsFilter.addInArrayFilter(I_M_HU_QRCode.COLUMNNAME_Aggregate_HU_Item_ID, aggregatedTUItemIds);
		}

		return queryBuilder
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						HUQRCodesRepository::extractHUOrAggregatedTUItemIdOrNull,
						this::toHUQRCode));
	}

	private HUQRCode toHUQRCode(final I_M_HU_QRCode record)
	{
		try
		{
			return jsonObjectMapper.readValue(record.getattributes(), HUQRCode.class);
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Failed converting JSON to HUQRCode", e)
					.setParameter("json", record.getattributes());
		}
	}

}
