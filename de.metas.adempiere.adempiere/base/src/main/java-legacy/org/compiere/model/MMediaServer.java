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
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.compiere.util.DB;

/**
 * 	Media Server Model
 *
 *  @author Jorg Janke
 *  @version $Id: MMediaServer.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MMediaServer extends X_CM_Media_Server
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1065424104545571149L;


	/**
	 * 	Get Media Server
	 *	@param project
	 *	@return server list
	 */
	public static MMediaServer[] getMediaServer (MWebProject project)
	{
		ArrayList<MMediaServer> list = new ArrayList<MMediaServer>();
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM CM_Media_Server WHERE CM_WebProject_ID=? ORDER BY CM_Media_Server_ID";
		try
		{
			pstmt = DB.prepareStatement (sql, project.get_TrxName());
			pstmt.setInt (1, project.getCM_WebProject_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				list.add (new MMediaServer (project.getCtx(), rs, project.get_TrxName()));
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		MMediaServer[] retValue = new MMediaServer[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getMediaServer

	/**	Logger	*/
	private static Logger s_log = LogManager.getLogger(MMediaServer.class);


	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CM_Media_Server_ID id
	 *	@param trxName transaction
	 */
	public MMediaServer (Properties ctx, int CM_Media_Server_ID, String trxName)
	{
		super (ctx, CM_Media_Server_ID, trxName);
	}	//	MMediaServer

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs request
	 *	@param trxName transaction
	 */
	public MMediaServer (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MMediaServer

	/**
	 * 	(Re-)Deploy all media
	 * 	@param media array of media to deploy
	 * 	@return true if deployed
	 */
	public boolean deploy (MMedia[] media)
	{
		// i don't want an outdated commons-net version in our dependencies just so that this unused feature compiles without errors
		throw new UnsupportedOperationException("This method is currently not implemented");
// @formatter:off
//		// Check whether the host is our example localhost, we will not deploy locally, but this is no error
//		if (this.getIP_Address().equals("127.0.0.1") || this.getName().equals("localhost")) {
//			log.warn("You have not defined your own server, we will not really deploy to localhost!");
//			return true;
//		}
//
//		FTPClient ftp = new FTPClient();
//		try
//		{
//			ftp.connect (getIP_Address());
//			if (ftp.login (getUserName(), getPassword()))
//				log.info("Connected to " + getIP_Address() + " as " + getUserName());
//			else
//			{
//				log.warn("Could NOT connect to " + getIP_Address() + " as " + getUserName());
//				return false;
//			}
//		}
//		catch (Exception e)
//		{
//			log.warn("Could NOT connect to " + getIP_Address()
//				+ " as " + getUserName(), e);
//			return false;
//		}
//
//		boolean success = true;
//		String cmd = null;
//		//	List the files in the directory
//		try
//		{
//			cmd = "cwd";
//			ftp.changeWorkingDirectory (getFolder());
//			//
//			cmd = "list";
//			String[] fileNames = ftp.listNames();
//			log.debug("Number of files in " + getFolder() + ": " + fileNames.length);
//
//			/*
//			FTPFile[] files = ftp.listFiles();
//			log.info("Number of files in " + getFolder() + ": " + files.length);
//			for (int i = 0; i < files.length; i++)
//				log.debug(files[i].getTimestamp() + " \t" + files[i].getName());*/
//			//
//			cmd = "bin";
//			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
//			//
//			for (int i = 0; i < media.length; i++)
//			{
//				if (!media[i].isSummary()) {
//					log.info(" Deploying Media Item:" + media[i].get_ID() + media[i].getExtension());
//					MImage thisImage = media[i].getImage();
//
//					// Open the file and output streams
//					byte[] buffer = thisImage.getData();
//					ByteArrayInputStream is = new ByteArrayInputStream(buffer);
//
//					String fileName = media[i].get_ID() + media[i].getExtension();
//					cmd = "put " + fileName;
//					ftp.storeFile(fileName, is);
//					is.close();
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			log.warn(cmd, e);
//			success = false;
//		}
//		//	Logout from the FTP Server and disconnect
//		try
//		{
//			cmd = "logout";
//			ftp.logout();
//			cmd = "disconnect";
//			ftp.disconnect();
//		}
//		catch (Exception e)
//		{
//			log.warn(cmd, e);
//		}
//		ftp = null;
//		return success;
// @formatter:on
	}	//	deploy

}	//	MMediaServer
