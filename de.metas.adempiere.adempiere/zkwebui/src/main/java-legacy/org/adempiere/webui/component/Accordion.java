/******************************************************************************
 * Copyright (C) 2008 Low Heng Sin                                            *
 * Copyright (C) 2008 Idalica Corporation                                     *		
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.adempiere.webui.component;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.North;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Div;
import org.zkoss.zul.Vbox;

/**
 * A custom accoridon implementation using borderlayout
 * @author hengsin
 *
 */
public class Accordion extends Borderlayout implements EventListener {
	
	private static final long serialVersionUID = 5898232602746332810L;
	
	private Vbox southBox;
	private Vbox northBox;
	
	private List<String> labelList = new ArrayList<String>();
	private List<Div> headerList = new ArrayList<Div>();
	private List<Component> componentList = new ArrayList<Component>();
	private int selectedIndex = -1;
	
	public Accordion() {
		North north = new North();		
		appendChild(north);
		northBox = new Vbox();
		northBox.setWidth("100%");
		north.appendChild(northBox);
		north.setSplittable(false);
		north.setCollapsible(false);
		
		Center center = new Center();
		center.setFlex(true);
		appendChild(new Center());
		
		South south = new South();
		appendChild(south);
		southBox = new Vbox();
		southBox.setWidth("100%");
		south.appendChild(southBox);
		south.setSplittable(false);
		south.setCollapsible(false);
	}
	
	/**
	 * 
	 * @param component
	 * @param label
	 */
	public void add(Component component, String label) {
		ToolBarButton button = new ToolBarButton();
		button.setLabel(label);
		button.addEventListener(Events.ON_CLICK, this);
		button.setWidth("100%");
		
		Div div = new Div();
		div.setClass("z-center-header");
		div.appendChild(button);
		northBox.appendChild(div);
		
		labelList.add(label);
		headerList.add(div);
		componentList.add(component);
	}
	
	/**
	 * 
	 * @param index
	 * @param label
	 */
	public void setLabel(int index, String label) {
		labelList.set(index, label);
		((ToolBarButton)headerList.get(index).getFirstChild()).setLabel(label);
		if (selectedIndex == index) {
			getCenter().setTitle(label);
		}
	}

	public void onEvent(Event event) throws Exception {
		if (Events.ON_CLICK.equals(event.getName()) && event.getTarget() instanceof ToolBarButton) {			
			ToolBarButton button = (ToolBarButton) event.getTarget();
			String label = button.getLabel();
			
			int index = labelList.indexOf(label);
			if (index >= 0) {
				setSelectedIndex(index);
			}
		}		
	}
	
	/**
	 * 
	 * @param index
	 */
	public void setSelectedIndex(int index) {
		selectedIndex = index;
		render(index);
	}

	private void render(int index) {
		northBox.getChildren().clear();
		southBox.getChildren().clear();
		getCenter().getChildren().clear();
		
		for (int i = 0; i < index; i++) {
			northBox.appendChild(headerList.get(i));
		}
		
		getCenter().setTitle(labelList.get(index));
		getCenter().appendChild(componentList.get(index));
		
		for (int i = index + 1; i < labelList.size(); i++) {
			southBox.appendChild(headerList.get(i));
		}
		
		this.invalidate();
	}

	/**
	 * @param index
	 * @param tooltiptext
	 */
	public void setTooltiptext(int index, String tooltiptext) {
		headerList.get(index).setTooltiptext(tooltiptext);
	}
	
	/**
	 * Get the header div component
	 * @param index
	 * @return Div
	 */
	public Div getHeader(int index) {
		return headerList.get(index);
	}

}
