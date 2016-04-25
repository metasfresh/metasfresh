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
package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.io.File;

import org.junit.Ignore;

import junit.framework.TestCase;

/**
 *	The class <code>EMailTest</code> contains tests for the class 
 *	EMail
 * 	<p>
 *
 *  @author Jorg Janke
 *  @version  $Id: EMailTest.java,v 1.2 2006/07/30 00:54:36 jjanke Exp $
 */
@Ignore("ignored because it needs database connection")
public class EMailTest extends TestCase
{
	/**
	 * Construct new test instance
	 *
	 * @param name the test name
	 */
	public EMailTest(String name)
	{
		super(name);
	}

	String host = 	"admin.adempiere.org";
	String usr = 	"jjanke";
	String pwd = 	"";
	//
	String from = 	"jjanke@adempiere.org";
	//
	String to = 	"jjanke@yahoo.com";
	String to2 = 	"jjanke@acm.org";
	String to3 = 	"jorg.janke@adempiere.org";

	/**
	 * Perform pre-test initialization
	 * @throws Exception
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
		org.compiere.Adempiere.startup (true);
	}	//	setup

	/**
	 *	Test sending to internal
	 *
	public void testInternal()
	{
		EMail emailTest = new EMail(host, from, from, "TestInternal", "Test Internal Message");
		assertEquals(emailTest.send(), EMail.SENT_OK);
	}	//	testInternal

	/**
	 *	Test sending to internal authenticated
	 *
	public void testInternalAuthenticate()
	{
		EMail emailTest = new EMail(host, from, from, "TestInternalAuthenticate", "Test Internal Authenticate Message");
		emailTest.setEMailUser(usr, pwd);
		assertEquals(emailTest.send(), EMail.SENT_OK);
	}	//	testInternalAuthenticate

	/**
	 *	Test sending to external
	 *
	public void testExternal()
	{
		EMail emailTest = new EMail(host, from, to, "TestExternal", "Test External Message");
		assertNotSame(emailTest.send(), EMail.SENT_OK);
	}	//	testExternal

	/**
	 *	Test sending to external authenticated
	 *
	public void testExternalAuthenticate()
	{
		EMail emailTest = new EMail(host, from, to, "TestExternalAuthenticate", "Test External Authenticate Message");
		emailTest.setEMailUser(usr, pwd);
		assertEquals(emailTest.send(), EMail.SENT_OK);
	}	//	testExternalAuthenticate

	/**
	 *	Test sending HTML
	 *
	public void testHTML()
	{
		EMail emailTest = new EMail(host, from, to);
		emailTest.addCc(to2);
		emailTest.setMessageHTML("TestHTML", "Test HTML Message");
		emailTest.setEMailUser(usr, pwd);
		assertEquals(emailTest.send(), EMail.SENT_OK);
	}	//	testHTML

	/**
	 *	Test sending Attachment
	 *
	public void testAttachment()
	{
		EMail emailTest = new EMail(host, from, to, "TestAttachment", "Test Attachment Message");
		emailTest.addTo(to2);
		emailTest.addCc(to3);
		emailTest.addAttachment(new File("C:\\Adempiere\\RUN_Adempiere.sh"));
		emailTest.setEMailUser(usr, pwd);
		assertEquals(emailTest.send(), EMail.SENT_OK);
	}	//	testAttachmentHTML

	/**
	 *	Test sending Attachment HTML
	 */
	public void testAttachmentHTML()
	{
		EMail emailTest = new EMail(System.getProperties(), host, from, to, null, null);
		emailTest.addTo(to2);
		emailTest.addCc(to3);
		emailTest.setMessageHTML("TestAttachmentHTML", "Test Attachment HTML Message");
		emailTest.addAttachment(new File("C:\\Adempiere\\RUN_Adempiere.sh"));
		emailTest.createAuthenticator(usr, pwd);
		assertEquals(emailTest.send(), EMail.SENT_OK);
	}	//	testAttachmentHTML

	/**
	 * Launch the test.
	 * @param args String[]
	 */
	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(EMailTest.class);
	}	//	main

}	//	EMailTest
