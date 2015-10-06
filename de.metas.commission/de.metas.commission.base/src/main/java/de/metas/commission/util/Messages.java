package de.metas.commission.util;

/*
 * #%L
 * de.metas.commission.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public final class Messages
{

	private Messages()
	{
	}

	/**
	 * Indicates that a certain C_BPartner has no sponsor
	 * <ul>
	 * <li>Param1: BPartner Value</li>
	 * <li>Param2: BPartner Name</li>
	 * </ul>
	 */
	public static final String SPONSOR_MISSING_FOR_BPARTNER_2P = "SponsorMissingForBPartner_2P";

	// public static final String SPONSOR_DELETION_FORBIDDEN = "SponsorDeletionForbidden";

	public static final String SPONSOR_SALESREP_DELETION_FORBIDDEN = "SponsorSalesRepDeletionForbidden";

	public static final String SPONSOR_SALESREP_VALIDFROM_TOO_EARLY = "SponsorValidFromTooEarly";

	public static final String SPONSOR_SALESREP_VALIDFROM_TOO_LATE = "SponsorValidFromTooLate";

	public static final String SPONSOR_SALESREP_VALIDTO_TOO_LATE = "SponsorValidToTooLate";

	/**
	 * Message indicates that there is no sales rep associated with a given sponsor.
	 * <ul>
	 * <li>Param1: SponsorNo</li>
	 * </ul>
	 */
	public static final String SPONSOR_NO_SALESREP_1P = "SponsorNoSalesRep_1P";

	public static final String SPONSOR_VALIDITY_GAP_2P = "SponsorValidityGap";

	public static final String SPONSOR_VALIDITY_OVERLAP_3P = "SponsorValidityOverlap";

	/**
	 * Message indicates that a given sponsor is a child of another sponsor.
	 * <ul>
	 * <li>Param1: The child sponsor's sponsorNo</li>
	 * <li>Param2: The parent sponsor's sponsorNo</li>
	 * <li>Param3: The path(s) that lead from child to parent</li>
	 * </ul>
	 */
	public static final String SPONSOR_IS_CHILD_3P = "SponsorIsChild_3P";

	/**
	 * Message indicates that it is not allowed to delete a po when it might be commission-relevant.
	 * <p>
	 * Note: Usually, logging is enabled for relevant PO like invoice lines etc, so deletion is not allowed anyways.
	 * 
	 * <ul>
	 * <li>Param1: String identifying the PO</li>
	 * </ul>
	 */
	public static final String RELEVANT_PO_DELETION_FORBIDDEN_1P = "CommissionRelevantPoDeletionForbidden_1P";

	/**
	 * Message indicates that payroll completion has failed.
	 * <ul>
	 * <li>Param1: DocumentNo of invoice</li>
	 * <li>Param2: specific error message (if any)</li>
	 * </ul>
	 */
	public static final String INVOICE_COMPLETE_IT_FAILURE_2P = "InvoiceCompletItFailed_2P";

	/**
	 * Message indicates that a inconsistency has been found in the config or between the config and implmentation code.
	 * <ul>
	 * <li>Param1: a description of the inconsistency</li>
	 * <li>Param2 (optional): String rep of an inconsistent Object</li>
	 * </ul>
	 */
	public static final String COMMISSION_CONFIG_INCONSISTENT_2P = "CommissionConfigInconsistent_2P";

	/**
	 * Message indicates that a data inconsistency has been found.
	 * <ul>
	 * <li>Param1: a description of the inconsistency</li>
	 * <li>Param2 (optional): String rep of an inconsistent Object</li>
	 * </ul>
	 */
	public static final String COMMISSION_DATA_INCONSISTENT_2P = "CommissionDataInconsistent_2P";

	/**
	 * Name of the Attribute for the highest rank in the last 12 months in the attribute set for commission documents. No Parameters.
	 */
	public static final String HIGHEST_RANK_IN_12_MONTHS = "CommissionHighestRankIn12Months";

	/**
	 * Name of the Attribute for the rank of the commission payroll in the attribute set for commission documents. No Parameters.
	 */
	public static final String RANK_OF_PAYROLL_PERIOD = "CommissionRankOfPayrollPeriod";

	/**
	 * Name of the Attribute for the current rank in the AttributeSet for commission documents. No Parameters.
	 */
	public static final String CURRENT_RANK = "CommissionCurrentRank";
}
