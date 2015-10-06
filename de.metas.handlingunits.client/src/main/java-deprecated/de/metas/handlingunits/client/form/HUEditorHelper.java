package de.metas.handlingunits.client.form;

import java.awt.Event;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.compiere.apps.AEnv;

import de.metas.handlingunits.client.editor.command.view.action.ProcessAction;
import de.metas.handlingunits.client.editor.command.view.action.SaveAction;
import de.metas.handlingunits.client.editor.hu.view.swing.HUEditorPanel;

public final class HUEditorHelper
{
	private HUEditorHelper()
	{
		super();
	}

	public static JMenuBar createMenu(final JMenuBar menuBar, final HUEditorPanel editor)
	{
		final JMenuBar menuBarToUse;
		if (menuBar == null)
		{
			menuBarToUse = new JMenuBar();
		}
		else
		{
			menuBarToUse = menuBar;
		}

		// File
		{
			final JMenu handlingUnitsMenu = AEnv.getMenu("HUEditor");
			menuBarToUse.add(handlingUnitsMenu);

			// Save
			{
				final AbstractAction saveAction = new SaveAction(editor);
				final JMenuItem handlingUnitsSave = new JMenuItem(saveAction);
				handlingUnitsMenu.add(handlingUnitsSave);

				handlingUnitsSave.setMnemonic(KeyEvent.VK_S);
				handlingUnitsSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
				handlingUnitsSave.setToolTipText("Save");
			}

			// Process
			{
				final AbstractAction processAction = new ProcessAction(editor);
				final JMenuItem mi = new JMenuItem(processAction);
				handlingUnitsMenu.add(mi);

				// mi.setMnemonic(KeyEvent.VK_S);
				// mi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
				mi.setToolTipText("Process");
			}

		}

		// Edit
		{
			final JMenu editMenu = AEnv.getMenu("Edit");
			menuBarToUse.add(editMenu);

			// Undo
			{
				final JMenuItem editUndo = new JMenuItem(editor.getModel().getHistorySupport().getUndoAction());
				editMenu.add(editUndo);

				editUndo.setMnemonic(KeyEvent.VK_U);
				editUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Event.CTRL_MASK));
				editUndo.setToolTipText("Undo previous action");
			}

			// Redo
			{
				final JMenuItem editRedo = new JMenuItem(editor.getModel().getHistorySupport().getRedoAction());
				editMenu.add(editRedo);

				editRedo.setMnemonic(KeyEvent.VK_R);
				editRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Event.CTRL_MASK));
				editRedo.setToolTipText("Redo action");
			}
		}

		return menuBarToUse;
	}
}
