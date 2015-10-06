/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.session;

import java.util.logging.Level;

import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.CreateException;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.compiere.Adempiere.RunMode;
import org.compiere.interfaces.Server;
import org.compiere.interfaces.ServerLocal;
import org.compiere.interfaces.ServerRemote;
import org.compiere.util.Env;

/**
 * Adempiere Server Bean.
 *
 * @author Jorg Janke
 * @version $Id: ServerBean.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 * @author Low Heng Sin - Added remote transaction management - Added support to run db process remotely on server
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL - BF [ 1757523 ]
 */
@Stateless(mappedName = Server.JNDI_NAME, name = Server.EJB_NAME)
@Local({ ServerLocal.class })
@Remote({ ServerRemote.class })
@DeclareRoles({ "adempiereUsers" })
@RolesAllowed({ "adempiereUsers" })
public class ServerBean extends ServerBase implements ServerRemote, ServerLocal
{
	/**
	 * Create the Session Bean
	 */
	@PostConstruct
	public void ejbCreate()
	{
		// m_no = ++s_no;
		try
		{
			final boolean started = Env.getSingleAdempiereInstance().startup(RunMode.BACKEND);
			if (!started)
			{
				throw new CreateException("Adempiere could not start");
			}
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "ejbCreate", ex);
			// throw new CreateException ();
		}
		log.info("#" + getStatus());
	}	// ejbCreate

	@Override
	@PermitAll
	public String getStatus()
	{
		return super.getStatus();
	}
}	// ServerBean
