/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          * 
 * http://www.adempiere.org                                           * 
 *                                                                    * 
 * Copyright (C) Akuna Group Ltd.                                     * 
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
 *  - Michael Judd (michael.judd@akunagroup.com                       * 
 **********************************************************************/
package com.akunagroup.uk.postcode;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.adempiere.util.GenerateModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Manages connections with the postcode server, retrieves the response and
 * parses it. The best way to use it is to first issue a postcode lookup. 
 * Please see Capscan Doucmentation located at:
 *    http://www.capscanondemand.com/docs/OnDemand.pdf
 * 
 * @author Michael Judd
 * @version $Id$
 */
public class AddressLookup implements AddressLookupInterface {
	/** The logger. */
	private static Logger log = LogManager.getLogger(GenerateModel.class);

	/**
	 * The access code which is used along with clientID to authenticate the
	 * client.
	 */
	private String accessCode = null;
	
	/**
	 * The clientID. which is used along with accessCode to authenticate the
	 * client.
	 */
	private String clientID = null;

	/** The URL of the server to lookup the postcode on. */
	private String serverUrl = null;

	/**
	 * Postcode lookup results.<br>
	 * Keys are
	 * <li>the Postcode</li>
	 * <li>a Postcode object</li>
	 * 
	 */
	
	private HashMap<String, Object> postcodeData = new HashMap<String, Object>();

	// 
	/**
	 * Creates a new instance of AddressLookup.
	 */
	public AddressLookup() {
		// Default constructor logic here -if any!
	}
	
	

	/**
	 * Creates a new instance of AddressLookup.
	 * 
	 * @param serverUrl
	 *            URL of the postcode server.
	 * @param serialNo
	 *            Serial no. (used in authentication along with password)
	 * @param password
	 *            Password (used in authentication along with Serial no.)
	 * 
	 */
	public AddressLookup(String serverUrl, String serialNo, String password) {
		this.serverUrl = serverUrl.trim();
		this.clientID = serialNo.trim();
		this.accessCode = password.trim();
	}

	
	/**
	 * Builds the URL of the appropriate application on the server.
	 * 
	 * @param postcode
	 *            Postcode
	 * @return The URL
	 */
	private URL buildUrl(String postcode) {
		try {
			StringBuffer urlStr = new StringBuffer();
			urlStr.append(serverUrl);
			urlStr.append(serverUrl.endsWith("/") ? "" : "/");
			urlStr.append("/query?op=query&");
			urlStr.append("cc=" + clientID + "&");
			urlStr.append("ac=" + accessCode + "&");
			urlStr.append("DSGID=1&AmbiguityId=1&MaxReturns=250&FieldList=&ParamList=&sAppID=Adempiere&MaxReturns=200");
			urlStr.append("&Lookfor=" + postcode);		
			URL url = new URL(urlStr.toString());
			return url;
		} catch (MalformedURLException e) {
			log.error("Lookup URL: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	} // buildUrl

	/**
	 * Extracts the information out of server postcode lookup response.
	 * 
	 * @param resultDoc
	 *            The result document from server.
	 * @return The number of addresses extracted.
	 */
	private int extractPCodeInfo(Document xmlDoc) {
		
		Postcode postcode = new Postcode();		
		int resultCode = 0;
		
		xmlDoc.getDocumentElement().normalize();
		
		// Get the root element.
		Element doc = xmlDoc.getDocumentElement();
		
		//System.out.println("Root Node: " + doc.getNodeName());
		
		// Get the children.
		NodeList DataElements = doc.getElementsByTagName("DataElement"); 
		System.out.println("Number of DataElements: " + DataElements.getLength());
		
		for (int i = 0; i < DataElements.getLength(); i++ ) {
			
			// need to loop through elements to find the 
			Node firstDataNode = DataElements.item(i);
			
			if (firstDataNode.getNodeType() == Node.ELEMENT_NODE) {
					Element firstElement = (Element) firstDataNode;
					NodeList firstNameList = firstElement.getElementsByTagName("Name");
					Element NameElement = (Element) firstNameList.item(0);
					log.debug("Name: "  + NameElement.getChildNodes().item(0).getNodeValue().trim() + " Node: " + i);
					
					// Found and ADDR Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("ADDR")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);						
						postcode.setAddr(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}
					// Found and STREET Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("STREET")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setStreet1(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}
					//	Found and LOCALITY Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("LOCALITY")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setStreet2(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}
					// Found and POSTTOWN Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("POSTTOWN")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setCity(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}	 
						
					// Found and COUNTY Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("COUNTY")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setRegion(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}	 
					// Found and POSTCODE Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("POSTCODE")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setPostcode(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}	 
					// Found and COUNTRY Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("COUNTRY")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setCountry(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}	 
					// Found and COUNTRYCODE Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("COUNTRYCODE")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setCountryCode(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}	 
					// Found and TRADCOUNTY Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("TRADCOUNTY")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setTradCounty(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}	 
					// Found and LONLOCOUT Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("LONLOCOUT")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setLonLocation(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}	 
					// Found and ADMINCOUNTY Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("ADMINCOUNTY")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						postcode.setAdminCounty(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}
					//	Found and ADMINCOUNTY Node
					if (NameElement.getChildNodes().item(0).getNodeValue().trim().equals("RESCODE")) {
						NodeList firstValueList = firstElement.getElementsByTagName("Value");
						Element ValueElement = (Element) firstValueList.item(0);
						log.debug("Value: "  + ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim() + " Node: " + i);
						resultCode = (int) new Integer(ValueElement.getChildNodes().item(0).getNodeValue().replaceAll("\n","").trim());
					}
						
			}
		}
		
		
		int returnValue = 0;
		
		switch (resultCode)
		{
			case 0:	// internal error
			{
				String errorMsg = "An internal error occurred when connecting to Capscan Service";
				String errorTitle = "Error";
				JOptionPane.showMessageDialog(null, errorMsg, errorTitle, JOptionPane.ERROR_MESSAGE);
				break;
			}
			case 1:	// address matched to postcode
			{
				returnValue = 1;
				break;
			}
			case 2:	// No hits found
			{
				String errorMsg = "No matching addresses found for this post code";
				String errorTitle = "Error";
				JOptionPane.showMessageDialog(null, errorMsg, errorTitle, JOptionPane.ERROR_MESSAGE);
				break;
			}
			case 3:	// Insufficient information to determine postcode
			{
				String errorMsg = "Insufficient information provided to determin post code";
				String errorTitle = "Error";
				JOptionPane.showMessageDialog(null, errorMsg, errorTitle, JOptionPane.ERROR_MESSAGE);
				break;
			}
			case 4:	// Results cover more than one postcode
			{
				String errorMsg = "The results cover more than one postcode";
				String errorTitle = "Error";
				JOptionPane.showMessageDialog(null, errorMsg, errorTitle, JOptionPane.ERROR_MESSAGE);
				break;
			}
			case 5:	// Not a UK address
			{
				String errorMsg = "Not a UK address";
				String errorTitle = "Error";
				JOptionPane.showMessageDialog(null, errorMsg, errorTitle, JOptionPane.ERROR_MESSAGE);
				break;
			}
		}
			
			
		if (returnValue == 1)	// address found
		{
			// put the London Location in to the City where the County is London and the Country is the UK
			if (postcode.getRegion().equals("London") && (postcode.getCountryCode().equals("UK") || postcode.getCountryCode().equals("GB")))
				postcode.setCity(postcode.getLonLocation());
			else
				postcode.setRegion(postcode.getAdminCounty());
		
			postcodeData.put(postcode.getPostcode(), postcode);
		} else
			log.warn("Postcode lookup error: " + postcode.getPostcode());
		
		//if (postcode.getPostcode().length()==0)
		//	System.out.println("Postcode NOT found! ");
		//else
		
		return returnValue;
	}

	/**
	 * Fetch the lookup result from server.
	 * 
	 * @param cgiUrl
	 *            The URL of the CGI or application to call on server.
	 * @return The result in form of a document.
	 */
	private Document fetchResult(URL cgiUrl) {
		try {
			// Get document builder.
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			// Get the connection.
			URLConnection URLconnection = cgiUrl.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			// TODO try block here to catch connection (java.net) exceptions
			int responseCode = httpConnection.getResponseCode();
			// Fetch the result.
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream inStream = httpConnection.getInputStream();
				try {
					Document doc = docBuilder.parse(inStream);
					return doc;
				} catch (org.xml.sax.SAXException e) {
					log.error("Fetch Result: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			log.error("Fetch Result: " + e.getMessage());
			e.printStackTrace();
		}

		return null;
	} // fetchResult

	
	/**
	 * @return the accessCode
	 */
	public String getPassword() {
		return accessCode;
	}

	@Override
	public HashMap<String, Object> getAddressData() {
		return (HashMap<String, Object>) postcodeData;
	}
	
	/**
	 * @return the clientID
	 */
	public String getClientID() {
		return clientID;
	}

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * Performs a postcode lookup fetching a set of addresses from server.
	 * 
	 * @param postcode
	 *            The postcode to lookup for.
	 * @return The number of addresses fetched or -1 in case of error.
	 */
	@Override
	public int lookupPostcode(String postcode) {
		// Build server application URL.
		URL url = buildUrl(postcode);
		if (url == null) {
			log.error("URL: Can't build URL.");
			return -1;
		}
		// Fetch the lookup result from server.
		Document resultDoc = fetchResult(url);
		if (resultDoc == null) {
			log.error("Result document is null.");
			return -1;
		}
		// Extract addresses and postkeys out of lookup results.
		return extractPCodeInfo(resultDoc);
	}
	
	
	/**
	 * @param accessCode
	 *            the accessCode to set
	 */
	@Override
	public void setPassword(String password) {
		this.accessCode = password.trim();
	}

	/**
	 * @param clientID
	 *            the clientID to set
	 */
	@Override
	public void setClientID(String clientID) {
		this.clientID = clientID.trim();
	}

	/**
	 * @param serverUrl
	 *            the serverUrl to set
	 */
	@Override
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl.trim();
	}

	/**
	 * Creates a new instance of PostcodeLookup.
	 */
	@Override
	public AddressLookupInterface newInstance() {
		return new AddressLookup();
	}

// metas
	@Override
	public void setCountryCode(String countryCode)
	{
	}
	@Override
	public void setCity(String city)
	{
	}

	@Override
	public boolean isRegisterLocalSupported()
	{
		return false;
	}

	@Override
	public void registerLocal(String postcode)
	{
		throw new UnsupportedOperationException();
	}

} // PostcodeLookup
