
drop view if exists ad_changelog_counts_v;
create view ad_changelog_counts_v as
select l.ad_table_id, t.tablename, l.ad_column_id, c.columnname, count(l.ad_changelog_id) as count,
       'DELETE FROM AD_ChangeLog WHERE AD_Column_ID='||l.ad_column_id||';'||'/*delete '||t.tablename||'.'||c.columnname|| ' AD_ChangeLog records */' as delete_statement,
       'UPDATE AD_Column SET isallowlogging=''N'' WHERE ad_column_id = '||l.ad_column_id||'; /* change AD_Column '||t.tablename||'.'||c.columnname|| ' to not be AD_ChangeLog''ed anymore */'
from ad_changelog l
         join ad_table t on t.ad_table_id=l.ad_table_id
         join ad_column c on c.ad_column_id=l.ad_column_id
group by l.ad_table_id, t.tablename, l.ad_column_id, c.columnname
order by count(l.ad_changelog_id) desc;
comment on view ad_changelog_counts_v is 'Aims at helping to tune changelog settings by counting the current number of AD_ChangeLog records per column';


UPDATE AD_SysConfig
SET Value='N',
    description=description || '; It is recommented to use N, unless you have a concrete reason not to.',
    updatedby=99, updated='2021-01-23'
WHERE Name = 'SYSTEM_INSERT_CHANGELOG'
;

--M_ShipmentSchedule.QtyOnHand
UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 500235;
DELETE
FROM ad_changelog
WHERE ad_column_id = 500235;

--MD_Candidate.Qty
DELETE
FROM ad_changelog
WHERE ad_column_id = 556476;
UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 556476;

--MD_Candidate.MD_Candidate_GroupId
DELETE
FROM ad_changelog
WHERE ad_column_id = 556510;
UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 556510;

-- AD_Scheduler.Status
DELETE
FROM ad_changelog
WHERE ad_column_id = 545143;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 545143;

--C_Invoice_Candidate.QtyToInvoiceInUOM
DELETE
FROM ad_changelog
WHERE ad_column_id = 568541;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 568541;

--C_Invoice_Candidate.QtyToInvoiceInUOM_Calc
DELETE
FROM ad_changelog
WHERE ad_column_id = 568521;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 568521;

--C_Invoice_Candidate.NetAmtToInvoice
DELETE
FROM ad_changelog
WHERE ad_column_id = 545315;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 545315;

--C_Invoice_Candidate.QtyInvoicedInUOM
DELETE
FROM ad_changelog
WHERE ad_column_id = 568520;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 568520;

--C_Invoice_Candidate.QtyInvoiced
DELETE
FROM ad_changelog
WHERE ad_column_id = 545313;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 545313;

--C_Invoice_Candidate.QtyToInvoice
DELETE
FROM ad_changelog
WHERE ad_column_id = 544914;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 544914;

--C_Invoice_Candidate.QtyToInvoiceBeforeDiscount
DELETE
FROM ad_changelog
WHERE ad_column_id = 551813;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 551813;

--C_Invoice_Candidate.NetAmtInvoiced
DELETE
FROM ad_changelog
WHERE ad_column_id = 545854;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 545854;

--AD_Scheduler.DateNextRun
DELETE
FROM ad_changelog
WHERE ad_column_id = 11257;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 11257;

--AD_Scheduler.DateLastRun
DELETE
FROM ad_changelog
WHERE ad_column_id = 11264;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 11264;

--C_Invoice_Candidate.DateToInvoice
DELETE
FROM ad_changelog
WHERE ad_column_id = 546339;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 546339;


--M_ShipmentSchedule.QtyToDeliver
DELETE
FROM ad_changelog
WHERE ad_column_id = 500237;

UPDATE ad_column
SET isallowlogging='N'
WHERE ad_column_id = 500237;

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=568475;/*delete C_Invoice_Candidate.QtyDeliveredInUOM AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 568475; /* change AD_Column C_Invoice_Candidate.QtyDeliveredInUOMto not be AD_ChagneLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=545314;/*delete C_Invoice_Candidate.QtyDelivered AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 545314; /* change AD_Column C_Invoice_Candidate.QtyDeliveredto not be AD_ChagneLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=544880;/*delete M_ShipmentSchedule.QtyDelivered AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 544880; /* change AD_Column M_ShipmentSchedule.QtyDeliveredto not be AD_ChagneLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=556515;/*delete MD_Candidate.SeqNo AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 556515; /* change AD_Column MD_Candidate.SeqNoto not be AD_ChagneLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=2225;/*delete C_OrderLine.QtyReserved AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 2225; /* change AD_Column C_OrderLine.QtyReservedto not be AD_ChagneLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=544788;/*delete M_ShipmentSchedule.QtyPickList AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 544788; /* change AD_Column M_ShipmentSchedule.QtyPickListto not be AD_ChagneLog'ed anymore */

DELETE FROM AD_ChangeLog WHERE AD_Column_ID=568528;/*delete C_Invoice_Candidate.QtyToInvoiceInPriceUOM AD_ChangeLog records */
UPDATE AD_Column SET isallowlogging='N' WHERE ad_column_id = 568528; /* change AD_Column C_Invoice_Candidate.QtyToInvoiceInPriceUOMto not be AD_ChagneLog'ed anymore */
