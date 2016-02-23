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

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CLogMgt;

/**
 * 	Optimal Payment Processor Services Interface.
 *	
 *  @author Jorg Janke
 *  @version $Id: PP_Optimal.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class PP_Optimal extends PaymentProcessor
	implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3588486265248400291L;



	/**
	 * 	PP_Optimal
	 */
	public PP_Optimal ()
	{
		super ();
	}	//	PP_Optimal

	/**	Status					*/
	private boolean		m_ok = false;
	/** Optimal Client Version	*/
	private static final String	_CLIENT_VERSION = "1.1";
	
	//	Mandatory parameters
	protected static final String MERCHANT_ID          = "merchantId";
	protected static final String MERCHANT_PWD         = "merchantPwd";
	protected static final String ACCOUNT_ID           = "account";
	protected static final String CIPHER               = "Cipher";
	protected static final String PAYMENT_SERVER       = "PaymentServerURL";
	protected static final String PAYMENT_SERVER_PORT  = "PaymentServerPort";
	protected static final String PROXY_SERVER         = "ProxyServer";
	protected static final String PROXY_PORT           = "ProxyPort";
	protected static final String PROXY_USERID         = "ProxyUserId";
	protected static final String PROXY_PWD            = "ProxyPwd";
	protected static final String ACTIVE_RECOVERY      = "ActiveRecovery";
	protected static final String HTTPVERSION          = "HTTPVersion";
	  
	//	Optional parameters
	protected static final String RETRIES              = "Retries";
	protected static final String TIMEOUT              = "Timeout";
	protected static final String LOGLEVEL             = "LogLevel";
	protected static final String LOG_BASE_PATH        = "LogBasePath";
	protected static final String LOG_FILENAME         = "LogFilename";
	protected static final String LOG_MAX_SIZE         = "LogMaxSize";
	protected static final String LOG_MAX_BACKUP       = "LogMaxBackupFiles";

	//	some request parameters
	protected static final String MERCHANT_TXN         = "merchantTxn";
	protected static final String MERCHANT_DATA        = "merchantData";
	protected static final String CLIENT_VERSION       = "clientVersion";
	protected static final String TXN_NUMBER           = "txnNumber";
	protected static final String CARD_NUMBER          = "cardNumber";
	protected static final String CARD_EXPIRATION      = "cardExp";
	protected static final String CARD_TYPE            = "cardType";
	protected static final String STREET               = "streetAddr";
	protected static final String STREET2              = "streetAddr2";
	protected static final String EMAIL                = "email";
	protected static final String PHONE                = "phone";
	protected static final String PROVINCE             = "province";
	protected static final String COUNTRY              = "country";
	protected static final String CITY                 = "city";
	protected static final String ZIP                  = "zip";
	protected static final String CVD_INDICATOR        = "cvdIndicator";
	protected static final String CVD_INDICATOR_None   = "0";
	protected static final String CVD_INDICATOR_Provided = "1";
	protected static final String CVD_VALUE	        = "cvdValue";
	protected static final String CVD_INFO	            = "cvdInfo";
	protected static final String CUST_NAME1           = "custName1";
	protected static final String CUST_NAME2           = "custName2";
	  
	//	some response parameters
	protected static final String STATUS               = "status";
	protected static final String TXN_TYPE             = "txnType";
	protected static final String AMOUNT               = "amount";
	protected static final String CURRENT_AMOUNT       = "curAmount";
	protected static final String ERROR_CODE           = "errCode";
	protected static final String ERROR_STRING         = "errString";
	protected static final String SUB_ERROR            = "subError";
	protected static final String SUB_ERROR_STRING     = "subErrorString";

	protected static final String AUTH_CODE            = "authCode";
	protected static final String AUTH_TIME            = "authTime";
	protected static final String AVS_INFO             = "avsInfo";
	  

	//	some useful constants
	protected static final String QUERY_OPERATION          = "Q";
	protected static final String FAILURE_LOOKUP_OPERATION = "FT";
	/** Operation p1-8					*/
	protected static final String OPERATION                = "operation";
	protected static final String OPERATION_Purchase       = "P";
	protected static final String OPERATION_Authorization  = "A";
	protected static final String OPERATION_Settlement 	= "S";
	
	
	
	//	Stratus returned by Failure Lookup Request
	protected static final String STATUS_SEARCH_COMPLETED  = "status=C" ;
	//	Status returned by a Query Request
	protected static final String REQUEST_PENDING          = "status=P";
	protected static final String REQUEST_COMPLETE         = "status=C";
	protected static final String REQUEST_FAILED           = "status=F";
	protected static final String AUTHORIZATION_COMPLETE   = "status=A";
	protected static final String AUTHORIZATION_FAILED     = "status=AF";
	protected static final String SETTLEMENT_COMPLETE      = "status=S";
	protected static final String SETTLEMENT_FAILED        = "status=SF";
	protected static final String MANUAL_INTERVENTION      = "status=M";
	protected static final String REQUEST_ABORTED          = "status=AB";
	protected static final String REQUEST_NOT_FOUND        = "status=NF";
	protected static final String UNKNOWN_TYPE             = "status=U";
	protected static final String REQUEST_ERROR            = "status=E";
	/**	AVS Codes					*/
	protected static Properties	AVSCodes = new Properties();
	/**	Card Types					*/
	protected static Properties	CARDTypes = new Properties();
	/**	CVD Info					*/
	protected static Properties	CVDInfo = new Properties();
	
	static
	{
		//	Page 1-9
		AVSCodes.put("X", "Exact. Nine digit zip and address match");
		AVSCodes.put("Y", "Yes. Five digit zip and address match");
		AVSCodes.put("A", "Address matches, Zip not");
		AVSCodes.put("W", "Nine digit zip matches, address not");
		AVSCodes.put("Z", "Five digit zip matches, address not");
		AVSCodes.put("N", "No Part matches");
		AVSCodes.put("U", "Address info unabailable");
		AVSCodes.put("R", "Retry");
		AVSCodes.put("S", "AVS not supported");
		AVSCodes.put("E", "AVS not supported for this industry");
		AVSCodes.put("B", "AVS not performed");
		AVSCodes.put("Q", "Unknown response from issuer");
		//	Page 1-14
		CARDTypes.put("AMEX", "AM");
		CARDTypes.put("DINERS", "DI");
		CARDTypes.put("VISA", "VI");
		//	CVD Info 1-20
		CVDInfo.put("M", "Match");
		CVDInfo.put("N", "No Match");
		CVDInfo.put("P", "Not Processed");
		CVDInfo.put("S", "Not Present");
		CVDInfo.put("U", "Issuer not certified");
	}
	
	/**
	 *  Get Version
	 *  @return version
	 */
	public String getVersion()
	{
		return "Optimal " + _CLIENT_VERSION;
	}   //  getVersion

	/**
	 * 	Process CC
	 *	@return true if OK
	 *	@throws IllegalArgumentException
	 */
	public boolean processCC ()
		throws IllegalArgumentException
	{
		log.fine(p_mpp.getHostAddress() + ":" + p_mpp.getHostPort() + ", Timeout=" + getTimeout()
			+ "; Proxy=" + p_mpp.getProxyAddress() + ":" + p_mpp.getProxyPort() + " " + p_mpp.getProxyLogon() + " " + p_mpp.getProxyPassword());
		setEncoded(true);

		String urlString = p_mpp.getHostAddress();
			//	"https://realtime.firepay.com/servlet/DPServlet";
			//	"https://realtime.test.firepay.com/servlet/DPServlet";
		if (p_mpp.getHostPort() != 0)
			urlString += ":" + p_mpp.getHostPort();
		
		/** General Parameters			*/
		StringBuffer param = new StringBuffer(200);
		//	 Merchant username and password.
		param.append(createPair(MERCHANT_ID, p_mpp.getUserID(), 80))
			.append(AMP).append(createPair(MERCHANT_PWD, p_mpp.getPassword(), 20))
			.append(AMP).append(createPair(ACCOUNT_ID, p_mpp.getPartnerID(), 10));
	//	param.append(AMP).append(createPair(MERCHANT_DATA, "comment", 255));

		/**	Cipher supported : 	SSL_RSA_WITH_RC4_128_MD5, SSL_RSA_WITH_RC4_128_SHA,	SSL_RSA_WITH_DES_CBC_SHA, SSL_RSA_WITH_3DES_EDE_CBC_SHA, SSL_RSA_EXPORT_WITH_RC4_40_MD5
		param.append("&Cipher=SSL_RSA_WITH_RC4_128_MD5");
		//	HTTP Version
		param.append("&HTTPVersion=1.0");
		//	Path to the keystore (cacerts) file.
		**/

		param.append(AMP).append(createPair(CARD_TYPE, "VI", 6));
		param.append(AMP).append(createPair(CARD_NUMBER, p_mp.getCreditCardNumber(), 19));
		param.append(AMP).append(createPair(CARD_EXPIRATION, p_mp.getCreditCardExp("/"), 5));
		param.append(AMP).append(createPair(AMOUNT, p_mp.getPayAmtInCents(), 10));
		param.append(AMP).append(createPair(OPERATION, OPERATION_Purchase, 1));
		param.append(AMP).append(createPair(MERCHANT_TXN, p_mp.getC_Payment_ID(), 255));
		param.append(AMP).append(createPair(CLIENT_VERSION, _CLIENT_VERSION, 4));
		param.append(AMP).append(createPair(CUST_NAME1, p_mp.getA_Name(), 255));
		param.append(AMP).append(createPair(STREET, p_mp.getA_Street(), 255));
		param.append(AMP).append(createPair(CITY, p_mp.getA_City(), 255));
		param.append(AMP).append(createPair(PROVINCE, p_mp.getA_State(), 2));
		param.append(AMP).append(createPair(ZIP, p_mp.getA_Zip(), 10));
		param.append(AMP).append(createPair(COUNTRY, p_mp.getA_Country(), 2));
	//	param.append(AMP).append(createPair("&phone", p_mp.getA_Phone(), 40));
		param.append(AMP).append(createPair("&email", p_mp.getA_EMail(), 40));
		param.append(AMP).append(createPair(CVD_INDICATOR, CVD_INDICATOR_Provided, 1));
		param.append(AMP).append(createPair(CVD_VALUE, "123", 4));
		
		try
		{
			log.fine("-> " + param.toString());
			Properties prop = getConnectPostProperties(urlString, param.toString());
			m_ok = prop != null;
			//	authCode=, authTime=1132330817, subErrorString=Card has expired: 04/04, errCode=91, clientVersion=1.1, status=E, subError=0, actionCode=CP, errString=Invalid Payment Information. Please verify request parameters.
			//	authCode=197705, authTime=1132336527, curAmount=0, avsInfo=B, clientVersion=1.1, status=SP, amount=200, cvdInfo=M, txnNumber=1000000
			if (m_ok)
			{
				String status = prop.getProperty(STATUS);
				m_ok = status != null && status.equals("SP");	//	Successful Purchase
				String authCode = prop.getProperty(AUTH_CODE);
				String authTime = prop.getProperty(AUTH_TIME);
				//
				String errCode = prop.getProperty(ERROR_CODE);
				String errString = prop.getProperty(ERROR_STRING);
				String subError = prop.getProperty(SUB_ERROR);
				String subErrorString = prop.getProperty(SUB_ERROR_STRING);
				String actionCode = prop.getProperty("actionCode");
				//
				String authorisedAmount = prop.getProperty(CURRENT_AMOUNT);
				String amount = prop.getProperty(AMOUNT);
				String avsInfo = prop.getProperty(AVS_INFO);
				String cvdInfo = prop.getProperty(CVD_INFO);
				
				log.fine("<- Status=" + status + ", AuthCode=" + authCode + ", Error=" + errString);
			}
			if (!m_ok)
				log.warning("<- " + prop);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, param.toString(), e);
			m_ok = false;
		}
		return m_ok;
	}	//	processCC

	/**
	 * 	Is Processed OK
	 *	@return true of ok
	 */
	public boolean isProcessedOK ()
	{
		return m_ok;
	}	//	isProcessedOK
	

	
	/**
	 * 	Test
	 *	@param args ifnored
	 */
	public static void main (String[] args)
	{
/**
> Test Administration Server and Login
> -----------------------------------
> The information you require to access the TEST administration server 
> is (case sensitivity matters):
> 
> Web Site: https://admin.test.firepay.com
> 
> Test Account #2
> Username: sparctwo001
> User password: abcd1234
> 
> Test Payment Server URL
> -----------------------
> To connect your web site to our TEST payment service, your technical 
> people will use the following URL:
> 
> https://realtime.test.firepay.com/servlet/DPServlet
> 
> Test Cards
> ----------
> Below are the cards you can use in the test environment. Transactions 
> done with these cards will either be successful or fail depending on 
> the amount provided with the transaction (see below).
> 
> Visa:
> 4387751111011
> 4387751111029
> 4387751111111038
> 4387751111111053
> 
> MasterCard:
> 5442981111111015
> 5442981111111023
> 5442981111111031
> 5442981111111056
> 
> The following amounts will cause either approval or various declines 
> with the cards mentioned above:
> Amount less than 20.00 = Approval
> 20.00 to 29.99 = (221, 1002) Reenter
> 30.00 to 39.99 = (221, 1003) Referral
> 40.00 to 49.99 = (221, 1004) PickUp
> 50.00 to 59.99 = (34, 1005) Decline
> 60.00 to 69.99 = (2) Timeout
> Amount greater than 69.99 = Approval
**/
		CLogMgt.initialize(true);
		CLogMgt.setLevel(Level.ALL);
		PP_Optimal pp = new PP_Optimal();
		pp.processCC();
		pp.isProcessedOK();
		
	}	//	main
	
}	//	PP_Optimal
