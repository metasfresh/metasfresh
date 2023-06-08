package de.metas.invoicecandidate.modelvalidator;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.service.BPartnerCreditLimitRepository;
import de.metas.bpartner.service.BPartnerStats;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.bpartner.service.impl.BPartnerStatsService;
import de.metas.bpartner.service.impl.CalculateCreditStatusRequest;
import de.metas.bpartner.service.impl.CreditStatus;
import de.metas.currency.ICurrencyBL;
import de.metas.document.IDocTypeDAO;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.order.event.OrderUserNotifications;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_DocType;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_C_DocType;
import org.compiere.util.DisplayType;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Interceptor(I_C_Order.class)
public class C_Order
{
	// @DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE, ModelValidator.TIMING_AFTER_REACTIVATE, ModelValidator.TIMING_AFTER_CLOSE })
	// public void invalidateInvoiceCandidates(final I_C_Order order)
	// {
	// 	final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	// 	invoiceCandidateHandlerBL.invalidateCandidatesFor(order);
	// }

	final BPartnerStatsService bPartnerStatsService = SpringContextHolder.instance.getBean(BPartnerStatsService.class);

	final BPartnerCreditLimitRepository creditLimitRepo = Adempiere.getBean(BPartnerCreditLimitRepository.class);
	final IBPartnerStatsDAO bpartnerStatsDAO = Services.get(IBPartnerStatsDAO.class);
	final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_PREPARE })
	public void checkCreditLimit(@NonNull final I_C_Order order)
	{

		if (!isCheckCreditLimitNeeded(order))
		{
			return;
		}

		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(order.getBill_BPartner_ID());
		final BigDecimal soCreditUsed = stats.getSoCreditUsed();
		final CreditStatus soCreditStatus = stats.getSoCreditStatus();
		final Timestamp dateOrdered = order.getDateOrdered();

		final boolean doNotEnforceSOCreditstatus = false; // todo sys config
		if(doNotEnforceSOCreditstatus)
		{
			final OrderUserNotifications orderUserNotifications = OrderUserNotifications.newInstance();

			final String bpartnerName = null; // TODO
			final String creditLimitDifferenceMessage = null; // TODO

			// TODO: Build the message like the ones below are built
			// I used de.metas.deliveryplanning.DeliveryInstructionUserNotificationsProducer.notifyDeliveryInstructionError for example

			orderUserNotifications.notifyCreditLimitExceeded(bpartnerName, creditLimitDifferenceMessage);

		}
		final BigDecimal creditLimit = creditLimitRepo.retrieveCreditLimitByBPartnerId(order.getBill_BPartner_ID(), dateOrdered);

		if (CreditStatus.CreditStop.equals(soCreditStatus))
		{
			throw new AdempiereException(TranslatableStrings.builder()
												 .appendADElement("BPartnerCreditStop").append(":")
												 .append(" ").appendADElement("SO_CreditUsed").append("=").append(soCreditUsed, DisplayType.Amount)
												 .append(", ").appendADElement("SO_CreditLimit").append("=").append(creditLimit, DisplayType.Amount)
												 .build());
		}
		if (CreditStatus.CreditHold.equals(soCreditStatus))
		{
			throw new AdempiereException(TranslatableStrings.builder()
												 .appendADElement("BPartnerCreditHold").append(":")
												 .append(" ").appendADElement("SO_CreditUsed").append("=").append(soCreditUsed, DisplayType.Amount)
												 .append(", ").appendADElement("SO_CreditLimit").append("=").append(creditLimit, DisplayType.Amount)
												 .build());
		}

		final BigDecimal grandTotal = currencyBL.convertBase(
				order.getGrandTotal(),
				CurrencyId.ofRepoId(order.getC_Currency_ID()),
				order.getDateOrdered().toInstant(),
				CurrencyConversionTypeId.ofRepoIdOrNull(order.getC_ConversionType_ID()),
				ClientId.ofRepoId(order.getAD_Client_ID()),
				OrgId.ofRepoId(order.getAD_Org_ID()));

		final CalculateCreditStatusRequest request = CalculateCreditStatusRequest.builder()
				.stat(stats)
				.additionalAmt(grandTotal) // null is threated like zero
				.date(dateOrdered)
				.build();
		final CreditStatus calculatedSOCreditStatus = bPartnerStatsService.calculateProjectedSOCreditStatus(request);

		if (CreditStatus.CreditHold.equals(calculatedSOCreditStatus))
		{
			throw new AdempiereException(TranslatableStrings.builder()
												 .appendADElement("BPartnerOverOCreditHold").append(":")
												 .append(" ").appendADElement("SO_CreditUsed").append("=").append(soCreditUsed, DisplayType.Amount)
												 .append(", ").appendADElement("GrandTotal").append("=").append(grandTotal, DisplayType.Amount)
												 .append(", ").appendADElement("SO_CreditLimit").append("=").append(creditLimit, DisplayType.Amount)
												 .build());
		}
	}

	private boolean isCheckCreditLimitNeeded(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return false;
		}

		final BPartnerStats stats = bpartnerStatsDAO.getCreateBPartnerStats(order.getBill_BPartner_ID());
		if (CreditStatus.NoCreditCheck.equals(stats.getSoCreditStatus()))
		{
			return false;
		}

		final I_C_DocType dt = docTypeDAO.getById(order.getC_DocTypeTarget_ID());

		final PaymentRule paymentRule = PaymentRule.ofCode(order.getPaymentRule());
		if (X_C_DocType.DOCSUBTYPE_POSOrder.equals(dt.getDocSubType())
				&& paymentRule.isCash()
				&& !sysConfigBL.getBooleanValue("CHECK_CREDIT_ON_CASH_POS_ORDER", true, order.getAD_Client_ID(), order.getAD_Org_ID()))
		{
			// ignore -- don't validate for Cash POS Orders depending on sysconfig parameter
			return false;
		}
		else if (X_C_DocType.DOCSUBTYPE_PrepayOrder.equals(dt.getDocSubType())
				&& !sysConfigBL.getBooleanValue("CHECK_CREDIT_ON_PREPAY_ORDER", true, order.getAD_Client_ID(), order.getAD_Org_ID()))
		{
			// ignore -- don't validate Prepay Orders depending on sysconfig parameter
			return false;
		}

		return true;
	}
}
