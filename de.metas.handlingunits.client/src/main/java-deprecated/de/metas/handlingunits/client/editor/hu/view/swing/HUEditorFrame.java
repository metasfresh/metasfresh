package de.metas.handlingunits.client.editor.hu.view.swing;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.adempiere.util.Check;
import org.compiere.apps.StatusBar;

import de.metas.handlingunits.client.form.HUEditorHelper;
import de.metas.handlingunits.document.IDataSource;

public class HUEditorFrame
{
	private final JFrame frame;
	private final StatusBar statusBar = new StatusBar();

	private final HUEditorPanel editorPanel = new HUEditorPanel();

	public HUEditorFrame()
	{
		this(new JFrame("HU Editor"));
	}

	public HUEditorFrame(final JFrame frame)
	{
		super();
		Check.assumeNotNull(frame, "frame not null");
		this.frame = frame;
		initFrame();
	}

	private void initFrame()
	{
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(editorPanel, BorderLayout.CENTER);
		frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

		// Menu
		if (frame.getJMenuBar() == null)
		{
			frame.setJMenuBar(new JMenuBar());
		}
		HUEditorHelper.createMenu(frame.getJMenuBar(), editorPanel);

		frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
		frame.pack();

		// NOTE: when calling pack() WFramePeer is created and ExtendedState gets lost so we need to set it again here
		frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);

		// frame.setVisible(true); // don't show it automatically
	}

	public void setDataDource(final IDataSource dataSource)
	{
		editorPanel.setDataDource(dataSource);
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public HUEditorPanel getHUEditorPanel()
	{
		return editorPanel;
	}

	public void dispose()
	{
		frame.dispose();
	}

}
