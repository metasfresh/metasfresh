package de.metas.printing.esb.base.inout.bean;

/*
 * #%L
 * de.metas.printing.esb.base
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


import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.metas.printing.esb.api.PrintJobInstructionsConfirm;
import de.metas.printing.esb.base.jaxb.JAXBConstants;
import de.metas.printing.esb.base.jaxb.generated.CPrintJobInstructionsStatusEnum;
import de.metas.printing.esb.base.jaxb.generated.ObjectFactory;
import de.metas.printing.esb.base.jaxb.generated.PRTCPrintJobInstructionsConfirmType;
import de.metas.printing.esb.base.jaxb.generated.ReplicationEventEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationModeEnum;
import de.metas.printing.esb.base.jaxb.generated.ReplicationTypeEnum;

/**
 * {@link PRTCPrintJobInstructionsConfirmType} converters.
 * 
 * NOTE: please don't add here camel related code
 * 
 * @author tsa
 * 
 */
public class PRTCPrintJobInstructionsConfirmTypeConverter
{
	private static final transient Logger logger = Logger.getLogger(PRTCPrintJobInstructionsConfirmTypeConverter.class.getName());

	protected final transient ObjectFactory factory = new ObjectFactory();

	public PRTCPrintJobInstructionsConfirmType createPRTADPrintPackageResponse(final PrintJobInstructionsConfirm response, final int sessionId)
	{
		final PRTCPrintJobInstructionsConfirmType convertedResponse = new PRTCPrintJobInstructionsConfirmType();
		convertedResponse.setADSessionIDAttr(BigInteger.valueOf(sessionId));

		if (response.getPrintJobInstructionsID() == null)
		{
			logger.log(Level.SEVERE, "Print job Instructions ID cannot be null!");

			throw new IllegalArgumentException("Print job Instructions ID cannot be null!");
		}

		convertedResponse.setCPrintJobInstructionsID(BigInteger.valueOf(Long.valueOf(response.getPrintJobInstructionsID())));

		switch (response.getStatus())
		{
			case Gedruckt:
				convertedResponse.setStatus(CPrintJobInstructionsStatusEnum.Done);
				convertedResponse.setErrorMsg(null);
				break;
			case Druckfehler:
				convertedResponse.setStatus(CPrintJobInstructionsStatusEnum.Error);
				convertedResponse.setErrorMsg(response.getErrorMsg());
				break;
			default:
				throw new IllegalArgumentException("Invalid original response status: " + response.getStatus());
		}

		// ADempiere Specific Data
		convertedResponse.setReplicationEventAttr(ReplicationEventEnum.AfterChange);
		convertedResponse.setReplicationModeAttr(ReplicationModeEnum.Table);
		convertedResponse.setReplicationTypeAttr(ReplicationTypeEnum.Merge);
		convertedResponse.setVersionAttr(JAXBConstants.PRT_C_PRINT_JOB_INSTRUCTIONS_CONFIRM_FORMAT_VERSION);

		return convertedResponse;
	}

}
