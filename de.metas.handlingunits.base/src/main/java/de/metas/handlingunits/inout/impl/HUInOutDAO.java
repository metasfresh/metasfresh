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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_InOut;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.attribute.Constants;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.inout.IHUInOutDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inout.IInOutDAO;

public class HUInOutDAO implements IHUInOutDAO
{
	@Override
	public List<I_M_InOutLine> retrievePackingMaterialLines(final I_M_InOut inOut)
	{
		return retrievePackingMaterialLinesQuery(inOut)
				.list(I_M_InOutLine.class);
	}

	private final IQuery<I_M_InOutLine> retrievePackingMaterialLinesQuery(final I_M_InOut inOut)
	{
		final IQueryBuilder<de.metas.handlingunits.model.I_M_InOutLine> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(de.metas.handlingunits.model.I_M_InOutLine.class, inOut)
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
				.match();
	}

	/**
	 * NOTE: keep in sync with {@link #retrieveCompletedReceiptLineOrNull(I_M_HU)} logic
	 */
	@Override
	public List<I_M_HU> retrieveHandlingUnits(final I_M_InOut inOut)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

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
	public List<I_M_HU> retrieveShippedHandlingUnits(final I_M_InOut inOut)
	{

		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

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

	/**
	 * NOTE: keep in sync with {@link #retrieveHandlingUnits(I_M_InOut)} logic
	 */
	@Override
	public I_M_InOutLine retrieveCompletedReceiptLineOrNull(final I_M_HU hu)
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
		final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
		final IDocumentBL docActionBL = Services.get(IDocumentBL.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(hu);
		final I_M_Attribute attrReceiptInOutLine = attributeDAO.retrieveAttributeByValue(ctx, Constants.ATTR_ReceiptInOutLine_ID, I_M_Attribute.class);

		final I_M_HU_Attribute huAttrReceiptInOutLine = huAttributesDAO.retrieveAttribute(hu, attrReceiptInOutLine);
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

		if (!docActionBL.isDocumentCompletedOrClosed(inoutLine.getM_InOut()))
		{
			return null;
		}
		return inoutLine;
	}

	@Override
	public List<I_M_InOutLine> retrieveInOutLinesForHU(final I_M_HU topLevelHU)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		return huAssignmentDAO.retrieveModelsForHU(topLevelHU, I_M_InOutLine.class);
	}

	@Override
	public List<I_M_HU> retrieveHUsForReceiptLineId(final int receiptLineId)
	{
		final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
		return huAssignmentDAO.retrieveTopLevelHUsForModel(TableRecordReference.of(I_M_InOutLine.Table_Name, receiptLineId), ITrx.TRXNAME_ThreadInherited);
	}
}
