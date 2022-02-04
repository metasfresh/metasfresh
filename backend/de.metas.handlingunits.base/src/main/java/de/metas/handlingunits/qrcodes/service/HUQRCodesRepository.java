package de.metas.handlingunits.qrcodes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuItemId;
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
		final HuItemId aggregateHUItemId = HuItemId.ofRepoIdOrNull(record.getAggregate_HU_Item_ID());
		if (huId == null && aggregateHUItemId == null)
		{
			return Optional.empty();
		}

		return Optional.of(HUQRCodeAssignment.builder()
				.id(HUQRCodeUniqueId.ofJson(record.getUniqueId()))
				.huId(huId)
				.aggregateHUItemId(aggregateHUItemId)
				.build());
	}

	public void createNew(@NonNull HUQRCode qrCode, @Nullable HUQRCodeAssignment assignment)
	{
		if (assignment != null && !HUQRCodeUniqueId.equals(qrCode.getId(), assignment.getId()))
		{
			throw new AdempiereException("QR Code and Assignment IDs shall match");
		}

		final I_M_HU_QRCode record = InterfaceWrapperHelper.newInstance(I_M_HU_QRCode.class);
		record.setUniqueId(qrCode.getId().getAsString());
		record.setattributes(toJsonString(qrCode));

		if (assignment != null)
		{
			record.setM_HU_ID(HuId.toRepoId(assignment.getHuId()));
			record.setAggregate_HU_Item_ID(HuItemId.toRepoId(assignment.getAggregateHUItemId()));
		}

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
