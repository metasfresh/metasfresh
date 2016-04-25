package org.adempiere.ui.sideactions.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.adempiere.ui.sideactions.model.ISideAction;
import org.adempiere.ui.sideactions.model.ISideActionsGroupsListModel;
import org.adempiere.ui.sideactions.model.SideActionsGroupModel;
import org.adempiere.ui.sideactions.model.SideActionsGroupsListModel;
import org.adempiere.ui.sideactions.model.ToggableSideAction;
import org.junit.Ignore;

/**
 * Use this class as a playground for {@link ISideAction} related Swing components.
 * 
 * I am using it to manually check different aspects of side action components, without database and without adempiere.
 * 
 * @author tsa
 *
 */
@Ignore
public class SideActionsGroupsListPanel_Manual_Test
{
	static int nextActionId = 1;

	public static void main(String[] args)
	{
		final ISideActionsGroupsListModel groupsListModel = new SideActionsGroupsListModel();

		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		//
		// Main panel
		{
			final JPanel panel = new JPanel();
			panel.setBackground(Color.YELLOW);
			panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
			// panel.setMinimumSize(new Dimension(800, 600)); // not use by BorderLayout
			panel.setPreferredSize(new Dimension(800, 600));

			final JButton btnInit = new JButton("Init");
			panel.add(btnInit);
			btnInit.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					initModelWithDummyActions(groupsListModel);
				}
			});

			final JTextField groupIndexField = new JTextField();
			groupIndexField.setText("0");
			panel.add(groupIndexField);

			final JButton btnAdd = new JButton("Add");
			panel.add(btnAdd);
			btnAdd.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					final int groupIndex = Integer.parseInt(groupIndexField.getText());
					final TestToggableSideAction action = new TestToggableSideAction();
					System.out.println("Adding " + action + " to group " + groupIndex);
					groupsListModel.getGroups()
							.getElementAt(groupIndex)
							.addAction(action);
				}
			});

			final JButton btnRemove = new JButton("Remove");
			panel.add(btnRemove);
			btnRemove.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					final int groupIndex = Integer.parseInt(groupIndexField.getText());
					groupsListModel.getGroups()
							.getElementAt(groupIndex)
							.removeAction(0);
				}
			});

			final JButton btnRemoveAll = new JButton("Clear");
			panel.add(btnRemoveAll);
			btnRemoveAll.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent e)
				{
					final int groupIndex = Integer.parseInt(groupIndexField.getText());
					groupsListModel.getGroups()
							.getElementAt(groupIndex)
							.removeAllActions();
				}
			});

			frame.add(panel, BorderLayout.CENTER);
		}

		//
		// Side panel
		{
			final SideActionsGroupsListPanel panel = new SideActionsGroupsListPanel();
			// panel.setMinimumSize(new Dimension(200, 100));// not used by border layout
			panel.setPreferredSize(new Dimension(200, 100));
			panel.setModel(groupsListModel);
			frame.add(panel, BorderLayout.EAST);
		}

		//
		// Show frame
		frame.setMaximumSize(new Dimension(1024, 1000));
		frame.pack();
		frame.setVisible(true);
	}

	private static void initModelWithDummyActions(final ISideActionsGroupsListModel groupsListModel)
	{
		for (int groupIndex = 0; groupIndex < 3; groupIndex++)
		{
			final String id = "group" + groupIndex;
			final String title = "Group " + groupIndex;
			final boolean defaultCollapsed = false;
			final SideActionsGroupModel group = new SideActionsGroupModel(id, title, defaultCollapsed);
			groupsListModel.addGroup(group);

			for (int actionIndex = 0; actionIndex < 10; actionIndex++)
			{
				final ISideAction action = new TestToggableSideAction();
				group.addAction(action);
			}
		}

	}

	private static class TestToggableSideAction extends ToggableSideAction
	{
		private String displayName;

		public TestToggableSideAction()
		{
			super();
			this.displayName = "Action " + nextActionId;
			nextActionId++;

			setToggled(true);
		}

		@Override
		public String getDisplayName()
		{
			return displayName;
		}

		@Override
		public void execute()
		{
			System.out.println("Executing " + this);
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
