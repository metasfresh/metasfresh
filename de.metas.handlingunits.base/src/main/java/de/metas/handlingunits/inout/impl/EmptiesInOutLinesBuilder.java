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
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.IPackingMaterialDocumentLine;
import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.impl.AbstractPackingMaterialDocumentLinesBuilder;
import de.metas.handlingunits.impl.PlainPackingMaterialDocumentLineSource;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutBL;

/* package */class EmptiesInOutLinesBuilder extends AbstractPackingMaterialDocumentLinesBuilder
{
	//
	// Services
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);

	private final IReference<I_M_InOut> _inoutRef;

	public EmptiesInOutLinesBuilder(final IReference<I_M_InOut> inoutRef)
	{
		super();

		Check.assumeNotNull(inoutRef, "inoutRef not null");
		_inoutRef = inoutRef;
	}

	private final I_M_InOut getM_InOut()
	{
		return _inoutRef.getValue();
	}

	public void addSource(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		final BigDecimal qtyBD = BigDecimal.valueOf(qty);
		final PlainPackingMaterialDocumentLineSource source = new PlainPackingMaterialDocumentLineSource(packingMaterial, qtyBD);
		addSource(source);
	}

	@Override
	protected void assertValid(final IPackingMaterialDocumentLineSource source)
	{
		Check.assumeInstanceOf(source, PlainPackingMaterialDocumentLineSource.class, "source");
	}

	@Override
	protected IPackingMaterialDocumentLine createPackingMaterialDocumentLine(final I_M_HU_PackingMaterial packingMaterial)
	{
		final I_M_InOut inout = getM_InOut();
		final I_M_InOutLine inoutLine = inOutBL.newInOutLine(inout, I_M_InOutLine.class);
		inoutLine.setM_Product_ID(packingMaterial.getM_Product_ID());

		return new EmptiesInOutLinePackingMaterialDocumentLine(inoutLine);
	}

	private final EmptiesInOutLinePackingMaterialDocumentLine toImpl(final IPackingMaterialDocumentLine pmLine)
	{
		return (EmptiesInOutLinePackingMaterialDocumentLine)pmLine;
	}

	@Override
	protected void removeDocumentLine(final IPackingMaterialDocumentLine pmLine)
	{
		final EmptiesInOutLinePackingMaterialDocumentLine inoutLinePMLine = toImpl(pmLine);
		final I_M_InOutLine inoutLine = inoutLinePMLine.getM_InOutLine();
		if (!InterfaceWrapperHelper.isNew(inoutLine))
		{
			InterfaceWrapperHelper.delete(inoutLine);
		}
	}

	@Override
	protected void createDocumentLine(final IPackingMaterialDocumentLine pmLine)
	{
		final EmptiesInOutLinePackingMaterialDocumentLine inoutLinePMLine = toImpl(pmLine);

		final I_M_InOut inout = getM_InOut();
		InterfaceWrapperHelper.save(inout); // make sure inout header is saved

		final I_M_InOutLine inoutLine = inoutLinePMLine.getM_InOutLine();
		inoutLine.setM_InOut(inout); // make sure inout line is linked to our M_InOut_ID (in case it was just saved)
		inoutLine.setIsActive(true); // just to be sure
		//task FRESH-273
		inoutLine.setIsPackagingMaterial(true);
		InterfaceWrapperHelper.save(inoutLine);
	}

	@Override
	protected void linkSourceToDocumentLine(final IPackingMaterialDocumentLineSource source, final IPackingMaterialDocumentLine pmLine)
	{
		// nothing
	}

}
