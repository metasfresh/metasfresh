ALTER TABLE c_doctype
    DROP COLUMN CopyDocumentNote
;


select backup_table('c_doctype');
update c_doctype set CopyDescriptionAndDocumentNote=null where  iscopydescriptiontodocument='N';
update c_doctype set CopyDescriptionAndDocumentNote='CD' where  iscopydescriptiontodocument='Y';

