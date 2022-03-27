

ALTER TABLE IF EXISTS public.ad_eventlog DROP CONSTRAINT IF EXISTS cqueueworkpackage_adeventlog;
update AD_Column SET DDL_NoForeignKey='Y', TechnicalNote='No foreign key constraint because C_Queue_WorkPackage_ID can be moved to an archive-table', updatedby=99, updated='2022-02-15 09:15:30' where AD_Column_ID=579208;

