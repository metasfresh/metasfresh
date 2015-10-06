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
package org.eevolution.tools;

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

import java.io.InputStream;
import java.util.Properties;

/**
* @author Gunther Hoppe, tranSIT GmbH Ilmenau/Germany
* @version 1.0, October 14th 2005
*/
public class PLoader {

	protected Properties properties;

	public PLoader(String properties) {
		
		init(getClass(), properties);
	}

	public PLoader(Class<?> clazz, String properties) {
		
		init(clazz, properties);
	}
	
	protected void init(Class<?> clazz, String name) {
		
		properties = new Properties();
		InputStream is = clazz.getResourceAsStream(name);
		try {
		
			if(is != null) {
				
				properties.load(is);
			}
		}
		catch(Exception e) {
			
			e.printStackTrace();
		}
		finally {
			
			try {
				
				if(is != null) {
					
					is.close();
				}
			}
			catch(Exception ee) {
				
				ee.printStackTrace();
			}
		}
	}
	
	public Properties getProperties() {
		
		return properties;
	}
}
