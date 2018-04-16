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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.LoggerLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.apps.AEnv;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.grid.VPanel;
import org.compiere.model.GridField;
import org.compiere.model.I_C_BPartner;
import org.compiere.swing.CPanel;
import org.compiere.swing.table.AnnotatedTableFactory;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.jdesktop.swingx.JXTable;

import com.google.common.collect.Lists;
import com.jgoodies.looks.Options;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.payment.IPaymentString;
import de.metas.banking.payment.paymentallocation.model.IInvoiceCandidateRow;
import de.metas.banking.payment.paymentallocation.model.IInvoiceRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceCandidatesTableModel;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationContext;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationTotals;
import de.metas.banking.payment.paymentallocation.service.IPaymentAllocationFormDAO;
import de.metas.i18n.IMsgBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateEnqueuer;
import de.metas.invoicecandidate.api.impl.PlainInvoicingParams;
import de.metas.invoicecandidate.form.CreateInvoiceCandidateDialog;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.payment.model.I_C_Payment_Request;

/**
 * Automatic reading and processing of Payment Document<br>
 * <br>
 * Read Payment Document with document Reader
 * <ul>
 * <li>Window opens with List of possible vendors (if not unique)</li>
 * <li>Window opens with pre-filtered Rechnungsdispo Lines.</li>
 * <li>User can select/ deselect Lines for invoicing</li>
 * <li>Status Bar with comparison of Read payment amount vs. selected payment amount.</li>
 * <li>Possibility of discount (skonto and so on, same as in Zahlung-Zuordnung ({@link de.metas.banking.payment.paymentallocation.form.PaymentAllocationForm}))</li>
 * <li>After user presses ok, check if selected amount matches read amount. Choice for Zahlung anweisen Document. Create invoice and Zahlung anweisen line.</li>
 * </ul>
 *
 * WARNING: atm this functionality is NOT used!!!!
 *
 * @author al
 */
final class ReadPaymentForm
		extends AbstractPaymentForm
		implements FormPanel, ActionListener, TableModelListener, VetoableChangeListener
{
	// Services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	private final transient IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);
	private final transient IClientUI clientUI = Services.get(IClientUI.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IPaymentAllocationFormDAO dao = Services.get(IPaymentAllocationFormDAO.class);

	public static final String PROPERTY_ReadPaymentStringButton = "ReadPaymentString";

	private static final String PROPERTY_C_BPartner_ID = "C_BPartner_ID";
	private static final String PROPERTY_Date = "Date";
	private static final String PROPERTY_AD_Org_ID = "AD_Org_ID";
	private static final String PROPERTY_C_Currency_ID = "C_Currency_ID";
	private static final String PROPERTY_RefreshButton = "Refresh";

	private static final String MSG_PREFIX = "ReadPaymentForm.";
	private static final String PROPERTY_PaymentRequest_TotalPayments = IInvoiceRow.PROPERTY_PaymentRequestAmt;

	private static final String BUTTON_CREATE_INVOICE = "CreateInvoice";
	private static final String BUTTON_CREATE_INVOICE_CANDIDATE = "@Create@ @" + I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID + "@";

	// private static final int PID_C_Invoice_Candidate_EnqueueSelection = 540304;
	// private static final String PARA_IgnoreInvoiceSchedule = "IgnoreInvoiceSchedule";
	// private static final String PARA_DateInvoiced = I_C_Invoice.COLUMNNAME_DateInvoiced;

	private final CPanel mainPanel = new CPanel();
	private final List<Object> componentList = new ArrayList<>();

	private final InvoiceCandidatesTableModel invoiceCandidatesTableModel = new InvoiceCandidatesTableModel();
	private BigDecimal _totalPayExisting = BigDecimal.ZERO;

	ReadPaymentForm()
	{
		super();
	}

	private PaymentAllocationContext createPaymentAllocationContext()
	{
		return PaymentAllocationContext.builder()
				.setAD_Org_ID(getAD_Org_ID())
				.setC_BPartner_ID(getC_BPartner_ID())
				.setC_Currency_ID(getC_Currency_ID())
				.setDate(getDate())
				//
				.build();
	}

	/**
	 * Initialize Panel
	 *
	 * @param WindowNo window
	 * @param frame frame
	 */
	@Override
	public void init(final int WindowNo, final FormFrame frame)
	{
		setWindowNo(WindowNo);
		m_frame = frame;
		Env.setContext(getCtx(), WindowNo, "IsSOTrx", true); // defaults to no
		try
		{
			dynInit();
			jbInit();
			frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

			updateInfos(true); // re-query
		}
		catch (final Exception e)
		{
			logger.error(e.getLocalizedMessage(), e);
		}
	}

	// parameterPanel
	// -

	// allocationPanel
	private final JButton createInvoiceButton = new JButton();
	private final JButton createInvoiceCandidateButton = new JButton();

	// invoiceCandidatePanel

	private final StatusBar statusBar = new StatusBar();

	/**
	 * Static Init
	 *
	 */
	private void jbInit()
	{
		final CPanel panel = new CPanel();
		AdempierePLAF.setDefaultBackground(panel);

		// mainPanel
		final BorderLayout mainLayout = new BorderLayout();
		mainPanel.setLayout(mainLayout);

		final VPanel parameterPanel = VPanel.newCustomFormPanel("Parameter Panel", getWindowNo());
		mainPanel.add(parameterPanel, BorderLayout.NORTH);

		final CPanel invoiceCandidatePanel = new CPanel();
		invoiceCandidatePanel.setLayout(new BorderLayout());
		mainPanel.add(invoiceCandidatePanel, BorderLayout.CENTER);
		mainPanel.add(allocationPanel, BorderLayout.SOUTH);

		// parameterPanel
		parameterPanel.setBorder(null);
		parameterPanel.putClientProperty(Options.NO_CONTENT_BORDER_KEY, Boolean.TRUE);

		final Properties ctx = getCtx();

		{
			bpartnerField = parameterPanel.newFormField()
					.setDisplayType(DisplayType.Search)
					.setColumnName(PROPERTY_C_BPartner_ID)
					.setHeader(msgBL.translate(ctx, PROPERTY_C_BPartner_ID))
					.setSameLine(true)
					.setMandatory(true)
					.setAD_Column_ID(3499)
					.setEditorListener(this)
					.add();
			componentList.add(bpartnerField);
		}

		{
			dateField = parameterPanel.newFormField()
					.setDisplayType(DisplayType.Date)
					.setColumnName(PROPERTY_Date)
					.setHeader(msgBL.getMsg(ctx, PROPERTY_Date))
					.setEditorListener(this)
					.add();
			componentList.add(dateField);
		}

		{
			organisationField = parameterPanel.newFormField()
					.setDisplayType(DisplayType.TableDir)
					.setHeader(msgBL.translate(ctx, PROPERTY_AD_Org_ID))
					.setColumnName(PROPERTY_AD_Org_ID)
					.setSameLine(true)
					.setMandatory(true)
					.setAD_Column_ID(839)
					.setEditorListener(this)
					.add();
			componentList.add(organisationField);
			organisationField.setValue(Env.getAD_Org_ID(ctx), false);
			setAD_Org_ID(Env.getAD_Org_ID(ctx));
		}

		{
			currencyField = parameterPanel.newFormField()
					.setDisplayType(DisplayType.TableDir)
					.setHeader(msgBL.translate(ctx, PROPERTY_C_Currency_ID))
					.setColumnName(PROPERTY_C_Currency_ID)
					.setSameLine(false)
					.setMandatory(true)
					.setAD_Column_ID(3505)
					.setEditorListener(this)
					.add();
			componentList.add(currencyField);
			currencyField.setValue(Env.getContextAsInt(ctx, "$C_Currency_ID"), false);
			setC_Currency_ID(Env.getContextAsInt(ctx, "$C_Currency_ID"));
		}

		{
			final GridField readPaymentDocumentButton = parameterPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setHeader(msgBL.getMsg(ctx, PROPERTY_ReadPaymentStringButton))
					.setColumnName(PROPERTY_ReadPaymentStringButton)
					.setEditorListener(this)
					.add();
			componentList.add(readPaymentDocumentButton);
		}

		{
			final GridField refreshButton = parameterPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setHeader(msgBL.getMsg(ctx, PROPERTY_RefreshButton))
					.setColumnName(PROPERTY_RefreshButton)
					.setEditorListener(this)
					.add();
			componentList.add(refreshButton);
		}

		// components
		final JLabel invoiceCandidateLabel = new JLabel();
		invoiceCandidateLabel.setRequestFocusEnabled(false);
		invoiceCandidateLabel.setText(msgBL.getMsg(ctx, MSG_PREFIX + "invoiceCandidate"));
		invoiceCandidatePanel.add(invoiceCandidateLabel, BorderLayout.NORTH);
		// invoiceCandidatePanel.add(controlPanel, Borderlayout.NORTH);

		//
		// Invoice candidates table
		final JXTable invoiceCandidatesTable = AnnotatedTableFactory.newInstance().create();
		invoiceCandidatesTable.setModel(invoiceCandidatesTableModel);
		invoiceCandidatesTableModel.addTableModelListener(this);
		//
		final JScrollPane invoiceCandidateScrollPane = new JScrollPane();
		invoiceCandidateScrollPane.setPreferredSize(new Dimension(200, 200));
		invoiceCandidateScrollPane.getViewport().add(invoiceCandidatesTable, null);
		// invoiceCandidateMiniTable.setColorCompare(BigDecimal.ZERO);
		// invoiceCandidateMiniTable.setColorColumn(INV_CAND_ColorColumn);
		invoiceCandidatePanel.add(invoiceCandidateScrollPane, BorderLayout.CENTER);

		// allocationPanel
		allocationPanel.setLayout(allocationLayout);

		// components
		paymentSumLabel.setText(msgBL.getMsg(ctx, PROPERTY_PaymentRequest_TotalPayments));
		paymentSumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		allocationPanel.add(paymentSumLabel, "growx, pushx");

		paymentSumField.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		paymentSumField.setEditable(false);
		paymentSumField.setHorizontalAlignment(SwingConstants.RIGHT);
		allocationPanel.add(paymentSumField, "growx, pushx"); // will be filled with every call of calculate()

		differenceLabel.setText(msgBL.getMsg(ctx, MSG_PREFIX + "difference"));
		differenceLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		allocationPanel.add(differenceLabel, "growx, pushx");

		differenceField.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		differenceField.setEditable(false);
		differenceField.setHorizontalAlignment(SwingConstants.RIGHT);
		allocationPanel.add(differenceField, "growx, pushx");

		invoiceSumLabel.setText(msgBL.getMsg(ctx, MSG_PREFIX + "totalInvoices"));
		invoiceSumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		allocationPanel.add(invoiceSumLabel, "growx, pushx");

		invoiceSumField.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		invoiceSumField.setEditable(false);
		invoiceSumField.setHorizontalAlignment(SwingConstants.RIGHT);
		allocationPanel.add(invoiceSumField, "growx, pushx");

		createInvoiceButton.setText(msgBL.getMsg(ctx, BUTTON_CREATE_INVOICE));
		createInvoiceButton.addActionListener(this);
		allocationPanel.add(createInvoiceButton, "cell 3 1, growx, pushx");

		createInvoiceCandidateButton.setText(msgBL.parseTranslation(ctx, BUTTON_CREATE_INVOICE_CANDIDATE));
		createInvoiceCandidateButton.addActionListener(this);
		allocationPanel.add(createInvoiceCandidateButton, "cell 5 1, growx, pushx");
	}

	/**
	 * Dispose
	 */
	@Override
	public void dispose()
	{
		if (m_frame != null)
		{
			m_frame.dispose();
		}
		m_frame = null;
	}

	/**
	 * Dynamic Init
	 */
	private void dynInit()
	{
		statusBar.setStatusLine(msgBL.getMsg(getCtx(), "AllocateStatus"));
		statusBar.setStatusDB("");
	}

	/**
	 * Action Listener. - MultiCurrency - Allocate
	 *
	 * @param e event
	 */
	@Override
	public final void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (final Exception ex)
		{
			clientUI.warn(getWindowNo(), ex);
		}
	}

	private final void actionPerformed0(final ActionEvent e) throws Exception
	{
		logger.info("");

		if (PROPERTY_RefreshButton.equals(e.getActionCommand()))
		{
			updateInfos(true);
		}
		else if (PROPERTY_ReadPaymentStringButton.equals(e.getActionCommand()))
		{
			readPaymentDocument();
		}
		else if (e.getSource().equals(createInvoiceButton))
		{
			enqueueSelection();
		}
		else if (e.getSource().equals(createInvoiceCandidateButton))
		{
			createNewInvoiceCandidate();
		}
	}

	/**
	 * NEVER save it. For each {@link I_C_Invoice} created from {@link #enqueueSelection()}, the template will be used to create a new {@link I_C_Payment_Request} entry.
	 */
	private I_C_Payment_Request paymentRequestTemplate = null;

	private final void readPaymentDocument()
	{
		final Properties ctx = getCtx();

		final ReadPaymentDocumentDialog readPaymentDocumentDialog = ReadPaymentDocumentDialog.create(ctx, m_frame, getAD_Org_ID());
		readPaymentDocumentDialog.setTitle(msgBL.getMsg(ctx, PROPERTY_ReadPaymentStringButton));

		final ReadPaymentDocumentPanel dialogComponent = readPaymentDocumentDialog.getDialogComponent();

		// gh #781: provide the invoice's bPartner so the panel can filter matching accounts by relevance
		dialogComponent.setContextBPartner_ID(getC_BPartner_ID());

		readPaymentDocumentDialog.pack();
		readPaymentDocumentDialog.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosed(final WindowEvent e)
			{
				paymentRequestTemplate = dialogComponent.getCreatedPaymentRequest();
				if (paymentRequestTemplate == null)
				{
					// dialog was cancelled => nothing to do
					return;
				}

				//
				// Set partner
				boolean reQuery = false;
				final org.compiere.model.I_C_BP_BankAccount bankAccount = paymentRequestTemplate.getC_BP_BankAccount();
				if (bankAccount != null)
				{
					reQuery = setC_BPartner_ID(bankAccount.getC_BPartner_ID()); // reQuery if partner was changed
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
				setTotalPayExisting(totalPay);

				//
				// Refresh data
				updateInfos(reQuery);

				final IPaymentString parsedPaymentString = dialogComponent.getParsedPaymentStringOrNull();
				if (parsedPaymentString == null)
				{
					return;
				}
				paymentRequestTemplate.setReference(parsedPaymentString.getReferenceNoComplete());
				paymentRequestTemplate.setFullPaymentString(parsedPaymentString.getRawPaymentString());
			}
		});

		//
		// Display in center & expand
		AEnv.showCenterScreen(readPaymentDocumentDialog);
	}

	private final void enqueueSelection()
	{
		final Properties ctx = getCtx();

		trxManager.run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName)
			{
				final List<I_C_Invoice_Candidate> candidates = getSelectedInvoiceCandidates();
				if (candidates.isEmpty())
				{
					throw new AdempiereException("@" + IInvoiceCandidateEnqueuer.MSG_INVOICE_GENERATE_NO_CANDIDATES_SELECTED_0P + "@");
				}

				//
				// Generate invoices
				final Timestamp dateInvoiced = SystemTime.asDayTimestamp();
				final PlainInvoicingParams invoicingParams = new PlainInvoicingParams();
				invoicingParams.setDateInvoiced(dateInvoiced);
				invoicingParams.setDateAcct(dateInvoiced);

				final boolean storeInvoicesInResult = true;
				final IInvoiceGenerateResult existingResult = invoiceCandBL.createInvoiceGenerateResult(storeInvoicesInResult);

				final IInvoiceGenerateResult result = invoiceCandBL.generateInvoices()
						.setContext(ctx, localTrxName)
						.setLoggable(LoggerLoggable.of(logger, Level.INFO))
						.setIgnoreInvoiceSchedule(true) // ignoring the schedule because we assume that the iterator will only contain appropriate candidates.
						.setCollector(existingResult)
						.setInvoicingParams(invoicingParams)
						.generateInvoices(candidates.iterator());
				//
				// Create payment requests for each given invoice out of the payment request template
				for (final I_C_Invoice invoice : result.getC_Invoices())
				{
					paymentRequestBL.createPaymentRequest(invoice, getPaymentRequestTemplate());
				}

				//
				// Re-validate all invoice candidates involved
				invoiceCandBL.updateInvalid()
						.setContext(ctx, localTrxName)
						.setTaggedWithAnyTag()
						.setOnlyC_Invoice_Candidates(candidates)
						.update();
			}
		});

		//
		// Reload invoice candidates
		updateInfos(true);
	}

	/**
	 * Side-effect: updates the selected candidates' DateInvoiced value
	 *
	 * @param ctx
	 * @param trxName
	 * @return selected rows of invoice candidates, with their DateInvoice beeing updated
	 */
	private final List<I_C_Invoice_Candidate> getSelectedInvoiceCandidates()
	{
		final Set<Integer> invoiceCandidateIds = invoiceCandidatesTableModel.getSelectedInvoiceCandidateIds();
		if (invoiceCandidateIds.isEmpty())
		{
			return Collections.emptyList();
		}

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addInArrayOrAllFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateIds);

		final Iterator<I_C_Invoice_Candidate> invoiceCandidatesIt = invoiceCandDAO.retrieveInvoiceCandidates(queryBuilder);
		final List<I_C_Invoice_Candidate> invoiceCandidates = Lists.newArrayList(invoiceCandidatesIt);

		return invoiceCandidates;
	}

	private void createNewInvoiceCandidate()
	{
		final int partnerId = getC_BPartner_ID();
		if (partnerId <= 0)
		{
			final AdempiereException ex = new AdempiereException("@Select@ @" + I_C_BPartner.COLUMNNAME_C_BPartner_ID + "@");
			throw ex;
		}

		final Properties ctx = getCtx();

		final String title = msgBL.translate(ctx, I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID);
		final CreateInvoiceCandidateDialog createInvoiceCandidateDialog = new CreateInvoiceCandidateDialog(m_frame, title, partnerId, getC_Currency_ID(), getDate());

		createInvoiceCandidateDialog.pack();
		createInvoiceCandidateDialog.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosed(final WindowEvent e)
			{
				//
				// Refresh data
				final boolean reQuery = createInvoiceCandidateDialog.hasCreatedInvoice();
				updateInfos(reQuery);
			}
		});

		//
		// Display in center & expand
		AEnv.showCenterScreen(createInvoiceCandidateDialog);

		//
		// Select freshly-created candidate
		final int createdInvoiceCandidateId = createInvoiceCandidateDialog.getC_Invoice_Candidate_ID();
		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				selectInvoiceCandidates(Collections.singleton(createdInvoiceCandidateId));
			}
		});
	}

	private final void selectInvoiceCandidates(final Collection<Integer> invoiceCandidateIds)
	{
		invoiceCandidatesTableModel.selectRowsByInvoiceCandidateIds(invoiceCandidateIds);
		updateInfos(false); // do not requery, just refresh all data
	}

	@Override
	public void tableChanged(final TableModelEvent e)
	{
		// Not a table update
		final boolean isUpdate = e.getType() == TableModelEvent.UPDATE;
		if (!isUpdate)
		{
			return;
		}

		updateInfos(false);
	}

	/**
	 * Vetoable Change Listener. - Business Partner - Currency - Date
	 *
	 * @param e event
	 */
	@Override
	public void vetoableChange(final PropertyChangeEvent e)
	{
		final String name = e.getPropertyName();
		final Object value = e.getNewValue();

		logger.info(name + "=" + value);

		if (value == null)
		{
			return;
		}

		for (final Object component : componentList)
		{
			if (component instanceof GridField)
			{
				final GridField field = (GridField)component;
				if (field.getColumnName().equals(name))
				{
					if (DisplayType.YesNo == field.getDisplayType())
					{
						field.setValue((Boolean)value ? "Y" : "N", false);
					}
					else
					{
						field.setValue(value, false);
					}
					break;
				}
			}
		}

		final boolean reloadTables;
		if (name.equals(organisationField.getColumnName())) // Organization
		{
			setAD_Org_ID(((Integer)value).intValue());
			reloadTables = true;
		}
		else if (name.equals(bpartnerField.getColumnName())) // BPartner
		{
			setC_BPartner_ID(((Integer)value).intValue());
			reloadTables = true;
		}
		else if (name.equals(currencyField.getColumnName())) // Currency
		{
			setC_Currency_ID(((Integer)value).intValue());
			reloadTables = true;
		}
		else
		{
			reloadTables = false; // default
		}

		updateInfos(reloadTables);
	}

	/**
	 * Updates Tables, Totals and Difference
	 *
	 * @param requery
	 */
	protected final void updateInfos(final boolean requery)
	{
		checkBPartner();

		setDate((Timestamp)dateField.getValue());
		setAD_Org_ID((Integer)organisationField.getValue());

		updateInvoiceCandidateRows(requery);

		// Calculate Totals and their Difference
		updateTotals();
	}

	private final void updateInvoiceCandidateRows(final boolean requery)
	{
		//
		// Retrieve the rows from database if asked.
		if (requery)
		{
			final PaymentAllocationContext context = createPaymentAllocationContext();

			final List<IInvoiceCandidateRow> rows = dao.retrieveInvoiceCandidateRows(context);
			invoiceCandidatesTableModel.setRows(rows);
		}

	}

	/**
	 * @returns the latest date of all selected invoice candidates.
	 */
	@Override
	protected final Date calculateAllocationDate()
	{
		return invoiceCandidatesTableModel.getLatestDocumentDateOfSelectedRows();
	}

	/**
	 * task 09643
	 * 
	 * @returns the latest accounting date of all selected invoice candidates.
	 */
	@Override
	protected final Date calculateDateAcct()
	{
		return invoiceCandidatesTableModel.getLatestDateAcctOfSelectedRows();
	}

	@Override
	protected final PaymentAllocationTotals getTotals()
	{
		final BigDecimal totalInvoicedAmt = invoiceCandidatesTableModel.getTotalNetAmtToInvoiceOfSelectedRows();

		return PaymentAllocationTotals.builder()
				.setInvoicedAmt(totalInvoicedAmt)
				.setPaymentExistingAmt(_totalPayExisting)
				.setPaymentCandidatesAmt(BigDecimal.ZERO)
				.build();
	}

	private final void setTotalPayExisting(final BigDecimal totalPayExisting)
	{
		this._totalPayExisting = totalPayExisting;
	}

	public I_C_Payment_Request getPaymentRequestTemplate()
	{
		return paymentRequestTemplate;
	}
}
