
create table backup.edi_desadv_20230725_2 as select * from edi_desadv;
update edi_desadv set processed='Y', updatedby=99, updated='2023-07-25 08:44:00' where edi_exportstatus='N' /*Don't send*/ and processed!='Y'
;

