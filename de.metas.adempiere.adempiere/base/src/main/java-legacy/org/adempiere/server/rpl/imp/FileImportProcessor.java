/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Trifon Trifonov.                                     * 
 * Copyright (C) Contributors                                         * 
 *                                                                    * 
 * This program is free software; you can redistribute it and/or      * 
 * modify it under the terms of the GNU General Public License        * 
 * as published by the Free Software Foundation; either version 2     * 
 * of the License, or (at your option) any later version.             * 
 *                                                                    * 
 * This program is distributed in the hope that it will be useful,    * 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     * 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       * 
 * GNU General Public License for more details.                       * 
 *                                                                    * 
 * You should have received a copy of the GNU General Public License  * 
 * along with this program; if not, write to the Free Software        * 
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         * 
 * MA 02110-1301, USA.                                                * 
 *                                                                    * 
 * Contributors:                                                      * 
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *                                                                    *
 * Sponsors:                                                          *
 *  - E-evolution (http://www.e-evolution.com/)                       *
 **********************************************************************/
package org.adempiere.server.rpl.imp;

import java.util.List;
import java.util.Properties;

import org.adempiere.process.rpl.XMLHelper;
import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.server.rpl.IReplicationProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.adempiere.server.rpl.api.IImportHelper;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorParameter;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Services;

import org.w3c.dom.Document;

/**
 * 
 * @author Trifon N. Trifonov
 * @version $Id:$
 */
public class FileImportProcessor implements IImportProcessor {
	
	/**	Logger	*/
	protected Logger	log = LogManager.getLogger(FileImportProcessor.class);
	
	public void start(Properties ctx, IReplicationProcessor replicationProcessor, String trxName)
			throws Exception {
		
		final I_IMP_Processor impProcessor = replicationProcessor.getMImportProcessor();
		final List<I_IMP_ProcessorParameter> processorParameters = Services.get(IIMPProcessorDAO.class).retrieveParameters(impProcessor, trxName);
		
		String fileName = null;
		String folder  = "";
		for (final I_IMP_ProcessorParameter processorParameter : processorParameters)
		{
			log.info("ProcesParameter          Value = " + processorParameter.getValue());
			log.info("ProcesParameter ParameterValue = " + processorParameter.getParameterValue());
			if (processorParameter.getValue().equals("fileName"))
			{
				fileName = processorParameter.getParameterValue();
			}
			else if (processorParameter.getValue().equals("folder"))
			{
				folder = processorParameter.getParameterValue();
			}
		}
        
        if (fileName == null || fileName.length() == 0) {
        	throw new Exception("Missing IMP_ProcessorParameter with key 'fileName'!");
        }
        
		Document documentToBeImported = XMLHelper.createDocumentFromFile(folder + fileName);
		StringBuilder result = new StringBuilder();
		
		final IImportHelper impHelper = Services.get(IIMPProcessorBL.class).createImportHelper(ctx);
		impHelper.importXMLDocument(result, documentToBeImported, trxName );

//		addLog(0, null, null, Msg.getMsg(ctx, "ImportModelProcessResult") + "\n" + result.toString());
	}

	public void stop() throws Exception {
		// do nothing!
	}

	@Override
	public void createInitialParameters(I_IMP_Processor processor)
	{
		final IIMPProcessorBL impProcessorBL = Services.get(IIMPProcessorBL.class);
		
		impProcessorBL.createParameter(processor,
				"fileName",
				"Name of file from where xml will be imported",
				"Import Processor Parameter Description",
				"HDD Import Processor Parameter Help",
				"C_Order");
	}

}
