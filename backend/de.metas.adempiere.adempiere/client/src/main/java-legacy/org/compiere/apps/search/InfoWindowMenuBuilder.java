package org.compiere.apps.search;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import de.metas.adempiere.form.IClientUI;
import de.metas.i18n.IMsgBL;
import de.metas.security.IUserRolePermissions;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.images.Images;
import org.compiere.apps.AEnv;
import org.compiere.apps.AMenu;
import org.compiere.apps.AMenuStartItem;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.MMenu;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Properties;

/**
 * Helper class used to create, add and handle the Info windows menu items.
 *
 * The standard way of using it is:
 * <ul>
 * <li>create the builder instance: {@link #newBuilder()}
 * <li>configure it, i.e. {@link #setMenu(JMenu)}, {@link #setCtx(Properties)} etc
 * <li>call {@link #build()} to create all info window menu items. They will be automatically handled.
 * </ul>
 *
 * @author tsa
 *
 */
public final class InfoWindowMenuBuilder
{
	/** @return new builder instance */
	public static final InfoWindowMenuBuilder newBuilder()
	{
		return new InfoWindowMenuBuilder();
	}

	// services
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	// parameters
	private JMenu _menu;
	private Properties _ctx;
	private Supplier<Integer> _parentWindowNoSupplier = Suppliers.ofInstance(Env.WINDOW_None);

	private boolean _built = false;

	/** Opens a standard/hardcoded info window */
	private final ActionListener standardInfoWindowLauncher = new ActionListener()
	{

		@Override
		public void actionPerformed(final ActionEvent e)
		{
			try
			{
				openStandardInfoWindow(e);
			}
			catch (final Exception ex)
			{
				clientUI.error(getParentWindowNo(), ex);
			}
		}
	};

	private InfoWindowMenuBuilder()
	{
		super();
	}

	/** Creates, configures and handles all info window menu items */
	public void build()
	{
		markAsBuilt();

		final Properties ctx = getCtx();
		final IUserRolePermissions role = getUserRolePermissions();
		final JMenu menu = getMenu();
		// NOTE: avoid using getWindowNo() here because in some cases it might not be available

		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Product))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoProduct, null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.ALT_MASK), menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_BPartner))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoBPartner, null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.SHIFT_MASK + Event.ALT_MASK), menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_ShowAcct) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Account))
		{
			// no longer supported
			//AEnv.addMenuItem(InfoBuilder.ACTION_InfoAccount, null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.ALT_MASK + Event.CTRL_MASK), menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Schedule))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoSchedule, null, null, menu, standardInfoWindowLauncher);
		}
		// FR [ 1966328 ]
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_MRP))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoMRP, "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_CRP))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoCRP, "Info", null, menu, standardInfoWindowLauncher);
		}

		menu.addSeparator();

		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Order))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoOrder, "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Invoice))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoInvoice, "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_InOut))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoInOut, "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Payment))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoPayment, "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_CashJournal))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoCashLine, "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Resource))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoAssignment, "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Asset))
		{
			AEnv.addMenuItem(InfoBuilder.ACTION_InfoAsset, "Info", null, menu, standardInfoWindowLauncher);
		}

		//
		// Add all custom windows to menu
		for (final I_AD_InfoWindow infoWindow : Services.get(IADInfoWindowDAO.class).retrieveInfoWindowsInMenu(ctx))
		{
			final CMenuItem mi = new CMenuItem(infoWindow.getName(), Images.getImageIcon2(Info.DEFAULT_IconName));
			mi.setToolTipText(infoWindow.getDescription());
			menu.add(mi);
			mi.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(final ActionEvent e)
				{
					try
					{
						openGenericInfoWindow(infoWindow);
					}
					catch (final Exception ex)
					{
						clientUI.error(getParentWindowNo(), ex);
					}
				}
			});
		}
	}

	private final void assertNotBuilt()
	{
		Check.assume(!_built, "Info window menu items not already built");
	}

	private final void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	/** Open standard/hardcoded info window */
	private final void openStandardInfoWindow(final ActionEvent e)
	{
		final String actionCommand = e.getActionCommand();
		if (Check.isEmpty(actionCommand, true))
		{
			return;
		}
		
		final IUserRolePermissions role = getUserRolePermissions();
		final JFrame parentFrame = getParentFrame();
		final int windowNo = getParentWindowNo();
		
		if (actionCommand.equals(InfoBuilder.ACTION_InfoProduct) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Product))
		{
			InfoBuilder.showProduct(parentFrame, windowNo);
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoBPartner) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_BPartner))
		{
			InfoBuilder.showBPartner(parentFrame, windowNo);
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoAsset) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Asset))
		{
			InfoBuilder.showAsset(parentFrame, windowNo);
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoSchedule) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Schedule))
		{
			new org.compiere.apps.search.InfoSchedule(parentFrame, null, false);
		}
		// FR [ 1966328 ]
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoMRP) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_MRP))
		{
			final int m_menu_id = MMenu.getMenu_ID("MRP Info");
			final AMenu menu = AEnv.getAMenu();
			AMenuStartItem.startMenuItemById(m_menu_id, Services.get(IMsgBL.class).translate(Env.getCtx(), "MRP Info"), menu);		// async load
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoCRP) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_CRP))
		{
			final int m_menu_id = MMenu.getMenu_ID("CRP Info");
			final AMenu menu = AEnv.getAMenu();
			AMenuStartItem.startMenuItemById(m_menu_id, Services.get(IMsgBL.class).translate(Env.getCtx(), "CRP Info"), menu);		// async load
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoOrder) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Order))
		{
			InfoBuilder.showOrder(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoInvoice) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Invoice))
		{
			InfoBuilder.showInvoice(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoInOut) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_InOut))
		{
			InfoBuilder.showInOut(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoPayment) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Payment))
		{
			InfoBuilder.showPayment(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoCashLine) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_CashJournal))
		{
			InfoBuilder.showCashLine(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals(InfoBuilder.ACTION_InfoAssignment) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Resource))
		{
			InfoBuilder.showAssignment(parentFrame, windowNo, "");
		}
	}

	/** Open generic info window */
	private final void openGenericInfoWindow(final I_AD_InfoWindow infoWindow)
	{
		InfoBuilder.newBuilder()
				.setParentFrame(getParentFrame())
				.setWindowNo(getParentWindowNo())
				.setInfoWindow(infoWindow)
				.buildAndShow();
	}

	public InfoWindowMenuBuilder setCtx(final Properties ctx)
	{
		assertNotBuilt();

		Check.assumeNotNull(ctx, "ctx not null");
		_ctx = ctx;
		return this;
	}

	private final Properties getCtx()
	{
		return _ctx == null ? Env.getCtx() : _ctx;
	}

	private final IUserRolePermissions getUserRolePermissions()
	{
		final Properties ctx = getCtx();
		final IUserRolePermissions role = Env.getUserRolePermissions(ctx);
		return role;
	}

	public InfoWindowMenuBuilder setParentWindowNo(final int windowNo)
	{
		return setParentWindowNo(Suppliers.ofInstance(windowNo));
	}

	public InfoWindowMenuBuilder setParentWindowNo(final Supplier<Integer> windowNoSupplier)
	{
		Check.assumeNotNull(windowNoSupplier, "windowNoSupplier not null");
		_parentWindowNoSupplier = windowNoSupplier;
		return this;
	}

	private final int getParentWindowNo()
	{
		final Integer parentWindowNo = _parentWindowNoSupplier.get();
		return parentWindowNo == null ? Env.WINDOW_MAIN : parentWindowNo;
	}

	private final JFrame getParentFrame()
	{
		final int parentWindowNo = getParentWindowNo();

		// No windowNo => use main window
		if (parentWindowNo < 0 || parentWindowNo == Env.WINDOW_None)
		{
			return Env.getWindow(Env.WINDOW_MAIN);
		}

		// Use particular window
		final JFrame frame = Env.getWindow(parentWindowNo);
		if (frame != null)
		{
			return frame;
		}

		// Fallback to main window (shall not happen)
		return Env.getWindow(Env.WINDOW_MAIN);
	}

	/** Sets the menu where we shall add the info windows menu items */
	public InfoWindowMenuBuilder setMenu(final JMenu menu)
	{
		assertNotBuilt();

		Check.assumeNotNull(menu, "menu not null");
		_menu = menu;
		return this;
	}

	private final JMenu getMenu()
	{
		Check.assumeNotNull(_menu, "menu not null");
		return _menu;
	}
}
