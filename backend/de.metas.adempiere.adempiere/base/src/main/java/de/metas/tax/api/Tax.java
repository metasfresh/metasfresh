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
<<<<<<< HEAD
=======
import java.math.RoundingMode;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.sql.Timestamp;

@Value
public class Tax
{
	private static final Logger log = LogManager.getLogger(Tax.class);
	public static final int C_TAX_ID_NO_TAX_FOUND = 100;

	@NonNull TaxId taxId;
<<<<<<< HEAD
=======
	@NonNull String name;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	boolean isWholeTax;
=======
	boolean isSalesTax;
	boolean isWholeTax;
	boolean isReverseCharge;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	boolean isDocumentLevel;
	BigDecimal rate;
	BoilerPlateId boilerPlateId;
	@NonNull Integer seqNo;

	@Builder
	public Tax(final @NonNull TaxId taxId,
<<<<<<< HEAD
=======
			@NonNull final String name,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
			final boolean isWholeTax,
=======
			final boolean isSalesTax,
			final boolean isWholeTax,
			final boolean isReverseCharge,
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			final boolean isDocumentLevel,
			final BigDecimal rate,
			final BoilerPlateId boilerPlateId,
			final @NonNull Integer seqNo)
	{
		this.taxId = taxId;
<<<<<<< HEAD
		this.orgId = orgId;
		this.validFrom = validFrom;
		this.countryId = countryId;

=======
		this.name = name;
		this.orgId = orgId;
		this.validFrom = validFrom;
		this.countryId = countryId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.toCountryId = toCountryId;
		this.typeOfDestCountry = typeOfDestCountry;
		this.taxCategoryId = taxCategoryId;
		this.requiresTaxCertificate = requiresTaxCertificate;
		this.sopoType = sopoType;
		this.isTaxExempt = isTaxExempt;
		this.isFiscalRepresentation = isFiscalRepresentation;
		this.isSmallBusiness = isSmallBusiness;
<<<<<<< HEAD
		this.isWholeTax = isWholeTax;
=======
		this.isSalesTax = isSalesTax;
		this.isWholeTax = isWholeTax;
		this.isReverseCharge = isReverseCharge;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		this.isDocumentLevel = isDocumentLevel;
		this.rate = rate;
		this.boilerPlateId = boilerPlateId;
		this.seqNo = seqNo;
	}

	public boolean isTaxNotFound()
	{
		return C_TAX_ID_NO_TAX_FOUND == taxId.getRepoId();
	}

<<<<<<< HEAD
=======
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isZeroTax() {return this.rate.signum() == 0;}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
			final BigDecimal taxAmt = calculateTax(amount, taxIncluded, scale);
=======
			final BigDecimal taxAmt = calculateTax(amount, taxIncluded, scale).getTaxAmount();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			return amount.subtract(taxAmt);
		}
	}

	/**
	 * Calculate Tax - no rounding
	 *
	 * @param taxIncluded if true tax is calculated from gross otherwise from net
	 * @return tax amount
	 */
<<<<<<< HEAD
	public BigDecimal calculateTax(final BigDecimal amount, final boolean taxIncluded, final int scale)
=======
	public CalculateTaxResult calculateTax(final BigDecimal amount, final boolean taxIncluded, final int scale)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		// Null Tax
		if (rate.signum() == 0)
		{
<<<<<<< HEAD
			return BigDecimal.ZERO;
		}

		BigDecimal multiplier = rate.divide(Env.ONEHUNDRED, 12, BigDecimal.ROUND_HALF_UP);

		final BigDecimal taxAmt;
=======
			return CalculateTaxResult.ZERO;
		}

		BigDecimal multiplier = rate.divide(Env.ONEHUNDRED, 12, RoundingMode.HALF_UP);

		final BigDecimal taxAmt;
		final BigDecimal reverseChargeAmt;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		if (isWholeTax)
		{
			Check.assume(taxIncluded, "TaxIncluded shall be set when IsWholeTax is set");
			taxAmt = amount;
<<<<<<< HEAD
=======
			reverseChargeAmt = BigDecimal.ZERO;
		}
		else if (isReverseCharge)
		{
			Check.assume(!taxIncluded, "TaxIncluded shall NOT be set when IsReverseCharge is set");
			taxAmt = BigDecimal.ZERO;
			reverseChargeAmt = amount.multiply(multiplier);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else if (!taxIncluded)    // $100 * 6 / 100 == $6 == $100 * 0.06
		{
			taxAmt = amount.multiply(multiplier);
<<<<<<< HEAD
=======
			reverseChargeAmt = BigDecimal.ZERO;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
		else
		// $106 - ($106 / (100+6)/100) == $6 == $106 - ($106/1.06)
		{
			multiplier = multiplier.add(BigDecimal.ONE);
<<<<<<< HEAD
			final BigDecimal base = amount.divide(multiplier, 12, BigDecimal.ROUND_HALF_UP);
			taxAmt = amount.subtract(base);
		}

		final BigDecimal taxAmtFinal = taxAmt.setScale(scale, BigDecimal.ROUND_HALF_UP);

		log.debug("calculateTax: amount={} (incl={}, mult={}, scale={}) = {} [{}]", amount, taxIncluded, multiplier, scale, taxAmtFinal, taxAmt);

		return taxAmtFinal;
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}    // calculateTax

	@NonNull
	public BigDecimal calculateGross(@NonNull final BigDecimal netAmount, final int scale)
	{
<<<<<<< HEAD
		return netAmount.add(calculateTax(netAmount, false, scale));
=======
		return netAmount.add(calculateTax(netAmount, false, scale).getTaxAmount());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
