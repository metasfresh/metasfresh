package org.adempiere.apps.graph;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.text.Element;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;

public class FCHtmlEditorKit extends HTMLEditorKit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3371176452691681668L;
	
	public ViewFactory getViewFactory() {
		if (defaultFactory == null) {
			defaultFactory = new FCHtmlFactory(super.getViewFactory());
		}
		return defaultFactory;
	}

	private static class FCHtmlFactory implements ViewFactory {
		public FCHtmlFactory(ViewFactory factory) {
			oldFactory = factory;
		}
		public View create(Element elem) {
			View result;
			result = oldFactory.create(elem);
			if (result instanceof ImageView) {
				String src = (String)elem.getAttributes().
				getAttribute(HTML.Attribute.SRC);
				if ("res:".equals(src.substring(0, 4))) {
					result = new NewImageView(elem);
				}
			}
			return result;
		}
		private static class NewImageView extends ImageView {
			Element elem;
			public NewImageView(Element elem) {
				super(elem);
				this.elem=elem;
			}
			public Image getImage() {
				//return smile image
				//java.awt.Toolkit.getDefaultToolkit().getImage(getImageURL()).flush();
				//if (smileImage == null) {
					String src = (String)elem.getAttributes().
					getAttribute(HTML.Attribute.SRC);
					//System.out.println("img load: " + src.substring(4));
					URL url = getClass().getClassLoader().
					getResource(src.substring(4));
					//getResource("at/freecom/apps/images/freecom.gif");
					//getResource("javax/swing/text/html/icons/image-delayed.gif");
					if (url == null) return null;
					smileImage  =  Toolkit.getDefaultToolkit().getImage(url);
					if (smileImage==null) return null;
					//forcing image to load synchronously
					ImageIcon ii = new ImageIcon();
					ii.setImage(smileImage);
				//}
				return smileImage;
			}
			public URL getImageURL() {
				// here we return url to some image. It might be any
				// existing image.  we need to move ImageView to the
				// state where it thinks that image was loaded.
				// ImageView is calling getImage to get image for
				// measurement and painting when image was loaded
				if (false) {
					return getClass().getClassLoader().
					getResource("javax/swing/text/html/icons/image-delayed.gif");
				} else {
					String src = (String)elem.getAttributes().
					getAttribute(HTML.Attribute.SRC);
					//System.out.println("img load: " + src.substring(4));
					URL url = getClass().getClassLoader().
					getResource(src.substring(4));
					if (url != null) {
						// System.out.println("load image: " + url);
						return url;
					}
				}
				return null;
			}
			private static Image smileImage = null;
		}

		private ViewFactory oldFactory;
	}
	private static ViewFactory defaultFactory = null;
}
