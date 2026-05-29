package de.metas.edi.model;

import java.math.BigDecimal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface I_C_BPartner extends de.metas.invoicecandidate.model.I_C_BPartner
{
	// @formatter:off
	String COLUMNNAME_IsEdiDesadvRecipient = "IsEdiDesadvRecipient";
	boolean isEdiDesadvRecipient();
	void setIsEdiDesadvRecipient(boolean IsEdiDesadvRecipient);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IsEdiInvoicRecipient = "IsEdiInvoicRecipient";
	boolean isEdiInvoicRecipient();
	void setIsEdiInvoicRecipient(boolean IsEdiInvoicRecipient);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_EdiDesadvRecipientGLN = "EdiDesadvRecipientGLN";
	String getEdiDesadvRecipientGLN();
	void setEdiDesadvRecipientGLN(String EdiDesadvRecipientGLN);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_EdiInvoicRecipientGLN = "EdiInvoicRecipientGLN";
	String getEdiInvoicRecipientGLN();
	void setEdiInvoicRecipientGLN(String EdiInvoicRecipientGLN);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_EdiDESADVDefaultItemCapacity = "EdiDESADVDefaultItemCapacity";
	BigDecimal getEdiDESADVDefaultItemCapacity();
	void setEdiDESADVDefaultItemCapacity(BigDecimal EdiDESADVDefaultItemCapacity);
	// @formatter:on

	/**
	 * EDISendingMode AD_Reference_ID=542047
	 * Reference name: EDISendingMode
	 */
	int EDISendingMode_AD_Reference_ID = 542047;
	/**
	 * ReplicationInterface = R
	 */
	String EDISendingMode_ReplicationInterface = "R";
	/**
	 * ExternalSystem = E
	 */
	String EDISendingMode_ExternalSystem = "E";

	// @formatter:off
	String COLUMNNAME_EdiDESADVSendingMode = "EdiDESADVSendingMode";
	String getEdiDESADVSendingMode();
	void setEdiDESADVSendingMode(String EdiDESADVSendingMode);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_EdiINVOICSendingMode = "EdiINVOICSendingMode";
	String getEdiINVOICSendingMode();
	void setEdiINVOICSendingMode(String EdiINVOICSendingMode);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_EdiDESADV_ExternalSystem_Config_ID = "EdiDESADV_ExternalSystem_Config_ID";
	int getEdiDESADV_ExternalSystem_Config_ID();
	void setEdiDESADV_ExternalSystem_Config_ID(int EdiDESADV_ExternalSystem_Config_ID);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_EdiINVOIC_ExternalSystem_Config_ID = "EdiINVOIC_ExternalSystem_Config_ID";
	int getEdiINVOIC_ExternalSystem_Config_ID();
	void setEdiINVOIC_ExternalSystem_Config_ID(int EdiINVOIC_ExternalSystem_Config_ID);
	// @formatter:on
}
