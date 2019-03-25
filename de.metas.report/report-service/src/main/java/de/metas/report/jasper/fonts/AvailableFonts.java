package de.metas.report.jasper.fonts;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.fonts.FontUtil;

/*
 * #%L
 * ListFonts
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AvailableFonts extends JRDefaultScriptlet
{

	public String retrieveAvailableFonts() throws JRScriptletException
	{
		final GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();

		final StringBuilder sb = new StringBuilder();

		for (String font : e.getAvailableFontFamilyNames())
		{
			sb.append(font);
			sb.append("\n");
		}

		return sb.toString();
	}

	public static String getFontNames()
	{
		final List<String[]> classes = new ArrayList<String[]>();
		List<String> elements = new ArrayList<String>();

		final JasperReportsContext context = DefaultJasperReportsContext.getInstance();
		final Collection<?> extensionFonts = FontUtil.getInstance(context).getFontFamilyNames();
		for (final Iterator<?> it = extensionFonts.iterator(); it.hasNext();)
		{
			String fname = (String)it.next();
			elements.add(fname);
		}
		classes.add(elements.toArray(new String[elements.size()]));
		
		final StringBuilder sb = new StringBuilder();
		
		String[] names = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		for (int i = 0; i < names.length; i++)
		{
			sb.append(names[i]);
			sb.append("\n");
		}
		
		System.out.println(names);
		
		return sb.toString();
	}
	
	public static void main(String[] args)
	{

		getFontNames();
		
	}

}
