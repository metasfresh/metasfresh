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
package org.compiere.impexp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.compiere.model.MBankStatementLoader;
import org.compiere.util.Env;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 *	Parser for OFX bank statements
 *
 *  This class is a parser for OFX bankstatements. OFX versions from 102 to 202 
 *  and MS-Money OFC message sets are supported.
 *  Only fully XML compliant OFX data is supported. Files that are not XML
 *  compliant, e.g. OFX versions older then 200, will be preprocessed by
 *  the OFX1ToXML class before parsing.
 *  This class should be extended by a class that obtains the data to be parsed
 *  for example from a file, or using HTTP.
 *
 *	@author Eldir Tomassen
 *	@version $Id: OFXBankStatementHandler.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */

public abstract class OFXBankStatementHandler extends DefaultHandler
{

	protected MBankStatementLoader 	m_controller;
	protected String 				m_errorMessage = "";
	protected String 				m_errorDescription = "";
	protected BufferedReader 		m_reader = null;
	
	protected SAXParser 			m_parser;
	protected boolean 				m_success = false;
	//private boolean m_valid = false;

	protected StatementLine 		m_line;
	protected String 				m_routingNo = "0";
	protected String 				m_bankAccountNo = null;
	protected String 				m_currency = null;
	protected int 					HEADER_SIZE = 20;
	
	protected boolean 				m_test = false;
	protected Timestamp 			m_dateLastRun = null;
	protected Timestamp 			m_statementDate = null;

	StringBuffer 					m_valueBuffer = new StringBuffer();
	


	/**	XML OFX Tag					*/
	public static final String	XML_OFX_TAG = "OFX";
	
	/**	XML SIGNONMSGSRSV2 Tag				*/
	public static final String	XML_SIGNONMSGSRSV2_TAG = "SIGNONMSGSRSV2";
	/**	XML SIGNONMSGSRSV1 Tag				*/
	public static final String	XML_SIGNONMSGSRSV1_TAG = "SIGNONMSGSRSV1";
	
	/**	Record-response aggregate			*/
	public static final String	XML_SONRS_TAG = "SONRS";
	/**	Date and time of the server response		*/
	public static final String	XML_DTSERVER_TAG = "DTSERVER";	
	/**	Use USERKEY instead of USERID and USEPASS	*/
	public static final String	XML_USERKEY_TAG = "USERKEY";	
	/**	Date and time that USERKEY expires		*/
	public static final String	XML_TSKEYEXPIRE_TAG = "TSKEYEXPIRE";	
	/**	Language					*/
	public static final String	XML_LANGUAGE_TAG = "LANGUAGE";
	/**	Date and rime last update to profile information*/
	public static final String	XML_DTPROFUP_TAG = "DTPROFUP";
		
	/**	Status aggregate				*/
	public static final String	XML_STATUS_TAG = "STATUS";
	
	
	/**	Statement-response aggregate			*/
	public static final String	XML_STMTRS_TAG = "STMTRS";
	/**	XML CURDEF Tag					*/
	public static final String	XML_CURDEF_TAG = "CURDEF";
	
	/**	Account-from aggregate				*/
	public static final String	XML_BANKACCTFROM_TAG = "BANKACCTFROM";
	/**	Bank identifier					*/
	public static final String	XML_BANKID_TAG = "BANKID";
	/**	Branch identifier				*/
	public static final String	XML_BRANCHID_TAG = "BRANCHID";
	/**	XML ACCTID Tag					*/
	public static final String	XML_ACCTID_TAG = "ACCTID";
	/**	Type of account					*/
	public static final String	XML_ACCTTYPE_TAG = "ACCTTYPE";
	/**	Type of account					*/
	public static final String	XML_ACCTTYPE2_TAG = "ACCTTYPE2";
	/**	Checksum					*/
	public static final String	XML_ACCTKEY_TAG = "ACCTKEY";
	
	/**	XML BANKTRANLIST Tag				*/
	public static final String	XML_BANKTRANLIST_TAG = "BANKTRANLIST";
	/**	XML DTSTART Tag					*/
	public static final String	XML_DTSTART_TAG = "DTSTART";
	/**	XML DTEND Tag					*/
	public static final String	XML_DTEND_TAG = "DTEND";
	/**	XML STMTTRN Tag					*/
	public static final String	XML_STMTTRN_TAG = "STMTTRN";
	/**	XML TRNTYPE Tag					*/
	public static final String	XML_TRNTYPE_TAG = "TRNTYPE";
	/**	XML TRNAMT Tag					*/
	public static final String	XML_TRNAMT_TAG = "TRNAMT";
	/**	Transaction date				*/
	public static final String	XML_DTPOSTED_TAG = "DTPOSTED";
	/**	Effective date				*/
	public static final String	XML_DTAVAIL_TAG = "DTAVAIL";
	/**	XML FITID Tag					*/
	public static final String	XML_FITID_TAG = "FITID";
	/**	XML CHECKNUM Tag 				*/
	public static final String	XML_CHECKNUM_TAG = "CHECKNUM";
	/**	XML CHKNUM Tag (MS-Money OFC)			*/
	public static final String	XML_CHKNUM_TAG = "CHKNUM";
	/**	XML REFNUM Tag		*/
	public static final String	XML_REFNUM_TAG = "REFNUM";	
	/**	Transaction Memo				*/
	public static final String	XML_MEMO_TAG = "MEMO";
	/**	XML NAME Tag				*/
	public static final String	XML_NAME_TAG = "NAME";
	/**	XML PAYEEID Tag				*/
	public static final String	XML_PAYEEID_TAG = "PAYEEID";
	/**	TXML PAYEE Tag				*/
	public static final String	XML_PAYEE_TAG = "PAYEE";
	
	/**	XML LEDGERBAL Tag				*/
	public static final String	XML_LEDGERBAL_TAG = "LEDGERBAL";		
	/**	XML BALAMT Tag					*/
	public static final String	XML_BALAMT_TAG = "BALAMT";		
	/**	XML DTASOF Tag					*/
	public static final String	XML_DTASOF_TAG = "DTASOF";	
	/**	XML AVAILBAL Tag				*/
	public static final String	XML_AVAILBAL_TAG = "AVAILBAL";	
	/**	XML MKTGINFO Tag				*/
	public static final String	XML_MKTGINFO_TAG = "MKTGINFO";	
	
	/**
	 * 	Initialize the loader
	 * 	 * @param controller Reference to the BankStatementLoaderController
	@return Initialized succesfully
	 */
	protected boolean init(MBankStatementLoader controller)
	{
		boolean result = false;
		if (controller == null)
		{
			m_errorMessage = "ErrorInitializingParser";
			m_errorDescription = "ImportController is a null reference";
			return result;
		}
		this.m_controller = controller;
		try
		{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			m_parser = factory.newSAXParser();
			result = true;
		}
		catch(ParserConfigurationException e)
		{
			m_errorMessage = "ErrorInitializingParser";
			m_errorDescription = "Unable to configure SAX parser: " + e.getMessage();
		}
		catch(SAXException e)
		{
			m_errorMessage = "ErrorInitializingParser";
			m_errorDescription = "Unable to initialize SAX parser: " + e.getMessage();
		}
		return result;
	}	//	init
	
	/**
	 * 	Attach OFX input source, detect whether we are dealing with OFX1
	 *	(SGML) or OFX2 (XML). In case of OFX1, process the data to create 
	 *	valid XML.
	 *	@param is Reference to the BankStatementLoaderController
	 *	@return true if input is valid OFX data
	 */
	protected boolean attachInput(InputStream is)
	{
		boolean isOfx1 = true;
		boolean result = false;
		
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			reader.mark(HEADER_SIZE + 100);
			String header = "";
			for (int i = 0; i < HEADER_SIZE; i++)
			{
				header = header + reader.readLine();
			}
			if ((header.indexOf("<?OFX") != -1) || (header.indexOf("<?ofx") != -1))
			{
				isOfx1 = false;
			}
			else if ((header.indexOf("<?XML") != -1) || (header.indexOf("<?xml") != -1))
			{
				isOfx1 = false;
				//deted specific OFX version
			}
			else 
			{
				isOfx1 = true;
				//detect specific OFX version
			}
			reader.reset();
			
			if (isOfx1)
			{
				m_reader = new BufferedReader(new InputStreamReader(new OFX1ToXML(reader)));
			}
			else
			{
				m_reader = reader;
			}
			result = true;
		}
		catch(IOException e)
		{
			m_errorMessage = "ErrorReadingData";
			m_errorDescription = e.getMessage();
			return result;
		}

		return result;
	}	//	attachInput
	
	/**	Verify the validity of the OFX data
	 *	@return true if input is valid OFX data
	 */
	public boolean isValid()
	{
		boolean result = true;
		/*
		try
		{
			if (loadLines())
			{
				result = true;
				test = false;
			}
			m_reader.reset();
		}
		catch(IOException e)
		{
			m_errorMessage = "ErrorReadingData";
			m_errorDescription = e.getMessage();			
		}
		*/
		return result;
	}	//isValid

	/**	Check wether the import was succesfull
	 *	@return true if all statement lines have been imported succesfully
	 */
	public boolean importSuccessfull()
	{
		/*
		 * Currently there are no checks after the statement lines are read.
		 * Once all lines are read correctly a successfull import is assumed.
		 */
		return m_success;
	}	//	importSuccessfull
	
	/**
	 * Read statementlines from InputStream.
	 * @return load success
	 * This method will be invoked from ImportController.
	 */
	public boolean loadLines()
	{
		boolean result = false;
		try
		{
			m_parser.parse(new InputSource(m_reader), this);
			result = true;
			m_success = true;
		}
		catch(SAXException e)
		{
			m_errorMessage = "ErrorParsingData";
			m_errorDescription = e.getMessage();
		}
		catch(IOException e)
		{
			m_errorMessage = "ErrorReadingData";
			m_errorDescription = e.getMessage();
		}
		return result;
		
	}	//	loadLines
	
	/**
	 * Method getDateLastRun
	 * @return Timestamp
	 */
	public Timestamp getDateLastRun()
	{
		return m_dateLastRun;
	}
	
	/**
	 * Method getRoutingNo
	 * @return String
	 */
	public String getRoutingNo()
	{
		return m_line.routingNo;
	}
	
	/**
	 * Method getBankAccountNo
	 * @return String
	 */
	public String getBankAccountNo()
	{
		return m_line.bankAccountNo;
	}
	
	/**
	 * Method getStatementReference
	 * @return String
	 */
	public String getStatementReference()
	{
		return m_line.statementReference;
	}
	
	/**
	 * Method getStatementDate
	 * @return Timestamp
	 */
	public Timestamp getStatementDate()
	{
		return m_statementDate;
	}
	
	/**
	 * Method getReference
	 * @return String
	 */
	public String getReference()
	{
		return m_line.reference;
	}
	
	/**
	 * Method getStatementLineDate
	 * @return Timestamp
	 */
	public Timestamp getStatementLineDate()
	{
		return m_line.statementLineDate;
	}
	
	/**
	 * Method getValutaDate
	 * @return Timestamp
	 */
	public Timestamp getValutaDate()
	{
		return m_line.valutaDate;
	}
	
	/**
	 * Method getTrxType
	 * @return String
	 */
	public String getTrxType()
	{
		return m_line.trxType;
	}
	
	/**
	 * Method getIsReversal
	 * @return boolean
	 */
	public boolean getIsReversal()
	{
		return m_line.isReversal;
	}
	
	/**
	 * Method getCurrency
	 * @return String
	 */
	public String getCurrency()
	{
		return m_line.currency;
	}
	
	/**
	 * Method getStmtAmt
	 * @return BigDecimal
	 */
	public BigDecimal getStmtAmt()
	{
		return m_line.stmtAmt;
	}
	
	/**
	 * Method getTrxAmt
	 * @return Transaction Amount
	 */
	public BigDecimal getTrxAmt()
	{
		/* assume total amount = transaction amount
		 * todo: detect interest & charge amount
		 */
		return m_line.stmtAmt;
	}
	
	/**
	 * Method getInterestAmount
	 * @return Interest Amount
	 */
	public BigDecimal getInterestAmt()
	{
		return Env.ZERO;
	}
	
	
	/**
	 * Method getMemo
	 * @return String
	 */
	public String getMemo()
	{
		return m_line.memo;
	}
	
	/**
	 * Method getChargeName
	 * @return String
	 */
	public String getChargeName()
	{
		return m_line.chargeName;
	}
	
	/**
	 * Method getChargeAmt
	 * @return BigDecimal
	 */
	public BigDecimal getChargeAmt()
	{
		return m_line.chargeAmt;
	}
	
	/**
	 * Method getTrxID
	 * @return String
	 */
	public String getTrxID()
	{
		return m_line.trxID;
	}
	
	/**
	 * Method getPayeeAccountNo
	 * @return String
	 */
	public String getPayeeAccountNo()
	{
		return m_line.payeeAccountNo;
	}
	
	/**
	 * Method getPayeeName
	 * @return String
	 */
	public String getPayeeName()
	{
		return m_line.payeeName;
	}
	
	/**
	 * Method getCheckNo
	 * @return String
	 */
	public String getCheckNo()
	{
		return m_line.checkNo;
	}
	
	
	/**
	 * New XML element detected. The XML nesting
	 * structure is saved on the m_context stack.
	 * @param uri String
	 * @param localName String
	 * @param qName String
	 * @param attributes Attributes
	 * @throws org.xml.sax.SAXException
	 * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
	 */
	public void startElement (String uri, String localName, String qName, Attributes attributes)
		throws org.xml.sax.SAXException
	{
		boolean validOFX = true;
		/*
		 * Currently no validating is being done, valid OFX structure is assumed.
		 */
		if (!validOFX)
		{
			m_errorDescription = "Invalid OFX syntax: " + qName;
			throw new SAXException("Invalid OFX syntax: " + qName);
		}
		if (qName.equals(XML_STMTTRN_TAG))
		{
			m_line = new StatementLine(m_routingNo, m_bankAccountNo, m_currency);
		}
		
		m_valueBuffer=new StringBuffer();
		
	}	//	startElement
	
	/**
	 * Characters read from XML are assigned to a variable, based on the current m_context.
	 * No checks are being done, it is assumed that the context is correct.
	 * @param ch char[]
	 * @param start int
	 * @param length int
	 * @throws SAXException
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 **/	
	public void characters (char ch[], int start, int length) throws SAXException
	{

		m_valueBuffer.append(ch,start,length);
		
	}	//	characters
	
	
	/**
	 * Check for valid XML structure. (all tags are properly ended).
	 * The statements are passed to ImportController when the statement end (</STMTTRN>) 
	 * is detected.
	 * @param uri String
	 * @param localName String
	 * @param qName String
	 * @throws SAXException
	 * @see org.xml.sax.ContentHandler#endElement(String, String, String)
	 */
	public void endElement (String uri, String localName, String qName) throws SAXException
	{
		
		String XML_TAG=qName;
		String value=m_valueBuffer.toString();
		
		try
		{
			//Read statment level data
			
			/*
			 * Default currency for this set of statement lines
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>
			 */
			if (XML_TAG.equals(XML_CURDEF_TAG))
			{
				m_currency = value;
			}
			
			/* Routing Number (or SWIFT Code) for this set of statement lines
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKACCTFROM>
			 */
			else if (XML_TAG.equals(XML_BANKID_TAG))
			{
				m_routingNo = value;
			}
			
			/*
			 * Bank Account Number for this set of bank statement lines
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKACCTFROM>
			 */
			else if (XML_TAG.equals(XML_ACCTID_TAG))
			{
				m_bankAccountNo = value;
			}
			
			/*
			 * Last date for this set of statement lines
			 * This is the date that should be specified as the <DTSTART>
			 * for the next batch of statement lines, in order not to miss any 
			 * transactions.
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST>
			 */
			else if (XML_TAG.equals(XML_DTEND_TAG))
			{
				m_dateLastRun = parseOfxDate(value);
			}
			
			/*
			 *
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<AVAILBAL>
			 */
			else if (XML_TAG.equals(XML_DTASOF_TAG))
			{
				m_statementDate = parseOfxDate(value);
			}
			
			
			//Read statement line level data
			
			/*
			 * Transaction type, e.g. DEBIT, CREDIT, SRVCHG
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if (XML_TAG.equals(XML_TRNTYPE_TAG))
			{
				m_line.trxType = value;
			}
			
			/*
			 * Statement line date
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if (XML_TAG.equals(XML_DTPOSTED_TAG))
			{
				m_line.statementLineDate = parseOfxDate(value);
			}
			
			/*
			 * Valuta date
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if (XML_TAG.equals(XML_DTAVAIL_TAG))
			{
				m_line.valutaDate = parseOfxDate(value);
			}
			
			/*
			 * Total statement line amount
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if (XML_TAG.equals(XML_TRNAMT_TAG))
			{
				m_line.stmtAmt = new BigDecimal(value);
			}
			
			/*
			 * Transaction Identification
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if (XML_TAG.equals(XML_FITID_TAG))
			{
				m_line.trxID = value;
			}
			
			/*
			 * Check number for check transactions
			 * CHECKNUM for generic OFX, CHKNUM for MS-Money OFC
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if ((XML_TAG.equals(XML_CHECKNUM_TAG)) || (XML_TAG.equals(XML_CHKNUM_TAG)))
			{
				m_line.checkNo = value;
			}
			
			/*
			 * Statement line reference
			 * Additional transaction reference information
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if (XML_TAG.equals(XML_REFNUM_TAG))
			{
				m_line.reference = value;
			}
			
			/*
			 * Transaction memo
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if (XML_TAG.equals(XML_MEMO_TAG))
			{
				m_line.memo = value;
			}
			
			/*
			 * Payee Name
			 * <OFX>-<BANKMSGSRSV2>-<STMTTRNRS>-<STMTRS>-<BANKTRANLIST><STMTTRN>
			 */
			else if (XML_TAG.equals(XML_NAME_TAG))
			{
				m_line.payeeName = value;
			}
		}
		catch(Exception e)
		{
			
			m_errorDescription = "Invalid data: " + value + " <-> " + e.getMessage();
			throw new SAXException("Invalid data: " + value);
		}
		
		if (qName.equals(XML_STMTTRN_TAG))
		{
			if (!m_test)
			{
				if (!m_controller.saveLine())
				{
					m_errorMessage = m_controller.getErrorMessage();
						m_errorDescription = m_controller.getErrorDescription();
					throw new SAXException(m_errorMessage);
				}
			}
		}
	}	//	endElement
	
	
	/**
	 * Method parseOfxDate
	 * @param value String
	 * @return Timestamp
	 * @throws ParseException
	 */
	private Timestamp parseOfxDate(String value) throws ParseException
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			sdf.setLenient(false);
			return new Timestamp (sdf.parse(value).getTime());
		}
		catch(Exception e)
		{
			throw new ParseException("Error parsing date: " + value, 0);
		}
	}   //parseOfxDate
	
	/**
	 * Method getLastErrorMessage
	 * @return String
	 */
	public String getLastErrorMessage()
	{
		return m_errorMessage;
	}
	
	/**
	 * Method getLastErrorDescription
	 * @return String
	 */
	public String getLastErrorDescription()
	{
		return m_errorDescription;
	}
		
	/**
	 * @author ET
	 * @version $Id: OFXBankStatementHandler.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
	 */
	class StatementLine
	{
		protected String routingNo = null;
		protected String bankAccountNo = null;
		protected String statementReference = null;
		
		
		protected Timestamp statementLineDate = null;
		protected String reference = null;
		protected Timestamp valutaDate;
		protected String trxType = null; 
		protected boolean isReversal = false; 
		protected String currency = null;
		protected BigDecimal stmtAmt = null; 
		protected String memo = null; 
		protected String chargeName = null;
		protected BigDecimal chargeAmt = null;	
		protected String payeeAccountNo = null;
		protected String payeeName = null;
		protected String trxID = null;
		protected String checkNo = null;
		
		
		/**
		 * Constructor for StatementLine
		 * @param RoutingNo String
		 * @param BankAccountNo String
		 * @param Currency String
		 */
		public StatementLine(String RoutingNo, String BankAccountNo, String Currency)
		{
			bankAccountNo = BankAccountNo;
			routingNo = RoutingNo;
			currency = Currency;
		}

	}

}	//OFXBankStatementHandler
