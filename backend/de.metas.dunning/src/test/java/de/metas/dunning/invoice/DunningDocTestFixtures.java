package de.metas.dunning.invoice;

import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import lombok.NonNull;
import org.compiere.model.I_C_Invoice;

import static org.adempiere.model.InterfaceWrapperHelper.getModelTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2026 metas GmbH
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

/**
 * Creates the minimal record graph that links a {@link I_C_DunningDoc} to the invoices it dunns:
 * dunning doc, line, line source, candidate (pointing to the invoice via AD_Table_ID/Record_ID).
 */
public final class DunningDocTestFixtures
{
	private DunningDocTestFixtures() {}

	public static I_C_Invoice createInvoice()
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		saveRecord(invoice);
		return invoice;
	}

	public static I_C_DunningDoc createDunningDoc()
	{
		final I_C_DunningDoc dunningDoc = newInstance(I_C_DunningDoc.class);
		saveRecord(dunningDoc);
		return dunningDoc;
	}

	/** Creates the candidate for the given invoice and a dunning doc line (+ line source) which links it to the given dunning doc. */
	public static void addInvoiceLine(@NonNull final I_C_DunningDoc dunningDoc, @NonNull final I_C_Invoice invoice)
	{
		final I_C_Dunning_Candidate candidate = newInstance(I_C_Dunning_Candidate.class);
		candidate.setAD_Table_ID(getModelTableId(invoice));
		candidate.setRecord_ID(invoice.getC_Invoice_ID());
		saveRecord(candidate);

		addLine(dunningDoc, candidate);
	}

	public static void addLine(@NonNull final I_C_DunningDoc dunningDoc, @NonNull final I_C_Dunning_Candidate candidate)
	{
		final I_C_DunningDoc_Line line = newInstance(I_C_DunningDoc_Line.class);
		line.setC_DunningDoc(dunningDoc);
		saveRecord(line);

		final I_C_DunningDoc_Line_Source lineSource = newInstance(I_C_DunningDoc_Line_Source.class);
		lineSource.setC_DunningDoc_Line(line);
		lineSource.setC_Dunning_Candidate(candidate);
		saveRecord(lineSource);
	}
}
