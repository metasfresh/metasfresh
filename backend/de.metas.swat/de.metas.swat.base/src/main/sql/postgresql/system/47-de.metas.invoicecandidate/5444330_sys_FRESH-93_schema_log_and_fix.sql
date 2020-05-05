
-- Apr 8, 2016 9:37 AM
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,SQLStatement,Statistic_Count,Statistic_Seconds,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,540679,'Y','de.metas.adempiere.process.ExecuteUpdateSQL','N',TO_TIMESTAMP('2016-04-08 09:37:48','YYYY-MM-DD HH24:MI:SS'),100,'Identifies, logs and reenqueues invoice candidates that need to be updated','de.metas.invoicecandidate','Under the hood it selects from the view de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v.<br>
It then inserts the found records into the table de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update and also reenqueues them to be updated.<br>
From the table de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update  we can see which were the problematic ICs.<br>
Each problematic IC might be listed multiple times.<br>
Also see issue .FRESH-93','Y','N','N','N','N','N',0,'C_Invoice_Candidate_Failed_To_Update_Find_Log_Fix','N','Y','select X_MRP_ProductInfo_Detail_Insert_Fallback( (now() + interval ''10 days'')::date);',0,0,'SQL',TO_TIMESTAMP('2016-04-08 09:37:48','YYYY-MM-DD HH24:MI:SS'),100,'C_Invoice_Candidate_Failed_To_Update_Find_Log_Fix')
;

-- Apr 8, 2016 9:37 AM
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540679 AND NOT EXISTS (SELECT * FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Apr 8, 2016 9:40 AM
-- URL zum Konzept
UPDATE AD_Process SET AllowProcessReRun='N',Updated=TO_TIMESTAMP('2016-04-08 09:40:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540679
;

-- Apr 8, 2016 9:42 AM
-- URL zum Konzept
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,Description,EntityType,Frequency,FrequencyType,IsActive,IsIgnoreProcessingTime,KeepLogDays,ManageScheduler,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,540679,0,550036,TO_TIMESTAMP('2016-04-08 09:42:25','YYYY-MM-DD HH24:MI:SS'),100,'See the process doc for infos','de.metas.i',1,'H','Y','N',7,'N','C_Invoice_Candidate_Failed_To_Update_Find_Log_Fix','N','P','F','NEW',0,TO_TIMESTAMP('2016-04-08 09:42:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Apr 8, 2016 9:43 AM
-- URL zum Konzept
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy) VALUES (0,0,540679,540270,TO_TIMESTAMP('2016-04-08 09:43:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','Y',TO_TIMESTAMP('2016-04-08 09:43:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Apr 8, 2016 9:44 AM
-- URL zum Konzept
UPDATE AD_Process SET Name='Find, log and reenqueue ICs that failed to update',Updated=TO_TIMESTAMP('2016-04-08 09:44:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540679
;

-- Apr 8, 2016 9:44 AM
-- URL zum Konzept
UPDATE AD_Process_Trl SET IsTranslated='N' WHERE AD_Process_ID=540679
;

UPDATE AD_Process SET SqlStatement='SELECT de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_Find_Log_Fix();' WHERE AD_Process_ID=540679;

------------------
-- DDL


CREATE SCHEMA de_metas_invoicecandidate;

-- ICs that reference only an inoutLine (no orderline)
CREATE OR REPLACE VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Wrong_QtyDelivered_iol_v AS
SELECT ic.C_Invoice_Candidate_ID, ic.Created, ic.Updated, dt.Name, COALESCE(DateToInvoice_Override, DateToInvoice),
    ic.QtyDelivered,
    SUM(iol.MovementQty)
FROM C_Invoice_Candidate ic
    JOIN C_DocType dt ON dt.C_DocType_ID=ic.C_DocTypeInvoice_ID
    JOIN C_InvoiceCandidate_InOutLine ic_iol ON ic_iol.C_Invoice_Candidate_ID=ic.C_Invoice_Candidate_ID
        JOIN M_InOutLine iol ON iol.M_InOutLine_ID=ic_iol.M_InOutLine_ID
            JOIN M_InOut io ON io.M_InOut_ID=iol.M_InOut_ID
    LEFT JOIN C_OrderLine ol ON ol.C_OrderLine_ID=ic.C_OrderLine_ID
    LEFT JOIN C_Invoice_Candidate_Recompute icr ON icr.C_Invoice_Candidate_ID=ic.C_Invoice_Candidate_ID
WHERE true
	AND ic.Updated + interval '10 minutes' < now() -- were updated at least 10 minutes ago
    AND COALESCE(ic.Processed_Override, ic.Processed)='N'
    AND icr.C_Invoice_Candidate_ID IS NULL -- not recomputation
    AND ol.C_OrderLine_ID IS NULL -- inoutline-handler
    AND io.DocStatus IN ('CO','CL')
GROUP BY ic.C_Invoice_Candidate_ID, ic.Created, ic.Updated, dt.Name, COALESCE(DateToInvoice_Override, DateToInvoice), ic.QtyDelivered
HAVING ABS(ic.QtyDelivered)!=ABS(SUM(iol.MovementQty))
ORDER BY COALESCE(DateToInvoice_Override, DateToInvoice)
;
COMMENT ON VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Wrong_QtyDelivered_iol_v IS
'ICs that do not reference a C_OrderLine, have an inconsistend QtyDelivered value and were created/updated more than 10 minutes ago.
Issue FRESH-93'
;

-- ICs that do reference an orderline
CREATE OR REPLACE VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Wrong_QtyDelivered_ol_v AS
SELECT ic.C_Invoice_Candidate_ID, ic.Created, ic.Updated,
    ic.QtyOrdered, ol.QtyOrdered as ol_qtyOrdered,
    ic.QtyDelivered, ol.QtyDelivered as ol_qtyDelivered
FROM C_Invoice_Candidate ic
    JOIN C_OrderLine ol ON ol.C_OrderLine_ID=ic.C_OrderLine_ID
        JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID
    LEFT JOIN C_Invoice_Candidate_Recompute icr ON icr.C_Invoice_Candidate_ID=ic.C_Invoice_Candidate_ID
WHERE true
	AND ic.Updated + interval '10 minutes' < now() -- were updated at least 10 minutes ago
    AND COALESCE(ic.Processed_Override, ic.Processed)='N'
    AND icr.C_Invoice_Candidate_ID IS NULL -- not recomputed
    AND o.DocStatus IN ('CO','CL')
    AND (
        ic.QtyDelivered!=ol.QtyDelivered
        OR ic.QtyOrdered!=ol.QtyOrdered
    );
COMMENT ON VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Wrong_QtyDelivered_ol_v IS
'ICs that reference a C_OrderLine, have an inconsistend QtyDelivered value and were created/updated more than 10 minutes ago.
Issue FRESH-93'
;
	
	
CREATE OR REPLACE VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Stale_QtyInvoiced_v AS
SELECT ic.C_Invoice_Candidate_ID, ic.QtyInvoiced, SUM(ila.QtyInvoiced)
FROM C_Invoice_Candidate ic
	JOIN C_Invoice_Line_Alloc ila ON ic.C_Invoice_Candidate_ID=ila.C_Invoice_Candidate_ID
WHERE ic.Processed='N'
	AND ic.Updated + interval '10 minutes' < now() -- were updated at least 10 minutes ago
GROUP BY ic.C_Invoice_Candidate_ID, ic.QtyInvoiced
HAVING ic.QtyInvoiced!=SUM(ila.QtyInvoiced)
;
COMMENT ON VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Stale_QtyInvoiced_v IS
'ICs that have an inconsistend QtyInvoiced value and were created/updated more than 10 minutes ago.
Issue FRESH-93'
;
 
-- ICs that don't yet have an aggregation group
CREATE OR REPLACE VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Missing_Aggregation_Group_v AS
SELECT ic.C_Invoice_Candidate_ID, ic.Created, ic.Updated
FROM C_Invoice_Candidate ic
WHERE ic.C_Invoice_Candidate_Headeraggregation_Effective_ID IS NULL AND ic.Processed='N' AND IsToClear='N'
	AND ic.Updated + interval '10 minutes' < now() -- were updated at least 10 minutes ago
ORDER BY Updated desc 
;
COMMENT ON VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Missing_Aggregation_Group_v IS
'ICs that don''t yet have an aggregation group, but were created/updated more than 10 minutes ago.
Issue FRESH-93'
;

-- union all views into one
drop view if exists de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v ;
CREATE OR REPLACE VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v AS
SELECT 
	now()::timestamp with time zone as found,
	null::timestamp with time zone as reenqueued,
	'N'::character(1) AS IsErrorAcknowledged,
	'C_Invoice_Candidate_Wrong_QtyDelivered_iol_v' as problem_found_by,
	ic.*
FROM C_Invoice_Candidate ic 
WHERE ic.C_Invoice_Candidate_ID IN (select C_Invoice_Candidate_ID from de_metas_invoicecandidate.C_Invoice_Candidate_Wrong_QtyDelivered_iol_v)
	UNION
SELECT 
	now(),
	null,
	'N',
	'C_Invoice_Candidate_Wrong_QtyDelivered_ol_v',
	ic.*
FROM C_Invoice_Candidate ic 
WHERE ic.C_Invoice_Candidate_ID IN (select C_Invoice_Candidate_ID from de_metas_invoicecandidate.C_Invoice_Candidate_Wrong_QtyDelivered_ol_v)
	UNION
SELECT 
	now(),
	null,
	'N',
	'C_Invoice_Candidate_Stale_QtyInvoiced_v',
	ic.*
FROM C_Invoice_Candidate ic 
WHERE ic.C_Invoice_Candidate_ID IN (select C_Invoice_Candidate_ID from de_metas_invoicecandidate.C_Invoice_Candidate_Stale_QtyInvoiced_v)
	UNION
SELECT 
	now(),
	null,
	'N',
	'C_Invoice_Candidate_Missing_Aggregation_Group_v',
	ic.*
FROM C_Invoice_Candidate ic 
WHERE ic.C_Invoice_Candidate_ID IN (select C_Invoice_Candidate_ID from de_metas_invoicecandidate.C_Invoice_Candidate_Missing_Aggregation_Group_v)
;
COMMENT ON VIEW de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v IS
'Union that selects all invoice candidates which were idientified by one of the individual views.
Issue FRESH-93'
;

DROP TABLE IF EXISTS de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update;
CREATE TABLE de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update AS
SELECT *
FROM de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v
LIMIT 0;
COMMENT ON TABLE de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update IS
'Serves as a log for ICs that failed to update due to different reasons.
Issue FRESH-93'
;



CREATE OR REPLACE FUNCTION de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_Find_Log_Fix() RETURNS VOID AS
$BODY$

INSERT INTO de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update
SELECT * FROM de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v;

--select * from de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update 

WITH source as (
	UPDATE de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update 
	SET reenqueued=now() 
	WHERE reenqueued is null
	RETURNING C_Invoice_Candidate_ID
)
INSERT INTO C_Invoice_Candidate_Recompute
SELECT C_Invoice_Candidate_ID
FROM source;

$BODY$
  LANGUAGE sql VOLATILE;
  
COMMENT ON FUNCTION de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_Find_Log_Fix() IS
'Executes the view de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update_v and inserts the results into the table de_metas_invoicecandidate.C_Invoice_Candidate_Failed_To_Update.
Then it inserts the problematic ICs'' C_Invoice_Candidate_IDs into C_Invoice_Candidate_Recompute
Issue FRESH-93';
