package org.compiere.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Nullable;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IWindowNoAware;
import org.compiere.swing.CFrame;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/* package */ class WindowsIndex
{
	private static final Logger logger = LogManager.getLogger(WindowsIndex.class);

	private final AtomicInteger nextWindowNo = new AtomicInteger(Env.WINDOW_MAIN + 1);

	private final ConcurrentHashMap<Integer, Container> windowsByWindowNo = new ConcurrentHashMap<>();
	private final ArrayList<CFrame> hiddenWindows = new ArrayList<>();
	private boolean closingHiddenWindows = false;

	public int addWindow(@NonNull final Container window)
	{
		final int windowNo = nextWindowNo.getAndIncrement();
		addWindow(windowNo, window);
		return windowNo;
	}

	public int addWindow(final int windowNo, @NonNull final Container window)
	{
		Check.assumeGreaterOrEqualToZero(windowNo, "windowNo");

		final Container existingWindow = windowsByWindowNo.get(windowNo);
		if (existingWindow != null)
		{
			throw new AdempiereException("Adding window " + window + " using windowNo=" + windowNo + " is not allowed because that windowNo is already allocated for " + existingWindow);
		}

		windowsByWindowNo.put(windowNo, window);
		return windowNo;
	}

	public int getWindowNo(@Nullable final Component component)
	{
		if (component == null)
		{
			return Env.WINDOW_MAIN;
		}

		//
		// Get component's frame or directly the WindowNo
		JFrame winFrame = null;
		{
			Component element = component;
			while (element != null)
			{
				if (element instanceof IWindowNoAware)
				{
					return ((IWindowNoAware)element).getWindowNo();
				}
				else if (element instanceof JFrame)
				{
					winFrame = (JFrame)element;
					break;
				}
				else
				{
					element = element.getParent();
				}
			}
		}
		if (winFrame == null)
		{
			return Env.WINDOW_MAIN;
		}

		// loop through windows
		for (final Map.Entry<Integer, Container> entry : windowsByWindowNo.entrySet())
		{
			final Container window = entry.getValue();
			final JFrame windowFrame = SwingUtils.getFrame(window);
			if (winFrame.equals(windowFrame))
			{
				final int windowNo = entry.getKey();
				return windowNo;
			}
		}

		return Env.WINDOW_MAIN;
	}

	public void closeAll(final boolean preserveMainWindow)
	{
		closeHiddenWindows();

		final Container mainWindow = windowsByWindowNo.get(Env.WINDOW_MAIN);
		windowsByWindowNo.clear();

		if (preserveMainWindow && mainWindow != null)
		{
			windowsByWindowNo.put(Env.WINDOW_MAIN, mainWindow);
		}
	}

	private void closeHiddenWindows()
	{
		closingHiddenWindows = true;
		try
		{
			for (int i = 0; i < hiddenWindows.size(); i++)
			{
				final CFrame hidden = hiddenWindows.get(i);
				hidden.dispose();
			}
			hiddenWindows.clear();
		}
		finally
		{
			closingHiddenWindows = false;
		}
	}

	public JFrame getFrameByWindowNo(final int windowNo)
	{
		if (windowNo < 0)
		{
			return null;
		}
		else if (windowNo == 0)
		{
			logger.warn("Env.getWindow() called with wrong parameter; If you want obtain the main window (AMenu), then please use the constant Env.WINDOW_MAIN instead of 0 (and btw, it's 1 now)");

			Check.assume(Env.WINDOW_MAIN != 0, "WINDOW_MAIN was changed from 0 to 1"); // avoid StackoverFlow if it's ever changed back to 0
			return getFrameByWindowNo(Env.WINDOW_MAIN);
		}
		else
		{
			final Container window = windowsByWindowNo.get(windowNo);
			if (window == null)
			{
				return null;
			}

			return SwingUtils.getFrame(window);
		}
	}

	public void removeWindow(final int windowNo)
	{
		windowsByWindowNo.remove(windowNo);
	}

	/**
	 * Hide Window
	 *
	 * @return true if window is hidden, otherwise close it
	 */
	public boolean hideWindow(final CFrame window)
	{
		if (!Ini.isCacheWindow())
		{
			return false;
		}

		if (closingHiddenWindows)
		{
			return false;
		}

		for (final CFrame hidden : hiddenWindows)
		{
			logger.debug("Checking hidden window: {}", hidden);
			if(AdWindowId.equals(hidden.getAdWindowId(), window.getAdWindowId()))
			{
				return false;	// already there
			}
		}

		if (window.getAdWindowId() != null)         	// workbench
		{
			if (hiddenWindows.add(window))
			{
				window.setVisible(false);
				logger.debug("Added to hidden windows list: {}", window);
				// window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_ICONIFIED));
				if (hiddenWindows.size() > 10)
				{
					final CFrame toClose = hiddenWindows.remove(0);		// sort of lru
					try
					{
						closingHiddenWindows = true;
						toClose.dispose();
					}
					finally
					{
						closingHiddenWindows = false;
					}
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Show Window
	 *
	 * @param AD_Window_ID window
	 * @return {@link CFrame} or <code>null</code> if not found
	 */
	public CFrame showWindowByWindowId(final AdWindowId adWindowId)
	{
		for (int i = 0; i < hiddenWindows.size(); i++)
		{
			final CFrame hiddenFrame = hiddenWindows.get(i);
			if (AdWindowId.equals(hiddenFrame.getAdWindowId(), adWindowId))
			{
				hiddenWindows.remove(i); // NOTE: we can safely remove here because we are also returning (no future iterations)
				logger.debug("Showing window: {}", hiddenFrame);
				hiddenFrame.setVisible(true);
				// De-iconify window - teo_sarca [ 1707221 ]
				final int state = hiddenFrame.getExtendedState();
				if ((state & Frame.ICONIFIED) > 0)
				{
					hiddenFrame.setExtendedState(state & ~Frame.ICONIFIED);
				}
				//
				hiddenFrame.toFront();
				return hiddenFrame;
			}
		}
		return null;
	}

	/**
	 * Update all windows after look and feel changes.
	 */
	public Set<Window> updateUI()
	{
		final Set<Window> updated = new HashSet<>();
		for (final Container c : windowsByWindowNo.values())
		{
			final Window w = SwingUtils.getFrame(c);
			if (w == null)
			{
				continue;
			}
			if (updated.contains(w))
			{
				continue;
			}
			SwingUtilities.updateComponentTreeUI(w);
			w.validate();
			final RepaintManager mgr = RepaintManager.currentManager(w);
			final Component childs[] = w.getComponents();
			for (final Component child : childs)
			{
				if (child instanceof JComponent)
				{
					mgr.markCompletelyDirty((JComponent)child);
				}
			}
			w.repaint();
			updated.add(w);
		}
		for (final Window w : hiddenWindows)
		{
			if (updated.contains(w))
			{
				continue;
			}
			SwingUtilities.updateComponentTreeUI(w);
			w.validate();
			final RepaintManager mgr = RepaintManager.currentManager(w);
			final Component childs[] = w.getComponents();
			for (final Component child : childs)
			{
				if (child instanceof JComponent)
				{
					mgr.markCompletelyDirty((JComponent)child);
				}
			}
			w.repaint();
			updated.add(w);
		}
		return updated;
	}

}
