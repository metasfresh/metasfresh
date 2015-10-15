package org.adempiere.archive.api;

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


import org.adempiere.archive.spi.IArchiveEventListener;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_User;

public interface IArchiveEventManager extends ISingletonService
{
	String STATUS_Success = "Success";
	String STATUS_Failure = "Failure";

	/**
	 * To be used where you need to pass the "1 copy" parameter.
	 * 
	 * NOTE: please use this constant instead of hardcoding the number "1" because in most of the cases, you are passing this because the copies is not available or is not implemented.
	 */
	int COPIES_ONE = 1;


	void registerArchiveEventListener(IArchiveEventListener listener);


	void firePdfUpdate(I_AD_Archive archive, I_AD_User user);


	void fireEmailSent(I_AD_Archive archive, String action, I_AD_User user, String from, String to, String cc, String bcc, String status);


	void firePrintOut(I_AD_Archive archive, I_AD_User user, String printerName, int copies, String status);
}
