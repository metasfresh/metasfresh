package org.compiere.apps.search;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.images.Images;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.apps.AEnv;
import org.compiere.apps.AMenu;
import org.compiere.apps.AMenuStartItem;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.MMenu;
import org.compiere.swing.CFrame;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.adempiere.form.IClientUI;

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
			AEnv.addMenuItem("InfoProduct", null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.ALT_MASK), menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_BPartner))
		{
			AEnv.addMenuItem("InfoBPartner", null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.SHIFT_MASK + Event.ALT_MASK), menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_ShowAcct) && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Account))
		{
			AEnv.addMenuItem("InfoAccount", null, KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.ALT_MASK + Event.CTRL_MASK), menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Schedule))
		{
			AEnv.addMenuItem("InfoSchedule", null, null, menu, standardInfoWindowLauncher);
		}
		// FR [ 1966328 ]
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_MRP))
		{
			AEnv.addMenuItem("InfoMRP", "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_CRP))
		{
			AEnv.addMenuItem("InfoCRP", "Info", null, menu, standardInfoWindowLauncher);
		}

		menu.addSeparator();

		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Order))
		{
			AEnv.addMenuItem("InfoOrder", "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Invoice))
		{
			AEnv.addMenuItem("InfoInvoice", "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_InOut))
		{
			AEnv.addMenuItem("InfoInOut", "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Payment))
		{
			AEnv.addMenuItem("InfoPayment", "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_CashJournal))
		{
			AEnv.addMenuItem("InfoCashLine", "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Resource))
		{
			AEnv.addMenuItem("InfoAssignment", "Info", null, menu, standardInfoWindowLauncher);
		}
		if (role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Asset))
		{
			AEnv.addMenuItem("InfoAsset", "Info", null, menu, standardInfoWindowLauncher);
		}

		//
		// Add all custom windows to menu
		for (final I_AD_InfoWindow infoWindow : Services.get(IADInfoWindowDAO.class).retrieveInfoWindowsInMenu(ctx))
		{
			final CMenuItem mi = new CMenuItem(infoWindow.getName(), Images.getImageIcon2("Info16"));
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
		final IUserRolePermissions role = getUserRolePermissions();
		final JFrame parentFrame = getParentFrame();
		final String actionCommand = e.getActionCommand();
		final int windowNo = getParentWindowNo();
		if (Check.isEmpty(actionCommand, true))
		{
			return;
		}
		else if (actionCommand.equals("InfoProduct") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Product))
		{
			InfoBuilder.showProduct(parentFrame, windowNo);
		}
		else if (actionCommand.equals("InfoBPartner") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_BPartner))
		{
			InfoBuilder.showBPartner(parentFrame, windowNo);
		}
		else if (actionCommand.equals("InfoAsset") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Asset))
		{
			InfoBuilder.showAsset(parentFrame, windowNo);
		}
		else if (actionCommand.equals("InfoAccount") && role.hasPermission(IUserRolePermissions.PERMISSION_ShowAcct))
		{
			new org.compiere.acct.AcctViewer();
		}
		else if (actionCommand.equals("InfoSchedule") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Schedule))
		{
			new org.compiere.apps.search.InfoSchedule(parentFrame, null, false);
		}
		// FR [ 1966328 ]
		else if (actionCommand.equals("InfoMRP") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_MRP))
		{
			final CFrame frame = (CFrame)parentFrame;
			final int m_menu_id = MMenu.getMenu_ID("MRP Info");
			final AMenu menu = AEnv.getAMenu(frame);
			final AMenuStartItem form = new AMenuStartItem(m_menu_id, true, Services.get(IMsgBL.class).translate(Env.getCtx(), "MRP Info"), menu);		// async load
			form.start();
		}
		else if (actionCommand.equals("InfoCRP") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_CRP))
		{
			final CFrame frame = (CFrame)parentFrame;
			final int m_menu_id = MMenu.getMenu_ID("CRP Info");
			final AMenu menu = AEnv.getAMenu(frame);
			final AMenuStartItem form = new AMenuStartItem(m_menu_id, true, Services.get(IMsgBL.class).translate(Env.getCtx(), "CRP Info"), menu);		// async load
			form.start();
		}
		else if (actionCommand.equals("InfoOrder") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Order))
		{
			InfoBuilder.showOrder(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals("InfoInvoice") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Invoice))
		{
			InfoBuilder.showInvoice(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals("InfoInOut") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_InOut))
		{
			InfoBuilder.showInOut(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals("InfoPayment") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Payment))
		{
			InfoBuilder.showPayment(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals("InfoCashLine") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_CashJournal))
		{
			InfoBuilder.showCashLine(parentFrame, windowNo, "");
		}
		else if (actionCommand.equals("InfoAssignment") && role.hasPermission(IUserRolePermissions.PERMISSION_InfoWindow_Resource))
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
