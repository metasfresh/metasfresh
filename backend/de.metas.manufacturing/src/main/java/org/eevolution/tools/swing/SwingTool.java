package org.eevolution.tools.swing;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

import java.awt.Adjustable;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Event;
import java.awt.ItemSelectable;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.text.JTextComponent;

/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
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
 * Copyright (C) 2003-2007 e-Evolution,SC. All Rights Reserved.               *
 * Contributor(s): Victor Perez www.e-evolution.com                           *
 *****************************************************************************/

/**
* @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
* @version 1.0, October 14th 2005
*/
public class SwingTool {
	
	public static synchronized void setCursorsFromChild(Component child, boolean waiting) {
		
		Component com = child;

		while(com.getParent() != null) {
			
			com = (Component)(com.getParent());
		}
		
		setCursorsFromParent((Container)com, waiting);
	}	
	
	protected static boolean isIgnoredComponent(Component c) {
		
		boolean retVal = true;
		
		if(c instanceof ItemSelectable && c.isEnabled()) {
			
			retVal = false;
		}
		else if(c instanceof JTextComponent && c.isEnabled()) {
			
			retVal = false;
		}
		else if(c instanceof JFrame && c.isEnabled()) {
			
			retVal = false;
		}
		else if(c instanceof JPanel && c.isEnabled()) {
			
			retVal = false;
		}
		else if(c instanceof JLabel && c.isEnabled()) {
			
			retVal = false;
		}
		else if(c instanceof Adjustable && c.isEnabled()) {
			
			retVal = false;
		}

		return retVal;
	}
	
	public static void setCursor(Component c, boolean waiting) {
		
		if(isIgnoredComponent(c)) {
			
			return;
		}
		
		Cursor cursor_wait = new Cursor(Cursor.WAIT_CURSOR);
		Cursor cursor_def = getPredefinedCursor(c);

		Component com = c;
		
		if(waiting) {
			
			com.setCursor(cursor_wait);
		}
		else {
			
			com.setCursor(cursor_def);
		}
	}

	public static Cursor getPredefinedCursor(Component c) {
		
		Cursor cursor = Cursor.getDefaultCursor();

		if(c instanceof JTextComponent) {
			
			cursor = Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR);
		}
		else if(c instanceof ItemSelectable || c instanceof Adjustable) {
			
			cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
		}

		return cursor;
	}
	
	public static void setCursors(Component[] components, boolean waiting) {

		for( int i = 0; i < components.length; i++) {
			
			setCursor(components[i], waiting);
		}
	}	

	public synchronized static void setCursorsFromParent(Container parent, boolean waiting) {
		
		Container con = parent;
		
		for(int i = 0; i < con.getComponentCount(); i++) {
			
			setCursor(con.getComponent(i), waiting);

			if(con.getComponent(i) instanceof Container) {
			
				setCursorsFromParent((Container)con.getComponent(i), waiting);
			}
		}
	}

	public static Component searchComponent(Container parent, Class<?> clazz, boolean remove) {

		Container con = parent;
		Component retVal = null;
		Component c = null;
		
		for(int i = 0; i < con.getComponentCount(); i++) {

			c = con.getComponent(i);

			//Found the given class and breaks the loop
			if(clazz.isInstance(c)) {
				
				if(remove) {
					
					con.remove(c);
				}
				
				return c;
			}

			//Recursively calling this method to search in deep
			if(c instanceof Container) {

				c = searchComponent((Container)c , clazz, remove);
				
				if(clazz.isInstance(c)) {
				
					if(remove) {
						
						con.remove(retVal);
					}
					
					return c;
				}
			}
		}
		
		return null;
	}
	
	public static void addOpaque(JComponent c, final boolean opaque) {
		
		ContainerAdapter ca = new ContainerAdapter() {
		
			@Override
			public void componentAdded(ContainerEvent e) {

				setOpaque(e.getChild());
			}
			
			private void setOpaque(Component c) {
				
				//ignores all selectable items, like buttons
				if(c instanceof ItemSelectable) {
					
					return;
				}
				// sets transparent
				else if(c instanceof JComponent) {

					((JComponent)c).setOpaque(opaque);
				}
				// recursively calls this method for all container components
				else if(c instanceof Container) {
					
					for(int i = 0; i > ((Container)c).getComponentCount(); i++) {
						
						setOpaque(((Container)c).getComponent(i));
					}
				}
			}
		};
		c.addContainerListener(ca);
	}
	
	
	
	public static KeyStroke getKeyStrokeFor(String name, List<KeyStroke> usedStrokes) {
	
		return (name == null) ? null : getKeyStrokeFor(name.charAt(0), usedStrokes);
	}
	
	public static KeyStroke getKeyStrokeFor(char c, List<KeyStroke> usedStrokes) {
		
		int m = Event.CTRL_MASK;
		
		KeyStroke o = null;
		for(Iterator<?> i = usedStrokes.iterator(); i.hasNext();) {
			
			o = (KeyStroke)i.next();
			if(c == o.getKeyChar()) {

				if(c == o.getKeyChar()) {
					
					if(o.getModifiers() != Event.SHIFT_MASK+Event.CTRL_MASK) {
						
						m = Event.SHIFT_MASK+Event.CTRL_MASK;
					}
					else if(o.getModifiers() != Event.SHIFT_MASK+Event.ALT_MASK) {
						
						m = Event.SHIFT_MASK+Event.ALT_MASK;
					}
					else {
						
						m = -1;
					}
				}
			}
		}
		
		KeyStroke s = null;
		if(m != -1) {
			
			s = KeyStroke.getKeyStroke(c, m);
			usedStrokes.add(s);
		}
		
		return s;
	}
}
