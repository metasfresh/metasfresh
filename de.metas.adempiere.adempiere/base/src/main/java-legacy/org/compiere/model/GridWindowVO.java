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

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.WindowLoadException;
import org.adempiere.service.IRolePermLoggingBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *  Model Window Value Object
 *
 *  @author Jorg Janke
 *  @version  $Id: GridWindowVO.java,v 1.4 2006/07/30 00:58:04 jjanke Exp $
 */
public class GridWindowVO implements Serializable
{
	private static final transient Logger logger = LogManager.getLogger(GridWindowVO.class);

	/**
	 *  Create Window Value Object
	 *  @param ctx context
	 *  @param WindowNo window no for ctx
	 *  @param AD_Window_ID window id
	 *  @return MWindowVO
	 */
	public static GridWindowVO create (Properties ctx, int WindowNo, int AD_Window_ID)
	{
		final int adMenuId = 0; // N/A
		return create (ctx, WindowNo, AD_Window_ID, adMenuId);
	}   //  create

	/**
	 *  Create Window Value Object
	 *
	 *  @param ctx context
	 *  @param WindowNo window no for ctx
	 *  @param AD_Window_ID window id
	 *  @param AD_Menu_ID menu id
	 *  @return {@link GridWindowVO} or <code>null</code> (if not found or no access)
	 */
	public static GridWindowVO createOrNull (Properties ctx, int WindowNo, int AD_Window_ID, int AD_Menu_ID)
	{
		try
		{
			final GridWindowVO gridWindowVO = create(ctx, WindowNo, AD_Window_ID, AD_Menu_ID);
			return gridWindowVO;
		}
		catch (Exception ex)
		{
			logger.error(ex.getLocalizedMessage(), ex);
			return null;
		}
	}

	/**
	 * Load {@link GridWindowVO}.
	 *
	 * @param ctx
	 * @param WindowNo
	 * @param AD_Window_ID
	 * @param AD_Menu_ID
	 * @return loaded {@link GridWindowVO}; never returns <code>null</code>
	 * @throws WindowLoadException if window was not found or if there is no access for current logged in role.
	 */
	public static GridWindowVO create (final Properties ctx, final int WindowNo, final int AD_Window_ID, final int AD_Menu_ID)
	{
		logger.info("WindowNo={} - AD_Window_ID={}; AD_Menu_ID={}", WindowNo, AD_Window_ID, AD_Menu_ID);
		GridWindowVO vo = new GridWindowVO (ctx, WindowNo);
		vo.AD_Window_ID = AD_Window_ID;

		//  Get Window_ID if required	- (used by HTML UI)
		if (vo.AD_Window_ID <= 0 && AD_Menu_ID > 0)
		{
			final String sql = "SELECT AD_Window_ID, IsSOTrx, IsReadOnly FROM AD_Menu WHERE AD_Menu_ID=? AND Action='W'";
			final List<Object> sqlParams = Arrays.<Object>asList(AD_Menu_ID);
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
				DB.setParameters(pstmt, sqlParams);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					vo.AD_Window_ID = rs.getInt(1);
					String IsSOTrx = rs.getString(2);
					Env.setContext(ctx, WindowNo, "IsSOTrx", (IsSOTrx != null && IsSOTrx.equals("Y")));
					//
					String IsReadOnly = rs.getString(3);
					if (IsReadOnly != null && IsReadOnly.equals("Y"))
						vo.IsReadWrite = "Y";
					else
						vo.IsReadWrite = "N";
				}
			}
			catch (SQLException e)
			{
				throw new DBException(e, sql, sqlParams);
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null; pstmt = null;
			}
			logger.info("AD_Window_ID=" + vo.AD_Window_ID);
		}

		//  --  Get Window
		final StringBuilder sql = new StringBuilder("SELECT Name,Description,Help,WindowType, "
			+ "AD_Color_ID,AD_Image_ID,WinHeight,WinWidth, " // metas: removed a.IsReadWrite
			+ "IsSOTrx "
			+ ", IsOneInstanceOnly " // 10 // metas: US831
			);

		if (Env.isBaseLanguage(vo.ctx, "AD_Window"))
			sql.append("FROM AD_Window w WHERE w.AD_Window_ID=? AND w.IsActive='Y'"); // metas: tsa: changed the query because we check for permissions later
		else
			sql.append("FROM AD_Window_vt w WHERE w.AD_Window_ID=?")  // metas: tsa: changed the query because we check for permissions later
				.append(" AND AD_Language='")
				.append(Env.getAD_Language(vo.ctx)).append("'");
		final List<Object> sqlParams = Arrays.<Object>asList(vo.AD_Window_ID);
		String windowName = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//	create statement
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);

			// 	get data
			rs = pstmt.executeQuery();
			if (rs.next())
			{
				windowName = rs.getString(1);
				vo.Name = windowName;
				vo.Description = rs.getString(2);
				if (vo.Description == null)
					vo.Description = "";
				vo.Help = rs.getString(3);
				if (vo.Help == null)
					vo.Help = "";
				vo.WindowType = rs.getString(4);
				//
				vo.AD_Color_ID = rs.getInt(5);
				vo.AD_Image_ID = rs.getInt(6);
				// vo.IsReadWrite = rs.getString(7); // metas: not needed
				//
				vo.WinHeight = rs.getInt(7);
				vo.WinWidth = rs.getInt(8);
				//
				vo.IsSOTrx = "Y".equals(rs.getString(9));
				vo.IsOneInstanceOnly = "Y".equals(rs.getString(10)); // metas: US831
			}
			else
				vo = null;
		}
		catch (SQLException ex)
		{
			throw new DBException(ex, sql.toString(), sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		// Ensure ASP exceptions
		final IUserRolePermissions role = Env.getUserRolePermissions(ctx);
		// metas: begin: check for permissions using MRole API
		Boolean windowAccess = null;
		if (vo != null)
			windowAccess = role.getWindowAccess(vo.AD_Window_ID);
		if (vo != null && windowAccess == null)
			vo = null;		//	Not found
		if (vo != null && windowAccess != null)
			vo.IsReadWrite = (windowAccess.booleanValue() ? "Y" : "N");
		// metas: end: check for permissions using MRole API
		if (vo == null)
		{
			final WindowLoadException ex = new WindowLoadException("@NoAccess@", role.getName(), windowName, AD_Window_ID);
			Services.get(IRolePermLoggingBL.class).logWindowAccess(role.getAD_Role_ID(), AD_Window_ID, null, ex.getLocalizedMessage());
			throw ex;
		}
		//	Read Write
		if (vo.IsReadWrite == null)
		{
			final WindowLoadException ex = new WindowLoadException("@NoAccess@", role.getName(), windowName, AD_Window_ID);
			Services.get(IRolePermLoggingBL.class).logWindowAccess(role.getAD_Role_ID(), AD_Window_ID, null, ex.getLocalizedMessage()); // metas: 1934
			throw ex;
		}
		MUserDefWin.apply(vo); // Apply UserDef settings

		//  Create Tabs
		createTabs (vo);
		if (vo.Tabs == null || vo.Tabs.size() == 0)
		{
			final String loadErrorMessage = vo.getLoadErrorMessage() == null ? "Window tabs load error" : vo.getLoadErrorMessage();
			final WindowLoadException ex = new WindowLoadException(loadErrorMessage, role.getName(), windowName, AD_Window_ID);
			Services.get(IRolePermLoggingBL.class).logWindowAccess(role.getAD_Role_ID(), AD_Window_ID, null, ex.getLocalizedMessage()); // metas: 1934
			throw ex;
		}

		return vo;
	}   //  create

	/**
	 *  Create Window Tabs
	 *  @param mWindowVO Window Value Object
	 *  @return true if tabs were created
	 */
	private static boolean createTabs (GridWindowVO mWindowVO)
	{
		mWindowVO.Tabs = new ArrayList<GridTabVO>();

		final String sql = GridTabVO.getSQL(mWindowVO.ctx);
		int TabNo = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			//	create statement
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			pstmt.setInt(1, mWindowVO.AD_Window_ID);
			rs = pstmt.executeQuery();
			boolean firstTab = true;
			while (rs.next())
			{
				if (mWindowVO.AD_Table_ID == 0)
					mWindowVO.AD_Table_ID = rs.getInt("AD_Table_ID");
				//  Create TabVO
				GridTabVO mTabVO = GridTabVO.create(mWindowVO, TabNo, rs,
					mWindowVO.WindowType.equals(WINDOWTYPE_QUERY),  //  isRO
					mWindowVO.WindowType.equals(WINDOWTYPE_TRX));   //  onlyCurrentRows
				if (mTabVO == null && firstTab)
					break;		//	don't continue if first tab is null
				if (mTabVO != null)
				{
					if (!mTabVO.IsReadOnly && "N".equals(mWindowVO.IsReadWrite))
						mTabVO.IsReadOnly = true;
					mWindowVO.Tabs.add(mTabVO);
					TabNo++;        //  must be same as mWindow.getTab(x)
					firstTab = false;
				}
			}
		}
		catch (SQLException e)
		{
			logger.error("createTabs", e);
			return false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}

		//  No Tabs
		if (TabNo == 0 || mWindowVO.Tabs.size() == 0)
		{
			mWindowVO.addLoadErrorMessage("No Tabs - AD_Window_ID=" + mWindowVO.AD_Window_ID + " - " + sql, true); // metas: 1934
			logger.error("No Tabs - AD_Window_ID=" + mWindowVO.AD_Window_ID + " - " + sql);
			return false;
		}

		//	Put base table of window in ctx (for VDocAction)
		Env.setContext(mWindowVO.ctx, mWindowVO.WindowNo, "BaseTable_ID", mWindowVO.AD_Table_ID);
		return true;
	}   //  createTabs


	/**************************************************************************
	 *  Private Constructor
	 *  @param Ctx context
	 *  @param windowNo window no
	 */
	private GridWindowVO (Properties Ctx, int windowNo)
	{
		ctx = Ctx;
		WindowNo = windowNo;
	}   //  MWindowVO

	static final long serialVersionUID = 3802628212531678981L;

	/** Properties      */
	public Properties   ctx;
	/** Window Number	*/
	public int 		    WindowNo;

	/** Window				*/
	public	int			AD_Window_ID = 0;
	/** Name				*/
	public	String		Name = "";
	/** Desription			*/
	public	String		Description = "";
	/** Help				*/
	public	String		Help = "";
	/** Window Type			*/
	public	String		WindowType = "";
	/** Image				*/
	public int          AD_Image_ID = 0;
	/** Color				*/
	public int          AD_Color_ID = 0;
	/** Read Write			*/
	public String		IsReadWrite = null;
	/** Window Width		*/
	public int			WinWidth = 0;
	/** Window Height		*/
	public int			WinHeight = 0;
	/** Sales Order Trx		*/
	public boolean		IsSOTrx = false;

	/** Tabs contains GridTabVO elements   */
	public List<GridTabVO>	Tabs = null;
	/** Base Table		*/
	public int 			AD_Table_ID = 0;

	/** Qyery				*/
	public static final String	WINDOWTYPE_QUERY = "Q";
	/** Transaction			*/
	public static final String	WINDOWTYPE_TRX = "T";
	/** Maintenance			*/
	public static final String	WINDOWTYPE_MMAINTAIN = "M";

	/**
	 *  Set Context including contained elements
	 *  @param newCtx context
	 */
	public void setCtx (Properties newCtx)
	{
		ctx = newCtx;
		for (int i = 0; i < Tabs.size() ; i++)
		{
			GridTabVO tab = Tabs.get(i);
			tab.setCtx(newCtx);
		}
	}   //  setCtx

	/**
	 * 	Clone
	 * 	@param windowNo no
	 *	@return WindowVO
	 */
	public GridWindowVO clone (int windowNo)
	{
		GridWindowVO clone = null;
		try
		{
			clone = new GridWindowVO(ctx, windowNo);
			clone.AD_Window_ID = AD_Window_ID;
			clone.Name = Name;
			clone.Description = Description;
			clone.Help = Help;
			clone.WindowType = WindowType;
			clone.AD_Image_ID = AD_Image_ID;
			clone.AD_Color_ID = AD_Color_ID;
			clone.IsReadWrite = IsReadWrite;
			clone.WinWidth = WinWidth;
			clone.WinHeight = WinHeight;
			clone.IsSOTrx = IsSOTrx;
			Env.setContext(ctx, windowNo, "IsSOTrx", clone.IsSOTrx);
			clone.AD_Table_ID = AD_Table_ID;
			Env.setContext(ctx, windowNo, "BaseTable_ID", clone.AD_Table_ID);
			//
			clone.Tabs = new ArrayList<GridTabVO>();
			for (int i = 0; i < Tabs.size(); i++)
			{
				GridTabVO tab = Tabs.get(i);
				GridTabVO cloneTab = tab.clone(clone.ctx, windowNo);
				if (cloneTab == null)
					return null;
				clone.Tabs.add(cloneTab);
			}
		}
		catch (Exception e)
		{
			clone = null;
		}
		return clone;
	}	//	clone

// metas: begin
	private boolean IsOneInstanceOnly = false; // metas: US831
	public boolean isOneInstanceOnly() // metas: US831
	{
		return IsOneInstanceOnly;
	}

	private StringBuffer loadErrorMessages = null;
	protected void addLoadErrorMessage(String message, boolean checkEmpty)
	{
		if (Check.isEmpty(message, true))
			return;
		if (loadErrorMessages == null)
			loadErrorMessages = new StringBuffer();
		if (loadErrorMessages.length() > 0)
		{
			// Don't add this message
			if (checkEmpty)
				return;

			loadErrorMessages.append("\n");
		}
		loadErrorMessages.append(message);
	}
	public String getLoadErrorMessage()
	{
		return loadErrorMessages == null || loadErrorMessages.length() == 0 ? null : loadErrorMessages.toString();
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public List<GridTabVO> getTabs()
	{
		return Tabs;
	}
	
	public String getName()
	{
		return Name;
	}
	
	public String getDescription()
	{
		return Description;
	}
	
	public String getHelp()
	{
		return Help;
	}
// metas: end
}   //  MWindowVO

