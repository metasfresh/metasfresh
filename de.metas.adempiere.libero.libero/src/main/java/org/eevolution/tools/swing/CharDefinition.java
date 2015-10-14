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


/**
* @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
* @version 1.0, October 14th 2005
*/
public class CharDefinition {

    public CharDefinition() {
    }


    public static CharDefinition getDefaultDef() {
		
    	CharDefinition def = new CharDefinition();
		def.setAllowed("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 .,&-/+*$%");

        return def;
    }
    public static CharDefinition getNumberDef() {
		
    	CharDefinition def = new CharDefinition();
		def.setAllowed("1234567890");

        return def;
    }
    public static CharDefinition getLetterDef() {
		
    	CharDefinition def = new CharDefinition();
		def.setAllowed("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

        return def;
    }
    public static CharDefinition getCurrencyDef() {
		
    	CharDefinition def = new CharDefinition();
		def.setAllowed("1234567890,");

        return def;
    }

	public boolean contains(char c) {
		
		for(int i = 0; i < g_definition.length; i++) {
		
			if(c == g_definition[i]) return true;
		}
		
		return false;
	}

	public void setAllowed(String str) {
		
		g_definition = str.toCharArray();
	}


	private char[] g_definition;
}