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

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.compiere.model.I_C_PaySelection;

import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.util.ISingletonService;

public interface ISEPADocumentBL extends ISingletonService
{
	I_SEPA_Export createSEPAExportFromPaySelection(I_C_PaySelection from);

	/**
	 * Creates {@link I_SEPA_Export} entries from an iterator over SEPA documents
	 * 
	 * @param ctx
	 * @param iterator
	 * @param trxName
	 * @param ignorePaymentRule
	 * @return
	 */
	List<I_SEPA_Export> createSEPAExports(Properties ctx, Iterator<ISEPADocument> iterator, String trxName, boolean ignorePaymentRule);

	Date getDueDate(I_SEPA_Export_Line line);

	/**
	 * Method used to update data in a BP after a SEPA operation
	 * NOTE: Currently only a placeholder, as we're not using the functionality yet.
	 * 
	 * @param line
	 */
	void updateBparter(I_SEPA_Export_Line line);

	/**
	 * Generates the SEPA Credit Transfer xml from the given export header.
	 */
	SEPACreditTransferXML exportCreditTransferXML(I_SEPA_Export sepaExport);

}
