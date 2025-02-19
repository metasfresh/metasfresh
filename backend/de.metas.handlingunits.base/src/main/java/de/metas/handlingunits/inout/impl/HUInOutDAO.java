package de.metas.handlingunits.inout.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class HUInOutDAO implements IHUInOutDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);

	@Override
	public List<I_M_InOutLine> retrievePackingMaterialLines(final I_M_InOut inOut)
	{
		return retrievePackingMaterialLinesQuery(inOut)
				.list(I_M_InOutLine.class);
	}

	private IQuery<I_M_InOutLine> retrievePackingMaterialLinesQuery(final I_M_InOut inOut)
	{
		final IQueryBuilder<de.metas.handlingunits.model.I_M_InOutLine> queryBuilder = queryBL.createQueryBuilder(de.metas.handlingunits.model.I_M_InOutLine.class, inOut)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_M_InOutLine.COLUMNNAME_M_InOut_ID, inOut.getM_InOut_ID())
				.addEqualsFilter(de.metas.inout.model.I_M_InOutLine.COLUMNNAME_IsPackagingMaterial, true);

		queryBuilder.orderBy().addColumn(org.compiere.model.I_M_InOutLine.COLUMNNAME_Line);

		return queryBuilder
				.create();
	}

	@Override
	public boolean hasPackingMaterialLines(final I_M_InOut inOut)
	{
		return retrievePackingMaterialLinesQuery(inOut)
				.anyMatch();
	}

	/**
	 * NOTE: keep in sync with {@link #retrieveCompletedReceiptLineOrNull(I_M_HU)} logic
	 */
	@Override
	public List<I_M_HU> retrieveHandlingUnits(final I_M_InOut inOut)
	{
		final List<I_M_InOutLine> lines = inOutDAO.retrieveLines(inOut, I_M_InOutLine.class);

		final LinkedHashMap<Integer, I_M_HU> hus = new LinkedHashMap<>();
		for (final I_M_InOutLine line : lines)
		{
			final List<I_M_HU> lineHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(line);
			for (final I_M_HU hu : lineHUs)
			{
				hus.put(hu.getM_HU_ID(), hu);
			}
		}
		return new ArrayList<>(hus.values());
	}

	@Override
	public List<I_M_HU> retrieveHandlingUnitsByInOutLineId(@NonNull final InOutLineId inOutLineId)
	{
		return huAssignmentDAO.retrieveTopLevelHUsForModel(inOutDAO.getLineByIdInTrx(inOutLineId));
	}

	@Override
	public List<I_M_HU> retrieveShippedHandlingUnits(final I_M_InOut inOut)
	{
		final List<I_M_InOutLine> lines = inOutDAO.retrieveLines(inOut, I_M_InOutLine.class);

		final LinkedHashMap<Integer, I_M_HU> hus = new LinkedHashMap<>();
		for (final I_M_InOutLine line : lines)
		{
			final List<I_M_HU> lineHUs = huAssignmentDAO.retrieveTopLevelHUsForModel(line);
			for (final I_M_HU hu : lineHUs)
			{
				if (X_M_HU.HUSTATUS_Shipped.equals(hu.getHUStatus()))
				{
					hus.put(hu.getM_HU_ID(), hu);
				}
			}
		}
		return new ArrayList<>(hus.values());
	}

	@Override
	@NonNull
	public Map<InOutLineId, List<I_M_HU>> retrieveShippedHUsByShipmentLineId(@NonNull final Set<InOutLineId> shipmentLineIds)
	{
		final List<I_M_InOutLine> inOutLines = inOutDAO.getLinesByIds(shipmentLineIds, I_M_InOutLine.class);

		return inOutLines
				.stream()
				.map(inOutLine -> {
					final InOutLineId inOutLineId = InOutLineId.ofRepoId(inOutLine.getM_InOutLine_ID());
					return new HashMap.SimpleEntry<>(inOutLineId, getShippedHUsByShipmentLine(inOutLine));
				})
				.filter(entry -> !entry.getValue().isEmpty())
				.collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	/**
	 * NOTE: keep in sync with {@link #retrieveHandlingUnits(I_M_InOut)} logic
	 */
	@Override
	@Nullable
	public I_M_InOutLine retrieveCompletedReceiptLineOrNull(final I_M_HU hu)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(hu);
		final AttributeId receiptInOutLineAttributeId = attributeDAO.getAttributeIdByCode(HUAttributeConstants.ATTR_ReceiptInOutLine_ID);

		final I_M_HU_Attribute huAttrReceiptInOutLine = huAttributesDAO.retrieveAttribute(hu, receiptInOutLineAttributeId);
		if (huAttrReceiptInOutLine == null
				|| huAttrReceiptInOutLine.getValueNumber() == null
				|| huAttrReceiptInOutLine.getValueNumber().signum() <= 0)
		{
			return null;
		}

		final int inOutLineId = huAttrReceiptInOutLine.getValueNumber().intValue();
		final I_M_InOutLine inoutLine = InterfaceWrapperHelper.create(ctx, inOutLineId, I_M_InOutLine.class, ITrx.TRXNAME_ThreadInherited);
		if (!inoutLine.isActive())
		{
			return null;
		}

		final I_M_InOut inOut = inoutLine.getM_InOut();
		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(inOut.getDocStatus());
		if (!docStatus.isCompletedOrClosed())
		{
			return null;
		}
		return inoutLine;
	}

	@Override
	public List<I_M_InOutLine> retrieveInOutLinesForHU(final I_M_HU topLevelHU)
	{
		return huAssignmentDAO.retrieveModelsForHU(topLevelHU, I_M_InOutLine.class);
	}

	@Override
	public List<I_M_HU> retrieveHUsForReceiptLineId(final int receiptLineId)
	{
		return huAssignmentDAO.retrieveTopLevelHUsForModel(TableRecordReference.of(I_M_InOutLine.Table_Name, receiptLineId), ITrx.TRXNAME_ThreadInherited);
	}

	@NonNull
	private List<I_M_HU> getShippedHUsByShipmentLine(@NonNull final I_M_InOutLine inOutLine)
	{
		return huAssignmentDAO.retrieveTopLevelHUsForModel(inOutLine)
				.stream()
				.filter(hu -> X_M_HU.HUSTATUS_Shipped.equals(hu.getHUStatus()))
				.collect(ImmutableList.toImmutableList());
	}
}
