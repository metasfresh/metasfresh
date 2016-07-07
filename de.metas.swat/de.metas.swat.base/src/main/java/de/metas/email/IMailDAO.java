package de.metas.email;

import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_MailConfig;
import org.compiere.model.I_C_DocType;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IMailDAO extends ISingletonService
{

	/**
	 * @param client
	 * @param orgID
	 * @param processID
	 * @param docType
	 * @param customType
	 * @return The {@link I_AD_MailConfig} entries that fit the given parameters, empty list if none found
	 */
	List<I_AD_MailConfig> retrieveMailConfigs(I_AD_Client client, int orgID, int processID, I_C_DocType docType, String customType);

}
