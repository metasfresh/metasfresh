package org.compiere.report;

/*
 * #%L
 * de.metas.report.jasper.server.base
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

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import net.sf.jasperreports.engine.JRAbstractSvgRenderer;
import net.sourceforge.barbecue.Barcode;

public class Barbecue extends JRAbstractSvgRenderer
{

	private static final long serialVersionUID = 5112469398754718739L;
	
	private Barcode m_barcode = null;
	
	public Barbecue (Barcode barcode) 
	{
		m_barcode = barcode;
	}

    /**
     * Convenience method for creating a barcode renderer with the option of
     *
     *
     * @param barcode           The barcode
     * @param showDrawingText   If the alphanumeric text should be visible below the barcode
     */
    public Barbecue (Barcode barcode, boolean showDrawingText) {
        m_barcode = barcode;
        m_barcode.setDrawingText(showDrawingText);
    }

	@Override
	public void render(Graphics2D grx, Rectangle2D rectangle) 
	{
		try
		{
			m_barcode.draw(grx, (int)rectangle.getX(), (int)rectangle.getY());	
		}
		catch (Exception e)
		{
			// TODO implement exception handling
		}
		
	}

}
