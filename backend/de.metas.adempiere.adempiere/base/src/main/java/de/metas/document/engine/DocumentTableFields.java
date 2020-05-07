package de.metas.document.engine;

import org.compiere.model.I_AD_Client;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Interface used to wrap a given persistent object in order to access document related fields.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface DocumentTableFields
{
	//@formatter:off
	int getAD_Client_ID();
	I_AD_Client getAD_Client();
	int getAD_Org_ID();
	boolean isActive();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_DocumentNo = "DocumentNo";
	String getDocumentNo();
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_DocStatus = "DocStatus";
	String getDocStatus();
	void setDocStatus(String docStatus);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_DocAction = "DocAction";
	String getDocAction();
	void setDocAction(String docAction);
	//@formatter:on

	//@formatter:off
	boolean isProcessed();
	void setProcessed(boolean processed);
	//@formatter:on

	//@formatter:off
	String COLUMNNAME_Processing = "Processing";
	boolean isProcessing();
	void setProcessing(boolean processing);
	//@formatter:on
}
