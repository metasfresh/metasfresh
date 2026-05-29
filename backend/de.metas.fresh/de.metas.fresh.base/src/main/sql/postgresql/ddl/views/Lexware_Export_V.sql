/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

DROP VIEW IF EXISTS report.Lexware_Export_V
;

CREATE OR REPLACE VIEW report.Lexware_Export_V AS
SELECT
    -- Business Partner Core Information
    bp.c_bpartner_id,
    bp.value                       AS partner_number,
    bp.name                        AS company_name,
    bp.name2                       AS company_name2,

    -- Debitor/Creditor Classification
    CASE
        WHEN bp.iscustomer = 'Y' THEN 'D' -- Debitor
        WHEN bp.isvendor = 'Y'   THEN 'K' -- Kreditor (Creditor)
                                 ELSE 'N'
    END                            AS account_type,

    -- Address Information from Location
    loc.address1,
    loc.address2,
    loc.street,
    loc.housenumber,
    loc.postal,
    loc.city,
    loc.regionname,
    c.name                         AS country,

    -- Contact Information
    COALESCE(bpl.phone, bp.phone2) AS phone,
    bpl.fax,
    COALESCE(bpl.email, bp.email)  AS email,

    -- Tax and Financial Information
    bp.taxid                       AS tax_number,
    bp.vataxid                     AS vat_id,

    -- Payment Information
    bp.paymentrule                 AS payment_method,
    pt.value                       AS payment_term,

    -- Additional Information
    bp.referenceno                 AS reference_number,
    bp.url                         AS website,

    -- Status
    CASE
        WHEN bp.isactive = 'Y' THEN 'Active'
                               ELSE 'Inactive'

    END                            AS status,

    -- Dates
    bp.created                     AS creation_date,
    bp.updated                     AS last_update

FROM c_bpartner bp
         LEFT JOIN LATERAL (SELECT *
                            FROM c_bpartner_location bpl
                            WHERE bp.c_bpartner_id = bpl.c_bpartner_id
                            ORDER BY isbilltodefault DESC,
                                     isbillto DESC
                            LIMIT 1
    ) bpl ON TRUE
         LEFT JOIN c_location loc ON bpl.c_location_id = loc.c_location_id
         LEFT JOIN c_country c ON loc.c_country_id = c.c_country_id
         LEFT JOIN c_paymentterm pt ON bp.c_paymentterm_id = pt.c_paymentterm_id
WHERE bp.isactive = 'Y'
  AND (bp.iscustomer = 'Y' OR bp.isvendor = 'Y')
ORDER BY bp.value
;
