package org.compiere.util;

/*
 * #%L
 * de.metas.swat.base
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


import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JTextPane;

public final class HTML2ImageConverter {

	private HTML2ImageConverter() {
	}

	public static Image renderHTML(final String html, final int width,
			final int height) {

		JTextPane jTextPane = new JTextPane();
		
		jTextPane.setSize(50, height);
		jTextPane.setContentType("text/html");
		jTextPane.setText(html);
		jTextPane.setVisible(true);

		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
				
		jTextPane.paint(image.createGraphics());
		
		return image;
	}

	
}
