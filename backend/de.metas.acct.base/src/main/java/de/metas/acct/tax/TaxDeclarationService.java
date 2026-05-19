package de.metas.acct.tax;

import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

@Service
public class TaxDeclarationService
{
	private static final AdMessageKey MSG_TaxDeclaration_AlreadyProcessed = AdMessageKey.of("TaxDeclaration_AlreadyProcessed");

	@NonNull private final TaxDeclarationRepository taxDeclarationRepository;

	public TaxDeclarationService(@NonNull final TaxDeclarationRepository taxDeclarationRepository)
	{
		this.taxDeclarationRepository = taxDeclarationRepository;
	}

	public void build(@NonNull final TaxDeclarationId id)
	{
		final I_C_TaxDeclaration record = taxDeclarationRepository.getById(id);
		if (record.isProcessed())
		{
			throw new AdempiereException(MSG_TaxDeclaration_AlreadyProcessed).markAsUserValidationError();
		}

		DB.executeFunctionCallEx(
				ITrx.TRXNAME_ThreadInherited,
				"SELECT de_metas_acct.tax_declaration_build(?)",
				new Object[] { id });
	}
}
