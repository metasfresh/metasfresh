package de.metas.banking.payment.paymentallocation.form;

/*
 * #%L
 * de.metas.banking.swingui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.apps.ConfirmPanel;
import org.compiere.grid.ed.VLookup;
import org.compiere.grid.ed.VNumber;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.Lookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CPanel;
import org.compiere.swing.CTextField;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.IPaymentStringBL;
import de.metas.banking.payment.IPaymentStringDataProvider;
import de.metas.banking.payment.spi.IPaymentStringParser;
import de.metas.banking.payment.spi.exception.PaymentStringParseException;
import de.metas.i18n.IMsgBL;
import de.metas.interfaces.I_C_BP_Relation;
import de.metas.logging.LogManager;
import de.metas.payment.model.I_C_Payment_Request;
import net.miginfocom.swing.MigLayout;

class ReadPaymentDocumentPanel
		implements ActionListener
{
	// Services
	private static final Logger logger = LogManager.getLogger(ReadPaymentDocumentDialog.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IPaymentStringBL paymentStringBL = Services.get(IPaymentStringBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IClientUI clientUI = Services.get(IClientUI.class);

	public static final String HEADER_READ_PAYMENT_STRING = "ReadPaymentString";

	private static final String MSG_CouldNotFindOrCreateBPBankAccount = "de.metas.payment.CouldNotFindOrCreateBPBankAccount";

	private static final String SYSCONFIG_PaymentStringParserType = "de.metas.paymentallocation.form.ReadPaymentDocumentDialog.PaymentStringParserType";

	private static final String CONSTRAINT_GROWX_PUSHX = "growx, pushx";
	private static final String CONSTRAINT_GROWX_PUSHX_WIDTH_170_WRAP = "growx, pushx, width 170, wrap";

	private static final String FIELDNAME_PaymentString = "PaymentString";

	private static final KeyStroke KEYSTROKE_ENTER_RELEASED = KeyStroke.getKeyStroke("released ENTER");
	private static final KeyStroke KEYSTROKE_TAB_RELEASED = KeyStroke.getKeyStroke("released TAB");

	private static final String ACTION_ParsePaymentString = "ParsePaymentString";

	/**
	 * Payment request to be filled
	 */
	private I_C_Payment_Request paymentRequest = null;

	/**
	 * Bank account to be determined
	 */
	private I_C_BP_BankAccount _bankAccount;
	private boolean newBankAccount = false;

	private final int windowNo;
	private final Window _window;

	private final IPaymentStringParser paymentStringParser;
	private IPaymentString currentParsedPaymentString = null;
	private String currentPaymentString = "";

	//
	// Form panels, fields & buttons
	private final CPanel mainPanel = new CPanel();

	private final CPanel fieldPanel = new CPanel();
	private final MigLayout fieldLayout = new MigLayout();

	private final JLabel paymentStringLabel = new JLabel();
	private final CTextField paymentStringField = new CTextField();

	private final JLabel bpartnerLabel = new JLabel();
	private final VLookup bpartnerField;

	private final JLabel bpBankAccountLabel = new JLabel();
	private final VLookup bpBankAccountField;
	private final JLabel amountLabel = new JLabel();
	private final VNumber amountField;

	private int contextBPartner_ID;

	/**
	 * @param window
	 * @param AD_Org_ID
	 */
	public ReadPaymentDocumentPanel(final Window window, final int AD_Org_ID)
	{
		this(Env.createWindowNo(window), window, AD_Org_ID);
	}

	/**
	 * @param window
	 * @param AD_Org_ID
	 */
	ReadPaymentDocumentPanel(final int WindowNo, final Window window, final int AD_Org_ID)
	{
		super();

		_window = window;

		//
		// Initialize window number
		windowNo = WindowNo;

		//
		// Initialize field(s)
		final Properties ctx = Env.getCtx();

		//
		// Load payment string parser
		paymentStringParser = paymentStringBL.getParserForSysConfig(SYSCONFIG_PaymentStringParserType);

		//
		// Load partner lookup column
		final I_AD_Column bpartnerColumn = adTableDAO.retrieveColumn(I_C_BPartner.Table_Name, I_C_BPartner.COLUMNNAME_C_BPartner_ID);

		final String validationCode = I_C_BPartner.Table_Name + "." + I_C_BPartner.COLUMNNAME_AD_Org_ID + " = " + AD_Org_ID;
		final Lookup bpartnerLookup = MLookupFactory.get(
				Env.getCtx(),
				WindowNo,
				bpartnerColumn.getAD_Column_ID(),
				DisplayType.Search,
				null, // tablename
				I_C_BPartner.COLUMNNAME_C_BPartner_ID,
				138, // C_BPartner (trx)
				false,
				validationCode);

		bpartnerField = new VLookup(I_C_BPartner.COLUMNNAME_C_BPartner_ID,
				true, // mandatory
				false, // isReadOnly (initialized RO)
				true, // isUpdateable
				bpartnerLookup);

		//
		// Load bank account lookup column
		final I_AD_Column bpBankAccountColumn = adTableDAO.retrieveColumn(org.compiere.model.I_C_BP_BankAccount.Table_Name,
				org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID);
		final Lookup bpBankAccountLookup = MLookupFactory.get(Env.getCtx(), WindowNo, 0, bpBankAccountColumn.getAD_Column_ID(), DisplayType.Search);

		bpBankAccountField = new VLookup(I_C_Payment_Request.COLUMNNAME_C_BP_BankAccount_ID,
				true, // mandatory
				false, // isReadOnly
				true, // isUpdateable
				bpBankAccountLookup);

		amountField = new VNumber(I_C_Payment_Request.COLUMNNAME_Amount,
				false, // mandatory
				false, // isReadOnly
				true, // isUpdateable
				DisplayType.Number, // displayType
				msgBL.translate(ctx, I_C_Payment_Request.COLUMNNAME_Amount));

		init();
	}

	/**
	 * Optionally set the ID of a bPartner that needs to have a relation to the payment document which we are reading.<br>
	 * If set, and the system finds an existing {@link I_C_BP_BankAccount}, then that bank account is only used
	 * if it belongs to the given {@code contextBPartner} or to a second bPartner who has a {@code RemitTo} {@link I_C_BP_Relation} with the given {@code contextPartner}.
	 *
	 * @param contextBPartner_ID
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/781
	 */
	public void setContextBPartner_ID(final int contextBPartner_ID)
	{
		this.contextBPartner_ID = contextBPartner_ID;
	}

	public Component getComponent()
	{
		return mainPanel;
	}

	public I_C_Payment_Request getCreatedPaymentRequest()
	{
		return paymentRequest;
	}

	private final void init()
	{

		try
		{
			jbInit();
		}
		catch (final Exception e)
		{
			logger.error("", e);
		}
	}

	private final void jbInit()
	{
		AdempierePLAF.setDefaultBackground(mainPanel);
		mainPanel.setLayout(new BorderLayout());

		//
		// Bind panels
		mainPanel.add(fieldPanel, BorderLayout.CENTER);

		final Properties ctx = Env.getCtx();

		//
		// Init fieldPanel
		fieldPanel.setLayout(fieldLayout);

		// Payment String Field (for scanning)
		paymentStringLabel.setText(msgBL.getMsg(ctx, FIELDNAME_PaymentString));
		paymentStringLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		fieldPanel.add(paymentStringLabel, CONSTRAINT_GROWX_PUSHX);

		fieldPanel.add(paymentStringField, CONSTRAINT_GROWX_PUSHX_WIDTH_170_WRAP);

		// C_BPartner
		bpartnerLabel.setText(msgBL.translate(ctx, I_C_BPartner.COLUMNNAME_C_BPartner_ID));
		bpartnerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		fieldPanel.add(bpartnerLabel, CONSTRAINT_GROWX_PUSHX);

		fieldPanel.add(bpartnerField, CONSTRAINT_GROWX_PUSHX_WIDTH_170_WRAP);
		bpartnerField.enableLookupAutocomplete();

		// C_BP_BankAccount
		bpBankAccountLabel.setText(msgBL.translate(ctx, I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID));
		bpBankAccountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		fieldPanel.add(bpBankAccountLabel, CONSTRAINT_GROWX_PUSHX);

		fieldPanel.add(bpBankAccountField, CONSTRAINT_GROWX_PUSHX_WIDTH_170_WRAP);
		bpBankAccountField.enableLookupAutocomplete();

		// Amount
		amountLabel.setText(msgBL.translate(ctx, I_C_Payment_Request.COLUMNNAME_Amount));
		amountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		fieldPanel.add(amountLabel, CONSTRAINT_GROWX_PUSHX);

		fieldPanel.add(amountField, CONSTRAINT_GROWX_PUSHX_WIDTH_170_WRAP);

		//
		// Property change listeners
		{
			//
			// Handle deletion of bank accounts if user changes stuff
			bpBankAccountField.addVetoableChangeListener(new VetoableChangeListener()
			{
				@Override
				public void vetoableChange(final PropertyChangeEvent evt)
				{
					final int bankAccountToSetId = evt.getNewValue() == null ? -1 : (Integer)evt.getNewValue();
					onChangeC_BP_BankAccount_ID(bankAccountToSetId);
				}
			});
		}

		//
		// Field key listeners (i.e on Enter or Tab pressed)
		{
			final Action parsePaymentStringAction = new AbstractAction()
			{
				/**
					 *
					 */
				private static final long serialVersionUID = 6251194470048113990L;

				@Override
				public void actionPerformed(final ActionEvent e)
				{
					parsePaymentString(ctx);
				}
			};

			//
			// Bind TAB-ENTER on payment string field
			paymentStringField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KEYSTROKE_TAB_RELEASED, ACTION_ParsePaymentString);
			paymentStringField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KEYSTROKE_ENTER_RELEASED, ACTION_ParsePaymentString);
			paymentStringField.getActionMap().put(ACTION_ParsePaymentString, parsePaymentStringAction);
			paymentStringField.setFocusTraversalKeysEnabled(false);
		}

		//
		// Focus listeners
		{
			bpartnerField.addFocusListener(new FocusAdapter()
			{
				@Override
				public void focusLost(final FocusEvent e)
				{
					if (bpartnerField.getValueAsInt() <= 0)
					{
						return; // do not trigger payment parsing if no partner is selected
					}
					parsePaymentString(ctx);
				}
			});
		}

		//
		// Init confirmPanel
		final ConfirmPanel confirmPanel = ConfirmPanel.newWithOKAndCancel();
		mainPanel.add(confirmPanel, BorderLayout.SOUTH);
		confirmPanel.setActionListener(this);
	}

	private void dispose()
	{
		if (_window != null)
		{
			_window.dispose();
		}
	}

	/**
	 * Parse payment string if it was changed and update bank account and amount if it's valid
	 *
	 * @param e focus event
	 */
	private final void parsePaymentString(final Properties ctx)
	{
		updateCurrentPaymentString();

		if (Check.isEmpty(currentPaymentString, true))
		{
			return; // do nothing if it's empty
		}

		final IPaymentStringDataProvider dataProvider;
		try
		{
			dataProvider = paymentStringBL.getDataProvider(ctx, paymentStringParser, currentPaymentString);
		}
		catch (final PaymentStringParseException pspe)
		{
			final String adMessage = pspe.getLocalizedMessage() + " (\"" + currentPaymentString + "\")";
			clientUI.warn(windowNo, adMessage);
			return;
		}

		final IPaymentString paymentString = dataProvider.getPaymentString();

		final I_C_BP_BankAccount bpBankAccountExisting = getAndVerifyBPartnerAccount(dataProvider);
		if (bpBankAccountExisting != null)
		{
			setC_BPartner_ID(bpBankAccountExisting.getC_BPartner_ID());
			setC_BP_BankAccount(bpBankAccountExisting);
		}
		else
		{
			// if the C_BPartner is set from the field, create the C_BP_BankAccount on the fly
			// otherwise, show error and ask the user to set the partner before doing this.
			// also lock/unlock field as needed
			final int bpartnerId = getC_BPartner_ID();
			if (bpartnerId <= 0)
			{
				bpartnerField.setReadWrite(true); // set read-only to false (user can edit)
				bpartnerField.requestFocus();

				final Exception ex = new AdempiereException(ctx, MSG_CouldNotFindOrCreateBPBankAccount, new Object[] {});
				clientUI.warn(windowNo, ex);
				return;
			}
			else
			{
				final IContextAware contextProvider = PlainContextAware.newOutOfTrx(ctx);

				//
				// Let the user edit for now
				// bpartnerField.setReadWrite(false); // set read-only to true (user shall not be able to edit)
				final I_C_BP_BankAccount bpBankAccountNew = dataProvider.createNewC_BP_BankAccount(contextProvider, bpartnerId);
				setC_BP_BankAccount(bpBankAccountNew);
				newBankAccount = true;
			}
		}

		final BigDecimal amount = paymentString.getAmount();
		setAmount(amount);

		currentParsedPaymentString = paymentString;
	}

	/**
	 * Calls {@link IPaymentStringDataProvider#getC_BP_BankAccounts()} and
	 * <li>filters out accounts where {@link #validateAgainstContextBPartner(I_C_BP_BankAccount)} returned a {@code 3}
	 * <li>orders by the result of {@link #validateAgainstContextBPartner(I_C_BP_BankAccount)}, i.e. prefers 1s order 2s
	 * <li>returns the first match.
	 *
	 * @param dataProvider
	 * @return
	 */
	private I_C_BP_BankAccount getAndVerifyBPartnerAccount(final IPaymentStringDataProvider dataProvider)
	{
		final Optional<I_C_BP_BankAccount> firstBankAccount = dataProvider.getC_BP_BankAccounts().stream()
				.map(bankAccount -> ImmutablePair.of(bankAccount, validateAgainstContextBPartner(bankAccount)))
				.filter(pair -> pair.getRight() < 3)
				.sorted(Comparator.comparing(IPair::getRight))
				.map(pair -> pair.getLeft())
				.findFirst();

		return firstBankAccount.orElse(null);
	}

	/**
	 * Checks the given {@code bpBankAccount} against the partner that was set via {@link #setContextBPartner_ID(I_C_BPartner)} (if any) and returns:
	 * <li>{@code 1} if no {@link #setContextBPartner_ID(I_C_BPartner)} was set, or if the given {@code bpBankAccount}'s bPartner is the one that was set
	 * <li>{@code 2} else, if the given {@code bpBankAccount}'s bPartner is a {@code RemitTo} partner of the {@link #setContextBPartner_ID(I_C_BPartner)} partner
	 * <li>{@code 3} else
	 *
	 * @param bpBankAccount
	 * @return
	 */
	private int validateAgainstContextBPartner(final I_C_BP_BankAccount bpBankAccount)
	{
		if (contextBPartner_ID <= 0)
		{
			// we have no BPartner-Context-Info, so we can't verify the bank account.
			return 1;
		}

		if (contextBPartner_ID == bpBankAccount.getC_BPartner_ID())
		{
			// the BPartner from the account we looked up is thae one from context
			return 1;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final boolean existsRelation = queryBL.createQueryBuilder(I_C_BP_Relation.class, bpBankAccount)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsRemitTo, true)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, contextBPartner_ID)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID, bpBankAccount.getC_BPartner_ID())
				.create()
				.match();
		if (existsRelation)
		{
			return 2;
		}
		return 3;
	}

	public IPaymentString getParsedPaymentStringOrNull()
	{
		return currentParsedPaymentString;
	}

	/**
	 * Sets model payment string from it's corresponding field
	 */
	private final void updateCurrentPaymentString()
	{
		final String paymentString = (String)paymentStringField.getValue();
		currentPaymentString = paymentString;

		//
		// Set focus to the bank account field so that the user knows he's scanned the ESR button successfully
		if (Check.isEmpty(currentPaymentString, true))
		{
			bpartnerField.setReadWrite(false);
			paymentStringField.requestFocus();
		}
		else if (getC_BPartner_ID() <= 0)
		{
			bpartnerField.setReadWrite(true);
			bpartnerField.requestFocus();
		}
		else
		{
			bpBankAccountField.requestFocus();
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			//
			// If we're in the middle of changing the payment string and the user pressed enter, do not consider he pressed OK
			if (isPaymentStringChanging() || getC_BPartner_ID() <= 0)
			{
				updateCurrentPaymentString();
				return;
			}
			onDialogOk();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose();
		}
	}

	/**
	 * @return true if the internal value of the payment string differs from the value within it's corresponding field
	 */
	private boolean isPaymentStringChanging()
	{
		final Object paymentStringValue = paymentStringField.getValue();
		return !Objects.equals(currentPaymentString, paymentStringValue);
	}

	/**
	 * Flag that the window is being closed after pressing OK
	 */
	private boolean userPressedOK = false;

	/**
	 * When pressing OK, fill payment request
	 */
	private void onDialogOk()
	{
		final IContextAware contextProvider = PlainContextAware.newOutOfTrx(Env.getCtx());

		//
		// Create it, but do not save it!
		paymentRequest = InterfaceWrapperHelper.newInstance(I_C_Payment_Request.class, contextProvider);
		InterfaceWrapperHelper.setSaveDeleteDisabled(paymentRequest, true);

		// TODO: don't enable OK, or throw an error here, if no C_BP_BankAccount was found
		paymentRequest.setC_BP_BankAccount(getC_BP_BankAccountOrNull());

		final BigDecimal amount = getAmount();
		paymentRequest.setAmount(amount);

		final IPaymentString paymentString = getParsedPaymentStringOrNull();
		if (paymentString != null)
		{
			paymentRequest.setReference(paymentString.getReferenceNoComplete());
			paymentRequest.setFullPaymentString(paymentString.getRawPaymentString());
		}

		//
		// See JavaDoc
		userPressedOK = true;

		//
		// Dispose of this dialog
		dispose();
	}

	private I_C_BP_BankAccount getC_BP_BankAccountOrNull()
	{
		return _bankAccount;
	}

	private boolean isNewlyCreatedC_BP_BankAccount()
	{
		return newBankAccount // only if introduced within this window's instance
				&& _bankAccount != null && _bankAccount.getC_BP_BankAccount_ID() > 0; // existing bank accounts only
	}

	private void setC_BP_BankAccount(final I_C_BP_BankAccount bankAccount)
	{
		//
		// Set the internal BPBankAccount and the field's value (IMPORTANT because of property change listeners (deletion trigger))
		final int bankAccountToSetId;
		if (bankAccount != null && bankAccount.getC_BP_BankAccount_ID() > 0)
		{
			bankAccountToSetId = bankAccount.getC_BP_BankAccount_ID();
			bpBankAccountField.setValue(bankAccountToSetId);
		}
		else
		{
			bankAccountToSetId = -1;
			bpBankAccountField.setValue(null);
		}
		onChangeC_BP_BankAccount_ID(bankAccountToSetId);
	}

	private void onChangeC_BP_BankAccount_ID(final int bankAccountToSetId)
	{
		final int bankAccountAlreadySetId;

		final I_C_BP_BankAccount bankAccountAlreadySet = getC_BP_BankAccountOrNull();
		bankAccountAlreadySetId = bankAccountAlreadySet == null ? -1 : bankAccountAlreadySet.getC_BP_BankAccount_ID();

		if (bankAccountAlreadySetId == bankAccountToSetId)
		{
			return; // do nothing if it's the same account
		}

		if (isNewlyCreatedC_BP_BankAccount())
		{
			InterfaceWrapperHelper.delete(bankAccountAlreadySet); // only delete if introduced within this window's instance and if it's an existing bank account
		}

		final Integer bankAccountToSetIdAsInteger = bankAccountToSetId;
		if (bankAccountToSetIdAsInteger != null && bankAccountToSetIdAsInteger > 0)
		{
			_bankAccount = InterfaceWrapperHelper.create(Env.getCtx(), bankAccountToSetIdAsInteger, I_C_BP_BankAccount.class, ITrx.TRXNAME_None);
		}
		else
		{
			_bankAccount = null;
		}
		newBankAccount = false; // always assume false at the beginning
	}

	private int getC_BPartner_ID()
	{
		final int bpartnerId = bpartnerField.getValueAsInt();
		return bpartnerId;
	}

	private void setC_BPartner_ID(final int bpartnerId)
	{
		bpartnerField.setValue(bpartnerId);
	}

	private BigDecimal getAmount()
	{
		final BigDecimal amount;
		final Object amountFieldValue = amountField.getValue();
		if (amountFieldValue != null && !Check.isEmpty(amountFieldValue.toString()))
		{
			amount = new BigDecimal(amountFieldValue.toString());
		}
		else
		{
			amount = BigDecimal.ZERO;
		}
		return amount;
	}

	private void setAmount(final BigDecimal amount)
	{
		amountField.setValue(amount);
	}

	/**
	 * Executed when dialog focus is gained.
	 *
	 * @param e focus event
	 */
	public void focusGained(final FocusEvent e)
	{
		updateCurrentPaymentString();
	}

	public void onContainerDispose()
	{
		if (!userPressedOK && isNewlyCreatedC_BP_BankAccount())
		{
			InterfaceWrapperHelper.delete(getC_BP_BankAccountOrNull()); // will not be null if validated by isNewlyCreatedC_BP_BankAccount()
		}
		Env.clearWinContext(windowNo);
	}

	public Optional<ReadPaymentPanelResult> getResultIfValid()
	{
		final I_C_Payment_Request paymentRequestTemplate = getCreatedPaymentRequest();
		if (paymentRequestTemplate == null)
		{
			// dialog was cancelled => nothing to do
			return Optional.empty();
		}

		final ReadPaymentPanelResult.Builder resultBuilder = ReadPaymentPanelResult.builder();

		//
		// Set partner
		final org.compiere.model.I_C_BP_BankAccount bankAccount = paymentRequestTemplate.getC_BP_BankAccount();
		if (bankAccount != null)
		{
			resultBuilder.setC_BPartner_ID(bankAccount.getC_BPartner_ID());
		}

		//
		// Set payment
		final BigDecimal amount = paymentRequestTemplate.getAmount();
		final BigDecimal totalPay;
		if (amount != null)
		{
			totalPay = amount;
		}
		else
		{
			totalPay = BigDecimal.ZERO;
		}
		resultBuilder.setTotalPaymentCandidatesAmt(totalPay);

		final IPaymentString parsedPaymentString = getParsedPaymentStringOrNull();
		if (parsedPaymentString == null)
		{
			return Optional.empty();
		}
		final String reference = parsedPaymentString.getReferenceNoComplete();
		paymentRequestTemplate.setReference(reference);
		paymentRequestTemplate.setFullPaymentString(parsedPaymentString.getRawPaymentString()); // FRESH-318

		resultBuilder.setPaymentRequestTemplate(paymentRequestTemplate);
		resultBuilder.setPaymentReference(reference);

		final ReadPaymentPanelResult result = resultBuilder.build();
		return Optional.of(result);
	}
}
