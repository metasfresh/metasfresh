package de.metas.inoutcandidate.spi.impl;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.test.ErrorMessage;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.model.I_C_UOM;
import org.junit.Assert;

import de.metas.handlingunits.expectations.AbstractHUExpectation;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;

public class QualityExpectation<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	public static final QualityExpectation<Object> newExpectation()
	{
		final Object parentExpectation = null;
		return new QualityExpectation<>(parentExpectation);
	}

	// Expectations
	private QtyAndQualityExpectation<?> qtyAndQualityExpectation;
	@ToStringBuilder(skip = true)
	private I_C_UOM uom;
	private String attributesId;
	private boolean attributesIdSet;
	//
	@ToStringBuilder(skip = true)
	private I_M_ReceiptSchedule receiptSchedule;

	public QualityExpectation(final ParentExpectationType parentExpectation)
	{
		super(parentExpectation);
	}

	public QualityExpectation<ParentExpectationType> copyFrom(final QualityExpectation<?> from)
	{
		qtyAndQuality().copyFrom(from.qtyAndQuality());
		this.uom = from.uom;
		this.attributesId = from.attributesId;
		this.attributesIdSet = from.attributesIdSet;
		this.receiptSchedule = from.receiptSchedule;
		return this;
	}

	public QualityExpectation<ParentExpectationType> assertExpected(final HUReceiptLineCandidatesBuilder actual)
	{
		final String message = null;
		return assertExpected(message, actual);
	}

	public QualityExpectation<ParentExpectationType> assertExpected(final String message, final HUReceiptLineCandidatesBuilder actual)
	{
		final ErrorMessage messageToUse = newErrorMessage(message)
				.addContextInfo(actual);

		qtyAndQuality().assertExpected(messageToUse.expect("valid qty&quality"), actual.getQtyAndQuality());

		return this;
	}

	public QualityExpectation<ParentExpectationType> assertExpected(final HUReceiptLinePartCandidate actual)
	{
		final String message = null;
		return assertExpected(message, actual);
	}

	public QualityExpectation<ParentExpectationType> assertExpected(final String message, final HUReceiptLinePartCandidate actual)
	{
		final ErrorMessage messageToUse = newErrorMessage(message)
				.addContextInfo("HUReceiptLinePartCandidate: ", actual);

		assertNotNull(messageToUse.expect("not null"), actual);
		qtyAndQuality().assertExpected(messageToUse.expect("valid qty&quality"), actual.getQtyAndQuality());

		if (attributesIdSet)
		{
			assertEquals(messageToUse.expect("AttributesId"), attributesId, actual.getAttributes().getId());
		}

		return this;
	}

	public QualityExpectation<ParentExpectationType> assertExpected(final HUReceiptLineCandidate actual)
	{
		final String message = null;
		return assertExpected(message, actual);
	}

	public QualityExpectation<ParentExpectationType> assertExpected(final String message, final HUReceiptLineCandidate actual)
	{
		final ErrorMessage messageToUse = newErrorMessage(message)
				.addContextInfo(actual);

		if (receiptSchedule != null)
		{
			assertModelEquals(messageToUse.expect("valid receipt schedule"), receiptSchedule, actual.getM_ReceiptSchedule());
		}

		qtyAndQuality().assertExpected(messageToUse.expect("valid qty&quality"), actual.getQtyAndQuality());

		return this;
	}

	public HUReceiptLinePartCandidate createPart()
	{
		final I_M_ReceiptSchedule_Alloc rsa = createRSA();

		final PlainHUReceiptLinePartAttributes attributes = new PlainHUReceiptLinePartAttributes();
		if (attributesIdSet)
		{
			attributes.setId(attributesId);
		}
		attributes.setQualityDiscountPercent(qtyAndQuality().getQualityDiscountPercent());
		final HUReceiptLinePartCandidate part = new HUReceiptLinePartCandidate(attributes);
		part.add(rsa);

		//
		// Make sure part was correctly created
		assertExpected("part was not correctly created", part);

		System.out.println("--------------------------------------------------------------"
				+ "\n Expectation: " + this
				+ "\n Created Part: " + part
				);

		return part;
	}

	private I_M_ReceiptSchedule_Alloc createRSA()
	{
		Assert.assertNotNull("receipt schedule shall be set", receiptSchedule);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(receiptSchedule);
		final I_M_ReceiptSchedule_Alloc rsa = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule_Alloc.class, context);
		rsa.setM_ReceiptSchedule(receiptSchedule);
		rsa.setHU_QtyAllocated(qtyAndQuality().getQty());
		InterfaceWrapperHelper.save(rsa);
		return rsa;
	}

	//
	//
	// Expectations
	//
	//

	private QtyAndQualityExpectation<?> qtyAndQuality()
	{
		if (qtyAndQualityExpectation == null)
		{
			qtyAndQualityExpectation = QtyAndQualityExpectation.newInstance();
		}
		return qtyAndQualityExpectation;
	}

	public QualityExpectation<ParentExpectationType> qty(final BigDecimal qty)
	{
		qtyAndQuality().qty(qty);
		return this;
	}

	public QualityExpectation<ParentExpectationType> qty(final String qty)
	{
		return qty(new BigDecimal(qty));
	}

	public QualityExpectation<ParentExpectationType> qty(final int qty)
	{
		return qty(new BigDecimal(qty));
	}

	public BigDecimal getQty()
	{
		return qtyAndQuality().getQty();
	}

	public QualityExpectation<ParentExpectationType> qtyWithIssues(final BigDecimal qtyWithIssues)
	{
		qtyAndQuality().qtyWithIssues(qtyWithIssues);
		return this;
	}

	public QualityExpectation<ParentExpectationType> qtyWithIssues(final String qtyWithIssues)
	{
		return qtyWithIssues(new BigDecimal(qtyWithIssues));
	}

	public QualityExpectation<ParentExpectationType> qtyWithIssues(final int qtyWithIssues)
	{
		return qtyWithIssues(new BigDecimal(qtyWithIssues));
	}

	public QualityExpectation<ParentExpectationType> qtyWithoutIssues(final BigDecimal qtyWithoutIssues)
	{
		qtyAndQuality().qtyWithoutIssues(qtyWithoutIssues);
		return this;
	}

	public QualityExpectation<ParentExpectationType> qtyWithoutIssues(final String qtyWithoutIssues)
	{
		return qtyWithoutIssues(new BigDecimal(qtyWithoutIssues));
	}

	public QualityExpectation<ParentExpectationType> qtyWithoutIssues(final int qtyWithoutIssues)
	{
		return qtyWithoutIssues(new BigDecimal(qtyWithoutIssues));
	}

	public QualityExpectation<ParentExpectationType> qualityDiscountPercent(final BigDecimal qualityDiscountPercent)
	{
		qtyAndQuality().qualityDiscountPercent(qualityDiscountPercent);
		return this;
	}

	public QualityExpectation<ParentExpectationType> qualityDiscountPercent(final String qualityDiscountPercent)
	{
		return qualityDiscountPercent(new BigDecimal(qualityDiscountPercent));
	}

	public QualityExpectation<ParentExpectationType> qualityDiscountPercent(final int qualityDiscountPercent)
	{
		return qualityDiscountPercent(new BigDecimal(qualityDiscountPercent));
	}

	public BigDecimal getQualityDiscountPercent()
	{
		return qtyAndQuality().getQualityDiscountPercent();
	}

	public QualityExpectation<ParentExpectationType> uom(final I_C_UOM uom)
	{
		this.uom = uom;
		qtyAndQuality().qtyPrecision(uom == null ? 0 : uom.getStdPrecision());
		return this;
	}

	public I_C_UOM getC_UOM()
	{
		return uom;
	}

	public QualityExpectation<ParentExpectationType> receiptSchedule(final I_M_ReceiptSchedule receiptSchedule)
	{
		this.receiptSchedule = receiptSchedule;
		return this;
	}

	public QualityExpectation<ParentExpectationType> attributesId(final String attributesId)
	{
		this.attributesId = attributesId;
		this.attributesIdSet = true;
		return this;
	}
}
