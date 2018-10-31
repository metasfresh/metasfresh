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
 * Contributor: Carlos Ruiz - globalqss                                       *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.Color;
import java.awt.Cursor;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import org.adempiere.plaf.AdempierePLAF;
import org.compiere.Adempiere;
import org.compiere.util.Env;

import de.metas.util.Check;

/**
 * Online Help Browser & Link.
 *
 * @author Jorg Janke
 * @version $Id: OnlineHelp.java,v 1.2 2006/07/30 00:51:27 jjanke Exp $
 * 
 *          globalqss: fix error about null pointer in OnlineHelp.Worker.run
 *          change the URL for online help for connection
 */
public class OnlineHelp extends JEditorPane implements HyperlinkListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7201158578463954623L;

	/**
	 * Default Constructor
	 */
	public OnlineHelp()
	{
		super();
		setEditable(false);
		setContentType("text/html");
		addHyperlinkListener(this);
	}   // OnlineHelp

	/** Base of Online Help System */
	private static final String BASE_URL = Adempiere.getOnlineHelpURL();

	public static void openInDefaultBrowser()
	{
		if (Check.isEmpty(BASE_URL, true))
		{
			return;
		}
		Env.startBrowser(BASE_URL);
	}

	/**************************************************************************
	 * Hyperlink Listener
	 * 
	 * @param e event
	 */
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e)
	{
		// System.out.println("OnlineHelp.hyperlinkUpdate - " + e.getDescription() + " " + e.getURL());
		if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED)
			return;

		this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		//
		if (e instanceof HTMLFrameHyperlinkEvent)
		{
			HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent)e;
			HTMLDocument doc = (HTMLDocument)getDocument();
			doc.processHTMLFrameHyperlinkEvent(evt);
		}
		else if (e.getURL() == null)
			// remove # of the reference
			scrollToReference(e.getDescription().substring(1));
		else
		{
			try
			{
				setPage(e.getURL());
			}
			catch (Throwable t)
			{
				System.err.println("Help.hyperlinkUpdate - " + t.toString());
				displayError("Error", e.getURL(), t);
			}
		}
		this.setCursor(Cursor.getDefaultCursor());
	}	// hyperlinkUpdate

	/**
	 * Set Text
	 * 
	 * @param text text
	 */
	@Override
	public void setText(String text)
	{
		setBackground(AdempierePLAF.getInfoBackground());
		super.setText(text);
		setCaretPosition(0);        // scroll to top
	}   // setText

	/**
	 * Load URL async
	 * 
	 * @param url url
	 */
	@Override
	public void setPage(final URL url)
	{
		setBackground(Color.white);
		Runnable pgm = new Runnable()
		{
			@Override
			public void run()
			{
				loadPage(url);
			}
		};
		new Thread(pgm).start();
	}   // setPage

	/**
	 * Load Page Async
	 * 
	 * @param url url
	 */
	private void loadPage(URL url)
	{
		try
		{
			super.setPage(url);
		}
		catch (Exception e)
		{
			displayError("Error: URL not found", url, e);
		}
	}   // loadPage

	/**
	 * Display Error message
	 * 
	 * @param header header
	 * @param url url
	 * @param exception exception
	 */
	protected void displayError(String header, Object url, Object exception)
	{
		StringBuffer msg = new StringBuffer("<HTML><BODY>");
		msg.append("<H1>").append(header).append("</H1>")
				.append("<H3>URL=").append(url).append("</H3>")
				.append("<H3>Error=").append(exception).append("</H3>")
				.append("<p>&copy;&nbsp;Adempiere &nbsp; ")
				.append("<A HREF=\"").append(BASE_URL).append("\">Online Help</A></p>")
				.append("</BODY></HTML>");
		setText(msg.toString());
	}   // displayError

	/*************************************************************************/

	/**
	 * Online links.
	 * Key=AD_Window_ID (as String) - Value=URL
	 */
	private static HashMap<String, String> s_links = new HashMap<String, String>();
	static
	{
		new Worker(BASE_URL, s_links).start();
	}

	/**
	 * Is Online Help available.
	 * 
	 * @return true if available
	 */
	public static boolean isAvailable()
	{
		return s_links.size() != 0;
	}   // isAvailable

}   // OnlineHelp

/**
 * Online Help Worker
 */
class Worker extends Thread
{
	/**
	 * Worker Constructor
	 * 
	 * @param urlString url
	 * @param links links
	 */
	Worker(String urlString, HashMap<String, String> links)
	{
		m_urlString = urlString;
		m_links = links;
		setPriority(Thread.MIN_PRIORITY);
	}   // Worker

	private String m_urlString = null;
	private HashMap<String, String> m_links = null;

	/**
	 * Worker: Read available Online Help Pages
	 */
	@Override
	public void run()
	{
		if (m_links == null)
			return;
		URL url = null;
		try
		{
			url = new URL(m_urlString);
		}
		catch (Exception e)
		{
			System.err.println("OnlineHelp.Worker.run (url) - " + e);
		}
		if (url == null)
			return;

		// Read Reference Page
		try
		{
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			HTMLEditorKit kit = new HTMLEditorKit();
			HTMLDocument doc = (HTMLDocument)kit.createDefaultDocument();
			doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
			kit.read(new InputStreamReader(is), doc, 0);

			// Get The Links to the Help Pages
			HTMLDocument.Iterator it = doc.getIterator(HTML.Tag.A);
			Object target = null;
			Object href = null;
			while (it != null && it.isValid())
			{
				AttributeSet as = it.getAttributes();
				// ~ href=/help/100/index.html target=Online title=My Test
				// System.out.println("~ " + as);

				// key keys
				if (target == null || href == null)
				{
					Enumeration<?> en = as.getAttributeNames();
					while (en.hasMoreElements())
					{
						Object o = en.nextElement();
						if (target == null && o.toString().equals("target"))
							target = o;		// javax.swing.text.html.HTML$Attribute
						else if (href == null && o.toString().equals("href"))
							href = o;
					}
				}

				if (target != null && "Online".equals(as.getAttribute(target)))
				{
					// Format: /help/<AD_Window_ID>/index.html
					String hrefString = (String)as.getAttribute(href);
					if (hrefString != null)
					{
						try
						{
							// System.err.println(hrefString);
							String AD_Window_ID = hrefString.substring(hrefString.indexOf('/', 1), hrefString.lastIndexOf('/'));
							m_links.put(AD_Window_ID, hrefString);
						}
						catch (Exception e)
						{
							System.err.println("OnlineHelp.Worker.run (help) - " + e);
						}
					}
				}
				it.next();
			}
			is.close();
		}
		catch (ConnectException e)
		{
			// System.err.println("OnlineHelp.Worker.run URL=" + url + " - " + e);
		}
		catch (UnknownHostException uhe)
		{
			// System.err.println("OnlineHelp.Worker.run " + uhe);
		}
		catch (Exception e)
		{
			System.err.println("OnlineHelp.Worker.run (e) " + e);
			// e.printStackTrace();
		}
		catch (Throwable t)
		{
			System.err.println("OnlineHelp.Worker.run (t) " + t);
			// t.printStackTrace();
		}
		// System.out.println("OnlineHelp - Links=" + m_links.size());
	}   // run

}   // Worker
