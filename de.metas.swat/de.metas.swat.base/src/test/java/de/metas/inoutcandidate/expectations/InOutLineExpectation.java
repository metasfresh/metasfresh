package de.metas.inoutcandidate.expectations;

/*
 * #%L
 * de.metas.swat.base
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.mm.attributes.api.impl.AttributeInstanceExpectation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.test.AbstractExpectation;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Product;

import de.metas.document.engine.IDocument;
import de.metas.inout.model.I_M_InOutLine;

public class InOutLineExpectation<ParentExpectationType> extends AbstractExpectation<ParentExpectationType>
{
	private I_M_Product product = null;
	private String asiDescription = null;
	private boolean asiDescriptionSet = false;
	private BigDecimal qtyEntered = null;
	private BigDecimal movementQty = null;
	private BigDecimal qualityDiscountPercent;
	private boolean qualityDiscountPercentSet;
	private String qualityNote;
	private boolean qualityNoteSet;
	private Boolean inDispute;
	private Boolean packagingmaterial;
	private final List<AttributeInstanceExpectation<InOutLineExpectation<ParentExpectationType>>> attributeInstanceExpectations = new ArrayList<>();

	public InOutLineExpectation(final ParentExpectationType parent, final IContextAware context)
	{
		super(parent);
		setContext(context);
	}

	@OverridingMethodsMustInvokeSuper
	public InOutLineExpectation<ParentExpectationType> assertExpected(final I_M_InOutLine inoutLine)
	{
		ErrorMessage message = newErrorMessage()
				.addContextInfo(inoutLine);

		final I_M_Product expectedProduct = getM_Product();
		if (expectedProduct != null)
		{
			final I_M_Product actualProduct = inoutLine.getM_Product();
			assertModelEquals(message.expect("Invalid product"), expectedProduct, actualProduct);
		}

		if (asiDescriptionSet)
		{
			final String expectedASIDescription = getASIDescription();
			final I_M_AttributeSetInstance asi = inoutLine.getM_AttributeSetInstance();
			final String actualASIDescription;
			if (asi != null)
			{
				actualASIDescription = asi.getDescription();
			}
			else
			{
				actualASIDescription = null;
			}
			assertEquals(message.expect("ASI"), expectedASIDescription, actualASIDescription);
		}
		if (!attributeInstanceExpectations.isEmpty())
		{
			final I_M_AttributeSetInstance asi = inoutLine.getM_AttributeSetInstance();
			for (final AttributeInstanceExpectation<InOutLineExpectation<ParentExpectationType>> attributeExpectation : attributeInstanceExpectations)
			{
				attributeExpectation.assertExpected(message, asi);
			}
		}

		final BigDecimal expectedMovementQty = getMovementQty();
		if (expectedMovementQty != null)
		{
			final BigDecimal actualMovementQty = inoutLine.getMovementQty();
			assertEquals(message.expect("movement qty"), expectedMovementQty, actualMovementQty);
		}

		if (qtyEntered != null)
		{
			assertEquals(message.expect("QtyEntered"), qtyEntered, inoutLine.getQtyEntered());
		}

		if (qualityDiscountPercentSet)
		{
			assertEquals(message.expect("qualityDiscountPercent"), qualityDiscountPercent, inoutLine.getQualityDiscountPercent());
		}

		if (qualityNoteSet)
		{
			final String qualityNoteActual = inoutLine.getQualityNote();
			if (qualityNote == null)
			{
				assertEquals(message.expect("QualityNote shall be empty"), true, Check.isEmpty(qualityNoteActual, false));
			}
			else
			{
				assertEquals(message.expect("QualityNote"), qualityNote, qualityNoteActual);
			}
		}

		if (inDispute != null)
		{
			assertEquals(message.expect("InDispute"), inDispute, inoutLine.isInDispute());
		}
		if (packagingmaterial != null)
		{
			assertEquals(message.expect("PackagingMaterial"), packagingmaterial, inoutLine.isPackagingMaterial());
		}

		return this;
	}

	public <InOutLineType extends I_M_InOutLine> InOutLineType createInOutLine(final Class<InOutLineType> inoutLineClass)
	{
		// gh 1566: create a completed and active inout, otherwise the line won't be counted properly
		final I_M_InOut inout = InterfaceWrapperHelper.newInstance(I_M_InOut.class, getContext());
		inout.setIsActive(true);
		inout.setDocStatus(IDocument.STATUS_Completed);
		InterfaceWrapperHelper.save(inout);

		final InOutLineType inoutLine = InterfaceWrapperHelper.newInstance(inoutLineClass, getContext());
		inoutLine.setM_InOut(inout);
		populateModel(inoutLine);
		InterfaceWrapperHelper.save(inoutLine);
		return inoutLine;
	}

	/**
	 * Populate model which values from this expectation
	 *
	 * @param inoutLine
	 */
	@OverridingMethodsMustInvokeSuper
	protected void populateModel(final I_M_InOutLine inoutLine)
	{
		if (product != null)
		{
			inoutLine.setM_Product_ID(product.getM_Product_ID());
		}
		inoutLine.setQtyEntered(qtyEntered);
		inoutLine.setMovementQty(movementQty);
		if (inDispute != null)
		{
			inoutLine.setIsInDispute(inDispute);
		}
		inoutLine.setQualityDiscountPercent(qualityDiscountPercent);
		inoutLine.setQualityNote(qualityNote);
	}

	public final InOutLineExpectation<ParentExpectationType> product(final I_M_Product product)
	{
		this.product = product;
		return this;
	}

	public I_M_Product getM_Product()
	{
		return product;
	}

	public final InOutLineExpectation<ParentExpectationType> asiDescription(final String asiDescription)
	{
		this.asiDescription = asiDescription;
		this.asiDescriptionSet = true;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> noASIDescription()
	{
		return asiDescription(null);
	}

	public String getASIDescription()
	{
		return asiDescription;
	}

	public final InOutLineExpectation<ParentExpectationType> movementQty(final String movementQtyStr)
	{
		return movementQty(new BigDecimal(movementQtyStr));
	}

	public final InOutLineExpectation<ParentExpectationType> movementQty(final int movementQty)
	{
		return movementQty(new BigDecimal(movementQty));
	}

	public final InOutLineExpectation<ParentExpectationType> movementQty(final BigDecimal movementQty)
	{
		this.movementQty = movementQty;
		return this;
	}

	public BigDecimal getMovementQty()
	{
		return movementQty;
	}

	public InOutLineExpectation<ParentExpectationType> qtyEntered(final BigDecimal qtyEntered)
	{
		this.qtyEntered = qtyEntered;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> qualityDiscountPercent(final BigDecimal qualityDiscountPercent)
	{
		this.qualityDiscountPercent = qualityDiscountPercent;
		this.qualityDiscountPercentSet = true;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> qualityDiscountPercent(final String qualityDiscountPercent)
	{
		return qualityDiscountPercent(new BigDecimal(qualityDiscountPercent));
	}

	public InOutLineExpectation<ParentExpectationType> noQualityDiscountPercent()
	{
		return qualityDiscountPercent((BigDecimal)null);
	}

	public InOutLineExpectation<ParentExpectationType> qualityNote(final String qualityNote)
	{
		this.qualityNote = qualityNote;
		this.qualityNoteSet = true;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> noQualityNote()
	{
		return qualityNote(null);
	}

	public InOutLineExpectation<ParentExpectationType> inDispute(final boolean inDispute)
	{
		this.inDispute = inDispute;
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> packagingmaterialLine(boolean packagingmaterial)
	{
		this.packagingmaterial = packagingmaterial;
		return this;
	}

	public AttributeInstanceExpectation<InOutLineExpectation<ParentExpectationType>> newAttributeExpectation()
	{
		final AttributeInstanceExpectation<InOutLineExpectation<ParentExpectationType>> expectation = new AttributeInstanceExpectation<>(this);
		attributeInstanceExpectations.add(expectation);
		return expectation;
	}

	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final String valueString)
	{
		newAttributeExpectation().attribute(attribute).valueString(valueString);
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final BigDecimal valueNumber)
	{
		newAttributeExpectation().attribute(attribute).valueNumber(valueNumber);
		return this;
	}

	public InOutLineExpectation<ParentExpectationType> attribute(final I_M_Attribute attribute, final Date valueDate)
	{
		newAttributeExpectation().attribute(attribute).valueDate(valueDate);
		return this;
	}

}
