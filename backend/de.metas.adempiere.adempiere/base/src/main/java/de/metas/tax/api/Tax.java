/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.tax.api;

import de.metas.letter.BoilerPlateId;
import de.metas.location.CountryId;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_C_Tax;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Value
public class Tax
{
	private static final Logger log = LogManager.getLogger(Tax.class);
	public static final int C_TAX_ID_NO_TAX_FOUND = 100;

	@NonNull TaxId taxId;
	@NonNull OrgId orgId;
	@NonNull Timestamp validFrom;
	@Nullable
	CountryId countryId;
	@Nullable
	CountryId toCountryId;
	@Nullable
	TypeOfDestCountry typeOfDestCountry;
	@NonNull TaxCategoryId taxCategoryId;
	boolean requiresTaxCertificate;
	SOPOType sopoType;
	@Nullable
	Boolean isTaxExempt;
	@Nullable
	Boolean isFiscalRepresentation;
	@Nullable
	Boolean isSmallBusiness;
	boolean isWholeTax;
	boolean isDocumentLevel;
	BigDecimal rate;
	BoilerPlateId boilerPlateId;
	@NonNull Integer seqNo;

	@Builder
	public Tax(final @NonNull TaxId taxId,
			final @NonNull OrgId orgId,
			final @NonNull Timestamp validFrom,
			final @Nullable CountryId countryId,
			final @Nullable CountryId toCountryId,
			final @Nullable TypeOfDestCountry typeOfDestCountry,
			final @NonNull TaxCategoryId taxCategoryId,
			@Nullable final Boolean requiresTaxCertificate,
			final SOPOType sopoType,
			final boolean isTaxExempt,
			@Nullable final Boolean isFiscalRepresentation,
			@Nullable final Boolean isSmallBusiness,
			final boolean isWholeTax,
			final boolean isDocumentLevel,
			final BigDecimal rate,
			final BoilerPlateId boilerPlateId,
			final @NonNull Integer seqNo)
	{
		this.taxId = taxId;
		this.orgId = orgId;
		this.validFrom = validFrom;
		this.countryId = countryId;

		this.toCountryId = toCountryId;
		this.typeOfDestCountry = typeOfDestCountry;
		this.taxCategoryId = taxCategoryId;
		this.requiresTaxCertificate = requiresTaxCertificate;
		this.sopoType = sopoType;
		this.isTaxExempt = isTaxExempt;
		this.isFiscalRepresentation = isFiscalRepresentation;
		this.isSmallBusiness = isSmallBusiness;
		this.isWholeTax = isWholeTax;
		this.isDocumentLevel = isDocumentLevel;
		this.rate = rate;
		this.boilerPlateId = boilerPlateId;
		this.seqNo = seqNo;
	}

	public boolean isTaxNotFound()
	{
		return C_TAX_ID_NO_TAX_FOUND == taxId.getRepoId();
	}

	/**
	 * Calculate base amount, excluding tax
	 */
	public BigDecimal calculateBaseAmt(@NonNull final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(I_C_Tax.Table_Name, taxId))
		{
			if (isWholeTax)
			{
				log.debug("C_Tax has isWholeTax=true; -> return ZERO");
				return BigDecimal.ZERO;
			}
			if (!taxIncluded)
			{
				// the given amount is without tax => don't subtract the tax that is no included
				log.debug("Parameter taxIncluded=false; -> return given param amount={}", amount);
				return amount;
			}
			final BigDecimal taxAmt = calculateTax(amount, taxIncluded, scale);
			return amount.subtract(taxAmt);
		}
	}

	/**
	 * Calculate Tax - no rounding
	 *
	 * @param taxIncluded if true tax is calculated from gross otherwise from net
	 * @return tax amount
	 */
	public BigDecimal calculateTax(final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		// Null Tax
		if (rate.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		BigDecimal multiplier = rate.divide(Env.ONEHUNDRED, 12, BigDecimal.ROUND_HALF_UP);

		final BigDecimal taxAmt;
		if (isWholeTax)
		{
			Check.assume(taxIncluded, "TaxIncluded shall be set when IsWholeTax is set");
			taxAmt = amount;
		}
		else if (!taxIncluded)    // $100 * 6 / 100 == $6 == $100 * 0.06
		{
			taxAmt = amount.multiply(multiplier);
		}
		else
		// $106 - ($106 / (100+6)/100) == $6 == $106 - ($106/1.06)
		{
			multiplier = multiplier.add(BigDecimal.ONE);
			final BigDecimal base = amount.divide(multiplier, 12, BigDecimal.ROUND_HALF_UP);
			taxAmt = amount.subtract(base);
		}

		final BigDecimal taxAmtFinal = taxAmt.setScale(scale, BigDecimal.ROUND_HALF_UP);

		log.debug("calculateTax: amount={} (incl={}, mult={}, scale={}) = {} [{}]", amount, taxIncluded, multiplier, scale, taxAmtFinal, taxAmt);

		return taxAmtFinal;
	}    // calculateTax

	@NonNull
	public BigDecimal calculateGross(@NonNull final BigDecimal netAmount, final int scale)
	{
		return netAmount.add(calculateTax(netAmount, false, scale));
	}
}
