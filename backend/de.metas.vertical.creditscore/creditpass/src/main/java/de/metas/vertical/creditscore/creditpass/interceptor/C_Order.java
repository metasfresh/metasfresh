package de.metas.vertical.creditscore.creditpass.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Services;
import de.metas.vertical.creditscore.base.spi.model.ResultCode;
import de.metas.vertical.creditscore.base.spi.model.TransactionResult;
import de.metas.vertical.creditscore.base.spi.service.TransactionResultService;
import de.metas.vertical.creditscore.creditpass.CreditPassConstants;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.model.extended.I_C_Order;
import de.metas.vertical.creditscore.creditpass.repository.CreditPassConfigRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Interceptor(I_C_Order.class)
@Component
public class C_Order
{

	private final TransactionResultService transactionResultService;

	private final CreditPassConfigRepository creditPassConfigRepository;

	public C_Order(@NonNull final TransactionResultService transactionResultService,
			@NonNull final CreditPassConfigRepository creditPassConfigRepository)
	{
		this.transactionResultService = transactionResultService;
		this.creditPassConfigRepository = creditPassConfigRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW,
			ModelValidator.TYPE_BEFORE_CHANGE
	}, ifColumnsChanged = { I_C_Order.COLUMNNAME_PaymentRule,
	})
	public void setCreditPassInfo(final I_C_Order order)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(order.getC_BPartner_ID());
		final CreditPassConfig config = creditPassConfigRepository.getConfigByBPartnerId(bPartnerId);
		final Optional<TransactionResult> transactionResult = transactionResultService.findLastTransactionResult(order.getPaymentRule(), BPartnerId.ofRepoId(order.getC_BPartner_ID()));
		final Optional<CreditPassConfig> configuration = Optional.ofNullable(config);
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		if (configuration.isPresent() && configuration.get().getCreditPassConfigPaymentRuleList().stream()
				.map(CreditPassConfigPaymentRule::getPaymentRule)
				.anyMatch(pr -> StringUtils.equals(pr, order.getPaymentRule())))
		{
			if (transactionResult.filter(tr -> tr.getRequestDate().until(LocalDateTime.now(), ChronoUnit.DAYS) < config.getRetryDays()).isPresent())
			{
				if (transactionResult.filter(tr -> tr.getResultCodeEffective() == ResultCode.P).isPresent())
				{
					order.setCreditpassFlag(false);
					final ITranslatableString message = msgBL.getTranslatableMsgText(CreditPassConstants.CREDITPASS_STATUS_SUCCESS_MESSAGE_KEY);
					order.setCreditpassStatus(message.translate(Env.getAD_Language()));
				}
				else
				{
					order.setCreditpassFlag(true);
					final ITranslatableString message = msgBL.getTranslatableMsgText(CreditPassConstants.CREDITPASS_REQUEST_NEEDED_MESSAGE_KEY);
					order.setCreditpassStatus(message.translate(Env.getAD_Language()));
				}
			}
			else
			{
				order.setCreditpassFlag(true);
				final ITranslatableString message = msgBL.getTranslatableMsgText(CreditPassConstants.CREDITPASS_REQUEST_NEEDED_MESSAGE_KEY);
				order.setCreditpassStatus(message.translate(Env.getAD_Language()));
			}
		}
		else
		{
			order.setCreditpassFlag(false);
			final ITranslatableString message = msgBL.getTranslatableMsgText(CreditPassConstants.CREDITPASS_REQUEST_NOT_NEEDED_MESSAGE_KEY);
			order.setCreditpassStatus(message.translate(Env.getAD_Language()));
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void validateCreditpassForOrder(final I_C_Order order)
	{
		if (order.getCreditpassFlag() && order.isSOTrx())
		{
			throw new AdempiereException(CreditPassConstants.ORDER_COMPLETED_CREDITPASS_ERROR);
		}
	}

}
