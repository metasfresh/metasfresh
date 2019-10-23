/**********************************************************************
* This file is part of Adempiere ERP Bazaar                           *
* http://www.adempiere.org                                            *
*                                                                     *
* Copyright (C) Trifon Trifonov.                                      *
* Copyright (C) Contributors                                          *
*                                                                     *
* This program is free software; you can redistribute it and/or       *
* modify it under the terms of the GNU General Public License         *
* as published by the Free Software Foundation; either version 2      *
* of the License, or (at your option) any later version.              *
*                                                                     *
* This program is distributed in the hope that it will be useful,     *
* but WITHOUT ANY WARRANTY; without even the implied warranty of      *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the        *
* GNU General Public License for more details.                        *
*                                                                     *
* You should have received a copy of the GNU General Public License   *
* along with this program; if not, write to the Free Software         *
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,          *
* MA 02110-1301, USA.                                                 *
*                                                                     *
* Contributors:                                                       *
* - Trifon Trifonov (trifonnt@users.sourceforge.net)                  *
*                                                                     *
* Sponsors:                                                           *
* - E-evolution (http://www.e-evolution.com)                          *
***********************************************************************/
package org.adempiere.process.rpl.exp;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.process.rpl.IExportProcessor;
import org.compiere.model.I_EXP_ProcessorParameter;
import org.compiere.model.MEXPProcessor;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Trx;
import org.w3c.dom.Document;

/**
 * @author Trifon N. Trifonov
 */
public class HDDExportProcessor implements IExportProcessor {

	/**	Logger	*/
	protected Logger	log = LogManager.getLogger(getClass());
	
	public void process(Properties ctx, MEXPProcessor expProcessor, Document document, Trx trx) 
			throws Exception 
	{
		//String host 	= expProcessor.getHost();
		//int port 		= expProcessor.getPort();
		//String account 	= expProcessor.getAccount();
		//String password = expProcessor.getPasswordInfo();
        String fileName  = "";
        String folder  = "";
        
        // Read all processor parameters and set them!        
        I_EXP_ProcessorParameter[] processorParameters = expProcessor.getEXP_ProcessorParameters();
        if (processorParameters != null && processorParameters.length > 0) {
        	for (int i = 0; i < processorParameters.length; i++) {
        		
        		// One special parameter which will be used for remote folder name. 
        		// Or could add flag to ProcessorParameters table which will distinguish between 
        		// connection parameters and FTP Upload parameters.
        		log.info("ProcesParameter          Value = " + processorParameters[i].getValue());
        		log.info("ProcesParameter ParameterValue = " + processorParameters[i].getParameterValue());
        		if ( processorParameters[i].getValue().equals("fileName") ) {
        			fileName = processorParameters[i].getParameterValue();
        		} else if ( processorParameters[i].getValue().equals("folder") ) {
        			folder = processorParameters[i].getParameterValue();
        		}
        	}
        }
        
        if (fileName == null || fileName.length() == 0) {
        	throw new Exception("Missing EXP_ProcessorParameter with key 'fileName'!");
        }
		// Save the document to the disk file
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        tranFactory.setAttribute("indent-number", Integer.valueOf(1));
        
        Transformer aTransformer = tranFactory.newTransformer();
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Source src = new DOMSource(document);
		
        // =================================== Write to String
        Writer writer = new StringWriter();
        Result dest2 = new StreamResult(writer);
        aTransformer.transform(src, dest2);
        System.err.println(writer.toString());
        
        //writer = new OutputStreamWriter(new FileOutputStream(out), "utf-8");
        // =================================== Write to Disk
        try {
        	Result dest = new StreamResult(new File(folder + fileName));
            aTransformer.transform(src, dest);
            
            writer.close();
        } catch (TransformerException ex) {
        	ex.printStackTrace();
        	throw ex;
        }
		
	}

	@Override
	public void createInitialParameters(MEXPProcessor processor)
	{
		// TODO Auto-generated method stub
	}
}
