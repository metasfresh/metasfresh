package de.metas.handlingunits.qrcodes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.JsonObjectMapperHolder;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_QRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAssignment;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
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
		final HuId huId = HuId.ofRepoIdOrNull(record.getM_HU_ID());
		if (huId == null)
		{
			return Optional.empty();
		}

		final HUQRCodeUniqueId id = HUQRCodeUniqueId.ofJson(record.getUniqueId());
		return Optional.of(HUQRCodeAssignment.of(id, huId));
	}

	public void createNew(@NonNull HUQRCode qrCode, @Nullable HuId huId)
	{
		final I_M_HU_QRCode record = InterfaceWrapperHelper.newInstance(I_M_HU_QRCode.class);
		record.setUniqueId(qrCode.getId().getAsString());
		record.setattributes(toJsonString(qrCode));
		record.setM_HU_ID(HuId.toRepoId(huId));
		InterfaceWrapperHelper.save(record);
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

	public ImmutableSetMultimap<HuId, HUQRCode> getQRCodeByHuIds(@NonNull final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_QRCode.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						record -> HuId.ofRepoId(record.getM_HU_ID()),
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
