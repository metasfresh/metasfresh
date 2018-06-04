package de.metas.handlingunits.receiptschedule.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.expectations.HUAssignmentExpectation;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutDAO;

/**
 * @author al
 */
public class InOutLineExpectation<ParentExpectationType> extends de.metas.inoutcandidate.expectations.InOutLineExpectation<ParentExpectationType>
{
	private Integer qtyEnteredTU = null;

	private Integer referencedPackagingMaterialLineIdx;
	private final List<HUAssignmentExpectation<InOutLineExpectation<ParentExpectationType>>> huAssignmentExpectations = new ArrayList<>();

	public InOutLineExpectation(final ParentExpectationType parent, final IContextAware ctx)
	{
		super(parent, ctx);
	}

	public InOutLineExpectation<ParentExpectationType> assertExpected(final I_M_InOutLine inoutLine)
	{
		super.assertExpected(inoutLine);

		final ErrorMessage message = newErrorMessage()
				.addContextInfo(inoutLine);
		if (qtyEnteredTU != null)
		{
			assertEquals(message.expect("QtyEnteredTU"), qtyEnteredTU.intValue(), inoutLine.getQtyEnteredTU().intValueExact());
		}

		//
		// Check HU assignments expectations
		for (final HUAssignmentExpectation<InOutLineExpectation<ParentExpectationType>> huAssignmentExpectation : huAssignmentExpectations)
		{
			huAssignmentExpectation.tableAndRecordIdFromModel(inoutLine);
			huAssignmentExpectation.assertExpected(message);
		}

		if (referencedPackagingMaterialLineIdx != null)
		{
			if (referencedPackagingMaterialLineIdx <= 0)
			{
				assertEquals(message.expect("Not referencing packaging line"), 0, inoutLine.getM_PackingMaterial_InOutLine_ID());
			}
			else
			{
				final List<I_M_InOutLine> documentLines = Services.get(IInOutDAO.class).retrieveLines(inoutLine.getM_InOut(), I_M_InOutLine.class);
				final I_M_InOutLine packagingLine = documentLines.get(referencedPackagingMaterialLineIdx);

				assertEquals(message.expect("Referencing packaging line"), packagingLine.getM_InOutLine_ID(), inoutLine.getM_PackingMaterial_InOutLine_ID());
			}
		}

		return this;
	}

	@Override
	protected void populateModel(final de.metas.inout.model.I_M_InOutLine inoutLine)
	{
		final I_M_InOutLine huInOutLine = InterfaceWrapperHelper.create(inoutLine, I_M_InOutLine.class);
		if (qtyEnteredTU != null)
		{
			huInOutLine.setQtyEnteredTU(BigDecimal.valueOf(qtyEnteredTU));
		}
	}

	public InOutLineExpectation<ParentExpectationType> qtyEnteredTU(final int qtyEnteredTU)
	{
		this.qtyEnteredTU = qtyEnteredTU;
		return this;
	}

	/**
	 * 0 means 1st line etc
	 * 
	 * @param referencedPackagingMaterialLineIdx
	 * @return
	 */
	public InOutLineExpectation<ParentExpectationType> referencesPackagingMaterialLineIdx(final int referencedPackagingMaterialLineIdx)
	{
		this.referencedPackagingMaterialLineIdx = referencedPackagingMaterialLineIdx;
		return this;
	}

	public HUAssignmentExpectation<InOutLineExpectation<ParentExpectationType>> newHUAssignmentExpectation()
	{
		final HUAssignmentExpectation<InOutLineExpectation<ParentExpectationType>> huAssignmentExpectation = new HUAssignmentExpectation<>(this);

		huAssignmentExpectations.add(huAssignmentExpectation);

		return huAssignmentExpectation;
	}

	@Override
	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final String valueString)
	{
		super.attribute(attribute, valueString);
		return this;
	}

	@Override
	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final BigDecimal valueNumber)
	{
		super.attribute(attribute, valueNumber);
		return this;
	}

	@Override
	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final Date valueDate)
	{
		super.attribute(attribute, valueDate);
		return this;
	}

}