/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
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
package org.compiere.print;

import org.apache.ecs.ConcreteElement;
import org.apache.ecs.xhtml.a;

/**
 * 
 * @author hengsin
 *
 */
public interface IHTMLExtension {

	public String getClassPrefix();
	
	public String getStyleURL();
	
	public String getScriptURL();
	
	public void extendRowElement(ConcreteElement row, PrintData printData);
	
	public void extendIDColumn(int row, ConcreteElement columnElement, a href, PrintDataElement dataElement);
}
