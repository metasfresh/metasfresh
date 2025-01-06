/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

DROP VIEW IF EXISTS Fact_Acct_Transactions_View
;

CREATE OR REPLACE VIEW Fact_Acct_Transactions_View
AS
SELECT fact.fact_acct_id,
       fact.ad_client_id,
       fact.ad_org_id,
       fact.isactive,
       fact.created,
       fact.createdby,
       fact.updated,
       fact.updatedby,
       fact.c_acctschema_id,
       fact.account_id,
       fact.datetrx,
       fact.dateacct,
       fact.c_period_id,
       fact.ad_table_id,
       fact.record_id,
       fact.line_id,
       fact.gl_category_id,
       fact.gl_budget_id,
       fact.c_tax_id,
       fact.m_locator_id,
       fact.postingtype,
       fact.c_currency_id,
       fact.amtsourcedr,
       fact.amtsourcecr,
       fact.amtacctdr,
       fact.amtacctcr,
       fact.c_uom_id,
       fact.qty,
       fact.m_product_id,
       fact.c_bpartner_id,
       fact.ad_orgtrx_id,
       fact.c_locfrom_id,
       fact.c_locto_id,
       fact.c_salesregion_id,
       fact.c_project_id,
       fact.c_campaign_id,
       fact.c_activity_id,
       fact.user1_id,
       fact.user2_id,
       fact.description,
       fact.a_asset_id,
       fact.c_subacct_id,
       fact.userelement1_id,
       fact.userelement2_id,
       fact.c_projectphase_id,
       fact.c_projecttask_id,
       fact.currencyrate,
       fact.docstatus,
       fact.poreference,
       fact.subline_id,
       fact.documentno,
       fact.c_doctype_id,
       fact.docbasetype,
       fact.vatcode,
       fact.counterpart_fact_acct_id,
       fact.userelementnumber1,
       fact.userelementnumber2,
       fact.userelementstring1,
       fact.userelementstring2,
       fact.userelementstring3,
       fact.userelementstring4,
       fact.userelementstring5,
       fact.userelementstring6,
       fact.userelementstring7,
       fact.userElementDate1,
       fact.userElementDate2,
       fact.m_sectioncode_id,
       fact.C_OrderSO_ID,
       acctbalance(fact.account_id, fact.amtacctdr, fact.amtacctcr) AS balance,
       fact.m_costelement_id,
       fact.c_bpartner2_id,
       fact.c_bpartner_location_id,
       fact.OpenItemKey,
       fact.OI_TrxType,
       fact.IsOpenItemsReconciled,
       fact.OI_OpenAmount,
       fact.OI_OpenAmountSource,
       fact.c_harvesting_calendar_id,
       fact.harvesting_year_id
FROM fact_acct fact
;