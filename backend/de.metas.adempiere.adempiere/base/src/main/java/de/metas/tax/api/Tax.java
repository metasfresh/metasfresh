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
import java.math.RoundingMode;
import java.sql.Timestamp;

@Value
public class Tax
{
	private static final Logger log = LogManager.getLogger(Tax.class);
	public static final int C_TAX_ID_NO_TAX_FOUND = 100;

	@NonNull TaxId taxId;
	@NonNull String name;
	boolean isSummary;
	@NonNull OrgId orgId;
	@NonNull Timestamp validFrom;
	@Nullable CountryId countryId;
	@Nullable CountryId toCountryId;
	@Nullable TypeOfDestCountry typeOfDestCountry;
	@NonNull TaxCategoryId taxCategoryId;
	boolean requiresTaxCertificate;
	@Nullable SOPOType sopoType;
	@Nullable Boolean isTaxExempt;
	@Nullable Boolean isFiscalRepresentation;
	@Nullable Boolean isSmallBusiness;
	boolean isSalesTax;
	boolean isWholeTax;
	boolean isReverseCharge;
	boolean isDocumentLevel;
	@NonNull BigDecimal rate;
	@Nullable BoilerPlateId boilerPlateId;
	@NonNull Integer seqNo;
	@Nullable String taxCode;

	@Builder
	public Tax(
			@NonNull final TaxId taxId,
			@NonNull final String name,
			final boolean isSummary,
			@NonNull final OrgId orgId,
			@NonNull final Timestamp validFrom,
			@Nullable final CountryId countryId,
			@Nullable final CountryId toCountryId,
			@Nullable final TypeOfDestCountry typeOfDestCountry,
			@NonNull final TaxCategoryId taxCategoryId,
			final boolean requiresTaxCertificate,
			@Nullable final SOPOType sopoType,
			final boolean isTaxExempt,
			@Nullable final Boolean isFiscalRepresentation,
			@Nullable final Boolean isSmallBusiness,
			final boolean isSalesTax,
			final boolean isWholeTax,
			final boolean isReverseCharge,
			final boolean isDocumentLevel,
			@NonNull final BigDecimal rate,
			@Nullable final BoilerPlateId boilerPlateId,
			@NonNull final Integer seqNo,
			@Nullable final String taxCode)
	{
		this.taxId = taxId;
		this.name = name;
		this.isSummary = isSummary;
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
		this.isSalesTax = isSalesTax;
		this.isWholeTax = isWholeTax;
		this.isReverseCharge = isReverseCharge;
		this.isDocumentLevel = isDocumentLevel;
		this.rate = rate;
		this.boilerPlateId = boilerPlateId;
		this.seqNo = seqNo;
		this.taxCode = taxCode;
	}

	public boolean isTaxNotFound()
	{
		return C_TAX_ID_NO_TAX_FOUND == taxId.getRepoId();
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isZeroTax() {return this.rate.signum() == 0;}

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
			else if (!taxIncluded)
			{
				// the given amount is without tax => don't subtract the tax that is no included
				log.debug("Parameter taxIncluded=false; -> return given param amount={}", amount);
				return amount;
			}
			else
			{
				final BigDecimal taxAmt = calculateTax(amount, true, scale).getTaxAmount();
				return amount.subtract(taxAmt);
			}
		}
	}

	/**
	 * @param taxIncluded if true tax is calculated from gross otherwise from net
	 * @return tax amount
	 */
	public CalculateTaxResult calculateTax(@NonNull final BigDecimal amount, final boolean taxIncluded, final int scale)
	{
		// Null Tax
		if (rate.signum() == 0)
		{
			return CalculateTaxResult.ZERO;
		}

		BigDecimal multiplier = rate.divide(Env.ONEHUNDRED, 12, RoundingMode.HALF_UP);

		final BigDecimal taxAmt;
		final BigDecimal reverseChargeAmt;
		if (isWholeTax)
		{
			Check.assume(taxIncluded, "TaxIncluded shall be set when IsWholeTax is set");
			taxAmt = amount;
			reverseChargeAmt = BigDecimal.ZERO;
		}
		else if (isReverseCharge)
		{
			Check.assume(!taxIncluded, "TaxIncluded shall NOT be set when IsReverseCharge is set");
			taxAmt = BigDecimal.ZERO;
			reverseChargeAmt = amount.multiply(multiplier);
		}
		else if (!taxIncluded)    // $100 * 6 / 100 == $6 == $100 * 0.06
		{
			taxAmt = amount.multiply(multiplier);
			reverseChargeAmt = BigDecimal.ZERO;
		}
		else
		// $106 - ($106 / (100+6)/100) == $6 == $106 - ($106/1.06)
		{
			multiplier = multiplier.add(BigDecimal.ONE);
			final BigDecimal base = amount.divide(multiplier, 12, RoundingMode.HALF_UP);
			taxAmt = amount.subtract(base);
			reverseChargeAmt = BigDecimal.ZERO;
		}

		final BigDecimal taxAmtFinal = taxAmt.setScale(scale, RoundingMode.HALF_UP);
		final BigDecimal reverseChargeTaxAmtFinal = reverseChargeAmt.setScale(scale, RoundingMode.HALF_UP);

		log.debug("calculateTax: amount={} (incl={}, multiplier={}, scale={}) = {} [{}] / reverse charge = {} [{}]",
				amount, taxIncluded, multiplier, scale, taxAmtFinal, taxAmt, reverseChargeAmt, reverseChargeTaxAmtFinal);

		return CalculateTaxResult.builder()
				.taxAmount(taxAmtFinal)
				.reverseChargeAmt(reverseChargeTaxAmtFinal)
				.build();
	}

	@NonNull
	public BigDecimal calculateGross(@NonNull final BigDecimal netAmount, final int scale)
	{
		return netAmount.add(calculateTax(netAmount, false, scale).getTaxAmount());
	}
}
