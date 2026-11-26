package de.metas.payment.sepa.api;

/*
 * #%L
 * de.metas.payment.sepa
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

import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.ISingletonService;
import org.compiere.model.I_C_PaySelection;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

public interface ISEPADocumentBL extends ISingletonService
{
	/**
	 * @param chosenCreditTransferProtocol the credit transfer protocol to use; {@code null} means the default. Ignored for direct debit pay selections.
	 */
	I_SEPA_Export createSEPAExportFromPaySelection(I_C_PaySelection from, boolean isGroupTransactions, @Nullable SEPAProtocol chosenCreditTransferProtocol);

	Date getDueDate(I_SEPA_Export_Line line);

	/**
	 * @return the lines of the given export that are to be exported (active and without error)
	 */
	List<I_SEPA_Export_Line> retrieveLines(I_SEPA_Export sepaExport);

	/**
	 * Generates the SEPA Credit Transfer xml from the given export header.
	 */
	SEPACreditTransferXML exportCreditTransferXML(I_SEPA_Export sepaExport, SEPAExportContext exportContext);

}
