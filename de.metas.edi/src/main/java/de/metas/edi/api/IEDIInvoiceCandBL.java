package de.metas.edi.api;

/*
 * #%L
 * de.metas.edi
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


import org.adempiere.util.ISingletonService;

import de.metas.edi.model.I_C_Invoice_Candidate;

public interface IEDIInvoiceCandBL extends ISingletonService
{

	/**
	 * Set isEDIEnabled flag based on the table and record
	 * 
	 * @param candidate
	 * @task http://dewiki908/mediawiki/index.php/08926_EDI-Ausschalten_f%C3%BCr_bestimmte_Belege_%28109751792947%29
	 */
	void setEdiEnabled(final I_C_Invoice_Candidate candidate);
}
