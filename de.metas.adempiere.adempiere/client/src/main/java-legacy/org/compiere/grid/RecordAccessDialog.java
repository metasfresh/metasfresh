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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ALayout;
import org.compiere.apps.ALayoutConstraint;
import org.compiere.apps.ConfirmPanel;
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
import de.metas.security.IRoleDAO;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.permissions.TableRecordPermission;
import de.metas.security.requests.CreateRecordAccessRequest;
import de.metas.util.Services;

/**
 * Record Access Dialog
 *
 * @author Jorg Janke
 * @version $Id: RecordAccessDialog.java,v 1.3 2006/07/30 00:51:28 jjanke Exp $
 */
@SuppressWarnings("serial")
public class RecordAccessDialog extends CDialog
{
	private final int m_AD_Table_ID;
	private final int m_Record_ID;
	private final List<TableRecordPermission> recordPermissions = new ArrayList<>();
	private TableRecordPermission _currentRecordPermission = null;
	private int _currentRowIndex = 0;

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
			final List<KeyNamePair> roles = Services.get(IRoleDAO.class).retrieveAllRolesWithUserAccess()
					.stream()
					.map(role -> KeyNamePair.of(role.getId(), role.getName()))
					.sorted(Comparator.comparing(KeyNamePair::getName))
					.collect(ImmutableList.toImmutableList());
			roleField.setModel(ListComboBoxModel.ofNullable(roles));
		}

		// Load Record Access for all roles
		{
			final ClientId adClientId = Env.getClientId(ctx);
			final IUserRolePermissionsDAO permissionsRepo = Services.get(IUserRolePermissionsDAO.class);
			final Collection<TableRecordPermission> recordPermissions = permissionsRepo.retrieveRecordAccesses(m_AD_Table_ID, m_Record_ID, adClientId).getPermissionsList();
			setRecordAccesses(recordPermissions);
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
			final TableRecordPermission permission = getRecordAccessByIndex(rowIndex);
			setLine(permission, rowIndex);
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
	 * @param permission record access
	 */
	private void setLine(final TableRecordPermission permission, final int rowIndex)
	{
		RoleId roleId = null;
		boolean exclude = true;
		boolean readonly = false;
		boolean dependent = false;
		if (permission != null)
		{
			roleId = permission.getRoleId();
			exclude = permission.isExclude();
			readonly = permission.isReadOnly();
			dependent = permission.isDependentEntities();
		}

		cbExclude.setSelected(exclude);
		cbReadOnly.setSelected(readonly);
		cbDependent.setSelected(dependent);
		bDelete.setEnabled(permission != null);

		//
		KeyNamePair roleToSelect = null;
		for (int i = 0; i < roleField.getItemCount(); i++)
		{
			final KeyNamePair pp = roleField.getItemAt(i);
			if (roleId != null && pp.getKey() == roleId.getRepoId())
			{
				roleToSelect = pp;
			}
		}

		if (roleToSelect != null && permission != null)
		{
			roleField.setSelectedItem(roleToSelect);
			_currentRecordPermission = permission;
			_currentRowIndex = rowIndex;

		}
		else
		{
			_currentRecordPermission = null;
			_currentRowIndex = rowIndex;
		}
	}	// setLine

	private void setRecordAccesses(final Collection<TableRecordPermission> recordPermissionsToSet)
	{
		recordPermissions.clear();
		recordPermissions.addAll(recordPermissionsToSet);
	}

	private int getRecordAccessesCount()
	{
		return recordPermissions.size();
	}

	private TableRecordPermission getRecordAccessByIndex(final int index)
	{
		return recordPermissions.get(index);
	}

	/**
	 * @param ra
	 * @return row index
	 */
	private int addRecordAccess(final TableRecordPermission ra)
	{
		recordPermissions.add(ra);
		return recordPermissions.size() - 1;
	}

	private void removeRecordAccess(final TableRecordPermission recordAccessToDelete)
	{
		final ArrayKey key = mkKey(recordAccessToDelete);
		recordPermissions.removeIf(ra -> Objects.equals(key, mkKey(ra)));
	}

	private static final ArrayKey mkKey(final TableRecordPermission permission)
	{
		return ArrayKey.of(permission.getRoleId(), permission.getResource());

	}

	private TableRecordPermission getCurrentRecordPermission()
	{
		return _currentRecordPermission;
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

		final RoleId roleId = RoleId.ofRepoId(roleKNP.getKey());

		//
		final boolean isExclude = cbExclude.isSelected();
		final boolean isReadOnly = cbReadOnly.isSelected();
		final boolean isDependentEntities = cbDependent.isSelected();

		final TableRecordPermission currentRecordPermission = getCurrentRecordPermission();
		final boolean isNew = currentRecordPermission == null;

		final IUserRolePermissionsDAO permissionsRepo = Services.get(IUserRolePermissionsDAO.class);
		permissionsRepo.createRecordAccess(CreateRecordAccessRequest.builder()
				.roleId(roleId)
				.recordRef(TableRecordReference.of(m_AD_Table_ID, m_Record_ID))
				.exclude(isExclude)
				.readOnly(isReadOnly)
				.considerDependentEntriesToo(isDependentEntities)
				.build());

		//
		if (isNew)
		{
			final int rowIndex = addRecordAccess(currentRecordPermission);
			setLine(currentRecordPermission, rowIndex);
		}
	}	// cmd_save

	/**
	 * Delete Command
	 */
	private void cmd_delete()
	{
		final TableRecordPermission currentRecordPermission = getCurrentRecordPermission();
		if (currentRecordPermission == null)
		{
			throw new AdempiereException("@NoSelection@");
		}
		else
		{
			InterfaceWrapperHelper.delete(currentRecordPermission);
			removeRecordAccess(currentRecordPermission);
			setLine(null, 0);
		}
	}	// cmd_delete

}	// RecordAccessDialog
