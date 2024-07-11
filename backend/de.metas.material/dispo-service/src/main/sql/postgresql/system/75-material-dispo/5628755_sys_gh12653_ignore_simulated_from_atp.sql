/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2022 metas GmbH
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

DROP FUNCTION IF EXISTS de_metas_material.retrieve_atp_at_date(timestamp with time zone);
CREATE FUNCTION de_metas_material.retrieve_atp_at_date(IN p_date timestamp with time zone)
    RETURNS TABLE(
                     M_Product_ID numeric,
                     M_Warehouse_ID numeric,
                     C_BPartner_Customer_ID numeric,
                     StorageAttributesKey character varying,
                     DateProjected timestamp with time zone,
                     SeqNo numeric,
                     Qty numeric) AS
$BODY$
SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey)
    M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, DateProjected, SeqNo, Qty
FROM MD_Candidate
WHERE IsActive='Y' AND MD_Candidate_Type='STOCK' and DateProjected <= p_date and md_candidate_status <> 'simulated'
ORDER BY M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC
$BODY$
    LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_atp_at_date(timestamp with time zone)
    IS 'Note that the Qtys can be from MD_Candidates whose DateProjected is before the given p_date, if they were not yet superseeded by more recent values.
  Please keep this function in sync with the AD_Table named MD_Candidate_ATP_QueryResult and also with the function retrieve_atp_at_date_debug';

DROP FUNCTION IF EXISTS de_metas_material.retrieve_atp_at_date_debug(timestamp with time zone);
CREATE FUNCTION de_metas_material.retrieve_atp_at_date_debug(IN p_date timestamp with time zone)
    RETURNS TABLE(
                     M_Product_ID numeric,
                     M_Warehouse_ID numeric,
                     C_BPartner_Customer_ID numeric,
                     StorageAttributesKey character varying,
                     SeqNo numeric,
                     Qty numeric,
                     MD_Candidate_ID numeric,
                     Dateprojected timestamp with time zone) AS
$BODY$
SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey)
    M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, SeqNo, Qty, MD_Candidate_ID, Dateprojected
FROM MD_Candidate
WHERE IsActive='Y' AND MD_Candidate_Type='STOCK' and DateProjected <= p_date and md_candidate_status <> 'simulated'
ORDER BY M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC
$BODY$
    LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_atp_at_date_debug(timestamp with time zone)
    IS 'Like de_metas_material.retrieve_atp_at_date, but returns additional columns that are not part of the index.';

