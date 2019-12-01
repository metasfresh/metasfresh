/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.impexp.BankStatementLoaderInterface;
 
 
/**
 *	Bank Statement Loader Model
 *  This class is responsible for creating an instance of the
 *  bank statement loader class to use.
 *  It also inserts the data into the I_BankStatement table.
 *
 *  The loader objects can access their configuration (e.g. file, URL,
 *  password etc) using the corresponding get methods provided by this class.
 *  As this class is derived from PO, "Persistent Object" it has access to
 *  the loader configuration that has been entered for this loader.
 *  How these values are interpreted is the responsibility of the loader.
 *  There are two file names provided, getFileName() and getLocalFileName().
 *  The first one is the file name as entered on the loader configuration tab,
 *  the second one is the file name parameter from the loader process.
 *  Recommended behavior for a file based loader would be to use the file
 *  name from the process parameter if available, and resort to the file name
 *  from the loader configuration as a second option.
 *	
 *  For a HTTP based loader the file name from the loader configuration should
 *  be used to construct the URL to connect to (combined with the other parameters).
 *  In this scenario the file name from the process parameter can be used to save
 *  the acquired statement data to disk.
 *
 *	author Maarten Klinker, Eldir Tomassen
 *	@version $Id: MBankStatementLoader.java,v 1.3 2006/07/30 00:51:04 jjanke Exp $
 */
 public class MBankStatementLoader extends X_C_BankStatementLoader
 {
 	/**
	 * 
	 */
	private static final long serialVersionUID = -4096456424277340847L;

	/**	Number of statement lines imported			*/
	private int importCount = 0;
	
	/**	Message will be handled by Adempiere (e.g. translated)	*/
	private String errorMessage = "";
	
	/**	Additional error description				*/
	private String errorDescription = "";

	/**	Loader object to use 					*/
	private BankStatementLoaderInterface m_loader = null;
	
	/**	File name from process parameter							*/
	private String localFileName = null;

	
	
	/**
	 * 	Create a Statement Loader
	 *  Added for compatibility with new PO infrastructure (bug# 968136)
	 * 	@param ctx Current context
	 * 	@param C_BankStatementLoader_ID loader to use
	 *	@param trxName transaction
	 */
	public MBankStatementLoader(Properties ctx, int C_BankStatementLoader_ID, String trxName)
	{
		super(ctx, C_BankStatementLoader_ID, trxName);
		init(null);
	}	//	MBankStatementLoader
	
	/**
	 * 	Create a Statement Loader
	 * 	@param ctx Current context
	 * 	@param C_BankStatementLoader_ID loader to use
	 *  @param fileName input file
	 *	@param trxName transaction
	 */
 	public MBankStatementLoader(Properties ctx, int C_BankStatementLoader_ID, String fileName, String trxName)
	{
		super(ctx, C_BankStatementLoader_ID, trxName);
		init(fileName);
		
	}	//	MBankStatementLoader
 	
	/**
	 * 	Create a Statement Loader
	 * 	@param ctx Current context
	 * 	@param rs ResultSet
	 *	@param trxName transaction
	 */
 	public MBankStatementLoader(Properties ctx, ResultSet rs, String trxName)
 	{
 		super(ctx, rs, trxName);
 		init(null);
 	}	//	MBankStatementLoader
 	
 	private void init(String fileName)
	{
		localFileName = fileName;
		try
		{
			log.info( "MBankStatementLoader Class Name=" + getStmtLoaderClass());
			@SuppressWarnings("unchecked")
			Class<BankStatementLoaderInterface> bsrClass = (Class<BankStatementLoaderInterface>)Class.forName(getStmtLoaderClass());
			m_loader = bsrClass.newInstance();
		}
		catch(Exception e)
		{
			errorMessage = "ClassNotLoaded";
			errorDescription = e.getMessage();
		}		
	}
	
	/**
	 *	Return Name
	 *	@return Name
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MBankStatementLoader[")
			.append(get_ID ()).append("-").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	/**
	 *	Return Local File Name
	 *	@return Name
	 */
	public String getLocalFileName()
	{
		return localFileName;
	}	//	getLocalFileName
	
	/**
	 * 	Start loading Bankstatements
	 *	@return true if loading completed successfully
	 */
	public boolean loadLines()
	{
		boolean result = false;
		log.info( "MBankStatementLoader.loadLines");
		if (m_loader == null)
		{
			errorMessage = "ClassNotLoaded";
			return result;
		}
		//	Initialize the Loader 
		if (!m_loader.init(this))
		{
			errorMessage = m_loader.getLastErrorMessage();
			errorDescription = m_loader.getLastErrorDescription();
			return result;
		}
		//	Verify whether the data structure is valid
		if (!m_loader.isValid())
		{
			errorMessage = m_loader.getLastErrorMessage();
			errorDescription = m_loader.getLastErrorDescription();
			return result;
		}
		//	Load statement lines
		if (!m_loader.loadLines())
		{
			errorMessage = m_loader.getLastErrorMessage();
			errorDescription = m_loader.getLastErrorDescription();
			return result;
		}
		result = true;
		return result;
	}	//	loadLines
	
	/**
	 * 	Load a bank statement into the I_BankStatement table
	 *	@return Statement line was loaded successfully
	 *	This method is called by the BankStatementLoadere whenever a complete 
	 *	statement line has been read.
	 */
	public boolean saveLine()
	{
		log.info( "MBankStatementLoader.importLine");
		boolean result = false;
		X_I_BankStatement imp = new X_I_BankStatement(getCtx(), 0, get_TrxName());
		if (m_loader == null)
		{
			errorMessage = "LoadError";
			return result;
		}
		//	Bank Account fields
		log.info( "MBankStatementLoader.importLine Bank Account=" + m_loader.getBankAccountNo());
		imp.setBankAccountNo(m_loader.getBankAccountNo());
		log.info( "MBankStatementLoader.importLine Routing No=" + m_loader.getRoutingNo());
		imp.setRoutingNo(m_loader.getRoutingNo());
		
		//	Statement fields
		log.info( "MBankStatementLoader.importLine EFT Statement Reference No=" + m_loader.getStatementReference());
		imp.setEftStatementReference(m_loader.getStatementReference());
		log.info( "MBankStatementLoader.importLine EFT Statement Date=" + m_loader.getStatementDate());
		imp.setEftStatementDate(m_loader.getStatementDate());
        log.info( "MBankStatementLoader.importLine Statement Date=" + m_loader.getStatementDate());
        imp.setStatementDate(m_loader.getStatementDate());
		
		//	Statement Line fields
		log.info( "MBankStatementLoader.importLine EFT Transaction ID=" + m_loader.getTrxID());
		imp.setEftTrxID(m_loader.getTrxID());
		log.info( "MBankStatementLoader.importLine Statement Line Date=" + m_loader.getStatementLineDate());
		imp.setStatementLineDate(m_loader.getStatementLineDate());
		imp.setStatementLineDate(m_loader.getStatementLineDate());
		imp.setEftStatementLineDate(m_loader.getStatementLineDate());
		log.info( "MBankStatementLoader.importLine Valuta Date=" + m_loader.getValutaDate());
		imp.setValutaDate(m_loader.getValutaDate());
		imp.setEftValutaDate(m_loader.getValutaDate());
		log.info( "MBankStatementLoader.importLine Statement Amount=" + m_loader.getStmtAmt());
		imp.setStmtAmt(m_loader.getStmtAmt());
		imp.setEftAmt(m_loader.getStmtAmt());
		log.info( "MBankStatementLoader.importLine Transaction Amount=" + m_loader.getTrxAmt());
		imp.setTrxAmt(m_loader.getTrxAmt());
		log.info( "MBankStatementLoader.importLine Interest Amount=" + m_loader.getInterestAmt());
		imp.setInterestAmt(m_loader.getInterestAmt());
		log.info( "MBankStatementLoader.importLine Reference No=" + m_loader.getReference());
		imp.setReferenceNo(m_loader.getReference());
		imp.setEftReference(m_loader.getReference());
		log.info( "MBankStatementLoader.importLine Check No=" + m_loader.getCheckNo());
		imp.setEftCheckNo(m_loader.getCheckNo());
		log.info( "MBankStatementLoader.importLine Memo=" + m_loader.getMemo());
		imp.setMemo(m_loader.getMemo());
		imp.setEftMemo(m_loader.getMemo());
		log.info( "MBankStatementLoader.importLine Payee Name=" + m_loader.getPayeeName());
		imp.setEftPayee(m_loader.getPayeeName());
		log.info( "MBankStatementLoader.importLine Payee Account No=" + m_loader.getPayeeAccountNo());
		imp.setEftPayeeAccount(m_loader.getPayeeAccountNo());
		log.info( "MBankStatementLoader.importLine EFT Transaction Type=" + m_loader.getTrxType());
		imp.setEftTrxType(m_loader.getTrxType());
		log.info( "MBankStatementLoader.importLine Currency=" + m_loader.getCurrency());
		imp.setEftCurrency(m_loader.getCurrency());
		imp.setISO_Code(m_loader.getCurrency());
		log.info( "MBankStatementLoader.importLine Charge Name=" + m_loader.getChargeName());
		imp.setChargeName(m_loader.getChargeName());
		log.info( "MBankStatementLoader.importLine Charge Amount=" + m_loader.getChargeAmt());
		imp.setChargeAmt(m_loader.getChargeAmt());
		imp.setProcessed(false);
		imp.setI_IsImported(false);
		
		result = imp.save();
		if (result)
		{
			importCount ++;
		}
		else
		{
			errorMessage = "LoadError";
		}
		imp = null;
		return result;
	}	//	importLine

	/**
	 * 	Return the most recent error
	 *	@return Error message
	 *	This error message will be handled as a Adempiere message,
	 *	(e.g. it can be translated)
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}	//	getErrorMessage

	/**
	 * 	Return the most recent error description
	 *	@return Error description
	 *	This is an additional error description, it can be used to provided
	 *	descriptive information, such as a file name or SQL error, that can not
	 *	be translated by the Adempiere message system.
	 */
	public String getErrorDescription()
	{
		return errorDescription;
	}	//	getErrorDescription
	
	/**
	 * 	The total number of statement lines loaded
	 *	@return Number of imported statement lines
	 */
	public int getImportCount()
	{
		return importCount;
	}	//	getLoadCount

 
 }	//MBankStatementLoader
