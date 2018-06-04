/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin *
 * Copyright (C) 2009 Idalica Corporation *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 *****************************************************************************/
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
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;
import org.compiere.apps.StatusBar;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.apps.search.Info;
import org.compiere.apps.search.InfoBuilder;
import org.compiere.grid.VPanel;
import org.compiere.grid.ed.VButton;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_Payment;
import org.compiere.swing.CPanel;
import org.compiere.swing.table.AnnotatedTableFactory;
import org.compiere.swing.table.AnnotatedTableModel;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXTable;

import com.jgoodies.looks.Options;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_PaySelectionLine;
import de.metas.banking.payment.IPaymentRequestBL;
import de.metas.banking.payment.IPaymentRequestDAO;
import de.metas.banking.payment.paymentallocation.model.AllocableDocType;
import de.metas.banking.payment.paymentallocation.model.IAllocableDocRow;
import de.metas.banking.payment.paymentallocation.model.IInvoiceRow;
import de.metas.banking.payment.paymentallocation.model.IPaymentRow;
import de.metas.banking.payment.paymentallocation.model.InvoiceWriteOffAmountType;
import de.metas.banking.payment.paymentallocation.model.InvoicesTableModel;
import de.metas.banking.payment.paymentallocation.model.PaymentAllocationTotals;
import de.metas.banking.payment.paymentallocation.model.PaymentsTableModel;
import de.metas.banking.payment.paymentallocation.service.IPayableDocument;
import de.metas.banking.payment.paymentallocation.service.IPaymentDocument;
import de.metas.banking.payment.paymentallocation.service.Inbound2OutboundPaymentAllocationBuilder;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder;
import de.metas.banking.payment.paymentallocation.service.WriteOffAmountTooBigPaymentAllocationException;
import de.metas.i18n.IMsgBL;
import de.metas.interfaces.I_C_BPartner;
import de.metas.payment.api.IPaymentBL;
import de.metas.payment.model.I_C_Payment_Request;
import de.metas.process.ProcessInfo;

public class PaymentAllocationForm
		extends AbstractPaymentAllocationForm
		implements FormPanel, ActionListener, TableModelListener, VetoableChangeListener
{
	// Services
	private final IPaymentRequestDAO paymentRequestDAO = Services.get(IPaymentRequestDAO.class);
	private final IPaymentRequestBL paymentRequestBL = Services.get(IPaymentRequestBL.class);
	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	/**
	 * @task http://dewiki908/mediawiki/index.php/09558_Zahlung-Zuordnung_Sperre_raus_f%C3%BCr_G1093_%28109678726752%29
	 */
	private final String SPECIALVENDORID_ALLOWMULTIPLEDOCSALLOCATION_PREFIX = "de.metas.banking.SpecialVendorID_";

	/** This AD_Form_ID */
	public static final int AD_FORM_ID = 104;

	private static final String PROPERTY_C_BPartner_ID = "C_BPartner_ID";
	private static final String PROPERTY_Date = "Date";
	private static final String PROPERTY_AD_Org_ID = "AD_Org_ID";
	private static final String PROPERTY_C_Currency_ID = "C_Currency_ID";
	private static final String PROPERTY_RefreshButton = "Refresh";
	private final List<Object> componentList = new ArrayList<>();

	private static final String ERR_PaymentAllocationForm_WriteOffPayments_NoAllocation = "PaymentAllocationForm_WriteOffPayments_NoAllocation";

	// sys config vars
	private static final String SYSCONFIG_HideIsmultiCurrencyField = "PaymentAllocation.HideIsmultiCurrencyField";

	// private static final String HEADER_PAYMENT_INVOICE_CANDIDATE_FORM = "ReadPaymentForm";
	private static final int ReadPaymentForm_ID = 540056;

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

		// Avoid having an IsSOTrx to not collide with existing validation rules
		Env.setContext(getCtx(), WindowNo, "IsSOTrx", "X");

		try
		{
			dynInit();
			jbInit();

			updateTotals();

			frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
			frame.getContentPane().add(statusBar, BorderLayout.SOUTH);

			//
			// If this form was started from C_PaySelectionLine then preset the BPartner. (09027)
			final ProcessInfo processInfo = frame.getProcessInfo();
			if (processInfo != null)
			{
				final I_C_PaySelectionLine paySelectionLine = processInfo.getRecordIfApplies(I_C_PaySelectionLine.class, ITrx.TRXNAME_None).orElse(null);
				if (paySelectionLine != null)
				{
					final int bpartnerId = paySelectionLine.getC_BPartner_ID();
					if (bpartnerId > 0)
					{
						setC_BPartner_ID(bpartnerId);
						refresh();
					}
				}
			}
		}
		catch (final Exception e)
		{
			logger.error("", e);
		}
	}

	//
	// UI
	private final CPanel mainPanel = new CPanel();
	private final JButton allocateButton = new JButton();
	private final JButton writeOffPaymentButton = new JButton();
	private final JXTable paymentTable = AnnotatedTableFactory.newInstance().create();
	private final JXTable invoiceTable = AnnotatedTableFactory.newInstance().create();

	// controlPanel
	private GridField addForeignInvoice = null;
	private GridField addForeignPayment = null;
	private GridField addForeignPrePayOrder = null;
	private GridField createAllocateOutboundPayment = null;
	private GridField createUpdatePaySelection = null;
	private GridField assignPaymentReference = null; // 08129
	private VButton assignPaymentReferenceButton = null; // 08129

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

		final JSplitPane infoPanel = new JSplitPane();
		mainPanel.add(infoPanel, BorderLayout.CENTER);
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
					.setSameLine(false)
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
					.setSameLine(true)
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
		// hide multicurrency flag
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_HideIsmultiCurrencyField, true))
		{
			final GridField multiCurrencyField = parameterPanel.newFormField()
					.setDisplayType(DisplayType.YesNo)
					.setHeader(msgBL.getMsg(ctx, PROPERTY_MultiCurrency))
					.setColumnName(PROPERTY_MultiCurrency)
					.setSameLine(true)
					.setEditorListener(this)
					.add();
			componentList.add(multiCurrencyField);
			multiCurrencyField.setValue(DisplayType.toBooleanString(isMultiCurrency()), false);
		}
		{
			final GridField readPaymentDocumentButton = parameterPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setHeader(msgBL.getMsg(ctx, ReadPaymentDocumentPanel.HEADER_READ_PAYMENT_STRING))
					.setColumnName(ReadPaymentDocumentPanel.HEADER_READ_PAYMENT_STRING)
					.setSameLine(true)
					.setEditorListener(this)
					.add();
			componentList.add(readPaymentDocumentButton);
		}
		{
			final GridField refreshButton = parameterPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setHeader(msgBL.getMsg(ctx, PROPERTY_RefreshButton))
					.setColumnName(PROPERTY_RefreshButton)
					.setSameLine(true)
					.setEditorListener(this)
					.add();
			componentList.add(refreshButton);
		}
		{
			parameterPanel.newFormField()
					.setDisplayType(DisplayType.Search)
					.setHeader(msgBL.translate(ctx, "C_Payment_ID"))
					.setColumnName("C_Payment_ID")
					.setSameLine(false)
					.setMandatory(false)
					.setEditorListener(new VetoableChangeListener()
					{

						@Override
						public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
						{
							final int paymentId = evt.getNewValue() == null ? -1 : (Integer)evt.getNewValue();
							onUserChangedFilterPaymentId(paymentId);
						}
					})
					.add();
		}
		{
			parameterPanel.newFormField()
					.setDisplayType(DisplayType.String)
					.setHeader(msgBL.translate(ctx, "POReference"))
					.setColumnName("POReference")
					.setSameLine(true)
					.setMandatory(false)
					.setEditorListener(new VetoableChangeListener()
					{

						@Override
						public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException
						{
							final String poReference = evt.getNewValue() == null ? null : evt.getNewValue().toString();
							onUserChangedFilterPOReference(poReference);
						}
					})
					.add();
		}

		// infoPanel
		// infoPanel.setLayout(infoLayout);
		infoPanel.setOrientation(JSplitPane.VERTICAL_SPLIT);
		infoPanel.setBorder(BorderFactory.createLineBorder(panel.getBackground(), 1));
		infoPanel.setContinuousLayout(true);
		infoPanel.setResizeWeight(0.5);
		infoPanel.setPreferredSize(new Dimension(1001, 400));
		infoPanel.setDividerSize(10);

		final CPanel infoTopPanel = new CPanel();
		final CPanel infoBottomPanel = new CPanel();
		infoPanel.add(infoTopPanel, JSplitPane.TOP);
		infoPanel.add(infoBottomPanel, JSplitPane.BOTTOM);

		// infoTopPanel
		final BorderLayout infoTopLayout = new BorderLayout();
		infoTopPanel.setLayout(infoTopLayout);
		final CPanel paymentPanel = new CPanel();
		infoTopPanel.add(paymentPanel, BorderLayout.CENTER);
		// infoTopPanel.add(controlPanel, BorderLayout.SOUTH);

		// infoBottomPanel
		final BorderLayout infoBottomLayout = new BorderLayout();
		infoBottomPanel.setLayout(infoBottomLayout);

		final CPanel invoicePanel = new CPanel();
		infoBottomPanel.add(invoicePanel, BorderLayout.CENTER);
		infoBottomPanel.add(allocationPanel, BorderLayout.SOUTH);

		// paymentPanel
		final BorderLayout paymentLayout = new BorderLayout();
		paymentPanel.setLayout(paymentLayout);

		// components
		JLabel paymentLabel = new JLabel();
		paymentLabel.setRequestFocusEnabled(false);
		paymentLabel.setText(msgBL.getMsg(ctx, MSG_PREFIX + "payment"));
		paymentPanel.add(paymentLabel, BorderLayout.NORTH);

		final JScrollPane paymentScrollPane = new JScrollPane();
		paymentScrollPane.setPreferredSize(new Dimension(200, 200));
		paymentScrollPane.getViewport().add(paymentTable, null);

		paymentPanel.add(paymentScrollPane, BorderLayout.CENTER);

		// invoicePanel
		final BorderLayout invoiceLayout = new BorderLayout();
		invoicePanel.setLayout(invoiceLayout);

		// components
		final JLabel invoiceLabel = new JLabel();
		invoiceLabel.setRequestFocusEnabled(false);
		invoiceLabel.setText(msgBL.getMsg(ctx, MSG_PREFIX + "invoice"));
		invoicePanel.add(invoiceLabel, BorderLayout.NORTH);
		// invoicePanel.add(controlPanel, Borderlayout.NORTH);

		final JScrollPane invoiceScrollPane = new JScrollPane();
		invoiceScrollPane.setPreferredSize(new Dimension(200, 200));
		invoiceScrollPane.getViewport().add(invoiceTable, null);
		invoicePanel.add(invoiceScrollPane, BorderLayout.CENTER);

		//
		// controlPanel
		final VPanel controlPanel = VPanel.newCustomFormPanel("Control Panel", Env.WINDOW_None);
		controlPanel.setBorder(null);
		controlPanel.putClientProperty(Options.NO_CONTENT_BORDER_KEY, Boolean.TRUE);
		infoTopPanel.add(controlPanel, BorderLayout.SOUTH);

		{
			addForeignPayment = controlPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setHeader(msgBL.getMsg(ctx, MSG_PREFIX + "foreignPayments"))
					.setColumnName("addForeignPayment")
					.setSameLine(true)
					.setEditorListener(this)
					.add();
			componentList.add(addForeignPayment);
		}

		{
			addForeignInvoice = controlPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setHeader(msgBL.getMsg(ctx, MSG_PREFIX + "foreignInvoices"))
					.setColumnName("addForeignInvoice")
					.setSameLine(true)
					.setEditorListener(this)
					.add();
			componentList.add(addForeignInvoice);
		}

		{
			addForeignPrePayOrder = controlPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setHeader(msgBL.getMsg(ctx, MSG_PREFIX + "foreignPrePayOrders"))
					.setColumnName("addForeignPrePayOrder")
					.setSameLine(true)
					.setEditorListener(this)
					.add();
			componentList.add(addForeignPrePayOrder);
		}

		//
		// Create WriteOff checkboxes
		boolean firstWriteOffCheckbox = true;
		for (final InvoiceWriteOffAmountType type : InvoiceWriteOffAmountType.values())
		{
			final VetoableChangeListener listener = new VetoableChangeListener()
			{
				@Override
				public void vetoableChange(final PropertyChangeEvent evt) throws PropertyVetoException
				{
					final boolean allowed = DisplayType.toBoolean(evt.getNewValue());
					onAllowWriteOffFlagChanged(type, allowed);

					final boolean reloadTables = false;
					updateInfos(reloadTables);
				}
			};
			final GridField gridField = controlPanel.newFormField()
					.setDisplayType(DisplayType.YesNo)
					.setColumnName(type.name())
					.setHeader(msgBL.getMsg(ctx, type.getAD_Message()))
					.setSameLine(!firstWriteOffCheckbox)
					.setEditorListener(listener)
					.add();
			gridField.setValue(DisplayType.toBooleanString(isAllowWriteOffAmountOfType(type)), false);
			firstWriteOffCheckbox = false;
		}

		// 03678
		{
			createAllocateOutboundPayment = controlPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setColumnName("createAllocateOutboundPayment")
					.setHeader(msgBL.getMsg(ctx, MSG_PREFIX + "createAllocateOutboundPayment"))
					.setSameLine(false)
					.setEditorListener(this)
					.add();
			componentList.add(createAllocateOutboundPayment);
		}

		{
			createUpdatePaySelection = controlPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setColumnName("createUpdatePaySelection")
					.setHeader(msgBL.getMsg(ctx, MSG_PREFIX + "createUpdatePaySelection"))
					.setSameLine(true)
					.setEditorListener(this)
					.add();
			componentList.add(createUpdatePaySelection);
		}

		// Assign Payment Reference to selected Invoice (08129)
		{
			assignPaymentReference = controlPanel.newFormField()
					.setDisplayType(DisplayType.Button)
					.setColumnName("assignPaymentReference")
					.setHeader("") // will be updated later
					.setSameLine(true)
					.setEditorListener(this)
					.add();
			assignPaymentReferenceButton = (VButton)controlPanel.getEditor(assignPaymentReference.getColumnName());
			updateAssignPaymentReferenceButton();
			componentList.add(assignPaymentReference);
		}

		// allocationPanel
		allocationPanel.setLayout(allocationLayout);

		// components
		paymentCandidateSumLabel.setText(msgBL.getMsg(ctx, IInvoiceRow.PROPERTY_PaymentRequestAmt));
		paymentCandidateSumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		allocationPanel.add(paymentCandidateSumLabel, "growx, pushx");

		paymentCandidateSumField.setBackground(AdempierePLAF.getFieldBackground_Inactive());
		paymentCandidateSumField.setEditable(false);
		paymentCandidateSumField.setHorizontalAlignment(SwingConstants.RIGHT);
		allocationPanel.add(paymentCandidateSumField, "growx, pushx"); // will be filled after ReadPaymentForm (window Einlesen Zahlschein) is closed

		paymentSumLabel.setText(msgBL.getMsg(ctx, MSG_PREFIX + "totalPayments"));
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

		allocateButton.setText(msgBL.getMsg(ctx, "Process"));
		allocateButton.addActionListener(this);
		allocationPanel.add(allocateButton, "cell 5 1, growx, pushx");

		writeOffPaymentButton.setText(msgBL.getMsg(ctx, "WriteOffPayment"));
		writeOffPaymentButton.addActionListener(this);
		allocationPanel.add(writeOffPaymentButton, "cell 3 1, growx, pushx");
	}

	/**
	 * Dispose
	 */
	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		if (m_frame != null)
		{
			m_frame.dispose();
		}
		m_frame = null;
	}

	private final boolean isDisposed()
	{
		return m_frame == null;
	}

	/**
	 * Dynamic Init
	 */
	protected final void dynInit() throws Exception
	{
		//
		// Payments table
		final PaymentsTableModel paymentsTableModel = getPaymentsTableModel();
		paymentTable.setModel(paymentsTableModel);
		paymentsTableModel.addTableModelListener(this);

		//
		// Invoices table
		final InvoicesTableModel invoicesTableModel = getInvoicesTableModel();
		invoiceTable.setModel(invoicesTableModel);
		invoicesTableModel.addTableModelListener(this);

		//
		// Status bar
		final Properties ctx = getCtx();
		statusBar.setStatusLine(msgBL.getMsg(ctx, "AllocateStatus"));
		statusBar.setStatusDB("");
	}

	/**************************************************************************
	 * Action Listener. - MultiCurrency - Allocate
	 *
	 * @param e event
	 */
	@Override
	public void actionPerformed(final ActionEvent e)
	{
		try
		{
			actionPerformed0(e);
		}
		catch (final Exception ex)
		{
			clientUI.error(getWindowNo(), ex);
		}
	}

	private void actionPerformed0(final ActionEvent e)
	{
		logger.info("");

		if (e.getActionCommand().equals(addForeignInvoice.getColumnName()))
		{
			addForeignInvoice();
		}
		else if (e.getActionCommand().equals(addForeignPayment.getColumnName()))
		{
			addForeignPayment();
		}
		else if (e.getActionCommand().equals(addForeignPrePayOrder.getColumnName()))
		{
			addForeignPrePayOrder();
		}
		else if (e.getActionCommand().equalsIgnoreCase(createAllocateOutboundPayment.getColumnName()))
		{
			createAllocateOutboundPayment();
		}
		else if (e.getActionCommand().equalsIgnoreCase(createUpdatePaySelection.getColumnName()))
		{
			createUpdatePaySelection();
		}
		//
		// Assign current Payment Reference to selected Invoice (08129)
		else if (e.getActionCommand().equalsIgnoreCase(assignPaymentReference.getColumnName()))
		{
			assignPaymentReferenceToSelectedInvoice();
		}
		//
		// 08296: Functionality not used as it was originally intended; instead, they wish to directly read the Payment string and use it's reference
		//
		// else if (e.getActionCommand().equals(readPaymentFormButton.getColumnName()))
		// {
		// openReadPaymentForm();
		// }
		//
		// ...instead, read payment string directly from it's dialog
		//
		else if (ReadPaymentDocumentPanel.HEADER_READ_PAYMENT_STRING.equals(e.getActionCommand()))
		{
			openReadPaymentDocumentDialog();
		}
		else if (PROPERTY_RefreshButton.equals(e.getActionCommand()))
		{
			refresh();
		}
		else if (e.getSource().equals(allocateButton))
		{
			allocate();
		}
		else if (e.getSource().equals(writeOffPaymentButton))
		{
			writeOffPayment();
		}
		else
		{
			// nothing (silently skip)
		}
	}

	/**
	 * writeOff selected selected payments.
	 */
	private void writeOffPayment()
	{
		writeOffPaymentButton.setEnabled(false);
		m_frame.setBusy(true);
		try
		{
			final List<IPaymentRow> paymentRows = getPaymentRowsSelected();
			for (final IPaymentRow row : paymentRows)
			{
				final I_C_Payment payment = InterfaceWrapperHelper.create(getCtx(), row.getC_Payment_ID(), I_C_Payment.class, ITrx.TRXNAME_ThreadInherited);
				final BigDecimal amtToWriteOff = row.getDiscountAmt();
				final BigDecimal openAmt = row.getOpenAmtConv();
				if (amtToWriteOff.abs().compareTo(openAmt.abs()) > 0)
				{
					throw new WriteOffAmountTooBigPaymentAllocationException(row.getDocumentNo());
				}
				final Date allocDateTrx = getDate();
				paymentBL.paymentWriteOff(payment, amtToWriteOff, allocDateTrx);
			}

			// Re-query Invoices and Payments table, because our rows were changed for sure:
			// * open amount could be changed because we allocated
			// * applied amount is changed by the builder
			refresh();
		}
		finally
		{
			m_frame.setBusy(false);
			writeOffPaymentButton.setEnabled(true);
		}
	}

	/**
	 * Check if the vendor is allowed to allocate multiple documents in one step .<br>
	 * Generally, a vendor can not allocate more then one document per type.<br>
	 * But can we have a vendor exception from this rule.
	 *
	 * @return
	 * @task http://dewiki908/mediawiki/index.php/09558_Zahlung-Zuordnung_Sperre_raus_f%C3%BCr_G1093_%28109678726752%29
	 */
	private boolean isAllowOnlyOneVendorDoc()
	{
		final Map<String, String> bpartnerIDsMap = sysConfigBL.getValuesForPrefix(SPECIALVENDORID_ALLOWMULTIPLEDOCSALLOCATION_PREFIX, Env.getAD_Client_ID(getCtx()), 0);
		final List<String> bpartnerIDs = new ArrayList<>(new TreeMap<>(bpartnerIDsMap).values());

		if (bpartnerIDs.size() == 0 || !bpartnerIDs.contains(Integer.toString(getC_BPartner_ID())))
		{
			return true;
		}

		return false;
	}

	/**
	 * Allocate selected invoices and selected payments.
	 */
	private void allocate()
	{
		final PaymentAllocationTotals totals = getTotals();
		if (totals.hasDifferences())
		{
			throw new AdempiereException("@" + MSG_PREFIX + "totalsNotEqual@");
		}

		allocateButton.setEnabled(false);
		m_frame.setBusy(true);
		try
		{
			// FRESH-523: In case some of the selected payment lines have a discount set (are to be written off) the user is not allowed to allocate them if no invoice lines are selected
			boolean isWriteOff = false;

			if (getInvoiceRowsSelectedCount() <= 0)
			{
				for (final IPaymentRow payment : getPaymentRowsSelected())
				{
					if (payment.getDiscountAmt().signum() != 0)
					{
						isWriteOff = true;

						break;
					}
				}
			}

			if (isWriteOff)
			{
				final String errorMsg = Services.get(IMsgBL.class).getMsg(getCtx(), ERR_PaymentAllocationForm_WriteOffPayments_NoAllocation);
				throw new AdempiereException("@" + MSG_PREFIX + "@" + errorMsg);
			}

			final List<IPayableDocument> payableDocuments = new ArrayList<>();
			for (final IInvoiceRow invoice : getInvoiceRowsSelected())
			{
				payableDocuments.add(invoice.copyAsPayableDocument());
			}

			final List<IPaymentDocument> paymentDocuments = new ArrayList<>();
			for (final IPaymentRow payment : getPaymentRowsSelected())
			{
				paymentDocuments.add(payment.copyAsPaymentDocument());
			}

			// Build the allocation & update the status
			final Properties ctx = getCtx();
			final int adOrgId = getAD_Org_ID();
			final String summary = PaymentAllocationBuilder.newBuilder()
					.setCtx(ctx)
					.setC_Currency_ID(getC_Currency_ID())
					.setAD_Org_ID(adOrgId)
					.setPayableDocuments(payableDocuments)
					.setPaymentDocuments(paymentDocuments)
					.setAllowOnlyOneVendorDoc(isAllowOnlyOneVendorDoc())
					.setDateTrx(getAllocDate()) // task it_09643. Use allocation Date (transaction date)
					.setDateAcct(getDateAcct()) // separate transaction date from date acct
					.build();
			statusBar.setStatusLine(summary);

			// Re-query Invoices and Payments table, because our rows were changed for sure:
			// * open amount could be changed because we allocated
			// * applied amount is changed by the builder
			refresh();
		}
		finally
		{
			m_frame.setBusy(false);
			allocateButton.setEnabled(true);
		}
	}

	private void addForeignDocumentIds(final AllocableDocType docType, final InfoBuilder infoBuilder)
	{
		//
		// Create and show the Info window
		final Info info = infoBuilder
				.setParentFrame(Env.getFrame(m_frame))
				.setWindowNo(getWindowNo())
				.setModal(true)
				.setMultiSelection(true)
				.buildAndShow();
		if (!info.isOkPressed())
		{
			return;
		}

		//
		// Add selected document IDs
		final Object[] idsArray = info.getSelectedKeys();
		addForeignDocumentIds(docType, idsArray);

		// We need a full requery...
		refresh();
	}

	private void addForeignInvoice()
	{
		// Create the Info window
		final String whereClause = "IsPaid='N' AND C_BPartner_ID <> " + getC_BPartner_ID();
		addForeignDocumentIds(AllocableDocType.Invoice, InfoBuilder.newBuilder()
				.setTableName(I_C_Invoice.Table_Name)
				.setWhereClause(whereClause));

	}

	private void addForeignPayment()
	{
		// Create the Info window
		final String whereClause = "IsAllocated='N' AND PayAmt <> 0";
		addForeignDocumentIds(AllocableDocType.Payment, InfoBuilder.newBuilder()
				.setTableName(I_C_Payment.Table_Name)
				.setWhereClause(whereClause));
	}

	private void addForeignPrePayOrder()
	{
		// Create the Info window
		final String whereClause = "DocStatus = 'WP'";
		addForeignDocumentIds(AllocableDocType.PrepayOrder, InfoBuilder.newBuilder()
				.setTableName(I_C_Order.Table_Name)
				.setWhereClause(whereClause));

	}

	private void createAllocateOutboundPayment()
	{
		// Make sure all editing on payments table is stopped
		if (paymentTable.isEditing())
		{
			paymentTable.getCellEditor().stopCellEditing();
		}

		final boolean paymentsCreated = new Inbound2OutboundPaymentAllocationBuilder()
				.setCtx(getCtx())
				.setDate(getAllocDate())
				.addInboundPayments(getPaymentRowsSelected())
				.build();

		if (paymentsCreated)
		{
			refresh();
		}
	}

	/**
	 * Open a pop-up to select {@link I_C_PaySelection} & let the form do the rest
	 */
	private void createUpdatePaySelection()
	{
		final List<I_C_Invoice> selectedInvoices = getSelectedInvoices();
		if (selectedInvoices.isEmpty())
		{
			throw new AdempiereException("@Select@ @" + org.compiere.model.I_C_Invoice.Table_Name + "@");
		}

		final Properties ctx = getCtx();
		final String dialogTitle = msgBL.parseTranslation(ctx, "@Select@ @" + I_C_PaySelection.Table_Name + "@");

		final I_C_BPartner selectedPartner = InterfaceWrapperHelper.create(ctx, getC_BPartner_ID(), I_C_BPartner.class, ITrx.TRXNAME_None);
		final SelectPaySelectionDialog selectPaySelectionDialog = new SelectPaySelectionDialog(m_frame,
				dialogTitle,
				selectedPartner,
				selectedInvoices,
				getC_Currency_ID(),
				getPaymentReference());
		selectPaySelectionDialog.pack();

		//
		// Display in center & expand
		AEnv.showCenterScreen(selectPaySelectionDialog);
	}

	/**
	 * Assign current Payment Reference to selected Invoice.
	 *
	 * @task http://dewiki908/mediawiki/index.php/08129_Referenznummer_speichern_zu_Eingangsrechnung_%28100097741294%29
	 */
	private void assignPaymentReferenceToSelectedInvoice()
	{
		//
		// Get the payment request
		final I_C_Payment_Request paymentRequestTemplate = getPaymentRequestTemplate();
		if (paymentRequestTemplate == null)
		{
			throw new AdempiereException("@" + MSG_PREFIX + "SelectPaymentRequestFirstException" + "@");
		}

		//
		// Get the selected invoice
		final List<I_C_Invoice> invoices = getSelectedInvoices();
		if (invoices == null || invoices.isEmpty())
		{
			// nothing to do
			return;
		}
		if (invoices.size() != 1)
		{
			throw new AdempiereException("@" + MSG_PREFIX + "OnlyOneSelectedInvoiceAllowedException" + "@");
		}
		final I_C_Invoice invoice = invoices.get(0);
		if (paymentRequestDAO.hasPaymentRequests(invoice))
		{
			throw new AdempiereException("@" + MSG_PREFIX + "PaymentRequestForInvoiceAlreadyExistsException" + "@");
		}

		//
		// Create the payment request
		final I_C_Payment_Request paymentRequest = paymentRequestBL.createPaymentRequest(invoice, paymentRequestTemplate);
		_paymentRequestIdsCreated.add(paymentRequest.getC_Payment_Request_ID());
	}

	/**
	 * @return selected invoices from the invoice table
	 */
	private List<I_C_Invoice> getSelectedInvoices()
	{
		final Properties ctx = getCtx();
		final String trxName = ITrx.TRXNAME_None;

		final List<I_C_Invoice> selectedInvoices = new ArrayList<>();
		for (final IInvoiceRow row : getInvoiceRowsSelected())
		{
			final int invoiceId = row.getC_Invoice_ID();

			final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, invoiceId, I_C_Invoice.class, trxName);
			selectedInvoices.add(invoice);
		}
		return selectedInvoices;
	}

	private int getInvoiceRowsSelectedCount()
	{
		return getInvoiceRowsSelected().size();
	}

	/**
	 * Table Model Listener. - Recalculate Totals
	 *
	 * @param e event
	 */
	@Override
	public void tableChanged(final TableModelEvent e)
	{
		// Not a table update
		final boolean isUpdate = e.getType() == TableModelEvent.UPDATE;
		if (!isUpdate)
		{
			return;
		}

		final int modelColumnIndex = e.getColumn();
		if (modelColumnIndex == TableModelEvent.ALL_COLUMNS || modelColumnIndex < 0)
		{
			// i.e. all columns were changed => mainly this event is triggered by "fireTableDataChanged"
			// => we ignore it because we want to bind on events triggered by user when he/she is changing a column.
			return;
		}

		//
		// Extract the row index that was changed
		if (e.getFirstRow() != e.getLastRow())
		{
			// Multi-row update => we ignore this event because we want to intercept a particular cell update
			return;
		}
		final int modelRowIndex = e.getFirstRow();

		//
		// Get the right table model which was changed
		final AnnotatedTableModel<? extends IAllocableDocRow> model;
		if (Check.equals(e.getSource(), getInvoicesTableModel()))
		{
			model = getInvoicesTableModel();
		}
		else if (Check.equals(e.getSource(), getPaymentsTableModel()))
		{
			model = getPaymentsTableModel();
		}
		else
		{
			return;
		}

		// Ignore this event if we don't have rows
		if (model.getRowCount() <= 0)
		{
			return;
		}

		//
		// Call "onTableRowChanged"
		final IAllocableDocRow row = model.getRow(modelRowIndex);
		final String columnName = model.getColumnNameByColumnIndex(modelColumnIndex);
		onTableRowChanged(row, columnName);

		//
		// Because the method above could perform some changes in our model we shall update
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

		if (value == null)
		{
			return;
		}

		for (final Object component : componentList)
		{
			if (component instanceof GridField)
			{
				final GridField field = (GridField)component;
				if (Check.equals(field.getColumnName(), name))
				{
					if (DisplayType.YesNo == field.getDisplayType())
					{
						field.setValue(DisplayType.toBoolean(value), false);
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
		if (name.equals(organisationField.getColumnName()))  // Organization
		{
			setAD_Org_ID(((Integer)value).intValue());
			reloadTables = true; // re-query rows because our filtering criteria was changed and we could get more/less rows now
		}
		else if (name.equals(bpartnerField.getColumnName()))  // BPartner
		{
			setC_BPartner_ID(((Integer)value).intValue());
			reloadTables = true; // re-query rows because our filtering criteria was changed and we could get more/less rows now
		}
		else if (name.equals(currencyField.getColumnName()))  // Currency
		{
			setC_Currency_ID(((Integer)value).intValue());
			reloadTables = true; // re-query rows because our filtering criteria was changed and we could get more/less rows now
		}
		else if (PROPERTY_MultiCurrency.equals(name))
		{
			setIsMultiCurrency(DisplayType.toBoolean(value));
			reloadTables = true; // re-query rows because our filtering criteria was changed and we could get more/less rows now
		}
		else
		{
			reloadTables = false; // default
		}

		updateInfos(reloadTables);
	}

	private final void onUserChangedFilterPaymentId(final int paymentId)
	{
		// Set model's filter by Payment_ID
		final boolean changed = setFilter_Payment_ID(paymentId);
		if (!changed)
		{
			return;
		}

		//
		// Update the other filtering fields (based on current payment if any)
		final I_C_Payment payment;
		if (paymentId > 0)
		{
			payment = InterfaceWrapperHelper.create(getCtx(), paymentId, I_C_Payment.class, ITrx.TRXNAME_None);
		}
		else
		{
			payment = null;
		}
		if (payment != null)
		{
			setAD_Org_ID(payment.getAD_Org_ID());
			setC_BPartner_ID(payment.getC_BPartner_ID());
			setC_Currency_ID(payment.getC_Currency_ID());
			setDate(payment.getDateTrx());
		}

		//
		// Reload everything (including payment and invoice rows)
		refresh();
	}

	private final void onUserChangedFilterPOReference(final String poReference)
	{
		final boolean changed = setFilter_POReference(poReference);
		if (!changed)
		{
			return;
		}

		//
		// Reload
		// NOTE: don't refresh automatically because that would be to resource intensive
		// final boolean reloadTables = true;
		// updateInfos(reloadTables);
	}

	/**
	 * Open Payment Invoice Candidate Form<br>
	 * 08296: Functionality not used as it was originally intended; instead, they wish to directly read the Payment string and use it's reference
	 */
	@SuppressWarnings("unused")
	private void openReadPaymentForm()
	{
		final I_AD_Form form = InterfaceWrapperHelper.create(getCtx(), ReadPaymentForm_ID, I_AD_Form.class, ITrx.TRXNAME_None);

		//
		// Create window and populate it with the frame
		final FormFrame readPaymentFormFrame = AEnv.createForm(form);
		Check.assumeNotNull(readPaymentFormFrame, "readPaymentFormFrame not null");

		//
		// Copy configuration from formPanel when closing it
		final ReadPaymentForm readPaymentForm = readPaymentFormFrame.getFormPanel(ReadPaymentForm.class);
		readPaymentForm.setC_BPartner_ID(getC_BPartner_ID());
		readPaymentForm.setAD_Org_ID(getAD_Org_ID());
		readPaymentForm.setC_Currency_ID(getC_Currency_ID());
		readPaymentForm.setDate(getDate());

		readPaymentFormFrame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosed(final WindowEvent e)
			{
				final PaymentAllocationTotals totals = readPaymentForm.getTotals();
				final BigDecimal totalPayCand = totals.getPaymentExistingAmt().negate(); // ALWAYS negate; this sum is amount to be paid
				setTotalPaymentCandidatesAmt(totalPayCand);
				setPaymentReference(readPaymentForm.getPaymentReference());

				final boolean requery = false;
				updateInfos(requery);
			}
		});

		//
		// Update information in the ReadPaymentForm
		final boolean reloadTables = true;
		readPaymentForm.updateInfos(reloadTables);

		AEnv.showCenterWindowModal(readPaymentFormFrame, m_frame, new Runnable()
		{
			@Override
			public void run()
			{
				updateFromReadPaymentForm(readPaymentForm);
			}
		});
	}

	private final void updateFromReadPaymentForm(final ReadPaymentForm readPaymentForm)
	{
		if (isDisposed())
		{
			return; // don't update if the window was somehow closed
		}

		//
		// Copy configuration from formPanel when closing it
		setC_BPartner_ID(readPaymentForm.getC_BPartner_ID());
		setAD_Org_ID(readPaymentForm.getAD_Org_ID());
		setC_Currency_ID(readPaymentForm.getC_Currency_ID());
		setDate(readPaymentForm.getDate());
		setPaymentRequestTemplate(readPaymentForm.getPaymentRequestTemplate());

		//
		// Update information
		refresh();
	}

	private void openReadPaymentDocumentDialog()
	{
		final ReadPaymentDocumentDialog readPaymentDocumentDialog = ReadPaymentDocumentDialog.create(getCtx(), m_frame, getAD_Org_ID());
		final ReadPaymentDocumentPanel readPaymentDocumentPanel = readPaymentDocumentDialog.getDialogComponent();

		// gh #781: provide the invoice's bPartner so the panel can filter matching accounts by relevance
		readPaymentDocumentPanel.setContextBPartner_ID(getC_BPartner_ID());

		readPaymentDocumentDialog.addWindowListener(new ReadPaymentDialogWindowAdapter(readPaymentDocumentPanel)
		{
			@Override
			protected void onResult(final ReadPaymentPanelResult result)
			{
				updateFromReadPaymentPanelResult(result);
			}

			@Override
			protected boolean isParentWindowDisposed()
			{
				return PaymentAllocationForm.this.isDisposed();
			}
		});

		//
		// Display in center & expand
		AEnv.showCenterScreen(readPaymentDocumentDialog);
	}

	private final void updateFromReadPaymentPanelResult(final ReadPaymentPanelResult result)
	{
		if (result.getC_BPartner_ID() > 0)
		{
			PaymentAllocationForm.this.setC_BPartner_ID(result.getC_BPartner_ID());
		}

		final BigDecimal totalPaymentCandidatesAmt = result.getTotalPaymentCandidatesAmt().negate(); // ALWAYS negate; this sum is amount to be paid
		setTotalPaymentCandidatesAmt(totalPaymentCandidatesAmt);
		setPaymentRequestTemplate(result.getPaymentRequestTemplate());
		setPaymentReference(result.getPaymentReference());

		// Reload everything (including payment and invoice rows)
		refresh();
	}

	/**
	 * Reload payment and invoice rows, update all amount fields.
	 */
	public final void refresh()
	{
		final boolean reloadTables = true;
		updateInfos(reloadTables);
	}

	/**
	 * Updates Tables, Totals and Difference
	 *
	 * @param requery
	 */
	private final void updateInfos(final boolean requery)
	{
		// FIXME: consider not calling this method because it's quite expensive (even if async) and this method is called very offen.
		checkBPartner();

		resetCurrentPaymentRequestIfSomethingChanged();

		setDate((Timestamp)dateField.getValue());

		updateAllocableDocRows(requery);

		//
		// Calculate Totals and their Difference
		updateTotals();

		//
		// Update Payment Request related buttons and infos
		updateAssignPaymentReferenceButton();
	}

	private I_C_Payment_Request _paymentRequestTemplate;
	/**
	 * Set of C_Payment_Request_IDs which were created from current {@link #_paymentRequestTemplate}.
	 *
	 * NOTE: this set shall be cleared when a new request template is set.
	 */
	private final Set<Integer> _paymentRequestIdsCreated = new HashSet<>();

	private void setPaymentRequestTemplate(final I_C_Payment_Request paymentRequestTemplate)
	{
		_paymentRequestTemplate = paymentRequestTemplate;
		_paymentRequestIdsCreated.clear();
	}

	/**
	 * Checks if any of the created {@link I_C_Payment_Request}s were changed/missing and if that's the case, we have to reset the payment request template and related infos.
	 *
	 * NOTE: this method was introduced to support the use-case when user is deleting/inactivating a payment request he just created, and then he presses refresh button.
	 *
	 * @task http://dewiki908/mediawiki/index.php/08596_Zahlschein_Zuordnung_l%C3%B6schen_k%C3%B6nnen_%28105873815729%29
	 */
	private final void resetCurrentPaymentRequestIfSomethingChanged()
	{
		// No payment requests were created so far
		// => nothing to do
		if (_paymentRequestIdsCreated.isEmpty())
		{
			return;
		}

		// If all of our created payment requests are still there and fine
		// => we don't have to reset anything
		if (paymentRequestDAO.checkPaymentRequestsExists(getCtx(), _paymentRequestIdsCreated))
		{
			return;
		}

		//
		// Reset payment request template and related infos
		setPaymentRequestTemplate(null);
		setTotalPaymentCandidatesAmt(BigDecimal.ZERO);
	}

	protected I_C_Payment_Request getPaymentRequestTemplate()
	{
		return _paymentRequestTemplate;
	}

	/**
	 * @task 08129
	 */
	private void updateAssignPaymentReferenceButton()
	{
		if (assignPaymentReferenceButton == null)
		{
			return;
		}

		final Properties ctx = getCtx();

		boolean buttonEnabled = true;
		final StringBuilder buttonText = new StringBuilder();
		buttonText.append(msgBL.getMsg(ctx, MSG_PREFIX + "assignPaymentReference"));

		final I_C_Payment_Request paymentRequest = getPaymentRequestTemplate();
		if (paymentRequest != null)
		{
			buttonText.append(" (").append(paymentRequest.getReference()).append(")");
		}
		else
		{
			buttonEnabled = false;
		}

		if (buttonEnabled && getInvoiceRowsSelectedCount() != 1)
		{
			buttonEnabled = false;
		}

		assignPaymentReferenceButton.setText(buttonText.toString());
		assignPaymentReferenceButton.setEnabled(buttonEnabled);
	}
}
