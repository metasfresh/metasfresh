package de.metas.report.jasper.fonts;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Collection;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
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

	public static String retrieveAvailableFontsUsingGraphicsEnvironment()
	{
		final StringBuilder sb = new StringBuilder();

		Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		for (int i = 0; i < fonts.length; i++)
		{
			sb.append(fonts[i].getFontName());
			sb.append("\n");
		}
		return sb.toString();
	}

	public static String retrieveAvailableFontsUsingFontUtil()
	{
		final JasperReportsContext context = DefaultJasperReportsContext.getInstance();
		final Collection<String> extensionFonts = FontUtil.getInstance(context).getFontNames();
		
		final StringBuilder sb = new StringBuilder();
		
		extensionFonts.forEach(name -> {
			sb.append(name);
			sb.append("\n");
		});
		
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		System.out.println(retrieveAvailableFontsUsingFontUtil());
	}
}
