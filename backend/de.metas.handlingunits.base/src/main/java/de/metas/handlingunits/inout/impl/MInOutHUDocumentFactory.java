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

import de.metas.handlingunits.document.IHUDocument;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.document.impl.AbstractHUDocumentFactory;
import de.metas.inout.IInOutDAO;
import de.metas.materialtransaction.IMTransactionDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.X_M_InOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MInOutHUDocumentFactory extends AbstractHUDocumentFactory<I_M_InOut>
{
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	
	public MInOutHUDocumentFactory()
	{
		super(I_M_InOut.class);
	}

	@Override
	protected void createHUDocumentsFromTypedModel(final HUDocumentsCollector documentsCollector, final I_M_InOut inout)
	{
		// 05089: HU Editor shall be displayed only for Receipts
		if (inout.isSOTrx())
		{
			throw new AdempiereException("@M_InOut_ID@ @IsSOTrx@=@Y@");
		}

		final String docStatus = inout.getDocStatus();
		final List<String> expectedDocStatuses = Arrays.asList(
				X_M_InOut.DOCSTATUS_Completed,
				X_M_InOut.DOCSTATUS_Reversed
				);
		if (!expectedDocStatuses.contains(docStatus))
		{
			throw new AdempiereException("@Invalid@ @M_InOut_ID@ @DocStatus@: "
					+ "Actual: " + docStatus
					+ ", Expected: " + expectedDocStatuses
					+ ")");
		}

		final List<IHUDocumentLine> docLines = createHUDocumentLines(documentsCollector, inout);
		final IHUDocument doc = new MInOutHUDocument(inout, docLines);
		documentsCollector.getHUDocuments().add(doc);
	}

	private List<IHUDocumentLine> createHUDocumentLines(final HUDocumentsCollector documentsCollector, final I_M_InOut inOut)
	{
		final List<I_M_InOutLine> ioLines = Services.get(IInOutDAO.class).retrieveLines(inOut);
		if (ioLines.isEmpty())
		{
			throw AdempiereException.newWithTranslatableMessage("@NoLines@ (@M_InOut_ID@: " + inOut.getDocumentNo() + ")");
		}

		final List<IHUDocumentLine> sourceLines = new ArrayList<>(ioLines.size());
		for (final I_M_InOutLine ioLine : ioLines)
		{
			//
			// Create HU Document Line
			final List<I_M_Transaction> mtrxs = Services.get(IMTransactionDAO.class).retrieveReferenced(ioLine);
			for (final I_M_Transaction mtrx : mtrxs)
			{
				final MInOutLineHUDocumentLine sourceLine = new MInOutLineHUDocumentLine(ioLine, mtrx);
				sourceLines.add(sourceLine);
			}

			//
			// Create Target Qty
			final Capacity targetCapacity = Capacity.createCapacity(
					ioLine.getMovementQty(), // qty
					ProductId.ofRepoId(ioLine.getM_Product_ID()),
					uomDAO.getById(ioLine.getC_UOM_ID()),
					false // allowNegativeCapacity
					);
			documentsCollector.getTargetCapacities().add(targetCapacity);
		}

		return sourceLines;
	}
}
