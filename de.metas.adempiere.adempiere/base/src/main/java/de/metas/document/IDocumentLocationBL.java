package de.metas.document;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentLocation;

/**
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03120:_Error_in_DocumentLocation_callout_%282012080910000142%29
 */
public interface IDocumentLocationBL extends ISingletonService
{

	void setBPartnerAddress(IDocumentLocation order);

	void setBillToAddress(IDocumentBillLocation billLocation);

	void setDeliveryToAddress(IDocumentDeliveryLocation order);

}
