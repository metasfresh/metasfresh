/**
  Backup in case StartTime is empty
 */
create table if not exists backup.gh10027_R_Request as
select *
from R_Request
where true
  and isactive = 'Y'
  and starttime is null
  and startdate is not null
;

update R_Request r
set StartTime=StartDate
  , updated='2020-09-29 11:03:05.011762+02'
  , updatedby=100
where true
  and isactive = 'Y'
  and starttime is null
  and startdate is not null
;


/**
  Cleanup AD_UI_Element
 */

create table if not exists backup.gh10027_ad_ui_element as
select ue.*
FROM ad_field f
         INNER JOIN ad_tab t ON f.ad_tab_id = t.ad_tab_id
         inner join ad_ui_element ue on f.ad_field_id = ue.ad_field_id
WHERE f.ad_column_id = 13575
;

delete
from ad_ui_element
where ad_ui_element_id in (select ue.ad_ui_element_id
                           FROM ad_field f
                                    INNER JOIN ad_tab t ON f.ad_tab_id = t.ad_tab_id
                                    inner join ad_ui_element ue on f.ad_field_id = ue.ad_field_id
                           WHERE f.ad_column_id = 13575)
;


/**
  Cleanup AD_Field
 */

create table if not exists backup.gh10027_ad_field as
select f.*
FROM ad_field f
WHERE f.ad_column_id = 13575
;


delete
from ad_field
where ad_field_id in (select f.ad_field_id
                      FROM ad_field f
                      WHERE f.ad_column_id = 13575)
;


/**
  Cleanup AD_Column
 */

create table if not exists backup.gh10027_ad_column as
select c.*
from ad_column c
where ad_column_id = 13575
;

delete
from ad_column
where ad_column_id = 13575
;
;


/**
  Drop the column from this view.
  To me this looks like a ghost view, which is not used/found in any other file in the whole metasfresh project.

  I was advised not to delete it, in case it might be used somewhere.
 */

drop view if exists r_request_v
;

CREATE view r_request_v
            (r_request_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, documentno, r_requesttype_id, r_group_id, r_category_id, r_status_id, r_resolution_id, r_requestrelated_id, priority, priorityuser, duetype, summary, confidentialtype, isescalated, isselfservice, salesrep_id, ad_role_id, datelastaction, datelastalert, lastresult, processed, isinvoiced,
             c_bpartner_id, ad_user_id, c_campaign_id, c_order_id, c_invoice_id, c_payment_id, m_product_id, c_project_id, a_asset_id, m_inout_id, m_rma_id, ad_table_id, record_id, requestamt, r_mailtext_id, result, confidentialtypeentry, r_standardresponse_id, nextaction, datenextaction, starttime, endtime, qtyspent, qtyinvoiced, m_productspent_id, c_activity_id, closedate,
             c_invoicerequest_id, m_changerequest_id, taskstatus, qtyplan, datecompleteplan, datestartplan, m_fixchangenotice_id)
as
SELECT r_request.r_request_id,
       r_request.ad_client_id,
       r_request.ad_org_id,
       r_request.isactive,
       r_request.created,
       r_request.createdby,
       r_request.updated,
       r_request.updatedby,
       r_request.documentno,
       r_request.r_requesttype_id,
       r_request.r_group_id,
       r_request.r_category_id,
       r_request.r_status_id,
       r_request.r_resolution_id,
       r_request.r_requestrelated_id,
       r_request.priority,
       r_request.priorityuser,
       r_request.duetype,
       r_request.summary,
       r_request.confidentialtype,
       r_request.isescalated,
       r_request.isselfservice,
       r_request.salesrep_id,
       r_request.ad_role_id,
       r_request.datelastaction,
       r_request.datelastalert,
       r_request.lastresult,
       r_request.processed,
       r_request.isinvoiced,
       r_request.c_bpartner_id,
       r_request.ad_user_id,
       r_request.c_campaign_id,
       r_request.c_order_id,
       r_request.c_invoice_id,
       r_request.c_payment_id,
       r_request.m_product_id,
       r_request.c_project_id,
       r_request.a_asset_id,
       r_request.m_inout_id,
       r_request.m_rma_id,
       r_request.ad_table_id,
       r_request.record_id,
       r_request.requestamt,
       r_request.r_mailtext_id,
       r_request.result,
       r_request.confidentialtypeentry,
       r_request.r_standardresponse_id,
       r_request.nextaction,
       r_request.datenextaction,
       r_request.starttime,
       r_request.endtime,
       r_request.qtyspent,
       r_request.qtyinvoiced,
       r_request.m_productspent_id,
       r_request.c_activity_id,
       r_request.closedate,
       r_request.c_invoicerequest_id,
       r_request.m_changerequest_id,
       r_request.taskstatus,
       r_request.qtyplan,
       r_request.datecompleteplan,
       r_request.datestartplan,
       r_request.m_fixchangenotice_id
FROM r_request
WHERE r_request.isactive = 'Y'::bpchar
  AND r_request.processed = 'N'::bpchar
  AND getdate() > r_request.datenextaction
;

commit;

/**
  Drop table column
 */
SELECT db_alter_table('r_request', 'alter table r_request drop column startdate')
;

