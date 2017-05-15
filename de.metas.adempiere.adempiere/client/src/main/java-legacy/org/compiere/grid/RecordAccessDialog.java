/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.grid;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
import org.compiere.model.I_AD_Record_Access;
import org.compiere.model.I_AD_Role;
import org.compiere.swing.CButton;
import org.compiere.swing.CCheckBox;
import org.compiere.swing.CComboBox;
import org.compiere.swing.CDialog;
import org.compiere.swing.CLabel;
import org.compiere.swing.CPanel;
import org.compiere.swing.ListComboBoxModel;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.Msg;

/**
 * Record Access Dialog
 *
 * @author Jorg Janke
 * @version $Id: RecordAccessDialog.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
public class RecordAccessDialog extends CDialog
{
	private final int m_AD_Table_ID;
	private final int m_Record_ID;
	private final List<I_AD_Record_Access> _recordAccesses = new ArrayList<>();
	//
	private int _currentRowIndex = 0;
	private I_AD_Record_Access _currentRecordAccess = null;

	//
	// UI
	private final CComboBox<KeyNamePair> roleField = new CComboBox<>();
	private final CCheckBox cbExclude = new CCheckBox(Msg.translate(Env.getCtx(), "IsExclude"));
	private final CCheckBox cbReadOnly = new CCheckBox(Msg.translate(Env.getCtx(), "IsReadOnly"));
	private final CCheckBox cbDependent = new CCheckBox(Msg.translate(Env.getCtx(), "IsDependentEntities"));
	private final CButton bDelete = AEnv.getButton("Delete");
	private final CButton bNew = AEnv.getButton("New");
	private final JLabel rowNoLabel = new JLabel();
	private final CButton bUp = AEnv.getButton("Previous");
	private final CButton bDown = AEnv.getButton("Next");

	public RecordAccessDialog(final JFrame owner, final int AD_Table_ID, final int Record_ID)
	{
		super(owner, Msg.translate(Env.getCtx(), "RecordAccessDialog"));
		m_AD_Table_ID = AD_Table_ID;
		m_Record_ID = Record_ID;

		dynInit();
		jbInit();

		AEnv.showCenterWindow(owner, this);
	}	// RecordAccessDialog

	/**
	 * Dynamic Init
	 */
	private void dynInit()
	{
		final Properties ctx = Env.getCtx();

		//
		// Load Roles
		{
			final List<KeyNamePair> roles = Services.get(IRoleDAO.class).retrieveAllRolesWithUserAccess(ctx)
					.stream()
					.map(adRole -> KeyNamePair.of(adRole.getAD_Role_ID(), adRole.getName()))
					.sorted(Comparator.comparing(KeyNamePair::getName))
					.collect(ImmutableList.toImmutableList());
			roleField.setModel(ListComboBoxModel.ofNullable(roles));
		}

		// Load Record Access for all roles
		{
			final int adClientId = Env.getAD_Client_ID(ctx);
			final List<I_AD_Record_Access> recordAccesses = Services.get(IUserRolePermissionsDAO.class).retrieveRecordAccesses(m_AD_Table_ID, m_Record_ID, adClientId);
			setRecordAccesses(recordAccesses);
		}

		setLine(0, false);
	}	// dynInit

	/**
	 * Static Init
	 */
	private void jbInit()
	{
		final BorderLayout mainLayout = new BorderLayout();
		final CPanel centerPanel = new CPanel(new ALayout());
		final CLabel roleLabel = new CLabel(Msg.translate(Env.getCtx(), "AD_Role_ID"));
		final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();

		getContentPane().setLayout(mainLayout);
		getContentPane().add(centerPanel, BorderLayout.CENTER);
		getContentPane().add(confirmPanel, BorderLayout.SOUTH);
		//
		centerPanel.add(bUp, new ALayoutConstraint(0, 0));
		centerPanel.add(bNew, new ALayoutConstraint(0, 6));
		centerPanel.add(roleLabel, new ALayoutConstraint(1, 0));
		centerPanel.add(roleField, null);
		centerPanel.add(cbExclude, null);
		centerPanel.add(cbReadOnly, null);
		centerPanel.add(cbDependent, null);
		centerPanel.add(bDelete, null);
		centerPanel.add(bDown, new ALayoutConstraint(2, 0));
		centerPanel.add(rowNoLabel, new ALayoutConstraint(2, 6));
		//
		final Dimension size = centerPanel.getPreferredSize();
		size.width = 600;
		centerPanel.setPreferredSize(size);
		//
		bUp.addActionListener(this);
		bDown.addActionListener(this);
		bDelete.addActionListener(this);
		bNew.addActionListener(this);
		confirmPanel.setActionListener(this);
	}	// jbInit

	/**
	 * Set Line
	 *
	 * @param rowDelta delta to current row
	 * @param newRecord new
	 */
	private void setLine(final int rowDelta, boolean newRecord)
	{
		final int recordAccessesCount = getRecordAccessesCount();
		int maxIndex;
		int rowIndex;

		// nothing defined
		if (recordAccessesCount == 0)
		{
			rowIndex = 0;
			maxIndex = 0;
			newRecord = true;
			setLine(null, rowIndex);
		}
		else if (newRecord)
		{
			rowIndex = recordAccessesCount - 1 + 1;
			maxIndex = rowIndex;
			setLine(null, rowIndex);
		}
		else
		{
			rowIndex = getCurrentRowIndex() + rowDelta;
			maxIndex = recordAccessesCount - 1;
			if (rowIndex < 0)
			{
				rowIndex = 0;
			}
			else if (rowIndex > maxIndex)
			{
				rowIndex = maxIndex;
			}
			//
			final I_AD_Record_Access ra = getRecordAccessByIndex(rowIndex);
			setLine(ra, rowIndex);
		}

		//
		// Label
		{
			final StringBuilder txt = new StringBuilder();
			if (newRecord)
			{
				txt.append("+");
			}
			txt.append(rowIndex + 1).append("/").append(maxIndex + 1);
			rowNoLabel.setText(txt.toString());
		}

		//
		// set up/down
		bUp.setEnabled(rowIndex > 0);
		bDown.setEnabled(rowIndex < maxIndex);
	}	// setLine

	/**
	 * Set Selection
	 *
	 * @param ra record access
	 */
	private void setLine(final I_AD_Record_Access ra, final int rowIndex)
	{
		int AD_Role_ID = 0;
		boolean exclude = true;
		boolean readonly = false;
		boolean dependent = false;
		if (ra != null)
		{
			AD_Role_ID = ra.getAD_Role_ID();
			exclude = ra.isExclude();
			readonly = ra.isReadOnly();
			dependent = ra.isDependentEntities();
		}

		cbExclude.setSelected(exclude);
		cbReadOnly.setSelected(readonly);
		cbDependent.setSelected(dependent);
		bDelete.setEnabled(ra != null);

		//
		KeyNamePair roleToSelect = null;
		for (int i = 0; i < roleField.getItemCount(); i++)
		{
			final KeyNamePair pp = roleField.getItemAt(i);
			if (pp.getKey() == AD_Role_ID)
			{
				roleToSelect = pp;
			}
		}

		if (roleToSelect != null && ra != null)
		{
			roleField.setSelectedItem(roleToSelect);
			_currentRecordAccess = ra;
			_currentRowIndex = rowIndex;

		}
		else
		{
			_currentRecordAccess = null;
			_currentRowIndex = rowIndex;
		}
	}	// setLine

	private void setRecordAccesses(final Collection<I_AD_Record_Access> recordAccesses)
	{
		_recordAccesses.clear();
		_recordAccesses.addAll(recordAccesses);
	}

	private int getRecordAccessesCount()
	{
		return _recordAccesses.size();
	}

	private I_AD_Record_Access getRecordAccessByIndex(final int index)
	{
		return _recordAccesses.get(index);
	}

	/**
	 * @param ra
	 * @return row index
	 */
	private int addRecordAccess(final I_AD_Record_Access ra)
	{
		_recordAccesses.add(ra);
		return _recordAccesses.size() - 1;
	}

	private void removeRecordAccess(final I_AD_Record_Access recordAccessToDelete)
	{
		final ArrayKey key = mkKey(recordAccessToDelete);
		_recordAccesses.removeIf(ra -> Objects.equals(key, mkKey(ra)));
	}

	private static final ArrayKey mkKey(final I_AD_Record_Access ra)
	{
		final int adRoleId = ra.getAD_Role_ID();
		final int adTableId = ra.getAD_Table_ID();
		final int recordId = ra.getRecord_ID();
		return ArrayKey.of(adRoleId, adTableId, recordId);

	}

	private I_AD_Record_Access getCurrentRecordAccess()
	{
		return _currentRecordAccess;
	}

	public int getCurrentRowIndex()
	{
		return _currentRowIndex;
	}

	/**
	 * Action Listener
	 *
	 * @param event event
	 */
	@Override
	public void actionPerformed(final ActionEvent event)
	{
		try
		{
			if (event.getSource() == bUp)
			{
				setLine(-1, false);
			}
			else if (event.getSource() == bDown)
			{
				setLine(+1, false);
			}
			else if (event.getSource() == bNew)
			{
				setLine(0, true);
			}
			else
			{
				if (event.getSource() == bDelete)
				{
					cmd_delete();
				}
				else if (event.getActionCommand().equals(ConfirmPanel.A_OK))
				{
					cmd_save();
				}

				dispose();
			}
		}
		catch (final Exception ex)
		{
			ADialog.error(Env.WINDOW_None, this, ex);
		}
	}	// actionPerformed

	/**
	 * Save Command
	 */
	private void cmd_save()
	{
		final KeyNamePair roleKNP = roleField.getSelectedItem();
		roleField.setBackground(roleKNP == null);
		if (roleKNP == null)
		{
			throw new FillMandatoryException("AD_Role_ID");
		}
		final I_AD_Role role = Services.get(IRoleDAO.class).retrieveRole(Env.getCtx(), roleKNP.getKey());

		//
		final boolean isExclude = cbExclude.isSelected();
		final boolean isReadOnly = cbReadOnly.isSelected();
		final boolean isDependentEntities = cbDependent.isSelected();

		final I_AD_Record_Access currentRecordAccess = getCurrentRecordAccess();
		final boolean isNew = currentRecordAccess == null;

		Services.get(IUserRolePermissionsDAO.class).changeRecordAccess(role, m_AD_Table_ID, m_Record_ID, recordAccess -> {
			recordAccess.setIsActive(true);
			recordAccess.setIsExclude(isExclude);
			recordAccess.setIsReadOnly(isReadOnly);
			recordAccess.setIsDependentEntities(isDependentEntities);
		});

		//
		if (isNew)
		{
			final int rowIndex = addRecordAccess(currentRecordAccess);
			setLine(currentRecordAccess, rowIndex);
		}
	}	// cmd_save

	/**
	 * Delete Command
	 */
	private void cmd_delete()
	{
		final I_AD_Record_Access currentRecordAccess = getCurrentRecordAccess();
		if (currentRecordAccess == null)
		{
			throw new AdempiereException("@NoSelection@");
		}
		else
		{
			InterfaceWrapperHelper.delete(currentRecordAccess);
			removeRecordAccess(currentRecordAccess);
			setLine(null, 0);
		}
	}	// cmd_delete

}	// RecordAccessDialog
