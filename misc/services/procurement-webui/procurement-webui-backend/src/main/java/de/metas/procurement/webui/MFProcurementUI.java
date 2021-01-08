// package de.metas.procurement.webui;
//
// import java.net.InetAddress;
// import java.net.MalformedURLException;
// import java.net.URL;
// import java.net.UnknownHostException;
// import java.util.Locale;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Lazy;
// import org.vaadin.spring.i18n.I18N;
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
// import de.metas.procurement.webui.event.MFEventBus;
// import de.metas.procurement.webui.event.UIEventBus;
// import de.metas.procurement.webui.event.UserLoggedInEvent;
// import de.metas.procurement.webui.event.UserLogoutRequestEvent;
// import de.metas.procurement.webui.model.User;
// import de.metas.procurement.webui.server.NotificationErrorHandler;
// import de.metas.procurement.webui.service.IContractsService;
// import de.metas.procurement.webui.service.ISendService;
// import de.metas.procurement.webui.ui.component.MFNavigator;
// import de.metas.procurement.webui.ui.view.LoginView;
// import de.metas.procurement.webui.ui.view.MainView;
// import de.metas.procurement.webui.ui.view.PasswordResetView;
// import de.metas.procurement.webui.util.JavascriptUtils;
// import de.metas.procurement.webui.util.Locales;
// import elemental.events.KeyboardEvent.KeyCode;
// import fi.jasoft.qrcode.QRCode;
//
// /**
//  * The UI's "main" class
//  */
// @SuppressWarnings("serial")
// @SpringUI
// @Widgetset(Constants.WIDGETSET)
// @Theme(Constants.THEME_NAME)
// @PreserveOnRefresh
// @JavaScript({ JavascriptUtils.RESOURCE_JQuery, JavascriptUtils.RESOURCE_MainJS, JavascriptUtils.RESOURCE_Swiped })
// @Push(transport = Transport.WEBSOCKET_XHR)  // NOTE: we need XHR because we are using "remember me" cookie
// public class MFProcurementUI extends UI
// {
// 	public static final String getBpartner_uuid(final UI ui)
// 	{
// 		if (ui == null)
// 		{
// 			return null;
// 		}
// 		if (!(ui instanceof MFProcurementUI))
// 		{
// 			return null;
// 		}
//
// 		final MFProcurementUI procurementUI = (MFProcurementUI)ui;
// 		MFSession mfSession = procurementUI.getMFSession();
// 		if (mfSession == null)
// 		{
// 			return null;
// 		}
//
// 		return mfSession.getBpartner_uuid();
// 	}
//
// 	private final transient Logger logger = LoggerFactory.getLogger(getClass());
//
// 	private static final String STYLE_QRCodeWindow = "qr-code-window";
// 	private static final String STYLE_ConfirmLogoutWindow = "confirm-logout-window";
//
// 	@Autowired
// 	private I18N i18n;
//
// 	private final UIEventBus eventBus = new UIEventBus();
//
// 	@Autowired(required = true)
// 	@Lazy
// 	IContractsService contractsRepository;
//
// 	@Autowired(required = true)
// 	@Lazy
// 	private LoggingConfiguration loggingConfiguration;
//
// 	@Autowired(required = true)
// 	private MFEventBus applicationEventBus;
//
// 	public MFProcurementUI()
// 	{
// 		super();
//
// 		// DEBUGGING: increase log level for some of our classes
// 		// {
// 		// final Logger rootLogger = Logger.getLogger("");
// 		// for (final Handler handler : rootLogger.getHandlers())
// 		// {
// 		// handler.setLevel(Level.ALL);
// 		// }
// 		//
// 		// final Logger logger = Logger.getLogger(ConnectorTracker.class.getName());
// 		// logger.setLevel(Level.ALL);
// 		// }
// 	}
//
// 	@Override
// 	protected void init(final VaadinRequest request)
// 	{
// 		UIEventBus.register(this);
//
// 		//
// 		// Use browser's locale
// 		final Locale browserLocale = getPage().getWebBrowser().getLocale();
// 		setLocale(browserLocale);
// 		getSession().setLocale(browserLocale);
//
// 		//
// 		// Error handling
// 		setErrorHandler(NotificationErrorHandler.of(Notification.Type.ERROR_MESSAGE));
//
// 		//
// 		// Setup navigator
// 		MFNavigator.createAndBind(this)
// 				.setLoginView(LoginView.class, new Supplier<Boolean>()
// 				{
// 					@Override
// 					public Boolean get()
// 					{
// 						return isLoggedIn();
// 					}
// 				})
// 				.setDefaultView(MainView.class)
// 				.setViewNoLoginRequired(PasswordResetView.NAME, PasswordResetView.class);
// 	}
//
// 	@Override
// 	public void detach()
// 	{
// 		super.detach();
//
// 		applicationEventBus.unregisterAllExpired();
// 	}
//
// 	@Override
// 	public MFNavigator getNavigator()
// 	{
// 		return MFNavigator.cast(super.getNavigator());
// 	}
//
// 	public static MFNavigator getCurrentNavigator()
// 	{
// 		return getCurrentUI().getNavigator();
// 	}
//
// 	@SuppressWarnings("unused")
// 	private boolean isMobile()
// 	{
// 		if (VaadinSession.getCurrent().getAttribute("mobile") != null)
// 		{
// 			return true;
// 		}
//
// 		if (Page.getCurrent().getWebBrowser().isTouchDevice())
// 		{
// 			return true;
// 		}
//
// 		return false;
// 	}
//
// 	private void setIsMobile()
// 	{
// 		VaadinSession.getCurrent().setAttribute("mobile", true);
//
// 		// TODO:
// 		// updateContent();
// 	}
//
// 	public static MFProcurementUI getCurrentUI()
// 	{
// 		return (MFProcurementUI)getCurrent();
// 	}
//
// 	public static UIEventBus getCurrentEventBus()
// 	{
// 		return getCurrentUI().eventBus;
// 	}
//
// 	@Subscribe
// 	public void onUserLoggedIn(final UserLoggedInEvent event)
// 	{
// 		try
// 		{
// 			final MFSession mfSession = MFSession.builder()
// 					.setUser(event.getUser())
// 					.setContractsRepository(contractsRepository)
// 					.build();
// 			getSession().setAttribute(MFSession.class, mfSession);
// 			loggingConfiguration.updateMDC();
// 			logger.debug("User logged in");
//
// 			//
// 			// Set user's locale
// 			final Locale userLocale = Locales.forStringOrDefault(mfSession.getUser().getLanguage(), getLocale());
// 			setLocale(userLocale);
// 			getSession().setLocale(userLocale);
//
// 			JavascriptUtils.setBeforePageUnloadMessage(i18n.getWithDefault("pageUnLoadMessage", ""));
//
// 			final MFNavigator navigator = getNavigator();
//
// 			//
// 			// Show after login notification
// 			final String afterLoginMessage = event.getAfterLoginMessage();
// 			if (!Strings.isNullOrEmpty(afterLoginMessage))
// 			{
// 				final Notification notification = new Notification("", afterLoginMessage, Type.ERROR_MESSAGE);
// 				notification.setDelayMsec(60 * 1000);
// 				navigator.setNextViewNotification(notification);
// 			}
//
// 			navigator.navigateToDefaultView();
// 		}
// 		catch (final Exception e)
// 		{
// 			// thx http://stackoverflow.com/questions/25367566/severe-could-not-dispatch-event-eventbus-com-google-common-eventbus-subscriber
// 			logger.error("Caught exception trying to process UserLoggedInEvent=" + event, e);
// 			// TODO: do it right, see EventBus.LoggingSubscriberExceptionHandler.handleException(Throwable, SubscriberExceptionContext)
// 			throw e;
// 		}
// 	}
//
// 	@Subscribe
// 	public void onUserLogoutRequest(final UserLogoutRequestEvent event)
// 	{
// 		try
// 		{
// 			if (confirmNotSentProductQtyReports())
// 			{
// 				doLogout();
// 			}
// 		}
// 		catch (final Exception e)
// 		{
// 			// thx http://stackoverflow.com/questions/25367566/severe-could-not-dispatch-event-eventbus-com-google-common-eventbus-subscriber
// 			logger.error("Caught exception trying to process UserLogoutRequestEvent=" + event, e);
// 			// TODO: do it right, see EventBus.LoggingSubscriberExceptionHandler.handleException(Throwable, SubscriberExceptionContext)
// 			throw e;
// 		}
// 	}
//
// 	private final void doLogout()
// 	{
// 		logger.debug("User logging out...");
//
// 		getSession().setAttribute(MFSession.class, null);
// 		JavascriptUtils.setBeforePageUnloadMessage(null);
//
// 		getPage().setLocation("/");
// 		close();
//
// 		logger.debug("User logged done");
// 	}
//
// 	public User getLoggedInUser()
// 	{
// 		final User user = getLoggedInUserOrNull();
// 		if (user == null)
// 		{
// 			throw new RuntimeException(i18n.get("LoginService.error.notLoggedIn"));
// 		}
// 		return user;
// 	}
//
// 	public final boolean isLoggedIn()
// 	{
// 		return getLoggedInUserOrNull() != null;
// 	}
//
// 	public MFSession getMFSession()
// 	{
// 		return getSession().getAttribute(MFSession.class);
// 	}
//
// 	public static final MFSession getCurrentMFSession()
// 	{
// 		final VaadinSession vaadinSession = VaadinSession.getCurrent();
// 		if (vaadinSession == null)
// 		{
// 			return null;
// 		}
// 		return vaadinSession.getAttribute(MFSession.class);
// 	}
//
// 	private final User getLoggedInUserOrNull()
// 	{
// 		final MFSession mfSession = getMFSession();
// 		return mfSession == null ? null : mfSession.getUser();
// 	}
//
// 	public static boolean isSmallScreenDevice()
// 	{
// 		return UI.getCurrent().getPage().getBrowserWindowWidth() < 600;
// 	}
//
// 	public static boolean isLargeScreenDevice()
// 	{
// 		final float viewPortWidth = UI.getCurrent().getPage().getWebBrowser().getScreenWidth();
// 		return viewPortWidth > 1024;
// 	}
//
// 	@SuppressWarnings("unused")
// 	private final void showNotMobileNotificationWindow()
// 	{
// 		try
// 		{
// 			final URL appUrl = Page.getCurrent().getLocation().toURL();
// 			final String myIp = InetAddress.getLocalHost().getHostAddress();
// 			final String qrCodeUrl = appUrl.toString().replaceAll("localhost", myIp);
//
// 			final QRCode qrCode = new QRCode(i18n.get("notMobileMessage"), qrCodeUrl);
// 			qrCode.setWidth("150px");
// 			qrCode.setHeight("150px");
//
// 			final CssLayout qrCodeLayout = new CssLayout(qrCode);
// 			qrCodeLayout.setSizeFull();
//
// 			final Window window = new Window(null, qrCodeLayout);
// 			window.setWidth(500.0f, Unit.PIXELS);
// 			window.setHeight(200.0f, Unit.PIXELS);
// 			window.addStyleName(STYLE_QRCodeWindow);
// 			window.setModal(true);
// 			window.setResizable(false);
// 			window.setDraggable(false);
// 			window.addCloseListener(new CloseListener()
// 			{
//
// 				@Override
// 				public void windowClose(final CloseEvent e)
// 				{
// 					setIsMobile();
// 				}
// 			});
//
// 			UI.getCurrent().addWindow(window);
// 			window.center();
// 		}
// 		catch (final MalformedURLException e)
// 		{
// 			e.printStackTrace();
// 		}
// 		catch (final UnknownHostException e)
// 		{
// 			e.printStackTrace();
// 		}
// 	}
//
// 	private final boolean confirmNotSentProductQtyReports()
// 	{
// 		final MFSession mfSession = getMFSession();
//
// 		final ISendService sendService = mfSession.getSendService();
// 		if (sendService.getNotSentCounter() <= 0)
// 		{
// 			return true;
// 		}
//
// 		final Label message = new Label(i18n.get("Logout.sendWarning.message"));
// 		final Window confirmDialog = new Window(i18n.get("Logout.sendWarning.caption"));
// 		confirmDialog.addStyleName(STYLE_ConfirmLogoutWindow);
// 		confirmDialog.setModal(true);
// 		confirmDialog.setClosable(true);
// 		confirmDialog.addCloseShortcut(KeyCode.ESC);
// 		confirmDialog.setResizable(false);
//
// 		final VerticalLayout root = new VerticalLayout();
// 		confirmDialog.setContent(root);
//
// 		final VerticalLayout footer = new VerticalLayout();
// 		footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
// 		footer.setWidth("100%");
// 		footer.setSpacing(true);
//
// 		root.addComponents(message, footer);
//
// 		final Button buttonSend = new Button(i18n.get("Logout.sendWarning.sendAndLogoutButton"), new ClickListener()
// 		{
// 			@Override
// 			public void buttonClick(final ClickEvent event)
// 			{
// 				sendService.sendAll();
// 				confirmDialog.close();
// 				doLogout();
// 			}
// 		});
// 		buttonSend.addStyleName(ValoTheme.BUTTON_PRIMARY);
//
// 		final Button buttonLogout = new Button(i18n.get("Logout.caption"), new ClickListener()
// 		{
// 			@Override
// 			public void buttonClick(final ClickEvent event)
// 			{
// 				confirmDialog.close();
// 				doLogout();
// 			}
// 		});
// 		buttonLogout.addStyleName(ValoTheme.BUTTON_DANGER);
//
// 		footer.addComponents(buttonLogout, buttonSend);
// 		footer.setExpandRatio(buttonLogout, 1);
//
// 		addWindow(confirmDialog);
// 		confirmDialog.focus();
// 		return false;
// 	}
// }
