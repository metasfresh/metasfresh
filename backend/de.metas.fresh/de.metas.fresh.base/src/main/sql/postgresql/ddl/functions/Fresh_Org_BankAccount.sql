/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

DROP FUNCTION IF EXISTS report.Fresh_Org_BankAccount(IN p_Org_ID numeric)
;

CREATE FUNCTION report.Fresh_Org_BankAccount(p_Org_ID numeric)
    RETURNS TABLE
            (
                org_bank_acct  character varying,
                org_bank_name  character varying,
                org_bank_blz   character varying,
                org_bank_iban  character varying,
                org_bank_swift character varying
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT COALESCE(bpb.accountno, '')                 AS org_bank_acct,
       COALESCE(bank.name, '')                     AS org_bank_name,
       COALESCE(bpb.routingno, bank.routingno, '') AS org_bank_blz,
       COALESCE(bpb.iban, '')                      AS org_bank_iban,
       COALESCE(bank.swiftcode, '')                AS org_bank_swift

FROM ad_org org
         INNER JOIN c_bpartner org_bp ON org.ad_org_id = org_bp.ad_orgbp_id
         LEFT OUTER JOIN c_bp_bankaccount bpb ON org_bp.c_bpartner_id = bpb.c_bpartner_id AND bpb.IsActive = 'Y' AND (bpb.bankaccounttype = 'C' OR bpb.bankaccounttype IS NULL)
         LEFT OUTER JOIN c_bank bank ON bpb.c_bank_id = bank.c_bank_id

WHERE org.ad_org_id = p_Org_ID

ORDER BY NULLIF(TRIM(bpb.iban), '') IS NULL,          -- prefer records that do have an IBAN (note that false < true),
         (bpb.bankaccounttype = 'C') DESC NULLS LAST, -- Prefer 'C' accounts over others
         (bpb.IsDefault = 'Y') DESC,                  -- Prefer default accounts
         bpb.C_BP_BankAccount_ID DESC                 -- Prefer newest / highest ID
LIMIT 1
$$
;
