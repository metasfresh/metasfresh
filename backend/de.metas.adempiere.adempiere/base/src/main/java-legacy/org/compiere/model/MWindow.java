package org.compiere.model;

import de.metas.logging.LogManager;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Window Model
 *
 * @author Jorg Janke
 * @version $Id: MWindow.java,v 1.2 2006/07/30 00:58:05 jjanke Exp $
 */
public class MWindow extends X_AD_Window
{
	private static final Logger s_log = LogManager.getLogger(MWindow.class);

	public MWindow(final Properties ctx, final int AD_Window_ID, final String trxName)
	{
		super(ctx, AD_Window_ID, trxName);
		if (is_new())
		{
			setWindowType(WINDOWTYPE_Maintain);    // M
			setEntityType(ENTITYTYPE_UserMaintained);    // U
			setIsBetaFunctionality(false);
			setIsDefault(false);
			setIsSOTrx(true);    // Y
		}
	}

	public MWindow(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public void setWindowSize(final Dimension size)
	{
		if (size != null)
		{
			setWinWidth(size.width);
			setWinHeight(size.height);
		}
	}

	private MTab[] m_tabs = null;

	public MTab[] getTabs(final boolean reload, final String trxName)
	{
		if (m_tabs != null && !reload)
		{
			return m_tabs;
		}
		final String sql = "SELECT * FROM AD_Tab WHERE AD_Window_ID=? ORDER BY SeqNo";
		final ArrayList<MTab> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, getAD_Window_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new MTab(getCtx(), rs, trxName));
			}
		}
		catch (final Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		//
		m_tabs = new MTab[list.size()];
		list.toArray(m_tabs);
		return m_tabs;
	}    //	getFields

	@Override
	protected boolean afterSave(final boolean newRecord, final boolean success)
	{
		if (newRecord)    //	Add to all automatic roles
		{
			// Add to all automatic roles ... handled elsewhere
		}
		// Menu/Workflow update
		else if (is_ValueChanged("IsActive") || is_ValueChanged("Name")
				|| is_ValueChanged("Description") || is_ValueChanged("Help"))
		{
			final MMenu[] menues = MMenu.get(getCtx(), "AD_Window_ID=" + getAD_Window_ID(), get_TrxName());
			for (final MMenu menue : menues)
			{
				menue.setName(getName());
				menue.setDescription(getDescription());
				menue.setIsActive(isActive());
				menue.save();
			}
			//
			final X_AD_WF_Node[] nodes = getWFNodes(getCtx(), "AD_Window_ID=" + getAD_Window_ID(), get_TrxName());
			for (final X_AD_WF_Node node : nodes)
			{
				boolean changed = false;
				if (node.isActive() != isActive())
				{
					node.setIsActive(isActive());
					changed = true;
				}
				if (node.isCentrallyMaintained())
				{
					node.setName(getName());
					node.setDescription(getDescription());
					node.setHelp(getHelp());
					changed = true;
				}
				if (changed)
				{
					node.save();
				}
			}
		}
		return success;
	}

	public static X_AD_WF_Node[] getWFNodes(final Properties ctx, final String whereClause, final String trxName)
	{
		String sql = "SELECT * FROM AD_WF_Node";
		if (whereClause != null && whereClause.length() > 0)
		{
			sql += " WHERE " + whereClause;
		}
		final ArrayList<X_AD_WF_Node> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				list.add(new X_AD_WF_Node(ctx, rs, trxName));
			}
		}
		catch (final Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
		final X_AD_WF_Node[] retValue = new X_AD_WF_Node[list.size()];
		list.toArray(retValue);
		return retValue;
	}
}
