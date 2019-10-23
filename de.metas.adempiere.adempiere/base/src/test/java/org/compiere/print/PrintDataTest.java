package org.compiere.print;

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
import java.io.IOException;
import java.util.Properties;

import javax.xml.transform.stream.StreamResult;

import org.junit.Test;

public class PrintDataTest
{
	/**
	 * Lagacy test to make sure the XML is correcty generated and parsed.
	 * 
	 * @throws IOException
	 */
	@Test
	public void test_parseXml() throws IOException
	{
		PrintData pd = new PrintData(new Properties(), "test1");
		pd.addNode(new PrintDataElement("test1element1", "testvalue<1>", 0, null));
		pd.addNode(new PrintDataElement("test1element2", "testvalue&2&", 0, null));

		PrintData pdx = new PrintData(new Properties(), "test2");
		pdx.addNode(new PrintDataElement("test2element1-1", "testvalue11", 0, null));
		pdx.addNode(new PrintDataElement("test2element1-2", "testvalue12", 0, null));
		pdx.addRow(false, 0);
		pdx.addNode(new PrintDataElement("test2element2-1", "testvalue21", 0, null));
		pdx.addNode(new PrintDataElement("test2element2-2", "testvalue22", 0, null));

		pd.addNode(pdx);
		pd.addNode(new PrintDataElement("test1element3", "testvalue/3/", 0, null));

		final File file = File.createTempFile("printDataTest", ".xml");
		pd.createXML(file.getAbsolutePath());
		pd.createXML(new StreamResult(System.out));
		System.out.println("");
		pd.dump();

		// parse
		System.out.println("");
		PrintData pd1 = PrintData.parseXML(new Properties(), file);
		pd1.createXML(new StreamResult(System.out));
		System.out.println("");
		pd1.dump();
	}	// main

}
