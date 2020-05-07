/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 Adempiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXImageView;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.painter.Painter;

import de.metas.i18n.IMsgBL;

/**
 * Menu component that handles the functionality expected of a standard
 * "Windows" menu for MDI or MDI-like applications.
 * 
 * @author Low Heng Sin
 * @version 2006/11/20
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 				<li>BF [ 1808617 ] Close Windows from "Viewer" (report) is not working
 */
public class WindowMenu extends JMenu {
    /**
	 * 
	 */
	private static final long serialVersionUID = 381665816099431316L;
	private final WindowManager windowManager;
    private final JFrame frame;
    
    public static final String ShowAllWindows_ActionName = "ShowAllWindow";
    public static final KeyStroke ShowAllWindows_KeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_MASK);
    private final boolean displayShowAllAction = false; // disabled because it's pointless (see task 9287)
    private JMenuItem showAllWindows = null;
    
    private final JMenuItem closeAll = new JMenuItem("Close All Windows");
    private final JMenuItem closeOthers = new JMenuItem("Close Other Windows");
    
	private JXTitledPanel firstBox;
	
	private static final String getTitle(final Window window)
	{
		if (window == null)
		{
			return "?";
		}
		else if(window instanceof Frame)
		{
			return ((Frame)window).getTitle();
		}
		else if (window instanceof Dialog)
		{
			return ((Dialog)window).getTitle();
		}
		else
		{
			return window.getName();
		}
	}
    
    private static void setEnvText(JMenu menu, String msg)
    {
    	String text = Services.get(IMsgBL.class).getMsg(Env.getCtx(), msg);
		int pos = text.indexOf('&');
		if (pos != -1 && text.length() > pos)	//	We have a nemonic
		{
			char ch = text.toUpperCase().charAt(pos+1);
			if (ch != ' ')
			{
				text = text.substring(0, pos) + text.substring(pos+1);
				menu.setMnemonic(ch);
			}
		}
		menu.setText(text);
    }
    
    private static void setEnvText(JMenuItem menu, String msg)
    {
    	String text = Services.get(IMsgBL.class).getMsg(Env.getCtx(), msg);
		int pos = text.indexOf('&');
		if (pos != -1 && text.length() > pos)	//	We have a nemonic
		{
			char ch = text.toUpperCase().charAt(pos+1);
			if (ch != ' ')
			{
				text = text.substring(0, pos) + text.substring(pos+1);
				menu.setMnemonic(ch);
			}
		}
		menu.setText(text);
    }
    
    public WindowMenu(WindowManager windowManager, JFrame frame)
    {
    	super();
        this.windowManager = windowManager;
        this.frame = frame;
        setEnvText(this, "Window");
        closeAll.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent ae) {
                WindowMenu.this.windowManager.close();
            }
        });
        setEnvText(closeAll, "CloseAllWindows");
        closeOthers.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent ae) {
                WindowMenu.this.windowManager.closeOthers(WindowMenu.this.frame);
            }
        });
        
        //
        // Show All Windows
        if (displayShowAllAction)
        {
        	showAllWindows = new JMenuItem("Show All");
	        setEnvText(showAllWindows, ShowAllWindows_ActionName);
	        showAllWindows.setAccelerator(ShowAllWindows_KeyStroke);
	        showAllWindows.addActionListener(new ActionListener() {
	            @Override
				public void actionPerformed(ActionEvent ae) {
	            	SwingUtilities.invokeLater(new Runnable() {
	
						@Override
						public void run() {
							expose();
						}            		
	            	});
	            }
	        });
        }
        
        //
        // Close Other Windows
        setEnvText(closeOthers, "CloseOtherWindows");
        addMenuListener(new MenuListener() {
            @Override
			public void menuCanceled (MenuEvent e) {}

            @Override
			public void menuDeselected (MenuEvent e) {
                removeAll();
            }

            @Override
			public void menuSelected (MenuEvent e) {
                buildChildMenus();
            }
        });
    }

    /** @return true if the ShowAll windows action shall be displayed in menu (see {@link #ShowAllWindows_ActionName} ) */
	public final boolean isDisplayShowAllAction()
	{
		return displayShowAllAction;
	}

    private JXPanel createSelectionPanel() {
    	
    	GridLayout l = new GridLayout(3, 3);
    	l.setHgap(5);
		l.setVgap(5);
		
		JXPanel p = new JXPanel();
		p.setLayout(l);
		
		return p;
    }
    
    public void expose() {
		
		final JDialog dialog = new JDialog();				
		dialog.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		dialog.setUndecorated(true);
		dialog.setModal(true);
		
		//add escape to close
		ActionListener actionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent actionEvent)
			{
				dialog.setVisible(false);
			}
		};
		JRootPane jr = dialog.getRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		jr.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

		//new Thread(new Loader(dialog)).start();
		SwingUtilities.invokeLater(new Loader(dialog));
		
		dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		dialog.setVisible(true);		
	}

	private JXTitledPanel createImageBox(JPanel p, JDialog dialog,
			int width, int height, Window window) {
		BufferedImage bi = new BufferedImage (window.getWidth(), window.getHeight(),
				BufferedImage.TYPE_INT_RGB);	//	TYPE_INT_ARGB is tinted red
		window.paintAll(bi.createGraphics());
		
		Image image = bi.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		
		final JXTitledPanel box = new JXTitledPanel();
		@SuppressWarnings("rawtypes")
		final Painter painter = box.getTitlePainter();
		box.setTitlePainter(null);
		box.setFocusable(true);
		box.setTitle(getTitle(window));
		final JXImageView imageView = new JXImageView();
		imageView.setImage(image);
		imageView.setEditable(false);
		box.setContentContainer(imageView);
		box.setPreferredSize(new Dimension(width,height));		
		box.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				box.requestFocus();
			}				
		});
		imageView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				box.requestFocus();					
			}
		});
		
		PreviewMouseAdapter adapter = new PreviewMouseAdapter(dialog, window);
		box.addMouseListener(adapter);
		imageView.addMouseListener(adapter);
		
		imageView.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		box.addFocusListener(new FocusAdapter(){

			@Override
			public void focusGained(FocusEvent e) {
				box.setTitlePainter(painter);
			}

			@Override
			public void focusLost(FocusEvent e) {
				box.setTitlePainter(null);
			}
			
		});
		return box;
	}

	/* Sets up the children menus depending on the current desktop state */
    private void buildChildMenus()
    {
    	this.removeAll();
        final Window[] windows = windowManager.getWindows();

        if(displayShowAllAction)
        {
        	add(showAllWindows);
        }
        
        if ( !(frame instanceof AMenu) )
        {
        	add(closeOthers);
        }
        
        add(closeAll);
        
        if (windows.length > 0)
        { 
        	if (displayShowAllAction)
        	{
        		showAllWindows.setEnabled(true);
        	}
        	
        	closeAll.setEnabled(true);
        	
        	if ( windows.length > 1 )
        		closeOthers.setEnabled(true);
        	else
        		closeOthers.setEnabled(false);
        	
        	addSeparator();
        }
        else
        {
        	if (displayShowAllAction)
        	{
        		showAllWindows.setEnabled(false);
        	}
        	
        	closeAll.setEnabled(false);
        	closeOthers.setEnabled(false);
        }
        	
        if ( !(frame instanceof AMenu) )
        {
        	final JFrame frame = Env.getWindow(Env.WINDOW_MAIN);
        	if (frame != null && frame instanceof AMenu)
        	{
        		final WindowMenuItem menu = new WindowMenuItem(frame);
        		add(menu);
        		addSeparator();
        	}
        }
        
        for (final Window window : windows)
        {
            final WindowMenuItem menu = new WindowMenuItem(window);
            
            // Disable if is current window.
            if (window.equals(frame))
            {
            	menu.setEnabled(false);
            }
            add(menu);
        }
    }

    private static class WindowMenuItem extends JMenuItem
    {
		private static final long serialVersionUID = 5564119982489951394L;

		private final Window window;
		
		private static final ActionListener ACTION_LISTENER = new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent ae) {
            	final Window frame = ((WindowMenuItem)ae.getSource()).getWindow();
                AEnv.showWindow(frame);
            }
        };

        public WindowMenuItem(final Window window)
        {
            super(getTitle(window));
            
			this.window = window;

			// Use window's icon for our menu item icon.
            final List<Image> windowIcons = window.getIconImages();
            final Image windowIcon = windowIcons.isEmpty() ? null : windowIcons.get(0);
            final ImageIcon windowImageIcon = windowIcon == null ? null : new ImageIcon(windowIcon);
			setIcon(windowImageIcon);
			
			addActionListener(ACTION_LISTENER);
        }

        private Window getWindow()
        {
            return window;
        }
    }
    
    class Loader implements Runnable {
    	private JDialog dialog;
    	
		Loader(JDialog d) {
    		dialog = d;
    	}
		
		@Override
		public void run() {
			Window[] w = windowManager.getWindows();		
			Container dialogContent = dialog.getContentPane();
			dialogContent.setLayout(new BorderLayout());
			
			final CardLayout card = new CardLayout();
			final JXPanel cardContainer = new JXPanel(card);		
			
			dialogContent.add(cardContainer, BorderLayout.CENTER);
			
			JXPanel p = createSelectionPanel();		
			cardContainer.add(p, "page1");
			
			Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
			int width = ( s.width - 30 ) / 3;
			int height = ( s.height - 30 ) / 3;
			int count = 0;
			JFrame frame = Env.getWindow(0);
	    	if (frame != null && frame instanceof AMenu) {
	    		JXTitledPanel box = createImageBox(p, dialog, width, height,
						frame);
				p.add(box);
				count ++;
				firstBox = box;
	    	}
	    	int page = 1;
			for(int i = 0; i < w.length; i++) {
				count ++;
				Window window = w[i];
				JXTitledPanel box = createImageBox(p, dialog, width, height,
						window);
				p.add(box);
				if (i == 0 && firstBox == null) firstBox = box;
				if ( count == 9) {
					count = 0;
					page++;
					p = createSelectionPanel();
					cardContainer.add(p, "page" + page);
				}
			}
			for ( int i = count; i < 9; i++ ) {
				p.add(Box.createGlue());
			}
			dialog.addWindowFocusListener(new WindowFocusListener() {

				@Override
				public void windowGainedFocus(WindowEvent e) {
					if (firstBox != null)
						firstBox.requestFocus();
				}

				@Override
				public void windowLostFocus(WindowEvent e) {
				}
				
			});
			card.first(cardContainer);
			if (page > 1) {
				JXPanel ctrl = new JXPanel();
				JXButton previous = new JXButton("<");
				JXButton next = new JXButton(">");
				previous.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						card.previous(cardContainer);
					}
					
				});
				next.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						card.next(cardContainer);
					}				
				});
				ctrl.add(previous);
				ctrl.add(next);
				dialogContent.add(ctrl, BorderLayout.NORTH);
			}
			dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
    	
    }
    
    class PreviewMouseAdapter extends MouseAdapter {
    	private JDialog dialog;
    	private Window window;

		PreviewMouseAdapter(JDialog d, Window w) {
    		dialog = d;
    		window = w;
    	}

		@Override
		public void mouseClicked(MouseEvent e) {
			dialog.dispose();
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					AEnv.showWindow(window);
				}
			});			
		}
    }
}

 