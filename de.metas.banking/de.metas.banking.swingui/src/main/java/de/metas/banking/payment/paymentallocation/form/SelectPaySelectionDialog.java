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
import java.awt.Container;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.search.Info;
import org.compiere.apps.search.InfoBuilder;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.Lookup;
import org.compiere.model.MLookupFactory;
import org.compiere.swing.CDialog;
import org.compiere.swing.CPanel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.banking.model.I_C_BP_BankAccount;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.i18n.IMsgBL;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import net.miginfocom.swing.MigLayout;

/**
 * {@link I_C_PaySelection} dialog. See {@link #SelectPaySelectionDialog(Frame, String, List)}
 *
 * @author al
 */
final class SelectPaySelectionDialog
		extends CDialog
		implements ActionListener
{
	/**
	 *
	 */
	private static final long serialVersionUID = 5069654601970071033L;

	// services
	private static final Logger logger = LogManager.getLogger(SelectPaySelectionDialog.class);
	private final IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	private final int windowNo;

	//
	// Form panels, fields & buttons
	//
	private final CPanel mainPanel = new CPanel();
	private final BorderLayout mainLayout = new BorderLayout();

	private final CPanel fieldPanel = new CPanel();
	private final MigLayout fieldLayout = new MigLayout();

	private final JLabel paySelectionLabel = new JLabel();
	private final VLookup paySelectionField;

	private final JButton createNewPaySelection = new JButton();

	private final ConfirmPanel confirmPanel = ConfirmPanel.builder()
			.withCancelButton(true)
			.build();

	@SuppressWarnings("unused")
	private final I_C_BPartner partner;
	private final List<I_C_Invoice> invoicesToProcess;
	private final int currencyId;

	private final String paymentReference;

	/**
	 * Open pay selection dialog. When pressing OK, {@link I_C_PaySelectionLine}s will be created for the selected {@link I_C_PaySelection}
	 *
	 * @param owner
	 * @param title
	 * @param partner
	 * @param invoicesToProcess
	 *
	 * @throws HeadlessException
	 */
	public SelectPaySelectionDialog(final Frame owner,
			final String title,
			final I_C_BPartner partner,
			final List<I_C_Invoice> invoicesToProcess,
			final int currencyId,
			final String paymentReference)
			throws HeadlessException
	{
		super(owner, title, true); // modal (always)

		//
		// Initialize window number
		windowNo = Env.createWindowNo(getContentPane());

		this.partner = partner;
		this.invoicesToProcess = invoicesToProcess;
		this.currencyId = currencyId;

		final Properties ctx = Env.getCtx();

		//
		// Initialize field(s) which depend on window number
		final String paySelectionColumnName = org.compiere.model.I_C_PaySelection.COLUMNNAME_C_PaySelection_ID;
		final I_AD_Column paySelectionColumn = adTableDAO.retrieveColumn(org.compiere.model.I_C_PaySelection.Table_Name, paySelectionColumnName);

		//
		// Filter out pay selection for bank accounts not matching the currency
		final String whereClause = "EXISTS ("
				+ "SELECT 1 FROM C_BP_BankAccount bpba"
				+ " WHERE bpba.C_BP_BankAccount_ID=C_PaySelection.C_BP_BankAccount_ID"
				+ " AND bpba.C_Currency_ID=" + this.currencyId
				+ ")";

		final Lookup paySelectionLookup;
		try
		{
			paySelectionLookup = MLookupFactory.get(ctx,
					windowNo,
					paySelectionColumn.getAD_Column_ID(),
					DisplayType.Search,
					null, //table name
					paySelectionColumnName,
					paySelectionColumn.getAD_Reference_Value_ID(),
					false, // IsParent,
					whereClause);
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}

		paySelectionField = new VLookup(I_C_PaySelection.COLUMNNAME_C_PaySelection_ID,
				true, // mandatory
				false, // isReadOnly
				true, // isUpdateable
				paySelectionLookup);
		paySelectionField.enableLookupAutocomplete();

		this.paymentReference = paymentReference;

		init();
	}

	private final void init()
	{
		final Container contentPane = getContentPane();

		try
		{
			jbInit();

			contentPane.add(mainPanel, BorderLayout.CENTER);
			contentPane.add(confirmPanel, BorderLayout.SOUTH);
		}
		catch (final Exception e)
		{
			logger.error("", e);
		}
	}

	private final void jbInit()
	{
		AdempierePLAF.setDefaultBackground(mainPanel);
		mainPanel.setLayout(mainLayout);

		//
		// Bind panels
		mainPanel.add(fieldPanel, BorderLayout.NORTH);

		final Properties ctx = Env.getCtx();

		//
		// Init fieldPanel
		fieldPanel.setLayout(fieldLayout);

		// C_BP_BankAccount
		paySelectionLabel.setText(msgBL.translate(ctx, I_C_PaySelection.COLUMNNAME_C_PaySelection_ID));
		paySelectionLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		fieldPanel.add(paySelectionLabel, "growx, pushx");

		fieldPanel.add(paySelectionField, "growx, pushx, width 170, wrap");

		createNewPaySelection.setText(msgBL.parseTranslation(ctx, "@Create@ @" + I_C_PaySelection.COLUMNNAME_C_PaySelection_ID + "@"));
		createNewPaySelection.setHorizontalAlignment(SwingConstants.RIGHT);
		fieldPanel.add(createNewPaySelection, "pushx, alignx left");

		//
		// Bind action listener(s) to button(s)
		createNewPaySelection.addActionListener(e -> createNewPaySelection());

		//
		// Init confirmPanel
		confirmPanel.setActionListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (e.getActionCommand().equals(ConfirmPanel.A_OK))
		{
			onDialogOk();
		}
		else if (e.getActionCommand().equals(ConfirmPanel.A_CANCEL))
		{
			dispose(); // on cancel, dispose of the form
		}
	}

	/**
	 * When pressing OK, fill payment request
	 */
	private void onDialogOk()
	{
		final int paySelectionId = paySelectionField.getValueAsInt();
		if (paySelectionId <= 0)
		{
			final Exception ex = new AdempiereException("@Select@ @" + I_C_PaySelection.COLUMNNAME_C_PaySelection_ID + "@");
			ADialog.error(windowNo, getContentPane(), ex);
			return;
		}

		//
		// Create C_PaySelection in transaction
		trxManager.run((TrxRunnable)localTrxName -> {
			final I_C_PaySelection paySelection = InterfaceWrapperHelper.create(Env.getCtx(), paySelectionId, I_C_PaySelection.class, localTrxName);
			handleForPaySelection(paySelection);
		});
	}

	private void createNewPaySelection()
	{
		//
		// Create C_PaySelection in transaction
		trxManager.run((TrxRunnable)localTrxName -> {
			final IContextAware contextProvider = PlainContextAware.newWithTrxName(Env.getCtx(), localTrxName);
			final I_C_PaySelection paySelection = InterfaceWrapperHelper.newInstance(I_C_PaySelection.class, contextProvider);

			final Timestamp currentTimestamp = SystemTime.asTimestamp();
			paySelection.setName(currentTimestamp.toString());
			paySelection.setPayDate(currentTimestamp);

			final int bpBankAccountId = manuallySearchBPBankAccountId();
			if (bpBankAccountId == RESULT_CANCELLED)
			{
				return; // silently exit
			}
			else if (bpBankAccountId <= 0) // we REALLY need a bank account for this to work
			{
				final Exception ex = new AdempiereException("@NotFound@ @" + org.compiere.model.I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID + "@");
				ADialog.error(windowNo, getContentPane(), ex);
				return;
			}

			paySelection.setC_BP_BankAccount_ID(bpBankAccountId);

			InterfaceWrapperHelper.save(paySelection);
			handleForPaySelection(paySelection);
		});
	}

	private static final int RESULT_CANCELLED = -1;

	/**
	 * Open an {@link Info} search window for the user to find a suitable {@link I_C_BP_BankAccount}
	 *
	 * @return selected bank account or 0 if none was found or user cancelled
	 */
	private final int manuallySearchBPBankAccountId()
	{
		final Info ip = InfoBuilder.newBuilder()
				.setParentFrame((Frame)getOwner())
				.setModal(true)
				.setTableName(I_C_BP_BankAccount.Table_Name)
				.setWhereClause(I_C_BP_BankAccount.COLUMNNAME_C_Currency_ID + "=" + currencyId)
				.buildAndShow();

		final boolean cancelled = ip.isCancelled();
		if (cancelled)
		{
			return RESULT_CANCELLED;
		}

		final Integer result = (Integer)ip.getSelectedKey();
		if (result == null)
		{
			return RESULT_CANCELLED;
		}
		return result;
	}

	private final void handleForPaySelection(final I_C_PaySelection paySelection)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(paySelection);
		Check.assumeNotEmpty(trxName, "paySelection in transaction");

		for (final I_C_Invoice invoice : invoicesToProcess)
		{
			createPaySelectionLine(paySelection, invoice);
		}

		//
		// Load C_PaySelection window with the given ID
		SwingUtilities.invokeLater(() -> {
			final int paySelectionTableID = InterfaceWrapperHelper.getTableId(I_C_PaySelection.class);
			AEnv.zoom(paySelectionTableID, paySelection.getC_PaySelection_ID());
		});

		//
		// Dispose of this dialog
		dispose();
	}

	private final void createPaySelectionLine(final I_C_PaySelection paySelection, final I_C_Invoice invoice)
	{
		//
		// Attempt to retrieve it if existing, or create a new one otherwise
		final I_C_PaySelectionLine paySelectionLine;

		final List<I_C_PaySelectionLine> paySelectionLinesForInvoice = paySelectionDAO.retrievePaySelectionLinesMatchingInvoice(paySelection, invoice);
		if (paySelectionLinesForInvoice.size() > 1)
		{
			final Exception ex = new AdempiereException("@Matching@ @" + org.compiere.model.I_C_Invoice.Table_Name + "@");
			ADialog.error(windowNo, getContentPane(), ex); // TODO: throw ex
			return; // can't match
		}
		else if (paySelectionLinesForInvoice.size() == 1)
		{
			paySelectionLine = paySelectionLinesForInvoice.get(0);
		}
		else
		{
			paySelectionLine = InterfaceWrapperHelper.newInstance(I_C_PaySelectionLine.class, paySelection);
		}

		paySelectionLine.setAD_Org_ID(paySelection.getAD_Org_ID());
		paySelectionLine.setC_Invoice(invoice);
		paySelectionLine.setC_PaySelection(paySelection);
		paySelectionLine.setIsSOTrx(invoice.isSOTrx());
		paySelectionLine.setPaymentRule(invoice.getPaymentRule());
		paySelectionLine.setC_BPartner_ID(invoice.getC_BPartner_ID());
		paySelectionLine.setReference(paymentReference);

		if (!paymentRequestBL.updatePaySelectionLineFromPaymentRequestIfExists(paySelectionLine))
		{
			//
			// Fallback to invoice open amount (as of task 09698, before it was the invoice's GrandTotal)
			final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
			final BigDecimal payAmt = allocationDAO.retrieveOpenAmt(invoice, true);
			paySelectionLine.setPayAmt(payAmt);
			paySelectionLine.setDifferenceAmt(BigDecimal.ZERO);
		}

		//
		// 08701: the open amount needs to be the *real* open amount because that's what we will pay, and we want to take care of creditmemos too
		final BigDecimal openAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice);
		paySelectionLine.setOpenAmt(openAmt);

		paySelectionLine.setDiscountAmt(BigDecimal.ZERO);

		//
		// Commented-out lines below are left not completed (not required / default / we don't have data to auto-complete them)
		//
		// paySelectionLine.setDescription(String); // auto
		// paySelectionLine.setIsActive(boolean); // auto
		// paySelectionLine.setIsManual(boolean); // auto
		// paySelectionLine.setProcessed(boolean); // auto

		//
		// If the Line was not filled, increment it (just to be safe... defaultValue does not always work for columns)
		if (paySelectionLine.getLine() == 0)
		{
			final int line = (paySelectionDAO.retrievePaySelectionLinesCount(paySelection) + 1) * 10;
			paySelectionLine.setLine(line);
		}

		InterfaceWrapperHelper.save(paySelectionLine);
	}

	@Override
	public void dispose()
	{
		super.dispose();
		Env.clearWinContext(windowNo);
	}
}
