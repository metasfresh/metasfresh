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
package org.compiere.cm;

import java.io.IOException;
import java.io.Reader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.compiere.model.X_CM_BroadcastServer;
import org.compiere.util.CLogger;

/**
 * The CacheHandler handles deployment and clean of internal and external caches
 * 
 * @author Yves Sandfort
 * @version  $Id$ 
 */
public class CacheHandler {
	protected String[] cacheURLs;
	protected CLogger log;
	
	/**
	 * CacheHandler for single URL environment
	 * @param thisURL URL of this Server
	 * @param tLog thisLogger
	 * @param ctx Propertie Context
	 * @param trxName Transaction
	 */
	public CacheHandler(String thisURL, CLogger tLog, Properties ctx, String trxName) {
		int [] theseServers = X_CM_BroadcastServer.getAllIDs ("CM_BroadcastServer", "CM_WebProject_ID=0", trxName);
		if (theseServers!=null && theseServers.length>0) {
			String [] thisURLs = new String [theseServers.length];
			for(int i=0;i<theseServers.length;i++) {
				X_CM_BroadcastServer thisServer = new X_CM_BroadcastServer(ctx, theseServers[i] ,trxName);
				thisURLs[i] = thisServer.getIP_Address ();
			}
			cacheURLs = thisURLs;
		} else {
			// Okay we can't find any cluster config, so we will use the AppsServer from Client
			String [] thisURLs = new String[1];
			thisURLs[0] = thisURL;
			cacheURLs = thisURLs;
		}
		log = tLog;
	}
	
	/**
	 * CacheHandler form multiple URLs
	 * @param thisURLs Array of Cache Server URLs
	 * @param tLog Logger
	 */
	public CacheHandler(String [] thisURLs, CLogger tLog) {
		log = tLog;
		cacheURLs = thisURLs;
	}
	
	/**
	 * Clean Template cache for this ID
	 * @param ID ID of template to clean
	 */
	public void cleanTemplate(Integer ID) {
		cleanTemplate("" + ID);
	}
	
	/**
	 * Clean Template cache for this ID
	 * @param ID ID of template to clean
	 */
	public void cleanTemplate(String ID) {
		runURLRequest("Template", ID);
	}
	
	/**
	 * Empty all Template Caches
	 */
	public void emptyTemplate() {
		runURLRequest("Template", "0");
	}
	
	/**
	 * Clean ContainerCache for this ID
	 * @param ID for Container to clean
	 */
	public void cleanContainer(Integer ID) {
		cleanContainer("" + ID);
	}
	
	/**
	 * Clean ContainerCache for this ID
	 * @param ID for container to clean
	 */
	public void cleanContainer(String ID) {
		runURLRequest("Container", ID);
	}

	/**
	 * Clean ContainerTreeCache for this WebProjectID
	 * @param ID for Container to clean
	 */
	public void cleanContainerTree(Integer ID) {
		cleanContainerTree("" + ID);
	}
	
	/**
	 * Clean ContainerTreeCache for this WebProjectID
	 * @param ID for container to clean
	 */
	public void cleanContainerTree(String ID) {
		runURLRequest("ContainerTree", ID);
	}

	/**
	 * Clean Container Element for this ID
	 * @param ID for container element to clean
	 */
	public void cleanContainerElement(Integer ID) {
		cleanContainerElement("" + ID);
	}
	
	/**
	 * Clean Container Element for this ID
	 * @param ID for container element to clean
	 */
	public void cleanContainerElement(String ID) {
		runURLRequest("ContainerElement", ID);
	}
	
	private void runURLRequest(String cache, String ID) {
		String thisURL = null;
		for(int i=0; i<cacheURLs.length; i++) {
			try {
				thisURL = "http://" + cacheURLs[i] + "/cache/Service?Cache=" + cache + "&ID=" + ID;
				URL url = new URL(thisURL);
				Proxy thisProxy = Proxy.NO_PROXY;
				
			    URLConnection urlConn = url.openConnection(thisProxy);
			    urlConn.setUseCaches(false);
			    urlConn.connect();
			    Reader stream =             new java.io.InputStreamReader(
			                                urlConn.getInputStream());
			    StringBuffer srvOutput = new StringBuffer();

			    	try {
			    		int c;
			    		while ( (c=stream.read()) != -1 )
			    			srvOutput.append( (char)c );
			    		
			    	} catch (Exception E2) {
			    		E2.printStackTrace();
			    	}				
			} catch (IOException E) {
				if (log!=null)
					log.warning("Can't clean cache at:" + thisURL + " be carefull, your deployment server may use invalid or old cache data!");
			}
		}
	}

	/**
	 * Converts JNP URL to http URL for cache cleanup
	 * @param JNPURL String with JNP URL from Context
	 * @return clean servername
	 */
	public static String convertJNPURLToCacheURL(String JNPURL) {
		if (JNPURL.indexOf("jnp://")>=0) {
			JNPURL = JNPURL.substring(JNPURL.indexOf("jnp://")+6);
		}
		if (JNPURL.indexOf(':')>=0) {
			JNPURL = JNPURL.substring(0,JNPURL.indexOf(':'));
		}
		if (JNPURL.length()>0) {
			return JNPURL;
		} else {
			return null;
		}
	}
}
